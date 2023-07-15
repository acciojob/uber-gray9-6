package com.driver.services.impl;

import com.driver.exception.CustomerAlreadyExists;
import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		// first check if customer already registered or not
		Customer customerOptional = customerRepository2.findByMobile(customer.getMobile());

		if(customerOptional != null){
			throw new CustomerAlreadyExists("customer already exists");
		}else{
			// save the customer to database
			customerRepository2.save(customer);
		}
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function

		// check first that customer exists or not
		Optional<Customer> customerOptional = customerRepository2.findById(customerId);

		if (customerOptional.isPresent()){
			// delete the customer
			Customer customer = customerOptional.get();
			customerRepository2.delete(customer);
		}

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> driverList = driverRepository2.findAll();
		Driver minDriver = null;
		// finding driver with min driver id
		for(Driver driver : driverList){
			if(minDriver == null && driver.getCab().isAvailable()){
				minDriver = driver;
			}
			if(minDriver != null  && driver.getCab().isAvailable() && driver.getDriverId() < minDriver.getDriverId()){
				minDriver = driver;
			}
		}

		// if no driver found throw exception
		if(minDriver == null ) throw new Exception("No cab available!");

		// driver found
		// now lets book the cab for the customer

		TripBooking tripBooking = new TripBooking();
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setTripStatus(TripStatus.CONFIRMED);

		Customer customer = customerRepository2.findById(customerId).get();
		// make driver not available for any one at this moment while booking is processing
		minDriver.getCab().setAvailable(false);

		// set customer and driver details in trip booking
		tripBooking.setCustomer(customer);
		tripBooking.setDriver(minDriver);

		// calculating bill for the journey
		tripBooking.setBill(distanceInKm * minDriver.getCab().getPerKmRate());

		customer.getTripBookingList().add(tripBooking);
		minDriver.getTripBookingList().add(tripBooking);

		// saving their details in database
		driverRepository2.save(minDriver);
		customerRepository2.save(customer);

		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		if(tripBookingRepository2.findById(tripId).isPresent()){
			TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
			tripBooking.setBill(0);
			tripBooking.getDriver().getCab().setAvailable(true);
			tripBooking.setTripStatus(TripStatus.CANCELED);

			tripBookingRepository2.save(tripBooking);
		}

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		if(tripBookingRepository2.findById(tripId).isPresent()){
			TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
			tripBooking.setTripStatus(TripStatus.COMPLETED);
			tripBooking.getDriver().getCab().setAvailable(true);
			tripBookingRepository2.save(tripBooking);

		}

	}
}
