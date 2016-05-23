package projet;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;
import java.net.SocketException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Entite {
	static String id;
 	static int port_tcp;
	static int port_udp;
	static	int port_udp_suivant;
	static	String myIA;
	String ip_multi;
	int port_multi;
	int a=1024;
	int b=9999;
	DatagramSocket ds;

	public static String myIA() throws SocketException{
		String s = null;
		Enumeration<NetworkInterface> listNi=NetworkInterface.getNetworkInterfaces();
	 	while(listNi.hasMoreElements()){
			NetworkInterface nic=listNi.nextElement();
			Enumeration<InetAddress> listIa=nic.getInetAddresses();
			while(listIa.hasMoreElements()){
	 			InetAddress iac=listIa.nextElement();
				if(iac instanceof Inet4Address && !iac.isLoopbackAddress())
					{ s=iac.toString();
					String tab[]=s.split("\\/");
					return tab[1];
				}
			}
	 	}
		return null;

	}

public int portTcp(String c){

for(int i=a;i<b+1;i++)
 {  try{
	Socket socket=new Socket(c,i);
		socket.close();
	  }
	catch(Exception e)
	{
		return i;
	}
}

return -1;
}

public int portUdp(){

for(int i=a;i<=b;i++)
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

public String remplirZero(int n,String s){
	String str="";

	for(int i=0;i<n-s.length();i++)
		str+="0";

		return str+s;
}

public String myId(int portTcp,String ip){

String tab[]=ip.split("\\.");
String s=remplirZero(3,tab[3]);

return Character.toString(tab[2].charAt(tab[2].length()-1))+s+Integer.toString(portTcp);
}


public Entite (String myIA,int port,int port_suivant) throws UnknownHostException, SocketException{
this.port_udp=port;
this.myIA=myIA;
this.port_tcp=portTcp(this.myIA);
this.id=myId(this.port_tcp,this.myIA);
this.port_udp_suivant=port_suivant;
ds=new DatagramSocket(port_udp);
}

public Entite () throws UnknownHostException, SocketException{
this.myIA=myIA();
this.port_tcp=portTcp(this.myIA);
this.port_udp=portUdp();
this.id=myId(this.port_tcp,this.myIA);
this.port_udp_suivant=port_udp;
ds=new DatagramSocket(port_udp);
}
/*
public void udp() throws InterruptedException, SocketException{

	ds=new DatagramSocket(port_udp);

	 t1=new Thread(new ComUdpE(port_udp,ds,this));
	 t2=new Thread(new ComUdpR(port_udp,ds,this));
	 t3=new Thread(new ComTcp(port_tcp,this));
	t1.start();
	t2.start();
	t3.start();
//	t1.join();
//	t2.join();
//	t3.join();


}
*/

/*public void maj(String myIA,int port_udp_suivant) throws InterruptedException{


this.print();
}*/


public static void print(){
	System.out.println("port_tcp :"+port_tcp);
	System.out.println("port_udp :"+port_udp);
	System.out.println("port_udp_suivant :"+port_udp_suivant);
	System.out.println("myIA :"+myIA);
	System.out.println("myId :"+id);

}




}
