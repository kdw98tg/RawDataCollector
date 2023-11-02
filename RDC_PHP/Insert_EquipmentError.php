<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
mysqli_set_charset($con,"utf8");

//안드에서 들어오는 값
$reportUser =$_POST['reportUser'];
$equipmentCode =$_POST['equipmentCode'];
$errorType = $_POST['errorType'];
$stopTime = $_POST['stoppedTime'];
$restartTime = $_POST['restartTime'];

$query = "INSERT INTO equipment_error (report_code, equipment_code, error_type, stop_time, restart_time) 
VALUES ('$reportUser','$equipmentCode','$errorType','$stopTime','$restartTime')";
// $query = "INSERT INTO equipment_error (report_code, equipment_code, error_type, stop_time, restart_time) 
// VALUES ('$reportUser','$equipmentCode','$errorType','1000-01-01 00:00:00','1000-01-01 00:00:00')";

$res = mysqli_query($con,$query);

mysqli_close($con);
?>