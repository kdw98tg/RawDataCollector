<?php

	$con = mysqli_connect("localhost","kdw98tg","Rlaehddn1!","kdw98tg");
    mysqli_set_charset($con,"utf8");		

	$userCode = $_POST['userCode'];	

	$query = "SELECT * FROM user WHERE user_code = '$userCode'";
	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["user_code"] = $row[0];
				$rows["pw"]= $row[1];
				$rows["name"]= $row[2];
				$rows["email"] = $row[3];
				$rows["company"] = $row[4];
				$rows["phone_number"] = $row[5];
				$rows["profile_img"] = $row[6];
				$rows["position"] = $row[7];
				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>