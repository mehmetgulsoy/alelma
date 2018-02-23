package com.alelma.dto;

/**
 * This class is created by SQL DAL Maker. Don't modify it manually.
 * SQL DAL Maker project web-site: http://sqldalmaker.sourceforge.net
 */
public class Ariza  {

	private Integer id;
	private String tanim;
	private java.util.Date teslimtarih;
	private String uzuntanim;
	private java.util.Date kayittarih;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTanim() {
		return this.tanim;
	}

	public void setTanim(String tanim) {
		this.tanim = tanim;
	}

	public java.util.Date getTeslimtarih() {
		return this.teslimtarih;
	}

	public void setTeslimtarih(java.util.Date teslimtarih) {
		this.teslimtarih = teslimtarih;
	}

	public String getUzuntanim() {
		return this.uzuntanim;
	}

	public void setUzuntanim(String uzuntanim) {
		this.uzuntanim = uzuntanim;
	}

	public java.util.Date getKayittarih() {
		return this.kayittarih;
	}

	public void setKayittarih(java.util.Date kayittarih) {
		this.kayittarih = kayittarih;
	}
}