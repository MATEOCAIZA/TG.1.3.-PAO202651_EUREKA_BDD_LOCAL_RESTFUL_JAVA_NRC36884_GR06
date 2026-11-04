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

    private static final String NODO = "REST · JAVA";

    // Tema común de consola (mismo en los 4 clientes). Colores ANSI solo en terminal real;
    // en la salida de NetBeans (sin consola) se imprime sin códigos.
    private static final boolean COLOR = System.console() != null;
    private static final String MORADO = c("35"), VERDE = c("92"), NARANJA = c("33"), ROJO = c("91"), RESET = c("0");

    private static String c(String code) { return COLOR ? "\u001B[" + code + "m" : ""; }

    private static void banner() {
        System.out.println(MORADO + "  ╔════════════════════════════════════════════════╗");
        System.out.println("  ║ " + VERDE + " ▓▓ EUREKABANK ▓▓    TERMINAL DE OPERACIONES  " + MORADO + " ║");
        System.out.println("  ║ " + NARANJA + String.format(" NODO: %-20s   ESTADO: EN LÍNEA", NODO) + MORADO + " ║");
        System.out.println("  ╚════════════════════════════════════════════════╝" + RESET);
    }

    private static void titulo(String t) {
        System.out.println();
        System.out.println(VERDE + "  ▌ " + t.toUpperCase() + RESET);
        System.out.println(MORADO + "  ──────────────────────────────────────────────────" + RESET);
    }

    private static void opcion(String n, String texto) { System.out.println("  " + NARANJA + "[" + n + "]" + RESET + " " + texto); }
    private static void prompt(String p) { System.out.print("  " + NARANJA + "» " + RESET + p + ": "); }
    private static void ok(String m) { System.out.println(VERDE + "  ■ " + m + RESET); }
    private static void error(String m) { System.out.println(ROJO + "  ■ " + m + RESET); }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(java.util.Locale.US); // importes con punto decimal en cualquier PC
        BancoClienteControlador controlador = new BancoClienteControlador();

        banner();
        titulo("Acceso al sistema");
        prompt("Usuario");
        String usuario = sc.nextLine();
        prompt("Clave");
        String clave = sc.nextLine();

        if (!controlador.login(usuario, clave)) {
            error("Login incorrecto.");
            return;
        }
        ok("Bienvenido, " + usuario);

        int opcion;
        do {
            titulo("Menú principal");
            opcion("1", "Consultar saldo");
            opcion("2", "Realizar depósito");
            opcion("3", "Realizar retiro");
            opcion("4", "Realizar transferencia");
            opcion("5", "Consultar movimientos");
            opcion("0", "Salir");
            prompt("Opción");
            opcion = sc.nextInt(); sc.nextLine();

            switch (opcion) {
                case 1:
                    titulo("Consultar saldo");
                    prompt("Código de cuenta");
                    String cuenta1 = sc.nextLine();
                    double saldo = controlador.consultarSaldo(cuenta1);
                    ok("Saldo actual: $" + saldo);
                    break;
                case 2:
                    titulo("Depósito");
                    prompt("Cuenta");
                    String cuenta2 = sc.nextLine();
                    prompt("Importe");
                    double importe2 = sc.nextDouble(); sc.nextLine();
                    boolean ok2 = controlador.realizarDeposito(cuenta2, importe2, "0004");
                    if (ok2) ok("Depósito exitoso"); else error("Error en el depósito");
                    break;
                case 3:
                    titulo("Retiro");
                    prompt("Cuenta");
                    String cuenta3 = sc.nextLine();
                    prompt("Importe");
                    double importe3 = sc.nextDouble(); sc.nextLine();
                    boolean ok3 = controlador.realizarRetiro(cuenta3, importe3, "0004");
                    if (ok3) ok("Retiro exitoso"); else error("Error en el retiro");
                    break;
                case 4:
                    titulo("Transferencia");
                    prompt("Cuenta origen");
                    String origen = sc.nextLine();
                    prompt("Cuenta destino");
                    String destino = sc.nextLine();
                    prompt("Importe");
                    double importe4 = sc.nextDouble(); sc.nextLine();
                    prompt("Empleado");
                    String empleado = sc.nextLine();
                    boolean ok4 = controlador.realizarTransferencia(origen, destino, importe4, empleado);
                    if (ok4) ok("Transferencia exitosa"); else error("Error en la transferencia");
                    break;
                case 5:
                    titulo("Movimientos");
                    prompt("Cuenta");
                    String cuenta5 = sc.nextLine();
                    String movimientos = controlador.consultarMovimientos(cuenta5);
                    System.out.println(movimientos);
                    break;
                case 0:
                    ok("Hasta luego.");
                    break;
                default:
                    error("Opción inválida.");
                    break;
            }
        } while (opcion != 0);
    }
}
