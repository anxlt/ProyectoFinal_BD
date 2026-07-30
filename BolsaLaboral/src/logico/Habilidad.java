package logico;

import java.io.Serializable;

public class Habilidad implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idHabilidad;
    private String nombreHabilidad;

    public Habilidad(int idHabilidad, String nombreHabilidad) {
        this.idHabilidad = idHabilidad;
        this.nombreHabilidad = nombreHabilidad;
    }

    public int getIdHabilidad() { return idHabilidad; }
    public String getNombreHabilidad() { return nombreHabilidad; }

    @Override
    public String toString() { return nombreHabilidad; }
}