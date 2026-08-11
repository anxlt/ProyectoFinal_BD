package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import db.Conexion;

public class InformeSQL extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel modelo;

    public InformeSQL(String titulo, String descripcion, String sql) {
        setBounds(100, 100, 920, 620);
        setIconImage(Toolkit.getDefaultToolkit().getImage("recursos/icono.png"));
        getContentPane().setLayout(new BorderLayout());
        setTitle(titulo);
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);

        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        contentPanel.setBackground(new Color(4, 13, 18));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 10));

        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(24, 61, 61));
        panelHeader.setPreferredSize(new Dimension(900, 88));
        contentPanel.add(panelHeader, BorderLayout.NORTH);
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Consolas", Font.BOLD, 22));
        lblTitulo.setBounds(20, 12, 860, 32);
        panelHeader.add(lblTitulo);

        JLabel lblDesc = new JLabel("<html>" + descripcion + "</html>");
        lblDesc.setForeground(new Color(220, 220, 220));
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDesc.setBounds(20, 46, 860, 30);
        panelHeader.add(lblDesc);

        JPanel panelTabla = new JPanel();
        panelTabla.setBackground(new Color(228, 228, 228));
        panelTabla.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(panelTabla, BorderLayout.CENTER);
        panelTabla.setLayout(new BorderLayout(0, 0));

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(SystemColor.desktop);
        panelCentro.setLayout(new BorderLayout(0, 0));
        panelTabla.add(panelCentro, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        panelCentro.add(scrollPane, BorderLayout.CENTER);

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(modelo);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(36);
        table.setBackground(new Color(228, 228, 228));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(Color.BLACK);
        scrollPane.setViewportView(table);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(4, 87, 87));
        panelBotones.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
        contentPanel.add(panelBotones, BorderLayout.SOUTH);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setIcon(new ImageIcon("recursos/cerrar.png"));
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCerrar.setBackground(Color.WHITE);
        btnCerrar.setFocusable(false);
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBotones.add(btnCerrar);

        cargarDatos(sql);
    }

    private void cargarDatos(String sql) {
        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            Vector<String> headers = new Vector<>();
            for (int i = 1; i <= columnas; i++) {
                headers.add(formatearNombreColumna(meta.getColumnLabel(i)));
            }
            modelo.setColumnIdentifiers(headers);

            while (rs.next()) {
                Vector<Object> fila = new Vector<>();
                for (int i = 1; i <= columnas; i++) {
                    Object valor = rs.getObject(i);
                    fila.add(valor == null ? "" : valor);
                }
                modelo.addRow(fila);
            }

            DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
            leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
            }

            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay datos para mostrar.",
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar el informe:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatearNombreColumna(String raw) {
        if (raw == null || raw.isEmpty()) return raw;
        String s = raw.replace('_', ' ').trim();
        StringBuilder sb = new StringBuilder();
        for (String part : s.split("\\s+")) {
            if (part.isEmpty()) continue;
            if (sb.length() > 0) sb.append(' ');
            sb.append(Character.toUpperCase(part.charAt(0)));
            if (part.length() > 1) sb.append(part.substring(1).toLowerCase());
        }
        return sb.toString();
    }
}