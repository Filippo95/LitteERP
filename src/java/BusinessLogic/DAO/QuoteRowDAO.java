/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.QuoteRowInterface;
import BusinessLogic.VirtualEntities.VirtualQuoteRow;
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
public class QuoteRowDAO {

    Database db;

    public QuoteRowDAO(Database db) {
        this.db = db;
    }

    public List<QuoteRowInterface> getAllQuoteRowProduct(QuoteRowInterface vquote,int id_quote) throws NotFoundDBException, SQLException {
        String sql = "SELECT DISTINCT id_product,quantity,price,id_quote FROM quote_row_product WHERE active=1 AND id_quote=?";
        List<QuoteRowInterface> lista = new ArrayList();
        String[] pars = new String[1];
        pars[0] = "" + id_quote;
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            int id_product=rs.getInt("id_product");
            sql="SELECT model FROM product WHERE active=1 AND id=?";
            pars[0]=""+id_product;
            ResultSet rsp=this.db.PreparedStatement(sql, pars);
            String productModel="";
            while(rsp.next())
                productModel=rsp.getString("model");
            vquote = new VirtualQuoteRow();
            vquote.setId_quote(rs.getInt("id_quote"));
            vquote.setId_product(id_product);
            vquote.setQuantity(rs.getDouble("quantity"));
            vquote.setPrice(rs.getDouble("price"));
            vquote.setProductModel(productModel);
            lista.add(vquote);
        }
        return lista;
    }

    public List<QuoteRowInterface> getAllQuoteRowFree(QuoteRowInterface vquote,int id_quote) throws NotFoundDBException, SQLException {
        String sql = "SELECT DISTINCT description,quantity,subtot,id_quote FROM quote_row_free WHERE active=1 AND id_quote=?";

        List<QuoteRowInterface> lista = new ArrayList();
        String[] pars = new String[1];
        pars[0] = "" + id_quote;
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            vquote = new VirtualQuoteRow();
            vquote.setId_quote(rs.getInt("id_quote"));
            vquote.setDescription(rs.getString("description"));
            vquote.setQuantity(rs.getDouble("quantity"));
            vquote.setSubtot(rs.getDouble("subtot"));
            lista.add(vquote);
        }
        return lista;
    }

    public void insertVirtualQuoteRowProduct(QuoteRowInterface vquote) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `quote_row_product`( `id_product`,`quantity`, `price`, `id_quote`) VALUES (?,?,?,?) ";

        String[] pars = new String[4];
        pars[0] = "" + vquote.getId_product();
        pars[1] = "" + vquote.getQuantity();
        pars[2] = "" + vquote.getPrice();
        pars[3] = "" + vquote.getId_quote();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void insertVirtualQuoteRowFree(QuoteRowInterface vquote) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `quote_row_free`( `description`,`id_quote`, `quantity`, `subtot`) VALUES (?,?,?,?) ";

        String[] pars = new String[4];
        pars[0] = vquote.getDescription();
        pars[1] = "" + vquote.getId_quote();
        pars[2] = "" + vquote.getQuantity();
        pars[3] = "" + vquote.getSubtot();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void deleteVirtualRowFree(QuoteRowInterface vquote) throws NotFoundDBException {
        String sql = "UPDATE `quote_row_free` SET `active`=0 WHERE `description`=? AND`id_quote`=? AND`quantity`=? AND `subtot`=? ";
        String[] pars = new String[4];
        pars[0] = vquote.getDescription();
        pars[1] = "" + vquote.getId_quote();
        pars[2] = "" + vquote.getQuantity();
        pars[3] = "" + vquote.getSubtot();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }

    public void deleteVirtualRowProduct(QuoteRowInterface vquote) throws NotFoundDBException {
        String sql = "UPDATE `quote_row_product` SET `active`=0 WHERE `id_product`=? AND `quantity`=? AND `price`=? AND `id_quote`=? ";
        String[] pars = new String[4];
        pars[0] = "" + vquote.getId_product();
        pars[1] = "" + vquote.getQuantity();
        pars[2] = "" + vquote.getPrice();
        pars[3] = "" + vquote.getId_quote();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }
}
