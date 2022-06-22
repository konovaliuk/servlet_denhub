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
    <link rel="stylesheet" href="<c:url value='style/projectPage.css'/>">
</head>
<body class="vh-100">
    <div class="d-flex flex-column">
        <%@ include file="./header.html" %>
        <div class="flex-item main">
            <div class="d-flex flex-row align-items-stretch">
                <%@ include file="navbar.html"%>

            <div class="flex-row d-flex justify-content-center w-100 project">
                <div class="flex-item flex-column d-flex">
                    <div class="flex-item h-25 project-info">
                        <div class="flex-row d-flex">
                            <div class="d-flex justify-content-center w-100 info-title">
                                ${currentProject.getTitle()}
                            </div>
                            <c:if test="${currentProject.getOwnerId() == currentUser.getId()}">
                                <button type="button" class="edit-project" data-bs-toggle="modal" data-bs-target="#editProject">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                        <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                    </svg>
                                </button>
                            </c:if>
                        </div>
                    <hr class="break-line a">
                        Description: ${currentProject.getDescription()}
                    </div>
                    <div class="flex-item overflow-auto h-50 project-team">
                        <div class="d-flex justify-content-center w-100">
                            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#team">
                                Team
                            </button>
                        </div>
                        <div class="team">
                            <c:forEach items="${currentTeam}" var = "member">
                                <hr class="break-line b">
                                ${member.getUsername()}
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="flex-item flex-column d-flex">
                    <div class="flex-item h-25 project-progress">
                        Progress
                    </div>
                    <div class="flex-item h-50 project-tasks">
                        <div class="d-flex justify-content-center w-100">
                            <a href="/project/tasks?projectId=${currentProject.getId()}" class="btn btn-primary">
                                Tasks
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <div class="modal fade" id="team" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel-team">Project Team</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <c:forEach items="${currentTeam}" var = "member">
                        <div class="flex-row d-flex w-100 h-100">
                            <div class="flex-item w-100 h-100">
                                ${member.getUsername()}
                            </div>
                            <div class="flex-item flex-row d-flex h-100">
                                <c:if test="${currentUser.getId() != currentProject.getOwnerId()}">
                                    <c:if test="${!member.isManager()}">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#E98074" class="bi bi-star" viewBox="0 0 20 20">
                                            <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                                        </svg>
                                    </c:if>
                                    <c:if test="${member.isManager()}">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#E98074" class="bi bi-star-fill" viewBox="0 0 20 20">
                                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                                        </svg>
                                    </c:if>
                                    <c:if test="${currentUser.getId() == member.getUserId()}">
                                        <form method="post">
                                            <input type="hidden" name="_method" value="delete"/>
                                            <button type="submit" class="btn btn-secondary change-role" name="deleteMember" value="${member.getUserId()}:${member.getProjectId()}">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#E98074" class="bi bi-person-x-fill" viewBox="0 0 16 16">
                                                    <path fill-rule="evenodd" d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6.146-2.854a.5.5 0 0 1 .708 0L14 6.293l1.146-1.147a.5.5 0 0 1 .708.708L14.707 7l1.147 1.146a.5.5 0 0 1-.708.708L14 7.707l-1.146 1.147a.5.5 0 0 1-.708-.708L13.293 7l-1.147-1.146a.5.5 0 0 1 0-.708z"/>
                                                </svg>
                                            </button>
                                        </form>
                                    </c:if>
                                </c:if>
                                <c:if test="${currentUser.getId() == currentProject.getOwnerId()}">
                                    <form method="post">
                                        <input type="hidden" name="_method" value="put"/>
                                        <button type="submit" class="btn btn-secondary change-role" name="changeRole" value="${member.getUserId()}:${member.getProjectId()}:${!member.isManager()}">
                                            <c:if test="${!member.isManager()}">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#E98074" class="bi bi-star" viewBox="0 0 20 20">
                                                    <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                                                </svg>
                                            </c:if>
                                            <c:if test="${member.isManager()}">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="#E98074" class="bi bi-star-fill" viewBox="0 0 20 20">
                                                    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                                                </svg>
                                            </c:if>
                                        </button>
                                    </form>
                                    <c:if test="${member.getUserId() != currentProject.getOwnerId()}">
                                        <form method="post">
                                            <input type="hidden" name="_method" value="delete"/>
                                            <button type="submit" class="btn btn-secondary change-role" name="deleteMember" value="${member.getUserId()}:${member.getProjectId()}">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#E98074" class="bi bi-person-x-fill" viewBox="0 0 16 16">
                                                    <path fill-rule="evenodd" d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6.146-2.854a.5.5 0 0 1 .708 0L14 6.293l1.146-1.147a.5.5 0 0 1 .708.708L14.707 7l1.147 1.146a.5.5 0 0 1-.708.708L14 7.707l-1.146 1.147a.5.5 0 0 1-.708-.708L13.293 7l-1.147-1.146a.5.5 0 0 1 0-.708z"/>
                                                </svg>
                                            </button>
                                        </form>
                                    </c:if>
                                </c:if>
                            </div>
                        </div>
                        <hr class="break-line a">
                    </c:forEach>
                    <c:if test="${currentUser.getId() == currentProject.getOwnerId()}">
                        <div class="flex-row d-flex justify-content-center w-100 ">
                            <button type="button" class="btn btn-outline-primary invite-user" data-bs-toggle="modal" data-bs-target="#invitation">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#D8C3A5" class="bi bi-plus-square-fill" viewBox="0 0 16 16">
                                    <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm6.5 4.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3a.5.5 0 0 1 1 0z"/>
                                </svg>
                            </button>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="invitation" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form method="POST">
                    <input type="hidden" name="_method" value="post"/>
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel-invitation">Invite User</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="form-floating">
                            <input type="text" class="form-control" name="username" placeholder="Username">
                            <label for="username">Username</label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary cancel" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-secondary invite">Invite</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="modal fade" id="editProject" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <form class="create-project" method="POST">
            <input type="hidden" name="_method" value="patch"/>
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createProjectLabel">Edit project</h5>
                    </div>
                    <div class="modal-body">
                        <div class="form-floating">
                            <input type="text" class="form-control" name="title" placeholder="Title" value=${currentProject.getTitle()}>
                            <label for="title">Title</label>
                        </div>
                        <div class="form-floating">
                            <input type="text" class="form-control" name="description" placeholder="Description" value=${currentproject.getDescription()}>
                            <label for="description">Description</label>
                        </div>
                        <div class="form-floating">
                            <input type="date" class="form-control" name="dateStart" placeholder="Start of the project" value="${currentProject.getDateStart()}">
                            <label for="dateStart">Start of the project</label>
                        </div>
                        <div class="form-floating">
                            <input type="date" class="form-control" name="dateFinish" placeholder="End of the project" value="${currentProject.getDateFinish()}">
                            <label for="dateFinish">End of the project</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="allowAll" value="${currentProject.isAllowAll()}" onclick="setValue()">
                            <label class="form-check-label" for="allowAll">
                                Allow all members to create the tasks
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="showAll" value="${currentProject.isShowAll()}" onclick="setValue()">
                            <label class="form-check-label" for="showAll">
                                Show members all tasks
                            </label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary cancel" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-secondary create">Save</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</body>
<script>
        function setValue() {
        var showAll = document.getElementsByName('showAll');
        var allowAll = document.getElementsByName('allowAll');
        if (showAll[0].checked) showAll[0].value = true;
        if (!showAll[0].checked) showAll[0].value = false;
        if (allowAll[0].checked) allowAll[0].value = true;
        if (!allowAll[0].checked) allowAll[0].value = false;
    }

</script>
</html>
