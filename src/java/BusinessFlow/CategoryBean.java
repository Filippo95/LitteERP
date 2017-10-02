/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.CategoryInterface;
import BusinessLogic.Interface.LanguageInterface;
import BusinessLogic.VirtualEntities.VirtualCategory;
import BusinessLogic.VirtualEntities.VirtualLanguage;
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
public class CategoryBean {

    private int id;
    private int id_parent;
    private String name;
    private String description;
    private int id_lang;

    private List<CategoryInterface> all;
    private VirtualCategory vc;
    private VirtualLanguage vl;
    private List<LanguageInterface> languages;
    private Database db;
    private Login lg;
    
    private int result;

    public CategoryBean() {

    }

    public Boolean categoryViewUpdate(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vc=new VirtualCategory(this.id,this.name,this.description,this.id_lang,db);
            vl = new VirtualLanguage(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vc.updateTranslation();
                all = vc.getAll();
                languages = vl.getAll();
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

    public Boolean categoryViewInsert(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vc=new VirtualCategory(this.id, this.name, this.description, this.id_lang, this.db);
            vl = new VirtualLanguage(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vc.insertTranslation();
                all = vc.getAll();
                languages = vl.getAll();
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

    public Boolean categoryViewDelete(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vc = new VirtualCategory(this.db);
            vc.setId(this.id);
            vl = new VirtualLanguage(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vc.delete();
                all = vc.getAll();
                languages = vl.getAll();
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

    public Boolean categoryViewList(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vc = new VirtualCategory(db);
            vl = new VirtualLanguage(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = vc.getAll();
                languages = vl.getAll();
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

    public Boolean categoryInsertViewActionInsert(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vc= new VirtualCategory(db);
            vl = new VirtualLanguage(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                languages = vl.getAll();
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
    
    public Boolean categoryInsertViewActionModify(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vc= new VirtualCategory(db);
            vl = new VirtualLanguage(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = new <VirtualCategory>ArrayList();
                all.add(vc.getVirtualCategoryWithName(this.id));
                languages = vl.getAll();
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

    public Boolean categoryInsertInsertCategory(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vc = new VirtualCategory(0, this.id_parent,this.name, this.description, this.id_lang,  this.db);
            vl = new VirtualLanguage(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                this.id=vc.insertCategory();
                vc = new VirtualCategory(this.id, this.id_parent,this.name, this.description, this.id_lang,  this.db);
                vc.insertTranslation();
                all = new <VirtualCategory>ArrayList();
                all.add(vc.getVirtualCategoryWithName(this.id));
                languages = vl.getAll();
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

    public boolean categoryInsertUpdateCategory(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vc = new VirtualCategory(this.id, this.id_parent, this.name, this.description, this.id_lang, this.db);
            vl = new VirtualLanguage(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vc.updateCategory();
                vc.updateTranslation();
                languages = vl.getAll();
                all = new <VirtualCategory>ArrayList();
                all.add(vc.getVirtualCategoryWithName(this.id));
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

    public Boolean categoryInsertInsertTranslation(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vc=new VirtualCategory(this.id, this.name, this.description, this.id_lang, this.db);
            vl = new VirtualLanguage(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vc.insertTranslation();
                all = new <VirtualCategory>ArrayList();
                all.add(vc.getVirtualCategoryWithName(this.id));
                languages = vl.getAll();
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

    public boolean categoryInsertUpdateTranslation(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vc=new VirtualCategory(this.id, this.name, this.description, this.id_lang, this.db);
            vl = new VirtualLanguage(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vc.updateTranslation();
                languages = vl.getAll();
                all = new <VirtualCategory>ArrayList();
                all.add(vc.getVirtualCategoryWithName(this.id));
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

    public CategoryInterface getSavedCategory(int id) {
        CategoryInterface vc = null;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == id) {
                vc = all.get(i);
            }
        }
        return vc;
    }

    public int getLanguageNumber() {
        return languages.size();
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

    public int getId_parent() {
        return id_parent;
    }

    public void setId_parent(int id_parent) {
        this.id_parent = id_parent;
    }

    public int getId_lang() {
        return id_lang;
    }

    public void setId_lang(int id_lang) {
        this.id_lang = id_lang;
    }

    public List<LanguageInterface> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguageInterface> languages) {
        this.languages = languages;
    }

    public List<CategoryInterface> getAll() {
        return all;
    }

    public void setAll(List<CategoryInterface> all) {
        this.all = all;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
    
}
