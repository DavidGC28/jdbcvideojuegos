package com.krakdev.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Conexion {

    private static final Logger LOGGER = LogManager.getLogger(Conexion.class);
    
    private static final String URL = "jdbc:postgresql://localhost:5432/Apijdbc";
    private static final String USUARIO = "postgres";
    private static final String CLAVE = "12345";

    public static Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USUARIO, CLAVE);
            LOGGER.info("Conexión realizada");
        } catch (SQLException e) {
            LOGGER.error("Error al conectar: {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error general: {}", e.getMessage());
        }
        return con;
    }
}
