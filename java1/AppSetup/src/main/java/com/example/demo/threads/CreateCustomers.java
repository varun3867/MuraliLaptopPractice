package com.example.demo.threads;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.model.Customer;

@Component
public class CreateCustomers {
	
	
	
	public List<Customer> createCutomers(){
		List<Customer> customerList = new ArrayList<>();
		
		for(int i=0;i<4000;i++) {
			 customerList.add(new Customer("name-"+i, 32, 95000.0));
			 System.out.println(customerList.get(i));
		}
		return customerList;
	}
	
	public List<Customer> fetchFromXtoY(List<Customer> listCust, int start,int end){
		List<Customer> custStartToEnd = new ArrayList<>();
		for(int i=start;(i<=end && i!=400);i++) {
			custStartToEnd.add(listCust.get(i));
		}
		return custStartToEnd;
	}
	
	

}
