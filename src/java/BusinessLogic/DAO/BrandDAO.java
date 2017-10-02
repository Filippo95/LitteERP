/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.BrandInterface;
import BusinessLogic.VirtualEntities.VirtualBrand;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Conversion;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class BrandDAO {

    Database db;

    public BrandDAO(Database db) {
        this.db = db;
    }

    public List<BrandInterface> getAllBrand(BrandInterface vb) throws SQLException, NotFoundDBException {
        String sql = "SELECT * FROM brand WHERE active=?";
        String[] pars={""+1};
        List<BrandInterface> lista = new ArrayList();
        ResultSet rs = this.db.PreparedStatement(sql,pars);
        while (rs.next()) {
            vb=new VirtualBrand();
            vb.setId(rs.getInt("id"));
            vb.setName(rs.getString("name"));
            vb.setDescription(rs.getString("description"));
            vb.setHasProducts(this.hasProducts(vb.getId()));
            System.out.println(vb.getId()+vb.getName()+vb.getDescription());
            lista.add(vb);
        }
        for(int i=0;i<lista.size();i++){
            System.out.println(lista.get(i).getName());
        }
        return lista;
    }

    public BrandInterface getBrand(BrandInterface vb,int id) throws NotFoundDBException, ResultSetDBException, SQLException {
        String sql = "SELECT * FROM brand WHERE id=? AND active=1";

        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            vb.setId(rs.getInt("id"));
            vb.setName(rs.getString("name"));
            vb.setDescription(rs.getString("description"));
            vb.setHasProducts(this.hasProducts(vb.getId()));
        }
        vb.setHasProducts(this.hasProducts(vb.getId()));
        return vb;
    }

    public void insertVirtualBrand(BrandInterface vb) throws NotFoundDBException {
        String sql = "INSERT INTO `brand`( `name`, `description`) VALUES (?,?) ";
        String[] pars = new String[2];
        pars[0] = vb.getName();
        pars[1] = vb.getDescription();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void updateVirtualBrand(BrandInterface vb) throws NotFoundDBException {
        String sql = "UPDATE `brand` SET "
                + "`name`=?,"
                + "`description`=?"
                + "WHERE `id`=?";

        String[] pars = new String[3];
        pars[0] = vb.getName();
        pars[1] = vb.getDescription();
        pars[2] = "" + vb.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {

            System.out.println("Aggiornate 0 righe");
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

    public void deleteVirtualBrand(BrandInterface vb) throws NotFoundDBException {
        String sql = "UPDATE `brand` SET `active`=0 WHERE `id`=? ";

        String[] pars = new String[1];

        pars[0] = "" + vb.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }

    private Boolean hasProducts(int id_brand) throws NotFoundDBException, SQLException {
        String sql = "SELECT COUNT(*) as products FROM product WHERE id_brand=? AND active=1";
        String[] pars = new String[1];
        pars[0] = "" + id_brand;
        int count = 0;
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            count = rs.getInt("products");
        }
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

}
