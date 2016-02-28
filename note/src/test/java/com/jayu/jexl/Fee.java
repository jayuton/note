package com.jayu.jexl;

public class Fee {

	public Object value(String name){
		return "name="+name;
	}
	public Object sum(String name){
		return "name="+name;
	}
	public Object sum(long a,long b){
		return a + b;
	}
	public Object min(long a,long b){
		return Math.min(a, b);
	}
}
