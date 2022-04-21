package wesleycoelho.cursoudemy.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import wesleycoelho.cursoudemy.listadetarefas.R;
import wesleycoelho.cursoudemy.listadetarefas.helper.TarefaDAO;
import wesleycoelho.cursoudemy.listadetarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {
    private TextInputEditText textTarefa;
    private Tarefa tarefaAtual;
    private Tarefa tarefaSelecionada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);
        textTarefa = findViewById(R.id.textTarefa);
        //recuperar tarefa caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");
        //configurar tarefa na caixa de texto
        if( tarefaAtual != null){
            textTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.itemSalvar: {
                //executa a ação para o itme salvar
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if (tarefaAtual != null) {//update
                    String nomeTarefa = textTarefa.getText().toString();
                    if (!nomeTarefa.isEmpty()) {
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        //atualizar no banco de dados
                        if (tarefaDAO.atualizar(tarefa)) {
                            finish();
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Sucesso ao atualizar tarefa",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Erro ao atualizar tarefa",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {//insert
                    String nomeTarefa = textTarefa.getText().toString();
                    if (!nomeTarefa.isEmpty()) {
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        if (tarefaDAO.salvar(tarefa)) {
                            finish();
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Sucesso ao salvar tarefa",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Erro ao salvar tarefa",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }break;
        }
        return super.onOptionsItemSelected(item);
    }
}