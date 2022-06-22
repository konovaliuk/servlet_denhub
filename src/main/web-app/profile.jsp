<%--
  Created by IntelliJ IDEA.
  User: daria
  Date: 11.06.22
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<c:url value='style/profilePage.css'/>">
</head>
<body class="vh-100">
    <div class="d-flex flex-column">
        <%@ include file="./header.html" %>
        <div class="flex-item main">
            <div class="d-flex flex-row align-items-stretch">
                <%@ include file="navbar.html"%>
                <form method="POST">
                    <div class="flex-item justify-content-center profile">
                        <div class="row">
                            <div class="col-md-3 border-right">
                                <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                                    <img class="rounded-circle mt-5" width="150px" src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg"><span class="font-weight-bold">${currentUser.getUsername()}</span>
                                </div>
                            </div>
                            <div class="col-md-5 border-right">
                                <div class="p-3 py-5">
                                    <div class="row mt-5">
                                        <div class="col-md-6">
                                            <label class="labels">First name</label><br>
                                            <c:if test='${edit == false}'>
                                                <span class="badge bg-secondary w-75">${currentUser.getFirstName()}</span>
                                            </c:if>
                                            <c:if test='${edit == true}'>
                                                <input name="firstName" type="text" class="badge bg-secondary w-75" style="display: inline" value="${currentUser.getFirstName()}">
                                            </c:if>
                                        </div>
                                        <div class="col-md-6"><label class="labels">Last name</label><br>
                                            <c:if test='${edit == false}'>
                                                <span class="badge bg-secondary w-75">${currentUser.getLastName()}</span>
                                            </c:if>
                                            <c:if test='${edit == true}'>
                                                <input name="lastName" type="text" class="badge bg-secondary w-75" style="display: inline" value="${currentUser.getLastName()}">
                                            </c:if>
                                        </div>
                                        <div class="col-md-6">
                                            <c:if test='${edit == false}'>
                                                <a class="btn btn-secondary" href="?edit=true">
                                                    <svg  xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#EAE7DC" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                                        <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                                    </svg>
                                                </a>
                                            </c:if>
                                            <c:if test='${edit == true}'>
                                                <button type="submit" class="btn btn-secondary" onclick="document.location = document.location.pathname">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#EAE7DC" class="bi bi-save2" viewBox="0 0 16 16">
                                                        <path d="M2 1a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H9.5a1 1 0 0 0-1 1v4.5h2a.5.5 0 0 1 .354.854l-2.5 2.5a.5.5 0 0 1-.708 0l-2.5-2.5A.5.5 0 0 1 5.5 6.5h2V2a2 2 0 0 1 2-2H14a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h2.5a.5.5 0 0 1 0 1H2z"></path>
                                                    </svg>
                                                </button>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="col-md-12"><label class="labels">Bio </label><br>
                                            <c:if test='${edit == false}'>
                                                <span class="badge bg-secondary w-75">${currentUser.getBio()}</span>
                                            </c:if>
                                            <c:if test='${edit == true}'>
                                                <input type="text" name="bio" class="badge bg-secondary w-75" style="display: inline" value="${currentUser.getBio()}">
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
