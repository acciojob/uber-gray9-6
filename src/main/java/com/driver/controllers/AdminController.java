package com.driver.controllers;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;


	@PostMapping("/register")
	public ResponseEntity<Void> registerAdmin(@RequestBody Admin admin){
		try{
			adminService.adminRegister(admin);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<Admin> updateAdminPassword(@RequestParam Integer adminId, @RequestParam String password){
		try{
			Admin updatedAdmin = adminService.updatePassword(adminId, password);
			return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete")
	public void deleteAdmin(@RequestParam Integer adminId){
		adminService.deleteAdmin(adminId);
	}

	@GetMapping("/listOfCustomers")
	public List<Customer> listOfCustomers() {
			return  adminService.getListOfCustomers();
	}

	@GetMapping("/listOfDrivers")
	public List<Driver> listOfDrivers() {
		return adminService.getListOfDrivers();
	}
}
