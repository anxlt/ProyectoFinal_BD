package logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import db.UniversidadDAO;
import db.UniversidadDAOImpl;
import db.CarreraDAO;
import db.CarreraDAOImpl;

public class Universitario extends Candidato implements Serializable{

	private static final long serialVersionUID = 1L;

	private int idUniversidad;
	private int idCarrera;
	private String nivelAcademico;

	public Universitario(String codigo, String identificacion, String nombres, String apellidos,
	                     LocalDate fechaNacimiento, String genero, int idProvincia, int idMunicipio, String telefono,
	                     String correo, String jornada, String modalidad, String areaDeInteres, float aspiracionSalarial,
	                     boolean licenciaConducir, boolean disposicionMudarse, ArrayList<String> idiomas,
	                     int idUniversidad, int idCarrera, String nivelAcademico, String estado) {
		super(codigo, identificacion, nombres, apellidos, fechaNacimiento, genero, idProvincia, idMunicipio, telefono,
				correo, jornada, modalidad, areaDeInteres, aspiracionSalarial, licenciaConducir, disposicionMudarse,
				idiomas, estado);
		this.idUniversidad = idUniversidad;
		this.idCarrera = idCarrera;
		this.nivelAcademico = nivelAcademico;
	}

	public int getIdUniversidad() { return idUniversidad; }
	public void setIdUniversidad(int idUniversidad) { this.idUniversidad = idUniversidad; }

	public int getIdCarrera() { return idCarrera; }
	public void setIdCarrera(int idCarrera) { this.idCarrera = idCarrera; }

	public String getNivelAcademico() { return nivelAcademico; }
	public void setNivelAcademico(String nivelAcademico) { this.nivelAcademico = nivelAcademico; }

	// getters "calculados": el objeto solo guarda el id, el nombre se resuelve consultando la BD
	public String getUniversidad() {
		Universidad u = new UniversidadDAOImpl().buscarPorId(idUniversidad);
		return (u != null) ? u.getNombreUniversidad() : "";
	}

	public String getCarrera() {
		Carrera c = new CarreraDAOImpl().buscarPorId(idCarrera);
		return (c != null) ? c.getNombreCarrera() : "";
	}

	@Override
	public String getSobreMi() {
		StringBuilder sb = new StringBuilder();
		sb.append("Soy estudiante de ").append(getCarrera().toLowerCase())
				.append(" en la universidad ").append(getUniversidad())
				.append(", nivel académico ").append(getNivelAcademico().toLowerCase()).append(". ");
		sb.append("Mi área de interés es ").append(getAreaDeInteres().toLowerCase()).append(". ");

		if (isLicenciaConducir()) sb.append("Cuento con licencia de conducir. ");
		if (isDisposicionMudarse()) sb.append("Estoy dispuesto a mudarme si es necesario para el empleo. ");

		sb.append("Busco oportunidades en modalidad ").append(getModalidad().toLowerCase())
				.append(" y jornada ").append(getJornada().toLowerCase())
				.append(", con una aspiración salarial de RD$").append(getAspiracionSalarial()).append(". ");

		if (!getIdiomas().isEmpty()) {
			sb.append("Tengo conocimientos en los siguientes idiomas: ");
			for (int i = 0; i < getIdiomas().size(); i++) {
				sb.append(getIdiomas().get(i));
				if (i < getIdiomas().size() - 2) sb.append(", ");
				else if (i == getIdiomas().size() - 2) sb.append(" y ");
			}
			sb.append(". ");
		}

		return sb.toString().trim();
	}

	@Override
	public String getFormacion() {
		StringBuilder sb = new StringBuilder();
		sb.append("Estoy cursando estudios universitarios en la carrera de ").append(getCarrera().toLowerCase())
				.append(" en la universidad ").append(getUniversidad()).append(". ");
		sb.append("Mi nivel académico actual es ").append(getNivelAcademico().toLowerCase()).append(". ");

		return sb.toString().trim();
	}
}