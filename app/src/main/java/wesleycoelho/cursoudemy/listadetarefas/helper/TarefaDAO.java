package wesleycoelho.cursoudemy.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import wesleycoelho.cursoudemy.listadetarefas.model.Tarefa;

public class TarefaDAO implements ITarefaDAO{

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public TarefaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome",tarefa.getNomeTarefa());
        try{
            write.insert(DbHelper.TABELA_TAREFAS,null, cv);
            Log.i("Tarefa Salva", "Salva com sucesso.\n");

        }catch (Exception e){
            Log.i("Erro", "Erro ao salvar os dados.\n" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put( "nome", tarefa.getNomeTarefa() );
        String id = String.valueOf(tarefa.getId());
        try{
            String[] args = {id};
            write.update(DbHelper.TABELA_TAREFAS, cv, "id=?",args);
            Log.i("Tarefa Atualizada", "Tarefa atualizada com sucesso.\n");

        }catch (Exception e){
            Log.i("Erro", "Erro ao atualizar tarefa.\n" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try{
            String[] id = { String.valueOf( tarefa.getId() ) };
            write.delete(DbHelper.TABELA_TAREFAS, "id=?", id);
            Log.i("Tarefa Deletada", "Tarefa deletada com sucesso.\n");

        }catch (Exception e){
            Log.i("Erro", "Erro ao deletar tarefa.\n" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;" ;
        Cursor c = read.rawQuery(sql, null);

        while(c.moveToNext()){
            Tarefa tarefa = new Tarefa();
            int idIndex = c.getColumnIndex("id");
            long id = c.getLong(idIndex);
            int nomeIndex = c.getColumnIndex("nome");
            String nomeTarefa = c.getString(nomeIndex);
            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);
            tarefas.add(tarefa);
        }

        return tarefas;
    }
}
