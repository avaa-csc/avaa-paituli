<%@page import="fi.csc.avaa.paituli.MetadataTable"%>
<%@page import="com.google.gson.JsonElement"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.JsonParser"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="org.apache.taglibs.standard.tag.common.sql.ResultImpl"%>
<%@ page import="java.io.*,java.util.*,java.sql.*,java.net.URLDecoder"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<% response.setHeader("Access-Control-Allow-Origin", "http://avoin-test.csc.fi,https://avoin-test.csc.fi,http://avaa.tdata.fi,https://avaa.tdata.fi");%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>

<%    File f = new File("/opt/avaa/liferay-portal/tomcat/shared/paituli.properties");
      Properties prop = new Properties();
      FileInputStream in = new FileInputStream(f);
      prop.load(in); 
      String salasana = prop.getProperty("password");
      in.close();
%>

<c:set var="salasana"><%= salasana %></c:set>
<c:set var="lang"><%= null == request.getParameter("lang") ? "fi_FI" : request.getParameter("lang") %></c:set>
<sql:setDataSource var="paituliDataSrc" driver="org.postgresql.Driver" url="jdbc:postgresql://db4.csc.fi:5520/paituli" user="paituli-ro"  password="${salasana}"/> 
<sql:query dataSource="${paituliDataSrc}" var="result">
SELECT row_to_json(dataset) AS result FROM dataset
</sql:query>

<%
	ResultImpl resSet = (ResultImpl) pageContext.getAttribute("result");
	String language = (String) pageContext.getAttribute("lang");
	StringBuilder sb = new StringBuilder();	
	//Gson gson = new Gson();	
	//JsonArray resArray = new JsonArray();
	sb.append("[");
	SortedMap[] rows = resSet.getRows();
	for(int i=0; i < rows.length; i++) {
		SortedMap row = rows[i];
		sb.append(row.get("result"));
		if(i < rows.length-1) {
			sb.append(",");
		}
	}
	sb.append("]");
	JsonParser jsonParser = new JsonParser();
	JsonArray jArray = (JsonArray) jsonParser.parse(sb.toString());
	JsonArray modifiedJArray = MetadataTable.modifyMetadataLanguage(jArray, language);
	Gson gson = new Gson();
	out.print(gson.toJson(modifiedJArray));
%>