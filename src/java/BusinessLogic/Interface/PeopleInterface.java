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
public interface PeopleInterface {

    public int getId();

    public void setId(int id);

    public String getName();

    public void setName(String name);

    public String getRefer_to();

    public void setRefer_to(String refer_to);

    public String getEmail();

    public void setEmail(String email);

    public String getTelephone();

    public void setTelephone(String telephone);

    public String getCod_fisc();

    public void setCod_fisc(String cod_fisc);

    public String getPiva();

    public void setPiva(String piva);

    public String getAddress();

    public void setAddress(String address);

    public String getCivic();

    public void setCivic(String civic);

    public String getCap();

    public void setCap(String cap);

    public String getCity();

    public void setCity(String city);

    public String getProvince();

    public void setProvince(String province);

    public Boolean getProvider();

    public void setProvider(Boolean provider);

    public Boolean getCustomer();

    public void setCustomer(Boolean customer);

}
