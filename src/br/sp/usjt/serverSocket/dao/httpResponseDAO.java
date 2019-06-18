package br.sp.usjt.serverSocket.dao;

import br.sp.usjt.serverSocket.Model.httpResponse;
import br.sp.usjt.serverSocket.Utils.vars;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class httpResponseDAO extends simpleDAO {

    public static final String ENTRY_TYPE_START = "Servidor Iniciado";
    public static final String ENTRY_TYPE_STOP = "Servidor finalizado";

    public httpResponseDAO()  {

        super();
        try {

            this.conexao = ConnectionFactory.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.createTableQuery = vars.CREATE_TABLE_QUERY.get("httpResponse");

        this.tableName = httpResponse.class.getSimpleName();

        startQueryList();
        isTableExist();

    }

    public void close(){
        try {
            this.conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<httpResponse> getAll(){
        List<httpResponse> httpResponseList = new ArrayList<httpResponse>();

        try{
            PreparedStatement statement = conexao.prepareStatement(this.GET_ALL_QUERY);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){

                httpResponse hR = new httpResponse();
                hR.setFile(new File(rs.getString("file")));
                hR.setContentType(rs.getString("contentType"));
                hR.setHttpCode(rs.getInt("httpCode"));
                hR.setDataResposta(rs.getString("date"));

                httpResponseList.add(hR);
            }
            statement.close();

        }catch(SQLException ex){

        }

        return httpResponseList;
    }


    public void save(httpResponse response){
        try{
            PreparedStatement statement = conexao.prepareStatement("INSERT INTO httpResponse(file, contentType, httpCode, date) VALUES (?,?,?,?)");

            System.out.println(response.getFile().getAbsolutePath());
            statement.setString(1, response.getFile().getAbsolutePath().split("\\.",2)[1]);

            statement.setString(2,response.getContentType());
            statement.setInt(3,response.getHttpCode());
            statement.setString(4, response.getDataResposta());

            statement.execute();
            statement.close();

        }catch(SQLException ex){

        }
    }



    public void saveServerEntry(String ENTRY_TYPE){

        try{
            PreparedStatement statement = conexao.prepareStatement("INSERT INTO httpResponse(file, contentType, httpCode, date) VALUES (?,?,?,?)");

            statement.setString(1, ENTRY_TYPE);

            statement.setString(2,"server/stateChange");

            if(ENTRY_TYPE == ENTRY_TYPE_START){
                statement.setInt(3,0);
            }else{
                statement.setInt(3,1);
            }

            statement.setString(4, new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date()));

            statement.execute();
            statement.close();

            System.out.println("passei por aqui!");

        }catch(SQLException ex){

        }
    }


    public Map<String, Integer> countHttpCodes(){

        Map<String, Integer> mapCount = new HashMap<>();

        try{
            for(Map.Entry<Integer, String> pair : vars.HTTP_CODE_MEANING.entrySet()){
                PreparedStatement statement = conexao.prepareStatement("SELECT COUNT(httpCode) FROM httpresponse WHERE httpCode = ?;");
                statement.setInt(1, pair.getKey());

                ResultSet rs = statement.executeQuery();

                while(rs.next()){
                    mapCount.put("HTTP CODE: " + pair.getKey(), rs.getInt("COUNT(httpCode)"));
                }
            }

        }catch(SQLException ex){

        }

        return mapCount;
    }
}
