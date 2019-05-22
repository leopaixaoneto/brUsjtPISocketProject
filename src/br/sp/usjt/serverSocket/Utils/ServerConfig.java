package br.sp.usjt.serverSocket.Utils;

import java.io.File;

public class ServerConfig {

    //Variaveis globais para o server
	public static final File WEB_ROOT = new File(".");
	public static final String DEFAULT_FILE = "index.html";
	public static final String FILE_NOT_FOUND = "404.html";
	public static final String METHOD_NOT_SUPPORTED = "not_supported.html";
	public static final File RELATORIOS_ROOT = new File("relatorios.html");

	public static final String SERVER_NAME = "Java HTTP Sever - Auth: Paixao";
	public static final String SERVER_VERSION = "1.0";

	//Porta do Servidor
	public static final int PORT = 8080;
	
}
