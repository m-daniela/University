
<?php

require "connection.php";

$query = "select u.username, u.email, u.age, u.webpage, r.role_name from users u inner join roles r on u.id_role = r.id_role where u.username = '".$_GET['username']."';";
$response = mysqli_query($con, $query);
if(mysqli_num_rows($response) > 0){
	echo json_encode(mysqli_fetch_all($response, MYSQLI_ASSOC));
}
else{
	echo "error";
}

mysqli_close($con);


?>