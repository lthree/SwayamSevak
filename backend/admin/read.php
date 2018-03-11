<?php

include '../connection.php';
header('Content-type: application/json');
//echo "I am here";
$sql = "SELECT idAdmin, name,userName FROM admin";
//$sql = "SELECT * FROM admin";
$result = $con->query($sql);
$myObj = (object)([]);
if ($result->num_rows > 0) {
	
	$returnObj = array();
    // output data of each row
    while($r = $result->fetch_assoc()) {
		$returnObj['adminlist'][] = $r;	
    }
} else {
    echo "0 results";
}

$myJSON = json_encode($returnObj);

echo $myJSON;


?>
