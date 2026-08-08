package db.DAO;

import logico.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void insertar(Usuario u);
    Usuario buscarPorNombre(String nombre);
    List<Usuario> listarTodos();
}