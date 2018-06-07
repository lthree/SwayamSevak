<?php 


//echo "I was here";
// Event Details Image
include '../isVolunteerLogin.php';
include '../connection.php';

header('Content-type: application/json');

if(isset($_POST['idVolunteer']))
{
	$idVolunteer = $_POST['idVolunteer'];

$target_dir = "../volunteer/uploads/";
include '../upload.php';

//echo json_encode($returnObject);
$updateImage  = updateImagePath($target_file,$idVolunteer,$con);

if($updateImage == TRUE)
{
	echo json_encode($returnObject);
}
else{
	$response["error"] = TRUE;
	$response["error_msg"] = "File can't be uploaded to database";
	echo json_encode($response);
}
//echo "\n The target file is ".$target_file;

	




}
else
{
	// required post params is missing
	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameters  missing!";
	echo json_encode($response);
	exit();
}

function updateImagePath($target_file,$idVolunteer,$con)
{
	$sql = "UPDATE volunteer set photo='".$target_file."' where idVolunteer=".$idVolunteer."";
	//var_dump($con);
	//var_dump($sql);
	//die;
        $result = $con->query($sql);
		//var_dump($result);
		//die;
		return $result;
		/*if ($result) {
			return true;
        } else {
            return false;
        }*/
}



?>