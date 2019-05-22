package br.sp.usjt.serverSocket.Main;

import br.sp.usjt.serverSocket.Socket.Servidor;

public class Main {

	public static void main(String[] args) {


            //Instanciando servidor
			Servidor server = new Servidor();

            //Ligando servidor / Ligando a thread
			Thread serverThread = new Thread(server);
            serverThread.start();


            //Desligando servidor / desligando a thread
            try {
                serverThread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("pausando");
                server.pause();
            }



            //Religando servidor / religando a thread
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("retomando");
                serverThread = new Thread(server);
                serverThread.start();
            }


    }

}
