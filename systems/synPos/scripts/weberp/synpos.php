<?php
/**
* synpos.php: synPOS adaptor.
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* http://www.synpos.com
*
* @version 0.9.3
* @copyright Copyright (c) 2006 synPOS.com
*/

define(POSXML_VERSION, '1.0.0');
define(NONE_CATEGORY_ID, '-1');
define(POSXML_HEADER, '<?xml version="1.0" encoding="UTF-8"?>');

/**
* Gets the sub-categories and products in a category.
*
* @param $category The category id. An empty string '' means the root category.
* @return the sub-categories and products in the category or null if category not found
*/
function get_category($category_id) {
	global $db;

	$category_array = array();

	if($category_id) {
		$sql = "SELECT stockid
				FROM stockmaster
				WHERE categoryid = '".$category_id."'";

		$result = DB_query($sql,$db);

		while($myrow = DB_fetch_array($result, $db)) {
			$product_array = get_product($myrow['stockid']);

			if($product_array != null) {
				$category_array['product'][] = $product_array;
			}
		}
	} else {
		$sql = "SELECT *
  				FROM stockcategory";
		$result = DB_query($sql, $db);
		while($categories = DB_fetch_array($result)) {
			$category_array['category'][] = array('id'=>$categories['categoryid'], 'name'=>$categories['categorydescription'], 'desc'=>''); // encode category name. more special chars to escape?
		}
	}

	return $category_array;
}

/**
* Searches products.
*
* @param string $barcode The barcode (model) of the product
* @param string $name The name of the product
* @param string $desc The desc of the product
* @return product array or null if product not found
*/
function search_product($barcode, $name, $desc) {
	global $db;

	$sql = "SELECT stockid
			FROM stockmaster";

	if($barcode) {
		$where .= " barcode = '".$barcode."'";
	}

	if($name) {
		if($where) {
			$where .= " and ";
		}
		$where .= " description like '%".$name."%'";
	}

	if($desc) {
		if($where) {
			$where .= " and ";
		}
		$where .= " longdescription like '%".$desc."%'";
	}

	if($where) {
		$sql .= " where ".$where;
	}

	$result = DB_query($sql,$db);

	while($myrow = DB_fetch_array($result, $db)) {
		$product_array = get_product($myrow['stockid']);

		if($product_array != null) {
			$products_array[] = $product_array;
		}
	}

	return $products_array;
}

