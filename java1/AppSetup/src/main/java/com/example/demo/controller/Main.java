package com.example.demo.controller;

//Parent class
class A {
	static void fun() { System.out.println("A.fun()"); 
	}
}

//B is inheriting A
//Child class
class B extends A {
	static void fun() { System.out.println("B.fun()"); }
}

//Driver Method
public class Main {
	public static void main(String args[])
	{
		A a = new B();
		a.fun();
	}
}