package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import vo.*;
import svc.*;


@WebServlet("/member_dormancy")
public class MemberDormancyCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MemberDormancyCtrl() {  super();   }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		if(loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('�α��� ������ �������� �ʽ��ϴ�.');");
			out.println("location.replace('/cworldSite/login_form.jsp'); ");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		String pw = loginInfo.getMi_pw();
		String e1 = request.getParameter("e1");
		String e3 = request.getParameter("e3");
		String mail = e1 + "@" + e3;
		
		String sql = "update t_member_info set mi_status = 'a' "
				+ " where mi_id = '" + miid + "' and mi_mail = '" + mail +"' ";
		MemberDormancySvc memberDormancySvc = new MemberDormancySvc();
		System.out.println(sql);
		int result = memberDormancySvc.updateMemberStatus(sql);
		System.out.println(result);
		if (result == 1) {
			session.removeAttribute("loginInfo");
			loginInfo.setMi_status("a");
			session.setAttribute("loginInfo", loginInfo);
			// �α����� ȸ���� ������ ���� loginInfo �ν��Ͻ��� ������ �Ӽ����� ����
			response.sendRedirect("/cworldSite/index.jsp");
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�̸����� ��ġ���� �ʽ��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
	}
}
