package br.sp.usjt.serverSocket.View;

import br.sp.usjt.serverSocket.Socket.Servidor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerFrame extends JFrame {

    private Thread serverThread;
    private Servidor server;

    private Button iniciar,pausar;

    public ServerFrame(Servidor server){
        this.server = server;

        this.setTitle("Servidor");
        this.setLocationRelativeTo(null);

        this.setSize(200,100);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridLayout(1, 2));

        iniciar = new Button();
        iniciar.setLabel("Iniciar");
        iniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ligar();
                iniciar.setEnabled(false);
                pausar.setEnabled(true);
            }
        });

        pausar = new Button();
        pausar.setLabel("Pausar");
        pausar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausar.setEnabled(false);
                iniciar.setEnabled(true);
                desligar();
            }
        });

        pausar.setEnabled(false);

        this.add(iniciar);
        this.add(pausar);

        this.setVisible(true);
    }

    private void ligar(){

        //Religando servidor / religando a thread
        System.out.println("retomando");
        serverThread = new Thread(server);
        serverThread.start();


    }

    private void desligar(){
        //Desligando servidor / desligando a thread
        System.out.println("pausando");
        server.pause();
    }
}
