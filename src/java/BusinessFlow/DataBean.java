/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.DataInterface;
import BusinessLogic.VirtualEntities.VirtualData;
import Services.Database.Database;
import Services.Database.DatabaseService;
import Services.Exception.PersonalizedException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class DataBean {

    private String rag_soc;
    private String responsabile;
    private String domain;
    private String piva;
    private String codfisc;
    private String indirizzo;
    private String civico;
    private String cap;
    private String city;
    private String provincia;
    private String main_mail;
    private String contact_from_mail;
    private String sysadmin_mail;
    private String smtp_srv;
    private String pop3_srv;
    private byte[] logo;

    private VirtualData vd;
    private DataInterface data;
    private Database db;
    private Login lg;
    
    private int result;

    public DataBean() {

    }

    public Boolean update(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            VirtualData vd = new VirtualData(this.rag_soc, this.responsabile, this.domain, this.piva, this.codfisc, this.indirizzo, this.civico, this.cap, this.city, this.provincia, this.main_mail, this.contact_from_mail, this.sysadmin_mail, this.smtp_srv, this.pop3_srv, this.logo, this.db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vd.update();
                data=vd.getData();
                db.commit();
                db.close();
                return true;
            } else {
                db.commit();
                db.close();
                return false;
            }
        } catch (Exception e) {
            result=PersonalizedException.logAndRecoverException(e,db);
            db.rollBack();

        }
        return false;
    }

    public boolean list(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vd = new VirtualData(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                data=vd.getData();
                db.commit();
                db.close();
                return true;
            } else {
                db.commit();
                db.close();
                return false;
            }
        } catch (Exception e) {
            result=PersonalizedException.logAndRecoverException(e,db);
            db.rollBack();
        }
        return false;

    }

    public Boolean isLoggeOn(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vd = new VirtualData(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                data=vd.getData();
                db.commit();
                db.close();
                return true;
            } else {
                db.commit();
                db.close();
                return false;
            }
        } catch (Exception e) {
            result=PersonalizedException.logAndRecoverException(e,db);
            db.rollBack();

        }
        return false;
    }

    public String getRag_soc() {
        return rag_soc;
    }

    public void setRag_soc(String rag_soc) {
        this.rag_soc = rag_soc;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getCodfisc() {
        return codfisc;
    }

    public void setCodfisc(String codfisc) {
        this.codfisc = codfisc;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMain_mail() {
        return main_mail;
    }

    public void setMain_mail(String main_mail) {
        this.main_mail = main_mail;
    }

    public String getContact_from_mail() {
        return contact_from_mail;
    }

    public void setContact_from_mail(String contact_from_mail) {
        this.contact_from_mail = contact_from_mail;
    }

    public String getSysadmin_mail() {
        return sysadmin_mail;
    }

    public void setSysadmin_mail(String sysadmin_mail) {
        this.sysadmin_mail = sysadmin_mail;
    }

    public String getSmtp_srv() {
        return smtp_srv;
    }

    public void setSmtp_srv(String smtp_srv) {
        this.smtp_srv = smtp_srv;
    }

    public String getPop3_srv() {
        return pop3_srv;
    }

    public void setPop3_srv(String pop3_srv) {
        this.pop3_srv = pop3_srv;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
    
    public DataInterface getData()
    {
        return data;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
    
}
