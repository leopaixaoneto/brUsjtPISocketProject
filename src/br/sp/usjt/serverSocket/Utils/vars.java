package br.sp.usjt.serverSocket.Utils;

import java.util.HashMap;
import java.util.Map;

public class vars {

    //Mapa de codigos de resposta / Seu significado
    public static final Map<Integer, String> HTTP_CODE_MEANING = new HashMap<Integer, String>(){{
        this.put(200, "OK");
        this.put(400, "Bad Request");
        this.put(403, "Forbidden");
        this.put(404,"Not Found");
        this.put(500, "Internal Server Error");
    }};

    //Mapa de tipos de arquivo e seus respectivos content type
    public static final Map<String, String> MIME_CODE_MEANING = new HashMap<String, String>(){{
        this.put(".htm", "text/html");
        this.put(".html", "text/html");
        this.put(".jpg", "image/jpeg");
        this.put(".jpeg", "image/jpeg");
    }};

    public static final Map<String, String> CREATE_TABLE_QUERY = new HashMap<String, String>(){{
        this.put("httpResponse", "CREATE TABLE `httpResponse` (" +
                "\t`id` INT NOT NULL AUTO_INCREMENT," +
                "\t`file` VARCHAR(255) NOT NULL DEFAULT '0'," +
                "\t`contentType` VARCHAR(50) NOT NULL DEFAULT '0'," +
                "\t`httpCode` INT NOT NULL DEFAULT '0'," +
                "\t`date` VARCHAR(100) NOT NULL DEFAULT '0'," +
                "\tPRIMARY KEY (`id`)" +
                ")" +
                "COLLATE='latin1_swedish_ci';");

    }};

    public static final Map<Integer, Integer> TEST_RELATORIO = new HashMap<Integer, Integer>(){{
        this.put(200, 20);
        this.put(404, 5);
    }};

    public static final String RELATORIO_TYPE_LINE = "line";
    public static final String RELATORIO_TYPE_COLUMN = "column";
    public static final String RELATORIO_TYPE_BAR = "bar";
    public static final String RELATORIO_TYPE_AREA = "area";
    public static final String RELATORIO_TYPE_SPLINE = "spline";
    public static final String RELATORIO_TYPE_SPLINEAREA = "splineArea";
    public static final String RELATORIO_TYPE_STEPLINE = "stepLine";
    public static final String RELATORIO_TYPE_SCATTER = "scatter";
    public static final String RELATORIO_TYPE_BUBBLE = "bubble";
    public static final String RELATORIO_TYPE_STACKEDCOLUMN = "stackedColumn";
    public static final String RELATORIO_TYPE_STACKEDBAR = "stackedBar";
    public static final String RELATORIO_TYPE_STACKEDAREA = "stackedArea";
    public static final String RELATORIO_TYPE_STACKEDCOLUMN100 = "stackedColumn100";
    public static final String RELATORIO_TYPE_STACKEDBAR100 = "stackedBar100";
    public static final String RELATORIO_TYPE_STACKEDAREA100 = "stackedArea100";
    public static final String RELATORIO_TYPE_PIE = "pie";
    public static final String RELATORIO_TYPE_DOUGHNUT = "doughnut";

}
