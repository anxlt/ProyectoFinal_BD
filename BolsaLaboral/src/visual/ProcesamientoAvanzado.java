package visual;

import logico.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ProcesamientoAvanzado extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static JTable tablaOfertas;
	public static DefaultTableModel modeloOfertas = new DefaultTableModel() {
		@Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
		
		public Class getColumnClass(int column)
        {
            return getValueAt(0, column).getClass();
        }
	};
	public static Object[] rowOferta;
	
	private JButton btnProcesar;
	private JTextField txtFiltro;
	private static JTable tablaMatcheo;
	public static DefaultTableModel modeloMatcheo = new DefaultTableModel() {
		@Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
		
		public Class getColumnClass(int column)
        {
            return getValueAt(0, column).getClass();
        }
	};
	public static Object[] rowMatcheo;
	private ResultadoMatcheo resMatchSelec = null;
	
	private static ArrayList<OfertaLaboral> ofertas = new ArrayList<>();
	private static ArrayList<ResultadoMatcheo> resultados = new ArrayList<>();
	
	
	/**
	 * Create the dialog.
	 */
	public ProcesamientoAvanzado() {
		setTitle("Procesamiento Avanzado de Ofertas");
		setIconImage(Toolkit.getDefaultToolkit().getImage("recursos/icono.png"));
		setBounds(100, 100, 702, 659);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(new Color(228,228,228));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(228,228,228));
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(12, 13, 648, 215);
				panel.add(scrollPane);
				{
					tablaOfertas = new JTable();
					tablaOfertas.setForeground(Color.BLACK);
					tablaOfertas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
					String [] headers = {"Codigo", "Puesto", "Ofertador", "Area", "Estado"};
					modeloOfertas.setColumnIdentifiers(headers);
					tablaOfertas.setModel(modeloOfertas);
					scrollPane.setViewportView(tablaOfertas);
				}
			}
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 241, 648, 266);
			panel.add(scrollPane);
			
			tablaMatcheo = new JTable();
			tablaMatcheo.setForeground(Color.BLACK);
			tablaMatcheo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			tablaMatcheo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int index = tablaMatcheo.getSelectedRow();
					if(index >= 0) {
						resMatchSelec = BolsaLaboral.getInstancia().buscarResultado(resultados,tablaMatcheo.getValueAt(index,0).toString(),tablaMatcheo.getValueAt(index,1).toString());
						btnProcesar.setEnabled(true);
					}
				}
			});
			String [] headers = {"Oferta", "Codigo", "Nombre", "Porcentaje", "Condicion"};
			modeloMatcheo.setColumnIdentifiers(headers);
			tablaMatcheo.setModel(modeloMatcheo);
			scrollPane.setViewportView(tablaMatcheo);
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
				btnProcesar = new JButton("Procesar");
				btnProcesar.setBackground(Color.WHITE);
				btnProcesar.setIcon(new ImageIcon("recursos/vincular.png"));
				btnProcesar.setFont(new Font("Segoe UI", Font.BOLD, 16));
				btnProcesar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(BolsaLaboral.getInstancia().vincularOferta(resMatchSelec)) {
							JOptionPane.showMessageDialog(null,"Se ha creado la solicitud correctamente a la oferta " + resMatchSelec.getOferta().getPuesto() + ".","Informaci�n",JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null,"Esta solicitud ya existe.","Informaci�n",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				btnProcesar.setEnabled(false);
				buttonPane.add(btnProcesar);
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

		resultados = BolsaLaboral.getInstancia().procesamientoAvanzando();
		ofertas = BolsaLaboral.getInstancia().ofertasDisponibles();
		tablaOfertas.setRowHeight(36);
		tablaOfertas.getTableHeader().setReorderingAllowed(false);
		tablaMatcheo.setRowHeight(36);
		tablaMatcheo.getTableHeader().setReorderingAllowed(false);
		cargarResultados();
		cargarOfertas();
	}
	
	public void filtrar() {
	    String filtro = txtFiltro.getText().toLowerCase();
	    modeloOfertas.setRowCount(0);
	    rowOferta = new Object[tablaOfertas.getColumnCount()];
	    btnProcesar.setEnabled(false);

	    ArrayList<OfertaLaboral> ofertasVisibles = new ArrayList<>();
	    
	    for (OfertaLaboral aux : BolsaLaboral.getInstancia().getOfertas()) {
	        boolean coincide = 
	            aux.getCodigo().toLowerCase().contains(filtro) ||
	            aux.getOfertador().getNombre().toLowerCase().contains(filtro) ||
	            aux.getPuesto().toLowerCase().contains(filtro) ||
	            aux.getArea().toLowerCase().contains(filtro) ||
	            aux.getEstado().toLowerCase().contains(filtro);
	        
	        if (coincide) {
	            rowOferta[0] = aux.getCodigo();
	            rowOferta[1] = aux.getPuesto();
	            rowOferta[2] = aux.getOfertador().getNombre();
	            rowOferta[3] = new ImageIcon(getArea(aux.getArea()));
	            rowOferta[4] = aux.getEstado();
	            modeloOfertas.addRow(rowOferta);
	            ofertasVisibles.add(aux);
	        }
	    }
	    
	    actualizarResultadosFiltrados(ofertasVisibles);
	}

	private void actualizarResultadosFiltrados(ArrayList<OfertaLaboral> ofertasVisibles) {
	    modeloMatcheo.setRowCount(0);
	    rowMatcheo = new Object[tablaMatcheo.getColumnCount()];
	    
	    for (ResultadoMatcheo aux : resultados) {
	        if (ofertasVisibles.contains(aux.getOferta())) {
	            rowMatcheo[0] = aux.getOferta().getCodigo();
	            rowMatcheo[1] = aux.getSolicitante().getCodigo();
	            rowMatcheo[2] = aux.getSolicitante().getNombres() + " " + aux.getSolicitante().getApellidos();
	            rowMatcheo[3] = aux.getPorcentaje() + "%";
	            rowMatcheo[4] = new ImageIcon(getCondicion(aux.getCondicion()));
	            modeloMatcheo.addRow(rowMatcheo);
	        }
	    }
	}
	
	private static String getArea(String area) {
		area = area.replace("�","o");
		area = area.replace(" ","");
		return "recursos/" + area + ".png";
	}

	public static void cargarOfertas() {
		modeloOfertas.setRowCount(0);
		rowOferta = new Object[tablaOfertas.getColumnCount()];
		for (OfertaLaboral aux : ofertas) {
			rowOferta[0] = aux.getCodigo();
            rowOferta[1] = aux.getPuesto();
            rowOferta[2] = aux.getOfertador().getNombre();
            rowOferta[3] = new ImageIcon(getArea(aux.getArea()));
            rowOferta[4] = aux.getEstado();
			modeloOfertas.addRow(rowOferta);
		}
	}
	
	private static String getCondicion(String condicion) {
		condicion = condicion.toLowerCase();
		condicion = condicion.replace(" ","");
		return "recursos/" + condicion + ".png";
	}

	public static void cargarResultados() {
		modeloMatcheo.setRowCount(0);
		rowMatcheo = new Object[tablaMatcheo.getColumnCount()];
		for (ResultadoMatcheo aux : resultados) {
			rowMatcheo[0] = aux.getOferta().getCodigo();
            rowMatcheo[1] = aux.getSolicitante().getCodigo();
            rowMatcheo[2] = aux.getSolicitante().getNombres() + " " + aux.getSolicitante().getApellidos();
            rowMatcheo[3] = aux.getPorcentaje() + "%";
            rowMatcheo[4] = new ImageIcon(getCondicion(aux.getCondicion()));
			modeloMatcheo.addRow(rowMatcheo);
		}
	}
}
