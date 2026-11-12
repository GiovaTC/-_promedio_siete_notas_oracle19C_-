package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {

    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String USUARIO = "system";
    private static final String PASSWORD = "Tapiero123";

    public static Connection conectar()
    {
        try
        {
            Connection conexion = DriverManager.getConnection(
                    URL, USUARIO, PASSWORD
            );

            System.out.println("Conexion EXITOSA con ORACLE 19c!");

            return conexion;
        } catch (SQLException e) {
            System.out.println("ERROR de conexion: " + e.getMessage());

            return null;
        }
    }
}
