import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpCommunication
{	DatagramSocket dgramSocket;
	
	UdpCommunication(){
	//création du socket 
	dgramSocket= new DatagramSocket(1234);
	dgramSocket.setSoTimeout(100);
	}
	
	public Notification receiveDatagram() {
		byte[] buffer = new byte[256];
		DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
		try {
		this.dgramSocket.receive(inPacket);}
		catch (java.net.SocketTimeoutException e)
		{
			return new Notification();
		}
		InetAddress clientAddress = inPacket.getAddress();
		int clientPort = inPacket.getPort();
		String message = new String(inPacket.getData(), 0, inPacket.getLength());
		return new Notification(message, clientPort, clientAddress);
	}
	
	public void sendDatagram(String message, int port, InetAddress host ) {
		DatagramPacket outPacket= new DatagramPacket(message.getBytes(), message.length(), host , port);
		this.dgramSocket.send(outPacket);
	}
		
	
	
}
