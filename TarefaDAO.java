package com.chinese.english.listoftasks.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chinese.english.listoftasks.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    //Esse contrutor serve para que se passe o contexto da aplicação no momento da intanciacao
    public TarefaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {
        //Saving task
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{
            escreve.insert(DbHelper.TABELA_TAREFAS, null, cv);
            Log.i("INFO","Tarefa salva com sucesso");
        }catch(Exception e){
            Log.e("INFO","Erro ao salvar a tarefa" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        //Fill fields in the table
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());


        try{
            String[] args = {tarefa.getId().toString()};
            //this symbol ? to be replaced by content of args
            escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args);
            Log.i("INFO","Tarefa atualizada com sucesso");
        }catch(Exception e){
            Log.e("INFO","Erro ao atualizar a tarefa" + e.getMessage());
            return false;
        }


        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        String[] args = {tarefa.getId().toString()};
        try{
            escreve.delete(DbHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("INFO", "Tarefa excluída com sucesso");
        }catch(Exception e){
            Log.e("INFO", "Erro ao excluir a tarefa");
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM "+DbHelper.TABELA_TAREFAS+" ;";
        Cursor c = le.rawQuery(sql, null);

        while(c.moveToNext()){
            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));

            tarefa.setId( id );
            tarefa.setNomeTarefa( nomeTarefa );

            tarefas.add( tarefa );
        }

        return tarefas;
    }
}