/**
* Gets the information of a product given its stockid.
*
* @param string $stockno The stcok id of the product
* @return product array or null if product not found
*/
function get_product($stockid) {
	global $db;
	$product_array = 0;

	$sql = "SELECT currcode, salestype
			FROM debtorsmaster
			WHERE debtorno = '".$_SESSION['CustomerID']."'";

	$result = DB_query($sql,$db);

	if (DB_num_rows($result) > 0){
		$myrow = DB_fetch_array($result, $db);

		$salestype = $myrow['salestype'];
		$currcode = $myrow['currcode'];

		$sql = "SELECT stockmaster.description,
				stockmaster.longdescription,
				stockmaster.stockid,
				stockmaster.mbflag,
				stockmaster.discountcategory,
				(materialcost+labourcost+overheadcost) AS standardcost,
				stockmaster.barcode,
				stockmaster.taxcatid
				FROM stockmaster
				WHERE stockmaster.stockid = '".$stockid."'
				AND stockmaster.barcode != ''
				AND stockmaster.discontinued=0";

		$result1 = DB_query($sql,$db);

		if (DB_num_rows($result1) >0 ) {
			$myrow1 = DB_fetch_array($result1, $db);
			if (($_SESSION['AllowSalesOfZeroCostItems'] == false
			AND $myrow1['standardcost']>0
			AND ($myrow1['mbflag']=='B'
			OR $myrow1['mbflag']=='M'))
			OR ($_SESSION['AllowSalesOfZeroCostItems'] == false
			AND ($myrow1['mbflag']=='A'
			OR $myrow1['mbflag']=='D'
			OR $myrow1['mbflag']=='K'))
			OR $_SESSION['AllowSalesOfZeroCostItems']==true) {

				/*these checks above ensure that the item has a cost if the
				config.php variable AllowSalesOfZeroCostItems is set to false */

				$result = DB_query("SELECT SUM(locstock.quantity) AS quantity
								FROM locstock
								WHERE stockid='" .  $stockid . "'
								AND loccode ='".$_SESSION['UserStockLocation'] . "'
								GROUP BY stockid",$db);

				if(DB_num_rows($result) >0 ) {
					$myrow = DB_fetch_array($result, $db);
					$quantity = $myrow[0];
					//if($quantity < 1) {
					//	prnMsg(_('Out of stock').' ('.$quantity.'): '.$myrow1['stockid'],'warn');
					//}

					$discount = 0;

					if ( $myrow1['DiscountCategory'] != "" ){
						$result = DB_query("SELECT MAX(discountrate) AS discount
								FROM discountmatrix
								WHERE salestype='" .  $salestype . "'
									AND discountcategory ='" . $myrow1['discountcategory'] . "'
									AND quantitybreak < 1",$db);
						$myrow = DB_fetch_row($result);
						if ($myrow[0] != "" && $myrow[0] > 0) {
							$discount = $myrow[0];
						}
					}

					$price = GetPrice ($myrow1['stockid'], $_SESSION['CustomerID'] ,'', $db);

					if($price >= 0) {
						$price -= $discount * $price / 100.0;

						$sql = "SELECT locations.taxprovinceid,
									custbranch.taxgroupid
								FROM custbranch, locations
								WHERE custbranch.debtorno ='".$_SESSION['CustomerID']."'
								AND custbranch.branchcode ='".$_SESSION['UserBranch']."'
								AND locations.loccode=custbranch.defaultlocation";
						$result = DB_query($sql,$db);

						$sql = "SELECT taxgrouptaxes.calculationorder,
									taxauthorities.description,
									taxgrouptaxes.taxauthid,
									taxauthorities.taxglcode,
									taxgrouptaxes.taxontax,
									taxauthrates.taxrate
								FROM custbranch, locations, taxauthrates, taxgrouptaxes, taxauthorities
								WHERE taxgrouptaxes.taxgroupid=custbranch.taxgroupid
								AND taxauthrates.taxauthority=taxgrouptaxes.taxauthid
								AND taxauthrates.taxauthority=taxauthorities.taxid
								AND taxauthrates.dispatchtaxprovince=locations.taxprovinceid
								AND taxauthrates.taxcatid = '" . $myrow1['taxcatid'] . "'
								AND custbranch.debtorno ='".$_SESSION['CustomerID']."'
								AND custbranch.branchcode ='".$_SESSION['UserBranch']."'
								AND locations.loccode=custbranch.defaultlocation
								ORDER BY taxgrouptaxes.calculationorder";

						$result = DB_query($sql,$db);

						while ($myrow = DB_fetch_array($result)){
							$taxes[$myrow['calculationorder']] = new Tax($myrow['calculationorder'],
							$myrow['taxauthid'],
							$myrow['description'],
							$myrow['taxrate'],
							$myrow['taxontax'],
							$myrow['taxglcode']);
						}

						$tax_total =0;

						foreach ($taxes AS $tax) {
							if ($tax->TaxOnTax ==1){
								$tax_total += ($tax->TaxRate * ($price + $tax_total));
							} else {
								$tax_total += ($tax->TaxRate * $price);
							}
						}

						$tax_rate = 0;

						if($price > 0) {
							$tax_rate = 100.0 * $tax_total / $price;
						}

						$product_array = array('id'=>$myrow1['stockid'],
						'barcode' => $myrow1['barcode'],
						'name' => $myrow1['description'],
						'desc' => $myrow1['longdescription'],
						'price' => number_format($price, 2, '.', ''),
						'quantity' => $quantity,
						'tax' => number_format($tax_rate, 2, '.', ''));
					}
				}
			}
		}
	}

	return $product_array;
}

/**
* Creates a new order.
*
* @param array $products The products ordered
* @param string $subtotal The subtotal of this order
* @param string $tax The tax of this order
* @param string $total The total of this order
* @param string $customer_id The customer id
* @return order id or false
*/
function create_order($products, $subtotal, $tax, $total, $customer_id) {
	global $db;

	$_SESSION['Items'] = new Cart;
	$today = Date("Y-m-d H:i");

	$OrderHeaderSQL = "SELECT
					custbranch.debtorno,
					debtorsmaster.name,
					debtorsmaster.salestype,
					custbranch.branchcode,
					custbranch.brname,
					custbranch.braddress1,
					custbranch.braddress2,
					custbranch.braddress3,
					custbranch.braddress4,
					custbranch.braddress5,
					custbranch.braddress6,
					custbranch.phoneno,
					custbranch.email,
					debtorsmaster.currcode,
					custbranch.defaultlocation,
					locations.taxprovinceid,
					custbranch.taxgroupid,
					currencies.rate as currency_rate,
					custbranch.defaultshipvia
			FROM
				debtorsmaster,
				custbranch,
				currencies,
				locations
			WHERE custbranch.debtorno = debtorsmaster.debtorno
			AND custbranch.branchcode ='".$customer_id."'
			AND custbranch.debtorno = '".$_SESSION['CustomerID']."'
			AND locations.loccode=custbranch.defaultlocation
			AND debtorsmaster.currcode = currencies.currabrev
			";

	$result = DB_query($OrderHeaderSQL, $db);
	$myrow = DB_fetch_array($result);

	$_SESSION['Items']->DebtorNo = $myrow['debtorno'];
	//$_SESSION['Items']->OrderNo = $myrow['orderno'];
	$_SESSION['Items']->Branch = $myrow['branchcode'];
	$_SESSION['Items']->CustomerName = $myrow['name'];
	$_SESSION['Items']->CustRef = '';
	$_SESSION['Items']->Comments = '';
	$_SESSION['Items']->DefaultSalesType =$myrow['salestype'];
	$_SESSION['Items']->DefaultCurrency = $myrow['currcode'];
	//$BestShipper = $myrow['defaultshipvia'];
	$_SESSION['Items']->ShipVia = $myrow['defaultshipvia'];

	//if (is_null($BestShipper)){
	//   $BestShipper=0;
	//}
	$_SESSION['Items']->DeliverTo = $myrow['brname'];
	$_SESSION['Items']->DeliveryDate = ConvertSQLDate($today);
	$_SESSION['Items']->BrAdd1 = $myrow['braddress1'];
	$_SESSION['Items']->BrAdd2 = $myrow['braddress2'];
	$_SESSION['Items']->BrAdd3 = $myrow['braddress3'];
	$_SESSION['Items']->BrAdd4 = $myrow['braddress4'];
	$_SESSION['Items']->BrAdd5 = $myrow['braddress5'];
	$_SESSION['Items']->BrAdd6 = $myrow['braddress6'];
	$_SESSION['Items']->PhoneNo = $myrow['phoneno'];
	$_SESSION['Items']->Email = $myrow['email'];
	$_SESSION['Items']->Location = $myrow['defaultlocation'];
	$_SESSION['Items']->FreightCost = 0;
	$_SESSION['Old_FreightCost'] = 0;
	//$_POST['ChargeFreightCost'] = $_SESSION['Old_FreightCost'];
	$_SESSION['Items']->Orig_OrderDate = $today;
	$_SESSION['CurrencyRate'] = $myrow['currency_rate'];
	$_SESSION['Items']->TaxGroup = $myrow['taxgroupid'];
	$_SESSION['Items']->DispatchTaxProvince = $myrow['taxprovinceid'];
	//$_SESSION['Items']->GetFreightTaxes();
	$_SESSION['Items']->Quotation = 0;
	$_SESSION['Items']->DeliverBlind = 1;

	for($i = 0; $i < count($products); $i++) {
		// check quantity
		$result = DB_query("SELECT SUM(locstock.quantity) AS quantity
								FROM locstock, custbranch, stockmaster
								WHERE locstock.stockid=stockmaster.stockid
								AND locstock.loccode =custbranch.defaultlocation
								AND stockmaster.stockid='".$products[$i]['ProductId']."'
								AND custbranch.branchcode='".$customer_id."'
								AND custbranch.debtorno='".$_SESSION['CustomerID']."'
								GROUP BY locstock.stockid",$db);
		$myrow = DB_fetch_row($result);

		if($myrow[0] < $products[$i]['Quantity']) {
			return false;
		}

		// add to cart
		$LineItemsSQL = "SELECT stockmaster.stockid,
					stockmaster.description,
					stockmaster.controlled,
					stockmaster.serialised,
					stockmaster.volume,
					stockmaster.kgs,
					stockmaster.units,
					stockmaster.decimalplaces,
					stockmaster.mbflag,
					stockmaster.taxcatid,
					stockmaster.discountcategory,
					stockmaster.materialcost +
						stockmaster.labourcost +
						stockmaster.overheadcost AS standardcost
				FROM stockmaster
				WHERE stockmaster.stockid ='" . $products[$i]['ProductId'] . "'";

		$LineItemsResult = DB_query($LineItemsSQL,$db);

		if (db_num_rows($LineItemsResult)>0) {
			$myrow=db_fetch_array($LineItemsResult);
			$price = GetPrice ($myrow['stockid'], $_SESSION['CustomerID'] ,'', $db);
			$discount = 0;

			if ( $myrow['discountcategory'] != "" ){
				$result = DB_query("SELECT MAX(discountrate) AS discount
								FROM discountmatrix
								WHERE salestype='" .  $_SESSION['Items']->DefaultSalesType . "'
									AND discountcategory ='" . $myrow['discountcategory'] . "'
									AND quantitybreak < 1",$db);
				$myrow1 = DB_fetch_row($result);
				if ($myrow1[0] != "" && $myrow1[0] > 0) {
					$discount = $myrow1[0];
				}
			}

			$_SESSION['Items']->add_to_cart($myrow['stockid'],
			$products[$i]['Quantity'],
			$myrow['description'],
			$price,
			$discount,
			$myrow['units'],
			$myrow['volume'],
			$myrow['kgs'],
			0,
			$myrow['mbflag'],
			$today,
			$products[$i]['Quantity'],
			$myrow['discountcategory'],
			$myrow['controlled'],
			$myrow['serialised'],
			$myrow['decimalplaces'],
			'',
			'No',
			0,
			$myrow['taxcatid']);	/*NB NO Updates to DB */

			$_SESSION['Items']->LineItems[($_SESSION['Items']->LineCounter -1)]->StandardCost = $myrow['standardcost'];

			/*Calculate the taxes applicable to this line item from the customer branch Tax Group and Item Tax Category */

			$_SESSION['Items']->GetTaxes($_SESSION['Items']->LineCounter -1);
		}
	}

	$DelDate = FormatDateforSQL($_SESSION['Items']->DeliveryDate);

	$HeaderSQL = "INSERT INTO salesorders (
				debtorno,
				branchcode,
				customerref,
				comments,
				orddate,
				ordertype,
				shipvia,
				deliverto,
				deladd1,
				deladd2,
				deladd3,
				deladd4,
				deladd5,
				deladd6,
				contactphone,
				contactemail,
				freightcost,
				fromstkloc,
				deliverydate,
				quotation,
                deliverblind)
			VALUES (
				'" . $_SESSION['Items']->DebtorNo . "',
				'" . $_SESSION['Items']->Branch . "',
				'". DB_escape_string($_SESSION['Items']->CustRef) ."',
				'". DB_escape_string($_SESSION['Items']->Comments) ."',
				'" . Date("Y-m-d H:i") . "',
				'" . $_SESSION['Items']->DefaultSalesType . "',
				" . $_SESSION['Items']->ShipVia .",
				'" . DB_escape_string($_SESSION['Items']->DeliverTo) . "',
				'" . DB_escape_string($_SESSION['Items']->DelAdd1) . "',
				'" . DB_escape_string($_SESSION['Items']->DelAdd2) . "',
				'" . DB_escape_string($_SESSION['Items']->DelAdd3) . "',
				'" . DB_escape_string($_SESSION['Items']->DelAdd4) . "',
				'" . DB_escape_string($_SESSION['Items']->DelAdd5) . "',
				'" . DB_escape_string($_SESSION['Items']->DelAdd6) . "',
				'" . DB_escape_string($_SESSION['Items']->PhoneNo) . "',
				'" . DB_escape_string($_SESSION['Items']->Email) . "',
				" . $_SESSION['Items']->FreightCost .",
				'" . $_SESSION['Items']->Location ."',
				'" . $DelDate . "',
				" . $_SESSION['Items']->Quotation . ",
				" . $_SESSION['Items']->DeliverBlind ."
                )";

	$InsertQryResult = DB_query($HeaderSQL,$db);

	$OrderNo = DB_Last_Insert_ID($db,'salesorders','orderno');

	$_SESSION['Items']->total = 0;
	$_SESSION['Items']->totalVolume = 0;
	$_SESSION['Items']->totalWeight = 0;
	$TaxTotal =0;
	$TaxTotals = array();
	$TaxGLCodes = array();
	$StartOf_LineItemsSQL = "INSERT INTO salesorderdetails (
						orderlineno,
						orderno,
						stkcode,
						unitprice,
						quantity,
						discountpercent,
						narrative)
					VALUES (";

	foreach ($_SESSION['Items']->LineItems as $StockItem) {

		$LineItemsSQL = $StartOf_LineItemsSQL .
		$StockItem->LineNumber . ",
					" . $OrderNo . ",
					'" . $StockItem->StockID . "',
					". $StockItem->Price . ",
					" . $StockItem->Quantity . ",
					" . floatval($StockItem->DiscountPercent) . ",
					'" . DB_escape_string($StockItem->Narrative) . "'
				)";

		$Ins_LineItemResult = DB_query($LineItemsSQL, $db);

		$LineTotal = $StockItem->Quantity * $StockItem->Price * (1 - $StockItem->DiscountPercent);
		$_SESSION['Items']->total = $_SESSION['Items']->total + $LineTotal;
		$_SESSION['Items']->totalVolume = $_SESSION['Items']->totalVolume + ($StockItem->Quantity * $StockItem->Volume);
		$_SESSION['Items']->totalWeight = $_SESSION['Items']->totalWeight + ($StockItem->Quantity * $StockItem->Weight);
		$TaxLineTotal =0; //initialise tax total for the line

		foreach ($StockItem->Taxes AS $Tax) {

			if ($Tax->TaxOnTax ==1){
				$TaxTotals[$Tax->TaxAuthID] += ($Tax->TaxRate * ($LineTotal + $TaxLineTotal));
				$TaxLineTotal += ($Tax->TaxRate * ($LineTotal + $TaxLineTotal));
			} else {
				$TaxTotals[$Tax->TaxAuthID] += ($Tax->TaxRate * $LineTotal);
				$TaxLineTotal += ($Tax->TaxRate * $LineTotal);
			}
			$TaxGLCodes[$Tax->TaxAuthID] = $Tax->TaxGLCode;

		}


		$TaxTotal += $TaxLineTotal;
	} /* inserted line items into sales order details */
	/*Now Get the next invoice number - function in SQL_CommonFunctions*/
	$DefaultDispatchDate = Date("Y-m-d H:i");
	$InvoiceNo = GetNextTransNo(10, $db);
	$PeriodNo = GetPeriod($DefaultDispatchDate, $db);

	/*Start an SQL transaction */

	$SQL = "BEGIN";
	$Result = DB_query($SQL,$db);
	$DefaultDispatchDate = FormatDateForSQL($DefaultDispatchDate);
	/*Update order header for invoice charged on */
	$SQL = "UPDATE salesorders SET comments = CONCAT(comments,' Inv ','" . $InvoiceNo . "') WHERE orderno= " . $OrderNo;

	$Result = DB_query($SQL,$db);

	/*Now insert the DebtorTrans */

	$SQL = "INSERT INTO debtortrans (
			transno,
			type,
			debtorno,
			branchcode,
			trandate,
			prd,
			reference,
			tpe,
			order_,
			ovamount,
			ovgst,
			ovfreight,
			rate,
			invtext,
			shipvia,
			consignment
			)
		VALUES (
			". $InvoiceNo . ",
			10,
			'" . $_SESSION['CustomerID'] . "',
			'" . $customer_id . "',
			'" . $DefaultDispatchDate . "',
			" . $PeriodNo . ",
			'',
			'" . $_SESSION['Items']->DefaultSalesType . "',
			" . $OrderNo . ",
			" . $_SESSION['Items']->total . ",
			" . $TaxTotal . ",
			0,
			" . $_SESSION['CurrencyRate'] . ",
			'',
			" . $_SESSION['Items']->ShipVia . ",
			''
		)";


	$Result = DB_query($SQL,$db);

	$DebtorTransID = DB_Last_Insert_ID($db,'debtortrans','id');

	/* Insert the tax totals for each tax authority where tax was charged on the invoice */
	foreach ($TaxTotals AS $TaxAuthID => $TaxAmount) {

		$SQL = 'INSERT INTO debtortranstaxes (debtortransid,
							taxauthid,
							taxamount)
				VALUES (' . $DebtorTransID . ',
					' . $TaxAuthID . ',
					' . $TaxAmount/$_SESSION['CurrencyRate'] . ')';

		$Result = DB_query($SQL,$db);
	}

	foreach ($_SESSION['Items']->LineItems as $OrderLine) {
		/* Update location stock records if not a dummy stock item
		need the MBFlag later too so save it to $MBFlag */
		$Result = DB_query("SELECT mbflag FROM stockmaster WHERE stockid = '" . $OrderLine->StockID . "'",$db);

		$myrow = DB_fetch_row($Result);
		$MBFlag = $myrow[0];

		if ($MBFlag=="B" OR $MBFlag=="M") {
			$Assembly = False;

			/* Need to get the current location quantity
			will need it later for the stock movement */
			$SQL="SELECT locstock.quantity
					FROM locstock
					WHERE locstock.stockid='" . $OrderLine->StockID . "'
					AND loccode= '" . $_SESSION['Items']->Location . "'";
			//$ErrMsg = _('WARNING') . ': ' . _('Could not retrieve current location stock');
			$Result = DB_query($SQL, $db);

			if (DB_num_rows($Result)==1){
				$LocQtyRow = DB_fetch_row($Result);
				$QtyOnHandPrior = $LocQtyRow[0];
			} else {
				/* There must be some error this should never happen */
				$QtyOnHandPrior = 0;
			}

			$SQL = "UPDATE locstock
					SET quantity = locstock.quantity - " . $OrderLine->QtyDispatched . "
					WHERE locstock.stockid = '" . $OrderLine->StockID . "'
					AND loccode = '" . $_SESSION['Items']->Location . "'";

			//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Location stock record could not be updated because');
			//$DbgMsg = _('The following SQL to update the location stock record was used');
			$Result = DB_query($SQL,$db);

		} else if ($MBFlag=='A'){ /* its an assembly */
			/*Need to get the BOM for this part and make
			stock moves for the components then update the Location stock balances */
			$Assembly=True;
			$StandardCost =0; /*To start with - accumulate the cost of the comoponents for use in journals later on */
			$SQL = "SELECT bom.component,
						bom.quantity,
						stockmaster.materialcost+stockmaster.labourcost+stockmaster.overheadcost AS standard
					FROM bom,
						stockmaster
					WHERE bom.component=stockmaster.stockid
					AND bom.parent='" . $OrderLine->StockID . "'
					AND bom.effectiveto > '" . Date("Y-m-d") . "'
					AND bom.effectiveafter < '" . Date("Y-m-d") . "'";

			//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Could not retrieve assembly components from the database for'). ' '. $OrderLine->StockID . _('because').' ';
			//$DbgMsg = _('The SQL that failed was');
			$AssResult = DB_query($SQL,$db);

			while ($AssParts = DB_fetch_array($AssResult,$db)){

				$StandardCost += ($AssParts['standard'] * $AssParts['quantity']) ;
				/* Need to get the current location quantity
				will need it later for the stock movement */
				$SQL="SELECT locstock.quantity
						FROM locstock
						WHERE locstock.stockid='" . $AssParts['component'] . "'
						AND loccode= '" . $_SESSION['Items']->Location . "'";

				//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Can not retrieve assembly components location stock quantities because ');
				//$DbgMsg = _('The SQL that failed was');
				$Result = DB_query($SQL,$db);
				if (DB_num_rows($Result)==1){
					$LocQtyRow = DB_fetch_row($Result);
					$QtyOnHandPrior = $LocQtyRow[0];
				} else {
					/*There must be some error this should never happen */
					$QtyOnHandPrior = 0;
				}

				$SQL = "INSERT INTO stockmoves (
							stockid,
							type,
							transno,
							loccode,
							trandate,
							debtorno,
							branchcode,
							prd,
							reference,
							qty,
							standardcost,
							show_on_inv_crds,
							newqoh
						) VALUES (
							'" . $AssParts['component'] . "',
							 10,
							 " . $InvoiceNo . ",
							 '" . $_SESSION['Items']->Location . "',
							 '" . $DefaultDispatchDate . "',
							 '" . $_SESSION['Items']->DebtorNo . "',
							 '" . $_SESSION['Items']->Branch . "',
							 " . $PeriodNo . ",
							 '" . _('Assembly') . ': ' . $OrderLine->StockID . ' ' . _('Order') . ': ' . $OrderNo . "',
							 " . -$AssParts['quantity'] * $OrderLine->QtyDispatched . ",
							 " . $AssParts['standard'] . ",
							 0,
							 " . ($QtyOnHandPrior -($AssParts['quantity'] * $OrderLine->QtyDispatched)) . "
						)";
				//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Stock movement records for the assembly components of'). ' '. $OrderLine->StockID . ' ' . _('could not be inserted because');
				//$DbgMsg = _('The following SQL to insert the assembly components stock movement records was used');
				$Result = DB_query($SQL,$db);


				$SQL = "UPDATE locstock
						SET quantity = locstock.quantity - " . $AssParts['quantity'] * $OrderLine->QtyDispatched . "
						WHERE locstock.stockid = '" . $AssParts['component'] . "'
						AND loccode = '" . $_SESSION['Items']->Location . "'";

				//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Location stock record could not be updated for an assembly component because');
				//$DbgMsg = _('The following SQL to update the locations stock record for the component was used');
				$Result = DB_query($SQL,$db);
			} /* end of assembly explosion and updates */

			/*Update the cart with the recalculated standard cost from the explosion of the assembly's components*/
			$_SESSION['Items']->LineItems[$OrderLine->LineNumber]->StandardCost = $StandardCost;
			$OrderLine->StandardCost = $StandardCost;
		} /* end of its an assembly */

		// Insert stock movements - with unit cost
		$LocalCurrencyPrice= ($OrderLine->Price / $_SESSION['CurrencyRate']);

		if ($MBFlag=='B' OR $MBFlag=='M'){
			$SQL = "INSERT INTO stockmoves (
						stockid,
						type,
						transno,
						loccode,
						trandate,
						debtorno,
						branchcode,
						price,
						prd,
						reference,
						qty,
						discountpercent,
						standardcost,
						newqoh,
						narrative )
					VALUES ('" . $OrderLine->StockID . "',
						10,
						" . $InvoiceNo . ",
						'" . $_SESSION['Items']->Location . "',
						'" . $DefaultDispatchDate . "',
						'" . $_SESSION['Items']->DebtorNo . "',
						'" . $_SESSION['Items']->Branch . "',
						" . $LocalCurrencyPrice . ",
						" . $PeriodNo . ",
						'" . $OrderNo . "',
						" . -$OrderLine->QtyDispatched . ",
						" . $OrderLine->DiscountPercent . ",
						" . $OrderLine->StandardCost . ",
						" . ($QtyOnHandPrior - $OrderLine->QtyDispatched) . ",
						'" . DB_escape_string($OrderLine->Narrative) . "' )";
		} else {
			// its an assembly or dummy and assemblies/dummies always have nil stock (by definition they are made up at the time of dispatch  so new qty on hand will be nil
			$SQL = "INSERT INTO stockmoves (
						stockid,
						type,
						transno,
						loccode,
						trandate,
						debtorno,
						branchcode,
						price,
						prd,
						reference,
						qty,
						discountpercent,
						standardcost,
						narrative )
					VALUES ('" . $OrderLine->StockID . "',
						10,
						" . $InvoiceNo . ",
						'" . $_SESSION['Items']->Location . "',
						'" . $DefaultDispatchDate . "',
						'" . $_SESSION['Items']->DebtorNo . "',
						'" . $_SESSION['Items']->Branch . "',
						" . $LocalCurrencyPrice . ",
						" . $PeriodNo . ",
						'" . $OrderNO . "',
						" . -$OrderLine->QtyDispatched . ",
						" . $OrderLine->DiscountPercent . ",
						" . $OrderLine->StandardCost . ",
						'" . addslashes($OrderLine->Narrative) . "')";
		}


		//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Stock movement records could not be inserted because');
		//$DbgMsg = _('The following SQL to insert the stock movement records was used');
		$Result = DB_query($SQL,$db);

		/*Get the ID of the StockMove... */
		$StkMoveNo = DB_Last_Insert_ID($db,'stockmoves','stkmoveno');

		/*Insert the taxes that applied to this line */
		foreach ($OrderLine->Taxes as $Tax) {

			$SQL = 'INSERT INTO stockmovestaxes (stkmoveno,
									taxauthid,
									taxrate,
									taxcalculationorder,
									taxontax)
						VALUES (' . $StkMoveNo . ',
							' . $Tax->TaxAuthID . ',
							' . $Tax->TaxRate . ',
							' . $Tax->TaxCalculationOrder . ',
							' . $Tax->TaxOnTax . ')';

			//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Taxes and rates applicable to this invoice line item could not be inserted because');
			//$DbgMsg = _('The following SQL to insert the stock movement tax detail records was used');
			$Result = DB_query($SQL,$db);
		}


		/*Insert the StockSerialMovements and update the StockSerialItems  for controlled items*/

		if ($OrderLine->Controlled ==1){
			foreach($OrderLine->SerialItems as $Item){
				/*We need to add the StockSerialItem record and
				The StockSerialMoves as well */

				$SQL = "UPDATE stockserialitems
							SET quantity= quantity - " . $Item->BundleQty . "
							WHERE stockid='" . $OrderLine->StockID . "'
							AND loccode='" . $_SESSION['Items']->Location . "'
							AND serialno='" . $Item->BundleRef . "'";

				//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The serial stock item record could not be updated because');
				//$DbgMsg = _('The following SQL to update the serial stock item record was used');
				$Result = DB_query($SQL, $db);

				/* now insert the serial stock movement */

				$SQL = "INSERT INTO stockserialmoves (stockmoveno,
										stockid,
										serialno,
										moveqty)
						VALUES (" . $StkMoveNo . ",
							'" . $OrderLine->StockID . "',
							'" . $Item->BundleRef . "',
							" . -$Item->BundleQty . ")";

				//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The serial stock movement record could not be inserted because');
				//$DbgMsg = _('The following SQL to insert the serial stock movement records was used');
				$Result = DB_query($SQL, $db);
			}/* foreach controlled item in the serialitems array */
		} /*end if the orderline is a controlled item */

		/*Insert Sales Analysis records */

		$SQL="SELECT COUNT(*),
					salesanalysis.stockid,
					salesanalysis.stkcategory,
					salesanalysis.cust,
					salesanalysis.custbranch,
					salesanalysis.area,
					salesanalysis.periodno,
					salesanalysis.typeabbrev,
					salesanalysis.salesperson
				FROM salesanalysis,
					custbranch,
					stockmaster
				WHERE salesanalysis.stkcategory=stockmaster.categoryid
				AND salesanalysis.stockid=stockmaster.stockid
				AND salesanalysis.cust=custbranch.debtorno
				AND salesanalysis.custbranch=custbranch.branchcode
				AND salesanalysis.area=custbranch.area
				AND salesanalysis.salesperson=custbranch.salesman
				AND salesanalysis.typeabbrev ='" . $_SESSION['Items']->DefaultSalesType . "'
				AND salesanalysis.periodno=" . $PeriodNo . "
				AND salesanalysis.cust " . LIKE . " '" . $_SESSION['Items']->DebtorNo . "'
				AND salesanalysis.custbranch " . LIKE . " '" . $_SESSION['Items']->Branch . "'
				AND salesanalysis.stockid " . LIKE . " '" . $OrderLine->StockID . "'
				AND salesanalysis.budgetoractual=1
				GROUP BY salesanalysis.stockid,
					salesanalysis.stkcategory,
					salesanalysis.cust,
					salesanalysis.custbranch,
					salesanalysis.area,
					salesanalysis.periodno,
					salesanalysis.typeabbrev,
					salesanalysis.salesperson";

		//$ErrMsg = _('The count of existing Sales analysis records could not run because');
		//$DbgMsg = '<P>'. _('SQL to count the no of sales analysis records');
		$Result = DB_query($SQL,$db);

		$myrow = DB_fetch_row($Result);

		if ($myrow[0]>0){  /*Update the existing record that already exists */

			$SQL = "UPDATE salesanalysis
					SET amt=amt+" . ($OrderLine->Price * $OrderLine->QtyDispatched / $_SESSION['CurrencyRate']) . ",
					cost=cost+" . ($OrderLine->StandardCost * $OrderLine->QtyDispatched) . ",
					qty=qty +" . $OrderLine->QtyDispatched . ",
					disc=disc+" . ($OrderLine->DiscountPercent * $OrderLine->Price * $OrderLine->QtyDispatched / $_SESSION['CurrencyRate']) . "
					WHERE salesanalysis.area='" . $myrow[5] . "'
					AND salesanalysis.salesperson='" . $myrow[8] . "'
					AND typeabbrev ='" . $_SESSION['Items']->DefaultSalesType . "'
					AND periodno = " . $PeriodNo . "
					AND cust " . LIKE . " '" . $_SESSION['Items']->DebtorNo . "'
					AND custbranch " . LIKE . " '" . $_SESSION['Items']->Branch . "'
					AND stockid " . LIKE . " '" . $OrderLine->StockID . "'
					AND salesanalysis.stkcategory ='" . $myrow[2] . "'
					AND budgetoractual=1";

		} else { /* insert a new sales analysis record */

			$SQL = "INSERT INTO salesanalysis (
						typeabbrev,
						periodno,
						amt,
						cost,
						cust,
						custbranch,
						qty,
						disc,
						stockid,
						area,
						budgetoractual,
						salesperson,
						stkcategory
						)
					SELECT '" . $_SESSION['Items']->DefaultSalesType . "',
						" . $PeriodNo . ",
						" . ($OrderLine->Price * $OrderLine->QtyDispatched / $_SESSION['CurrencyRate']) . ",
						" . ($OrderLine->StandardCost * $OrderLine->QtyDispatched) . ",
						'" . $_SESSION['Items']->DebtorNo . "',
						'" . $_SESSION['Items']->Branch . "',
						" . $OrderLine->QtyDispatched . ",
						" . ($OrderLine->DiscountPercent * $OrderLine->Price * $OrderLine->QtyDispatched / $_SESSION['CurrencyRate']) . ",
						'" . $OrderLine->StockID . "',
						custbranch.area,
						1,
						custbranch.salesman,
						stockmaster.categoryid
					FROM stockmaster,
						custbranch
					WHERE stockmaster.stockid = '" . $OrderLine->StockID . "'
					AND custbranch.debtorno = '" . $_SESSION['Items']->DebtorNo . "'
					AND custbranch.branchcode='" . $_SESSION['Items']->Branch . "'";
		}

		//$ErrMsg = _('Sales analysis record could not be added or updated because');
		//$DbgMsg = _('The following SQL to insert the sales analysis record was used');
		$Result = DB_query($SQL,$db);

		/* If GLLink_Stock then insert GLTrans to credit stock and debit cost of sales at standard cost*/

		if ($_SESSION['CompanyRecord']['gllink_stock']==1 AND $OrderLine->StandardCost !=0){

			/*first the cost of sales entry*/

			$SQL = "INSERT INTO gltrans (
							type,
							typeno,
							trandate,
							periodno,
							account,
							narrative,
							amount
							)
					VALUES (
						10,
						" . $InvoiceNo . ",
						'" . $DefaultDispatchDate . "',
						" . $PeriodNo . ",
						" . GetCOGSGLAccount($Area, $OrderLine->StockID, $_SESSION['Items']->DefaultSalesType, $db) . ",
						'" . $_SESSION['Items']->DebtorNo . " - " . $OrderLine->StockID . " x " . $OrderLine->QtyDispatched . " @ " . $OrderLine->StandardCost . "',
						" . $OrderLine->StandardCost * $OrderLine->QtyDispatched . "
					)";

			//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The cost of sales GL posting could not be inserted because');
			//$DbgMsg = _('The following SQL to insert the GLTrans record was used');
			$Result = DB_query($SQL,$db);

			/*now the stock entry*/
			$StockGLCode = GetStockGLCode($OrderLine->StockID,$db);

			$SQL = "INSERT INTO gltrans (
							type,
							typeno,
							trandate,
							periodno,
							account,
							narrative,
							amount)
					VALUES (
						10,
						" . $InvoiceNo . ",
						'" . $DefaultDispatchDate . "',
						" . $PeriodNo . ",
						" . $StockGLCode['stockact'] . ",
						'" . $_SESSION['Items']->DebtorNo . " - " . $OrderLine->StockID . " x " . $OrderLine->QtyDispatched . " @ " . $OrderLine->StandardCost . "',
						" . (-$OrderLine->StandardCost * $OrderLine->QtyDispatched) . "
					)";

			//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The stock side of the cost of sales GL posting could not be inserted because');
			//$DbgMsg = _('The following SQL to insert the GLTrans record was used');
			$Result = DB_query($SQL,$db);
		} /* end of if GL and stock integrated and standard cost !=0 */

		if ($_SESSION['CompanyRecord']['gllink_debtors']==1 AND $OrderLine->Price !=0){

			//Post sales transaction to GL credit sales
			$SalesGLAccounts = GetSalesGLAccount($Area, $OrderLine->StockID, $_SESSION['Items']->DefaultSalesType, $db);

			$SQL = "INSERT INTO gltrans (
							type,
							typeno,
							trandate,
							periodno,
							account,
							narrative,
							amount
						)
					VALUES (
						10,
						" . $InvoiceNo . ",
						'" . $DefaultDispatchDate . "',
						" . $PeriodNo . ",
						" . $SalesGLAccounts['salesglcode'] . ",
						'" . $_SESSION['Items']->DebtorNo . " - " . $OrderLine->StockID . " x " . $OrderLine->QtyDispatched . " @ " . $OrderLine->Price . "',
						" . (-$OrderLine->Price * $OrderLine->QtyDispatched/$_SESSION['CurrencyRate']) . "
					)";

			//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The sales GL posting could not be inserted because');
			//$DbgMsg = '<BR>' ._('The following SQL to insert the GLTrans record was used');
			$Result = DB_query($SQL,$db);

			if ($OrderLine->DiscountPercent !=0){

				$SQL = "INSERT INTO gltrans (
							type,
							typeno,
							trandate,
							periodno,
							account,
							narrative,
							amount
						)
						VALUES (
							10,
							" . $InvoiceNo . ",
							'" . $DefaultDispatchDate . "',
							" . $PeriodNo . ",
							" . $SalesGLAccounts['discountglcode'] . ",
							'" . $_SESSION['Items']->DebtorNo . " - " . $OrderLine->StockID . " @ " . ($OrderLine->DiscountPercent * 100) . "%',
							" . ($OrderLine->Price * $OrderLine->QtyDispatched * $OrderLine->DiscountPercent/$_SESSION['CurrencyRate']) . "
						)";

				//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The sales discount GL posting could not be inserted because');
				//$DbgMsg = _('The following SQL to insert the GLTrans record was used');
				$Result = DB_query($SQL,$db);
			} /*end of if discount !=0 */
		} /*end of if sales integrated with debtors */

	} /*Quantity dispatched is more than 0 */
	/*end of OrderLine loop */

	if ($_SESSION['CompanyRecord']['gllink_debtors']==1){

		/*Post debtors transaction to GL debit debtors, credit freight re-charged and credit sales */
		if (($_SESSION['Items']->total + $_SESSION['Items']->FreightCost + $TaxTotal) !=0) {
			$SQL = "INSERT INTO gltrans (
						type,
						typeno,
						trandate,
						periodno,
						account,
						narrative,
						amount
						)
					VALUES (
						10,
						" . $InvoiceNo . ",
						'" . $DefaultDispatchDate . "',
						" . $PeriodNo . ",
						" . $_SESSION['CompanyRecord']['debtorsact'] . ",
						'" . $_SESSION['Items']->DebtorNo . "',
						" . (($_SESSION['Items']->total + $_SESSION['Items']->FreightCost + $TaxTotal)/$_SESSION['CurrencyRate']) . "
					)";

			//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The total debtor GL posting could not be inserted because');
			//$DbgMsg = _('The following SQL to insert the total debtors control GLTrans record was used');
			$Result = DB_query($SQL,$db);
		}

		/*Could do with setting up a more flexible freight posting schema that looks at the sales type and area of the customer branch to determine where to post the freight recovery */

		if ($_SESSION['Items']->FreightCost !=0) {
			$SQL = "INSERT INTO gltrans (
						type,
						typeno,
						trandate,
						periodno,
						account,
						narrative,
						amount
					)
				VALUES (
					10,
					" . $InvoiceNo . ",
					'" . $DefaultDispatchDate . "',
					" . $PeriodNo . ",
					" . $_SESSION['CompanyRecord']['freightact'] . ",
					'" . $_SESSION['Items']->DebtorNo . "',
					" . (-($_SESSION['Items']->FreightCost)/$_SESSION['CurrencyRate']) . "
				)";

			//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The freight GL posting could not be inserted because');
			//$DbgMsg = _('The following SQL to insert the GLTrans record was used');
			$Result = DB_query($SQL,$db);
		}
		foreach ( $TaxTotals as $TaxAuthID => $TaxAmount){
			if ($TaxAmount !=0 ){
				$SQL = "INSERT INTO gltrans (
						type,
						typeno,
						trandate,
						periodno,
						account,
						narrative,
						amount
						)
					VALUES (
						10,
						" . $InvoiceNo . ",
						'" . $DefaultDispatchDate . "',
						" . $PeriodNo . ",
						" . $TaxGLCodes[$TaxAuthID] . ",
						'" . $_SESSION['Items']->DebtorNo . "',
						" . (-$TaxAmount/$_SESSION['CurrencyRate']) . "
					)";

				//$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The tax GL posting could not be inserted because');
				//$DbgMsg = _('The following SQL to insert the GLTrans record was used');
				$Result = DB_query($SQL,$db);
			}
		}
	} /*end of if Sales and GL integrated */

	$SQL='COMMIT';
	$Result = DB_query($SQL,$db);

	if (DB_error_no($db) ==0) {
		return $InvoiceNo;
	} else {
		return false;
	}
}

