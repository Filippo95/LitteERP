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
public interface InvoiceInterface {

    public int getId();

    public void setId(int id);

    public GregorianCalendar getIdate();

    public void setIdate(GregorianCalendar idate);

    public int getId_people();

    public void setId_people(int id_people);

    public int getExpiring_days();

    public void setExpiring_days(int expiring_days);

    public Boolean getPayed();

    public void setPayed(Boolean payed);

    public String getClientName();

    public void setClientName(String clientName);

    public int getId_drain();

    public void setId_drain(int id_drain);
}
