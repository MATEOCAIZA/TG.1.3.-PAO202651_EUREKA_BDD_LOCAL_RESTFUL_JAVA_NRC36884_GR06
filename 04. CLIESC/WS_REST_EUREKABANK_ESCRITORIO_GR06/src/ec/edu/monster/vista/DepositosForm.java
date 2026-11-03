package ec.edu.monster.vista;

import ec.edu.monster.controlador.BancoClienteControlador;

import javax.swing.*;
import java.awt.*;

public class DepositosForm extends JFrame {
    private JTextField txtCuenta, txtValor, txtCodempleado;
    private JButton btnDepositar, btnMenuprincipal;

    public DepositosForm() {
        setTitle("Depósitos - Eurekabank");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centrar
        setResizable(false);
        setLayout(new BorderLayout());

        // Encabezado
        JLabel encabezado = new JLabel("REALIZAR DEPÓSITO", SwingConstants.CENTER);
        encabezado.setFont(new Font("Arial", Font.BOLD, 26));
        encabezado.setOpaque(true);
        encabezado.setBackground(new Color(153, 204, 255));
        encabezado.setPreferredSize(new Dimension(800, 60));
        add(encabezado, BorderLayout.NORTH);

        // Panel central
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBackground(new Color(235, 240, 255));
        add(panelCentro, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Etiqueta y campo de cuenta
        panelCentro.add(new JLabel("NUMERO DE CUENTA:"), gbc);
        gbc.gridx = 1;
        txtCuenta = new JTextField(15);
        panelCentro.add(txtCuenta, gbc);

        // Valor
        gbc.gridy++;
        gbc.gridx = 0;
        panelCentro.add(new JLabel("VALOR:"), gbc);
        gbc.gridx = 1;
        txtValor = new JTextField(15);
        panelCentro.add(txtValor, gbc);

        // Código de empleado
        gbc.gridy++;
        gbc.gridx = 0;
        panelCentro.add(new JLabel("CODIGO EMPLEADO:"), gbc);
        gbc.gridx = 1;
        txtCodempleado = new JTextField(15);
        panelCentro.add(txtCodempleado, gbc);

        // Botón Depositar
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnDepositar = new JButton("DEPOSITAR");
        btnDepositar.setFont(new Font("Arial", Font.BOLD, 16));
        btnDepositar.setBackground(new Color(204, 255, 204));
        btnDepositar.addActionListener(e -> realizarDeposito());
        panelCentro.add(btnDepositar, gbc);

        // Botón regresar al menú
        gbc.gridy++;
        btnMenuprincipal = new JButton("MENÚ PRINCIPAL");
        btnMenuprincipal.setFont(new Font("Arial", Font.BOLD, 16));
        btnMenuprincipal.setBackground(new Color(255, 229, 204));
        btnMenuprincipal.addActionListener(e -> {
            new MenuForm().setVisible(true);
            this.dispose();
        });
        panelCentro.add(btnMenuprincipal, gbc);

        // Imagen inferior decorativa
        JLabel imagen = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/monstersinc (1).png")));
        imagen.setHorizontalAlignment(SwingConstants.CENTER);
        imagen.setPreferredSize(new Dimension(800, 100));
        add(imagen, BorderLayout.SOUTH);
    }

    private void realizarDeposito() {
        String cuenta = txtCuenta.getText().trim();
        String valorStr = txtValor.getText().trim();
        String codEmpleado = txtCodempleado.getText().trim();

        if (cuenta.isEmpty() || valorStr.isEmpty() || codEmpleado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Todos los campos son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double valor = Double.parseDouble(valorStr);
            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "⚠️ El importe debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BancoClienteControlador controlador = new BancoClienteControlador();
            boolean resultado = controlador.realizarDeposito(cuenta, valor, codEmpleado);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "✅ Depósito exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo realizar el depósito. Verifique la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Importe inválido. Ingrese un número decimal.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al ejecutar el depósito.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DepositosForm().setVisible(true));
    }
}
