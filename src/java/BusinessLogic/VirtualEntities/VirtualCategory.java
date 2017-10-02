/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.Interface.CategoryInterface;
import BusinessLogic.DAO.CategoryDAO;
import BusinessLogic.Interface.CategoryInterface;
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
public class VirtualCategory implements CategoryInterface {

    private int id;
    private int id_parent;
    private String name;
    private String description;
    private int id_lang;
    
    private CategoryTranslations trans;
    private List<CategoryTranslations> transList;
    
    private String parents;
    private Boolean hasChildren;
    private Boolean hasProducts;

    private Database db;
    private CategoryDAO dao;

    public VirtualCategory(Database db) {
        this.db = db;
        dao = new CategoryDAO(this.db);
    }

    public VirtualCategory() {
    }

    public VirtualCategory(int id, int id_parent, String name, String description, int id_lang, Database db) {
        this.db = db;
        this.id = id;
        this.id_parent = id_parent;
        this.name = name;
        this.description = description;
        this.id_lang = id_lang;
        dao = new CategoryDAO(this.db);
    }

    public VirtualCategory(int id, int id_parent, Database db) {
        this.db = db;
        this.id = id;
        this.id_parent = id_parent;
        dao = new CategoryDAO(this.db);
    }

    public VirtualCategory(int id, String name, String description, int id_lang, Database db) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.id_lang = id_lang;
        this.db = db;
        dao = new CategoryDAO(this.db);
    }

    public VirtualCategory(int id, int id_parent, String name, String description, int id_lang) {
        this.id = id;
        this.id_parent = id_parent;
        this.name = name;
        this.description = description;
        this.id_lang = id_lang;
    }

    public List<CategoryInterface> getAll() throws ResultSetDBException, NotFoundDBException, SQLException {
        List<CategoryInterface> list = dao.getAllCategory(this);
        return list;
    }
    
    public CategoryInterface getVirtualCategoryWithName(int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getCategoryComplete(this,id);
    }

    public int insertCategory() throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException, SQLException {
        return dao.insertVirtualCategory(this);
    }

    public void updateCategory() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualCategory(this);
    }

    public void insertTranslation() throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException, SQLException {
        dao.insertTranslation(this);
    }

    public void updateTranslation() throws NotFoundDBException, ResultSetDBException, SQLException {
        dao.updateTransltion(this);
    }

    public void delete() throws NotFoundDBException, ResultSetDBException, SQLException {
        dao.deleteVirtualCategory(this);
    }
   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_parent() {
        return id_parent;
    }

    public void setId_parent(int id_parent) {
        this.id_parent = id_parent;
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

    public int getId_lang() {
        return id_lang;
    }

    public void setId_lang(int id_lang) {
        this.id_lang = id_lang;
    }

    public List<CategoryTranslations> getTransList() {
        return transList;
    }

    public void setTransList(List<CategoryTranslations> transList) {
        this.transList = transList;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public Boolean getHasProducts() {
        return hasProducts;
    }

    public void setHasProducts(Boolean hasProducts) {
        this.hasProducts = hasProducts;
    }

}
