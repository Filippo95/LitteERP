package BusinessLogic.VirtualEntities;

import BusinessLogic.DAO.DataDAO;
import BusinessLogic.Interface.DataInterface;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.SQLException;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class VirtualData implements DataInterface {

    private String rag_soc;
    private String responsabile;
    private String domain;
    private String piva;
    private String codfisc;
    private String indirizzo;
    private String civico;
    private String cap;
    private String city;
    private String provincia;
    private String main_mail;
    private String contact_from_mail;
    private String sysadmin_mail;
    private String smtp_srv;
    private String pop3_srv;
    private byte[] logo;

    private Database db;
    private DataDAO dao;

    public VirtualData(Database db) {
        this.db = db;
        dao = new DataDAO(this.db);
    }

    public VirtualData(String rag_soc, String responsabile, String domain, String piva, String codfisc, String indirizzo, String civico, String cap, String city, String provincia, String main_mail, String contact_from_mail, String sysadmin_mail, String smtp_srv, String pop3_srv, byte[] logo, Database db) {
        this.rag_soc = rag_soc;
        this.responsabile = responsabile;
        this.domain = domain;
        this.piva = piva;
        this.codfisc = codfisc;
        this.indirizzo = indirizzo;
        this.civico = civico;
        this.cap = cap;
        this.city = city;
        this.provincia = provincia;
        this.main_mail = main_mail;
        this.contact_from_mail = contact_from_mail;
        this.sysadmin_mail = sysadmin_mail;
        this.smtp_srv = smtp_srv;
        this.pop3_srv = pop3_srv;
        this.logo = logo;
        this.db = db;
        dao = new DataDAO(this.db);
    }

    public DataInterface getData() throws ResultSetDBException, NotFoundDBException, SQLException {
        return dao.getData(this);
    }

    /*public void insert() throws ResultSetDBException, NotFoundDBException, NoSuchAlgorithmException {
        dao.insertVirtualData(this);
    }*/
    public void update() throws NotFoundDBException, ResultSetDBException {
        dao.updateVirtualData(this);
    }

    /*public void delete() throws NotFoundDBException {
        dao.deleteVirtualData(this);
    }*/
    public String getRag_soc() {
        return rag_soc;
    }

    public void setRag_soc(String rag_soc) {
        this.rag_soc = rag_soc;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getCodfisc() {
        return codfisc;
    }

    public void setCodfisc(String codfisc) {
        this.codfisc = codfisc;
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

    public String getMain_mail() {
        return main_mail;
    }

    public void setMain_mail(String main_mail) {
        this.main_mail = main_mail;
    }

    public String getContact_from_mail() {
        return contact_from_mail;
    }

    public void setContact_from_mail(String contact_from_mail) {
        this.contact_from_mail = contact_from_mail;
    }

    public String getSysadmin_mail() {
        return sysadmin_mail;
    }

    public void setSysadmin_mail(String sysadmin_mail) {
        this.sysadmin_mail = sysadmin_mail;
    }

    public String getSmtp_srv() {
        return smtp_srv;
    }

    public void setSmtp_srv(String smtp_srv) {
        this.smtp_srv = smtp_srv;
    }

    public String getPop3_srv() {
        return pop3_srv;
    }

    public void setPop3_srv(String pop3_srv) {
        this.pop3_srv = pop3_srv;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

}
