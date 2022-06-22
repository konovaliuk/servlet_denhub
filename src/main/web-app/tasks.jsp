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
    <link rel="stylesheet" href="<c:url value='../style/taskPage.css'/>">
</head>
<body class="vh-100">
<div class="d-flex flex-column">
    <%@ include file="./header.html" %>
    <div class="flex-item main">
        <div class="d-flex flex-row align-items-stretch">
            <%@ include file="./navbar.html"%>

            <div class="flex-column d-flex justify-content-center project">
                <c:if test="${error != null}">
                    <div class="flex-row alert alert-danger error" role="alert">
                            ${error}
                    </div>
                </c:if>
                <div class="d-flex row flex-row overflow-auto flex-nowrap tasks-grid">
                    <c:forEach items="${taskStatus}" var="status">
                        <div class="d-flex flex-column overflow-auto justify-content-center w-25 tasks-list ${status.toString().toLowerCase()}">
                            <div class="d-flex justify-content-center list-title">
                                ${status.toString().toLowerCase().replace("_", " ")}
                            </div>
                            <ul class="flex-column d-flex w-100 h-100 tasks">
                                <c:if test='${taskGrid.containsKey(status.toString().toLowerCase())}'>
                                        <c:forEach items="${taskGrid.get(status.toString().toLowerCase())}" var="task">
                                            <div class="d-flex flex-column task" style="background-color: ${priorityColors.get(task.getTaskPriority().toString())}">
                                                <div class="flex-row d-flex">
                                                    <button class="w-100 open-task" type="submit" data-bs-toggle="modal" data-bs-target="#task" data-bs-title="${task.getTitle()}"
                                                    data-bs-instructions="${task.getInstructions()}" data-bs-dateCreated="${task.getDateCreated()}" data-bs-deadline="${task.getDeadline()}"
                                                    data-bs-usernameCreator="${task.getUsernameCreator()}" data-bs-usernameAssigned="${task.getUsernameAssigned()}"
                                                    data-bs-taskPriority="${task.getTaskPriority().toString().toLowerCase()}">
                                                        <div class="d-flex justify-content-center">
                                                                ${task.getTitle()}
                                                        </div>
                                                    </button>
                                                    <c:if test="${task.getIdCreator() == currentUser.getId() || currentProject.getOwnerId() == currentUser.getId() || task.getIdAssigned() == currentUser.getId()}">
                                                        <a class="edit-task" href="/project/tasks/task?projectId=${currentProject.getId()}&taskId=${task.getId()}">
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#EAE7DC" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                                                <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                                            </svg>
                                                        </a>
                                                        <form method="post">
                                                            <input type="hidden" name="_method" value="delete"/>
                                                            <button name="delete-task" class="delete-task" type="submit" value="${task.getId()}">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-lg" viewBox="0 0 16 16">
                                                                    <path d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8 2.146 2.854Z"/>
                                                                </svg>
                                                            </button>
                                                        </form>

                                                    </c:if>
                                                </div>
                                                <div class="flex-row">
                                                    <div class="flex-item">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hourglass-split" viewBox="0 0 16 16">
                                                            <path d="M2.5 15a.5.5 0 1 1 0-1h1v-1a4.5 4.5 0 0 1 2.557-4.06c.29-.139.443-.377.443-.59v-.7c0-.213-.154-.451-.443-.59A4.5 4.5 0 0 1 3.5 3V2h-1a.5.5 0 0 1 0-1h11a.5.5 0 0 1 0 1h-1v1a4.5 4.5 0 0 1-2.557 4.06c-.29.139-.443.377-.443.59v.7c0 .213.154.451.443.59A4.5 4.5 0 0 1 12.5 13v1h1a.5.5 0 0 1 0 1h-11zm2-13v1c0 .537.12 1.045.337 1.5h6.326c.216-.455.337-.963.337-1.5V2h-7zm3 6.35c0 .701-.478 1.236-1.011 1.492A3.5 3.5 0 0 0 4.5 13s.866-1.299 3-1.48V8.35zm1 0v3.17c2.134.181 3 1.48 3 1.48a3.5 3.5 0 0 0-1.989-3.158C8.978 9.586 8.5 9.052 8.5 8.351z"/>
                                                        </svg>
                                                        :  ${task.getDeadline()}
                                                    </div>
                                                    <div class="flex-item">
                                                        assigned: ${task.getUsernameAssigned()}
                                                    </div>
                                                </div>

                                            </div>
                                        </c:forEach>
                                </c:if>
                            </ul>
                        </div>
                        <div class="vertical-line"></div>
                    </c:forEach>
                </div>
                <div class="flex-row">
                    <c:if test="${currentProject.isAllowAll() || isManager}">
                        <button type="button" class="btn btn-secondary add-task" data-bs-toggle="modal" data-bs-target="#createTask">
                            Add new task
                        </button>
                    </c:if>
                    <a href="/../project?id=${currentProject.getId()}" class="btn btn-secondary add-task">
                        Go back
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="createTask" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <form class="create-project" method="POST">
        <input type="hidden" name="_method" value="post"/>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="d-flex justify-content-center w-100 modal-title" id="createTaskLabel">Add new task</h5>
                </div>
                <div class="modal-body">
                    <div class="form-floating">
                        <input type="text" class="form-control" name="title" placeholder="Title">
                        <label for="title">Title</label>
                    </div>
                    <div class="form-floating">
                        <input type="text" class="form-control" name="instructions" placeholder="Instructions">
                        <label for="instructions">Instructions</label>
                    </div>
                    <div class="form-floating">
                        <input type="date" class="form-control" name="deadline" placeholder="Deadline" min="${currentDate}">
                        <label for="deadline">Deadline</label>
                    </div>
                    <div class="form-floating">
                        <select class="form-select" name="priority" aria-label="Priority">
                            <c:forEach items="${taskPriority}" var="pr">
                                <option value="${pr}">${pr}</option>
                            </c:forEach>
                        </select>
                        <label for="priority">Priority</label>
                    </div>
                    <div class="form-floating">
                        <select class="form-select" name="assigned" aria-label="Assigned">
                            <c:forEach items="${currentTeam}" var="member">
                                <option value="${member.getUserId()}">${member.getUsername()}</option>
                            </c:forEach>
                        </select>
                        <label for="assigned">Assigned user</label>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary cancel" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-secondary add">Add</button>
                </div>
            </div>
        </div>
    </form>
