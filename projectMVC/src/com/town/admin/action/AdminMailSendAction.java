package com.town.admin.action;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminMailSendAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : AdminMailSendAction_execute 호출 ");
		
		// 세션제어
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 한글 처리(생략)
		// 전달받은 정보 저장 + pageNum
		String sender = request.getParameter("sender");
		String receiver = request.getParameter("receiver");
		String subject= request.getParameter("subject");
		String content = request.getParameter("content");
		
		String pageNum = request.getParameter("pageNum");
		
		// 멀티 쓰레드를 이용한 메일 전송 (메일을 전송하는 동안 다른 동작을 할 수 있도록)
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					
					response.setContentType("text/html; charset=UTF-8");
					
					Properties properties = System.getProperties();
					properties.put("mail.smtp.starttls.enable", "true");
					properties.put("mail.smtp.host", "smtp.gmail.com");
					properties.put("mail.smtp.auth", "true");
					properties.put("mail.smtp.port", "587");	// gmail 포트
					Authenticator auth = new GoogleAuthentication();
					Session s = Session.getDefaultInstance(properties, auth);
					Message message = new MimeMessage(s);
					Address sender_address = new InternetAddress(sender);
					Address receiver_address = new InternetAddress(receiver);
					message.setHeader("content-type", "text/html; charset=UTF-8");
					message.setFrom(sender_address);
					message.addRecipient(Message.RecipientType.TO, receiver_address);
					message.setSubject(subject);
					message.setContent(content, "text/html; charset=UTF-8");
					message.setSentDate(new java.util.Date());
					Transport.send(message);
					
					System.out.println(" M : 메일 전송 완료 ");
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		forward.setPath("./AdminMemberList.ad?pageNum="+pageNum);
		forward.setRedirect(true);
		
		return forward;
	}

}
