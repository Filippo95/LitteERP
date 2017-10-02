/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.BrandInterface;
import BusinessLogic.VirtualEntities.VirtualBrand;
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
public class BrandBean {

    private int id;
    private String name;
    private String description;

    private List<BrandInterface> all;
    private VirtualBrand vb;
    private Database db;
    private Login lg;
    
    private int result;

    public BrandBean() {

    }

    public Boolean update(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vb = new VirtualBrand(this.id, this.name, this.description, this.db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vb.update();
                all = vb.getAll();
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
            vb = new VirtualBrand(this.id, this.name, this.description, this.db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vb.insert();
                all = vb.getAll();
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
            vb = new VirtualBrand(this.db);
            vb.setId(this.id);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vb.delete();
                all = vb.getAll();
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
            vb = new VirtualBrand(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = vb.getAll();
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
            vb = new VirtualBrand(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all=new <VirtualBrand>ArrayList();
                all.add(vb.getVirtualBrand(this.id));
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
    
    public Boolean isLoggeOn(Cookie[] cookies)
    {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BrandInterface> getAll() {
        return all;
    }

    public void setAll(List<BrandInterface> all) {
        this.all = all;
    }   

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
