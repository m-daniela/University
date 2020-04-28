<?php

// db connection

define('db_host', 'localhost');
define('db_user', 'root');
define('db_password', 'pass');
define('db_name', 'enterprise');

$con = mysqli_connect(db_host, db_user, db_password, db_name);


?>