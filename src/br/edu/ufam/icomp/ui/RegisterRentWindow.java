package br.edu.ufam.icomp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ufam.icomp.bd.CustomerDAO;
import br.edu.ufam.icomp.bd.EmployeeDAO;
import br.edu.ufam.icomp.bd.ProductDAO;
import br.edu.ufam.icomp.bd.RentalDAO;
import br.edu.ufam.icomp.model.Customer;
import br.edu.ufam.icomp.model.Employee;
import br.edu.ufam.icomp.model.Product;
import br.edu.ufam.icomp.model.Rental;
import br.edu.ufam.icomp.utils.Utils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterRentWindow extends JFrame {

	private JPanel contentPane;
	private RentWindow parentWindow;
	private Rental rental;
	
	private ArrayList<Product> products;
	private ArrayList<Customer> customers;
	private ArrayList<Employee> employees;
	
	private JComboBox cmbCustomer;
	private JComboBox cmbProduct;
	private JComboBox cmbEmployee;
	
	/**
	 * Create the frame.
	 */
	public RegisterRentWindow() {
		this.products = new ProductDAO().getAll(true);
		this.employees = new EmployeeDAO().getAll(false);
		this.customers = new CustomerDAO().getAll(false);
		this.rental = new Rental();
		
		initializeComponents();
	}
	
	public RegisterRentWindow(RentWindow parentWindow) {
		this();
		this.parentWindow = parentWindow;
	}
	
	public RegisterRentWindow(RentWindow parentWindow, Rental rental) {
		this(parentWindow);
		this.rental = rental;
	}
	
	private void onClosing() {
		this.parentWindow.setEnabled(true);
	}
	
	private void btnConfirmarClicked() {
		RentalDAO dao = new RentalDAO();
		this.rental.setCustomer((Customer)cmbCustomer.getSelectedItem());
		this.rental.setProduct((Product) cmbProduct.getSelectedItem());
		this.rental.setEmployeeRent((Employee) cmbEmployee.getSelectedItem());
		
		if(this.rental.getId() == -1)
			dao.createRental(rental);
		
		this.parentWindow.setEnabled(true);
		this.parentWindow.refreshTableData();
		RegisterRentWindow.this.dispose();
	}
		
	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 390, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][grow][][][][][][]", "[][][][][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("Cliente");
		contentPane.add(lblNewLabel, "cell 1 0,alignx trailing");
		
		
		
		cmbCustomer = new JComboBox();
		contentPane.add(cmbCustomer, "cell 2 0 4 1,growx");
		
		JLabel lblNomeDoFuncionrio = new JLabel("Funcion\u00E1rio");
		contentPane.add(lblNomeDoFuncionrio, "cell 1 1,alignx trailing");
		
		cmbEmployee = new JComboBox();
		contentPane.add(cmbEmployee, "cell 2 1 4 1,growx");
		
		JLabel lblProduto = new JLabel("T\u00EDtulo");
		contentPane.add(lblProduto, "cell 1 2,alignx trailing");
		
		cmbProduct = new JComboBox();
		contentPane.add(cmbProduct, "cell 2 2 4 1,growx");
		
		JLabel lblData = new JLabel("Data");
		lblData.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblData, "cell 1 3,alignx right");
		
		JLabel lblCurrentDate = new JLabel("");
		contentPane.add(lblCurrentDate, "cell 2 3");
		
		Box verticalBox = Box.createVerticalBox();
		contentPane.add(verticalBox, "cell 3 4 1 3");
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConfirmarClicked();
			}
		});
		contentPane.add(btnConfirmar, "cell 2 8 4 1,growx");
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				onClosing();
			}
		});
		
		Utils.fillComboBox(cmbEmployee, this.employees);
		Utils.fillComboBox(cmbProduct, this.products);
		Utils.fillComboBox(cmbCustomer, this.customers);
		
		lblCurrentDate.setText(new SimpleDateFormat("dd/MM/YYYY").format(new Date()));
	}

}
