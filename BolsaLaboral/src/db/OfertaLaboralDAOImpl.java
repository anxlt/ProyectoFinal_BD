package db;

import logico.CentroEmpleador;
import logico.OfertaLaboral;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfertaLaboralDAOImpl implements OfertaLaboralDAO {

    @Override
    public void insertar(OfertaLaboral o) {
        String sql = "INSERT INTO OfertaLaboral (codigo, puesto, descripcion, area, modalidad, jornada, estado, "
                + "salario, experienciaMinima, vacantes, ofertadorCodigo, ofreceReubicacion, obligatorioMayorDeEdad, "
                + "obligatorioLicencia, nivelAcademico, porcentajeMinimo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, o.getCodigo());
                ps.setString(2, o.getPuesto());
                ps.setString(3, o.getDescripcion());
                ps.setString(4, o.getArea());
                ps.setString(5, o.getModalidad());
                ps.setString(6, o.getJornada());
                ps.setString(7, o.getEstado());
                ps.setFloat(8, o.getSalario());
                ps.setInt(9, o.getExperienciaMinima());
                ps.setInt(10, o.getVacantes());
                ps.setString(11, o.getOfertador().getCodigo());
                ps.setBoolean(12, o.isOfreceReubicacion());
                ps.setBoolean(13, o.isObligatorioMayorDeEdad());
                ps.setBoolean(14, o.isobligatorioLicencia());
                ps.setString(15, o.getNivelAcademico());
                ps.setInt(16, o.getPorcentajeMinimo());
                ps.executeUpdate();
            }

            insertarRequisitos(con, o.getCodigo(), o.getRequisitos());
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
                + "salario=?, experienciaMinima=?, vacantes=?, ofertadorCodigo=?, ofreceReubicacion=?, "
                + "obligatorioMayorDeEdad=?, obligatorioLicencia=?, nivelAcademico=?, porcentajeMinimo=? WHERE codigo=?";

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
                ps.setBoolean(13, o.isobligatorioLicencia());
                ps.setString(14, o.getNivelAcademico());
                ps.setInt(15, o.getPorcentajeMinimo());
                ps.setString(16, o.getCodigo());
                ps.executeUpdate();
            }

            try (PreparedStatement del = con.prepareStatement("DELETE FROM OfertaRequisito WHERE ofertaCodigo = ?")) {
                del.setString(1, o.getCodigo());
                del.executeUpdate();
            }
            insertarRequisitos(con, o.getCodigo(), o.getRequisitos());

            try (PreparedStatement del = con.prepareStatement("DELETE FROM OfertaIdioma WHERE ofertaCodigo = ?")) {
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

    private void insertarRequisitos(Connection con, String codigo, List<String> requisitos) throws SQLException {

        String buscar = "SELECT id FROM Requisito WHERE descripcion = ?";
        String insertar = "INSERT INTO Requisito(descripcion) VALUES(?)";
        String relacion = "INSERT INTO OfertaRequisito(ofertaCodigo, requisitoId) VALUES(?, ?)";

        for (String requisito : requisitos) {

            int idRequisito;

            try (PreparedStatement ps = con.prepareStatement(buscar)) {

                ps.setString(1, requisito);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        idRequisito = rs.getInt("id");

                    } else {

                        try (PreparedStatement psInsert = con.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS)) {

                            psInsert.setString(1, requisito);
                            psInsert.executeUpdate();

                            try (ResultSet claves = psInsert.getGeneratedKeys()) {

                                claves.next();
                                idRequisito = claves.getInt(1);

                            }
                        }
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(relacion)) {

                ps.setString(1, codigo);
                ps.setInt(2, idRequisito);

                ps.executeUpdate();
            }
        }
    }

    private void insertarIdiomas(Connection con, String codigo, List<String> idiomas) throws SQLException {

        String buscar = "SELECT id FROM Idioma WHERE nombre = ?";
        String insertar = "INSERT INTO Idioma(nombre) VALUES(?)";
        String relacion = "INSERT INTO OfertaIdioma(ofertaCodigo, idiomaId) VALUES(?, ?)";

        for (String idioma : idiomas) {

            int idIdioma;

            try (PreparedStatement ps = con.prepareStatement(buscar)) {

                ps.setString(1, idioma);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        idIdioma = rs.getInt("id");

                    } else {

                        try (PreparedStatement psInsert = con.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS)) {

                            psInsert.setString(1, idioma);

                            psInsert.executeUpdate();

                            try (ResultSet claves = psInsert.getGeneratedKeys()) {

                                claves.next();

                                idIdioma = claves.getInt(1);

                            }
                        }
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(relacion)) {

                ps.setString(1, codigo);

                ps.setInt(2, idIdioma);

                ps.executeUpdate();
            }
        }
    }

    @Override
    public void eliminar(String codigo) {

        Connection con = null;

        try {

            con = Conexion.conectar();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM OfertaIdioma WHERE ofertaCodigo=?")) {

                ps.setString(1, codigo);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM OfertaRequisito WHERE ofertaCodigo=?")) {

                ps.setString(1, codigo);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM OfertaLaboral WHERE codigo=?")) {

                ps.setString(1, codigo);
                ps.executeUpdate();
            }

            con.commit();

        } catch (SQLException e) {

            try {

                if (con != null)
                    con.rollback();

            } catch (SQLException ex) {

                ex.printStackTrace();

            }

            e.printStackTrace();

        } finally {

            try {

                if (con != null) {

                    con.setAutoCommit(true);
                    con.close();

                }

            } catch (SQLException e) {

                e.printStackTrace();

            }
        }
    }

    @Override
    public OfertaLaboral buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM OfertaLaboral WHERE codigo = ?";
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
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(con, rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private OfertaLaboral mapear(Connection con, ResultSet rs) throws SQLException {
        String codigo = rs.getString("codigo");
        String ofertadorCodigo = rs.getString("ofertadorCodigo");
        CentroEmpleador ofertador = new CentroEmpleadorDAOImpl().buscarPorCodigo(ofertadorCodigo);

        ArrayList<String> requisitos = obtenerRequisitos(con, codigo);
        ArrayList<String> idiomas = obtenerIdiomas(con, codigo);

        return new OfertaLaboral(
                codigo,
                rs.getString("puesto"),
                rs.getString("descripcion"),
                rs.getString("area"),
                rs.getString("modalidad"),
                rs.getString("jornada"),
                rs.getString("estado"),
                rs.getFloat("salario"),
                rs.getInt("experienciaMinima"),
                rs.getInt("vacantes"),
                ofertador,
                rs.getBoolean("ofreceReubicacion"),
                rs.getBoolean("obligatorioMayorDeEdad"),
                rs.getBoolean("obligatorioLicencia"),
                rs.getString("nivelAcademico"),
                requisitos,
                idiomas,
                rs.getInt("porcentajeMinimo")
        );
    }

    private ArrayList<String> obtenerRequisitos(Connection con, String codigo) throws SQLException {

        ArrayList<String> lista = new ArrayList<>();

        String sql =
                "SELECT R.descripcion " +
                        "FROM OfertaRequisito ORQ " +
                        "INNER JOIN Requisito R ON ORQ.requisitoId = R.id " +
                        "WHERE ORQ.ofertaCodigo = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    lista.add(rs.getString("descripcion"));

                }
            }
        }

        return lista;
    }
    private ArrayList<String> obtenerIdiomas(Connection con, String codigo) throws SQLException {

        ArrayList<String> lista = new ArrayList<>();

        String sql =
                "SELECT I.nombre " +
                        "FROM OfertaIdioma OI " +
                        "INNER JOIN Idioma I ON OI.idiomaId = I.id " +
                        "WHERE OI.ofertaCodigo = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    lista.add(rs.getString("nombre"));

                }
            }
        }

        return lista;
    }
}