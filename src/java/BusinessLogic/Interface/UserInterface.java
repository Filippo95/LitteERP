/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Interface;

/**
 *
 * @author filippo
 */
public interface UserInterface {
    public int getId();

    public void setId(int id) ;
    public String getUsername() ;

    public void setUsername(String username) ;

    public String getPasswd();

    public void setPasswd(String passwd);

    public String getNome();

    public void setNome(String nome);

    public String getCognome();

    public void setCognome(String cognome);

    public String getEmail();

    public void setEmail(String email);

    public String getTel() ;

    public void setTel(String tel) ;

    public String getIndirizzo() ;

    public void setIndirizzo(String indirizzo);

    public String getCivico();

    public void setCivico(String civico);

    public String getCap();

    public void setCap(String cap);

    public String getCity();

    public void setCity(String city);

    public String getProvincia();

    public void setProvincia(String provincia);

    public Boolean getAdmin();

    public void setAdmin(Boolean admin);
}
