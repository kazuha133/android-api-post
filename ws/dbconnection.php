<?php
class DBConnection{
    private $host = "localhost";
    private $db_name = "dbsinhvien";
    private $username = "root";
    private $password = "";
    private $conn;
 
	/**
	 * Khởi tạo - Mở kết nối đến database
	 *
	 * @param none
	 * @return database
	 */
    public function __construct(){
        $this->conn = new mysqli($this->host, $this->username, $this->password, $this->db_name);
    }
	
	/**
	 * Hủy - Đóng kết nối tới database
	 *
	 * @param none
	 * @return none
	 */
	function __destruct()
	{
		$this->conn->close();
	}
	
	/**
	 * getConnection
	 *
	 * @param none
	 * @return connection
	 */
	public function getConnection(){
        return $this->conn;
    }
}

