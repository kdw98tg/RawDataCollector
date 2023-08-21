<?php

$con = mysqli_connect("localhost","kdw98tg","Rlaehddn1!","kdw98tg");
mysqli_set_charset($con,"utf8");

//안드에서 들어오는 값
$userCode =$_POST['userCode'];
$curDate =$_POST['curDate'];
$curTime =$_POST['curTime'];

$query = "UPDATE attendance SET end_time = '$curTime' WHERE user_code = '$userCode' AND date ='$curDate'";

$res = mysqli_query($con,$query);

mysqli_close($con);
?>