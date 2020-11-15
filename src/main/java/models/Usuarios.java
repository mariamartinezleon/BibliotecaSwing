package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.JDBCMySQL;

public class Usuarios {

    private Integer codigo;
    private String nombre;
    private String apellido;
    private String carnet;
    private List<Movimientos> movimientosList;
    private UsuarioPerfiles codigoPerfil;

    public Usuarios() {
    }

    public Usuarios(Integer codigo, String nombre, String apellido, String carnet, UsuarioPerfiles codigoPerfil) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.carnet = carnet;
        this.codigoPerfil = codigoPerfil;
    }

    public Usuarios(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public List<Movimientos> getMovimientosList() {
        return movimientosList;
    }

    public void setMovimientosList(List<Movimientos> movimientosList) {
        this.movimientosList = movimientosList;
    }

    public UsuarioPerfiles getCodigoPerfil() {
        return codigoPerfil;
    }

    public void setCodigoPerfil(UsuarioPerfiles codigoPerfil) {
        this.codigoPerfil = codigoPerfil;
    }

    public boolean save() {
        try {
            Map<String, Object> params = new HashMap<>();
            String query = "insert into usuarios(codigo,nombre,apellido,carnet,codigo_perfil) values(:codigo,:nombre,:apellido,:carnet,:codigo_perfil)";
            if (codigo != null) {
                query = "update usuarios set nombre=:nombre,apellido=:apellido,carnet=:carnet,codigo_perfil=:codigo_perfil where codigo=:codigo";
                params.put("codigo", this.codigo);
            }
            params.put("nombre", this.nombre);
            params.put("apellido", this.apellido);
            params.put("carnet", this.carnet);
            params.put("codigo_perfil", this.codigoPerfil);
            JDBCMySQL msql = new JDBCMySQL();
            return msql.execute(query, params);
        } catch (Exception e) {
            System.err.println("Error al guardar usuarios");
            System.err.println(e.getMessage());
        }
        return false;
    }

    public List<Usuarios> list() {
        List<Usuarios> list = new ArrayList<>();
        JDBCMySQL mysql = new JDBCMySQL();
        String sql = "SELECT * FROM usuarios";
        ResultSet rs = mysql.query(sql, null);
        try {
            while (rs.next()) {
                list.add(new Usuarios(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        UsuarioPerfiles.find(rs.getInt(5))
                ));
            }
        } catch (Exception e) {
            System.err.println("Error al obtener lista usuarios");
            System.err.println(e.getMessage());
        }
        return list;
    }

    public static Usuarios find(int id) {
        JDBCMySQL mysql = new JDBCMySQL();
        String sql = "SELECT * FROM usuarios where codigo = " + id;
        ResultSet rs = mysql.query(sql, null);
        try {
            rs.first();
            return new Usuarios(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        UsuarioPerfiles.find(rs.getInt(5))
         );
                } catch (Exception e) {
            System.err.println("Error al obtener usuarios");
            System.err.println(e.getMessage());
        }
        return null;
    }
}