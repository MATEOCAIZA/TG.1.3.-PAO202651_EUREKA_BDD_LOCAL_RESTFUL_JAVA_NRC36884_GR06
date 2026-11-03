package ec.edu.monster.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoDB {

    private static final String URL =
        "jdbc:mysql://localhost:3306/eurekabank?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "root";

    // Contraseña configurada en tu contenedor de Docker (ej. admin123)
    private static final String PASS = "hola123";

    public AccesoDB() {
    }

    public static Connection getConnection() throws SQLException {

        Connection cn = null;

        try {

            // Cargar Driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("====================================");
            System.out.println("Intentando conectar a Docker (Local)...");
            System.out.println("Host: localhost:3306");
            System.out.println("Base: eurekabank");
            System.out.println("Usuario: " + USER);
            System.out.println("====================================");

            cn = DriverManager.getConnection(
                    URL,
                    USER,
                    PASS
            );

            System.out.println("====================================");
            System.out.println("CONEXION EXITOSA A DOCKER");
            System.out.println("====================================");

        } catch (SQLException e) {

            System.out.println("====================================");
            System.out.println("ERROR SQL");
            System.out.println("====================================");

            e.printStackTrace();

            throw e;

        } catch (ClassNotFoundException e) {

            System.out.println("====================================");
            System.out.println("DRIVER MYSQL NO ENCONTRADO");
            System.out.println("====================================");

            e.printStackTrace();

            throw new SQLException(
                    "ERROR, no se encuentra el driver MYSQL"
            );

        } catch (Exception e) {

            System.out.println("====================================");
            System.out.println("ERROR GENERAL");
            System.out.println("====================================");

            e.printStackTrace();

            throw new SQLException(
                    e.getMessage()
            );
        }

        return cn;
    }
}