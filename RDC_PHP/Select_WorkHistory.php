<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
    mysqli_set_charset($con,"utf8");		

	$userCode = $_POST['userCode'];
    $selectedDate = $_POST['selectedDate'];

	$query = "SELECT product_code, done_amount FROM producing WHERE accept_user ='$userCode' AND work_date = '$selectedDate'";
	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["product_code"] = $row[0];
				$rows["done_amount"]= $row[1];
						
				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>