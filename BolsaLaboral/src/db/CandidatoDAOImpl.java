package db;

import logico.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CandidatoDAOImpl implements CandidatoDAO {

    @Override
    public void insertar(Candidato c) {
        String sql = "INSERT INTO Candidato (id_candidato, identificacion, nombres, apellidos, fecha_nacimiento, "
                + "genero, id_provincia, id_municipio, telefono, correo, jornada, modalidad, area_interes, "
                + "aspiracion_salarial, licencia_conducir, disposicion_mudarse, estado, tipo_candidato, "
                + "universidad, carrera, nivel_academico, area_tecnica, anios_experiencia) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, c.getCodigo());
                ps.setString(2, c.getIdentificacion());
                ps.setString(3, c.getNombres());
                ps.setString(4, c.getApellidos());
                ps.setDate(5, Date.valueOf(c.getFechaNacimiento()));
                ps.setString(6, c.getGenero());
                ps.setInt(7, c.getIdProvincia());
                ps.setInt(8, c.getIdMunicipio());
                ps.setString(9, c.getTelefono());
                ps.setString(10, c.getCorreo());
                ps.setString(11, c.getJornada());
                ps.setString(12, c.getModalidad());
                ps.setString(13, c.getAreaDeInteres());
                ps.setFloat(14, c.getAspiracionSalarial());
                ps.setBoolean(15, c.isLicenciaConducir());
                ps.setBoolean(16, c.isDisposicionMudarse());
                ps.setString(17, c.getEstado());

                if (c instanceof Universitario u) {
                    ps.setString(18, "UNIVERSITARIO");
                    ps.setString(19, u.getUniversidad());
                    ps.setString(20, u.getCarrera());
                    ps.setString(21, u.getNivelAcademico());
                    ps.setNull(22, Types.VARCHAR);
                    ps.setNull(23, Types.INTEGER);

                } else if (c instanceof TecnicoSuperior t) {
                    ps.setString(18, "TECNICO");
                    ps.setNull(19, Types.VARCHAR);
                    ps.setNull(20, Types.VARCHAR);
                    ps.setNull(21, Types.VARCHAR);
                    ps.setString(22, t.getAreaTecnica());
                    ps.setInt(23, t.getAniosExperiencia());

                } else { // Obrero
                    ps.setString(18, "OBRERO");
                    ps.setNull(19, Types.VARCHAR);
                    ps.setNull(20, Types.VARCHAR);
                    ps.setNull(21, Types.VARCHAR);
                    ps.setNull(22, Types.VARCHAR);
                    ps.setNull(23, Types.INTEGER);
                }

                ps.executeUpdate();
            }

            insertarIdiomas(con, c.getCodigo(), c.getIdiomas());

            if (c instanceof Obrero o) {
                insertarHabilidades(con, c.getCodigo(), o.getHabilidades());
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
    public void actualizar(Candidato c) {
        String sql = "UPDATE Candidato SET identificacion = ?, nombres = ?, apellidos = ?, fecha_nacimiento = ?, "
                + "genero = ?, id_provincia = ?, id_municipio = ?, telefono = ?, correo = ?, jornada = ?, modalidad = ?, "
                + "area_interes = ?, aspiracion_salarial = ?, licencia_conducir = ?, disposicion_mudarse = ?, "
                + "estado = ?, universidad = ?, carrera = ?, nivel_academico = ?, area_tecnica = ?, anios_experiencia = ? "
                + "WHERE id_candidato = ?";

        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, c.getIdentificacion());
                ps.setString(2, c.getNombres());
                ps.setString(3, c.getApellidos());
                ps.setDate(4, Date.valueOf(c.getFechaNacimiento()));
                ps.setString(5, c.getGenero());
                ps.setInt(6, c.getIdProvincia());
                ps.setInt(7, c.getIdMunicipio());
                ps.setString(8, c.getTelefono());
                ps.setString(9, c.getCorreo());
                ps.setString(10, c.getJornada());
                ps.setString(11, c.getModalidad());
                ps.setString(12, c.getAreaDeInteres());
                ps.setFloat(13, c.getAspiracionSalarial());
                ps.setBoolean(14, c.isLicenciaConducir());
                ps.setBoolean(15, c.isDisposicionMudarse());
                ps.setString(16, c.getEstado());

                if (c instanceof Universitario u) {
                    ps.setString(17, u.getUniversidad());
                    ps.setString(18, u.getCarrera());
                    ps.setString(19, u.getNivelAcademico());
                    ps.setNull(20, Types.VARCHAR);
                    ps.setNull(21, Types.INTEGER);
                } else if (c instanceof TecnicoSuperior t) {
                    ps.setNull(17, Types.VARCHAR);
                    ps.setNull(18, Types.VARCHAR);
                    ps.setNull(19, Types.VARCHAR);
                    ps.setString(20, t.getAreaTecnica());
                    ps.setInt(21, t.getAniosExperiencia());
                } else { // Obrero
                    ps.setNull(17, Types.VARCHAR);
                    ps.setNull(18, Types.VARCHAR);
                    ps.setNull(19, Types.VARCHAR);
                    ps.setNull(20, Types.VARCHAR);
                    ps.setNull(21, Types.INTEGER);
                }

                ps.setString(22, c.getCodigo());
                ps.executeUpdate();
            }

            // Idiomas: se borran y se reinsertan tal como quedaron
            try (PreparedStatement del = con.prepareStatement(
                    "DELETE FROM CandidatoIdioma WHERE id_candidato = ?")) {
                del.setString(1, c.getCodigo());
                del.executeUpdate();
            }
            insertarIdiomas(con, c.getCodigo(), c.getIdiomas());

            // Habilidades: solo aplica si es Obrero
            try (PreparedStatement del = con.prepareStatement(
                    "DELETE FROM CandidatoHabilidad WHERE id_candidato = ?")) {
                del.setString(1, c.getCodigo());
                del.executeUpdate();
            }
            if (c instanceof Obrero o) {
                insertarHabilidades(con, c.getCodigo(), o.getHabilidades());
            }

            con.commit();

        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
    }


    private void insertarIdiomas(Connection con, String codigo, List<String> idiomas) throws SQLException {

        String buscarIdioma = "SELECT id_idioma FROM Idioma WHERE nombre_idioma = ?";
        String insertarIdioma = "INSERT INTO Idioma(nombre_idioma) VALUES(?)";
        String insertarRelacion = "INSERT INTO CandidatoIdioma(id_candidato, id_idioma) VALUES(?, ?)";

        for (String idioma : idiomas) {

            int idIdioma;

            try (PreparedStatement psBuscar = con.prepareStatement(buscarIdioma)) {
                psBuscar.setString(1, idioma);

                try (ResultSet rs = psBuscar.executeQuery()) {

                    if (rs.next()) {

                        idIdioma = rs.getInt("id_idioma");

                    } else {

                        try (PreparedStatement psInsert = con.prepareStatement(
                                insertarIdioma,
                                Statement.RETURN_GENERATED_KEYS)) {

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

            try (PreparedStatement psRelacion = con.prepareStatement(insertarRelacion)) {
                psRelacion.setString(1, codigo);
                psRelacion.setInt(2, idIdioma);
                psRelacion.executeUpdate();
            }
        }
    }

    private void insertarHabilidades(Connection con, String codigo, List<String> habilidades) throws SQLException {
        String sql = "INSERT INTO CandidatoHabilidad (id_candidato, habilidad) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (String hab : habilidades) {
                ps.setString(1, codigo);
                ps.setString(2, hab);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public void eliminar(String codigo) {

        Connection con = null;

        try {

            con = Conexion.conectar();
            con.setAutoCommit(false);

            PreparedStatement ps1 =
                    con.prepareStatement("DELETE FROM CandidatoIdioma WHERE id_candidato=?");
            ps1.setString(1, codigo);
            ps1.executeUpdate();

            PreparedStatement ps2 =
                    con.prepareStatement("DELETE FROM CandidatoHabilidad WHERE id_candidato=?");
            ps2.setString(1, codigo);
            ps2.executeUpdate();

            PreparedStatement ps3 =
                    con.prepareStatement("DELETE FROM Candidato WHERE id_candidato=?");
            ps3.setString(1, codigo);
            ps3.executeUpdate();

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
    public void actualizarEstado(String codigo, String estado) {
        String sql = "UPDATE Candidato SET estado = ? WHERE id_candidato = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setString(2, codigo);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Candidato buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM Candidato WHERE id_candidato = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(con, rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Candidato> listarTodos() {
        List<Candidato> lista = new ArrayList<>();
        String sql = "SELECT * FROM Candidato";

        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapear(con, rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Candidato mapear(Connection con, ResultSet rs) throws SQLException {
        String codigo = rs.getString("id_candidato");
        String identificacion = rs.getString("identificacion");
        String nombres = rs.getString("nombres");
        String apellidos = rs.getString("apellidos");
        LocalDate fechaNacimiento = rs.getDate("fecha_nacimiento").toLocalDate();
        String genero = rs.getString("genero");
        int idProvincia = rs.getInt("id_provincia");
        int idMunicipio = rs.getInt("id_municipio");
        String telefono = rs.getString("telefono");
        String correo = rs.getString("correo");
        String jornada = rs.getString("jornada");
        String modalidad = rs.getString("modalidad");
        String areaDeInteres = rs.getString("area_interes");
        float aspiracionSalarial = rs.getFloat("aspiracion_salarial");
        boolean licenciaConducir = rs.getBoolean("licencia_conducir");
        boolean disposicionMudarse = rs.getBoolean("disposicion_mudarse");
        String estado = rs.getString("estado");
        String tipo = rs.getString("tipo_candidato");

        ArrayList<String> idiomas = obtenerIdiomas(con, codigo);

        Candidato c;
        switch (tipo) {
            case "UNIVERSITARIO":
                c = new Universitario(codigo, identificacion, nombres, apellidos, fechaNacimiento, genero,
                        idProvincia, idMunicipio, telefono, correo, jornada, modalidad, areaDeInteres,
                        aspiracionSalarial, licenciaConducir, disposicionMudarse, idiomas,
                        rs.getString("universidad"), rs.getString("carrera"), rs.getString("nivel_academico"), estado);
                break;

            case "TECNICO":
                c = new TecnicoSuperior(codigo, identificacion, nombres, apellidos, fechaNacimiento, genero,
                        idProvincia, idMunicipio, telefono, correo, jornada, modalidad, areaDeInteres,
                        aspiracionSalarial, licenciaConducir, disposicionMudarse, idiomas,
                        rs.getString("area_tecnica"), rs.getInt("anios_experiencia"), estado);
                break;

            default: // OBRERO
                ArrayList<String> habilidades = obtenerHabilidades(con, codigo);
                c = new Obrero(codigo, identificacion, nombres, apellidos, fechaNacimiento, genero,
                        idProvincia, idMunicipio, telefono, correo, jornada, modalidad, areaDeInteres,
                        aspiracionSalarial, licenciaConducir, disposicionMudarse, idiomas, habilidades, estado);
                break;
        }

        return c;
    }

    private ArrayList<String> obtenerIdiomas(Connection con, String codigo) throws SQLException {

        ArrayList<String> idiomas = new ArrayList<>();

        String sql =
                "SELECT I.nombre_idioma " +
                        "FROM CandidatoIdioma CI " +
                        "INNER JOIN Idioma I ON CI.id_idioma = I.id_idioma " +
                        "WHERE CI.id_candidato = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    idiomas.add(rs.getString("nombre_idioma"));
                }
            }
        }

        return idiomas;
    }

    private ArrayList<String> obtenerHabilidades(Connection con, String codigo) throws SQLException {
        ArrayList<String> habilidades = new ArrayList<>();
        String sql = "SELECT habilidad FROM CandidatoHabilidad WHERE id_candidato = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) habilidades.add(rs.getString("habilidad"));
            }
        }
        return habilidades;
    }
}