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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
import javax.swing.JFormattedTextField;

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
	private JLabel lblCurrentDate;
	private JButton btnConfirmar;
	private JLabel lblPrice;
	private JComboBox cmbEmployeeRestitution;
	private JLabel lblEmployeeRestitution;
	private JFormattedTextField txtPrice;
	private JLabel lblAdditionalTax;
	private JTextField txtAdditionalTax;
	private JLabel lblTotal;
	private JTextField txtTotalValue;
	
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
		
		this.prepareForRestitution();
	}
	
	private void prepareForRestitution() {
		Utils.fillComboBox(this.cmbEmployeeRestitution, employees);
		
		this.cmbCustomer.setSelectedItem(this.rental.getCustomer());
		this.cmbEmployee.setSelectedItem(this.rental.getEmployeeRent());
		this.cmbProduct.setSelectedItem(this.rental.getProduct());
		this.lblCurrentDate.setText(this.rental.getRentDate());
		this.cmbEmployeeRestitution.setSelectedItem(this.rental.getEmployeeRent());
		this.cmbEmployeeRestitution.setVisible(true);
		this.lblEmployeeRestitution.setVisible(true);
		
		this.cmbCustomer.setEnabled(false);
		this.cmbEmployee.setEnabled(false);
		this.cmbProduct.setEnabled(false);
		this.txtPrice.setVisible(true);
		this.lblPrice.setVisible(true);
		this.lblAdditionalTax.setVisible(true);
		this.txtAdditionalTax.setVisible(true);
		this.txtTotalValue.setVisible(true);
		this.lblTotal.setVisible(true);
		
		calcPayValue();
	}
	
	private void calcPayValue() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		float value = 0, additionalTax = 0;
		try {
			Date strDate = format.parse(this.rental.getRentDate());
			Date today = new Date();
			
			long diff = today.getTime() - strDate.getTime();
			diff = TimeUnit.MILLISECONDS.toDays(diff) - this.rental.getProduct().getMaxPeriodRent();
			
			value = this.rental.getProduct().getPrice();
			
			if(diff > 0) 
				additionalTax += value*Math.pow(1 + 0.01, diff);
			
			this.txtPrice.setText(Float.toString(this.rental.getProduct().getPrice()));
			this.txtAdditionalTax.setText(Float.toString(additionalTax));
			this.txtTotalValue.setText(Float.toString(value + additionalTax));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		else {
			this.rental.setEmployeeRestitution((Employee) cmbEmployeeRestitution.getSelectedItem());
			this.rental.setWasDeveloped(true);
			dao.updateRental(rental);
		}
		
		this.parentWindow.setEnabled(true);
		this.parentWindow.refreshTableData();
		RegisterRentWindow.this.dispose();
	}
		
	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 365, 349);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][grow][grow][][][]", "[][][][][][][][][][][][][][]"));
		
		
		JLabel lblNewLabel = new JLabel("Cliente");
		contentPane.add(lblNewLabel, "flowx,cell 2 0,alignx leading");
		
		lblCurrentDate = new JLabel("");
		contentPane.add(lblCurrentDate, "cell 4 0,alignx right");
		
		lblCurrentDate.setText(new SimpleDateFormat("dd/MM/YYYY").format(new Date()));
		
		
		
		
		cmbCustomer = new JComboBox();
		contentPane.add(cmbCustomer, "cell 2 1 3 1,growx");
		Utils.fillComboBox(cmbCustomer, this.customers);
		
		JLabel lblNomeDoFuncionrio = new JLabel("Funcion\u00E1rio");
		contentPane.add(lblNomeDoFuncionrio, "cell 2 2,alignx leading");
		
		cmbEmployee = new JComboBox();
		contentPane.add(cmbEmployee, "cell 2 3 3 1,growx");
		
		Utils.fillComboBox(cmbEmployee, this.employees);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				onClosing();
			}
		});
		
		JLabel lblProduto = new JLabel("T\u00EDtulo");
		contentPane.add(lblProduto, "cell 2 4,alignx leading");
		
		cmbProduct = new JComboBox();
		contentPane.add(cmbProduct, "cell 2 5 3 1,growx");
		Utils.fillComboBox(cmbProduct, this.products);
		
		lblEmployeeRestitution = new JLabel("Funcion\u00E1rio Receptor");
		contentPane.add(lblEmployeeRestitution, "cell 2 6 4 1");
		lblEmployeeRestitution.setVisible(false);
		
		cmbEmployeeRestitution = new JComboBox();
		contentPane.add(cmbEmployeeRestitution, "cell 2 7 3 1,growx");
		
		lblPrice = new JLabel("Pre\u00E7o");
		contentPane.add(lblPrice, "cell 2 8,alignx leading");
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConfirmarClicked();
			}
		});
		
		lblAdditionalTax = new JLabel("Juros");
		contentPane.add(lblAdditionalTax, "cell 3 8,alignx right");
		
		
		
		txtPrice = new JFormattedTextField();
		contentPane.add(txtPrice, "cell 2 9,grow");
		
		txtAdditionalTax = new JTextField();
		contentPane.add(txtAdditionalTax, "cell 3 9 2 1,alignx right");
		txtAdditionalTax.setColumns(10);
		
		lblTotal = new JLabel("Valor total a ser pago");
		contentPane.add(lblTotal, "cell 2 10 2 1");
		
		txtTotalValue = new JTextField();
		contentPane.add(txtTotalValue, "cell 2 11,grow");
		txtTotalValue.setColumns(10);
		contentPane.add(btnConfirmar, "cell 2 13 3 1,growx");
		setTitle("Aluguel de T\u00EDtulo");
		this.cmbEmployeeRestitution.setVisible(false);
		this.txtPrice.setVisible(false);
		this.txtPrice.setEnabled(false);
		this.lblPrice.setVisible(false);		
		this.lblAdditionalTax.setVisible(false);
		this.txtAdditionalTax.setVisible(false);
		this.txtAdditionalTax.setEnabled(false);
		this.lblTotal.setVisible(false);
		this.txtTotalValue.setVisible(false);
		this.txtTotalValue.setEnabled(false);
	}
}
