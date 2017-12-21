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

import antiSpamFilterConfigurations.AntiSpamFilterAutomaticConfiguration;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
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
 * A Classe GUI.
 */
public class GUI {

	/** O controller. */
	private Controller controller = Controller.getInstance();

	/** A janela principal. */
	private JFrame janelaPrincipal;

	/** O painel. */
	private JPanel painel;

	/** A constante fator de tamanho da GUI. */
	static final double fator = 1;

	/** Fonte 1. */
	private static Font f = new Font("Century Gothic", Font.PLAIN, 18);

	/** Fonte 2. */
	private static Font f2 = new Font("Century Gothic", Font.PLAIN, 16);

	/** Fonte 3. */
	private static Font f3 = new Font("Century Gothic", Font.BOLD, 20);

	/** A janela de info. */
	private JTextField info;

	/** A janela indicadora de Falsos Positivos. */
	private JTextField fp;

	/** A janela indicadora de Falsos Negativos. */
	private JTextField fn;

	/** Tabela de regras. */
	private JTable table;

	/** Modelo da tabela de regras. */
	private DefaultTableModel model;

	/** Scroll da tabela */
	private JScrollPane scroll;

	/** File Chooser */
	final JFileChooser fc = new JFileChooser();

	/** Vetor de colunas */
	private String[] colunas;

	/** Boolean para estado do algoritmo */
	private boolean running = false;

	/** Texto de uma celula */
	private String cellText;

	/** Menu de escolha do ficheiro rules.cf */
	private JMenuItem rules_cf;
	
	/** Menu de escolha do ficheiro ham log. */
	private JMenuItem ham_log;
	
	/** Menu de escolha do ficheiro spam log. */
	private JMenuItem spam_log;
	
	/** Menu para guardar a configuração atual. */
	private JMenuItem guardar;
	
	/** Menu para abrir uma configuração criada anteriormente abrir. */
	private JMenuItem abrir;
	
	/** Menu para limpar a tabela de pesos */
	private JMenuItem limpar;

	/** Botão algoritmo. */
	private JButton algoritmo;

	/** Botão random. */
	private JButton random;

	/** Botão manual manualmente. */
	private JButton manual;

	// FRAME

