/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.SupplyInterface;
import BusinessLogic.VirtualEntities.VirtualSupply;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Filippo Luppi e Mattia Ravarotto
 */
public class SupplyDAO {

    Database db;

    public SupplyDAO(Database db) {
        this.db = db;
    }

    public List<SupplyInterface> getAllSupply(SupplyInterface vs) throws NotFoundDBException, SQLException {
        String sql = "SELECT * FROM supply WHERE active=1";

        List<SupplyInterface> lista = new ArrayList();
        ResultSet rs = this.db.select(sql);
        GregorianCalendar cal = null;
        while (rs.next()) {
            cal = new GregorianCalendar();
            cal.setTime(rs.getDate("last_mod"));
            vs = new VirtualSupply();
            vs.setId(rs.getInt("id"));
            vs.setId_product(rs.getInt("id_product"));
            vs.setId_provider(rs.getInt("id_provider"));
            vs.setQuantity(rs.getDouble("quantity"));
            vs.setProvider_unit_price(rs.getDouble("provider_unit_price"));
            vs.setLast_mod(cal);
            vs.setProductName(this.getProductModel(vs.getId_product()));
            vs.setProviderName(this.getProviderName(vs.getId_provider()));
            lista.add(vs);
        }
        return lista;
    }

    public SupplyInterface getSupply(SupplyInterface vs, int id) throws NotFoundDBException, ResultSetDBException, SQLException {
        String sql = "SELECT * FROM supply WHERE id=? AND active=1";

        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        GregorianCalendar cal = null;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            cal = new GregorianCalendar();
            cal.setTime(rs.getDate("last_mod"));
            vs.setId(rs.getInt("id"));
            vs.setId_product(rs.getInt("id_product"));
            vs.setId_provider(rs.getInt("id_provider"));
            vs.setQuantity(rs.getDouble("quantity"));
            vs.setProvider_unit_price(rs.getDouble("provider_unit_price"));
            vs.setLast_mod(cal);
            vs.setProductName(this.getProductModel(vs.getId_product()));
            vs.setProviderName(this.getProviderName(vs.getId_provider()));
        }

        return vs;
    }

    public void insertVirtualSupply(SupplyInterface vs) throws NotFoundDBException {
        String sql = "INSERT INTO `supply`( `id_product`, `id_provider`,`quantity`,`provider_unit_price`) VALUES (?,?,?,?) ";
        String[] pars = new String[4];
        pars[0] = "" + vs.getId_product();
        pars[1] = "" + vs.getId_provider();
        pars[2] = "" + vs.getQuantity();
        pars[3] = "" + vs.getProvider_unit_price();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void updateVirtualSupply(SupplyInterface vs) throws NotFoundDBException {
        String sql = "UPDATE `supply` SET `id_product`=?,`id_provider`=?,`quantity`=?,`provider_unit_price`=? WHERE `id`=?";

        String[] pars = new String[5];
        pars[0] = "" + vs.getId_product();
        pars[1] = "" + vs.getId_provider();
        pars[2] = "" + vs.getQuantity();
        pars[3] = "" + vs.getProvider_unit_price();
        pars[4] = "" + vs.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {

            System.out.println("Aggiornate 0 righe");
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

    public void deleteVirtualSupply(SupplyInterface vs) throws NotFoundDBException {
        String sql = "UPDATE `supply` SET `active`=0 WHERE `id`=? ";
        String[] pars = new String[1];
        pars[0] = "" + vs.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }

    public List<SupplyInterface> getSupplyByProductId(SupplyInterface vs, int id_product) throws NotFoundDBException, ResultSetDBException {
        String sql = "SELECT * FROM supply WHERE id=? AND active=1";
        List<SupplyInterface> lista = new ArrayList();
        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id_product;
        GregorianCalendar cal = new GregorianCalendar();
        try {
            rs = this.db.PreparedStatement(sql, pars);
            while (rs.next()) {
                cal.setTime(rs.getDate("last_mod"));
                vs = new VirtualSupply();
                vs.setId(rs.getInt("id"));
                vs.setId_product(rs.getInt("id_product"));
                vs.setId_provider(rs.getInt("id_provider"));
                vs.setQuantity(rs.getDouble("quantity"));
                vs.setProvider_unit_price(rs.getDouble("provider_unit_price"));
                vs.setLast_mod(cal);
                vs.setProductName(this.getProductModel(vs.getId_product()));
                vs.setProviderName(this.getProviderName(vs.getId_provider()));
                lista.add(vs);
            }
        } catch (SQLException e) {
            throw new ResultSetDBException("Errore: VirtualUser GetUser(int id) " + e.getMessage(), db);
        }
        return lista;
    }

    private String getProductModel(int id_product) throws NotFoundDBException, SQLException {
        String sql = "SELECT model FROM product WHERE active=1 AND id=?";
        String[] pars = new String[1];
        pars[0] = "" + id_product;
        ResultSet rsp = this.db.PreparedStatement(sql, pars);
        String productModel = "";
        while (rsp.next()) {
            productModel = rsp.getString("model");
        }
        return productModel;
    }

    private String getProviderName(int id_people) throws NotFoundDBException, SQLException {
        String sql = "SELECT name FROM people WHERE active=1 AND id=?";
        String[] pars = new String[1];
        pars[0] = "" + id_people;
        ResultSet rsp = this.db.PreparedStatement(sql, pars);
        String name = "";
        while (rsp.next()) {
            name = rsp.getString("name");
        }
        return name;
    }

}
