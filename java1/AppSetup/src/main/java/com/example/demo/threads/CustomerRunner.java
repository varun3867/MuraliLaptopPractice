package com.example.demo.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomerRunner implements CommandLineRunner {
	
	@Autowired
	private MultipleThreadsFetchingCustomer customer;
	
	@Override
	public void run(String... args) throws Exception {
		
		customer.get();

	}

}
