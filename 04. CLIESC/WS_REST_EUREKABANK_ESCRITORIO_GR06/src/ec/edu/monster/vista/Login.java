package ec.edu.monster.vista;

import ec.edu.monster.controlador.BancoClienteControlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnIngresar;

    public Login() {
        setTitle("EUREKABANK - Inicio de sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550); // Pantalla más grande
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(false);
        setLayout(new BorderLayout());

        // PANEL IZQUIERDO CON IMAGEN
        JLabel lblImagen = new JLabel();
        lblImagen.setIcon(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/background (1).png")));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(350, 550));
        add(lblImagen, BorderLayout.WEST);

        // PANEL DERECHO CON FORMULARIO
        JPanel panelForm = new JPanel();
        panelForm.setLayout(null); // Layout absoluto para diseño más libre
        panelForm.setBackground(new Color(240, 255, 255));
        add(panelForm, BorderLayout.CENTER);

        // TÍTULO PRINCIPAL
        JLabel lblTitulo = new JLabel("EUREKABANK");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(150, 30, 300, 40);
        panelForm.add(lblTitulo);

        // SUBTÍTULO
        JLabel lblSubtitulo = new JLabel("INICIAR SESIÓN");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setBounds(160, 80, 280, 30);
        panelForm.add(lblSubtitulo);

        // ETIQUETA USUARIO
        JLabel lblUsuario = new JLabel("USUARIO:");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        lblUsuario.setBounds(100, 160, 100, 30);
        panelForm.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        txtUsuario.setBounds(220, 160, 250, 30);
        panelForm.add(txtUsuario);

        // ETIQUETA CONTRASEÑA
        JLabel lblClave = new JLabel("CONTRASEÑA:");
        lblClave.setFont(new Font("Arial", Font.PLAIN, 16));
        lblClave.setBounds(100, 220, 120, 30);
        panelForm.add(lblClave);

        txtClave = new JPasswordField();
        txtClave.setFont(new Font("Arial", Font.PLAIN, 16));
        txtClave.setBounds(220, 220, 250, 30);
        panelForm.add(txtClave);

        // BOTÓN INGRESAR
        btnIngresar = new JButton("INGRESAR");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 18));
        btnIngresar.setBackground(new Color(0, 153, 153));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBounds(220, 290, 250, 40);
        panelForm.add(btnIngresar);

        // Evento del botón
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    private void login() {
        String usuario = txtUsuario.getText();
        String clave = new String(txtClave.getPassword());

        BancoClienteControlador controlador = new BancoClienteControlador();
        boolean ok = controlador.login(usuario, clave);
        if (ok) {
            MenuForm menu = new MenuForm();
            menu.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o clave incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        HudTheme.install(); // tema común EurekaBank
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
