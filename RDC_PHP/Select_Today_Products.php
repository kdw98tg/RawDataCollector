<?php

	$con = mysqli_connect("localhost","kdw98tg","Rlaehddn1!","kdw98tg");
    mysqli_set_charset($con,"utf8");		

	$userCode = $_POST['userCode'];
    $curDate = $_POST['curDate'];

	$query = "SELECT product.product_name, product.product_code, request_user.name AS requester, accept_user.name AS accepter,  product.product_image,equipment_code, process
	FROM producing
	INNER JOIN product ON producing.product_code = product.product_code
	INNER JOIN user AS request_user ON producing.request_user = request_user.user_code
	INNER JOIN user AS accept_user ON producing.accept_user = accept_user.user_code
	WHERE producing.work_date = '$curDate' AND producing.accept_user = '$userCode' ";
	 
	//echo $query;
	
	$res = mysqli_query($con, $query);	
		
		$rows = array();
		$result = array();

		while($row = mysqli_fetch_array($res))
		{	
				$rows["product_name"] = $row[0];
				$rows["product_code"]= $row[1];
				$rows["request_user"]= $row[2];
				$rows["accept_user"] = $row[3];
				$rows["product_image"] = $row[4];
				$rows["equipment_code"] = $row[5];
				$rows["process"] = $row[6];

				array_push($result, $rows);
		}
        echo json_encode(array("results"=>$result));	
	
	mysqli_close($con);	

?>