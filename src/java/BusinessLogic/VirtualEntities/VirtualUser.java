/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.UserDAO;
import BusinessLogic.Interface.UserInterface;
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
public class VirtualUser implements BusinessLogic.Interface.UserInterface {

    private int id;
    private String username;
    private String passwd;
    private String nome;
    private String cognome;
    private String email;
    private String tel;
    private String indirizzo;
    private String civico;
    private String cap;
    private String city;
    private String provincia;
    private Boolean admin;
    private Database db;

    private UserDAO dao;

    public VirtualUser(Database db) {
        this.db = db;
        dao = new UserDAO(this.db);
    }

    public VirtualUser() {
    }

    public VirtualUser(int id, String username, String passwd, String nome, String cognome, String email, String tel, String indirizzo, String civico, String cap, String city, String provincia, Boolean admin, Database db) {
        this.id=id;
        this.username = username;
        this.passwd = passwd;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.tel = tel;
        this.indirizzo = indirizzo;
        this.civico = civico;
        this.cap = cap;
        this.city = city;
        this.provincia = provincia;
        this.admin = admin;
        this.db = db;
        dao = new UserDAO(this.db);
    }

    public List<UserInterface> getAll() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllUser(this);
    }

    public UserInterface getVirtualUser(int id) throws ResultSetDBException, NotFoundDBException {
        return dao.getUser(this,id);
    }

    public UserInterface getVirtualUser(String username, String password) throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException {
        return dao.getUser(this,username, password);
    }

    public UserInterface getVirtualUserFromCookie(String uid, String password) throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException {
        return dao.getUserFromIDAndPSWD(this,uid, password);
    }

    public int insert() throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException, SQLException {
        return dao.insertVirtualUser(this);
    }

    public void update() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualUser(this);
    }

    public void delete() throws NotFoundDBException {
        dao.deleteVirtualUser(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
