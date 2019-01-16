/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bus_transit.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NelsonDelaTorre
 */

public class BusTransitServer extends Thread{
    private ServerSocket serverSocket;

    public BusTransitServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(1000);
    }
    
    public void run(){
        while(true){
            try {
                Socket server = serverSocket.accept();
                System.out.println("Connected to:"+server.getRemoteSocketAddress());
                
            } catch (IOException ex) {
                Logger.getLogger(BusTransitServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) throws Exception {

    }
    
    
    private static class clientHanlder{
        
    }
    
}
