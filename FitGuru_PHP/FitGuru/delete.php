<?php
require "connect.php";

if(isset($_POST['username'])){
    $username=$_POST['username'];

    $q=mysqli_query($con,"DELETE FROM users WHERE username='$username'");
    $response=array();

    if($q){
        $response["success"]=1;
        $response["message"]="Data berhasil delete";
        echo json_encode($response);
    }else{
        $response["success"]=0;
        $response["message"]="data gagal di hapus";
        echo json_encode($response);
    }
}else{
    $response["success"]=-1;
    $response["message"]="Data kosong";
    echo json_encode($response);
}
?>