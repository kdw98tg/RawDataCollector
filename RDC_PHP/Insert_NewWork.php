<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
mysqli_set_charset($con,"utf8");

//안드에서 들어오는 값
$requestUser =$_POST['requestUser'];
$acceptUser =$_POST['acceptUser'];
$workDate = $_POST['workDate'];
$equipmentCode = $_POST['equipment'];
$productCode = $_POST['product'];
$toolCode = $_POST["toolCode"];
$requestAmount=$_POST['amount'];
$process=$_POST['process'];

$query = "INSERT INTO producing (request_user, accept_user, work_date,equipment_code,product_code,tool_code, request_amount,process) 
VALUES ('$requestUser','$acceptUser','$workDate','$equipmentCode','$productCode','$toolCode','$requestAmount','$process')";


$res = mysqli_query($con,$query);

mysqli_close($con);
?>