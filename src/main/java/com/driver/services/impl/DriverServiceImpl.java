package com.driver.services.impl;

import com.driver.exception.DriverAlreadyExistsException;
import com.driver.exception.DriverDoesNotExistsException;
import com.driver.model.Cab;
import com.driver.repository.CabRepository;
import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Driver;
import com.driver.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository3;

	@Autowired
	CabRepository cabRepository3;

	@Override
	public void register(String mobile, String password){
		//Save a driver in the database having given details and a cab with ratePerKm as 10 and availability as True by default.
		List<Driver> driverList =  driverRepository3.findAll();

		// check, driver pehle se exists toh nahi karta, agar karta hai toh throe exception
		for(Driver driver : driverList){
			if(driver.getMobile().equals(mobile)){
				throw new DriverAlreadyExistsException("Driver already exists");
			}
		}

		// if not then make a driver to register
		Driver driver = new Driver();
		driver.setMobile(mobile);
		driver.setPassword(password);

		Cab cab = new Cab(10, true);


		// save both cab into driver and driver into cab ,, because of 1-1 mapping
		driver.setCab(cab);
		cab.setDriver(driver);


		driverRepository3.save(driver);

	}

	@Override
	public void removeDriver(int driverId){
		// Delete driver without using deleteById function
		Optional<Driver> driverOptional = driverRepository3.findById(driverId);

		// if driver exists
		if(driverOptional.isPresent()){
			// save the driver
			Driver driver = driverOptional.get();
			// now remove that driver
			driverRepository3.delete(driver);
		}

	}

	@Override
	public void updateStatus(int driverId){
		//Set the status of respective car to unavailable

		// first check the driver exists or not
		Optional<Driver> driverOptional = driverRepository3.findById(driverId);

		// if driver is not present with this id , throw the exception
		if(driverOptional.isPresent()){
			// if driver exists then get the drive
			Driver driver = driverOptional.get();

			// now get the cab details from driver and set the availability to false;
			Cab cab = driver.getCab();
			cab.setAvailable(false);

			// save the cab to driver
			driver.setCab(cab);
//
//			// save the driver to cab
//			cab.setDriver(driver);

			// save the driver to the database
			driverRepository3.save(driver);
		}

	}
}
