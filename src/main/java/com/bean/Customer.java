package com.bean;

public class Customer {
	
	int customerId;
	
	int ssn;
	
	String name;
	
	String address;
	
	int age;

	public Customer() {
	}

	public Customer(int customerId, int ssn, String name, String address, int age) {
		this.customerId = customerId;
		this.ssn = ssn;
		this.name = name;
		this.address = address;
		this.age = age;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getSsn() {
		return ssn;
	}

	public void setSsn(int ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", ssn=" + ssn + ", name=" + name + ", address=" + address
				+ ", age=" + age + "]";
	}
	
	
}
