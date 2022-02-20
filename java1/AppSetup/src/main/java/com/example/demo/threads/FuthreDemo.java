package com.example.demo.threads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FuthreDemo {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newFixedThreadPool(3);
		
		Future submit = es.submit(()->{
			if(Thread.currentThread().getName().equals("pool-1-thread-1"))
				try {
					System.out.println("Going to sleep state....");
					Thread.sleep(1000);
					System.out.println("Just completed sleep state.....");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			System.out.println(Thread.currentThread().getName());
		});

		System.out.println(submit.isDone());
		
		submit.get();
		
		System.out.println(submit.isDone());
		
		es.shutdown();
		
	}

}
