package visual;

import logico.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;


public class ResultadosVinculacion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static JTable table;
	public static DefaultTableModel modelo = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		public Class getColumnClass(int column)
		{
			return getValueAt(0, column).getClass();
		}
	};
	public static Object[] row;
	private ResultadoMatcheo seleccionado = null;
	private JButton btnContratar;
	private static ArrayList<ResultadoMatcheo> resultados = new ArrayList<>();

	/**
	 * Create the dialog.
	 */
	public ResultadosVinculacion(OfertaLaboral ofertaVinculada) {
		setTitle("Resultados de la Vinculacion");
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
								seleccionado = BolsaLaboral.getInstancia().buscarResultado(resultados,ofertaVinculada.getCodigo(),table.getValueAt(index,0).toString());
								btnContratar.setEnabled(true);
							}
						}
					});
					String [] headers = {"Codigo", "Solicitante", "Porcentaje", "Condicion"};
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
				JLabel lblNewLabel = new JLabel("Oferta:");
				lblNewLabel.setForeground(Color.BLACK);
				lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
				pnlFiltro.add(lblNewLabel);
			}
			{
				JLabel lblNombreOferta = new JLabel("");
				lblNombreOferta.setText(ofertaVinculada.getPuesto() + ", " + ofertaVinculada.getOfertador().getNombre());
				lblNombreOferta.setForeground(Color.BLACK);
				lblNombreOferta.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				pnlFiltro.add(lblNombreOferta);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(4, 87, 87));
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnContratar = new JButton("Vincular");
				btnContratar.setBackground(Color.WHITE);
				btnContratar.setIcon(new ImageIcon("recursos/vincular.png"));
				btnContratar.setFont(new Font("Segoe UI", Font.BOLD, 16));
				btnContratar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(seleccionado.getOferta().getVacantes() > 0) {
							if(BolsaLaboral.getInstancia().vincularOferta(seleccionado)) {
								JOptionPane.showMessageDialog(null,"Se ha creado la solicitud correctamente a la oferta " + seleccionado.getOferta().getPuesto() + ".","Informacion",JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null,"Esta solicitud ya existe.","Informacion",JOptionPane.INFORMATION_MESSAGE);
							}
						}
						else {
							JOptionPane.showMessageDialog(null,"El candidato no puede ser vinculado ya que no hay vacantes disponibles.","Advertencia",JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				btnContratar.setEnabled(false);
				buttonPane.add(btnContratar);
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

		resultados = BolsaLaboral.getInstancia().obtenerCandidatosOrdenadosParaOferta(ofertaVinculada);
		cargarResultados(ofertaVinculada);
		table.setRowHeight(36);
		table.getTableHeader().setReorderingAllowed(false);
		table.setBackground(new Color(228, 228, 228));
	}

	private static String getImagen(String condicion) {
		condicion = condicion.toLowerCase();
		condicion = condicion.replace(" ","");
		return "recursos/" + condicion + ".png";
	}

	public static void cargarResultados(OfertaLaboral oferta) {
		if (modelo == null) return;
		modelo.setRowCount(0);
		for (ResultadoMatcheo aux : resultados) {
			Object[] fila = new Object[4];
			fila[0] = aux.getSolicitante().getCodigo();
			fila[1] = aux.getSolicitante().getNombres() + " " + aux.getSolicitante().getApellidos();
			fila[2] = aux.getPorcentaje() + "%";
			fila[3] = new ImageIcon(getImagen(aux.getCondicion()));
			modelo.addRow(fila);
		}
	}

}