package br.edu.ufam.icomp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ufam.icomp.bd.CustomerDAO;
import br.edu.ufam.icomp.model.Customer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegisterCustomerWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private CustomerWindow parentWindow;
	
	private Customer customer;

	/**
	 * Create the frame.
	 */
	public RegisterCustomerWindow() {
		initializeComponents();
	}
	
	public RegisterCustomerWindow(CustomerWindow parentWindow) {
		this.customer = new Customer("");
		this.parentWindow = parentWindow;
		initializeComponents();
	}
	
	public RegisterCustomerWindow(CustomerWindow parentWindow, Customer customer) {
		this(parentWindow);
		this.customer = customer;
		this.txtNome.setText(this.customer.getName());
	}
	
	private void onClosing() {
		this.parentWindow.setEnabled(true);
	}
	
	private boolean formValidate() {
		return (!this.txtNome.getText().isEmpty());
	}
	
	private void btnOkClicked() {
		if(formValidate()) {
			CustomerDAO dao = new CustomerDAO();
			this.customer.setName(txtNome.getText());
			
			if(this.customer.getId() == -1) 
				dao.registerCustomer(customer);
			else dao.updateCustomer(customer);
			
			this.parentWindow.setEnabled(true);
			this.parentWindow.refreshTableData();
			RegisterCustomerWindow.this.dispose();
		}
		else JOptionPane.showMessageDialog(this, "Preencha todos os campos");
	}
	
	private void initializeComponents() {
		setTitle("Cadastro de Cliente");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 155);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		
		
		JLabel lblNome = new JLabel("Nome");
		panel_1.add(lblNome);
		
		
		txtNome = new JTextField();
		panel_1.add(txtNome);
		txtNome.setColumns(20);
		
		JButton btnOk = new JButton("Confirmar");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnOkClicked();
			}
		});
		btnOk.setVerticalAlignment(SwingConstants.BOTTOM);
		panel.add(btnOk, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				onClosing();
			}
		});
	}
}
