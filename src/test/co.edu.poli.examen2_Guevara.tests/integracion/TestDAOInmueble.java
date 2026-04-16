package integracion;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.examen2_Guevara.modelo.Apartamento;
import co.edu.poli.examen2_Guevara.modelo.Casa;
import co.edu.poli.examen2_Guevara.modelo.Inmueble;
import co.edu.poli.examen2_Guevara.modelo.Propietario;
import co.edu.poli.examen2_Guevara.servicios.DAOInmueble;

public class TestDAOInmueble {

    @Test
    void create_apartamento_y_readone() throws Exception {

        DAOInmueble dao = new DAOInmueble();

        Propietario propietario = new Propietario("P001", "Test");

        Apartamento apto = new Apartamento(
                "999001",
                "2025-12-25",
                true,
                propietario,
                5
        );

        String result = dao.create(apto);

        assertTrue(result.toLowerCase().contains("correctamente")
                || result.toLowerCase().contains("guardado")
                || result.toLowerCase().contains("creado"));

        Inmueble i = dao.readone("999001");

        assertNotNull(i);
        assertTrue(i instanceof Apartamento);

        Apartamento a = (Apartamento) i;
        assertEquals(5, a.getNumeroPiso());
    }

    @Test
    void create_casa_y_readone() throws Exception {

        DAOInmueble dao = new DAOInmueble();

        Propietario propietario = new Propietario("P001", "Test");

        Casa casa = new Casa(
                "999002",
                "2025-12-25",
                true,
                propietario,
                2
        );

        String result = dao.create(casa);

        assertTrue(result.toLowerCase().contains("correctamente")
                || result.toLowerCase().contains("guardado")
                || result.toLowerCase().contains("creado"));

        Inmueble i = dao.readone("999002");

        assertNotNull(i);
        assertTrue(i instanceof Casa);

        Casa c = (Casa) i;
        assertEquals(2, c.getCantidadPisos());
    }

    @Test
    void readone_noExiste() throws Exception {

        DAOInmueble dao = new DAOInmueble();

        Inmueble i = dao.readone("000000");

        assertNull(i);
    }
}