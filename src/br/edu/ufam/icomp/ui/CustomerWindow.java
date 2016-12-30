package br.edu.ufam.icomp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import br.edu.ufam.icomp.bd.CustomerDAO;
import br.edu.ufam.icomp.model.Customer;

import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public CustomerWindow() {
		initializeComponents();
	}
		
	private void btnNewCustomerClicked() {
		this.setEnabled(false);
		
		RegisterCustomerWindow window = new RegisterCustomerWindow(CustomerWindow.this);
		window.setLocationRelativeTo(CustomerWindow.this.getOwner());
		window.setVisible(true);
	}
	
	private void tableCellClicked() {
		int row = table.getSelectedRow();
		Customer customer = new Customer((int) table.getValueAt(row, 0), 
				(String) table.getValueAt(row, 1));
		
		this.setEnabled(false);
		
		RegisterCustomerWindow window = new RegisterCustomerWindow(CustomerWindow.this, customer);
		window.setLocationRelativeTo(CustomerWindow.this.getOwner());
		window.setVisible(true);
	}
	
	public void refreshTableData() {
		table.setModel(new CustomerDAO().getTableModel());
	}
	
	private void initializeComponents() {
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
		table.setModel(new CustomerDAO().getTableModel());
		table.getColumnModel().getColumn(0).setMinWidth(35);
		JScrollPane pane = new JScrollPane(table);
		contentPane.add(pane, "cell 0 0,grow");
		
		JButton btnNewCustomer = new JButton("Novo Cliente");
		btnNewCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewCustomerClicked();
			}
		});
		contentPane.add(btnNewCustomer, "cell 0 1");
	}

}
