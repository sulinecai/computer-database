<%@ page pageEncoding= "UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="ListComputers"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="label.addComputer"/></h1>
                    <form action="AddComputer" method="POST" onsubmit="return validateForm()">                    
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="label.computer"/> *</label>
                                <input type="text" class="form-control" required id="computerName" name="computerName" placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introducedDate"><spring:message code="label.introduced"/></label>
                                <input type="date" class="form-control" id="introducedDate" name="introducedDate" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinuedDate"><spring:message code="label.discontinued"/></label>
                                <input type="date" class="form-control" id="discontinuedDate" name="discontinuedDate" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="idCompany"><spring:message code="label.company"/></label>
                                <select class="form-control" id="idCompany" name="idCompany">
                                    <option value="0" selected="selected">--</option>
	                                <c:forEach items="${companies}" var="company">
	                                   <option value="${company.idCompany}" ><c:out value="${company.idCompany} - ${company.companyName}"/></option>
	                                </c:forEach>
                                </select>
                            </div>   
                            <i>* <spring:message code="form.required"/></i>               
                        </fieldset>                        
                        <div class="actions pull-right">
                            <input type="submit" value='<spring:message code="button.add"/>' class="btn btn-primary">
                            or
                            <a href="ListComputers" class="btn btn-default"><spring:message code="button.cancel"/></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/validateDates.js"></script>
</body>
</html>