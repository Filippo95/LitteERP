/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.PeopleInterface;
import BusinessLogic.Interface.ProductInterface;
import BusinessLogic.Interface.SupplyInterface;
import BusinessLogic.VirtualEntities.VirtualPeople;
import BusinessLogic.VirtualEntities.VirtualProduct;
import BusinessLogic.VirtualEntities.VirtualSupply;
import Services.Database.Database;
import Services.Database.DatabaseService;
import Services.Exception.PersonalizedException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.Cookie;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class SupplyBean {

    private int id;
    private int id_product;
    private int id_provider;
    private double quantity;
    private double provider_unit_price;
    private GregorianCalendar last_mod;

    private List<SupplyInterface> all;
    private List<ProductInterface> products;
    private List<PeopleInterface> providers;
    
    private VirtualSupply vs;
    private VirtualProduct vp;
    private VirtualPeople prov;
    private Database db;
    private Login lg;
    
    private int result;

    public SupplyBean() {

    }

    public Boolean update(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vs = new VirtualSupply(this.id, this.id_product, this.id_provider, this.quantity, this.provider_unit_price, this.last_mod, this.db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vs.update();
                all = vs.getAll();
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
            vs = new VirtualSupply(this.id, this.id_product, this.id_provider, this.quantity, this.provider_unit_price, this.last_mod, this.db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vs.insert();
                all = vs.getAll();
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
            vs = new VirtualSupply(this.db);
            vs.setId(this.id);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vs.delete();
                all = vs.getAll();
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
            vs = new VirtualSupply(db);
            vp = new VirtualProduct(db);
            prov = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = vs.getAll();
                db.commit();
                db.close();
                return true;
            } else {
                db.commit();
                db.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println("Prima");
            result=PersonalizedException.logAndRecoverException(e,db);
            db.rollBack();
        }
        return false;

    }

    public Boolean SupplyInsertActionModify(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vs = new VirtualSupply(db);
            vp = new VirtualProduct(db);
            prov = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = new <VirtualSupply>ArrayList();
                all.add(vs.getVirtualSupply(this.id));
                products=vp.getAll();
                providers=prov.getAllProviders();
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

    public Boolean SupplyInsertActionInsert(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            lg = new Login(db);
            vp = new VirtualProduct(db);
            prov = new VirtualPeople(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                products=vp.getAll();
                providers=prov.getAllProviders();
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

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_provider() {
        return id_provider;
    }

    public void setId_provider(int id_provider) {
        this.id_provider = id_provider;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getProvider_unit_price() {
        return provider_unit_price;
    }

    public void setProvider_unit_price(double provider_unit_price) {
        this.provider_unit_price = provider_unit_price;
    }

    public GregorianCalendar getLast_mod() {
        return last_mod;
    }

    public void setLast_mod(GregorianCalendar last_mod) {
        this.last_mod = last_mod;
    }

    public List<SupplyInterface> getAll() {
        return all;
    }

    public List<ProductInterface> getProducts() {
        return products;
    }

    public List<PeopleInterface> getProviders() {
        return providers;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
