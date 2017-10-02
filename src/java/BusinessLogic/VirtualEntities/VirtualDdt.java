/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.DdtDAO;
import BusinessLogic.Interface.DdtInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class VirtualDdt implements DdtInterface {

    private int id;
    private GregorianCalendar ddate;
    private int id_people;
    private int id_people_vett;
    private int id_drain;
    private String destination;
    private String cause;
    private String aspect;
    private String ncoll;
    
    private String clientName;
    private String vettName;
    
    private Database db;
    private DdtDAO dao;

    public VirtualDdt(Database db) {
        this.db = db;
        dao = new DdtDAO(this.db);
    }

    public VirtualDdt() {
    }

    public VirtualDdt(int id, int id_people, int id_people_vett, int id_drain, String destination, String cause, String aspect, String ncoll, Database db) {
        this.id = id;
        this.id_people = id_people;
        this.id_people_vett = id_people_vett;
        this.id_drain=id_drain;
        this.destination = destination;
        this.cause = cause;
        this.aspect = aspect;
        this.ncoll = ncoll;
        this.db = db;
        dao = new DdtDAO(this.db);
    }

    public List<DdtInterface> getAll() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllDdt(this);
    }

    public DdtInterface getVirtualDdt(int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getDdt(this,id);
    }
    
    public DdtInterface getVirtualDdtByDrainId(int id_drain) throws NotFoundDBException, SQLException {
        return dao.getDdtByDrainId(this,id_drain);
    }

    public int insert() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.insertVirtualDdt(this);
    }
    
    public int insertFromQuote(int id_quote) throws NotFoundDBException, SQLException {
        return dao.insertVirtualDdtFromQuote(id_quote,this);
    }

    public void update() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualDdt(this);
    }
    
    public Boolean existInvoice(int id_drain) throws NotFoundDBException, SQLException
    {
        return dao.existInvoice(id_drain);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GregorianCalendar getDdate() {
        return ddate;
    }

    public void setDdate(GregorianCalendar ddate) {
        this.ddate = ddate;
    }

    public int getId_people() {
        return id_people;
    }

    public void setId_people(int id_people) {
        this.id_people = id_people;
    }

    public int getId_people_vett() {
        return id_people_vett;
    }

    public void setId_people_vett(int id_people_vett) {
        this.id_people_vett = id_people_vett;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public String getNcoll() {
        return ncoll;
    }

    public void setNcoll(String ncoll) {
        this.ncoll = ncoll;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getVettName() {
        return vettName;
    }

    public void setVettName(String vettName) {
        this.vettName = vettName;
    }

    public int getId_drain() {
        return id_drain;
    }

    public void setId_drain(int id_drain) {
        this.id_drain = id_drain;
    }

}
