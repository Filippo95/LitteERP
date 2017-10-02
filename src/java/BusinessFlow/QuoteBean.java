/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.PeopleInterface;
import BusinessLogic.Interface.ProductInterface;
import BusinessLogic.Interface.QuoteInterface;
import BusinessLogic.Interface.QuoteRowInterface;
import BusinessLogic.VirtualEntities.VirtualQuote;
import BusinessLogic.VirtualEntities.VirtualQuoteRow;
import BusinessLogic.VirtualEntities.VirtualPeople;
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
public class QuoteBean {

    private int id;
    private int id_people;
    String description;
    private String expiring_date;
    private Boolean approved;

    private int id_product;
    private double quantity;
    private double subtot;
    private double price;

    private List<QuoteInterface> all;
    private List<QuoteRowInterface> quoteRowsFree;
    private List<QuoteRowInterface> quoteRowsProduct;
    private List<ProductInterface> products;
    private List<PeopleInterface> people;
    private VirtualProduct vprod;
    private VirtualPeople vpeople;
    private VirtualQuote vquote;
    private VirtualQuoteRow vquoteRow;
    private Database db;
    private Login lg;
    
    private int result;

    public QuoteBean() {

    }

    public Boolean update(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vquote = new VirtualQuote(this.id, this.id_people, this.description, this.expiring_date, this.approved, this.db);
            vquoteRow = new VirtualQuoteRow(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vquote.update();
                all = new <VirtualQuote>ArrayList();
                all.add(vquote.getVirtualQuote(this.id));
                quoteRowsFree = vquoteRow.getAllRowFree(id);
                quoteRowsProduct = vquoteRow.getAllRowProduct(id);
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
            vquote = new VirtualQuote(this.id, this.id_people, this.description, this.expiring_date, this.approved, this.db);
            vquoteRow = new VirtualQuoteRow(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                
                this.id = vquote.insert();
                System.out.append("Inseeito");
                //TODO: Generare Scarico
                all = new <VirtualQuote>ArrayList();
                all.add(vquote.getVirtualQuote(this.id));
                quoteRowsFree = vquoteRow.getAllRowFree(id);
                quoteRowsProduct = vquoteRow.getAllRowProduct(id);
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

    public Boolean deleteRow(Cookie[] cookies, String choice) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            lg = new Login(db);
            vquote = new VirtualQuote(db);
            vquoteRow = new VirtualQuoteRow(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                switch (choice) {
                    case "rowfree": {
                        vquoteRow = new VirtualQuoteRow(id, description, quantity, subtot, db);
                        vquoteRow.deleteRowFree();
                        break;
                    }
                    case "rowproduct": {
                        vquoteRow = new VirtualQuoteRow(id, id_product, quantity, price, db);
                        vquoteRow.deleteRowProduct();
                        break;
                    }
                }
                all = new <VirtualQuote>ArrayList();
                all.add(vquote.getVirtualQuote(this.id));
                quoteRowsFree = vquoteRow.getAllRowFree(id);
                quoteRowsProduct = vquoteRow.getAllRowProduct(id);
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
            vquote = new VirtualQuote(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = vquote.getAll();
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
            vquote = new VirtualQuote(db);
            vquoteRow = new VirtualQuoteRow(db);
            vprod = new VirtualProduct(db);
            vpeople = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = new <VirtualQuote>ArrayList();
                all.add(vquote.getVirtualQuote(this.id));
                quoteRowsFree = vquoteRow.getAllRowFree(id);
                quoteRowsProduct = vquoteRow.getAllRowProduct(id);
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
            result=PersonalizedException.logAndRecoverException(e,db);
            db.rollBack();

        }
        return false;
    }

    public Boolean isLoggedOn(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vquote = new VirtualQuote(db);
            vprod = new VirtualProduct(db);
            vpeople = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = new <VirtualQuote>ArrayList();
                if (id != 0) {
                    all.add(vquote.getVirtualQuote(id));
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
            result=PersonalizedException.logAndRecoverException(e,db);
            db.rollBack();

        }
        return false;
    }

    public Boolean insertRow(Cookie[] cookies, String choice) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vquote = new VirtualQuote(db);
            vprod = new VirtualProduct(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                switch (choice) {
                    case "rowfree": {
                        vquoteRow = new VirtualQuoteRow(id, description, quantity, subtot, db);
                        vquoteRow.insertRowFree();
                        break;
                    }
                    case "rowproduct": {
                        vquoteRow = new VirtualQuoteRow(id, id_product, quantity, price, db);
                        vquoteRow.insertRowProduct();
                        break;
                    }
                }
                all = new <VirtualQuote>ArrayList();
                all.add(vquote.getVirtualQuote(this.id));
                quoteRowsFree = vquoteRow.getAllRowFree(id);
                quoteRowsProduct = vquoteRow.getAllRowProduct(id);
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
            result=PersonalizedException.logAndRecoverException(e,db);
            db.rollBack();
        }
        return false;
    }
    
    public Boolean approve(Cookie[] cookies)
    {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vquote = new VirtualQuote(db);
            vquote.setId(id);
            vquoteRow = new VirtualQuoteRow(db);
            vprod = new VirtualProduct(db);
            vpeople = new VirtualPeople(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                System.out.println("Prima"+vquote.getId());
                vquote.approve();
                System.out.println("Dopo");
                all = new <VirtualQuote>ArrayList();
                all.add(vquote.getVirtualQuote(this.id));
                quoteRowsFree = vquoteRow.getAllRowFree(id);
                quoteRowsProduct = vquoteRow.getAllRowProduct(id);
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

    public String getExpiring_date() {
        return expiring_date;
    }

    public void setExpiring_date(String expiring_date) {
        this.expiring_date = expiring_date;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
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

    public List<QuoteInterface> getAll() {
        return all;
    }

    public List<QuoteRowInterface> getQuoteRowsFree() {
        return quoteRowsFree;
    }

    public List<QuoteRowInterface> getQuoteRowsProduct() {
        return quoteRowsProduct;
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

}
