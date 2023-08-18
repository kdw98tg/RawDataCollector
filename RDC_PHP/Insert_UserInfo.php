<?php

$con = mysqli_connect("localhost","kdw98tg","Rlaehddn1!","kdw98tg");
mysqli_set_charset($con,"utf8");

//안드에서 들어오는 값
$userCode =$_POST['userCode'];
$userPw =$_POST['userPw'];
$userName =$_POST['userName'];
$userEmail =$_POST['userEmail'];
$userCompany =$_POST['userCompany'];
$userPhoneNumber =$_POST['userPhoneNumber'];
$userProfile =$_POST['userProfile'];
$userPosition =$_POST['position'];

$query = "INSERT INTO user (user_code, pw, name, email, company, phone_number, profile_img, position) 
VALUES ('$userCode','$userPw','$userName','$userEmail','$userCompany','$userPhoneNumber','$userProfile','$userPosition')";

$res = mysqli_query($con,$query);

mysqli_close($con);
?>