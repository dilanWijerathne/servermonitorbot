package javamailTuto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;


import java.util.Properties;
import java.util.Scanner;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SMTPAuthenticator implements Runnable {

	private static int str;
	private static List<String> strArrList;
	private static List<String> strArrEmailList;
	private static String tempUrlList;
	public static String [] backupEmailList;
	
	
	public static int exB = 0;
	
	public static Exception exB1;
	public static Exception exB2;
	public static Exception exB3;
	
	private static String sst;
	public static String arg;

	public SMTPAuthenticator(){
		
	}
	
	public static void main(String[] args) throws Exception {
		
		try {
			
			try{
				if(args.length ==0){
					arg = "run";
				}
				else if(args.equals("break")){
					arg = args[0];
				}
				else if(args[0].equals("run")){
					arg = args[0];
				}
			}
			catch(Exception ex){}
			

			SMTPAuthenticator sa = new SMTPAuthenticator();

			//adding servers url via txt file
			Scanner s = new Scanner(new File("url-input.txt"));
			//Scanner s = new Scanner(new File("C:/url/url-input.txt"));
			ArrayList<String> list = new ArrayList<String>();
			while (s.hasNext()){
				list.add(s.next());
			}
			strArrList = list;
			s.close();
			//---------------------------------------------------------//
			
			//adding email accounts which want to notify
			Scanner es = new Scanner(new File("Email-input.txt"));
			//Scanner es = new Scanner(new File("C:/url/Email-input.txt"));
			ArrayList<String> list2 = new ArrayList<String>();
			while (es.hasNext()){
				list2.add(es.next());
			}
			strArrEmailList = list2;
			es.close();
			
			String []emailList = new String[strArrEmailList.size()];
			strArrEmailList.toArray(emailList);
			backupEmailList = emailList;
			
			

			while (true) {
				
				{	
					if(arg.equals("break")){
					
					break;
					
					}
				}
				
				for(String temp : strArrList){
					tempUrlList = temp;
					sa.checkResponse(temp);
					
					if (str != 200) {
						
						
						for(int i = 0; i < emailList.length ; i++ ){
							
							if(exB == 0 ){
								sa.sendTemplateEmail(emailList[i]);
								//i = 0;
								//exB = 1; 
							}
							else if(exB ==1){
								backupEmail(emailList[i]);
								//i = 0;
								//exB =2;
							}
							else if(exB ==2){
								backupEmail_backup(emailList[i]);
								//i = 0;
								//exB =3;
							}
							else if(exB ==3){
								sendExEmail(emailList[i], exB3, "Anathurak__all__emails__usage_has_exceeded_");
								//i = 0;
							}
							
							
						}
						
					}
					else if(str == 200){
						
					}
				}
				
				
				
			    Thread.sleep(3600* 1000);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			for(String s : strArrEmailList){
				sendExEmail(s,ex,"__Main__Method__Anathurak");
			
			}
			sendEx(ex,"__Main__Method__Anathurak");
			
		}

	}


	/*
	public List setUrlList (){
		 
		List<String> url=new ArrayList<String>();
		  url.add("http://www.sequapay.com");
		  url.add("http://www.findmyfare.com");
		  url.add("http://www.sequapay.com/pay");
		  url.add("http://www.pre3.findmyfare.lk");
		
		  return url;
		
	}*/

	public void checkResponse(String strUrl) throws Exception {

		try {
			// URL url = new URL("http://www.sequapay.com/");
			URL url = new URL(strUrl);
			// URL url = new URL("https://payment.findmyfare.com/payconfirm");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			//connection.setRequestMethod("GET");
			connection.connect();

			int code = connection.getResponseCode();
			str = code;
			//System.out.println("Response code of the object is " + code + "  " + strUrl);
			//System.out.println("Response code of the object is " + code + "  " + tempUrlList);
			if (code == 200) {
				//System.out.println("OK");
			}
		} catch (Exception ex) {
			//System.out.println("Anathurak Anathurak!!");
			//System.out.println(" Web/Server " + strUrl + " :: Exception :: " + ex);
			//System.out.println("Anathurak Prathicharaye Anathurak!!");
			for(String s : strArrEmailList){
				sendExEmail(s,ex,"__checkResponse__Method__Anathurak");
			}
			sendEx(ex,"__checkResponse__Method__Anathurak");
			
		}
	}

	public void sendTemplateEmail(String emailId) throws Exception {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", 25);
		props.put("mail.smtp.socketFactory.port", 25);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol", "smtp");
		Session mailSession = null;

		mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				//return new PasswordAuthentication("fmf.test007@gmail.com", "123ananda");
				return new PasswordAuthentication("fmf.test007@gmail.com", "123ananda");
			}
		});

		try {

			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject("Server Response");
			message.setFrom(new InternetAddress("fmf.test007@gmail.com"));
			//message.setFrom(new InternetAddress("fmf.test007@gmail.com"));
			
			
			//String[] to = new String[] { "rajeew.dssc@gmail.com","rajeew.iit@gmail.com" };
			//String[] to = new String[] {emailId};
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[0]));
			/*message.addRecipients(Message.RecipientType.CC, 
                    InternetAddress.parse("abc@abc.com,abc@def.com,ghi@abc.com"));*/
			message.addRecipients(Message.RecipientType.TO, emailId);
			
			
			String body = "server response " + str + " error in  " + tempUrlList + " ";
			message.setContent(body, "text/html");
			transport.connect();

			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			
			
			//String[] to = new String[] { "rajeew.dssc@gmail.com","rajeew.iit@gmail.com" };
			//String[] to = strArrEmailList;
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[0]));
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[1]));
			//String body = "server response " + str + " error in  " + tempUrl + " ";
			
			
			
		} catch (Exception ex) {
			
			exB = 1;
			exB1 = ex;
			//exB1 = ex;
			//System.out.println("Anathurak Anathurak!!");
			//System.out.println("EmailFunction Exception ::" + ex);
			//System.out.println("Anathurak Widyuth Thapale Anathurak!!");
			
			
			/*for(String s : strArrEmailList){
				sendExEmail(s,ex,"___sendTemplateEmail___Method__Anathurak");
				
				
			}
			sendEx(ex,"___sendTemplateEmail___Method__Anathurak");
			for(int i = 0; i < backupEmailList.length ; i++ ){
				//sa.sendTemplateEmail(emailList[i]);
				backupEmail(backupEmailList[i]);
				
			}*/
			
			
			
			
		}
	}
	
	public static void backupEmail(String emailId) throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", 25);
		props.put("mail.smtp.socketFactory.port", 25);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol", "smtp");
		Session mailSession = null;

		mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("fmf007.test@gmail.com", "123ananda");
			}
		});

		try {

			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject("Server Response - backup 1");
			message.setFrom(new InternetAddress("fmf007.test@gmail.com"));
					message.addRecipients(Message.RecipientType.TO, emailId);

			String body = "server response " + str + " error in  " + tempUrlList + " ";
			message.setContent(body, "text/html");
			transport.connect();

			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
					
		} catch (Exception ex) {
			exB =2;
			exB2 = ex;
			for (String s : strArrEmailList) {
				sendExEmail(s, ex, "___sendTemplateEmail___Method__Anathurak");

			}
			sendEx(ex,"___sendTemplateEmail___Method__Anathurak");
			for(int i = 0; i < backupEmailList.length ; i++ ){
				backupEmail_backup(backupEmailList[i]);
			}
		}
	}

	public static void backupEmail_backup(String emailId) throws Exception {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", 25);
		props.put("mail.smtp.socketFactory.port", 25);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol", "smtp");
		Session mailSession = null;

		mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				//return new PasswordAuthentication("fmf.test007@gmail.com", "123ananda");
				return new PasswordAuthentication("rajeew.dssc@yahoo.com", "irajeewceobitch0625");
			}
		});

		try {

			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject("Server Response - backup 2");
			message.setFrom(new InternetAddress("rajeew.dssc@yahoo.com"));
			message.addRecipients(Message.RecipientType.TO, emailId);
			
			String body = "server response " + str + " error in  " + tempUrlList + " ";
			message.setContent(body, "text/html");
			transport.connect();

			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
				
		} catch (Exception ex) {
			exB =3;
			exB3 = ex;
			
			for(String s : strArrEmailList){
				sendExEmail(s,ex,"___sendTemplateEmail___Method__Anathurak ::  Daily user sending quota exceeded");
				
				
			}
			
			sendEx(ex,"___sendTemplateEmail___Method__Anathurak");
			for(int i = 0; i < backupEmailList.length ; i++ ){
				//sa.sendTemplateEmail(emailList[i]);
				backupEmail(backupEmailList[i]);
				
			}
			
			
			
		}
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	
	public static void sendExEmail(String emailId,Exception exception2, String subEx) throws Exception {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", 25);
		props.put("mail.smtp.socketFactory.port", 25);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol", "smtp");
		Session mailSession = null;

		mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("fmf007.test@gmail.com", "123ananda");
			}
		});

		try {

			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject("This is about BOT EXCEPTION " + subEx);
			message.setFrom(new InternetAddress("fmf007.test@gmail.com"));
			
			message.addRecipients(Message.RecipientType.TO, emailId);
			
			
			String body =  subEx +"server response " + exception2 + " error in  " + subEx + " 		";
			message.setContent(body, "text/html");
			transport.connect();

			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			
			
		} catch (Exception ex) {
			
			for (String s : strArrEmailList) {
				sendExEmail(s, ex, "___sendTemplateEmail___Method__Anathurak");

			}
		}
	}

	
	public static void sendEx(Exception exception2, String subEx) throws Exception {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", 25);
		props.put("mail.smtp.socketFactory.port", 25);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol", "smtp");
		Session mailSession = null;

		mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("rajeew.dssc@yahoo.com", "irajeewceobitch0625");
			}
		});

		try {

			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject("This is about BOT EXCEPTION " + subEx);
			message.setFrom(new InternetAddress("rajeew.dssc@yahoo.com"));
			
			
			String[] to = new String[] { "rajeew.dssc@gmail.com","rajeew.iit@gmail.com" };
			//String[] to = new String[] {emailId};
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[0]));
			
			String body =  subEx +"BOT EKE AnAtHuRaK ANATHURAK.... " + exception2 + " ANATHURAK in  " + subEx + " 		";
			message.setContent(body, "text/html");
			transport.connect();

			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			
			
		} catch (Exception exception) {
			
			/*System.out.println("Anathurak Anathurak!!");
			System.out.println("EmailFunction Exception ::" + exception);
			System.out.println("Anathurak Widyuth Thapale Anathurak!!");*/
		}
	}
	

}
