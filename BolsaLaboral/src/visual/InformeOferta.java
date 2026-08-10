package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.BolsaLaboral;
import logico.OfertaLaboral;
import logico.Solicitud;

public class InformeOferta extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblArea;
	private JLabel lblPuesto;
	private JPanel pnlEnfasis;
	private JTable table;
	private JLabel lblNewLabel;
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
	private JPanel pnlResumen;
	private JLabel lblCantidadVac;
	private JLabel lblJornada;
	private JLabel lblModalidad;
	private JLabel lblEstado;
	private JLabel lblDeAceptacion;
	private ArrayList<Solicitud> solicitudesVinculadas = new ArrayList<>();

	/**
	 * Create the dialog.
	 */
	public InformeOferta(OfertaLaboral oferta) {
		setBounds(100, 100, 685, 651);
		setIconImage(Toolkit.getDefaultToolkit().getImage("recursos/icono.png"));
		getContentPane().setLayout(new BorderLayout());
		setTitle("(" + oferta.getCodigo() + ") " + oferta.getPuesto());
		setResizable(false);
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(new Color(255, 255, 255));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		pnlEnfasis = new JPanel();
		pnlEnfasis.setBounds(0, 0, 679, 130);
		contentPanel.add(pnlEnfasis);
		pnlEnfasis.setLayout(null);

		lblPuesto = new JLabel("PUESTO LABORAL");
		lblPuesto.setForeground(Color.WHITE);
		lblPuesto.setFont(new Font("Verdana", Font.BOLD, 26));
		lblPuesto.setBounds(19, 13, 595, 46);
		pnlEnfasis.add(lblPuesto);

		lblArea = new JLabel("");
		lblArea.setBounds(29, 82, 40, 40);
		pnlEnfasis.add(lblArea);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setBackground(Color.WHITE);
		separator.setBounds(19, 67, 630, 2);
		pnlEnfasis.add(separator);

		lblJornada = new JLabel("");
		lblJornada.setToolTipText((String) null);
		lblJornada.setBounds(81, 82, 40, 40);
		pnlEnfasis.add(lblJornada);

		lblModalidad = new JLabel("");
		lblModalidad.setToolTipText((String) null);
		lblModalidad.setBounds(133, 82, 40, 40);
		pnlEnfasis.add(lblModalidad);

		lblEstado = new JLabel("Estado:");
		lblEstado.setToolTipText((String) null);
		lblEstado.setHorizontalAlignment(SwingConstants.LEFT);
		lblEstado.setForeground(Color.WHITE);
		lblEstado.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblEstado.setBounds(360, 92, 190, 22);
		pnlEnfasis.add(lblEstado);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(347, 82, 1, 40);
		pnlEnfasis.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setForeground(Color.WHITE);
		separator_2.setBounds(174, 82, 1, 40);
		pnlEnfasis.add(separator_2);

		lblDeAceptacion = new JLabel("% de Aceptación:");
		lblDeAceptacion.setToolTipText((String) null);
		lblDeAceptacion.setHorizontalAlignment(SwingConstants.LEFT);
		lblDeAceptacion.setForeground(Color.WHITE);
		lblDeAceptacion.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblDeAceptacion.setBounds(185, 92, 150, 22);
		pnlEnfasis.add(lblDeAceptacion);

		JButton btnCancelar = new JButton("Cerrar");
		btnCancelar.setIcon(new ImageIcon("recursos/cerrar.png"));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnCancelar.setFocusable(false);
		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setActionCommand("Cancel");
		btnCancelar.setBounds(502, 555, 140, 31);
		contentPanel.add(btnCancelar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 187, 643, 339);
		contentPanel.add(scrollPane);

		{
			table = new JTable();
			table.setForeground(Color.BLACK);
			table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			String[] headers = { "Código", "Solicitante", "Fecha Solicitud", "Estado", " " };
			modelo.setColumnIdentifiers(headers);
			table.setModel(modelo);
			scrollPane.setViewportView(table);
		}

		lblNewLabel = new JLabel("Solicitudes Vinculadas");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 18));
		lblNewLabel.setBounds(157, 143, 352, 31);
		contentPanel.add(lblNewLabel);

		table.setRowHeight(36);

		pnlResumen = new JPanel();
		pnlResumen.setBounds(0, 547, 452, 48);
		contentPanel.add(pnlResumen);
		pnlResumen.setLayout(null);

		lblCantidadVac = new JLabel("Vacantes disponibles:");
		lblCantidadVac.setBounds(12, 13, 428, 22);
		lblCantidadVac.setHorizontalAlignment(SwingConstants.LEFT);
		lblCantidadVac.setForeground(Color.WHITE);
		lblCantidadVac.setFont(new Font("Consolas", Font.PLAIN, 18));
		pnlResumen.add(lblCantidadVac);

		table.getTableHeader().setReorderingAllowed(false);
		cargarOferta(oferta);
		solicitudesVinculadas = BolsaLaboral.getInstancia().obtenerSolicitudesVinculadas(oferta);
		cargarDetalles();
	}

	public void cargarOferta(OfertaLaboral oferta) {
		lblPuesto.setText(oferta.getPuesto());
		lblArea.setToolTipText(oferta.getArea());
		lblEstado.setText("Estado: " + oferta.getEstado());
		lblCantidadVac.setText("Vacantes Disponibles: " + oferta.getVacantes());
		lblDeAceptacion.setText("% Mínimo: " + oferta.getPorcentajeMinimo() + "%");

		String nombreArea = oferta.getArea().toLowerCase();
		nombreArea = nombreArea.replace("í", "i");
		nombreArea = nombreArea.replace("ó", "o");
		nombreArea = nombreArea.replace(" ", "");
		lblArea.setIcon(new ImageIcon("recursos/" + nombreArea + ".png"));

		Color fondo = getFondo(oferta.getArea());
		lblJornada.setToolTipText(oferta.getJornada());
		cargarJornada(oferta.getJornada());
		lblModalidad.setToolTipText(oferta.getModalidad());
		cargarModalidad(oferta.getModalidad());
		pnlEnfasis.setBackground(fondo);
		pnlResumen.setBackground(fondo);
	}

	private void cargarJornada(String nombreJornada) {
		nombreJornada = nombreJornada.toLowerCase();
		nombreJornada = nombreJornada.replace(" ", "");
		lblJornada.setIcon(new ImageIcon("recursos/" + nombreJornada + ".png"));
	}

	private void cargarModalidad(String modalidad) {
		String nombreModalidad = modalidad.toLowerCase();
		nombreModalidad = nombreModalidad.replace("í", "i");
		lblModalidad.setIcon(new ImageIcon("recursos/" + nombreModalidad + ".png"));
	}

	public Color getFondo(String area) {
		switch (area) {
			case "Finanzas":
				return new Color(213, 69, 27);
			case "Recursos Humanos":
				return new Color(27, 60, 83);
			case "Marketing":
				return new Color(197, 23, 46);
			case "Limpieza":
				return new Color(78, 102, 136);
			case "Seguridad":
				return new Color(10, 64, 12);
			case "TI":
				return new Color(9, 107, 104);
			case "Salud":
				return new Color(162, 18, 50);
			case "Operaciones":
				return new Color(39, 63, 79);
			case "Administración":
				return new Color(190, 49, 68);
			case "Atención al Cliente":
				return new Color(130, 17, 49);
			default:
				return new Color(57, 62, 7);
		}
	}

	private String getEstado(String estado) {
		estado = estado.toLowerCase();
		estado = estado.replace(" ", "");
		return "recursos/" + estado + ".png";
	}

	public void cargarDetalles() {
		modelo.setRowCount(0);
		row = new Object[table.getColumnCount()];
		for (Solicitud sol : solicitudesVinculadas) {
			row[0] = sol.getCodigo();
			row[1] = sol.getSolicitante().getNombres() + " " + sol.getSolicitante().getApellidos();
			row[2] = sol.getFechaSolicitud().toString();
			row[3] = sol.getEstado();
			row[4] = new ImageIcon(getEstado(sol.getEstado()));
			modelo.addRow(row);
		}
	}
}