package br.edu.ufam.icomp.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.model.core.ID;

import br.edu.ufam.icomp.model.Customer;
import br.edu.ufam.icomp.model.Product;

public class ProductDAO extends RentalDatabase {
	private static String TABLE_NAME = "Product";
		
	public static String getTableName() {
		return TABLE_NAME;
	}
	
	public ArrayList<Product> getAll(boolean filter) {
		String query = "SELECT * FROM " + TABLE_NAME;
		ArrayList<Product> result = new ArrayList<>();
		
		try {
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while(rs.next()) {
				result.add(new Product(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getInt(6),
						rs.getFloat(7)));
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	public boolean createProduct(Product product) {
		String createProductStatement = "INSERT INTO " + TABLE_NAME +
			" (title, description, type, maxPeriodRent, totalInStock, rentPrice)" +
			" VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(createProductStatement);
			
			statement.setString(1, product.getTitle());
			statement.setString(2, product.getDescription());
			statement.setString(3, product.getType());
			statement.setInt(4, product.getMaxPeriodRent());
			statement.setInt(5, product.getTotalInStock());
			statement.setFloat(6, product.getPrice());
			
			statement.execute();
			connection.commit();
			return true;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean updateProduct(Product product) {
		String updateProductStatement = "UPDATE " + TABLE_NAME +
			" SET title = ?, description = ?, type = ?, maxperiodrent = ?, totalinstock = ?, rentprice = ?" +
			" WHERE id = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(updateProductStatement);
			
			statement.setString(1, product.getTitle());
			statement.setString(2, product.getDescription());
			statement.setString(3, product.getType());
			statement.setInt(4, product.getMaxPeriodRent());
			statement.setInt(5, product.getTotalInStock());
			statement.setFloat(6, product.getPrice());
			
			statement.setInt(7, product.getId());
			
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
		return "select id as C�digo, title as T�tulo, description as Descri��o, type as Tipo, maxPeriodRent as Per�odoMax, totalInStock as Estoque, rentPrice as Pre�o from " + TABLE_NAME;
	}
}
