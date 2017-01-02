package br.edu.ufam.icomp.model;

public class Customer {
	private int id;
	private String name;
	
	public Customer() {
		this.id = -1;
	}
	
	public Customer(String name) {
		this();
		this.name = name;
	}
		
	public Customer(int id, String name) {
		this(name);
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String toString() {
		return this.name;
	}
}
