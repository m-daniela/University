

<!DOCTYPE html>
<html>
<head>
	<title>Profile</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
	<div class="container">
		<div id="details">
			
		</div>
		<hr>
		<input type="hidden" name="input" value=<?php echo $_GET['username']?>><br>
		<a href=<?php echo "change.php?username=" . $_GET['username']?>>Change details.</a><br>
		<a href=<?php echo "delete.php?username=" . $_GET['username']?>>Delete profile.</a><br>
		<a href="browse.php">Browse users.</a><br>
		<a href="index.html">Log out.</a>
	</div>
	<script type="text/javascript">
		window.onload = function(){
			let value = document.querySelector("input").value;
			console.log(value);
			let request = new XMLHttpRequest();
			request.open('GET', "get_data.php?username=" + value, true);
			request.onload = function(){
				if (this.status == 200){
					console.log(this.responseText);
					var data = JSON.parse(this.responseText);
					
					var output = '<h1>Details</h1>';
					output += '<p>Username: ' + data[0].username + '</p>' +
						'<p>Email: ' + data[0].email + '</p>' +
						'<p>Age: ' + data[0].age + '</p>' +
						'<p>Webpage: ' + data[0].webpage + '</p>' +
						'<p>Role: ' + data[0].role_name + '</p>';
					document.getElementById('details').innerHTML = output;
				}

			}
			request.send();
		}
	</script>
</body>
</html>