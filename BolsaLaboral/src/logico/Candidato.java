package logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public abstract class Candidato implements Serializable{

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String identificacion;
	private String nombres;
	private String apellidos;
	private LocalDate fechaNacimiento;
	private String genero;
	private int idProvincia;
	private int idMunicipio;
	private String telefono;
	private String correo;
	private String jornada;
	private String modalidad;
	private String areaDeInteres;
	private float aspiracionSalarial;
	private boolean licenciaConducir;
	private boolean disposicionMudarse;
	private String estado;
	private ArrayList<String> idiomas;
	private ArrayList<Solicitud> misSolicitudes;

	public Candidato(String codigo, String identificacion, String nombres, String apellidos, LocalDate fechaNacimiento,
	                 String genero, int idProvincia, int idMunicipio, String telefono, String correo, String jornada,
	                 String modalidad, String areaDeInteres, float aspiracionSalarial, boolean licenciaConducir,
	                 boolean disposicionMudarse, ArrayList<String> idiomas, String estado) {
		super();
		this.codigo = codigo;
		this.identificacion = identificacion;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.genero = genero;
		this.idProvincia = idProvincia;
		this.idMunicipio = idMunicipio;
		this.telefono = telefono;
		this.correo = correo;
		this.jornada = jornada;
		this.modalidad = modalidad;
		this.areaDeInteres = areaDeInteres;
		this.aspiracionSalarial = aspiracionSalarial;
		this.licenciaConducir = licenciaConducir;
		this.disposicionMudarse = disposicionMudarse;
		this.idiomas = idiomas;
		this.misSolicitudes =  new ArrayList<Solicitud> ();
		this.estado = estado;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}

	public int getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(int idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getJornada() {
		return jornada;
	}

	public void setJornada(String jornada) {
		this.jornada = jornada;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public float getAspiracionSalarial() {
		return aspiracionSalarial;
	}

	public void setAspiracionSalarial(float aspiracionSalarial) {
		this.aspiracionSalarial = aspiracionSalarial;
	}

	public boolean isLicenciaConducir() {
		return licenciaConducir;
	}

	public void setLicenciaConducir(boolean licenciaConducir) {
		this.licenciaConducir = licenciaConducir;
	}

	public boolean isDisposicionMudarse() {
		return disposicionMudarse;
	}

	public void setDisposicionMudarse(boolean disposicionMudarse) {
		this.disposicionMudarse = disposicionMudarse;
	}

	public ArrayList<String> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(ArrayList<String> idiomas) {
		this.idiomas = idiomas;
	}

	public ArrayList<Solicitud> getMisSolicitudes() {
		return misSolicitudes;
	}

	public void setMisSolicitudes(ArrayList<Solicitud> misSolicitudes) {
		this.misSolicitudes = misSolicitudes;
	}

	public String getAreaDeInteres() {
		return areaDeInteres;
	}

	public void setAreaDeInteres(String areaDeInteres) {
		this.areaDeInteres = areaDeInteres;
	}

	public abstract String getSobreMi();

	public abstract String getFormacion();

	public void cambiarEstadoSolicitudesAEmpleado() {
		for (Solicitud solicitud : misSolicitudes) {
			solicitud.setEstado("Aprobada");
		}
	}

	public void cambiarEstadoSolicitudesADesempleado() {
		for (Solicitud solicitud : misSolicitudes) {
			solicitud.setEstado("Rechazada");
		}
	}

	public int getEdad() {
		return Period.between(fechaNacimiento, LocalDate.now()).getYears();
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void addSolicitud(Solicitud solicitud) {
		misSolicitudes.add(solicitud);
	}

}
