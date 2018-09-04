package com.accenture.myapp.controller;
/**
 * 
 */


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.myapp.model.Employee;
import com.accenture.myapp.repository.EmployeeRepository;

/**
 * @author anurag.a.sachan
 *
 */
@RestController
@RequestMapping("/test")
public class EmployeeController {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
	SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MMM-YYYY");
	DateFormat formatter = new SimpleDateFormat("DD-MMM-YYYY");
	
	@Autowired
	EmployeeRepository employeeBusiness;
	
	@RequestMapping("/emp/empid/{empid}")
    @ResponseBody
    public Employee getEmployee(@PathVariable("empid") long empId) {
		LOG.info("Getting employee details for Emp-Id:{} ",empId);
        Employee emp = employeeBusiness.getOne(empId);
        String dob =dateFormat.format(emp.getDob());
        String doj =dateFormat.format(emp.getDoj());
        try {
			emp.setDob(formatter.parse(dob));
			emp.setDoj(formatter.parse(doj));
		} catch (ParseException e) {
			LOG.error("Date formating error {}", e.fillInStackTrace());
		}
        return emp;
    }
	
	@RequestMapping("/emp")
    @ResponseBody
    public List<Employee> getEmployeeList() {
		LOG.info("Getting employee details for Emp-Id:{} ");
        return employeeBusiness.findAll();
    }
	

	@PostMapping("/emp")
	 @ResponseBody
	public Employee createEmployee(@Valid @RequestBody Employee emp) {
		LOG.info("Saving employee details : {}",emp);
		emp.setCreatedAt(new Date());
		emp.setUpdatedAt(new Date());
	    return employeeBusiness.save(emp);
	}
	
	@DeleteMapping("/emp/empid/{empid}")
	@ResponseBody
	public void deleteEmployee(@PathVariable("empid") long empid) {
		LOG.info("Deleting employee details : {}",empid);
	    employeeBusiness.deleteById(empid);
	}
	
	@PutMapping("/emp/empid/{empid}")
	@ResponseBody
	public Employee updateEmployee(@Valid @RequestBody Employee emp,@PathVariable("empid") long empid) {
		LOG.info("Updating employee details : {}",empid);
		Employee empData = employeeBusiness.getOne(empid);
		emp.setId(empid);
		emp.setCreatedAt(empData.getCreatedAt());
		emp.setUpdatedAt(new Date());
	    return employeeBusiness.save(emp);
	}

}
