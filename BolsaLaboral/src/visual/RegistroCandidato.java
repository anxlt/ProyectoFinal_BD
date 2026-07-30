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
import java.awt.Component;

import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import db.AreaTecnicaDAO;
import db.AreaTecnicaDAOImpl;
import logico.AreaTecnica;

public class RegistroCandidato extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTabbedPane contenedor;
	private Candidato candidatoAct = null;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtCedula;
	private JTextField txtCorreo;
	private JComboBox<Provincia> cmbProvincia;
	private JComboBox<Municipio> cmbMunicipio;
	private ProvinciaDAO provinciaDAO = new ProvinciaDAOImpl();
	private MunicipioDAO municipioDAO = new MunicipioDAOImpl();
	private JRadioButton rdTecnico;
	private JRadioButton rdUniversitario;
	private JRadioButton rdObrero;
	private JSpinner spnFechaNac;
	private JTextField txtTelefono;
	private JComboBox<Universidad> cmbUniversidad;
	private JComboBox<AreaTecnica> cmbAreaTecnica;
	private UniversidadDAO universidadDAO = new UniversidadDAOImpl();
	private CarreraDAO carreraDAO = new CarreraDAOImpl();
	private AreaTecnicaDAO areaTecnicaDAO = new AreaTecnicaDAOImpl();
	private JPanel pnlTipoCand;
	private JPanel pnlEstudiante;
	private JPanel pnlTecnico;
	private JPanel pnlObrero;
	private JLabel lblIcoModalidad;
	private JLabel lblIcoJornada;
	private JComboBox cmbJornada;
	private JComboBox cmbModalidad;
	private JLabel lblIcoArea;
	private JComboBox cmbArea;


	private JPanel pnlIdiomas;
	private JSpinner spnSalarioEsperado;
	private JSpinner spnAniosExp;
	private JComboBox<Carrera> cmbCarrera;
	private JComboBox cmbNivel;
	private JCheckBox chkLicenciaConducir;
	private JCheckBox chkMudarse;

	private JCheckBox chckbxIngles;
	private JCheckBox chckbxItaliano;
	private JCheckBox chckbxEspanol;
	private JCheckBox chckbxFrances;
	private JCheckBox chckbxPortugues;
	private JCheckBox chckbxAleman;
	private JCheckBox chckbxCoreano;
	private JCheckBox chckbxJapones;
	private JCheckBox chckbxMandarin;

	private JCheckBox chkPlomeria;
	private JCheckBox chkCarpintero;
	private JCheckBox chkCajero;
	private JCheckBox chkSoldadura;
	private JCheckBox chkElectrica;
	private JCheckBox chkMecanica;
	private JCheckBox chkAlbanileria;
	private JCheckBox chkRedes;
	private JCheckBox chkConduccion;
	private JCheckBox chkReparacion;
	private JCheckBox chkVentas;
	private JCheckBox chkFotografia;
	private JCheckBox chkCocina;
	private JCheckBox chkLimpieza;
	private JCheckBox chkPintura;
	private JComboBox cmbGenero;
	private JComboBox cmbEstadoLab;
	/**
	 * Create the dialog.
	 */
	public RegistroCandidato(Candidato cand) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("recursos/icono.png"));
		setResizable(false);
		if(cand == null) {
			setTitle("Registrar Candidato");
		}
		else {
			setTitle("Modificar Candidato");
			candidatoAct = cand;
		}
		setBounds(100, 100, 570, 681);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(228, 228, 228));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		contenedor = new JTabbedPane(JTabbedPane.TOP);
		contenedor.setBackground(new Color(153, 204, 204));
		contenedor.setForeground(Color.BLACK);
		contenedor.setFont(new Font("Segoe UI", Font.BOLD, 18));
		contentPanel.add(contenedor, BorderLayout.CENTER);

		JPanel pnlPersonal = new JPanel();
		contenedor.addTab("Personal", null, pnlPersonal, null);
		pnlPersonal.setBackground(new Color(228, 228, 228));
		pnlPersonal.setLayout(null);

		JLabel label = new JLabel("C\u00F3digo:");
		label.setFont(new Font("Segoe UI", Font.BOLD, 16));
		label.setBounds(12, 9, 84, 29);
		pnlPersonal.add(label);

		txtCodigo = new JTextField();
		txtCodigo.setText("CAN-" + BolsaLaboral.genCodigoCandidato);
		txtCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtCodigo.setEditable(false);
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(106, 13, 305, 22);
		pnlPersonal.add(txtCodigo);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(12, 55, 503, 6);
		pnlPersonal.add(separator);

		JLabel lblNombres = new JLabel("Nombres:");
		lblNombres.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNombres.setBounds(12, 69, 84, 29);
		pnlPersonal.add(lblNombres);

		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtNombre.setColumns(10);
		txtNombre.setBounds(106, 71, 305, 22);
		pnlPersonal.add(txtNombre);

		JLabel lblApellido = new JLabel("Apellidos:");
		lblApellido.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblApellido.setBounds(12, 121, 84, 29);
		pnlPersonal.add(lblApellido);

		txtApellido = new JTextField();
		txtApellido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtApellido.setColumns(10);
		txtApellido.setBounds(106, 123, 305, 22);
		pnlPersonal.add(txtApellido);

		JLabel lblCdula = new JLabel("C\u00E9dula:");
		lblCdula.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblCdula.setBounds(12, 175, 84, 29);
		pnlPersonal.add(lblCdula);

		txtCedula = new JTextField();
		txtCedula.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtCedula.setColumns(10);
		txtCedula.setBounds(106, 177, 305, 22);
		pnlPersonal.add(txtCedula);

		JLabel lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento:");
		lblFechaDeNacimiento.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblFechaDeNacimiento.setBounds(12, 217, 173, 29);
		pnlPersonal.add(lblFechaDeNacimiento);

		spnFechaNac = new JSpinner();
		spnFechaNac.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		spnFechaNac.setBounds(201, 222, 210, 22);

		Calendar cal = Calendar.getInstance();
		Date fechaActual = cal.getTime();
		Date fechaMaxima = fechaActual;
		cal.add(Calendar.YEAR, -100);
		Date fechaMinima = cal.getTime();
		cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -16);
		Date fechaPorDefecto = cal.getTime();
		SpinnerDateModel modeloFecha = new SpinnerDateModel(fechaPorDefecto, fechaMinima, fechaMaxima, Calendar.DAY_OF_MONTH);
		spnFechaNac.setModel(modeloFecha);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spnFechaNac, "dd/MM/yyyy");
		spnFechaNac.setEditor(editor);
		pnlPersonal.add(spnFechaNac);

		JPanel pnlContactos = new JPanel();
		pnlContactos.setLayout(null);
		pnlContactos.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Contactos y Ubicaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlContactos.setBackground(new Color(228, 228, 228));
		pnlContactos.setBounds(12, 311, 513, 229);
		pnlPersonal.add(pnlContactos);

		JLabel label_1 = new JLabel("Tel\u00E9fono:");
		label_1.setFont(new Font("Segoe UI", Font.BOLD, 16));
		label_1.setBounds(12, 28, 84, 29);
		pnlContactos.add(label_1);

		txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(108, 35, 305, 22);
		pnlContactos.add(txtTelefono);

		JLabel label_2 = new JLabel("Correo:");
		label_2.setFont(new Font("Segoe UI", Font.BOLD, 16));
		label_2.setBounds(12, 78, 84, 29);
		pnlContactos.add(label_2);

		txtCorreo = new JTextField();
		txtCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(108, 81, 305, 22);
		pnlContactos.add(txtCorreo);

		cmbProvincia = new JComboBox<>();
		for (Provincia p : provinciaDAO.listarTodas()) {
			cmbProvincia.addItem(p);
		}
		cmbProvincia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbProvincia.setBounds(108, 133, 305, 22);
		pnlContactos.add(cmbProvincia);

		JLabel label_3 = new JLabel("Provincia:");
		label_3.setFont(new Font("Segoe UI", Font.BOLD, 16));
		label_3.setBounds(12, 126, 84, 29);
		pnlContactos.add(label_3);

		JLabel label_4 = new JLabel("Municipio:");
		label_4.setFont(new Font("Segoe UI", Font.BOLD, 16));
		label_4.setBounds(12, 179, 84, 29);
		pnlContactos.add(label_4);

		cmbMunicipio = new JComboBox<>();
		cmbMunicipio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbMunicipio.setBounds(108, 182, 305, 22);
		pnlContactos.add(cmbMunicipio);

		cmbProvincia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarMunicipios();
			}
		});
		cargarMunicipios();

		cmbGenero = new JComboBox();
		cmbGenero.setModel(new DefaultComboBoxModel(new String[] {"Femenino", "Masculino"}));
		cmbGenero.setSelectedIndex(0);
		cmbGenero.setMaximumRowCount(11);
		cmbGenero.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbGenero.setBounds(106, 269, 305, 29);
		pnlPersonal.add(cmbGenero);

		JLabel lblGnero = new JLabel("G\u00E9nero:");
		lblGnero.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblGnero.setBounds(12, 269, 74, 29);
		pnlPersonal.add(lblGnero);

		JPanel pnlEspecializacion = new JPanel();
		contenedor.addTab("Especializaciones", null, pnlEspecializacion, null);
		pnlEspecializacion.setLayout(null);
		pnlEspecializacion.setBackground(new Color(228, 228, 228));

		pnlTecnico = new JPanel();
		pnlTecnico.setBounds(12, 97, 510, 210);
		pnlEspecializacion.add(pnlTecnico);
		pnlTecnico.setLayout(null);
		pnlTecnico.setBorder(new TitledBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Estudiante T\u00E9cnico", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlTecnico.setBackground(new Color(228, 228, 228));

		JLabel lblreaTcnica = new JLabel("\u00C1rea T\u00E9cnica:");
		lblreaTcnica.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblreaTcnica.setBounds(12, 25, 106, 29);
		pnlTecnico.add(lblreaTcnica);

		JLabel lblAosDeExperiencia = new JLabel("A\u00F1os de Experiencia:");
		lblAosDeExperiencia.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblAosDeExperiencia.setBounds(12, 82, 168, 29);
		pnlTecnico.add(lblAosDeExperiencia);

		spnAniosExp = new JSpinner();
		spnAniosExp.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spnAniosExp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		spnAniosExp.setBounds(177, 86, 258, 22);
		pnlTecnico.add(spnAniosExp);

		cmbAreaTecnica = new JComboBox<>();
		for (AreaTecnica a : areaTecnicaDAO.listarTodas()) {
			cmbAreaTecnica.addItem(a);
		}
		if (cmbAreaTecnica.getItemCount() > 0) {
			cmbAreaTecnica.setSelectedIndex(0);
		}
		cmbAreaTecnica.setMaximumRowCount(11);
		cmbAreaTecnica.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbAreaTecnica.setBounds(130, 25, 305, 29);
		pnlTecnico.add(cmbAreaTecnica);

		pnlObrero = new JPanel();
		pnlObrero.setLayout(null);
		pnlObrero.setBorder(new TitledBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Habilidades del Obrero", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlObrero.setBackground(new Color(228, 228, 228));
		pnlObrero.setBounds(12, 97, 510, 210);
		pnlEspecializacion.add(pnlObrero);

		chkPlomeria = new JCheckBox("Plomer\u00EDa");
		chkPlomeria.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkPlomeria.setBackground(new Color(228, 228, 228));
		chkPlomeria.setBounds(8, 24, 113, 25);
		pnlObrero.add(chkPlomeria);

		chkCarpintero = new JCheckBox("Carpinter\u00EDa");
		chkCarpintero.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkCarpintero.setBackground(new Color(228, 228, 228));
		chkCarpintero.setBounds(8, 61, 113, 25);
		pnlObrero.add(chkCarpintero);

		chkCajero = new JCheckBox("Gesti\u00F3n Financiera");
		chkCajero.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkCajero.setBackground(new Color(228, 228, 228));
		chkCajero.setBounds(8, 101, 147, 25);
		pnlObrero.add(chkCajero);

		chkSoldadura = new JCheckBox("Soldadura");
		chkSoldadura.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkSoldadura.setBackground(new Color(228, 228, 228));
		chkSoldadura.setBounds(8, 143, 113, 25);
		pnlObrero.add(chkSoldadura);

		chkElectrica = new JCheckBox("Instalaci\u00F3n El\u00E9ctrica");
		chkElectrica.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkElectrica.setBackground(new Color(228, 228, 228));
		chkElectrica.setBounds(8, 181, 147, 25);
		pnlObrero.add(chkElectrica);

		chkMecanica = new JCheckBox("Mec\u00E1nica");
		chkMecanica.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkMecanica.setBackground(new Color(228, 228, 228));
		chkMecanica.setBounds(181, 25, 113, 25);
		pnlObrero.add(chkMecanica);

		chkAlbanileria = new JCheckBox("Alba\u00F1iler\u00EDa");
		chkAlbanileria.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkAlbanileria.setBackground(new Color(228, 228, 228));
		chkAlbanileria.setBounds(181, 62, 113, 25);
		pnlObrero.add(chkAlbanileria);

		chkRedes = new JCheckBox("Redes Sociales");
		chkRedes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkRedes.setBackground(new Color(228, 228, 228));
		chkRedes.setBounds(181, 101, 135, 25);
		pnlObrero.add(chkRedes);

		chkConduccion = new JCheckBox("Conducci\u00F3n");
		chkConduccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkConduccion.setBackground(new Color(228, 228, 228));
		chkConduccion.setBounds(181, 143, 113, 25);
		pnlObrero.add(chkConduccion);

		chkReparacion = new JCheckBox("Reparaci\u00F3n de Electr\u00F3nicos");
		chkReparacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkReparacion.setBackground(new Color(228, 228, 228));
		chkReparacion.setBounds(181, 182, 199, 25);
		pnlObrero.add(chkReparacion);

		chkVentas = new JCheckBox("Ventas");
		chkVentas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkVentas.setBackground(new Color(228, 228, 228));
		chkVentas.setBounds(389, 25, 113, 25);
		pnlObrero.add(chkVentas);

		chkFotografia = new JCheckBox("Fotograf\u00EDa");
		chkFotografia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkFotografia.setBackground(new Color(228, 228, 228));
		chkFotografia.setBounds(389, 62, 113, 25);
		pnlObrero.add(chkFotografia);

		chkCocina = new JCheckBox("Cocina");
		chkCocina.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkCocina.setBackground(new Color(228, 228, 228));
		chkCocina.setBounds(389, 102, 113, 25);
		pnlObrero.add(chkCocina);

		chkLimpieza = new JCheckBox("Limpieza");
		chkLimpieza.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkLimpieza.setBackground(new Color(228, 228, 228));
		chkLimpieza.setBounds(389, 144, 113, 25);
		pnlObrero.add(chkLimpieza);

		chkPintura = new JCheckBox("Pintura");
		chkPintura.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkPintura.setBackground(new Color(228, 228, 228));
		chkPintura.setBounds(389, 182, 113, 25);
		pnlObrero.add(chkPintura);

		pnlTipoCand = new JPanel();
		pnlTipoCand.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Tipo de Candidato", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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

		rdTecnico = new JRadioButton("Estudiante T\u00E9cnico");
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

		pnlEstudiante = new JPanel();
		pnlEstudiante.setBorder(new TitledBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Estudiante Universitario", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlEstudiante.setBounds(12, 97, 510, 210);
		pnlEstudiante.setBackground(new Color(228, 228, 228));
		pnlEspecializacion.add(pnlEstudiante);
		pnlEstudiante.setLayout(null);

		JLabel lblCarrera = new JLabel("Carrera:");
		lblCarrera.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblCarrera.setBounds(12, 25, 84, 29);
		pnlEstudiante.add(lblCarrera);

		JLabel lblUniversidad = new JLabel("Universidad:");
		lblUniversidad.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblUniversidad.setBounds(12, 82, 106, 29);
		pnlEstudiante.add(lblUniversidad);

		cmbUniversidad = new JComboBox<>();
		for (Universidad u : universidadDAO.listarTodas()) {
			cmbUniversidad.addItem(u);
		}
		cmbUniversidad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbUniversidad.setBounds(116, 86, 305, 22);
		pnlEstudiante.add(cmbUniversidad);

		JLabel lblNivelAcadmico = new JLabel("Nivel Acad\u00E9mico:");
		lblNivelAcadmico.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNivelAcadmico.setBounds(12, 144, 146, 29);
		pnlEstudiante.add(lblNivelAcadmico);

		cmbNivel = new JComboBox();
		cmbNivel.setModel(new DefaultComboBoxModel(new String[] {"Grado", "Postgrado", "Doctorado"}));
		cmbNivel.setSelectedIndex(0);
		cmbNivel.setMaximumRowCount(11);
		cmbNivel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbNivel.setBounds(158, 145, 263, 29);
		pnlEstudiante.add(cmbNivel);

		cmbCarrera = new JComboBox<>();
		for (Carrera c : carreraDAO.listarTodas()) {
			cmbCarrera.addItem(c);
		}
		cmbCarrera.setMaximumRowCount(11);
		cmbCarrera.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbCarrera.setBounds(116, 30, 305, 29);
		pnlEstudiante.add(cmbCarrera);

		pnlIdiomas = new JPanel();
		pnlIdiomas.setLayout(null);
		pnlIdiomas.setBorder(new TitledBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Idiomas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlIdiomas.setBackground(new Color(228, 228, 228));
		pnlIdiomas.setBounds(12, 317, 510, 145);
		pnlEspecializacion.add(pnlIdiomas);

		chckbxIngles = new JCheckBox("Ingl\u00E9s");
		chckbxIngles.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxIngles.setBackground(new Color(228, 228, 228));
		chckbxIngles.setBounds(8, 24, 113, 25);
		pnlIdiomas.add(chckbxIngles);

		chckbxItaliano = new JCheckBox("Italiano");
		chckbxItaliano.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxItaliano.setBackground(new Color(228, 228, 228));
		chckbxItaliano.setBounds(8, 61, 113, 25);
		pnlIdiomas.add(chckbxItaliano);

		chckbxEspanol = new JCheckBox("Espa\u00F1ol");
		chckbxEspanol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxEspanol.setBackground(new Color(228, 228, 228));
		chckbxEspanol.setBounds(8, 101, 113, 25);
		pnlIdiomas.add(chckbxEspanol);

		chckbxFrances = new JCheckBox("Franc\u00E9s");
		chckbxFrances.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxFrances.setBackground(new Color(228, 228, 228));
		chckbxFrances.setBounds(181, 101, 113, 25);
		pnlIdiomas.add(chckbxFrances);

		chckbxPortugues = new JCheckBox("Portugu\u00E9s");
		chckbxPortugues.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxPortugues.setBackground(new Color(228, 228, 228));
		chckbxPortugues.setBounds(181, 25, 113, 25);
		pnlIdiomas.add(chckbxPortugues);

		chckbxAleman = new JCheckBox("Alem\u00E1n");
		chckbxAleman.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxAleman.setBackground(new Color(228, 228, 228));
		chckbxAleman.setBounds(181, 62, 113, 25);
		pnlIdiomas.add(chckbxAleman);

		chckbxMandarin = new JCheckBox("Chino");
		chckbxMandarin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxMandarin.setBackground(new Color(228, 228, 228));
		chckbxMandarin.setBounds(389, 25, 113, 25);
		pnlIdiomas.add(chckbxMandarin);

		chckbxCoreano = new JCheckBox("Coreano");
		chckbxCoreano.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxCoreano.setBackground(new Color(228, 228, 228));
		chckbxCoreano.setBounds(389, 62, 113, 25);
		pnlIdiomas.add(chckbxCoreano);

		chckbxJapones = new JCheckBox("Japon\u00E9s");
		chckbxJapones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chckbxJapones.setBackground(new Color(228, 228, 228));
		chckbxJapones.setBounds(389, 101, 113, 25);
		pnlIdiomas.add(chckbxJapones);

		JLabel lbltieneLicenciaDe = new JLabel("\u00BFTiene licencia de conducir?");
		lbltieneLicenciaDe.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lbltieneLicenciaDe.setBounds(12, 475, 219, 29);
		pnlEspecializacion.add(lbltieneLicenciaDe);

		chkLicenciaConducir = new JCheckBox("");
		chkLicenciaConducir.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkLicenciaConducir.setBackground(new Color(228, 228, 228));
		chkLicenciaConducir.setBounds(239, 478, 39, 25);
		pnlEspecializacion.add(chkLicenciaConducir);

		JPanel pnlPreferencias = new JPanel();
		pnlPreferencias.setBackground(new Color(228, 228, 228));
		contenedor.addTab("Preferencias", null, pnlPreferencias, null);
		pnlPreferencias.setLayout(null);

		JLabel lblModalidad = new JLabel("Modalidad:");
		lblModalidad.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblModalidad.setBounds(12, 17, 106, 29);
		pnlPreferencias.add(lblModalidad);

		cmbModalidad = new JComboBox();
		cmbModalidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarModalidad();
			}
		});
		cmbModalidad.setModel(new DefaultComboBoxModel(new String[] {"Presencial", "Remoto", "H\u00EDbrido"}));
		cmbModalidad.setMaximumRowCount(11);
		cmbModalidad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbModalidad.setBounds(111, 18, 305, 29);
		pnlPreferencias.add(cmbModalidad);

		lblIcoModalidad = new JLabel("");
		lblIcoModalidad.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblIcoModalidad.setBounds(433, 17, 32, 32);
		pnlPreferencias.add(lblIcoModalidad);

		JLabel lblJornada = new JLabel("Jornada:");
		lblJornada.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblJornada.setBounds(12, 71, 106, 29);
		pnlPreferencias.add(lblJornada);

		cmbJornada = new JComboBox();
		cmbJornada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarJornada();
			}
		});
		cmbJornada.setModel(new DefaultComboBoxModel(new String[] {"Tiempo Completo", "Medio Tiempo", "Jornada Nocturna", "Jornada Rotativa"}));
		cmbJornada.setMaximumRowCount(11);
		cmbJornada.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbJornada.setBounds(111, 72, 305, 29);
		pnlPreferencias.add(cmbJornada);

		lblIcoJornada = new JLabel("");
		lblIcoJornada.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblIcoJornada.setBounds(433, 69, 32, 32);
		pnlPreferencias.add(lblIcoJornada);

		JLabel lblrea = new JLabel("\u00C1rea:");
		lblrea.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblrea.setBounds(12, 127, 106, 29);
		pnlPreferencias.add(lblrea);

		cmbArea = new JComboBox();
		cmbArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarArea();
			}
		});
		cmbArea.setModel(new DefaultComboBoxModel(new String[] {"No definido", "Finanzas", "Recursos Humanos", "Marketing", "Limpieza", "Seguridad", "TI", "Salud", "Operaciones", "Administraci\u00F3n", "Atenci\u00F3n al Cliente", "Educaci\u00F3n"}));
		cmbArea.setMaximumRowCount(12);
		cmbArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbArea.setBounds(111, 128, 305, 29);
		pnlPreferencias.add(cmbArea);

		lblIcoArea = new JLabel("");
		lblIcoArea.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblIcoArea.setBounds(428, 127, 32, 32);
		pnlPreferencias.add(lblIcoArea);

		JLabel lblSalarioEsperado = new JLabel("Salario Esperado:");
		lblSalarioEsperado.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblSalarioEsperado.setBounds(12, 195, 144, 29);
		pnlPreferencias.add(lblSalarioEsperado);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(12, 176, 503, 6);
		pnlPreferencias.add(separator_1);

		JLabel lblestaraDispuestoA = new JLabel("\u00BFEstar\u00EDa dispuesto a mudarse si es requerido?");
		lblestaraDispuestoA.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblestaraDispuestoA.setBounds(12, 248, 359, 29);
		pnlPreferencias.add(lblestaraDispuestoA);
		{
			JPanel pnlInferior = new JPanel();
			pnlInferior.setBackground(new Color(92, 131, 116));
			pnlInferior.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(pnlInferior, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Confimar");
				okButton.setBackground(Color.WHITE);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if(verificar()) {
								registrarCandidato();
							}
						} catch (FormatException ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				okButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
				if(candidatoAct == null) {
					okButton.setText("Registrar");
					okButton.setIcon(new ImageIcon("recursos/agregarP.png"));

				}
				else {
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
				btnLimpiar.setActionCommand("OK");
				pnlInferior.add(btnLimpiar);
				okButton.setActionCommand("OK");
				pnlInferior.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setBackground(Color.WHITE);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
				cancelButton.setIcon(new ImageIcon("recursos/cerrar.png"));
				cancelButton.setActionCommand("Cancel");
				pnlInferior.add(cancelButton);
			}
		}
		cmbJornada.setSelectedIndex(0);
		cmbArea.setSelectedIndex(0);
		cmbModalidad.setSelectedIndex(0);

		spnSalarioEsperado = new JSpinner();
		spnSalarioEsperado.setModel(new SpinnerNumberModel(new Float(12000), new Float(12000), null, new Float(1000)));
		spnSalarioEsperado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		spnSalarioEsperado.setBounds(154, 200, 262, 22);
		pnlPreferencias.add(spnSalarioEsperado);

		chkMudarse = new JCheckBox("");
		chkMudarse.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		chkMudarse.setBackground(new Color(228, 228, 228));
		chkMudarse.setBounds(359, 252, 39, 25);
		pnlPreferencias.add(chkMudarse);

		JLabel lblposeeTrabajoActualmente = new JLabel("Estado laboral:");
		lblposeeTrabajoActualmente.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblposeeTrabajoActualmente.setBounds(12, 296, 128, 29);
		pnlPreferencias.add(lblposeeTrabajoActualmente);

		cmbEstadoLab = new JComboBox();
		cmbEstadoLab.setModel(new DefaultComboBoxModel(new String[] {"Desempleado", "Empleado", "En Espera"}));
		cmbEstadoLab.setSelectedIndex(0);
		cmbEstadoLab.setMaximumRowCount(12);
		cmbEstadoLab.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbEstadoLab.setBounds(154, 297, 262, 29);
		pnlPreferencias.add(cmbEstadoLab);
		cambiarEspecializacion("Estudiante Universitario");
		cargarDatos();

	}

	private void cambiarEspecializacion(String especializacion) {
		if(especializacion.equalsIgnoreCase("Obrero")) {
			pnlEstudiante.setVisible(false);
			pnlTecnico.setVisible(false);
			pnlObrero.setVisible(true);
		}
		else if(especializacion.equalsIgnoreCase("Estudiante Universitario")) {
			pnlObrero.setVisible(false);
			pnlTecnico.setVisible(false);
			pnlEstudiante.setVisible(true);
		}
		else if(especializacion.equalsIgnoreCase("Estudiante Tecnico")) {
			pnlEstudiante.setVisible(false);
			pnlObrero.setVisible(false);
			pnlTecnico.setVisible(true);
		}
	}

	private void cargarArea() {
		String nombreArea = cmbArea.getSelectedItem().toString().toLowerCase();
		nombreArea = nombreArea.replace(" ","o");
		nombreArea = nombreArea.replace(" ","");

		lblIcoArea.setIcon(new ImageIcon("recursos/" + nombreArea + ".png"));
	}

	private void cargarJornada() {
		String nombreJornada = cmbJornada.getSelectedItem().toString().toLowerCase();
		nombreJornada = nombreJornada.replace(" ","");
		lblIcoJornada.setIcon(new ImageIcon("recursos/" + nombreJornada + ".png"));
	}

	private void cargarModalidad() {
		String nombreModalidad = cmbModalidad.getSelectedItem().toString().toLowerCase();
		nombreModalidad = nombreModalidad.replace(" ","i");
		lblIcoModalidad.setIcon(new ImageIcon("recursos/" + nombreModalidad + ".png"));
	}


	private void limpiar() {

		txtCodigo.setText("CAN-" + BolsaLaboral.genCodigoCandidato);
		txtNombre.setText("");
		txtApellido.setText("");
		txtCedula.setText("");
		txtCorreo.setText("");
		txtTelefono.setText("");
		if (cmbProvincia.getItemCount() > 0) cmbProvincia.setSelectedIndex(0);
		cargarMunicipios();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -25);
		spnFechaNac.setValue(cal.getTime());

		if(cmbUniversidad != null && cmbUniversidad.getItemCount() > 0) cmbUniversidad.setSelectedIndex(0);
		if(cmbCarrera != null && cmbCarrera.getItemCount() > 0) cmbCarrera.setSelectedIndex(0);
		if(cmbNivel != null) cmbNivel.setSelectedIndex(0);

		cmbAreaTecnica.setSelectedIndex(0);
		if(spnAniosExp != null) spnAniosExp.setValue(0);

		rdUniversitario.setSelected(true);
		cambiarEspecializacion("Estudiante Universitario");

		if(chkPlomeria != null) chkPlomeria.setSelected(false);
		if(chkCarpintero != null) chkCarpintero.setSelected(false);
		if(chkCajero != null) chkCajero.setSelected(false);
		if(chkSoldadura != null) chkSoldadura.setSelected(false);
		if(chkElectrica != null) chkElectrica.setSelected(false);
		if(chkMecanica != null) chkMecanica.setSelected(false);
		if(chkAlbanileria != null) chkAlbanileria.setSelected(false);
		if(chkRedes != null) chkRedes.setSelected(false);
		if(chkConduccion != null) chkConduccion.setSelected(false);
		if(chkReparacion != null) chkReparacion.setSelected(false);
		if(chkVentas != null) chkVentas.setSelected(false);
		if(chkFotografia != null) chkFotografia.setSelected(false);
		if(chkCocina != null) chkCocina.setSelected(false);
		if(chkLimpieza != null) chkLimpieza.setSelected(false);
		if(chkPintura != null) chkPintura.setSelected(false);

		if(chckbxIngles != null) chckbxIngles.setSelected(false);
		if(chckbxItaliano != null) chckbxItaliano.setSelected(false);
		if(chckbxEspanol != null) chckbxEspanol.setSelected(false);
		if(chckbxFrances != null) chckbxFrances.setSelected(false);
		if(chckbxPortugues != null) chckbxPortugues.setSelected(false);
		if(chckbxAleman != null) chckbxAleman.setSelected(false);
		if(chckbxMandarin != null) chckbxMandarin.setSelected(false);
		if(chckbxCoreano != null) chckbxCoreano.setSelected(false);
		if(chckbxJapones != null) chckbxJapones.setSelected(false);

		cmbModalidad.setSelectedIndex(0);
		cmbJornada.setSelectedIndex(0);
		cmbArea.setSelectedIndex(0);
		cmbGenero.setSelectedIndex(0);
		cmbEstadoLab.setSelectedIndex(0);

		if(chkLicenciaConducir != null) chkLicenciaConducir.setSelected(false);
		if(chkMudarse != null) chkMudarse.setSelected(false);
		if(spnSalarioEsperado != null) spnSalarioEsperado.setValue(12000.0f);
	}

	private void registrarCandidato() {
		try {
			String codigo = txtCodigo.getText();
			String nombres = txtNombre.getText().trim();
			String apellidos = txtApellido.getText().trim();
			String cedula = txtCedula.getText().trim();
			String correo = txtCorreo.getText().trim();
			String telefono = txtTelefono.getText().trim();
			int idProvincia = ((Provincia) cmbProvincia.getSelectedItem()).getIdProvincia();
			int idMunicipio = ((Municipio) cmbMunicipio.getSelectedItem()).getIdMunicipio();

			Date fechaNacSpinner = (Date) spnFechaNac.getValue();
			LocalDate fechaNacimiento = fechaNacSpinner.toInstant()
					.atZone(java.time.ZoneId.systemDefault()).toLocalDate();

			String modalidad = cmbModalidad.getSelectedItem().toString();
			String jornada = cmbJornada.getSelectedItem().toString();
			String areaInteres = cmbArea.getSelectedItem().toString();
			String genero = cmbGenero.getSelectedItem().toString();
			String estadoLaboral = cmbEstadoLab.getSelectedItem().toString();
			float salarioEsperado = ((Number) spnSalarioEsperado.getValue()).floatValue();

			boolean licenciaConducir = chkLicenciaConducir.isSelected();
			boolean mudarse = chkMudarse.isSelected();

			ArrayList<String> idiomas = new ArrayList<>();
			for(Component cmp : pnlIdiomas.getComponents()) {
				if(cmp instanceof JCheckBox) {
					JCheckBox chk = (JCheckBox)cmp;
					if(chk.isSelected()) {
						idiomas.add(chk.getText());
					}
				}
			}


			Candidato nuevoCandidato = null;

			if(rdUniversitario.isSelected()) {
				Universidad universidadSel = (Universidad) cmbUniversidad.getSelectedItem();
				Carrera carreraSel = (Carrera) cmbCarrera.getSelectedItem();
				String nivelAcademico = cmbNivel.getSelectedItem().toString();

				nuevoCandidato = new Universitario(codigo, cedula, nombres, apellidos,
						fechaNacimiento, genero, idProvincia, idMunicipio, telefono, correo, jornada,
						modalidad, areaInteres, salarioEsperado, licenciaConducir, mudarse, idiomas,
						universidadSel.getIdUniversidad(), carreraSel.getIdCarrera(),
						nivelAcademico, estadoLaboral);

			} else if (rdTecnico.isSelected()) {
				AreaTecnica areaSel = (AreaTecnica) cmbAreaTecnica.getSelectedItem();
				int aniosExperiencia = ((Number) spnAniosExp.getValue()).intValue();

				nuevoCandidato = new TecnicoSuperior(codigo, cedula, nombres, apellidos,
						fechaNacimiento, genero, idProvincia, idMunicipio, telefono, correo, jornada,
						modalidad, areaInteres, salarioEsperado, licenciaConducir, mudarse,
						idiomas, areaSel.getIdAreaTecnica(), aniosExperiencia, estadoLaboral);

			} else if (rdObrero.isSelected()) {
				ArrayList<String> habilidades = new ArrayList<>();

				for(Component cmp : pnlObrero.getComponents()) {
					if(cmp instanceof JCheckBox) {
						JCheckBox chk = (JCheckBox)cmp;
						if(chk.isSelected()) {
							habilidades.add(chk.getText());
						}
					}
				}

				nuevoCandidato = new Obrero(codigo, cedula, nombres, apellidos,
						fechaNacimiento, genero,idProvincia, idMunicipio, telefono, correo, jornada,
						modalidad, areaInteres, salarioEsperado, licenciaConducir, mudarse,
						idiomas, habilidades,estadoLaboral);
			}

			if(nuevoCandidato != null) {
				if(candidatoAct == null) {
					BolsaLaboral.getInstancia().registrarCandidato(nuevoCandidato);
					JOptionPane.showMessageDialog(this, "Candidato registrado exitosamente",
							"Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);

					limpiar();
					contenedor.setSelectedIndex(0);

				} else {
					candidatoAct.setNombres(nuevoCandidato.getNombres());
					candidatoAct.setApellidos(nuevoCandidato.getApellidos());
					candidatoAct.setIdentificacion(nuevoCandidato.getIdentificacion());
					candidatoAct.setCorreo(nuevoCandidato.getCorreo());
					candidatoAct.setTelefono(nuevoCandidato.getTelefono());
					candidatoAct.setIdProvincia(nuevoCandidato.getIdProvincia());
					candidatoAct.setIdMunicipio(nuevoCandidato.getIdMunicipio());
					candidatoAct.setFechaNacimiento(nuevoCandidato.getFechaNacimiento());
					candidatoAct.setGenero(nuevoCandidato.getGenero());
					candidatoAct.setJornada(nuevoCandidato.getJornada());
					candidatoAct.setModalidad(nuevoCandidato.getModalidad());
					candidatoAct.setAreaDeInteres(nuevoCandidato.getAreaDeInteres());
					candidatoAct.setAspiracionSalarial(nuevoCandidato.getAspiracionSalarial());
					candidatoAct.setLicenciaConducir(nuevoCandidato.isLicenciaConducir());
					candidatoAct.setDisposicionMudarse(nuevoCandidato.isDisposicionMudarse());
					candidatoAct.setIdiomas(nuevoCandidato.getIdiomas());
					candidatoAct.setEstado(nuevoCandidato.getEstado());

					if (candidatoAct instanceof Universitario && nuevoCandidato instanceof Universitario) {
						Universitario uAct = (Universitario) candidatoAct;
						Universitario uNuevo = (Universitario) nuevoCandidato;
						uAct.setIdUniversidad(uNuevo.getIdUniversidad());
						uAct.setIdCarrera(uNuevo.getIdCarrera());
						uAct.setNivelAcademico(uNuevo.getNivelAcademico());
					} else if (candidatoAct instanceof TecnicoSuperior && nuevoCandidato instanceof TecnicoSuperior) {
						((TecnicoSuperior) candidatoAct).setIdAreaTecnica(((TecnicoSuperior) nuevoCandidato).getIdAreaTecnica());
						((TecnicoSuperior) candidatoAct).setAniosExperiencia(((TecnicoSuperior) nuevoCandidato).getAniosExperiencia());
					} else if (candidatoAct instanceof Obrero && nuevoCandidato instanceof Obrero) {
						((Obrero)candidatoAct).setHabilidades(((Obrero)nuevoCandidato).getHabilidades());
					}

					BolsaLaboral.getInstancia().modificarCandidato(candidatoAct);   // <-- nuevo: persiste el cambio

					JOptionPane.showMessageDialog(this, "Candidato modificado exitosamente",
							"Modificación Exitosa", JOptionPane.INFORMATION_MESSAGE);
					dispose();
					ConsultarCandidatos.cargarCandidatos();
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al procesar los datos: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cargarMunicipios() {
		cmbMunicipio.removeAllItems();
		Provincia seleccionada = (Provincia) cmbProvincia.getSelectedItem();
		if (seleccionada != null) {
			for (Municipio m : municipioDAO.listarPorProvincia(seleccionada.getIdProvincia())) {
				cmbMunicipio.addItem(m);
			}
		}
	}

	private void cargarDatos() {
		if (candidatoAct != null) {
			txtCodigo.setText(candidatoAct.getCodigo());
			txtNombre.setText(candidatoAct.getNombres());
			txtApellido.setText(candidatoAct.getApellidos());
			txtCedula.setText(candidatoAct.getIdentificacion());
			txtCorreo.setText(candidatoAct.getCorreo());
			txtTelefono.setText(candidatoAct.getTelefono());

			for (int i = 0; i < cmbProvincia.getItemCount(); i++) {
				if (cmbProvincia.getItemAt(i).getIdProvincia() == candidatoAct.getIdProvincia()) {
					cmbProvincia.setSelectedIndex(i);
					break;
				}
			}
			cargarMunicipios();
			for (int i = 0; i < cmbMunicipio.getItemCount(); i++) {
				if (cmbMunicipio.getItemAt(i).getIdMunicipio() == candidatoAct.getIdMunicipio()) {
					cmbMunicipio.setSelectedIndex(i);
					break;
				}
			}

			Date fechaNac = Date.from(candidatoAct.getFechaNacimiento()
					.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
			spnFechaNac.setValue(fechaNac);

			cmbModalidad.setSelectedItem(candidatoAct.getModalidad());
			cmbJornada.setSelectedItem(candidatoAct.getJornada());
			cmbArea.setSelectedItem(candidatoAct.getAreaDeInteres());
			cmbGenero.setSelectedItem(candidatoAct.getGenero());
			cmbEstadoLab.setSelectedItem(candidatoAct.getEstado());
			spnSalarioEsperado.setValue(candidatoAct.getAspiracionSalarial());
			chkLicenciaConducir.setSelected(candidatoAct.isLicenciaConducir());
			chkMudarse.setSelected(candidatoAct.isDisposicionMudarse());

			for (String idioma : candidatoAct.getIdiomas()) {
				switch (idioma) {
					case "Inglés": chckbxIngles.setSelected(true); break;
					case "Italiano": chckbxItaliano.setSelected(true); break;
					case "Español": chckbxEspanol.setSelected(true); break;
					case "Francés": chckbxFrances.setSelected(true); break;
					case "Portugués": chckbxPortugues.setSelected(true); break;
					case "Alemán": chckbxAleman.setSelected(true); break;
					case "Chino": chckbxMandarin.setSelected(true); break;
					case "Coreano": chckbxCoreano.setSelected(true); break;
					case "Japonés": chckbxJapones.setSelected(true); break;
				}
			}

			if (candidatoAct instanceof Universitario) {
				rdUniversitario.setSelected(true);
				rdTecnico.setEnabled(false);
				rdObrero.setEnabled(false);

				cambiarEspecializacion("Estudiante Universitario");

				Universitario uni = (Universitario) candidatoAct;

				for (int i = 0; i < cmbUniversidad.getItemCount(); i++) {
					if (cmbUniversidad.getItemAt(i).getIdUniversidad() == uni.getIdUniversidad()) {
						cmbUniversidad.setSelectedIndex(i);
						break;
					}
				}
				for (int i = 0; i < cmbCarrera.getItemCount(); i++) {
					if (cmbCarrera.getItemAt(i).getIdCarrera() == uni.getIdCarrera()) {
						cmbCarrera.setSelectedIndex(i);
						break;
					}
				}
				cmbNivel.setSelectedItem(uni.getNivelAcademico());

			} else if (candidatoAct instanceof TecnicoSuperior) {
				rdTecnico.setSelected(true);
				rdUniversitario.setEnabled(false);
				rdObrero.setEnabled(false);

				cambiarEspecializacion("Estudiante Tecnico");

				TecnicoSuperior tecnico = (TecnicoSuperior) candidatoAct;
				for (int i = 0; i < cmbAreaTecnica.getItemCount(); i++) {
					if (cmbAreaTecnica.getItemAt(i).getIdAreaTecnica() == tecnico.getIdAreaTecnica()) {
						cmbAreaTecnica.setSelectedIndex(i);
						break;
					}
				}
				spnAniosExp.setValue(tecnico.getAniosExperiencia());

			} else if (candidatoAct instanceof Obrero) {
				rdObrero.setSelected(true);
				rdUniversitario.setEnabled(false);
				rdTecnico.setEnabled(false);

				cambiarEspecializacion("Obrero");

				Obrero obrero = (Obrero) candidatoAct;
				for (String habilidad : obrero.getHabilidades()) {
					switch (habilidad) {
						case "Plomería": chkPlomeria.setSelected(true); break;
						case "Carpintería": chkCarpintero.setSelected(true); break;
						case "Gestión Financiera": chkCajero.setSelected(true); break;
						case "Soldadura": chkSoldadura.setSelected(true); break;
						case "Instalación Eléctrica": chkElectrica.setSelected(true); break;
						case "Mecánica": chkMecanica.setSelected(true); break;
						case "Albañilería": chkAlbanileria.setSelected(true); break;
						case "Redes Sociales": chkRedes.setSelected(true); break;
						case "Conducción": chkConduccion.setSelected(true); break;
						case "Reparación de Electrónicos": chkReparacion.setSelected(true); break;
						case "Ventas": chkVentas.setSelected(true); break;
						case "Fotografía": chkFotografia.setSelected(true); break;
						case "Cocina": chkCocina.setSelected(true); break;
						case "Limpieza": chkLimpieza.setSelected(true); break;
						case "Pintura": chkPintura.setSelected(true); break;
					}
				}
			}
		} else {
			rdUniversitario.setEnabled(true);
			rdTecnico.setEnabled(true);
			rdObrero.setEnabled(true);
		}
	}


	private boolean verificar() throws FormatException {
		if(txtNombre.getText().trim().isEmpty()) {
			throw new FormatException("El nombre es obligatorio");
		}
		if(txtApellido.getText().trim().isEmpty()) {
			throw new FormatException("El apellido es obligatoria");
		}
		String cedula = txtCedula.getText().trim().replaceAll("[^0-9]", "");
		if(cedula.length() != 11) {
			throw new FormatException("La c dula debe tener 11 d gitos");
		}

		Date fechaNacimiento = (Date) spnFechaNac.getValue();
		Calendar cal = Calendar.getInstance();
		Date fechaActual = cal.getTime();

		if(fechaNacimiento.after(fechaActual)) {
			throw new FormatException("La fecha de nacimiento no puede ser futura");
		}

		cal.add(Calendar.YEAR, -16);
		if(fechaNacimiento.after(cal.getTime())) {
			throw new FormatException("El candidato debe tener al menos 16 a os");
		}

		cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -100);
		if(fechaNacimiento.before(cal.getTime())) {
			throw new FormatException("La fecha de nacimiento no puede ser anterior a " + (Calendar.getInstance().get(Calendar.YEAR) - 100));
		}

		if(cmbGenero == null || cmbGenero.getSelectedItem() == null ||
				cmbGenero.getSelectedItem().toString().trim().isEmpty()) {
			throw new FormatException("El g nero es obligatoria");
		}

		if(txtTelefono.getText().trim().isEmpty()) {
			throw new FormatException("El tel fono es obligatorio");
		}

		if(txtCorreo.getText().trim().isEmpty()) {
			throw new FormatException("El correo es obligatorio");
		}

		if(cmbProvincia.getSelectedItem() == null) {
			throw new FormatException("La provincia es obligatoria");
		}
		if(cmbMunicipio.getSelectedItem() == null) {
			throw new FormatException("El municipio es obligatorio");
		}

		if(!txtCorreo.getText().contains("@") || !txtCorreo.getText().contains(".")) {
			throw new FormatException("Formato del correo inv lido. Ejemplo: usuario@dominio.com\"");
		}

		String telefono = txtTelefono.getText().trim().replaceAll("[^0-9]", "");
		if(telefono.length() != 10) {
			throw new FormatException("El tel fono debe tener 10 d gitos");
		}

		if(rdUniversitario.isSelected()) {
			if(cmbUniversidad.getSelectedItem() == null) {
				throw new FormatException("La universidad es obligatoria para estudiantes universitarios");
			}
			if(cmbCarrera.getSelectedItem() == null) {
				throw new FormatException("La carrera es obligatoria para estudiantes universitarios");
			}

		} else if (rdTecnico.isSelected()) {
			if (cmbAreaTecnica.getSelectedItem() == null) {
				throw new FormatException("El área técnica es obligatoria para técnicos superiores");
			}
		} else if (rdObrero.isSelected()) {
			boolean tieneHabilidad = false;
			for (Component cmp : pnlObrero.getComponents()) {
				if (cmp instanceof JCheckBox) {
					JCheckBox chk = (JCheckBox) cmp;
					if (chk.isSelected()) {
						tieneHabilidad = true;
						break;
					}
				}
			}
			if (!tieneHabilidad) {
				throw new FormatException("Debe seleccionar al menos una habilidad para obreros");
			}
		}
		if(cmbModalidad == null || cmbModalidad.getSelectedItem() == null ||
				cmbModalidad.getSelectedItem().toString().trim().isEmpty()) {
			throw new FormatException("La modalidad es obligatoria");
		}
		if(cmbArea == null || cmbArea.getSelectedItem() == null ||
				cmbArea.getSelectedItem().toString().trim().isEmpty()) {
			throw new FormatException("Debe seleccionar un  rea");
		}

		if(cmbJornada == null || cmbJornada.getSelectedItem() == null ||
				cmbJornada.getSelectedItem().toString().trim().isEmpty()) {
			throw new FormatException("La jornada es obligatoria");
		}

		boolean tieneIdioma = false;
		for(Component cmp : pnlIdiomas.getComponents()) {
			if(cmp instanceof JCheckBox) {
				JCheckBox chk = (JCheckBox)cmp;
				if(chk.isSelected()) {
					tieneIdioma = true;
					break;
				}
			}
		}
		if(!tieneIdioma) {
			throw new FormatException("Debe seleccionar al menos un idioma");
		}


		float salarioEsperado = ((Number) spnSalarioEsperado.getValue()).floatValue();
		if(salarioEsperado < 12000) {
			throw new FormatException("El salario esperado debe ser al menos 12,000");
		}

		return true;
	}
}