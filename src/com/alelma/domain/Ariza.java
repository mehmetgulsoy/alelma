package com.alelma.domain;

/*
 * For Table ariza
 */
public class Ariza implements java.io.Serializable, Cloneable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3849185636653605055L;

 

    /* id, PK */
    protected long id;

    /* tanim */
    protected String tanim;

    /* teslim_tarih */
    protected java.util.Date teslimTarih;

    /* uzun_tanim */
    protected String uzunTanim;

    /* kayit_tarih */
    protected java.util.Date kayitTarih; 

    /* id, PK */
    public long getId() {
        return id;
    } 

    public void setId(long id) {
		this.id = id;
	}

	/* tanim */
    public String getTanim() {
        return tanim;
    }

    /* tanim */
    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    /* teslim_tarih */
    public java.util.Date getTeslimTarih() {
        return teslimTarih;
    }

    /* teslim_tarih */
    public void setTeslimTarih(java.util.Date teslimTarih) {
        this.teslimTarih = teslimTarih;
    }

    /* uzun_tanim */
    public String getUzunTanim() {
        return uzunTanim;
    }

    /* uzun_tanim */
    public void setUzunTanim(String uzunTanim) {
        this.uzunTanim = uzunTanim;
    }

    /* kayit_tarih */
    public java.util.Date getKayitTarih() {
        return kayitTarih;
    }

    /* kayit_tarih */
    public void setKayitTarih(java.util.Date kayitTarih) {
        this.kayitTarih = kayitTarih;
    }

    /* Indicates whether some other object is "equal to" this one. */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || !(obj instanceof Ariza))
            return false;

        Ariza bean = (Ariza) obj;

        if (this.id != bean.id)
            return false;

        if (this.tanim == null) {
            if (bean.tanim != null)
                return false;
        }
        else if (!this.tanim.equals(bean.tanim)) 
            return false;

        if (this.teslimTarih == null) {
            if (bean.teslimTarih != null)
                return false;
        }
        else if (!this.teslimTarih.equals(bean.teslimTarih)) 
            return false;

        if (this.uzunTanim == null) {
            if (bean.uzunTanim != null)
                return false;
        }
        else if (!this.uzunTanim.equals(bean.uzunTanim)) 
            return false;

        if (this.kayitTarih == null) {
            if (bean.kayitTarih != null)
                return false;
        }
        else if (!this.kayitTarih.equals(bean.kayitTarih)) 
            return false;

        return true;
    }

    /* Creates and returns a copy of this object. */
    public Object clone()
    {
        Ariza bean = new Ariza();
        bean.id = this.id;
        bean.tanim = this.tanim;
        if (this.teslimTarih != null)
            bean.teslimTarih = (java.util.Date) this.teslimTarih.clone();
        bean.uzunTanim = this.uzunTanim;
        if (this.kayitTarih != null)
            bean.kayitTarih = (java.util.Date) this.kayitTarih.clone();
        return bean;
    }

    /* Returns a string representation of the object. */
    public String toString() {
        String sep = "\r\n";
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getName()).append(sep);
        sb.append("[").append("id").append(" = ").append(id).append("]").append(sep);
        sb.append("[").append("tanim").append(" = ").append(tanim).append("]").append(sep);
        sb.append("[").append("teslimTarih").append(" = ").append(teslimTarih).append("]").append(sep);
        sb.append("[").append("uzunTanim").append(" = ").append(uzunTanim).append("]").append(sep);
        sb.append("[").append("kayitTarih").append(" = ").append(kayitTarih).append("]").append(sep);
        return sb.toString();
    }
}