/**
* Cancels an order.
*
* @param string $order_id The order id to be cancelled
*/
function cancel_order($order_id) {
	global $db;
	$_GET['InvoiceNumber'] = $order_id;

	unset($_SESSION['CreditItems']->LineItems);
	unset($_SESSION['CreditItems']);

	Session_register('CreditItems');
	Session_register('ProcessingCredit');
	Session_Register('CurrencyRate');
	Session_Register('Old_FreightCost');

	$_SESSION['ProcessingCredit'] = $_GET['InvoiceNumber'];
	$_SESSION['CreditItems'] = new cart;

	/*read in all the guff from the selected invoice into the Items cart	*/

	$InvoiceHeaderSQL = "SELECT DISTINCT
				debtortrans.id as transid,
				debtortrans.debtorno,
				debtorsmaster.name,
				debtortrans.branchcode,
				debtortrans.reference,
				debtortrans.invtext,
				debtortrans.order_,
				debtortrans.trandate,
				debtortrans.tpe,
				debtortrans.shipvia,
				debtortrans.ovfreight,
				debtortrans.rate AS currency_rate,
				debtorsmaster.currcode,
				custbranch.defaultlocation,
				custbranch.taxgroupid,
				stockmoves.loccode,
				locations.taxprovinceid
			FROM debtortrans INNER JOIN debtorsmaster ON
				debtortrans.debtorno = debtorsmaster.debtorno
				INNER JOIN custbranch ON
				debtortrans.branchcode = custbranch.branchcode
				AND debtortrans.debtorno = custbranch.debtorno
				INNER JOIN currencies ON
				debtorsmaster.currcode = currencies.currabrev
				INNER JOIN stockmoves ON
				stockmoves.transno=debtortrans.transno
				INNER JOIN locations ON
				stockmoves.loccode = locations.loccode
			WHERE debtortrans.transno = " . $_GET['InvoiceNumber'] . "
				AND debtortrans.type=10
				AND stockmoves.type=10";

	$ErrMsg = _('A credit cannot be produced for the selected invoice') . '. ' . _('The invoice details cannot be retrieved because');
	$DbgMsg = _('The SQL used to retrieve the invoice details was');
	$GetInvHdrResult = DB_query($InvoiceHeaderSQL,$db,$ErrMsg,$DbgMsg);

	if (DB_num_rows($GetInvHdrResult)==1) {

		$myrow = DB_fetch_array($GetInvHdrResult);

		/*CustomerID variable registered by header.inc */
		$_SESSION['CreditItems']->DebtorNo = $myrow['debtorno'];
		$_SESSION['CreditItems']->TransID = $myrow['transid'];
		$_SESSION['CreditItems']->Branch = $myrow['branchcode'];
		$_SESSION['CreditItems']->CustomerName = $myrow['name'];
		$_SESSION['CreditItems']->CustRef = $myrow['reference'];
		$_SESSION['CreditItems']->Comments = $myrow['invtext'];
		$_SESSION['CreditItems']->DefaultSalesType =$myrow['tpe'];
		$_SESSION['CreditItems']->DefaultCurrency = $myrow['currcode'];
		$_SESSION['CreditItems']->Location = $myrow['loccode'];
		$_SESSION['Old_FreightCost'] = $myrow['ovfreight'];
		$_SESSION['CurrencyRate'] = $myrow['currency_rate'];
		$_SESSION['CreditItems']->OrderNo = $myrow['order_'];
		$_SESSION['CreditItems']->ShipVia = $myrow['shipvia'];
		$_SESSION['CreditItems']->TaxGroup = $myrow['taxgroupid'];
		$_SESSION['CreditItems']->FreightCost = $myrow['ovfreight'];
		$_SESSION['CreditItems']->DispatchTaxProvince = $myrow['taxprovinceid'];
		$_SESSION['CreditItems']->GetFreightTaxes();

		DB_free_result($GetInvHdrResult);

		/*now populate the line items array with the stock movement records for the invoice*/


		$LineItemsSQL = "SELECT stockmoves.stkmoveno,
					stockmoves.stockid,
					stockmaster.description,
					stockmaster.volume,
					stockmaster.kgs,
					stockmaster.mbflag,
					stockmaster.controlled,
					stockmaster.serialised,
					stockmaster.decimalplaces,
					stockmaster.taxcatid,
					stockmaster.units,
					stockmaster.discountcategory,
					(stockmoves.price * " . $_SESSION['CurrencyRate'] . ") AS price, -
					stockmoves.qty as quantity,
					stockmoves.discountpercent,
					stockmoves.trandate,
					stockmaster.materialcost
						+ stockmaster.labourcost
						+ stockmaster.overheadcost AS standardcost,
					stockmoves.narrative
				FROM stockmoves, stockmaster
				WHERE stockmoves.stockid = stockmaster.stockid
				AND stockmoves.transno =" . $_GET['InvoiceNumber'] . "
				AND stockmoves.type=10
				AND stockmoves.show_on_inv_crds=1";

		$ErrMsg = _('This invoice can not be credited using this program') . '. ' . _('A manual credit note will need to be prepared') . '. ' . _('The line items of the order cannot be retrieved because');
		$Dbgmsg = _('The SQL used to get the transaction header was');

		$LineItemsResult = DB_query($LineItemsSQL,$db,$ErrMsg, $DbgMsg);

		if (db_num_rows($LineItemsResult)>0) {

			while ($myrow=db_fetch_array($LineItemsResult)) {

				$LineNumber = $_SESSION['CreditItems']->LineCounter;

				$_SESSION['CreditItems']->add_to_cart($myrow['stockid'],
				$myrow['quantity'],
				$myrow['description'],
				$myrow['price'],
				$myrow['discountpercent'],
				$myrow['units'],
				$myrow['volume'],
				$myrow['kgs'],
				0,
				$myrow['mbflag'],
				$myrow['trandate'],
				0,
				$myrow['discountcategory'],
				$myrow['controlled'],
				$myrow['serialised'],
				$myrow['decimalplaces'],
				$myrow['narrative'],
				'No',
				0,
				$myrow['taxcatid']
				);

				$_SESSION['CreditItems']->LineItems[$LineNumber]->StandardCost = $myrow['standardcost'];
				$_SESSION['CreditItems']->GetExistingTaxes($LineNumber, $myrow['stkmoveno']);

				if ($myrow['controlled']==1){/* Populate the SerialItems array too*/

					$SQL = "SELECT 	serialno,
							moveqty
						FROM stockserialmoves
						WHERE stockmoveno=" . $myrow['stkmoveno'] . "
						AND stockid = '" . $myrow['stockid'] . "'";

					$ErrMsg = _('This invoice can not be credited using this program') . '. ' . _('A manual credit note will need to be prepared') . '. ' . _('The line item') . ' ' . $myrow['stockid'] . ' ' . _('is controlled but the serial numbers or batch numbers could not be retrieved because');
					$DbgMsg = _('The SQL used to get the controlled item details was');
					$SerialItemsResult = DB_query($SQL,$db,$ErrMsg, $DbgMsg);

					while ($SerialItemsRow = DB_fetch_array($SerialItemsResult)){
						$_SESSION['CreditItems']->LineItems[$LineNumber]->SerialItems[$SerialItemsRow['serialno']] = new SerialItem($SerialItemsRow['serialno'], -$SerialItemsRow['moveqty']);
					}
				} /* end if the item is a controlled item */
			} /* loop thro line items from stock movement records */

		} else { /* there are no stock movement records created for that invoice */
			return false;
			//echo "<CENTER><A HREF='$rootpath/index.php?" . SID . "'>" . _('Back to the menu') . '</A></CENTER>';
			//prnMsg( _('There are no line items that were retrieved for this invoice') . '. ' . _('The automatic credit program can not create a credit note from this invoice'),'warn');
			//include("includes/footer.inc");
			//exit;
		} //end of checks on returned data set
		DB_free_result($LineItemsResult);
	} else {
		return false;
		//prnMsg( _('This invoice can not be credited using the automatic facility') . '<BR>' . _('CRITICAL ERROR') . ': ' . _('Please report that a duplicate DebtorTrans header record was found for invoice') . ' ' . $SESSION['ProcessingCredit'],'warn');
		//include("includes/footer.inc");
		//exit;
	} //valid invoice record returned from the entered invoice number

	$_SESSION['CreditItems']->total = 0;
	$_SESSION['CreditItems']->totalVolume = 0;
	$_SESSION['CreditItems']->totalWeight = 0;

	$TaxTotals = array();
	$TaxGLCodes = array();
	$TaxTotal =0;

	foreach ($_SESSION['CreditItems']->LineItems as $LnItm) {
		$LineTotal =($LnItm->QtyDispatched * $LnItm->Price * (1 - $LnItm->DiscountPercent));

		$_SESSION['CreditItems']->total = $_SESSION['CreditItems']->total + $LineTotal;
		$_SESSION['CreditItems']->totalVolume = $_SESSION['CreditItems']->totalVolume + $LnItm->QtyDispatched * $LnItm->Volume;
		$_SESSION['CreditItems']->totalWeight = $_SESSION['CreditItems']->totalWeight + $LnItm->QtyDispatched * $LnItm->Weight;
		$DisplayLineTotal = number_format($LineTotal,2);
		$TaxLineTotal =0; //initialise tax total for the line

		foreach ($LnItm->Taxes AS $Tax) {
			if ($Tax->TaxOnTax ==1){
				$TaxTotals[$Tax->TaxAuthID] += ($Tax->TaxRate * ($LineTotal + $TaxLineTotal));
				$TaxLineTotal += ($Tax->TaxRate * ($LineTotal + $TaxLineTotal));
			} else {
				$TaxTotals[$Tax->TaxAuthID] += ($Tax->TaxRate * $LineTotal);
				$TaxLineTotal += ($Tax->TaxRate * $LineTotal);
			}

			$TaxGLCodes[$Tax->TaxAuthID] = $Tax->TaxGLCode;
		}

		$TaxTotal += $TaxLineTotal;

		$DisplayTaxAmount = number_format($TaxLineTotal ,2);
		$DisplayGrossLineTotal = number_format($LineTotal+ $TaxLineTotal,2);
	}

	$FreightTaxTotal =0; //initialise tax total

	foreach ($_SESSION['CreditItems']->FreightTaxes as $FreightTaxLine) {
		if ($FreightTaxLine->TaxOnTax ==1){
			$TaxTotals[$FreightTaxLine->TaxAuthID] += ($FreightTaxLine->TaxRate * ($_SESSION['CreditItems']->FreightCost + $FreightTaxTotal));
			$FreightTaxTotal += ($FreightTaxLine->TaxRate * ($_SESSION['CreditItems']->FreightCost + $FreightTaxTotal));
		} else {
			$TaxTotals[$FreightTaxLine->TaxAuthID] += ($FreightTaxLine->TaxRate * $_SESSION['CreditItems']->FreightCost);
			$FreightTaxTotal += ($FreightTaxLine->TaxRate * $_SESSION['CreditItems']->FreightCost);
		}

		$TaxGLCodes[$FreightTaxLine->TaxAuthID] = $FreightTaxLine->TaxGLCode;
	}

	$TaxTotal += $FreightTaxTotal;
	$DisplayTotal = number_format($_SESSION['CreditItems']->total + $_SESSION['CreditItems']->FreightCost,2);

	$DefaultDispatchDate = Date($_SESSION['DefaultDateFormat']);

	/* SQL to process the postings for sales credit notes... First Get the area where the credit note is to from the branches table */

	$SQL = "SELECT area
		FROM custbranch
		WHERE custbranch.debtorno ='". $_SESSION['CreditItems']->DebtorNo . "'
		AND custbranch.branchcode = '" . $_SESSION['CreditItems']->Branch . "'";

	$Result = DB_query($SQL,$db);
	$myrow = DB_fetch_row($Result);
	$Area = $myrow[0];
	DB_free_result($Result);

	/*company record is read in on login and has information on GL Links and debtors GL account*/

	if ($_SESSION['CompanyRecord']==0){
		return false;
		/*The company data and preferences could not be retrieved for some reason */
		//prnMsg(_('The company information and preferences could not be retrieved') . ' - ' . _('see your system administrator'),'error');
		//include('includes/footer.inc');
		//exit;
	}


	/*Now Get the next credit note number - function in SQL_CommonFunctions*/

	$CreditNo = GetNextTransNo(11, $db);
	//$DefaultDispatchDate = Date("Y-m-d H:i");
	$PeriodNo = GetPeriod($DefaultDispatchDate, $db);

	/*Start an SQL transaction */

	$SQL = "BEGIN";
	$Result = DB_query($SQL,$db);

	$DefaultDispatchDate= FormatDateForSQL($DefaultDispatchDate);


	/*Calculate the allocation and see if it is possible to allocate to the invoice being credited */

	$SQL = "SELECT (ovamount+ovgst+ovfreight-ovdiscount-alloc) as baltoallocate
		FROM debtortrans
		WHERE transno=" . $_SESSION['ProcessingCredit'] . " AND type=10";
	$Result = DB_query($SQL,$db);
	$myrow = DB_fetch_row($Result);


	/*Do some rounding */

	$_SESSION['CreditItems']->total = round($_SESSION['CreditItems']->total,2);
	$TaxTotal = round($TaxTotal,2);

	$Allocate_amount=0;
	$Settled =0;
	$SettledInvoice=0;
	if ($myrow[0]>0){ /*the invoice is not already fully allocated */

		if ($myrow[0] > ($_SESSION['CreditItems']->total + $_SESSION['CreditItems']->FreightCost + $TaxTotal)){

			$Allocate_amount = $_SESSION['CreditItems']->total + $_SESSION['CreditItems']->FreightCost + $TaxTotal;
			$Settled = 1;
		} else { /*the balance left to allocate is less than the credit note value */
			$Allocate_amount = $myrow[0];
			$SettledInvoice = 1;
			$Settled =0;
		}

		/*Now need to update the invoice DebtorTrans record for the amount to be allocated and if the invoice is now settled*/

		$SQL = "UPDATE debtortrans
			SET alloc = alloc + " . $Allocate_amount . ",
			settled=" . $SettledInvoice . "
			WHERE transno = " . $_SESSION['ProcessingCredit'] . "
			AND type=10";

		$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The alteration to the invoice record to reflect the allocation of the credit note to the invoice could not be done because');
		$DbgMsg = _('The following SQL to update the invoice allocation was used');
		$Result = DB_query($SQL,$db,$ErrMsg,DbgMsg,true);
	}

	/*Now insert the Credit Note into the DebtorTrans table with the allocations as calculated above*/

	$SQL = "INSERT INTO debtortrans (transno,
					type,
					debtorno,
					branchcode,
					trandate,
					prd,
					reference,
					tpe,
					order_,
					ovamount,
					ovgst,
					ovfreight,
					rate,
					invtext,
					alloc,
					settled)
		VALUES (". $CreditNo . ",
			11,
			'" . $_SESSION['CreditItems']->DebtorNo . "',
			'" . $_SESSION['CreditItems']->Branch . "',
			'" . $DefaultDispatchDate . "',
			" . $PeriodNo . ", 'Inv-" . $_SESSION['ProcessingCredit'] . "',
			'" . $_SESSION['CreditItems']->DefaultSalesType . "',
			" . $_SESSION['CreditItems']->OrderNo . ",
			" . -($_SESSION['CreditItems']->total) . ",
			" . -$TaxTotal . ", " . -$_SESSION['CreditItems']->FreightCost . ",
			" . $_SESSION['CurrencyRate'] . ",
			'" . $_POST['CreditText'] . "',
			" . -$Allocate_amount . ",
			" . $Settled . ")";

	$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The customer credit note transaction could not be added to the database because');
	$DbgMsg = _('The following SQL to insert the customer credit note was used');
	$Result = DB_query($SQL,$db,$ErrMsg, $DbgMsg, true);

	$CreditTransID = DB_Last_Insert_ID($db,'debtortrans','id');

	/* Insert the tax totals for each tax authority where tax was charged on the invoice */
	foreach ($TaxTotals AS $TaxAuthID => $TaxAmount) {

		$SQL = 'INSERT INTO debtortranstaxes (debtortransid,
							taxauthid,
							taxamount)
				VALUES (' . $CreditTransID . ',
					' . $TaxAuthID . ',
					' . -($TaxAmount/$_SESSION['CurrencyRate']) . ')';

		$ErrMsg =_('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The debtor transaction taxes records could not be inserted because');
		$DbgMsg = _('The following SQL to insert the debtor transaction taxes record was used');
		$Result = DB_query($SQL,$db,$ErrMsg,$DbgMsg,true);
	}

	/*Now insert the allocation record if > 0 */
	if ($Allocate_amount!=0){
		$SQL = "INSERT INTO custallocns (amt,
						transid_allocfrom,
						transid_allocto,
						datealloc)
			VALUES (" . $Allocate_amount . ",
				" . $CreditTransID . ",
				" . $_SESSION['CreditItems']->TransID . ",
				'" . Date('Y-m-d') . "')";

		$ErrMsg =  _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The allocation record for the credit note could not be added to the database because');
		$DbgMsg = _('The following SQL to insert the allocation record for the credit note was used');
		$Result = DB_query($SQL,$db,$ErrMsg,$DbgMsg,true);

	}

	/* Update sales order details quantity invoiced less this credit quantity. */

	foreach ($_SESSION['CreditItems']->LineItems as $OrderLine) {

		if ($OrderLine->QtyDispatched >0){
			$LocalCurrencyPrice= round(($OrderLine->Price / $_SESSION['CurrencyRate']),2);

			/*Determine the type of stock item being credited */
			$SQL = "SELECT mbflag FROM stockmaster WHERE stockid = '" . $OrderLine->StockID . "'";
			$Result = DB_query($SQL,
			$db,
			_('Could not determine if the item') . ' ' . $OrderLine->StockID . ' ' . _('is purchased or manufactured'),
			_('The SQL used that failed was'),true);
			$MBFlagRow = DB_fetch_row($Result);
			$MBFlag = $MBFlagRow[0];
			if ($MBFlag=='M' OR $MBFlag=='B'){
				/*Need to get the current location quantity will need it later for the stock movements */
				$SQL="SELECT locstock.quantity
					FROM locstock
					WHERE locstock.stockid='" . $OrderLine->StockID . "'
					AND loccode= '" . $_SESSION['CreditItems']->Location . "'";
				$Result = DB_query($SQL, $db);
				if (DB_num_rows($Result)==1){
					$LocQtyRow = DB_fetch_row($Result);
					$QtyOnHandPrior = $LocQtyRow[0];
				} else {
					/*There must actually be some error this should never happen */
					$QtyOnHandPrior = 0;
				}
			} else {
				$QtyOnHandPrior =0; //because its a dummy/assembly/kitset part
			}

			if ($_POST['CreditType']=='Return'){

				$SQL = "UPDATE salesorderdetails
					SET qtyinvoiced = qtyinvoiced - " . $OrderLine->QtyDispatched . ",
					completed=0
					WHERE orderno = " . $_SESSION['ProcessingCredit'] . "
					AND stkcode = '" . $OrderLine->StockID . "'";

				$ErrMsg =  _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The sales order detail record could not be updated for the reduced quantity invoiced because');
				$DbgMsg = _('The following SQL to update the sales order detail record was used');
				$Result = DB_query($SQL,$db,$ErrMsg,$DbgMsg,true);
				/* Update location stock records if not a dummy stock item */

				if ($MBFlag=="B" OR $MBFlag=="M") {

					$SQL = "UPDATE
						locstock
						SET locstock.quantity = locstock.quantity + " . $OrderLine->QtyDispatched . "
						WHERE locstock.stockid = '" . $OrderLine->StockID . "'
						AND loccode = '" . $_SESSION['CreditItems']->Location . "'";

					$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Location stock record could not be updated because');
					$DbgMsg = _('The following SQL to update the location stock record was used');
					$Result = DB_query($SQL, $db, $ErrMsg,$DbgMsg,true);

				} else if ($MBFlag=='A'){ /* its an assembly */
					/*Need to get the BOM for this part and make stock moves for the components
					and of course update the Location stock balances */

					$StandardCost =0; /*To start with - accumulate the cost of the comoponents for use in journals later on */
					$sql = "SELECT
				    	bom.component,
				    	bom.quantity,
					stockmaster.materialcost
						+ stockmaster.labourcost
						+ stockmaster.overheadcost AS standard
					FROM bom,
						stockmaster
					WHERE bom.component=stockmaster.stockid
					AND bom.parent='" . $OrderLine->StockID . "'
					AND bom.effectiveto > '" . Date('Y-m-d') . "'
					AND bom.effectiveafter < '" . Date('Y-m-d') . "'";

					$ErrMsg = _('Could not retrieve assembly components from the database for') . ' ' . $OrderLine->StockID . ' ' . _('because');
					$DbgMsg = _('The SQL that failed was');
					$AssResult = DB_query($sql,$db, $ErrMsg, $DbgMsg, true);

					while ($AssParts = DB_fetch_array($AssResult,$db)){

						$StandardCost += $AssParts['standard'];
						/*Determine the type of stock item being credited */
						$SQL = "SELECT mbflag
							FROM
							stockmaster
							WHERE stockid = '" . $AssParts['component'] . "'";
						$Result = DB_query($SQL,$db);
						$MBFlagRow = DB_fetch_row($Result);
						$Component_MBFlag = $MBFlagRow[0];

						/* Insert stock movements for the stock coming back in - with unit cost */
						if ($Component_MBFlag=='M' OR $Component_MBFlag=='B'){
							/*Need to get the current location quantity will need it later for the stock movement */
							$SQL="SELECT locstock.quantity
							FROM locstock
							WHERE locstock.stockid='" . $AssParts['component'] . "'
							AND loccode= '" . $_SESSION['CreditItems']->Location . "'";
							$Result = DB_query($SQL, $db, _('Couldnt get the current location stock of the assembly component') . ' ' . $AssParts['component'], _('The SQL that failed was'), true);
							if (DB_num_rows($Result)==1){
								$LocQtyRow = DB_fetch_row($Result);
								$QtyOnHandPrior = $LocQtyRow[0];
							} else {
								/*There must actually be some error this should never happen */
								$QtyOnHandPrior = 0;
							}
						} else {
							$QtyOnHandPrior =0; //because its a dummy/assembly/kitset part
						}

						if ($Component_MBFlag=="M" OR $Component_MBFlag=="B"){

							$SQL = "INSERT INTO stockmoves (
									stockid,
									type,
									transno,
									loccode,
									trandate,
									debtorno,
									branchcode,
									prd,
									reference,
									qty,
									standardcost,
									show_on_inv_crds,
									newqoh )
							VALUES ('" . $AssParts['component'] . "',
								11,
								" . $CreditNo . ",
								'" . $_SESSION['CreditItems']->Location . "',
								'" . $DefaultDispatchDate . "',
								'" . $_SESSION['CreditItems']->DebtorNo . "',
								'" . $_SESSION['CreditItems']->Branch . "',
								" . $PeriodNo . ",
								'" . _('Ex Inv') . ': ' .  $_SESSION['ProcessingCredit'] . ' ' . _('Assembly') . ': ' . $OrderLine->StockID . "',
								" . $AssParts['quantity'] * $OrderLine->QtyDispatched . ",
								" . $AssParts['standard'] . ",
								0,
								" . ($QtyOnHandPrior + ($AssParts['quantity'] * $OrderLine->QtyDispatched)) . "
								)";
						} else {

							$SQL = "INSERT INTO stockmoves (
								stockid,
								type,
								transno,
								loccode,
								trandate,
								debtorno,
								branchcode,
								prd,
								reference,
								qty,
								standardcost,
								show_on_inv_crds)
							VALUES ('" . $AssParts['component'] . "',
							11,
							" . $CreditNo . ",
							'" . $_SESSION['CreditItems']->Location . "',
							'" . $DefaultDispatchDate . "',
							'" . $_SESSION['CreditItems']->DebtorNo . "',
							'" . $_SESSION['CreditItems']->Branch . "',
							" . $PeriodNo . ",
							'" . _('Ex Inv') . ': ' . $_SESSION['ProcessingCredit'] . ' ' . _('Assembly') . ': ' . $OrderLine->StockID . "',
							" . $AssParts['quantity'] * $OrderLine->QtyDispatched . ",
							" . $AssParts['standard'] . ",
							0)";
						}

						$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Stock movement records for the assembly components of') .' ' . $OrderLine->StockID . ' ' . _('could not be inserted because');
						$DbgMsg = _('The following SQL to insert the assembly components stock movement records was used');
						$Result = DB_query($SQL, $db, $ErrMsg, $DbgMsg, true);


						if ($Component_MBFlag=="M" OR $Component_MBFlag=="B"){
							$SQL = "UPDATE locstock
							SET locstock.quantity = locstock.quantity + " . $AssParts['quantity'] * $OrderLine->QtyDispatched . "
							WHERE locstock.stockid = '" . $AssParts['component'] . "'
							AND loccode = '" . $_SESSION['CreditItems']->Location . "'";

							$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Location stock record could not be updated for an assembly component because');
							$DbgMsg = _('The following SQL to update the components location stock record was used');
							$Result = DB_query($SQL,
							$db,
							$ErrMsg,
							$DbgMsg,
							true);
						}
					} /* end of assembly explosion and updates */
					/*Update the cart with the recalculated standard cost from the explosion of the assembly's components*/
					$_SESSION['CreditItems']->LineItems[$OrderLine->LineNumber]->StandardCost = $StandardCost;
					$OrderLine->StandardCost = $StandardCost;
				}

				/* Insert stock movements for the stock coming back in - with unit cost */

				if ($MBFlag=="M" OR $MBFlag=="B"){
					$SQL = "INSERT INTO stockmoves (
								stockid,
								type,
								transno,
								loccode,
								trandate,
								debtorno,
								branchcode,
								price,
								prd,
								reference,
								qty,
								discountpercent,
								standardcost,
								newqoh,
								narrative)
						VALUES ('" . $OrderLine->StockID . "',
							11,
							" . $CreditNo . ",
							'" . $_SESSION['CreditItems']->Location . "',
							'" . $DefaultDispatchDate . "',
							'" . $_SESSION['CreditItems']->DebtorNo . "',
							'" . $_SESSION['CreditItems']->Branch . "',
							" . $LocalCurrencyPrice . ",
							" . $PeriodNo . ",
							'Ex Inv - " . $_SESSION['ProcessingCredit'] . "',
							" . $OrderLine->QtyDispatched . ",
							" . $OrderLine->DiscountPercent . ",
							" . $OrderLine->StandardCost . ",
							" .  ($QtyOnHandPrior + $OrderLine->QtyDispatched) . ",
							'" . DB_escape_string($OrderLine->Narrative) . "')";
				} else {

					$SQL = "INSERT INTO stockmoves (
							stockid,
							type,
							transno,
							loccode,
							trandate,
							debtorno,
							branchcode,
							price,
							prd,
							reference,
							qty,
							discountpercent,
							standardcost,
							narrative)
						VALUES ('" . $OrderLine->StockID . "',
							11,
							" . $CreditNo . ",
							'" . $_SESSION['CreditItems']->Location . "',
							'" . $DefaultDispatchDate . "',
							'" . $_SESSION['CreditItems']->DebtorNo . "',
							'" . $_SESSION['CreditItems']->Branch . "',
							" . $LocalCurrencyPrice . ",
							" . $PeriodNo . ",
							'Ex Inv - " . $_SESSION['ProcessingCredit'] . "',
							" . $OrderLine->QtyDispatched . ",
							" . $OrderLine->DiscountPercent . ",
							" . $OrderLine->StandardCost . ",
							'" . DB_escape_string($OrderLine->Narrative) . "'
						)";
				}

				$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Stock movement records could not be inserted because');
				$DbgMsg = _('The following SQL to insert the stock movement records was used');
				$Result = DB_query($SQL, $db,$ErrMsg,$DbgMsg,true);


			}  elseif ($_POST['CreditType']=='WriteOff') {
				/*Insert a stock movement coming back in to show the credit note and
				a reversing stock movement to show the write off
				no mods to location stock records*/

				$SQL = "INSERT INTO stockmoves (
							stockid,
							type,
							transno,
							loccode,
							trandate,
							debtorno,
							branchcode,
							price,
							prd,
							reference,
							qty,
							discountpercent,
							standardcost,
							newqoh,
							narrative,
							taxrate)
					VALUES ('" . $OrderLine->StockID . "',
						11,
						" . $CreditNo . ",
						'" . $_SESSION['CreditItems']->Location . "',
						'" . $DefaultDispatchDate . "',
						'" . $_SESSION['CreditItems']->DebtorNo . "',
						'" . $_SESSION['CreditItems']->Branch . "',
						" . $LocalCurrencyPrice . ",
						" . $PeriodNo . ",
						'Ex Inv - " . $_SESSION['ProcessingCredit'] . "',
						" . $OrderLine->QtyDispatched . ",
						" . $OrderLine->DiscountPercent . ",
						" . $OrderLine->StandardCost . ",
						" . ($QtyOnHandPrior +$OrderLine->QtyDispatched)  . ",
						'" . DB_escape_string($OrderLine->Narrative) . "')";

				$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Stock movement records could not be inserted because');
				$DbgMsg = _('The following SQL to insert the stock movement records was used');
				$Result = DB_query($SQL, $db,$ErrMsg, $DbgMsg, true);

				$SQL = "INSERT INTO stockmoves (
							stockid,
							type,
							transno,
							loccode,
							trandate,
							debtorno,
							branchcode,
							price,
							prd,
							reference,
							qty,
							discountpercent,
							standardcost,
							show_on_inv_crds,
							newqoh,
							narrative
							)
					VALUES ('" . $OrderLine->StockID . "',
						11,
						" . $CreditNo . ",
						'" . $_SESSION['CreditItems']->Location . "',
						'" . $DefaultDispatchDate . "',
						'" . $_SESSION['CreditItems']->DebtorNo . "',
						'" . $_SESSION['CreditItems']->Branch . "',
						" . $LocalCurrencyPrice . ",
						" . $PeriodNo . ",
						'" . _('Written off ex Inv') . ' - ' . $_SESSION['ProcessingCredit'] . "',
						" . -$OrderLine->QtyDispatched . ",
						" . $OrderLine->DiscountPercent . ",
						" . $OrderLine->StandardCost . ",
						0,
						" . $QtyOnHandPrior . ",
						'" . DB_escape_string($OrderLine->Narrative) . "')";


				$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Stock movement records could not be inserted because');
				$DbgMsg = _('The following SQL to insert the stock movement records was used');
				$Result = DB_query($SQL, $db, $ErrMsg, $DbgMsg, true);

			} elseif ($_POST['CreditType']=='ReverseOverCharge') {
				/*Insert a stock movement coming back in to show the credit note  - flag the stockmovement not to show on stock movement enquiries - its is not a real stock movement only for invoice line - also no mods to location stock records*/
				$SQL = "INSERT INTO stockmoves (
							stockid,
							type,
							transno,
							loccode,
							trandate,
							debtorno,
							branchcode,
							price,
							prd,
							reference,
							qty,
							discountpercent,
							standardcost,
							newqoh,
							hidemovt,
							narrative)
					VALUES ('" . $OrderLine->StockID . "',
						11,
						" . $CreditNo . ",
						'" . $_SESSION['CreditItems']->Location . "',
						'" . $DefaultDispatchDate . "',
						'" . $_SESSION['CreditItems']->DebtorNo . "',
						'" . $_SESSION['CreditItems']->Branch . "',
						" . $LocalCurrencyPrice . ",
						" . $PeriodNo . ",
						" . _('Ex Inv') .' - ' . $_SESSION['ProcessingCredit'] . "',
						" . $OrderLine->QtyDispatched . ",
						" . $OrderLine->DiscountPercent . ",
						" . $OrderLine->StandardCost . ",
						" . $QtyOnHandPrior  . ",
						1,
						'" . DB_escape_string($OrderLine->Narrative) . "')";


				$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Stock movement records could not be inserted because');
				$DbgMsg = _('The following SQL to insert the stock movement records for the purpose of display on the credit note was used');

				$Result = DB_query($SQL, $db,$ErrMsg, $DbgMsg, true);
			}

			/*Get the ID of the StockMove... */
			$StkMoveNo = DB_Last_Insert_ID($db,'stockmoves','stkmoveno');

			/*Insert the taxes that applied to this line */
			foreach ($OrderLine->Taxes as $Tax) {

				$SQL = 'INSERT INTO stockmovestaxes (stkmoveno,
									taxauthid,
									taxrate,
									taxcalculationorder,
									taxontax)
						VALUES (' . $StkMoveNo . ',
							' . $Tax->TaxAuthID . ',
							' . $Tax->TaxRate . ',
							' . $Tax->TaxCalculationOrder . ',
							' . $Tax->TaxOnTax . ')';

				$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('Taxes and rates applicable to this credit note line item could not be inserted because');
				$DbgMsg = _('The following SQL to insert the stock movement tax detail records was used');
				$Result = DB_query($SQL,$db,$ErrMsg,$DbgMsg,true);
			}

			/*Insert Sales Analysis records */

			$SQL="SELECT COUNT(*),
				stkcategory,
				salesanalysis.area,
				salesperson
			FROM salesanalysis,
				custbranch,
				stockmaster
			WHERE salesanalysis.stkcategory=stockmaster.categoryid
			AND salesanalysis.stockid=stockmaster.stockid
			AND salesanalysis.cust=custbranch.debtorno
			AND salesanalysis.custbranch=custbranch.branchcode
			AND salesanalysis.area=custbranch.area
			AND salesanalysis.salesperson=custbranch.salesman
			AND typeabbrev ='" . $_SESSION['CreditItems']->DefaultSalesType . "'
			AND periodno=" . $PeriodNo . "
			AND cust = '" . $_SESSION['CreditItems']->DebtorNo . "'
			AND custbranch = '" . $_SESSION['CreditItems']->Branch . "'
			AND salesanalysis.stockid = '" . $OrderLine->StockID . "'
			AND budgetoractual=1
			GROUP BY stkcategory, salesanalysis.area, salesperson";

			$ErrMsg = _('The count to check for existing Sales analysis records could not run because');
			$DbgMsg = _('SQL to count the no of sales analysis records');

			$Result = DB_query($SQL,$db,$ErrMsg, $DbgMsg, true);

			$myrow = DB_fetch_row($Result);

			if ($myrow[0]>0){  /*Update the existing record that already exists */

				if ($_POST['CreditType']=='ReverseOverCharge'){

					$SQL = "UPDATE salesanalysis
						SET amt=amt-" . ($OrderLine->Price * $OrderLine->QtyDispatched / $_SESSION['CurrencyRate']) . ",
							disc=disc-" . ($OrderLine->DiscountPercent * $OrderLine->Price * $OrderLine->QtyDispatched / $_SESSION['CurrencyRate']) . "
						WHERE salesanalysis.area='" . $myrow[2] . "'
						AND salesanalysis.salesperson='" . $myrow[3] . "'
						AND typeabbrev ='" . $_SESSION['CreditItems']->DefaultSalesType . "'
						AND periodno = " . $PeriodNo . "
						AND cust = '" . $_SESSION['CreditItems']->DebtorNo . "'
						AND custbranch = '" . $_SESSION['CreditItems']->Branch . "'
						AND stockid = '" . $OrderLine->StockID . "'
						AND salesanalysis.stkcategory ='" . $myrow[1] . "'
						AND budgetoractual=1";

				} else {

					$SQL = "UPDATE salesanalysis
							SET amt=amt-" . ($OrderLine->Price * $OrderLine->QtyDispatched / $_SESSION['CurrencyRate']) . ",
							cost=cost-" . ($OrderLine->StandardCost * $OrderLine->QtyDispatched) . ",
							qty=qty-" . $OrderLine->QtyDispatched . ",
							disc=disc-" . ($OrderLine->DiscountPercent * $OrderLine->Price * $OrderLine->QtyDispatched / $_SESSION['CurrencyRate']) . "
						WHERE salesanalysis.area='" . $myrow[2] . "'
						AND salesanalysis.salesperson='" . $myrow[3] . "'
						AND typeabbrev ='" . $_SESSION['CreditItems']->DefaultSalesType . "'
						AND periodno = " . $PeriodNo . "
						AND cust = '" . $_SESSION['CreditItems']->DebtorNo . "'
						AND custbranch = '" . $_SESSION['CreditItems']->Branch . "'
						AND stockid = '" . $OrderLine->StockID . "'
						AND salesanalysis.stkcategory ='" . $myrow[1] . "'
						AND budgetoractual=1";
				}

			} else { /* insert a new sales analysis record */

				if ($_POST['CreditType']=='ReverseOverCharge'){

					$SQL = "INSERT salesanalysis (typeabbrev,
									periodno,
									amt,
									cust,
									custbranch,
									qty,
									disc,
									stockid,
									area,
									budgetoractual,
									salesperson,
									stkcategory)
						SELECT '" . $_SESSION['CreditItems']->DefaultSalesType . "',
							" . $PeriodNo . ",
							" . -($OrderLine->Price * $OrderLine->QtyDispatched / $_SESSION['CurrencyRate']) . ",
							'" . $_SESSION['CreditItems']->DebtorNo . "',
							'" . $_SESSION['CreditItems']->Branch . "',
							0,
							" . -($OrderLine->DiscountPercent * $OrderLine->Price * $OrderLine->QtyDispatched / $_SESSION['CurrencyRate']) . ",
							'" . $OrderLine->StockID . "',
							custbranch.area,
							1,
							custbranch.salesman,
							stockmaster.categoryid
						FROM stockmaster,
							custbranch
						WHERE stockmaster.stockid = '" . $OrderLine->StockID . "'
						AND custbranch.debtorno = '" . $_SESSION['CreditItems']->DebtorNo . "'
						AND custbranch.branchcode='" . $_SESSION['CreditItems']->Branch . "'";

				} else {

					$SQL = "INSERT salesanalysis (typeabbrev,
									periodno,
									amt,
									cost,
									cust,
									custbranch,
									qty,
									disc,
									stockid,
									area,
									budgetoractual,
									salesperson,
									stkcategory)
						SELECT '" . $_SESSION['CreditItems']->DefaultSalesType . "',
							" . $PeriodNo . ", " . -($OrderLine->Price * $OrderLine->QtyDispatched / $_SESSION['CurrencyRate']) . ",
							" . -($OrderLine->StandardCost * $OrderLine->QtyDispatched) . ",
							'" . $_SESSION['CreditItems']->DebtorNo . "',
							'" . $_SESSION['CreditItems']->Branch . "',
							" . -$OrderLine->QtyDispatched . ",
							" . -($OrderLine->DiscountPercent * $OrderLine->Price * $OrderLine->QtyDispatched / $_SESSION['CurrencyRate']) . ",
							'" . $OrderLine->StockID . "',
							custbranch.area,
							1,
							custbranch.salesman,
							stockmaster.categoryid
						FROM stockmaster,
							custbranch
						WHERE stockmaster.stockid = '" . $OrderLine->StockID . "'
						AND custbranch.debtorno = '" . $_SESSION['CreditItems']->DebtorNo . "'
						AND custbranch.branchcode='" . $_SESSION['CreditItems']->Branch . "'";

				}
			}

			$ErrMsg = _('The sales analysis record for this credit note could not be added because');
			$DbgMsg = _('The following SQL to insert the sales analysis record was used');
			$Result = DB_query($SQL,$db,$ErrMsg, $DbgMsg, true);


			/* If GLLink_Stock then insert GLTrans to credit stock and debit cost of sales at standard cost*/

			if ($_SESSION['CompanyRecord']['gllink_stock']==1
			AND ($OrderLine->StandardCost !=0  OR $StandardCost !=0)
			AND $_POST['CreditType']!='ReverseOverCharge'){

				/*first the cost of sales entry*/

				$COGSAccount = GetCOGSGLAccount($Area, $OrderLine->StockID, $_SESSION['CreditItems']->DefaultSalesType, $db);

				$SQL = "INSERT INTO gltrans (type,
								typeno,
								trandate,
								periodno,
								account,
								narrative,
								amount)
						VALUES (11,
							" . $CreditNo . ",
							'" . $DefaultDispatchDate . "',
							" . $PeriodNo . ",
							" . $COGSAccount . ",
							'" . $_SESSION['CreditItems']->DebtorNo . " - " . $OrderLine->StockID . " x " . $OrderLine->QtyDispatched . " @ " . $OrderLine->StandardCost . "',
							" . -round($OrderLine->StandardCost * $OrderLine->QtyDispatched,2) . "
							)";

				$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The cost of sales GL posting could not be inserted because');
				$DbgMsg = _('The following SQL to insert the GLTrans record was used');
				$Result = DB_query($SQL,$db,$ErrMsg, $DbgMsg, true);

				/*now the stock entry*/


				if ($_POST['CreditType']=='WriteOff'){
					$SQL = "INSERT INTO gltrans (type,
									typeno,
									trandate,
									periodno,
									account,
									narrative,
									amount)
							VALUES (11,
								" . $CreditNo . ",
								'" . $DefaultDispatchDate . "',
								" . $PeriodNo . ",
								" . $_POST['WriteOffGLCode'] . ",
								'" . $_SESSION['CreditItems']->DebtorNo . " - " . $OrderLine->StockID . " x " . $OrderLine->QtyDispatched . " @ " . $OrderLine->StandardCost . "',
								" . round($OrderLine->StandardCost * $OrderLine->QtyDispatched,2) . ")";
				} else {
					$StockGLCode = GetStockGLCode($OrderLine->StockID, $db);
					$SQL = "INSERT INTO gltrans (type,
									typeno,
									trandate,
									periodno,
									account,
									narrative,
									amount)
							VALUES (11,
								" . $CreditNo . ",
								'" . $DefaultDispatchDate . "',
								" . $PeriodNo . ",
								" . $StockGLCode['stockact'] . ",
								'" . $_SESSION['CreditItems']->DebtorNo . " - " . $OrderLine->StockID . " x " . $OrderLine->QtyDispatched . " @ " . $OrderLine->StandardCost . "',
								" . round($OrderLine->StandardCost * $OrderLine->QtyDispatched,2) . ")";
				}

				$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The stock side or write off of the cost of sales GL posting could not be inserted because');
				$DbgMsg = _('The following SQL to insert the GLTrans record was used');
				$Result = DB_query($SQL,$db,$ErrMsg, $DbgMsg, true);


			} /* end of if GL and stock integrated and standard cost !=0 */

			if ($_SESSION['CompanyRecord']['gllink_debtors']==1 AND $OrderLine->Price !=0){

				//Post sales transaction to GL credit sales
				$SalesGLAccounts = GetSalesGLAccount($Area, $OrderLine->StockID, $_SESSION['CreditItems']->DefaultSalesType, $db);

				$SQL = "INSERT INTO gltrans (type,
								typeno,
								trandate,
								periodno,
								account,
								narrative,
								amount)
						VALUES (11,
							" . $CreditNo . ",
							'" . $DefaultDispatchDate . "',
							" . $PeriodNo . ",
							" . $SalesGLAccounts['salesglcode'] . ",
							'" . $_SESSION['CreditItems']->DebtorNo . " - " . $OrderLine->StockID . " x " . $OrderLine->QtyDispatched . " @ " . $OrderLine->Price . "',
							" . round(($OrderLine->Price * $OrderLine->QtyDispatched)/$_SESSION['CurrencyRate'],2) . "
							)";

				$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The credit note GL posting could not be inserted because');
				$DbgMsg = _('The following SQL to insert the GLTrans record was used');
				$Result = DB_query($SQL,$db,$ErrMsg, $DbgMsg, true);

				if ($OrderLine->DiscountPercent !=0){

					$SQL = "INSERT INTO gltrans (type,
									typeno,
									trandate,
									periodno,
									account,
									narrative,
									amount)
							VALUES (11,
								" . $CreditNo . ",
								'" . $DefaultDispatchDate . "',
								" . $PeriodNo . ",
								" . $SalesGLAccounts['discountglcode'] . ",
								'" . $_SESSION['CreditItems']->DebtorNo . " - " . $OrderLine->StockID . " @ " . ($OrderLine->DiscountPercent * 100) . "%',
								" . -round(($OrderLine->Price * $OrderLine->QtyDispatched * $OrderLine->DiscountPercent)/$_SESSION['CurrencyRate'],2) . "
								)";
					$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The credit note discount GL posting could not be inserted because');
					$DbgMsg = _('The following SQL to insert the GLTrans record was used');
					$Result = DB_query($SQL,$db,$ErrMsg, $DbgMsg, true);
				} /*end of if discount !=0 */
			} /*end of if sales integrated with debtors */
		} /*Quantity dispatched is more than 0 */
	} /*end of OrderLine loop */


	if ($_SESSION['CompanyRecord']['gllink_debtors']==1){

		/*Post credit note transaction to GL credit debtors, debit freight re-charged and debit sales */
		if (($_SESSION['CreditItems']->total + $_SESSION['CreditItems']->FreightCost + $TaxTotal) !=0) {
			$SQL = "INSERT INTO gltrans (type,
							typeno,
							trandate,
							periodno,
							account,
							narrative,
							amount)
					VALUES (11,
						" . $CreditNo . ",
						'" . $DefaultDispatchDate . "',
						" . $PeriodNo . ",
						" . $_SESSION['CompanyRecord']['debtorsact'] . ",
						'" . $_SESSION['CreditItems']->DebtorNo . "',
						" . -round(($_SESSION['CreditItems']->total + $_SESSION['CreditItems']->FreightCost + $TaxTotal)/$_SESSION['CurrencyRate'],2) . "
					)";

			$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The total debtor GL posting for the credit note could not be inserted because');
			$DbgMsg = _('The following SQL to insert the GLTrans record was used');
			$Result = DB_query($SQL,$db,$ErrMsg, $DbgMsg, true);
		}
		if ($_SESSION['CreditItems']->FreightCost !=0) {
			$SQL = "INSERT INTO gltrans (type,
							typeno,
							trandate,
							periodno,
							account,
							narrative,
							amount)
				VALUES (11,
					" . $CreditNo . ",
					'" . $DefaultDispatchDate . "',
					" . $PeriodNo . ",
					" . $_SESSION['CompanyRecord']['freightact'] . ",
					'" . $_SESSION['CreditItems']->DebtorNo . "',
					" . $_SESSION['CreditItems']->FreightCost/$_SESSION['CurrencyRate'] . "
					)";

			$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The freight GL posting for this credit note could not be inserted because');
			$DbgMsg = _('The following SQL to insert the GLTrans record was used');
		}
		foreach ( $TaxTotals as $TaxAuthID => $TaxAmount){
			if ($TaxAmount !=0 ){
				$SQL = "INSERT INTO gltrans (
						type,
						typeno,
						trandate,
						periodno,
						account,
						narrative,
						amount
						)
					VALUES (
						11,
						" . $CreditNo . ",
						'" . $DefaultDispatchDate . "',
						" . $PeriodNo . ",
						" . $TaxGLCodes[$TaxAuthID] . ",
						'" . $_SESSION['CreditItems']->DebtorNo . "',
						" . ($TaxAmount/$_SESSION['CurrencyRate']) . "
					)";

				$ErrMsg = _('CRITICAL ERROR') . '! ' . _('NOTE DOWN THIS ERROR AND SEEK ASSISTANCE') . ': ' . _('The tax GL posting could not be inserted because');
				$DbgMsg = _('The following SQL to insert the GLTrans record was used');
				$Result = DB_query($SQL,$db,$ErrMsg,$DbgMsg,true);
			}
		}
	} /*end of if Sales and GL integrated */

	$SQL='COMMIT';
	$Result = DB_query($SQL,$db);

	unset($_SESSION['CreditItems']->LineItems);
	unset($_SESSION['CreditItems']);
	unset($_SESSION['ProcessingCredit']);

	/*end of process credit note */
	return $order_id;
}

