/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.DAO;

import BusinessLogic.Interface.ImageInterface;
import BusinessLogic.VirtualEntities.VirtualImage;
import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author filippo
 */
public class ImageDAO {

    Database db;

    public ImageDAO(Database db) {
        this.db = db;
    }

    public List<ImageInterface> getVirtualImages(ImageInterface img,int id_product) throws NotFoundDBException, SQLException {
        String sql = "SELECT * FROM image WHERE id_product=? AND active=1";

        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = "" + id_product;
        List<ImageInterface> vimg = new ArrayList();

        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            img = new VirtualImage();
            img.setId(rs.getInt("id"));
            img.setFile_format(rs.getString("file_format"));
            img.setContent(rs.getBlob("content").getBytes(1, (int) rs.getBlob("content").length()));
            vimg.add(img);
        }
        return vimg;
    }

    public void insertVirtualImage(ImageInterface img) throws NotFoundDBException, SQLException {
        System.out.println("Ciao sono il dao, ora inserisco");
        
        Blob blob  = new SerialBlob(img.getContent());
        System.out.println("Ho il blob");
        String sql = "INSERT INTO `image`( `content`, `file_format`, `id_product`, `active`) "
                + "VALUES (?,?,?,'1') ";   
        System.out.println("ho la stringa sql ");
       
        if (this.db.PreparedStatementforImg(sql,blob,img.getFile_format(),img.getId_product())<1) {
            throw new NotFoundDBException("Inserite 0 righe nel database");
        }
        
        
        
        System.out.println("ok6");
       
        
        System.out.println("ok, ho fatto ");

    }

    public void deleteVirtualImage(ImageInterface aThis) throws NotFoundDBException {
        System.out.println("Ciao sono il dao delle immagini sto per cancellarne una ");
        String sql = "UPDATE `image` SET `active`=0 WHERE `id`=? ";
        String[] pars = new String[1];
        pars[0] = "" + aThis.getId();
        
        System.out.println("l'id dell'immagine da eliminare: "+aThis.getId());
        if (this.db.PreparedStatementUpdate(sql, pars) < 1) {
            throw new NotFoundDBException("Eliminate 0 righe!");
        }
        
        System.out.println("Ciao sono il dao e ho finito ");
    }

    public List<ImageInterface> getAllVirtualImages(ImageInterface img) throws NotFoundDBException, SQLException {
         String sql = "SELECT * FROM image WHERE  active=?";

        ResultSet rs;
        String[] pars = new String[1];
        pars[0] = ""+1;
        List<ImageInterface> vimg = new ArrayList();
        rs = this.db.PreparedStatement(sql, pars);
        while (rs.next()) {
            img = new VirtualImage();
            img.setId(rs.getInt("id"));
            img.setId_product(rs.getInt("id_product"));
            img.setFile_format(rs.getString("file_format"));
            img.setContent(rs.getBlob("content").getBytes(1, (int) rs.getBlob("content").length()));
            vimg.add(img);
        }
        return vimg; 
    }
}
