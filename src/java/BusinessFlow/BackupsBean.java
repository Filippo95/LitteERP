/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.VirtualEntities.VirtualBrand;
import Services.Database.Database;
import Services.Database.DatabaseService;
import Services.Exception.PersonalizedException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;

/**
 *
 * @author filippo
 *
 */
public class BackupsBean {

    private Database db;
    private Login lg;
    public File[] contents;

    private int result;

    public BackupsBean() {

    }

    public Boolean isLoggeOn(Cookie[] cookies) {
        db = null;
        try {
            db = DatabaseService.getDataBase();
            lg = new Login(db);
            String[] auth = Services.Cookies.Cookies.readAuthCookies(cookies);
            if (lg.checkUser(auth[0], auth[1])) {
                db.commit();
                db.close();
                this.getFiles();
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

    public void getFiles() {
        System.out.println("Sono il bean e sto per fare la get file");
        File directory = new File("C:\\Users\\ravar\\Desktop\\Gestionale - 20 giugno 2017-1_29\\web\\Backups\\");
        if (directory.isDirectory()) {
            contents = directory.listFiles();
            if (contents.length == 0) {
                System.out.println("Erroe no file trovati");
            } else {
                for (File f : contents) {
                    System.out.println(f.getAbsolutePath());
                }
            }

        } else {

            System.out.println("Non è una directory");
        }

    }

    public Login getLg() {
        return lg;
    }

    public void setLg(Login lg) {
        this.lg = lg;
    }

}
