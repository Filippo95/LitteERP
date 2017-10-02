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
public interface DdtRowInterface {

    public int getId_ddt();

    public void setId_ddt(int id_ddt);

    public String getDescription();

    public void setDescription(String description);

    public int getId_product();

    public void setId_product(int id_product);

    public double getQuantity();

    public void setQuantity(double quantity);

    public double getUnit_price();

    public void setUnit_price(double unit_price);

    public double getPrice();

    public void setPrice(double price);
    
     public String getProductModel() ;

    public void setProductModel(String productModel) ;
}
