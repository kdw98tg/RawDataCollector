<?php

	$con = mysqli_connect("localhost","kdw98tg","Rlaehddn1!","kdw98tg");
    mysqli_set_charset($con,"utf8");		

	$type = $_POST['type'];	

	//$query = "SELECT product_name FROM product";
	$query = "SELECT *  FROM product";
	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["product_code"] = $row[0];
				$rows["product_name"] = $row[1];
				$rows["product_image"] = $row[2];
				$rows["customer"] = $row[3];
				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>