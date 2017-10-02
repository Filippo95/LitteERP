/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.SupplyDAO;
import BusinessLogic.Interface.SupplyInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class VirtualSupply implements SupplyInterface {

    private int id;
    private int id_product;
    private int id_provider;
    private double quantity;
    private double provider_unit_price;
    private GregorianCalendar last_mod;
    
    private String productName;
    private String providerName;

    private Database db;
    private SupplyDAO dao;

    public VirtualSupply(Database db) {
        this.db = db;
        dao = new SupplyDAO(this.db);
    }

    public VirtualSupply() {
    }

    public VirtualSupply(int id, int id_product, int id_provider, double quantity, double provider_unit_price, GregorianCalendar last_mod, Database db) {
        this.id = id;
        this.id_product = id_product;
        this.id_provider = id_provider;
        this.quantity = quantity;
        this.provider_unit_price = provider_unit_price;
        this.last_mod = last_mod;
        this.db = db;
        dao = new SupplyDAO(this.db);
    }

    public List<SupplyInterface> getAll() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllSupply(this);
    }

    public SupplyInterface getVirtualSupply(int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getSupply(this,id);
    }

    public List<SupplyInterface> getVirtualSupplyByProductId(int id_product) throws ResultSetDBException, NotFoundDBException {
        return dao.getSupplyByProductId(this,id);
    }

    public void insert() throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException {
        dao.insertVirtualSupply(this);
    }

    public void update() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualSupply(this);
    }

    public void delete() throws NotFoundDBException {
        dao.deleteVirtualSupply(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_provider() {
        return id_provider;
    }

    public void setId_provider(int id_provider) {
        this.id_provider = id_provider;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getProvider_unit_price() {
        return provider_unit_price;
    }

    public void setProvider_unit_price(double provider_unit_price) {
        this.provider_unit_price = provider_unit_price;
    }

    public GregorianCalendar getLast_mod() {
        return last_mod;
    }

    public void setLast_mod(GregorianCalendar last_mod) {
        this.last_mod = last_mod;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

}
