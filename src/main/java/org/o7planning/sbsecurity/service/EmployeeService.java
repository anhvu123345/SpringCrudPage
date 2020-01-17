package org.o7planning.sbsecurity.service;

import java.util.List;

import org.o7planning.sbsecurity.entity.Employee;

public interface EmployeeService {
	
	Iterable<Employee> findAll();
	
	List<Employee> search(String q);
	
	Employee findOne(long id);
	
	void save(Employee emp);
	
	void delete(Employee emp);

}
