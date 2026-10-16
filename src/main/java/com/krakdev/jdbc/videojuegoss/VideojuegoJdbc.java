package com.krakdev.jdbc.videojuegoss;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.krakdev.jdbc.Conexion;
import com.krakdev.videojuegos.entidades.Videojuego;

public class VideojuegoJdbc {

    private static final Logger LOGGER = LogManager.getLogger(VideojuegoJdbc.class);

    //  Método INSERTAR
    public Videojuego insertar(Videojuego videojuego) {
        String sql = "INSERT INTO videojuegos (codigo, nombre, plataforma, precio, disponible, genero) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, videojuego.getCodigo());
            ps.setString(2, videojuego.getNombre());
            ps.setString(3, videojuego.getPlataforma());
            ps.setDouble(4, videojuego.getPrecio());
            ps.setBoolean(5, videojuego.isDisponible());
            ps.setString(6, videojuego.getGenero());
            
            ps.executeUpdate();
            LOGGER.info("¡Videojuego con código {} insertado exitosamente!", videojuego.getCodigo());
            
        } catch (SQLException e) {
            LOGGER.error("Error de SQL al insertar el videojuego: {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error general al insertar el videojuego: {}", e.getMessage());
        }
        return videojuego;
    }

    //  Método LISTAR
    public List<Videojuego> listar() {
        List<Videojuego> lista = new ArrayList<>();
        String sql = "SELECT codigo, nombre, plataforma, precio, disponible, genero FROM videojuegos";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                String plataforma = rs.getString("plataforma");
                double precio = rs.getDouble("precio");
                boolean disponible = rs.getBoolean("disponible");
                String genero = rs.getString("genero");

                Videojuego v = new Videojuego(codigo, nombre, plataforma, precio, disponible, genero);
                lista.add(v);
            }
            LOGGER.info("Se listaron {} videojuegos correctamente.", lista.size());

        } catch (SQLException e) {
            LOGGER.error("Error de SQL al listar los videojuegos: {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error general al listar los videojuegos: {}", e.getMessage());
        }
        return lista;
    }

    //  Método BUSCAR por código
    public Videojuego buscarPorCodigo(String codigoBuscado) {
        Videojuego videojuego = null;
        String sql = "SELECT codigo, nombre, plataforma, precio, disponible, genero FROM videojuegos WHERE codigo = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoBuscado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String codigo = rs.getString("codigo");
                    String nombre = rs.getString("nombre");
                    String plataforma = rs.getString("plataforma");
                    double precio = rs.getDouble("precio");
                    boolean disponible = rs.getBoolean("disponible");
                    String genero = rs.getString("genero");

                    videojuego = new Videojuego(codigo, nombre, plataforma, precio, disponible, genero);
                    LOGGER.info("Videojuego con código {} encontrado.", codigoBuscado);
                } else {
                    LOGGER.warn("No se encontró ningún videojuego con el código: {}", codigoBuscado);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Error de SQL al buscar el videojuego: {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error general al buscar el videojuego: {}", e.getMessage());
        }
        return videojuego;
    }

    //  Método ACTUALIZAR
    public Videojuego actualizar(Videojuego videojuego) {
        String sql = "UPDATE videojuegos SET nombre = ?, plataforma = ?, precio = ?, disponible = ?, genero = ? WHERE codigo = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, videojuego.getNombre());
            ps.setString(2, videojuego.getPlataforma());
            ps.setDouble(3, videojuego.getPrecio());
            ps.setBoolean(4, videojuego.isDisponible());
            ps.setString(5, videojuego.getGenero());
            ps.setString(6, videojuego.getCodigo());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                LOGGER.info("¡Videojuego con código {} actualizado exitosamente!", videojuego.getCodigo());
            } else {
                LOGGER.warn("No se pudo actualizar, el código {} no existe.", videojuego.getCodigo());
            }

        } catch (SQLException e) {
            LOGGER.error("Error de SQL al actualizar el videojuego: {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error general al actualizar el videojuego: {}", e.getMessage());
        }
        return videojuego;
    }

    //  Método ELIMINAR
    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM videojuegos WHERE codigo = ?";
        boolean eliminado = false;

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                eliminado = true;
                LOGGER.info("¡Videojuego con código {} eliminado exitosamente!", codigo);
            } else {
                LOGGER.warn("No se pudo eliminar, el código {} no fue encontrado.", codigo);
            }

        } catch (SQLException e) {
            LOGGER.error("Error de SQL al eliminar el videojuego: {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error general al eliminar el videojuego: {}", e.getMessage());
        }
        return eliminado;
    }
}