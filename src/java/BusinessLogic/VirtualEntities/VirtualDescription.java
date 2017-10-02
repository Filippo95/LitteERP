/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.DescriptionDAO;
import BusinessLogic.Interface.DescriptionInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.util.List;

/**
 *
 * @author filippo
 */
public class VirtualDescription implements DescriptionInterface {

    private int id_product;
    private int id_lang;
    private String description;
    private DescriptionDAO dao;
    private Database db;

    public VirtualDescription() {
    }

    public VirtualDescription(int id_product, int id_lang, String description, Database db) {
        this.id_product = id_product;
        this.description = description;
        this.id_lang = id_lang;
        this.db = db;
        this.dao = new DescriptionDAO(db);
    }

    public VirtualDescription(Database db) {
        this.db = db;
        this.dao = new DescriptionDAO(db);
    }

    public List<DescriptionInterface> getDescriptions(int id) throws NotFoundDBException, ResultSetDBException {
        return dao.getDescriptionsByProductId(this,id);
    }
    
    public void insert() throws ResultSetDBException, NotFoundDBException {
        dao.insertVirtualDescription(this);
    }

    public void update() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualDescription(this);
    }
    public void delete() throws NotFoundDBException
    {
        dao.deleteVirtualDescription(this);
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_lang() {
        return id_lang;
    }

    public void setId_lang(int id_lang) {
        this.id_lang = id_lang;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
