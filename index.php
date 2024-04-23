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
	
function fcm() {
    $token = $_POST['token'];
	$cabecera = array(
				'Authorization: key=AAAAilnFTGU:APA91bEEnAVrHa5ipgsJlTnWE6uQWUNh9F2omPTmY-CM9enCiKOsDHelmeUfBMuH3yYZ62DNtsCK8cY8L-UE4MlRRUCUe9FSrSFlWTZ-wukDMQLzy_ZMk4dezjZCiSzIbESrZIYmMHLC',
				'Content-Type: application/json');
	$msg = array('to' => $token,
				'data' => array (
					'mensaje' => 'Este es un mensaje definido en el index.php del servidor. Se envia a traves de firebase mediante una petición HTTP con cURL',
					'cuerpo' => 'Se ha recibido un mensaje de Firebase',
					'titulo' => 'Firebase'));
	$msgJSON = json_encode($msg);
	
	$ch = curl_init(); 
	curl_setopt($ch, CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send');
	curl_setopt($ch, CURLOPT_POST, true );
	curl_setopt($ch, CURLOPT_HTTPHEADER, $cabecera);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true );
	curl_setopt($ch, CURLOPT_POSTFIELDS, $msgJSON );
	$resultado= curl_exec($ch);
	curl_close($ch);
	
	echo curl_errno($ch);
	echo $resultado;
}

function imagenes() {
	$fotoEn64 = $_POST['foto'];
	$directorio = __DIR__ . '/fotos/';
	$nombreImagen = uniqid() . '.png';
	$rutaImagen = $folder_path . $file_name;
	file_put_contents($rutaImagen, $fotoEn64);
	echo $nombreImagen;
}

$funcion = $_POST['funcion'];
if ($funcion == 'inicioSesionYRegistro') {
	inicioSesionYRegistro();
} else if ($funcion == 'fcm') {
	fcm();
} else if ($funcion == 'imagenes') {
	imagenes();
}     



?>