/**
* Gets customer given its id.
*
* @param string $id The customers id
* @return customer array or null if customer not found
*/
function get_customer($id) {
	global $db;
	$sql = "SELECT *
				FROM custbranch
				WHERE branchcode = '".$id."',
				AND debtorno = '".$_SESSION['CustomerID']."'";
	$result = DB_query($sql, $db);

	if (DB_num_rows($result) > 0) { // exists. update
		$myrow = DB_fetch_row($result);
		$names = explode(" ", $myrow['brname']);

		$customers_array[] = array('first_name' => $names[0],
		'last_name' => $names[1],
		'title' => 'M',
		'birth_date' => '',
		'address1' => $myrow['braddress1'],
		'address2' => $myrow['braddress2'],
		'city' => $myrow['braddress3'],
		'state' => $customers['braddress4'],
		'zip' => $myrow['braddress5'],
		'country' => $myrow['braddress6'],
		'phone' => $myrow['phoneno'],
		'fax' => $myrow['faxno'],
		'email' => $myrow['email']
		);
	}

	return $customers_array;
}

/**
* Creates a customer or updates it if exists.
*
* @param string $first_name The customers first_name
* @param string $last_name The customers last_name
* @param string $title The customers gender
* @param string $birth_date The customers dob
* @param string $address1 The customers address line1
* @param string $address2 The customers address line2
* @param string $city The customers city
* @param string $state The customers state
* @param string $zip The customers zip code
* @param string $country The customers country
* @param string $phone The customers phone
* @param string $fax The customers fax
* @param string $email The customers email address
* @return customer id or false
*/
function create_customer($first_name, $last_name, $title, $birth_date, $address1, $address2, $city, $state, $zip, $country, $phone, $fax, $email) {
	global $db;
	$sql= "SELECT * FROM custbranch WHERE email='".$email."' AND debtorno = '".$_SESSION['CustomerID']."'";
	$result = DB_query($sql,$db);

	if (DB_num_rows($result) > 0) { // exists. update
		$myrow = DB_fetch_array($result);
		$branch_code = $myrow['branchcode'];

		$sql = "UPDATE custbranch SET brname = '" . DB_escape_string($first_name . ' '. $last_name) . "',
						braddress1 = '" . DB_escape_string($address1) . "',
						braddress2 = '" . DB_escape_string($address2) . "',
						braddress3 = '" . DB_escape_string($city) . "',
						braddress4 = '" . DB_escape_string($state) . "',
						braddress5 = '" . DB_escape_string($zip) . "',
						braddress6 = '" . DB_escape_string($country) . "',
						phoneno='" . DB_escape_string($phone) . "',
						faxno='" . DB_escape_string($fax) . "',

						contactname='" . DB_escape_string($first_name . ' '. $last_name) . "',

						email='" . $email . "',

						brpostaddr1 = '" . DB_escape_string($address1) . "',
						brpostaddr2 = '" . DB_escape_string($address2) . "',
						brpostaddr3 = '" . DB_escape_string($city . ",".$state.' '.$zip) . "',
						brpostaddr4 = '" . DB_escape_string($country) . "'
					WHERE branchcode = '".$branch_code."'
					AND debtorno='".$_SESSION['CustomerID']."'";
	} else { // new
		$branch_code = create_branchcode($_SESSION['CustomerID'], $first_name, $last_name);

		if(!$branch_code) {
			return false;
		}

		$sql = "SELECT *
				FROM custbranch
				WHERE branchcode = '".$_SESSION['UserBranch']."'
				AND debtorno = '".$_SESSION['CustomerID']."'";
		$result = DB_query($sql,$db);
		$myrow = DB_fetch_array($result);

		$sql = "INSERT INTO custbranch (branchcode,
						debtorno,
						brname,
						braddress1,
						braddress2,
						braddress3,
						braddress4,
						braddress5,
						braddress6,
						estdeliverydays,
						fwddate,
						salesman,
						phoneno,
						faxno,
						contactname,
						area,
						email,
						taxgroupid,
						defaultlocation,
						brpostaddr1,
						brpostaddr2,
						brpostaddr3,
						brpostaddr4,
						disabletrans,
						defaultshipvia,
						custbranchcode,
						deliverblind)
				VALUES ('" . $branch_code . "',
					'" . $_SESSION['CustomerID'] . "',
					'" . DB_escape_string($first_name . ' '. $last_name) . "',
					'" . DB_escape_string($address1) . "',
					'" . DB_escape_string($address2) . "',
					'" . DB_escape_string($city) . "',
					'" . DB_escape_string($state) . "',
					'" . DB_escape_string($zip) . "',
					'" . DB_escape_string($country) . "',
					" . $myrow['estdeliverydays'] . ",
					" . $myrow['fwddate'] . ",
					'" . $myrow['salesman'] . "',
					'" . DB_escape_string($phone) . "',
					'" . DB_escape_string($fax) . "',
					'" . DB_escape_string($first_name . ' '. $last_name) . "',
					'" . $myrow['area'] . "',
					'" . DB_escape_string($email) . "',
					" . $myrow['taxgroupid'] . ",
					'" . $myrow['defaultlocation'] . "',
					'" . DB_escape_string($address1) . "',
					'" . DB_escape_string($address2) . "',
					'" . DB_escape_string($city . ",".$state.' '.$zip) . "',
					'" . DB_escape_string($country) . "',
					" . $myrow['disabletrans'] . ",
					" . $myrow['defaultshipvia'] . ",
					'" . $myrow['custbranchcode'] ."',
					" . $myrow['deliverblind'] . "
					)";
	}

	$result = DB_query($sql,$db);

	if (DB_error_no($db) ==0) {
		return $branch_code;
	} else {
		return false;
	}
}

