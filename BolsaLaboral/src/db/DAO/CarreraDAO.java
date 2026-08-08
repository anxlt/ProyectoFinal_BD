package db.DAO;
import logico.Carrera;
import java.util.List;

public interface CarreraDAO {
    List<Carrera> listarTodas();
    Carrera buscarPorId(int idCarrera);
}