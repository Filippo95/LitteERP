
package Services.Database.Exception;

import Services.Database.*;
import Services.Log.*;

public class DBException extends Exception {
  
  protected Database database=null;
  protected String logMessage;
    
  public DBException() {}
  
  public DBException(String msg) {
    this(msg,null);
  }
  
  /**
   * Constructs an <code>DBException</code> with the specified detail message and
   * the specified DataBase.
   * @param msg the detail message.
   * @param oDatabase the DataBase
   */
  
  public DBException(String msg,Database database) {
    
    super(msg);
    this.database=database;
    logMessage="Created Exception: "+ msg;
    log();
  }
  
  public void log() {    
    ErrorLog.databaseErrorLog(logMessage);    
  }
}

