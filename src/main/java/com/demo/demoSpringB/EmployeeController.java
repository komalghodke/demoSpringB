package com.demo.demoSpringB;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	    @Autowired
	    private EmployeeService service;

	    @GetMapping
	    public List<Employee> getAllEmployees() { return service.getAllEmployees(); }
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
	        return ResponseEntity.ok(service.getEmployeeById(id));
	    }
	    
	    @PostMapping
	    public Employee createEmployee(@RequestBody Employee employee) { return service.saveEmployee(employee); }
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
	        Employee employee = service.getEmployeeById(id);
	        if (employee == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	        employee.setName(updatedEmployee.getName());
	        employee.setDesignation(updatedEmployee.getDesignation());
	        Employee savedEmployee = service.saveEmployee(employee);
	        return ResponseEntity.ok(savedEmployee);
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
	        Employee employee = service.getEmployeeById(id); 
	        if (employee != null) { 
	            service.deleteEmployee(id);
	            return ResponseEntity.noContent().build(); 
	        }

	        return ResponseEntity.notFound().build();
	    }
}
