<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
    mysqli_set_charset($con,"utf8");		

	$type = $_POST['type'];	

	$query = "SELECT * FROM tool";
	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["tool_code"] = $row[0];
				$rows["tool_name"] = $row[1];
				$rows["serial_number"] = $row[2];
				$rows["uses_amount"] = $row[3];
				$rows["tool_image"] = $row[4];
				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>