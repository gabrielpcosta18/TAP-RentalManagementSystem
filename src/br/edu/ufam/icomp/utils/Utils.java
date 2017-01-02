package br.edu.ufam.icomp.utils;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import br.edu.ufam.icomp.model.Customer;
import br.edu.ufam.icomp.model.Employee;
import br.edu.ufam.icomp.model.Product;

public class Utils {
	public static void fillComboBox(JComboBox comboBox, List<? extends Object> data) {
		for(Object employee : data) {
			comboBox.addItem(employee);
		}
	}
}
