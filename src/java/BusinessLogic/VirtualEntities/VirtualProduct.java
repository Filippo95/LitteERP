/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.DescriptionDAO;
import BusinessLogic.DAO.ImageDAO;
import BusinessLogic.DAO.ProductDAO;
import BusinessLogic.Interface.ProductInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class VirtualProduct implements ProductInterface {

    private int id;
    private String model;
    private int id_category;
    private int id_brand;
    private int id_unit;
    private String state;
    private String tags;
    private String note;
    private String wharehouse_position;
    private double weight;
    private double max_order;
    private double min_order;
    private double profit;
    private double price;
    private int iva;

    private int currentlyPresentNumber;
    private String brandName;
    private String categoryName;
    private String unitName;


    private Database db;
    private ProductDAO dao;

    public VirtualProduct(Database db) {
        this.db = db;

        dao = new ProductDAO(this.db);
    }
//senza db

    public VirtualProduct() {
    }
//con db

    public VirtualProduct(int id, String model, int id_category, int id_brand, int id_unit, String state, String tags, String note,
            String wharehouse_position, double weight, double max_order, double min_order, double profit, double price, int iva,Database db) {
        this.id = id;
        this.model = model;
        this.id_category = id_category;
        this.id_brand = id_brand;
        this.id_unit = id_unit;
        this.state = state;
        this.tags = tags;
        this.note = note;
        this.wharehouse_position = wharehouse_position;
        this.weight = weight;
        this.max_order = max_order;
        this.min_order = min_order;
        this.profit = profit;
        this.price = price;
        this.iva=iva;
        this.db = db;
        dao = new ProductDAO(this.db);
    }

    public List<ProductInterface> getAll() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllProduct(this);
    }

    public ProductInterface getVirtualProduct(int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getProduct(this,id);
    }

    public int insert() throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException, SQLException {
        return dao.insertVirtualProduct(this);
    }

    public void update() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualProduct(this);
    }

    public void delete() throws NotFoundDBException {
        dao.deleteVirtualProduct(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getId_brand() {
        return id_brand;
    }

    public void setId_brand(int id_brand) {
        this.id_brand = id_brand;
    }

    public int getId_unit() {
        return id_unit;
    }

    public void setId_unit(int id_unit) {
        this.id_unit = id_unit;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWharehouse_position() {
        return wharehouse_position;
    }

    public void setWharehouse_position(String wharehouse_position) {
        this.wharehouse_position = wharehouse_position;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getMax_order() {
        return max_order;
    }

    public void setMax_order(double max_order) {
        this.max_order = max_order;
    }

    public double getMin_order() {
        return min_order;
    }

    public void setMin_order(double min_order) {
        this.min_order = min_order;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getCurrentlyPresentNumber() {
        return currentlyPresentNumber;
    }

    public void setCurrentlyPresentNumber(int currentlyPresentNumber) {
        this.currentlyPresentNumber = currentlyPresentNumber;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

}
