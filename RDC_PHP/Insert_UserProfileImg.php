<?php
require_once "dbDetails.php";

$con = mysqli_connect(HOST,USER,PASS,DB);
mysqli_set_charset($con,"utf8");

header("Content-Type:text/html;charset=utf-8");

$userCode = $_POST['userCode'];

$serverDir = $_POST['serverDir'];

str_replace("\"", "", $userCode);
str_replace("\"", "", $serverDir);

// 파일 받기
$profile_File =$_FILES['uploaded_file']['name'];

// 저장할 경로

$file_name =$_SERVER['DOCUMENT_ROOT']."/RDC/Profile/UserProfile";//업로드된 파일의 이름을 가져옴
$tempData = $_FILES['uploaded_file']['tmp_name'];
$name = basename($_FILES["uploaded_file"]['name']);

$query ="UPDATE user SET profile_img = '$serverDir' WHERE user_code = '$userCode' ";


// // 임시폴더에서  ->  경로 이동 .파일이름bin

//echo ($userId);

$file_path = $file_name."/".$name.".jpg";

//만약 $profile_File 변수가 설정되어 있다면 실행
//$profile_File 변수가 설정되어 있다면 실행
//isset() 함수는 변수가 설정되어 있고 null이 아닐경우 true를 반환
//따라서 이 조건은 파일이 업로드 되었는 지 확인함
if(isset($profile_File)){

    //만약 move_uploaded_file() 함수가 성공적으로 파일을 이동시키면 실행
    //move_uploaded_file() 함수는 업로드된 파일을 임시 디렉토리에서 지정한 경로로 옮김
    //$tempData는 업로드된 파일의 임시 경로를 나타내며, $file_path는 파일을 저장할 경로를 나타냄
    //이 부분은 파일 업로드가 성공했을 때 실행되며, "완료"라는 문자열을 출력
 if(move_uploaded_file($tempData, $file_path))
 {
    //업로드도 됐고, 파일 이동도 완료됨
  echo "완료";
 }
 else
 {
    //파일이동 실패
  echo "실패";
 }
}
else{
    //$profile_File 변수가 설정되어 있지 않다면 실행함
    //이 조건은 파일이 업로드 되지 않았을 때 실행됨
    //파일 없음 문자열을 출력
 echo "파일없음";
}
mysqli_query($con,$query);

mysqli_close($con);

 ?>
