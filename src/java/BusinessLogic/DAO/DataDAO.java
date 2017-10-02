/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.DataInterface;
import BusinessLogic.VirtualEntities.VirtualData;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class DataDAO {

    private Database db;

    public DataDAO(Database db) {
        this.db = db;
    }

    public DataInterface getData(DataInterface vd) throws SQLException, NotFoundDBException {
        String sql = "SELECT * FROM data WHERE active=1";

        ResultSet rs;
        rs = this.db.select(sql);
        while (rs.next()) {
            vd.setRag_soc(rs.getString("rag_soc"));
            vd.setResponsabile(rs.getString("responsabile"));
            vd.setDomain(rs.getString("domain"));
            vd.setPiva(rs.getString("piva"));
            vd.setCodfisc(rs.getString("codfisc"));
            vd.setIndirizzo(rs.getString("indirizzo"));
            vd.setCivico(rs.getString("civico"));
            vd.setCap(rs.getString("cap"));
            vd.setCity(rs.getString("city"));
            vd.setProvincia(rs.getString("provincia"));
            vd.setMain_mail(rs.getString("main_mail"));
            vd.setContact_from_mail(rs.getString("contact_from_mail"));
            vd.setSysadmin_mail(rs.getString("sysadmin_mail"));
            vd.setSmtp_srv(rs.getString("smtp_srv"));
            vd.setPop3_srv(rs.getString("pop3_srv"));
            vd.setLogo(rs.getBlob("logo").getBytes(1, (int) rs.getBlob("logo").length()));
        }
        return vd;
    }

    public void updateVirtualData(DataInterface vd) throws NotFoundDBException {
        String sql = "UPDATE `data` SET `rag_soc`=?,`responsabile`=?,`domain`=?,`piva`=?,"
                + "`codfisc`=?,`indirizzo`=?,`civico`=?,`cap`=?,`city`=?,`provincia`=?,"
                + "`main_mail`=?,`contact_from_mail`=?,`sysadmin_mail`=?,`smtp_srv`=?,"
                + "`pop3_srv`=?,`logo`=? WHERE `active`=1";

        String[] pars = new String[16];
        pars[0] = vd.getRag_soc();
        pars[1] = vd.getResponsabile();
        pars[2] = vd.getDomain();
        pars[3] = vd.getPiva();
        pars[4] = vd.getCodfisc();
        pars[5] = vd.getIndirizzo();
        pars[6] = vd.getCivico();
        pars[7] = vd.getCap();
        pars[8] = vd.getCity();
        pars[9] = vd.getProvincia();
        pars[10] = vd.getMain_mail();
        pars[11] = vd.getContact_from_mail();
        pars[12] = vd.getSysadmin_mail();
        pars[13] = vd.getSmtp_srv();
        pars[14] = vd.getPop3_srv();
        pars[15] = "" + Arrays.toString(vd.getLogo());
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

}
