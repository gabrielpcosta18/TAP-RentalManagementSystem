package br.edu.ufam.icomp.model;

public class Product {
	private int id;
	private String title;
	private String description;
	private String type;
	private int maxPeriodRent;
	private int totalInStock;
	private float price;
	
	public Product() {
		this.id = -1;
	}
	
	public Product(String title, String description, String type, int maxPeriodRent, int totalInStock, float price) {
		this();
		this.title = title;
		this.description = description;
		this.setType(type);
		this.setMaxPeriodRent(maxPeriodRent);
		this.totalInStock = totalInStock;
		this.price = price;
	}
	
	public Product(int id, String title, String description, String type, int maxPeriodRent, int totalInStock, float price) {
		this(title, description, type, maxPeriodRent,totalInStock, price);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getTotalInStock() {
		return totalInStock;
	}
	
	public void setTotalInStock(int totalInStock) {
		this.totalInStock = totalInStock;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float value) {
		this.price = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMaxPeriodRent() {
		return maxPeriodRent;
	}

	public void setMaxPeriodRent(int maxPeriodRent) {
		this.maxPeriodRent = maxPeriodRent;
	}	
}
