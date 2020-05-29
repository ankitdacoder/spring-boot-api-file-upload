package com.developer.idea.util;

import java.math.BigDecimal;

public class ReportSecondModel {

	private String type;
	private double sp;
	private double titer;
	private double log2;
	
	
	
	
	public ReportSecondModel(String type, double sp, double titer, double log2) {
		super();
		this.type = type;
		this.sp = sp;
		this.titer = titer;
		this.log2 = log2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getSp() {
		return sp;
	}
	public void setSp(double sp) {
		this.sp = sp;
	}
	public double getTiter() {
		return titer;
	}
	public void setTiter(double titer) {
		this.titer = titer;
	}
	public double getLog2() {
		return log2;
	}
	public void setLog2(double log2) {
		this.log2 = log2;
	}
	
	
	
	
	
}
