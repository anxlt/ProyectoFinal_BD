package db;
import logico.Habilidad;
import java.util.List;

public interface HabilidadDAO {
    List<Habilidad> listarTodas();
    Habilidad buscarPorId(int idHabilidad);
}