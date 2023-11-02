import java.net.ConnectException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        do{
            System.out.println("Elige una acción. Pulsa cualquier tecla para salir:");
            System.out.println("\t1. Mostrar usuarios");
            System.out.println("\t2. Registrar usuario");
            System.out.println("\t3. Login");
            try {
                input = sc.nextInt();
            } catch (InputMismatchException e) {
                input = 0;
            }
            switch (input) {
                case 1:
                    try {
                        Login.showUsers();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Registro: ");
                        Login.registerForm();
                        System.out.println("-----------");
                    } catch (RegisterException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ConnectException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Registered succesfully");
                    break;
                case 3:
                    try {
                        System.out.println("Login: ");
                        Login.loginForm();
                        System.out.println("-----------");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (LoginException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Logged succesfully");
                    break;
                default:
                    System.out.println("saliendo...");
                    break;

            }
        } while (input > 0 && input < 4);

        System.out.println("END");





    }
}