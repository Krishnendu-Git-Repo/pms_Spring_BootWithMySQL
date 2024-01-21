package com.project.projectManagementSystem.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.projectManagementSystem.dto.EmployeeDTO;
import com.project.projectManagementSystem.exception.CustomException;
import com.project.projectManagementSystem.helper.JWTUtill;
import com.project.projectManagementSystem.model.Employee;
import com.project.projectManagementSystem.repo.EmployeeRepo;
import com.project.projectManagementSystem.response.GlobalResponse;
import com.project.projectManagementSystem.service.EmployeeService;
import com.project.projectManagementSystem.utility.CommonUtility;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private CommonUtility commonUtility;
	@Autowired
	private JWTUtill jwtUtill;
	
	
	public static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Override
	public GlobalResponse createEmployee(@Valid Employee employee) {
		try {
			Employee addEmployee = this.commonUtility.addEmployee(employee);
			Employee savedEmployee = this.employeeRepo.save(addEmployee);
			Long empId = savedEmployee.getId();
			return new GlobalResponse("200", "Employee Added Successfully", empId);
			
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse getEmployeeById(String token, Long id) {
		try {
			String email = this.jwtUtill.extractUsername(token.substring(7));
			Optional<Employee> employeeById = this.employeeRepo.findById(id);
				if (employeeById.isPresent()) {
					Employee employee = employeeById.get();
					return new GlobalResponse("200", "Success", employee);
				} else {
					return new GlobalResponse("400", "Employee Does Not Exist", null);
				}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse updateEmployee(String token, @Valid EmployeeDTO employeeDTO) {
		try {
			Optional<Employee> employeeData = this.employeeRepo.findById(employeeDTO.getId());
			if (employeeData.isPresent()) {
				Employee employee = employeeData.get();
				Employee updateEmployee = new Employee();
				updateEmployee.setId(employee.getId());
				updateEmployee.setFirstName(employeeDTO.getFirstName() == null ? employee.getFirstName()
						: employeeDTO.getFirstName());
				updateEmployee.setLastName(
						employeeDTO.getLastName() == null ? employee.getLastName() : employeeDTO.getLastName());
				updateEmployee.setDepartment(employeeDTO.getDepartment() == null ? employee.getDepartment()
						: employeeDTO.getDepartment());
				updateEmployee
						.setEmail(employeeDTO.getEmail() == null ? employee.getEmail() : employeeDTO.getEmail());
				updateEmployee.setDateOfBirth(employee.getDateOfBirth());
				updateEmployee.setIsDeleted(employee.getIsDeleted());
				updateEmployee.setPassword(employee.getPassword());
				Employee newUpdatedEmployee = this.employeeRepo.save(updateEmployee);
				
				return new GlobalResponse("200", "Employee Updated Successfully", newUpdatedEmployee);
			} else {
				return new GlobalResponse("400", "Employee Does Not Exist", null);	
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse deleteEmployeeById(String token, Long id) {
		try {
			Optional<Employee> employeeData = this.employeeRepo.findById(id);
			Boolean isDelete = Boolean.TRUE;
			if (employeeData.isPresent()) {
				if (employeeData.get().getIsDeleted().equals(Boolean.FALSE)) {
					Employee employee = employeeData.get();
					employee.setIsDeleted(isDelete);
					Employee savedEmployee = this.employeeRepo.save(employee);
					return new GlobalResponse("200", "Employee is Deleted Successfully", savedEmployee.getIsDeleted());
				} else {
					return new GlobalResponse("400", "Employee Has Been Alreaday Deleted", null);	
				}
			} else {
				return new GlobalResponse("400", "Employee Does Not Exist", null);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse findAllEmployee(String token, Boolean isDeleted) {
		try {
		    List<Employee> allEmployee = this.employeeRepo.findByIsDeleted(isDeleted);
		    if(allEmployee.size() > 0) {
			    return new GlobalResponse("200", "Success", allEmployee);
		    } else {
		    	return new GlobalResponse("400", "Employee Not Found !", null);	
		    }
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
