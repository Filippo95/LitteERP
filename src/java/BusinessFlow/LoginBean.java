/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.BrandInterface;
import BusinessLogic.Interface.InvoiceInterface;
import BusinessLogic.Interface.ProductInterface;
import BusinessLogic.Interface.QuoteInterface;
import java.util.GregorianCalendar;
import java.util.Calendar;
import BusinessLogic.VirtualEntities.VirtualBrand;
import BusinessLogic.VirtualEntities.VirtualInvoice;
import BusinessLogic.VirtualEntities.VirtualProduct;
import BusinessLogic.VirtualEntities.VirtualQuote;
import Services.Database.Database;
import Services.Database.DatabaseService;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import Services.Exception.PersonalizedException;
import java.sql.SQLException;
import javax.servlet.http.Cookie;
import java.util.List;

/**
 *
 * @author filippo
 */
public class LoginBean {

    private String username;
    private String passwd;
    private Login lg;
    private Cookie[] cookies;
    private Database db;

    private VirtualProduct vprod;
    private List<ProductInterface> products;
    private VirtualInvoice vinvoice;
    private List<InvoiceInterface> invoices;
    private VirtualBrand vb;
    private List<BrandInterface> brands;
    private VirtualQuote vq;
    private List<QuoteInterface> quotes;

    private String[] date;

    private int result;

    public LoginBean() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Boolean checkUser(Cookie[] cookies, String status) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vprod = new VirtualProduct(db);
            vinvoice = new VirtualInvoice(db);
            vb = new VirtualBrand(db);
            vq = new VirtualQuote(db);
            lg = new Login(db);
            switch (status) {
                case "view": {
                    System.out.println("prima");
                        if (cookies != null) {
                            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
                            if (lg.checkUser(auth[0], auth[1])) {
                                products = vprod.getAll();
                                invoices = vinvoice.getAll();
                                brands = vb.getAll();
                                quotes = vq.getAll();
                                date = this.populateDates(invoices);
                                db.commit();
                                db.close();
                                return true;
                            } else {
                                //db.commit();
                                db.close();
                                return false;
                            }
                        } else {
                            //db.commit();
                            db.close();
                            return false;
                        }
                    
                }
                case "login": {
                    if (lg.checkUserFromUsrnameAndPassword(username, passwd)) {
                        this.cookies = Services.Cookies.Cookies.setAuthCookie("" + lg.vu.getId(), passwd);
                        products = vprod.getAll();
                        invoices = vinvoice.getAll();
                        brands = vb.getAll();
                        quotes = vq.getAll();
                        date = this.populateDates(invoices);
                        db.commit();
                        db.close();
                        return true;
                    } else {
                        db.commit();
                        db.close();
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
        }
        return false;

    }

    public Cookie[] logout(Cookie[] cookie) {
        for (int i = 0; i < cookie.length; i++) {
            cookie[i].setMaxAge(0);
        }
        return cookie;
    }

    private String[] populateDates(List<InvoiceInterface> vi) throws NotFoundDBException, ResultSetDBException, SQLException {
        GregorianCalendar cal = null;
        String[] date = new String[vi.size()];
        for (int i = 0; i < vi.size(); i++) {
            cal = vi.get(i).getIdate();
            String localdate = cal.get(Calendar.YEAR) + "," + cal.get(Calendar.MONTH) + "," + cal.get(Calendar.DATE);
            System.out.println(date);
            VirtualInvoice inv = new VirtualInvoice(db);
            int number = inv.getInvoiceNumber(cal);
            date[i] = "[Date.UTC(" + localdate + ")," + number + "],";
        }
        return date;
    }

    public Login getlg() {
        return lg;
    }

    public void setlg(Login lg) {
        this.lg = lg;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<ProductInterface> getProducts() {
        return products;
    }

    public List<InvoiceInterface> getInvoices() {
        return invoices;
    }

    public List<BrandInterface> getBrands() {
        return brands;
    }

    public List<QuoteInterface> getQuotes() {
        return quotes;
    }

    public String[] getDate() {
        return date;
    }

}
