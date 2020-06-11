<%@ page pageEncoding= "UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
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
                <c:out value="${nbComputers}"/> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="AddComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>    

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
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
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                
                <c:forEach items="${computers}" var="computer" varStatus="status">
	                <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="EditComputer?idComputer=${computer.idComputer}" onclick=""><c:out value="${computer.name}"/></a>
                        </td>
                        <td><c:out value="${computer.introducedDate}"/></td>
                        <td><c:out value="${computer.discontinuedDate}"/></td>
                        <td><c:out value="${computer.companyDTO.name}"/></td>
                    </tr>
				</c:forEach>               
                    
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
			 <c:if test="${lastPageIndex > 0}">
				<ul class="pagination">
				    <c:if test="${currentPage > 1}">	
				    	<li class="page-item"> <a href="ListComputers?page=1" aria-label="First">
						       <span aria-hidden="true">&laquo;&laquo;</span></a>
				        </li>		
					    <li class="page-item"> <a href="ListComputers?page=${currentPage-1}" aria-label="Previous">
						       <span aria-hidden="true">&laquo;</span></a>
				        </li>
				        
                    </c:if>
					<c:forEach var="i" begin="${currentPage}" end="${lastPageIndex}" step="1">
					   <c:set var="activePage" value=""/>                             
					    <c:if test="${i == currentPage}"> 
					       <c:set var="activePage" value="active"/>                             
 					    </c:if>          
						<li class="page-item ${activePage}"><a href="ListComputers?page=${i}"><c:out value="${i}" /></a></li>
					</c:forEach>
					<c:if test="${currentPage < nbPages}">
					   <li class="page-item"><a href="ListComputers?page=${currentPage+1}" aria-label="Next">
					       <span aria-hidden="true">&raquo;</span></a>
					   </li>
					   <li class="page-item"><a href="ListComputers?page=${nbPages}" aria-label="Last">
					       <span aria-hidden="true">&raquo;&raquo;</span></a>
					   </li>
					</c:if>
				</ul>
			</c:if>


			<div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default">10</button>
            <button type="button" class="btn btn-default">50</button>
            <button type="button" class="btn btn-default">100</button>
        </div>
        </div>
        

    </footer>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>

</body>
</html>