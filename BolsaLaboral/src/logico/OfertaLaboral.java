package logico;

import java.io.Serializable;
import java.util.ArrayList;

import db.AreaTecnicaDAOImpl;
import db.CarreraDAOImpl;
import db.HabilidadDAOImpl;

public class OfertaLaboral implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String puesto;
	private String descripcion;
	private String area;
	private String modalidad;
	private String jornada;
	private String estado;
	private float salario;
	private int experienciaMinima;
	private int vacantes;
	private CentroEmpleador ofertador;
	private boolean ofreceReubicacion;
	private boolean obligatorioMayorDeEdad;
	private boolean obligatorioLicencia;
	private String nivelAcademico;
	private ArrayList<String> idiomasRequeridas;
	private int porcentajeMinimo;

	/** Solo uno de estos tres se usa según nivelAcademico; los otros van null. */
	private Integer idCarrera;
	private Integer idAreaTecnica;
	private Integer idHabilidad;

	public OfertaLaboral(String codigo, String puesto, String descripcion, String area,
	                     String modalidad, String jornada, String estado, float salario, int experienciaMinima,
	                     int vacantes, CentroEmpleador ofertador, boolean ofreceReubicacion,
	                     boolean mayorDeEdadObligatorio, boolean obligatorioLicencia, String nivelAcademico,
	                     Integer idCarrera, Integer idAreaTecnica, Integer idHabilidad,
	                     ArrayList<String> idiomasRequeridas, int porcentajeMinimo) {
		this.codigo = codigo;
		this.puesto = puesto;
		this.descripcion = descripcion;
		this.area = area;
		this.modalidad = modalidad;
		this.jornada = jornada;
		this.estado = estado;
		this.salario = salario;
		this.experienciaMinima = experienciaMinima;
		this.vacantes = vacantes;
		this.ofertador = ofertador;
		this.ofreceReubicacion = ofreceReubicacion;
		this.obligatorioMayorDeEdad = mayorDeEdadObligatorio;
		this.obligatorioLicencia = obligatorioLicencia;
		this.nivelAcademico = nivelAcademico;
		this.idCarrera = idCarrera;
		this.idAreaTecnica = idAreaTecnica;
		this.idHabilidad = idHabilidad;
		this.idiomasRequeridas = idiomasRequeridas != null ? idiomasRequeridas : new ArrayList<>();
		this.porcentajeMinimo = porcentajeMinimo;
	}

	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }

	public String getPuesto() { return puesto; }
	public void setPuesto(String puesto) { this.puesto = puesto; }

	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

	public String getArea() { return area; }
	public void setArea(String area) { this.area = area; }

	public String getModalidad() { return modalidad; }
	public void setModalidad(String modalidad) { this.modalidad = modalidad; }

	public String getJornada() { return jornada; }
	public void setJornada(String jornada) { this.jornada = jornada; }

	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }

	public float getSalario() { return salario; }
	public void setSalario(float salario) { this.salario = salario; }

	public int getExperienciaMinima() { return experienciaMinima; }
	public void setExperienciaMinima(int experienciaMinima) { this.experienciaMinima = experienciaMinima; }

	public int getVacantes() { return vacantes; }
	public void setVacantes(int vacantes) { this.vacantes = vacantes; }

	public CentroEmpleador getOfertador() { return ofertador; }
	public void setOfertador(CentroEmpleador ofertador) { this.ofertador = ofertador; }

	public boolean isOfreceReubicacion() { return ofreceReubicacion; }
	public void setOfreceReubicacion(boolean ofreceReubicacion) { this.ofreceReubicacion = ofreceReubicacion; }

	public boolean isObligatorioMayorDeEdad() { return obligatorioMayorDeEdad; }
	public void setObligatorioMayorDeEdad(boolean obligatorioMayorDeEdad) { this.obligatorioMayorDeEdad = obligatorioMayorDeEdad; }

	public boolean isObligatorioLicencia() { return obligatorioLicencia; }
	public void setObligatorioLicencia(boolean obligatorioLicencia) { this.obligatorioLicencia = obligatorioLicencia; }

	public String getNivelAcademico() { return nivelAcademico; }
	public void setNivelAcademico(String nivelAcademico) { this.nivelAcademico = nivelAcademico; }

	public ArrayList<String> getIdiomasRequeridas() { return idiomasRequeridas; }
	public void setIdiomasRequeridas(ArrayList<String> idiomasRequeridas) { this.idiomasRequeridas = idiomasRequeridas; }

	public void agregarIdioma(String idioma) {
		if (idiomasRequeridas == null) idiomasRequeridas = new ArrayList<>();
		if (!idiomasRequeridas.contains(idioma)) idiomasRequeridas.add(idioma);
	}

	public int getPorcentajeMinimo() { return porcentajeMinimo; }
	public void setPorcentajeMinimo(int porcentajeMinimo) { this.porcentajeMinimo = porcentajeMinimo; }

	public Integer getIdCarrera() { return idCarrera; }
	public void setIdCarrera(Integer idCarrera) { this.idCarrera = idCarrera; }

	public Integer getIdAreaTecnica() { return idAreaTecnica; }
	public void setIdAreaTecnica(Integer idAreaTecnica) { this.idAreaTecnica = idAreaTecnica; }

	public Integer getIdHabilidad() { return idHabilidad; }
	public void setIdHabilidad(Integer idHabilidad) { this.idHabilidad = idHabilidad; }

	/** Nombres resueltos (útiles para UI / matching). */
	public String getNombreCarrera() {
		if (idCarrera == null) return "";
		Carrera c = new CarreraDAOImpl().buscarPorId(idCarrera);
		return c != null ? c.getNombreCarrera() : "";
	}

	public String getNombreAreaTecnica() {
		if (idAreaTecnica == null) return "";
		AreaTecnica a = new AreaTecnicaDAOImpl().buscarPorId(idAreaTecnica);
		return a != null ? a.getNombreArea() : "";
	}

	public String getNombreHabilidad() {
		if (idHabilidad == null) return "";
		Habilidad h = new HabilidadDAOImpl().buscarPorId(idHabilidad);
		return h != null ? h.getNombreHabilidad() : "";
	}
}