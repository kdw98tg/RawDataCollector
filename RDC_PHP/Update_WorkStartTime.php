<?php

$con = mysqli_connect("localhost","kdw98tg","Rlaehddn1!","kdw98tg");
mysqli_set_charset($con,"utf8");

//안드에서 들어오는 값
$acceptUser =$_POST['acceptUser'];
$workStartTime =$_POST['workStartTime'];
$workDate =$_POST['workDate'];
$productCode =$_POST['productCode'];


$query = "UPDATE producing SET work_start_time ='$workStartTime' WHERE accept_user='$acceptUser' AND work_date ='$workDate' AND product_code='$productCode'";

$res = mysqli_query($con,$query);

mysqli_close($con);
?>