/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.Exception;

import Services.Database.Database;
import Services.Database.Exception.NotFoundDBException;
import Services.Database.Exception.ResultSetDBException;
import Services.Error.EService;
import java.sql.SQLException;

/**
 *
 * @author filippo
 */
public class PersonalizedException {

    public static int logAndRecoverException(Exception e,Database database) {
        if (e instanceof NotFoundDBException) {
            EService.logAndRecover((NotFoundDBException)e);
            return EService.UNRECOVERABLE_ERROR;
            
        }
        if (e instanceof ResultSetDBException) {
            EService.logAndRecover((ResultSetDBException) e);
            return EService.UNRECOVERABLE_ERROR;
        }
        if(e instanceof SQLException)
        {
            Exception ex=new ResultSetDBException("ContactService: getContact():  Errore nel ResultSet: "+e.getMessage(),database);
            EService.logAndRecover((ResultSetDBException) ex);
            return EService.UNRECOVERABLE_ERROR;
        }
        return EService.UNRECOVERABLE_ERROR;
    }

}
