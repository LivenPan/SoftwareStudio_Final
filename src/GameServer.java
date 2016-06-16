

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


import java.util.TimeZone;

public class GameServer extends JFrame{
	private static final long serialVersionUID = 1L;
	private ServerSocket serverSocket;
	private JTextArea textArea;

	public static int cnt = 0;
	private ConnectionThread client1;
	private ConnectionThread client2;
	public static int valClient = 0;
	//for server
	
	//parameter
	private int questionIndex;
	private String[] questionList;
	private boolean isSet = false;
	
	public GameServer(int portNum){
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
		
		this.questionIndex =  15;
		
		questionList = new String[16];
		questionList[0] = new String("C");
		questionList[1] = new String("A");
		questionList[2] = new String("C");
		questionList[3] = new String("B");
		questionList[4] = new String("C");
		questionList[5] = new String("B");
		questionList[6] = new String("C");
		questionList[7] = new String("D");
		questionList[8] = new String("D");
		questionList[9] = new String("D");
		questionList[10] = new String("C");
		questionList[11] = new String("B");
		questionList[12] = new String("B");
		questionList[13] = new String("C");
		questionList[14] = new String("B");
		questionList[15] = new String("D");
		
		setVisible(true);
	}
	
	public void runForever() throws InterruptedException{
		while(true){
		if(cnt==2){
			if(client1.isDie==true){
				client1.sendMessage("LOS");
				client2.sendMessage("WIN");
				client1.isFin = true;
				client2.isFin = true;
				cnt = 0;
				isSet = false;
			}
			if(client2.isDie==true){
				client1.sendMessage("WIN");
				client2.sendMessage("LOS");
				client1.isFin = true;
				client2.isFin = true;
				cnt = 0;
				isSet = false;
			}
		}
		if(cnt<2){
			try{
				String str = "Server is waiting......\n";
				this.textArea.append(str); //印出等待連線的資訊
				Socket connectionToClient = this.serverSocket.accept(); //接受client的連線
				//印出client的資訊
				//System.out.println("Get connection from client " + connectionToClient.getInetAddress() + ":" + connectionToClient.getPort());
				if(cnt == 0){	//最初尚未有人連線
					
					client1 = new ConnectionThread(connectionToClient); //new一個ConnectionThread給client1
					InetAddress IPAddr = connectionToClient.getInetAddress();//得到IPaddress
					printConnectMessage(IPAddr);//印出訊息
					cnt++;
					
					client1.start();
					client1.sleep(100);
					this.client1.sendMessage("WAI");//傳給client1訊息
					
				}
				else{	//已經有一人連線
					client2 = new ConnectionThread(connectionToClient);
					InetAddress IPAddr = connectionToClient.getInetAddress();
					printConnectMessage(IPAddr);
					cnt++;
					client2.start();
					client1.sleep(100);
					client2.sleep(100);
					client1.sendMessage("STA");
					client2.sendMessage("STA");
					
				}	
			}catch(BindException e){
				e.printStackTrace();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
		}
			else if(cnt==2){
				this.textArea.append("why");
				if(client1.getATK == true && client2.getATK==true && isSet == false){
					this.textArea.append("why");
					//System.out.println("why");
					client1.sendMessage("ATK"+String.valueOf(client2.atk));
					client2.sendMessage("ATK"+String.valueOf(client1.atk));	
					isSet = true;
				}else if(client1.getATK == true && client2.getATK==true && isSet == true){
					checkAns(client1.ans,client2.ans);
				}
			}
		}
		
	}
	
	public void checkAns(String ans1 , String ans2){
		//System.out.println(ans1+" "+ans2);
		if(ans1!=null){
			if(ans1.equals(questionList[questionIndex])==true){
				if(questionIndex==0){
					questionIndex = 15;
				}else{
					questionIndex--;
				}
				client1.sendMessage("COR");
				client2.sendMessage("INJ");
				
			}else{
				client1.sendMessage("WRO");
			}
			
			client1.ans = null;
			client2.ans = null;
		}
		
		else if(ans2!=null){
			if(ans2.equals(questionList[questionIndex])==true){
				if(questionIndex==0){
					questionIndex = 15;
				}else{
					questionIndex--;
				}
				client2.sendMessage("COR");
				client1.sendMessage("INJ");
				
			}else{
				client2.sendMessage("WRO");
			}
			
			client1.ans = null;
			client2.ans = null;
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
		
		private boolean getATK = false;
		private int atk;
		private boolean isFin = false;
		private boolean isDie = false;
		private String ans;
		
		private String line;
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
			while(isFin==false){
					
				try{
					Thread.sleep(100);
					line = this.reader.readLine(); //得到client1輸入的單字
					System.out.println(" I fuck u " + line);
					//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~");
					if(line != null){
						if(line.length()==3){
							
							if(line.equals("DIE")){
								isDie = true;
								System.out.println("GGGG");
							}
							if(line.equals("PLA")){
								System.out.println("123");
								sendMessage("QUE"+String.valueOf(questionIndex));
							}
						}else{
							if(line.substring(0,3).equals("ANS")){
								ans = line.substring(3,4);
							}
							if(line.substring(0,3).equals("REA")){
								atk = Integer.parseInt(line.substring(3,6));
						
								getATK = true;
								//System.out.println(getATK);
							}
							
						}
					}else{
						System.out.println("i AM NULL");
					}
					
				}
				catch(IOException e){
					e.printStackTrace();
				}catch (InterruptedException e) {
				}
			}
		}
		
		public void sendMessage(String message){
			this.writer.println(message);
			this.writer.flush();
		}
	}

	
	public static void main(String[] args) throws InterruptedException{
		
		GameServer server = new GameServer(8000);
		server.runForever();
	}
}
