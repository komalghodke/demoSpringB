package com.demo.demoSpringB;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	    @Autowired
	    private EmployeeService service;
	    
	    @Autowired
	    private EmployeeRepository repository;

	    @GetMapping
	    public List<Employee> getAllEmployees() { return service.getAllEmployees(); }
	    
	    @GetMapping("/sorted")
	    public List<Employee> sortedEmployees() {
	        return service.getAllEmpByDept();
	    }
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
	        return ResponseEntity.ok(service.getEmployeeById(id));
	    }
	    
	    @PostMapping
	    public Employee createEmployee(@RequestBody Employee employee) {
	    	return service.saveEmployee(employee);
	    }

	    @PostMapping("/batch")
	    public ResponseEntity<String> addMultipleEmployees(@Valid @RequestBody List<Employee> employees) {
	        service.saveAll(employees);
	        return ResponseEntity.ok("All employees saved");
	    }
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
	        Employee employee = service.getEmployeeById(id);
	        if (employee == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	        if (updatedEmployee.getName() != null)
	        	employee.setName(updatedEmployee.getName());
	        if (updatedEmployee.getDesignation() != null)
	        	employee.setDesignation(updatedEmployee.getDesignation());
	        if (updatedEmployee.getDepartment() != null) 
	        	employee.setDepartment(updatedEmployee.getDepartment());
	        if (updatedEmployee.getLocation() != null) 
	        	employee.setLocation(updatedEmployee.getLocation());
	        if (updatedEmployee.getSalary() != null)
	        		employee.setSalary(updatedEmployee.getSalary());
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
	    
	    @GetMapping("/groupByDept")
	    public ResponseEntity<Map<String, List<Employee>>> getGroupedByDept() {
	        List<Employee> employees = service.getAllEmployees();
	        Map<String, List<Employee>> grouped = 
	            employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
	        return ResponseEntity.ok(grouped);
	    }
	    
	    @GetMapping("/highestSal")
	    public ResponseEntity<List<Employee>> getHighestSalary() {
	        List<Employee> topEarners = repository.highestSalary();
	        return ResponseEntity.ok(topEarners);
	    }
	    
	    @GetMapping("/secondHighestSal")
	    public ResponseEntity<Long> getSecondHighestSalary() {
	        Long salary = service.getSecondHighestSalary();
	        return salary != null ? ResponseEntity.ok(salary) : ResponseEntity.notFound().build();
	    }
	    
	    @GetMapping("/topEarnersByDept")
        public ResponseEntity<List<Employee>> getTopEarnersByDept() {
            List<Employee> employees = service.getTopEarnersByDept();
            return ResponseEntity.ok(employees);
        }
	    
	    @CrossOrigin(origins = "http://localhost:3000")
	    @GetMapping("/search")
	    public ResponseEntity<String> search(@RequestParam String query) {
	    	String serpApiKey = "";
	        String serpUrl = "https://serpapi.com/search.json?q=" + query + "&api_key=" + serpApiKey;
	        RestTemplate restTemplate = new RestTemplate();
	        String response = restTemplate.getForObject(serpUrl, String.class);
	        return ResponseEntity.ok(response);
	    }

}
