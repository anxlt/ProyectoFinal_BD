package db;

import logico.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CandidatoDAOImpl implements CandidatoDAO {

    @Override
    public void insertar(Candidato c) {
        String sqlCandidato = "INSERT INTO Candidato (id_candidato, identificacion, nombres, apellidos, "
                + "fecha_nacimiento, genero, provincia, municipio, telefono, correo, jornada, modalidad, "
                + "area_interes, aspiracion_salarial, licencia_conducir, disposicion_mudarse, estado, tipo_candidato) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sqlCandidato)) {
                ps.setString(1, c.getCodigo());
                ps.setString(2, c.getIdentificacion());
                ps.setString(3, c.getNombres());
                ps.setString(4, c.getApellidos());
                ps.setDate(5, Date.valueOf(c.getFechaNacimiento()));
                ps.setString(6, c.getGenero());
                ps.setString(7, c.getProvincia());
                ps.setString(8, c.getMunicipio());
                ps.setString(9, c.getTelefono());
                ps.setString(10, c.getCorreo());
                ps.setString(11, c.getJornada());
                ps.setString(12, c.getModalidad());
                ps.setString(13, c.getAreaDeInteres());
                ps.setFloat(14, c.getAspiracionSalarial());
                ps.setBoolean(15, c.isLicenciaConducir());
                ps.setBoolean(16, c.isDisposicionMudarse());
                ps.setString(17, c.getEstado());

                if (c instanceof Universitario) {
                    ps.setString(18, "UNIVERSITARIO");
                } else if (c instanceof TecnicoSuperior) {
                    ps.setString(18, "TECNICO");
                } else {
                    ps.setString(18, "OBRERO");
                }

                ps.executeUpdate();
            }

            // Insertar en la tabla hija correspondiente
            if (c instanceof Universitario u) {
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO Universitario (id_candidato, universidad, carrera, nivel_academico) "
                                + "VALUES (?,?,?,?)")) {
                    ps.setString(1, u.getCodigo());
                    ps.setString(2, u.getUniversidad());
                    ps.setString(3, u.getCarrera());
                    ps.setString(4, u.getNivelAcademico());
                    ps.executeUpdate();
                }
            } else if (c instanceof TecnicoSuperior t) {
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO TecnicoSuperior (id_candidato, area_tecnica, anios_experiencia) "
                                + "VALUES (?,?,?)")) {
                    ps.setString(1, t.getCodigo());
                    ps.setString(2, t.getAreaTecnica());
                    ps.setInt(3, t.getAniosExperiencia());
                    ps.executeUpdate();
                }
            } else if (c instanceof Obrero o) {
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO Obrero (id_candidato) VALUES (?)")) {
                    ps.setString(1, o.getCodigo());
                    ps.executeUpdate();
                }
                insertarHabilidades(con, o.getCodigo(), o.getHabilidades());
            }

            insertarIdiomas(con, c.getCodigo(), c.getIdiomas());

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
        String sqlCandidato = "UPDATE Candidato SET identificacion = ?, nombres = ?, apellidos = ?, "
                + "fecha_nacimiento = ?, genero = ?, provincia = ?, municipio = ?, telefono = ?, correo = ?, "
                + "jornada = ?, modalidad = ?, area_interes = ?, aspiracion_salarial = ?, licencia_conducir = ?, "
                + "disposicion_mudarse = ?, estado = ? WHERE id_candidato = ?";

        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sqlCandidato)) {
                ps.setString(1, c.getIdentificacion());
                ps.setString(2, c.getNombres());
                ps.setString(3, c.getApellidos());
                ps.setDate(4, Date.valueOf(c.getFechaNacimiento()));
                ps.setString(5, c.getGenero());
                ps.setString(6, c.getProvincia());
                ps.setString(7, c.getMunicipio());
                ps.setString(8, c.getTelefono());
                ps.setString(9, c.getCorreo());
                ps.setString(10, c.getJornada());
                ps.setString(11, c.getModalidad());
                ps.setString(12, c.getAreaDeInteres());
                ps.setFloat(13, c.getAspiracionSalarial());
                ps.setBoolean(14, c.isLicenciaConducir());
                ps.setBoolean(15, c.isDisposicionMudarse());
                ps.setString(16, c.getEstado());
                ps.setString(17, c.getCodigo());
                ps.executeUpdate();
            }

            // Actualizar la tabla hija correspondiente
            if (c instanceof Universitario u) {
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE Universitario SET universidad = ?, carrera = ?, nivel_academico = ? "
                                + "WHERE id_candidato = ?")) {
                    ps.setString(1, u.getUniversidad());
                    ps.setString(2, u.getCarrera());
                    ps.setString(3, u.getNivelAcademico());
                    ps.setString(4, u.getCodigo());
                    ps.executeUpdate();
                }
            } else if (c instanceof TecnicoSuperior t) {
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE TecnicoSuperior SET area_tecnica = ?, anios_experiencia = ? "
                                + "WHERE id_candidato = ?")) {
                    ps.setString(1, t.getAreaTecnica());
                    ps.setInt(2, t.getAniosExperiencia());
                    ps.setString(3, t.getCodigo());
                    ps.executeUpdate();
                }
            } else if (c instanceof Obrero o) {
                // Obrero no tiene columnas propias que actualizar, solo habilidades
                try (PreparedStatement del = con.prepareStatement(
                        "DELETE FROM CandidatoHabilidad WHERE id_candidato = ?")) {
                    del.setString(1, o.getCodigo());
                    del.executeUpdate();
                }
                insertarHabilidades(con, o.getCodigo(), o.getHabilidades());
            }

            // Idiomas: se borran y se reinsertan tal como quedaron
            try (PreparedStatement del = con.prepareStatement(
                    "DELETE FROM CandidatoIdioma WHERE id_candidato = ?")) {
                del.setString(1, c.getCodigo());
                del.executeUpdate();
            }
            insertarIdiomas(con, c.getCodigo(), c.getIdiomas());

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
                                insertarIdioma, Statement.RETURN_GENERATED_KEYS)) {
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

    /**
     * Igual que insertarIdiomas, pero para Habilidad: busca la habilidad por
     * nombre, la crea si no existe, y guarda la relacion en CandidatoHabilidad
     * (que ahora referencia a Obrero, no columnas de texto sueltas).
     */
    private void insertarHabilidades(Connection con, String codigo, List<String> habilidades) throws SQLException {

        String buscarHabilidad = "SELECT id_habilidad FROM Habilidad WHERE nombre_habilidad = ?";
        String insertarHabilidad = "INSERT INTO Habilidad(nombre_habilidad) VALUES(?)";
        String insertarRelacion = "INSERT INTO CandidatoHabilidad(id_candidato, id_habilidad) VALUES(?, ?)";

        for (String habilidad : habilidades) {
            int idHabilidad;

            try (PreparedStatement psBuscar = con.prepareStatement(buscarHabilidad)) {
                psBuscar.setString(1, habilidad);
                try (ResultSet rs = psBuscar.executeQuery()) {
                    if (rs.next()) {
                        idHabilidad = rs.getInt("id_habilidad");
                    } else {
                        try (PreparedStatement psInsert = con.prepareStatement(
                                insertarHabilidad, Statement.RETURN_GENERATED_KEYS)) {
                            psInsert.setString(1, habilidad);
                            psInsert.executeUpdate();
                            try (ResultSet claves = psInsert.getGeneratedKeys()) {
                                claves.next();
                                idHabilidad = claves.getInt(1);
                            }
                        }
                    }
                }
            }

            try (PreparedStatement psRelacion = con.prepareStatement(insertarRelacion)) {
                psRelacion.setString(1, codigo);
                psRelacion.setInt(2, idHabilidad);
                psRelacion.executeUpdate();
            }
        }
    }

    @Override
    public void eliminar(String codigo) {
        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            // Gracias a ON DELETE CASCADE en las FK de Universitario/Obrero/
            // TecnicoSuperior/CandidatoHabilidad/CandidatoIdioma, basta con
            // borrar de Candidato. Se deja explicito por claridad y por si
            // el motor no tiene el cascade configurado.
            try (PreparedStatement ps1 = con.prepareStatement(
                    "DELETE FROM CandidatoIdioma WHERE id_candidato=?")) {
                ps1.setString(1, codigo);
                ps1.executeUpdate();
            }
            try (PreparedStatement ps2 = con.prepareStatement(
                    "DELETE FROM CandidatoHabilidad WHERE id_candidato=?")) {
                ps2.setString(1, codigo);
                ps2.executeUpdate();
            }
            try (PreparedStatement ps3 = con.prepareStatement(
                    "DELETE FROM Universitario WHERE id_candidato=?")) {
                ps3.setString(1, codigo);
                ps3.executeUpdate();
            }
            try (PreparedStatement ps4 = con.prepareStatement(
                    "DELETE FROM Obrero WHERE id_candidato=?")) {
                ps4.setString(1, codigo);
                ps4.executeUpdate();
            }
            try (PreparedStatement ps5 = con.prepareStatement(
                    "DELETE FROM TecnicoSuperior WHERE id_candidato=?")) {
                ps5.setString(1, codigo);
                ps5.executeUpdate();
            }
            try (PreparedStatement ps6 = con.prepareStatement(
                    "DELETE FROM Candidato WHERE id_candidato=?")) {
                ps6.setString(1, codigo);
                ps6.executeUpdate();
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
        String provincia = rs.getString("provincia");
        String municipio = rs.getString("municipio");
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
            case "UNIVERSITARIO": {
                String universidad = null, carrera = null, nivelAcademico = null;
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT universidad, carrera, nivel_academico FROM Universitario WHERE id_candidato = ?")) {
                    ps.setString(1, codigo);
                    try (ResultSet rsHijo = ps.executeQuery()) {
                        if (rsHijo.next()) {
                            universidad = rsHijo.getString("universidad");
                            carrera = rsHijo.getString("carrera");
                            nivelAcademico = rsHijo.getString("nivel_academico");
                        }
                    }
                }
                c = new Universitario(codigo, identificacion, nombres, apellidos, fechaNacimiento, genero,
                        provincia, municipio, telefono, correo, jornada, modalidad, areaDeInteres,
                        aspiracionSalarial, licenciaConducir, disposicionMudarse, idiomas,
                        universidad, carrera, nivelAcademico, estado);
                break;
            }
            case "TECNICO": {
                String areaTecnica = null;
                int aniosExperiencia = 0;
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT area_tecnica, anios_experiencia FROM TecnicoSuperior WHERE id_candidato = ?")) {
                    ps.setString(1, codigo);
                    try (ResultSet rsHijo = ps.executeQuery()) {
                        if (rsHijo.next()) {
                            areaTecnica = rsHijo.getString("area_tecnica");
                            aniosExperiencia = rsHijo.getInt("anios_experiencia");
                        }
                    }
                }
                c = new TecnicoSuperior(codigo, identificacion, nombres, apellidos, fechaNacimiento, genero,
                        provincia, municipio, telefono, correo, jornada, modalidad, areaDeInteres,
                        aspiracionSalarial, licenciaConducir, disposicionMudarse, idiomas,
                        areaTecnica, aniosExperiencia, estado);
                break;
            }
            default: { // OBRERO
                ArrayList<String> habilidades = obtenerHabilidades(con, codigo);
                c = new Obrero(codigo, identificacion, nombres, apellidos, fechaNacimiento, genero,
                        provincia, municipio, telefono, correo, jornada, modalidad, areaDeInteres,
                        aspiracionSalarial, licenciaConducir, disposicionMudarse, idiomas, habilidades, estado);
                break;
            }
        }

        return c;
    }

    private ArrayList<String> obtenerIdiomas(Connection con, String codigo) throws SQLException {
        ArrayList<String> idiomas = new ArrayList<>();
        String sql = "SELECT I.nombre_idioma "
                + "FROM CandidatoIdioma CI "
                + "INNER JOIN Idioma I ON CI.id_idioma = I.id_idioma "
                + "WHERE CI.id_candidato = ?";

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
        String sql = "SELECT H.nombre_habilidad "
                + "FROM CandidatoHabilidad CH "
                + "INNER JOIN Habilidad H ON CH.id_habilidad = H.id_habilidad "
                + "WHERE CH.id_candidato = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    habilidades.add(rs.getString("nombre_habilidad"));
                }
            }
        }
        return habilidades;
    }
}