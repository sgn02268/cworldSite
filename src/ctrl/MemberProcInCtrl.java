package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import vo.*;
import svc.*;

@WebServlet("/memberProcIn")
public class MemberProcInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MemberProcInCtrl() {   super();   }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	request.setCharacterEncoding("utf-8");
    	MemberInfo memberInfo = new MemberInfo();	
		MemberAddr memberAddr = new MemberAddr();		
		
		//  ȸ������ ���� �Է�
		memberInfo.setMi_phone(request.getParameter("p1") + "-" + request.getParameter("p2") + "-" + request.getParameter("p3"));
		memberInfo.setMi_mail(request.getParameter("e1") + "@" + request.getParameter("e3"));
		
		memberInfo.setMi_id(request.getParameter("mi_id").trim().toLowerCase());
		memberInfo.setMi_pw(request.getParameter("mi_pw").trim());
		memberInfo.setMi_name(request.getParameter("mi_name").trim());
		memberInfo.setMi_gender(request.getParameter("mi_gender"));
		memberInfo.setMi_adv(request.getParameter("mi_adv"));
		memberInfo.setMi_birth(request.getParameter("by") + "-" + request.getParameter("bm") + "-" + request.getParameter("bd"));
		
		//  ȸ������ �ּ� �Է�
		memberAddr.setMi_id(memberInfo.getMi_id());
		memberAddr.setMa_name("�⺻�ּ�");
		memberAddr.setMa_receiver(memberInfo.getMi_name());
		memberAddr.setMa_phone(memberInfo.getMi_phone());
		memberAddr.setMa_basic("y");
		memberAddr.setMa_zip(request.getParameter("ma_zip").trim());
		memberAddr.setMa_addr1(request.getParameter("ma_addr1").trim());
		memberAddr.setMa_addr2(request.getParameter("ma_addr2").trim());
			
		MemberProcInSvc memberProcInSvc = new MemberProcInSvc();
		int result = memberProcInSvc.memberInsert(memberInfo, memberAddr);
		
		if (result == 1) {	// ���������� ���� �Ǿ�����
			LoginSvc loginSvc = new LoginSvc();
			MemberInfo loginInfo = loginSvc.getLoginMember(memberInfo.getMi_id(), memberInfo.getMi_pw());
			loginSvc.getLoginUpdate(memberInfo.getMi_id(), memberInfo.getMi_pw());
			HttpSession session = request.getSession();
			session.setAttribute("loginInfo", loginInfo);
			response.sendRedirect("/cworldSite/member/join_after.jsp"); 
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('�۾��� �����߽��ϴ�. �ٽ� �õ����ּ���.'); history.back(); </script>");
			out.close();
		}
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
