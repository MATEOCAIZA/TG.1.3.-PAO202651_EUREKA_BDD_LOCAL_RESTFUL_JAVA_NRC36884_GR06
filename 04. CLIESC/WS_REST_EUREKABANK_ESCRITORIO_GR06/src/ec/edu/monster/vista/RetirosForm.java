package ec.edu.monster.vista;

import ec.edu.monster.controlador.BancoClienteControlador;

import javax.swing.*;
import java.awt.*;

public class RetirosForm extends JFrame {
    private JTextField txtCuenta;
    private JTextField txtValor;
    private JTextField txtCodempleado;
    private JButton btnRetirar;
    private JButton btnMenuprincipal;

    public RetirosForm() {
        setTitle("Retiros - Eurekabank");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Encabezado
        JLabel titulo = new JLabel("REALIZAR RETIRO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(153, 204, 255));
        titulo.setPreferredSize(new Dimension(900, 60));
        add(titulo, BorderLayout.NORTH);

        // Panel central con formulario
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBackground(new Color(240, 245, 255));
        add(panelCentro, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo Cuenta
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentro.add(new JLabel("NÚMERO DE CUENTA:"), gbc);

        gbc.gridx = 1;
        txtCuenta = new JTextField(20);
        txtCuenta.setFont(new Font("Arial", Font.PLAIN, 16));
        panelCentro.add(txtCuenta, gbc);

        // Campo Valor
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCentro.add(new JLabel("VALOR A RETIRAR:"), gbc);

        gbc.gridx = 1;
        txtValor = new JTextField(20);
        txtValor.setFont(new Font("Arial", Font.PLAIN, 16));
        panelCentro.add(txtValor, gbc);

        // Campo Código Empleado
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCentro.add(new JLabel("CÓDIGO EMPLEADO:"), gbc);

        gbc.gridx = 1;
        txtCodempleado = new JTextField(20);
        txtCodempleado.setFont(new Font("Arial", Font.PLAIN, 16));
        panelCentro.add(txtCodempleado, gbc);

        // Botón Retirar
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnRetirar = new JButton("RETIRAR");
        btnRetirar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRetirar.setBackground(new Color(204, 255, 204));
        btnRetirar.setFocusPainted(false);
        btnRetirar.addActionListener(e -> realizarRetiro());
        panelCentro.add(btnRetirar, gbc);

        // Botón Menú
        gbc.gridy = 4;
        btnMenuprincipal = new JButton("MENÚ PRINCIPAL");
        btnMenuprincipal.setFont(new Font("Arial", Font.BOLD, 16));
        btnMenuprincipal.setBackground(new Color(255, 229, 204));
        btnMenuprincipal.setFocusPainted(false);
        btnMenuprincipal.addActionListener(e -> {
            new MenuForm().setVisible(true);
            this.dispose();
        });
        panelCentro.add(btnMenuprincipal, gbc);

        // Pie con imágenes
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelInferior.setBackground(new Color(240, 245, 255));
        panelInferior.setPreferredSize(new Dimension(900, 160));
        add(panelInferior, BorderLayout.SOUTH);

        JLabel img1 = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/pngwing.com (1) (1).png")));
        JLabel img2 = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/pngwing.com (2) (1).png")));
        JLabel img3 = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/monstersinc (1).png")));

        panelInferior.add(img1);
        panelInferior.add(img2);
        panelInferior.add(img3);
    }

    private void realizarRetiro() {
        String cuenta = txtCuenta.getText().trim();
        String valorStr = txtValor.getText().trim();
        String codEmpleado = txtCodempleado.getText().trim();

        if (cuenta.isEmpty() || valorStr.isEmpty() || codEmpleado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double valor = Double.parseDouble(valorStr);
            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "El importe debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BancoClienteControlador controlador = new BancoClienteControlador();
            boolean resultado = controlador.realizarRetiro(cuenta, valor, codEmpleado);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "💸 Retiro exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo realizar el retiro. Verifique la cuenta o el saldo.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Importe inválido. Ingrese un número decimal.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al ejecutar el retiro.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
