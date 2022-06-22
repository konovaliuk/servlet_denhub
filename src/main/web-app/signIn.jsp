<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<c:url value='style/singUp.css'/>">
</head>
<body>
    <div class="form-signin">
        <form method="POST">
            <div class="form-floating">
                <input type="text" class="form-control" name="username" placeholder="Username" value=${param.username}>
                <label for="username">Username</label>
            </div>
            <div class="form-floating">
                <input type="text" class="form-control" name="password" placeholder="Password" value=${param.password}>
                <label for="password">Password</label>
            </div>
            <c:if test="${error != null}">
                <div class="alert alert-danger" role="alert">
                        ${error}
                </div>
            </c:if>
            <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
            <p class="mt-2 text-muted text-center">Don&apos;t have an account? <a href="<c:url value='/signup'/>">Sign up</a></p>
        </form>
    </div>
</body>
</html>