<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
mysqli_set_charset($con,"utf8");

//안드에서 들어오는 값
$reportUser =$_POST['reportUser'];
$productCode =$_POST['productCode'];
$errorType = $_POST['errorType'];
$amount = $_POST['amount'];

$query = "INSERT INTO product_error (report_code, product_code, error_type, amount) VALUES ('$reportUser','$productCode','$errorType','$amount')";

$res = mysqli_query($con,$query);

mysqli_close($con);
?>