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
public interface DescriptionInterface {
    public int getId_product();

    public void setId_product(int id_product);

    public int getId_lang();

    public void setId_lang(int id_lang);

    public String getDescription() ;

    public void setDescription(String description);
}
