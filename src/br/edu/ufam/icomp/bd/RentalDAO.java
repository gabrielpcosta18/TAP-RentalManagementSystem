package br.edu.ufam.icomp.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import br.edu.ufam.icomp.model.Product;
import br.edu.ufam.icomp.model.Rental;

public class RentalDAO extends RentalDatabase {
	private static String TABLE_NAME = "Rental";
	private static String[] COLUMNS = {"id", "customerId", "employeeRentId", 
			"employeeRestitutionId", "productId", "rentDate", "wasDeveloped"};
	
	public static String getTableName() {
		return TABLE_NAME;
	}
	
	@Override
	public DefaultTableModel getTableModel() {
		String query = getTableQuery();
		
		try {			
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			Vector<String> columnNames = new Vector<>();
			
			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();
			
			for(int i = 1; i <= columnCount; i++) 
				columnNames.add(meta.getColumnName(i));
			
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		    while (rs.next()) {
		        Vector<Object> vector = new Vector<Object>();
		        vector.add(rs.getObject(1));
		        vector.add(new CustomerDAO().getCustomerById(rs.getInt(2)));
		        vector.add(new EmployeeDAO().getEmployeeById(rs.getInt(3)));
		        vector.add(new ProductDAO().getProductById(rs.getInt(4)));
		        vector.add(rs.getObject(5));
		        vector.add((boolean) rs.getObject(6)? "Sim": "Não");
		        data.add(vector);
		    }
		    
		    DefaultTableModel model = new DefaultTableModel(data, columnNames) {
		    	@Override
	    	    public boolean isCellEditable(int row, int column) {
	    	       //all cells false
	    	       return false;
	    	    }
		    	
		    	@Override
	    	    public String getColumnName(int index) {
	    	       //all cells false
		    		String s = columnNames.get(index);
	    	        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	    	    }
		    };
		    
		    return model;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return null;		
	}
	
	@Override
	public String getTableQuery() {
		// TODO Auto-generated method stub
		String str = "select r.id as Código, r.customerId as Cliente, r.employeeRentId as Funcionario, " +
				"r.productId as Titulo, to_char(r.rentDate, \'DD/MM/YYYY\') as DataEmprestimo, r.wasDeveloped as Devolvido from " + 
				TABLE_NAME + " as r";
		/*return "select r.id as Código, c.name as NomeCliente, e.name as NomeFuncionario, " +
				"p.title as Titulo, r.rentDate as DataEmprestimo, r.wasDeveloped as Devolvido from " + 
				TABLE_NAME  + " as r INNER JOIN " + EmployeeDAO.getTableName() + " as e ON r.employeeRentId = e.id " + 
				" INNER JOIN " + CustomerDAO.getTableName() + " as c ON r.customerId = c.id" + 
				" INNER JOIN " + ProductDAO.getTableName() + " as p ON r.productId = p.id";*/
		return str;
	}
	
	public boolean createRental(Rental rental) {
		String createProductStatement = "INSERT INTO " + TABLE_NAME +
			" (customerId, employeeRentId, productId)" +
			" VALUES (?, ?, ?)";
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(createProductStatement);
			
			statement.setInt(1, rental.getCustomer().getId());
			statement.setInt(2, rental.getEmployeeRent().getId());
			statement.setInt(3, rental.getProduct().getId());
			
			statement.execute();
			connection.commit();
			return true;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean updateRental(Rental rental) {
		String updateRentalStatement = "UPDATE " + TABLE_NAME +
			" SET employeerestitutionid = ?, wasdeveloped = ? " +
			" WHERE id = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(updateRentalStatement);
			
			statement.setInt(1, rental.getEmployeeRestitution().getId());
			statement.setBoolean(2, rental.isWasDeveloped());
			
			statement.setInt(3, rental.getId());
			
			statement.execute();
			return true;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
