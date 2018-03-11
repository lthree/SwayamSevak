<?php

//$con->close();

include 'connection.php';


$query = "CREATE TABLE `topadmin` (   `idAdmin` int(11) NOT NULL AUTO_INCREMENT,   `userName` varchar(45) DEFAULT NULL,   `name` varchar(45) DEFAULT NULL,   `dateOfBirth` datetime DEFAULT NULL,   `sex` varchar(2) DEFAULT NULL,   `aadharNumber` decimal(12,0) DEFAULT NULL,   `mobileNumber` decimal(12,0) DEFAULT NULL,   `address` varchar(100) DEFAULT NULL,   `Admincol` varchar(45) DEFAULT NULL,   `photo` blob,   PRIMARY KEY (`idAdmin`) ) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='\n\n'";


if ($stmt = $con->prepare($query)) {
    $stmt->execute();
    $stmt->bind_result($field1, $field2);
    while ($stmt->fetch()) {
        //printf("%s, %s\n", $field1, $field2);
    }
    $stmt->close();
}

?>
