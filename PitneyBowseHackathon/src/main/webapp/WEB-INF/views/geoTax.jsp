<%@page import="com.pb.services.ServiceTax"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
String address =(String)session.getAttribute("address");
String amount = "100";
List<String> taxList = ServiceTax.getTaxDetails(address,amount);
if(taxList.size()<1){
	out.println("Invalid Address");
}
else{
for(int i=0;i<taxList.size();i++){
	out.println(taxList.get(i)+"<br>");
}
}
%>
</body>
</html>