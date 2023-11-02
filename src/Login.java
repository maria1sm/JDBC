import com.mysql.cj.log.Log;
import org.mindrot.jbcrypt.BCrypt;

import java.net.ConnectException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Login {
    static boolean logged = false;

    private static Connection connect() throws SQLException {

            String url = "jdbc:mysql://localhost/usuarios";
            String usuario = "root";
            String password = "root";
            return DriverManager.getConnection(url, usuario, password);

    }
    public static void register(String nombre, String email, String password) throws SQLException, RegisterException {
        PreparedStatement sentencia = connect().prepareStatement("INSERT INTO user VALUES ( ? , ? , ? , ?, ?)");
        sentencia.setInt(1, 0);
        sentencia.setString(2, nombre);
        if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            sentencia.setString(3, email);
        } else {
            throw new RegisterException("Email no valido");
        }
        String passHashed = BCrypt.hashpw(password, BCrypt.gensalt());
        sentencia.setString(4, passHashed);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        sentencia.setString(5, dtf.format(LocalDateTime.now()));
        try{
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sentencia.close();
        connect().close();
    }
    public static void registerForm () throws SQLException, ConnectException, RegisterException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nombre: ");
        String nombre = sc.next();
        System.out.println("Email: ");
        String email = sc.next();
        System.out.println("Password: ");
        String password = sc.next();
        register(nombre, email, password);
    }
    public static void loginForm () throws SQLException, LoginException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Email: ");
        String email = sc.next();
        System.out.println("Password: ");
        String password = sc.next();
        login(email, password);
    }
    public static void login(String email, String password) throws SQLException, LoginException {
        Statement sentencia = connect().createStatement();
        ResultSet resultado = sentencia.executeQuery("SELECT email, password FROM user");
        while (resultado.next()){
            if(resultado.getString(1).equals(email)) {
                if (BCrypt.checkpw(password, resultado.getString(2))) {
                    logged = true;
                } else {
                    throw new LoginException("Error en el login");
                }
                break;
            }
        }
        resultado.close();
        sentencia.close();
        connect().close();
    }
    public static void showUsers() throws SQLException {

        if (logged){
        Statement sentencia = connect().createStatement();
        ResultSet resultado = sentencia.executeQuery("SELECT * FROM user");
        while (resultado.next()){
            System.out.println(resultado.getInt(1) + " " + resultado.getString(2) + " " + resultado.getString(3));
        }
        resultado.close();
        sentencia.close();
        connect().close();
        } else {
            System.out.println("No puedes acceder a los usuarios sin hacer login");
        }

    }

}
