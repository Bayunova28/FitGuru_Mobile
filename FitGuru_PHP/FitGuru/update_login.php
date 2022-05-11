<?php
require "connect.php";

 if(isset($_POST['username'])){
    $username = $_POST["username"];

        $q=mysqli_query($con,"UPDATE login_information set username='$username' WHERE id='1'");
        $response=array();

        if($q){
            $response["success"]=1;
            $response["message"]="Data berhasil diupdate";
            echo json_encode($response);
        }else{
            $response["success"]=0;
            $response["message"]="data gagal diupdate";
            echo json_encode($response);
        }
    }else{
        $response["success"]=-1;
        $response["message"]="Data kosong";
        echo json_encode($response);
    }
?>