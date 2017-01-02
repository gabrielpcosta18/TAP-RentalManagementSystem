package br.edu.ufam.icomp.bd;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.ufam.icomp.model.Product;
import br.edu.ufam.icomp.model.Rental;

public class RentalDAO extends RentalDatabase {
	private static String TABLE_NAME = "Rental";
	
	public static String getTableName() {
		return TABLE_NAME;
	}
	
	@Override
	public String getTableQuery() {
		// TODO Auto-generated method stub
		return "select r.id as Código, c.name as NomeCliente, e.name as NomeFuncionario, " +
				"p.title as Titulo, r.rentDate as DataEmprestimo, r.wasDeveloped as Devolvido from " + 
				TABLE_NAME  + " as r INNER JOIN " + EmployeeDAO.getTableName() + " as e ON r.employeeRentId = e.id " + 
				" INNER JOIN " + CustomerDAO.getTableName() + " as c ON r.customerId = c.id" + 
				" INNER JOIN " + ProductDAO.getTableName() + " as p ON r.productId = p.id";
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
}
