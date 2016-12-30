package br.edu.ufam.icomp.bd;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.ufam.icomp.model.Employee;

public class EmployeeDAO extends RentalDatabase {
	private static String TABLE_NAME = "Employee";
	
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
	public String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
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
