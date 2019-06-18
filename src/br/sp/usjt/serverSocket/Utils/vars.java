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
        this.put(0, "Server Started");
        this.put(1,"Server Stopped");
    }};

    //Mapa de tipos de arquivo e seus respectivos content type
    public static final Map<String, String> MIME_CODE_MEANING = new HashMap<String, String>(){{
        this.put(".htm", "text/html");
        this.put(".html", "text/html");
        this.put(".jpg", "image/jpeg");
        this.put(".jpeg", "image/jpeg");
        this.put(".css", "text/css");
        this.put(".js", "text/javascript");
        this.put(".pdf", "application/pdf");
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



    //HARDCODED DATA TO GRAPHICS

    //"HORAS MAIS ACESSADAS"
    public static final Map<String, Integer> GRAPH2_DATA = new HashMap<String, Integer>(){{
        this.put("19:00", 15);
        this.put("20:00", 25);
        this.put("21:00", 60);
        this.put("22:00", 30);
    }
    };


    //"DIAS MAIS ACESSADOS"
    public static final Map<String, Integer> GRAPH3_DATA = new HashMap<String, Integer>(){{
            this.put("22/05/2019", 25);
            this.put("29/05/2019", 30);
            this.put("05/06/2019", 35);
            this.put("12/06/2019", 50);
        }
    };


    //"Mes MAIS ACESSADAS"
    public static final Map<String, Integer> GRAPH4_DATA = new HashMap<String, Integer>(){{
        this.put("abril", 30);
        this.put("maio", 55);
        this.put("Junho", 85);
        }
    };

    //"Ano MAIS ACESSADAS"
    public static final Map<String, Integer> GRAPH5_DATA = new HashMap<String, Integer>(){{
        this.put("2017", 0);
        this.put("2018", 0);
        this.put("2019", 130);
        }
    };

    //"Arquivos MAIS ACESSADOS"
    public static final Map<String, Integer> GRAPH6_DATA = new HashMap<String, Integer>(){{
        this.put("index.html", 40);
        this.put("relatorios.html", 60);
        this.put("imgtest.jpg", 5);
        this.put("404.html", 10);
    }
    };

    //"tempo de resposta"
    public static final Map<String, Integer> GRAPH7_DATA = new HashMap<String, Integer>(){{
        this.put(" <= 15ms", 50);
        this.put(">15ms E <= 60ms", 25);
        this.put(">60ms E <= 200ms", 15);
        this.put(">200ms", 10);
        }
    };

    //"TEMPO CONECTADO"
    public static final Map<String, Integer> GRAPH8_DATA = new HashMap<String, Integer>(){{
        this.put("Por Segundos", 105);
        this.put("Por Minutos", 24);
        this.put("Por Horas", 1);
        }
    };

    //"IPS MAIS ACESSANTES"
    public static final Map<String, Integer> GRAPH9_DATA = new HashMap<String, Integer>(){{
        this.put("127.0.0.1", 130);
        }
    };

    //"NOTA QUE MERECEMOS"
    public static final Map<String, Integer> GRAPH10_DATA = new HashMap<String, Integer>(){{
        this.put("Leonardo", 100);
        this.put("Kaio", 100);
        this.put("Pedro", 100);
        this.put("Rafaela", 100);
        this.put("Bruno", 100);
    }
    };
}
