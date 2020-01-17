package org.o7planning.sbsecurity.service;

import java.util.List;

import org.o7planning.sbsecurity.entity.Employee;
import org.o7planning.sbsecurity.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Iterable<Employee> findAll() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}

	@Override
	public List<Employee> search(String q) {
		// TODO Auto-generated method stub
		return employeeRepository.findByNameContaining(q);
	}

	@Override
	public Employee findOne(long id) {
		// TODO Auto-generated method stub
		return employeeRepository.findOne(id);
	}

	@Override
	public void save(Employee emp) {
		// TODO Auto-generated method stub
		employeeRepository.save(emp);
	}

	@Override
	public void delete(Employee emp) {
		// TODO Auto-generated method stub
		employeeRepository.delete(emp);
	}

}
