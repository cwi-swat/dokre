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
define(ROOT_CATEGORY_ID, '0');
define(POSXML_HEADER, '<?xml version="1.0" encoding="UTF-8"?>');

/**
* Verifies the username and password required for calling this script.
*
* @param string $username The username
* @param string $password The password
* @return true or false
*/
function authenticate($username, $password) {
	if(strcmp($username, SYNPOS_USERNAME) == 0 && strcmp($password, SYNPOS_PASSWORD) == 0) return true;
	else return false;
}

/**
* Gets the sub-categories and products of a category.
*
* @param $category The path array of the category, eg, ['level 1']['Level 2']. An empty string '' represents the root category.
* @return the sub-categories and products in the category or null if category not found
*/
function get_category($category_id) {
	if($category_id < 0) {
		return null;
	}

	$category_array = array();

	$lang = new language();
	$lang->set_language(DEFAULT_LANGUAGE);

	$categories_query = tep_db_query("select cd.categories_id, cd.categories_name from " . TABLE_CATEGORIES . " c, " . TABLE_CATEGORIES_DESCRIPTION . " cd where c.parent_id = '" . $category_id . "' and c.categories_id = cd.categories_id and cd.language_id = '". (int) $lang->language['id'] . "'");

	while($categories = tep_db_fetch_array($categories_query)) {
		$category_array['category'][] = array('id'=>$categories['categories_id'], 'name'=>$categories['categories_name'],'desc'=>''); // encode category name. more special chars to escape?
	}

	$products_query = tep_db_query("select p.products_id, p.products_model, p.products_quantity, pd.products_name, pd.products_description, p.products_price, p.products_tax_class_id from " . TABLE_PRODUCTS . " p, " . TABLE_PRODUCTS_DESCRIPTION . " pd, " . TABLE_PRODUCTS_TO_CATEGORIES . " p2c where pd.products_id = p.products_id and p.products_id = p2c.products_id and p2c.categories_id = '" . $category_id . "' and pd.language_id = '" . (int) $lang->language['id'] . "'");

	while($products = tep_db_fetch_array($products_query)) {
		$prid = $products['products_id'];
		$products_price = $products['products_price'];
		$products_tax = tep_get_tax_rate($products['products_tax_class_id']);
		$products_name = $products['products_name'];
		$products_desc = '';//$products['products_description'];
		$products_number = $products['products_model'];

		$specials_query = tep_db_query("select specials_new_products_price from " . TABLE_SPECIALS . " where products_id = '" . (int)$prid . "' and status = '1'");

		if(tep_db_num_rows($specials_query)) {
			$specials = tep_db_fetch_array($specials_query);
			$products_price = $specials['specials_new_products_price'];
		}
		/*
		$attributes_price = 0;
		$attributes_query = tep_db_query("select po.products_options_name, pov.products_options_values_name, pa.options_values_price, pa.price_prefix from " . TABLE_PRODUCTS_ATTRIBUTES . " pa, " . TABLE_PRODUCTS_OPTIONS . " po, " . TABLE_PRODUCTS_OPTIONS_VALUES . " pov where pa.products_id = " . $prid . " and po.language_id = " . $lang->language['id'] . " and po.products_options_id = pa.options_id and pov.language_id = " . $lang->language['id'] . " and pov.products_options_values_id = pa.options_values_id");

		while($attributes = tep_db_fetch_array($attributes_query)) {
		$attributes_array[$attributes['products_options_name']] = $attributes['products_options_values_name'];

		if ($attributes['price_prefix'] == '+') {
		$attributes_price += $attributes['options_values_price'];
		} else {
		$attributes_price -= $attributes['options_values_price'];
		}

		$products_desc .= " " . $attributes['products_options_name'] . ":" . $attributes['products_options_values_name'];
		}

		$products_price += $attributes_price;
		*/
		$category_array['product'][] = array('id' => $prid,
		'barcode' => $products_number,
		'name' => $products_name,
		'desc' => $products_desc,
		'price' => $products_price,
		'quantity' => $products['products_quantity'],
		'tax' => $products_tax);
	}

	return $category_array;
}

