import java.io.*;
import java.net.*;

public class HisCinema {
	private static FileInputStream fis;
    public static void main(String argv[]) throws Exception
    {
        
        ServerSocket welcomeSocket = new ServerSocket(40360);
		 
		String fileName = "index.txt";
		while(true){
			Socket connectionSocket = welcomeSocket.accept();
			
			DataOutputStream os =
            new DataOutputStream(connectionSocket.getOutputStream());
			
			 try {
					fis = new FileInputStream(fileName);
					os.writeBytes(fileName+"\n");
					os.writeInt(fis.available());
			           sendFile(fis,connectionSocket);
					os.close();
		             } catch (Exception e) {
			                e.printStackTrace();
							throw e; 
		             }
            System.out.println(fileName);
			
		}               
    }

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
}