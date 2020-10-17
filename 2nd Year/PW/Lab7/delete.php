
<!DOCTYPE html>
<html>
<head>
	<title>Delete account</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
	<div class="container">
		<a href=<?php echo "profile.php?username=" . $_GET['username']?>>Back to profile</a>
		<form method="post" action="processing.php">
			<input type="hidden" name="old_username" value=<?php echo $_GET['username']?>><br>
			<p>Enter your password:</p>
			<input type="password" name="password" placeholder="Password"><br>
			<button type="submit" name="delete_account">Delete account</button>

		</form>
	</div>
</body>
</html>