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
                    <h1><spring:message code="label.registration"/></h1>
                    <form action="RegisterUser" method="POST">                    
                        <fieldset>
                            <div class="form-group">
                                <label for="username"><spring:message code="label.username"/> *</label>
                                <input type="text" class="form-control" required id="username" name="username" placeholder='<spring:message code="username"/>'>
                            </div>
                            <div class="form-group">
                                <label for="password"><spring:message code="label.password"/> *</label>
                                <input type="text" class="form-control" required id="password" name="password" placeholder='<spring:message code="password"/>'>
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