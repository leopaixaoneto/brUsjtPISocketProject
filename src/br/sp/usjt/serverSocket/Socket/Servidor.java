package br.sp.usjt.serverSocket.Socket;

import br.sp.usjt.serverSocket.Utils.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public final class Servidor implements Runnable {
	
	private ServerSocket serverSocket;
	private boolean STOPPED = false;
	
	private List<Thread> clientConectionsThreads = new ArrayList<>();
	private List<ClientHandler> clientConnections = new ArrayList<>();
	
	public Servidor() {
		
	}
	
	public void start() {
		
		try {
		
			STOPPED = false;
			serverSocket = new ServerSocket(ServerConfig.PORT);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void pause() {
		try {
			
			STOPPED = true;
			
			//desliga as Threads
			//desliga as conex�es
			closeConnections();
			
			serverSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void closeConnections() {
		
		for(ClientHandler c : clientConnections) {
			c.close();
		}
		
		for(Thread t : clientConectionsThreads) {
			t.interrupt();
		}
	}
	
	
	@Override
	public void run() {
		
		start();
		
		while(!STOPPED) {
			try {
				if(serverSocket != null || serverSocket.isBound()){
					ClientHandler myClient = new ClientHandler(serverSocket.accept());
					clientConnections.add(myClient);

					Thread thread = new Thread(myClient);
					clientConectionsThreads.add(thread);

					thread.start();
				}
			} catch(SocketException ex){

            }catch (IOException e) {
				e.printStackTrace();
			}
			
			//Create Thread to handle this
		}
	}
	
	
	
	
}
