/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.InvoiceInterface;
import BusinessLogic.Interface.InvoiceRowInterface;
import BusinessLogic.Interface.PeopleInterface;
import BusinessLogic.Interface.ProductInterface;
import BusinessLogic.VirtualEntities.VirtualBrand;
import BusinessLogic.VirtualEntities.VirtualCategory;
import BusinessLogic.VirtualEntities.VirtualDatasheet;
import BusinessLogic.VirtualEntities.VirtualDescription;
import BusinessLogic.VirtualEntities.VirtualDrain;
import BusinessLogic.VirtualEntities.VirtualDrainRow;
import BusinessLogic.VirtualEntities.VirtualImage;
import BusinessLogic.VirtualEntities.VirtualInvoice;
import BusinessLogic.VirtualEntities.VirtualInvoiceRow;
import BusinessLogic.VirtualEntities.VirtualLanguage;
import BusinessLogic.VirtualEntities.VirtualPeople;
import BusinessLogic.VirtualEntities.VirtualProduct;
import BusinessLogic.VirtualEntities.VirtualUnit;
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
public class InvoiceBean {

    private int id;
    private GregorianCalendar idate;
    private int id_people;
    private int id_drain;
    private int expiring_days;
    private Boolean payed;
    private int id_quote;

    private String description;
    private int id_product;
    private double quantity;
    private double subtot;
    private double price;

    private List<InvoiceInterface> all;
    private List<InvoiceRowInterface> invoiceRowsFree;
    private List<InvoiceRowInterface> invoiceRowsProduct;
    private List<ProductInterface> products;
    private List<PeopleInterface> people;
    private VirtualProduct vprod;
    private VirtualPeople vpeople;
    private VirtualInvoice vinvoice;
    private VirtualInvoiceRow vinvoiceRow;
    private VirtualDrain vdrain;
    private VirtualDrainRow vdrainrow;
    private Database db;
    private Login lg;

    private int result;

    private Boolean ddtExists;

    private VirtualCategory vc = null;
    private VirtualBrand vb = null;
    private VirtualUnit vu = null;
    private VirtualDescription vd = null;
    private VirtualLanguage vl = null;
    private VirtualImage vi = null;
    private VirtualDatasheet vdt = null;

    public InvoiceBean() {

    }

