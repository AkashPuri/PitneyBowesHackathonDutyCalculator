<%@page import="com.pb.services.CountryCodeFinder"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
This is checkout page !!
<form action="proceessFormAddress">

            <div>
            Select your country
<input type="text" name="address" value="" required>
              <select name="sourceCountry">
  <%
  HashMap<String,String> countryMapFrom = CountryCodeFinder.getCountryMapFrom();
		  for (Map.Entry<String, String> entry : countryMapFrom.entrySet())
		  {
		      out.println("<option value="+entry.getKey()+">"+entry.getKey()+"</option>");
		  }
  %>
</select>
<br>
            <div>Select destination country
               <select name="destinationCountry">
  <%
  HashMap<String,String> countryMapTo = CountryCodeFinder.getCountryMapTo();
		  for (Map.Entry<String, String> entry : countryMapTo.entrySet())
		  {
		      out.println("<option value="+entry.getKey()+">"+entry.getKey()+"</option>");
		  }
  %>
</select>
<input type="Submit" value="Proceed"/>
            </div>
            </div>
            </form>
</body>
</html>