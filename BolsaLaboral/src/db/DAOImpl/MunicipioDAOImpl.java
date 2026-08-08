package db.DAOImpl;

import db.Conexion;
import db.DAO.MunicipioDAO;
import logico.Municipio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDAOImpl implements MunicipioDAO {

    @Override
    public List<Municipio> listarTodos() {
        List<Municipio> lista = new ArrayList<>();
        String sql = "SELECT id_municipio, nombre_municipio, id_provincia FROM Municipio ORDER BY nombre_municipio";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Municipio> listarPorProvincia(int idProvincia) {
        List<Municipio> lista = new ArrayList<>();
        String sql = "SELECT id_municipio, nombre_municipio, id_provincia FROM Municipio "
                + "WHERE id_provincia = ? ORDER BY nombre_municipio";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProvincia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Municipio buscarPorId(int idMunicipio) {
        String sql = "SELECT id_municipio, nombre_municipio, id_provincia FROM Municipio WHERE id_municipio = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMunicipio);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Municipio mapear(ResultSet rs) throws SQLException {
        // Ajusta este constructor si el tuyo tiene otro orden de parámetros
        return new Municipio(
                rs.getInt("id_municipio"),
                rs.getString("nombre_municipio"),
                rs.getInt("id_provincia")
        );
    }
}
