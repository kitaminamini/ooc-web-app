<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <script>
        function removeConfirm(){
            return confirm("Are you sure you want remove this user?");
        }

        function logoutConfirm(){
                return confirm("Are you sure you want to logout?");
        }
    </script>
    <body>
        <h2>Welcome, ${username}</h2>
        <p>${error}</p>
        <h2>User List</h2>
        <table width="59%" border="1">
            <tr>
                <th>username</th>
                <th>password</th>
                <th>nickname</th>
                <th></th>
                <th></th>
            </tr>

            <c:forEach items="${users}" var="user">
            <tr>
              <td><c:out value="${user.username}" /></td>
              <td><c:out value="${user.password}" /></td>
              <td><c:out value="${user.nickname}" /></td>
              <td><form action="/edituser" method="get">
              <input type="hidden" value="${user.username}", name="username">
              <input type="submit" value="Edit" name="edit">
              </form></td>
              <td><form action="/userlist" method="post" onsubmit="return removeConfirm()">
              <input type="hidden" value="${user.username}", name="username">
              <input type="submit" value="Remove" name="remove">
              </form></td>
            </tr>
            </c:forEach>
        </table>
        <form action="/adduser" method="get">
                    <input type="submit" value="Add User" name="Add User">
        </form>
        <form action = "/index.jsp" method = "post" onsubmit="return logoutConfirm()">
                        <input type="submit" value="Logout" name="Logout">
        </form>
    </body>
</html>