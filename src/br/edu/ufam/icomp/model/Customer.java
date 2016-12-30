package br.edu.ufam.icomp.model;

public class Customer {
	private int id;
	private String name;
	
	public Customer(String name) {
		this.name = name;
		this.id = -1;
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
}
