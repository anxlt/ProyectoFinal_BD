package db.DAO;

import logico.CentroEmpleador;
import java.util.List;

public interface CentroEmpleadorDAO {
    void insertar(CentroEmpleador c);
    void actualizar(CentroEmpleador c);
    void eliminar(String codigo);
    CentroEmpleador buscarPorCodigo(String codigo);
    List<CentroEmpleador> listarTodos();
}