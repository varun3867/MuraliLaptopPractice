package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Impl {
	
	
	public static void main(String[] args) {
		
		String str  = "abc123#234bcd";
		
		//abc=123
		//bcd=234
		
		String[] strArray = str.split("#");
		for(String s:strArray) {
			//System.out.println(s);		
		}
		String substring1 = str.substring(0,3) + "=" + str.substring(3, 6);
		System.out.println(substring1);
		
		String substring2 = str.substring(10,13) + "=" + str.substring(7,10);
		System.out.println(substring2);
		
	}
		

}
