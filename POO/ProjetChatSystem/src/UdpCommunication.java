import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpCommunication
{	
	DatagramSocket dgramSocket;
	
	public UdpCommunication()
	{
		//création du socket 
		try
		{
			dgramSocket= new DatagramSocket(1234);
			dgramSocket.setSoTimeout(100);
		} catch (SocketException e)
		{
			e.printStackTrace();
		}
	}
	
	public Notification receiveDatagram()
	{
		byte[] buffer = new byte[256];
		DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
		try {
			this.dgramSocket.receive(inPacket);}
		catch (java.net.SocketTimeoutException e)
		{
			return new Notification();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		InetAddress clientAddress = inPacket.getAddress();
		int clientPort = inPacket.getPort();
		String message = new String(inPacket.getData(), 0, inPacket.getLength());
		return new Notification(message, clientPort, clientAddress);
	}
	
	public void sendDatagram(String message, int port, InetAddress host )
	{
		DatagramPacket outPacket= new DatagramPacket(message.getBytes(), message.length(), host , port);
		try
		{
			this.dgramSocket.send(outPacket);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
