<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../_inc/inc_header.jsp" %>
<%
if (loginInfo != null){%>
<script>
alert('잘못된 경로로 들어오셨습니다.');
history.back();
</script>
<%} 
request.setCharacterEncoding("utf-8");

String[] arrDomain = {"naver.com", "nate.com", "gmail.com", "daum.net", "yahoo.com"}; 

%>
<style>
.notselect { display:none;}
</style>
<script>
$(document).ready(function(){
	$("#e2").change(function(){
		if ($(this).val() == ""){
			$("#e3").val("");
			$("#e3").attr("readonly",true);
		} else if ($(this).val() == "direct"){
			$("#e3").val("");
			$("#e3").focus();
			$("#e3").attr("readonly",false);
		} else{
			$("#e3").val($(this).val());
			$("#e3").attr("readonly",true);
		}	
	});
});

</script>
<div class="content" id="findPw" align="center">
	<form name="frmfindId" action="../find_pw" method="post">
		<h1>비밀번호 찾기</h1>
		<table id="findIdTable">
			<tr>
			<td width="30%" height="50" align="right">아이디 &nbsp;</td>
			<td align="left"> &nbsp; &nbsp;&nbsp; <input type="text" name="uid" id="uid" value=""/></td>
			</tr>
			<tr>
			<td width="30%" height="50" align="right">이름 &nbsp;</td>
			<td align="left"> &nbsp; &nbsp;&nbsp; <input type="text" name="name" id="name" value=""/></td>
			</tr>
			<tr id="findIdbyMail">
			<td width="30%" height="50" align="right">이메일 &nbsp;</td>
			<td height="50" align="left"> &nbsp; &nbsp;&nbsp;
			<input type="text" name="e1" size="5" maxlength="20"/> @
			<input type="text" name="e3" id="e3" size="8" maxlength="20" readonly="true"/>
			<select name="e2" id="e2">
				<option value="" >도메인 선택</option>
				<% for (int i = 0 ; i < arrDomain.length ; i++){ %>
				<option value="<%=arrDomain[i] %>"><%=arrDomain[i] %></option>
				<%} %>
				<option value="direct">직접 입력</option>
			</select>
			</td></tr>
			<tr><td colspan="2" align="center"  height="50"><input type="submit" value="비밀번호 찾기"/>
			</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>
<%@ include file="../_inc/inc_footer.jsp" %>