<html>
    <body>
        <h2>Register</h2>
        <p>${error}</p>
        <form action="/register" method="post">
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
    </body>
</html>