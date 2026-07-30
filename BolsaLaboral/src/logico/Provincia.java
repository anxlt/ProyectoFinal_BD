package logico;

import java.io.Serializable;

public class Provincia implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idProvincia;
    private String nombreProvincia;

    public Provincia(int idProvincia, String nombreProvincia) {
        this.idProvincia = idProvincia;
        this.nombreProvincia = nombreProvincia;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    @Override
    public String toString() {
        // Importante: así se ve el nombre (no el id) dentro del JComboBox
        return nombreProvincia;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Provincia)) return false;
        return idProvincia == ((Provincia) obj).idProvincia;
    }
}
