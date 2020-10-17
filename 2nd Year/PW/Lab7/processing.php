<?php

require "connection.php";

# log in into the given account
if (isset($_POST['login_btn'])){

	$username = $_POST['username'];
	$password = $_POST['password'];

	if(empty($username) || empty($password)){
		header("Location: index.html?error=wrongemail&username=".$username);
		exit();
	}
	else{
		$query = "select username from users where username like ? or email like ? and password = ?";
		$stm = mysqli_stmt_init($con);
		if (!mysqli_stmt_prepare($stm, $query)){
			header("Location: index.html?error=connection_error");
			exit();
		}
		else{
			mysqli_stmt_bind_param($stm, 'sss', $username, $username, $password);
			mysqli_stmt_execute($stm);
			$id = mysqli_stmt_get_result($stm);
			if ($row = mysqli_fetch_assoc($id)){
				// $pass_check = password_verify($password, $row['password']);
				// if (!){
					header("Location: profile.php?username=".$row['username']);
					exit();
				// }
			}
			// header("Location: index.php?success");
			// exit();
		}
	}

	mysqli_stmt_close($stm);
}

// create a new account
if (isset($_POST['signup_btn'])){

	$email = $_POST['email'];
	$username = $_POST['username']; 
	$password = $_POST['password'];
	$age = $_POST['age'];
	$webpage = $_POST['webpage'];
	$role = $_POST['role'];

	if(empty($email) || empty($username) || empty($password)){
		// check if mandatory fields are emtpy
		header("Location: signup.html?error=emptyfields&email=".$email.
		"&username=".$username."&age=".$age."&webpage=".$webpage);
		exit();
	}
	else if(!filter_var($email, FILTER_VALIDATE_EMAIL)){
		// check if mail is valid
		header("Location: signup.html?error=wrongemail&username=".$username);
		exit();
	}
	else if(!is_numeric($age) || $age<18){
		// check if is a number and > 18 
		header("Location: signup.html?email=".$email.
		"&username=".$username."&age=".$age."&webpage=".$webpage);
		exit();
	}
	else{
		$query = "select id_user from users where username = ? or email = ?;";
		$stm = mysqli_stmt_init($con);
		if(!mysqli_stmt_prepare($stm, $query)){
			header("Location: signup.html?error=connection_error");
			exit();
		}
		else{
			mysqli_stmt_bind_param($stm, "ss", $username, $email);
			mysqli_stmt_execute($stm);
			mysqli_stmt_store_result($stm);
			$result = mysqli_stmt_num_rows($stm);
			if($result>0){
				header("Location: signup.html?error=useroremailtaken");
			exit();
			}
			else{
				$sql = "insert into users(username, password, age, email, webpage, id_role) values(?, ?, ?, ?, ?, ?);";
				$stm1 = mysqli_stmt_init($con);
				if(!mysqli_stmt_prepare($stm1, $sql)){
					header("Location: signup.html?error=connection_error1");
					exit();
				}
				else{
					$hashy = password_hash($password, PASSWORD_DEFAULT);
					mysqli_stmt_bind_param($stm1, 'sssssi', $username, $password, $age, $email, $webpage, $role);
					mysqli_stmt_execute($stm1);
					header("Location: profile.php?username=".$username);
					exit();
				}
			}
		}
	}

	mysqli_stmt_close($stm);
	
}

