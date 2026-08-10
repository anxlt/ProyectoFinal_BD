package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import db.Conexion;

public class InformeSQL extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel modelo;

    public InformeSQL(String titulo, String descripcion, String sql) {
        setBounds(100, 100, 900, 600);
        setIconImage(Toolkit.getDefaultToolkit().getImage("recursos/icono.png"));
        getContentPane().setLayout(new BorderLayout());
        setTitle(titulo);
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setBackground(new Color(4, 13, 18));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(24, 61, 61));
        panelHeader.setPreferredSize(new java.awt.Dimension(900, 90));
        contentPanel.add(panelHeader, BorderLayout.NORTH);
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Consolas", Font.BOLD, 22));
        lblTitulo.setBounds(20, 10, 850, 35);
        panelHeader.add(lblTitulo);

        JLabel lblDesc = new JLabel(descripcion);
        lblDesc.setForeground(Color.WHITE);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDesc.setBounds(20, 45, 850, 30);
        panelHeader.add(lblDesc);

        JPanel panelTabla = new JPanel();
        panelTabla.setBackground(new Color(4, 13, 18));
        contentPanel.add(panelTabla, BorderLayout.CENTER);
        panelTabla.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(modelo);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(4, 87, 87));
        table.getTableHeader().setForeground(Color.WHITE);
        scrollPane.setViewportView(table);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(4, 87, 87));
        panelBotones.setPreferredSize(new java.awt.Dimension(900, 50));
        contentPanel.add(panelBotones, BorderLayout.SOUTH);
        panelBotones.setLayout(null);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setIcon(new ImageIcon("recursos/cerrar.png"));
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCerrar.setBackground(Color.WHITE);
        btnCerrar.setFocusable(false);
        btnCerrar.setBounds(730, 8, 140, 32);
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
                headers.add(meta.getColumnLabel(i));
            }
            modelo.setColumnIdentifiers(headers);

            while (rs.next()) {
                Vector<Object> fila = new Vector<>();
                for (int i = 1; i <= columnas; i++) {
                    fila.add(rs.getObject(i));
                }
                modelo.addRow(fila);
            }

            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay datos para mostrar.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}