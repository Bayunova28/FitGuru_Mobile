<?php
require "connect.php";

$rate = isset($_POST["rate"]) ? $_POST["rate"] : '';;
$comment = isset($_POST["comment"]) ? $_POST["comment"] : '';;

$sql = "insert into rating  (rate, comment) values ('$rate','$comment');";

if(mysqli_query($con,$sql))
{

$response["success"] = 1;
$response["message"] = "Data berhasil ditambah";
echo json_encode($response);

}
else
{
$response["success"] = 0;
$response["message"] = "Data gagal ditambah";
echo json_encode($response); ;

}
?>