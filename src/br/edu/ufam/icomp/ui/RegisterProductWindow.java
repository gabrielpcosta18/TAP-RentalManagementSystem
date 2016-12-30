package br.edu.ufam.icomp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import br.edu.ufam.icomp.bd.ProductDAO;
import br.edu.ufam.icomp.model.Product;

import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JSpinner;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegisterProductWindow extends JFrame {
	private JTextField txtTitle;
	private JTextField txtType;
	private JTextArea txtDescription;
	private JSpinner spinMaxRentPeriod;
	private JSpinner spinTotalInStock;
	private JFormattedTextField txtRentValue;
	private Product product;
	private ProductWindow parentWindow;
	
	/**
	 * Create the frame.
	 */
	public RegisterProductWindow() {
		initializeComponents();
		this.product = new Product();
	}
	
	public RegisterProductWindow(ProductWindow parentWindow) {
		this();
		this.parentWindow = parentWindow;
	}
	
	public RegisterProductWindow(ProductWindow parentWindow, Product product) {
		this(parentWindow);
		this.product = product;
		
		txtDescription.setText(product.getDescription());
		txtRentValue.setText(Float.toString(product.getPrice()));
		txtTitle.setText(product.getTitle());
		txtType.setText(product.getType());
		spinMaxRentPeriod.setValue(product.getMaxPeriodRent());
		spinTotalInStock.setValue(product.getTotalInStock());
	}
	
	private void btnOkClicked() {
		ProductDAO dao = new ProductDAO();
		this.product.setTitle(txtTitle.getText());
		this.product.setDescription(txtDescription.getText());
		this.product.setType(txtType.getText());
		this.product.setTotalInStock((int) spinTotalInStock.getValue());
		this.product.setMaxPeriodRent((int) spinMaxRentPeriod.getValue());
		this.product.setPrice(Float.parseFloat(txtRentValue.getText().substring(1)));
		
		if(this.product.getId() == -1)
			dao.createProduct(product);
		else dao.updateProduct(product);
		
		this.parentWindow.setEnabled(true);
		this.parentWindow.refreshTableData();
		RegisterProductWindow.this.dispose();
	}
	
	private void onClosing() {
		this.parentWindow.setEnabled(true);
	}
	
	private void initializeComponents() {
		setResizable(false);
		setTitle("Cadastro de T\u00EDtulo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 300);
		getContentPane().setLayout(new MigLayout("", "[][grow][][grow][]", "[][][][][][][][grow][][]"));
		
		JLabel lblTtulo = new JLabel("T\u00EDtulo");
		getContentPane().add(lblTtulo, "flowx,cell 1 0");
		
		txtTitle = new JTextField();
		getContentPane().add(txtTitle, "cell 1 1,growx");
		txtTitle.setColumns(10);
		
		JLabel lblTipo = new JLabel("Tipo");
		getContentPane().add(lblTipo, "cell 1 2");
		
		JLabel lblPreo = new JLabel("Valor do Aluguel");
		getContentPane().add(lblPreo, "cell 3 2");
		lblTipo.setLabelFor(txtType);
		
		txtType = new JTextField();
		getContentPane().add(txtType, "cell 1 3,growx");
		txtType.setColumns(10);
		
		
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);

		
		
		txtRentValue = new JFormattedTextField(format);
		txtRentValue.setHorizontalAlignment(SwingConstants.TRAILING);
		txtRentValue.setValue(2.2);
		lblPreo.setLabelFor(txtRentValue);
		getContentPane().add(txtRentValue, "cell 3 3,growx");
		
		JLabel lblPerodoMximoDe = new JLabel("Quantidade M\u00E1xima de Dias de Aluguel");
		lblPerodoMximoDe.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblPerodoMximoDe, "cell 1 4");
		
		JLabel lblTotalEmEstoque = new JLabel("Total em estoque");
		getContentPane().add(lblTotalEmEstoque, "cell 3 4");
		
		
		spinMaxRentPeriod = new JSpinner();
		getContentPane().add(spinMaxRentPeriod, "cell 1 5,growx");
		
		spinTotalInStock = new JSpinner();
		getContentPane().add(spinTotalInStock, "cell 3 5,growx");
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		getContentPane().add(lblDescrio, "cell 1 6");
		
		
		
		txtDescription = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(txtDescription);
		getContentPane().add(scrollPane, "cell 1 7 3 1,grow");
		
		JButton btnOk = new JButton("Confirmar");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnOkClicked();
			}
		});
		getContentPane().add(btnOk, "cell 1 9 3 1,alignx center");
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				onClosing();
			}
		});
		
	}
}
