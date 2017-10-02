package BusinessLogic.DAO;

import BusinessLogic.Interface.PeopleInterface;
import BusinessLogic.VirtualEntities.VirtualPeople;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author filippo
 */
public class PeopleDAO {

    Database db;

    public PeopleDAO(Database db) {
        this.db = db;
    }

    public List<PeopleInterface> getAllPeople(PeopleInterface vp) throws ResultSetDBException, NotFoundDBException, SQLException {
        String sql = "SELECT * FROM people WHERE active=1";

        List<PeopleInterface> lista = new ArrayList();

        ResultSet rs = this.db.select(sql);
        while (rs.next()) {
            vp = new VirtualPeople();
            vp.setId(rs.getInt("id"));
            vp.setName(rs.getString("name"));
            vp.setRefer_to(rs.getString("refer_to"));
            vp.setEmail(rs.getString("email"));
            vp.setTelephone(rs.getString("telephone"));
            vp.setCod_fisc(rs.getString("cod_fisc"));
            vp.setPiva(rs.getString("piva"));
            vp.setAddress(rs.getString("address"));
            vp.setCivic(rs.getString("civic"));
            vp.setCap(rs.getString("cap"));
            vp.setCity(rs.getString("city"));
            vp.setProvince(rs.getString("province"));
            vp.setProvider(rs.getBoolean("provider"));
            vp.setCustomer(rs.getBoolean("customer"));
            lista.add(vp);
        }
        return lista;

    }

    public PeopleInterface getPeople(PeopleInterface vp,int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        String sql = "SELECT * FROM people WHERE id=? AND active=1";

        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            vp = new VirtualPeople();
            vp.setId(rs.getInt("id"));
            vp.setName(rs.getString("name"));
            vp.setRefer_to(rs.getString("refer_to"));
            vp.setEmail(rs.getString("email"));
            vp.setTelephone(rs.getString("telephone"));
            vp.setCod_fisc(rs.getString("cod_fisc"));
            vp.setPiva(rs.getString("piva"));
            vp.setAddress(rs.getString("address"));
            vp.setCivic(rs.getString("civic"));
            vp.setCap(rs.getString("cap"));
            vp.setCity(rs.getString("city"));
            vp.setProvince(rs.getString("province"));
            vp.setProvider(rs.getBoolean("provider"));
            vp.setCustomer(rs.getBoolean("customer"));
        }
        return vp;

    }

    public void updateVirtualPeople(PeopleInterface vp) throws NotFoundDBException {
        String sql = "UPDATE `people` SET "
                + "`name`=?,"
                + "`refer_to`=?,"
                + "`email`=?,"
                + "`telephone`=?,"
                + "`cod_fisc`=?,"
                + "`piva`=?,"
                + "`address`=?,"
                + "`civic`=?,"
                + "`cap`=?,"
                + "`city`=?,"
                + "`province`=?,"
                + "`provider`=?,"
                + "`customer`=? "
                + "WHERE `id`=?";

        String[] pars = new String[14];
        pars[0] = vp.getName();
        pars[1] = vp.getRefer_to();
        pars[2] = vp.getEmail();
        pars[3] = vp.getTelephone();
        pars[4] = vp.getCod_fisc();
        pars[5] = vp.getPiva();
        pars[6] = vp.getAddress();
        pars[7] = vp.getCivic();
        pars[8] = vp.getCap();
        pars[9] = vp.getCity();
        pars[10] = vp.getProvince();
        if (vp.getProvider()) {
            pars[11] = "" + 1;
        } else {
            pars[11] = "" + 0;
        }
        if (vp.getCustomer()) {
            pars[12] = "" + 1;
        } else {
            pars[12] = "" + 0;
        }
        pars[13] = "" + vp.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

    public int insertVirtualPeople(PeopleInterface vp) throws NotFoundDBException, NoSuchAlgorithmException, SQLException {
        String sql = "INSERT INTO `people`(`name`,`refer_to`,`email`,`telephone`,`cod_fisc`,`piva`,`address`,`civic`,`cap`,`city`,`province`,`provider`,`customer`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String[] pars = new String[13];
        pars[0] = vp.getName();
        pars[1] = vp.getRefer_to();
        pars[2] = vp.getEmail();
        pars[3] = vp.getTelephone();
        pars[4] = vp.getCod_fisc();
        pars[5] = vp.getPiva();
        pars[6] = vp.getAddress();
        pars[7] = vp.getCivic();
        pars[8] = vp.getCap();
        pars[9] = vp.getCity();
        pars[10] = vp.getProvince();
        if (vp.getProvider()) {
            pars[11] = "" + 1;
        } else {
            pars[11] = "" + 0;
        }
        if (vp.getCustomer()) {
            pars[12] = "" + 1;
        } else {
            pars[12] = "" + 0;
        }
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
        sql = "SELECT LAST_INSERT_ID();";
        ResultSet rs = this.db.select(sql);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("LAST_INSERT_ID()");
        }
        System.out.println("id utente inserito:" + id);
        return id;
    }

    public void deleteVirtualPeople(PeopleInterface vp) throws NotFoundDBException {
        String sql = "UPDATE `people` SET `active`=0 WHERE `id`=? ";

        String[] pars = new String[1];

        pars[0] = "" + vp.getId();
        this.db.PreparedStatementUpdate(sql, pars);
    }

    public List<PeopleInterface> getAllProviders(PeopleInterface vp) throws NotFoundDBException, SQLException {
        String sql = "SELECT * FROM people WHERE `provider`=1 AND active=1";

        List<PeopleInterface> lista = new ArrayList();
        ResultSet rs = this.db.select(sql);
        while (rs.next()) {
            vp = new VirtualPeople();
            vp.setId(rs.getInt("id"));
            vp.setName(rs.getString("name"));
            vp.setRefer_to(rs.getString("refer_to"));
            vp.setEmail(rs.getString("email"));
            vp.setTelephone(rs.getString("telephone"));
            vp.setCod_fisc(rs.getString("cod_fisc"));
            vp.setPiva(rs.getString("piva"));
            vp.setAddress(rs.getString("address"));
            vp.setCivic(rs.getString("civic"));
            vp.setCap(rs.getString("cap"));
            vp.setCity(rs.getString("city"));
            vp.setProvince(rs.getString("province"));
            vp.setProvider(rs.getBoolean("provider"));
            vp.setCustomer(rs.getBoolean("customer"));
            lista.add(vp);
        }
        return lista;

    }

}
