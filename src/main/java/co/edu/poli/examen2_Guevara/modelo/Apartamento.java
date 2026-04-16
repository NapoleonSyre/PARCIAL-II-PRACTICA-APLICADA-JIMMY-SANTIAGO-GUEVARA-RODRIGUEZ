package co.edu.poli.examen2_Guevara.modelo;

public class Apartamento extends Inmueble {

    private int numeroPiso;

    public Apartamento() {
    }

    public Apartamento(String numero, String fechaCompra, boolean estado, Propietario propietario, int numeroPiso) {
        super(numero, fechaCompra, estado, propietario);
        this.numeroPiso = numeroPiso;
    }

    public int getNumeroPiso() {
        return numeroPiso;
    }

    public void setNumeroPiso(int numeroPiso) {
        this.numeroPiso = numeroPiso;
    }
    @Override
    public String toString() {
        return "Apartamento{" +
                "numero='" + getNumero() + '\'' +
                ", fechaCompra='" + getFechaCompra() + '\'' +
                ", estado=" + isEstado() +
                ", propietario=" + getPropietario() +
                ", numeroPiso=" + numeroPiso +
                '}';
    }
}