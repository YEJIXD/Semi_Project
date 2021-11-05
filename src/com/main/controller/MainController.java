package com.main.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.qna.dao.QnaDao;
import com.qna.dto.QnaDto;
import com.user.dao.UserDao;
import com.user.dto.UserDto;


@WebServlet("/MainController")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String command = request.getParameter("command");
		System.out.println("[command : " + command + "]");		
		
		UserDao dao = new UserDao();
		QnaDao udao = new QnaDao();
			
		if(command.equals("updatestart")) {
			int userno = Integer.parseInt(request.getParameter("userno"));
			dispatch("mypage_update.jsp",request,response);
			
		}else if(command.equals("update")) {
			int userno = Integer.parseInt(request.getParameter("userno"));
			String name = request.getParameter("name");
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String addr = request.getParameter("addr");
			
			UserDto dto = new UserDto();
			dto.setUserno(userno);
			dto.setUsername(name);
			dto.setUserid(id);
			dto.setUserpw(pw);
			dto.setUserphone(phone);
			dto.setUseremail(email);
			dto.setUseraddr(addr);
			
			int res = dao.update(dto);
			
			if(res>0) {
				jsResponse("회원 수정 성공","MainController?command=updateinfo&userno="+userno,response);

			}else {
				jsResponse("회원 수정 실패","MainController?command=update",response);
			}
			
		}else if(command.equals("qna")) {
			String qatype = request.getParameter("qatype");
			
			if(qatype == "") {
				qatype = "1";
			}
			
			List<QnaDto> list = udao.selectAll();
			List<QnaDto> faqlist = udao.selectFaq();
			
			request.setAttribute("list", list);
			request.setAttribute("faqlist", faqlist);
			RequestDispatcher disp = request.getRequestDispatcher("qna.jsp");
			disp.forward(request, response);
			
		}else if(command.equals("selectone")) {
			int qano = Integer.parseInt(request.getParameter("qano"));
			int userno = Integer.parseInt(request.getParameter("userno"));
			UserDto loginUser = dao.selectOne(userno);
			
			QnaDto dto = udao.selectOne(qano);
			
			request.setAttribute("dto", dto);
			HttpSession session = request.getSession();
			session.setAttribute("loginUser",loginUser);
			RequestDispatcher dis = request.getRequestDispatcher("question_board_selectone.jsp");
			dis.forward(request, response);
			
		}else if(command.equals("getlist")) {
			String qatype = request.getParameter("qatype");

			List<QnaDto> getlist = udao.selectType(qatype);
			
			
			
			//JSONObject obj = new JSONObject();
			
			request.setAttribute("getlist", getlist);
			RequestDispatcher disp = request.getRequestDispatcher("faq_qa_type.jsp");
			disp.forward(request, response);
			
			
			
		}else if(command.equals("writeform")) {
			response.sendRedirect("question_board_write.jsp");
			
		}else if(command.equals("boardwrite")) {
			int qagpno = Integer.parseInt(request.getParameter("qa_gpno"));
			int qagpsq = Integer.parseInt(request.getParameter("qa_gpsq"));
			String qauserid = request.getParameter("user_id");
			int qauserno = Integer.parseInt(request.getParameter("user_no"));
			String qatitle = request.getParameter("title");
			String qacontent = request.getParameter("content");
			String qatype = request.getParameter("qa_type");
			//String qafaq = request.getParameter("qa_faq");
			//String qastatus = request.getParameter("qa_status");
			//String photo = request.getParameter("photo");
			
			
			QnaDto dto = new QnaDto();
			dto.setQagpno(qagpno);
			dto.setQagpsq(qagpsq);
			dto.setUserid(qauserid);
			dto.setUserno(qauserno);
			dto.setQatitle(qatitle);
			dto.setQacontent(qacontent);
			dto.setQatype(qatype);
			//dto.setQafaq(qafaq);
			//dto.setQastatus(qastatus);
			//dto.setQapic(photo);
			
			int res = udao.insert(dto);
			if(res>0) {
				dispatch("MainController?command=qna",request,response);
			}else {
				dispatch("MainController?command=writeform",request,response);
			}
			
		}else if(command.equals("updateform")) {
			int qano = Integer.parseInt(request.getParameter("qano"));
			
			QnaDto dto = udao.selectOne(qano);
			request.setAttribute("dto", dto);
			dispatch("question_board_update.jsp", request, response);
			
		}else if(command.equals("boardupdate")) {
			int qano = Integer.parseInt(request.getParameter("qano"));
			
			String qatitle = request.getParameter("qatitle");
			String qacontent = request.getParameter("qacontent");
			//String photo = request.getParameter("photo");
			
			QnaDto dto = new QnaDto();
			
			
			dto.setQatitle(qatitle);
			dto.setQacontent(qacontent);
			dto.setQano(qano);
			//dto.setQapic(photo);
			
			int res = udao.update(dto);
			 
			if(res>0) {
				dispatch("MainController?command=qna",request,response);
			}else {
				dispatch("MainController?command=selectone&qano="+qano,request,response);
			}
			
		}else if(command.equals("delete")) {
			int qano = Integer.parseInt(request.getParameter("qano"));
			
			int res = udao.delete(qano);
			
			// 성공 시 qna 페이지로 이동, 실패 시 상세페이지로 이동
			if(res>0) {
				dispatch("MainController?command=qna", request, response);
			}else {
				dispatch("MainController?command=detail&qano="+qano, request, response);
			}
			
			
			
		}else if(command.equals("deleteinfo")) {
			int userno = Integer.parseInt(request.getParameter("userno"));
			
			boolean res = dao.delete(userno);
			
			if(res) {
				jsResponse("회원 탈퇴 성공","usercontroller?command=logout",response);
			}else {
				jsResponse("회원 탈퇴 실패","MainController?command=update",response);
			}
		}else if(command.equals("updateinfo")) {
			int userno = Integer.parseInt(request.getParameter("userno"));
		
			UserDto loginUser = dao.selectOne(userno);
			HttpSession session = request.getSession();
			session.setAttribute("loginUser",loginUser);
			dispatch("main.jsp",request,response);
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
