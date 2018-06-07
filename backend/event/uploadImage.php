<?php 


//echo "I was here";
// Event Details Image
include '../isVolunteerLogin.php';
include '../connection.php';

//header('Content-type: application/json');

if(isset($_POST['idEvent']))
{
	$idEvent = $_POST['idEvent'];
$target_dir = "../event/uploads/";
include '../upload.php';

//echo json_encode($returnObject);
$updateImage  = updateImagePath($target_file,$idEvent,$con);

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

function updateImagePath($target_file,$idEvent,$con)
{
		/*$stmt = $con->prepare("UPDATE events set photo=? where idEvent=?");
        $stmt->bind_param("si",$target_file, $idEvent);
        
		$result = $stmt->execute();
	*/
	$sql = "UPDATE event set photo='".$target_file."' where idEvent=".$idEvent."";
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