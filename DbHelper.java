package com.chinese.english.listoftasks.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper{
    //this command determine the version of app
    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";

    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//method used in the installing

        String sql = "CREATE TABLE IF NOT EXISTS "+ TABELA_TAREFAS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, "+" nome TEXT NOT NULL); ";

        /*Creating a new table:
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS "+ TABELA_USUARIO +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, "+" nome TEXT NOT NULL)"; */

        try{
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela");
        }catch(Exception e){
            Log.i("INFO DB","Erro ao criar a tabela" + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//method used for upgrade during the use

        String sql = "DROP TABLE IF EXISTS " + TABELA_TAREFAS +" ;";
        try{
            db.execSQL(sql);
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao atualizar o app");
        }catch(Exception e){
            Log.i("INFO DB","Erro ao atualizar o app" + e.getMessage());
        }
    }
}
