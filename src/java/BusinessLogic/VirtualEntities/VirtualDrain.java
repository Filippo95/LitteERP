/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.DrainDAO;
import BusinessLogic.Interface.DrainInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class VirtualDrain implements DrainInterface {

    private String ddate;
    private int id;
    private Database db;
    private DrainDAO dao;

    public VirtualDrain(Database db) {
        this.db = db;
        dao = new DrainDAO(this.db);
    }

    public VirtualDrain() {
    }

    public List<DrainInterface> getAll() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllDrain(this);
    }

    public DrainInterface getVirtualDrain(int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getDrain(this,id);
    }

    public int insert() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.insertVirtualDrain(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

}
