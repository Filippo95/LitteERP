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
public interface DrainRowInterface {

    public int getId_drain();

    public void setId_drain(int id_drain);

    public double getQuantity();

    public void setQuantity(double quantity);

    public String getProductModel();

    public void setProductModel(String productModel);

    public int getId_product();

    public void setId_product(int id_product);

    public double getPrice();

    public void setPrice(double price);

    public void setProdloc(String prodloc);

    public String getProdloc();
}
