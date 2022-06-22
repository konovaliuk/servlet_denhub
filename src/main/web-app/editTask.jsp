<%--
  Created by IntelliJ IDEA.
  User: daria
  Date: 22.06.22
  Time: 06:55
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
    <link rel="stylesheet" href="<c:url value='../../style/taskPage.css'/>">
</head>
<body class="vh-100">
<div class="d-flex flex-column">
    <%@ include file="./header.html" %>
    <div class="flex-item main">
        <div class="d-flex flex-row align-items-stretch">
            <%@ include file="./navbar.html"%>
            <form method="POST">
                <div class="flex-column d-flex justify-content-center task-edit">
                    <div class="form-floating eddit">
                        <input <c:if test="${currentUser.getId() != currentProject.getOwnerId() || currentUser.getId() != currentTask.getIdCreator()}">readonly</c:if> type="text" class="form-control" name="title" placeholder="Title" value="${currentTask.getTitle()}">
                        <label for="title">Title</label>
                    </div>
                    <div class="form-floating eddit">
                        <input type="text" class="form-control" name="instructions" placeholder="Instructions" value="${currentTask.getInstructions()}">
                        <label for="instructions">Instructions</label>
                    </div>
                    <div class="form-floating eddit">
                        <input type="date" class="form-control" name="deadline" placeholder="Deadline" value="${currentTask.getDeadline()}" min="${currentDate}">
                        <label for="deadline">Deadline</label>
                    </div>
                    <div class="form-floating eddit">
                        <select class="form-select" name="priority" aria-label="Priority">
                            <c:forEach items="${taskPriority}" var="pr">
                                <c:if test="${currentTask.getTaskPriority() == pr}">
                                    <option selected value="${pr}">${pr}</option>
                                </c:if>
                                <c:if test="${!(currentTask.getTaskPriority() == pr)}">
                                    <option value="${pr}">${pr}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <label for="priority">Priority</label>
                    </div>
                    <div class="form-floating eddit">
                        <select class="form-select" name="status" aria-label="Status">
                            <c:forEach items="${taskStatus}" var="status">
                                <c:if test="${currentTask.getTaskStatus() == status}">
                                    <option selected value="${status}">${status}</option>
                                </c:if>
                                <c:if test="${!(currentTask.getTaskStatus() == status)}">
                                    <option value="${status}">${status}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <label for="status">Status</label>
                    </div>
                    <div class="form-floating eddit">
                        <select class="form-select" name="assigned" aria-label="Assigned">
                            <c:forEach items="${currentTeam}" var="member">
                                <c:if test="${currentTask.getIdAssigned() == member.getUserId()}">
                                    <option selected value="${member.getUserId()}">${member.getUsername()}</option>
                                </c:if>
                                <c:if test="${!(currentTask.getIdAssigned() == member.getUserId())}">
                                    <option value="${member.getUserId()}">${member.getUsername()}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <label for="assigned">Assigned user</label>
                    </div>
                    <c:if test="${error != null}">
                        <div class="alert alert-danger" role="alert">
                                ${error}
                        </div>
                    </c:if>
                    <div class="flex-row d-flex form-floating eddit">
                        <button class="btn btn-lg btn-secondary save" type="submit">Save</button>
                        <a href="/project/tasks?projectId=${projectId}" class="btn btn-lg btn-secondary">Cancel</a>
                    </div>

                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
