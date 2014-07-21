package co.edu.sena.digilistmobile.digilist;


public class Stand {
    private int capacidad;
    private String descripcion;

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void revisarStand(int identificador) {
    }

    public boolean agregarStan(int capacidad, String descripcion) {
        return false;
    }

    public boolean modificarStan(String criterio, String terminoAModificar) {
        return false;
    }

    public boolean eliminarStand(int identificador) {
        return false;
    }

}
