/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Interface;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public interface DataInterface {

    public String getRag_soc();

    public void setRag_soc(String rag_soc);

    public String getResponsabile();

    public void setResponsabile(String responsabile);

    public String getDomain();

    public void setDomain(String domain);

    public String getPiva();

    public void setPiva(String piva);

    public String getCodfisc();

    public void setCodfisc(String codfisc);

    public String getIndirizzo();

    public void setIndirizzo(String indirizzo);

    public String getCivico();

    public void setCivico(String civico);

    public String getCap();

    public void setCap(String cap);

    public String getCity();

    public void setCity(String city);

    public String getProvincia();

    public void setProvincia(String provincia);

    public String getMain_mail();

    public void setMain_mail(String main_mail);

    public String getContact_from_mail();

    public void setContact_from_mail(String contact_from_mail);

    public String getSysadmin_mail();

    public void setSysadmin_mail(String sysadmin_mail);

    public String getSmtp_srv();

    public void setSmtp_srv(String smtp_srv);

    public String getPop3_srv();

    public void setPop3_srv(String pop3_srv);

    public byte[] getLogo();

    public void setLogo(byte[] logo);

}
