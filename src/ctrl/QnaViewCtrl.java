package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import vo.*;
import svc.*;


@WebServlet("/qna_view")
public class QnaViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public QnaViewCtrl() { super(); }
	
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	int idx = Integer.parseInt(request.getParameter("idx"));
    	int cpage = Integer.parseInt(request.getParameter("cpage"));
    	
    	QnaViewSvc qnaViewSvc = new QnaViewSvc();
    	QnaInfo qi = qnaViewSvc.getQnaInfo(idx);
    	
    	if (qi != null) {
			request.setAttribute("qi", qi);
			// request ��ü�� freeInfo��� �̸��� �Ӽ��� freeList���� ������ �Ͽ� ���� �� ����
			RequestDispatcher dispatcher = request.getRequestDispatcher("member/qna_view.jsp");
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
