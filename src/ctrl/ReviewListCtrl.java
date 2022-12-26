package ctrl;

import java.io.*;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/review_list")
public class ReviewListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ReviewListCtrl() { super(); }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;
    	// ���� ������ ��ȣ, ������ ũ��, ��� ũ��, ���ڵ�(��ǰ) ����, ������ ����, ���� ������ ��ȣ ���� ������ ����
    	if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
		}
    	
    	String schtype = request.getParameter("schtype");	// ����, ����, �ۼ���, ����+����
		String keyword = request.getParameter("keyword");	// �˻���		
		String where = " where rl_isview = 'y' ";		// �˻� ������ ���� ��� where ���� ������ ����
		if (schtype == null || keyword == null) {
			schtype = "";
			keyword = "";
		} else if (!schtype.equals("") && !keyword.equals("")) {	// �˻����ǰ� �˻�� ���� ���
			URLEncoder.encode(keyword, "UTF-8");
			// ������Ʈ������ �ְ� �޴� �˻�� �ѱ��� ��� IE���� ��Ȥ ������ �߻��� �� �����Ƿ� �����ڵ�� ��ȯ��Ŵ
			if (schtype.equals("tc")) {	// �˻� ������ '����+����'�� ���
				where += " and rl_title like '%" + keyword + "%' or rl_content like '%" + keyword + "%' ";
			} else if(schtype.equals("writer")) {
				where += " and mi_id like '%" + keyword + "%' ";
			} else {
				where += " and rl_" + schtype + " like '%" + keyword + "%' ";
			}			
		}
		
    	ListSvc listSvc = new ListSvc();
    	rcnt = listSvc.getReviewCount(where);	
    	
    	ArrayList<ReviewInfo> reviewList = listSvc.getReviewList(where, cpage, psize); 
    	
    	pcnt = rcnt / psize;
    	if (rcnt % psize > 0) {
    		pcnt++;	// ��ü ������ ��
    	}
    	spage = (cpage - 1) / psize * psize + 1;	// ��� ���� ������ ��ȣ
    	PageInfo pageInfo = new PageInfo();
    	
    	pageInfo.setBsize(bsize);			pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);				pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);				pageInfo.setSpage(spage);			
		pageInfo.setSchtype(schtype);		pageInfo.setKeyword(keyword);
		// ����¡ ���� ������� �˻� �� ���� �������� pageInfo �ν��Ͻ��� ����
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("reviewList", reviewList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("bbs/review_list.jsp");
		dispatcher.forward(request, response);
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
