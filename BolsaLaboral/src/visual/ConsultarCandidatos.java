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

public class ConsultarCandidatos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static JTable table;
	public static DefaultTableModel modelo = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	public static Object[] row;
	private Candidato seleccionado = null;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JTextField txtFiltro;
	private JButton btnVisualizar;

	/**
	 * Create the dialog.
	 */
	public ConsultarCandidatos() {
		setTitle("Listado de Candidatos");
		setIconImage(Toolkit.getDefaultToolkit().getImage("recursos/icono.png"));
		setBounds(100, 100, 665, 606);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(new Color(228,228,228));
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
							if(index >= 0) {
								seleccionado = BolsaLaboral.getInstancia().buscarCandidatoByCodigo(table.getValueAt(index, 0).toString());
								btnDelete.setEnabled(true);
								btnUpdate.setEnabled(true);
								btnVisualizar.setEnabled(true);
							}
						}
					});
					String [] headers = {"Codigo", "Nombre", "Cedula", "Nivel Academico"};
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
			buttonPane.setBackground(new Color(92, 131, 116));
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnUpdate = new JButton("Modificar");
				btnUpdate.setBackground(Color.WHITE);
				btnUpdate.setIcon(new ImageIcon("recursos/modificar.png"));
				btnUpdate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						RegistroCandidato registro = new RegistroCandidato(seleccionado);
						registro.setModal(true);
						registro.setLocationRelativeTo(ConsultarCandidatos.this);
						registro.setVisible(true);

						cargarCandidatos();

						seleccionado = null;
						btnDelete.setEnabled(false);
						btnUpdate.setEnabled(false);
						btnVisualizar.setEnabled(false);
					}
				});
				{
					btnVisualizar = new JButton("Visualizar");
					btnVisualizar.setBackground(Color.WHITE);
					btnVisualizar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(seleccionado != null) {
								visualizarCandidato(seleccionado);
							}
						}
					});
					btnVisualizar.setIcon(new ImageIcon("recursos/cv.png"));
					btnVisualizar.setFont(new Font("Segoe UI", Font.BOLD, 16));
					btnVisualizar.setEnabled(false);
					btnVisualizar.setActionCommand("OK");
					buttonPane.add(btnVisualizar);
				}
				btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 16));
				btnUpdate.setEnabled(false);
				btnUpdate.setActionCommand("OK");
				buttonPane.add(btnUpdate);
				getRootPane().setDefaultButton(btnUpdate);
			}
			{
				btnDelete = new JButton("Eliminar");
				btnDelete.setBackground(Color.WHITE);
				btnDelete.setIcon(new ImageIcon("recursos/eliminar.png"));
				btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 16));
				btnDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(seleccionado != null) {
							int option = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar al candidato " + seleccionado.getNombres() + " " + seleccionado.getApellidos() + " que posee el ID: "+seleccionado.getCodigo()+"?", "Eliminar", JOptionPane.WARNING_MESSAGE);
							if(option == JOptionPane.OK_OPTION){
								try {
									BolsaLaboral.getInstancia().eliminarCandidato(seleccionado);
									cargarCandidatos();
									seleccionado = null;
									btnDelete.setEnabled(false);
									btnUpdate.setEnabled(false);
									btnVisualizar.setEnabled(false);
								}
								catch (NotRemovableException ex) {
									JOptionPane.showMessageDialog(null,ex.getMessage(),"Advertencia",JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
				});
				btnDelete.setEnabled(false);
				buttonPane.add(btnDelete);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.setBackground(Color.WHITE);
				btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
				btnCancelar.setIcon(new ImageIcon("recursos/cerrar.png"));
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}

		table.setRowHeight(24);
		cargarCandidatos();
		table.getTableHeader().setReorderingAllowed(false);
		table.setBackground(new Color(228, 228, 228));
	}

	private void visualizarCandidato(Candidato candidato) {
		CV visualizar = new CV(candidato);
		visualizar.setModal(true);
		visualizar.setVisible(true);
	}

	public void filtrar() {
		String filtro = txtFiltro.getText().toLowerCase();
		modelo.setRowCount(0);

		seleccionado = null;
		btnDelete.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnVisualizar.setEnabled(false);

		for (Candidato aux : BolsaLaboral.getInstancia().getCandidatos()) {
			if (aux.getCodigo() == null) continue;
			boolean coincide =
					aux.getCodigo().toLowerCase().contains(filtro) ||
							(aux.getNombres() + " " + aux.getApellidos()).toLowerCase().contains(filtro) ||
							(aux.getIdentificacion() != null && aux.getIdentificacion().toLowerCase().contains(filtro)) ||
							getNivelAcademico(aux).toLowerCase().contains(filtro);

			if (coincide) {
				Object[] fila = new Object[4];
				fila[0] = aux.getCodigo();
				fila[1] = aux.getNombres() + " " + aux.getApellidos();
				fila[2] = aux.getIdentificacion();
				fila[3] = getNivelAcademico(aux);
				modelo.addRow(fila);
			}
		}
	}

	private static String getNivelAcademico(Candidato candidato) {
		if (candidato instanceof Universitario) {
			return "Estudiante Universitario";
		} else if (candidato instanceof TecnicoSuperior) {
			return "Estudiante Técnico";
		} else if (candidato instanceof Obrero) {
			return "Obrero";
		}
		return "";
	}

	public static void cargarCandidatos() {
		if (modelo == null) return;
		modelo.setRowCount(0);
		for (Candidato aux : BolsaLaboral.getInstancia().getCandidatos()) {
			if (aux.getCodigo() == null) continue;
			Object[] fila = new Object[4];
			fila[0] = aux.getCodigo();
			fila[1] = aux.getNombres() + " " + aux.getApellidos();
			fila[2] = aux.getIdentificacion();
			fila[3] = getNivelAcademico(aux);
			modelo.addRow(fila);
		}
	}

}