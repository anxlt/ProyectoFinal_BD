package db.DAO;

import java.util.List;
import logico.Solicitud;

public interface SolicitudDAO {

    void insertar(Solicitud s);

    void actualizar(Solicitud s);

    void eliminar(String codigo);

    Solicitud buscarPorCodigo(String codigo);

    List<Solicitud> listarTodos();

}