<?php 
include '../connection.php';
include '../isVolunteerLogin.php';
//echo "Page is there yes";

header('Content-type: application/json');

if (isset($_POST['locationQuery'])) {
	$locationQuery = $_POST['locationQuery'];
	//echo $locationQuery;
	///// Fetch list from database
	$sql = "SELECT * FROM Places";
	$result = $con->query($sql);
	//$myObj = (object)([]);
	if ($result->num_rows > 0) {
		$dbPlacelistAll = array();
		// output data of each row
		while($r = $result->fetch_assoc()) {
			$dbPlacelistAll['placelist'][] = $r;	
		}
	} 
	//echo json_encode($dbPlacelistAll['placelist']);
	
	//echo "\nHi Hello \n";
	//print_r($dbPlacelistAll);
	
	$arr=json_decode($locationQuery,true);
	///echo $arr['placelist'][0]['Latitude'];
	
	$returnObj = array();
	
	foreach($arr['placelist'] as $queryPlaceList ){
		//echo "\nQuery list".$queryPlaceList['Latitude']."and".$queryPlaceList['Longitude'];
		//print_r($dbPlacelistAll['placelist']);die;
		foreach($dbPlacelistAll['placelist'] as $key => $dbPlacelist) {
			//echo "\n Key is here ".$key;
		//var_dump($dbPlacelistAll);die;
			//echo $dbPlaceList['Latitude'];die;
			//echo "\nDB List".$dbPlaceListAll['placelist'][$key]['Latitude']."and".$dbPlaceListAll['placelist'][$key]['Longitude'];
			if($queryPlaceList['Latitude'] == $dbPlacelist['Latitude'] and $queryPlaceList['Longitude'] == $dbPlacelist['Longitude'])
			{
				$returnObj['placeStatus'][] = $dbPlacelist;
			}	
	}
	
	}
	
	
	echo json_encode($returnObj);
	echo "\n";



}else{
	// required post params is missing
	$response["error"] = TRUE;
	$response["error_msg"] = "Required parameters  missing!";
	echo json_encode($response);
	exit();
	
}


?>