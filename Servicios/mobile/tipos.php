<?php

$criterio = isset($_GET['criterio']) ? $_GET['criterio'] : "";
$termino = isset($_GET['termino']) ? $_GET['termino'] : "";
require_once 'funciones_bd.php';

class tipos {
    function __construct($criterio, $termino) {
        $db = new funciones_BD();
        echo json_encode($db->getTipos($criterio, $termino));
    }

}
$tipos = new tipos($criterio, $termino);
?>
