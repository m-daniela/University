window.onload = function(){
	var request = new XMLHttpRequest();
	request.open('POST', 'get_roles.php', true);
	request.onload = function(){
		if (this.status == 200){
			var data = JSON.parse(this.responseText);
			var output = '';
			for (var i in data){
				// j = i + 1;
				output += '<option value=' + i + '>' + data[i].role_name + '</option>';

			}
			document.getElementById('role').innerHTML = output;
		}

	}
	request.send();
}