package visual;

import logico.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import exception.NotRemovableException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConsultarSolicitudes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static JTable table;
	public static DefaultTableModel modelo = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		public Class getColumnClass(int column) {
			return getValueAt(0, column).getClass();
		}
	};
	public static Object[] row;
	private Solicitud seleccionado = null;
	private JTextField txtFiltro;
	private JButton btnContratar;
	private JButton btnRechazar;

	/**
	 * Create the dialog.
	 */
	public ConsultarSolicitudes() {
		setTitle("Listado de Solicitudes a Procesar");
		setIconImage(Toolkit.getDefaultToolkit().getImage("recursos/icono.png"));
		setBounds(100, 100, 665, 606);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(new Color(228, 228, 228));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(SystemColor.desktop);
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					table = new JTable();
					table.setForeground(Color.BLACK);
					table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							int index = table.getSelectedRow();
							if (index >= 0) {
								seleccionado = BolsaLaboral.getInstancia()
										.buscarSolicitudByCodigo(table.getValueAt(index, 0).toString());
								btnContratar.setEnabled(true);
								btnRechazar.setEnabled(true);
							}
						}
					});
					String[] headers = { "Código", "Solicitante", "Puesto", "Ofertador", "Estado" };
					modelo.setColumnIdentifiers(headers);
					table.setModel(modelo);
					scrollPane.setViewportView(table);
				}
			}
		}
		{
			JPanel pnlFiltro = new JPanel();
			pnlFiltro.setBackground(new Color(228, 228, 228));
			contentPanel.add(pnlFiltro, BorderLayout.NORTH);
			{
				JLabel lblIconFiltrar = new JLabel("");
				lblIconFiltrar.setIcon(new ImageIcon("recursos/filtrar.png"));
				pnlFiltro.add(lblIconFiltrar);
			}
			{
				JLabel lblNewLabel = new JLabel("Criterio del Filtro: ");
				lblNewLabel.setForeground(Color.BLACK);
				lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
				pnlFiltro.add(lblNewLabel);
			}
			{
				txtFiltro = new JTextField();
				txtFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				txtFiltro.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						filtrar();
					}
				});
				pnlFiltro.add(txtFiltro);
				txtFiltro.setColumns(16);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(4, 87, 87));
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				{
					btnContratar = new JButton("Contratar");
					btnContratar.setIcon(new ImageIcon("recursos/trabajar.png"));
					btnContratar.setBackground(Color.WHITE);
					btnContratar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (seleccionado != null && BolsaLaboral.getInstancia().esProcesable(seleccionado)) {
								BolsaLaboral.getInstancia().contratarCandidato(seleccionado);
								JOptionPane.showMessageDialog(null,
										"Contratación procesada satisfactoriamente.",
										"Información", JOptionPane.INFORMATION_MESSAGE);
								cargarSolicitudes();
								filtrar();
							} else {
								JOptionPane.showMessageDialog(null,
										"Solo se pueden aprobar y rechazar solicitudes que no han sido procesadas.",
										"Advertencia", JOptionPane.WARNING_MESSAGE);
							}
						}
					});
					{
						btnRechazar = new JButton("Rechazar");
						btnRechazar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if (seleccionado != null && BolsaLaboral.getInstancia().esProcesable(seleccionado)) {
									BolsaLaboral.getInstancia().rechazarCandidato(seleccionado);
									JOptionPane.showMessageDialog(null,
											"Rechazo procesado satisfactoriamente.",
											"Información", JOptionPane.INFORMATION_MESSAGE);
									cargarSolicitudes();
									filtrar();
								} else {
									JOptionPane.showMessageDialog(null,
											"Solo se pueden aprobar y rechazar solicitudes que no han sido procesadas.",
											"Advertencia", JOptionPane.WARNING_MESSAGE);
								}
							}
						});
						btnRechazar.setIcon(new ImageIcon("recursos/rechazar.png"));
						btnRechazar.setFont(new Font("Segoe UI", Font.BOLD, 16));
						btnRechazar.setEnabled(false);
						btnRechazar.setBackground(Color.WHITE);
						btnRechazar.setActionCommand("OK");
						buttonPane.add(btnRechazar);
					}
					btnContratar.setFont(new Font("Segoe UI", Font.BOLD, 16));
					btnContratar.setEnabled(false);
					btnContratar.setActionCommand("OK");
					buttonPane.add(btnContratar);
				}
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.setBackground(Color.WHITE);
				btnCancelar.setIcon(new ImageIcon("recursos/cerrar.png"));
				btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}

		cargarSolicitudes();
		table.setRowHeight(36);
		table.getTableHeader().setReorderingAllowed(false);
		table.setBackground(new Color(228, 228, 228));
	}

	public void filtrar() {
		String filtro = txtFiltro.getText().toLowerCase();
		modelo.setRowCount(0);

		seleccionado = null;
		btnContratar.setEnabled(false);
		btnRechazar.setEnabled(false);

		for (Solicitud aux : BolsaLaboral.getInstancia().getSolicitudes()) {
			if (aux.getCodigo() == null)
				continue;
			boolean coincide = aux.getCodigo().toLowerCase().contains(filtro)
					|| (aux.getOfertaSolicitada() != null && aux.getOfertaSolicitada().getPuesto() != null
					&& aux.getOfertaSolicitada().getPuesto().toLowerCase().contains(filtro))
					|| (aux.getOfertaSolicitada() != null && aux.getOfertaSolicitada().getOfertador() != null
					&& aux.getOfertaSolicitada().getOfertador().getNombre() != null
					&& aux.getOfertaSolicitada().getOfertador().getNombre().toLowerCase().contains(filtro))
					|| (aux.getSolicitante() != null
					&& (aux.getSolicitante().getNombres() + " " + aux.getSolicitante().getApellidos())
					.toLowerCase().contains(filtro))
					|| (aux.getEstado() != null && aux.getEstado().toLowerCase().contains(filtro));

			if (coincide) {
				Object[] fila = new Object[5];
				fila[0] = aux.getCodigo();
				fila[1] = aux.getSolicitante().getNombres() + " " + aux.getSolicitante().getApellidos();
				fila[2] = aux.getOfertaSolicitada().getPuesto();
				fila[3] = aux.getOfertaSolicitada().getOfertador().getNombre();
				fila[4] = aux.getEstado();
				modelo.addRow(fila);
			}
		}
	}

	public static void cargarSolicitudes() {
		if (modelo == null)
			return;
		modelo.setRowCount(0);
		for (Solicitud aux : BolsaLaboral.getInstancia().getSolicitudes()) {
			if (aux.getCodigo() == null)
				continue;
			Object[] fila = new Object[5];
			fila[0] = aux.getCodigo();
			fila[1] = aux.getSolicitante().getNombres() + " " + aux.getSolicitante().getApellidos();
			fila[2] = aux.getOfertaSolicitada().getPuesto();
			fila[3] = aux.getOfertaSolicitada().getOfertador().getNombre();
			fila[4] = aux.getEstado();
			modelo.addRow(fila);
		}
	}
}