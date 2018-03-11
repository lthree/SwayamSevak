<?php
$host="127.0.0.1";
$port=3306;
$socket="";
$user="mumbaihack";
$password="mumbaihack";
$dbname="shaleentestdb";

$con = new mysqli($host, $user, $password, $dbname, $port, $socket)
	or die ('Could not connect to the database server' . mysqli_connect_error());
if($con){
//echo "Connected";
}
?>
