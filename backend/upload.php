<?php


///$target_dir = "../uploads/";
$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
//echo $target_file;
$uploadOk = 1;
$imageFileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));
// Check if image file is a actual image or fake image
if(isset($_POST["submit"])) {
    $check = getimagesize($_FILES["fileToUpload"]["tmp_name"]);
    if($check !== false) {
        //echo "File is an image - " . $check["mime"] . ".";
        $uploadOk = 1;
    } else {
		$response["error"] = TRUE;
	$response["error_msg"] = "File is not an image.";
	echo json_encode($response);
	exit();
        //echo "File is not an image.";
        $uploadOk = 0;
    }
}/*
// Check if file already exists
if (file_exists($target_file)) {
    echo "Sorry, file already exists.";
    $uploadOk = 0;
}*/
// Check file size
if ($_FILES["fileToUpload"]["size"] > 5000000) {
    //echo "Sorry, your file is too large.";
	
		$response["error"] = TRUE;
	$response["error_msg"] = "File is too large.";
	echo json_encode($response);
	exit();
    $uploadOk = 0;
}
// Allow certain file formats
if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg"
&& $imageFileType != "gif" ) {
    //echo "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
    $response["error"] = TRUE;
	$response["error_msg"] = "Only JPG, JPEG, PNG & GIF files are allowed.";
	echo json_encode($response);
	exit();
    
	
	$uploadOk = 0;
}
// Check if $uploadOk is set to 0 by an error
if ($uploadOk == 0) {
    //echo "Sorry, your file was not uploaded.";
	$response["error"] = TRUE;
	$response["error_msg"] = "File was not uploaded.";
	echo json_encode($response);
// if everything is ok, try to upload file
} else {
    if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
        $returnObject['message']= "The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded. ";
		$returnObject['relativePath'] = $target_file;
		//echo json_encode($returnObject);
    } else {
        $response["error"] = TRUE;
		$response["error_msg"] = "File was not uploaded due to filesystem error.";
		echo json_encode($response);
    }
}
?>
