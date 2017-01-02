package br.edu.ufam.icomp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import br.edu.ufam.icomp.bd.CustomerDAO;
import br.edu.ufam.icomp.bd.EmployeeDAO;
import br.edu.ufam.icomp.model.Customer;
import br.edu.ufam.icomp.model.Employee;
import net.miginfocom.swing.MigLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EmployeeWindow extends JFrame {
	private JPanel contentPane;
	private JTable table;
	private MainWindow parentWindow;

	/**
	 * Create the frame.
	 */
	public EmployeeWindow() {
		initializeComponents();
	}
	
	public EmployeeWindow(MainWindow parentWindow) {
		this();
		this.parentWindow = parentWindow;
	}
	
	private void btnNewCustomerClicked() {
		this.setEnabled(false);
		
		RegisterEmployeeWindow window = new RegisterEmployeeWindow(EmployeeWindow.this);
		window.setLocationRelativeTo(EmployeeWindow.this.getOwner());
		window.setVisible(true);
	}
	
	private void tableCellClicked() {
		int row = table.getSelectedRow();
		Employee employee = new Employee((int) table.getModel().getValueAt(row, 0), 
				(String) table.getModel().getValueAt(row, 1));
		
		this.setEnabled(false);
		
		RegisterEmployeeWindow window = new RegisterEmployeeWindow(EmployeeWindow.this, employee);
		window.setLocationRelativeTo(EmployeeWindow.this.getOwner());
		window.setVisible(true);
	}
	
	public void refreshTableData() {
		table.setModel(new EmployeeDAO().getTableModel());
	}

	private void initializeComponents() {
		setTitle("Funcion\u00E1rios");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				tableCellClicked();
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new EmployeeDAO().getTableModel());
		table.getColumnModel().getColumn(0).setMinWidth(35);
		table.removeColumn(table.getColumnModel().getColumn(0));
		JScrollPane pane = new JScrollPane(table);
		contentPane.add(pane, "cell 0 0,grow");
		
		JButton btnNewCustomer = new JButton("Novo Cliente");
		btnNewCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewCustomerClicked();
			}
		});
		contentPane.add(btnNewCustomer, "cell 0 1");
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				onClosing();
			}
		});
	}
	
	private void onClosing() {
		this.parentWindow.frame.setEnabled(true);
	}
}
