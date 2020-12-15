<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String params = request.getParameter("params");
if(params.equals("good")){
%>
	<h3 style="color: red;">Good! 럭키데이네요^ㅅ^</h3>
<%
} else if(params.equals("bad")){
%>
	<h3 style="color: blue;">Bad! 언럭키데이ㅠㅅㅠ</h3>
<%
}
%>