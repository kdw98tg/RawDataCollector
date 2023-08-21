<?php

$con = mysqli_connect("localhost","kdw98tg","Rlaehddn1!","kdw98tg");
mysqli_set_charset($con,"utf8");

//안드에서 들어오는 값
$userCode =$_POST['userCode'];
$curDate =$_POST['curDate'];
$curTime =$_POST['curTime'];

$query = "INSERT INTO attendance (user_code, date, start_time) VALUES ('$userCode','$curDate','$curTime')";

$res = mysqli_query($con,$query);

mysqli_close($con);
?>