package antiSpamFilter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

public class GUI extends JFrame {

	private JPanel contentPane;
	static final double fator=0.5;
	private static Font f =new Font("Century Gothic", Font.PLAIN,18);
	private static Font f2 =new Font("Century Gothic", Font.PLAIN,16);
	private JTextField info;
	private JTextField fp;
	private JTextField fn;
	private JTable table;
	private JScrollPane scroll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI janela = new GUI();
					janela.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		
//MENU
		
		JMenuBar menuBar = new JMenuBar();
		JMenu ficheiros = new JMenu("Ficheiros");
		JMenu guardar = new JMenu("Guardar configuração");
		JMenu historico = new JMenu("Histórico");

		ficheiros.setFont(f2);
		guardar.setFont(f2);
		historico.setFont(f2);
		
		menuBar.add(ficheiros);
		menuBar.add(guardar);
		menuBar.add(historico);
		
		setJMenuBar(menuBar);
		
//configuração da janela
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0,0,(int) (screenSize.width*fator),(int) (screenSize.height*fator));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][grow][grow][][grow]", "[100px][100px][][][250px][100px][][]"));
		setResizable(false);
		setLocationRelativeTo(null);
		
// Info e titulo da tabela
		
		info = new JTextField();
		info.setMinimumSize(new Dimension(400, 40));	
		info.setFont(f);
		contentPane.add(info, "cell 0 0 2 1,alignx center");
		info.setColumns(10);
		info.setEnabled(false);
		
		JLabel regras = new JLabel("Regras e pesos respetivos");
		contentPane.add(regras, "cell 0 1 2 1,alignx center");
		regras.setFont(f);
		
// Botões 
		
		JButton random = new JButton("Aleatório");
		random.setMinimumSize(new Dimension(140, 60));;	
		random.setFont(f);
		random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
//FP e FN
       		
		JLabel falsospositivos = new JLabel("Falsos Positivos");
		contentPane.add(falsospositivos, "cell 2 0 1 2,alignx center");
		falsospositivos.setFont(f);
		
		
		JLabel falsosnegativos = new JLabel("Falsos Negativos");
		contentPane.add(falsosnegativos, "cell 4 0 1 2,alignx center");
		falsosnegativos.setFont(f);
		
		fp = new JTextField();
		contentPane.add(fp, "cell 2 2,alignx center,aligny center");
		fp.setColumns(5);
		fp.setMinimumSize(new Dimension(75, 75));
		fp.setFont(f);
		fp.setEnabled(false);
		
		fn = new JTextField();
		contentPane.add(fn, "cell 4 2,alignx center");
		fn.setColumns(5);
		fn.setMinimumSize(new Dimension(75, 75));
		fn.setFont(f);
		fn.setEnabled(false);
		contentPane.add(random, "cell 2 4,alignx center");
		
//Tabela Regras e pesos associados
		
		Object[][] rules = new Object[][] {
            {"T_LOTS_OF_MONEY", 4 },
            {"HTML_IMAGE_RATIO_04", 2},
            {"NORMAL_HTTP_TO_IP",3},
            {"SUBJECT_NEEDS_ENCODING",1},
            {"LOW_PRICE",5},
            {"T_LOTS_OF_MONEY", 4 },
            {"HTML_IMAGE_RATIO_04", 2},
            {"NORMAL_HTTP_TO_IP",3},
            {"SUBJECT_NEEDS_ENCODING",1},
            {"LOW_PRICE",5},
            {"T_LOTS_OF_MONEY", 4 },
            {"HTML_IMAGE_RATIO_04", 2},
            {"NORMAL_HTTP_TO_IP",3},
            {"SUBJECT_NEEDS_ENCODING",1},
            {"LOW_PRICE",5},
        };
        String [] colunas = new String[] {"Regras" , "Peso"};
        
     	DefaultTableModel model = new DefaultTableModel(rules, colunas);  
     	table= new JTable(model);
		table.setCellSelectionEnabled(true);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.getTableHeader().setFont(f2);
		table.getColumnModel().getColumn(0).setPreferredWidth(500);
		table.getTableHeader().setBackground(Color.WHITE);
		table.setRowHeight(25);
		table.setFont(f2);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(renderer);
		scroll = new JScrollPane(table);
		contentPane.add(scroll, "cell 0 2 2 5,grow");
		
//Botao Algoritmo
		
		JButton algoritmo = new JButton("Algoritmo");
		algoritmo.setMinimumSize(new Dimension(140, 60));;
		algoritmo.setFont(f);
		contentPane.add(algoritmo, "cell 4 4,alignx center");

//Botao Iniciar
		
		JButton iniciar = new JButton("Iniciar");
		iniciar.setMinimumSize(new Dimension(140, 60));;
		iniciar.setFont(f);
		iniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(iniciar, "cell 2 5 3 1,alignx center");
		
	}

}
