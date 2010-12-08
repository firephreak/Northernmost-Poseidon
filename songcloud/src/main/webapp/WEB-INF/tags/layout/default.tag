<%@attribute name="content" required="true" fragment="true" %>
<%@attribute name="title" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
    <head>
        <title><c:out value="${title != null ? title : 'SongCloud'}"/></title>
        <link rel="stylesheet" href="<c:url value="../../../css/songcloud.css"/>"/>
    </head>
    <body>
        <div id="container">
            <div id="header">
                <div id="logo">
                    <img id="songcloud_top_logo" src="<c:url value="/images/songcloud256player.png" />" height="170" />
                </div>
                <div id="title">SONGCLOUD</div>
            </div>
            <div id="content">
                <jsp:invoke fragment="content"/>
            </div>
        </div>
    </body>
</html>