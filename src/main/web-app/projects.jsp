<%--
  Created by IntelliJ IDEA.
  User: daria
  Date: 12.06.22
  Time: 13:27
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
    <link rel="stylesheet" href="<c:url value='style/projects.css'/>">
</head>
<body class="vh-100">
<div class="d-flex flex-column">
    <%@ include file="./header.html" %>
    <div class="flex-item main">
        <div class="d-flex flex-row align-items-stretch">
            <%@ include file="navbar.html"%>
            <div class="flex-item d-flex align-items-center justify-content-center w-100">
                <div class="shelf">
                    <c:if test="${error != null}">
                        <div class="flex-row alert alert-danger error" role="alert">
                                ${error}
                        </div>
                    </c:if>
                    <div class="flex-row d-flex justify-content-end invitations-row">
                        <button type="button" class="btn btn-outline-secondary invitations" data-bs-toggle="modal" data-bs-target="#invitationsModal">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#8E8D8A" class="bi bi-inboxes-fill" viewBox="0 0 20 20">
                                <path d="M4.98 1a.5.5 0 0 0-.39.188L1.54 5H6a.5.5 0 0 1 .5.5 1.5 1.5 0 0 0 3 0A.5.5 0 0 1 10 5h4.46l-3.05-3.812A.5.5 0 0 0 11.02 1H4.98zM3.81.563A1.5 1.5 0 0 1 4.98 0h6.04a1.5 1.5 0 0 1 1.17.563l3.7 4.625a.5.5 0 0 1 .106.374l-.39 3.124A1.5 1.5 0 0 1 14.117 10H1.883A1.5 1.5 0 0 1 .394 8.686l-.39-3.124a.5.5 0 0 1 .106-.374L3.81.563zM.125 11.17A.5.5 0 0 1 .5 11H6a.5.5 0 0 1 .5.5 1.5 1.5 0 0 0 3 0 .5.5 0 0 1 .5-.5h5.5a.5.5 0 0 1 .496.562l-.39 3.124A1.5 1.5 0 0 1 14.117 16H1.883a1.5 1.5 0 0 1-1.489-1.314l-.39-3.124a.5.5 0 0 1 .121-.393z"/>
                            </svg>
                        </button>
                    </div>
                    <div class="flex-row">
                        <ul class='list-inline'>
                            <li class='book'>
                                <button type="button" class="btn btn-primary book-btn" data-bs-toggle="modal" data-bs-target="#createProject">
                                    <svg width="180" height="250" viewBox="0 0 180 250" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <rect width="180" height="250" rx="6" fill="#D8C3A5"/>
                                        <path d="M54.72 114.701H67.56H80.04V101.46V88.9831H102.54V101.46V114.701H114.24H126.72V127.469V136.005H115.26H102.54V147.246V159.605H80.04V149.423V136.005H67.56H54.72V114.701Z" fill="#E98074"/>
                                    </svg>

                                </button>
                            </li>

                            <c:forEach var = "i" begin = "0" end = "2">
                                <c:if test="${projectGrid.get(0).get(i) != null}">
                                    <li class='book'>
                                        <a class="btn" href="/project?id=${projectGrid.get(0).get(i).getId()}">
                                            <svg width="180" height="250" viewBox="0 0 180 250" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                <rect width="180" height="250" rx="6" fill="#D8C3A5"/>
                                                <rect y="75" width="180" height="100" fill="#8E8D8A"/>
                                                <text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle" fill="#D8C3A5" font-size="20">${projectGrid.get(0).get(i).getTitle()}</text>
                                                <c:if test="${projectGrid.get(0).get(i).getOwnerId() == currentUser.getId()}">
                                                    <path d="M160 199L164.49 212.82H179.021L167.265 221.361L171.756 235.18L160 226.639L148.244 235.18L152.735 221.361L140.979 212.82H155.51L160 199Z" fill="#E98074"/>
                                                </c:if>
                                            </svg>
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                        </div>

                <div class="flex-row">
                    <ul class='list-inline'>
                        <c:forEach var = "i" begin = "0" end = "3">
                            <c:if test="${projectGrid.get(1).get(i) != null}">
                                <li class='book'>
                                    <a class="btn" href="/project?id=${projectGrid.get(1).get(i).getId()}">
                                        <svg width="180" height="250" viewBox="0 0 180 250" fill="none" xmlns="http://www.w3.org/2000/svg">
                                            <rect width="180" height="250" rx="6" fill="#D8C3A5"/>
                                            <rect y="75" width="180" height="100" fill="#8E8D8A"/>
                                            <text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle" fill="#D8C3A5" font-size="20">${projectGrid.get(1).get(i).getTitle()}</text>
                                            <c:if test="${projectGrid.get(1).get(i).getOwnerId() == currentUser.getId()}">
                                                <path d="M160 199L164.49 212.82H179.021L167.265 221.361L171.756 235.18L160 226.639L148.244 235.18L152.735 221.361L140.979 212.82H155.51L160 199Z" fill="#E98074"/>
                                            </c:if>
                                        </svg>
                                    </a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="createProject" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <form class="create-project" method="POST">
        <input type="hidden" name="_method" value="post"/>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="createProjectLabel">Create project</h5>
                </div>
                <div class="modal-body">
                    <div class="form-floating">
                        <input type="text" class="form-control" name="title" placeholder="Title" value=${param.title}>
                        <label for="title">Title</label>
                    </div>
                    <div class="form-floating">
                        <input type="text" class="form-control" name="description" placeholder="Description" value=${param.description}>
                        <label for="description">Description</label>
                    </div>
                    <div class="form-floating">
                        <input type="date" class="form-control" name="dateStart" placeholder="Start of the project">
                        <label for="dateStart">Start of the project</label>
                    </div>
                    <div class="form-floating">
                        <input type="date" class="form-control" name="dateFinish" placeholder="End of the project">
                        <label for="dateFinish">End of the project</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary cancel" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-secondary create">Create</button>
                </div>
            </div>
        </div>
    </form>
</div>

<div class="modal fade" id="invitationsModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <form class="change-inv-status" method="POST">
        <input type="hidden" name="_method" value="put"/>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="invitationsLabel">Invitations</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <c:if test="${invitations.size() == 0}">
                        Empty here...
                    </c:if>
                    <c:forEach items="${invitations}" var="invitation">
                        <div class="invitation">
                            <div class="smth">
                                    ${invitation.getInviterUsername()} invites you to the project ${invitation.getProjectTitle()}
                            </div>
                            <div>
                                <button type="submit" name="join" class="btn btn-secondary  inv-b join" value="${invitation.getInvitationId()}:CONFIRMED">
                                    join
                                </button>
                                <button type="submit" name="join" class="btn btn-secondary inv-b" value="${invitation.getInvitationId()}:REJECTED">
                                    discard
                                </button>
                            </div>
                        </div>
                        <hr class="break-line">
                    </c:forEach>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>
