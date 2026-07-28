package db;

import logico.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDAOImpl implements SolicitudDAO {

    @Override
    public void insertar(Solicitud s) {

        String sql = """
                INSERT INTO Solicitud
                (codigo,fechaSolicitud,estado,candidatoCodigo,ofertaCodigo)
                VALUES(?,?,?,?,?)
                """;

        try(Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1,s.getCodigo());
            ps.setDate(2,Date.valueOf(s.getFechaSolicitud()));
            ps.setString(3,s.getEstado());
            ps.setString(4,s.getSolicitante().getCodigo());
            ps.setString(5,s.getOfertaSolicitada().getCodigo());

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void actualizar(Solicitud s) {

        String sql="""
                UPDATE Solicitud
                SET fechaSolicitud=?,
                    estado=?,
                    candidatoCodigo=?,
                    ofertaCodigo=?
                WHERE codigo=?
                """;

        try(Connection con=Conexion.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setDate(1,Date.valueOf(s.getFechaSolicitud()));
            ps.setString(2,s.getEstado());
            ps.setString(3,s.getSolicitante().getCodigo());
            ps.setString(4,s.getOfertaSolicitada().getCodigo());
            ps.setString(5,s.getCodigo());

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void eliminar(String codigo) {

        String sql="DELETE FROM Solicitud WHERE codigo=?";

        try(Connection con=Conexion.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setString(1,codigo);
            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public Solicitud buscarPorCodigo(String codigo) {

        String sql="SELECT * FROM Solicitud WHERE codigo=?";

        try(Connection con=Conexion.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setString(1,codigo);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                Candidato candidato=
                        new CandidatoDAOImpl().buscarPorCodigo(rs.getString("candidatoCodigo"));

                OfertaLaboral oferta=
                        new OfertaLaboralDAOImpl().buscarPorCodigo(rs.getString("ofertaCodigo"));

                return new Solicitud(
                        rs.getString("codigo"),
                        rs.getDate("fechaSolicitud").toLocalDate(),
                        rs.getString("estado"),
                        candidato,
                        oferta
                );
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Solicitud> listarTodos() {

        ArrayList<Solicitud> lista=new ArrayList<>();

        String sql="SELECT * FROM Solicitud";

        try(Connection con=Conexion.conectar();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(sql)){

            while(rs.next()){

                Candidato candidato=
                        new CandidatoDAOImpl().buscarPorCodigo(rs.getString("candidatoCodigo"));

                OfertaLaboral oferta=
                        new OfertaLaboralDAOImpl().buscarPorCodigo(rs.getString("ofertaCodigo"));

                lista.add(new Solicitud(
                        rs.getString("codigo"),
                        rs.getDate("fechaSolicitud").toLocalDate(),
                        rs.getString("estado"),
                        candidato,
                        oferta
                ));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return lista;
    }

}