<?php
class funciones_BD {
    private $db;
    // constructor
    function __construct() {
        require_once 'connectbd.php';
        // connecting to database

        $this->db = new DB_Connect();
        $this->db->connect();
    }
    // destructor
    function __destruct() {
        
    }
    /**
     * agregar nuevo usuario
     */
    public function adduser($username, $password) {

        $result = mysql_query("INSERT INTO usuarios(username,passw) VALUES('$username', '$password')");
        // check for successful store

        if ($result) {

            return true;
        } else {

            return false;
        }
    }
    /**
     * Verificar si el usuario ya existe por el username
     */
    public function isuserexist($username) {

        $result = mysql_query("SELECT username from usuarios WHERE username = '$username'");

        $num_rows = mysql_num_rows($result); //numero de filas retornadas

        if ($num_rows > 0) {

            // el usuario existe 

            return true;
        } else {
            // no existe
            return false;
        }
    }
    public function login($user, $passw) {

        $result = mysql_query("SELECT COUNT(*) FROM usuario where usuario='$user' and Contrasena='$passw'");
        $count = mysql_fetch_row($result);

        /* como el usuario debe ser unico cuenta el numero de ocurrencias con esos datos */


        if ($count[0] == 0) {

            return true;
        } else {

            return false;
        }
    }
    public function getProductos($criterio, $terminoBusqueda) {        
        if (empty($criterio)) {
            $criterio = 'name';
        }
       $datas = array();
        $i = 0;
        //mysql_set_charset("utf8"); 
        $res = mysql_query("SELECT idProduct,name,description,reference,price,idMaterial as material ,idType as type FROM product where $criterio like '%$terminoBusqueda%'");
        if (mysql_num_rows($res)) {
            while ($data = mysql_fetch_assoc($res)) {
                $datas[] = $data;
            }
        }
        return $datas;
    }
    public function getMateriales($criterio, $terminoBusqueda) {        
        if (empty($criterio)) {
            $criterio = 'nombre';
        }
       $datas = array();
        $i = 0;
        $res = mysql_query("SELECT idMaterial,Nombre FROM material where $criterio like '%$terminoBusqueda%'");
        if (mysql_num_rows($res)) {
            while ($data = mysql_fetch_assoc($res)) {
                $datas[] = $data;
            }
        }
        return $datas;
    }
    public function getTipos($criterio, $terminoBusqueda) {        
        if (empty($criterio)) {
            $criterio = 'nombre';
        }
       $datas = array();        
       mysql_set_charset("utf8"); 
        $res = mysql_query("SELECT idTipo,Nombre,Descripcion FROM tipo where $criterio like '%$terminoBusqueda%'");
        if (mysql_num_rows($res)) {
            while ($data = mysql_fetch_assoc($res)) {
                $datas[] = $data;
            }
        }
        return $datas;
    }

}

?>
