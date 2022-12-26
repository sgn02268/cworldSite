package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/product_list")
public class ProductListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductListCtrl() { super(); }
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 12, bsize = 9, rcnt = 0, pcnt = 0, spage = 0;
		// ���� ������ ��ȣ, ������ ũ��, ���ũ��(����¡), ���ڵ�(��ǰ) ����, �������� ����, ���������� ����
		if(request.getParameter("cpage") != null) 
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		// �˻� ���� where�� ����
		String pcb = request.getParameter("pcb");	// ��з�
		String pcs = request.getParameter("pcs");	// �Һз�
		String sch = request.getParameter("sch");	// �˻�����(���ݴ� & ��ǰ�� & �귣��)
		String where ="" ;
		if (pcb != null && !pcb.equals(""))
			where += " and a.pcb_id = '" + pcb + "' ";
		if (pcs != null && !pcs.equals(""))
			where += " and a.pcs_id = '" + pcs + "' ";
		if (sch != null && !sch.equals("")) {
		// &sch=p10000~20000,ntest, bB1:B2:B3
			String[] arrSch = sch.split(",");
			for (int i = 0; i < arrSch.length; i++) {
				char c = arrSch[i].charAt(0);
				if(c == 'p') {	// ���ݴ� �˻��� ���
					String sp = arrSch[i].substring(1, arrSch[i].indexOf('~')); 
					if(sp != null && !sp.contentEquals(""))
						where += " and pi_price >= '" + sp + "' ";
					
					String ep = arrSch[i].substring(arrSch[i].indexOf("~") + 1);
					if(ep != null && !ep.contentEquals(""))
						where += " and pi_price <= '" + ep + "' ";
				} else if (c == 'n') { // ��ǰ��  �˻��� ���
					where += " and pi_name like '%" + arrSch[i].substring(1) + "%' ";
				} else if (c == 'l') {
					String arrSpecial[] = arrSch[i].substring(1).split(":");
					where += " and (";
					for (int j = 0 ; j < arrSpecial.length ; j++) {
						where += (j==0 ? "": " or ") + "a.pi_special like '%" + arrSpecial[j] + "%' ";
					}
					where += ") ";
				}
			}
		}
		
		// �������� where�� ����
		String o = request.getParameter("o");		// ��������
		if(o == null || o.contentEquals(""))	o = "a";
		String orderBy = "";
		switch (o) {
			case "a": orderBy = " order by a.pi_date desc";		break;	// ��Ͽ���
			case "b": orderBy = " order by a.pi_srcnt desc";	break;	// �Ǹŷ���
			case "c": orderBy = " order by a.pi_dc desc";		break;	// ��������
			case "d": orderBy = " order by realPrice asc";		break;	// ���ݳ�����
			case "e": orderBy = " order by realPrice desc";		break;	// ���ݳ�����
			case "f": orderBy = " order by a.pi_score desc";	break;	// ����������
			case "g": orderBy = " order by a.pi_review desc";	break;	// ���丹����
			case "h": orderBy = " order by a.pi_read desc";		break;	// ��ȸ������
		}
		String v = request.getParameter("v");		// ������
		if(v != null && v.equals("l")) { psize = 10; } // �������� ������� ��� ������ ũ�⸦ 10����
		else { v = "g"; }
		
		ProductListSvc productListSvc = new ProductListSvc();
		rcnt = productListSvc.getProductCount(where);
		// �˻��� ��ǰ�� �� ������ ��ü ������ ���� ���� �� ���
		
		ArrayList<PdtInfo> productList = productListSvc.getProductList(cpage, psize, where, orderBy);
		// �˻��� ��ǰ�� �� ���� ���������� ������ ��ǰ ����� �޾ƿ�
		ArrayList<PdtCtgrSmall> smallList = new ArrayList<PdtCtgrSmall>();
		if (pcb != null && !pcb.equals("")) { //�˻����� �� ��з��� ������
			smallList = productListSvc.getSmallList(pcb);
			//��з��� ���ϴ� �Һз� ����� �޾ƿ�	
		}
		
		pcnt = rcnt / psize;	if(rcnt % psize > 0) pcnt++; // ��ü ������ ��
		spage = (cpage - 1 ) / psize * psize + 1;	//��� ���� ������ ��ȣ
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize); 	pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt); 	pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt); 	pageInfo.setSpage(spage);
		pageInfo.setSch(sch); 		pageInfo.setPcs(pcs);
		pageInfo.setPcb(pcb); 		pageInfo.setV(v);
		pageInfo.setO(o);
		// ����¡ ���� ������� �˻� �� ���� �������� pageInfo �ν��Ͻ��� ����
	
		request.setAttribute("productList", productList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("smallList", smallList);
		RequestDispatcher dispatcher =  request.getRequestDispatcher("product/product_list.jsp");
    	dispatcher.forward(request, response); 
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
