<%@page import="fi.csc.avaa.paituli.email.SendPaituliEmail"%>
<%
/**
 * Copyright (c) 2015 CSC. All rights reserved.
 * @author Juha-Matti Lehtinen
 * @author Pekka Järveläinen
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fi.csc.avaa.tools.StringTools,
        fi.csc.avaa.paituli.email.GenerateDownloadPackageAction"%>
<% response.setHeader("Access-Control-Allow-Origin", "http://avoin-test.csc.fi,https://avoin-test.csc.fi,http://avaa.tdata.fi,https://avaa.tdata.fi");%>
<c:set var="lang"><%= null == request.getParameter("language") ? "fi_FI" : request.getParameter("language") %></c:set>
<%
	String filePaths = StringTools.isEmptyOrNull(request.getParameter("filepaths")) ? null : request.getParameter("filepaths");
	String filenames = StringTools.isEmptyOrNull(request.getParameter("filenames")) ? null : request.getParameter("filenames");
	String org = StringTools.isEmptyOrNull(request.getParameter("org")) ? null : request.getParameter("org");
	String data = StringTools.isEmptyOrNull(request.getParameter("data")) ? null : request.getParameter("data");
	String scale = StringTools.isEmptyOrNull(request.getParameter("scale")) ? null : request.getParameter("scale");
	String year = StringTools.isEmptyOrNull(request.getParameter("year")) ? null : request.getParameter("year");
	String coordsys = StringTools.isEmptyOrNull(request.getParameter("coordsys")) ? null : request.getParameter("coordsys");
	String format = StringTools.isEmptyOrNull(request.getParameter("format")) ? null : request.getParameter("format");
	String email = StringTools.isEmptyOrNull(request.getParameter("email")) ? null : request.getParameter("email");
	String dataId = StringTools.isEmptyOrNull(request.getParameter("data_id")) ? null : request.getParameter("data_id");
	
	String language = (String) pageContext.getAttribute("lang");
	if(StringTools.isEmptyOrNull(filePaths) || StringTools.isEmptyOrNull(email)) {
		if("fi_FI".equals(language)) {
			out.print("Kutsusta puuttuu parametreja. Ne on merkitty seuraavassa sanalla 'null'.<br>filepaths: " + filePaths + "<br>email: " + email);
		} else if ("en_US".equals(language)) {
			out.print("Request is missing parameters. They are marked with the word 'null' in the following.<br>filepaths: " + filePaths + "<br>email: " + email);
		} else {
			out.print("Kutsusta puuttuu parametreja. Ne on merkitty seuraavassa sanalla 'null'.<br>filepaths: " + filePaths + "<br>email: " + email);
		}
	} else {
		new SendPaituliEmail(email, new GenerateDownloadPackageAction(filePaths), language, org, data, scale, year, coordsys, format, filePaths, dataId).start();
		response.setStatus(200);
	}
 %>