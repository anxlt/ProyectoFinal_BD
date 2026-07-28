package db;

import logico.CentroEmpleador;
import logico.OfertaLaboral;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfertaLaboralDAOImpl implements OfertaLaboralDAO {

    @Override
    public void insertar(OfertaLaboral o) {
        // CAMBIO: Se actualizaron los nombres de las columnas al formato de la nueva base de datos
        String sql = "INSERT INTO OfertaLaboral (id_oferta, puesto, descripcion, area, modalidad, jornada, estado, "
                + "salario, experiencia_minima, vacantes, id_centro, ofrece_reubicacion, obligatorio_mayor_edad, "
                + "obligatorio_licencia, nivel_academico, porcentaje_minimo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
        // CAMBIO: Se actualizaron los nombres de las columnas
        String sql = "UPDATE OfertaLaboral SET puesto=?, descripcion=?, area=?, modalidad=?, jornada=?, estado=?, "
                + "salario=?, experiencia_minima=?, vacantes=?, id_centro=?, ofrece_reubicacion=?, "
                + "obligatorio_mayor_edad=?, obligatorio_licencia=?, nivel_academico=?, porcentaje_minimo=? WHERE id_oferta=?";

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

            // CAMBIO: id_oferta en lugar de ofertaCodigo
            try (PreparedStatement del = con.prepareStatement("DELETE FROM OfertaRequisito WHERE id_oferta = ?")) {
                del.setString(1, o.getCodigo());
                del.executeUpdate();
            }
            insertarRequisitos(con, o.getCodigo(), o.getRequisitos());

            // CAMBIO: id_oferta en lugar de ofertaCodigo
            try (PreparedStatement del = con.prepareStatement("DELETE FROM OfertaIdioma WHERE id_oferta = ?")) {
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
        // CAMBIO: id_requisito, descripcion_requisito y id_oferta
        String buscar = "SELECT id_requisito FROM Requisito WHERE descripcion_requisito = ?";
        String insertar = "INSERT INTO Requisito(descripcion_requisito) VALUES(?)";
        String relacion = "INSERT INTO OfertaRequisito(id_oferta, id_requisito) VALUES(?, ?)";

        for (String requisito : requisitos) {
            int idRequisito;
            try (PreparedStatement ps = con.prepareStatement(buscar)) {
                ps.setString(1, requisito);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idRequisito = rs.getInt("id_requisito"); // CAMBIO
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
        // CAMBIO: id_idioma, nombre_idioma y id_oferta
        String buscar = "SELECT id_idioma FROM Idioma WHERE nombre_idioma = ?";
        String insertar = "INSERT INTO Idioma(nombre_idioma) VALUES(?)";
        String relacion = "INSERT INTO OfertaIdioma(id_oferta, id_idioma) VALUES(?, ?)";

        for (String idioma : idiomas) {
            int idIdioma;
            try (PreparedStatement ps = con.prepareStatement(buscar)) {
                ps.setString(1, idioma);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idIdioma = rs.getInt("id_idioma"); // CAMBIO
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

            // CAMBIO: ofertaCodigo por id_oferta
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM OfertaIdioma WHERE id_oferta=?")) {
                ps.setString(1, codigo);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement("DELETE FROM OfertaRequisito WHERE id_oferta=?")) {
                ps.setString(1, codigo);
                ps.executeUpdate();
            }

            // CAMBIO: codigo por id_oferta
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
        // CAMBIO: codigo por id_oferta
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
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(con, rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private OfertaLaboral mapear(Connection con, ResultSet rs) throws SQLException {
        // CAMBIO: Se actualizaron los nombres con los que se obtiene la informacion en la BD
        String codigo = rs.getString("id_oferta");
        String ofertadorCodigo = rs.getString("id_centro");
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
                rs.getInt("experiencia_minima"),
                rs.getInt("vacantes"),
                ofertador,
                rs.getBoolean("ofrece_reubicacion"),
                rs.getBoolean("obligatorio_mayor_edad"),
                rs.getBoolean("obligatorio_licencia"),
                rs.getString("nivel_academico"),
                requisitos,
                idiomas,
                rs.getInt("porcentaje_minimo")
        );
    }

    private ArrayList<String> obtenerRequisitos(Connection con, String codigo) throws SQLException {
        ArrayList<String> lista = new ArrayList<>();

        // CAMBIO: id_requisito, descripcion_requisito y id_oferta
        String sql =
                "SELECT R.descripcion_requisito " +
                        "FROM OfertaRequisito ORQ " +
                        "INNER JOIN Requisito R ON ORQ.id_requisito = R.id_requisito " +
                        "WHERE ORQ.id_oferta = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("descripcion_requisito")); // CAMBIO
                }
            }
        }
        return lista;
    }

    private ArrayList<String> obtenerIdiomas(Connection con, String codigo) throws SQLException {
        ArrayList<String> lista = new ArrayList<>();

        // CAMBIO: id_idioma, nombre_idioma y id_oferta
        String sql =
                "SELECT I.nombre_idioma " +
                        "FROM OfertaIdioma OI " +
                        "INNER JOIN Idioma I ON OI.id_idioma = I.id_idioma " +
                        "WHERE OI.id_oferta = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("nombre_idioma")); // CAMBIO
                }
            }
        }
        return lista;
    }
}