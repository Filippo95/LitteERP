/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Interface;

import BusinessLogic.VirtualEntities.CategoryTranslations;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public interface CategoryInterface {

    public int getId();

    public void setId(int id);

    public int getId_parent();

    public void setId_parent(int id_parent);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public int getId_lang();

    public void setId_lang(int id_lang);

    public List<CategoryTranslations> getTransList();

    public void setTransList(List<CategoryTranslations> transList);

    public String getParents();

    public void setParents(String parents);

    public Boolean getHasChildren();

    public void setHasChildren(Boolean hasChildren);

    public Boolean getHasProducts();

    public void setHasProducts(Boolean hasProducts);
}
