package com.example.demo.controller;

public class Test {

	public static void main(String[] args) {
		
		String s="varun";
		String r = "";
		
		for(int i=s.length()-1;i>=0;i--){
			 String re =  s.charAt(i) + "";
			 r = r.concat(re);
		}
		System.out.println(r);
	}

}
