/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.LanguageDAO;
import BusinessLogic.Interface.LanguageInterface;
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
public class VirtualLanguage implements LanguageInterface{

    private int id;
    private String name;

    private LanguageDAO dao;
    private Database db;

    public VirtualLanguage(Database db) {
        this.db = db;
        dao = new LanguageDAO(db);
    }

    public VirtualLanguage(int id, String name, Database db) {
        this.id = id;
        this.name = name;
        this.db = db;
        dao = new LanguageDAO(db);
    }

    public VirtualLanguage() {
    }
    
    
    public List<LanguageInterface> getAll() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllLanguages(this);
    }

    public LanguageInterface getVirtualLanguage(int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getLanguage(this,id);
    }
    
    public void insert() throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException {
        dao.insertVirtualLanguage(this);
    }

    public void update() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualLanguage(this);
    }
    public void delete() throws NotFoundDBException
    {
        dao.deleteVirtualLanguage(this);
    }
    
//getters and setters1
    
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

}
