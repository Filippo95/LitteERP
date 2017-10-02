/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.Database;

import java.io.*;
import java.sql.*;

import Services.Database.Exception.*;
import java.util.ArrayList;

public class Database {

    private Connection connection;
    private PreparedStatement statement;

    public Database(Connection connection) throws NotFoundDBException {

        try {
            this.connection = connection;
            if (this.connection == null) {
                throw new NotFoundDBException("DataBase: DataBase(): Impossibile Aprire la Connessione Logica");
            }

            this.connection.setAutoCommit(false);

        } catch (Exception ex) {
            ByteArrayOutputStream stackTrace = new ByteArrayOutputStream();
            ex.printStackTrace(new PrintWriter(stackTrace, true));
            throw new NotFoundDBException("DataBase: DataBase(): Impossibile Aprire la Connessione col DataBase: \n" + stackTrace);
        }

    }

    public ResultSet select(String sql) throws NotFoundDBException {

        ResultSet resultSet;

        try {
            resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException ex) {
            throw new NotFoundDBException("DataBase: select(): Impossibile eseguire la query sul DB. Eccezione: " + ex + "\n " + sql, this);
        }
    }

    public ResultSet PreparedStatement(String sql, String[] par) throws NotFoundDBException {
        ResultSet rs = null;
        try {
            statement = this.connection.prepareStatement(sql);
            for (int i = 0; i < par.length; i++) {
                statement.setString(i + 1, par[i]);
            }
            System.out.println(statement.toString());
            rs = statement.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new NotFoundDBException("DataBase: ResultSet PrepareStatement(): Impossibile eseguire la query sul DB. Eccezione: " + ex + "\n " + sql, this);
        }

        return rs;

    }

    public int PreparedStatementUpdate(String sql, String[] par) throws NotFoundDBException {
        int rs = 0;
        try {
            statement = this.connection.prepareStatement(sql);
            for (int i = 0; i < par.length; i++) {
                statement.setString(i + 1, par[i]);
            }
            System.out.println(statement.toString());
            rs = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new NotFoundDBException("DataBase: ResultSet PrepareStatement(): Impossibile eseguire la query sul DB. Eccezione: " + ex + "\n " + sql, this);
        }
        return rs;

    }

    public int PreparedStatementforImg(String sql, Blob content, String contentType, int id_product) throws NotFoundDBException {

        System.out.println("Sono la funzione PreparedStatementforImg");
        int rs = 0;
        System.out.println("Sono la classe database");
        try {
            statement = this.connection.prepareStatement(sql);

            System.out.println("Sono la classe database: ho fatto lo statement");
            statement.setBlob(1, content);

            System.out.println("Sono la classe database: ho inserito il blob");
            statement.setString(2, contentType);
            statement.setInt(3, id_product);
            System.out.println("sono la classe Database e ho settato il prepared statement");
            rs = statement.executeUpdate();
            System.out.println("Ho eseguito la query ");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("sono la classe database e sono caduta in eccezione");
            throw new NotFoundDBException("DataBase: ResultSet PrepareStatement(): Impossibile eseguire la query sul DB. Eccezione: " + ex + "\n " + sql, this);
        }
        return rs;
    }

    public int PreparedStatementforDatasheet(String sql, Blob content, String contentType, int id_product) throws NotFoundDBException {

        System.out.println("Sono la funzione PreparedStatementforDatashett");
        int rs = 0;
        System.out.println("Sono la classe database");
        try {
            statement = this.connection.prepareStatement(sql);

            System.out.println("Sono la classe database: ho fatto lo statement");
            statement.setString(1, contentType);
            statement.setBlob(2, content);
            statement.setInt(3, id_product);

            System.out.println("sono la classe Database e ho settato il prepared statement " + id_product);

            System.out.println("sono la classe Database la query è: ");
            rs = statement.executeUpdate();
            System.out.println("Ho eseguito la query ");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("sono la classe database e sono caduta in eccezione");
            throw new NotFoundDBException("DataBase: ResultSet PrepareStatement(): Impossibile eseguire la query sul DB. Eccezione: " + ex + "\n " + sql, this);
        }
        return rs;
    }

    public void commit() throws NotFoundDBException {

        try {

            connection.commit();
            statement.close();
            return;

        } catch (SQLException ex) {
            throw new NotFoundDBException("DataBase: commit(): Impossibile eseguire la commit sul DB. Eccezione: " + ex, this);
        }

    }

    public void rollBack() {

        try {
            connection.rollback();
            statement.close();
            return;
        } catch (SQLException ex) {
            new NotFoundDBException("DataBase: rollback(): Impossibile eseguire il RollBack sul DB. Eccezione: " + ex);
        }
    }

    public void close() throws NotFoundDBException {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new NotFoundDBException("DataBase: close(): Impossibile chiudere il DB. Eccezione: " + ex);
        }
    }

    protected void finalize() throws Throwable {
        this.close();
    }

}
