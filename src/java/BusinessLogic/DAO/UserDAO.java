/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.UserInterface;
import BusinessLogic.VirtualEntities.VirtualUser;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conversion;

/**
 *
 * @author filippo
 */
public class UserDAO {

    Database db;

    public UserDAO(Database db) {
        this.db = db;
    }

    public List<UserInterface> getAllUser(UserInterface vu) throws ResultSetDBException, NotFoundDBException, SQLException {
        String sql = "SELECT * FROM user Where active=1";

        List<UserInterface> lista = new ArrayList();

        ResultSet rs = this.db.select(sql);
        while (rs.next()) {
            vu = new VirtualUser();
            vu.setId(rs.getInt("id"));
            vu.setUsername(rs.getString("username"));
            vu.setPasswd(rs.getString("passwd"));
            vu.setNome(rs.getString("nome"));
            vu.setCognome(rs.getString("cognome"));
            vu.setEmail(rs.getString("email"));
            vu.setTel(rs.getString("tel"));
            vu.setIndirizzo(rs.getString("indirizzo"));
            vu.setCivico(rs.getString("civico"));
            vu.setCap(rs.getString("cap"));
            vu.setCity(rs.getString("city"));
            vu.setProvincia(rs.getString("provincia"));
            vu.setAdmin(rs.getBoolean("admin"));
            lista.add(vu);
        }

        return lista;

    }

    public UserInterface getUser(UserInterface vu, int id) throws ResultSetDBException, NotFoundDBException {
        String sql = "SELECT * FROM user WHERE id=? AND active=1";

        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        try {
            rs = this.db.PreparedStatement(sql, pars);
            while (rs.next()) {
                vu = new VirtualUser();
                vu.setId(rs.getInt("id"));
                vu.setUsername(rs.getString("username"));
                vu.setPasswd(rs.getString("passwd"));
                vu.setNome(rs.getString("nome"));
                vu.setCognome(rs.getString("cognome"));
                vu.setEmail(rs.getString("email"));
                vu.setTel(rs.getString("tel"));
                vu.setIndirizzo(rs.getString("indirizzo"));
                vu.setCivico(rs.getString("civico"));
                vu.setCap(rs.getString("cap"));
                vu.setCity(rs.getString("city"));
                vu.setProvincia(rs.getString("provincia"));
                vu.setAdmin(rs.getBoolean("admin"));
            }
        } catch (SQLException e) {
            throw new ResultSetDBException("Errore: VirtualUser GetUser(int id) " + e.getMessage(), db);
        }
        return vu;

    }

    public UserInterface getUser(UserInterface vu, String username, String password) throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException {
        String sql = "SELECT * FROM user WHERE username=? AND passwd=? AND active=1";

        ResultSet rs;
        String[] pars = new String[2];
        pars[0] = username;
        pars[1] = Conversion.fromStringtoMD5(password);
        vu=null;
        try {
            rs = this.db.PreparedStatement(sql, pars);
            while (rs.next()) {
                vu = new VirtualUser();
                vu.setId(rs.getInt("id"));
                vu.setUsername(rs.getString("username"));
                vu.setPasswd(rs.getString("passwd"));
                vu.setNome(rs.getString("nome"));
                vu.setCognome(rs.getString("cognome"));
                vu.setEmail(rs.getString("email"));
                vu.setTel(rs.getString("tel"));
                vu.setIndirizzo(rs.getString("indirizzo"));
                vu.setCivico(rs.getString("civico"));
                vu.setCap(rs.getString("cap"));
                vu.setCity(rs.getString("city"));
                vu.setProvincia(rs.getString("provincia"));
                vu.setAdmin(rs.getBoolean("admin"));
            }
        } catch (SQLException e) {
            throw new ResultSetDBException("Errore: VirtualUser GetUser(int id) " + e.getMessage(), db);
        }
        return vu;

    }

