/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.DrainRowDAO;
import BusinessLogic.Interface.DrainRowInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class VirtualDrainRow implements DrainRowInterface {

    private int id_drain;
    private int id_product;
    private double quantity;

    private String productModel;
    private String prodloc;
    private double price;

    Database db;
    DrainRowDAO dao;

    public VirtualDrainRow(Database db) {
        this.db = db;
        dao = new DrainRowDAO(this.db);
    }

    public VirtualDrainRow() {
    }

    public VirtualDrainRow(int id_drain, int id_product, double quantity, Database db) {
        this.id_drain = id_drain;
        this.id_product = id_product;
        this.quantity = quantity;
        this.db = db;
        dao = new DrainRowDAO(this.db);
    }

    public List<DrainRowInterface> getAllRow(int id_drain) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllDrainRow(this, id_drain);
    }

    public void insertRow() throws ResultSetDBException, NotFoundDBException, SQLException {
        dao.insertVirtualDrainRow(this);
    }

    public void deleteRowFree() throws NotFoundDBException {
        dao.deleteVirtualRow(this);
    }

    public int getId_drain() {
        return id_drain;
    }

    public void setId_drain(int id_drain) {
        this.id_drain = id_drain;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProdloc() {
        return prodloc;
    }

    public void setProdloc(String prodloc) {
        this.prodloc = prodloc;
    }

}
