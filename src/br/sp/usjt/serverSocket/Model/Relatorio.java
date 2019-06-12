package br.sp.usjt.serverSocket.Model;

import java.util.Map;

public class Relatorio {

    private String finalCode;

    private Map<?, Integer> map;

    private String nomeRelatorio;

    private String relatorioType;

    private String boxText;

    private String tagText;

    private String containerText;


    public Relatorio(Map<?, Integer> map, String nomeRelatorio, String relatorioType, String boxText, String tagText, String containerText){
        this.map = map;
        this.nomeRelatorio = nomeRelatorio;
        this.relatorioType = relatorioType;
        this.boxText = boxText;
        this.tagText = tagText;
        this.finalCode = "";
        this.containerText = containerText;
        mount();
    }


    public void mount(){


        finalCode +=
                "<div class=\"col-md-6\">\n" +
                "                <a href=\"relatorios.html\" class=\"blog-entry element-animate\" data-animate-effect=\"fadeIn\">\n" +
                "                  <div class=\"blog-content-body\">\n" +
                "                    <div class=\"post-meta\">\n" +
                "                      <span class=\"category\">" + tagText + "</span>\n";

        finalCode +=(
                "                      <span class=\"mr-2\"><script>\n" +
                        "                                document.write(new Date().getDay() + \"/\");\n" +
                        "                                document.write(new Date().getMonth() + \"/\");\n" +
                        "                                document.write(new Date().getFullYear());\n" +
                        "                              </script></span> &bullet;\n" +
                        "                    </div>\n" +
                        "                    <h2>" + boxText + "</h2>\n" +
                        "    <div id=\"" + containerText + "\" style=\"height: 300px; width: 100%;\"></div>\n" +
                        "                  </div>\n" +
                        "                </a>\n" +
                        "            </div>\n");


        finalCode += ( "    <script type=\"text/javascript\">\n" +
                "      window.addEventListener('load', function() {\n" +
                "        var " + containerText + " = new CanvasJS.Chart(\""+ containerText +"\", {\n" +
                "          title: {\n" +
                "            text: \"" + nomeRelatorio + "\"\n" +
                "          },\n" +
                "          data: [\n" +
                "            {\n" +
                "              type: \"" + relatorioType + "\",\n" +
                "              dataPoints: [ \n");

        int cont = 1;

        for(Map.Entry<?, Integer> pair : map.entrySet()){
            if(cont < map.entrySet().size()) {
                finalCode += "{ label: \"" + pair.getKey() + "\", y: " + pair.getValue() + "},\n";
                cont++;
            }else{
                finalCode += "{ label: \"" + pair.getKey() + "\", y: " + pair.getValue() + "}\n";
            }
        }


        finalCode += ("]\n" +
                "            }\n" +
                "          ]\n" +
                "        });\n" +
                "        " + containerText + ".render();\n" +
                "});\n" +
                "    </script>");


        }



    public String getFinalCode() {
        return finalCode;
    }

    public void setFinalCode(String finalCode) {
        this.finalCode = finalCode;
    }

    public Map<?, Integer> getMap() {
        return map;
    }

    public void setMap(Map<?, Integer> map) {
        this.map = map;
    }

    public String getNomeRelatorio() {
        return nomeRelatorio;
    }

    public void setNomeRelatorio(String nomeRelatorio) {
        this.nomeRelatorio = nomeRelatorio;
    }

    public String getRelatorioType() {
        return relatorioType;
    }

    public void setRelatorioType(String relatorioType) {
        this.relatorioType = relatorioType;
    }

    public String getBoxText() {
        return boxText;
    }

    public void setBoxText(String boxText) {
        this.boxText = boxText;
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }
}

