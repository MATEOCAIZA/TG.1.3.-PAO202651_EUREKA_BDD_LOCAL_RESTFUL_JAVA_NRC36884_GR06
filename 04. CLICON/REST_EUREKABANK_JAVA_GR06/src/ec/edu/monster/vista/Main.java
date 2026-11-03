/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.vista;
import ec.edu.monster.controlador.BancoClienteControlador;
import java.util.Scanner;
/**
 *
 * @author Isaac
 */

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BancoClienteControlador controlador = new BancoClienteControlador();

        System.out.println("=== LOGIN ===");
        System.out.print("Usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Clave: ");
        String clave = sc.nextLine();

        if (!controlador.login(usuario, clave)) {
            System.out.println("Login incorrecto.");
            return;
        }

        int opcion;
        do {
            System.out.println("\n=== MENÚ BANCO ===");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Realizar depósito");
            System.out.println("3. Realizar retiro");
            System.out.println("4. Realizar transferencia");
            System.out.println("5. Consultar movimientos");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt(); sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Código de cuenta: ");
                    String cuenta1 = sc.nextLine();
                    double saldo = controlador.consultarSaldo(cuenta1);
                    System.out.println("Saldo actual: $" + saldo);
                    break;
                case 2:
                    System.out.print("Cuenta: ");
                    String cuenta2 = sc.nextLine();
                    System.out.print("Importe: ");
                    double importe2 = sc.nextDouble(); sc.nextLine();
                    boolean ok2 = controlador.realizarDeposito(cuenta2, importe2, "0004");
                    System.out.println(ok2 ? "Depósito exitoso" : "Error en el depósito");
                    break;
                case 3:
                    System.out.print("Cuenta: ");
                    String cuenta3 = sc.nextLine();
                    System.out.print("Importe: ");
                    double importe3 = sc.nextDouble(); sc.nextLine();
                    boolean ok3 = controlador.realizarRetiro(cuenta3, importe3, "0004");
                    System.out.println(ok3 ? "Retiro exitoso" : "Error en el retiro");
                    break;
                case 4:
                    System.out.print("Cuenta origen: ");
                    String origen = sc.nextLine();
                    System.out.print("Cuenta destino: ");
                    String destino = sc.nextLine();
                    System.out.print("Importe: ");
                    double importe4 = sc.nextDouble(); sc.nextLine();
                    System.out.print("Empleado: ");
                    String empleado = sc.nextLine();
                    boolean ok4 = controlador.realizarTransferencia(origen, destino, importe4, empleado);
                    System.out.println(ok4 ? "Transferencia exitosa" : "Error en la transferencia");
                    break;
                case 5:
                    System.out.print("Cuenta: ");
                    String cuenta5 = sc.nextLine();
                    String movimientos = controlador.consultarMovimientos(cuenta5);
                    System.out.println("Movimientos:\n" + movimientos);
                    break;
                case 0:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 0);
    }
}
