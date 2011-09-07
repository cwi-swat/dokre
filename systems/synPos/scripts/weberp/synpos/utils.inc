<?php
function create_branchcode($debtorno, $first_name, $last_name, $max_len = 10, $min_len = 1) {
	global $db;

	if($max_len < $min_len || $min_len < 1) {
		return false;
	}

	$code = $last_name.$first_name;

	while(strlen($code) > $max_len) {
		$code = substr($code, 0, strlen($code) - 1);
	}

	while(true) {
		$sql ="SELECT branchcode FROM custbranch WHERE debtorno = '".$_SESSION['CustomerID']."' AND branchcode LIKE '".$code."%'";
		$result = DB_query($sql, $db);

		while($myrow = DB_fetch_array($result)) {
			$codes[] = $myrow['branchcode'];
		}

		if($codes) {
			$i = 1;

			while(in_array($code.$i, $codes) || strlen($code.$i) < $min_len) {
				$i++;
			}

			if(strlen($code . $i) > $max_len) {
				if($code != '') {
					$code = substr($code, 0, strlen($code) - 1);
				} else {
					return false;
				}
			} else {
				retrun ($code . $i);
			}
		} else {
			return $code;
		}
	}
}
?>