	/**
	 * Instanciamento da gui.
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
		Color c = new Color(254, 254, 254, 100);
		painel.setBackground(c);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// MENU-------------------------------------------------------------------
		JMenuBar menuBar = new JMenuBar();
		JMenu ficheiros = new JMenu("Ficheiros");
		JMenu opcoes = new JMenu("Opções");

		ficheiros.setFont(f2);
		opcoes.setFont(f2);

		menuBar.add(opcoes);
		menuBar.add(ficheiros);

		rules_cf = new JMenuItem("Caminho para o ficheiro rules.cf");
		ham_log = new JMenuItem("Caminho para o ficheiro ham.log");
		spam_log = new JMenuItem("Caminho para o ficheiro spam.log");
		guardar = new JMenuItem("Guardar configuração");
		abrir = new JMenuItem("Abrir configuração");
		limpar = new JMenuItem("Limpar configuração");

		rules_cf.setFont(f2);
		ham_log.setFont(f2);
		spam_log.setFont(f2);
		guardar.setFont(f2);
		abrir.setFont(f2);
		limpar.setFont(f2);

		ficheiros.add(rules_cf);
		ficheiros.add(ham_log);
		ficheiros.add(spam_log);

		opcoes.add(abrir);
		opcoes.add(guardar);
		opcoes.add(limpar);

		janelaPrincipal.setJMenuBar(menuBar);

		// Listener Menu-----------------------------------------------

		rules_cf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog dialog = new FileDialog(janelaPrincipal, "Caminho para o ficheiro rules.cf");
				dialog.setMode(FileDialog.LOAD);
				dialog.setFile("*.cf");
				dialog.setVisible(true);
				dialog.setFile("rules.cf");
				String file = dialog.getDirectory() + dialog.getFile();
				if (dialog.getFile() != null) {
					if (!dialog.getFile().contains("rules")) {
						JOptionPane.showMessageDialog(janelaPrincipal,
								"Certifique-se de que o ficheiro selecionado contem as regras", "Erro",
								JOptionPane.ERROR_MESSAGE);
					} else {
						addinfo("Caminho para o ficheiro rules.cf definido");
						controller.setRulesPath(file);
						controller.clearRules();
						controller.readRules("novoFicheiro");
						painel.remove(scroll);
						criarTabela();
						janelaPrincipal.revalidate();
						janelaPrincipal.repaint();
					}
				}
			}
		});

		ham_log.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (controller.isRulesdef()) {
					FileDialog dialog = new FileDialog(janelaPrincipal, "Caminho para o ficheiro ham.log");
					dialog.setMode(FileDialog.LOAD);
					dialog.setFile("*.log*");
					dialog.setVisible(true);
					String file = dialog.getDirectory() + dialog.getFile();
					if (dialog.getFile() != null) {
						if (!dialog.getFile().contains("ham")) {
							JOptionPane.showMessageDialog(janelaPrincipal,
									"Certifique-se de que o ficheiro selecionado contem os emails de ham", "Erro",
									JOptionPane.ERROR_MESSAGE);
						} else {
							addinfo("Caminho para o ficheiro ham.log definido");
							controller.setHamPath(file);
							controller.readHam();
						}
					}
				} else {
					JOptionPane.showMessageDialog(janelaPrincipal, "Comece por selecionar o ficheiro rules.cf", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		spam_log.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (controller.isRulesdef()) {
					FileDialog dialog = new FileDialog(janelaPrincipal, "Caminho para o ficheiro spam.log");
					dialog.setMode(FileDialog.LOAD);
					dialog.setFile("*.log*");
					dialog.setVisible(true);
					String file = dialog.getDirectory() + dialog.getFile();
					if (dialog.getFile() != null) {
						if (!dialog.getFile().contains("spam")) {
							JOptionPane.showMessageDialog(janelaPrincipal,
									"Certifique-se de que o ficheiro selecionado contem os emails de spam", "Erro",
									JOptionPane.ERROR_MESSAGE);
						} else {
							addinfo("Caminho para o ficheiro spam.log definido");
							controller.setSpamPath(file);
							controller.readSpam();
						}
					}
				} else {
					JOptionPane.showMessageDialog(janelaPrincipal, "Comece por selecionar o ficheiro rules.cf", "Erro",
							JOptionPane.ERROR_MESSAGE);
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
				if (dialog.getFile() != null) {
					addinfo("Configuração carregada");
					controller.setRulesPath(file);
					System.out.println(file);
					controller.clearRules();
					controller.readRules("abrirFicheiro");
					setTable();
					painel.remove(scroll);
					criarTabela();
					janelaPrincipal.revalidate();
					janelaPrincipal.repaint();
				}
			}
		});

		guardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (controller.ficheirosDef()) {
					controller.guardarPesos();
					addinfo("Ficheiro de pesos guardado");
					File ficheiro = new File(controller.getRulesPath());
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
				} else {
					JOptionPane.showMessageDialog(janelaPrincipal, "Tabela vazia", "Erro", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		limpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (controller.ficheirosDef()) {
					controller.readRules("novoFicheiro");
					setTable();
					addinfo("Configuração limpa");
				} else {

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

		// tryinit();
		// Tabela Regras e pesos associados-----------------------------------

		criarTabela();

		// Botao Algoritmo (Thread que corre o algoritmo
		// separadamente)------------------------

		algoritmo = new JButton("Algoritmo");
		algoritmo.setMinimumSize(new Dimension(140, 60));
		;
		algoritmo.setFont(f);
		algoritmo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (controller.ficheirosDef()) {

					String[] args = {""};

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
								controller.readNSGAII("AntiSpamFilterProblem.NSGAII.rf",
										"AntiSpamFilterProblem.NSGAII.rs");
								setTable();
								janelaPrincipal.setEnabled(true);
								fp.setText(String.valueOf(controller.calcularFP()));
								fn.setText(String.valueOf(controller.calcularFN()));
								classificar();
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

		random = new JButton("Aleatório");
		random.setMinimumSize(new Dimension(140, 60));
		random.setFont(f);
		random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gerarficheiros();
				if (controller.ficheirosDef()) {
					controller.pesosAleatorios();
					setTable();
					addinfo("Pesos aleatórios gerados");
					fp.setText(String.valueOf(controller.calcularFP()));
					fn.setText(String.valueOf(controller.calcularFN()));
					classificar();
				} else {
					addinfo("Verifique os caminhos dos ficheiros");
				}
			}
		});

		painel.add(random, "cell 2 4,alignx center");

		// Botao Manual---------------------------------------

		manual = new JButton("Manual");
		manual.setMinimumSize(new Dimension(140, 60));
		;
		manual.setFont(f);
		manual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (controller.ficheirosDef()) {
					fp.setText(String.valueOf(controller.calcularFP()));
					fn.setText(String.valueOf(controller.calcularFN()));
					classificar();
					addinfo("Pesos manuais gerados");
				} else {
					addinfo("Verifique os caminhos dos ficheiros");
				}
			}

		});

		painel.add(manual, "cell 2 5 3 1,alignx center");

		janelaPrincipal.setVisible(true);

		addinfo("Bem Vindo");

	}
	
	

	/**
	 * Criar tabela.
	 */
	// tabela-------------------------------------------------------------------
	public void criarTabela() {
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
		table.getTableHeader().setBackground(Color.gray);
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
				boolean isNumeric = cell.getText().replace("-", "").chars().allMatch(Character::isDigit);
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
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
					int row = table.getSelectedRow();
					boolean isNumeric = cell.getText().replace("-", "").chars().allMatch(Character::isDigit);
					if ((isNumeric && Double.valueOf(cell.getText()) <= 5) && (Double.valueOf(cell.getText()) >= -5)) {
						controller.pesosManuais(row, cell.getText());
						System.out.println("editada " + row + " para " + cell.getText());

					} else {
						// System.out.println(text);
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
	 * Atualizar a tabela.
	 */
	public void setTable() {
		String[][] rules = controller.getDadosTabela();
		if (model.getRowCount() != rules.length) {
			model = new DefaultTableModel(controller.getDadosTabela(), colunas) {

				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					if (columnIndex == 0)
						return false;
					else
						return true;
				}
			};
		}
		for (int i = 0; i < rules.length; i++) {
			model.setValueAt(rules[i][0], i, 0);
			model.setValueAt(rules[i][1], i, 1);
		}

	}

	// Janela INFO (Thread que altera a informação apresentada na janela
	// INFO-----------------

	/**
	 * Adicionar informação á janela info.
	 *
	 * @param s , sendo a String a mostrar
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
	 */
	public void classificar() {

		int total = controller.calcularFP() + controller.calcularFN();
			if (controller.calcularFP() < total * 0.20) {
				fp.setForeground(Color.RED);
				fn.setForeground(Color.RED);
			} else {
				fp.setForeground(Color.GREEN);
				fn.setForeground(Color.GREEN);
			}
	}
	
	/**
	 * Devolve o botão do menu rules_cf.
	 *
	 * @return the rules cf
	 */
	public JMenuItem getRules_cf() {
		return rules_cf;
	}
	
	/**
	 * Devolve o ham log JMenuItem.
	 *
	 * @return the ham log 
	 */
	public JMenuItem getHam_log() {
		return ham_log;
	}
	
	/**
	 * Devolve o  spam log  JMenuItem.
	 *
	 * @return the spam log
	 */
	public JMenuItem getSpam_log() {
		return spam_log;
	}
	
	/**
	 * Devolve o  guardar JMenuItem.
	 *
	 * @return the guardar
	 */
	public JMenuItem getGuardar() {
		return guardar;
	}
	
	/**
	 * Devolve o  abrir JMenuItem.
	 *
	 * @return the abrir
	 */
	public JMenuItem getAbrir() {
		return abrir;
	}
	
	/**
	 * Devolve o  limpar JMenuItem.
	 *
	 * @return the limpar
	 */
	public JMenuItem getLimpar() {
		return limpar;
	}
	
	/**
	 * Devolve o botão algoritmo.
	 *
	 * @return the algoritmo
	 */
	public JButton getAlgoritmo() {
		return algoritmo;
	}
	
	/**
	 * Devolve o botão random.
	 *
	 * @return the random
	 */
	public JButton getRandom() {
		return random;
	}
	
	/**
	 * Devolve o botão manual manual.
	 *
	 * @return the manual
	 */
	public JButton getmanual() {
		return manual;
	}
	/**
	 * Gera os ficheiros Latex e gráfico
	 *
	 */
	public void gerarficheiros() {
		try {
			String[] params = new String[2];
			params[0]="C:\\Program Files\\R\\R-3.4.3\\bin\\x64\\Rscript.exe";
			params[1]="C:\\Users\\bruno\\git\\ES1-2017-METIA1-47\\experimentBaseDirectory\\AntiSpamStudy\\R\\HV.Boxplot.R";
			
			String [] envp = new String[1];
			envp[0] = "Path=C:\\Program Files\\R\\R-3.4.3\\bin\\x64";
			
			Process process = Runtime.getRuntime().exec(params,envp,new File("C:\\Users\\bruno\\git\\ES1-2017-METIA1-47\\experimentBaseDirectory\\AntiSpamStudy\\R"));
		
			
			System.out.println("Ficheiros criados criados");
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		try {
			String[] params = new String[2];
			params[0]="C:\\Program Files\\MikTeX 2.9\\miktex\\bin\\x64\\pdflatex.exe";
			params[1]="C:\\Users\\bruno\\git\\ES1-2017-METIA1-47\\experimentBaseDirectory\\AntiSpamStudy\\latex\\AntiSpamStudy.tex";
			
			String [] envp = new String[1];
			envp[0] = "Path=C:\\Program Files\\MiKTeX 2.9\\miktex\\bin\\x64";
			
			Process process = Runtime.getRuntime().exec(params,envp,new File("C:\\Users\\bruno\\git\\ES1-2017-METIA1-47\\experimentBaseDirectory\\AntiSpamStudy\\latex"));
		
			
			System.out.println("Ficheiros criados criados");
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
