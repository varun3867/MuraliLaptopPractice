package com.example.demo.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadsExample {

	public static void main(String[] args) {
			
		//List<Integer> list = new ArrayList<>();
			ExecutorService es = Executors.newFixedThreadPool(2);
			
			for(int i=0;i<10;i++) {
				int task=i;
				es.execute(()->{	
					System.out.println(Thread.currentThread().getName()+" : Task "+ task);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}
				
			es.shutdown();
	}

}
