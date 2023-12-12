<?php

namespace Database;

use PDO;
use PDOException;

class Database
{
    //DB params
    private $host = 'localhost';
    private $db_name = 'your_contact';
    private $username = 'root';
    private $password = '';

    //DB Conn

    /**
     * Database constructor.
     */
    public function __construct()
    {

    }

    public function connect(): PDO
    {
        $conn = null;
        try {
            $conn = new PDO("mysql:127.0.0.1=$this->host;port=3306;dbname=" . $this->db_name,
                $this->username, $this->password);
            $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            echo 'Connection Error: ' . $e->getMessage();
        }

        return $conn;
    }
}