    public UserInterface getUserFromIDAndPSWD(UserInterface vu, String uid, String password) throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException {
        String sql = "SELECT * FROM user WHERE  passwd=? AND active=1";
        vu=null;
        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = password;
        try {
            rs = this.db.PreparedStatement(sql, pars);
            while (rs.next()) {
                String hashid = Conversion.fromStringtoMD5("" + rs.getInt("id"));
                if (hashid.equals(uid)) {
                    vu = new VirtualUser();
                    vu.setId(rs.getInt("id"));
                    vu.setUsername(rs.getString("username"));
                    vu.setPasswd(rs.getString("passwd"));
                    vu.setNome(rs.getString("nome"));
                    vu.setCognome(rs.getString("cognome"));
                    vu.setEmail(rs.getString("email"));
                    vu.setTel(rs.getString("tel"));
                    vu.setIndirizzo(rs.getString("indirizzo"));
                    vu.setCivico(rs.getString("civico"));
                    vu.setCap(rs.getString("cap"));
                    vu.setCity(rs.getString("city"));
                    vu.setProvincia(rs.getString("provincia"));
                    vu.setAdmin(rs.getBoolean("admin"));
                }
            }
        } catch (SQLException e) {
            throw new ResultSetDBException("Errore: VirtualUser GetUser(int id) " + e.getMessage(), db);
        }
        return vu;

    }

    public void updateVirtualUser(UserInterface vu) throws NotFoundDBException {
        String sql = "UPDATE `user` SET "
                + "`username`=?,"
                + "`passwd`=?,"
                + "`nome`=?,"
                + "`cognome`=?,"
                + "`email`=?,"
                + "`tel`=?,"
                + "`indirizzo`=?,"
                + "`civico`=?,"
                + "`cap`=?,"
                + "`city`=?,"
                + "`provincia`=?,"
                + "`admin`=?"
                + "WHERE `id`=?";

        String[] pars = new String[13];
        pars[0] = vu.getUsername();
        pars[1] = vu.getPasswd();
        pars[2] = vu.getNome();
        pars[3] = vu.getCognome();
        pars[4] = vu.getEmail();
        pars[5] = vu.getTel();
        pars[6] = vu.getIndirizzo();
        pars[7] = vu.getCivico();
        pars[8] = vu.getCap();
        pars[9] = vu.getCity();
        pars[10] = vu.getProvincia();
        if (vu.getAdmin()) {
            pars[11] = "" + 1;
        } else {
            pars[11] = "" + 0;
        }
        pars[12] = "" + vu.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Aggiornate 0 righe");
        }

    }

    public int insertVirtualUser(UserInterface vu) throws NotFoundDBException, NoSuchAlgorithmException, ResultSetDBException, SQLException {
        String sql = "INSERT INTO `user`( `username`, `passwd`, `nome`, `cognome`, `email`, `tel`, `indirizzo`, `civico`, `cap`, `city`, `provincia`, `admin`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        String[] pars = new String[12];
        pars[0] = vu.getUsername();
        pars[1] = Conversion.fromStringtoMD5(vu.getPasswd());
        pars[2] = vu.getNome();
        pars[3] = vu.getCognome();
        pars[4] = vu.getEmail();
        pars[5] = vu.getTel();
        pars[6] = vu.getIndirizzo();
        pars[7] = vu.getCivico();
        pars[8] = vu.getCap();
        pars[9] = vu.getCity();
        pars[10] = vu.getProvincia();
        if (vu.getAdmin()) {
            pars[11] = "" + 1;
        } else {
            pars[11] = "" + 0;
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

    public void deleteVirtualUser(UserInterface vu) throws NotFoundDBException {
        String sql = "UPDATE `user` SET `active`=0 WHERE `id`=? ";

        String[] pars = new String[1];

        pars[0] = "" + vu.getId();
        this.db.PreparedStatementUpdate(sql, pars);
    }

}
