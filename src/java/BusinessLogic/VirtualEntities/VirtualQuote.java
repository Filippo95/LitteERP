/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.QuoteDAO;
import BusinessLogic.Interface.QuoteInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class VirtualQuote implements QuoteInterface {

    private int id;
    private int id_people;
    private String description;
    private String expiring_date;
    private Boolean approved;
    
    private String clientName;
    
    private Database db;
    private QuoteDAO dao;

    public VirtualQuote(Database db) {
        this.db = db;
        dao = new QuoteDAO(this.db);
    }

    public VirtualQuote() {
    }

    public VirtualQuote(int id, int id_people,  String description, String expiring_date, Boolean approved, Database db) {
        this.id = id;
        this.id_people = id_people;
        this.description=description;
        this.expiring_date = expiring_date;
        this.approved = approved;
        this.db = db;
        dao = new QuoteDAO(this.db);
    }

    public List<QuoteInterface> getAll() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllQuote(this);
    }

    public QuoteInterface getVirtualQuote(int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getQuote(this,id);
    }

    public int insert() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.insertVirtualQuote(this);
    }

    public void update() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualQuote(this);
    }

    public void approve() throws NotFoundDBException {
        dao.approve(this);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_people() {
        return id_people;
    }

    public void setId_people(int id_people) {
        this.id_people = id_people;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpiring_date() {
        return expiring_date;
    }

    public void setExpiring_date(String expiring_date) {
        this.expiring_date = expiring_date;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }
    
}