/**
* Gets the information of a product given its number.
*
* @param string $id The id (model) of the product
* @return product array or null if product not found
*/
function get_product($id) {
	$lang = new language();
	$lang->set_language(DEFAULT_LANGUAGE);
	$products_query = tep_db_query("select p.products_id, p.products_model, p.products_quantity, pd.products_name, pd.products_description, p.products_price, p.products_tax_class_id from " . TABLE_PRODUCTS . " p, " . TABLE_PRODUCTS_DESCRIPTION . " pd where pd.products_id = p.products_id and p.products_id = '" . $id . "' and pd.language_id = '" . (int) $lang->language['id'] . "'");

	if($products = tep_db_fetch_array($products_query)) {
		$prid = $products['products_id'];
		$products_price = $products['products_price'];
		$products_tax = tep_get_tax_rate($products['products_tax_class_id']);
		$products_desc = '';//$products['products_description'];
		$products_name = $products['products_name'];
		$products_number = $products['products_model'];

		$specials_query = tep_db_query("select specials_new_products_price from " . TABLE_SPECIALS . " where products_id = '" . (int)$prid . "' and status = '1'");

		if(tep_db_num_rows($specials_query)) {
			$specials = tep_db_fetch_array($specials_query);
			$products_price = $specials['specials_new_products_price'];
		}
		/*
		$attributes_price = 0;
		$attributes_query = tep_db_query("select po.products_options_name, pov.products_options_values_name, pa.options_values_price, pa.price_prefix from " . TABLE_PRODUCTS_ATTRIBUTES . " pa, " . TABLE_PRODUCTS_OPTIONS . " po, " . TABLE_PRODUCTS_OPTIONS_VALUES . " pov where pa.products_id = " . $prid . " and po.language_id = " . $lang->language['id'] . " and po.products_options_id = pa.options_id and pov.language_id = " . $lang->language['id'] . " and pov.products_options_values_id = pa.options_values_id");

		while($attributes = tep_db_fetch_array($attributes_query)) {
		$attributes_array[$attributes['products_options_name']] = $attributes['products_options_values_name'];

		if ($attributes['price_prefix'] == '+') {
		$attributes_price += $attributes['options_values_price'];
		} else {
		$attributes_price -= $attributes['options_values_price'];
		}

		$products_desc .= " " . $attributes['products_options_name'] . ":" . $attributes['products_options_values_name'];
		}

		$products_price += $attributes_price;
		*/
		$products_array = array('id' => $prid,
		'barcode' => $products_number,
		'name' => $products_name,
		'desc' => $products_desc,
		'price' => $products_price,
		'quantity' => $products['products_quantity'],
		'tax' => $products_tax);

		return $products_array;

	} else {
		return null;
	}
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
	$lang = new language();
	$lang->set_language(DEFAULT_LANGUAGE);
	$sql = "select p.products_id, p.products_quantity, p.products_model, pd.products_name, pd.products_description, p.products_price, p.products_tax_class_id from " . TABLE_PRODUCTS . " p, " . TABLE_PRODUCTS_DESCRIPTION . " pd where pd.products_id = p.products_id and pd.language_id = '" . (int) $lang->language['id'] . "'";

	if($barcode) {
		$sql .= " and p.products_model = '".$barcode."'";
	}

	if($name) {
		$sql .= " and pd.products_name like '%".$name."%'";
	}

	if($desc) {
		$sql .= " and pd.products_description like '%".$desc."%'";
	}

	$products_query = tep_db_query($sql);

	while($products = tep_db_fetch_array($products_query)) {
		$prid = $products['products_id'];
		$products_price = $products['products_price'];
		$products_tax = tep_get_tax_rate($products['products_tax_class_id']);
		$products_desc = '';//$products['products_description'];
		$products_name = $products['products_name'];
		$products_number = $products['products_model'];

		$specials_query = tep_db_query("select specials_new_products_price from " . TABLE_SPECIALS . " where products_id = '" . (int)$prid . "' and status = '1'");

		if(tep_db_num_rows($specials_query)) {
			$specials = tep_db_fetch_array($specials_query);
			$products_price = $specials['specials_new_products_price'];
		}
		/*
		$attributes_price = 0;
		$attributes_query = tep_db_query("select po.products_options_name, pov.products_options_values_name, pa.options_values_price, pa.price_prefix from " . TABLE_PRODUCTS_ATTRIBUTES . " pa, " . TABLE_PRODUCTS_OPTIONS . " po, " . TABLE_PRODUCTS_OPTIONS_VALUES . " pov where pa.products_id = " . $prid . " and po.language_id = " . $lang->language['id'] . " and po.products_options_id = pa.options_id and pov.language_id = " . $lang->language['id'] . " and pov.products_options_values_id = pa.options_values_id");

		while($attributes = tep_db_fetch_array($attributes_query)) {
		$attributes_array[$attributes['products_options_name']] = $attributes['products_options_values_name'];

		if ($attributes['price_prefix'] == '+') {
		$attributes_price += $attributes['options_values_price'];
		} else {
		$attributes_price -= $attributes['options_values_price'];
		}

		$products_desc .= " " . $attributes['products_options_name'] . ":" . $attributes['products_options_values_name'];
		}

		$products_price += $attributes_price;
		*/
		$products_array[] = array('id' => $prid,
		'barcode' => $products_number,
		'name' => $products_name,
		'desc' => $products_desc,
		'price' => $products_price,
		'quantity' => $products['products_quantity'],
		'tax' => $products_tax);
	}

	return $products_array;
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
	global $language, $currency, $currencies;
	$customer_address_query = tep_db_query("select c.customers_firstname, c.customers_lastname, c.customers_telephone, c.customers_email_address, ab.entry_company, ab.entry_street_address, ab.entry_suburb, ab.entry_postcode, ab.entry_city, ab.entry_zone_id, z.zone_name, co.countries_id, co.countries_name, co.countries_iso_code_2, co.countries_iso_code_3, co.address_format_id, ab.entry_state from " . TABLE_CUSTOMERS . " c, " . TABLE_ADDRESS_BOOK . " ab left join " . TABLE_ZONES . " z on (ab.entry_zone_id = z.zone_id) left join " . TABLE_COUNTRIES . " co on (ab.entry_country_id = co.countries_id) where c.customers_id = '" . (int)$customer_id . "' and ab.customers_id = '" . (int)$customer_id . "' and c.customers_default_address_id = ab.address_book_id");
	$customer_address = tep_db_fetch_array($customer_address_query);

	if(!tep_not_null($customer_address)) {
		return false;
	}

	tep_db_query("update " . TABLE_CUSTOMERS_INFO . " set customers_info_date_of_last_logon = now(), customers_info_number_of_logons = customers_info_number_of_logons+1 where customers_info_id = '" . (int)$customer_id . "'");

	$sql_data_array = array('customers_id' => $customer_id,
	'customers_name' => $customer_address['customers_firstname'] . ' ' . $customer_address['customers_lastname'],
	'customers_company' => $customer_address['entry_company'],
	'customers_street_address' => $customer_address['entry_street_address'],
	'customers_suburb' => $customer_address['entry_suburb'],
	'customers_city' => $customer_address['entry_city'],
	'customers_postcode' => $customer_address['entry_postcode'],
	'customers_state' => ((tep_not_null($customer_address['entry_state'])) ? $customer_address['entry_state'] : $customer_address['zone_name']),
	'customers_country' => $customer_address['countries_name'],
	'customers_telephone' => $customer_address['customers_telephone'],
	'customers_email_address' => $customer_address['customers_email_address'],
	'customers_address_format_id' => $customer_address['address_format_id'],
	'delivery_name' => $customer_address['customers_firstname'] . ' ' . $customer_address['customers_lastname'],
	'delivery_company' => $customer_address['entry_company'],
	'delivery_street_address' => $customer_address['entry_street_address'],
	'delivery_suburb' => $customer_address['entry_suburb'],
	'delivery_city' => $customer_address['entry_city'],
	'delivery_postcode' => $customer_address['entry_postcode'],
	'delivery_state' => ((tep_not_null($customer_address['entry_state'])) ? $customer_address['entry_state'] : $customer_address['zone_name']),
	'delivery_country' => $customer_address['countries_name'],
	'delivery_address_format_id' => $customer_address['address_format_id'],
	'billing_name' => $customer_address['customers_firstname'] . ' ' . $customer_address['customers_lastname'],
	'billing_company' => $customer_address['entry_company'],
	'billing_street_address' => $customer_address['entry_street_address'],
	'billing_suburb' => $customer_address['entry_suburb'],
	'billing_city' => $customer_address['entry_city'],
	'billing_postcode' => $customer_address['entry_postcode'],
	'billing_state' => ((tep_not_null($customer_address['entry_state'])) ? $customer_address['entry_state'] : $customer_address['zone_name']),
	'billing_country' => $customer_address['countries_name'],
	'billing_address_format_id' => $customer_address['address_format_id'],
	'payment_method' => 'Cash',
	'cc_type' => '',
	'cc_owner' => '',
	'cc_number' => '',
	'cc_expires' => '',
	'date_purchased' => 'now()',
	'orders_status' => DEFAULT_ORDERS_STATUS_ID,
	'currency' => $currency,
	'currency_value' => $currencies->currencies[$currency]['value']);
	tep_db_perform(TABLE_ORDERS, $sql_data_array);
	$insert_id = tep_db_insert_id();

	if(tep_not_null(MODULE_ORDER_TOTAL_SUBTOTAL_STATUS == 'true')) {
		include(DIR_WS_LANGUAGES . $language . '/modules/order_total/ot_subtotal.php');
		$sql_data_array = array('orders_id' => $insert_id,
		'title' => MODULE_ORDER_TOTAL_SUBTOTAL_TITLE,
		'text' => $currencies->format($subtotal, true, $currency, $currencies->currencies[$currency]['value']),
		'value' => $subtotal,
		'class' => 'ot_subtotal',
		'sort_order' => MODULE_ORDER_TOTAL_SUBTOTAL_SORT_ORDER);
		tep_db_perform(TABLE_ORDERS_TOTAL, $sql_data_array);
	}

	if(tep_not_null(MODULE_ORDER_TOTAL_TAX_STATUS == 'true')) {
		include(DIR_WS_LANGUAGES . $language . '/modules/order_total/ot_tax.php');
		$sql_data_array = array('orders_id' => $insert_id,
		'title' => MODULE_ORDER_TOTAL_TAX_TITLE,
		'text' => $currencies->format($tax, true, $currency, $currencies->currencies[$currency]['value']),
		'value' => $tax,
		'class' => 'ot_tax',
		'sort_order' => MODULE_ORDER_TOTAL_TAX_SORT_ORDER);
		tep_db_perform(TABLE_ORDERS_TOTAL, $sql_data_array);
	}

	if(tep_not_null(MODULE_ORDER_TOTAL_TOTAL_STATUS == 'true')) {
		include(DIR_WS_LANGUAGES . $language . '/modules/order_total/ot_total.php');
		$sql_data_array = array('orders_id' => $insert_id,
		'title' => MODULE_ORDER_TOTAL_TOTAL_TITLE,
		'text' => $currencies->format($total, true, $currency, $currencies->currencies[$currency]['value']),
		'value' => $total,
		'class' => 'ot_total',
		'sort_order' => MODULE_ORDER_TOTAL_TOTAL_SORT_ORDER);
		tep_db_perform(TABLE_ORDERS_TOTAL, $sql_data_array);
	}

	$sql_data_array = array('orders_id' => $insert_id,
	'orders_status_id' => DEFAULT_ORDERS_STATUS_ID,
	'date_added' => 'now()',
	'customer_notified' => 1,
	'comments' => '');
	tep_db_perform(TABLE_ORDERS_STATUS_HISTORY, $sql_data_array);
	$lang = new language();
	$lang->set_language(DEFAULT_LANGUAGE);

	for ($i=0, $n=sizeof($products); $i<$n; $i++) {
		$products_query = tep_db_query("select p.products_id, p.products_model, p.products_quantity, pd.products_name, pd.products_description, p.products_price, p.products_tax_class_id from " . TABLE_PRODUCTS . " p, " . TABLE_PRODUCTS_DESCRIPTION . " pd where pd.products_id = p.products_id and p.products_id = '" . $products[$i]['ProductId'] . "' and pd.language_id = '" . (int) $lang->language['id'] . "'");

		if($check_products = tep_db_fetch_array($products_query)) {
			$prid = $check_products['products_id'];
			$products_price = $check_products['products_price'];
			$products_tax = tep_get_tax_rate($check_products['products_tax_class_id']);
			$products_desc = '';//$products['products_description'];
			$products_name = $check_products['products_name'];
			$products_model = $check_products['products_model'];

			$specials_query = tep_db_query("select specials_new_products_price from " . TABLE_SPECIALS . " where products_id = '" . (int)$prid . "' and status = '1'");

			if(tep_db_num_rows($specials_query)) {
				$specials = tep_db_fetch_array($specials_query);
				$products_price = $specials['specials_new_products_price'];
			}

			// Stock Update - Joao Correia
			if (STOCK_LIMITED == 'true') {
				$stock_query = tep_db_query("select products_quantity from " . TABLE_PRODUCTS . " where products_id = '" . tep_get_prid($products[$i]['ProductId']) . "'");

				if (tep_db_num_rows($stock_query) > 0) {
					$stock_values = tep_db_fetch_array($stock_query);
					$stock_left = $stock_values['products_quantity'] - $products[$i]['Quantity'];
					tep_db_query("update " . TABLE_PRODUCTS . " set products_quantity = '" . $stock_left . "' where products_id = '" . tep_get_prid($products[$i]['ProductId']) . "'");

					if ( ($stock_left < 1) && (STOCK_ALLOW_CHECKOUT == 'false') ) {
						tep_db_query("update " . TABLE_PRODUCTS . " set products_status = '0' where products_id = '" . tep_get_prid($products[$i]['ProductId']) . "'");
					}
				}
			}

			// Update products_ordered (for bestsellers list)
			tep_db_query("update " . TABLE_PRODUCTS . " set products_ordered = products_ordered + " . sprintf('%d', $products[$i]['Quantity']) . " where products_id = '" . tep_get_prid($products[$i]['ProductId']) . "'");

			$sql_data_array = array('orders_id' => $insert_id,
			'products_id' => $prid,
			'products_model' => $products_model,
			'products_name' => $products_name,
			'products_price' => $products_price,
			'final_price' => $products_price,
			'products_tax' => $products_tax,
			'products_quantity' => $products[$i]['Quantity']);
			tep_db_perform(TABLE_ORDERS_PRODUCTS, $sql_data_array);
			$order_products_id = tep_db_insert_id();
		}
	}

	return $insert_id;
}

