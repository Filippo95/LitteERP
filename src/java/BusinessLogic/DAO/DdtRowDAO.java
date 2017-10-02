/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.DdtRowInterface;
import BusinessLogic.VirtualEntities.VirtualDdtRow;
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
public class DdtRowDAO {

    Database db;

    public DdtRowDAO(Database db) {
        this.db = db;
    }

    public List<DdtRowInterface> getAllDdtRowProduct(DdtRowInterface vddt,int id_ddt) throws NotFoundDBException, SQLException {
        String sql = "SELECT DISTINCT id_product,quantity,price,id_ddt FROM ddt_row_product WHERE active=1 AND id_ddt=?";

        List<DdtRowInterface> lista = new ArrayList();
        String[] pars = new String[1];
        pars[0] = "" + id_ddt;
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            int id_product = rs.getInt("id_product");
            sql = "SELECT model FROM product WHERE active=1 AND id=?";
            pars[0] = "" + id_product;
            ResultSet rsp = this.db.PreparedStatement(sql, pars);
            String productModel = "";
            while (rsp.next()) {
                productModel = rsp.getString("model");
            }
            vddt = new VirtualDdtRow();
            vddt.setId_ddt(rs.getInt("id_ddt"));
            vddt.setId_product(rs.getInt("id_product"));
            vddt.setQuantity(rs.getDouble("quantity"));
            vddt.setPrice(rs.getDouble("price"));
            vddt.setProductModel(productModel);
            lista.add(vddt);
        }
        return lista;
    }

    public List<DdtRowInterface> getAllDdtRowFree(DdtRowInterface vddt,int id_ddt) throws NotFoundDBException, SQLException {
        String sql = "SELECT DISTINCT description,id_ddt,quantity,unit_price FROM ddt_row_free WHERE active=1 AND id_ddt=?";

        List<DdtRowInterface> lista = new ArrayList();
        String[] pars = new String[1];
        pars[0] = "" + id_ddt;
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            vddt = new VirtualDdtRow();
            vddt.setId_ddt(rs.getInt("id_ddt"));
            vddt.setDescription(rs.getString("description"));
            vddt.setQuantity(rs.getDouble("quantity"));
            vddt.setUnit_price(rs.getDouble("unit_price"));
            lista.add(vddt);
        }
        return lista;
    }

    public void insertVirtualDdtRowProduct(DdtRowInterface vddt) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `ddt_row_product`( `id_product`,`quantity`, `price`, `id_ddt`) VALUES (?,?,?,?) ";

        String[] pars = new String[4];
        pars[0] = "" + vddt.getId_product();
        pars[1] = "" + vddt.getQuantity();
        pars[2] = "" + vddt.getPrice();
        pars[3] = "" + vddt.getId_ddt();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void insertVirtualDdtRowFree(DdtRowInterface vddt) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `ddt_row_free`( `description`,`id_ddt`, `quantity`, `unit_price`) VALUES (?,?,?,?) ";

        String[] pars = new String[4];
        pars[0] = "" + vddt.getDescription();
        pars[1] = "" + vddt.getId_ddt();
        pars[2] = "" + vddt.getQuantity();
        pars[3] = "" + vddt.getUnit_price();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void insertVirtualDdtRowsFromQuote(int id_quote, int id_ddt) throws NotFoundDBException, SQLException {
        String sql = "SELECT DISTINCT id_product,quantity,price FROM quote_row_product WHERE id_quote=? AND active=1 ";
        String[] pars = new String[1];
        pars[0] = "" + id_quote;
        ResultSet rs = db.PreparedStatement(sql, pars);
        while (rs.next()) {
            sql = "INSERT INTO ddt_row_product ( `id_product`,`quantity`, `price`, `id_ddt`) VALUES (?,?,?,?) ";
            pars = new String[4];
            pars[0] = "" + rs.getInt("id_product");
            pars[1] = "" + rs.getDouble("quantity");
            pars[2] = "" + rs.getDouble("price");
            pars[3] = "" + id_ddt;
            if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
                throw new NotFoundDBException("Inserite 0 righe nel database");
            }
            sql = "INSERT INTO drain_row ( `id_product`,`quantity`, `id_drain`) VALUES (?,?,(SELECT id_drain FROM ddt WHERE id=?)) ";
            pars=new String[3];
             pars[0] = "" + rs.getInt("id_product");
            pars[1] = "" + rs.getDouble("quantity");
            pars[2] = "" + id_ddt;
            if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
                throw new NotFoundDBException("Inserite 0 righe nel database");
            }
        }
        sql = "SELECT DISTINCT description,quantity,subtot FROM quote_row_free WHERE id_quote=? AND active=1 ";
        pars = new String[1];
        pars[0] = "" + id_quote;
        rs = db.PreparedStatement(sql, pars);
        while (rs.next()) {
            sql = "INSERT INTO ddt_row_free ( `description`,`quantity`, `unit_price`, `id_ddt`) VALUES (?,?,?,?) ";
            pars = new String[4];
            pars[0] = "" + rs.getString("description");
            pars[1] = "" + rs.getDouble("quantity");
            pars[2] = "" + rs.getDouble("subtot");
            pars[3] = "" + id_ddt;
            if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
                throw new NotFoundDBException("Inserite 0 righe nel database");
            }
        }
    }

    public void deleteVirtualRowFree(DdtRowInterface vddt) throws NotFoundDBException {
        String sql = "UPDATE `ddt_row_free` SET `active`=0 WHERE `description`=? AND`id_ddt`=? AND`quantity`=? AND `unit_price`=? ";
        String[] pars = new String[4];
        pars[0] = vddt.getDescription();
        pars[1] = "" + vddt.getId_ddt();
        pars[2] = "" + vddt.getQuantity();
        pars[3] = "" + vddt.getUnit_price();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }

    public void deleteVirtualRowProduct(DdtRowInterface vddt) throws NotFoundDBException {
        String sql = "UPDATE `ddt_row_product` SET `active`=0 WHERE `id_product`=? AND `quantity`=? AND `price`=? AND `id_ddt`=? ";
        String[] pars = new String[4];
        pars[0] = "" + vddt.getId_product();
        pars[1] = "" + vddt.getQuantity();
        pars[2] = "" + vddt.getPrice();
        pars[3] = "" + vddt.getId_ddt();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }

}
