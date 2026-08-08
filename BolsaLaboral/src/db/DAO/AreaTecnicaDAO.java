package db.DAO;

import logico.AreaTecnica;
import java.util.List;

public interface AreaTecnicaDAO {
    List<AreaTecnica> listarTodas();
    AreaTecnica buscarPorId(int idAreaTecnica);
}