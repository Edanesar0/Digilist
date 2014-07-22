<?php

$criterio=$_GET['criterio'];
require_once 'funciones_bd.php';
class productos {
    
    function __construct() {
        $db=new funciones_BD();
        echo json_encode($db->productos("", ""));
    }
}
echo 'Criterio '.$criterio."<br>";
$db = new productos($criterio);

?>
