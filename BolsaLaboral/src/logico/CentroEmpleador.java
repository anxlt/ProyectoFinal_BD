package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class CentroEmpleador implements Serializable{

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String rnc;
	private String nombre;
	private String sector;
	private int idProvincia;
	private int idMunicipio;
	private String telefono;
	private String correo;
	private ArrayList<OfertaLaboral> ofertasLaborales;

	public CentroEmpleador(String codigo, String nombre, String sector, int idProvincia, int idMunicipio, String telefono, String correo, String rnc) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.sector = sector;
		this.idProvincia = idProvincia;
		this.idMunicipio = idMunicipio;
		this.telefono = telefono;
		this.correo = correo;
		this.rnc = rnc;
		this.ofertasLaborales = new ArrayList<>();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
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

	public ArrayList<OfertaLaboral> getOfertasLaborales() {
		return ofertasLaborales;
	}

	public void setOfertasLaborales(ArrayList<OfertaLaboral> ofertasLaborales) {
		this.ofertasLaborales = ofertasLaborales;
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

	public String getRnc() {
		return rnc;
	}

	public void setRnc(String rnc) {
		this.rnc = rnc;
	}

}
