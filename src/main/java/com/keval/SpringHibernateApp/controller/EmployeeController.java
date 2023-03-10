package com.keval.SpringHibernateApp.controller;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.keval.SpringHibernateApp.model.Employee;
import com.keval.SpringHibernateApp.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeserviceimpl; 
	
	@RequestMapping("/view")
	public String viewEmployee(Model model) {
		model.addAttribute("employeeList",employeeserviceimpl.viewEmployee());
		return "../ViewEmployee.jsp";
	}
	
	@RequestMapping("/create")
	public String createPage(Model model) {
		model.addAttribute("todayDate", LocalDate.now().toString());
		model.addAttribute("employee",new Employee());
		return "../EmployeeDataEntry.jsp";
	}
	
	@RequestMapping("/update{employeeId}")
	public String updatePage(Model model,@PathVariable int employeeId) {
		model.addAttribute("todayDate", LocalDate.now().toString());
		model.addAttribute("employee",employeeserviceimpl.fetchData(employeeId));
		return "../EmployeeDataEntry.jsp";
	}
	
	@RequestMapping("/delete{employeeId}")
	public String Employee(@PathVariable int employeeId) {
		employeeserviceimpl.deleteEmployee(employeeId);
		return "redirect:/employee/view";
	}
	
	@RequestMapping(value="/submit", method= RequestMethod.POST)
	public String createEmployee(@ModelAttribute() Employee employee) {
		if(employee.getEmployeeId()==0) {
			employeeserviceimpl.createEmployee(employee);
		}else {
			employeeserviceimpl.updateEmployee(employee);
		}
		return "redirect:/employee/view";
	}
}
