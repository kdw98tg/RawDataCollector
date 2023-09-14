<?php

	$con = mysqli_connect("localhost","kdw98tg","Rlaehddn1!","kdw98tg");
    mysqli_set_charset($con,"utf8");		

	$userCode = $_POST["userCode"];

	$query = "SELECT SUM(FLOOR(TIME_TO_SEC(TIMEDIFF(end_time,start_time))/3600)) as total_work_time  FROM attendance WHERE user_code ='$userCode'";

	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["total_work_time"] = $row[0];

				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>