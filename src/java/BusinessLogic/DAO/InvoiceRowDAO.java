/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.InvoiceRowInterface;
import BusinessLogic.VirtualEntities.VirtualInvoiceRow;
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
public class InvoiceRowDAO {

    Database db;

    public InvoiceRowDAO(Database db) {
        this.db = db;
    }

    public List<InvoiceRowInterface> getAllInvoiceRowProduct(InvoiceRowInterface vinvoice, int id_invoice) throws NotFoundDBException, SQLException {
        String sql = "SELECT DISTINCT id_product,quantity,price,id_invoice FROM invoice_row_product WHERE active=1 AND id_invoice=?";
        List<InvoiceRowInterface> lista = new ArrayList();
        String[] pars = new String[1];
        pars[0] = "" + id_invoice;
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
            vinvoice = new VirtualInvoiceRow();
            vinvoice.setId_invoice(rs.getInt("id_invoice"));
            vinvoice.setId_product(id_product);
            vinvoice.setQuantity(rs.getDouble("quantity"));
            vinvoice.setPrice(rs.getDouble("price"));
            vinvoice.setProductModel(productModel);
            lista.add(vinvoice);
        }
        return lista;
    }

    public List<InvoiceRowInterface> getAllInvoiceRowFree(InvoiceRowInterface vinvoice, int id_invoice) throws NotFoundDBException, SQLException {
        String sql = "SELECT DISTINCT description,quantity,subtot,id_invoice FROM invoice_row_free WHERE active=1 AND id_invoice=?";

        List<InvoiceRowInterface> lista = new ArrayList();
        String[] pars = new String[1];
        pars[0] = "" + id_invoice;
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            vinvoice = new VirtualInvoiceRow();
            vinvoice.setId_invoice(rs.getInt("id_invoice"));
            vinvoice.setDescription(rs.getString("description"));
            vinvoice.setQuantity(rs.getDouble("quantity"));
            vinvoice.setSubtot(rs.getDouble("subtot"));
            lista.add(vinvoice);
        }
        return lista;
    }

    public void insertVirtualInvoiceRowProduct(InvoiceRowInterface vinvoice) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `invoice_row_product`( `id_product`,`quantity`, `price`, `id_invoice`) VALUES (?,?,?,?) ";

        String[] pars = new String[4];
        pars[0] = "" + vinvoice.getId_product();
        pars[1] = "" + vinvoice.getQuantity();
        pars[2] = "" + vinvoice.getPrice();
        pars[3] = "" + vinvoice.getId_invoice();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void insertVirtualInvoiceRowFree(InvoiceRowInterface vinvoice) throws NotFoundDBException, SQLException {
        String sql = "INSERT INTO `invoice_row_free`( `description`,`id_invoice`, `quantity`, `subtot`) VALUES (?,?,?,?) ";

        String[] pars = new String[4];
        pars[0] = vinvoice.getDescription();
        pars[1] = "" + vinvoice.getId_invoice();
        pars[2] = "" + vinvoice.getQuantity();
        pars[3] = "" + vinvoice.getSubtot();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void insertVirtualInvoiceRowsFromDrainId(int id_drain, int id_invoice) throws NotFoundDBException, SQLException {
        String sql = "SELECT DISTINCT id_product,quantity,price FROM ddt_row_product WHERE id_ddt=(SELECT id FROM ddt WHERE id_drain=?) AND active=1 ";
        String[] pars = new String[1];
        pars[0] = "" + id_drain;
        ResultSet rs = db.PreparedStatement(sql, pars);
        while (rs.next()) {
            sql = "INSERT INTO invoice_row_product ( `id_product`,`quantity`, `price`, `id_invoice`) VALUES (?,?,?,?) ";
            pars = new String[4];
            pars[0] = "" + rs.getInt("id_product");
            pars[1] = "" + rs.getDouble("quantity");
            pars[2] = "" + rs.getDouble("price");
            pars[3] = "" + id_invoice;
            if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
                throw new NotFoundDBException("Inserite 0 righe nel database");
            }
        }
        sql = "SELECT DISTINCT description,quantity,unit_price FROM ddt_row_free WHERE id_ddt=(SELECT id FROM ddt WHERE id_drain=?) AND active=1 ";
        pars = new String[1];
        pars[0] = "" + id_drain;
        rs = db.PreparedStatement(sql, pars);
        while (rs.next()) {
            sql = "INSERT INTO invoice_row_free ( `description`,`quantity`, `subtot`, `id_invoice`) VALUES (?,?,?,?) ";
            pars = new String[4];
            pars[0] = "" + rs.getString("description");
            pars[1] = "" + rs.getDouble("quantity");
            pars[2] = "" + rs.getDouble("unit_price");
            pars[3] = "" + id_invoice;
            if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
                throw new NotFoundDBException("Inserite 0 righe nel database");
            }
        }
    }

    public void insertVirtualInvoiceRowsQuote(int id_quote, int id_invoice) throws NotFoundDBException, SQLException {
        String sql = "SELECT DISTINCT id_product,quantity,price FROM quote_row_product WHERE id_quote=? AND active=1 ";
        String[] pars = new String[1];
        pars[0] = "" + id_quote;
        ResultSet rs = db.PreparedStatement(sql, pars);
        while (rs.next()) {
            sql = "INSERT INTO invoice_row_product ( `id_product`,`quantity`, `price`, `id_invoice`) VALUES (?,?,?,?) ";
            pars = new String[4];
            pars[0] = "" + rs.getInt("id_product");
            pars[1] = "" + rs.getDouble("quantity");
            pars[2] = "" + rs.getDouble("price");
            pars[3] = "" + id_invoice;
            if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
                throw new NotFoundDBException("Inserite 0 righe nel database");
            }
            sql = "INSERT INTO drain_row ( `id_product`,`quantity`, `id_drain`) VALUES (?,?,(SELECT id_drain FROM invoice WHERE id=?)) ";
            pars = new String[3];
            pars[0] = "" + rs.getInt("id_product");
            pars[1] = "" + rs.getDouble("quantity");
            pars[2] = "" + id_invoice;
            if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
                throw new NotFoundDBException("Inserite 0 righe nel database");
            }
        }
        sql = "SELECT DISTINCT description,quantity,subtot FROM quote_row_free WHERE id_quote=? AND active=1 ";
        pars = new String[1];
        pars[0] = "" + id_quote;
        rs = db.PreparedStatement(sql, pars);
        while (rs.next()) {
            sql = "INSERT INTO invoice_row_free ( `description`,`quantity`, `subtot`, `id_invoice`) VALUES (?,?,?,?) ";
            pars = new String[4];
            pars[0] = "" + rs.getString("description");
            pars[1] = "" + rs.getDouble("quantity");
            pars[2] = "" + rs.getDouble("subtot");
            pars[3] = "" + id_invoice;
            if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
                throw new NotFoundDBException("Inserite 0 righe nel database");
            }
        }
    }

    public void deleteVirtualRowFree(InvoiceRowInterface vinvoice) throws NotFoundDBException {
        String sql = "UPDATE `invoice_row_free` SET `active`=0 WHERE `description`=? AND`id_invoice`=? AND`quantity`=? AND `subtot`=? ";
        String[] pars = new String[4];
        pars[0] = vinvoice.getDescription();
        pars[1] = "" + vinvoice.getId_invoice();
        pars[2] = "" + vinvoice.getQuantity();
        pars[3] = "" + vinvoice.getSubtot();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }

    public void deleteVirtualRowProduct(InvoiceRowInterface vinvoice) throws NotFoundDBException {
        String sql = "UPDATE `invoice_row_product` SET `active`=0 WHERE `id_product`=? AND `quantity`=? AND `price`=? AND `id_invoice`=? ";
        String[] pars = new String[4];
        pars[0] = "" + vinvoice.getId_product();
        pars[1] = "" + vinvoice.getQuantity();
        pars[2] = "" + vinvoice.getPrice();
        pars[3] = "" + vinvoice.getId_invoice();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }

}
