<?php

	$con = mysqli_connect("localhost","kdw98tg","Rlaehddn1!","kdw98tg");
    mysqli_set_charset($con,"utf8");		

	$type = $_POST['type'];	

	$query = "SELECT CONCAT(name,'(',user_code,')') AS name FROM user WHERE position='사원';";
	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["name"] = $row[0];
				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>