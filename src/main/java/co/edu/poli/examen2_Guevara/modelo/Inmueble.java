package co.edu.poli.examen2_Guevara.modelo;

public abstract class Inmueble {

    private String numero;
    private String fechaCompra;
    private boolean estado;
    private Propietario propietario;

    public Inmueble() {
    }

    public Inmueble(String numero, String fechaCompra, boolean estado, Propietario propietario) {
        this.numero = numero;
        this.fechaCompra = fechaCompra;
        this.estado = estado;
        this.propietario = propietario;
    }

    public String activar() {
        this.estado = true;
        return "Inmueble ACTIVADO";
    }

    public String inactivar() {
        this.estado = false;
        return "Inmueble INACTIVO";
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "numero='" + numero + '\'' +
                ", fechaCompra='" + fechaCompra + '\'' +
                ", estado=" + estado +
                ", propietario=" + propietario +
                '}';
    }
}