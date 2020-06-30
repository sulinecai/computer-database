<%@ page pageEncoding= "UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                    <div class="label label-default pull-right">
                        id: <c:out value="${computer.idComputer}" />
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="EditComputer" method="POST" onsubmit="return validateForm()">
                        <input type="hidden" value="${currentPage}" id="currentPage" name="currentPage"/> <!-- TODO: Change this value with the computer id -->
                        <input type="hidden" value="${computer.idComputer}" id="idComputer" name="idComputer"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name *</label>
                                <input type="text" class="form-control" required id="computerName" name="computerName" value="${computer.computerName}">
                            </div>
                            <div class="form-group">
                                <label for="introducedDate">Introduced date</label>
                                <input type="date" class="form-control" id="introducedDate" name="introducedDate" value="${computer.introducedDate}">
                            </div>
                            <div class="form-group">
                                <label for="discontinuedDate">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinuedDate" name="discontinuedDate" value="${computer.discontinuedDate}">
                            </div>
                            <div class="form-group">
                                <label for="idCompany">Company</label>
                                <select class="form-control" id="idCompany" name="idCompany">
                                    <option value="0">--</option>
                                    <c:forEach items="${companies}" var="company">
										<c:set var="currentCompany" value="" />
										<c:if test="${company.idCompany == computer.companyDTO.idCompany}">
											<c:set var="currentCompany" value="selected" />
										</c:if>
										<c:out value="${currentCompany}"></c:out>
										<option value="${company.idCompany}"
											<c:out value="${currentCompany}"/>>
											<c:out value="${company.idCompany} - ${company.companyName}" />
										</option>
									</c:forEach>
                                </select>
                            </div>  
                            <i>* required</i>       
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="ListComputers" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
<script src="resources/js/validateDates.js"></script>    
</body>
</html>