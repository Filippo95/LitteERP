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
public interface InvoiceRowInterface {

    public int getId_invoice();

    public void setId_invoice(int id_invoice);

    public String getDescription();

    public void setDescription(String description);

    public int getId_product();

    public void setId_product(int id_product);

    public double getQuantity();

    public void setQuantity(double quantity);

    public double getSubtot();

    public void setSubtot(double subtot);

    public double getPrice();

    public void setPrice(double price);

    public String getProductModel();

    public void setProductModel(String productModel);

}
