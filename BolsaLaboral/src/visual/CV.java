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

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class CV extends JDialog {

	private final JPanel contenedor = new JPanel();
	public static Object[] row;
	private JTextPane txpIdiomas;
	private JTextPane txpDatosFormacion;
	private JPanel pnlResumen;
	private JLabel lblNombre;
	private JLabel lblFechaNac;
	private JTextPane txpDescripcion;
	private JLabel lblDatosAcadmicos;
	private JLabel lblModalidad;
	private JLabel lblJornada;
	private JLabel lblArea;
	private JLabel lblform;
	private JLabel lblUbic;
	private JLabel lblTelefono;

	/**
	 * Create the dialog.
	 */
	public CV(Candidato solicitante) {
		setResizable(false);
		setTitle(solicitante.getNombres() + " " + solicitante.getApellidos());
		setIconImage(Toolkit.getDefaultToolkit().getImage("recursos/icono.png"));
		setBounds(100, 100, 734, 695);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		contenedor.setBackground(new Color(228, 228, 228));
		getContentPane().add(contenedor, BorderLayout.CENTER);
		contenedor.setLayout(null);
		{
			JButton btnCancelar = new JButton("Cerrar");
			btnCancelar.setBackground(Color.WHITE);
			btnCancelar.setIcon(new ImageIcon("recursos/cerrar.png"));
			btnCancelar.setBounds(554, 600, 140, 31);
			contenedor.add(btnCancelar);
			btnCancelar.setFocusable(false);
			btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 16));
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancelar.setActionCommand("Cancel");
		}

		pnlResumen = new JPanel();
		pnlResumen.setBackground(new Color(34, 34, 34));
		pnlResumen.setBounds(0, 0, 220, 660);
		contenedor.add(pnlResumen);
		pnlResumen.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel(" Ubicaci\u00F3n");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Consolas", Font.BOLD, 18));
		lblNewLabel_1.setIcon(new ImageIcon("recursos/ubicacion.png"));
		lblNewLabel_1.setBounds(35, 94, 151, 34);
		pnlResumen.add(lblNewLabel_1);

		JLabel lblContactos = new JLabel(" Contactos");
		lblContactos.setIcon(new ImageIcon("recursos/contactos.png"));
		lblContactos.setForeground(Color.WHITE);
		lblContactos.setFont(new Font("Consolas", Font.BOLD, 18));
		lblContactos.setBounds(35, 176, 151, 34);
		pnlResumen.add(lblContactos);

		JLabel lblIdiomas = new JLabel(" Idiomas");
		lblIdiomas.setIcon(new ImageIcon("recursos/idiomas.png"));
		lblIdiomas.setForeground(Color.WHITE);
		lblIdiomas.setFont(new Font("Consolas", Font.BOLD, 18));
		lblIdiomas.setBounds(35, 262, 151, 34);
		pnlResumen.add(lblIdiomas);

		JLabel lblNivelAcadmico = new JLabel(" Formaci\u00F3n");
		lblNivelAcadmico.setIcon(new ImageIcon("recursos/nivel.png"));
		lblNivelAcadmico.setForeground(Color.WHITE);
		lblNivelAcadmico.setFont(new Font("Consolas", Font.BOLD, 18));
		lblNivelAcadmico.setBounds(35, 13, 151, 34);
		pnlResumen.add(lblNivelAcadmico);

		txpIdiomas = new JTextPane();
		txpIdiomas.setEditable(false);
		txpIdiomas.setForeground(Color.WHITE);
		txpIdiomas.setFont(new Font("Consolas", Font.PLAIN, 16));
		txpIdiomas.setBackground(Color.BLACK);
		txpIdiomas.setBounds(12, 298, 196, 236);
		pnlResumen.add(txpIdiomas);

		lblform = new JLabel(" Formaci\u00F3n");
		lblform.setHorizontalAlignment(SwingConstants.CENTER);
		lblform.setForeground(Color.WHITE);
		lblform.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblform.setBounds(12, 52, 196, 34);
		pnlResumen.add(lblform);

		lblUbic = new JLabel(" Formaci\u00F3n");
		lblUbic.setHorizontalAlignment(SwingConstants.CENTER);
		lblUbic.setForeground(Color.WHITE);
		lblUbic.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblUbic.setBounds(12, 129, 196, 34);
		pnlResumen.add(lblUbic);

		lblTelefono = new JLabel("<dynamic>,<dynamic>");
		lblTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		lblTelefono.setForeground(Color.WHITE);
		lblTelefono.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblTelefono.setBounds(12, 215, 196, 34);
		pnlResumen.add(lblTelefono);

		lblNombre = new JLabel("NOMBRE");
		lblNombre.setHorizontalAlignment(SwingConstants.LEFT);
		lblNombre.setFont(new Font("Verdana", Font.PLAIN, 30));
		lblNombre.setBounds(234, 13, 460, 70);
		contenedor.add(lblNombre);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(234, 117, 460, 3);
		contenedor.add(separator);

		JLabel lblNewLabel = new JLabel("SOBRE MI");
		lblNewLabel.setBounds(234, 122, 460, 54);
		contenedor.add(lblNewLabel);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);

		txpDescripcion = new JTextPane();
		txpDescripcion.setEditable(false);
		txpDescripcion.setForeground(new Color(0, 0, 0));
		txpDescripcion.setFont(new Font("Verdana", Font.PLAIN, 15));
		txpDescripcion.setText("Descripci\u00F3n ultraepica");
		txpDescripcion.setBounds(234, 165, 460, 167);
		contenedor.add(txpDescripcion);

		lblDatosAcadmicos = new JLabel("DATOS ACAD\u00C9MICOS");
		lblDatosAcadmicos.setHorizontalAlignment(SwingConstants.LEFT);
		lblDatosAcadmicos.setForeground(Color.BLACK);
		lblDatosAcadmicos.setFont(new Font("Consolas", Font.BOLD, 18));
		lblDatosAcadmicos.setBounds(234, 345, 460, 54);
		contenedor.add(lblDatosAcadmicos);

		txpDatosFormacion = new JTextPane();
		txpDatosFormacion.setEditable(false);
		txpDatosFormacion.setText("Detalles de la estudiasao");
		txpDatosFormacion.setForeground(Color.BLACK);
		txpDatosFormacion.setFont(new Font("Verdana", Font.PLAIN, 15));
		txpDatosFormacion.setBounds(234, 394, 460, 126);
		contenedor.add(txpDatosFormacion);

		lblFechaNac = new JLabel("Fecha Nac.");
		lblFechaNac.setIcon(new ImageIcon("recursos/calendario.png"));
		lblFechaNac.setHorizontalAlignment(SwingConstants.LEFT);
		lblFechaNac.setForeground(Color.BLACK);
		lblFechaNac.setFont(new Font("Consolas", Font.BOLD, 14));
		lblFechaNac.setBounds(234, 82, 267, 38);
		contenedor.add(lblFechaNac);

		JLabel lblPreferenciasLaborales = new JLabel("Preferencias:");
		lblPreferenciasLaborales.setHorizontalAlignment(SwingConstants.LEFT);
		lblPreferenciasLaborales.setForeground(Color.BLACK);
		lblPreferenciasLaborales.setFont(new Font("Consolas", Font.BOLD, 18));
		lblPreferenciasLaborales.setBounds(232, 599, 140, 38);
		contenedor.add(lblPreferenciasLaborales);

		lblArea = new JLabel("");
		lblArea.setBackground(Color.WHITE);
		lblArea.setBounds(375, 597, 40, 40);
		contenedor.add(lblArea);

		lblModalidad = new JLabel("");
		lblModalidad.setBackground(Color.WHITE);
		lblModalidad.setBounds(477, 597, 40, 40);
		contenedor.add(lblModalidad);

		lblJornada = new JLabel("");
		lblJornada.setBackground(Color.WHITE);
		lblJornada.setBounds(427, 597, 40, 40);
		contenedor.add(lblJornada);

		cargarCV(solicitante);
		setApariencia(solicitante.getAreaDeInteres());
	}

	public void cargarCV(Candidato solicitante) {
		if(solicitante instanceof Universitario) {
			lblform.setText(((Universitario) solicitante).getNivelAcademico());
		}
		else if(solicitante instanceof TecnicoSuperior) {
			lblform.setText("T cnico");
		}
		else if(solicitante instanceof Obrero) {
			lblform.setText("Trabajador");
		}

		lblNombre.setText(getFormatNombre(solicitante));
		lblFechaNac.setText(solicitante.getFechaNacimiento().toString());
		lblUbic.setText(getFormatUbicacion(solicitante));
		lblUbic.setToolTipText(getFormatUbicacionCompleta(solicitante));
		lblTelefono.setText(solicitante.getTelefono());
		lblArea.setToolTipText(solicitante.getAreaDeInteres());
		lblJornada.setToolTipText(solicitante.getJornada());
		lblModalidad.setToolTipText(solicitante.getModalidad());
		cargarIdiomas(solicitante.getIdiomas());
		txpDescripcion.setText(solicitante.getSobreMi());
		txpDatosFormacion.setText(solicitante.getFormacion());
		cargarArea(solicitante.getAreaDeInteres());
		cargarJornada(solicitante.getJornada());
		cargarModalidad(solicitante.getModalidad());
	}

	private String getFormatUbicacionCompleta(Candidato solicitante) {
		db.MunicipioDAO municipioDAO = new db.MunicipioDAOImpl();
		db.ProvinciaDAO provinciaDAO = new db.ProvinciaDAOImpl();
		logico.Municipio m = municipioDAO.buscarPorId(solicitante.getIdMunicipio());
		logico.Provincia p = provinciaDAO.buscarPorId(solicitante.getIdProvincia());
		return (m != null ? m.getNombreMunicipio() : "") + ", " + (p != null ? p.getNombreProvincia() : "");
	}

	private String getFormatUbicacion(Candidato solicitante) {
		db.MunicipioDAO municipioDAO = new db.MunicipioDAOImpl();
		db.ProvinciaDAO provinciaDAO = new db.ProvinciaDAOImpl();
		logico.Municipio m = municipioDAO.buscarPorId(solicitante.getIdMunicipio());
		logico.Provincia p = provinciaDAO.buscarPorId(solicitante.getIdProvincia());
		String municipio = m != null ? m.getNombreMunicipio() : "";
		String provincia = p != null ? p.getNombreProvincia() : "";
		String ubicacion = municipio + ", " + provincia;

		if (ubicacion.length() <= 15) {
			return ubicacion;
		}
		int maxTotal = 15;
		int maxPorParte = (maxTotal - 2) / 2;

		String municipioAbrev = municipio.length() > maxPorParte
				? municipio.substring(0, maxPorParte - 1) + "."
				: municipio;

		String provinciaAbrev = provincia.length() > maxPorParte
				? provincia.substring(0, maxPorParte - 1) + "."
				: provincia;

		String resultado = municipioAbrev + ", " + provinciaAbrev;
		while (resultado.length() > 15 && provinciaAbrev.length() > 2) {
			provinciaAbrev = provinciaAbrev.substring(0, provinciaAbrev.length() - 2) + ".";
			resultado = municipioAbrev + ", " + provinciaAbrev;
		}

		return resultado;
	}

	private String getFormatNombre(Candidato solicitante) {
		String nombreCompleto = solicitante.getNombres() + " " + solicitante.getApellidos();

		if (nombreCompleto.length() > 26) {
			String[] nombres = solicitante.getNombres().split(" ");
			String[] apellidos = solicitante.getApellidos().split(" ");

			StringBuilder strb = new StringBuilder();
			if (nombres.length > 0) {
				strb.append(nombres[0]);
			}
			if (nombres.length > 1) {
				strb.append(" ").append(nombres[1].charAt(0)).append(".");
			}
			if (apellidos.length > 0) {
				strb.append(" ").append(apellidos[0]);
			}

			if (apellidos.length > 1) {
				strb.append(" ").append(apellidos[1].charAt(0)).append(".");
			}

			return strb.toString().trim();
		} else {
			return nombreCompleto;
		}
	}


	private void cargarIdiomas(ArrayList<String> idiomas) {
		for(String idioma : idiomas) {
			txpIdiomas.setText(txpIdiomas.getText().concat("\n" + idioma));
		}

	}

	private void cargarArea(String nombreArea) {
		nombreArea = nombreArea.toLowerCase();
		nombreArea = nombreArea.replace(" ","o");
		nombreArea = nombreArea.replace(" ","");

		lblArea.setIcon(new ImageIcon("recursos/" + nombreArea + ".png"));
	}

	private void cargarJornada(String nombreJornada) {
		nombreJornada = nombreJornada.toLowerCase();
		nombreJornada = nombreJornada.replace(" ","");
		lblJornada.setIcon(new ImageIcon("recursos/" + nombreJornada + ".png"));
	}

	private void cargarModalidad(String modalidad) {
		String nombreModalidad = modalidad.toLowerCase();
		nombreModalidad = nombreModalidad.replace(" ","i");
		lblModalidad.setIcon(new ImageIcon("recursos/" + nombreModalidad + ".png"));
	}


	private Color getColorPrincipal(String area) {
		switch (area) {
			case "Finanzas": return new Color(213, 69, 27);
			case "Recursos Humanos": return new Color(27, 60, 83);
			case "Marketing": return new Color(197, 23, 46);
			case "Limpieza": return new Color(78, 102, 136);
			case "Seguridad": return new Color(10, 64, 12);
			case "TI": return new Color(9, 107, 104);
			case "Salud": return new Color(162, 18, 50);
			case "Operaciones": return new Color(39, 63, 79);
			case "Administraci n": return new Color(190, 49, 68);
			case "Atenci n al Cliente": return new Color(130, 17, 49);
			default: return new Color(57, 62, 7);
		}
	}


	public void setApariencia(String area) {
		Color fondoPanel = getColorPrincipal(area);
		pnlResumen.setBackground(fondoPanel);
		txpIdiomas.setBackground(fondoPanel);
		txpIdiomas.setFocusable(false);

		Color fondoGeneral = new Color(255, 255, 255);
		contenedor.setBackground(fondoGeneral);

		txpDescripcion.setBackground(fondoGeneral);
		txpDescripcion.setFocusable(false);
		txpDatosFormacion.setBackground(fondoGeneral);
		txpDatosFormacion.setFocusable(false);

	}
}