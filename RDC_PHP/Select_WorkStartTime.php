<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
    mysqli_set_charset($con,"utf8");		

	$acceptUser =$_POST['acceptUser'];
	$workDate =$_POST['workDate'];
	$productCode =$_POST['productCode'];

	$query = "SELECT work_start_time FROM producing  WHERE accept_user='$acceptUser' AND work_date ='$workDate' AND product_code='$productCode' ";
	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["work_start_time"] = $row[0];
				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>