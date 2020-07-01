<%@ page pageEncoding= "UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
            <h1 id="homeTitle">
                <c:out value="${nbComputers}"/> <spring:message code="search.number"/>
            </h1>
        <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder='<spring:message code="search.name"/>' />
                        <input type="submit" id="searchsubmit" value='<spring:message code="button.search"/>' class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="AddComputer"><spring:message code="button.addComputer"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="button.edit"/></a>
                </div>
            </div>
        </div>    

        <form id="deleteForm" action="deleteComputer" method="POST">
            <input type="hidden" name="selection" value="">
            <input type="hidden" name="nbComputers" value="${nbComputers}">
            <input type="hidden" name="currentPage" value="${currentPage}">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
						<c:set var="orderByValueLink" value=""/>  
						<c:if test="${orderBy == null || orderBy.isEmpty()}"> 
							<c:set var="orderByValueLink" value="&orderBy=computerAsc"/>                             
						</c:if>                           
						<c:if test="${orderBy.equals('computerAsc')}"> 
							<c:set var="orderByValueLink" value="&orderBy=computerDesc"/>                             
						</c:if> 
						<c:if test="${orderBy.equals('computerDesc')}"> 
							<c:set var="orderByValueLink" value=""/>                             
						</c:if> 
						
                        <th>
                        	<a href="ListComputers?${orderByValueLink}" onclick=""><spring:message code="title.computer"/> &#9650; &#9660;</a>
                        </th>
                        <th>
                            <spring:message code="title.introduced"/>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <spring:message code="title.discontinued"/>
                            
                        </th>
                        <c:set var="orderByValueLink" value=""/> 
						<c:if test="${orderBy == null || orderBy.isEmpty()}"> 
							<c:set var="orderByValueLink" value="&orderBy=companyAsc"/>                             
						</c:if>                         
						<c:if test="${orderBy.equals('companyAsc')}"> 
							<c:set var="orderByValueLink" value="&orderBy=companyDesc"/>                             
						</c:if> 
						<c:if test="${orderBy.equals('companyDesc')}"> 
							<c:set var="orderByValueLink" value=""/>                             
						</c:if> 
                        <!-- Table header for Company -->
                        <th>
                            <a href="ListComputers?${orderByValueLink}" onclick=""><spring:message code="title.company"/> &#9650; &#9660;</a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                
                <c:forEach items="${computers}" var="computer" varStatus="status">
	                <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${computer.idComputer}">
                        </td>
                        <td>
                            <a href="EditComputer?page=${currentPage}&idComputer=${computer.idComputer}" onclick=""><c:out value="${computer.computerName}"/></a>
                        </td>
                        <td><c:out value="${computer.introducedDate}"/></td>
                        <td><c:out value="${computer.discontinuedDate}"/></td>
                        <td><c:out value="${computer.companyDTO.companyName}"/></td>
                    </tr>
				</c:forEach>               
                    
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
    
        <div class="container text-center">
			<c:set var="searchValue" value=""/>                             
			<c:if test="${search != null}"> 
				<c:set var="searchValue" value="&search=${search}"/>                             
			</c:if> 
			<c:set var="pgSize" value=""/>                             
			<c:if test="${pageSize != null}"> 
				<c:set var="pgSize" value="&pageSize=${pageSize}"/>                             
			</c:if> 
			<c:set var="orderByValue" value=""/>  
			<c:if test="${orderBy != null}"> 
				<c:set var="orderByValue" value="&orderBy=${orderBy}"/>                             
			</c:if> 
			 <c:if test="${lastPageIndex > 0}">
				<ul class="pagination">
				    <c:if test="${currentPage > 1}">	
				    	<li class="page-item"> <a href="ListComputers?page=1${searchValue}${pgSize}${orderByValue}" aria-label="First">
						       <span aria-hidden="true">&laquo;&laquo;</span></a>
				        </li>		
					    <li class="page-item"> <a href="ListComputers?page=${currentPage-1}${searchValue}${pgSize}${orderByValue}" aria-label="Previous">
						       <span aria-hidden="true">&laquo;</span></a>
				        </li>
                    </c:if>
					<c:forEach var="i" begin="${currentPage}" end="${lastPageIndex}" step="1">
					   <c:set var="activePage" value=""/>                             
					    <c:if test="${i == currentPage}"> 
					       <c:set var="activePage" value="active"/>                             
 					    </c:if>   
						<li class="page-item ${activePage}"><a href="ListComputers?page=${i}${searchValue}${pgSize}${orderByValue}"><c:out value="${i}" /></a></li>
					</c:forEach>
					<c:if test="${currentPage < nbPages}">
					   <li class="page-item"><a href="ListComputers?page=${currentPage+1}${searchValue}${pgSize}${orderByValue}" aria-label="Next">
					       <span aria-hidden="true">&raquo;</span></a>
					   </li>
					   <li class="page-item"><a href="ListComputers?page=${nbPages}${searchValue}${pgSize}${orderByValue}" aria-label="Last">
					       <span aria-hidden="true">&raquo;&raquo;</span></a>
					   </li>
					</c:if>
				</ul>
			</c:if>

			<div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default" onclick="window.location.href='?pageSize=10${searchValue}${orderByValue}'">10</button>
            <button type="button" class="btn btn-default" onclick="window.location.href='?pageSize=50${searchValue}${orderByValue}'">50</button>
            <button type="button" class="btn btn-default" onclick="window.location.href='?pageSize=100${searchValue}${orderByValue}'">100</button>
        </div>
        </div>

    </footer>
<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/dashboard.js"></script>

</body>
</html>