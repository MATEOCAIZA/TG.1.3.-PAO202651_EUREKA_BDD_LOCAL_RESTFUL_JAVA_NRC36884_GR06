package ec.edu.monster.vista;

import ec.edu.monster.controlador.BancoClienteControlador;

import javax.swing.*;
import java.awt.*;

public class MovimientosForm extends JFrame {
    private JTextField txtCuenta;
    private JButton btnConsultar, btnMenuPrincipal;

    public MovimientosForm() {
        setTitle("Consultar Movimientos - Eurekabank");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Encabezado
        JLabel encabezado = new JLabel("CONSULTAR MOVIMIENTOS", SwingConstants.CENTER);
        encabezado.setFont(new Font("Arial", Font.BOLD, 26));
        encabezado.setOpaque(true);
        encabezado.setBackground(new Color(153, 204, 255));
        encabezado.setPreferredSize(new Dimension(900, 60));
        add(encabezado, BorderLayout.NORTH);

        // Panel central con formulario
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBackground(new Color(240, 245, 255));
        add(panelCentro, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta y campo de texto
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblCuenta = new JLabel("NUMERO DE CUENTA:");
        lblCuenta.setFont(new Font("Arial", Font.BOLD, 16));
        panelCentro.add(lblCuenta, gbc);

        gbc.gridx = 1;
        txtCuenta = new JTextField(20);
        txtCuenta.setFont(new Font("Arial", Font.PLAIN, 16));
        panelCentro.add(txtCuenta, gbc);

        // Botón Consultar
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnConsultar = new JButton("CONSULTAR");
        btnConsultar.setFont(new Font("Arial", Font.BOLD, 16));
        btnConsultar.setBackground(new Color(204, 255, 204));
        btnConsultar.addActionListener(e -> consultarMovimientos());
        panelCentro.add(btnConsultar, gbc);

        // Botón Menú Principal
        gbc.gridy++;
        btnMenuPrincipal = new JButton("MENÚ PRINCIPAL");
        btnMenuPrincipal.setFont(new Font("Arial", Font.BOLD, 16));
        btnMenuPrincipal.setBackground(new Color(255, 229, 204));
        btnMenuPrincipal.addActionListener(e -> {
            new MenuForm().setVisible(true);
            this.dispose();
        });
        panelCentro.add(btnMenuPrincipal, gbc);

        // Panel inferior con imágenes decorativas
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

    private void consultarMovimientos() {
        String cuenta = txtCuenta.getText().trim();

        if (cuenta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingrese el número de cuenta.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            BancoClienteControlador controlador = new BancoClienteControlador();
            String respuestaJson = controlador.consultarMovimientos(cuenta);

            MovimientosDForm ventana = new MovimientosDForm(respuestaJson);
            ventana.setLocationRelativeTo(this);
            ventana.setVisible(true);
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al consultar movimientos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
