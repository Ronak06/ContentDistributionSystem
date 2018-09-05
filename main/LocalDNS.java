import java.io.*;
import java.net.*;
import java.util.*;
public class LocalDNS {
    public static ArrayList<Record> records = new ArrayList<Record>();

    public static String vidCinema;
	public static String getIP;

    public LocalDNS(String authoCinema){

    }

    public static String start(String authoCinema) throws IOException {
        String newCinema="", value="";
        vidCinema = authoCinema;
		 String modifiedSentence="";
        int x = vidCinema.indexOf(".")+1;


        newCinema = vidCinema.substring(x);

        String ip="";
        for(int i = 0; i<records.size(); i++)
        {
            if(records.get(i).type.equals("NS")){
                if(newCinema.equals(records.get(i).name.toLowerCase())){
                    value = records.get(i).value;
                }
            }
        }

        for(int i = 0; i<records.size(); i++)
        {
            if(records.get(i).type.equals("A")){
                if(value.equals(records.get(i).name))
                    ip = records.get(i).value;
            }
        }
		try{
			
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("141.117.232.67");
		
        byte[] sendData = new byte[19];
        byte[] receiveData = new byte[10];

        sendData = vidCinema.getBytes();

        DatagramPacket sendPacket =
        new DatagramPacket(sendData, sendData.length, IPAddress, 40364);
		
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        clientSocket.receive(receivePacket);
         modifiedSentence =
        new String(receivePacket.getData());
		
        System.out.println("FROM HisCinema SERVER:" + modifiedSentence);
        clientSocket.close();

		
            //------HERCDNIP------
            getIP = findHerCDNIP(modifiedSentence);
			return getIP;
		}catch (Exception e){
			System.exit(0);
		}
        return null;
    }

    public static String findHerCDNIP(String modifiedSentence) throws IOException{
        String value="", newCinema="";
        String ip="";

        for(int i = 0; i<records.size(); i++)
        {
            if(records.get(i).type.equals("CN")){
                if(modifiedSentence.equals(records.get(i).name)){
                    value = records.get(i).value;
                }
            }
        }

        for(int i = 0; i<records.size(); i++)
        {
            if(records.get(i).type.equals("A")){
                if(value.equals(records.get(i).name))
                    ip = records.get(i).value;
            }
        }
		System.out.println("IP IS: " + ip);
        return ip;
    }

    public static void main(String []args) throws IOException{
        DatagramSocket serverSocket = new DatagramSocket(40361);
		records.add(new Record("hisCinema.com", "NShiscinema.com", "NS"));
        records.add(new Record("NShiscinema.com", "192.168.0.1", "A"));
        records.add(new Record("NSherCDN.com", "192.168.0.2", "A"));
        records.add(new Record("herCDN.com", "NSherCDN.com", "NS"));
        records.add(new Record("herCDN.com", "www.herCDN.com", "CN"));
        records.add(new Record("www.herCDN.com", "141.117.232.66", "A"));
        byte[] receiveData = new byte[19];
        byte[] sendData = new byte[15];

        while(true) {
		
            DatagramPacket receivePacket =
            new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(receivePacket);

            String sentence = new String(receivePacket.getData());
            System.out.println("Received from Client: " + sentence);

            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();

            //RUN START METHOD
			String temp = "";

             temp = start(sentence); 

            sendData = temp.getBytes();

            DatagramPacket sendPacket =
            new DatagramPacket(sendData, sendData.length, IPAddress, port);

            serverSocket.send(sendPacket);
        }
    }
}
