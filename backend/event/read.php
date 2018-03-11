<?php


include '../isVolunteerLogin.php';

include '../connection.php';

//echo "This Page exist";


header('Content-type: application/json');
//echo "I am here";

$sql = "SELECT * FROM event";
$result = $con->query($sql);
$myObj = (object)([]);
if ($result->num_rows > 0) {
	
	$returnObj = array();
    // output data of each row
    while($r = $result->fetch_assoc()) {
		$returnObj['eventlist'][] = $r;	
    }
} else {
    echo "0 results";
}

$myJSON = json_encode($returnObj);

echo $myJSON;


?>
