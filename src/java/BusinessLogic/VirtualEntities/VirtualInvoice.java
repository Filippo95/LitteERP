/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.InvoiceDAO;
import BusinessLogic.Interface.InvoiceInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class VirtualInvoice implements InvoiceInterface {

    private int id;
    private GregorianCalendar idate;
    private int expiring_days;
    private Boolean payed;
    private int id_people;
    private int id_drain;
    
    private String clientName;
    
    private Database db;
    private InvoiceDAO dao;

    public VirtualInvoice(Database db) {
        this.db = db;
        dao = new InvoiceDAO(this.db);
    }

    public VirtualInvoice() {
    }

    public VirtualInvoice(int id, int expiring_days, Boolean payed, int id_people, int id_drain, Database db) {
        this.id = id;
        this.id_people = id_people;
        this.id_drain=id_drain;
        this.expiring_days = expiring_days;
        this.payed = payed;
        this.db = db;
        dao = new InvoiceDAO(this.db);
    }

    public List<InvoiceInterface> getAll() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllInvoice(this);
    }

    public InvoiceInterface getVirtualInvoice(int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getInvoice(this,id);
    }
    
    public InvoiceInterface getVirtualInvoiceByDrainId(int id_drain) throws NotFoundDBException, SQLException {
        return dao.getInvoiceByDrainId(this,id_drain);
    }
    
    public int getInvoiceNumber(GregorianCalendar date) throws NotFoundDBException, ResultSetDBException, SQLException
    {
        return dao.getInvoiceNumber(date);
    }

    public int insert() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.insertVirtualInvoice(this);
    }
    
    public int insertFromDrainId(int id_drain) throws NotFoundDBException, SQLException {
        return dao.insertVirtualInvoiceFromDrainId(id_drain);
    }
    
    public int insertFromQuote(int id_quote) throws NotFoundDBException, SQLException {
        return dao.insertVirtualInvoiceFromQuote(id_quote,this);
    }

    public void update() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualInvoice(this);
    }

    public Boolean existDdt(int id_drain) throws NotFoundDBException, SQLException{
        return dao.existDdt(id_drain);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GregorianCalendar getIdate() {
        return idate;
    }

    public void setIdate(GregorianCalendar idate) {
        this.idate = idate;
    }

    public int getId_people() {
        return id_people;
    }

    public void setId_people(int id_people) {
        this.id_people = id_people;
    }

    public int getExpiring_days() {
        return expiring_days;
    }

    public void setExpiring_days(int expiring_days) {
        this.expiring_days = expiring_days;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getId_drain() {
        return id_drain;
    }

    public void setId_drain(int id_drain) {
        this.id_drain = id_drain;
    }

}
