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
public interface UnitInterface {

    public int getId();

    public void setId(int id);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public Boolean getHasProducts();

    public void setHasProducts(Boolean hasProducts);
}
