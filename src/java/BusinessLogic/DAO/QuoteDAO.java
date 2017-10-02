/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.QuoteInterface;
import BusinessLogic.VirtualEntities.VirtualQuote;
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
public class QuoteDAO {

    Database db;

    public QuoteDAO(Database db) {
        this.db = db;
    }

    public List<QuoteInterface> getAllQuote(QuoteInterface vq) throws NotFoundDBException, SQLException {
        String sql = "SELECT * FROM quote WHERE active=1";

        List<QuoteInterface> lista = new ArrayList();
        ResultSet rs = this.db.select(sql);
        GregorianCalendar cal = null;
        while (rs.next()) {
            cal = new GregorianCalendar();
            cal.setTime(rs.getDate("expiring_date"));
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            String date = day + "-" + (month + 1) + "-" + year;
            int id_people = rs.getInt("id_people");
            sql = "SELECT name FROM people WHERE active=1 AND id=?";
            String[] pars = new String[1];
            pars[0] = "" + id_people;
            ResultSet rsp = this.db.PreparedStatement(sql, pars);
            String clientName = "";
            while (rsp.next()) {
                clientName = rsp.getString("name");
            }
            vq = new VirtualQuote();
            vq.setId(rs.getInt("id"));
            vq.setId_people(id_people);
            vq.setDescription(rs.getString("description"));
            vq.setExpiring_date(date);
            vq.setApproved(rs.getBoolean("approved"));
            vq.setClientName(clientName);
            lista.add(vq);
        }
        return lista;
    }

    public QuoteInterface getQuote(QuoteInterface vq,int id) throws NotFoundDBException, ResultSetDBException, SQLException {
        String sql = "SELECT * FROM quote WHERE id=? AND active=1";
        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        GregorianCalendar cal = null;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            cal = new GregorianCalendar();
            cal.setTime(rs.getDate("expiring_date"));
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            String date = year + "-" + (month + 1) + "-" + day;
            int id_people = rs.getInt("id_people");
            sql = "SELECT name FROM people WHERE active=1 AND id=?";
            pars = new String[1];
            pars[0] = "" + id_people;
            ResultSet rsp = this.db.PreparedStatement(sql, pars);
            String clientName = "";
            while (rsp.next()) {
                clientName = rsp.getString("name");
            }
            vq = new VirtualQuote();
            vq.setId(rs.getInt("id"));
            vq.setId_people(id_people);
            vq.setDescription(rs.getString("description"));
            vq.setExpiring_date(date);
            vq.setApproved(rs.getBoolean("approved"));
            vq.setClientName(clientName);
        }
        return vq;
    }

    public int insertVirtualQuote(QuoteInterface vq) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `quote`( `description`,`expiring_date`, `approved`,`id_people`) VALUES (?,?,?,?) ";
        String[] pars = new String[4];
        pars[0] = "" + vq.getDescription();
        pars[1] = vq.getExpiring_date();
        if (vq.getApproved()) {
            pars[2] = "" + 1;
        } else {
            pars[2] = "" + 0;
        }
        pars[3] = "" + vq.getId_people();
        
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

    public void updateVirtualQuote(QuoteInterface vq) throws NotFoundDBException {
        String sql = "UPDATE `quote` SET `description`=?,`expiring_date`=?,`approved`=?,`id_people`=? WHERE `id`=?";

        String[] pars = new String[5];
        pars[0] = "" + vq.getDescription();
        pars[1] = vq.getExpiring_date();
        if (vq.getApproved()) {
            pars[2] = "" + 1;
        } else {
            pars[2] = "" + 0;
        }
        pars[3] = "" + vq.getId_people();
        pars[4] = "" + vq.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            System.out.println("Aggiornate 0 righe");
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

    public void approve(QuoteInterface vq) throws NotFoundDBException {
        String sql = "UPDATE `quote` SET `approved`=? WHERE `id`=?";

        String[] pars = new String[2];
        pars[0] = "" + 1;
        pars[1] = "" + vq.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            System.out.println("Aggiornate 0 righe");
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

}
