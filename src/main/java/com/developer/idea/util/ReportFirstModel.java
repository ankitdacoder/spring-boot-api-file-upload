package com.developer.idea.util;

import java.math.BigDecimal;

public class ReportFirstModel {

	private String sample;
	private String wellId;
	private String od;
	private BigDecimal sp;
	private double titer;
	private long group;
	private String result;
	public String getSample() {
		return sample;
	}
	public void setSample(String sample) {
		this.sample = sample;
	}
	public String getWellId() {
		return wellId;
	}
	public void setWellId(String wellId) {
		this.wellId = wellId;
	}
	public String getOd() {
		return od;
	}
	public void setOd(String od) {
		this.od = od;
	}
	public BigDecimal getSp() {
		return sp;
	}
	public void setSp(BigDecimal sp) {
		this.sp = sp;
	}
	public double getTiter() {
		return titer;
	}
	public void setTiter(double titer) {
		this.titer = titer;
	}
	public long getGroup() {
		return group;
	}
	public void setGroup(long group) {
		this.group = group;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public ReportFirstModel(String sample, String wellId, String od, BigDecimal sp, double titer, long group, String result) {
		super();
		this.sample = sample;
		this.wellId = wellId;
		this.od = od;
		this.sp = sp;
		this.titer = titer;
		this.group = group;
		this.result = result;
	}
	
	
}
