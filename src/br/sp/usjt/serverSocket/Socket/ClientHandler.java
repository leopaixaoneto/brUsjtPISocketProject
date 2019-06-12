package br.sp.usjt.serverSocket.Socket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import br.sp.usjt.serverSocket.Model.Relatorio;
import br.sp.usjt.serverSocket.Model.RelatorioGenerate;
import br.sp.usjt.serverSocket.Model.httpRequest;
import br.sp.usjt.serverSocket.Model.httpResponse;
import br.sp.usjt.serverSocket.Utils.ServerConfig;
import br.sp.usjt.serverSocket.Utils.vars;
import br.sp.usjt.serverSocket.dao.httpResponseDAO;
import com.google.gson.JsonObject;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;


public class ClientHandler implements Runnable {

	
	private Socket connection;
	private boolean debug = true;

	private httpResponseDAO httpresponseDAO;

	
	public ClientHandler(Socket connection) {
		this.connection = connection;
		this.httpresponseDAO = new httpResponseDAO();

		//System.out.println("conectei");
		
	}
	
	public void close() {
		try {
			this.connection.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {


        //Iniciando Leitores
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;

        try {

            //in of Data
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            //out of string/text/data
            out = new PrintWriter(connection.getOutputStream());

            //data Out / files
            dataOut = new BufferedOutputStream(connection.getOutputStream());

            httpRequest requisicao = new httpRequest(in.readLine(), connection.getRemoteSocketAddress().toString().replace("/", ""));

            httpResponse response = null;

            boolean responseAlreadySended = false;

            JsonObject props = requisicao.getProps();

            if (requisicao.getMethod().equals("GET")) {



                if (requisicao.getPathFile().endsWith("/")) {
                    response = new httpResponse(ServerConfig.DEFAULT_FILE, 200);
                }else if(requisicao.getPathFile().endsWith("/relatorios.html")){

                    RelatorioGenerate gen = new RelatorioGenerate();

                    String relType = vars.RELATORIO_TYPE_BAR;

                    if(props != null){
                        if(props.has("type")){

                            System.out.println(props.get("type"));

                            if(props.get("type").toString().replace("\"", "").equals("") || props.get("type").toString().replace("\"", "").equals("bar")){
                                relType = vars.RELATORIO_TYPE_BAR;
                            }

                            if(props.get("type").toString().replace("\"", "").equals("line")){
                                relType = vars.RELATORIO_TYPE_LINE;
                            }

                            if(props.get("type").toString().replace("\"", "").equals("doughnut")){
                                relType = vars.RELATORIO_TYPE_DOUGHNUT;
                            }

                            if(props.get("type").toString().replace("\"", "").equals("area")){
                                relType = vars.RELATORIO_TYPE_AREA;
                            }

                            if(props.get("type").toString().replace("\"", "").equals("column")){
                                relType = vars.RELATORIO_TYPE_COLUMN;
                            }

                            gen.setSelected(props.get("type").toString().replace("\"", ""));
                        }
                    }else{
                        gen.setSelected("bar");
                    }


                    List<Relatorio> relatorioList = new ArrayList();

                    relatorioList.add(new Relatorio(httpresponseDAO.countHttpCodes(),"Relatorio HTTP Code Responses" , relType, "Relatório referente as recorrências de HTTP CODES nas respostas efetuadas aos usuários","Relatorio", "chartContainer1"));
                    relatorioList.add(new Relatorio(vars.GRAPH2_DATA,"Relatorio sobre as Horas mais acessadas" , relType, "Relatório referente as recorrências de acessos em horas expecificas","Relatorio", "chartContainer2"));
                    relatorioList.add(new Relatorio(vars.GRAPH3_DATA,"Relatorio sobre os Dias mais acessados" , relType, "Relatório referente as recorrências de acessos em dias expecificos","Relatorio", "chartContainer3"));
                    relatorioList.add(new Relatorio(vars.GRAPH4_DATA,"Relatorio sobre os Meses mais acessados" , relType, "Relatório referente as recorrências de acessos em meses expecificos","Relatorio", "chartContainer4"));
                    relatorioList.add(new Relatorio(vars.GRAPH5_DATA,"Relatorio sobre os Anos mais acessados" , relType, "\"Relatório referente as recorrências de acessos em anos expecificos\"","Relatorio", "chartContainer5"));
                    relatorioList.add(new Relatorio(vars.GRAPH6_DATA,"Relatorio sobre os Arquivos mais acessados" , relType, "Relatório referente as recorrências de acessos a arquivos expecificos","Relatorio", "chartContainer6"));
                    relatorioList.add(new Relatorio(vars.GRAPH7_DATA,"Relatorio sobre o tempo de resposta de cada acesso" , relType, "Relatório referente ao tempo decorrido para cada resposta","Relatorio", "chartContainer7"));
                    relatorioList.add(new Relatorio(vars.GRAPH8_DATA,"Relatorio sobre o Tempo gasto em cada acesso" , relType, "Relatório referente ao tempo gasto na aplicação por cada usuário","Relatorio", "chartContainer8"));
                    relatorioList.add(new Relatorio(vars.GRAPH9_DATA,"Relatorio sobre os IP's que mais acessão" , relType, "Relatório referente aos IP's de origem que mais acessam a aplicão","Relatorio", "chartContainer9"));
                    relatorioList.add(new Relatorio(vars.GRAPH10_DATA,"Relatorio sobre as notas merecidas pelos integrantes" , relType, "Relatório referente as futuras notas dos integrantes do grupo","Relatorio", "chartContainer10"));

                    gen.mount(relatorioList);

                    response = new httpResponse("relatorios.html", 200);


                }else{
                    response = new httpResponse((requisicao.getPathFile()), 200);
                }

                try{

                    response.send(out, dataOut);

                    if (debug) {
                        System.out.println("200 Ok : " + requisicao.getMethod() + " method.");
                    }


                }catch (FileNotFoundException ex) {

                        if (debug) {
                            System.out.println("404 File Not Found : " + requisicao.getMethod() + " method.");
                        }

                    if(response != null){

                        response.setHttpCode(404);
                        httpresponseDAO.save(response);
                        responseAlreadySended = true;

                        response = new httpResponse(ServerConfig.FILE_NOT_FOUND, 404);
                        //response.setFile(new File(requisicao.getPathFile()));

                        response.send(out, dataOut);
                    }
                }

            }else if (requisicao.getMethod().equals("POST")) {


            }else if (requisicao.getMethod().equals("HEAD")) {


            }else {

                if (debug) {
                    System.out.println("501 Not Implemented : " + requisicao.getMethod() + " method.");
                }


                response = new httpResponse(ServerConfig.METHOD_NOT_SUPPORTED, 501);
                response.send(out, dataOut);

            }

            if(response != null && !responseAlreadySended)httpresponseDAO.save(response);


        }catch (Exception Ex) {

        }finally {

		    try{
		        in.close();
                out.close();
                dataOut.close();
                connection.close();

            }catch(IOException ex){
                System.out.println("Error closing stream : " + ex.getMessage());
            }

            if(debug){
                System.out.println("Connection closed. \n");
            }

        }
			
	}


}
