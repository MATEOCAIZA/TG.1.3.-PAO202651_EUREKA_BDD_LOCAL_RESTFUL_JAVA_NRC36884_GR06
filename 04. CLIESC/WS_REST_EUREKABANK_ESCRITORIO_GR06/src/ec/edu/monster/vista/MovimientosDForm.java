package ec.edu.monster.vista;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientosDForm extends JFrame {
    private JTable tableMovimientos;
    private JButton btnMenuPrincipal, btnAnterior, btnSiguiente;
    private JLabel lblPagina;
    private List<JSONObject> movimientos;
    private int paginaActual = 1;
    private final int MOVIMIENTOS_POR_PAGINA = 10;

    public MovimientosDForm(String jsonMovimientos) {
        setTitle("Movimientos - Eurekabank");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Encabezado
        JLabel encabezado = new JLabel("LISTA DE MOVIMIENTOS", SwingConstants.CENTER);
        encabezado.setFont(new Font("Arial", Font.BOLD, 28));
        encabezado.setOpaque(true);
        encabezado.setBackground(new Color(153, 204, 255));
        encabezado.setPreferredSize(new Dimension(1080, 60));
        add(encabezado, BorderLayout.NORTH);

        // Tabla con scroll
        tableMovimientos = new JTable();
        tableMovimientos.setFont(new Font("Arial", Font.PLAIN, 14));
        tableMovimientos.setRowHeight(28);
        JScrollPane scrollPane = new JScrollPane(tableMovimientos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Movimientos registrados"));
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setPreferredSize(new Dimension(1080, 220));
        panelInferior.setBackground(new Color(240, 245, 255));
        add(panelInferior, BorderLayout.SOUTH);

        // Botón Menú Principal
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(240, 245, 255));
        btnMenuPrincipal = new JButton("MENÚ PRINCIPAL");
        btnMenuPrincipal.setFont(new Font("Arial", Font.BOLD, 16));
        btnMenuPrincipal.setBackground(new Color(255, 229, 204));
        btnMenuPrincipal.setPreferredSize(new Dimension(200, 40));
        btnMenuPrincipal.addActionListener(e -> {
            new MenuForm().setVisible(true);
            this.dispose();
        });
        panelBoton.add(btnMenuPrincipal);

        // Paginación
        JPanel panelPaginacion = new JPanel();
        panelPaginacion.setBackground(new Color(240, 245, 255));

        btnAnterior = new JButton("◀ Anterior");
        btnSiguiente = new JButton("Siguiente ▶");
        lblPagina = new JLabel("Página 1", SwingConstants.CENTER);

        btnAnterior.setFont(new Font("Arial", Font.BOLD, 14));
        btnSiguiente.setFont(new Font("Arial", Font.BOLD, 14));
        lblPagina.setFont(new Font("Arial", Font.PLAIN, 14));

        btnAnterior.setEnabled(false);
        btnAnterior.addActionListener(e -> {
            paginaActual--;
            cargarPagina();
        });

        btnSiguiente.addActionListener(e -> {
            paginaActual++;
            cargarPagina();
        });

        panelPaginacion.add(btnAnterior);
        panelPaginacion.add(lblPagina);
        panelPaginacion.add(btnSiguiente);

        // Imágenes decorativas
        JPanel panelImagenes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        panelImagenes.setBackground(new Color(240, 245, 255));
        JLabel img1 = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/pngwing.com (1) (1).png")));
        JLabel img2 = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/pngwing.com (2) (1).png")));
        JLabel img3 = new JLabel(new ImageIcon(getClass().getResource("/ec/edu/monster/recursos/monstersinc (1).png")));
        panelImagenes.add(img1);
        panelImagenes.add(img2);
        panelImagenes.add(img3);

        // Estructura del pie de página
        panelInferior.add(panelBoton, BorderLayout.NORTH);
        panelInferior.add(panelPaginacion, BorderLayout.CENTER);
        panelInferior.add(panelImagenes, BorderLayout.SOUTH);

        // Inicializar datos
        cargarJSON(jsonMovimientos);
        cargarPagina();
    }

    private void cargarJSON(String json) {
        movimientos = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                movimientos.add(array.getJSONObject(i));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al procesar JSON.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPagina() {
        int inicio = (paginaActual - 1) * MOVIMIENTOS_POR_PAGINA;
        int fin = Math.min(inicio + MOVIMIENTOS_POR_PAGINA, movimientos.size());

        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"N°", "Fecha", "Tipo", "Importe"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = inicio; i < fin; i++) {
            JSONObject obj = movimientos.get(i);
            modelo.addRow(new Object[]{
                    obj.getInt("nromov"),
                    obj.getString("fecha"),
                    obj.getString("tipo"),
                    obj.getDouble("importe")
            });
        }

        tableMovimientos.setModel(modelo);
        actualizarControlesPaginacion();
    }

    private void actualizarControlesPaginacion() {
        int totalPaginas = (int) Math.ceil((double) movimientos.size() / MOVIMIENTOS_POR_PAGINA);
        lblPagina.setText("Página " + paginaActual + " de " + totalPaginas);
        btnAnterior.setEnabled(paginaActual > 1);
        btnSiguiente.setEnabled(paginaActual < totalPaginas);
    }
}