/**
* Updates a customer or creates it if doesn't exist.
*
* @param string $id The customers id
* @param string $date date updated
* @param string $first_name The customers first_name
* @param string $last_name The customers last_name
* @param string $title The customers gender
* @param string $birth_date The customers dob
* @param string $address1 The customers address line1
* @param string $address2 The customers address line2
* @param string $city The customers city
* @param string $state The customers state
* @param string $country The customers country
* @param string $zip The customers zip code
* @param string $phone The customers phone
* @param string $fax The customers fax
* @param string $email The customers email address
* @return customer id or false
*/
function update_customer($id, $date, $first_name, $last_name, $title, $birth_date, $address1, $address2, $city, $state, $zip, $country, $phone, $fax, $email) {
	global $db;
	$sql= "SELECT * FROM custbranch WHERE branchcode ='".$id."' AND debtorno = '".$_SESSION['CustomerID']."'";
	$result = DB_query($sql,$db);

	if (DB_num_rows($result) > 0) { // exists. update
		$myrow = DB_fetch_array($result);
		$branch_code = $myrow['branchcode'];

		$sql = "UPDATE custbranch SET brname = '" . DB_escape_string($first_name . ' '. $last_name) . "',
						braddress1 = '" . DB_escape_string($address1) . "',
						braddress2 = '" . DB_escape_string($address2) . "',
						braddress3 = '" . DB_escape_string($city) . "',
						braddress4 = '" . DB_escape_string($state) . "',
						braddress5 = '" . DB_escape_string($zip) . "',
						braddress6 = '" . DB_escape_string($country) . "',
						phoneno='" . DB_escape_string($phone) . "',
						faxno='" . DB_escape_string($fax) . "',

						contactname='" . DB_escape_string($first_name . ' '. $last_name) . "',

						email='" . $email . "',

						brpostaddr1 = '" . DB_escape_string($address1) . "',
						brpostaddr2 = '" . DB_escape_string($address2) . "',
						brpostaddr3 = '" . DB_escape_string($city . ",".$state.' '.$zip) . "',
						brpostaddr4 = '" . DB_escape_string($country) . "'
					WHERE branchcode = '".$branch_code."'
					AND debtorno='".$DebtorNo."'";
	} else { // new
		$branch_code = create_branchcode($_SESSION['CustomerID'], $first_name, $last_name);

		if(!$branch_code) {
			return false;
		}

		$sql = "SELECT *
				FROM custbranch
				WHERE branchcode = '".$_SESSION['UserBranch']."',
				AND debtorno = '".$_SESSION['CustomerID']."'";
		$result = DB_query($sql,$db);
		$myrow = DB_fetch_array($result);

		$sql = "INSERT INTO custbranch (branchcode,
						debtorno,
						brname,
						braddress1,
						braddress2,
						braddress3,
						braddress4,
						braddress5,
						braddress6,
						estdeliverydays,
						fwddate,
						salesman,
						phoneno,
						faxno,
						contactname,
						area,
						email,
						taxgroupid,
						defaultlocation,
						brpostaddr1,
						brpostaddr2,
						brpostaddr3,
						brpostaddr4,
						disabletrans,
						defaultshipvia,
						custbranchcode,
                       			        deliverblind)
				VALUES ('" . $branch_code . "',
					'" . $_SESSION['CustomerID'] . "',
					'" . DB_escape_string($first_name . ' '. $last_name) . "',
					'" . DB_escape_string($address1) . "',
					'" . DB_escape_string($address2) . "',
					'" . DB_escape_string($city) . "',
					'" . DB_escape_string($state) . "',
					'" . DB_escape_string($zip) . "',
					'" . DB_escape_string($country) . "',
					" . $myrow['estdeliverydays'] . ",
					" . $myrow['fwddate'] . ",
					'" . $myrow['salesman'] . "',
					'" . DB_escape_string($phone) . "',
					'" . DB_escape_string($fax) . "',
					'" . DB_escape_string($first_name . ' '. $last_name) . "',
					'" . $myrow['area'] . "',
					'" . DB_escape_string($email) . "',
					" . $myrow['taxgroupid'] . ",
					'" . $myrow['defaultlocation'] . "',
					'" . DB_escape_string($$address1) . "',
					'" . DB_escape_string($$address2) . "',
					'" . DB_escape_string($city . ",".$state.' '.$zip) . "',
					'" . DB_escape_string($country) . "',
					" . $myrow['disabletrans'] . ",
					" . $myrow['defaultshipvia'] . ",
					'" . $myrow['custbranchcode'] ."',
					" . $myrow['deliverblind'] . "
					)";
	}

	$result = DB_query($sql,$db);

	if (DB_error_no($db) ==0) {
		return $branch_code;
	} else {
		return false;
	}
}

