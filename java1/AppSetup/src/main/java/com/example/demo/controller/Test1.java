package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Test1 {
	
	public static void main(String[] args) {
		
		
			List<Integer> l = new ArrayList<>(Arrays.asList(5,6,7,9,1,2,4,0));
			long start = System.currentTimeMillis();
			List<Integer> pl = l.parallelStream().sorted().collect(Collectors.toList());
			long end = System.currentTimeMillis();
			System.out.println(end-start);
			
			long start2 = System.currentTimeMillis();
			List<Integer> pl2 = l.stream().sorted().collect(Collectors.toList());
			long end2 = System.currentTimeMillis();
			System.out.println(end2-start2);
			
			//l.add(Integer.valueOf(10));
//			for(int i=0;i<l.size();i++) {

			//	System.out.println(i+" "+l.get(i));
			//}
			
			//Test.fibonacci();
			
			
			
		
	}
			
	
	public static void fibonacci() {
		//0,1,2,3,5,8,13,.
		int ite = 7;
		int first = 0;
		int second = 1;
		int third = 0;
		while(ite>0) {
			
			third = first + second;
			first = second;
			second = third;
			System.out.println(third);
			ite = ite - 1;
			
		}
		
		
	}
	
	
}
