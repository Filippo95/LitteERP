/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.BrandInterface;
import BusinessLogic.Interface.CategoryInterface;
import BusinessLogic.Interface.DatasheetInterface;
import BusinessLogic.Interface.DescriptionInterface;
import BusinessLogic.Interface.ImageInterface;
import BusinessLogic.Interface.LanguageInterface;
import BusinessLogic.Interface.ProductInterface;
import BusinessLogic.Interface.UnitInterface;
import BusinessLogic.VirtualEntities.VirtualBrand;
import BusinessLogic.VirtualEntities.VirtualCategory;
import BusinessLogic.VirtualEntities.VirtualDescription;
import BusinessLogic.VirtualEntities.VirtualDatasheet;
import BusinessLogic.VirtualEntities.VirtualImage;
import BusinessLogic.VirtualEntities.VirtualLanguage;
import BusinessLogic.VirtualEntities.VirtualProduct;
import BusinessLogic.VirtualEntities.VirtualUnit;
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
public class ProductBean {

    private int id;
    private String model;
    private int id_category;
    private int id_brand;
    private int id_unit;
    private String state;
    private String tags;
    private String note;
    private String wharehouse_position;
    private double weight;
    private double max_order;
    private double min_order;
    private double profit;
    private double price;
    private int iva;

    private byte[] content;
    private String content_type;

    private int id_lang;
    private String description;

    private VirtualCategory vc = null;
    private VirtualBrand vb = null;
    private VirtualUnit vu = null;
    private VirtualDescription vd = null;
    private VirtualLanguage vl = null;
    private VirtualImage vi = null;
    private VirtualDatasheet vdt = null;

    private List<CategoryInterface> allCategories;
    private List<BrandInterface> allBrand;
    private List<UnitInterface> allUnit;
    private List<DescriptionInterface> descriptions;
    private List<LanguageInterface> languages;
    private List<ImageInterface> allImages;
    private List<DatasheetInterface> allDatasheet;

    private List<ProductInterface> all;
    private VirtualProduct vp;
    private Database db;
    private Login lg;

    private int result;

    public ProductBean() {

    }

    public Boolean update(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vp = new VirtualProduct(this.id, this.model, this.id_category, this.id_brand, this.id_unit, this.state, this.tags, this.note,
                    this.wharehouse_position, this.weight, this.max_order, this.min_order, this.profit, this.price, this.iva, this.db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vp.update();
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
            vp = new VirtualProduct(0, this.model, this.id_category, this.id_brand, this.id_unit, this.state, this.tags, this.note,
                    this.wharehouse_position, this.weight, this.max_order, this.min_order, this.profit, this.price, this.iva, this.db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                this.id = vp.insert();
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

    public Boolean delete(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vp = new VirtualProduct(this.db);
            vp.setId(this.id);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vp.delete();
                all = vp.getAll();
                populateAllList();
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
            vp = new VirtualProduct(db);
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
            result = PersonalizedException.logAndRecoverException(e, db);

        }
        return false;

    }

    public boolean insertImage(Cookie[] cookies) {
        db = null;

        System.out.println("il bean inizia il try");
        try {

            db = DatabaseService.getDataBase();
            System.out.println("il bean istanzia i due oggetti");
            vi = new VirtualImage(0, this.id, this.content_type, this.content, this.db);
            lg = new Login(db);
            vp = new VirtualProduct(db);

            System.out.println("ora sta per prendere i cookie");
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);

            System.out.println("il bean fha i cookie e fa l if ora");

            System.out.println("ha preso i seguenti cookies: " + auth[0] + " - " + auth[1]);
            if (lg.checkUser(auth[0], auth[1])) {

                System.out.println("il bean fa la vi.insert()");
                vi.insert();

                System.out.println("il bean ha fatto la vi.insert()");
                populateAllList();
                all = vp.getAll();
                db.commit();
                db.close();
                return true;

            } else {
                db.commit();
                db.close();

                System.out.println("il bean sbaglia il cheuser");
                return false;
            }
        } catch (Exception e) {
            System.out.println("il bean va in eccezzione" + e.toString());
            result = PersonalizedException.logAndRecoverException(e, db);

        }
        return false;

    }

    public boolean insertDatasheet(Cookie[] cookies) {
        db = null;

        System.out.println("il bean inizia il try");
        try {

            db = DatabaseService.getDataBase();
            System.out.println("il bean istanzia i due oggetti");
            vdt = new VirtualDatasheet(0, this.id, this.content_type, this.content, this.db);
            System.out.println("il virtual datasheet viene istanziato con id");
            lg = new Login(db);
            vp = new VirtualProduct(db);

            System.out.println("ora sta per prendere i cookie");
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);

            System.out.println("il bean fha i cookie e fa l if ora");

            System.out.println("ha preso i seguenti cookies: " + auth[0] + " - " + auth[1]);
            if (lg.checkUser(auth[0], auth[1])) {

                System.out.println("il bean fa la vi.insert()");
                vdt.insert();

                System.out.println("il bean ha fatto la vi.insert()");
                populateAllList();
                all = vp.getAll();
                db.commit();
                db.close();
                return true;

            } else {
                db.commit();
                db.close();

                System.out.println("il bean sbaglia il cheuser");
                return false;
            }
        } catch (Exception e) {
            System.out.println("il bean va in eccezzione" + e.toString());
            result = PersonalizedException.logAndRecoverException(e, db);

        }
        return false;

    }

