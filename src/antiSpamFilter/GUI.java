/*
 * 
 */
package antiSpamFilter;

import java.awt.Color;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

/**
 * The Class GUI.
 */
public class GUI {
	
	/** The controller. */
	private Controller controller = Controller.getInstance();
	
	/** The janela principal. */
	private JFrame janelaPrincipal;
	
	/** The painel. */
	private JPanel painel;
	
	/** The Constant fator. */
	static final double fator = 1;
	
	/** The font 1. */
	private static Font f = new Font("Century Gothic", Font.PLAIN, 18);
	
	/** The font 2. */
	private static Font f2 = new Font("Century Gothic", Font.PLAIN, 16);
	
	/** The font 3. */
	private static Font f3 = new Font("Century Gothic", Font.BOLD, 20);
	
	/** The info textfield. */
	private JTextField info;
	
	/** The false positives textfield. */
	private JTextField fp;
	
	/** The false negative textfield. */
	private JTextField fn;
	
	/** The table. */
	private JTable table;
	
	/** The model. */
	private DefaultTableModel model;
	
	/** The scroll. */
	private JScrollPane scroll;
	
	/** The file chooser. */
	final JFileChooser fc = new JFileChooser();
	
	/** The colunas. */
	private String[] colunas;
	
	/** The running boolean. */
	private boolean running = false;
	
	/** The cell text. */
	private String cellText;

	// FRAME

