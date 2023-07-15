<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
  String filename = "transactions.pdf";
String filepath = "C:\\Users\\2500857\\Desktop\\Eclipse\\Workspace\\bank-casestudy-master-221028\\";
response.setContentType("APPLICATION/OCTET-STREAM");
response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
java.io.FileInputStream fileInputStream = new java.io.FileInputStream(filepath + filename);
int i;
while ((i = fileInputStream.read()) != -1) {
  out.write(i);
}
fileInputStream.close();
%>
</body>
</html>