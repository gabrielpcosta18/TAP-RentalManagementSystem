package br.edu.ufam.icomp.model;

public class Employee {
	private int id;
	private String name;
	
	public Employee(String name) {
		this.name = name;
		this.id = -1;
	}
	
	public Employee(int id, String name) {
		this(name);
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String toString() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
