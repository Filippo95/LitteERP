/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.DdtInterface;
import BusinessLogic.VirtualEntities.VirtualDdt;
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
public class DdtDAO {

    Database db;

    public DdtDAO(Database db) {
        this.db = db;
    }

    public List<DdtInterface> getAllDdt(DdtInterface vddt) throws NotFoundDBException, SQLException {
        String sql = "SELECT * FROM ddt WHERE active=?";
        String[] pars = new String[1];
        pars[0] = "" + 1;
        List<DdtInterface> lista = new ArrayList();
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        GregorianCalendar cal = null;
        while (rs.next()) {
            cal = new GregorianCalendar();
            cal.setTime(rs.getDate("ddate"));
            vddt = new VirtualDdt();
            vddt.setId(rs.getInt("id"));
            vddt.setDdate(cal);
            vddt.setId_people(rs.getInt("id_people"));
            vddt.setId_people_vett(rs.getInt("id_people_vett"));
            vddt.setId_drain(rs.getInt("id_drain"));
            vddt.setDestination(rs.getString("destination"));
            vddt.setCause(rs.getString("cause"));
            vddt.setCause(rs.getString("aspect"));
            vddt.setNcoll(rs.getString("ncoll"));
            vddt.setClientName(this.getPersonName(rs.getInt("id_people")));
            vddt.setVettName(this.getPersonName(rs.getInt("id_people_vett")));
            lista.add(vddt);
        }
        return lista;
    }

    public DdtInterface getDdt(DdtInterface vddt,int id) throws NotFoundDBException, ResultSetDBException, SQLException {
        String sql = "SELECT * FROM ddt WHERE id=? AND active=1";
        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        GregorianCalendar cal = null;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            cal = new GregorianCalendar();
            cal.setTime(rs.getDate("ddate"));
            vddt.setId(rs.getInt("id"));
            vddt.setDdate(cal);
            vddt.setId_people(rs.getInt("id_people"));
            vddt.setId_people_vett(rs.getInt("id_people_vett"));
            vddt.setId_drain(rs.getInt("id_drain"));
            vddt.setDestination(rs.getString("destination"));
            vddt.setCause(rs.getString("cause"));
            vddt.setCause(rs.getString("aspect"));
            vddt.setNcoll(rs.getString("ncoll"));
            vddt.setClientName(this.getPersonName(rs.getInt("id_people")));
            vddt.setVettName(this.getPersonName(rs.getInt("id_people_vett")));
        }
        return vddt;
    }

    public DdtInterface getDdtByDrainId(DdtInterface vddt,int id_drain) throws NotFoundDBException, SQLException {
        String sql = "SELECT * FROM ddt WHERE id_drain=? AND active=1";
        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id_drain;
        GregorianCalendar cal = null;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            cal = new GregorianCalendar();
            cal.setTime(rs.getDate("ddate"));
            vddt.setId(rs.getInt("id"));
            vddt.setDdate(cal);
            vddt.setId_people(rs.getInt("id_people"));
            vddt.setId_people_vett(rs.getInt("id_people_vett"));
            vddt.setId_drain(rs.getInt("id_drain"));
            vddt.setDestination(rs.getString("destination"));
            vddt.setCause(rs.getString("cause"));
            vddt.setCause(rs.getString("aspect"));
            vddt.setNcoll(rs.getString("ncoll"));
            vddt.setClientName(this.getPersonName(rs.getInt("id_people")));
            vddt.setVettName(this.getPersonName(rs.getInt("id_people_vett")));
        }

        return vddt;

    }

    public int insertVirtualDdt(DdtInterface vddt) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `ddt`( `ddate`,`id_people`, `id_people_vett`,`id_drain`,`destination`,`cause`,`aspect`,`ncoll`) VALUES (?,?,?,?,?,?,?,?) ";
        GregorianCalendar cal = new GregorianCalendar();
        String[] pars = new String[8];
        pars[0] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
        pars[1] = "" + vddt.getId_people();
        pars[2] = "" + vddt.getId_people_vett();
        pars[3] = "" + vddt.getId_drain();
        pars[4] = vddt.getDestination();
        pars[5] = vddt.getCause();
        pars[6] = vddt.getAspect();
        pars[7] = vddt.getNcoll();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
        System.out.println("ddt inserito");
        //Restituisco l'id al Bean in modo da poter inserire le linee sucessivamente
        sql = "SELECT LAST_INSERT_ID();";
        ResultSet rs = this.db.select(sql);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("LAST_INSERT_ID()");
        }
        System.out.println("id ddt inserito:" + id);
        return id;
    }

    public int insertVirtualDdtFromQuote(int id_quote, DdtInterface vddt) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `ddt`( `ddate`,`id_people`,`id_drain`) VALUES "
                + "(?,(SELECT id_people FROM quote WHERE id=?),?)";
        GregorianCalendar cal = new GregorianCalendar();
        String[] pars = new String[3];
        pars[0] = pars[0] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
        pars[1] = "" + id_quote;
        pars[2] = "" + vddt.getId_drain();
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

    public void updateVirtualDdt(DdtInterface vddt) throws NotFoundDBException {
        String sql = "UPDATE `ddt` SET `id_people`=?,`id_people_vett`=?,`destination`=?,`cause`=?,`aspect`=?,`ncoll`=? WHERE `id`=?";

        String[] pars = new String[7];
        pars[0] = "" + vddt.getId_people();
        pars[1] = "" + vddt.getId_people_vett();
        pars[2] = vddt.getDestination();
        pars[3] = vddt.getCause();
        pars[4] = vddt.getAspect();
        pars[5] = vddt.getNcoll();
        pars[6] = "" + vddt.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            System.out.println("Aggiornate 0 righe");
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

    public Boolean existInvoice(int id_drain) throws NotFoundDBException, SQLException {
        //Guardo se esiste un invoice con id_drain dato contando le occorrenze
        String sql = "SELECT COUNT(*) AS tot FROM invoice where id_drain=? AND active=1";
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

    private String getPersonName(int id_people) throws NotFoundDBException, SQLException {
        String name = "";
        String sql = "SELECT name FROM people WHERE active=1 AND id=?";
        String[] pars = new String[1];
        pars[0] = "" + id_people;
        ResultSet rsp = this.db.PreparedStatement(sql, pars);
        String productModel = "";
        while (rsp.next()) {
            name = rsp.getString("name");
        }
        return name;
    }

}
