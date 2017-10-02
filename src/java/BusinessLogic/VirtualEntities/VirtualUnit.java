/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.UnitDAO;
import BusinessLogic.Interface.UnitInterface;
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
public class VirtualUnit implements UnitInterface {

    private int id;
    private String name;
    private String description;
    private Boolean hasProducts;

    private Database db;
    private UnitDAO dao;

    public VirtualUnit(Database db) {
        this.db = db;
        dao = new UnitDAO(this.db);
    }

    public VirtualUnit() {
    }

    public VirtualUnit(int id, String name, String description, Database db) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.db = db;
        dao = new UnitDAO(this.db);
    }

    public List<UnitInterface> getAll() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllUnit(this);
    }

    public UnitInterface getVirtualUnit(int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getUnit(this,id);
    }

    public void insert() throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException {
        dao.insertVirtualUnit(this);
    }

    public void update() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualUnit(this);
    }

    public void delete() throws NotFoundDBException {
        dao.deleteVirtualUnit(this);
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
