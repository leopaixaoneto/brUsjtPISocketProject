package br.sp.usjt.serverSocket.Main;

import br.sp.usjt.serverSocket.Socket.Servidor;
import br.sp.usjt.serverSocket.View.ServerFrame;

public class Main {

	public static void main(String[] args) {



            //Instanciando servidor
			Servidor server = new Servidor();

            ServerFrame frame = new ServerFrame(server);

    }

}