/**
* Searches customers.
*
* @param string $first_name The customers first_name
* @param string $last_name The customers last_name
* @param string $title The customers gender
* @param string $birth_date The customers dob
* @param string $address1 The customers address line1
* @param string $address2 The customers address line2
* @param string $city The customers city
* @param string $state The customers state
* @param string $zip The customers zip code
* @param string $country The customers country
* @param string $phone The customers phone
* @param string $fax The customers fax
* @param string $email The customers email address
* @return customers or false
*/
function search_customer($first_name, $last_name, $title, $birth_date, $address1, $address2, $city, $state, $zip, $country, $phone, $fax, $email) {
	global $db;
	$sql = "SELECT *
			FROM custbranch
			WHERE debtorno = '".$_SESSION['CustomerID']."'";

	if($first_name) {
		$sql .= " AND brname like '%".$first_name."%'";
	}

	if($last_name) {
		$sql .= " AND brname like '%".$last_name."%'";
	}

	if($title) {
	}

	if($birth_date) {
	}

	if($address1) {
		$sql .= " AND braddress1 like '%".$address1."%'";
	}

	if($address2) {
		$sql .= " AND braddress2 like '%".$address2."%'";
	}

	if($city) {
		$sql .= " AND braddress3 like '%".$city."%'";
	}

	if($state) {
		$sql .= " AND braddress4 like '%".$state."%'";
	}

	if($zip) {
		$sql .= " AND braddress5 like '%".$zip."%'";
	}

	if($country) {
		$sql .= " AND braddress6 like '%".$country."%'";
	}

	if($phone) {
		$sql .= " AND phoneno like '%".$phone."%'";
	}

	if($fax) {
		$sql .= " AND faxno like '%".$fax."%'";
	}

	if($email) {
		$sql .= " AND email like '%".$email."%'";
	}

	$result = DB_query($sql, $db);

	while($myrow = DB_fetch_row($result)) {
		$names = explode(" ", $myrow['brname']);

		$customers_array[] = array('first_name' => $names[0],
		'last_name' => $names[1],
		'title' => 'M',
		'birth_date' => '',
		'address1' => $myrow['braddress1'],
		'address2' => $myrow['braddress2'],
		'city' => $myrow['braddress3'],
		'state' => $customers['braddress4'],
		'zip' => $myrow['braddress5'],
		'country' => $myrow['braddress6'],
		'phone' => $myrow['phoneno'],
		'fax' => $myrow['faxno'],
		'email' => $myrow['email']
		);
	}

	return $customers_array;
}

