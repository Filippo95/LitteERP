/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.DrainInterface;
import BusinessLogic.VirtualEntities.VirtualDrain;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class DrainDAO {

    Database db;

    public DrainDAO(Database db) {
        this.db = db;
    }

    public List<DrainInterface> getAllDrain(DrainInterface vd) throws NotFoundDBException, SQLException {
        String sql = "SELECT * FROM drain WHERE active=1";

        List<DrainInterface> lista = new ArrayList();
        ResultSet rs = this.db.select(sql);
        GregorianCalendar cal=null;
        while (rs.next()) {
            cal=new GregorianCalendar();
            cal.setTime(rs.getDate("ddate"));
            int day=cal.get(Calendar.DATE);
            int month=cal.get(Calendar.MONTH);
            int year=cal.get(Calendar.YEAR);
            String date=""+day+"-"+(month+1)+"-"+year;
            vd = new VirtualDrain();
            vd.setId(rs.getInt("id"));
            vd.setDdate(date);
            lista.add(vd);
        }
        return lista;
    }

    public DrainInterface getDrain(DrainInterface vd,int id) throws NotFoundDBException, ResultSetDBException, SQLException {
        String sql = "SELECT * FROM drain WHERE id=? AND active=1";
        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        GregorianCalendar cal = null;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            cal=new GregorianCalendar();
            cal.setTime(rs.getDate("ddate"));
            int day=cal.get(Calendar.DATE);
            int month=cal.get(Calendar.MONTH);
            int year=cal.get(Calendar.YEAR);
            String date=""+day+"-"+(month+1)+"-"+year;
            vd = new VirtualDrain();
            vd.setId(id);
            vd.setDdate(date);
        }
        return vd;
    }

    public int insertVirtualDrain(DrainInterface vd) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `drain`( `active`) VALUES (?) ";
        String[] pars = new String[1];
        pars[0] = ""+1;
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
        //Restituisco l'id al Bean in modo da poter inserire le linee sucessivamente
        sql = "SELECT LAST_INSERT_ID();";
        ResultSet rs = this.db.select(sql);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("LAST_INSERT_ID()");
        }
        return id;
    }

}
