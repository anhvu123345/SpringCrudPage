package org.o7planning.sbsecurity.repository;

import java.util.List;

import org.o7planning.sbsecurity.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	List<Employee> findByNameContaining(String q);
}
