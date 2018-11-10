package handler;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import main.INIT;

public class HTTPRequestHandler extends TimerTask{

	@Override
	public void run() {
		 try {
			INIT.server = new ServerSocket(80);
			  INIT.client =INIT.server.accept();
			  INIT.LogConsole("		");
			  INIT.LogConsole("Neue Connection >> "+INIT.client.getInetAddress()+":"+INIT.client.getPort());
			  
			  //txtarea.setText(String.valueOf(txtarea.getText()) + "\n" + "Ein Client wurde angenommen: IP: " +INIT.client.getInetAddress() + ":" +INIT.client.getPort());
	          final OutputStream out =INIT.client.getOutputStream();
	          final PrintWriter writer = new PrintWriter(out);
	          final InputStream in =INIT.client.getInputStream();
	         INIT.reader = new BufferedReader(new InputStreamReader(in));
	         
	          handler(INIT.client);
	          writer.close();
	         INIT.reader.close();
	         INIT.client.close();
	         INIT.server.close();
	        
	         
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		
	}

	public static void handler(Socket s) {
		String rsp = "";
		String msg;
		String file;
		boolean isImg = false;
		try {
			while ((msg = INIT.reader.readLine()) != null) {
				 StringTokenizer tk = new StringTokenizer(msg);
				    if(tk.hasMoreTokens() && tk.nextToken().equalsIgnoreCase("GET")) {
				    	file = tk.nextToken().replace("/", "\\");
				    	if(file.equals("\\favicon.ico")) {
				    		INIT.LogConsole("Favicon wurde geblockt!");
				    	}
				    	if(file.equals("\\")) {
					    	if(INIT.index.exists()) {
					    		final java.util.List<String> rsp2 = Files.readAllLines(INIT.index.toPath());
					            for (final String line : rsp2) { 
					                rsp +=line;
					            }
					    	}else {
					    		File folder = new File(INIT.pd);
					    		File[] listOfFiles = folder.listFiles();
					    		
					    		String ffles = "<html>"
					    				+ "<head>"
					    				+ "<title>Keine index.html</title>"
					    				+ "<style>"
					    				+ "body{"
					    				+ "background-color: yellow;"
					    				+ "}"
					    				+ "</style>"
					    				+ "</head>"
					    				+ "<body>"
					    				+ "<center></br><h1>Datein</h1></br><p>";
					    		for (File file2 : listOfFiles) {
					    		    if (file2.isFile()) {
					    		        ffles += "<a href=\""+file2.getName()+"\">"+file2.getName()+"</a></br>";
					    		    }
					    		}
					    		
					    		rsp = ffles+"</p><br><br></br><h6>RainbowsOfRainbowPages 1.0</h6></center></body></html>";
					    	}
				    	}else {
				    		File fg = new File(INIT.pd+file);
				    		boolean s1s = false;
				    		if(fg.exists()) {
				    			if(fg.getName().toLowerCase().contains(".png".toLowerCase())) {
				    				rsp = INIT.imgnotaccept;
				    			}else {
				    				final java.util.List<String> rsp2 = Files.readAllLines(fg.toPath());
						            for (final String line : rsp2) { 
						                rsp +=line;
						            }
				    			}
					            
					            s1s = true;
				    		}else {
				    			if(s1s == false) {
				    				rsp = INIT.notfoundpage;
				    			}else {
				    				
				    			}
				    		}
				    	}
				    	System.out.println(file);
				    	 final BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				            final PrintWriter out = new PrintWriter(s.getOutputStream(), true);
				            out.println("HTTP/1.0 200");
				            if(isImg == true) {
				            	out.println("Content-type: image/png");
				            }else {
				            	
				            }
				          //out.println("Content-type: text/html");
				           rsp = rsp.replace("{fdpath}", "pages"+file);
				           rsp = rsp.replace("{v}", "v1.0");
				           String type = "";
				           File f223 = new File(INIT.pd+file);
				           if(f223.isDirectory()) {
				        	   type="Ordner";
				           }else {
				        	   type="Datei";
				           }
				           rsp.replace("{fstype}", type);
				            //out.println("Server-name: gommehd");
				            out.println("Content-length: " + rsp.length());
				            out.println("");
				            out.println(rsp);
				            out.flush();
				            rsp = "";
				            
				    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