    public Boolean update(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vinvoice = new VirtualInvoice(this.id, this.expiring_days, this.payed, this.id_people, this.id_drain, this.db);
            vinvoiceRow = new VirtualInvoiceRow(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vinvoice.update();
                all = new <VirtualInvoice>ArrayList();
                all.add(vinvoice.getVirtualInvoice(this.id));
                invoiceRowsFree = vinvoiceRow.getAllRowFree(id);
                invoiceRowsProduct = vinvoiceRow.getAllRowProduct(id);
                ddtExists = vinvoice.existDdt(all.get(0).getId_drain());
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

            vinvoiceRow = new VirtualInvoiceRow(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vdrain = new VirtualDrain(db);
                this.id_drain = vdrain.insert();
                vinvoice = new VirtualInvoice(this.id, this.expiring_days, this.payed, this.id_people, this.id_drain, this.db);
                this.id = vinvoice.insert();
                all = new <VirtualInvoice>ArrayList();
                all.add(vinvoice.getVirtualInvoice(this.id));
                invoiceRowsFree = vinvoiceRow.getAllRowFree(id);
                invoiceRowsProduct = vinvoiceRow.getAllRowProduct(id);
                ddtExists = vinvoice.existDdt(all.get(0).getId_drain());
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
            vinvoice = new VirtualInvoice(db);
            vinvoiceRow = new VirtualInvoiceRow(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                switch (choice) {
                    case "rowfree": {
                        vinvoiceRow = new VirtualInvoiceRow(id, description, quantity, subtot, db);
                        vinvoiceRow.deleteRowFree();
                        break;
                    }
                    case "rowproduct": {
                        vinvoiceRow = new VirtualInvoiceRow(id, id_product, quantity, price, db);
                        vinvoiceRow.deleteRowProduct();
                        vdrainrow = new VirtualDrainRow(id_drain, id_product, quantity, db);
                        vdrainrow.deleteRowFree();
                        break;
                    }
                }
                all = new <VirtualInvoice>ArrayList();
                all.add(vinvoice.getVirtualInvoice(this.id));
                invoiceRowsFree = vinvoiceRow.getAllRowFree(id);
                invoiceRowsProduct = vinvoiceRow.getAllRowProduct(id);
                ddtExists = vinvoice.existDdt(all.get(0).getId_drain());
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
            db = DatabaseService.getDataBase();
            vinvoice = new VirtualInvoice(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = vinvoice.getAll();
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
            vinvoice = new VirtualInvoice(db);
            vinvoiceRow = new VirtualInvoiceRow(db);
            vprod = new VirtualProduct(db);
            vpeople = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = new <VirtualInvoice>ArrayList();
                all.add(vinvoice.getVirtualInvoice(this.id));
                invoiceRowsFree = vinvoiceRow.getAllRowFree(id);
                invoiceRowsProduct = vinvoiceRow.getAllRowProduct(id);
                ddtExists = vinvoice.existDdt(all.get(0).getId_drain());
                people = vpeople.getAll();
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

    public Boolean getOneByIdDrain(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vinvoice = new VirtualInvoice(db);
            vinvoiceRow = new VirtualInvoiceRow(db);
            vprod = new VirtualProduct(db);
            vpeople = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                ddtExists = true;
                all = new <VirtualInvoice>ArrayList();
                all.add(vinvoice.getVirtualInvoiceByDrainId(id_drain));
                invoiceRowsFree = vinvoiceRow.getAllRowFree(all.get(0).getId());
                invoiceRowsProduct = vinvoiceRow.getAllRowProduct(all.get(0).getId());
                people = vpeople.getAll();
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
            vinvoice = new VirtualInvoice(db);
            vprod = new VirtualProduct(db);
            vpeople = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = new <VirtualInvoice>ArrayList();
                if (id != 0) {
                    all.add(vinvoice.getVirtualInvoice(id));
                }
                people = vpeople.getAll();
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

    public Boolean insertRow(Cookie[] cookies, String choice) {
        db = null;

        try {
            db = DatabaseService.getDataBase();
            vinvoice = new VirtualInvoice(db);
            vprod = new VirtualProduct(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                switch (choice) {
                    case "rowfree": {
                        vinvoiceRow = new VirtualInvoiceRow(id, description, quantity, subtot, db);
                        vinvoiceRow.insertRowFree();
                        break;
                    }
                    case "rowproduct": {
                        vinvoiceRow = new VirtualInvoiceRow(id, id_product, quantity, price, db);
                        vinvoiceRow.insertRowProduct();
                        vdrainrow = new VirtualDrainRow(id_drain, id_product, quantity, db);
                        vdrainrow.insertRow();
                        break;
                    }
                }
                all = new <VirtualInvoice>ArrayList();
                all.add(vinvoice.getVirtualInvoice(this.id));
                invoiceRowsFree = vinvoiceRow.getAllRowFree(id);
                invoiceRowsProduct = vinvoiceRow.getAllRowProduct(id);
                ddtExists = vinvoice.existDdt(all.get(0).getId_drain());
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

    public Boolean generateFromOtherDocument(Cookie[] cookies, String documentType) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vinvoice = new VirtualInvoice(this.db);
                switch (documentType) {
                    case "drain": {
                        System.out.println("ok");
                        this.id = vinvoice.insertFromDrainId(id_drain);
                        vinvoiceRow = new VirtualInvoiceRow(db);
                        vinvoiceRow.insertRowsFromDrain(id_drain, id);
                        break;
                    }
                    case "quote": {
                        //Genero il Drain
                        vdrain = new VirtualDrain(db);
                        this.id_drain = vdrain.insert();
                        vinvoice.setId_drain(id_drain);
                        //Genero la fattura
                        this.id = vinvoice.insertFromQuote(id_quote);
                        vinvoiceRow = new VirtualInvoiceRow(db);
                        vinvoiceRow.insertRowsFromQuote(id_quote, id);
                        break;
                    }
                }
                all = new <VirtualInvoice>ArrayList();
                all.add(vinvoice.getVirtualInvoice(this.id));
                invoiceRowsFree = vinvoiceRow.getAllRowFree(id);
                invoiceRowsProduct = vinvoiceRow.getAllRowProduct(id);
                ddtExists = vinvoice.existDdt(all.get(0).getId_drain());
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

    public GregorianCalendar getIdate() {
        return idate;
    }

    public void setIdate(GregorianCalendar idate) {
        this.idate = idate;
    }

    public int getId_people() {
        return id_people;
    }

    public void setId_people(int id_people) {
        this.id_people = id_people;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
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

    public int getExpiring_days() {
        return expiring_days;
    }

    public void setExpiring_days(int expiring_days) {
        this.expiring_days = expiring_days;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    public double getSubtot() {
        return subtot;
    }

    public void setSubtot(double subtot) {
        this.subtot = subtot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<InvoiceInterface> getAll() {
        return all;
    }

    public List<InvoiceRowInterface> getInvoiceRowsFree() {
        return invoiceRowsFree;
    }

    public List<InvoiceRowInterface> getInvoiceRowsProduct() {
        return invoiceRowsProduct;
    }

    public List<ProductInterface> getProducts() {
        return products;
    }

    public List<PeopleInterface> getPeople() {
        return people;
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

    public Boolean getDdtExists() {
        return ddtExists;
    }

    public int getId_quote() {
        return id_quote;
    }

    public void setId_quote(int id_quote) {
        this.id_quote = id_quote;
    }

}
