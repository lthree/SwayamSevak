<?php

include '../connection.php';
//echo "This Page exist";

if (isset($_POST['idVolunteer'])) {
	$idVolunteer = $_POST['idVolunteer'];
	//echo $idVolunteer;
	if(setLogout($idVolunteer,$con)){
		$response["error"] = False;
		$response["error_msg"] = "Logout Successfully!";
		echo json_encode($response);
	}else{
		$response["error"] = True;
		$response["error_msg"] = "Unable to login now due to database error";
		echo json_encode($response);
	}
}else{
	// required post params is missing
	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameters email or password is missing!";
	echo json_encode($response);
}

function setLogout($idVolunteer,$con){
	$sql = "UPDATE volunteer SET loginToken = NULL, isOnline = 0 WHERE idVolunteer='$idVolunteer'";
	//echo $sql;
	if ($con->query($sql) === TRUE) {
		//echo "Logout successfully";
		return true;
   } else {
    //echo "Error in Logout: " . $con->error;
	return false;
	
	
}
}

?>