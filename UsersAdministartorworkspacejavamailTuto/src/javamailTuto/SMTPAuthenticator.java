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
	//private static String[] strArr;
	//private static String tempUrl;
	private static List<String> strArrList;
	private static List<String> strArrEmailList;
	private static String tempUrlList;
	
	private static String sst;

	public SMTPAuthenticator(){
		
	}
	
	public static void main(String[] args) throws Exception {
		
		try {
			
			
			
			//String subEx = "Main";

			SMTPAuthenticator sa = new SMTPAuthenticator();

			//adding servers url via txt file
			Scanner s = new Scanner(new File("C:/url/url-input.txt"));
			ArrayList<String> list = new ArrayList<String>();
			while (s.hasNext()){
				list.add(s.next());
			}
			strArrList = list;
			s.close();
			//---------------------------------------------------------//
			
			//adding email accounts which want to notify
			Scanner es = new Scanner(new File("C:/url/Email-input.txt"));
			ArrayList<String> list2 = new ArrayList<String>();
			while (es.hasNext()){
				list2.add(es.next());
			}
			strArrEmailList = list2;
			es.close();
			
			String []emailList = new String[strArrEmailList.size()];
			strArrEmailList.toArray(emailList);
			
			
			//strArrList = sa.setUrlList();
			// System.out.println(sa.setUrl().length);

			while (true) {
				
				{	
					if(args[0].equals("break")){
					String c = args[0];
					break;
					
					}
				}
				
				for(String temp : strArrList){
					tempUrlList = temp;
					sa.checkResponse(temp);
					
					if (str != 200) {
						
						/*for(String em : strArrEmailList){
							sa.sendTemplateEmail(em);
						}*/
						/*for (int i = 0; i < strArrEmailList.size(); i++) {
							sa.sendTemplateEmail(strArrEmailList.get(i));
						}*/
						for(int i = 0; i < emailList.length ; i++ ){
							sa.sendTemplateEmail(emailList[i]);
							//System.out.println(emailList[i]);
						}
						//Local address contains illegal character in string 
					}
					else if(str == 200){
						System.out.println(" Up and running finely ");
					}
				}
				
				
				
			    Thread.sleep(10* 1000);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			for(String s : strArrEmailList){
				sendExEmail(s,ex,"__Main__Method__Anathurak");
				//System.out.println(emailList[i]);
			}
			
			System.out.println("Anathurak Anathurak!!");
			System.out.println("Pradhana Kramaye Anathurak!!");
			//sendExEmail("",e,"__Main__Method__Anathurak");
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
			System.out.println("Response code of the object is " + code + "  " + tempUrlList);
			if (code == 200) {
				System.out.println("OK");
			}
		} catch (Exception ex) {
			System.out.println("Anathurak Anathurak!!");
			System.out.println(" Web/Server " + strUrl + " :: Exception :: " + ex);
			System.out.println("Anathurak Prathicharaye Anathurak!!");
			for(String s : strArrEmailList){
				sendExEmail(s,ex,"__checkResponse__Method__Anathurak");
			}
			
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
				return new PasswordAuthentication("fmf.test007@gmail.com", "123ananda");
			}
		});

		try {

			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject("Server Response");
			message.setFrom(new InternetAddress("fmf.test007@gmail.com"));
			
			
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
			System.out.println("Anathurak Anathurak!!");
			System.out.println("EmailFunction Exception ::" + ex);
			System.out.println("Anathurak Widyuth Thapale Anathurak!!");
			for(String s : strArrEmailList){
				sendExEmail(s,ex,"___sendTemplateEmail___Method__Anathurak");
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
				return new PasswordAuthentication("fmf.test007@gmail.com", "123ananda");
			}
		});

		try {

			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);

			message.setSubject("This is about BOT EXCEPTION " + subEx);
			message.setFrom(new InternetAddress("fmf.test007@gmail.com"));
			
			
			//String[] to = new String[] { "rajeew.dssc@gmail.com","rajeew.iit@gmail.com" };
			//String[] to = new String[] {emailId};
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[0]));
			/*message.addRecipients(Message.RecipientType.CC, 
                    InternetAddress.parse("abc@abc.com,abc@def.com,ghi@abc.com"));*/
			message.addRecipients(Message.RecipientType.TO, emailId);
			
			
			String body =  subEx +"server response " + exception2 + " error in  " + subEx + " 		";
			message.setContent(body, "text/html");
			transport.connect();

			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			
			
			//String[] to = new String[] { "rajeew.dssc@gmail.com","rajeew.iit@gmail.com" };
			//String[] to = strArrEmailList;
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[0]));
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[1]));
			//String body = "server response " + str + " error in  " + tempUrl + " ";
			
			
			
		} catch (Exception exception) {
			System.out.println("Anathurak Anathurak!!");
			System.out.println("EmailFunction Exception ::" + exception);
			System.out.println("Anathurak Widyuth Thapale Anathurak!!");
		}
	}


}
