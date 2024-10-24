package com.luv2code.cruddeom.rest;

import com.luv2code.cruddeom.entity.Employee;
import com.luv2code.cruddeom.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class EmployeeRestController {
    private EmployeeService employeeService;

    // quick and dirty : inject employee dao(use constructor injection)
    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    // expose "/employee" and return a list of employees

    @GetMapping("employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    // add mapping for get/employees/{employeeId}

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);

        if (theEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }
        return theEmployee;
    }


    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee) {
        // also just in case they pass an id in JSON ... set id to 0

        // this is to force a save of new time ... instead of update

        theEmployee.setId(0);

        Employee dbEmployee = employeeService.save(theEmployee);

        return dbEmployee;
    }

    // add mapping for put/employees -update existing employee

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee) {

        Employee dbEmployee = employeeService.save(theEmployee);

        return dbEmployee;
    }

    // delete mapping for delete/employees/{employeeId} - delete employee

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {

        Employee temEmployee = employeeService.findById(employeeId);

        // throw exception if null

        if (temEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        employeeService.deleteById(employeeId);

        return "Deleted employee id is : " + employeeId;
    }

}
