<?php

	$con = mysqli_connect("localhost","kdw98tg","Rlaehddn1!","kdw98tg");
    mysqli_set_charset($con,"utf8");		

	$toolCode = $_POST["toolCode"];

	$query = "SELECT producing.work_date AS date ,tool.serial_number AS serial_number, equipment_code ,done_amount
    FROM producing Join tool ON producing.tool_code = tool.tool_code WHERE producing.tool_code = '$toolCode';
";
	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["date"] = $row[0];
				$rows["serial_number"] = $row[1];
				$rows["equipment_code"] = $row[2];
				$rows["done_amount"] = $row[3];
				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>