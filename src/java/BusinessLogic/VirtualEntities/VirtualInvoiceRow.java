/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.InvoiceRowDAO;
import BusinessLogic.Interface.InvoiceRowInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class VirtualInvoiceRow implements InvoiceRowInterface {

    private int id_invoice;
    private String description;
    private int id_product;
    private double quantity;
    private double subtot;
    private double price;

    private String productModel;

    Database db;
    InvoiceRowDAO dao;

    public VirtualInvoiceRow(Database db) {
        this.db = db;
        dao = new InvoiceRowDAO(this.db);
    }

    public VirtualInvoiceRow() {
    }

    public VirtualInvoiceRow(int id_invoice, int id_product, double quantity, double price, Database db) {
        this.id_invoice = id_invoice;
        this.id_product = id_product;
        this.quantity = quantity;
        this.price = price;
        this.db = db;
        dao = new InvoiceRowDAO(this.db);
    }

    public VirtualInvoiceRow(int id_invoice, String description, double quantity, double subtot, Database db) {
        this.id_invoice = id_invoice;
        this.description = description;
        this.quantity = quantity;
        this.subtot = subtot;
        this.db = db;
        dao = new InvoiceRowDAO(this.db);
    }

    public List<InvoiceRowInterface> getAllRowProduct(int id_ddt) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllInvoiceRowProduct(this,id_ddt);
    }

    public List<InvoiceRowInterface> getAllRowFree(int id_ddt) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllInvoiceRowFree(this,id_ddt);
    }

    public void insertRowFree() throws ResultSetDBException, NotFoundDBException, SQLException {
        dao.insertVirtualInvoiceRowFree(this);
    }

    public void insertRowProduct() throws ResultSetDBException, NotFoundDBException, SQLException {
        dao.insertVirtualInvoiceRowProduct(this);
    }
    
    public void insertRowsFromDrain(int id_drain,int id_invoice) throws NotFoundDBException, SQLException {
        dao.insertVirtualInvoiceRowsFromDrainId(id_drain,id_invoice);
    }
    
    public void insertRowsFromQuote(int id_quote, int id) throws NotFoundDBException, SQLException {
        dao.insertVirtualInvoiceRowsQuote(id_quote,id);
    }

    public void deleteRowFree() throws NotFoundDBException {
        dao.deleteVirtualRowFree(this);
    }

    public void deleteRowProduct() throws NotFoundDBException {
        dao.deleteVirtualRowProduct(this);
    }

    public int getId_invoice() {
        return id_invoice;
    }

    public void setId_invoice(int id_invoice) {
        this.id_invoice = id_invoice;
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
