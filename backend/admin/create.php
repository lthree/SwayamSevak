<?php

//$con->close();

include 'connection.php';

$query = "CREATE TABLE `donor` (   `idDonor` int(11) NOT NULL AUTO_INCREMENT,   `userName` varchar(45) DEFAULT NULL,   `name` varchar(45) NOT NULL,   `dateOfBirth` datetime NOT NULL,   `sex` varchar(2) NOT NULL,   `aadharNumber` decimal(12,0) NOT NULL,   `mobileNumber` decimal(12,0) NOT NULL,   `address` varchar(100) NOT NULL,   `photo` blob NOT NULL,   `password` varchar(45) NOT NULL,   PRIMARY KEY (`idDonor`) ) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='\n\n'";


if ($stmt = $con->prepare($query)) {
    $stmt->execute();
    $stmt->bind_result($field1, $field2);
    while ($stmt->fetch()) {
        //printf("%s, %s\n", $field1, $field2);
    }
    $stmt->close();
}

?>
