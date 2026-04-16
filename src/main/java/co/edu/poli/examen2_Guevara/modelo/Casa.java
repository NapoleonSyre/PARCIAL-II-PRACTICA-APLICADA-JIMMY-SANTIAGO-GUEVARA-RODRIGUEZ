package co.edu.poli.examen2_Guevara.modelo;

public class Casa extends Inmueble {

    private int cantidadPisos;

    public Casa() {
    }

    public Casa(String numero, String fechaCompra, boolean estado, Propietario propietario, int cantidadPisos) {
        super(numero, fechaCompra, estado, propietario);
        this.cantidadPisos = cantidadPisos;
    }

    public int getCantidadPisos() {
        return cantidadPisos;
    }

    public void setCantidadPisos(int cantidadPisos) {
        this.cantidadPisos = cantidadPisos;
    }
    @Override
    public String toString() {
        return "Casa{" +
                "numero='" + getNumero() + '\'' +
                ", fechaCompra='" + getFechaCompra() + '\'' +
                ", estado=" + isEstado() +
                ", propietario=" + getPropietario() +
                ", cantidadPisos=" + cantidadPisos +
                '}';
    }
}