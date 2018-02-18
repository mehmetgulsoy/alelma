/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alelma.wiew;

import com.alelma.domain.Ariza;
import com.alelma.domain.dao.ArizaDAO;
import com.alelma.domain.orm.ArizaDAOImpl;
import java.sql.SQLException;
import java.util.Date; 
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
 

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

    public UrunKayit() { 
    }    

    public UrunKayit(String kısaTanım, Date teslimTarihi, String uzunTanım) {
        this.kısaTanım = kısaTanım;
        this.teslimTarihi = teslimTarihi;
        this.uzunTanım = uzunTanım;
    }
    
    public void save(){
         
        Ariza ariza = new Ariza();
        ariza.setTeslimTarih(teslimTarihi);
        ariza.setTanim(kısaTanım);
        ariza.setUzunTanim(uzunTanım);   
  
        ArizaDAO arizaDAO = new ArizaDAOImpl();        
        try {
            arizaDAO.create(ariza);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "İşlem Başarılı.Kayıt Yapıldı",  null);
            FacesContext.getCurrentInstance().addMessage(null, message);            
            
        } catch (SQLException ex) {
            Logger.getLogger(UrunKayit.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata Oluştu.Kayit Yapılamadı.",  null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }       
        
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

}
