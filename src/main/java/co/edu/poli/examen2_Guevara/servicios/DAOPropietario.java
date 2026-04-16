package co.edu.poli.examen2_Guevara.servicios;

import co.edu.poli.examen2_Guevara.modelo.Propietario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOPropietario implements CRUD<Propietario> {

    private final Connection con;

    public DAOPropietario() throws Exception {
        con = ConexionBD.getInstancia().getConexion();
    }

    @Override
    public String create(Propietario p) throws Exception {
        String sql = "INSERT INTO propietario(id, nombre) VALUES(?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, p.getId());
        ps.setString(2, p.getNombre());
        ps.executeUpdate();
        return "Propietario creado correctamente";
    }

    @Override
    public <K> Propietario readone(K id) throws Exception {
        String sql = "SELECT * FROM propietario WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id.toString());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Propietario(
                    rs.getString("id"),
                    rs.getString("nombre")
            );
        }

        return null;
    }

    @Override
    public List<Propietario> readall() throws Exception {
        String sql = "SELECT * FROM propietario";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Propietario> lista = new ArrayList<>();

        while (rs.next()) {
            Propietario p = new Propietario(
                    rs.getString("id"),
                    rs.getString("nombre")
            );
            lista.add(p);
        }

        return lista;
    }
}