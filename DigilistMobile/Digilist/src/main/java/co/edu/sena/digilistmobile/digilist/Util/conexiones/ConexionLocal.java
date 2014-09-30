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
             db.execSQL("CREATE TABLE IF NOT EXISTS `city` (" +
                    "  `idCity` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `description` VARCHAR(40) NULL," +
                    "  PRIMARY KEY (`idCity`));" +
                    "CREATE TABLE IF NOT EXISTS `client` (" +
                    "  `idClient` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `idCity` INT(11) NOT NULL," +
                    "  `name` VARCHAR(500) NULL," +
                    "  `address` VARCHAR(45) NULL DEFAULT NULL," +
                    "  `phone` INT(11) NULL DEFAULT NULL," +
                    "  PRIMARY KEY (`idClient`)," +
                    "  INDEX `Ciudad_idCiudad` (`idCity` ASC)," +
                    "  CONSTRAINT `cliente_ibfk_1`" +
                    "    FOREIGN KEY (`idCity`)" +
                    "    REFERENCES `city` (`idCity`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);" +
                    "CREATE TABLE IF NOT EXISTS `comentarios` (" +
                    "  `idComentarios` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `Nombre` VARCHAR(40) NULL DEFAULT NULL," +
                    "  `Correo` VARCHAR(255) NULL DEFAULT NULL," +
                    "  `Asunto` VARCHAR(45) NULL DEFAULT NULL," +
                    "  `Mensaje` VARCHAR(255) NULL DEFAULT NULL," +
                    "  PRIMARY KEY (`idComentarios`));" +
                    "CREATE TABLE IF NOT EXISTS `historicalSupply` (" +
                    "  `idSupply` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `previousAmount` DOUBLE NULL," +
                    "  `date` DATE NULL," +
                    "  `newAmount` DOUBLE NULL," +
                    "  `description` VARCHAR(45) NULL," +
                    "  `idProduct` INT NULL," +
                    "  INDEX `index1` (`idSupply` ASC)," +
                    "  PRIMARY KEY (`idSupply`));" +
                    "CREATE TABLE IF NOT EXISTS `material` (" +
                    "  `idMaterial` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `name` VARCHAR(20) NOT NULL," +
                    "  `description` VARCHAR(45) NULL DEFAULT NULL," +
                    "  PRIMARY KEY (`idMaterial`));" +
                    "CREATE TABLE IF NOT EXISTS `role` (" +
                    "  `idRol` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `description` VARCHAR(45) NULL DEFAULT NULL," +
                    "  PRIMARY KEY (`idRol`));" +
                    "CREATE TABLE IF NOT EXISTS `user` (" +
                    "  `idUser` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `idCity` INT(11) NOT NULL," +
                    "  `names` VARCHAR(500) NULL," +
                    "  `last_name` VARCHAR(500) NULL," +
                    "  `phone` INT(11) NULL DEFAULT NULL," +
                    "  `address` VARCHAR(45) NULL," +
                    "  `idRol` INT(11) NOT NULL," +
                    "  `user` VARCHAR(500) NULL," +
                    "  `pass` VARCHAR(500) NULL," +
                    "  PRIMARY KEY (`idUser`, `idRol`)," +
                    "  INDEX `Ciudad_idCiudad` (`idCity` ASC)," +
                    "  INDEX `fk_vendedor_rol1_idx` (`idRol` ASC)," +
                    "  CONSTRAINT `vendedor_ibfk_1`" +
                    "    FOREIGN KEY (`idCity`)" +
                    "    REFERENCES `city` (`idCity`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `fk_vendedor_rol1`" +
                    "    FOREIGN KEY (`idRol`)" +
                    "    REFERENCES `role` (`idRol`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);" +
                    "CREATE TABLE IF NOT EXISTS `order` (" +
                    "  `idOrder` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `idCity` INT(11) NOT NULL," +
                    "  `idUser` INT(11) NOT NULL," +
                    "  `idClient` INT(11) NOT NULL," +
                    "  `address` VARCHAR(255) NULL," +
                    "  PRIMARY KEY (`idOrder`)," +
                    "  INDEX `Cliente_idCliente` (`idClient` ASC)," +
                    "  INDEX `Vendedor_idVendedor` (`idUser` ASC)," +
                    "  INDEX `Ciudad_idCiudad` (`idCity` ASC)," +
                    "  CONSTRAINT `pedido_ibfk_1`" +
                    "    FOREIGN KEY (`idClient`)" +
                    "    REFERENCES `client` (`idClient`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `pedido_ibfk_2`" +
                    "    FOREIGN KEY (`idUser`)" +
                    "    REFERENCES `user` (`idUser`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `pedido_ibfk_3`" +
                    "    FOREIGN KEY (`idCity`)" +
                    "    REFERENCES `city` (`idCity`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);" +
                    "CREATE TABLE IF NOT EXISTS `permission` (" +
                    "  `idPermission` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `description` VARCHAR(45) NULL," +
                    "  PRIMARY KEY (`idPermission`));" +
                    "CREATE TABLE IF NOT EXISTS `type` (" +
                    "  `idType` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `name` VARCHAR(20) NOT NULL," +
                    "  `description` VARCHAR(45) NULL DEFAULT NULL," +
                    "  `dimension` VARCHAR(45) NULL," +
                    "  PRIMARY KEY (`idType`));" +
                    "CREATE TABLE IF NOT EXISTS `product` (" +
                    "  `idProduct` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `name` VARCHAR(20) NULL," +
                    "  `description` VARCHAR(45) NULL DEFAULT NULL," +
                    "  `reference` VARCHAR(30) NULL DEFAULT NULL," +
                    "  `price` DOUBLE NULL DEFAULT NULL," +
                    "  `idMaterial` INT(11) NOT NULL," +
                    "  `idType` INT(11) NOT NULL," +
                    "  PRIMARY KEY (`idProduct`)," +
                    "  INDEX `Tipo_idTipo` (`idType` ASC)," +
                    "  INDEX `Material_idMaterial` (`idMaterial` ASC)," +
                    "  CONSTRAINT `producto_ibfk_1`" +
                    "    FOREIGN KEY (`idType`)" +
                    "    REFERENCES `type` (`idType`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `producto_ibfk_2`" +
                    "    FOREIGN KEY (`idMaterial`)" +
                    "    REFERENCES `material` (`idMaterial`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);" +
                    "CREATE TABLE IF NOT EXISTS `role_has_permission` (" +
                    "  `idRol` INT(11) NOT NULL," +
                    "  `idPermission` INT(11) NOT NULL," +
                    "  PRIMARY KEY (`idRol`, `idPermission`)," +
                    "  INDEX `Permisos_idPermisos` (`idPermission` ASC)," +
                    "  CONSTRAINT `rol_has_permisos_ibfk_1`" +
                    "    FOREIGN KEY (`idRol`)" +
                    "    REFERENCES `role` (`idRol`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `rol_has_permisos_ibfk_2`" +
                    "    FOREIGN KEY (`idPermission`)" +
                    "    REFERENCES `permission` (`idPermission`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);" +
                    "CREATE TABLE IF NOT EXISTS `stan` (" +
                    "  `idStan` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `capacity` INT(11) NOT NULL," +
                    "  `description` VARCHAR(45) NULL DEFAULT NULL," +
                    "  PRIMARY KEY (`idStan`));" +
                    "CREATE TABLE IF NOT EXISTS `stock` (" +
                    "  `idStock` INT(11) NOT NULL AUTO_INCREMENT," +
                    "  `idProduct` INT(11) NOT NULL," +
                    "  `idStan` INT(11) NOT NULL," +
                    "  `amount` INT(11) NULL," +
                    "  PRIMARY KEY (`idStock`)," +
                    "  INDEX `Stan_idStan` (`idStan` ASC)," +
                    "  INDEX `Producto_idProducto` (`idProduct` ASC)," +
                    "  CONSTRAINT `stock_ibfk_1`" +
                    "    FOREIGN KEY (`idStan`)" +
                    "    REFERENCES `stan` (`idStan`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `stock_ibfk_2`" +
                    "    FOREIGN KEY (`idProduct`)" +
                    "    REFERENCES `product` (`idProduct`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);" +
                    "CREATE TABLE IF NOT EXISTS `order_has_product` (" +
                    "  `idOrder` INT(11) NOT NULL," +
                    "  `idProduct` INT(11) NOT NULL," +
                    "  `date` DATE NULL," +
                    "  `amount` INT NULL," +
                    "  PRIMARY KEY (`idOrder`, `idProduct`)," +
                    "  INDEX `fk_order_has_product_product1_idx` (`idProduct` ASC)," +
                    "  INDEX `fk_order_has_product_order1_idx` (`idOrder` ASC)," +
                    "  CONSTRAINT `fk_order_has_product_order1`" +
                    "    FOREIGN KEY (`idOrder`)" +
                    "    REFERENCES `order` (`idOrder`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `fk_order_has_product_product1`" +
                    "    FOREIGN KEY (`idProduct`)" +
                    "    REFERENCES `product` (`idProduct`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);");

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
