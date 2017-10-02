/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Interface;

import BusinessLogic.VirtualEntities.VirtualDescription;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public interface ProductInterface {

    public int getId();

    public void setId(int id);

    public String getModel();

    public void setModel(String model);

    public int getId_category();

    public void setId_category(int id_category);

    public int getId_brand();

    public void setId_brand(int id_brand);

    public int getId_unit();

    public void setId_unit(int id_unit);

    public String getState();

    public void setState(String state);

    public String getTags();

    public void setTags(String tags);

    public String getNote();

    public void setNote(String note);

    public String getWharehouse_position();

    public void setWharehouse_position(String wharehouse_position);

    public double getWeight();

    public void setWeight(double weight);

    public double getMax_order();

    public void setMax_order(double max_order);

    public double getMin_order();

    public void setMin_order(double min_order);

    public double getProfit();

    public void setProfit(double profit);

    public double getPrice();

    public void setPrice(double price);

    public int getIva();

    public void setIva(int iva);
    
    public String getBrandName();

    public void setBrandName(String brandName);

    public String getCategoryName() ;

    public void setCategoryName(String categoryName);

    public String getUnitName() ;

    public void setUnitName(String unitName) ;

    public int getCurrentlyPresentNumber();

    public void setCurrentlyPresentNumber(int currentlyPresentNumber);
}
