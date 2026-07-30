package logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class TecnicoSuperior extends Candidato implements Serializable{

	private static final long serialVersionUID = 1L;

	private String areaTecnica;
	private int aniosExperiencia;

	public TecnicoSuperior(String codigo, String identificacion, String nombres, String apellidos,
	                       LocalDate fechaNacimiento, String genero, int idProvincia, int idMunicipio, String telefono,
	                       String correo, String jornada, String modalidad, String areaDeInteres, float aspiracionSalarial,
	                       boolean licenciaConducir, boolean disposicionMudarse, ArrayList<String> idiomas, String areaTecnica, int aniosExperiencia, String estado) {
		super(codigo, identificacion, nombres, apellidos, fechaNacimiento, genero, idProvincia, idMunicipio, telefono,
				correo, jornada, modalidad, areaDeInteres, aspiracionSalarial, licenciaConducir, disposicionMudarse,
				idiomas,estado);
		this.areaTecnica = areaTecnica;
		this.aniosExperiencia = aniosExperiencia;
	}

	public String getAreaTecnica() {
		return areaTecnica;
	}

	public void setAreaTecnica(String areaTecnica) {
		this.areaTecnica = areaTecnica;
	}

	public int getAniosExperiencia() {
		return aniosExperiencia;
	}

	public void setAniosExperiencia(int aniosExperiencia) {
		this.aniosExperiencia = aniosExperiencia;
	}

	@Override
	public String getSobreMi() {
		StringBuilder sb = new StringBuilder();
		sb.append("Soy t cnico en ").append(areaTecnica.toLowerCase())
				.append(" con ").append(aniosExperiencia)
				.append(aniosExperiencia == 1 ? " a o" : " a os").append(" de experiencia laboral. ");
		sb.append("Mi  rea de inter s principal es ").append(getAreaDeInteres().toLowerCase()).append(". ");

		if (isLicenciaConducir()) sb.append("Cuento con licencia de conducir. ");
		if (isDisposicionMudarse()) sb.append("Tengo disponibilidad para mudarme si el trabajo lo requiere. ");

		sb.append("Estoy interesado en una modalidad ").append(getModalidad().toLowerCase())
				.append(" y jornada ").append(getJornada().toLowerCase())
				.append(", con una aspiraci n salarial de RD$").append(getAspiracionSalarial()).append(". ");

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

	@Override
	public String getFormacion() {
		StringBuilder sb = new StringBuilder();
		sb.append("Soy t cnico superior en el  rea de ").append(areaTecnica.toLowerCase()).append(". ");
		sb.append("He complementado mi formaci n con ").append(aniosExperiencia)
				.append(aniosExperiencia == 1 ? " a o" : " a os").append(" de experiencia en el campo. ");

		return sb.toString().trim();
	}
}
