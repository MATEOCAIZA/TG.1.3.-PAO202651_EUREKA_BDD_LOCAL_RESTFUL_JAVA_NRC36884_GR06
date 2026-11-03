package ec.edu.monster.vista;

import ec.edu.monster.controlador.BancoClienteControlador;

import javax.swing.*;
import java.awt.*;

public class ConsultarForm extends JFrame {
    private JTextField txtCuenta;
    private JButton btnConsultar, btnMenuPrincipal;

    public ConsultarForm() {
        setTitle("Consultar Saldo - Eurekabank");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // ENCABEZADO
        JLabel encabezado = new JLabel("CONSULTAR SALDO", SwingConstants.CENTER);
        encabezado.setFont(new Font("Arial", Font.BOLD, 26));
        encabezado.setOpaque(true);
        encabezado.setBackground(new Color(153, 204, 255));
        encabezado.setPreferredSize(new Dimension(800, 60));
        add(encabezado, BorderLayout.NORTH);

        // PANEL CENTRAL
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBackground(new Color(240, 245, 255));
        add(panelCentro, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblCuenta = new JLabel("NUMERO DE CUENTA:");
        lblCuenta.setFont(new Font("Arial", Font.BOLD, 16));
        panelCentro.add(lblCuenta, gbc);

        gbc.gridx = 1;
        txtCuenta = new JTextField(20);
        txtCuenta.setFont(new Font("Arial", Font.PLAIN, 16));
        panelCentro.add(txtCuenta, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        btnConsultar = new JButton("CONSULTAR");
        btnConsultar.setFont(new Font("Arial", Font.BOLD, 16));
        btnConsultar.setBackground(new Color(204, 255, 204));
        btnConsultar.addActionListener(e -> consultarSaldo());
        panelCentro.add(btnConsultar, gbc);

        gbc.gridy++;
        btnMenuPrincipal = new JButton("MENÚ PRINCIPAL");
        btnMenuPrincipal.setFont(new Font("Arial", Font.BOLD, 16));
        btnMenuPrincipal.setBackground(new Color(255, 229, 204));
        btnMenuPrincipal.addActionListener(e -> {
            new MenuForm().setVisible(true);
            this.dispose();
        });
        panelCentro.add(btnMenuPrincipal, gbc);

        // PANEL INFERIOR CON IMÁGENES
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 10));
        panelInferior.setBackground(new Color(240, 245, 255));
        panelInferior.setPreferredSize(new Dimension(800, 150));
        add(panelInferior, BorderLayout.SOUTH);

        JLabel img1 = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/pngwing.com (1) (1).png")));
        JLabel img2 = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/pngwing.com (2) (1).png")));
        JLabel img3 = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/monstersinc (1).png")));

        panelInferior.add(img1);
        panelInferior.add(img2);
        panelInferior.add(img3);
    }

    private void consultarSaldo() {
        String cuenta = txtCuenta.getText().trim();

        if (cuenta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingrese un número de cuenta.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            BancoClienteControlador controlador = new BancoClienteControlador();
            double saldo = controlador.consultarSaldo(cuenta);

            if (saldo >= 0) {
                JOptionPane.showMessageDialog(this, "💲 Saldo actual: $" + String.format("%.2f", saldo), "Consulta de saldo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Cuenta no válida o no activa.", "Consulta de saldo", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al consultar saldo.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConsultarForm().setVisible(true));
    }
}