</div>

<div class="modal fade" id="task" tabindex="-1" aria-labelledby="taskLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="d-flex justify-content-center w-100 modal-title" id="exampleModalLabel">Task</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="d-flex flex-column modal-body task-body">
                <div class="flex-item instructions"></div>
                <hr class="break-line">
                <div class="flex-row d-flex">
                    <div class="flex-item w-50 deadline"></div>
                    <div class="flex-item priority"></div>
                </div>
                <hr class="break-line">
                <div class="flex-item assigned"></div>
                <hr class="break-line">
                <div class="flex-row d-flex">
                    <div class="flex-item w-50 created"></div>
                    <div class="flex-item creator"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
        var taskModal = document.getElementById('task')
        taskModal.addEventListener('show.bs.modal', function (event) {

        var button = event.relatedTarget

        var title = button.getAttribute('data-bs-title')
        var instructions = button.getAttribute('data-bs-instructions')
        var dateCreated = button.getAttribute('data-bs-dateCreated')
        var deadline = button.getAttribute('data-bs-deadline')
        var usernameCreator = button.getAttribute('data-bs-usernameCreator')
        var usernameAssigned = button.getAttribute('data-bs-usernameAssigned')
        var taskPriority = button.getAttribute('data-bs-taskPriority')
        var taskStatus = button.getAttribute('data-bs-taskStatus')

        var titleField = taskModal.querySelector('.modal-title')
        var instructionsField = taskModal.querySelector('.instructions')
        var deadlineField = taskModal.querySelector('.deadline')
        var priorityField = taskModal.querySelector('.priority')
        var assignedField = taskModal.querySelector('.assigned')
        var createdField = taskModal.querySelector('.created')
        var creatorField = taskModal.querySelector('.creator')

        titleField.textContent = title
        instructionsField.textContent = "Instructions: " + instructions
        deadlineField.textContent = "deadline: " + deadline
        priorityField.textContent = "priority: " + taskPriority
        assignedField.textContent = "assigned: " + usernameAssigned
        createdField.textContent = "created: " + dateCreated
        creatorField.textContent = "by: " + usernameCreator
        })
        </script>
</html>
