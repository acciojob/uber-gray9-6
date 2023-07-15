package com.driver.services.impl;

import java.util.List;
import java.util.Optional;

import com.driver.exception.AdminAlreadyExistsException;
import com.driver.exception.AdminDoesNotExistsException;
import com.driver.exception.CustomerListIsEmptyException;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository1;

	@Autowired
	DriverRepository driverRepository1;

	@Autowired
	CustomerRepository customerRepository1;

	@Override
	public void adminRegister(Admin admin) {

		// check whether this admin already exists or not , check by name , because name is unique
		List<Admin> adminList = adminRepository1.findAll();
		for(Admin admin1 : adminList){
			if(admin1.getUsername().equals(admin.getUsername())){
				throw new AdminAlreadyExistsException("Admin already exists");
			}
		}

		// if not then save the admin in the database
		adminRepository1.save(admin);
	}

	@Override
	public Admin updatePassword(Integer adminId, String password) {
		// first check if admin exists or not
		Optional<Admin> adminOptional = adminRepository1.findById(adminId);

		// if admin does not exists , throw the exception
		if(!adminOptional.isPresent()){
			throw new AdminDoesNotExistsException("Admin does not exists");
		}

		// if admin exists then save the admin
		Admin admin = adminOptional.get();

		// now change the admin password
		admin.setPassword(password);

		//save the admin to database
		return adminRepository1.save(admin);

	}

	@Override
	public void deleteAdmin(int adminId){
		// Delete admin without using deleteById function

		// first check if admin exists or not
		Optional<Admin> adminOptional = adminRepository1.findById(adminId);

		// if admin does not exists then throw the exception
		if(adminOptional.isPresent()){
			// if it exist then save the admin
			Admin admin = adminOptional.get();

			// now delete that admin
			adminRepository1.delete(admin);
		}

	}

	@Override
	public List<Driver> getListOfDrivers() {
		//Find the list of all drivers
		List<Driver> driverList = driverRepository1.findAll();

		if(driverList.isEmpty()){
			return  null;
		}
		return driverList;
	}

	@Override
	public List<Customer> getListOfCustomers() {
		//Find the list of all customers
		List<Customer> customerList = customerRepository1.findAll();
		if(customerList.isEmpty()){
			return  null;
		}
		return customerList;
	}

}
