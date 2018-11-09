package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;

import handler.HTTPRequestHandler;



public class INIT {
	
	//Folders
	static File folder;
	static File pages;
	static File config;
	public static File index;
	//File
	public static File port;
	public static File not_found;
	public static File imgNotaccept;
	//Strings
	public static String notfoundpage="";
	public static String imgnotaccept="";
	static String mf = "RainbowServer\\";
	static String cd = "RainbowServer\\configs\\";
	public static String pd = "RainbowServer\\pages\\";
	//Reader
	static FileReader freader;
	static BufferedReader rd;
	//ints
	//writer
	static FileWriter writer1;
	//
	public static PrintWriter writer;
	public static InputStream in;
    public static OutputStream out;
    public static ServerSocket server;
    public static Socket client;
    public static BufferedReader reader;
	static int pt;
	//Timer
	public static Timer t1;
	
	public static void LogConsole(String msg) {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println( sdf.format(cal.getTime()) +" : >> "+msg);
	}
	public static void main(String[] args) {
		t1 = new Timer();
		//Main folder
		index = new File(pd+"index.html");
		//Index
		folder = new File("RainbowServer");
		folder.mkdir();
		//Unter Folder
		pages = new File(mf+"pages");
		pages.mkdir();
		config = new File(mf+"configs");	
		config.mkdir();
		//Datein (config,pages)
		port = new File(cd+"port.cfg");
		not_found = new File(cd+"404.html");
		imgNotaccept = new File(cd+"PicNotAccept.html");
		LogConsole("Datein wurden geladen!         [1]");
		
		
		//File Create
		try {
			
			

			if(!not_found.exists()) {
			
			not_found.createNewFile();
			writer1 = new FileWriter(not_found);
			writer1.write("<html>"
					+ "<head>"
					+ "<title>404</title>"
					+ "</head>"
					+ "<body>"
					+ "<h1>404</h1>"
					+ "</body>"
					+ "</html>");
			writer1.flush();
			LogConsole("404 Datei wurde erstellt       [PATH=/configs/404.html]");
			}else {
				
			}
			if(!imgNotaccept.exists()) {
				imgNotaccept.createNewFile();
				FileWriter fw = new FileWriter(imgNotaccept);
				fw.write("<html>\r\n" + 
						"	<head>\r\n" + 
						"		<title>\r\n" + 
						"			ERROR\r\n" + 
						"		</title>\r\n" + 
						"	</head>\r\n" + 
						"	<body>\r\n" + 
						"		<center>\r\n" + 
						"			<div>\r\n" + 
						"				<p>Sorry,</br>IMAGES werden nicht akzeptiert da ein crash passieren kann!</p>\r\n" + 
						"			</div>\r\n" + 
						"		</center>\r\n" + 
						"	</body>\r\n" + 
						"</html>");
				fw.flush();
				LogConsole("ImgNotaccept wurde erstellt!");
			}else {
				
			}
			final java.util.List<String> rsp2 = Files.readAllLines(not_found.toPath());
    		
            for (final String line : rsp2) {
                notfoundpage += line;
            }
           LogConsole("404 Datei wurde geladen");
           final java.util.List<String> rsp22 = Files.readAllLines(imgNotaccept.toPath());
   		
           for (final String line2 : rsp22) {
               imgnotaccept += line2;
           }
          LogConsole("ImgNotaccept wurde geladen!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LogConsole("Server Port 80");
		LogConsole("Zeige Datein An...");
		String[] files = pages.list();
		Arrays.sort(files);
		LogConsole("Datein:");
		boolean isdatas = false;
		 for ( int i=0; i<files.length; i++ )
		    {
			 isdatas = true;
		      LogConsole("Datei oder Ordner: "+files[i]);
		    }
		 if(isdatas == false) {
			 LogConsole("Keine Datein vorhanden!");
		 }
		 LogConsole("Initialisiren wurde Abgeschlossen");
		 LogConsole("---------------------------------");
		 LogConsole("");
		 LogConsole("");
		 LogConsole("Warte auf Verbindung!");
		 t1.schedule(new HTTPRequestHandler(), 1,1);
		 
	}

}
