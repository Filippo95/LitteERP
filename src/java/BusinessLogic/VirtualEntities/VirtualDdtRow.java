/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.DdtRowDAO;
import BusinessLogic.Interface.DdtRowInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class VirtualDdtRow implements DdtRowInterface {

    private int id_ddt;
    private String description;
    private int id_product;
    private double quantity;
    private double unit_price;
    private double price;
    
    private String productModel;

    Database db;
    DdtRowDAO dao;

    public VirtualDdtRow(Database db) {
        this.db = db;
        dao = new DdtRowDAO(this.db);
    }

    public VirtualDdtRow() {
    }

    public VirtualDdtRow(int id_ddt, int id_product, double quantity, double price, Database db) {
        this.id_ddt = id_ddt;
        this.id_product = id_product;
        this.quantity = quantity;
        this.price = price;
        this.db = db;
        dao = new DdtRowDAO(this.db);
    }
    public VirtualDdtRow(int id_ddt, String description, double quantity, double unit_price, Database db) {
        this.id_ddt = id_ddt;
        this.description = description;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.db = db;
        dao = new DdtRowDAO(this.db);
    }

    public List<DdtRowInterface> getAllRowProduct(int id_ddt) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllDdtRowProduct(this,id_ddt);
    }

    public List<DdtRowInterface> getAllRowFree(int id_ddt) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllDdtRowFree(this,id_ddt);
    }

    public void insertRowFree() throws ResultSetDBException, NotFoundDBException, SQLException {
        dao.insertVirtualDdtRowFree(this);
    }

    public void insertRowProduct() throws ResultSetDBException, NotFoundDBException, SQLException {
        dao.insertVirtualDdtRowProduct(this);
    }

    public void insertRowsFromQuote(int id_quote, int id_ddt) throws NotFoundDBException, SQLException {
        dao.insertVirtualDdtRowsFromQuote(id_quote, id_ddt);
    }

    public void deleteRowFree() throws NotFoundDBException {
        dao.deleteVirtualRowFree(this);
    }

    public void deleteRowProduct() throws NotFoundDBException {
        dao.deleteVirtualRowProduct(this);
    }

    public int getId_ddt() {
        return id_ddt;
    }

    public void setId_ddt(int id_ddt) {
        this.id_ddt = id_ddt;
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

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
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
