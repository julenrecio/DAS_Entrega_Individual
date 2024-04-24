<?php

# funcion para el inicio de sesion y el registro de usuarios
function inicioSesionYRegistro() {
	# Se establecen las variables para la conexión con la base de datos
	$DB_SERVER="db"; 
	$DB_USER="admin";
	$DB_PASS="test"; 
	$DB_DATABASE="database"; 
	#Se establece la conexión
	$con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);

	if (mysqli_connect_errno()) {
	echo 'Error de conexion: ' . mysqli_connect_error();
	exit();
	}
	# Se reciben los parametros de la petición
	$user = $_POST["username"];
	$pass = $_POST["password"];
	$accion = $_POST["accion"];
	
	# Si ejecuta el inicio de sesion o el registro dependiendo de la accion especificada
	switch($accion) {
		case 'iniciosesion':
			# Consulta sql para ver si hay algun usuario y contraseña que coincidan en la base de datos
			$resultado = mysqli_query($con, "SELECT * FROM users WHERE username = '$user' AND password = '$pass'");

			if (!$resultado) {
			echo 'Ha ocurrido algún error: ' . mysqli_error($con);
			}
			# Se recoge una fila del resultado
			$fila = mysqli_fetch_array($resultado);
			# Si no hay nada, se muestra un mensaje de error
			if ($fila['id'] == "") {
				echo "Usuario y/o contraseña incorrectos";
			} else {
				echo "Inicio de sesion correcto, bienvenido ", $fila['username'];
			} 
			break;
		case 'registro':
		# query sql para introducir un nuevo usuario 
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
# funcion para los mensajes FCM
function fcm() {
	# se recibe el token
    $token = $_POST['token'];
	# se esprecifica la cabecera
	$cabecera = array(
				'Authorization: key=AAAAilnFTGU:APA91bEEnAVrHa5ipgsJlTnWE6uQWUNh9F2omPTmY-CM9enCiKOsDHelmeUfBMuH3yYZ62DNtsCK8cY8L-UE4MlRRUCUe9FSrSFlWTZ-wukDMQLzy_ZMk4dezjZCiSzIbESrZIYmMHLC',
				'Content-Type: application/json');
	# se especifica el mensaje con el destinatario y los datos
	$msg = array('to' => $token,
				'data' => array (
					'mensaje' => 'Este es un mensaje definido en el index.php del servidor. Se envia a traves de firebase mediante una petición HTTP con cURL',
					'cuerpo' => 'Se ha recibido un mensaje de Firebase',
					'titulo' => 'Firebase'));
	$msgJSON = json_encode($msg);
	
	# Se especifican la URL, el metodo, la cabecera y el mensaje codificado
	$ch = curl_init(); 
	curl_setopt($ch, CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send');
	curl_setopt($ch, CURLOPT_POST, true );
	curl_setopt($ch, CURLOPT_HTTPHEADER, $cabecera);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true );
	curl_setopt($ch, CURLOPT_POSTFIELDS, $msgJSON );
	# Se ejecuta la petición
	$resultado= curl_exec($ch);
	curl_close($ch);
	
	echo curl_errno($ch);
	echo $resultado;
}
# funcion para guardar las imagenes en el servidor
function imagenes() {
	# Se recoge la imagen del parametro de la petición
	$fotoEn64 = $_POST['foto'];
	# Se indica el directorio
	$directorio = __DIR__ . '/fotos/';
	# Se genera un id unico para la imagen
	$nombreImagen = uniqid() . '.png';
	$rutaImagen = $folder_path . $file_name;
	# Se guarda la imagen en la ruta
	file_put_contents($rutaImagen, $fotoEn64);
	echo $nombreImagen;
}

# Se ejecutara una funcion u otra dependiendo del parametro funcion
$funcion = $_POST['funcion'];
if ($funcion == 'inicioSesionYRegistro') {
	inicioSesionYRegistro();
} else if ($funcion == 'fcm') {
	fcm();
} else if ($funcion == 'imagenes') {
	imagenes();
}     



?>