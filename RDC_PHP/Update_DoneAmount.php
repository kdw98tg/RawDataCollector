<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
mysqli_set_charset($con,"utf8");

//안드에서 들어오는 값
$acceptUser =$_POST['acceptUser'];
$doneAmount =$_POST['doneAmount'];
$workDate =$_POST['workDate'];
$productCode =$_POST['productCode'];


$query = "UPDATE producing SET done_amount ='$doneAmount' WHERE accept_user='$acceptUser' AND work_date ='$workDate' AND product_code='$productCode'";

$res = mysqli_query($con,$query);

mysqli_close($con);
?>