package br.edu.ufam.icomp.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.ufam.icomp.model.Employee;
import br.edu.ufam.icomp.model.Product;

public class EmployeeDAO extends RentalDatabase {
	private static String TABLE_NAME = "Employee";
	
	public static String getTableName() {
		return TABLE_NAME;
	}
	
	private static Employee getEmployeeFromResultSet(ResultSet rs) {
		try {
			return new Employee(
					rs.getInt(1),
					rs.getString(2));
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	public static Employee getEmployeeById(int id) {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
		
		try {
			PreparedStatement st = connection.prepareStatement(query);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();
			rs.next();
			
			return getEmployeeFromResultSet(rs);			
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	public ArrayList<Employee> getAll(boolean filter) {
		String query = "SELECT * FROM " + TABLE_NAME;
		ArrayList<Employee> result = new ArrayList<>();
		
		try {
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while(rs.next()) {
				result.add(new Employee(
						rs.getInt(1),
						rs.getString(2)));
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
		
	public boolean registerEmployee(Employee employee) {
		String createEmployeeStatement = "INSERT INTO " + TABLE_NAME +
			" (name)" +
			" VALUES (?)";
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(createEmployeeStatement);
			
			statement.setString(1, employee.getName());
			
			statement.execute();
			connection.commit();
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

	public boolean updateEmployee(Employee employee) {
		String createEmployeeStatement = "UPDATE " + TABLE_NAME +
				" SET name = ?" +
				" WHERE id = ?";
			
		try {
			PreparedStatement statement = connection.prepareStatement(createEmployeeStatement);
			
			statement.setString(1, employee.getName());
			statement.setInt(2, employee.getId());
			
			statement.execute();
			return true;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}	
	}
}
