package logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import db.AreaTecnicaDAOImpl;

public class TecnicoSuperior extends Candidato implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idAreaTecnica;
	private int aniosExperiencia;

	public TecnicoSuperior(String codigo, String identificacion, String nombres, String apellidos,
	                       LocalDate fechaNacimiento, String genero, int idProvincia, int idMunicipio,
	                       String telefono, String correo, String jornada, String modalidad,
	                       String areaDeInteres, float aspiracionSalarial, boolean licenciaConducir,
	                       boolean disposicionMudarse, ArrayList<String> idiomas,
	                       int idAreaTecnica, int aniosExperiencia, String estado) {
		super(codigo, identificacion, nombres, apellidos, fechaNacimiento, genero, idProvincia, idMunicipio,
				telefono, correo, jornada, modalidad, areaDeInteres, aspiracionSalarial, licenciaConducir,
				disposicionMudarse, idiomas, estado);
		this.idAreaTecnica = idAreaTecnica;
		this.aniosExperiencia = aniosExperiencia;
	}

	public int getIdAreaTecnica() {
		return idAreaTecnica;
	}

	public void setIdAreaTecnica(int idAreaTecnica) {
		this.idAreaTecnica = idAreaTecnica;
	}

	public int getAniosExperiencia() {
		return aniosExperiencia;
	}

	public void setAniosExperiencia(int aniosExperiencia) {
		this.aniosExperiencia = aniosExperiencia;
	}

	/** Nombre resuelto desde BD (para UI y matching por texto si hace falta). */
	public String getAreaTecnica() {
		AreaTecnica a = new AreaTecnicaDAOImpl().buscarPorId(idAreaTecnica);
		return (a != null) ? a.getNombreArea() : "";
	}
	@Override
	public String getFormacion() {
		return "Técnico Superior en " + getAreaTecnica()
				+ " (" + aniosExperiencia + (aniosExperiencia == 1 ? " año" : " años") + " de experiencia)";
	}
	@Override
	public String getSobreMi() {
		StringBuilder sb = new StringBuilder();
		sb.append("Soy técnico en ").append(getAreaTecnica().toLowerCase())
				.append(" con ").append(aniosExperiencia)
				.append(aniosExperiencia == 1 ? " año" : " años").append(" de experiencia laboral. ");
		sb.append("Mi área de interés principal es ").append(getAreaDeInteres().toLowerCase()).append(". ");

		if (isLicenciaConducir()) sb.append("Cuento con licencia de conducir. ");
		if (isDisposicionMudarse()) sb.append("Tengo disponibilidad para mudarme si el trabajo lo requiere. ");

		sb.append("Estoy interesado en una modalidad ").append(getModalidad().toLowerCase())
				.append(" y jornada ").append(getJornada().toLowerCase())
				.append(", con una aspiración salarial de RD$").append(getAspiracionSalarial()).append(". ");

		if (!getIdiomas().isEmpty()) {
			sb.append("Manejo los siguientes idiomas: ");
			for (int i = 0; i < getIdiomas().size(); i++) {
				sb.append(getIdiomas().get(i));
				if (i < getIdiomas().size() - 2) sb.append(", ");
				else if (i == getIdiomas().size() - 2) sb.append(" y ");
			}
			sb.append(". ");
		}
		return sb.toString().trim();
	}
}