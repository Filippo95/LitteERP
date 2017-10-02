/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.InvoiceInterface;
import BusinessLogic.VirtualEntities.VirtualInvoice;
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
public class InvoiceDAO {

    Database db;

    public InvoiceDAO(Database db) {
        this.db = db;
    }

    public List<InvoiceInterface> getAllInvoice(InvoiceInterface vi) throws NotFoundDBException, SQLException {
        String sql = "SELECT * FROM invoice WHERE active=1";

        List<InvoiceInterface> lista = new ArrayList();
        ResultSet rs = this.db.select(sql);
        GregorianCalendar cal = null;
        while (rs.next()) {
            cal = new GregorianCalendar();
            cal.setTime(rs.getDate("idate"));
            int id_people = rs.getInt("id_people");
            sql = "SELECT name FROM people WHERE active=1 AND id=?";
            String[] pars = new String[1];
            pars[0] = "" + id_people;
            ResultSet rsp = this.db.PreparedStatement(sql, pars);
            String clientName = "";
            while (rsp.next()) {
                clientName = rsp.getString("name");
            }
            vi = new VirtualInvoice();
            vi.setId(rs.getInt("id"));
            vi.setIdate(cal);
            vi.setExpiring_days(rs.getInt("expiring_days"));
            vi.setPayed(rs.getBoolean("payed"));
            vi.setId_people(id_people);
            vi.setId_drain(rs.getInt("id_drain"));
            vi.setClientName(clientName);
            lista.add(vi);
        }
        return lista;
    }

    public InvoiceInterface getInvoice(InvoiceInterface vi, int id) throws NotFoundDBException, ResultSetDBException, SQLException {
        String sql = "SELECT * FROM invoice WHERE id=? AND active=1";
        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        GregorianCalendar cal = null;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            cal = new GregorianCalendar();
            cal.setTime(rs.getDate("idate"));
            int id_people = rs.getInt("id_people");
            sql = "SELECT name FROM people WHERE active=1 AND id=?";
            pars = new String[1];
            pars[0] = "" + id_people;
            ResultSet rsp = this.db.PreparedStatement(sql, pars);
            String clientName = "";
            while (rsp.next()) {
                clientName = rsp.getString("name");
            }
            vi = new VirtualInvoice();
            vi.setId(rs.getInt("id"));
            vi.setIdate(cal);
            vi.setExpiring_days(rs.getInt("expiring_days"));
            vi.setPayed(rs.getBoolean("payed"));
            vi.setId_people(id_people);
            vi.setId_drain(rs.getInt("id_drain"));
            vi.setClientName(clientName);
        }
        return vi;
    }

    public InvoiceInterface getInvoiceByDrainId(InvoiceInterface vi, int id_drain) throws NotFoundDBException, SQLException {
        String sql = "SELECT * FROM invoice WHERE id_drain=? AND active=1";
        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id_drain;
        GregorianCalendar cal = null;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            cal = new GregorianCalendar();
            cal.setTime(rs.getDate("idate"));
            int id_people = rs.getInt("id_people");
            sql = "SELECT name FROM people WHERE active=1 AND id=?";
            pars = new String[1];
            pars[0] = "" + id_people;
            ResultSet rsp = this.db.PreparedStatement(sql, pars);
            String clientName = "";
            while (rsp.next()) {
                clientName = rsp.getString("name");
            }
            vi = new VirtualInvoice();
            vi.setId(rs.getInt("id"));
            vi.setIdate(cal);
            vi.setExpiring_days(rs.getInt("expiring_days"));
            vi.setPayed(rs.getBoolean("payed"));
            vi.setId_people(id_people);
            vi.setId_drain(rs.getInt("id_drain"));
            vi.setClientName(clientName);
        }
        return vi;
    }

    public int getInvoiceNumber(GregorianCalendar date) throws NotFoundDBException, ResultSetDBException, SQLException {
        String sql = "SELECT COUNT(*) AS tot FROM invoice WHERE idate=? AND active=1";
        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DATE);
        int number = 0;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            number = rs.getInt("tot");
        }
        return number;
    }

    public int insertVirtualInvoice(InvoiceInterface vi) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `invoice`( `idate`,`expiring_days`, `payed`,`id_people`,`id_drain`) VALUES (?,?,?,?,?) ";
        GregorianCalendar cal = new GregorianCalendar();
        String[] pars = new String[5];
        pars[0] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
        pars[1] = "" + vi.getExpiring_days();
        if (vi.getPayed()) {
            pars[2] = "" + 1;
        } else {
            pars[2] = "" + 0;
        }
        pars[3] = "" + vi.getId_people();
        pars[4] = "" + vi.getId_drain();
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

    public int insertVirtualInvoiceFromDrainId(int id_drain) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `invoice`( `idate`,`expiring_days`, `payed`,`id_people`,`id_drain`) VALUES "
                + "(?,0,1,(SELECT id_people FROM ddt WHERE id_drain=?),?)";
        GregorianCalendar cal = new GregorianCalendar();
        String[] pars = new String[3];
        pars[0] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
        pars[1] = "" + id_drain;
        pars[2] = "" + id_drain;
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

    public int insertVirtualInvoiceFromQuote(int id_quote, InvoiceInterface vi) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `invoice`( `idate`,`expiring_days`, `payed`,`id_people`,`id_drain`) VALUES "
                + "(?,0,1,(SELECT id_people FROM quote WHERE id=?),?)";
        GregorianCalendar cal = new GregorianCalendar();
        String[] pars = new String[3];
        pars[0] = pars[0] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
        pars[1] = "" + id_quote;
        pars[2] = "" + vi.getId_drain();
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

    public void updateVirtualInvoice(InvoiceInterface vi) throws NotFoundDBException {
        String sql = "UPDATE `invoice` SET `expiring_days`=?,`payed`=?,`id_people`=? WHERE `id`=?";

        String[] pars = new String[4];
        pars[0] = "" + vi.getExpiring_days();
        if (vi.getPayed()) {
            pars[1] = "" + 1;
        } else {
            pars[1] = "" + 0;
        }
        pars[2] = "" + vi.getId_people();
        pars[3] = "" + vi.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            System.out.println("Aggiornate 0 righe");
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

    public Boolean existDdt(int id_drain) throws NotFoundDBException, SQLException {
        //Guardo se esiste un invoice con id_drain dato contando le occorrenze
        String sql = "SELECT COUNT(*) AS tot FROM ddt where id_drain=? AND active=1";
        String[] pars = new String[1];
        pars[0] = "" + id_drain;
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        int occ = 0;
        while (rs.next()) {
            occ = rs.getInt("tot");
        }
        if (occ > 0) {
            return true;
        } else {
            return false;
        }
    }

}
