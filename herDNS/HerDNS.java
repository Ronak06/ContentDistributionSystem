import java.io.*;
import java.net.*;

public class HerDNS {
	private static FileInputStream fis;
    public static void sendFile(FileInputStream fis,Socket s) throws IOException {
  		DataOutputStream dos = new DataOutputStream(s.getOutputStream());		
		System.out.println(fis.available());
  		byte[] buffer = new byte[4096];

  		while (fis.read(buffer) > 0) {
  			dos.write(buffer);
  		}

  		fis.close();
  		dos.close();
  	}
	
    public static void main(String []args) throws IOException {
		String clientSentence;
		String capitalizedSentence;
		String fileName = "1.mp4";

		ServerSocket welcomeSocket = new ServerSocket(40365);

		while(true) 
		{

			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient =
			new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			fileName = (inFromClient.readLine()+".mp4");
			System.out.println("Recieved: " +fileName);
            DataOutputStream outToClient =
            new DataOutputStream(connectionSocket.getOutputStream());
			
            //outToClient.writeBytes(fileName + "\n");
            try {
					fis = new FileInputStream(fileName);
					outToClient.writeInt(fis.available());
			           sendFile(fis,connectionSocket);
					outToClient.close();
		             } catch (Exception e) {
			                e.printStackTrace();
							throw e;
		             }
            System.out.println(fileName);
            
		}
		 
    }
}          