package visual;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;

import javax.print.attribute.UnmodifiableSetException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.Printable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import logico.BolsaLaboral;
import logico.Usuario;


public class Principal extends JFrame {

	private JPanel contentPane;
	private Dimension dim;
	private JMenu mnGestion;
	private JMenu mnCatalogoDeOfertas;
	private JMenu mnCentros;
	private JMenu mnCandidatos;

	/**
	 * Launch the application.
	 */
    /*public static void main(String[] args) {
       EventQueue.invokeLater(new Runnable() {
          public void run() {
             try {
                Principal frame = new Principal();
                frame.setVisible(true);
             } catch (Exception e) {
                e.printStackTrace();
             }
          }
       });
    }*/

	/**
	 * Create the frame.
	 */

	public Principal() {

		setTitle("Bolsa Laboral");
		setIconImage(Toolkit.getDefaultToolkit().getImage("recursos/icono.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 571, 417);
		dim = super.getToolkit().getScreenSize();
		super.setSize(dim.width, dim.height-45);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		setJMenuBar(menuBar);

		mnCentros = new JMenu("Centros de Trabajo");
		mnCentros.setIcon(new ImageIcon("recursos/empresa.png"));
		mnCentros.setForeground(Color.BLACK);
		mnCentros.setFont(new Font("Segoe UI", Font.BOLD, 20));
		menuBar.add(mnCentros);

		JMenuItem mntmCentConsultar = new JMenuItem("  Consultar");
		mntmCentConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultarCentros cons = new ConsultarCentros();
				cons.setModal(true);
				cons.setVisible(true);
			}
		});
		mntmCentConsultar.setIcon(new ImageIcon("recursos/consulta.png"));
		mntmCentConsultar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnCentros.add(mntmCentConsultar);

