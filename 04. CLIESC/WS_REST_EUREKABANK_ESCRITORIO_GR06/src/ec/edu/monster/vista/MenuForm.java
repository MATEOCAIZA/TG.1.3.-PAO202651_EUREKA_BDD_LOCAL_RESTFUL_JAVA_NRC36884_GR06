package ec.edu.monster.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuForm extends JFrame {
    private JButton btnDepositar, btnConsultar, btnMovimientos, btnRetirar, btnTransferir;

    public MenuForm() {
        setTitle("Menú Principal - Eurekabank");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Panel superior con título
        JLabel titulo = new JLabel("EUREKABANK - MENÚ PRINCIPAL", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(153, 204, 255));
        titulo.setPreferredSize(new Dimension(900, 60));
        add(titulo, BorderLayout.NORTH);

        // Panel central principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(235, 245, 255));
        add(mainPanel, BorderLayout.CENTER);

        // Panel lateral izquierdo con imagen
        JLabel imagenIzquierda = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/pngwing.com (1) (1).png")));
        imagenIzquierda.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(imagenIzquierda, BorderLayout.WEST);

        // Panel lateral derecho con otra imagen
        JLabel imagenDerecha = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/pngwing.com (2) (1).png")));
        imagenDerecha.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(imagenDerecha, BorderLayout.EAST);

        // Panel central con botones
        JPanel botonPanel = new JPanel(new GridBagLayout());
        botonPanel.setOpaque(false);
        mainPanel.add(botonPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        btnDepositar = crearBoton("DEPOSITAR");
        btnDepositar.addActionListener(e -> abrirVentana(new DepositosForm()));
        gbc.gridy = 0;
        botonPanel.add(btnDepositar, gbc);

        btnRetirar = crearBoton("RETIRAR");
        btnRetirar.addActionListener(e -> abrirVentana(new RetirosForm()));
        gbc.gridy = 1;
        botonPanel.add(btnRetirar, gbc);

        btnTransferir = crearBoton("TRANSFERIR");
        btnTransferir.addActionListener(e -> abrirVentana(new TransferenciaForm()));
        gbc.gridy = 2;
        botonPanel.add(btnTransferir, gbc);

        btnConsultar = crearBoton("CONSULTAR SALDO");
        btnConsultar.addActionListener(e -> abrirVentana(new ConsultarForm()));
        gbc.gridy = 3;
        botonPanel.add(btnConsultar, gbc);

        btnMovimientos = crearBoton("VER MOVIMIENTOS");
        btnMovimientos.addActionListener(e -> abrirVentana(new MovimientosForm()));
        gbc.gridy = 4;
        botonPanel.add(btnMovimientos, gbc);

        // Imagen decorativa inferior
        JLabel imagenInferior = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/monstersinc (1).png")));
        imagenInferior.setHorizontalAlignment(SwingConstants.CENTER);
        imagenInferior.setPreferredSize(new Dimension(900, 100));
        add(imagenInferior, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(250, 45));
        btn.setBackground(new Color(204, 204, 255));
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setFocusPainted(false);
        return btn;
    }

    private void abrirVentana(JFrame frame) {
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuForm().setVisible(true));
    }
}
