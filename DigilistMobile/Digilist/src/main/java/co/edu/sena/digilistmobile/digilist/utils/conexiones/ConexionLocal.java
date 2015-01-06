package co.edu.sena.digilistmobile.digilist.utils.conexiones;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


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
                    "  `idCity`INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `description` VARCHAR(40) NULL," +
                    "  PRIMARY KEY (`idCity`));");
            db.execSQL("CREATE TABLE IF NOT EXISTS `client` (" +
                    "  `idClient` INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `idCity` INT(11) NOT NULL," +
                    "  `name` VARCHAR(500) NULL," +
                    "  `address` VARCHAR(45) NULL DEFAULT NULL," +
                    "  `phone` INT(11) NULL DEFAULT NULL," +
                    "  PRIMARY KEY (`idClient`)," +
                    "  CONSTRAINT `cliente_ibfk_1`" +
                    "    FOREIGN KEY (`idCity`)" +
                    "    REFERENCES `city` (`idCity`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);");
            db.execSQL("CREATE TABLE IF NOT EXISTS `comentarios` (" +
                    "  `idComentarios` INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `Nombre` VARCHAR(40) NULL DEFAULT NULL," +
                    "  `Correo` VARCHAR(255) NULL DEFAULT NULL," +
                    "  `Asunto` VARCHAR(45) NULL DEFAULT NULL," +
                    "  `Mensaje` VARCHAR(255) NULL DEFAULT NULL," +
                    "  PRIMARY KEY (`idComentarios`));");
            db.execSQL("CREATE TABLE IF NOT EXISTS `historicalSupply` (" +
                    "  `idSupply` INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `previousAmount` DOUBLE NULL," +
                    "  `date` DATE NULL," +
                    "  `newAmount` DOUBLE NULL," +
                    "  `description` VARCHAR(45) NULL," +
                    "  `idProduct` INT NULL," +
                    "  PRIMARY KEY (`idSupply`));");
            db.execSQL("CREATE TABLE IF NOT EXISTS `material` (" +
                    "  `idMaterial` INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `name` VARCHAR(20) NOT NULL," +
                    "  `description` VARCHAR(45) NULL DEFAULT NULL," +
                    "  PRIMARY KEY (`idMaterial`));");
            db.execSQL("CREATE TABLE IF NOT EXISTS `role` (" +
                    "  `idRol` INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `description` VARCHAR(45) NULL DEFAULT NULL," +
                    "  PRIMARY KEY (`idRol`));");
            db.execSQL("CREATE TABLE IF NOT EXISTS `user` (" +
                    "  `idUser` INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `idCity` INT(11) NOT NULL," +
                    "  `names` VARCHAR(500) NULL," +
                    "  `last_name` VARCHAR(500) NULL," +
                    "  `phone` INT(11) NULL DEFAULT NULL," +
                    "  `address` VARCHAR(45) NULL," +
                    "  `idRol` INT(11) NOT NULL," +
                    "  `user` VARCHAR(500) NULL," +
                    "  `pass` VARCHAR(500) NULL," +
                    "  PRIMARY KEY (`idUser`, `idRol`)," +
                    "  CONSTRAINT `vendedor_ibfk_1`" +
                    "    FOREIGN KEY (`idCity`)" +
                    "    REFERENCES `city` (`idCity`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `fk_vendedor_rol1`" +
                    "    FOREIGN KEY (`idRol`)" +
                    "    REFERENCES `role` (`idRol`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);");
            db.execSQL("CREATE TABLE IF NOT EXISTS `order` (" +
                    "  `idOrder` INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `idCity` INT(11) NOT NULL," +
                    "  `idUser` INT(11) NOT NULL," +
                    "  `idClient` INT(11) NOT NULL," +
                    "  `address` VARCHAR(255) NULL," +
                    "  PRIMARY KEY (`idOrder`)," +
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
                    "    ON UPDATE NO ACTION);");
            db.execSQL("CREATE TABLE IF NOT EXISTS `permission` (" +
                    "  `idPermission` INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `description` VARCHAR(45) NULL," +
                    "  PRIMARY KEY (`idPermission`));");
            db.execSQL("CREATE TABLE IF NOT EXISTS `type` (" +
                    "  `idType` INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `name` VARCHAR(20) NOT NULL," +
                    "  `description` VARCHAR(45) NULL DEFAULT NULL," +
                    "  `dimension` VARCHAR(45) NULL," +
                    "  PRIMARY KEY (`idType`));");
            db.execSQL("CREATE TABLE IF NOT EXISTS `product` (" +
                    "  `idProduct` INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `name` VARCHAR(20) NULL," +
                    "  `description` VARCHAR(45) NULL DEFAULT NULL," +
                    "  `reference` VARCHAR(30) NULL DEFAULT NULL," +
                    "  `price` DOUBLE NULL DEFAULT NULL," +
                    "  `idMaterial` INT(11) NOT NULL," +
                    "  `idType` INT(11) NOT NULL," +
                    "  PRIMARY KEY (`idProduct`)," +
                    "  CONSTRAINT `producto_ibfk_1`" +
                    "    FOREIGN KEY (`idType`)" +
                    "    REFERENCES `type` (`idType`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `producto_ibfk_2`" +
                    "    FOREIGN KEY (`idMaterial`)" +
                    "    REFERENCES `material` (`idMaterial`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);");
            db.execSQL("CREATE TABLE IF NOT EXISTS `role_has_permission` (" +
                    "  `idRol` INT(11) NOT NULL," +
                    "  `idPermission` INT(11) NOT NULL," +
                    "  PRIMARY KEY (`idRol`, `idPermission`)," +
                    "  CONSTRAINT `rol_has_permisos_ibfk_1`" +
                    "    FOREIGN KEY (`idRol`)" +
                    "    REFERENCES `role` (`idRol`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `rol_has_permisos_ibfk_2`" +
                    "    FOREIGN KEY (`idPermission`)" +
                    "    REFERENCES `permission` (`idPermission`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);");
            db.execSQL("CREATE TABLE IF NOT EXISTS `stand` (" +
                    "  `idStand` INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `capacity` INT(11) NOT NULL," +
                    "  `description` VARCHAR(45) NULL DEFAULT NULL," +
                    "  PRIMARY KEY (`idStand`));");
            db.execSQL("CREATE TABLE IF NOT EXISTS `stock` (" +
                    "  `idStock` INTEGER AUTO_INCREMENT NOT NULL ," +
                    "  `idProduct` INT(11) NOT NULL," +
                    "  `idStan` INT(11) NOT NULL," +
                    "  `amount` INT(11) NULL," +
                    "  PRIMARY KEY (`idStock`)," +
                    "  CONSTRAINT `stock_ibfk_1`" +
                    "    FOREIGN KEY (`idStan`)" +
                    "    REFERENCES `stand` (`idStand`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION," +
                    "  CONSTRAINT `stock_ibfk_2`" +
                    "    FOREIGN KEY (`idProduct`)" +
                    "    REFERENCES `product` (`idProduct`)" +
                    "    ON DELETE NO ACTION" +
                    "    ON UPDATE NO ACTION);");
            db.execSQL("CREATE TABLE IF NOT EXISTS `order_has_product` (" +
                    "  `idOrder` INT(11) NOT NULL," +
                    "  `idProduct` INT(11) NOT NULL," +
                    "  `date` DATE NULL," +
                    "  `amount` INT NULL," +
                    "  PRIMARY KEY (`idOrder`, `idProduct`)," +
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
            db.execSQL("DROP TABLE IF EXISTS comentarios");
            db.execSQL("DROP TABLE IF EXISTS historicalSupply");
            db.execSQL("DROP TABLE IF EXISTS order_has_product");
            db.execSQL("DROP TABLE IF EXISTS role_has_permission");
            db.execSQL("DROP TABLE IF EXISTS stock");
            db.execSQL("DROP TABLE IF EXISTS stand");
            db.execSQL("DROP TABLE IF EXISTS product");
            db.execSQL("DROP TABLE IF EXISTS material");
            db.execSQL("DROP TABLE IF EXISTS permission");
            db.execSQL("DROP TABLE IF EXISTS 'order'");
            db.execSQL("DROP TABLE IF EXISTS type");
            db.execSQL("DROP TABLE IF EXISTS client");
            db.execSQL("DROP TABLE IF EXISTS user");
            db.execSQL("DROP TABLE IF EXISTS role");
            db.execSQL("DROP TABLE IF EXISTS city");

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

    public Cursor read(String sql) {
        /**Crea un array para agregar los datos y se pueda utilizar como contenido de un adaptador*/
        Cursor c = nBD.rawQuery(sql, null);
        //recorre y agrega
        return c;
    }

    public void limpiar() {
        try {
            nBD.delete("user", null, null);
            nBD.delete("comentarios", null, null);
            nBD.delete("historicalSupply", null, null);
            nBD.delete("order_has_product", null, null);
            nBD.delete("role_has_permission", null, null);
            nBD.delete("stock", null, null);
            nBD.delete("stand", null, null);
            nBD.delete("product", null, null);
            nBD.delete("material", null, null);
            nBD.delete("type", null, null);
            nBD.delete("permission", null, null);
            nBD.delete("`order`", null, null);
            nBD.delete("client", null, null);
            nBD.delete("role", null, null);
            nBD.delete("city", null, null);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

    }


}
