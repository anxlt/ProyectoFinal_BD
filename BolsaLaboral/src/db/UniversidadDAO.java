package db;
import logico.Universidad;
import java.util.List;

public interface UniversidadDAO {
    List<Universidad> listarTodas();
    Universidad buscarPorId(int idUniversidad);
}