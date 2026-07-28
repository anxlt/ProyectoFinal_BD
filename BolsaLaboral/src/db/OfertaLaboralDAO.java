package db;

import logico.OfertaLaboral;
import java.util.List;

public interface OfertaLaboralDAO {
    void insertar(OfertaLaboral o);
    void actualizar(OfertaLaboral o);
    void eliminar(String codigo);
    OfertaLaboral buscarPorCodigo(String codigo);
    List<OfertaLaboral> listarTodos();
}