<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<script>
    function logoutConfirm(){
        return confirm("Are you sure you want to logout?");
    }
</script>
<body>
<h2>Welcome, ${username}</h2>
    <form action = "/index.jsp" method = "post" onsubmit="return logoutConfirm()">
        <input type="submit" value="Logout" name="Logout">
    </form>
    <form action="/adduser" method="get">
                <input type="submit" value="Add User" name="Add User">
    </form>
    <form action="/userlist" method="get">
                <input type="submit" value="View User List" name="View User List">
    </form>
</body>
</html>
