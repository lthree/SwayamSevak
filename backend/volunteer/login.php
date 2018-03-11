<?php 


include '../connection.php';
//echo "This Page exist";

// json response array
$response = array("error" => FALSE);

if (isset($_POST['email']) && isset($_POST['password'])) {

// receiving the post params
	$email = $_POST['email'];
	$password = $_POST['password'];

// get the user by email and password
	$user = isUserCorrect($email,$password,$con);

	if ($user != false) {
	// user is found
	    $loginToken = (string)(date("Y-m-d H:i:s")).($user["idVolunteer"]);
		//echo $loginToken;
		if(setOnline($user["idVolunteer"],$loginToken,$con)){
		$response["error"] = FALSE;
		$response["user"]["idVolunteer"] = $user["idVolunteer"];
		$response["user"]["name"] = $user["name"];
		$response["user"]["loginToken"] = $loginToken;
		$response["user"]["email"] = $user["email"];
		echo json_encode($response);
		//session_start();
		}else{
			$response["error"] = TRUE;
		$response["error_msg"] = "Unable to login now due to database error";
		echo json_encode($response);
			
		}
	
	} else {
	// user is not found with the credentials
		$response["error"] = TRUE;
		$response["error_msg"] = "Login credentials are wrong. Please try again!";
		echo json_encode($response);
	}
} else {
// required post params is missing
	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameters email or password is missing!";
	echo json_encode($response);
}


function setOnline($idVolunteer,$loginToken,$con){
	$sql = "UPDATE volunteer SET loginToken = '$loginToken', isOnline = 1 WHERE idVolunteer='$idVolunteer'";
	//echo $sql;
	if ($con->query($sql) === TRUE) {
		//echo "Record updated successfully";
		return true;
   } else {
    //echo "Error updating record: " . $con->error;
	return false;
}

	
}




function isUserCorrect($email,$password,$con) {
	
	
		$sql = "SELECT idVolunteer,name,email,password FROM volunteer WHERE email = '".$email."'";
        
		//echo $sql;
		$result = $con->query($sql);
        if ($result->num_rows > 0) {
            // user existed
			$r = $result->fetch_assoc();
			if($r['password']==$password){
				
				return $r;
			}else{
				//wrong password
				return false;
			}
        } else {
            // user not existed
            return false;
        }
    }
  
?>