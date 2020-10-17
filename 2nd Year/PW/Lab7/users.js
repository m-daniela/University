

document.getElementById('show').addEventListener('click', set_users);
document.getElementById('in').addEventListener('keypress', function(e){
	if(e.key === 'Enter'){
		e.preventDefault();
		set_users();
	}
});


function set_users(){
	let request = new XMLHttpRequest();
	request.open('POST', 'get_users.php', true);
	request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	
	let selected = document.getElementsByTagName("select");
	let selected_value = selected[0].options[selected[0].selectedIndex].text;
	let input = document.getElementById("in").value;
	let para = "filter=" + selected_value + "&in=" + input;
	// let para = "filter=" + document.getElementById("filter") + "&in=" + document.getElementById("in").value;

	let prev = document.getElementById("searches");
	let button = document.createElement("P");
	button.appendChild(document.createTextNode(selected_value + ": " + input));
	prev.appendChild(button);

	request.onload = function(){
		if (this.status == 200){
			console.log(para);
			var data = JSON.parse(this.responseText);
			var output = '<p id="header"><span>Username</span><span>Email</span><span>Age</span><span>Website</span><span>Role</span></p>';
			for (var i in data){
				output += '<p><span><a href=profile.php?username=' + data[i].username + '>' + data[i].username +'</a></span>' + 
						'<span>' + data[i].email + '</span>' + 
						'<span>' + data[i].age + '</span>' + 
						'<span>' + data[i].webpage + '</span>' + 
						'<span>' + data[i].role_name + '</span></p>';

			}
			document.getElementById('results').innerHTML = output;
		}

	}
	request.send(para);
}

window.onload = function(){
	let request = new XMLHttpRequest();
	request.open('POST', 'get_users.php', true);
	request.onload = function(){
		if (this.status == 200){
			console.log(this.responseText);
			var data = JSON.parse(this.responseText);
			let filters = [];
			Object.keys(data[0]).forEach(function(key){
				filters.push(key);
			});
			var output = '';
			for (var i in filters){
				output += '<option value=' + i + '>' + filters[i] + '</option>';
			}
			document.getElementById('filter').innerHTML = output;
		}

	}
	request.send();
}

