package projet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.util.regex.Pattern;

public class main {

	public static int portUdp(){

		for(int i=1024;i<=9999;i++)
		 {  try{

			 DatagramSocket dso=new DatagramSocket(i);
			 dso.close();
			 return i;
				}
			catch(Exception e)
			{
			}
		}
		return -1;
	}

	public static String myIA(String s){
		Pattern p = Pattern.compile("[/]");
	String[] items = p.split(s);
	return items[1];

	}

	/**
	 * @param args
	 * @throws UnknownHostException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws UnknownHostException, InterruptedException, SocketException {

		Entite entite=null;

		if (args.length==2){
		try{
		Socket socket=new Socket(args[0],Integer.parseInt(args[1]));
		BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

		String mess=br.readLine();
		System.out.println("j'ai recu :"+mess);

		Pattern p = Pattern.compile(" ");
		String[] items = p.split(mess);

		String ip=items[1];
		String port=items[2];

		int a=1024,b=9999;
		String c="localhost";

		String port_udp=Integer.toString(portUdp());
		String myIA=myIA(InetAddress.getLocalHost().toString());

		pw.print("WELC "+myIA+" "+port_udp+"\n");
		//pw.print("WELC \n");
		pw.flush();

		mess=br.readLine();
		System.out.println("j'ai recu :"+mess);


		pw.close();
		br.close();
		socket.close();

		entite =new Entite(ip,Integer.parseInt(port_udp),Integer.parseInt(port));

		}
		catch(Exception e){
	//	System.out.println(e);
	//	e.printStackTrace();
	 entite =new Entite();
		}
}

		else
			 entite =new Entite();

		//	entite.print();
		//	entite.udp();
			//entite.tcp();
		//	int a=0,b=0,c=0;
		//	while(a<10000);

		entite.print();

	Thread t1=new Thread(new ComUdpE(entite));
	Thread t2=new Thread(new ComUdpR(entite));
	Thread t3=new Thread(new ComTcp(entite));
	t1.start();
	t2.start();
	t3.start();
	t1.join();
	t2.join();
	t3.join();





	}

}