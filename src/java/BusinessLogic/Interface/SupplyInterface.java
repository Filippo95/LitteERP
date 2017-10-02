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
public interface SupplyInterface {

    public int getId();

    public void setId(int id);

    public int getId_product();

    public void setId_product(int id_product);

    public int getId_provider();

    public void setId_provider(int id_provider);

    public double getQuantity();

    public void setQuantity(double quantity);

    public double getProvider_unit_price();

    public void setProvider_unit_price(double provider_unit_price);

    public GregorianCalendar getLast_mod();

    public void setLast_mod(GregorianCalendar last_mod);

    public String getProductName();

    public void setProductName(String productName);

    public String getProviderName();

    public void setProviderName(String providerName);

}
