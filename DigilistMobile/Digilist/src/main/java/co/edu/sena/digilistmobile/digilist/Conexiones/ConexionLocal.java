package co.edu.sena.digilistmobile.digilist.Conexiones;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ADMIN on 25/07/2014.
 */
public class ConexionLocal {

    public static final String ID_TIENDA = "tienda";
    public static final String ID_PRODUCTO = "producto";
    public static final String ID_FECHA = "fecha";
    public static final String ID_CANTIDAD = "cantidad";
    public static final String ID_ASESOR = "asesor";
    public static final String ID_CATEGORIA = "categoria";
    public static final String ID_PRECIO = "precio";
    public static final String N_BD = "Dreamteam";
    public static final String N_TABLA = "ventas";
    public static final int VERSION_BD = 1;
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
            db.execSQL("CREATE TABLE IF NOT EXISTS inicio"
                    + "(asesor TEXT NOT NULL PRIMARY KEY,"
                    + "contrasena TEXT NOT NULL,"
                    + ID_TIENDA + " TEXT NOT NULL,"
                    + "cadena TEXT NOT NULL,"
                    + "nombre TEXT NOT NULL,"
                    + "cargo TEXT NOT NULL)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + N_TABLA);
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
    public long insertarTiendas(String _id, String cadena, String nombre) {
        ContentValues cv2 = new ContentValues();
        cv2.put("_id", _id);
        cv2.put("nombre", nombre);
        cv2.put("cadena", cadena);
        /**Se crea un contenedor para especifcar los campos y se agregan los datos y se coloca insertWithOnConflict para que ignore si hay un error*/
        return nBD.insertWithOnConflict("tiendas", null, cv2, SQLiteDatabase.CONFLICT_IGNORE);


    }


}
