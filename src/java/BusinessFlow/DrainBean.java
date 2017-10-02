/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.DrainInterface;
import BusinessLogic.Interface.DrainRowInterface;
import BusinessLogic.Interface.ProductInterface;
import BusinessLogic.VirtualEntities.VirtualDrain;
import BusinessLogic.VirtualEntities.VirtualDrainRow;
import BusinessLogic.VirtualEntities.VirtualProduct;
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
public class DrainBean {

    private int id;
    private String ddate;

    private int id_product;
    private double quantity;

    private List<DrainInterface> all;
    private List<DrainRowInterface> drainRows;
    private List<ProductInterface> products;

    private VirtualDrain vdrain;
    private VirtualDrainRow vdrainRow;
    private VirtualProduct vproduct;
    private Database db;
    private Login lg;

    private int result;

    public DrainBean() {

    }

    public Boolean insertDrainAndRow(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vdrain = new VirtualDrain(db);
            vproduct = new VirtualProduct(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                this.id = vdrain.insert();
                vdrainRow = new VirtualDrainRow(id, id_product, quantity, db);
                vdrainRow.insertRow();
                all = new <VirtualDrain>ArrayList();
                all.add(vdrain.getVirtualDrain(this.id));
                drainRows = vdrainRow.getAllRow(id);
                products = vproduct.getAll();
                db.commit();
                db.close();
                return true;
            } else {
                db.commit();
                db.close();
                return false;
            }
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
        }
        return false;
    }

    public Boolean deleteRow(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            lg = new Login(db);
            vdrain = new VirtualDrain(db);
            vdrainRow = new VirtualDrainRow(db);
            vproduct = new VirtualProduct(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vdrainRow = new VirtualDrainRow(id, id_product, quantity, db);
                vdrainRow.deleteRowFree();
                all = new <VirtualDrain>ArrayList();
                all.add(vdrain.getVirtualDrain(this.id));
                drainRows = vdrainRow.getAllRow(id);
                products = vproduct.getAll();
                db.commit();
                db.close();
                return true;
            } else {
                db.commit();
                db.close();
                return false;
            }
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);

        }
        return false;
    }

    public boolean list(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vdrain = new VirtualDrain(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = vdrain.getAll();
                db.commit();
                db.close();
                return true;
            } else {
                db.commit();
                db.close();
                return false;
            }
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
        }
        return false;

    }

    public Boolean getOne(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vdrain = new VirtualDrain(db);
            vdrainRow = new VirtualDrainRow(db);
            vproduct = new VirtualProduct(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = new <VirtualDrain>ArrayList();
                all.add(vdrain.getVirtualDrain(this.id));
                drainRows = vdrainRow.getAllRow(id);
                products = vproduct.getAll();
                db.commit();
                db.close();
                return true;
            } else {
                db.commit();
                db.close();
                return false;
            }
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
        }
        return false;
    }

    public Boolean isLoggedOn(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vdrain = new VirtualDrain(db);
            vproduct = new VirtualProduct(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = new <VirtualDrain>ArrayList();
                if (id != 0) {
                    all.add(vdrain.getVirtualDrain(id));
                }
                products = vproduct.getAll();
                db.commit();
                db.close();
                return true;
            } else {
                db.commit();
                db.close();
                return false;
            }
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);

        }
        return false;
    }

    public Boolean insertRow(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vdrain = new VirtualDrain(db);
            vproduct = new VirtualProduct(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vdrainRow = new VirtualDrainRow(id, id_product, quantity, db);
                vdrainRow.insertRow();
                all = new <VirtualDrain>ArrayList();
                all.add(vdrain.getVirtualDrain(this.id));
                drainRows = vdrainRow.getAllRow(id);
                products = vproduct.getAll();
                db.commit();
                db.close();
                return true;
            } else {
                db.commit();
                db.close();
                return false;
            }
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public List<DrainInterface> getAll() {
        return all;
    }

    public void setAll(List<DrainInterface> all) {
        this.all = all;
    }

    public List<DrainRowInterface> getDrainRows() {
        return drainRows;
    }

    public void setDrainRows(List<DrainRowInterface> drainRows) {
        this.drainRows = drainRows;
    }

    public List<ProductInterface> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInterface> products) {
        this.products = products;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
