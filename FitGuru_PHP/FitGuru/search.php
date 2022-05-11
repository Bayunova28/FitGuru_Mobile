<?php

define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','fitguru');

 
$con = mysqli_connect(HOST,USER,PASS,DB);
$username  = $_GET['username'];
 
$sql = "select * from users where username = '$username'";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,array(
'username'=>$row[2],
'fullname'=>$row[1],
'password'=>$row[3],
'email'=>$row[4],
'phone'=>$row[5],
'address'=>$row[6],
'weight'=>$row[7]
));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>