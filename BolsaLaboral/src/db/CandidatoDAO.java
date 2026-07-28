package db;

import logico.Candidato;
import java.util.List;

public interface CandidatoDAO {
    void insertar(Candidato c);
    void actualizar(Candidato c);
    void actualizarEstado(String codigo, String estado);
    void eliminar(String codigo);
    Candidato buscarPorCodigo(String codigo);
    List<Candidato> listarTodos();
}