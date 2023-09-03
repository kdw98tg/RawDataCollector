<?php

	$con = mysqli_connect("localhost","kdw98tg","Rlaehddn1!","kdw98tg");
    mysqli_set_charset($con,"utf8");		

	$acceptUser =$_POST['acceptUser'];
	$workDate =$_POST['workDate'];
	$productCode =$_POST['productCode'];

	$query = "SELECT work_end_time FROM producing  WHERE accept_user='$acceptUser' AND work_date ='$workDate' AND product_code='$productCode' ";
	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["work_end_time"] = $row[0];
				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>