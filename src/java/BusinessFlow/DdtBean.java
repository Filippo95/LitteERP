/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.DdtInterface;
import BusinessLogic.Interface.DdtRowInterface;
import BusinessLogic.Interface.PeopleInterface;
import BusinessLogic.Interface.ProductInterface;
import BusinessLogic.VirtualEntities.VirtualPeople;
import BusinessLogic.VirtualEntities.VirtualDdt;
import BusinessLogic.VirtualEntities.VirtualDdtRow;
import BusinessLogic.VirtualEntities.VirtualDrain;
import BusinessLogic.VirtualEntities.VirtualDrainRow;
import BusinessLogic.VirtualEntities.VirtualInvoice;
import BusinessLogic.VirtualEntities.VirtualProduct;
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
public class DdtBean {

    private int id;
    private GregorianCalendar ddate;
    private int id_people;
    private int id_people_vett;
    private int id_drain;
    private String destination;
    private String cause;
    private String aspect;
    private String ncoll;
    private int id_quote;

    private String description;
    private int id_product;
    private double quantity;
    private double unit_price;
    private double price;

    private Boolean existInvoice;

    private int result;

    private List<DdtInterface> all;
    private List<DdtRowInterface> ddtRowsFree;
    private List<DdtRowInterface> ddtRowsProduct;
    private List<PeopleInterface> people;
    private List<ProductInterface> products;
    private VirtualDdt vddt;
    private VirtualDdtRow vddtRow;
    private VirtualPeople vp;
    private VirtualProduct vprod;
    private VirtualDrain vdrain;
    private VirtualInvoice vinvoice;
    private VirtualDrainRow vdrainrow;
    private Database db;
    private Login lg;

    public DdtBean() {

    }

