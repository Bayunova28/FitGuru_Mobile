<?php
require "connect.php";

 if(isset($_POST['username'])&& isset($_POST['fullname'])&& isset($_POST['password'])&& isset($_POST['email']) && isset($_POST['phone']) && isset($_POST['address']) && isset($_POST['weight'])){
    $username = $_POST["username"];
	$fullname = $_POST["fullname"];
	$password = $_POST["password"];
	$email = $_POST["email"];
	$phone = $_POST["phone"];
	$address = $_POST["address"];
	$weight = $_POST["weight"];

        $q=mysqli_query($con,"UPDATE users set fullname='$fullname', password = '$password', email = '$email', phone = '$phone', address = '$address', weight = '$weight' WHERE username='$username'");
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
