<?php
require "connect.php";

$sql = "select * from product";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,array('product_name'=>$row[1],
'product_price'=>$row[2],
'product_description'=>$row[3],
'product_picture'=>$row[4]

));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>