		JMenuItem mntmCentRegistrar = new JMenuItem("  Registrar");
		mntmCentRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistroCentro registro = new RegistroCentro(null);
				registro.setModal(true);
				registro.setVisible(true);
			}
		});
		mntmCentRegistrar.setIcon(new ImageIcon("recursos/registro.png"));
		mntmCentRegistrar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnCentros.add(mntmCentRegistrar);

		mnCandidatos = new JMenu("Candidatos");
		mnCandidatos.setIcon(new ImageIcon("recursos/trabajador.png"));
		mnCandidatos.setForeground(Color.BLACK);
		mnCandidatos.setFont(new Font("Segoe UI", Font.BOLD, 20));
		menuBar.add(mnCandidatos);

		JMenuItem mntmCandConsultar = new JMenuItem("  Consultar");
		mntmCandConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultarCandidatos cons = new ConsultarCandidatos();
				cons.setModal(true);
				cons.setVisible(true);
			}
		});
		mntmCandConsultar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmCandConsultar.setIcon(new ImageIcon("recursos/consulta.png"));
		mnCandidatos.add(mntmCandConsultar);

		JMenuItem mntmCandRegistrar = new JMenuItem("  Registrar");
		mntmCandRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistroCandidato reg = new RegistroCandidato(null);
				reg.setModal(true);
				reg.setVisible(true);
			}
		});
		mntmCandRegistrar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmCandRegistrar.setIcon(new ImageIcon("recursos/registro.png"));
		mnCandidatos.add(mntmCandRegistrar);

		mnCatalogoDeOfertas = new JMenu("Catalogo de Ofertas");
		mnCatalogoDeOfertas.setIcon(new ImageIcon("recursos/conexion.png"));
		mnCatalogoDeOfertas.setForeground(Color.BLACK);
		mnCatalogoDeOfertas.setFont(new Font("Segoe UI", Font.BOLD, 20));
		menuBar.add(mnCatalogoDeOfertas);

		JMenuItem mntmCatConsultar = new JMenuItem("  Consultar");
		mntmCatConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultarOfertas cons = new ConsultarOfertas();
				cons.setModal(true);
				cons.setVisible(true);
			}
		});
		mntmCatConsultar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmCatConsultar.setIcon(new ImageIcon("recursos/consulta.png"));
		mnCatalogoDeOfertas.add(mntmCatConsultar);

		JMenuItem mntmCatRegistrar = new JMenuItem("  Registrar");
		mntmCatRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistroOfertaLaboral reg = new RegistroOfertaLaboral(null);
				reg.setModal(true);
				reg.setVisible(true);
			}
		});
		mntmCatRegistrar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmCatRegistrar.setIcon(new ImageIcon("recursos/registro.png"));
		mnCatalogoDeOfertas.add(mntmCatRegistrar);

		JMenuItem mntmSolicitudes = new JMenuItem("  Solicitudes");
		mntmSolicitudes.setIcon(new ImageIcon("recursos/solicitud.png"));
		mntmSolicitudes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultarSolicitudes cons = new ConsultarSolicitudes();
				cons.setModal(true);
				cons.setVisible(true);
			}
		});
		mntmSolicitudes.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnCatalogoDeOfertas.add(mntmSolicitudes);

		mnGestion = new JMenu("Gestion de Datos");
		mnGestion.setForeground(Color.BLACK);
		mnGestion.setFont(new Font("Segoe UI", Font.BOLD, 20));
		mnGestion.setIcon(new ImageIcon("recursos/gestion.png"));
		menuBar.add(mnGestion);


		JMenuItem mntmProcesamiento = new JMenuItem("  Procesamiento");
		mntmProcesamiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProcesamientoAvanzado pros = new ProcesamientoAvanzado();
				pros.setModal(true);
				pros.setVisible(true);
			}
		});
		mntmProcesamiento.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmProcesamiento.setIcon(new ImageIcon("recursos/avanzado.png"));
		mnGestion.add(mntmProcesamiento);

		JMenuItem mntmInformes = new JMenuItem("  Informe");
		mnGestion.add(mntmInformes);
		mntmInformes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InformeGeneral inf = new InformeGeneral();
				inf.setModal(true);
				inf.setVisible(true);
			}
		});
		mntmInformes.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmInformes.setIcon(new ImageIcon("recursos/informes.png"));
		JMenuItem mntmDesbalance = new JMenuItem("  Desbalance Oferta vs Demanda");
		mntmDesbalance.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmDesbalance.setIcon(new ImageIcon("recursos/informes.png"));
		mntmDesbalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InformeSQL(
						"Desbalance Oferta vs Demanda por Área",
						"Compara ofertas activas y candidatos por área. El desbalance indica si faltan o sobran personas.",
						"SELECT * FROM DesbalanceOfertaDemandaPorArea"
				).setVisible(true);
			}
		});
		mnGestion.add(mntmDesbalance);

		JMenuItem mntmIdiomas = new JMenuItem("  Demanda vs Oferta de Idiomas");
		mntmIdiomas.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmIdiomas.setIcon(new ImageIcon("recursos/informes.png"));
		mntmIdiomas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InformeSQL(
						"Idiomas más demandados vs oferta de candidatos",
						"Muestra qué idiomas piden las ofertas y cuántos candidatos los hablan.",
						"SELECT * FROM DemandaVsOfertaIdiomas"
				).setVisible(true);
			}
		});
		mnGestion.add(mntmIdiomas);

		JMenuItem mntmOfertasRiesgo = new JMenuItem("  Ofertas en Riesgo de Pérdida");
		mntmOfertasRiesgo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmOfertasRiesgo.setIcon(new ImageIcon("recursos/informes.png"));
		mntmOfertasRiesgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InformeSQL(
						"Ofertas en riesgo de pérdida",
						"Ofertas activas sin solicitudes o con mucho tiempo abiertas sin llenarse.",
						"SELECT * FROM OfertasSinLlenarRiesgo"
				).setVisible(true);
			}
		});
		mnGestion.add(mntmOfertasRiesgo);

		JMenuItem mntmRankingCentros = new JMenuItem("  Ranking de Centros por Colocación");
		mntmRankingCentros.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmRankingCentros.setIcon(new ImageIcon("recursos/informes.png"));
		mntmRankingCentros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InformeSQL(
						"Ranking de centros por éxito de colocación",
						"Porcentaje de cobertura de vacantes y colocaciones por centro empleador.",
						"SELECT * FROM RankingCentrosColocacion ORDER BY cobertura_pct DESC, vacantes_completadas DESC"
				).setVisible(true);
			}
		});
		mnGestion.add(mntmRankingCentros);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(4, 13, 18));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0,0, getWidth(),getHeight());
		lblFondo.setIcon(new ImageIcon("recursos/fondo.png"));
		contentPane.add(lblFondo);

		userUI();

	}

	private void userUI() {
		if(!BolsaLaboral.getInstancia().getUsuarioActual().getTipo().equals("Admin")) {
			mnGestion.setEnabled(false);
		}
	}

}