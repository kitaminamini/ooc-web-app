<html>
    <body>
        <h2>Add User</h2>
        <p>${error}</p>
        <form action="/adduser" method="post">
            Username:<br/>
            <input type="text" name="username"/>
            <br/>
            Password:<br/>
            <input type="password" name="password">
            <br/>
            Nickname:<br/>
            <input type="text" name="nickname">
            <br><br>
            <input type="submit" value="Submit">
        </form>
        <form action="/userlist" method="get">
                        <input type="submit" value="Back to User List" name="Back to User List">
        </form>
    </body>
</html>