/**
* Cancels an order.
*
* @param string $id The order id to be cancelled
*/
function cancel_order($id) {
	$products_query = tep_db_query("select * from " . TABLE_ORDERS_PRODUCTS . " where orders_id = '" . $id ."'");

	if(tep_db_num_rows($products_query) > 0) {
		if(STOCK_LIMITED == 'true') {
			while($products = tep_db_fetch_array($products_query)) {
				$stock_query = tep_db_query("select products_quantity from " . TABLE_PRODUCTS . " where products_id = '" . tep_get_prid($products['products_id']) . "'");

				if (tep_db_num_rows($stock_query) > 0) {
					$stock_values = tep_db_fetch_array($stock_query);
					$stock_left = $stock_values['products_quantity'] + $products['products_quantity'];
					tep_db_query("update " . TABLE_PRODUCTS . " set products_quantity = '" . $stock_left . "' where products_id = '" . tep_get_prid($products['products_id']) . "'");

					if ( ($stock_values['products_quantity'] < 1) && ($stock_left > 0) && (STOCK_ALLOW_CHECKOUT == 'false') ) {
						tep_db_query("update " . TABLE_PRODUCTS . " set products_status = '1' where products_id = '" . tep_get_prid($products['products_id']) . "'");
					}
				}
			}
		}

		return true;
	} else {
		return false;
	}
}

