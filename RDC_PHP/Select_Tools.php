<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
    mysqli_set_charset($con,"utf8");		

	$type = $_POST['type'];	

	$query = "SELECT tool_code FROM tool";
	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["tool_code"] = $row[0];
				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>