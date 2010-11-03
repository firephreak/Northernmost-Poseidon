<%@attribute name="content" required="true" fragment="true" %>
<%@attribute name="title" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
    <head>
        <title><c:out value="${title != null ? title : 'Jittr'}"/></title>
        <link rel="stylesheet" href="<c:url value="/css/jittr.css"/>"/>
    </head>
    <body>
        <div id="container">
            <div id="header">
                <div id="logo">
                    <img id="songcloud_top_logo" src="../../../images/songcloud256.png" height="170" />
                </div>
                <div id="title">SongCloud</div>
            </div>
            <div id="content">
                <jsp:invoke fragment="content"/>
            </div>

        </div>
        <div id="footer">
            <div id="footer_info">
                    Cool info goes here.
            </div>
        </div>
    </body>
</html>
