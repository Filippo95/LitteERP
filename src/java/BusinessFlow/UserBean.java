/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.UserInterface;
import BusinessLogic.VirtualEntities.VirtualUser;
import Services.Database.Database;
import Services.Database.DatabaseService;
import Services.Exception.PersonalizedException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import util.Conversion;

/**
 *
 * @author filippo
 */
public class UserBean {

    private int id;
    private String username;
    private String oldpasswd;
    private String passwd;
    private String nome;
    private String cognome;
    private String email;
    private String tel;
    private String indirizzo;
    private String civico;
    private String cap;
    private String city;
    private String provincia;
    private Boolean admin;

    private Boolean logged;

    private List<UserInterface> all;
    private VirtualUser vu;
    private Database db;
    Login lg;
    
    private int result;

    public UserBean() {

    }
     public Boolean update(Cookie[] cookies)
    {
         db = null;
        try {
            db = DatabaseService.getDataBase();
            if(passwd==null)
            {
                vu  = new VirtualUser(this.id,this.username, this.oldpasswd, this.nome, this.cognome, this.email, this.tel, this.indirizzo, this.civico, this.cap, this.city, this.provincia, this.admin,this.db);
            }
            else 
            {
                 vu  = new VirtualUser(this.id,this.username, Conversion.fromStringtoMD5(this.passwd), this.nome, this.cognome, this.email, this.tel, this.indirizzo, this.civico, this.cap, this.city, this.provincia, this.admin,this.db);
           
            }
            
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vu.update();
                all=new <VirtualUser>ArrayList();
                all.add(  vu.getVirtualUser(this.id));
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
    public Boolean insert(Cookie[] cookies)
    {
         db = null;
        try {
            db = DatabaseService.getDataBase();
            vu = new VirtualUser(0,this.username, this.passwd, this.nome, this.cognome, this.email, this.tel, this.indirizzo, this.civico, this.cap, this.city, this.provincia, this.admin,this.db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                id=vu.insert();
                all=new <VirtualUser>ArrayList();
                all.add(  vu.getVirtualUser(this.id));
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
    public Boolean delete(Cookie[] cookies)
    {
         db = null;
        try {
            db = DatabaseService.getDataBase();
            vu = new VirtualUser(this.db);
            vu.setId(this.id);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                vu.delete();
                all = vu.getAll();
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
    public boolean list(Cookie[] cookies)
    {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vu = new VirtualUser(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all = vu.getAll();
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
    public Boolean getOne(Cookie[] cookies)
    {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            vu = new VirtualUser(db);
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                all=new <VirtualUser>ArrayList();
                all.add(  vu.getVirtualUser(this.id));
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
    
    //Getters & setters 

    public Boolean getLogged() {
        return logged;
    }

    public void setLogged(Boolean logged) {
        this.logged = logged;
    }

    public List<UserInterface> getAll() {
        return this.all;
    }

    public void setAll(List<UserInterface> all) {
        this.all = all;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
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

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin=admin;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Login getLg() {
        return lg;
    }

    public void setLg(Login lg) {
        this.lg = lg;
    }

    public String getOldpasswd() {
        return oldpasswd;
    }

    public void setOldpasswd(String oldpasswd) {
        this.oldpasswd = oldpasswd;
    }
    

}
