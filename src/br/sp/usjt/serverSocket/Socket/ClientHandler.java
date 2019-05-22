package br.sp.usjt.serverSocket.Socket;

import java.io.*;
import java.net.Socket;

import br.sp.usjt.serverSocket.Model.RelatorioGenerate;
import br.sp.usjt.serverSocket.Model.httpRequest;
import br.sp.usjt.serverSocket.Model.httpResponse;
import br.sp.usjt.serverSocket.Utils.ServerConfig;
import br.sp.usjt.serverSocket.dao.httpResponseDAO;


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

            if (requisicao.getMethod().equals("GET")) {



                if (requisicao.getPathFile().endsWith("/")) {
                    response = new httpResponse(ServerConfig.DEFAULT_FILE, 200);
                }else if(requisicao.getPathFile().endsWith("/relatorios.html")){

                    RelatorioGenerate gen = new RelatorioGenerate();

                    response = new httpResponse("relatorios.html", 200, gen.mount("Relatorio" , httpresponseDAO.countHttpCodes()));
                    response.setFile(response.getFile());

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
                    }

                        response = new httpResponse(ServerConfig.FILE_NOT_FOUND, 404);
                        response.send(out, dataOut);

                        if (debug) {
                            System.out.println("File " + requisicao.getPathFile() + " not found");
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
