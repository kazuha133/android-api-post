<?php
// Turn off all error reporting
error_reporting(1);

// the sinhviendal file
require_once 'sinhviendal.php';

// message to return
$message = array();

$dal = new SinhvienDAL();

switch($_GET["action"])
{
	case 'getall':
		$message = $dal->getAll();
		break;
	
	case 'insert':
		$masv = $_GET["masv"];
		$tensv = $_GET["tensv"];
		$result = $dal->insert($masv, $tensv);
		$message = ["message" => json_encode($result)];
		break;
		
	case 'insertsome':
		$data = $_GET["data"];
		$result = $dal->insertsome($data);
		$message = ["message" => json_encode($result)];
		break;
	
	case 'delete':
		$masv = $_GET["masv"];
		$result = $dal->delete($masv);
		$message = ["message" => json_encode($result)];
		break;
	
	case 'update':
		$masv = $_GET["masv"];
		$tensv = $_GET["tensv"];
		$result = $dal->update($masv, $tensv);
		$message = ["message" => json_encode($result)];
		break;

	default:
		$message = ["message" => "Unknown method " . $_GET["action"]];
		break;
}

//The JSON message
header('Content-type: application/json; charset=utf-8');

//Clean (erase) the output buffer
ob_clean();

echo json_encode($message);

