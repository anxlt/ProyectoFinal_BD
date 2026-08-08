package db.DAOImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import logico.OfertaLaboral;
import logico.Solicitud;
import logico.VacanteCompletada;

public class VacanteCompletadaDAOImpl {

    public boolean insertar(VacanteCompletada v) {
        String sql = """
        INSERT INTO VacanteCompletada (fecha_contratacion, id_solicitud, id_oferta)
        OUTPUT INSERTED.id_vacante
        VALUES (?, ?, ?)
    """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(v.getFechaContratacion()));
            ps.setString(2, v.getSolicitudAceptada().getCodigo());
            ps.setString(3, v.getOfertaOcupada().getCodigo());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    v.setCodigo(rs.getString(1)); // código generado por la BD
                }
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public VacanteCompletada buscarPorCodigo(String codigo) {
        // CAMBIO: codigo por id_vacante
        String sql = "SELECT * FROM VacanteCompletada WHERE id_vacante = ?";
        VacanteCompletada v = null;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // CAMBIO: llaves del ResultSet actualizadas (id_solicitud, id_oferta, id_vacante, fecha_contratacion)
                    Solicitud sol = new SolicitudDAOImpl().buscarPorCodigo(rs.getString("id_solicitud"));
                    OfertaLaboral ofer = new OfertaLaboralDAOImpl().buscarPorCodigo(rs.getString("id_oferta"));

                    v = new VacanteCompletada(
                            rs.getString("id_vacante"),
                            sol,
                            ofer,
                            rs.getDate("fecha_contratacion").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    public List<VacanteCompletada> listarTodos() {
        List<VacanteCompletada> lista = new ArrayList<>();
        String sql = "SELECT * FROM VacanteCompletada";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // CAMBIO: llaves del ResultSet actualizadas
                Solicitud sol = new SolicitudDAOImpl().buscarPorCodigo(rs.getString("id_solicitud"));
                OfertaLaboral ofer = new OfertaLaboralDAOImpl().buscarPorCodigo(rs.getString("id_oferta"));

                VacanteCompletada v = new VacanteCompletada(
                        rs.getString("id_vacante"),
                        sol,
                        ofer,
                        rs.getDate("fecha_contratacion").toLocalDate()
                );
                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizar(VacanteCompletada v) {
        // CAMBIO: Nombres de columnas actualizados en el SET y WHERE
        String sql = """
            UPDATE VacanteCompletada 
            SET fecha_contratacion = ?, id_solicitud = ?, id_oferta = ? 
            WHERE id_vacante = ?
        """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(v.getFechaContratacion()));
            ps.setString(2, v.getSolicitudAceptada().getCodigo());
            ps.setString(3, v.getOfertaOcupada().getCodigo());
            ps.setString(4, v.getCodigo());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(String codigo) {
        // CAMBIO: codigo por id_vacante
        String sql = "DELETE FROM VacanteCompletada WHERE id_vacante = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}