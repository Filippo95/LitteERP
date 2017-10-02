/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.QuoteRowDAO;
import BusinessLogic.Interface.QuoteRowInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class VirtualQuoteRow implements QuoteRowInterface {

    private int id_quote;
    private String description;
    private int id_product;
    private double quantity;
    private double subtot;
    private double price;

    private String productModel;

    Database db;
    QuoteRowDAO dao;

    public VirtualQuoteRow(Database db) {
        this.db = db;
        dao = new QuoteRowDAO(this.db);
    }

    public VirtualQuoteRow() {
    }

    public VirtualQuoteRow(int id_quote, int id_product, double quantity, double price, Database db) {
        this.id_quote = id_quote;
        this.id_product = id_product;
        this.quantity = quantity;
        this.price = price;
        this.db = db;
        dao = new QuoteRowDAO(this.db);
    }

    public VirtualQuoteRow(int id_quote, String description, double quantity, double subtot, Database db) {
        this.id_quote = id_quote;
        this.description = description;
        this.quantity = quantity;
        this.subtot = subtot;
        this.db = db;
        dao = new QuoteRowDAO(this.db);
    }

    public List<QuoteRowInterface> getAllRowProduct(int id_quote) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllQuoteRowProduct(this,id_quote);
    }

    public List<QuoteRowInterface> getAllRowFree(int id_quote) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllQuoteRowFree(this,id_quote);
    }

    /*public VirtualQuote getVirtualQuote(int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getQuote(id);
    }*/
    public void insertRowFree() throws ResultSetDBException, NotFoundDBException, SQLException {
        dao.insertVirtualQuoteRowFree(this);
    }

    public void insertRowProduct() throws ResultSetDBException, NotFoundDBException, SQLException {
        dao.insertVirtualQuoteRowProduct(this);
    }

    public void deleteRowFree() throws NotFoundDBException {
        dao.deleteVirtualRowFree(this);
    }

    public void deleteRowProduct() throws NotFoundDBException {
        dao.deleteVirtualRowProduct(this);
    }

    public int getId_quote() {
        return id_quote;
    }

    public void setId_quote(int id_quote) {
        this.id_quote = id_quote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getSubtot() {
        return subtot;
    }

    public void setSubtot(double subtot) {
        this.subtot = subtot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

}
