/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.DescriptionInterface;
import BusinessLogic.VirtualEntities.VirtualDescription;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author filippo
 */
public class DescriptionDAO {

    private Database db;

    public DescriptionDAO(Database db) {
        this.db = db;
    }


    public List<DescriptionInterface> getDescriptionsByProductId(DescriptionInterface vp,int id) throws NotFoundDBException, ResultSetDBException {
        String sql = "SELECT DISTINCT id_product,id_lang,description FROM description WHERE id_product=? AND active=1";

        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        List<DescriptionInterface> list = new ArrayList();
        try {
            rs = this.db.PreparedStatement(sql, pars);
            while (rs.next()) {
                vp = new VirtualDescription();
                vp.setId_product(rs.getInt("id_product"));
                vp.setId_lang(rs.getInt("id_lang"));
                vp.setDescription(rs.getString("description"));
                list.add(vp);

            }
        } catch (SQLException e) {
            throw new ResultSetDBException("Errore: VirtualProduct GetProduct(int id) " + e.getMessage(), db);
        }
        return list;
    }

    public void insertVirtualDescription(DescriptionInterface vd) throws NotFoundDBException {
        String sql = "INSERT INTO `description`( `id_product`, `id_lang`,`description`) VALUES (?,?,?) ";
        String[] pars = new String[3];
        pars[0] = "" + vd.getId_product();
        pars[1] = "" + vd.getId_lang();
        pars[2] = vd.getDescription();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void updateVirtualDescription(DescriptionInterface vd) throws NotFoundDBException {
        String sql = "UPDATE `description` SET `description`=? WHERE `id_product`=? AND `id_lang`=?";
        String[] pars = new String[3];
        pars[0] = "" + vd.getDescription();
        pars[1] = "" + vd.getId_product();
        pars[2] = ""+ vd.getId_lang();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            System.out.println("Aggiornate 0 righe");
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

    public void deleteVirtualDescription(DescriptionInterface vd) throws NotFoundDBException {
        String sql = "UPDATE `description` SET `active`=0 WHERE `id_product`=? ";
        String[] pars = new String[1];
        pars[0] = "" + vd.getId_product();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }

}
