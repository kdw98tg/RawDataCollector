<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
mysqli_set_charset($con,"utf8");

header("Content-Type:text/html;charset=utf-8");

//userCode가 서버DIR로 바로 사용될것임
$userCode = $_POST['userCode'];

//$serverDir = $_POST['serverDir'];

//$filePath = $_POST['filePath'];

// 파일 받기
$profile_File =$_FILES['uploaded_file']['name'];
//echo $profile_File;
// 저장할 경로

$file_name =$_SERVER['DOCUMENT_ROOT']."/RDC/Profile/UserProfile";//업로드된 파일의 이름을 가져옴
$tempData = $_FILES['uploaded_file']['tmp_name'];
$name = basename($_FILES["uploaded_file"]['name']);

$serverDir = $name.".jpg";//서버에 올라갈 파일명, 디비에 저장될 파일명과 일치

//userCode를 그냥 바로 서버 DIR로 사용할거임
$query ="UPDATE user SET profile_img = '$serverDir' WHERE user_code = '$userCode'";
//echo $query;
// // 임시폴더에서  ->  경로 이동 .파일이름bin

//echo ($userId);

$file_path = $file_name."/".$serverDir;
//echo $file_name;
//echo "file_path = " . $file_path;
if(isset($profile_File)){
 if(move_uploaded_file($tempData, $file_path))
 //if(move_uploaded_file($tempData, "/kdw98tg/www/RDC/Test.jpg"))//되는 코드임
 {
  echo "완료";
 }
 else
 {
  echo "실패";
 }
}
else{
 echo "파일없음";
}
mysqli_query($con,$query);

mysqli_close($con);

 ?>
