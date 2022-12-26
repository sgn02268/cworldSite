package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/mypage_order_list")
public class MypageOrderListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MypageOrderListCtrl() { super(); }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		ArrayList<MypageOrderInfo> ol = new ArrayList<MypageOrderInfo>(); 
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
		
		MypageOrderListSvc mypageOrderListSvc = new MypageOrderListSvc();
		rcnt = mypageOrderListSvc.getOrderListCount(miid);
		// �˻��� �Խñ��� �� ������ �Խñ� �Ϸù�ȣ ��°� ��ü �������� ����� ���� �ʿ��� ��
		
		ol = mypageOrderListSvc.getOrderList(miid, cpage, psize);
		// ���ȭ�鿡�� ������ �Խñ� ����� ArrayList������ �޾ƿ� - �ʿ��� ��ŭ�� �޾ƿ��� ���� cpage�� psize�� �ʿ�
		pageInfo.setBsize(bsize);			pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);				pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);				pageInfo.setSpage(spage);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("ol", ol);
		// request ��ü�� freeInfo��� �̸��� �Ӽ��� freeList���� ������ �Ͽ� ���� �� ����
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("member/mypage_order_list.jsp");
		dispatcher.forward(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
