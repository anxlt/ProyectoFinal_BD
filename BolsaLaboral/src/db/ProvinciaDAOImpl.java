package db;

import logico.Provincia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProvinciaDAOImpl implements ProvinciaDAO {

    @Override
    public List<Provincia> listarTodas() {
        List<Provincia> lista = new ArrayList<>();
        String sql = "SELECT id_provincia, nombre_provincia FROM Provincia ORDER BY nombre_provincia";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Provincia(rs.getInt("id_provincia"), rs.getString("nombre_provincia")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Provincia buscarPorId(int idProvincia) {
        String sql = "SELECT id_provincia, nombre_provincia FROM Provincia WHERE id_provincia = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProvincia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Provincia(rs.getInt("id_provincia"), rs.getString("nombre_provincia"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
