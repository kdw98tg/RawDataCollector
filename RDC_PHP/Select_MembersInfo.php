<?php

	$con = mysqli_connect("localhost","kdw98tg","Rlaehddn1!","kdw98tg");
    mysqli_set_charset($con,"utf8");		

	$company = $_POST['company'];	


	$query = "SELECT user_code, name, email, phone_number,profile_img,position,wage  FROM user 
	WHERE company = '$company' 
	ORDER BY position";
	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["user_code"] = $row[0];
				$rows["name"]= $row[1];
				$rows["email"] = $row[2];
				$rows["phone_number"] = $row[3];
				$rows["profile_img"] = $row[4];
				$rows["position"] = $row[5];
				$rows["wage"] = $row[6];
				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>