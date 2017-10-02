package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.PeopleDAO;
import BusinessLogic.Interface.PeopleInterface;
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
public class VirtualPeople implements PeopleInterface {

    private int id;
    private String name;
    private String refer_to;
    private String email;
    private String telephone;
    private String cod_fisc;
    private String piva;
    private String address;
    private String civic;
    private String cap;
    private String city;
    private String province;
    private Boolean provider;
    private Boolean customer;

    private Database db;
    private PeopleDAO dao;

    public VirtualPeople(Database db) {
        this.db = db;
        dao = new PeopleDAO(this.db);
    }

    public VirtualPeople() {
    }

    public VirtualPeople(int id, String name, String refer_to, String email, String telephone, String cod_fisc, String piva, String address, String civic, String cap, String city, String province, Boolean provider, Boolean customer, Database db) {
        this.id = id;
        this.name = name;
        this.refer_to = refer_to;
        this.email = email;
        this.telephone = telephone;
        this.cod_fisc = cod_fisc;
        this.piva = piva;
        this.address = address;
        this.civic = civic;
        this.cap = cap;
        this.city = city;
        this.province = province;
        this.provider = provider;
        this.customer = customer;
        this.db = db;
        dao = new PeopleDAO(this.db);
    }

    public List<PeopleInterface> getAll() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getAllPeople(this);
    }

    public List<PeopleInterface> getAllProviders() throws NotFoundDBException, SQLException {
        return dao.getAllProviders(this);
    }

    public PeopleInterface getVirtualPeople(int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getPeople(this,id);
    }

    public int insert() throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException, SQLException {
        return dao.insertVirtualPeople(this);
    }

    public void update() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualPeople(this);
    }

    public void delete() throws NotFoundDBException {
        dao.deleteVirtualPeople(this);
    }

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

    public String getRefer_to() {
        return refer_to;
    }

    public void setRefer_to(String refer_to) {
        this.refer_to = refer_to;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCod_fisc() {
        return cod_fisc;
    }

    public void setCod_fisc(String cod_fisc) {
        this.cod_fisc = cod_fisc;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCivic() {
        return civic;
    }

    public void setCivic(String civic) {
        this.civic = civic;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Boolean getProvider() {
        return provider;
    }

    public void setProvider(Boolean provider) {
        this.provider = provider;
    }

    public Boolean getCustomer() {
        return customer;
    }

    public void setCustomer(Boolean customer) {
        this.customer = customer;
    }

}
