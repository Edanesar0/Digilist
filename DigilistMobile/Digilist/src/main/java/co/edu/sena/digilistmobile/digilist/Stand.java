package co.edu.sena.digilistmobile.digilist;


import co.edu.sena.digilistmobile.digilist.Conexiones.RequestsAndResponses;

public class Stand {
    private int capacidad;
    private String descripcion;
    RequestsAndResponses requestsAndResponses;

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
        requestsAndResponses= new RequestsAndResponses();
        //requestsAndResponses.getMateriales();
    }

    public boolean agregarStan(int capacidad, String descripcion) {

        requestsAndResponses= new RequestsAndResponses();
        // requestsAndResponses.postMateriales();
        return false;
    }

    public boolean modificarStan(String criterio, String terminoAModificar) {

        requestsAndResponses= new RequestsAndResponses();
        // requestsAndResponses.putMateriales();
        return false;
    }

    public boolean eliminarStand(int identificador) {

        requestsAndResponses= new RequestsAndResponses();
        //requestsAndResponses.deleteMateriales();
        return false;
    }

}
