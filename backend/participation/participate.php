<?php

include '../isVoluteerLogin.php';
include '../connection.php';

//echo "I am here";
if(isset($_POST['idEvent']) && isset($_POST['idVolunteer']))
{
	$idEvent = $_POST['idEvent'];
	$idVolunteer = $_POST['idVolunteer'];

	$sql = "insert into participation(idVolunteer, idEvent, approvedBit,isActive) values(".$idVolunteer.",".$idEvent.",b'1',1)";
	//echo $sql;
	
	$result = $con ->query($sql);
	if($result)
	{
		$response["error"] = False;
		$response["error_msg"] = "Participated Successfully!";
		echo json_encode($response);
	}
	else{
	$response["error"] = True;
		$response["error_msg"] = "Database Error!";
		echo json_encode($response);
	}
}
else 
{
$response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, adhaar, email or password) is missing!";
    echo json_encode($response);
}

?>