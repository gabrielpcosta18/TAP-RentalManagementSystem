package br.edu.ufam.icomp.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import br.edu.ufam.icomp.bd.RentalDatabase;

import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		RentalDatabase db = new RentalDatabase();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		
		JMenu Cadastrar = new JMenu("Cadastrar");
		menuBar.add(Cadastrar);
		
		
		JMenuItem mntmCliente_1 = new JMenuItem("Cliente");
		mntmCliente_1.addMouseListener(new MouseAdapter() {		
			@Override
			public void mousePressed(MouseEvent e) {
				/*RegisterCustomerWindow window = new RegisterCustomerWindow();
				window.setLocationRelativeTo(MainWindow.this.frame);
				window.setVisible(true);*/
			}
		});
		Cadastrar.add(mntmCliente_1);
		
		JMenuItem mntmFuncionrio = new JMenuItem("Funcion\u00E1rio");
		mntmFuncionrio.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				RegisterEmployeeWindow window = new RegisterEmployeeWindow();
				window.setLocationRelativeTo(MainWindow.this.frame);
				window.setVisible(true);
			}
		});
		Cadastrar.add(mntmFuncionrio);
		
		JMenuItem mntmProduto = new JMenuItem("Produto");
		mntmProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				RegisterProductWindow window = new RegisterProductWindow();
				window.setLocationRelativeTo(MainWindow.this.frame); 
				window.setVisible(true);
			}
		});
		Cadastrar.add(mntmProduto);
		
		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);
		
		JMenuItem mntmProduto_1 = new JMenuItem("Produto");
		mntmProduto_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
		});
		mnEditar.add(mntmProduto_1);
		frame.getContentPane().setLayout(new MigLayout("", "[grow][][][grow]", "[grow][][grow]"));
		
		JButton btnCustomer = new JButton("Clientes");
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnCustomerClicked();
			}
		});
		frame.getContentPane().add(btnCustomer, "cell 0 0,grow");
		
		JButton btnEmployee = new JButton("Funcion\u00E1rios");
		btnEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEmployeeClicked();
			}
		});
		frame.getContentPane().add(btnEmployee, "cell 3 0,grow");
		
		JButton btnRent = new JButton("Alugu\u00E9is");
		frame.getContentPane().add(btnRent, "flowx,cell 0 2,grow");
		
		JButton btnProduct = new JButton("Produtos");
		btnProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnProductClicked();
			}
		});
		frame.getContentPane().add(btnProduct, "cell 3 2,grow");
	}
	
	private void btnProductClicked() {
		ProductWindow window = new ProductWindow();
		window.setLocationRelativeTo(MainWindow.this.frame);
		window.setVisible(true);
	}
	
	private void btnCustomerClicked() {
		CustomerWindow window = new CustomerWindow();
		window.setLocationRelativeTo(MainWindow.this.frame);
		window.setVisible(true);
	}
	
	private void btnEmployeeClicked() {
		EmployeeWindow window = new EmployeeWindow();
		window.setLocationRelativeTo(MainWindow.this.frame);
		window.setVisible(true);
	}

}
