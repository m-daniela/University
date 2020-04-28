
<!DOCTYPE html>
<html>
<head>
	<title>Change profile</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
	<div class="container">
		<a href=<?php echo "profile.php?username=" . $_GET['username']?>>Back to profile</a>
		<form  action="processing.php" method="post">
			<input type="hidden" name="old_username" value=<?php echo $_GET['username']?>><br>
			<input type="text" name="username" placeholder="New Username"><br>
			<button type="submit" name="change_username">Change Username</button>
		
			<hr>

			<input type="password" name="old_password" placeholder="Old password"><br>
			<input type="password" name="new_password" placeholder="New password"><br>
			<button type="submit" name="change_password">Change Password</button>

			<hr>

			<input type="text" name="email" placeholder="E-mail"><br>
			<input type="text" name="age" placeholder="Age"><br>
			<input type="text" name="webpage" placeholder="Webpage"><br>

			<button type="submit" name="change_details">Change Details</button>

			

		</form> 
	</div>
</body>
</html>