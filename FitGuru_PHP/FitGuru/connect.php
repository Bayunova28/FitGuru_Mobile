<?php
$host = "localhost";
$user = "root";
$password =  "";
$db = "fitguru";

$con = mysqli_connect($host,$user,$password,$db);

if(!$con)
{
    die("Error in connection " .mysqli_connect_error());
}
else
{
echo" " ;

}
?>