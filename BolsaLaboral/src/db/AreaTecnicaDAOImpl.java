package db;

import logico.AreaTecnica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AreaTecnicaDAOImpl implements AreaTecnicaDAO {

    @Override
    public List<AreaTecnica> listarTodas() {
        List<AreaTecnica> lista = new ArrayList<>();
        String sql = "SELECT id_area_tecnica, nombre_area FROM AreaTecnica ORDER BY nombre_area";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new AreaTecnica(
                        rs.getInt("id_area_tecnica"),
                        rs.getString("nombre_area")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public AreaTecnica buscarPorId(int idAreaTecnica) {
        String sql = "SELECT id_area_tecnica, nombre_area FROM AreaTecnica WHERE id_area_tecnica = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAreaTecnica);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new AreaTecnica(
                            rs.getInt("id_area_tecnica"),
                            rs.getString("nombre_area"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}