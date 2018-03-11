<?php

  
include '../connection.php';
//echo "This Page exist";

if (isset($_POST['name']) && isset($_POST['adhaar']) && isset($_POST['email']) && isset($_POST['password'])) {
 
    // receiving the post params
    $name = $_POST['name'];
	$adhaar = $_POST['adhaar'];
    $email = (string)$_POST['email'];
    $password = $_POST['password'];
   //echo $email;
    // check if user is already existed with the same email
    if (isUserExisted($email,$con)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email;
        echo json_encode($response);
		
    } else {
        // create a new user
        $user = storeUser($name, $adhaar, $email, $password,$con);
        
		if ($user) {
            // user stored successfully
			//echo "Yahan tak toaa hi gaye;";
            $response["error"] = FALSE;
        
            $response["user"]["name"] = $name;
            $response["user"]["email"] = $email;
            
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, adhaar, email or password) is missing!";
    echo json_encode($response);
}











function storeUser($name, $adhaar, $email, $password,$con) {
 
        $stmt = $con->prepare("INSERT INTO volunteer(idVolunteer,userName,name,dateOfBirth,sex,aadharNumber,mobileNumber,address,photo,password,validBit,email) VALUES (NULL, NULL, ?, NULL, NULL, ?, NULL, NULL, NULL,?, b'1',?);");
        $stmt->bind_param("siss",$name, $adhaar,$password, $email);
        
		$result = $stmt->execute();
	
        $stmt->close();
 
        if ($result) {
			return true;
        } else {
            return false;
        }
  }
  function isUserExisted($email,$con) {
	
	
		$sql = "SELECT email FROM volunteer WHERE email = '".$email."'";
        
		//echo $sql;
		$result = $con->query($sql);
        if ($result->num_rows > 0) {
            // user existed
            return true;
        } else {
            // user not existed
            return false;
        }
    }
  

?>