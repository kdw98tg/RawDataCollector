<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
mysqli_set_charset($con,"utf8");

//안드에서 들어오는 값
$userCode =$_POST['userCode'];
$curDateTime =$_POST['curDateTime'];
//$curTime =$_POST['curTime'];

$query = "INSERT INTO attendance (user_code, start_time) VALUES ('$userCode','$curDateTime')";

$res = mysqli_query($con,$query);

mysqli_close($con);
?>