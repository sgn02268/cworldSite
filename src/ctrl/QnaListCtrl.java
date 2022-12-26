package ctrl;

import java.io.*;
import java.net.URLEncoder;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import vo.*;
import svc.*;
import java.util.*;

@WebServlet("/qna_list")
public class QnaListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public QnaListCtrl() { super(); }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		ArrayList<QnaInfo> qnaList = new ArrayList<QnaInfo>(); 
		PageInfo pageInfo = new PageInfo();
		// �����Խ��� �� ����� �����ϱ� ���� ArrayList�� �ȿ� ����� �����ʹ� FreeList�� �ν��Ͻ��� ���
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		String miid = loginInfo.getMi_id();
		
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;
		// ���� ������ ��ȣ, ������ ũ��, ��� ũ��, ���ڵ�(�Խñ�) ����, ������ ����, ���� ������ ��ȣ ���� ������ ����
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
			// cpage ���� ������ �޾Ƽ� int������ ����ȯ ��Ŵ(���Ȼ��� ������ ��������� �ؾ� �ϱ� ����)
		}
		
		QnaListSvc qnaListSvc = new QnaListSvc();
		rcnt = qnaListSvc.getQnaListCount(miid);
		// �˻��� �Խñ��� �� ������ �Խñ� �Ϸù�ȣ ��°� ��ü �������� ����� ���� �ʿ��� ��
		
		qnaList = qnaListSvc.getQnaList(miid, cpage, psize);
		// ���ȭ�鿡�� ������ �Խñ� ����� ArrayList������ �޾ƿ� - �ʿ��� ��ŭ�� �޾ƿ��� ���� cpage�� psize�� �ʿ�
		pageInfo.setBsize(bsize);			pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);				pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);				pageInfo.setSpage(spage);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("qnaList", qnaList);
		// request ��ü�� freeInfo��� �̸��� �Ӽ��� freeList���� ������ �Ͽ� ���� �� ����
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("member/qna_list.jsp");
		dispatcher.forward(request, response);
	}
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
