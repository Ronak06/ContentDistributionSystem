import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.Desktop;
public class Client {

    public static int num = 0;
    public static String fileURL = "";
	public static String sendNum ="";
    public static String her = "www.herCDN.com/F";
	public static HttpResponse ok = new HttpResponse(200,"ok");
	public static HttpResponse br = new HttpResponse(400,"Bad Request");
	public static HttpResponse nf = new HttpResponse(404,"Not Found");
	public static HttpResponse se = new HttpResponse(500,"Internal Server Error");
	
    public static void herCDN(String modifiedSentence) throws IOException{
	
        int serverNum;
        String line, authoCinema;


		
		byte [] b = new byte[200020];
		Socket clientSocket = new Socket(modifiedSentence, 40365);
		DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
        System.out.println("Connected to herCDN.com web server");
		DataOutputStream outToServer =
		new DataOutputStream(clientSocket.getOutputStream());
		outToServer.writeBytes(sendNum + '\n');
        serverNum = inFromServer.readInt();
		//System.out.println("serverNum: "+serverNum);
        try {
                  saveFile(clientSocket,serverNum,"test.mp4");
				 
            } catch (IOException e) {
                  e.printStackTrace();
            }
				outToServer.close();
			 inFromServer.close();
		clientSocket.close();
       
    }

    public static void main(String[] args) throws IOException, NullPointerException {
        System.out.println("Welcome to our Content Distribution System (CDN)");
        String serverNum;
        String line, authoCinema;
		String modifiedSentence="";
		String newFile ="";
		
		byte [] b = new byte[20002];
		int count = 2;
		while ( count != 0)
		{
		Socket sr;
		try{
		 sr = new Socket("141.117.232.69", 40360);
		
        
		
       // BufferedReader inFromServer =
       // new BufferedReader(new InputStreamReader(sr.getInputStream()));
		
		//newFile = inFromServer.readLine();
		//System.out.println("newfile: " + newFile);
		
		System.out.println("Connected to hisCinema.com web server");
		DataInputStream inFromServer = new DataInputStream(sr.getInputStream());
		
		newFile = inFromServer.readLine();
		int num = inFromServer.readInt();
		try {
                  saveFile(sr,num,newFile);
				 
            } catch (IOException e) {
                  e.printStackTrace();
            }
		
		}catch (ConnectException e){
			System.out.println("STATUS CODE: "+ nf.getValue()+", RESPONSE: "+ nf.getMessage());
			System.exit(nf.getValue());
		}
		
        BufferedReader inFromUser =
        new BufferedReader(new InputStreamReader(System.in));	
			
        System.out.println("Enter a number (1-4): ");
        sendNum = inFromUser.readLine();
		
        num = Integer.parseInt(sendNum);
        if(!(num >= 1 && num <= 4)){
			System.out.println("STATUS CODE: "+ br.getValue()+", RESPONSE: "+ br.getMessage());
			System.exit(br.getValue());
        }
		System.out.println("STATUS CODE: "+ ok.getValue()+", RESPONSE: "+ ok.getMessage());
        File input = new File(newFile);
        Scanner file = new Scanner(input);
		
        while(file.hasNextLine()){
            String indexFile = file.nextLine();
			boolean contained = indexFile.contains(sendNum);
			
            if(contained)
            {
				fileURL = indexFile;
            }
        }

        int x = fileURL.indexOf("v");
        int y = fileURL.lastIndexOf("/");
		
        authoCinema = fileURL.substring(x, y);

        inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		try{
        DatagramSocket clientUDPSocket = new DatagramSocket();

        InetAddress IPAddress = InetAddress.getByName("localhost");

        byte[] sendData = new byte[19];
        byte[] receiveData = new byte[15];

        sendData = authoCinema.getBytes();
		
			//System.out.println("sldghgidfsgkhrl");
        DatagramPacket sendPacket =
        new DatagramPacket(sendData, sendData.length, IPAddress, 40361);
		
        clientUDPSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        clientUDPSocket.receive(receivePacket);


        modifiedSentence =
        new String(receivePacket.getData());

        System.out.println("FROM localDNS SERVER:" + modifiedSentence);
        }catch (ConnectException e){
			System.out.println("STATUS CODE: "+ nf.getValue()+", RESPONSE: "+ nf.getMessage());
			System.exit(nf.getValue());
				}catch (SocketException f)
		{
			System.out.println("STATUS CODE: "+ se.getValue()+", RESPONSE: "+ se.getMessage());
			System.exit(se.getValue());
		}
		

		//------herCDN------
		try{
			herCDN(modifiedSentence);
		}catch (ConnectException e){
			
			System.out.println("STATUS CODE: "+ nf.getValue()+", RESPONSE: "+ nf.getMessage());
			System.exit(nf.getValue());
		}catch (SocketException f)
		{
			System.out.println("STATUS CODE: "+ se.getValue()+", RESPONSE: "+ se.getMessage());
			System.exit(se.getValue());
		}
		count --;
		System.out.println("STATUS CODE: "+ ok.getValue()+", RESPONSE: "+ ok.getMessage());
		}
			
    }
	
    private static void saveFile(Socket clientSock, int fileSize,String fileName) throws IOException {
		DataInputStream dis = new DataInputStream(clientSock.getInputStream());
		FileOutputStream fos = new FileOutputStream(fileName);
		byte[] buffer = new byte[4096];
		
		int filesize = (int)(Math.ceil(fileSize/buffer.length)+1)*buffer.length;		// Send file size in separate msg
		//System.out.println("fileSize: "+filesize);
		int read = 0;
		int totalRead = 0;
		int remaining = filesize;
		while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
			//System.out.println("beginning: " + remaining);
			totalRead += read;
			remaining -= read;
			//System.out.println("read " + totalRead + " bytes.");
			fos.write(buffer, 0, read);
			//System.out.println("ending: " + remaining);
		}
		System.out.println(fileName + " File downloaded.");
		if(fileName.contains(".mp4"))
			Desktop.getDesktop().open(new File("test.mp4")); 
		fos.close();
		dis.close();
	}

}
