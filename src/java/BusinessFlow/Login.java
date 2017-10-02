/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessFlow;

import BusinessLogic.Interface.UserInterface;
import BusinessLogic.VirtualEntities.VirtualUser;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.security.NoSuchAlgorithmException;
import util.Conversion;

/**
 *
 * @author filippo
 */
public class Login {

    VirtualUser auxvu;
    UserInterface vu;

    public Login(Database db) {
        auxvu = new VirtualUser(db);
    }

    public Boolean checkUser(String uid, String password) throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException {
        vu=auxvu.getVirtualUserFromCookie(uid, password);
        if (vu == null) {
            System.out.println("false");
            return false;
        }
        else {
           System.out.println("true");
            return true;
        }
    }
    public Boolean checkUserFromUsrnameAndPassword(String username, String password) throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException {
        vu = auxvu.getVirtualUser(username, password);
        if (vu == null) {
            return false;
        }
        if (vu.getUsername().equals(username) && vu.getPasswd().equals(Conversion.fromStringtoMD5(password))) {
            return true;
        } else {
            return false;
        }
    }

    public UserInterface getVu() {
        return vu;
    }

    public void setVu(VirtualUser vu) {
        this.vu = vu;
    }
   

}
