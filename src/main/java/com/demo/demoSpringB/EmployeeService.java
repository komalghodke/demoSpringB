package com.demo.demoSpringB;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository repository;

	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	public Employee saveEmployee(Employee employee) {
		return repository.save(employee);
	}

	public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }
	
	public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
	
	public List<Employee> getAllEmpByDept() {
	    List<Employee> employees = repository.findAll();
	    employees.sort(Comparator.comparing(Employee::getDepartment));
	    return employees;
	}

	public void saveAll(List<Employee> employees) {
		repository.saveAll(employees);		
	}

	
}
