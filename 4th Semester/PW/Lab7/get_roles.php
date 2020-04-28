<?php

require "connection.php";

$query = "select role_name from roles;";
$response = mysqli_query($con, $query);
if(mysqli_num_rows($response) > 0){
	echo json_encode(mysqli_fetch_all($response, MYSQLI_ASSOC));
}
else{
	echo "error";
}

mysqli_close($con);

?>