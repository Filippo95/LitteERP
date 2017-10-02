/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.PeopleInterface;
import BusinessLogic.VirtualEntities.VirtualPeople;
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
public class PeopleBean {

    private int id;
    private String name;
    private String refer_to;
    private String email;
    private String telephone;
    private String cod_fisc;
    private String piva;
    private String address;
    private String civic;
    private String cap;
    private String city;
    private String province;
    private Boolean provider;
    private Boolean customer;

    private List<PeopleInterface> all;
    private VirtualPeople vp;
    private Database db;
    private Login lg;
    
    private int result;

    public PeopleBean() {

    }

    public Boolean update(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vp = new VirtualPeople(this.id, this.name, this.refer_to, this.email, this.telephone, this.cod_fisc, this.piva, this.address, this.civic, this.cap, this.city, this.province, this.provider, this.customer, this.db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vp.update();
                all = new <VirtualPeople>ArrayList();
                all.add(vp.getVirtualPeople(this.id));
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

    public Boolean insert(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vp = new VirtualPeople(0, this.name, this.refer_to, this.email, this.telephone, this.cod_fisc, this.piva, this.address, this.civic, this.cap, this.city, this.province, this.provider, this.customer, this.db);
            
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                id=vp.insert();
                all = new <VirtualPeople>ArrayList();
                all.add(vp.getVirtualPeople(this.id));
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

    public Boolean delete(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vp = new VirtualPeople(this.db);
            vp.setId(this.id);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vp.delete();
                all = vp.getAll();
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
            vp = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = vp.getAll();
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

    public Boolean getOne(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vp = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = new <VirtualPeople>ArrayList();
                all.add(vp.getVirtualPeople(this.id));
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
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefer_to() {
        return refer_to;
    }

    public void setRefer_to(String refer_to) {
        this.refer_to = refer_to;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCod_fisc() {
        return cod_fisc;
    }

    public void setCod_fisc(String cod_fisc) {
        this.cod_fisc = cod_fisc;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCivic() {
        return civic;
    }

    public void setCivic(String civic) {
        this.civic = civic;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Boolean getProvider() {
        return provider;
    }

    public void setProvider(Boolean provider) {
        this.provider = provider;
    }

    public Boolean getCustomer() {
        return customer;
    }

    public void setCustomer(Boolean customer) {
        this.customer = customer;
    }

    public List<PeopleInterface> getAll() {
        return all;
    }

    public void setAll(List<PeopleInterface> all) {
        this.all = all;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
