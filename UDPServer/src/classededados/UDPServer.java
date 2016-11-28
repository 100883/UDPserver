/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classededados;

/**
 *
 * @author Gui Freitas
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.swing.JOptionPane;

public class UDPServer {

    private static final int PORT = 9000; //A palavra chave "final" significa que a variavel é uma constante(não muda)
    private static DatagramSocket datagramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;

    public static void main(String[] args) {

        JOptionPane.showMessageDialog(null, "Abrindo porta...\n");
        try {
            datagramSocket = new DatagramSocket(PORT);

        } catch (SocketException sockEx) {
            JOptionPane.showMessageDialog(null, "Não foi possivel o acesso a porta!");
            System.exit(1);
        }
        handleClient();
    }

    private static void handleClient() {
        try {
            String messageIn, messageOut;
            int numMessages = 0;
            do {
                buffer = new byte[256];
                inPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(inPacket);
                InetAddress clientAddress = inPacket.getAddress();
                int clientPort = inPacket.getPort();
                messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println("Mensagem recebida.");
                numMessages++;
                messageOut = "Mensagem " + numMessages + ": " + messageIn;
                outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), clientAddress, clientPort);
                datagramSocket.send(outPacket);
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally // Se exception thrown, fecha a conexão.
        {
            System.out.println("\n* Fechando conexão... *");
            datagramSocket.close();
        }
    }

}
