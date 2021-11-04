package com.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.dao.UserDao;
import com.user.dto.UserDto;
import com.user.message.messageApp;

@WebServlet("/usercontroller")
public class UserController extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      response.setContentType("text/html; charset=UTF-8");
      
      String command = request.getParameter("command");
      System.out.println("[ command : " + command + "]");
      
      UserDao dao = new UserDao();
      
      
      if(command.equals("idChk")) {
         String myid=request.getParameter("id");
         String res = dao.idChk(myid);
         
         boolean idnotused=true;
         
         //중복되는 경우가 있을경우
         if(res!=null){ 
            idnotused=false;
         }
         
         response.sendRedirect("idchk.jsp?idnotused="+idnotused);
      }else if(command.equals("login")){
         String user_id = request.getParameter("user_id");
         String user_pw = request.getParameter("user_pw");
         
         UserDto loginUser = dao.login(user_id, user_pw);
         
         if(loginUser.getUserid() != null) {
            
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser);
            session.setMaxInactiveInterval(60*60);
         
            if(loginUser.getRole().equals("M")) {
               dispatch("main.jsp",request,response);
            }else if(loginUser.getRole().equals("BU")) {
               dispatch("main.jsp",request,response);
            }else if(loginUser.getRole().equals("DM")) {
               dispatch("main.jsp",request,response);
            }else if(loginUser.getRole().equals("GU")) {
               dispatch("main.jsp",request,response);
            }
         
         }else {
            jsResponse("로그인 실패","main.jsp",response);
         
         }

      }else if(command.equals("insertuser")) {
         String myid = request.getParameter("myid");
         String mypw = request.getParameter("mypw");
         String myname = request.getParameter("myname");
         String myzip = request.getParameter("myzipcode");
         String mygender = request.getParameter("mygender");
         String myaddr = request.getParameter("myaddr1")+" "+request.getParameter("myaddr2");
         String mybirth = request.getParameter("mybirthyy")+"/"+request.getParameter("mybirthmm")+"/"+request.getParameter("mybirthdd");
         String myphone = request.getParameter("myphone");
         String myemail = request.getParameter("myemail");
         
         UserDto dto = new UserDto();
         dto.setUserid(myid);
         dto.setUserpw(mypw);
         dto.setUsername(myname);
         dto.setUserzip(myzip);
         dto.setUsergender(mygender);
         dto.setUserbirthdate(mybirth);
         dto.setUseraddr(myaddr);
         dto.setUserphone(myphone);
         dto.setUseremail(myemail);
         
         
         int res = dao.insertUser(dto);
         
         if(res>0) {
            jsResponse("회원 가입 성공", "usercontroller?command=loginform", response);
         }else {
            jsResponse("회원 가입 실패", "usercontroller?command=registform", response);
         }
      }else if(command.equals("registform")) {
         response.sendRedirect("join_user.jsp");
      }else if(command.equals("naver_login")) {
         response.sendRedirect("naverlogin.jsp");
      
      }else if(command.equals("logout")) {
      HttpSession session = request.getSession();
      session.invalidate();
      response.sendRedirect("main.jsp");
      
      }else if(command.equals("loginform")) {
         response.sendRedirect("login.jsp");
      }else if(command.equals("findid")) {
    	  String mybirth = request.getParameter("mybirthyy")+"/"+request.getParameter("mybirthmm")+"/"+request.getParameter("mybirthdd");
    	  String myphone = request.getParameter("myphone");
      }else if(command.equals("phonechk")) {
    	  String myphone = request.getParameter("myphone");
    	  String mybirthyy = request.getParameter("mybirthyy");
    	  String mybirthmm = request.getParameter("mybirthmm");
    	  String mybirthdd = request.getParameter("mybirthdd");
    	  String ran = "";
    	  
    	  ran=messageApp.sendsms(myphone);
    	  System.out.println(ran);
    	  
    	  if(ran!=""&&ran!=null) {
				jsResponse("인증번호 발신이 성공했습니다","login_find_id_input.jsp&ran="+ran+"myphone="+myphone+"mybirthyy="+mybirthyy+"mybirthmm="+mybirthmm+"mybirthdd="+mybirthdd,response);
			} else {
				jsResponse("인증번호 발신이 실패했습니다","login_find_id_input.jsp",response);
			}
      }
   }
   
   private void dispatch(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      RequestDispatcher dis = request.getRequestDispatcher(url);
      dis.forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
   
   private void jsResponse(String msg, String url,HttpServletResponse response) throws IOException {
      String s = "<script type='text/javascript'>"+"alert('"+msg+"');"+"location.href='"+url+"';"+"</script>";
      
      PrintWriter out = response.getWriter();
      out.print(s);
   }
}

