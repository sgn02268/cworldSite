package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/g_qna_proc_in")
public class GQnaProcInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public GQnaProcInCtrl() { super(); }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String gq_title = request.getParameter("gq_title").trim().replace("'","''").replace("<", "&lt;");
		String gq_content = request.getParameter("gq_content").trim().replace("'","''").replace("<", "&lt;");
		String p1 = request.getParameter("p1");
		String p2 = request.getParameter("p2");
		String p3 = request.getParameter("p3");
		String e1 = request.getParameter("e1");
		String e2 = request.getParameter("e2");
		String gq_gname = request.getParameter("gq_gname");
		String gq_phone =  p1 + "-" + p2 + "-" + p3;
		String email = e1 + "@" + e2;
		
		String gq_ef = email;
			
		
		GqnaInfo gqnaInfo = new GqnaInfo();	// �Խñ� ������ ������ �ν��Ͻ�
		gqnaInfo.setGq_title(gq_title);
		gqnaInfo.setGq_gname(gq_gname);
		gqnaInfo.setGq_content(gq_content);
		gqnaInfo.setGq_phone(gq_phone);
		gqnaInfo.setGq_ef(gq_ef);
		gqnaInfo.setGq_ip(request.getRemoteAddr());
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		// ����� �� �ʿ���(t_free_list ���̺� ������) �����͵��� ��� FreeList�� �ν��Ͻ� freeList�� ����
		String miid = loginInfo.getMi_id();
		gqnaInfo.setMi_id(miid);
		GQnaProcInSvc gQnaProcInSvc = new GQnaProcInSvc();
		int idx = gQnaProcInSvc.GqnaListInsert(gqnaInfo);
		// insert ������ �����ϹǷ� insert���� ���ڵ� ������ �޾ƿ��� ���� �Ϲ����̳�,
		// ó�� �� �ش� �� ���� ȭ������ ���� �ϱ� ������ �ش� ���� �۹�ȣ�� �޾ƿ�
		
		if (idx > 0) {	// ���������� ���� ��ϵǾ�����
			response.sendRedirect("g_qna_view?cpage=1&idx=" + idx);
		} else {	// �� ��Ͻ� ������ �߻�������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�� ��Ͽ� �����߽��ϴ�. �ٽ� �õ��� ������.');");
			out.println("history.back()");
			out.println("</script>");
			
			
		}
	}
}

