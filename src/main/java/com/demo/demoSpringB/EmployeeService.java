package com.demo.demoSpringB;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
		 return repository.findById(id)
			        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
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

	public Long getSecondHighestSalary() {
	    List<Long> salaries = repository.findDistinctSalaries();
	    if (salaries.size() >= 2) {
	        return salaries.get(1);
	    }
	    return null;
	}
	
	public List<Employee> getTopEarnersByDept() {
        return repository.getTopEarnersByDept();
    }
	
}
