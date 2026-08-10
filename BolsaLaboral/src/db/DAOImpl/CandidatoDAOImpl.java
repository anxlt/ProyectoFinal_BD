package db.DAOImpl;

import db.Conexion;
import db.DAO.CandidatoDAO;
import logico.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CandidatoDAOImpl implements CandidatoDAO {

    @Override
    public void insertar(Candidato c) {
        // Solo id_municipio (la provincia se obtiene vía Municipio)
        String sqlBase = "INSERT INTO Candidato (identificacion, nombres, apellidos, fecha_nacimiento, "
                + "genero, id_municipio, telefono, correo, jornada, modalidad, area_interes, "
                + "aspiracion_salarial, licencia_conducir, disposicion_mudarse, estado, tipo_candidato) "
                + "OUTPUT INSERTED.id_candidato "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            String tipo;
            if (c instanceof Universitario) tipo = "UNIVERSITARIO";
            else if (c instanceof TecnicoSuperior) tipo = "TECNICO";
            else tipo = "OBRERO";

            try (PreparedStatement ps = con.prepareStatement(sqlBase)) {
                ps.setString(1, c.getIdentificacion());
                ps.setString(2, c.getNombres());
                ps.setString(3, c.getApellidos());
                ps.setDate(4, Date.valueOf(c.getFechaNacimiento()));
                ps.setString(5, c.getGenero());
                ps.setInt(6, c.getIdMunicipio());
                ps.setString(7, c.getTelefono());
                ps.setString(8, c.getCorreo());
                ps.setString(9, c.getJornada());
                ps.setString(10, c.getModalidad());
                ps.setString(11, c.getAreaDeInteres());
                ps.setFloat(12, c.getAspiracionSalarial());
                ps.setBoolean(13, c.isLicenciaConducir());
                ps.setBoolean(14, c.isDisposicionMudarse());
                ps.setString(15, c.getEstado());
                ps.setString(16, tipo);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        c.setCodigo(rs.getString(1)); // código generado por la BD
                    }
                }
            }

            if (c instanceof Universitario u) {
                String sql = "INSERT INTO Universitario (id_candidato, id_universidad, id_carrera, nivel_academico) VALUES (?,?,?,?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, u.getCodigo());
                    ps.setInt(2, u.getIdUniversidad());
                    ps.setInt(3, u.getIdCarrera());
                    ps.setString(4, u.getNivelAcademico());
                    ps.executeUpdate();
                }
            } else if (c instanceof TecnicoSuperior t) {
                String sql = "INSERT INTO TecnicoSuperior (id_candidato, id_area_tecnica, anios_experiencia) VALUES (?,?,?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, t.getCodigo());
                    ps.setInt(2, t.getIdAreaTecnica());
                    ps.setInt(3, t.getAniosExperiencia());
                    ps.executeUpdate();
                }
            } else if (c instanceof Obrero o) {
                String sql = "INSERT INTO Obrero (id_candidato) VALUES (?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
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
        String sqlBase = "UPDATE Candidato SET identificacion=?, nombres=?, apellidos=?, fecha_nacimiento=?, "
                + "genero=?, id_municipio=?, telefono=?, correo=?, jornada=?, modalidad=?, "
                + "area_interes=?, aspiracion_salarial=?, licencia_conducir=?, disposicion_mudarse=?, estado=? "
                + "WHERE id_candidato=?";

        Connection con = null;
        try {
            con = Conexion.conectar();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sqlBase)) {
                ps.setString(1, c.getIdentificacion());
                ps.setString(2, c.getNombres());
                ps.setString(3, c.getApellidos());
                ps.setDate(4, Date.valueOf(c.getFechaNacimiento()));
                ps.setString(5, c.getGenero());
                ps.setInt(6, c.getIdMunicipio());
                ps.setString(7, c.getTelefono());
                ps.setString(8, c.getCorreo());
                ps.setString(9, c.getJornada());
                ps.setString(10, c.getModalidad());
                ps.setString(11, c.getAreaDeInteres());
                ps.setFloat(12, c.getAspiracionSalarial());
                ps.setBoolean(13, c.isLicenciaConducir());
                ps.setBoolean(14, c.isDisposicionMudarse());
                ps.setString(15, c.getEstado());
                ps.setString(16, c.getCodigo());
                ps.executeUpdate();
            }

            if (c instanceof Universitario u) {
                String sql = "UPDATE Universitario SET id_universidad=?, id_carrera=?, nivel_academico=? WHERE id_candidato=?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, u.getIdUniversidad());
                    ps.setInt(2, u.getIdCarrera());
                    ps.setString(3, u.getNivelAcademico());
                    ps.setString(4, u.getCodigo());
                    ps.executeUpdate();
                }
            } else if (c instanceof TecnicoSuperior t) {
                String sql = "UPDATE TecnicoSuperior SET id_area_tecnica=?, anios_experiencia=? WHERE id_candidato=?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, t.getIdAreaTecnica());
                    ps.setInt(2, t.getAniosExperiencia());
                    ps.setString(3, t.getCodigo());
                    ps.executeUpdate();
                }
            } else if (c instanceof Obrero o) {
                try (PreparedStatement del = con.prepareStatement(
                        "DELETE FROM CandidatoHabilidad WHERE id_candidato=?")) {
                    del.setString(1, o.getCodigo());
                    del.executeUpdate();
                }
                insertarHabilidades(con, o.getCodigo(), o.getHabilidades());
            }

            try (PreparedStatement del = con.prepareStatement(
                    "DELETE FROM CandidatoIdioma WHERE id_candidato=?")) {
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

    private void insertarHabilidades(Connection con, String codigo, List<String> habilidades) throws SQLException {
        String buscar = "SELECT id_habilidad FROM Habilidad WHERE nombre_habilidad = ?";
        String insertarHab = "INSERT INTO Habilidad(nombre_habilidad) VALUES(?)";
        String insertarRel = "INSERT INTO CandidatoHabilidad(id_candidato, id_habilidad) VALUES(?, ?)";

        for (String hab : habilidades) {
            int idHab;
            try (PreparedStatement psBuscar = con.prepareStatement(buscar)) {
                psBuscar.setString(1, hab);
                try (ResultSet rs = psBuscar.executeQuery()) {
                    if (rs.next()) {
                        idHab = rs.getInt("id_habilidad");
                    } else {
                        try (PreparedStatement psIns = con.prepareStatement(
                                insertarHab, Statement.RETURN_GENERATED_KEYS)) {
                            psIns.setString(1, hab);
                            psIns.executeUpdate();
                            try (ResultSet claves = psIns.getGeneratedKeys()) {
                                claves.next();
                                idHab = claves.getInt(1);
                            }
                        }
                    }
                }
            }
            try (PreparedStatement psRel = con.prepareStatement(insertarRel)) {
                psRel.setString(1, codigo);
                psRel.setInt(2, idHab);
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

            // Orden por FKs: relaciones -> subtipos -> base
            ejecutarDelete(con, "DELETE FROM CandidatoIdioma WHERE id_candidato=?", codigo);
            ejecutarDelete(con, "DELETE FROM CandidatoHabilidad WHERE id_candidato=?", codigo);
            ejecutarDelete(con, "DELETE FROM Universitario WHERE id_candidato=?", codigo);
            ejecutarDelete(con, "DELETE FROM TecnicoSuperior WHERE id_candidato=?", codigo);
            ejecutarDelete(con, "DELETE FROM Obrero WHERE id_candidato=?", codigo);
            ejecutarDelete(con, "DELETE FROM Candidato WHERE id_candidato=?", codigo);

            con.commit();
        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private void ejecutarDelete(Connection con, String sql, String codigo) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.executeUpdate();
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
                if (rs.next()) return mapear(con, rs);
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

    private Candidato mapear(Connection con, ResultSet rs) throws SQLException {
        String codigo = rs.getString("id_candidato");
        String identificacion = rs.getString("identificacion");
        String nombres = rs.getString("nombres");
        String apellidos = rs.getString("apellidos");
        LocalDate fechaNacimiento = rs.getDate("fecha_nacimiento").toLocalDate();
        String genero = rs.getString("genero");
        int idMunicipio = rs.getInt("id_municipio");
        // Provincia derivada del municipio (ya no se guarda en Candidato)
        int idProvincia = 0;
        Municipio mun = new MunicipioDAOImpl().buscarPorId(idMunicipio);
        if (mun != null) {
            idProvincia = mun.getIdProvincia();
        }
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

        switch (tipo == null ? "" : tipo.toUpperCase()) {
            case "UNIVERSITARIO": {
                int idUniversidad = 0;
                int idCarrera = 0;
                String nivel = "";
                String sqlU = "SELECT id_universidad, id_carrera, nivel_academico FROM Universitario WHERE id_candidato=?";
                try (PreparedStatement ps = con.prepareStatement(sqlU)) {
                    ps.setString(1, codigo);
                    try (ResultSet ru = ps.executeQuery()) {
                        if (ru.next()) {
                            idUniversidad = ru.getInt("id_universidad");
                            idCarrera = ru.getInt("id_carrera");
                            nivel = ru.getString("nivel_academico");
                        }
                    }
                }
                c = new Universitario(codigo, identificacion, nombres, apellidos, fechaNacimiento, genero,
                        idProvincia, idMunicipio, telefono, correo, jornada, modalidad, areaDeInteres,
                        aspiracionSalarial, licenciaConducir, disposicionMudarse, idiomas,
                        idUniversidad, idCarrera, nivel, estado);
                break;
            }
            case "TECNICO": {
                int idAreaTecnica = 0;
                int anios = 0;
                String sqlT = "SELECT id_area_tecnica, anios_experiencia FROM TecnicoSuperior WHERE id_candidato=?";
                try (PreparedStatement ps = con.prepareStatement(sqlT)) {
                    ps.setString(1, codigo);
                    try (ResultSet rt = ps.executeQuery()) {
                        if (rt.next()) {
                            idAreaTecnica = rt.getInt("id_area_tecnica");
                            anios = rt.getInt("anios_experiencia");
                        }
                    }
                }
                c = new TecnicoSuperior(codigo, identificacion, nombres, apellidos, fechaNacimiento, genero,
                        idProvincia, idMunicipio, telefono, correo, jornada, modalidad, areaDeInteres,
                        aspiracionSalarial, licenciaConducir, disposicionMudarse, idiomas,
                        idAreaTecnica, anios, estado);
                break;
            }
            default: { // OBRERO
                ArrayList<String> habilidades = obtenerHabilidades(con, codigo);
                c = new Obrero(codigo, identificacion, nombres, apellidos, fechaNacimiento, genero,
                        idProvincia, idMunicipio, telefono, correo, jornada, modalidad, areaDeInteres,
                        aspiracionSalarial, licenciaConducir, disposicionMudarse, idiomas, habilidades, estado);
                break;
            }
        }
        return c;
    }

    private ArrayList<String> obtenerIdiomas(Connection con, String codigo) throws SQLException {
        ArrayList<String> idiomas = new ArrayList<>();
        String sql = "SELECT I.nombre_idioma FROM CandidatoIdioma CI "
                + "INNER JOIN Idioma I ON CI.id_idioma = I.id_idioma WHERE CI.id_candidato = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) idiomas.add(rs.getString("nombre_idioma"));
            }
        }
        return idiomas;
    }

    private ArrayList<String> obtenerHabilidades(Connection con, String codigo) throws SQLException {
        ArrayList<String> habilidades = new ArrayList<>();
        String sql = "SELECT H.nombre_habilidad FROM CandidatoHabilidad CH "
                + "INNER JOIN Habilidad H ON CH.id_habilidad = H.id_habilidad WHERE CH.id_candidato = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) habilidades.add(rs.getString("nombre_habilidad"));
            }
        }
        return habilidades;
    }
}