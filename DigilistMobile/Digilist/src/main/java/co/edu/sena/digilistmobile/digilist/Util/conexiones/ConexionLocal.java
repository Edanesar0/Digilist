package co.edu.sena.digilistmobile.digilist.util.conexiones;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ConexionLocal {

    public static final String N_BD = "dbDigilist";
    public static final int VERSION_BD = 2;
    private BDhelper nHelper;
    private final Context nContexto;
    private SQLiteDatabase nBD;

    /**
     * clase que tiene metodos que permiten el uso de sqllite
     */

    private static class BDhelper extends SQLiteOpenHelper {

        public BDhelper(Context context) {
            super(context, N_BD, null, VERSION_BD);
        }

        /**
         * Creacion de base de datos
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS `ciudad` (" +
                    "  `idCiudad` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Descripcion` varchar(40) DEFAULT NULL," +
                    "  PRIMARY KEY (`idCiudad`)" +
                    "  )");
            db.execSQL("CREATE TABLE IF NOT EXISTS `cliente` (" +
                    "  `idCliente` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Ciudad_idCiudad` integer  NOT NULL," +
                    "  `Nombre` varchar(25) DEFAULT NULL," +
                    "  `Direccion` varchar(45) DEFAULT NULL," +
                    "  `Telefono` integer  DEFAULT NULL," +
                    "  PRIMARY KEY (`idCliente`)," +
                    "  FOREIGN KEY (`Ciudad_idCiudad`) REFERENCES `ciudad` (`idCiudad`) ON DELETE NO ACTION ON UPDATE NO ACTION" +
                    ") ");
            db.execSQL("CREATE TABLE IF NOT EXISTS `comentarios` (" +
                    "  `idComentarios` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Nombre` varchar(40) DEFAULT NULL," +
                    "  `Correo` varchar(255) DEFAULT NULL," +
                    "  `Asunto` varchar(45) DEFAULT NULL," +
                    "  `Mensaje` varchar(255) DEFAULT NULL," +
                    "  PRIMARY KEY (`idComentarios`)" +
                    ") ");
            db.execSQL("CREATE TABLE IF NOT EXISTS `cotizacion` (" +
                    "  `idCotizacion` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Vendedor_idVendedor` integer  NOT NULL," +
                    "  `Cliente_idCliente` integer  NOT NULL," +
                    "  `Fecha` date DEFAULT NULL," +
                    "  PRIMARY KEY (`idCotizacion`)," +
                    "  FOREIGN KEY (`Cliente_idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY (`Vendedor_idVendedor`) REFERENCES `vendedor` (`idVendedor`) ON DELETE NO ACTION ON UPDATE NO ACTION  " +
                    ") ");
            db.execSQL("CREATE TABLE IF NOT EXISTS `detallecotizacion` (" +
                    "  `idDetalleCotizacion` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Producto_idProducto` integer  NOT NULL," +
                    "  `Cotizacion_idCotizacion` integer  NOT NULL," +
                    "  `Cantidad` integer  DEFAULT NULL," +
                    "  `Precio` double DEFAULT NULL," +
                    "  PRIMARY KEY (`idDetalleCotizacion`)," +
                    "  FOREIGN KEY (`Cotizacion_idCotizacion`) REFERENCES `cotizacion` (`idCotizacion`) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY (`Producto_idProducto`) REFERENCES `producto` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION  " +
                    ") ");
            db.execSQL("CREATE TABLE IF NOT EXISTS `detallesdepedido` (" +
                    "  `Stock_idStock` integer  NOT NULL," +
                    "  `Pedido_idPedido` integer  NOT NULL," +
                    "  `PrecioUnidad` double DEFAULT NULL," +
                    "  `Cantidad` integer  DEFAULT NULL," +
                    "  PRIMARY KEY (`Stock_idStock`,`Pedido_idPedido`)," +
                    "  FOREIGN KEY (`Stock_idStock`) REFERENCES `stock` (`idStock`) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY (`Pedido_idPedido`) REFERENCES `pedido` (`idPedido`) ON DELETE NO ACTION ON UPDATE NO ACTION  " +
                    ");");
            db.execSQL("CREATE TABLE IF NOT EXISTS `detallesurtido` (" +
                    "  `Surtido_idSurtido` integer  NOT NULL," +
                    "  `Stock_idStock` integer  NOT NULL," +
                    "  `Cantidad` integer  DEFAULT NULL," +
                    "  PRIMARY KEY (`Surtido_idSurtido`,`Stock_idStock`)," +
                    "  FOREIGN KEY (`Surtido_idSurtido`) REFERENCES `surtido` (`idSurtido`) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY (`Stock_idStock`) REFERENCES `stock` (`idStock`) ON DELETE NO ACTION ON UPDATE NO ACTION  " +
                    ");");
            db.execSQL("CREATE TABLE IF NOT EXISTS `material` (" +
                    "  `idMaterial` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Nombre` varchar(20) DEFAULT NULL," +
                    "  `Descripcion` varchar(45) DEFAULT NULL," +
                    "  PRIMARY KEY (`idMaterial`)" +
                    ");");
            db.execSQL("CREATE TABLE IF NOT EXISTS `pedido` (" +
                    "  `idPedido` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Ciudad_idCiudad` integer  NOT NULL," +
                    "  `Vendedor_idVendedor` integer  NOT NULL," +
                    "  `Cliente_idCliente` integer  NOT NULL," +
                    "  `Direccion` varchar(255) DEFAULT NULL," +
                    "  PRIMARY KEY (`idPedido`)," +
                    "  FOREIGN KEY (`Vendedor_idVendedor`) REFERENCES `vendedor` (`idVendedor`) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY (`Cliente_idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY (`Ciudad_idCiudad`) REFERENCES `ciudad` (`idCiudad`) ON DELETE NO ACTION ON UPDATE NO ACTION" +
                    ") ");
            db.execSQL("CREATE TABLE IF NOT EXISTS `permisos` (" +
                    "  `idPermisos` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Descripcion` varchar(45) DEFAULT NULL," +
                    "  PRIMARY KEY (`idPermisos`)" +
                    ");");
            db.execSQL("CREATE TABLE IF NOT EXISTS `tipo` (" +
                    "  `idTipo` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Nombre` varchar(20) NOT NULL," +
                    "  `Descripcion` varchar(45) DEFAULT NULL," +
                    "  PRIMARY KEY (`idTipo`)" +
                    ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS `producto` (" +
                    "  `idProducto` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Nombre` varchar(20) DEFAULT NULL," +
                    "  `Descripcion` varchar(45) DEFAULT NULL," +
                    "  `Referencia` varchar(30) DEFAULT NULL," +
                    "  `Precio` double DEFAULT NULL," +
                    "  `Dimencion` varchar(255) DEFAULT NULL," +
                    "  `Material_idMaterial` integer  NOT NULL," +
                    "  `Tipo_idTipo` integer  NOT NULL," +
                    "  PRIMARY KEY (`idProducto`)," +
                    "  FOREIGN KEY (`Tipo_idTipo`) REFERENCES `tipo` (`idTipo`) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY (`Material_idMaterial`) REFERENCES `material` (`idMaterial`) ON DELETE NO ACTION ON UPDATE NO ACTION" +
                    ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS `rol` (" +
                    "  `idRol` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Descripcion` varchar(45) DEFAULT NULL," +
                    "  PRIMARY KEY (`idRol`)" +
                    ");");
            db.execSQL("CREATE TABLE IF NOT EXISTS `rol_has_permisos` (" +
                    "  `Rol_idRol` integer  NOT NULL," +
                    "  `Permisos_idPermisos` integer  NOT NULL," +
                    "  PRIMARY KEY (`Rol_idRol`,`Permisos_idPermisos`)," +
                    "  FOREIGN KEY (`Rol_idRol`) REFERENCES `rol` (`idRol`) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY (`Permisos_idPermisos`) REFERENCES `permisos` (`idPermisos`) ON DELETE NO ACTION ON UPDATE NO ACTION  " +
                    ");");
            db.execSQL("CREATE TABLE IF NOT EXISTS `stan` (" +
                    "  `idStan` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Capacidad` integer  NOT NULL," +
                    "  `Descripcion` varchar(45) DEFAULT NULL," +
                    "  PRIMARY KEY (`idStan`)" +
                    ") ");
            db.execSQL("CREATE TABLE IF NOT EXISTS `stock` (" +
                    "  `idStock` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Producto_idProducto` integer  NOT NULL," +
                    "  `Stan_idStan` integer  NOT NULL," +
                    "  `Cantidad` integer  DEFAULT NULL," +
                    "  PRIMARY KEY (`idStock`)," +
                    "  FOREIGN KEY (`Stan_idStan`) REFERENCES `stan` (`idStan`) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY (`Producto_idProducto`) REFERENCES `producto` (`idProducto`) ON DELETE NO ACTION ON UPDATE NO ACTION  " +
                    ");");
            db.execSQL("CREATE TABLE IF NOT EXISTS `surtido` (" +
                    "  `idSurtido` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Stan_idStan` integer  NOT NULL," +
                    "  `Fecha` date DEFAULT NULL," +
                    "  PRIMARY KEY (`idSurtido`)," +
                    "  FOREIGN KEY (`Stan_idStan`) REFERENCES `stan` (`idStan`) ON DELETE NO ACTION ON UPDATE NO ACTION  " +
                    ") ");
            db.execSQL("CREATE TABLE IF NOT EXISTS `usuario` (" +
                    "  `Usuario` varchar(25) NOT NULL," +
                    "  `Rol_idRol` integer  NOT NULL," +
                    "  `Contrasena` varchar(256) DEFAULT NULL," +
                    "  PRIMARY KEY (`Usuario`)," +
                    "  FOREIGN KEY (`Rol_idRol`) REFERENCES `rol` (`idRol`) ON DELETE NO ACTION ON UPDATE NO ACTION" +
                    "  );");
            db.execSQL("CREATE TABLE IF NOT EXISTS `vendedor` (" +
                    "  `idVendedor` integer  AUTO_INCREMENT NOT NULL ," +
                    "  `Usuario_Usuario` varchar(25) NOT NULL," +
                    "  `Ciudad_idCiudad` integer  NOT NULL," +
                    "  `Nombre` varchar(20) DEFAULT NULL," +
                    "  `Apellido` varchar(20) DEFAULT NULL," +
                    "  `Telefono` integer  DEFAULT NULL," +
                    "  `Direccion` varchar(45) DEFAULT NULL," +
                    "  PRIMARY KEY (`idVendedor`)," +
                    "  FOREIGN KEY (`Ciudad_idCiudad`) REFERENCES `ciudad` (`idCiudad`) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY (`Usuario_Usuario`) REFERENCES `usuario` (`Usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION  " +
                    ") ");

        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            if (!db.isReadOnly()) {
                // Enable foreign key constraints
                db.execSQL("PRAGMA foreign_keys=ON;");
            }
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS `ciudad`;");
            db.execSQL("DROP TABLE IF EXISTS `cliente`;");
            db.execSQL("DROP TABLE IF EXISTS `comentarios`;");
            db.execSQL("DROP TABLE IF EXISTS `cotizacion`;");
            db.execSQL("DROP TABLE IF EXISTS `detallecotizacion`;   ");
            db.execSQL("DROP TABLE IF EXISTS `detallesdepedido`;");
            db.execSQL("DROP TABLE IF EXISTS `detallesurtido`;");
            db.execSQL("DROP TABLE IF EXISTS `material`;");
            db.execSQL("DROP TABLE IF EXISTS `pedido`;");
            db.execSQL("DROP TABLE IF EXISTS `permisos`;");
            db.execSQL("DROP TABLE IF EXISTS `tipo`;");
            db.execSQL("DROP TABLE IF EXISTS `producto`;");
            db.execSQL("DROP TABLE IF EXISTS `rol`;");
            db.execSQL("DROP TABLE IF EXISTS `rol_has_permisos`;");
            db.execSQL("DROP TABLE IF EXISTS `stan`;");
            db.execSQL("DROP TABLE IF EXISTS `stock`;");
            db.execSQL("DROP TABLE IF EXISTS `surtido`;");
            db.execSQL("DROP TABLE IF EXISTS `usuario`;");
            db.execSQL("DROP TABLE IF EXISTS `vendedor`;");

            onCreate(db);

        }
    }

    public ConexionLocal(Context c) {
        nContexto = c;
    }

    public ConexionLocal abrir() {
        nHelper = new BDhelper(nContexto);
        nBD = nHelper.getWritableDatabase();//ecribir en la base de datos
        return this;
    }

    /**
     * Se cierra la base de datos
     */
    public void cerrar() {
        nHelper.close();
    }

    /**
     * Se agregan las tiendas
     */
    public long insert(String tabla, ContentValues cv) {
        /**Se crea un contenedor para especifcar los campos y se agregan los datos y se coloca insertWithOnConflict para que ignore si hay un error*/
        return nBD.insertWithOnConflict(tabla, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public Cursor readProducto(String sql) {
        /**Crea un array para agregar los datos y se pueda utilizar como contenido de un adaptador*/
        Cursor c = nBD.rawQuery(sql, null);
        //recorre y agrega
        return c;
    }


}
