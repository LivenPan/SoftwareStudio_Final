package server;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.text.SimpleDateFormat;

import javax.swing.*;

import java.util.Date;
import java.util.TimeZone;

public class ChatServer extends JFrame{
	private ServerSocket serverSocket;
	private JTextArea textArea;
	private JTextField textField;
	private Date now;
	public static int cnt = 0;
	private ConnectionThread client1, client2;
	public static int valClient = 0;
	
	public ChatServer(int portNum){
		//setLayout(null);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.textArea = new JTextArea();//創造server端資訊顯示的textArea
		this.textArea.setEditable(false);
		this.textArea.setPreferredSize(new Dimension(500,300));//textArea範圍
		JScrollPane scrollPane = new JScrollPane(this.textArea);
		
		//格式化
		SimpleDateFormat nowdate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		//==GMT標準時間往後加八小時
		nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		//==取得目前時間
		String sdate = nowdate.format(new java.util.Date());
		
	    this.add(scrollPane);
	    
		try{
			this.serverSocket = new ServerSocket(portNum);//創造一個serverSocket
			//System.out.printf("Server starts listening on port %d.\n", portNum);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	    this.pack();
	    this.textArea.append("Server start at " + sdate + "\n");
	    cnt = 0;
	    
		setVisible(true);
	}
	
	public void runForever(){
		String str = "Server is waiting......\n";
		this.textArea.append(str); //印出等待連線的資訊
		
		while(true){
			try{
				Socket connectionToClient = this.serverSocket.accept(); //接受client的連線
				//印出client的資訊
				//System.out.println("Get connection from client " + connectionToClient.getInetAddress() + ":" + connectionToClient.getPort());
				if(cnt == 0){	//最初尚未有人連線
					client1 = new ConnectionThread(connectionToClient); //new一個ConnectionThread給client1
					InetAddress IPAddr = connectionToClient.getInetAddress();//得到IPaddress
					printConnectMessage(IPAddr);//印出訊息
					++cnt;
					this.client1.sendMessage("One");//傳給client1訊息
					client1.start();
				}
				else{	//已經有一人連線
					client2 = new ConnectionThread(connectionToClient);
					InetAddress IPAddr = connectionToClient.getInetAddress();
					printConnectMessage(IPAddr);
					++cnt;
					this.client1.sendMessage("Start"); //傳給client1訊息說現在有2人連線
					this.client2.sendMessage("Start"); //傳給client2訊息說現在有2人連線
					client2.start();
				}	
			}catch(BindException e){
				e.printStackTrace();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void printConnectMessage(InetAddress IPAddr){	//在textArea印出連線的資訊
		String connect = new String("Server is connected!!\n");
		String message = IPAddr.toString();
		this.textArea.append(connect);
		
		if(cnt == 0){
			this.textArea.append("Player1's host name is ");
			this.textArea.append(message + "\n");
			this.textArea.append("Player1's IP address is ");
			this.textArea.append(message + "\n");
		}
		else{
			this.textArea.append("Player2's host name is ");
			this.textArea.append(message + "\n");
			this.textArea.append("Player2's IP address is ");
			this.textArea.append(message + "\n");
		}
		
		this.textArea.append("Server is waiting....");
	}
	
	public void broadCast(String message){
		client1.sendMessage(message);
		client2.sendMessage(message);
	}
	
	// Define an inner class (class name should be ConnectionThread)
	class ConnectionThread extends Thread{
		private Socket socket;
		private BufferedReader reader;
		private PrintWriter writer;
		
		public ConnectionThread(Socket socket) throws IOException{
			this.socket = socket;
			try {
				this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void run(){
			while(true){
				String client_fir = "JustTest", client_sec = "JustTest";
				try {
					client_fir = ChatServer.this.client1.reader.readLine(); //得到client1輸入的單字
					client_sec = ChatServer.this.client2.reader.readLine();	//得到client2輸入的單字
					
					if(client_fir.equals(client_sec) == true){
						ChatServer.this.broadCast("Equal"); //回傳給2個client相等的資訊
					}
					
					else if(client_fir.equals(client_sec) == false){
						ChatServer.this.broadCast("NotEqual"); //回完給2個client不相等的資訊
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(client_fir.equals(client_sec)){
					ChatServer.this.broadCast("True");
				}		
			}
		}
		
		public void sendMessage(String message){
			this.writer.println(message);
			this.writer.flush();
		}
	}

	
	public static void main(String[] args){
		
		ChatServer server = new ChatServer(8000);
		server.runForever();
	}
}
