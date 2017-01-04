package br.edu.ufam.icomp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ufam.icomp.bd.EmployeeDAO;
import br.edu.ufam.icomp.model.Employee;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegisterEmployeeWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private EmployeeWindow parentWindow;
	private Employee employee;

	/**
	 * Create the frame.
	 */
	public RegisterEmployeeWindow() {
		setTitle("Cadastro Funcion\u00E1rio");
		initializeComponents();
		this.employee = new Employee("");
	}
	
	public RegisterEmployeeWindow(EmployeeWindow parentWindow) {
		this();
		this.parentWindow = parentWindow;
	}
	
	public RegisterEmployeeWindow(EmployeeWindow parentWindow, Employee employee) {
		this(parentWindow);
		this.employee = employee;
		txtName.setText(this.employee.getName());
	}
	
	private boolean formValidate() {
		return (!this.txtName.getText().isEmpty());
	}
	
	private void btnConfirmarClicked() {
		if(formValidate()) {
			EmployeeDAO dao = new EmployeeDAO();
			this.employee.setName(txtName.getText());
			
			if(this.employee.getId() == -1) dao.registerEmployee(employee);
			else dao.updateEmployee(employee);
			
			this.parentWindow.setEnabled(true);
			this.parentWindow.refreshTableData();
			RegisterEmployeeWindow.this.dispose();
		}
		else JOptionPane.showMessageDialog(this, "Preencha todos os campos");
	}
	
	private void onClosing() {
		this.parentWindow.setEnabled(true);
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 155);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblNome = new JLabel("Nome");
		panel.add(lblNome);
		
		txtName = new JTextField();
		txtName.setHorizontalAlignment(SwingConstants.TRAILING);
		panel.add(txtName);
		txtName.setColumns(10);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConfirmarClicked();
			}
		});
		
		contentPane.add(btnConfirmar, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				onClosing();
			}
		});
		
	}
}
