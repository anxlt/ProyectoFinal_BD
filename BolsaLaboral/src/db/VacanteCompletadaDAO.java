package db;

import java.util.List;
import logico.VacanteCompletada;

public interface VacanteCompletadaDAO {

    void insertar(VacanteCompletada v);

    VacanteCompletada buscarPorCodigo(String codigo);

    List<VacanteCompletada> listarTodos();

}