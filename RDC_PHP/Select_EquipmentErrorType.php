<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
    mysqli_set_charset($con,"utf8");		

	$query = "SELECT error_name FROM equipment_error_types";
	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["error_name"] = $row[0];
				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>