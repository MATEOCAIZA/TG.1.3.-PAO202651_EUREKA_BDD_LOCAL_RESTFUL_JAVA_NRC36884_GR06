package ec.edu.monster.vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.JTableHeader;

/**
 * Tema común EurekaBank (morado / verde neón / naranja), igual en los 4 clientes.
 * Se instala una vez en main(): pinta cada ventana al abrirse, sin tocar los formularios.
 */
public final class HudTheme {

    public static final Color BG = new Color(0x0B0812), PANEL = new Color(0x160E24), INPUT = new Color(0x120B1F),
            PURPLE = new Color(0x5C2D91), PURPLE2 = new Color(0x7A45B8), GREEN = new Color(0x8FE34F),
            GREEN_DIM = new Color(0x3F6E22), ORANGE = new Color(0xFF8C2E), TEXT = new Color(0xECE6F7);
    private static final Font FONT = new Font("Segoe UI", Font.PLAIN, 14), MONO = new Font("Consolas", Font.PLAIN, 14);

    /** "SOAP · JAVA" o "REST · JAVA" según la carpeta/jar del proyecto, para distinguirlos con los dos abiertos. */
    public static final String NODO = nodo();

    private HudTheme() { }

    private static String nodo() {
        try {
            String ruta = HudTheme.class.getProtectionDomain().getCodeSource().getLocation().toString();
            return ruta.toUpperCase().contains("SOAP") ? "SOAP · JAVA" : "REST · JAVA";
        } catch (Exception e) {
            return "REST · JAVA";
        }
    }

    // Prefijo en el título y distintivo naranja arriba a la derecha
    private static void marcar(Window w) {
        if (w instanceof Frame) {
            Frame f = (Frame) w;
            String t = f.getTitle() == null ? "" : f.getTitle();
            if (!t.contains(NODO)) f.setTitle("[" + NODO + "] " + t);
        } else if (w instanceof Dialog) {
            Dialog d = (Dialog) w;
            String t = d.getTitle() == null ? "" : d.getTitle();
            if (!t.contains(NODO)) d.setTitle("[" + NODO + "] " + t);
        }
        if (!(w instanceof JFrame)) return;
        JLayeredPane capa = ((JFrame) w).getLayeredPane();
        JLabel marca = new JLabel("■ " + NODO);
        marca.putClientProperty("hud", Boolean.TRUE); // que style no lo repinte
        marca.setOpaque(true); marca.setBackground(INPUT); marca.setForeground(ORANGE);
        marca.setFont(MONO.deriveFont(Font.BOLD, 13f));
        marca.setBorder(new CompoundBorder(new LineBorder(ORANGE, 1), new EmptyBorder(2, 6, 2, 6)));
        marca.setSize(marca.getPreferredSize());
        capa.add(marca, JLayeredPane.PALETTE_LAYER);
        Runnable ubicar = () -> marca.setLocation(capa.getWidth() - marca.getWidth() - 8, 6);
        ubicar.run();
        capa.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) { ubicar.run(); }
        });
    }

    public static void install() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // Metal respeta los colores
        } catch (Exception ignored) { }
        for (String k : new String[]{"Panel.background", "OptionPane.background", "RootPane.background", "Viewport.background"}) {
            UIManager.put(k, PANEL);
        }
        UIManager.put("OptionPane.messageForeground", TEXT);
        UIManager.put("Label.foreground", TEXT);
        UIManager.put("Button.background", PURPLE);
        UIManager.put("Button.foreground", TEXT);
        UIManager.put("Button.select", GREEN);
        UIManager.put("Button.focus", ORANGE);
        Toolkit.getDefaultToolkit().addAWTEventListener(e -> {
            if (e.getID() == WindowEvent.WINDOW_OPENED) apply(((WindowEvent) e).getWindow());
        }, AWTEvent.WINDOW_EVENT_MASK);
    }

    public static void apply(Window w) {
        w.setBackground(PANEL);
        if (w instanceof RootPaneContainer) ((RootPaneContainer) w).getContentPane().setBackground(PANEL);
        style(w);
        marcar(w);
        w.repaint();
    }

    private static void style(Component c) {
        if (c instanceof JComponent && ((JComponent) c).getClientProperty("hud") != null) return;
        if (c instanceof JComponent) ((JComponent) c).putClientProperty("hud", Boolean.TRUE);

        if (c instanceof JButton) {
            JButton b = (JButton) c;
            b.setBackground(PURPLE); b.setForeground(TEXT); b.setFocusPainted(false);
            b.setFont(FONT.deriveFont(Font.BOLD)); b.setOpaque(true); b.setContentAreaFilled(true);
            b.setBorder(new CompoundBorder(new LineBorder(GREEN, 1), new EmptyBorder(6, 14, 6, 14)));
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { b.setBackground(GREEN); b.setForeground(INPUT); }
                @Override public void mouseExited(MouseEvent e) { b.setBackground(PURPLE); b.setForeground(TEXT); }
            });
        } else if (c instanceof JTextField || c instanceof JTextArea || c instanceof JComboBox || c instanceof JSpinner) {
            c.setBackground(INPUT); c.setForeground(GREEN); c.setFont(MONO);
            if (c instanceof javax.swing.text.JTextComponent) {
                ((javax.swing.text.JTextComponent) c).setCaretColor(ORANGE);
                ((JComponent) c).setBorder(new CompoundBorder(new LineBorder(PURPLE2, 1), new EmptyBorder(4, 6, 4, 6)));
            }
        } else if (c instanceof JTable) {
            JTable t = (JTable) c;
            t.setBackground(INPUT); t.setForeground(TEXT); t.setGridColor(GREEN_DIM); t.setFont(MONO); t.setRowHeight(24);
            t.setSelectionBackground(PURPLE2); t.setSelectionForeground(TEXT);
            JTableHeader h = t.getTableHeader();
            if (h != null) { h.setBackground(PURPLE); h.setForeground(GREEN); h.setFont(FONT.deriveFont(Font.BOLD)); }
        } else if (c instanceof JScrollPane) {
            ((JScrollPane) c).getViewport().setBackground(INPUT);
            ((JScrollPane) c).setBorder(new LineBorder(GREEN, 1));
        } else if (c instanceof JLabel) {
            JLabel l = (JLabel) c;
            if (l.isOpaque()) l.setBackground(PURPLE); // franjas de título
            if (l.getIcon() == null) {
                boolean titulo = l.getFont() != null && l.getFont().getSize() >= 16;
                l.setForeground(titulo ? GREEN : TEXT);
                if (titulo) l.setText(l.getText() == null ? null : l.getText().toUpperCase());
            }
        } else if (c instanceof JPanel || c instanceof JRootPane || c instanceof JLayeredPane) {
            c.setBackground(PANEL);
            Border b = ((JComponent) c).getBorder();
            if (b instanceof TitledBorder) { ((TitledBorder) b).setTitleColor(GREEN); ((TitledBorder) b).setBorder(new LineBorder(GREEN, 1)); }
        } else if (c instanceof JMenuBar || c instanceof JMenu || c instanceof JMenuItem) {
            c.setBackground(PURPLE); c.setForeground(TEXT);
        }
        if (c instanceof Container) for (Component k : ((Container) c).getComponents()) style(k);
    }
}