/**
* Gets customer given its id.
*
* @param string $id The customers id
* @return customer array or null if customer not found
*/
function get_customer($id) {
	$customers_query = tep_db_query("select c.customers_firstname, c.customers_lastname, c.customers_gender, c.customers_dob, ab.entry_street_address, ab.entry_suburb, ab.entry_city, ab.entry_state, ab.entry_postcode, c.customers_telephone, c.customers_fax, c.customers_email_address from " . TABLE_CUSTOMERS . " c, " . TABLE_ADDRESS_BOOK . " ab where c.customers_id = ab.customers_id and c.default_address_id = ab.address_book_id and c.customers_id = '".$id . "'");
	$customers_array = null;

	while($customers = tep_db_fetch_array($customers_query)) {

		$customers_array[] = array('first_name' => $customers['customers_firstname'],
		'last_name' => $customers['customers_lastname'],
		'title' => $customers['customers_gender'] == 'm' ? 'Mr.' : 'Ms.',
		'birth_date' => $customers['customers_dob'],
		'address1' => $customers['entry_street_address'],
		'address2' => $customers['entry_subsurb'],
		'city' => $customers['entry_city'],
		'state' => $customers['entry_state'],
		'zip' => $customers['entry_postcode'],
		'phone' => $customers['customers_telephone'],
		'fax' => $customers['customers_fax'],
		'email' => $customers['customers_email_address']
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
* @param string $phone The customers phone
* @param string $fax The customers fax
* @param string $email The customers email address
* @return customer id or false
*/
function create_customer($first_name, $last_name, $title, $birth_date, $address1, $address2, $city, $state, $zip, $phone, $fax, $email) {
	global $language;
	$customers_query = tep_db_query("select customers_id, customers_default_address_id from " . TABLE_CUSTOMERS . " where customers_email_address = '".$email . "'");

	if($customers = tep_db_fetch_array($customers_query)) {	// update
		$id = $customers['customers_id'];

		$sql_data_array = array('customers_firstname' => $first_name,
		'customers_lastname' => $last_name,
		'customers_email_address' => $email,
		'customers_telephone' => $phone,
		'customers_fax' => $fax);

		if (ACCOUNT_GENDER == 'true') $sql_data_array['customers_gender'] = ($title == 'M' ? 'm' : 'f');
		if (ACCOUNT_DOB == 'true') $sql_data_array['customers_dob'] = tep_date_raw($birth_date);

		tep_db_perform(TABLE_CUSTOMERS, $sql_data_array, 'update', "customers_id = ".$id);

		$sql_data_array = array(
		'entry_firstname' => $first_name,
		'entry_lastname' => $last_name,
		'entry_street_address' => $address1,
		'entry_postcode' => $zip,
		'entry_city' => $city);

		if (ACCOUNT_GENDER == 'true') $sql_data_array['entry_gender'] = ($title == 'M' ? 'm' : 'f');
		if (ACCOUNT_COMPANY == 'true') $sql_data_array['entry_company'] = '';
		if (ACCOUNT_SUBURB == 'true') $sql_data_array['entry_suburb'] = $address2;

		if (ACCOUNT_STATE == 'true') {
			$zone_id = 0;
			$check_query = tep_db_query("select count(*) as total from " . TABLE_ZONES . " where zone_country_id = '" . (int) STORE_COUNTRY . "'");
			$check = tep_db_fetch_array($check_query);
			$entry_state_has_zones = ($check['total'] > 0);

			if ($entry_state_has_zones == true) {
				$zone_query = tep_db_query("select distinct zone_id from " . TABLE_ZONES . " where zone_country_id = '" . (int) STORE_COUNTRY . "' and (zone_name like '" . tep_db_input($state) . "%' or zone_code like '%" . tep_db_input($state) . "%')");

				if (tep_db_num_rows($zone_query) == 1) {
					$zone = tep_db_fetch_array($zone_query);
					$zone_id = $zone['zone_id'];
				}
			}

			if ($zone_id > 0) {
				$sql_data_array['entry_zone_id'] = $zone_id;
				$sql_data_array['entry_state'] = '';
			} else {
				$sql_data_array['entry_zone_id'] = '0';
				$sql_data_array['entry_state'] = $state;
			}
		}

		tep_db_perform(TABLE_ADDRESS_BOOK, $sql_data_array, 'update', "customers_id = ".$id);
		tep_db_query("update " . TABLE_CUSTOMERS_INFO . " set customers_info_number_of_logons = customers_info_number_of_logons+1, customers_info_date_account_last_modified = now() where customers_info_id =".$id);

		return $id;

	} else { // create a new account
		//Generate a random 8-char password
		$salt = "46z3haZzegmn676PA3rUw2vrkhcLEn2p1c6gf7vp2ny4u3qqfqBh5j6kDhuLmyv9xf";
		srand((double)microtime()*1000000);
		$password = '';

		for ($x = 0; $x < 7; $x++) {
			$num = rand() % 33;
			$tmp = substr($salt, $num, 1);
			$password = $password . $tmp;
		}

		$sql_data_array = array('customers_firstname' => $first_name,
		'customers_lastname' => $last_name,
		'customers_email_address' => $email,
		'customers_telephone' => $phone,
		'customers_fax' => $fax,
		'customers_newsletter' => false,
		'customers_password' => tep_encrypt_password($password));

		if (ACCOUNT_GENDER == 'true') $sql_data_array['customers_gender'] = ($title == 'M' ? 'm' : 'f');
		if (ACCOUNT_DOB == 'true') $sql_data_array['customers_dob'] = tep_date_raw($birth_date);

		tep_db_perform(TABLE_CUSTOMERS, $sql_data_array);

		$customer_id = tep_db_insert_id();


		$sql_data_array = array('customers_id' => $customer_id,
		'entry_firstname' => $first_name,
		'entry_lastname' => $last_name,
		'entry_street_address' => $address1,
		'entry_postcode' => $zip,
		'entry_city' => $city,
		'entry_country_id' => STORE_COUNTRY);

		if (ACCOUNT_GENDER == 'true') $sql_data_array['entry_gender'] = ($title == 'M' ? 'm' : 'f');
		if (ACCOUNT_COMPANY == 'true') $sql_data_array['entry_company'] = '';
		if (ACCOUNT_SUBURB == 'true') $sql_data_array['entry_suburb'] = $address2;

		if (ACCOUNT_STATE == 'true') {
			$zone_id = 0;
			$check_query = tep_db_query("select count(*) as total from " . TABLE_ZONES . " where zone_country_id = '" . (int) STORE_COUNTRY . "'");
			$check = tep_db_fetch_array($check_query);
			$entry_state_has_zones = ($check['total'] > 0);

			if ($entry_state_has_zones == true) {
				$zone_query = tep_db_query("select distinct zone_id from " . TABLE_ZONES . " where zone_country_id = '" . (int) STORE_COUNTRY . "' and (zone_name like '" . tep_db_input($state) . "%' or zone_code like '%" . tep_db_input($state) . "%')");

				if (tep_db_num_rows($zone_query) == 1) {
					$zone = tep_db_fetch_array($zone_query);
					$zone_id = $zone['zone_id'];
				}
			}

			if ($zone_id > 0) {
				$sql_data_array['entry_zone_id'] = $zone_id;
				$sql_data_array['entry_state'] = '';
			} else {
				$sql_data_array['entry_zone_id'] = '0';
				$sql_data_array['entry_state'] = $state;
			}
		}

		tep_db_perform(TABLE_ADDRESS_BOOK, $sql_data_array);

		$address_id = tep_db_insert_id();

		tep_db_query("update " . TABLE_CUSTOMERS . " set customers_default_address_id = '" . (int)$address_id . "' where customers_id = '" . (int)$customer_id . "'");

		tep_db_query("insert into " . TABLE_CUSTOMERS_INFO . " (customers_info_id, customers_info_number_of_logons, customers_info_date_account_created) values ('" . (int)$customer_id . "', '0', now())");

		require(DIR_WS_LANGUAGES . $language . '/' . FILENAME_CREATE_ACCOUNT);

		$email_text = sprintf(EMAIL_GREET_NONE, $first_name) . EMAIL_WELCOME . EMAIL_TEXT;
		$email_text .=  "Username: " . $email . "\nPassword: " . $password . "\n\n";
		$email_text .= EMAIL_CONTACT;

		tep_mail($first_name . " " . $last_name, $email, EMAIL_SUBJECT, $email_text, STORE_OWNER, STORE_OWNER_EMAIL_ADDRESS);
		return $customer_id;
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
* @param string $zip The customers zip code
* @param string $phone The customers phone
* @param string $fax The customers fax
* @param string $email The customers email address
* @return customer id or false
*/
function update_customer($id, $date, $first_name, $last_name, $title, $birth_date, $address1, $address2, $city, $state, $zip, $phone, $fax, $email) {
	global $language;
	$customers_query = tep_db_query("select * from " . TABLE_CUSTOMERS_INFO . " where customers_info_id = '".$id . "'");

	if($customers = tep_db_fetch_array($customers_query)) {	// update
		$last_mod = $customers['customers_info_date_account_last_modified'];
		if(($last_mod != '0000-00-00 00:00:00') && ($last_mod != '') ) {

			$year = (int)substr($last_mod, 0, 4);
			$month = (int)substr($last_mod, 5, 2);
			$day = (int)substr($last_mod, 8, 2);
			$hour = (int)substr($last_mod, 11, 2);
			$minute = (int)substr($last_mod, 14, 2);
			$second = (int)substr($last_mod, 17, 2);
			$last_mod = mktime($hour,$minute,$second,$month,$day,$year) * 1000;
		} else {
			$last_mod = 0;
		}
		if($last_mod < $date) {
			$sql_data_array = array('customers_firstname' => $first_name,
			'customers_lastname' => $last_name,
			'customers_email_address' => $email,
			'customers_telephone' => $phone,
			'customers_fax' => $fax);

			if (ACCOUNT_GENDER == 'true') $sql_data_array['customers_gender'] = ($title == 'M' ? 'm' : 'f');
			if (ACCOUNT_DOB == 'true') $sql_data_array['customers_dob'] = tep_date_raw($birth_date);

			tep_db_perform(TABLE_CUSTOMERS, $sql_data_array, 'update', "customers_id = ".$id);

			$sql_data_array = array(
			'entry_firstname' => $first_name,
			'entry_lastname' => $last_name,
			'entry_street_address' => $address1,
			'entry_postcode' => $zip,
			'entry_city' => $city);

			if (ACCOUNT_GENDER == 'true') $sql_data_array['entry_gender'] = ($title == 'M' ? 'm' : 'f');
			if (ACCOUNT_COMPANY == 'true') $sql_data_array['entry_company'] = '';
			if (ACCOUNT_SUBURB == 'true') $sql_data_array['entry_suburb'] = $address2;

			if (ACCOUNT_STATE == 'true') {
				$zone_id = 0;
				$check_query = tep_db_query("select count(*) as total from " . TABLE_ZONES . " where zone_country_id = '" . (int) STORE_COUNTRY . "'");
				$check = tep_db_fetch_array($check_query);
				$entry_state_has_zones = ($check['total'] > 0);

				if ($entry_state_has_zones == true) {
					$zone_query = tep_db_query("select distinct zone_id from " . TABLE_ZONES . " where zone_country_id = '" . (int) STORE_COUNTRY . "' and (zone_name like '" . tep_db_input($state) . "%' or zone_code like '%" . tep_db_input($state) . "%')");

					if (tep_db_num_rows($zone_query) == 1) {
						$zone = tep_db_fetch_array($zone_query);
						$zone_id = $zone['zone_id'];
					}
				}

				if ($zone_id > 0) {
					$sql_data_array['entry_zone_id'] = $zone_id;
					$sql_data_array['entry_state'] = '';
				} else {
					$sql_data_array['entry_zone_id'] = '0';
					$sql_data_array['entry_state'] = $state;
				}
			}

			tep_db_perform(TABLE_ADDRESS_BOOK, $sql_data_array, 'update', "customers_id = ".$id);
			tep_db_query("update " . TABLE_CUSTOMERS_INFO . " set customers_info_number_of_logons = customers_info_number_of_logons+1, customers_info_date_account_last_modified = now() where customers_info_id =".$id);
		}

		return $id;
	} else { // create a new account
		//Generate a random 8-char password
		$salt = "46z3haZzegmn676PA3rUw2vrkhcLEn2p1c6gf7vp2ny4u3qqfqBh5j6kDhuLmyv9xf";
		srand((double)microtime()*1000000);
		$password = '';

		for ($x = 0; $x < 7; $x++) {
			$num = rand() % 33;
			$tmp = substr($salt, $num, 1);
			$password = $password . $tmp;
		}

		$sql_data_array = array('customers_firstname' => $first_name,
		'customers_lastname' => $last_name,
		'customers_email_address' => $email,
		'customers_telephone' => $phone,
		'customers_fax' => $fax,
		'customers_newsletter' => false,
		'customers_password' => tep_encrypt_password($password));

		if (ACCOUNT_GENDER == 'true') $sql_data_array['customers_gender'] = ($title == 'M' ? 'm' : 'f');
		if (ACCOUNT_DOB == 'true') $sql_data_array['customers_dob'] = tep_date_raw($birth_date);

		tep_db_perform(TABLE_CUSTOMERS, $sql_data_array);

		$customer_id = tep_db_insert_id();


		$sql_data_array = array('customers_id' => $customer_id,
		'entry_firstname' => $first_name,
		'entry_lastname' => $last_name,
		'entry_street_address' => $address1,
		'entry_postcode' => $zip,
		'entry_city' => $city,
		'entry_country_id' => STORE_COUNTRY);

		if (ACCOUNT_GENDER == 'true') $sql_data_array['entry_gender'] = ($title == 'M' ? 'm' : 'f');
		if (ACCOUNT_COMPANY == 'true') $sql_data_array['entry_company'] = '';
		if (ACCOUNT_SUBURB == 'true') $sql_data_array['entry_suburb'] = $address2;

		if (ACCOUNT_STATE == 'true') {
			$zone_id = 0;
			$check_query = tep_db_query("select count(*) as total from " . TABLE_ZONES . " where zone_country_id = '" . (int) STORE_COUNTRY . "'");
			$check = tep_db_fetch_array($check_query);
			$entry_state_has_zones = ($check['total'] > 0);

			if ($entry_state_has_zones == true) {
				$zone_query = tep_db_query("select distinct zone_id from " . TABLE_ZONES . " where zone_country_id = '" . (int) STORE_COUNTRY . "' and (zone_name like '" . tep_db_input($state) . "%' or zone_code like '%" . tep_db_input($state) . "%')");

				if (tep_db_num_rows($zone_query) == 1) {
					$zone = tep_db_fetch_array($zone_query);
					$zone_id = $zone['zone_id'];
				}
			}

			if ($zone_id > 0) {
				$sql_data_array['entry_zone_id'] = $zone_id;
				$sql_data_array['entry_state'] = '';
			} else {
				$sql_data_array['entry_zone_id'] = '0';
				$sql_data_array['entry_state'] = $state;
			}
		}

		tep_db_perform(TABLE_ADDRESS_BOOK, $sql_data_array);

		$address_id = tep_db_insert_id();

		tep_db_query("update " . TABLE_CUSTOMERS . " set customers_default_address_id = '" . (int)$address_id . "' where customers_id = '" . (int)$customer_id . "'");

		tep_db_query("insert into " . TABLE_CUSTOMERS_INFO . " (customers_info_id, customers_info_number_of_logons, customers_info_date_account_created) values ('" . (int)$customer_id . "', '0', now())");

		require(DIR_WS_LANGUAGES . $language . '/' . FILENAME_CREATE_ACCOUNT);

		$email_text = sprintf(EMAIL_GREET_NONE, $first_name) . EMAIL_WELCOME . EMAIL_TEXT;
		$email_text .=  "Username: " . $email . "\nPassword: " . $password . "\n\n";
		$email_text .= EMAIL_CONTACT;

		tep_mail($first_name . " " . $last_name, $email, EMAIL_SUBJECT, $email_text, STORE_OWNER, STORE_OWNER_EMAIL_ADDRESS);
		return $customer_id;
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
* @param string $phone The customers phone
* @param string $fax The customers fax
* @param string $email The customers email address
* @return customers or false
*/
function search_customer($first_name, $last_name, $title, $birth_date, $address1, $address2, $city, $state, $zip, $phone, $fax, $email) {
	$sql = "select c.customers_id, c.customers_firstname, c.customers_lastname, c.customers_gender, c.customers_dob, ab.entry_street_address, ab.entry_suburb, ab.entry_city, ab.entry_state, ab.entry_postcode, c.customers_telephone, c.customers_fax, c.customers_email_address from " . TABLE_CUSTOMERS . " c, " . TABLE_ADDRESS_BOOK . " ab where c.customers_id = ab.customers_id";

	if($first_name) {
		$sql .= " and c.customers_firstname like '%".$first_name."%'";
	}

	if($last_name) {
		$sql .= " and c.customers_lastname like '%".$last_name."%'";
	}

	if($title) {
		$gender = ($title == 'M' ? 'm' : 'f');
		$sql .= " and c.customers_gender = '".$gender."'";
	}

	if($birth_date) {
		$sql .= " and c.customers_dob ='".$birth_date."'";
	}

	if($address1) {
		$sql .= " and ab.entry_street_address like '%".$address1."%'";
	}

	if($address2) {
		$sql .= " and ab.entry_suburb like '%".$address2."%'";
	}

	if($city) {
		$sql .= " and ab.entry_city like '%".$city."%'";
	}

	if($state) {
		$sql .= " and ab.entry_state like '%".$state."%'";
	}

	if($zip) {
		$sql .= " and ab.entry_postcode like '%".$zip."%'";
	}

	if($phone) {
		$sql .= " and ab.entry_telephone like '%".$phone."%'";
	}

	if($fax) {
		$sql .= " and ab.entry_fax like '%".$fax."%'";
	}

	if($email) {
		$sql .= " and ab.entry_email_address like '%".$email."%'";
	}

	$customers_query = tep_db_query($sql);
	$customers_array = null;

	while($customers = tep_db_fetch_array($customers_query)) {

		$customers_array[] = array('id' => $customers['customers_id'],
		'first_name' => $customers['customers_firstname'],
		'last_name' => $customers['customers_lastname'],
		'title' => $customers['customers_gender'] == 'm' ? 'M' : 'F',
		'birth_date' => $customers['customers_dob'],
		'address1' => $customers['entry_street_address'],
		'address2' => $customers['entry_subsurb'],
		'city' => $customers['entry_city'],
		'state' => $customers['entry_state'],
		'zip' => $customers['entry_postcode'],
		'phone' => $customers['customers_telephone'],
		'fax' => $customers['customers_fax'],
		'email' => $customers['customers_email_address']
		);
	}

	return $customers_array;
}

require('includes/application_top.php');

// Authenticate the request
if(isset($_COOKIE['Auth'])) {
	$pieces = explode(':', $_COOKIE['Auth']);
	$username = $pieces[0];
	$password = $pieces[1];
}

if(!authenticate($username, $password)) {
	echo 'Unauthorized: username='.$username.';password='.$password;
	exit;
}

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


require(DIR_WS_INCLUDES . 'application_bottom.php');
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
}
function count_numeric_items(&$array){
	return is_array($array) ? count(array_filter(array_keys($array), 'is_numeric')) : 0;
}
?>