// change username
if(isset($_POST['change_username'])){
	$old_username = $_POST['old_username'];
	$username = $_POST['username'];
	$query = "select * from users where username = ?";
	$stm = mysqli_stmt_init($con);
	mysqli_stmt_prepare($stm,  $query);
	mysqli_stmt_bind_param($stm, "s", $username);
	mysqli_stmt_execute($stm);
	mysqli_stmt_store_result($stm);
	$result = mysqli_stmt_num_rows($stm);
	if($result>0){
		header("Location: change.php?error=usertaken&username=".$old_username);
		exit();
	}
	else{
		if (empty($username)){
			header("Location: change.php?username=".$old_username);
			exit();
		}
		else{
			$find_user = "select id_user from users where username = ?;";
			$stm = mysqli_stmt_init($con);
			if(!mysqli_stmt_prepare($stm, $find_user)){
				header("Location: change.php?username=".$old_username);
				exit();
			}
			else{
				mysqli_stmt_bind_param($stm, "s", $old_username);
				mysqli_stmt_execute($stm);
				$result = mysqli_stmt_get_result($stm);
				if($row = mysqli_fetch_assoc($result)){
					$id = $row['id_user'];
					$change = "update users set username = ? where id_user = ?";
					$stm2 = mysqli_stmt_init($con);
					if(!mysqli_stmt_prepare($stm2, $change)){
						header("Location: change.php?username=".$old_username);
						exit();
					}
					else{
						mysqli_stmt_bind_param($stm2, "si", $username, $id);
						mysqli_stmt_execute($stm2);
						header("Location: profile.php?username=".$username);
						exit();
					}
				}
			}
		}
	}
}

// change password
if (isset($_POST['change_password'])) {
	$old_username = $_POST['old_username'];
	$old_password = $_POST['old_password'];
	$password = $_POST['new_password'];
	if(empty($old_password) || empty($password)){
		header("Location: change.php?username=".$old_username);
		exit();
	}
	else{
		$check_pass = "select * from users where username = ? and password = ?";
		$stm = mysqli_stmt_init($con);
		if(!mysqli_stmt_prepare($stm,  $check_pass)){
			header("Location: change.php?username=".$old_username);
			exit();
		}
		mysqli_stmt_bind_param($stm, "ss", $old_username, $old_password);
		mysqli_stmt_execute($stm);
		mysqli_stmt_store_result($stm);
		$result = mysqli_stmt_num_rows($stm);
		if ($result == 0){
			header("Location: change.php?error=wrongpassword&username=".$old_username);
			exit();
		}
		else{
			$query = "update users set password = ? where username = ? and password = ?";
			$stm2 = mysqli_stmt_init($con);
			mysqli_stmt_prepare($stm2, $query);
			mysqli_stmt_bind_param($stm2, "sss", $password, $old_username, $old_password);
			mysqli_stmt_execute($stm2);
			header("Location: profile.php?username=".$old_username);
			exit();

		}

	}
}

// change details
if(isset($_POST['change_details'])){
	$old_username = $_POST['old_username'];
	$email = $_POST['email'];
	$age = $_POST['age'];
	$webpage = $_POST['webpage'];
	$query = "select * from users where email = ?";
	$stm = mysqli_stmt_init($con);
	mysqli_stmt_prepare($stm,  $query);
	mysqli_stmt_bind_param($stm, "s", $email);
	mysqli_stmt_execute($stm);
	mysqli_stmt_store_result($stm);
	$result = mysqli_stmt_num_rows($stm);
	if($result > 0){
		header("Location: change.php?error=emailused&username=".$old_username);
		exit();
	}
	else{
		if(empty($email) || empty($age) || empty($webpage)){
			header("Location: change.php?username=".$old_username."&email=".$email."&age=".$age."$webpage=".$webpage);
				exit();
		}
		else if(!is_numeric($age) || $age<18){
			header("Location: change.php?error=wrongage&username=".$old_username);
			exit();
		}
		else{
			$query = "update users set email = ?, age = ?, webpage = ? where username = ?";
			$stm = mysqli_stmt_init($con);
			mysqli_stmt_prepare($stm, $query);
			mysqli_stmt_bind_param($stm, "siss", $email, $age, $webpage, $old_username);
			mysqli_stmt_execute($stm);
			header("Location: profile.php?username=".$old_username);
			exit();
		}
	}
}

// delete account
if(isset($_POST['delete_account'])){
	$old_username = $_POST['old_username'];
	$password = $_POST['password'];
	if(empty($password)){
		header("Location: change.php?username=".$old_username);
		exit();
	}
	else{
		$query = "delete from users where username = ? and password = ?";
		$stm = mysqli_stmt_init($con);
		mysqli_stmt_prepare($stm, $query);
		mysqli_stmt_bind_param($stm, "ss", $old_username, $password);
		mysqli_stmt_execute($stm);
		header("Location: index.html");
		exit();
	}
}


mysqli_close($con);

?>