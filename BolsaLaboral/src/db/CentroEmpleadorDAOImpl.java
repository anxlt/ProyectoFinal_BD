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
        // CAMBIO: 'codigo' por 'id_centro' y 'nombre' por 'nombre_centro'
        String sql = "INSERT INTO CentroEmpleador (id_centro, rnc, nombre_centro, sector, id_provincia, id_municipio, telefono, correo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCodigo());
            ps.setString(2, c.getRnc());
            ps.setString(3, c.getNombre());
            ps.setString(4, c.getSector());
            ps.setInt(5, c.getIdProvincia());
            ps.setInt(6, c.getIdMunicipio());
            ps.setString(7, c.getTelefono());
            ps.setString(8, c.getCorreo());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(CentroEmpleador c) {
        // CAMBIO: 'nombre' por 'nombre_centro' y 'codigo' por 'id_centro'
        String sql = "UPDATE CentroEmpleador SET rnc = ?, nombre_centro = ?, sector = ?, id_provincia = ?, "
                + "id_municipio = ?, telefono = ?, correo = ? WHERE id_centro = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getRnc());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getSector());
            ps.setInt(4, c.getIdProvincia());
            ps.setInt(5, c.getIdMunicipio());
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
        // CAMBIO: 'codigo' por 'id_centro'
        String sql = "DELETE FROM CentroEmpleador WHERE id_centro = ?";
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
        // CAMBIO: 'codigo' por 'id_centro'
        String sql = "SELECT * FROM CentroEmpleador WHERE id_centro = ?";
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
                // CAMBIO: extraer los datos usando los nuevos nombres de columna
                rs.getString("id_centro"),
                rs.getString("nombre_centro"),
                rs.getString("sector"),
                rs.getInt("id_provincia"),
                rs.getInt("id_municipio"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("rnc")
        );
    }
}