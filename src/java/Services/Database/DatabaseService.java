/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.Database;



import Services.Database.Database;
import java.sql.*;

import Config.*;
import Services.Database.Exception.*;

public class DatabaseService extends Object {
  
  public DatabaseService() {}

  public static synchronized Database getDataBase() throws NotFoundDBException {

     try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(Constants.DB_CONNECTION_STRING);
        return new Database(connection);
      } catch (Exception e) {
        throw new NotFoundDBException("DBService: Impossibile creare la Connessione al DataBase: " + e);
      }
    }

    


}