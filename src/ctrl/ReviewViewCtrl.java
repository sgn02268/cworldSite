package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import vo.*;
import svc.*;

@WebServlet("/review_view")
public class ReviewViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ReviewViewCtrl() { super(); }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		ListSvc listSvc = new ListSvc();
		int result = listSvc.readUpdateReview(idx);
		// ����ڰ� ������ �Խñ��� ��ȸ���� ������Ű�� �޼ҵ� ȣ��
		ReviewInfo ri = listSvc.getReviewInfo(idx);
		// ����ڰ� ������ �Խñ��� ������� FreeList�� �ν��Ͻ� freeList�� ����
		request.setAttribute("piid", request.getParameter("piid"));
		if (ri != null) {
			request.setAttribute("ri", ri);
			// request ��ü�� freeInfo��� �̸��� �Ӽ��� freeList���� ������ �Ͽ� ���� �� ����
			RequestDispatcher dispatcher = request.getRequestDispatcher("bbs/review_view.jsp");
			dispatcher.forward(request, response);
			// Redirect ����� �ƴ� Dispatcher ������� �̵���Ŵ
			// Dispatcher ������� �̵��ϹǷ� url�� ������� �ʰ� ������ request�� response��ü�� �̵��� ���Ͽ����� �����Ӱ� ����� �� �ְ� ��
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�߸��� ��η� ���Խ��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
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
