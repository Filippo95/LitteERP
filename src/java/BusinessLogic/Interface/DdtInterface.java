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
public interface DdtInterface {

    public int getId();

    public void setId(int id);

    public GregorianCalendar getDdate();

    public void setDdate(GregorianCalendar ddate);

    public int getId_people();

    public void setId_people(int id_people);

    public int getId_people_vett();

    public void setId_people_vett(int id_people_vett);

    public String getDestination();

    public void setDestination(String destination);

    public String getCause();

    public void setCause(String cause);

    public String getAspect();

    public void setAspect(String aspect);

    public String getNcoll();

    public void setNcoll(String ncoll);
    public int getId_drain();
    public void setId_drain(int id_drain);
    public String getClientName() ;

    public void setClientName(String clientName);

    public String getVettName() ;

    public void setVettName(String vettName);
}
