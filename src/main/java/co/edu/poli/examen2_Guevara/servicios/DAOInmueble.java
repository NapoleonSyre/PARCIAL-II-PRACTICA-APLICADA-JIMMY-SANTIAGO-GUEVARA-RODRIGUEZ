package co.edu.poli.examen2_Guevara.servicios;

import co.edu.poli.examen2_Guevara.modelo.Apartamento;
import co.edu.poli.examen2_Guevara.modelo.Casa;
import co.edu.poli.examen2_Guevara.modelo.Inmueble;
import co.edu.poli.examen2_Guevara.modelo.Propietario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOInmueble implements CRUD<Inmueble> {

    private final Connection con;
    private final DAOPropietario daoPropietario;

    public DAOInmueble() throws Exception {
        con = ConexionBD.getInstancia().getConexion();
        daoPropietario = new DAOPropietario();
    }

    @Override
    public String create(Inmueble i) throws Exception {

        String sqlInmueble = "INSERT INTO inmueble(numero, fecha_compra, estado, propietario_id) VALUES(?, ?, ?, ?)";
        PreparedStatement psInmueble = con.prepareStatement(sqlInmueble);
        psInmueble.setString(1, i.getNumero());
        psInmueble.setString(2, i.getFechaCompra());
        psInmueble.setBoolean(3, i.isEstado());
        psInmueble.setString(4, i.getPropietario().getId());
        psInmueble.executeUpdate();

        if (i instanceof Apartamento) {
            String sqlApartamento = "INSERT INTO inmueble_apartamento(numero, numero_piso) VALUES(?, ?)";
            PreparedStatement psApartamento = con.prepareStatement(sqlApartamento);
            psApartamento.setString(1, i.getNumero());
            psApartamento.setInt(2, ((Apartamento) i).getNumeroPiso());
            psApartamento.executeUpdate();
            return "Apartamento creado correctamente";
        }

        if (i instanceof Casa) {
            String sqlCasa = "INSERT INTO inmueble_casa(numero, cantidad_pisos) VALUES(?, ?)";
            PreparedStatement psCasa = con.prepareStatement(sqlCasa);
            psCasa.setString(1, i.getNumero());
            psCasa.setInt(2, ((Casa) i).getCantidadPisos());
            psCasa.executeUpdate();
            return "Casa creada correctamente";
        }

        return "Inmueble creado correctamente";
    }

    @Override
    public <K> Inmueble readone(K id) throws Exception {

        String sql = """
                SELECT i.numero, i.fecha_compra, i.estado, i.propietario_id,
                       a.numero_piso,
                       c.cantidad_pisos
                FROM inmueble i
                LEFT JOIN inmueble_apartamento a ON i.numero = a.numero
                LEFT JOIN inmueble_casa c ON i.numero = c.numero
                WHERE i.numero = ?
                """;

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id.toString());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Propietario propietario = daoPropietario.readone(rs.getString("propietario_id"));

            if (rs.getObject("numero_piso") != null) {
                return new Apartamento(
                        rs.getString("numero"),
                        rs.getString("fecha_compra"),
                        rs.getBoolean("estado"),
                        propietario,
                        rs.getInt("numero_piso")
                );
            }

            if (rs.getObject("cantidad_pisos") != null) {
                return new Casa(
                        rs.getString("numero"),
                        rs.getString("fecha_compra"),
                        rs.getBoolean("estado"),
                        propietario,
                        rs.getInt("cantidad_pisos")
                );
            }
        }

        return null;
    }

    @Override
    public List<Inmueble> readall() throws Exception {

        String sql = """
                SELECT i.numero, i.fecha_compra, i.estado, i.propietario_id,
                       a.numero_piso,
                       c.cantidad_pisos
                FROM inmueble i
                LEFT JOIN inmueble_apartamento a ON i.numero = a.numero
                LEFT JOIN inmueble_casa c ON i.numero = c.numero
                """;

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Inmueble> lista = new ArrayList<>();

        while (rs.next()) {
            Propietario propietario = daoPropietario.readone(rs.getString("propietario_id"));

            if (rs.getObject("numero_piso") != null) {
                Inmueble a = new Apartamento(
                        rs.getString("numero"),
                        rs.getString("fecha_compra"),
                        rs.getBoolean("estado"),
                        propietario,
                        rs.getInt("numero_piso")
                );
                lista.add(a);
            } else if (rs.getObject("cantidad_pisos") != null) {
                Inmueble c = new Casa(
                        rs.getString("numero"),
                        rs.getString("fecha_compra"),
                        rs.getBoolean("estado"),
                        propietario,
                        rs.getInt("cantidad_pisos")
                );
                lista.add(c);
            }
        }

        return lista;
    }
}