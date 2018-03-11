<?php

include 'connection.php';

$query = "INSERT INTO `testdb`.`admin` (`idAdmin`, `userName`, `name`, `dateOfBirth`, `sex`, `aadharNumber`, `mobileNumber`, `address`, `photo`, `password`) VALUES (<{idAdmin: }>, <{userName: }>, <{name: }>, <{dateOfBirth: }>, <{sex: }>, <{aadharNumber: }>, <{mobileNumber: }>, <{address: }>, <{photo: }>, <{password: }>)";


if ($stmt = $con->prepare($query)) {
    $stmt->execute();
    $stmt->bind_result($field1, $field2);
    while ($stmt->fetch()) {
        //printf("%s, %s\n", $field1, $field2);
    }
    $stmt->close();
}

?>
