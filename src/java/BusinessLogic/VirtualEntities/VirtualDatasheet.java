/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.DatasheetDAO;
import BusinessLogic.Interface.DatasheetInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author filippo
 */
public class VirtualDatasheet implements DatasheetInterface {

    private int id_product;
    private String file_format;
    private int id;
    private byte[] content;
    private Database db;
    private DatasheetDAO dao;

    public VirtualDatasheet(Database db) {
        this.db = db;
        dao = new DatasheetDAO(this.db);
    }
    
    public VirtualDatasheet() {
    }
    
    public VirtualDatasheet(int i, int id, String content_type, byte[] content, Database db) {
        this.id = i;
        this.content = content;
        this.id_product=id;
        this.db = db;
        this.file_format = content_type;
        this.dao = new DatasheetDAO(db);

    }
 public void insert() throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException, SQLException {
        System.out.println("Passo al DAO");
        dao.insertVirtualDatasheet(this);
    }

    public void delete() throws NotFoundDBException {
        dao.deleteVirtualDatasheet(this);
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

    public String getFile_format() {
        return file_format;
    }

    public void setFile_format(String file_format) {
        this.file_format = file_format;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
  public List<DatasheetInterface> getDatasheets(int id) throws NotFoundDBException, SQLException {
        return dao.getDatasheets(this,id);
    }

    public DatasheetInterface getDatasheets(int id, int index) throws NotFoundDBException, SQLException {
        return dao.getDatasheets(this,id).get(index);
    }

    public List<DatasheetInterface> getAll(int id) throws NotFoundDBException, SQLException {
        return dao.getDatasheets(this,id);
         }

    public List<DatasheetInterface> getAll() throws NotFoundDBException, SQLException {
        return dao.getAllVirtualDatasheet(this);
       }
}
