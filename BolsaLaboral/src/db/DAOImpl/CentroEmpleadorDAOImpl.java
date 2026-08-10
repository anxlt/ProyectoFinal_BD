package db.DAOImpl;

import db.Conexion;
import db.DAO.CentroEmpleadorDAO;
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
        String sql = "INSERT INTO CentroEmpleador (rnc, nombre_centro, sector, id_municipio, telefono, correo) "
                + "OUTPUT INSERTED.id_centro "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getRnc());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getSector());
            ps.setInt(4, c.getIdMunicipio());
            ps.setString(5, c.getTelefono());
            ps.setString(6, c.getCorreo());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c.setCodigo(rs.getString(1)); // código generado por la BD
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actualizar(CentroEmpleador c) {
        // CAMBIO: 'nombre' por 'nombre_centro' y 'codigo' por 'id_centro'
        String sql = "UPDATE CentroEmpleador SET rnc = ?, nombre_centro = ?, sector = ?, "
                + "id_municipio = ?, telefono = ?, correo = ? WHERE id_centro = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getRnc());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getSector());
            ps.setInt(4, c.getIdMunicipio());
            ps.setString(5, c.getTelefono());
            ps.setString(6, c.getCorreo());
            ps.setString(7, c.getCodigo());
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
        int idMunicipio = rs.getInt("id_municipio");
        // Provincia derivada del municipio
        int idProvincia = 0;
        logico.Municipio mun = new MunicipioDAOImpl().buscarPorId(idMunicipio);
        if (mun != null) {
            idProvincia = mun.getIdProvincia();
        }
        return new CentroEmpleador(
                rs.getString("id_centro"),
                rs.getString("nombre_centro"),
                rs.getString("sector"),
                idProvincia,
                idMunicipio,
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("rnc")
        );
    }
}