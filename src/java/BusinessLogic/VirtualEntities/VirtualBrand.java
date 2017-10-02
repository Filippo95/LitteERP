/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.BrandDAO;
import BusinessLogic.Interface.BrandInterface;
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
public class VirtualBrand implements BrandInterface {
    private int id;
    private String name;
    private String description;
    
    private Boolean hasProducts;
    
    private Database db;
    private BrandDAO dao;
    
    public VirtualBrand(Database db)
    {
        this.db=db;
        dao = new BrandDAO(this.db);
    }
    
    public VirtualBrand(){
    }
    
    public VirtualBrand(int id,String name,String description,Database db)
    {
        this.id=id;
        this.name=name;
        this.description=description;
        this.db=db;
        dao = new BrandDAO(this.db);
    }
    
    public List<BrandInterface> getAll() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllBrand(this);
    }

    public BrandInterface getVirtualBrand(int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getBrand(this,id);
    }
    
    public void insert() throws ResultSetDBException, NotFoundDBException {
        dao.insertVirtualBrand(this);
    }

    public void update() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualBrand(this);
    }
    public void delete() throws NotFoundDBException
    {
        dao.deleteVirtualBrand(this);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasProducts() {
        return hasProducts;
    }

    public void setHasProducts(Boolean hasProducts) {
        this.hasProducts = hasProducts;
    }
    
}
