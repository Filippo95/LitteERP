/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.DrainRowInterface;
import BusinessLogic.VirtualEntities.VirtualDrainRow;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class DrainRowDAO {

    Database db;

    public DrainRowDAO(Database db) {
        this.db = db;
    }

    public List<DrainRowInterface> getAllDrainRow(DrainRowInterface vd,int id_drain) throws NotFoundDBException, SQLException {
        String sql = "SELECT DISTINCT id_drain,id_product,quantity FROM drain_row WHERE active=1 AND id_drain=?";
        List<DrainRowInterface> lista = new ArrayList();
        String[] pars = new String[1];
        pars[0] = "" + id_drain;
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            int id_product = rs.getInt("id_product");
            sql = "SELECT model,price,`wharehouse_position` FROM product WHERE active=1 AND id=?";
            pars[0] = "" + id_product;
            ResultSet rsp = this.db.PreparedStatement(sql, pars);
            String productModel = "";
            String prodloc=" ";
            double price = 0;
            while (rsp.next()) {
                productModel = rsp.getString("model");
                price = rsp.getDouble("price");
                prodloc=rsp.getString("wharehouse_position");
            }
            vd = new VirtualDrainRow();
            vd.setId_drain(id_drain);
            vd.setId_product(id_product);
            vd.setQuantity(rs.getDouble("quantity"));
            vd.setProductModel(productModel);
            vd.setPrice(price);
            vd.setProdloc(prodloc);
            lista.add(vd);
        }
        return lista;
    }

    public void insertVirtualDrainRow(DrainRowInterface vd) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `drain_row`( `id_drain`,`id_product`, `quantity`) VALUES (?,?,?) ";

        String[] pars = new String[3];
        pars[0] = "" + vd.getId_drain();
        pars[1] = "" + vd.getId_product();
        pars[2] = "" + vd.getQuantity();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void deleteVirtualRow(DrainRowInterface vd) throws NotFoundDBException {
        String sql = "UPDATE `drain_row` SET `active`=0 WHERE `id_drain`=? AND `id_product`=? AND `quantity`=?";
        String[] pars = new String[3];
        pars[0] = "" + vd.getId_drain();
        pars[1] = "" + vd.getId_product();
        pars[2] = "" + vd.getQuantity();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }

}
