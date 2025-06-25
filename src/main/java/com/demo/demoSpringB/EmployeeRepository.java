package com.demo.demoSpringB;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long>  {
	
	@Query("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e2.salary) FROM Employee e2)")
	List<Employee> highestSalary();
	
	@Query("SELECT DISTINCT e.salary FROM Employee e ORDER BY e.salary DESC")
	List<Long> findDistinctSalaries();
	
	@Query("SELECT e FROM Employee e WHERE e.salary = (" +
		       "SELECT MAX(e2.salary) FROM Employee e2 WHERE e2.department = e.department)")
	List<Employee> getTopEarnersByDept();

}
