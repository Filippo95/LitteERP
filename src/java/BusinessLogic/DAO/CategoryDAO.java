/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.CategoryInterface;
import BusinessLogic.VirtualEntities.CategoryTranslations;
import BusinessLogic.VirtualEntities.VirtualCategory;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class CategoryDAO {

    private Database db;
    String parents;

    public CategoryDAO(Database db) {
        this.db = db;
    }

    public String getCategoryParents(int id) throws SQLException, NotFoundDBException, ResultSetDBException {
        CategoryInterface vc = new VirtualCategory();
        vc = this.getCategory(vc, id);
        if (vc.getId() == 0) {
            return "Root";
        } else {
            CategoryDAO dao = new CategoryDAO(db);
            return dao.getCategoryParents(vc.getId_parent()) + ">" + vc.getTransList().get(0).getName();
        }
    }

    public List<CategoryInterface> getAllCategory(CategoryInterface vc) throws SQLException, NotFoundDBException, ResultSetDBException {
        /*Ottengo tutte le categorie salvate nel database*/
        String sql = "SELECT * FROM category WHERE active=1 Order By id_parent,id";
        List<CategoryInterface> lista = new ArrayList();
        ResultSet rs = this.db.select(sql);
        while (rs.next()) {
            vc=new VirtualCategory();
            vc.setId(rs.getInt("id"));
            vc.setId_parent(rs.getInt("id_parent"));
            lista.add(vc);
        }
        //Per ogni categoria estratta estraggo anche le traduzioni collegate
        for (int i = 0; i < lista.size(); i++) {
            sql = "SELECT * FROM category_transition WHERE id_category=? AND active=1";
            String[] pars = new String[1];
            pars[0] = "" + lista.get(i).getId();
            List<CategoryTranslations> transList = new ArrayList();
            rs = this.db.PreparedStatement(sql, pars);
            while (rs.next()) {
                CategoryTranslations trans = new CategoryTranslations(lista.get(i).getId(), rs.getString("name"), rs.getString("description"), rs.getInt("id_lang"));
                transList.add(trans);
            }
            /*Per ogni categoria setto la lista di traduzioni estratte*/
            lista.get(i).setTransList(transList);
            lista.get(i).setParents(getCategoryParents(lista.get(i).getId()));
            lista.get(i).setHasChildren(this.hasChildren(lista.get(i).getId()));
            lista.get(i).setHasProducts(this.hasProducts(lista.get(i).getId()));
        }
        return lista;
    }

    public CategoryInterface getCategoryComplete(CategoryInterface vc,int id) throws NotFoundDBException, ResultSetDBException, SQLException {
        /*Vado su Database ed estraggo la categoria e categoria madre*/
        String sql = "SELECT * FROM category WHERE id=? AND active=1";
        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            vc = new VirtualCategory();
            vc.setId(rs.getInt("id"));
            vc.setId_parent(rs.getInt("id_parent"));
        }
        /*Ottenuta la categoria con id dato, estraggo da database le traduzioni*/
        sql = "SELECT * FROM category_transition WHERE id_category=? AND active=1";
        pars[0] = "" + id;
        List<CategoryTranslations> transList = new ArrayList();
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            CategoryTranslations vb = new CategoryTranslations(rs.getInt("id_category"), rs.getString("name"), rs.getString("description"), rs.getInt("id_lang"));
            transList.add(vb);
        }
        /*Setto per la categoria estratta la lista di traduzioni collegate*/
        vc.setTransList(transList);
        vc.setParents(getCategoryParents(vc.getId()));
        vc.setHasChildren(this.hasChildren(id));
        vc.setHasProducts(this.hasProducts(id));
        return vc;
    }

    public CategoryInterface getCategory(CategoryInterface vc,int id) throws NotFoundDBException, ResultSetDBException, SQLException {
        /*Vado su Database ed estraggo la categoria e categoria madre*/
        String sql = "SELECT * FROM category WHERE id=? AND active=1";
        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            vc = new VirtualCategory();
            vc.setId(rs.getInt("id"));
            vc.setId_parent(rs.getInt("id_parent"));
        }
        /*Ottenuta la categoria con id dato, estraggo da database le traduzioni*/
        sql = "SELECT * FROM category_transition WHERE id_category=? AND active=1";
        pars = new String[1];
        pars[0] = "" + id;
        List<CategoryTranslations> transList = new ArrayList();
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            CategoryTranslations vb = new CategoryTranslations(id, rs.getString("name"), rs.getString("description"), rs.getInt("id_lang"));
            transList.add(vb);
        }
        /*Setto per la categoria estratta la lista di traduzioni collegate*/
        vc.setTransList(transList);
        return vc;
    }

    public int insertVirtualCategory(CategoryInterface vc) throws NotFoundDBException, SQLException {
        /*Vado solo sulla tabella category a salvare la categoria madre*/
        String sql = "INSERT INTO `category`( `id_parent`) VALUES (?) ";
        String[] pars = new String[1];
        pars[0] = "" + vc.getId_parent();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
        /*Prendo l'id della categoria inserita che mi servirà successivamente per inserire correttamente le traduzioni collegate*/
        sql = "SELECT LAST_INSERT_ID();";
        ResultSet rs = this.db.select(sql);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("LAST_INSERT_ID()");
        }
        return id;
    }

    public void updateVirtualCategory(CategoryInterface vc) throws NotFoundDBException {
        /*Modifica della sola tabella category*/
        String sql = "UPDATE `category` SET `id_parent`=? WHERE `id`=?";
        String[] pars = new String[2];
        pars[0] = "" + vc.getId_parent();
        pars[1] = "" + vc.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            System.out.println("Aggiornate 0 righe");
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

    public void deleteVirtualCategory(CategoryInterface vc) throws NotFoundDBException {
        /*Prima elimino la riga sulla tabella category settando active=0*/
        String sql = "UPDATE `category` SET `active`=0 WHERE `id`=? ";
        String[] pars = new String[1];
        pars[0] = "" + vc.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
        /*Poi elimino le traduzioni collegate*/
        ResultSet rs;
        sql = "UPDATE `category_transition` SET `active`=0 WHERE `id_category`=?";
        pars = new String[1];
        pars[0] = "" + vc.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }

    public void insertTranslation(CategoryInterface vc) throws NotFoundDBException {
        /*Vado ad inserire in category_translation la traduzione inserita dall'utente*/
        String sql = "INSERT INTO `category_transition`( `id_category`,`name`, `description`, `id_lang`) VALUES (?,?,?,?)";
        String[] pars = new String[4];
        pars[0] = "" + vc.getId();
        pars[1] = vc.getName();
        pars[2] = vc.getDescription();
        pars[3] = "" + vc.getId_lang();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void updateTransltion(CategoryInterface vc) throws NotFoundDBException, SQLException {
        //Recupero l'id della traduzione da aggiornare
        String sql = "SELECT DISTINCT id FROM category_transition WHERE `id_category`=? AND `id_lang`=?";
        ResultSet rs;
        int id = 0;
        String[] pars = new String[2];
        pars[0] = "" + vc.getId();
        pars[1] = "" + vc.getId_lang();
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            id = rs.getInt("id");
        }
        //Aggiorno la traduzione corretta
        sql = "UPDATE `category_transition` SET `id_category`=?,`name`=?,`description`=?,`id_lang`=? WHERE `id`=?";
        pars = new String[5];
        pars[0] = "" + vc.getId();
        pars[1] = vc.getName();
        pars[2] = vc.getDescription();
        pars[3] = "" + vc.getId_lang();
        pars[4] = "" + id;
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

    private Boolean hasChildren(int id_category) throws NotFoundDBException, SQLException {
        String sql = "SELECT COUNT(*) as childrens FROM category WHERE id_parent=? AND active=1";
        String[] pars = new String[1];
        pars[0] = "" + id_category;
        int count = 0;
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            count = rs.getInt("childrens");
        }
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean hasProducts(int id_category) throws NotFoundDBException, SQLException {
        String sql = "SELECT COUNT(*) as products FROM product WHERE id_category=? AND active=1";
        String[] pars = new String[1];
        pars[0] = "" + id_category;
        int count = 0;
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            count = rs.getInt("products");
        }
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}