    public Boolean update(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vddt = new VirtualDdt(this.id, this.id_people, this.id_people_vett, this.id_drain, this.destination, this.cause, this.aspect, this.ncoll, this.db);
            vddtRow = new VirtualDdtRow(db);
            vprod = new VirtualProduct(db);
            vp = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                //Aggiorno il ddt
                vddt.update();
                all = new <VirtualDdt>ArrayList();
                //Aggiungo alla lista un solo ddt, quello che dovrò far vedere
                all.add(vddt.getVirtualDdt(this.id));
                //Controllo l'esistenza della corrispondente fattura
                existInvoice = vddt.existInvoice(all.get(0).getId_drain());
                people = vp.getAll();
                ddtRowsFree = vddtRow.getAllRowFree(id);
                ddtRowsProduct = vddtRow.getAllRowProduct(id);
                products = vprod.getAll();
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
            db.rollBack();

        }
        return false;
    }

    public Boolean insert(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vddtRow = new VirtualDdtRow(db);
            vprod = new VirtualProduct(db);
            vp = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vdrain = new VirtualDrain(db);
                this.id_drain = vdrain.insert();
                vddt = new VirtualDdt(this.id, this.id_people, this.id_people_vett, this.id_drain, this.destination, this.cause, this.aspect, this.ncoll, this.db);
                this.id = vddt.insert();
                all = new <VirtualDdt>ArrayList();
                all.add(vddt.getVirtualDdt(this.id));
                //Controllo l'esistenza della corrispondente fattura
                existInvoice = vddt.existInvoice(all.get(0).getId_drain());
                people = vp.getAll();
                products = vprod.getAll();
                ddtRowsFree = vddtRow.getAllRowFree(id);
                ddtRowsProduct = vddtRow.getAllRowProduct(id);
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
            db.rollBack();

        }
        return false;
    }

    public Boolean insertFromQuote(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vprod = new VirtualProduct(db);
            vp = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                //Genero il drain
                vdrain = new VirtualDrain(db);
                this.id_drain = vdrain.insert();
                vddt = new VirtualDdt(this.db);
                /*Setto id_drain nel vddt instanziato*/
                vddt.setId_drain(id_drain);
                this.id = vddt.insertFromQuote(id_quote);
                vddtRow = new VirtualDdtRow(db);
                vddtRow.insertRowsFromQuote(id_quote, id);
                //Ottengo i particolari del ddt che poi mostrerò
                all = new <VirtualDdt>ArrayList();
                all.add(vddt.getVirtualDdt(this.id));
                all.get(0).setClientName("");
                all.get(0).setVettName("");
                //Controllo l'esistenza della corrispondente fattura
                existInvoice = vddt.existInvoice(all.get(0).getId_drain());
                people = vp.getAll();
                products = vprod.getAll();
                ddtRowsFree = vddtRow.getAllRowFree(id);
                ddtRowsProduct = vddtRow.getAllRowProduct(id);
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
            db.rollBack();

        }
        return false;
    }

    public Boolean deleteRow(Cookie[] cookies, String choice) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            lg = new Login(db);
            vddt = new VirtualDdt(db);
            vddtRow = new VirtualDdtRow(db);
            vp = new VirtualPeople(db);
            vprod = new VirtualProduct(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                switch (choice) {
                    case "rowfree": {
                        vddtRow = new VirtualDdtRow(id, description, quantity, unit_price, db);
                        vddtRow.deleteRowFree();
                        break;
                    }
                    case "rowproduct": {
                        vddtRow = new VirtualDdtRow(id, id_product, quantity, price, db);
                        vddtRow.deleteRowProduct();
                        vdrainrow = new VirtualDrainRow(id_drain, id_product, quantity, db);
                        vdrainrow.deleteRowFree();
                        break;
                    }
                }
                all = new <VirtualDdt>ArrayList();
                all.add(vddt.getVirtualDdt(this.id));
                //Controllo l'esistenza della corrispondente fattura
                existInvoice = vddt.existInvoice(all.get(0).getId_drain());
                people = vp.getAll();
                products = vprod.getAll();
                ddtRowsFree = vddtRow.getAllRowFree(id);
                ddtRowsProduct = vddtRow.getAllRowProduct(id);
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
            db.rollBack();

        }
        return false;
    }

    public boolean list(Cookie[] cookies) {
        db = null;
        try {
            System.out.println("Prima");
            db = DatabaseService.getDataBase();
            vddt = new VirtualDdt(db);
            vp = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = vddt.getAll();
                for (int i = 0; i < all.size(); i++) {
                    people = vp.getAll();
                }
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
            db.rollBack();
        }
        return false;

    }

    public Boolean getOne(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vddt = new VirtualDdt(db);
            vddtRow = new VirtualDdtRow(db);
            vprod = new VirtualProduct(db);
            vp = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = new <VirtualDdt>ArrayList();
                all.add(vddt.getVirtualDdt(this.id));
                //Controllo l'esistenza della corrispondente fattura
                existInvoice = vddt.existInvoice(all.get(0).getId_drain());
                people = vp.getAll();
                ddtRowsFree = vddtRow.getAllRowFree(id);
                ddtRowsProduct = vddtRow.getAllRowProduct(id);
                products = vprod.getAll();
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
            db.rollBack();

        }
        return false;
    }

    public Boolean isLoggedOn(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vddt = new VirtualDdt(db);
            vp = new VirtualPeople(db);
            vprod = new VirtualProduct(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = new <VirtualDdt>ArrayList();
                if (id != 0) {
                    all.add(vddt.getVirtualDdt(this.id));
                }
                people = vp.getAll();
                products = vprod.getAll();
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

    public Boolean insertRow(Cookie[] cookies, String choice) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vp = new VirtualPeople(db);
            vddt = new VirtualDdt(db);
            vprod = new VirtualProduct(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                switch (choice) {
                    case "rowfree": {
                        vddtRow = new VirtualDdtRow(id, description, quantity, unit_price, db);
                        vddtRow.insertRowFree();
                        break;
                    }
                    case "rowproduct": {
                        vddtRow = new VirtualDdtRow(id, id_product, quantity, price, db);
                        vddtRow.insertRowProduct();
                        vdrainrow = new VirtualDrainRow(id_drain, id_product, quantity, db);
                        vdrainrow.insertRow();
                        break;
                    }
                }
                all = new <VirtualDdt>ArrayList();
                all.add(vddt.getVirtualDdt(this.id));
                //Controllo l'esistenza della corrispondente fattura
                existInvoice = vddt.existInvoice(all.get(0).getId_drain());
                people = vp.getAll();
                products = vprod.getAll();
                ddtRowsFree = vddtRow.getAllRowFree(id);
                ddtRowsProduct = vddtRow.getAllRowProduct(id);
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
            db.rollBack();
        }
        return false;
    }

    public Boolean getOneByIdDrain(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vddt = new VirtualDdt(db);
            vddtRow = new VirtualDdtRow(db);
            vprod = new VirtualProduct(db);
            vp = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                existInvoice = true;
                all = new <VirtualDdt>ArrayList();
                all.add(vddt.getVirtualDdtByDrainId(this.id_drain));
                people = vp.getAll();
                ddtRowsFree = vddtRow.getAllRowFree(all.get(0).getId());
                ddtRowsProduct = vddtRow.getAllRowProduct(all.get(0).getId());
                products = vprod.getAll();
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

    public GregorianCalendar getDdate() {
        return ddate;
    }

    public void setDdate(GregorianCalendar ddate) {
        this.ddate = ddate;
    }

    public int getId_people() {
        return id_people;
    }

    public void setId_people(int id_people) {
        this.id_people = id_people;
    }

    public int getId_people_vett() {
        return id_people_vett;
    }

    public void setId_people_vett(int id_people_vett) {
        this.id_people_vett = id_people_vett;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public String getNcoll() {
        return ncoll;
    }

    public void setNcoll(String ncoll) {
        this.ncoll = ncoll;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<DdtInterface> getAll() {
        return all;
    }

    public List<PeopleInterface> getPeople() {
        return people;
    }

    public List<DdtRowInterface> getDdtRowsFree() {
        return ddtRowsFree;
    }

    public List<DdtRowInterface> getDdtRowsProduct() {
        return ddtRowsProduct;
    }

    public List<ProductInterface> getProducts() {
        return products;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getId_drain() {
        return id_drain;
    }

    public void setId_drain(int id_drain) {
        this.id_drain = id_drain;
    }

    public Boolean getExistInvoice() {
        return existInvoice;
    }

    public int getId_quote() {
        return id_quote;
    }

    public void setId_quote(int id_quote) {
        this.id_quote = id_quote;
    }

}
