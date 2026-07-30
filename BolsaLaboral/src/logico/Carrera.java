package logico;

import java.io.Serializable;

public class Carrera implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idCarrera;
    private String nombreCarrera;

    public Carrera(int idCarrera, String nombreCarrera) {
        this.idCarrera = idCarrera;
        this.nombreCarrera = nombreCarrera;
    }

    public int getIdCarrera() { return idCarrera; }
    public void setIdCarrera(int idCarrera) { this.idCarrera = idCarrera; }
    public String getNombreCarrera() { return nombreCarrera; }
    public void setNombreCarrera(String nombreCarrera) { this.nombreCarrera = nombreCarrera; }

    @Override
    public String toString() { return nombreCarrera; } // para que se vea el nombre en el JComboBox

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Carrera)) return false;
        return idCarrera == ((Carrera) obj).idCarrera;
    }
}