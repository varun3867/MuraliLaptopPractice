package com.example.demo.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBOperationsWithExecutorService {

	public static void main(String[] args) {
		
		ExecutorService es =  Executors.newFixedThreadPool(2);
		es.execute(()->{
			System.out.println("Fetching Database Records From 1-100");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		es.execute(()->{
			System.out.println("Fetching Database Records From 101-200");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		es.execute(()->{
			System.out.println("Fetching Database Records From 201-300");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		es.execute(()->{
			System.out.println("Fetching Database Records From 301-400");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		es.shutdown();
		
	}

}
