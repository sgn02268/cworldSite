package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/mypage_rent_list")
public class MypageRentListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MypageRentListCtrl() { super(); }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		ArrayList<MypageRentInfo> mrl = new ArrayList<MypageRentInfo>(); 
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
		
		MypageRentListSvc mypageRentListSvc = new MypageRentListSvc();
		rcnt = mypageRentListSvc.getRentListCount(miid);
		// �˻��� �Խñ��� �� ������ �Խñ� �Ϸù�ȣ ��°� ��ü �������� ����� ���� �ʿ��� ��
		
		mrl = mypageRentListSvc.getRentList(miid, cpage, psize);
		// ���ȭ�鿡�� ������ �Խñ� ����� ArrayList������ �޾ƿ� - �ʿ��� ��ŭ�� �޾ƿ��� ���� cpage�� psize�� �ʿ�
		pageInfo.setBsize(bsize);			pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);				pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);				pageInfo.setSpage(spage);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("mrl", mrl);
		// request ��ü�� freeInfo��� �̸��� �Ӽ��� freeList���� ������ �Ͽ� ���� �� ����
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("member/mypage_rent_list.jsp");
		dispatcher.forward(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
