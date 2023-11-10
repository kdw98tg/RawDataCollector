<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
mysqli_set_charset($con,"utf8");

//안드에서 들어오는 값
$serverDir =$_POST['serverDir'];
$file_name =$_SERVER['DOCUMENT_ROOT']."/RDC/Profile/".$serverDir;
//$file_name =$_SERVER['DOCUMENT_ROOT']."/RDC/Profile/EquipmentProfile/12123.jpg";

//$query = "UPDATE attendance SET end_time = '$curDateTime' WHERE user_code = '$userCode'";
unlink($file_name);

echo $file_name;
$res = mysqli_query($con,$query);

mysqli_close($con);
?>