package visual;

import java.awt.BorderLayout;
import exception.*;
import logico.*;
import db.*;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;

public class RegistroOfertaLaboral extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private OfertaLaboral ofertaAct = null;

	private JTextField txtCodigo;
	private JTextField txtPuesto;
	private JRadioButton rdTecnico;
	private JRadioButton rdUniversitario;
	private JRadioButton rdObrero;
	private JPanel pnlTipoCand;
	private JLabel lblIcoModalidad;
	private JLabel lblIcoJornada;
	private JComboBox cmbJornada;
	private JComboBox cmbModalidad;
	private JComboBox cmbArea;
	private JLabel lblIcoArea;
	private JPanel pnlTecnico;
	private JPanel pnlCarreras;
	private JPanel pnlObrero;
	private JComboBox cmbOfertador;
	private JSpinner spnSalario;
	private JTextArea txtDescripcion;
	private JSpinner spnVacantes;

	private JCheckBox chckbxIngls, chckbxPortugus, chckbxItaliano, chckbxAlemn,
			chckbxMandarn, chckbxCoreano, chckbxEspaol, chckbxFrancs, chckbxJapons;

	private JCheckBox chkbxMayor;
	private JCheckBox chkReubicacion;
	private JCheckBox chkLicencia;

	private JCheckBox[] checkIdiomas;

	private JSpinner spnAniosExp;
	private JSpinner spnPorcentaje;

	private CarreraDAO carreraDAO = new CarreraDAOImpl();
	private AreaTecnicaDAO areaTecnicaDAO = new AreaTecnicaDAOImpl();
	private HabilidadDAO habilidadDAO = new HabilidadDAOImpl();

	private JComboBox<Carrera> cmbCarrera;
	private JComboBox<AreaTecnica> cmbAreaTecnica;
	private JComboBox<Habilidad> cmbHabilidad;

	public RegistroOfertaLaboral(JDialog parent, OfertaLaboral oferta) {
		super(parent, true);
		setIconImage(Toolkit.getDefaultToolkit().getImage("recursos/icono.png"));
		setResizable(false);
		if (oferta == null) {
			setTitle("Registrar Oferta Laboral");
		} else {
			setTitle("Modificar Oferta Laboral");
			ofertaAct = oferta;
		}
		setBounds(100, 100, 570, 662);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(228, 228, 228));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JTabbedPane contenedor = new JTabbedPane(JTabbedPane.TOP);
		contenedor.setBackground(new Color(153, 204, 204));
		contenedor.setForeground(Color.BLACK);
		contenedor.setFont(new Font("Segoe UI", Font.BOLD, 18));
		contentPanel.add(contenedor, BorderLayout.CENTER);

		// ===== Generalidades =====
		JPanel pnlGeneralidades = new JPanel();
		contenedor.addTab("Generalidades", null, pnlGeneralidades, null);
		pnlGeneralidades.setBackground(new Color(228, 228, 228));
		pnlGeneralidades.setLayout(null);

		JLabel label = new JLabel("Código:");
		label.setFont(new Font("Segoe UI", Font.BOLD, 16));
		label.setBounds(12, 9, 84, 29);
		pnlGeneralidades.add(label);

		txtCodigo = new JTextField();
		txtCodigo.setText("");
		txtCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtCodigo.setEditable(false);
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(127, 15, 305, 22);
		pnlGeneralidades.add(txtCodigo);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(12, 55, 503, 6);
		pnlGeneralidades.add(separator);

		JLabel lblNombres = new JLabel("Puesto:");
		lblNombres.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNombres.setBounds(12, 69, 84, 29);
		pnlGeneralidades.add(lblNombres);

		txtPuesto = new JTextField();
		txtPuesto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtPuesto.setColumns(10);
		txtPuesto.setBounds(127, 73, 305, 22);
		pnlGeneralidades.add(txtPuesto);

		JLabel lblApellido = new JLabel("Ofertador:");
		lblApellido.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblApellido.setBounds(12, 121, 84, 29);
		pnlGeneralidades.add(lblApellido);

		JLabel lblCdula = new JLabel("Salario:");
		lblCdula.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblCdula.setBounds(12, 175, 84, 29);
		pnlGeneralidades.add(lblCdula);

		cmbOfertador = new JComboBox();
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
		modelo.addElement("<Seleccione un ofertador>");
		for (CentroEmpleador centro : BolsaLaboral.getInstancia().getCentros()) {
			modelo.addElement(centro.getCodigo() + " : " + centro.getNombre());
		}
		cmbOfertador.setModel(modelo);
		cmbOfertador.setSelectedIndex(0);
		cmbOfertador.setMaximumRowCount(Math.max(1, modelo.getSize()));
		cmbOfertador.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbOfertador.setBounds(127, 124, 305, 29);
		pnlGeneralidades.add(cmbOfertador);

		spnSalario = new JSpinner();
		spnSalario.setModel(new SpinnerNumberModel(new Float(12000), new Float(12000), null, new Float(1)));
		spnSalario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		spnSalario.setBounds(127, 182, 305, 22);
		pnlGeneralidades.add(spnSalario);

		JLabel lblDescripcin = new JLabel("Descripción:");
		lblDescripcin.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblDescripcin.setBounds(12, 230, 108, 29);
		pnlGeneralidades.add(lblDescripcin);

		txtDescripcion = new JTextArea();
		txtDescripcion.setBounds(127, 235, 305, 107);
		pnlGeneralidades.add(txtDescripcion);

		JLabel lblCantVacantes = new JLabel("Vacantes:");
		lblCantVacantes.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblCantVacantes.setBounds(12, 367, 108, 29);
		pnlGeneralidades.add(lblCantVacantes);

		spnVacantes = new JSpinner();
		spnVacantes.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spnVacantes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		spnVacantes.setBounds(127, 371, 305, 22);
		pnlGeneralidades.add(spnVacantes);

		JLabel label_1 = new JLabel("Área:");
		label_1.setFont(new Font("Segoe UI", Font.BOLD, 16));
		label_1.setBounds(12, 419, 106, 29);
		pnlGeneralidades.add(label_1);

		cmbArea = new JComboBox();
		cmbArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarArea();
			}
		});
		cmbArea.setModel(new DefaultComboBoxModel(new String[] {
				"No definido", "Finanzas", "Recursos Humanos", "Marketing", "Limpieza", "Seguridad",
				"TI", "Salud", "Operaciones", "Administración", "Atención al Cliente", "Educación"
		}));
		cmbArea.setMaximumRowCount(12);
		cmbArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbArea.setBounds(127, 417, 305, 29);
		pnlGeneralidades.add(cmbArea);

		lblIcoArea = new JLabel("");
		lblIcoArea.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblIcoArea.setBounds(444, 416, 32, 32);
		pnlGeneralidades.add(lblIcoArea);

		// ===== Requerimientos =====
		JPanel pnlEspecializacion = new JPanel();
		contenedor.addTab("Requerimientos", null, pnlEspecializacion, null);
		pnlEspecializacion.setLayout(null);
		pnlEspecializacion.setBackground(new Color(228, 228, 228));

		pnlTipoCand = new JPanel();
		pnlTipoCand.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
				"Nivel Académico Requerido", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlTipoCand.setBackground(new Color(228, 228, 228));
		pnlTipoCand.setBounds(12, 13, 510, 71);
		pnlEspecializacion.add(pnlTipoCand);
		pnlTipoCand.setLayout(null);

		rdUniversitario = new JRadioButton("Estudiante Universitario");
		rdUniversitario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarEspecializacion("Estudiante Universitario");
			}
		});
		rdUniversitario.setSelected(true);
		rdUniversitario.setBackground(new Color(228, 228, 228));
		rdUniversitario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		rdUniversitario.setBounds(29, 26, 182, 25);
		pnlTipoCand.add(rdUniversitario);

		rdTecnico = new JRadioButton("Estudiante Técnico");
		rdTecnico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarEspecializacion("Estudiante Tecnico");
			}
		});
		rdTecnico.setBackground(new Color(228, 228, 228));
		rdTecnico.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		rdTecnico.setBounds(215, 26, 149, 25);
		pnlTipoCand.add(rdTecnico);

		rdObrero = new JRadioButton("Obrero");
		rdObrero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarEspecializacion("Obrero");
			}
		});
		rdObrero.setBackground(new Color(228, 228, 228));
		rdObrero.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		rdObrero.setBounds(368, 26, 134, 25);
		pnlTipoCand.add(rdObrero);

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(rdObrero);
		grupo.add(rdUniversitario);
		grupo.add(rdTecnico);

		// Carrera (1 combo)
		pnlCarreras = new JPanel();
		pnlCarreras.setLayout(null);
		pnlCarreras.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
				"Carrera requerida", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlCarreras.setBackground(new Color(228, 228, 228));
		pnlCarreras.setBounds(12, 97, 510, 265);
		pnlEspecializacion.add(pnlCarreras);

		JLabel lblCarreraReq = new JLabel("Carrera:");
		lblCarreraReq.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblCarreraReq.setBounds(12, 33, 123, 29);
		pnlCarreras.add(lblCarreraReq);

		cmbCarrera = new JComboBox<>();
		for (Carrera c : carreraDAO.listarTodas()) {
			cmbCarrera.addItem(c);
		}
		if (cmbCarrera.getItemCount() > 0) cmbCarrera.setSelectedIndex(0);
		cmbCarrera.setMaximumRowCount(11);
		cmbCarrera.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbCarrera.setBounds(150, 34, 305, 29);
		pnlCarreras.add(cmbCarrera);

		// Técnico
		pnlTecnico = new JPanel();
		pnlTecnico.setLayout(null);
		pnlTecnico.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
				"Requerimientos Técnicos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlTecnico.setBackground(new Color(228, 228, 228));
		pnlTecnico.setBounds(12, 97, 510, 265);
		pnlEspecializacion.add(pnlTecnico);

		JLabel lblreaRequerida = new JLabel("Área requerida:");
		lblreaRequerida.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblreaRequerida.setBounds(12, 33, 123, 29);
		pnlTecnico.add(lblreaRequerida);

		cmbAreaTecnica = new JComboBox<>();
		for (AreaTecnica a : areaTecnicaDAO.listarTodas()) {
			cmbAreaTecnica.addItem(a);
		}
		if (cmbAreaTecnica.getItemCount() > 0) cmbAreaTecnica.setSelectedIndex(0);
		cmbAreaTecnica.setMaximumRowCount(11);
		cmbAreaTecnica.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbAreaTecnica.setBounds(150, 34, 305, 29);
		pnlTecnico.add(cmbAreaTecnica);

		spnAniosExp = new JSpinner();
		spnAniosExp.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spnAniosExp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		spnAniosExp.setBounds(181, 89, 274, 22);
		pnlTecnico.add(spnAniosExp);

		JLabel lblMnimoDeExperiencia = new JLabel("Años de experiencia:");
		lblMnimoDeExperiencia.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblMnimoDeExperiencia.setBounds(12, 85, 171, 29);
		pnlTecnico.add(lblMnimoDeExperiencia);

		// Obrero
		pnlObrero = new JPanel();
		pnlObrero.setLayout(null);
		pnlObrero.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
				"Requerimientos del Obrero", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlObrero.setBackground(new Color(228, 228, 228));
		pnlObrero.setBounds(12, 97, 510, 265);
		pnlEspecializacion.add(pnlObrero);

		JLabel lblHabilidadRequerida = new JLabel("Habilidad requerida:");
		lblHabilidadRequerida.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblHabilidadRequerida.setBounds(12, 33, 171, 29);
		pnlObrero.add(lblHabilidadRequerida);

		cmbHabilidad = new JComboBox<>();
		for (Habilidad h : habilidadDAO.listarTodas()) {
			cmbHabilidad.addItem(h);
		}
		if (cmbHabilidad.getItemCount() > 0) cmbHabilidad.setSelectedIndex(0);
		cmbHabilidad.setMaximumRowCount(11);
		cmbHabilidad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbHabilidad.setBounds(181, 34, 274, 29);
		pnlObrero.add(cmbHabilidad);

		// Idiomas
		JPanel pnlIdiomas = new JPanel();
		pnlIdiomas.setBounds(12, 375, 510, 145);
		pnlEspecializacion.add(pnlIdiomas);
		pnlIdiomas.setLayout(null);
		pnlIdiomas.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)),
				"Idiomas Requeridos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlIdiomas.setBackground(new Color(228, 228, 228));

		chckbxIngls = new JCheckBox("Inglés");
		chckbxIngls.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxIngls.setBackground(new Color(228, 228, 228));
		chckbxIngls.setBounds(8, 24, 113, 25);
		pnlIdiomas.add(chckbxIngls);

		chckbxItaliano = new JCheckBox("Italiano");
		chckbxItaliano.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxItaliano.setBackground(new Color(228, 228, 228));
		chckbxItaliano.setBounds(8, 61, 113, 25);
		pnlIdiomas.add(chckbxItaliano);

		chckbxEspaol = new JCheckBox("Español");
		chckbxEspaol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxEspaol.setBackground(new Color(228, 228, 228));
		chckbxEspaol.setBounds(8, 101, 113, 25);
		pnlIdiomas.add(chckbxEspaol);

		chckbxFrancs = new JCheckBox("Francés");
		chckbxFrancs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxFrancs.setBackground(new Color(228, 228, 228));
		chckbxFrancs.setBounds(181, 101, 113, 25);
		pnlIdiomas.add(chckbxFrancs);

		chckbxPortugus = new JCheckBox("Portugués");
		chckbxPortugus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxPortugus.setBackground(new Color(228, 228, 228));
		chckbxPortugus.setBounds(181, 25, 113, 25);
		pnlIdiomas.add(chckbxPortugus);

		chckbxAlemn = new JCheckBox("Alemán");
		chckbxAlemn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxAlemn.setBackground(new Color(228, 228, 228));
		chckbxAlemn.setBounds(181, 62, 113, 25);
		pnlIdiomas.add(chckbxAlemn);

		chckbxMandarn = new JCheckBox("Chino");
		chckbxMandarn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxMandarn.setBackground(new Color(228, 228, 228));
		chckbxMandarn.setBounds(389, 25, 113, 25);
		pnlIdiomas.add(chckbxMandarn);

		chckbxCoreano = new JCheckBox("Coreano");
		chckbxCoreano.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxCoreano.setBackground(new Color(228, 228, 228));
		chckbxCoreano.setBounds(389, 62, 113, 25);
		pnlIdiomas.add(chckbxCoreano);

		chckbxJapons = new JCheckBox("Japonés");
		chckbxJapons.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxJapons.setBackground(new Color(228, 228, 228));
		chckbxJapons.setBounds(389, 101, 113, 25);
		pnlIdiomas.add(chckbxJapons);

		// ===== Laboral =====
		JPanel pnlCondiciones = new JPanel();
		pnlCondiciones.setBackground(new Color(228, 228, 228));
		contenedor.addTab("Laboral", null, pnlCondiciones, null);
		pnlCondiciones.setLayout(null);

		JLabel lblModalidad = new JLabel("Modalidad:");
		lblModalidad.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblModalidad.setBounds(12, 17, 106, 29);
		pnlCondiciones.add(lblModalidad);

		cmbModalidad = new JComboBox();
		cmbModalidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarModalidad();
			}
		});
		cmbModalidad.setModel(new DefaultComboBoxModel(new String[] { "Presencial", "Remoto", "Híbrido" }));
		cmbModalidad.setMaximumRowCount(11);
		cmbModalidad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbModalidad.setBounds(111, 18, 305, 29);
		pnlCondiciones.add(cmbModalidad);

		lblIcoModalidad = new JLabel("");
		lblIcoModalidad.setBounds(433, 17, 32, 32);
		pnlCondiciones.add(lblIcoModalidad);

		JLabel lblJornada = new JLabel("Jornada:");
		lblJornada.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblJornada.setBounds(12, 71, 106, 29);
		pnlCondiciones.add(lblJornada);

		cmbJornada = new JComboBox();
		cmbJornada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarJornada();
			}
		});
		cmbJornada.setModel(new DefaultComboBoxModel(new String[] {
				"Tiempo Completo", "Medio Tiempo", "Jornada Nocturna", "Jornada Rotativa"
		}));
		cmbJornada.setMaximumRowCount(11);
		cmbJornada.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbJornada.setBounds(111, 72, 305, 29);
		pnlCondiciones.add(cmbJornada);

		lblIcoJornada = new JLabel("");
		lblIcoJornada.setBounds(433, 69, 32, 32);
		pnlCondiciones.add(lblIcoJornada);

		JLabel label_2 = new JLabel("Porcentaje Mínimo:");
		label_2.setFont(new Font("Segoe UI", Font.BOLD, 16));
		label_2.setBounds(12, 126, 164, 29);
		pnlCondiciones.add(label_2);

		spnPorcentaje = new JSpinner();
		spnPorcentaje.setModel(new SpinnerNumberModel(0, 0, 100, 10));
		spnPorcentaje.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		spnPorcentaje.setBounds(180, 130, 236, 22);
		pnlCondiciones.add(spnPorcentaje);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(12, 174, 503, 6);
		pnlCondiciones.add(separator_1);

		JLabel lblesObligatorioSer = new JLabel("¿Es obligatorio ser mayor de edad?");
		lblesObligatorioSer.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblesObligatorioSer.setBounds(12, 189, 269, 29);
		pnlCondiciones.add(lblesObligatorioSer);

		chkbxMayor = new JCheckBox("");
		chkbxMayor.setBackground(new Color(228, 228, 228));
		chkbxMayor.setBounds(289, 193, 39, 25);
		pnlCondiciones.add(chkbxMayor);

		JLabel lblestaraDispuestoA = new JLabel("¿Ofrece reubicación?");
		lblestaraDispuestoA.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblestaraDispuestoA.setBounds(12, 227, 175, 29);
		pnlCondiciones.add(lblestaraDispuestoA);

		chkReubicacion = new JCheckBox("");
		chkReubicacion.setBackground(new Color(228, 228, 228));
		chkReubicacion.setBounds(182, 231, 39, 25);
		pnlCondiciones.add(chkReubicacion);

		JLabel lblrequiereLicenciaDe = new JLabel("¿Requiere licencia de conducir?");
		lblrequiereLicenciaDe.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblrequiereLicenciaDe.setBounds(12, 272, 251, 29);
		pnlCondiciones.add(lblrequiereLicenciaDe);

		chkLicencia = new JCheckBox("");
		chkLicencia.setBackground(new Color(228, 228, 228));
		chkLicencia.setBounds(274, 276, 39, 25);
		pnlCondiciones.add(chkLicencia);

		cmbJornada.setSelectedIndex(0);
		cmbModalidad.setSelectedIndex(0);

		// ===== Botones =====
		JPanel pnlInferior = new JPanel();
		pnlInferior.setBackground(new Color(24, 61, 61));
		pnlInferior.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(pnlInferior, BorderLayout.SOUTH);

		JButton okButton = new JButton("Confirmar");
		okButton.setBackground(Color.WHITE);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (verificar()) {
						guardarOferta();
					}
				} catch (FormatException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		okButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
		if (ofertaAct == null) {
			okButton.setText("Registrar");
			okButton.setIcon(new ImageIcon("recursos/agregarP.png"));
		} else {
			okButton.setText("Modificar");
			okButton.setIcon(new ImageIcon("recursos/modificar.png"));
		}

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBackground(Color.WHITE);
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
			}
		});
		btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 16));
		pnlInferior.add(btnLimpiar);
		pnlInferior.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setBackground(Color.WHITE);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
		cancelButton.setIcon(new ImageIcon("recursos/cerrar.png"));
		pnlInferior.add(cancelButton);

		cmbArea.setSelectedIndex(0);
		cambiarEspecializacion("Estudiante Universitario");
		cargarDatos();
	}

	public RegistroOfertaLaboral(OfertaLaboral oferta) {
		this(null, oferta);
	}

	private void guardarOferta() {
		ArrayList<String> idiomas = new ArrayList<>();
		for (JCheckBox cb : checkIdiomas) {
			if (cb.isSelected()) idiomas.add(cb.getText());
		}

		String nivelAcademico;
		Integer idCarrera = null;
		Integer idAreaTecnica = null;
		Integer idHabilidad = null;
		int expMin = 0;

		if (rdUniversitario.isSelected()) {
			nivelAcademico = rdUniversitario.getText();
			idCarrera = ((Carrera) cmbCarrera.getSelectedItem()).getIdCarrera();
		} else if (rdTecnico.isSelected()) {
			nivelAcademico = rdTecnico.getText();
			idAreaTecnica = ((AreaTecnica) cmbAreaTecnica.getSelectedItem()).getIdAreaTecnica();
			expMin = ((Number) spnAniosExp.getValue()).intValue();
		} else {
			nivelAcademico = rdObrero.getText();
			idHabilidad = ((Habilidad) cmbHabilidad.getSelectedItem()).getIdHabilidad();
		}

		if (ofertaAct != null) {
			ofertaAct.setPuesto(txtPuesto.getText());
			ofertaAct.setDescripcion(txtDescripcion.getText());
			ofertaAct.setSalario(((Number) spnSalario.getValue()).floatValue());
			ofertaAct.setVacantes(((Number) spnVacantes.getValue()).intValue());
			ofertaAct.setOfertador(BolsaLaboral.getInstancia().getCentros().get(cmbOfertador.getSelectedIndex() - 1));
			ofertaAct.setArea((String) cmbArea.getSelectedItem());
			ofertaAct.setModalidad((String) cmbModalidad.getSelectedItem());
			ofertaAct.setJornada((String) cmbJornada.getSelectedItem());
			ofertaAct.setObligatorioMayorDeEdad(chkbxMayor.isSelected());
			ofertaAct.setOfreceReubicacion(chkReubicacion.isSelected());
			ofertaAct.setObligatorioLicencia(chkLicencia.isSelected());
			ofertaAct.setPorcentajeMinimo(((Integer) spnPorcentaje.getValue()).intValue());
			ofertaAct.setIdiomasRequeridas(idiomas);
			ofertaAct.setNivelAcademico(nivelAcademico);
			ofertaAct.setExperienciaMinima(expMin);
			ofertaAct.setIdCarrera(idCarrera);
			ofertaAct.setIdAreaTecnica(idAreaTecnica);
			ofertaAct.setIdHabilidad(idHabilidad);

			if (BolsaLaboral.getInstancia().modificarOfertaLaboral(ofertaAct)) {
				JOptionPane.showMessageDialog(null, "La oferta: " + txtPuesto.getText()
						+ " ha sido modificada exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
				ConsultarOfertas.cargarOfertas();
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "La oferta " + txtPuesto.getText() + " no logró ser modificada.");
			}
		} else {
			OfertaLaboral nuevaOferta = new OfertaLaboral(
					null,
					txtPuesto.getText(),
					txtDescripcion.getText(),
					cmbArea.getSelectedItem().toString(),
					cmbModalidad.getSelectedItem().toString(),
					cmbJornada.getSelectedItem().toString(),
					"Activa",
					((Number) spnSalario.getValue()).floatValue(),
					expMin,
					((Number) spnVacantes.getValue()).intValue(),
					BolsaLaboral.getInstancia().getCentros().get(cmbOfertador.getSelectedIndex() - 1),
					chkReubicacion.isSelected(),
					chkbxMayor.isSelected(),
					chkLicencia.isSelected(),
					nivelAcademico,
					idCarrera,
					idAreaTecnica,
					idHabilidad,
					idiomas,
					((Integer) spnPorcentaje.getValue()).intValue()
			);
			BolsaLaboral.getInstancia().registrarOfertaLaboral(nuevaOferta);
			JOptionPane.showMessageDialog(null, "La oferta laboral ha sido agregada correctamente.",
					"Información", JOptionPane.INFORMATION_MESSAGE);
			txtCodigo.setText(nuevaOferta.getCodigo());  // muestra el código real
			limpiar();
		}
	}

	private void cargarJornada() {
		String nombreJornada = cmbJornada.getSelectedItem().toString().toLowerCase().replace(" ", "");
		lblIcoJornada.setIcon(new ImageIcon("recursos/" + nombreJornada + ".png"));
	}

	private void cargarModalidad() {
		String nombreModalidad = cmbModalidad.getSelectedItem().toString().toLowerCase().replace("í", "i");
		lblIcoModalidad.setIcon(new ImageIcon("recursos/" + nombreModalidad + ".png"));
	}

	private void cargarArea() {
		String nombreArea = cmbArea.getSelectedItem().toString().toLowerCase().replace("ó", "o").replace(" ", "");
		lblIcoArea.setIcon(new ImageIcon("recursos/" + nombreArea + ".png"));
	}

	private void cambiarEspecializacion(String especializacion) {
		if (especializacion.equalsIgnoreCase("Obrero")) {
			pnlCarreras.setVisible(false);
			pnlTecnico.setVisible(false);
			pnlObrero.setVisible(true);
		} else if (especializacion.equalsIgnoreCase("Estudiante Universitario")) {
			pnlObrero.setVisible(false);
			pnlTecnico.setVisible(false);
			pnlCarreras.setVisible(true);
		} else if (especializacion.equalsIgnoreCase("Estudiante Técnico")) {
			pnlCarreras.setVisible(false);
			pnlObrero.setVisible(false);
			pnlTecnico.setVisible(true);
		}
	}

	private boolean idiomaSeleccionado() {
		for (JCheckBox cb : checkIdiomas) {
			if (cb.isSelected()) return true;
		}
		return false;
	}

	private void cargarDatos() {
		checkIdiomas = new JCheckBox[] {
				chckbxIngls, chckbxPortugus, chckbxItaliano,
				chckbxAlemn, chckbxMandarn, chckbxCoreano,
				chckbxEspaol, chckbxFrancs, chckbxJapons
		};

		if (ofertaAct == null) return;

		txtCodigo.setText(ofertaAct.getCodigo());
		txtPuesto.setText(ofertaAct.getPuesto());
		cmbOfertador.setSelectedIndex(
				BolsaLaboral.getInstancia().buscarIndiceCentroByCodigo(ofertaAct.getOfertador().getCodigo()) + 1);
		spnSalario.setValue(ofertaAct.getSalario());
		txtDescripcion.setText(ofertaAct.getDescripcion());
		spnVacantes.setValue(ofertaAct.getVacantes());
		spnPorcentaje.setValue(ofertaAct.getPorcentajeMinimo());
		cmbArea.setSelectedItem(ofertaAct.getArea());
		cmbModalidad.setSelectedItem(ofertaAct.getModalidad());
		cmbJornada.setSelectedItem(ofertaAct.getJornada());
		spnAniosExp.setValue(ofertaAct.getExperienciaMinima());
		chkbxMayor.setSelected(ofertaAct.isObligatorioMayorDeEdad());
		chkReubicacion.setSelected(ofertaAct.isOfreceReubicacion());
		chkLicencia.setSelected(ofertaAct.isObligatorioLicencia());

		for (JCheckBox cbIdioma : checkIdiomas) {
			if (ofertaAct.getIdiomasRequeridas().contains(cbIdioma.getText())) {
				cbIdioma.setSelected(true);
			}
		}

		if (ofertaAct.getNivelAcademico().equals(rdUniversitario.getText())) {
			rdUniversitario.setSelected(true);
			cambiarEspecializacion("Estudiante Universitario");
			for (int i = 0; i < cmbCarrera.getItemCount(); i++) {
				if (ofertaAct.getIdCarrera() != null
						&& cmbCarrera.getItemAt(i).getIdCarrera() == ofertaAct.getIdCarrera()) {
					cmbCarrera.setSelectedIndex(i);
					break;
				}
			}
		} else if (ofertaAct.getNivelAcademico().equals(rdTecnico.getText())) {
			rdTecnico.setSelected(true);
			cambiarEspecializacion("Estudiante Técnico");
			for (int i = 0; i < cmbAreaTecnica.getItemCount(); i++) {
				if (ofertaAct.getIdAreaTecnica() != null
						&& cmbAreaTecnica.getItemAt(i).getIdAreaTecnica() == ofertaAct.getIdAreaTecnica()) {
					cmbAreaTecnica.setSelectedIndex(i);
					break;
				}
			}
		} else if (ofertaAct.getNivelAcademico().equals(rdObrero.getText())) {
			rdObrero.setSelected(true);
			cambiarEspecializacion("Obrero");
			for (int i = 0; i < cmbHabilidad.getItemCount(); i++) {
				if (ofertaAct.getIdHabilidad() != null
						&& cmbHabilidad.getItemAt(i).getIdHabilidad() == ofertaAct.getIdHabilidad()) {
					cmbHabilidad.setSelectedIndex(i);
					break;
				}
			}
		}
	}

	private void limpiar() {
		txtPuesto.setText("");
		cmbOfertador.setSelectedIndex(0);
		spnSalario.setValue(12000f);
		txtDescripcion.setText("");
		spnVacantes.setValue(1);
		cmbArea.setSelectedIndex(0);
		cmbModalidad.setSelectedIndex(0);
		cmbJornada.setSelectedIndex(0);
		if (cmbCarrera.getItemCount() > 0) cmbCarrera.setSelectedIndex(0);
		if (cmbAreaTecnica.getItemCount() > 0) cmbAreaTecnica.setSelectedIndex(0);
		if (cmbHabilidad.getItemCount() > 0) cmbHabilidad.setSelectedIndex(0);
		spnAniosExp.setValue(0);
		spnPorcentaje.setValue(0);

		for (JCheckBox cb : checkIdiomas) {
			cb.setSelected(false);
		}
		chkbxMayor.setSelected(false);
		chkReubicacion.setSelected(false);
		chkLicencia.setSelected(false);

		rdUniversitario.setSelected(true);
		cambiarEspecializacion("Estudiante Universitario");
	}

	private boolean verificar() throws FormatException {
		if (cmbOfertador.getSelectedIndex() == 0) {
			throw new FormatException("Debe seleccionar un centro empleador válido.");
		}
		if (txtPuesto.getText().trim().isEmpty()) {
			throw new FormatException("Debe ingresar un título para el puesto.");
		}
		if (txtDescripcion.getText().trim().isEmpty()) {
			throw new FormatException("Debe ingresar una descripción para la oferta.");
		}
		if (!idiomaSeleccionado()) {
			throw new FormatException("Debe seleccionar por lo menos un idioma requerido.");
		}
		if (cmbArea.getSelectedIndex() == 0) {
			throw new FormatException("Debe seleccionar un área.");
		}
		if (rdUniversitario.isSelected() && cmbCarrera.getSelectedItem() == null) {
			throw new FormatException("Debe seleccionar una carrera.");
		}
		if (rdTecnico.isSelected() && cmbAreaTecnica.getSelectedItem() == null) {
			throw new FormatException("Debe seleccionar un área técnica.");
		}
		if (rdObrero.isSelected() && cmbHabilidad.getSelectedItem() == null) {
			throw new FormatException("Debe seleccionar una habilidad.");
		}
		return true;
	}
}