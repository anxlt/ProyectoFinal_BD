package logico;

import java.io.Serializable;

public class Universidad implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idUniversidad;
    private String nombreUniversidad;

    public Universidad(int idUniversidad, String nombreUniversidad) {
        this.idUniversidad = idUniversidad;
        this.nombreUniversidad = nombreUniversidad;
    }

    public int getIdUniversidad() { return idUniversidad; }
    public void setIdUniversidad(int idUniversidad) { this.idUniversidad = idUniversidad; }
    public String getNombreUniversidad() { return nombreUniversidad; }
    public void setNombreUniversidad(String nombreUniversidad) { this.nombreUniversidad = nombreUniversidad; }

    @Override
    public String toString() { return nombreUniversidad; } // así se ve en el JComboBox

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Universidad)) return false;
        return idUniversidad == ((Universidad) obj).idUniversidad;
    }
}