package br.edu.ufam.icomp.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.ufam.icomp.model.Customer;
import br.edu.ufam.icomp.model.Employee;
public class CustomerDAO extends RentalDatabase {
	private static String TABLE_NAME = "Customer";	
	private static String[] columns = {"id", "name"};
	
	public static String getTableName() {
		return TABLE_NAME;
	}
	
	private static Customer getCustomerFromResultSet(ResultSet rs) {
		try {
			return new Customer(
					rs.getInt(1),
					rs.getString(2));
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	public static Customer getCustomerById(int id) {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
		
		try {
			PreparedStatement st = connection.prepareStatement(query);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();
			rs.next();
			
			return getCustomerFromResultSet(rs);			
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	public ArrayList<Customer> getAll(boolean filter) {
		String query = "SELECT * FROM " + TABLE_NAME;
		ArrayList<Customer> result = new ArrayList<>();
		
		try {
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while(rs.next()) {
				result.add(new Customer(
						rs.getInt(columns[0]),
						rs.getString(columns[1])));
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
		
	public boolean registerCustomer(Customer customer) {
		String createCostumerStatement = "INSERT INTO " + TABLE_NAME +
			"(name)" +
			" VALUES (?)";
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(createCostumerStatement);
			
			statement.setString(1, customer.getName());
			
			statement.execute();
			connection.commit();
			return true;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean updateCustomer(Customer customer) {
		String createCostumerStatement = "UPDATE " + TABLE_NAME +
			" SET name = ?" +
			" WHERE id = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(createCostumerStatement);
			
			statement.setString(1, customer.getName());
			statement.setInt(2, customer.getId());
			
			statement.execute();
			return true;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	@Override
	public String getTableQuery() {
		// TODO Auto-generated method stub
		return "select id as Código, name as Nome from " + TABLE_NAME;
	}
}
