/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Interface;


/**
 *
 * @author filippo
 */
public interface ImageInterface {
     public int getId() ;

    public void setId(int id) ;
     

    public int getId_product();

    public void setId_product(int id_product) ;

    public String getFile_format() ;

    public void setFile_format(String file_format);

    public byte[] getContent() ;

    public void setContent(byte[] content);


    
}
