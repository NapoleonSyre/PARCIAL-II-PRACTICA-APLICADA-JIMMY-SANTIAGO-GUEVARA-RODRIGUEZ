package unitaria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.examen2_Guevara.modelo.Apartamento;
import co.edu.poli.examen2_Guevara.modelo.Inmueble;
import co.edu.poli.examen2_Guevara.modelo.Propietario;

public class TestInmueble {

    @Test
    void inactivar_cambiaEstadoAFalso() {
        Propietario propietario = new Propietario("1", "Test");

        Inmueble i = new Apartamento("123", "2025-12-25", true, propietario, 5);

        String mensaje = i.inactivar();

        assertFalse(i.isEstado());
        assertTrue(mensaje.contains("INACTIVO"));
    }

    @Test
    void activar_cambiaEstadoAVerdadero() {
        Propietario propietario = new Propietario("1", "Test");

        Inmueble i = new Apartamento("123", "2025-12-25", false, propietario, 5);

        String mensaje = i.activar();

        assertTrue(i.isEstado());
        assertTrue(mensaje.contains("ACTIVADO"));
    }

    @Test
    void getters_retornaValoresCorrectos() {
        Propietario propietario = new Propietario("1", "Test");

        Inmueble i = new Apartamento("123", "2025-12-25", true, propietario, 5);

        assertEquals("123", i.getNumero());
        assertEquals("2025-12-25", i.getFechaCompra());
        assertTrue(i.isEstado());
        assertEquals(propietario, i.getPropietario());
    }

    @Test
    void setters_modificanValores() {
        Propietario propietario = new Propietario("1", "Test");
        Propietario nuevo = new Propietario("2", "Nuevo");

        Inmueble i = new Apartamento("123", "2025-12-25", true, propietario, 5);

        i.setNumero("999");
        i.setFechaCompra("2030-01-01");
        i.setEstado(false);
        i.setPropietario(nuevo);

        assertEquals("999", i.getNumero());
        assertEquals("2030-01-01", i.getFechaCompra());
        assertFalse(i.isEstado());
        assertEquals(nuevo, i.getPropietario());
    }

    @Test
    void toString_contieneDatos() {
        Propietario propietario = new Propietario("1", "Test");

        Inmueble i = new Apartamento("123", "2025-12-25", true, propietario, 5);

        String texto = i.toString();

        assertTrue(texto.contains("123"));
        assertTrue(texto.contains("2025-12-25"));
    }
}