$PageSecurity = 1; //Inquiries/Order Entry

// Authenticate the request
if(isset($_COOKIE['Auth'])) {
	$pieces = explode(':', $_COOKIE['Auth']); // company:username:password
	$_POST['CompanyNameField'] = $pieces[0];
	$_POST['UserNameEntryField'] = $pieces[1];
	$_POST['Password'] = $pieces[2];
}

include('synpos/session.inc');
include('synpos/utils.inc');
include('synpos/GetPrice.inc');
include('includes/SQL_CommonFunctions.inc');
include('includes/DefineCartClass.php');
include('includes/GetSalesTransGLCodes.inc');
include('includes/DefineSerialItems.php');

if($xml_request = file_get_contents("php://input")) {
	$data = XML_unserialize($xml_request);

	if(isset($data['GetCategoryRequest'])) {
		$response = "<GetCategoryResponse>";
		$category = get_category($data['GetCategoryRequest']['CategoryId']);

		if(is_array($category)) {
			for($i = 0; $i < count($category['product']); $i++) {
				// product
				$response .= "<Product>
                        		<ProductId>".$category['product'][$i]['id']."</ProductId>
                        		<Barcode>".$category['product'][$i]['barcode']."</Barcode>
                        		<Name>".$category['product'][$i]['name']."</Name>
                        		<Description>".$category['product'][$i]['desc']."</Description>
                        		<Quantity>".$category['product'][$i]['quantity']."</Quantity>
                        		<Price>".$category['product'][$i]['price']."</Price>
                        		<Tax>".$category['product'][$i]['tax']."</Tax>
                      			</Product>";
			}

			for($i = 0; $i < count($category['category']); $i++) {
				// sub-category
				$response .= "<Category>
								<CategoryId>".$category['category'][$i]['id']."</CategoryId>
								<Name>".$category['category'][$i]['name']."</Name>
								<Description>".$category['category'][$i]['desc']."</Description>
								</Category>";
			}
		} else {
			$response .= "<Error><ErrorCode>0</ErrorCode><ErrorMessage>Category not found</ErrorMessage></Error>";
		}

		$response .= "</GetCategoryResponse>";
		echo POSXML_HEADER . $response;
	} elseif(isset($data['GetProductRequest'])) {
		$response = "<GetProductResponse>";
		$product = get_product($data['GetProductRequest']['ProductId']);

		if($product != null) {
			$response .= "<Product>
                      <ProductId>".$product['id']."</ProductId>
                      <Barcode>".$product['barcode']."</Barcode>
                      <Name>".$product['name']."</Name>
                      <Description>".$product['desc']."</Description>
                      <Quantity>".$product['quantity']."</Quantity>
                      <Price>".$product['price']."</Price>
                      <Tax>".$product['tax']."</Tax>
                    </Product>";
		} else {
			$response .= "<Error><ErrorCode>0</ErrorCode><ErrorMessage>Product not found</ErrorMessage></Error>";
		}

		$response .= "</GetProductResponse>";
		echo POSXML_HEADER . $response;
	} elseif(isset($data['SearchProductRequest'])) {
		$response = "<SearchProductResponse>";
		$products = search_product($data['SearchProductRequest']['Barcode'], $data['SearchProductRequest']['Name'], $data['SearchProductRequest']['Description']);

		if($products) {
			for($i = 0; $i < count($products); $i++) {
				$response .= "<Product>
                      <ProductId>".$products[$i]['id']."</ProductId>
                      <Barcode>".$products[$i]['barcode']."</Barcode>
                      <Name>".$products[$i]['name']."</Name>
                      <Description>".$products[$i]['desc']."</Description>
                      <Quantity>".$products[$i]['quantity']."</Quantity>
                      <Price>".$products[$i]['price']."</Price>
                      <Tax>".$products[$i]['tax']."</Tax>
                    </Product>";
			}
		} else {
			$response .= "<Error><ErrorCode>0</ErrorCode><ErrorMessage>Product not found</ErrorMessage></Error>";
		}

		$response .= "</SearchProductResponse>";
		echo POSXML_HEADER . $response;
	} elseif(isset($data['AddOrderRequest'])) {
		$response = "<AddOrderResponse>";

		if(isset($data['AddOrderRequest']['Product'][0])) {
			foreach($data['AddOrderRequest']['Product'] as $product) {
				$products[] = $product;
			}

		} else {
			$products[] = $data['AddOrderRequest']['Product'];

		}

		$order_id = create_order($products, $data['AddOrderRequest']['SubTotal'], $data['AddOrderRequest']['Tax'], $data['AddOrderRequest']['Total'], $data['AddOrderRequest']['CustomerId']);

		if($order_id === false) {
			$response .= "<Error><ErrorCode>0</ErrorCode><ErrorMessage>Order error</ErrorMessage></Error>";
		} else {
			$response .= "<OrderId>".$order_id."</OrderId>";
		}

		$response .= "</AddOrderResponse>";
		echo POSXML_HEADER . $response;
	} elseif(isset($data['CancelOrderRequest'])) {
		$response = "<CancelOrderResponse>";

		if(cancel_order($data['CancelOrderRequest']['OrderId'])) {
			$response .= "<OrderId>".$data['CancelOrderRequest']['OrderId']."</OrderId>";
		} else {
			$response .= "<Error><ErrorCode>0</ErrorCode><ErrorMessage>Order error</ErrorMessage></Error>";
		}

		$response .= "</CancelOrderResponse>";
		echo POSXML_HEADER . $response;
	} elseif(isset($data['GetCustomerRequest'])) {
		$response = "<GetCustomerResponse>";
		$customer = get_customer($data['GetCustomerRequest']['CustomerId']);

		if($customer != null) {
			for($i = 0; $i < count($customer); $i++) {
				$response .= "<Customer>
				            <CustomerId>".$data['GetCustomerRequest']['CustomerId']."</CustomerId>
                      		<FirstName>".$customer[$i]['first_name']."</FirstName>
                      		<LastName>".$customer[$i]['last_name']."</LastName>
                      		<Title>".$customer[$i]['title']."</Title>
                      		<BirthDate>".$customer[$i]['birth_date']."</BirthDate>
                      		<Street1>".$customer[$i]['address1']."</Street1>
                      		<Street2>".$customer[$i]['address2']."</Street2>
                      		<City>".$customer[$i]['city']."</City>
                      		<State>".$customer[$i]['state']."</State>
                      		<Zip>".$customer[$i]['zip']."</Zip>
                      		<Country>".$customers[$i]['country']."</Country>
                      		<Phone>".$customer[$i]['phone']."</Phone>
                      		<Fax>".$customer[$i]['fax']."</Fax>
                      		<Email>".$customer[$i]['email']."</Email>
                    		</Customer>";
			}
		} else {
			$response .= "<Error><ErrorCode>0</ErrorCode><ErrorMessage>Customer not found</ErrorMessage></Error>";
		}

		$response .= "</GetCustomerResponse>";
		echo POSXML_HEADER . $response;
	} elseif(isset($data['AddCustomerRequest'])) {
		$response = "<AddCustomerResponse>";
		$cust_id = create_customer($data['AddCustomerRequest']['FirstName'],
		$data['AddCustomerRequest']['LastName'],
		$data['AddCustomerRequest']['Title'],
		$data['AddCustomerRequest']['BirthDate'],
		$data['AddCustomerRequest']['Street1'],
		$data['AddCustomerRequest']['Street2'],
		$data['AddCustomerRequest']['City'],
		$data['AddCustomerRequest']['State'],
		$data['AddCustomerRequest']['Zip'],
		$data['AddCustomerRequest']['Country'],
		$data['AddCustomerRequest']['Phone'],
		$data['AddCustomerRequest']['Fax'],
		$data['AddCustomerRequest']['Email']);

		if($cust_id === false) {
			$response .= "<Error><ErrorCode>0</ErrorCode><ErrorMessage>Customer error</ErrorMessage></Error>";
		} else {
			$response .= "<CustomerId>".$cust_id."</CustomerId>";
		}

		$response .= "</AddCustomerResponse>";
		echo POSXML_HEADER . $response;
	} elseif(isset($data['UpdateCustomerRequest'])) {
		$response = "<UpdateCustomerResponse>";
		$cust_id = update_customer($data['UpdateCustomerRequest']['CustomerId'], $data['UpdateCustomerRequest']['DateModified'], $data['UpdateCustomerRequest']['FirstName'],
		$data['UpdateCustomerRequest']['LastName'],
		$data['UpdateCustomerRequest']['Title'],
		$data['UpdateCustomerRequest']['BirthDate'],
		$data['UpdateCustomerRequest']['Street1'],
		$data['UpdateCustomerRequest']['Street2'],
		$data['UpdateCustomerRequest']['City'],
		$data['UpdateCustomerRequest']['State'],
		$data['UpdateCustomerRequest']['Zip'],
		$data['UpdateCustomerRequest']['Country'],
		$data['UpdateCustomerRequest']['Phone'],
		$data['UpdateCustomerRequest']['Fax'],
		$data['UpdateCustomerRequest']['Email']);

		if($cust_id === false) {
			$response .= "<Error><ErrorCode>0</ErrorCode><ErrorMessage>Customer error</ErrorMessage></Error>";
		} else {
			$response .= "<CustomerId>".$cust_id."</CustomerId>";
		}

		$response .= "</UpdateCustomerResponse>";
		echo POSXML_HEADER . $response;
	} elseif(isset($data['SearchCustomerRequest'])) {
		$response = "<SearchCustomerResponse>";
		$customers = search_customer($data['SearchCustomerRequest']['FirstName'],
		$data['SearchCustomerRequest']['LastName'],
		$data['SearchCustomerRequest']['Title'],
		$data['SearchCustomerRequest']['BirthDate'],
		$data['SearchCustomerRequest']['Street1'],
		$data['SearchCustomerRequest']['Street2'],
		$data['SearchCustomerRequest']['City'],
		$data['SearchCustomerRequest']['State'],
		$data['SearchCustomerRequest']['Zip'],
		$data['SearchCustomerRequest']['Country'],
		$data['SearchCustomerRequest']['Phone'],
		$data['SearchCustomerRequest']['Fax'],
		$data['SearchCustomerRequest']['Email']);

		if($customers != null) {
			for($i = 0; $i < count($customers); $i++) {
				$response .= "<Customer>
				            <CustomerId>".$customers[$i]['id']."</CustomerId>
                      		<FirstName>".$customers[$i]['first_name']."</FirstName>
                      		<LastName>".$customers[$i]['last_name']."</LastName>
                      		<Title>".$customers[$i]['title']."</Title>
                      		<BirthDate>".$customers[$i]['birth_date']."</BirthDate>
                      		<Street1>".$customers[$i]['address1']."</Street1>
                      		<Street2>".$customers[$i]['address2']."</Street2>
                      		<City>".$customers[$i]['city']."</City>
                      		<State>".$customers[$i]['state']."</State>
                      		<Zip>".$customers[$i]['zip']."</Zip>
                      		<Country>".$customers[$i]['country']."</Country>
                      		<Phone>".$customers[$i]['phone']."</Phone>
                      		<Fax>".$customers[$i]['fax']."</Fax>
                      		<Email>".$customers[$i]['email']."</Email>
                    		</Customer>";
			}
		} else {
			$response .= "<Error><ErrorCode>0</ErrorCode><ErrorMessage>Customer error</ErrorMessage></Error>";
		}

		$response .= "</SearchCustomerResponse>";
		echo POSXML_HEADER . $response;
	} else {
		die("Unknown request");
	}
} else {
	die("XML error");
}

