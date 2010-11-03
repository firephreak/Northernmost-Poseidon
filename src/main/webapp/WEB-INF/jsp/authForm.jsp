<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:default title="Jittr Auth Request" >
    <jsp:attribute name="content">
        <div class="textframe_left">
        <div id="instructions_login" >
            To use SongCloud, please log into your DropBox account so we can get your music! <br /> <br />

            <!--
            <ol>
                <li>Click <a href="<c:out value="${authUrl}"/>" target="_blank">here</a> to log in to Twitter to generate a PIN.</li>
                <li>Return to this page and enter the PIN below.</li>
                <li>Click Go.</li>
            </ol>
            -->
            <!--
            <form action="<c:url value="/auth"/>" method="post">
                <label for="pin">PIN:</label>
                <input id="pin" type="text" name="pin"/>
                <button type="submit">Go</button>
                <input type="image" src="../../images/buttonpic.png" height="25" alt="submit form">
            </form>
            -->

            <form action="<c:url value="/auth" />" method="post">
                <label id="usernamelabel" for="usernamefield">Username:</label>
                <input id="usernamefield" type="text" size="10" name="username" /> <br />
                <label id="passwordlabel" for="passwordfield">Password:</label>
                <input id="passwordfield" type="password" size="10" name="password" /> <br />
                <input type="checkbox" name="rememberme" value="yes" /> Remember Me!<br />
                <input type="image" src="<c:url value="/images/loginbuttontrimmed.png" />" height="40" alt="submit button" />
            </form>

            <div id="errormessage">
                <c:set var="fail" value="true" />
                <c:if test="true" var="fail">
                    Username and/or password is incorrect.
                </c:if>
            </div>
        </div>
        </div>

 
            <div id="quick_description">
                <span id="slogan_thing">Free Your Music.</span>
            </div>


        <div class="textframe_right">
            <div id="11_02_10_2" class="updates_info">
                <big>Online</big> <br />
                <em>11/02/10</em> <br/> <br/>
                Title says it all, we're up and running online. <br/> <br/>
            </div>
        </div>

        <div class="textframe_right">
            <div id="11_02_10" class="updates_info">
                <big>Home Sweet Home</big> <br />
                <em>11/02/10</em> <br/> <br/>
                We finally have a homepage! <br/> <br/>
                Granted, nothing is clickable yet, you can't log in, and you can't listen to a single song, but
                hey, everybody's gotta start somewhere right?  Right.
            </div>
        </div>

        <div class="textframe_left">
            <div id="navigation">
                <ul>
                    <a><li><em>Home</em></li></a> <br/> 
                    <a><li><em>What is SongCloud?</em></li></a> <br/>
                    <a><li><em>How Does it Work?</em></li></a> <br/>
                    <a><li><em>About Us</em></li></a> 
                </ul>
            </div>
        </div>

        <div class="textframe_right">
            <div id="testblock" class="updates_info">
                <big>Home Sweet Home</big> <br />
                <em>11/02/10</em> <br/> <br/>
                We finally have a homepage! (Tester-block)<br/> <br/>
                Granted, nothing is clickable yet, you can't log in, and you can't listen to a single song, but
                hey, everybody's gotta start somewhere right?  Right.
            </div>
        </div>

        <div class="textframe_right">
            <div id="testblock2" class="updates_info">
                <big>Home Sweet Home</big> <br />
                <em>11/02/10</em> <br/> <br/>
                We finally have a homepage! (Tester-block)<br/> <br/>
                Granted, nothing is clickable yet, you can't log in, and you can't listen to a single song, but
                hey, everybody's gotta start somewhere right?  Right.
            </div>
        </div>

                <div class="textframe_right">
            <div id="testblock3" class="updates_info">
                <big>Home Sweet Home</big> <br />
                <em>11/02/10</em> <br/> <br/>
                We finally have a homepage! (Tester block)<br/> <br/>
                Granted, nothing is clickable yet, you can't log in, and you can't listen to a single song, but
                hey, everybody's gotta start somewhere right?  Right.
            </div>
        </div>

        <div class="textframe_right">
            <div id="testblock4" class="updates_info">
                <big>Home Sweet Home</big> <br />
                <em>11/02/10</em> <br/> <br/>
                We finally have a homepage! (Tester block)<br/> <br/>
                Granted, nothing is clickable yet, you can't log in, and you can't listen to a single song, but
                hey, everybody's gotta start somewhere right?  Right.
            </div>
        </div>

        <div class="textframe_right">
            <div id="testblock5" class="updates_info">
                <big>Home Sweet Home</big> <br />
                <em>11/02/10</em> <br/> <br/>
                We finally have a homepage! (Tester block)<br/> <br/>
                Granted, nothing is clickable yet, you can't log in, and you can't listen to a single song, but
                hey, everybody's gotta start somewhere right?  Right.
            </div>
        </div>

        <div class="textframe_right">
            <div id="testblock6" class="updates_info">
                <big>Home Sweet Home</big> <br />
                <em>11/02/10</em> <br/> <br/>
                We finally have a homepage! (Tester block)<br/> <br/>
                Granted, nothing is clickable yet, you can't log in, and you can't listen to a single song, but
                hey, everybody's gotta start somewhere right?  Right.
            </div>
        </div>
        
    </jsp:attribute>
</layout:default>