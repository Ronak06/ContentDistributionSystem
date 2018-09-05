import java.io.*;
import java.net.*;
import java.util.Scanner;

public class HisCinemaDNS {

    public static void main(String argv[]) throws Exception
    {
        LocalDNS.records.add(new Record("video.hiscinema.com", "herCDN.com", "R"));
        String var="";

        DatagramSocket serverSocket = new DatagramSocket(40364);

        byte[] receiveData = new byte[19];
        byte[] sendData = new byte[10];

        while(true) {

            DatagramPacket receivePacket =
            new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(receivePacket);

            String sentence = new String(receivePacket.getData());
            System.out.println("Received from localDNS: " + sentence);

            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();

            for(int i = 0; i<LocalDNS.records.size(); i++)
            {

                if(LocalDNS.records.get(i).type.equals("R")){
                    if(sentence.equals(LocalDNS.records.get(i).name.toLowerCase())){
                       var = LocalDNS.records.get(i).value;
                    }
                }
            }
            sendData = var.getBytes();

            DatagramPacket sendPacket =
            new DatagramPacket(sendData, sendData.length, IPAddress, port);

            serverSocket.send(sendPacket);

        }
    }
}
