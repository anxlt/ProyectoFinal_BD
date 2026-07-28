package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logico.OfertaLaboral;
import logico.Solicitud;
import logico.VacanteCompletada;

public class VacanteCompletadaDAOImpl {

    public boolean insertar(VacanteCompletada v) {
        String sql = """
            INSERT INTO VacanteCompletada (codigo, fechaContratacion, solicitudCodigo, ofertaCodigo)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getCodigo());
            ps.setDate(2, Date.valueOf(v.getFechaContratacion()));
            ps.setString(3, v.getSolicitudAceptada().getCodigo());
            ps.setString(4, v.getOfertaOcupada().getCodigo());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public VacanteCompletada buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM VacanteCompletada WHERE codigo = ?";
        VacanteCompletada v = null;

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Solicitud sol = new SolicitudDAOImpl().buscarPorCodigo(rs.getString("solicitudCodigo"));
                    OfertaLaboral ofer = new OfertaLaboralDAOImpl().buscarPorCodigo(rs.getString("ofertaCodigo"));

                    v = new VacanteCompletada(
                            rs.getString("codigo"),
                            sol,
                            ofer,
                            rs.getDate("fechaContratacion").toLocalDate()
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
                Solicitud sol = new SolicitudDAOImpl().buscarPorCodigo(rs.getString("solicitudCodigo"));
                OfertaLaboral ofer = new OfertaLaboralDAOImpl().buscarPorCodigo(rs.getString("ofertaCodigo"));

                VacanteCompletada v = new VacanteCompletada(
                        rs.getString("codigo"),
                        sol,
                        ofer,
                        rs.getDate("fechaContratacion").toLocalDate()
                );
                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizar(VacanteCompletada v) {
        String sql = """
            UPDATE VacanteCompletada 
            SET fechaContratacion = ?, solicitudCodigo = ?, ofertaCodigo = ? 
            WHERE codigo = ?
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
        String sql = "DELETE FROM VacanteCompletada WHERE codigo = ?";

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