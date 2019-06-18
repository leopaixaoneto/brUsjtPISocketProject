package br.sp.usjt.serverSocket.dao;

import br.sp.usjt.serverSocket.Model.httpResponse;
import br.sp.usjt.serverSocket.Utils.vars;
import com.sun.org.apache.bcel.internal.generic.SWITCH;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class httpResponseDAO extends simpleDAO {

    public static final String ENTRY_TYPE_START = "Servidor Iniciado";
    public static final String ENTRY_TYPE_STOP = "Servidor finalizado";
    public static final int COUNT_DAY = 0;
    public static final int COUNT_MONTH = 1;
    public static final int COUNT_YEAR = 2;
    public static final int COUNT_HOUR = 3;
    public static final int COUNT_UPTIME = 4;


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

        }catch(SQLException ex){

        }
    }


    public Map<String, Integer> countHttpCodes(){

        Map<String, Integer> mapCount = new LinkedHashMap<>();

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

    public Map<String, Integer> countContentTypeResponses(){
        Map<String, Integer> mapCount = new LinkedHashMap<>();

        try {
            PreparedStatement statement = conexao.prepareStatement("SELECT contentType, count(*) from httpresponse group by contentType ORDER by count(*) ASC");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                mapCount.put(rs.getString("contentType"), rs.getInt("count(*)"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mapCount;
    }

    public Map<String, Integer> countMostAccessedFiles(){
        Map<String, Integer> mapCount = new LinkedHashMap<>();

        try {
            PreparedStatement statement = conexao.prepareStatement("SELECT file, count(*) from httpresponse group by file ORDER by count(*) ASC");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                mapCount.put(rs.getString("file"), rs.getInt("count(*)"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mapCount;
    }

    public Map<String, Integer> countMostAccess(int count_type){
        Map<String, Integer> mapCount = new LinkedHashMap<>();

        Map<Integer, Integer> auxCount = new LinkedHashMap<>();

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        LocalDateTime localDateTime = null;

        if(count_type == COUNT_UPTIME){

            double  up = 0;
            double down = 0;
            LocalDateTime localDateTimeBefore = null;

            try {
                PreparedStatement statement =  conexao.prepareStatement("SELECT date,httpCode FROM httpresponse WHERE httpCode = 1 OR httpCode = 0 ORDER BY date");
                ResultSet rs = statement.executeQuery();
                long difAux = 0;

                while (rs.next()){
                    if(localDateTimeBefore == null){
                        localDateTimeBefore = fmt.parse(rs.getString("date")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    }else{
                        localDateTime = fmt.parse(rs.getString("date")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                        difAux = ChronoUnit.SECONDS.between(localDateTimeBefore, localDateTime);

                        if(rs.getInt("httpCode") == 0){
                            up += difAux;
                        }else{
                            down += difAux;
                        }

                        localDateTimeBefore = localDateTime;
                    }

                    if(rs.isLast()){
                        difAux = ChronoUnit.SECONDS.between(localDateTimeBefore, LocalDateTime.now());

                        if(rs.getInt("httpCode") == 0){
                            up += difAux;
                        }else{
                            down += difAux;
                        }
                    }
                }

                double onTime = (up/(up+down)) * 100;
                double offTime = (down/(up+down)) * 100;

                System.out.println("SEGUNDOS LIGADO: " + up);
                System.out.println("SEGUNDOS DESLIGADO: " + down);

                mapCount.put("On (%)", (int) onTime);
                mapCount.put("Off (%)", (int) offTime);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{
            List<LocalDateTime> dateList = new ArrayList();

            try {
                PreparedStatement statement = conexao.prepareStatement("SELECT date FROM httpresponse ORDER BY date");
                ResultSet rs = statement.executeQuery();

                while (rs.next()){
                    localDateTime = fmt.parse(rs.getString("date")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    dateList.add(localDateTime);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            switch (count_type){
                case(COUNT_DAY):
                    for(int i = 1; i<=31; i++){
                        auxCount.put(i,0);
                    }

                    for(LocalDateTime ld : dateList){
                        if(auxCount.containsKey(ld.getDayOfMonth())){
                            auxCount.put(ld.getDayOfMonth(), auxCount.get(ld.getDayOfMonth()) + 1);
                        }
                    }

                    auxCount.forEach((k,v) -> {
                        if(v > 0){
                            mapCount.put(String.valueOf(k),v);
                        }
                    });

                    break;

                case(COUNT_MONTH):
                    for(int i = 1; i<=12; i++){
                        auxCount.put(i,0);
                    }

                    for(LocalDateTime ld : dateList){
                        if(auxCount.containsKey((ld.getMonthValue()))){
                            auxCount.put(ld.getMonthValue(), auxCount.get(ld.getMonthValue()) + 1);
                        }
                    }


                    auxCount.forEach((k,v) -> {
                        if(v > 0){
                            mapCount.put(Month.of(k).getDisplayName(TextStyle.FULL, new Locale("pt")),v);
                        }
                    });

                    break;

                case(COUNT_YEAR):
                    for(int i = 2000; i<=2050; i++){
                        auxCount.put(i,0);
                    }

                    for(LocalDateTime ld : dateList){
                        if(auxCount.containsKey(ld.getYear())){
                            auxCount.put(ld.getYear(), auxCount.get(ld.getYear()) + 1);
                        }
                    }

                    auxCount.forEach((k,v) -> {
                        if(v > 0){
                            mapCount.put(String.valueOf(k),v);
                        }
                    });

                    break;

                case(COUNT_HOUR):
                    for(int i = 0; i<=23; i++){
                        auxCount.put(i,0);
                    }

                    for(LocalDateTime ld : dateList){
                        if(auxCount.containsKey(ld.getHour())){
                            auxCount.put(ld.getHour(), auxCount.get(ld.getHour()) + 1);
                        }
                    }

                    auxCount.forEach((k,v) -> {
                        if(v > 0){
                            mapCount.put(String.valueOf(k)+":00 ~ " + String.valueOf(k)+":59",v);
                        }
                    });


                    break;

                case(COUNT_UPTIME):

                    break;

                default:
                    break;
            }
        }

        return mapCount;
    }


    public Map<String, Integer> countDiferenceBetweenMonth(int month) {


        Map<String, Integer> mapCount = new LinkedHashMap<>();

        Map<Integer, Map<Integer,Integer>> auxCount = new LinkedHashMap<>();
        Map<Integer,Integer> auxMonthCount = new LinkedHashMap<>();

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        LocalDateTime localDateTime = null;

        List<LocalDateTime> dateList = new ArrayList();

        for(int i = 1; i<=12; i++){
            auxMonthCount.put(i,0);
        }

        for(int i = 2000; i<=2050; i++){
            auxCount.put(i,auxMonthCount);
        }


        for(Map.Entry<String, Integer> pair : countMostAccess(COUNT_YEAR).entrySet()){
            try {
                PreparedStatement statement = conexao.prepareStatement("SELECT date FROM httpresponse WHERE date LIKE '%" + pair.getKey() + "%' ORDER BY date");

                System.out.println("SELECT date FROM httpresponse WHERE date LIKE '%" + pair.getKey() +"%' ORDER BY date");

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    localDateTime = fmt.parse(rs.getString("date")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    dateList.add(localDateTime);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for(LocalDateTime ld : dateList){
                if(ld.getMonthValue() == month){
                    auxMonthCount.put(ld.getMonthValue(), auxMonthCount.get(ld.getMonthValue()) + 1);
                }
            }

            auxCount.put(Integer.parseInt(pair.getKey()), auxMonthCount);

            auxCount.forEach((k,v) -> {
                System.out.println("KeyTested: " + k + " || PairKeyTested: " + pair.getKey());

                if(String.valueOf(k).equals(pair.getKey())){
                    if(v.get(month) > 0){
                        mapCount.put(String.valueOf(k),v.get(month));
                    }
                    System.out.println("KEY: " + k + " VALUE: " + v.get(month));
                }
            });

        }



        return mapCount;
    }

    //FEITO - SELECT COUNT(httpCode) FROM httpresponse WHERE httpCode = ?;
    //FEITO - SELECT contentType, count(*) from httpresponse group by contentType ORDER by count(*) ASC
    //FEITO - SELECT file, count(*) from httpresponse group by file ORDER by count(*) ASC
    //
    //
    //
    //

}
