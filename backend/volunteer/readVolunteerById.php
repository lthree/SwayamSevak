<?php


include '../isVolunteerLogin.php';

include '../connection.php';

//echo "This Page exist";
header('Content-type: application/json');

if (isset($_POST['idVolunteer'])) {
	$idVolunteer = $_POST['idVolunteer'];
	

//echo "I am here";

$sql = "SELECT * FROM volunteer WHERE idVolunteer = ".$idVolunteer;
//echo "\n".$sql;
$result = $con->query($sql);
$myObj = (object)([]);
if ($result->num_rows > 0) {
	
	$returnObj = array();
    // output data of each row
    while($r = $result->fetch_assoc()) {
		$returnObj['volunteerDetails'][] = $r;	
    }
} else {
    echo "0 results";
}

$myJSON = json_encode($returnObj);

echo $myJSON;

	
}else{
	// required post params is missing
	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameters  missing!";
	echo json_encode($response);
	exit();
	
}




?>
