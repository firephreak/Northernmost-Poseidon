<%@attribute name="content" required="true" fragment="true" %>
<%@attribute name="title" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
    <head>
        <title><c:out value="${title != null ? title : 'SongCloud Player'}"/></title>
        <link rel="stylesheet" href="<c:url value="/css/songcloud.css"/>"/>
    </head>
    <player_body>
        <div id="player_container">
            <div id="content">
                <jsp:invoke fragment="content"/>
            </div>
        </div>
    </player_body>
</html>
