package br.edu.ufam.icomp.ui;

import java.awt.EventQueue;
import java.awt.Frame;

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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow {

	public JFrame frame;

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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Gerenciador de Loca\u00E7\u00F5es");
		frame.setBounds(100, 100, 515, 375);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new MigLayout("", "[grow][][][grow]", "[grow][][grow]"));
		
		JButton btnCustomer = new JButton("Clientes");
		btnCustomer.setFocusable(false);
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnCustomerClicked();
			}
		});
		frame.getContentPane().add(btnCustomer, "cell 0 0,grow");
		
		JButton btnEmployee = new JButton("Funcion\u00E1rios");
		btnEmployee.setFocusable(false);
		btnEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEmployeeClicked();
			}
		});
		frame.getContentPane().add(btnEmployee, "cell 3 0,grow");
		
		JButton btnRent = new JButton("Alugu\u00E9is");
		btnRent.setFocusable(false);
		btnRent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRentClicked();
			}
		});
		frame.getContentPane().add(btnRent, "flowx,cell 0 2,grow");
		
		JButton btnProduct = new JButton("Produtos");
		btnProduct.setFocusable(false);
		btnProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnProductClicked();
			}
		});
		frame.getContentPane().add(btnProduct, "cell 3 2,grow");
	}
	
	private void btnRentClicked() {
		RentWindow window = new RentWindow(this);
		openOtheWindow(window);
	}
	
	private void btnProductClicked() {
		ProductWindow window = new ProductWindow(this);
		openOtheWindow(window);
	}
	
	private void btnCustomerClicked() {
		CustomerWindow window = new CustomerWindow(this);
		openOtheWindow(window);
	}
	
	private void btnEmployeeClicked() {
		EmployeeWindow window = new EmployeeWindow(this);
		openOtheWindow(window);
	}
	
	private void openOtheWindow(Frame window) {
		this.frame.setEnabled(false);
		window.setLocationRelativeTo(this.frame);
		window.setVisible(true);
	}

}
