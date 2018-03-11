<?php

include 'connection.php';


$query = "DELETE FROM `testdb`.`admin` WHERE <{where_expression}>";


if ($stmt = $con->prepare($query)) {
    $stmt->execute();
    $stmt->bind_result($field1, $field2);
    while ($stmt->fetch()) {
        //printf("%s, %s\n", $field1, $field2);
    }
    $stmt->close();
}


?>
