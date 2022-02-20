package com.example.demo.threads;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Customer;
@Component
class MultipleThreadsFetchingCustomer {
	
	@Autowired
	private CreateCustomers customers;

	
	public void get() {
		
		List<Customer> list = customers.createCutomers();
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		
		es.execute(()->{
			List<Customer> list1 = customers.fetchFromXtoY(list,1, 1000);
			for(Customer c:list1) {
				System.out.println(c);
			}
		});
		
		
		es.execute(()->{
			List<Customer> list2 = customers.fetchFromXtoY(list,1001, 2000);
			for(Customer c:list2) {
				System.out.println(c);
			}
		});
		
		
		es.execute(()->{
			List<Customer> list3 = customers.fetchFromXtoY(list,2001, 3000);
			for(Customer c:list3) {
				System.out.println(c);
			}
		});
		
		
		es.execute(()->{
			List<Customer> list4 = customers.fetchFromXtoY(list,3001, 4000);
			for(Customer c:list4) {
				System.out.println(c);
			}
		});
		
		es.shutdown();
	}
}
