package db;

import logico.Municipio;
import java.util.List;

public interface MunicipioDAO {
    List<Municipio> listarTodos();
    List<Municipio> listarPorProvincia(int idProvincia);
    Municipio buscarPorId(int idMunicipio);
}