exit;

###################################################################################
#
# XML Library, by Keith Devens, version 1.2b
# http://keithdevens.com/software/phpxml
#
# This code is Open Source, released under terms similar to the Artistic License.
# Read the license at http://keithdevens.com/software/license
#
###################################################################################

###################################################################################
# XML_unserialize: takes raw XML as a parameter (a string)
# and returns an equivalent PHP data structure
###################################################################################
function & XML_unserialize(&$xml){
	$xml_parser = &new XML();
	$data = &$xml_parser->parse($xml);
	$xml_parser->destruct();
	return $data;
}
###################################################################################
# XML_serialize: serializes any PHP data structure into XML
# Takes one parameter: the data to serialize. Must be an array.
###################################################################################
function & XML_serialize(&$data, $level = 0, $prior_key = NULL){
	if($level == 0){ ob_start(); echo '<?xml version="1.0" ?>',"\n"; }
	while(list($key, $value) = each($data))
	if(!strpos($key, ' attr')) #if it's not an attribute
	#we don't treat attributes by themselves, so for an empty element
	# that has attributes you still need to set the element to NULL

	if(is_array($value) and array_key_exists(0, $value)){
		XML_serialize($value, $level, $key);
	}else{
		$tag = $prior_key ? $prior_key : $key;
		echo str_repeat("\t", $level),'<',$tag;
		if(array_key_exists("$key attr", $data)){ #if there's an attribute for this element
			while(list($attr_name, $attr_value) = each($data["$key attr"]))
			echo ' ',$attr_name,'="',htmlspecialchars($attr_value),'"';
			reset($data["$key attr"]);
		}

		if(is_null($value)) echo " />\n";
		elseif(!is_array($value)) echo '>',htmlspecialchars($value),"</$tag>\n";
		else echo ">\n",XML_serialize($value, $level+1),str_repeat("\t", $level),"</$tag>\n";
	}
	reset($data);
	if($level == 0){ $str = &ob_get_contents(); ob_end_clean(); return $str; }
}
###################################################################################
# XML class: utility class to be used with PHP's XML handling functions
###################################################################################
class XML{
	var $parser;   #a reference to the XML parser
	var $document; #the entire XML structure built up so far
	var $parent;   #a pointer to the current parent - the parent will be an array
	var $stack;    #a stack of the most recent parent at each nesting level
	var $last_opened_tag; #keeps track of the last tag opened.

	function XML(){
		$this->parser = &xml_parser_create();
		xml_parser_set_option(&$this->parser, XML_OPTION_CASE_FOLDING, false);
		xml_set_object(&$this->parser, &$this);
		xml_set_element_handler(&$this->parser, 'open','close');
		xml_set_character_data_handler(&$this->parser, 'data');
	}
	function destruct(){ xml_parser_free(&$this->parser); }
	function & parse(&$data){
		$this->document = array();
		$this->stack    = array();
		$this->parent   = &$this->document;
		return xml_parse(&$this->parser, &$data, true) ? $this->document : NULL;
	}
	function open(&$parser, $tag, $attributes){
		$this->data = ''; #stores temporary cdata
		$this->last_opened_tag = $tag;
		if(is_array($this->parent) and array_key_exists($tag,$this->parent)){ #if you've seen this tag before
			if(is_array($this->parent[$tag]) and array_key_exists(0,$this->parent[$tag])){ #if the keys are numeric
				#this is the third or later instance of $tag we've come across
				$key = count_numeric_items($this->parent[$tag]);
			}else{
				#this is the second instance of $tag that we've seen. shift around
				if(array_key_exists("$tag attr",$this->parent)){
					$arr = array('0 attr'=>&$this->parent["$tag attr"], &$this->parent[$tag]);
					unset($this->parent["$tag attr"]);
				}else{
					$arr = array(&$this->parent[$tag]);
				}
				$this->parent[$tag] = &$arr;
				$key = 1;
			}
			$this->parent = &$this->parent[$tag];
		}else{
			$key = $tag;
		}
		if($attributes) $this->parent["$key attr"] = $attributes;
		$this->parent  = &$this->parent[$key];
		$this->stack[] = &$this->parent;
	}
	function data(&$parser, $data){
		if($this->last_opened_tag != NULL) #you don't need to store whitespace in between tags
		$this->data .= $data;
	}
	function close(&$parser, $tag){
		if($this->last_opened_tag == $tag){
			$this->parent = $this->data;
			$this->last_opened_tag = NULL;
		}
		array_pop($this->stack);
		if($this->stack) $this->parent = &$this->stack[count($this->stack)-1];
	}

	function count_numeric_items(&$array){
		return is_array($array) ? count(array_filter(array_keys($array), 'is_numeric')) : 0;
	}
}
?>
