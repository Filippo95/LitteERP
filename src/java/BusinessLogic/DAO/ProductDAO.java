package BusinessLogic.DAO;

import BusinessLogic.Interface.CategoryInterface;
import BusinessLogic.Interface.ProductInterface;
import BusinessLogic.VirtualEntities.VirtualCategory;
import BusinessLogic.VirtualEntities.VirtualDescription;
import BusinessLogic.VirtualEntities.VirtualProduct;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    Database db;
    VirtualDescription vdescr;

    public ProductDAO(Database db) {
        this.db = db;
        vdescr = new VirtualDescription(db);
    }

    public List<ProductInterface> getAllProduct(ProductInterface vp) throws ResultSetDBException, NotFoundDBException, SQLException {
        String sql = "SELECT * FROM product WHERE active=?";
        String[] pars = new String[1];
        pars[0] = "" + 1;
        List<ProductInterface> lista = new ArrayList();
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            //Mi popolo i campi brandName, categoryName e unitName
            int id_cat = rs.getInt("id_category");
            int id_brand = rs.getInt("id_brand");
            int id_unit = rs.getInt("id_unit");
            sql = "SELECT name FROM brand WHERE id=? AND active=1";
            pars[0] = "" + id_brand;
            ResultSet auxrs = this.db.PreparedStatement(sql, pars);
            String brandName = "";
            while (auxrs.next()) {
                brandName = auxrs.getString("name");
            }
            String categoryName = "";
            VirtualCategory cat = new VirtualCategory(db);
            CategoryInterface vi = cat.getVirtualCategoryWithName(id_cat);
            categoryName = vi.getParents();
            String unitName = "";
            sql = "SELECT name FROM unit WHERE id=? AND active=1";
            pars[0] = "" + id_unit;
            auxrs = this.db.PreparedStatement(sql, pars);
            while (auxrs.next()) {
                unitName = auxrs.getString("name");
            }
            int currentlyPresentNumber = this.getCurrentlyPresentProductNumber(rs.getInt("id"));
            vp = new VirtualProduct();
            vp.setId(rs.getInt("id"));
            vp.setModel(rs.getString("model"));
            vp.setId_category(id_cat);
            vp.setId_brand(id_brand);
            vp.setId_unit(id_unit);
            vp.setState(rs.getString("state"));
            vp.setTags(rs.getString("tags"));
            vp.setNote(rs.getString("note"));
            vp.setWharehouse_position(rs.getString("wharehouse_position"));
            vp.setWeight(rs.getDouble("weight"));
            vp.setMax_order(rs.getDouble("max_order"));
            vp.setMin_order(rs.getDouble("min_order"));
            vp.setProfit(rs.getDouble("profit"));
            vp.setPrice(rs.getDouble("price"));
            vp.setIva(rs.getInt("iva"));
            vp.setBrandName(brandName);
            vp.setCategoryName(categoryName);
            vp.setUnitName(unitName);
            vp.setCurrentlyPresentNumber(currentlyPresentNumber);
            lista.add(vp);
        }
        return lista;
    }

    public ProductInterface getProduct(ProductInterface vp, int id) throws ResultSetDBException, NotFoundDBException, SQLException {
        String sql = "SELECT * FROM product WHERE id=? AND active=1";

        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        vp = null;

        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            //Mi popolo i campi brandName, categoryName e unitName
            int id_cat = rs.getInt("id_category");
            int id_brand = rs.getInt("id_brand");
            int id_unit = rs.getInt("id_unit");
            sql = "SELECT name FROM brand WHERE id=? AND active=1";
            pars[0] = "" + id_brand;
            ResultSet auxrs = this.db.PreparedStatement(sql, pars);
            String brandName = "";
            while (auxrs.next()) {
                brandName = auxrs.getString("name");
            }
            String categoryName = "";
            VirtualCategory cat = new VirtualCategory(db);
            CategoryInterface vi = cat.getVirtualCategoryWithName(id_cat);
            categoryName = vi.getParents();
            String unitName = "";
            sql = "SELECT name FROM unit WHERE id=? AND active=1";
            pars[0] = "" + id_unit;
            auxrs = this.db.PreparedStatement(sql, pars);
            while (auxrs.next()) {
                unitName = auxrs.getString("name");
            }
            int currentlyPresentNumber = this.getCurrentlyPresentProductNumber(rs.getInt("id"));
            vp = new VirtualProduct();
            vp.setId(rs.getInt("id"));
            vp.setModel(rs.getString("model"));
            vp.setId_category(id_cat);
            vp.setId_brand(id_brand);
            vp.setId_unit(id_unit);
            vp.setState(rs.getString("state"));
            vp.setTags(rs.getString("tags"));
            vp.setNote(rs.getString("note"));
            vp.setWharehouse_position(rs.getString("wharehouse_position"));
            vp.setWeight(rs.getDouble("weight"));
            vp.setMax_order(rs.getDouble("max_order"));
            vp.setMin_order(rs.getDouble("min_order"));
            vp.setProfit(rs.getDouble("profit"));
            vp.setPrice(rs.getDouble("price"));
            vp.setIva(rs.getInt("iva"));
            vp.setBrandName(brandName);
            vp.setCategoryName(categoryName);
            vp.setUnitName(unitName);
            vp.setCurrentlyPresentNumber(currentlyPresentNumber);
        }
        return vp;

    }

    public void updateVirtualProduct(ProductInterface vp) throws NotFoundDBException {
        String sql = "UPDATE `product` SET `model`=?,`id_category`=?,`id_brand`=?,`id_unit`=?,`state`=?,`tags`=?,"
                + "`note`=?,`wharehouse_position`=?,`weight`=?,`max_order`=?,`min_order`=?,`profit`=?,"
                + "`price`=?,`iva`=? WHERE `id`=?";

        String[] pars = new String[15];
        pars[0] = vp.getModel();
        pars[1] = "" + vp.getId_category();
        pars[2] = "" + vp.getId_brand();
        pars[3] = "" + vp.getId_unit();
        pars[4] = vp.getState();
        pars[5] = vp.getTags();
        pars[6] = vp.getNote();
        pars[7] = vp.getWharehouse_position();
        pars[8] = "" + vp.getWeight();
        pars[9] = "" + vp.getMax_order();
        pars[10] = "" + vp.getMin_order();
        pars[11] = "" + vp.getProfit();
        pars[12] = "" + vp.getPrice();
        pars[13] = "" + vp.getIva();
        pars[14] = "" + vp.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Aggiornate 0 righe");
        }

    }

    public int insertVirtualProduct(ProductInterface vp) throws NotFoundDBException, NoSuchAlgorithmException, SQLException {
        String sql = "INSERT INTO `product`(`model`,`id_category`,`id_brand`,`id_unit`,`state`,`tags`,`note`,`wharehouse_position`,"
                + "`weight`,`max_order`,`min_order`,`profit`,`price`,`iva`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String[] pars = new String[14];
        pars[0] = vp.getModel();
        pars[1] = "" + vp.getId_category();
        pars[2] = "" + vp.getId_brand();
        pars[3] = "" + vp.getId_unit();
        pars[4] = vp.getState();
        pars[5] = vp.getTags();
        pars[6] = vp.getNote();
        pars[7] = vp.getWharehouse_position();
        pars[8] = "" + vp.getWeight();
        pars[9] = "" + vp.getMax_order();
        pars[10] = "" + vp.getMin_order();
        pars[11] = "" + vp.getProfit();
        pars[12] = "" + vp.getPrice();
        pars[13] = "" + vp.getIva();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
        sql = "SELECT LAST_INSERT_ID();";
        ResultSet rs = this.db.select(sql);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("LAST_INSERT_ID()");
        }
        return id;
    }

    public void deleteVirtualProduct(ProductInterface vp) throws NotFoundDBException {
        String sql = "UPDATE `product` SET `active`=0 WHERE `id`=? ";

        String[] pars = new String[1];
        pars[0] = "" + vp.getId();
        this.db.PreparedStatementUpdate(sql, pars);
    }

    private int getCurrentlyPresentProductNumber(int id) throws NotFoundDBException, SQLException {
        int number = 0;
        String sql = "SELECT quantity FROM supply WHERE id_product=?";
        String[] pars = new String[1];
        pars[0] = "" + id;
        ResultSet rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            number = number + rs.getInt("quantity");
        }
        sql = "SELECT quantity FROM drain_row WHERE id_product=?";
        rs = db.PreparedStatement(sql, pars);
        while (rs.next()) {
            number = number - rs.getInt("quantity");
        }
        return number;
    }

}
