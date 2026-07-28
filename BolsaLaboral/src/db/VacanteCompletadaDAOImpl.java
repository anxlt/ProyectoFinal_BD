package db;

import logico.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacanteCompletadaDAOImpl implements VacanteCompletadaDAO{

    @Override
    public void insertar(VacanteCompletada v){

        String sql="""
                INSERT INTO VacanteCompletada
                (codigo,fecha,solicitudCodigo,ofertaCodigo)
                VALUES(?,?,?,?)
                """;

        try(Connection con=Conexion.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setString(1,v.getCodigo());
            ps.setDate(2,Date.valueOf(v.getFecha()));
            ps.setString(3,v.getSolicitud().getCodigo());
            ps.setString(4,v.getOferta().getCodigo());

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public VacanteCompletada buscarPorCodigo(String codigo){

        String sql="SELECT * FROM VacanteCompletada WHERE codigo=?";

        try(Connection con=Conexion.conectar();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setString(1,codigo);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                Solicitud solicitud=
                        new SolicitudDAOImpl().buscarPorCodigo(rs.getString("solicitudCodigo"));

                OfertaLaboral oferta=
                        new OfertaLaboralDAOImpl().buscarPorCodigo(rs.getString("ofertaCodigo"));

                return new VacanteCompletada(
                        rs.getString("codigo"),
                        solicitud,
                        oferta,
                        rs.getDate("fecha").toLocalDate()
                );
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<VacanteCompletada> listarTodos(){

        ArrayList<VacanteCompletada> lista=new ArrayList<>();

        String sql="SELECT * FROM VacanteCompletada";

        try(Connection con=Conexion.conectar();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(sql)){

            while(rs.next()){

                Solicitud solicitud=
                        new SolicitudDAOImpl().buscarPorCodigo(rs.getString("solicitudCodigo"));

                OfertaLaboral oferta=
                        new OfertaLaboralDAOImpl().buscarPorCodigo(rs.getString("ofertaCodigo"));

                lista.add(new VacanteCompletada(
                        rs.getString("codigo"),
                        solicitud,
                        oferta,
                        rs.getDate("fecha").toLocalDate()
                ));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return lista;
    }

}