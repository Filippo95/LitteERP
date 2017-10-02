/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.UnitInterface;
import BusinessLogic.VirtualEntities.VirtualUnit;
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
public class UnitDAO {

    Database db;

    public UnitDAO(Database db) {
        this.db = db;
    }

    public List<UnitInterface> getAllUnit(UnitInterface vu) throws SQLException, NotFoundDBException {
        String sql = "SELECT * FROM unit WHERE active=1";

        List<UnitInterface> lista = new ArrayList();
        ResultSet rs = this.db.select(sql);
        while (rs.next()) {
            vu = new VirtualUnit();
            vu.setId(rs.getInt("id"));
            vu.setName(rs.getString("name"));
            vu.setDescription(rs.getString("description"));
            vu.setHasProducts(this.hasProducts(vu.getId()));
            lista.add(vu);
        }
        return lista;
    }

    public UnitInterface getUnit(UnitInterface vu,int id) throws NotFoundDBException, ResultSetDBException, SQLException {
        String sql = "SELECT * FROM unit WHERE id=? AND active=1";

        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id;
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            vu.setId(rs.getInt("id"));
            vu.setName(rs.getString("name"));
            vu.setDescription(rs.getString("description"));
            vu.setHasProducts(this.hasProducts(vu.getId()));
        }
        return vu;
    }

    public void insertVirtualUnit(UnitInterface vu) throws NotFoundDBException {
        String sql = "INSERT INTO `unit`( `name`, `description`) VALUES (?,?) ";
        String[] pars = new String[2];
        pars[0] = vu.getName();
        pars[1] = vu.getDescription();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
    }

    public void updateVirtualUnit(UnitInterface vu) throws NotFoundDBException {
        String sql = "UPDATE `unit` SET `name`=?,`description`=? WHERE `id`=?";

        String[] pars = new String[3];
        pars[0] = vu.getName();
        pars[1] = vu.getDescription();
        pars[2] = "" + vu.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {

            System.out.println("Aggiornate 0 righe");
            throw new NotFoundDBException("Aggiornate 0 righe");
        }
    }

    public void deleteVirtualUnit(UnitInterface vu) throws NotFoundDBException {
        String sql = "UPDATE `unit` SET `active`=0 WHERE `id`=? ";

        String[] pars = new String[1];
        pars[0] = "" + vu.getId();
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
    }

    private Boolean hasProducts(int id_unit) throws NotFoundDBException, SQLException {
        String sql = "SELECT COUNT(*) as products FROM product WHERE id_unit=? AND active=1";
        String[] pars = new String[1];
        pars[0] = "" + id_unit;
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
