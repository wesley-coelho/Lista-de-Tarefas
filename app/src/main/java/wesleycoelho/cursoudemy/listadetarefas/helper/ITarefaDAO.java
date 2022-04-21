package wesleycoelho.cursoudemy.listadetarefas.helper;

import java.util.List;

import wesleycoelho.cursoudemy.listadetarefas.model.Tarefa;

public interface ITarefaDAO {
    public boolean salvar(Tarefa tarefa);
    public boolean atualizar(Tarefa tarefa);
    public boolean deletar(Tarefa tarefa);
    public List<Tarefa> listar();

}
