package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import logico.BolsaLaboral;

public class InformeGeneral extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblCandidatos;
	private JLabel lblOfertas;
	private JLabel lblEmpresas;
	private JLabel lblSolicitudes;
	private JLabel lblContratados;
	private JLabel lblDeExito;
	private JLabel lblTasaCobertura;
	private JLabel lblOfVacias;

	/**
	 * Create the dialog.
	 */
	public InformeGeneral() {
		setBounds(100, 100, 789, 675);
		setIconImage(Toolkit.getDefaultToolkit().getImage("recursos/icono.png"));
		getContentPane().setLayout(new BorderLayout());
		setTitle("Informe General");
		setResizable(false);
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(new Color(4, 13, 18));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

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
		btnCancelar.setBounds(608, 579, 140, 31);
		contentPanel.add(btnCancelar);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(24, 61, 61));
		panel.setBounds(0, 0, 783, 81);
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Bienvenido");
		lblNewLabel.setBounds(18, 13, 142, 55);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 24));
		panel.add(lblNewLabel);

		JLabel lblUsuario = new JLabel("USUARIO");
		lblUsuario.setBounds(159, 13, 487, 55);
		lblUsuario.setText(BolsaLaboral.getInstancia().getUsuarioActual().getNombreUsuario());
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Consolas", Font.BOLD, 24));
		panel.add(lblUsuario);

		JLabel lblResumenInformativo = new JLabel("Resumen Informativo");
		lblResumenInformativo.setHorizontalAlignment(SwingConstants.CENTER);
		lblResumenInformativo.setForeground(Color.WHITE);
		lblResumenInformativo.setFont(new Font("Consolas", Font.PLAIN, 20));
		lblResumenInformativo.setBounds(10, 94, 761, 55);
		contentPanel.add(lblResumenInformativo);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(250, 98, 1, 50);
		contentPanel.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(Color.WHITE);
		separator_1.setBounds(535, 98, 1, 50);
		contentPanel.add(separator_1);

		JPanel pnlCandidatos = new JPanel();
		pnlCandidatos.setBorder(new LineBorder(Color.WHITE, 2));
		pnlCandidatos.setBackground(new Color(4, 87, 87));
		pnlCandidatos.setBounds(20, 162, 173, 183);
		contentPanel.add(pnlCandidatos);
		pnlCandidatos.setLayout(null);

		JLabel lblIcoCand = new JLabel("");
		lblIcoCand.setBounds(22, 13, 128, 128);
		pnlCandidatos.add(lblIcoCand);
		lblIcoCand.setIcon(new ImageIcon("recursos/dashcand.png"));

		lblCandidatos = new JLabel("Candidatos");
		lblCandidatos.setBounds(0, 144, 173, 39);
		pnlCandidatos.add(lblCandidatos);
		lblCandidatos.setForeground(Color.WHITE);
		lblCandidatos.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblCandidatos.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(Color.WHITE, 2));
		panel_1.setBackground(new Color(4, 87, 87));
		panel_1.setBounds(205, 162, 173, 183);
		contentPanel.add(panel_1);

		JLabel lblIcoOfer = new JLabel("");
		lblIcoOfer.setIcon(new ImageIcon("recursos/dashvac.png"));
		lblIcoOfer.setBounds(22, 13, 128, 128);
		panel_1.add(lblIcoOfer);

		lblOfertas = new JLabel("Ofertas");
		lblOfertas.setHorizontalAlignment(SwingConstants.CENTER);
		lblOfertas.setForeground(Color.WHITE);
		lblOfertas.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblOfertas.setBounds(0, 144, 173, 39);
		panel_1.add(lblOfertas);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new LineBorder(Color.WHITE, 2));
		panel_2.setBackground(new Color(4, 87, 87));
		panel_2.setBounds(390, 162, 173, 183);
		contentPanel.add(panel_2);

		JLabel lblIcoCent = new JLabel("");
		lblIcoCent.setIcon(new ImageIcon("recursos/dashcentros.png"));
		lblIcoCent.setBounds(22, 13, 128, 128);
		panel_2.add(lblIcoCent);

		lblEmpresas = new JLabel("Empresas");
		lblEmpresas.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmpresas.setForeground(Color.WHITE);
		lblEmpresas.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblEmpresas.setBounds(0, 144, 173, 39);
		panel_2.add(lblEmpresas);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new LineBorder(Color.WHITE, 2));
		panel_3.setBackground(new Color(4, 87, 87));
		panel_3.setBounds(575, 162, 173, 183);
		contentPanel.add(panel_3);

		JLabel lblIcoSol = new JLabel("");
		lblIcoSol.setIcon(new ImageIcon("recursos/dashsolicitud.png"));
		lblIcoSol.setBounds(22, 13, 128, 128);
		panel_3.add(lblIcoSol);

		lblSolicitudes = new JLabel("Solicitudes");
		lblSolicitudes.setHorizontalAlignment(SwingConstants.CENTER);
		lblSolicitudes.setForeground(Color.WHITE);
		lblSolicitudes.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblSolicitudes.setBounds(0, 144, 173, 39);
		panel_3.add(lblSolicitudes);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new LineBorder(Color.WHITE, 2));
		panel_4.setBackground(new Color(4, 87, 87));
		panel_4.setBounds(20, 377, 173, 183);
		contentPanel.add(panel_4);

		JLabel lblIcoContr = new JLabel("");
		lblIcoContr.setIcon(new ImageIcon("recursos/dashcontratados.png"));
		lblIcoContr.setBounds(22, 13, 128, 128);
		panel_4.add(lblIcoContr);

		lblContratados = new JLabel("Contratados");
		lblContratados.setHorizontalAlignment(SwingConstants.CENTER);
		lblContratados.setForeground(Color.WHITE);
		lblContratados.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblContratados.setBounds(0, 144, 173, 39);
		panel_4.add(lblContratados);

		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new LineBorder(Color.WHITE, 2));
		panel_5.setBackground(new Color(4, 87, 87));
		panel_5.setBounds(205, 377, 173, 183);
		contentPanel.add(panel_5);

		JLabel lblIcoExito = new JLabel("");
		lblIcoExito.setIcon(new ImageIcon("recursos/dashexito.png"));
		lblIcoExito.setBounds(22, 13, 128, 128);
		panel_5.add(lblIcoExito);

		lblDeExito = new JLabel("De Éxito");
		lblDeExito.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeExito.setForeground(Color.WHITE);
		lblDeExito.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblDeExito.setBounds(0, 144, 173, 39);
		panel_5.add(lblDeExito);

		JPanel panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBorder(new LineBorder(Color.WHITE, 2));
		panel_6.setBackground(new Color(4, 87, 87));
		panel_6.setBounds(390, 377, 173, 183);
		contentPanel.add(panel_6);

		JLabel lblIcoCover = new JLabel("");
		lblIcoCover.setIcon(new ImageIcon("recursos/dashcobertura.png"));
		lblIcoCover.setBounds(22, 13, 128, 128);
		panel_6.add(lblIcoCover);

		lblTasaCobertura = new JLabel("de Cobertura");
		lblTasaCobertura.setHorizontalAlignment(SwingConstants.CENTER);
		lblTasaCobertura.setForeground(Color.WHITE);
		lblTasaCobertura.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblTasaCobertura.setBounds(0, 144, 173, 39);
		panel_6.add(lblTasaCobertura);

		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBorder(new LineBorder(Color.WHITE, 2));
		panel_7.setBackground(new Color(4, 87, 87));
		panel_7.setBounds(575, 377, 173, 183);
		contentPanel.add(panel_7);

		JLabel lblIcoVacias = new JLabel("");
		lblIcoVacias.setIcon(new ImageIcon("recursos/dashofvacias.png"));
		lblIcoVacias.setBounds(22, 13, 128, 128);
		panel_7.add(lblIcoVacias);

		lblOfVacias = new JLabel("Ofertas vacías");
		lblOfVacias.setHorizontalAlignment(SwingConstants.CENTER);
		lblOfVacias.setForeground(Color.WHITE);
		lblOfVacias.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblOfVacias.setBounds(0, 144, 173, 39);
		panel_7.add(lblOfVacias);

		cargarValores();
	}

	private void cargarValores() {
		int cantCand = BolsaLaboral.getInstancia().getCandidatos().size();
		lblCandidatos.setText(formatNumero(cantCand) + (cantCand != 1 ? " Candidatos" : " Candidato"));

		int cantOfertas = BolsaLaboral.getInstancia().getOfertas().size();
		lblOfertas.setText(formatNumero(cantOfertas) + (cantOfertas != 1 ? " Ofertas" : " Oferta"));

		int cantCentros = BolsaLaboral.getInstancia().getCentros().size();
		lblEmpresas.setText(formatNumero(cantCentros) + (cantCentros != 1 ? " Empresas" : " Empresa"));

		int cantSolicitudes = BolsaLaboral.getInstancia().getSolicitudes().size();
		lblSolicitudes.setText(formatNumero(cantSolicitudes) + (cantSolicitudes != 1 ? " Solicitudes" : " Solicitud"));

		int cantVacCompletadas = BolsaLaboral.getInstancia().getVacantes().size();
		lblContratados.setText(formatNumero(cantVacCompletadas) + (cantVacCompletadas != 1 ? " Contratados" : " Contratado"));

		lblTasaCobertura.setText(BolsaLaboral.getInstancia().calcularTasaCovertura() + "% De Cobertura");

		if (cantSolicitudes > 0) {
			lblDeExito.setText((cantVacCompletadas / Math.max(cantSolicitudes, 1)) * 100 + "% De Éxito");
		} else {
			lblDeExito.setText("0% de Éxito");
		}

		int ofVacias = BolsaLaboral.getInstancia().obtenerOfertasVacias();
		lblOfVacias.setText(formatNumero(ofVacias) + (ofVacias != 1 ? " Ofertas Vacías" : " Oferta Vacía"));
	}

	private String formatNumero(int valor) {
		if (valor < 1000) {
			return String.valueOf(valor);
		} else if (valor < 1_000_000) {
			double ref = valor / 1000.0;
			return String.format("%.1fK", ref);
		} else {
			double ref = valor / 1_000_000.0;
			return String.format("%.1fM", ref);
		}
	}
}