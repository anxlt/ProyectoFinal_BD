package logico;

import java.io.Serializable;

public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idMunicipio;
    private String nombreMunicipio;
    private int idProvincia;

    public Municipio(int idMunicipio, String nombreMunicipio, int idProvincia) {
        this.idMunicipio = idMunicipio;
        this.nombreMunicipio = nombreMunicipio;
        this.idProvincia = idProvincia;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    @Override
    public String toString() {
        // Así se ve el nombre (no el id) dentro del JComboBox
        return nombreMunicipio;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Municipio)) return false;
        return idMunicipio == ((Municipio) obj).idMunicipio;
    }
}
