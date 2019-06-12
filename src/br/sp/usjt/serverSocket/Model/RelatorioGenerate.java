package br.sp.usjt.serverSocket.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RelatorioGenerate {

    String selected = "";

    public RelatorioGenerate(){

    }

    public File mount(List<Relatorio> relatorios){

        System.out.println("SELECTED = " + selected);

        String cabecalho ="<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <title>Colorlib Balita &mdash; Minimal Blog Template</title>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta\n" +
                "      name=\"viewport\"\n" +
                "      content=\"width=device-width, initial-scale=1, shrink-to-fit=no\"\n" +
                "    />\n" +
                "\n" +
                "    <link\n" +
                "      href=\"https://fonts.googleapis.com/css?family=Josefin+Sans:300, 400,700\"\n" +
                "      rel=\"stylesheet\"\n" +
                "    />\n" +
                "\n" +
                "    <link rel=\"stylesheet\" href=\"css/bootstrap.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/animate.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/owl.carousel.min.css\">\n" +
                "\n" +
                "    <link rel=\"stylesheet\" href=\"fonts/ionicons/css/ionicons.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"fonts/fontawesome/css/font-awesome.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"fonts/flaticon/font/flaticon.css\">\n" +
                "\n" +
                "    <!-- Theme Style -->\n" +
                "    <link rel=\"stylesheet\" href=\"http://localhost:8080/css/style.css\">\n" +
                "\n" +
                "  </head>\n" +
                "  <body selectedIndex = \"" + selected +"\">\n" +
                "    <header role=\"banner\">\n" +
                "      <div class=\"top-bar\">\n" +
                "        <div class=\"container\"></div>\n" +
                "      </div>\n" +
                "\n" +
                "      <div class=\"container logo-wrap\">\n" +
                "        <div class=\"row pt-5\">\n" +
                "          <div class=\"col-12 text-center\">\n" +
                "            <h1 class=\"site-logo\"><a href=\"relatorios.html\">RELATÓRIOS</a></h1>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </header>\n" +
                "    <!-- END header -->\n" +
                "\n" +
                "    <section class=\"site-section py-sm\">\n" +
                "      <div class=\"container\" id=\"mainDiv\">\n" +
                "        <div class=\"row\">\n" +
                "          <div class=\"col-md-6\">\n" +
                "            <h2 class=\"mb-4\">Relatórios</h2>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "        <div class=\"row blog-entries\">\n" +
                "          <div class=\"main-content\">\n" +
                "            <div class=\"row\">\n" +
                "              <!-- INSERIR AQUI OS GRÁFICOS -->";


        String footer = "\n" +
                "              <!-- FIM GRÁFICOS-->\n" +
                "            </div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </section>\n" +
                "\n" +
                "    <footer class=\"site-footer\">\n" +
                "      <div class=\"container\">\n" +
                "        <div class=\"row mb-5\">\n" +
                "          <div class=\"col-md-4\">\n" +
                "            <h3>Créditos</h3>\n" +
                "            <p>\n" +
                "              <img\n" +
                "                src=\"images/sjtLogo.png\"\n" +
                "                alt=\"Image placeholder\"\n" +
                "                class=\"img-fluid\"\n" +
                "              />\n" +
                "            </p>\n" +
                "\n" +
                "            <p>\n" +
                "              Integrantes: <br />\n" +
                "              Leonardo Paixão Viana - 8162258712 <br />\n" +
                "              Kaio Vinicius Diniz - 816118179 <br />\n" +
                "              Pedro Augusto Cabral Rocha - 81614082 <br />\n" +
                "              Rafaela Santos Pacheco - 201506209 <br />\n" +
                "              Bruno Felix - 81610370 <br />\n" +
                "            </p>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "        <div class=\"row\">\n" +
                "          <div class=\"col-md-12\">\n" +
                "            <script>\n" +
                "              document.write(new Date().getFullYear());\n" +
                "            </script>\n" +
                "            Trabalho Projeto Interdisciplinar | Trabalho feito com o\n" +
                "            <i class=\"fa fa-heart-o\" aria-hidden=\"true\"></i> para faculdade\n" +
                "            <a href=\"https://www.usjt.br/\" target=\"_blank\">São Judas!</a>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </footer>\n" +
                "    <!-- END footer -->\n" +
                "\n" +
                "    <!-- loader -->\n" +
                "    <div id=\"loader\" class=\"show fullscreen\">\n" +
                "      <svg class=\"circular\" width=\"48px\" height=\"48px\">\n" +
                "        <circle\n" +
                "          class=\"path-bg\"\n" +
                "          cx=\"24\"\n" +
                "          cy=\"24\"\n" +
                "          r=\"22\"\n" +
                "          fill=\"none\"\n" +
                "          stroke-width=\"4\"\n" +
                "          stroke=\"#eeeeee\"\n" +
                "        />\n" +
                "        <circle\n" +
                "          class=\"path\"\n" +
                "          cx=\"24\"\n" +
                "          cy=\"24\"\n" +
                "          r=\"22\"\n" +
                "          fill=\"none\"\n" +
                "          stroke-width=\"4\"\n" +
                "          stroke-miterlimit=\"10\"\n" +
                "          stroke=\"#f4b214\"\n" +
                "        />\n" +
                "      </svg>\n" +
                "    </div>\n" +
                "\n" +
                "    <script src=\"js/jquery-3.2.1.min.js\"></script>\n" +
                "    <script src=\"js/popper.min.js\"></script>\n" +
                "    <script src=\"js/bootstrap.min.js\"></script>\n" +
                "\n" +
                "    <script src=\"http://localhost:8080/js/jquery-migrate-3.0.0.js\"></script>\n" +
                "    <script src=\"http://localhost:8080/js/owl.carousel.min.js\"></script>\n" +
                "    <script src=\"http://localhost:8080/js/jquery.waypoints.min.js\"></script>\n" +
                "    <script src=\"http://localhost:8080/js/jquery.stellar.min.js\"></script>\n" +
                "    <script src=\"http://localhost:8080/js/main.js\"></script>\n" +
                "    <script src=\"https://canvasjs.com/assets/script/canvasjs.min.js\"></script>\n" +
                "    <script src=\"js/relatorios.js\"> </script>  \n"+
                "  </body>\n" +
                "</html>\n";


        StringBuffer inputBuffer = new StringBuffer();

        File result = null;

        inputBuffer.append(cabecalho);

        for(Relatorio r : relatorios){
            if(r != null) {
                    System.out.println("IMPRIMIDO: " + r.getFinalCode());
                    inputBuffer.append(r.getFinalCode());
            }

        }


        inputBuffer.append(footer);

        FileOutputStream fileOut = null;

        try {
            result = new File("Relatorios.html");

            fileOut = new FileOutputStream(result);
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException ex){

        }

        return result;

    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

}