	/**
	 * Instantiates a new gui.
	 */
	public GUI() {
		// configuração da janela-------------------------------------------------
		janelaPrincipal = new JFrame("AntiSpammers");
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janelaPrincipal.setBounds(0, 0, (int) (960 * fator), (int) (540 * fator));
		painel = new JPanel();
		painel.setBorder(new EmptyBorder(5, 5, 5, 5));
		janelaPrincipal.setContentPane(painel);
		painel.setLayout(new MigLayout("", "[grow][grow][grow][][grow]", "[100px][100px][][][250px][100px][][]"));
		janelaPrincipal.setResizable(false);
		janelaPrincipal.setLocationRelativeTo(null);

		// MENU-------------------------------------------------------------------
		JMenuBar menuBar = new JMenuBar();
		JMenu ficheiros = new JMenu("Ficheiros");
		JMenu opcoes = new JMenu("Opções");
		JMenu historico = new JMenu("Histórico");

		ficheiros.setFont(f2);
		opcoes.setFont(f2);
		historico.setFont(f2);

		menuBar.add(opcoes);
		menuBar.add(ficheiros);
		menuBar.add(historico);

		JMenuItem rules_cf = new JMenuItem("Definir o caminho para o ficheiro rules.cf");
		JMenuItem ham_txt = new JMenuItem("Definir o caminho para o ficheiro ham.txt");
		JMenuItem spam_txt = new JMenuItem("Definir o caminho para o ficheiro spam.txt");
		JMenuItem guardar = new JMenuItem("Guardar configuração");
		JMenuItem abrir = new JMenuItem("Abrir configuração");

		rules_cf.setFont(f2);
		ham_txt.setFont(f2);
		spam_txt.setFont(f2);
		guardar.setFont(f2);
		abrir.setFont(f2);

		ficheiros.add(rules_cf);
		ficheiros.add(ham_txt);
		ficheiros.add(spam_txt);

		opcoes.add(abrir);
		opcoes.add(guardar);
		
		janelaPrincipal.setJMenuBar(menuBar);

		// Listener Menu-----------------------------------------------

		rules_cf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog dialog = new FileDialog(janelaPrincipal, "Selecione o caminho para o ficheiro rules.cf");
				dialog.setMode(FileDialog.LOAD);
				dialog.setFile("*.cf");
				dialog.setVisible(true);
				String file = dialog.getDirectory() + dialog.getFile();
				if (file != null) {
					addinfo("Caminho para o ficheiro rules.cf definido");
					controller.setRulesPath(file);
					controller.readRules("novoFicheiro");
					criarTabela();
					
				}
			}
		});

		ham_txt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog dialog = new FileDialog(janelaPrincipal, "Selecione o caminho para o ficheiro ham.txt");
				dialog.setMode(FileDialog.LOAD);
				dialog.setFile("*.txt");
				dialog.setVisible(true);
				String file = dialog.getDirectory() + dialog.getFile();
				System.out.println(dialog.getDirectory());
				if (file != null) {
					addinfo("Caminho para o ficheiro ham.txt definido");
					controller.setHamPath(file);
					controller.readHam();
				}

			}
		});

		spam_txt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog dialog = new FileDialog(janelaPrincipal, "Selecione o caminho para o ficheiro spam.txt");
				dialog.setMode(FileDialog.LOAD);
				dialog.setFile("*.txt");
				dialog.setVisible(true);
				String file = dialog.getDirectory() + dialog.getFile();
				if (file != null) {
					addinfo("Caminho para o ficheiro spam.txt definido");
					controller.setSpamPath(file);
					controller.readSpam();
				}
			}
		});
		
		abrir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog dialog = new FileDialog(janelaPrincipal, "Selecione o caminho para o ficheiro rules.cf");
				dialog.setMode(FileDialog.LOAD);
				dialog.setFile("*.cf");
				dialog.setVisible(true);
				String file = dialog.getDirectory() + dialog.getFile();
				if (file != null) {
					addinfo("Configuração carregada");
					controller.setRulesPath(file);
					controller.readRules("abrirFicheiro");
					setTable();
				}
			}
		});
		
		
		guardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (controller.ficheirosDef()) {
					controller.guardarPesos();
					addinfo("Ficheiro de pesos guardado");
					File ficheiro = new File(controller.getRulesPath()+"rules.cf");
					if (Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().open(ficheiro);
						} catch (IOException e1) {
							System.out.println("Erro na abertura do ficheiro");
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(janelaPrincipal, "Não é possível abrir o ficheiro gerado", "Erro",
								JOptionPane.ERROR_MESSAGE);

					}
				}
					else {
						JOptionPane.showMessageDialog(janelaPrincipal, "Tabela vazia", "Erro",
								JOptionPane.ERROR_MESSAGE);
					}
				
			}
		});
		// Info e titulo da tabela---------------------------------------

		info = new JTextField();
		info.setMinimumSize(new Dimension(400, 40));
		info.setFont(f.deriveFont(Font.BOLD));
		info.setForeground(Color.WHITE);
		painel.add(info, "cell 0 0 2 1,alignx center");
		info.setColumns(10);
		info.setEditable(false);
		info.setBackground(Color.BLACK);
		info.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel regras = new JLabel("Regras e pesos respetivos");
		painel.add(regras, "cell 0 1 2 1,alignx center");
		regras.setFont(f);

		// FP e FN--------------------------------------------------------

		JLabel falsospositivos = new JLabel("Falsos Positivos");
		painel.add(falsospositivos, "cell 2 0 1 2,alignx center");
		falsospositivos.setFont(f);

		JLabel falsosnegativos = new JLabel("Falsos Negativos");
		painel.add(falsosnegativos, "cell 4 0 1 2,alignx center");
		falsosnegativos.setFont(f);

		fp = new JTextField();
		painel.add(fp, "cell 2 2,alignx center,aligny center");
		fp.setColumns(5);
		fp.setMinimumSize(new Dimension(75, 75));
		fp.setFont(f3);
		fp.setEditable(false);
		fp.setBackground(Color.WHITE);
		fp.setHorizontalAlignment(SwingConstants.CENTER);

		fn = new JTextField();
		painel.add(fn, "cell 4 2,alignx center");
		fn.setColumns(5);
		fn.setMinimumSize(new Dimension(75, 75));
		fn.setFont(f3);
		fn.setEditable(false);
		fn.setBackground(Color.WHITE);
		fn.setHorizontalAlignment(SwingConstants.CENTER);

		//tryinit();
		// Tabela Regras e pesos associados-----------------------------------

		criarTabela();

		// Botao Algoritmo (Thread que corre o algoritmo
		// separadamente)------------------------

		JButton algoritmo = new JButton("Algoritmo");
		algoritmo.setMinimumSize(new Dimension(140, 60));
		;
		algoritmo.setFont(f);
		algoritmo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (controller.ficheirosDef()) {

					String[] args = {};

					new Thread(new Runnable() {
						public void run() {
							try {

								janelaPrincipal.setEnabled(false);
								running = true;
								addinfo("A gerar configuração ideal, aguarde...");
								new AntiSpamFilterAutomaticConfiguration();
								AntiSpamFilterAutomaticConfiguration.main(args);
								running = false;
								addinfo("Pesos ideais gerados");
								controller.setCount(0);
								controller.readNSGAII("AntiSpamFilterProblem.NSGAII.rf","AntiSpamFilterProblem.NSGAII.rs");
								setTable();
								janelaPrincipal.setEnabled(true);
								fp.setText(String.valueOf(controller.calcularFP()));
								fn.setText(String.valueOf(controller.calcularFN()));
								classificar("Leisure");
							} catch (IOException e1) {
								System.out.println("Erro na thread do algoritmo");
								e1.printStackTrace();
							}
						}
					}).start();
				} else {
					addinfo("Verifique os caminhos dos ficheiros");
				}

			}
		});
		painel.add(algoritmo, "cell 4 4,alignx center");

		// Botão Aleatorio ----------------------------------

		JButton random = new JButton("Aleatório");
		random.setMinimumSize(new Dimension(140, 60));
		;
		random.setFont(f);
		random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (controller.ficheirosDef()) {
					controller.pesosAleatorios();
					setTable();
					addinfo("Pesos aleatórios gerados");
					fp.setText(String.valueOf(controller.calcularFP()));
					fn.setText(String.valueOf(controller.calcularFN()));
					classificar("Leisure");
				} else {
					addinfo("Verifique os caminhos dos ficheiros");
				}
			}
		});

		painel.add(random, "cell 2 4,alignx center");

		// Botao Manual---------------------------------------

		JButton iniciar = new JButton("Manual");
		iniciar.setMinimumSize(new Dimension(140, 60));
		;
		iniciar.setFont(f);
		iniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (controller.ficheirosDef()) {
					fp.setText(String.valueOf(controller.calcularFP()));
					fn.setText(String.valueOf(controller.calcularFN()));
					classificar("Leisure");
					addinfo("Pesos manuais gerados");
				} else {
					addinfo("Verifique os caminhos dos ficheiros");
				}
			}

		});

		painel.add(iniciar, "cell 2 5 3 1,alignx center");

		janelaPrincipal.setVisible(true);

		addinfo("Bem Vindo");

	}

	// Definir os caminhos dos ficheiros por defeito na localização do
	/**
	 * Tryinit.
	 */
	// projeto----------------
	private void tryinit() {
		controller.setRulesPath("rules.cf");
		controller.readRules("novoFicheiro");
		// setTable();
		
		controller.setHamPath("ham.log.txt");
		controller.readHam();
		controller.setSpamPath("spam.log.txt");
		controller.readSpam();
	}

	// Definições da
	/**
	 * Criar tabela.
	 */
	// tabela-------------------------------------------------------------------
	private void criarTabela() {
		colunas = new String[] { "Regras", "Peso" };
		model = new DefaultTableModel(controller.getDadosTabela(), colunas) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0)
					return false;
				else
					return true;
			}
		};

		table = new JTable(model);
		table.getTableHeader().setFont(f2);
		table.getTableHeader().setBackground(Color.WHITE);
		table.getColumnModel().getColumn(0).setMinWidth(300);
		table.setRowHeight(25);
		table.setFont(f2);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(renderer);
		JTextField cell = new JTextField();
		DefaultCellEditor cellEditor = new DefaultCellEditor(cell);
		table.getColumnModel().getColumn(1).setCellEditor(cellEditor);
		cell.setBorder(new LineBorder(Color.BLACK));
		cell.setFont(f2);
		cell.setHorizontalAlignment(JTextField.CENTER);
		
		cell.addMouseListener(new MouseListener() {
			

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				cellText = cell.getText();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				table.getCellEditor().stopCellEditing();
				int row = table.getSelectedRow();
				boolean isNumeric = cell.getText().replace("-","").chars().allMatch(Character::isDigit);
				if ((isNumeric && Double.valueOf(cell.getText()) <= 5) && (Double.valueOf(cell.getText()) >= -5)) {
					controller.pesosManuais(row, cell.getText());
					System.out.println("editada " + row + " para " + cell.getText());

				} else {
					System.out.println(cellText);
					cell.setText(cellText);
					setTable();
					JOptionPane.showMessageDialog(janelaPrincipal, "Introduza um número entre -5 e 5 ", "Erro",
							JOptionPane.ERROR_MESSAGE);

				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				((JTextField) e.getSource()).selectAll();
				// row = table.getSelectedRow();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		cell.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(e.getKeyCode());
				if (e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_DOWN) {
					int row = table.getSelectedRow();
					boolean isNumeric = cell.getText().replace("-","").chars().allMatch(Character::isDigit);
					if ((isNumeric && Double.valueOf(cell.getText()) <= 5) && (Double.valueOf(cell.getText()) >= -5)) {
						controller.pesosManuais(row, cell.getText());
						System.out.println("editada " + row + " para " + cell.getText());

					} else {
					//	System.out.println(text);
						cell.setText(cellText);
						setTable();
						JOptionPane.showMessageDialog(janelaPrincipal, "Introduza um número entre -5 e 5 ", "Erro",
								JOptionPane.ERROR_MESSAGE);

					}
				}
				
			}
		});
		scroll = new JScrollPane(table);
		painel.add(scroll, "cell 0 2 2 5,grow");
	}

	/**
	 * Sets the table.
	 */
	public void setTable() {
		String[][] rules = controller.getDadosTabela();
		for (int i = 0; i < rules.length; i++) {
			model.setValueAt(rules[i][0], i, 0);
			model.setValueAt(rules[i][1], i, 1);
		}

	}

	// Janela INFO (Thread que altera a informação apresentada na janela
	// INFO-----------------

	/**
	 * Addinfo.
	 *
	 * @param s the s
	 */
	public void addinfo(String s) {
		new Thread(new Runnable() {

			public void run() {
				try {
					if (s.equals("Bem Vindo")) {
						info.setText("Bem Vindo");
						Thread.sleep(1500);
						if (!controller.ficheirosDef())
							info.setText("Defina os caminhos para os ficheiros");
					} else if (s.equals("A gerar configuração ideal, aguarde...")) {
						while (running) {
							info.setText("A gerar configuração ideal, aguarde  " + controller.getCount());
							Thread.sleep(500);
						}

					} else {
						info.setText(s);
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		// info.setText(s);
	}

	// Função de classificação da configuração gerada

	/**
	 * Classificar.
	 *
	 * @param tipo the tipo
	 */
	private void classificar(String tipo) {
		int total = controller.calcularFP() + controller.calcularFN();
		if (tipo.equals("Leisure")) {
			if (controller.calcularFP() < total * 0.20) {
				fp.setForeground(Color.RED);
				fn.setForeground(Color.RED);
			} else if (controller.calcularFP() < total * 0.7 && controller.calcularFP() > total * 0.2) {
				fp.setForeground(Color.BLUE);
				fn.setForeground(Color.BLUE);
			} else {
				fp.setForeground(Color.GREEN);
				fn.setForeground(Color.GREEN);
			}
		}
	}

}
