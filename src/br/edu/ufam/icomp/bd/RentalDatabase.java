package br.edu.ufam.icomp.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class RentalDatabase {
	private static String url = "jdbc:postgresql://localhost:5432/RentalDB";
	private static String user = "postgres";
	private static String password = "123456";
	protected static Connection connection = null;
	
	public RentalDatabase() {
		if(connection == null) conect();
	}
	
	private static boolean conect() {
		try {
			connection = DriverManager.getConnection(url, user, password);
			return true;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public static boolean disconnect() {
		try {
			connection.close();
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
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
		        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
		            vector.add(rs.getObject(columnIndex));
		        }
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

	public String getTableQuery() {
		// TODO Auto-generated method stub
		return "";
	}

	public Vector<String> getColumnsName() {
		// TODO Auto-generated method stub
		return null;
	}
}
