/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.Cookies;

import BusinessLogic.VirtualEntities.VirtualUser;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.Cookie;
import util.Conversion;

/**
 *
 * @author filippo
 */
public class Cookies {

    public static String[] readAuthCookies(Cookie[] cookies) {
        String[] arr = new String[2];
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("uid")) {
                    arr[0] = cookies[i].getValue();
                }
                if (cookies[i].getName().equals("password")) {
                    arr[1] = cookies[i].getValue();
                }
            }
        }
        return arr;
    }

    public static Cookie[] setAuthCookie(String uid, String password) throws NoSuchAlgorithmException {
        Cookie[] cookies = new Cookie[2];
        cookies[0] = new Cookie("uid", Conversion.fromStringtoMD5(uid));
        cookies[1] = new Cookie("password", Conversion.fromStringtoMD5(password));
        return cookies;
    }

}
