<?php

include 'connection.php'

$query = "UPDATE `testdb`.`admin` SET `idAdmin` = <{idAdmin: }>, `userName` = <{userName: }>, `name` = <{name: }>, `dateOfBirth` = <{dateOfBirth: }>, `sex` = <{sex: }>, `aadharNumber` = <{aadharNumber: }>, `mobileNumber` = <{mobileNumber: }>, `address` = <{address: }>, `photo` = <{photo: }>, `password` = <{password: }> WHERE `idAdmin` = <{expr}>";


if ($stmt = $con->prepare($query)) {
    $stmt->execute();
    $stmt->bind_result($field1, $field2);
    while ($stmt->fetch()) {
        //printf("%s, %s\n", $field1, $field2);
    }
    $stmt->close();
}

?>
