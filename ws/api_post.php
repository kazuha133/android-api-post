<?php
// Turn off all error reporting
error_reporting(0);

// the sinhviendal file
require_once 'sinhviendal.php';

// message to return
$message = array();

$dal = new SinhvienDAL();

switch($_POST["action"])
{
	case 'getall':
		$message = $dal->getAll();
		break;
	
	case 'insert':
		$masv = $_POST["masv"];
		$tensv = $_POST["tensv"];
		$result = $dal->insert($masv, $tensv);
		$message = ["message" => json_encode($result)];
		break;
		
	case 'insertsome':
		$data = $_POST["data"];
		$result = $dal->insertsome($data);
		$message = ["message" => json_encode($result)];
		break;
	
	case 'delete':
		$masv = $_POST["masv"];
		$result = $dal->delete($masv);
		$message = ["message" => json_encode($result)];
		break;
	
	case 'update':
		$masv = $_POST["masv"];
		$tensv = $_POST["tensv"];
		$result = $dal->update($masv, $tensv);
		$message = ["message" => json_encode($result)];
		break;

	default:
		$message = ["message" => "Unknown method " . $_POST["action"]];
		break;
}

//The JSON message
header('Content-type: application/json; charset=utf-8');

//Clean (erase) the output buffer
ob_clean();

echo json_encode($message);

