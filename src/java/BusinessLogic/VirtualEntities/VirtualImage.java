/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.ImageDAO;
import BusinessLogic.Interface.ImageInterface;
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
public class VirtualImage implements ImageInterface{
    private int id;
    private int id_product;
    private String file_format;
    private byte[] content;

    private Database db;
    private ImageDAO dao;

    public VirtualImage(Database db) {
        this.db = db;
        dao = new ImageDAO(this.db);
    }

    public VirtualImage(int id,int id_product, String file_format, byte[] content, Database db) {
       this.id=id;
        this.content = content;
        this.file_format = file_format;
        this.id_product = id_product;
        this.db=db;
        dao = new ImageDAO(this.db);
    }

    public VirtualImage() {
    }

    public void insert() throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException, SQLException {
        dao.insertVirtualImage(this);
    }

    public void delete() throws NotFoundDBException {
        dao.deleteVirtualImage(this);
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

    public List<ImageInterface> getImages(int id) throws NotFoundDBException, SQLException {
        return dao.getVirtualImages(this,id);
    }

    public ImageInterface getImages(int id, int index) throws NotFoundDBException, SQLException {
        return dao.getVirtualImages(this,id).get(index);
    }

    public List<ImageInterface> getAll(int id) throws NotFoundDBException, SQLException {
        return dao.getVirtualImages(this,id);
         }

    public List<ImageInterface> getAll() throws NotFoundDBException, SQLException {
        return dao.getAllVirtualImages(this);
       }

    public Database getDb() {
        return db;
    }

    public void setDb(Database db) {
        this.db = db;
    }

}
