<html>
    <body>
        <h2>Edit User</h2>
        <p>${error}</p>
        <form action="/edituser" method="post">
            Username:<br/>
            <input type="text" value="${usern}" name="username"/>
            <br/>
            Password:<br/>
            <input type="password" value="${pass}" name="password">
            <br/>
            Nickname:<br/>
            <input type="text" value="${nick}" name="nickname">
            <br><br>
            <input type="submit" value="Submit">
            <input type="hidden" value="${usern}", name="usernameToEdit">
        </form>
    </body>
</html>