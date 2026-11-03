package ec.edu.monster.vista;

import ec.edu.monster.controlador.BancoClienteControlador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TransferenciaForm extends JFrame {

    private JTextField txtCuentaOrigen;
    private JTextField txtCuentaDestino;
    private JTextField txtValor;
    private JTextField txtCodEmpleado;
    private JButton btnTransferir;
    private JButton btnMenuPrincipal;

    public TransferenciaForm() {
        setTitle("Transferencia de Fondos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(204, 204, 255));
        setContentPane(mainPanel);

        // Logo superior
        JLabel lblLogo = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/monstersinc (1).png")));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblLogo, BorderLayout.NORTH);

        // Centro con el formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); // hereda color de fondo
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Tahoma", Font.BOLD, 16);

        JLabel lblCuentaOrigen = new JLabel("CUENTA DE ORIGEN:");
        lblCuentaOrigen.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblCuentaOrigen, gbc);

        txtCuentaOrigen = new JTextField();
        txtCuentaOrigen.setFont(font);
        gbc.gridx = 1;
        formPanel.add(txtCuentaOrigen, gbc);

        JLabel lblCuentaDestino = new JLabel("CUENTA DE DESTINO:");
        lblCuentaDestino.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblCuentaDestino, gbc);

        txtCuentaDestino = new JTextField();
        txtCuentaDestino.setFont(font);
        gbc.gridx = 1;
        formPanel.add(txtCuentaDestino, gbc);

        JLabel lblValor = new JLabel("VALOR:");
        lblValor.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblValor, gbc);

        txtValor = new JTextField();
        txtValor.setFont(font);
        gbc.gridx = 1;
        formPanel.add(txtValor, gbc);

        JLabel lblCodEmpleado = new JLabel("CÓDIGO EMPLEADO:");
        lblCodEmpleado.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblCodEmpleado, gbc);

        txtCodEmpleado = new JTextField();
        txtCodEmpleado.setFont(font);
        gbc.gridx = 1;
        formPanel.add(txtCodEmpleado, gbc);

        // Botones
        btnTransferir = new JButton("TRANSFERIR");
        btnTransferir.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(btnTransferir, gbc);

        btnMenuPrincipal = new JButton("MENÚ PRINCIPAL");
        btnMenuPrincipal.setFont(font);
        gbc.gridy = 5;
        formPanel.add(btnMenuPrincipal, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Imagenes laterales
        JLabel lblIzq = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/pngwing.com (1) (1).png")));
        JLabel lblDer = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/pngwing.com (2) (1).png")));
        mainPanel.add(lblIzq, BorderLayout.WEST);
        mainPanel.add(lblDer, BorderLayout.EAST);

        // Eventos
        btnTransferir.addActionListener(this::realizarTransferencia);
        btnMenuPrincipal.addActionListener(e -> {
            new MenuForm().setVisible(true);
            dispose();
        });
    }

    private void realizarTransferencia(ActionEvent evt) {
        String origen = txtCuentaOrigen.getText().trim();
        String destino = txtCuentaDestino.getText().trim();
        String valorStr = txtValor.getText().trim();
        String codEmpleado = txtCodEmpleado.getText().trim();

        if (origen.isEmpty() || destino.isEmpty() || valorStr.isEmpty() || codEmpleado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Todos los campos son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (origen.equals(destino)) {
            JOptionPane.showMessageDialog(this, "⚠️ La cuenta origen y destino no pueden ser iguales.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double valor = Double.parseDouble(valorStr);
            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "⚠️ El importe debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BancoClienteControlador controlador = new BancoClienteControlador();
            boolean resultado = controlador.realizarTransferencia(origen, destino, valor, codEmpleado);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "✅ Transferencia realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo realizar la transferencia.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Importe inválido. Ingrese un número decimal.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al ejecutar la transferencia.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TransferenciaForm().setVisible(true));
    }
}
