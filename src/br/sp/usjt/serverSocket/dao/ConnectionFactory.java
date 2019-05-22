package br.sp.usjt.serverSocket.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

	//
	// Classe principal de conex�o para todas outras classes.
	//
public class ConnectionFactory {

	//A classe que faz a interface entre o MySQL e o JAVA SE
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	//O Banco de Dados da Aplicacacao
		private static final String DATABASE = "serven";
	//Onde o banco de dados se encontra (servidor de banco de dados)
	private static final String URL = "jdbc:mysql://localhost:3311/" + DATABASE;
	private static final String LOGIN = "root";
	private static final String SENHA = "root";
	private static Connection connection = null;

	
	//CONSTRUTOR
	public ConnectionFactory() {
	
	}
	
	//Cria um metodo est�tico chamado getConnection que
	//retorna uma Connection
	//Esse metodo realiza conex�o com o MySQL(SGBD)
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		System.out.println("Abriu Conexao");
		//Recupera a classe de conex�o ao SGBD
		Class.forName(DRIVER);
		//Faz uma conexao cao banco de daods utilizando como parametros
		//Um endereco de servidor, um login e uma senha,
		connection = DriverManager.getConnection(URL,LOGIN,SENHA);
		System.out.println("Conexao aberta");
		return connection;
		
		}
		
		//Fecha uma conex�o com o banco de dados
		public static void closeConnection() throws SQLException {
			connection.close();
		}

	
	}
	