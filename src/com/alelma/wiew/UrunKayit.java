/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alelma.wiew;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.alelma.dao.ArizaDao;
import com.alelma.dao.DataStoreManager;
import com.alelma.dto.Ariza;

/**
 *
 * @author Mehmet
 */
@ManagedBean
@SessionScoped
public class UrunKayit {

	private String kısaTanım;
	private Date teslimTarihi;
	private String uzunTanım;
	private DataStoreManager dm;
	private ArizaDao arizaDAO;

	public UrunKayit() {

	}

	public UrunKayit(String kısaTanım, Date teslimTarihi, String uzunTanım) {
		this.kısaTanım = kısaTanım;
		this.teslimTarihi = teslimTarihi;
		this.uzunTanım = uzunTanım;
	}

	public String getKısaTanım() {
		return kısaTanım;
	}

	public void setKısaTanım(String kısaTanım) {
		this.kısaTanım = kısaTanım;
	}

	public Date getTeslimTarihi() {
		return teslimTarihi;
	}

	public void setTeslimTarihi(Date teslimTarihi) {
		this.teslimTarihi = teslimTarihi;
	}

	public String getUzunTanım() {
		return uzunTanım;
	}

	public void setUzunTanım(String uzunTanım) {
		this.uzunTanım = uzunTanım;
	}

	public void save() {

		Ariza ariza = new Ariza();
		ariza.setTeslimtarih(getTeslimTarihi());

		ariza.setTanim(getKısaTanım());
		ariza.setUzuntanim(getUzunTanım());

		try {
			dm = new DataStoreManager();
			dm.open();
			arizaDAO = dm.createArizaDao();
			arizaDAO.createAriza(ariza);
			dm.close();

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "İşlem Başarılı.Kayıt Yapıldı", null);
			FacesContext.getCurrentInstance().addMessage(null, message);

		} catch (Exception ex) {
			Logger.getLogger(UrunKayit.class.getName()).log(Level.SEVERE, null, ex);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Oluştu.Kayit Yapılamadı.", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}

	public ArizaDao getArizaDAO() {
		return arizaDAO;
	}

	public void setArizaDAO(ArizaDao arizaDAO) {
		this.arizaDAO = arizaDAO;
	}

}
