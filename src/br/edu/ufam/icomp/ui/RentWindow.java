package br.edu.ufam.icomp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import br.edu.ufam.icomp.bd.CustomerDAO;
import br.edu.ufam.icomp.bd.RentalDAO;
import br.edu.ufam.icomp.model.Customer;
import br.edu.ufam.icomp.model.Employee;
import br.edu.ufam.icomp.model.Product;
import br.edu.ufam.icomp.model.Rental;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

public class RentWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private MainWindow parentWindow;
	
	/**
	 * Create the frame.
	 */
	public RentWindow() {
		initializeComponents();
	}
	
	public RentWindow(MainWindow parentWindow) {
		this();
		this.parentWindow = parentWindow;
	}
	
	private void btnNewRentClicked() {
		this.setEnabled(false);
		
		RegisterRentWindow window = new RegisterRentWindow(RentWindow.this);
		window.setLocationRelativeTo(RentWindow.this.getOwner());
		window.setVisible(true);
	}
	
	private void tableCellClicked() {
		int row = table.getSelectedRow();
		Rental rental = new Rental((int) table.getModel().getValueAt(row, 0),
				(Employee) table.getModel().getValueAt(row, 2) , (Customer) table.getModel().getValueAt(row, 1), 
				(Product) table.getModel().getValueAt(row, 3), table.getModel().getValueAt(row, 4).toString());
		
		this.setEnabled(false);
		
		RegisterRentWindow window = new RegisterRentWindow(RentWindow.this, rental);
		window.setLocationRelativeTo(RentWindow.this.getOwner());
		window.setVisible(true);
	}
	
	public void refreshTableData() {
		table.setModel(new RentalDAO().getTableModel());
	}
	
	private void initializeComponents() {
		setTitle("Alugu\u00E9is");
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
		table.setModel(new RentalDAO().getTableModel());
		table.removeColumn(table.getColumnModel().getColumn(0));
		table.getColumnModel().getColumn(0).setMinWidth(35);
		JScrollPane pane = new JScrollPane(table);
		contentPane.add(pane, "cell 0 0,grow");
		
		JButton btnNewRent = new JButton("Novo Aluguel");
		btnNewRent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewRentClicked();
			}
		});
		contentPane.add(btnNewRent, "cell 0 1");
		
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
