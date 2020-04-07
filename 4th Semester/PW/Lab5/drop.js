
let menus = document.querySelectorAll('.click_drop');
let submenus = document.querySelectorAll('.submenu_content');


function show_submenu(id, position){
	let content = document.getElementById(id);
	let style = window.getComputedStyle(content);
	if (style.display == 'none'){
		closeAll(submenus);
		content.style.display = 'block';
		menus[position].style.backgroundColor = '#94f294';
		menus[position].style.color = '#01a06f';
	}
	else{
		menus[position].style.backgroundColor = '#01a06f';
		menus[position].style.color = '#94f294';
		content.style.display = 'none';
	}

}

function closeAll(submenus){
	for (var i = 0; i < submenus.length; i++) {
		menus[i].style.backgroundColor = '#01a06f';
		menus[i].style.color = '#94f294';
		submenus[i].style.display = 'none';
	}
	menus[i].style.backgroundColor = '#01a06f';
	menus[i].style.color = '#94f294';
}


menus[0].addEventListener('click', function(){
	show_submenu('menu', 0);
});
menus[1].addEventListener('click', function(){
	show_submenu('submenu1', 1);
});
menus[2].addEventListener('click', function(){
	show_submenu('submenu2', 2);
});
menus[3].addEventListener('click', function(){
	show_submenu('submenu3', 3);
});
menus[4].addEventListener('click', function(){
	show_submenu('submenu4', 4);
});
menus[5].addEventListener('click', function(){
	show_submenu('submenu5', 5);
});

document.addEventListener("click", function(e) {
	if (e.target.tagName == 'HTML' || e.target.tagName == 'BODY'){
		document.getElementById('menu').style.display = 'none';
		closeAll(submenus);
	}
});

