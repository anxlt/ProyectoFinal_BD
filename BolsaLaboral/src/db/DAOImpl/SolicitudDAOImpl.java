package db.DAOImpl;

import db.Conexion;
import db.DAO.SolicitudDAO;
import logico.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDAOImpl implements SolicitudDAO {

    @Override
    public void insertar(Solicitud s) {
        String sql = """
            INSERT INTO Solicitud
            (fecha_solicitud, estado_solicitud, id_candidato, id_oferta)
            OUTPUT INSERTED.id_solicitud
            VALUES(?,?,?,?)
            """;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(s.getFechaSolicitud()));
            ps.setString(2, s.getEstado());
            ps.setString(3, s.getSolicitante().getCodigo());
            ps.setString(4, s.getOfertaSolicitada().getCodigo());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s.setCodigo(rs.getString(1)); // código generado por la BD
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Solicitud s) {
        // CAMBIO: Se actualizaron los nombres de las columnas y la condición WHERE
        String sql="""
                UPDATE Solicitud
                SET fecha_solicitud=?,
                    estado_solicitud=?,
                    id_candidato=?,
                    id_oferta=?
                WHERE id_solicitud=?
                """;

        try(Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setDate(1, Date.valueOf(s.getFechaSolicitud()));
            ps.setString(2, s.getEstado());
            ps.setString(3, s.getSolicitante().getCodigo());
            ps.setString(4, s.getOfertaSolicitada().getCodigo());
            ps.setString(5, s.getCodigo());

            ps.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(String codigo) {
        // CAMBIO: codigo por id_solicitud
        String sql="DELETE FROM Solicitud WHERE id_solicitud=?";

        try(Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, codigo);
            ps.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Solicitud buscarPorCodigo(String codigo) {
        // CAMBIO: codigo por id_solicitud
        String sql="SELECT * FROM Solicitud WHERE id_solicitud=?";

        try(Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                // CAMBIO: Se cambiaron las llaves para extraer el candidato y la oferta
                Candidato candidato =
                        new CandidatoDAOImpl().buscarPorCodigo(rs.getString("id_candidato"));

                OfertaLaboral oferta =
                        new OfertaLaboralDAOImpl().buscarPorCodigo(rs.getString("id_oferta"));

                return new Solicitud(
                        rs.getString("id_solicitud"),          // CAMBIO
                        rs.getDate("fecha_solicitud").toLocalDate(), // CAMBIO
                        rs.getString("estado_solicitud"),      // CAMBIO
                        candidato,
                        oferta
                );
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Solicitud> listarTodos() {

        ArrayList<Solicitud> lista = new ArrayList<>();

        String sql="SELECT * FROM Solicitud";

        try(Connection con = Conexion.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){

            while(rs.next()){
                // CAMBIO: Se cambiaron las llaves para extraer el candidato y la oferta
                Candidato candidato =
                        new CandidatoDAOImpl().buscarPorCodigo(rs.getString("id_candidato"));

                OfertaLaboral oferta =
                        new OfertaLaboralDAOImpl().buscarPorCodigo(rs.getString("id_oferta"));

                lista.add(new Solicitud(
                        rs.getString("id_solicitud"),          // CAMBIO
                        rs.getDate("fecha_solicitud").toLocalDate(), // CAMBIO
                        rs.getString("estado_solicitud"),      // CAMBIO
                        candidato,
                        oferta
                ));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}