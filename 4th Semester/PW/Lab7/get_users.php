<?php

include "connection.php";
$query = "select u.username, u.email, u.age, u.webpage, r.role_name from users u inner join roles r on u.id_role = r.id_role";


if (isset($_POST['filter'])){
	$criteria = $_POST['filter'];
	$input = $_POST['in'];
	if(!empty($input)){
		if ($criteria == "age"){
			$query1 = $query." where " . $criteria . " = " . $input .";";
		}
		else{
			$query1 = $query." where " . $criteria . " like '%" . $input ."%';";
		}

		$response = mysqli_query($con, $query1);
		echo json_encode(mysqli_fetch_all($response, MYSQLI_ASSOC));
	}
	else{
		$response = mysqli_query($con, $query);
		if(mysqli_num_rows($response) > 0){
			echo json_encode(mysqli_fetch_all($response, MYSQLI_ASSOC));
		}
		else{
			echo "error1";
		}

	}
}
else{
		$response = mysqli_query($con, $query);
		if(mysqli_num_rows($response) > 0){
			echo json_encode(mysqli_fetch_all($response, MYSQLI_ASSOC));
		}
		else{
			echo "error2";
		}

	}


mysqli_close($con);






// $response = mysqli_query($con, $query);
// if(mysqli_num_rows($response) > 0){
// 	echo json_encode(mysqli_fetch_all($response, MYSQLI_ASSOC));
// }
// else{
// 	echo "error";
// }

// if (isset($_POST['filter'])){
// 	$criteria = $_POST['filter'];
// 	$input = $_POST['in'];
// 	if(!empty($input)){
// 		$query = $query." where ? = ?;";
// 		// echo $query;
// 		$stm = mysqli_stmt_init($con);
// 		if (!mysqli_stmt_prepare($stm, $query)){
// 			echo "error0";
// 			exit();
// 		}
// 		else{
// 				if ($criteria == "age"){
// 					mysqli_stmt_bind_param($stm, 'si', $criteria, $input);
// 				}
// 				else{
// 					mysqli_stmt_bind_param($stm, 'ss', $criteria, $input);
// 				}
// 				mysqli_stmt_execute($stm);
// 				// mysqli_stmt_store_result($stm);
// 				// echo mysqli_stmt_num_rows($stm);
// 				$result = mysqli_stmt_get_result($stm);
					
// 				if(mysqli_stmt_num_rows($stm) > 0){
// 					// echo $query;
// 					// echo json_encode(mysqli_fetch_assoc($result));
// 					while ($data = mysqli_stmt_fetch_assoc($stm)) {
// 				        echo json_encode($data);  // <--- available in $data
// 				    }
// 				}
// 				else{
// 					echo "error1" . mysqli_error($con);
// 				}

// 		}
// 	}
// 	else{
// 		$response = mysqli_query($con, $query);
// 		if(mysqli_num_rows($response) > 0){
// 			echo json_encode(mysqli_fetch_all($response, MYSQLI_ASSOC));
// 		}
// 		else{
// 			echo "error2";
// 		}

// 	}

	
// }
// else{
// 		$response = mysqli_query($con, $query);
// 		if(mysqli_num_rows($response) > 0){
// 			echo json_encode(mysqli_fetch_all($response, MYSQLI_ASSOC));
// 		}
// 		else{
// 			echo "error2";
// 		}

// 	}




?>