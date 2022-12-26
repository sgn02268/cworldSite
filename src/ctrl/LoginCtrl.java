package ctrl;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import vo.*;
import svc.*;

@WebServlet("/login")
public class LoginCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginCtrl() {   super();    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid").trim().toLowerCase();
		String pwd = request.getParameter("pwd").trim();
		String url = request.getParameter("url").replace('$', '&');
		
		LoginSvc loginSvc = new LoginSvc();
		MemberInfo loginInfo = loginSvc.getLoginMember(uid, pwd);
		
		if (loginInfo != null) { // �α��� ����
			loginSvc.getLoginUpdate(uid, pwd);
			HttpSession session = request.getSession();
			session.setAttribute("loginInfo", loginInfo);
			// �α����� ȸ���� ������ ���� loginInfo �ν��Ͻ��� ������ �Ӽ����� ����
			response.sendRedirect(url);
			
		} else {				 // �α��� ����
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���̵�� ��ȣ�� Ȯ���ϼ���.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
	}
}
