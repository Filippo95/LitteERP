/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.LanguageInterface;
import BusinessLogic.VirtualEntities.VirtualLanguage;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class LanguageDAO {

    Database db;

    public LanguageDAO(Database db) {
        this.db = db;
    }

    public List<LanguageInterface> getAllLanguages(LanguageInterface vl) throws SQLException, NotFoundDBException {
        String sql = "SELECT * FROM language WHERE active=1";

        List<LanguageInterface> lista = new ArrayList();
        ResultSet rs = this.db.select(sql);
        while (rs.next()) {
            vl = new VirtualLanguage();
            vl.setId(rs.getInt("id"));
            vl.setName(rs.getString("name"));
            lista.add(vl);
        }
        return lista;
    }

    public LanguageInterface getLanguage(LanguageInterface vl, int id) throws NotFoundDBException, ResultSetDBException, SQLException {
        String sql = "SELECT * FROM language WHERE id=? AND active=1";
        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            vl = new VirtualLanguage();
            vl.setId(rs.getInt("id"));
            vl.setName(rs.getString("name"));
        }

        return vl;
    }

    public void insertVirtualLanguage(LanguageInterface vl) throws NotFoundDBException {
        String sql = "INSERT INTO `language`( `name`) VALUES (?) ";
        String[] pars = new String[1];
        pars[0] = vl.getName();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void updateVirtualLanguage(LanguageInterface vl) throws NotFoundDBException {
        String sql = "UPDATE `language` SET `name`=? WHERE `id`=?";
        String[] pars = new String[2];
        pars[0] = vl.getName();
        pars[1] = "" + vl.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            System.out.println("Aggiornate 0 righe");
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

    public void deleteVirtualLanguage(LanguageInterface vl) throws NotFoundDBException {
        String sql = "UPDATE `language` SET `active`=0 WHERE `id`=? ";
        String[] pars = new String[1];
        pars[0] = "" + vl.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }
}
