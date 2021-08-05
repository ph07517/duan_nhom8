<?php
$host ="localhost";
$usename ="root";
$password = "";
$database = "thietbi";

$conn = mysqli_connect($host,$usename,$password,$database);
mysqli_query($conn,"SET NAMES 'utf8'");
?>