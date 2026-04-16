package co.edu.poli.examen2_Guevara.modelo;

public class Propietario {

    private String id;
    private String nombre;

    // Constructor vacío
    public Propietario() {
    }

    // Constructor con parámetros
    public Propietario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}