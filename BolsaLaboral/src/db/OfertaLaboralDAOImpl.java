package db;

import logico.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfertaLaboralDAOImpl implements OfertaLaboralDAO {

    @Override
    public void insertar(OfertaLaboral o) {
        String sql = "INSERT INTO OfertaLaboral (puesto, descripcion, area, modalidad, jornada, estado, "
                + "salario, experiencia_minima, vacantes, id_centro, ofrece_reubicacion, obligatorio_mayor_edad, "
                + "obligatorio_licencia, nivel_academico, porcentaje_minimo, id_carrera, id_area_tecnica, id_habilidad) "
                + "OUTPUT INSERTED.id_oferta "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, o.getPuesto());
                ps.setString(2, o.getDescripcion());
                ps.setString(3, o.getArea());
                ps.setString(4, o.getModalidad());
                ps.setString(5, o.getJornada());
                ps.setString(6, o.getEstado());
                ps.setFloat(7, o.getSalario());
                ps.setInt(8, o.getExperienciaMinima());
                ps.setInt(9, o.getVacantes());
                ps.setString(10, o.getOfertador().getCodigo());
                ps.setBoolean(11, o.isOfreceReubicacion());
                ps.setBoolean(12, o.isObligatorioMayorDeEdad());
                ps.setBoolean(13, o.isObligatorioLicencia());
                ps.setString(14, o.getNivelAcademico());
                ps.setInt(15, o.getPorcentajeMinimo());
                setNullableInt(ps, 16, o.getIdCarrera());
                setNullableInt(ps, 17, o.getIdAreaTecnica());
                setNullableInt(ps, 18, o.getIdHabilidad());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        o.setCodigo(rs.getString(1)); // código generado por la BD
                    }
                }
            }

            insertarIdiomas(con, o.getCodigo(), o.getIdiomasRequeridas());
            con.commit();

        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public void actualizar(OfertaLaboral o) {
        String sql = "UPDATE OfertaLaboral SET puesto=?, descripcion=?, area=?, modalidad=?, jornada=?, estado=?, "
                + "salario=?, experiencia_minima=?, vacantes=?, id_centro=?, ofrece_reubicacion=?, "
                + "obligatorio_mayor_edad=?, obligatorio_licencia=?, nivel_academico=?, porcentaje_minimo=?, "
                + "id_carrera=?, id_area_tecnica=?, id_habilidad=? WHERE id_oferta=?";

        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, o.getPuesto());
                ps.setString(2, o.getDescripcion());
                ps.setString(3, o.getArea());
                ps.setString(4, o.getModalidad());
                ps.setString(5, o.getJornada());
                ps.setString(6, o.getEstado());
                ps.setFloat(7, o.getSalario());
                ps.setInt(8, o.getExperienciaMinima());
                ps.setInt(9, o.getVacantes());
                ps.setString(10, o.getOfertador().getCodigo());
                ps.setBoolean(11, o.isOfreceReubicacion());
                ps.setBoolean(12, o.isObligatorioMayorDeEdad());
                ps.setBoolean(13, o.isObligatorioLicencia());
                ps.setString(14, o.getNivelAcademico());
                ps.setInt(15, o.getPorcentajeMinimo());
                setNullableInt(ps, 16, o.getIdCarrera());
                setNullableInt(ps, 17, o.getIdAreaTecnica());
                setNullableInt(ps, 18, o.getIdHabilidad());
                ps.setString(19, o.getCodigo());
                ps.executeUpdate();
            }

            try (PreparedStatement del = con.prepareStatement("DELETE FROM OfertaIdioma WHERE id_oferta=?")) {
                del.setString(1, o.getCodigo());
                del.executeUpdate();
            }
            insertarIdiomas(con, o.getCodigo(), o.getIdiomasRequeridas());

            con.commit();

        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private void setNullableInt(PreparedStatement ps, int index, Integer value) throws SQLException {
        if (value == null) {
            ps.setNull(index, Types.INTEGER);
        } else {
            ps.setInt(index, value);
        }
    }

    private void insertarIdiomas(Connection con, String codigo, List<String> idiomas) throws SQLException {
        if (idiomas == null) return;

        String buscar = "SELECT id_idioma FROM Idioma WHERE nombre_idioma = ?";
        String insertar = "INSERT INTO Idioma(nombre_idioma) VALUES(?)";
        String relacion = "INSERT INTO OfertaIdioma(id_oferta, id_idioma) VALUES(?, ?)";

        for (String idioma : idiomas) {
            int idIdioma;
            try (PreparedStatement ps = con.prepareStatement(buscar)) {
                ps.setString(1, idioma);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idIdioma = rs.getInt("id_idioma");
                    } else {
                        try (PreparedStatement psIns = con.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS)) {
                            psIns.setString(1, idioma);
                            psIns.executeUpdate();
                            try (ResultSet claves = psIns.getGeneratedKeys()) {
                                claves.next();
                                idIdioma = claves.getInt(1);
                            }
                        }
                    }
                }
            }
            try (PreparedStatement psRel = con.prepareStatement(relacion)) {
                psRel.setString(1, codigo);
                psRel.setInt(2, idIdioma);
                psRel.executeUpdate();
            }
        }
    }

    @Override
    public void eliminar(String codigo) {
        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement("DELETE FROM OfertaIdioma WHERE id_oferta=?")) {
                ps.setString(1, codigo);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM OfertaLaboral WHERE id_oferta=?")) {
                ps.setString(1, codigo);
                ps.executeUpdate();
            }

            con.commit();
        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public OfertaLaboral buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM OfertaLaboral WHERE id_oferta = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(con, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OfertaLaboral> listarTodos() {
        List<OfertaLaboral> lista = new ArrayList<>();
        String sql = "SELECT * FROM OfertaLaboral";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(con, rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private OfertaLaboral mapear(Connection con, ResultSet rs) throws SQLException {
        String codigo = rs.getString("id_oferta");
        String idCentro = rs.getString("id_centro");

        CentroEmpleador centro = new CentroEmpleadorDAOImpl().buscarPorCodigo(idCentro);
        ArrayList<String> idiomas = obtenerIdiomas(con, codigo);

        Integer idCarrera = (Integer) rs.getObject("id_carrera");
        Integer idAreaTecnica = (Integer) rs.getObject("id_area_tecnica");
        Integer idHabilidad = (Integer) rs.getObject("id_habilidad");

        return new OfertaLaboral(
                codigo,
                rs.getString("puesto"),
                rs.getString("descripcion"),
                rs.getString("area"),
                rs.getString("modalidad"),
                rs.getString("jornada"),
                rs.getString("estado"),
                rs.getFloat("salario"),
                rs.getInt("experiencia_minima"),
                rs.getInt("vacantes"),
                centro,
                rs.getBoolean("ofrece_reubicacion"),
                rs.getBoolean("obligatorio_mayor_edad"),
                rs.getBoolean("obligatorio_licencia"),
                rs.getString("nivel_academico"),
                idCarrera,
                idAreaTecnica,
                idHabilidad,
                idiomas,
                rs.getInt("porcentaje_minimo")
        );
    }

    private ArrayList<String> obtenerIdiomas(Connection con, String codigo) throws SQLException {
        ArrayList<String> lista = new ArrayList<>();
        String sql = "SELECT I.nombre_idioma FROM OfertaIdioma OI "
                + "INNER JOIN Idioma I ON OI.id_idioma = I.id_idioma WHERE OI.id_oferta = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(rs.getString("nombre_idioma"));
            }
        }
        return lista;
    }
}