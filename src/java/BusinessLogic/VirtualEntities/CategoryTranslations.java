/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.VirtualEntities;

import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class CategoryTranslations {

    private int id;
    private int id_category;
    private String name;
    private String description;
    private int lanId;
    private List<CategoryTranslations> list;

    private Database db;

    public CategoryTranslations(int id_category, String name, String description, int lanId) {
        this.id_category = id_category;
        this.name = name;
        this.description = description;
        this.lanId = lanId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLanId() {
        return lanId;
    }

    public void setLanId(int lanId) {
        this.lanId = lanId;
    }

}
