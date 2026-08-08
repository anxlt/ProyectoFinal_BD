package db.DAO;

import logico.Provincia;
import java.util.List;

public interface ProvinciaDAO {
    List<Provincia> listarTodas();
    Provincia buscarPorId(int idProvincia);
}
