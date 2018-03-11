<?php

include 'connection.php';
//echo "This Page exist";



public function isLogin(){
	if (isset($_POST['idVolunteer']) && isset($_POST['loginToken'])) {
	$idVolunteer = $_POST['idVolunteer'];
	loginToken = $_POST['loginToken'];
	//echo $idVolunteer, $loginToken;
	$sql = "Select idVolunteer from volunteer where isOnline = 1 and loginToken ='".$loginToken."' and idVolunteer =$idVolunteer";
	echo "\n".$sql."\n";
	if($con){ 
	echo "Connection exist";}
	$result = $con->query($sql);
	
	//echo json_encode($result);
        if ($result->num_rows > 0) {
            // user existed
				echo "True it is";
				$returnStatus = true;
			}
         else {
            // user not existed
            $returnStatus  false;
        }

	if($returnStatus ==false){
		echo "All Not Good";
		$response["error"] = True;
		$response["error_msg"] = "Login failed please re-login";
		echo json_encode($response);
		return false;
	}
	else{
		return true;
		$con->close();
	}
	}else{
	// required post params is missing
	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameters  missing!";
	echo json_encode($response);
	return false;
	
}
	}
echo "Sab Sahi Saat";

?>