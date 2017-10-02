/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Interface;

import java.util.GregorianCalendar;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public interface QuoteInterface {

    public int getId();

    public void setId(int id);

    public int getId_people();

    public void setId_people(int id_people);

    public String getDescription();

    public void setDescription(String description);

    public String getExpiring_date();

    public void setExpiring_date(String expiring_date);

    public Boolean getApproved();

    public void setApproved(Boolean approved);

    public void setClientName(String clientName);

    public String getClientName();
}
