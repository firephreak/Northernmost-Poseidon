<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:default title="SongCloud" >
    <jsp:attribute name="content">

        <div id="quick_description" class="textframe_right">
            <span id="slogan_thing">Free Your Music.</span>
        </div>

        <div id="login" class="textframe_right" >
                Please log in using your Dropbox account. Place .mp3 files in /Public/Music/ <br /> <br />
                <a href="<c:url value="/auth" />">Login With Dropbox</a>
        </div>

    </jsp:attribute>
</layout:default>
