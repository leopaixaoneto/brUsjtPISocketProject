package br.sp.usjt.serverSocket.dao;

import br.sp.usjt.serverSocket.Utils.vars;

import java.sql.*;

public class simpleDAO {

    public Connection conexao;
    public String tableName;

    public String GET_ALL_QUERY;

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
               return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean createTable(){
        Statement stmt = null;
        try {

            stmt = conexao.createStatement();
            stmt.execute(getTableQuery());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getTableQuery(){
        return vars.CREATE_TABLE_QUERY.get(this.getClass().getSimpleName());
    }

    public void startQueryList(){
        GET_ALL_QUERY = "SELECT * FROM " + tableName;
    }


}
