package logico;

import java.io.Serializable;

public class AreaTecnica implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idAreaTecnica;
    private String nombreArea;

    public AreaTecnica(int idAreaTecnica, String nombreArea) {
        this.idAreaTecnica = idAreaTecnica;
        this.nombreArea = nombreArea;
    }

    public int getIdAreaTecnica() {
        return idAreaTecnica;
    }

    public void setIdAreaTecnica(int idAreaTecnica) {
        this.idAreaTecnica = idAreaTecnica;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    @Override
    public String toString() {
        return nombreArea;
    }
}