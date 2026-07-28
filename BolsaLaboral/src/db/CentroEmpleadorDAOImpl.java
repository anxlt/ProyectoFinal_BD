package db;

import logico.CentroEmpleador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CentroEmpleadorDAOImpl implements CentroEmpleadorDAO {

    @Override
    public void insertar(CentroEmpleador c) {
        String sql = "INSERT INTO CentroEmpleador (codigo, rnc, nombre, sector, provincia, municipio, telefono, correo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCodigo());
            ps.setString(2, c.getRnc());
            ps.setString(3, c.getNombre());
            ps.setString(4, c.getSector());
            ps.setString(5, c.getProvincia());
            ps.setString(6, c.getMunicipio());
            ps.setString(7, c.getTelefono());
            ps.setString(8, c.getCorreo());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(CentroEmpleador c) {
        String sql = "UPDATE CentroEmpleador SET rnc = ?, nombre = ?, sector = ?, provincia = ?, "
                + "municipio = ?, telefono = ?, correo = ? WHERE codigo = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getRnc());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getSector());
            ps.setString(4, c.getProvincia());
            ps.setString(5, c.getMunicipio());
            ps.setString(6, c.getTelefono());
            ps.setString(7, c.getCorreo());
            ps.setString(8, c.getCodigo());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(String codigo) {
        String sql = "DELETE FROM CentroEmpleador WHERE codigo = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CentroEmpleador buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM CentroEmpleador WHERE codigo = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);
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

    @Override
    public List<CentroEmpleador> listarTodos() {
        List<CentroEmpleador> lista = new ArrayList<>();
        String sql = "SELECT * FROM CentroEmpleador";

        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private CentroEmpleador mapear(ResultSet rs) throws SQLException {
        return new CentroEmpleador(
                rs.getString("codigo"),
                rs.getString("nombre"),
                rs.getString("sector"),
                rs.getString("provincia"),
                rs.getString("municipio"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("rnc")
        );
    }
}