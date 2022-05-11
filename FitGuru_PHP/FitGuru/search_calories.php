<?php

define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','fitguru');

 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$sql = "select * from Calories where id = '1'";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,array(
'calories'=>$row[1]
));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>