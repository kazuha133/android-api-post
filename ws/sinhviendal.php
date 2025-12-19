<?php
// the dbconnection file
require_once 'dbconnection.php';

class SinhvienDAL{
	/**
	 * Lấy danh sách sinh viên
	 *
	 * @param none
	 * @return array[] Danh sách sinh viên
	 */
	public function getAll()
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = 'SELECT masv, tensv FROM sinhvien_tbl';
		$list = array();
		$result = $conn->query($query);
		while ($row = $result->fetch_assoc())
		{
			$list[] = $row;
		}
		return $list;
	}
	
	/**
	 * Thêm 1 sinh viên vào CSDL
	 *
	 * @param integer masv	Mã sinh viên
	 * @param string tensv	Tên sinh viên
	 * @return true/false	Kết quả thực hiện câu sql
	 */
	function insert($masv, $tensv)
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = 'INSERT INTO sinhvien_tbl (masv, tensv) VALUES (?, ?)';
		$stmt = $conn->prepare($query);
		$stmt->bind_param("is", $masv, $tensv);
		return $stmt->execute();
	}
	
	/**
	 * Thêm nhiều sinh viên vào CSDL
	 *
	 * @param string data	Chuỗi JSON chứa thông tin của các sinh viên cần thêm
	 * @return int	Số dòng thêm thành công
	 */
	function insertsome($data)
	{
		$result = 0;
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = 'INSERT INTO sinhvien_tbl (masv, tensv) VALUES (?, ?)';
		$arrSinhvien = json_decode($data, TRUE);
		
		foreach ($arrSinhvien as $sinhvien) {
			var_dump($sinhvien);
			$stmt = $conn->prepare($query);
			$stmt->bind_param("is", $sinhvien["masv"], $sinhvien["tensv"]);
			if ($stmt->execute()) $result++;
		}
		return $result;
	}
	
	/**
	 * Xóa 1 sinh viên
	 *
	 * @param integer masv	Mã sinh viên
	 * @return true/false	Kết quả thực hiện câu sql
	 */
	function delete($masv)
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = 'DELETE FROM sinhvien_tbl WHERE masv = ?';
		$stmt = $conn->prepare($query);
		$stmt->bind_param("i", $masv);
		return $stmt->execute();
	}
	
	/**
	 * Cập nhật 1 sinh viên
	 *
	 * @param integer masv	Mã sinh viên
	 * @param string tensv	Tên sinh viên
	 * @return true/false	Kết quả thực hiện câu sql
	 */
	function update($masv, $tensv)
	{
		$dbConnection = new DBConnection();
		$conn = $dbConnection->getConnection();
		$query = 'UPDATE sinhvien_tbl SET tensv = ? WHERE masv = ?';
		$stmt = $conn->prepare($query);
		$stmt->bind_param("si", $tensv, $masv);
		return $stmt->execute();
	}
}

