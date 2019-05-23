package br.sp.usjt.serverSocket.dao;


import java.sql.*;

public abstract class simpleDAO {

    public Connection conexao;
    public String tableName;

    public String GET_ALL_QUERY;
    public String createTableQuery;

    public boolean isTableExist(){
        DatabaseMetaData dbm = null;

        try {
            dbm = conexao.getMetaData();
            // check if className table is there
            ResultSet tables = dbm.getTables(null, null, tableName, null);

            if (tables.next()) {
                return true;
            }
            else {
                createTable();
               return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createTable();
        return false;
    }

    public boolean createTable(){
        Statement stmt = null;
        try {

            stmt = conexao.createStatement();
            stmt.execute(createTableQuery);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void startQueryList(){
        GET_ALL_QUERY = "SELECT * FROM " + tableName;
    }


}
