<?php

function inicioSesionYRegistro() {
	$DB_SERVER="db"; 
	$DB_USER="admin";
	$DB_PASS="test"; 
	$DB_DATABASE="database"; 

	$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);

	if (mysqli_connect_errno()) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	exit();
	}

	$user = $_POST["username"];
	$pass = $_POST["password"];
	$accion = $_POST["accion"];

	switch($accion) {
		case 'iniciosesion':
			$resultado = mysqli_query($con, "SELECT * FROM users WHERE username = '$user' AND password = '$pass'");

			if (!$resultado) {
			echo 'Ha ocurrido algún error: ' . mysqli_error($con);
			}

			$fila = mysqli_fetch_array($resultado);
			if ($fila['id'] == "") {
				echo "Usuario y/o contraseña incorrectos";
			} else {
				echo "Inicio de sesion correcto, bienvenido ", $fila['username'];
			} 
			break;
		case 'registro':
			$resultado = mysqli_query($con, "INSERT INTO users(username, password) VALUES ('$user', '$pass')");
			
			if ($resultado) {
				echo 'Se ha registrado correctamente, ya puede iniciar sesion';
			} else {
				echo 'Ha ocurrido algún error: ' . mysqli_error($con);
			}
			break;
		default:
			echo 'Accion no valida';
			break;
	}
}

function fcmGuardarToken() {
	$DB_SERVER="db"; 
	$DB_USER="admin";
	$DB_PASS="test"; 
	$DB_DATABASE="database"; 

	$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
	
	if (mysqli_connect_errno()) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	exit();
	}
	$token = $_POST['token'];
	
	$resultado = mysqli_query($con, "INSERT INTO tokens(token) VALUES ('$token')");
}

function fcmEnviarMensaje() {
	$DB_SERVER="db"; 
	$DB_USER="admin";
	$DB_PASS="test"; 
	$DB_DATABASE="database"; 

	$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
	
	if (mysqli_connect_errno()) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	exit();
	}
	
	$mensaje = $_POST['mensaje'];
	$tokensBD = mysqli_query($con, "SELECT * FROM tokens");
	$tokens = [];
	while ($row = mysqli_fetch_assoc($tokensBD)) {
		$tokens[] = $row['token'];
	}
	
	$cabecera = array(
				'Authorization: key=AAAAilnFTGU:APA91bEEnAVrHa5ipgsJlTnWE6uQWUNh9F2omPTmY-CM9enCiKOsDHelmeUfBMuH3yYZ62DNtsCK8cY8L-UE4MlRRUCUe9FSrSFlWTZ-wukDMQLzy_ZMk4dezjZCiSzIbESrZIYmMHLC',
				'Content-Type: application/json');
	$msg = array('registration_ids' => $tokens,
				'data' => array (
					'mensaje' => $mensaje,
					'cuerpo' => 'Se ha recibido un mensaje',
					'titulo' => 'Mensaje'));
	$msgJSON = json_encode($msg);
	
	$ch = curl_init(); 
	curl_setopt($ch, CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send');
	curl_setopt($ch, CURLOPT_POST, true );
	curl_setopt($ch, CURLOPT_HTTPHEADER, $cabecera);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true );
	curl_setopt($ch, CURLOPT_POSTFIELDS, $msgJSON );
	$resultado= curl_exec($ch);
	curl_close($ch);
	
	echo 'Se ha enviado una notificación a todos los usuarios de la app';
}

function insertarImagenBD() {
	$DB_SERVER="db"; 
	$DB_USER="admin";
	$DB_PASS="test"; 
	$DB_DATABASE="database"; 

	$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);

	if (mysqli_connect_errno()) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	exit();
	}
	
	$foto = $_POST['foto'];
	$titulo = $_POST['titulo'];
	$resultado = mysqli_query($con, "INSERT INTO images(image, title) VALUES ('$foto', '$titulo')");
	
	if ($resultado) {
		echo 'Se ha guardado la foto correctamente';
	} else {
		echo 'Ha ocurrido algún error: ' . mysqli_error($con);
	}
}

function extraerImagenBD() {
	$DB_SERVER="db"; 
	$DB_USER="admin";
	$DB_PASS="test"; 
	$DB_DATABASE="database"; 

	$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);

	if (mysqli_connect_errno()) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	exit();
	}
	$titulo = $_POST['titulo'];
	$resultado = mysqli_query($con, "SELECT image FROM images WHERE title = '$titulo'");
	if (!$resultado) {
		echo 'Ha ocurrido algún error: ' . mysqli_error($con);
		exit;
	}
	else {
		$photo = mysqli_fetch_array($resultado);
		echo base64_decode($photo['image']);
	}
}

$funcion = $_POST['funcion'];
if ($funcion == 'inicioSesionYRegistro') {
	inicioSesionYRegistro();
} else if ($funcion == 'fcmEnviarMensaje') {
	fcmEnviarMensaje();
} else if ($funcion == 'fcmGuardarToken') {
	fcmGuardarToken();
} else if ($funcion == 'extraerImagenBD') {
	extraerImagenBD();
} else if ($funcion == 'insertarImagenBD') {
	insertarImagenBD();
}
?>
