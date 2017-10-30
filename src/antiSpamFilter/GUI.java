package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GUI {
	
	public static void main(String[] args) {
		JFrame GUI = new JFrame("Anti-Spamers");
		
		
		//Menu
		JPanel GUI_1 = new JPanel();
		
		JButton ficheiros = new JButton("Ficheiros");
		JButton Guardar = new JButton("Guardar");
		JButton Histórico = new JButton("Histórico");
		GUI_1.add(ficheiros);
		GUI_1.add(Guardar);
		GUI_1.add(Histórico);
		
		//Info FP FN
		JPanel GUI_2= new JPanel();

		JTextArea Info = new JTextArea(10,10);
		Info.setText("Informação");
		JLabel Fpositivos= new JLabel("                       FP");
		JLabel Fnegativos= new JLabel("                       FN");
		JTextField FP = new JTextField(5);
		JTextField FN = new JTextField(5);
		
		GUI_2.add(Info);
		GUI_2.add(Fpositivos);
		GUI_2.add(FP);
		GUI_2.add(Fnegativos);
		GUI_2.add(FN);
		
		
		//Tabela Regras e pesos associados
		JPanel GUI_3 = new JPanel();
		JPanel GUI_4 = new JPanel();
		
		GUI_3.setLayout(new BorderLayout());
		GUI_4.setLayout(new GridLayout(1,3));
		
		//assumi que existem 5 regras
		
		 Object[][] rules = new Object[][] {
	            {"T_LOTS_OF_MONEY", 4 },
	            {"HTML_IMAGE_RATIO_04", 2},
	            {"NORMAL_HTTP_TO_IP",3},
	            {"SUBJECT_NEEDS_ENCODING",1},
	            {"LOW_PRICE",5},
	        };
	     String [] colunas = new String[] {"Regras" , "Peso"};
	     
	    DefaultTableModel model = new DefaultTableModel(rules, colunas);  
		JTable regras= new JTable(model);
		regras.add(new JScrollPane());
		 
		//grafico
		JTextArea grafico = new JTextArea(5,5);
		grafico.setText("Gráfico");
		
		//BOTÕES
		JButton aleatório = new JButton("Aleatório");
		JButton nsga = new JButton("NSGA II");
		
		GUI_3.add(regras, BorderLayout.WEST);
		
		
		GUI_4.add(grafico);
		GUI_4.add(aleatório);
		GUI_4.add(nsga);
		
		GUI_3.add(GUI_4, BorderLayout.EAST);
		
		GUI_1.add(GUI_2);
		GUI_1.add(GUI_3);
		GUI.add(GUI_1);
		
		//configuração da janela
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.pack();
		GUI.setSize(500,400);
		GUI.setVisible(true);

		
	}

}