    public Boolean getOne(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vp = new VirtualProduct(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = new <VirtualProduct>ArrayList();
                all.add(vp.getVirtualProduct(this.id));
                populateAllList();
                PopulateAllImages();
                PopulateDescriptions();
                PopulateLanguages();
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
            vp = new VirtualProduct(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);

            if (lg.checkUser(auth[0], auth[1])) {
                populateAllList();
                PopulateLanguages();
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
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();
        }
        return false;
    }

    public Boolean isLoggeOn(Cookie[] cookies, int idVI, int i) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            lg = new Login(db);
            vp = new VirtualProduct(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);

            if (lg.checkUser(auth[0], auth[1])) {
                if (i == 1) {

                    VirtualImage vitodel = new VirtualImage(db);
                    vitodel.setId(idVI);
                    vitodel.delete();
                } else if (i == 2) {
                    VirtualDatasheet vitodel = new VirtualDatasheet(db);
                    vitodel.setId(idVI);
                    vitodel.delete();
                }

                populateAllList();
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
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();

        }

        return false;
    }

    public Boolean insertDescription(Cookie[] cookies) {
        //Inserimento della sola traduzione della descrizione
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vd = new VirtualDescription(this.id, this.id_lang, this.description, db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vd.insert();
                PopulateLanguages();
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

    public Boolean updateDescription(Cookie[] cookies) {
        //Modifica della sola traduzione della descrizione
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vd = new VirtualDescription(this.id, this.id_lang, this.description, db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vd.update();
                PopulateDescriptions();
                PopulateLanguages();
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

    public void populateAllList() {
        this.PopulateAllBrand();
        this.PopulateAllCategories();
        this.PopulateAllUnit();
        this.PopulateAllImages();
        this.PopulateAllDatasheet();
    }

    public void PopulateAllCategories() {
        try {
            vc = new VirtualCategory(db);
            allCategories = vc.getAll();
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();
        }
    }

    public void PopulateAllUnit() {
        try {
            vu = new VirtualUnit(db);
            allUnit = vu.getAll();

        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();

        }

    }

    public void PopulateAllBrand() {
        try {
            vb = new VirtualBrand(db);
            allBrand = vb.getAll();

        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();

        }

    }

    public void PopulateDescriptions() {
        try {
            vd = new VirtualDescription(db);
            descriptions = vd.getDescriptions(this.id);
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();
        }
    }

    private void PopulateAllImages() {
        try {
            vi = new VirtualImage(db);
            allImages = vi.getAll();

        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();

        }
    }

    public void PopulateLanguages() {
        try {
            vl = new VirtualLanguage(db);
            languages = vl.getAll();
        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();
        }
    }

    private void PopulateAllDatasheet() {
        try {
            vdt = new VirtualDatasheet(db);
            allDatasheet = vdt.getAll();

        } catch (Exception e) {
            result = PersonalizedException.logAndRecoverException(e, db);
            db.rollBack();

        }
    }

    //getters & setters
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getId_brand() {
        return id_brand;
    }

    public void setId_brand(int id_brand) {
        this.id_brand = id_brand;
    }

    public int getId_unit() {
        return id_unit;
    }

    public void setId_unit(int id_unit) {
        this.id_unit = id_unit;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWharehouse_position() {
        return wharehouse_position;
    }

    public void setWharehouse_position(String wharehouse_position) {
        this.wharehouse_position = wharehouse_position;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getMax_order() {
        return max_order;
    }

    public void setMax_order(double max_order) {
        this.max_order = max_order;
    }

    public double getMin_order() {
        return min_order;
    }

    public void setMin_order(double min_order) {
        this.min_order = min_order;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId_lang() {
        return id_lang;
    }

    public void setId_lang(int id_lang) {
        this.id_lang = id_lang;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductInterface> getAll() {
        return all;
    }

    public void setAll(List<ProductInterface> all) {
        this.all = all;
    }

    public List<BrandInterface> getAllBrand() {
        return allBrand;
    }

    public void setAllBrand(List<BrandInterface> allBrand) {
        this.allBrand = allBrand;
    }

    public List<CategoryInterface> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<CategoryInterface> allCategories) {
        this.allCategories = allCategories;
    }


    public List<UnitInterface> getAllUnit() {
        return allUnit;
    }

    public List<DescriptionInterface> getDescriptions() {
        return descriptions;
    }

    public List<LanguageInterface> getLanguages() {
        return languages;
    }

    public List<ImageInterface> getAllImages() {
        return allImages;
    }

    public void setAllImages(List<ImageInterface> allImages) {
        this.allImages = allImages;
    }

    public List<DatasheetInterface> getAllDatasheet() {
        return allDatasheet;
    }

    public void setAllDatasheet(List<DatasheetInterface> allDatasheet) {
        this.allDatasheet = allDatasheet;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

}
