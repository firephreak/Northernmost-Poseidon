<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<html>
<head>
    <title><c:out value="${title != null ? title : 'SongCloud Player'}"/></title>
    <link rel="stylesheet" href="<c:url value="/css/songcloud.css"/>"/>

    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/prototype/1.6.1.0/prototype.js"></script>
    <script type="text/javascript">


        window.onresize = resize;

        function resize(){
            var myWidth = 0, myHeight = 0;
            myWidth = window.innerWidth;
            myHeight = window.innerHeight;

            var player_left = document.getElementById('player_left');
            player_left.setAttribute('style',"height:"+(myHeight-50)+"px;");

            var player_center_view = document.getElementById('player_center_view');
            player_center_view.setAttribute('style',"width:"+(myWidth-409)+'px; height:'+(myHeight-270)+"px;");

            var box_table_a = document.getElementById('box-table-a');
            box_table_a.setAttribute('style',"width:"+(myWidth-409)+'px;');

            var control_float = document.getElementById('control_float');
            control_float.setAttribute('style',"width:"+(myWidth-419)+"px;");

            var player_right = document.getElementById('player_right');
            player_right.setAttribute('style',"height:"+(myHeight-24)+"px;");

            var directory = document.getElementById('directory_structure');
            directory.setAttribute('style',"height:"+(myHeight-220)+"px;");
        }

        SONGS = [];
        current_song = null;

        window.onload = function () {
            resize();
            var A = [], B = [], C = [];
            var as = $$("#player_center_view a");
            for (var i = 0; i < as.length; i++) {
                var href = as[i].getAttribute("href");
                var obj = { href: href, a: as[i], tr: as[i].parentNode.parentNode };
                SONGS.push(obj);
                if (i <= Math.round(as.length / 3)) {
                    A.push(obj);
                } else if (i <= Math.round(as.length / 3) * 2) {
                    B.push(obj);
                } else {
                    C.push(obj);
                }
                as[i].update(href.substring(href.lastIndexOf("/") + 1));
                as[i].onclick = mk_onclick(i);
                as[i].parentNode.parentNode.onclick = mk_onclick(i);
            }
            lookup(A);
            lookup(B);
            lookup(C);
        };

        function mk_onclick(i) { return function () { play_song(i); return false; }; };

        function lookup(as) {
            if (as.length > 0) {
                var url = encodeURI(as[0].href);
                var tds = as[0].tr.getElementsByTagName("td");
                var td_name = tds[0], td_artist = tds[1], td_album = tds[2];
                new Ajax.Request("<c:url value="/id3" />", {
                    parameters: { url: url },
                    onSuccess: function (transport) {
                        var track = transport.responseXML.getElementsByTagName("track")[0];
                        var g = function (a) { return track.getElementsByTagName(a)[0].childNodes[0].nodeValue; };
                        as[0].a.update(g("name"));
                        td_artist.update(g("artist"));
                        td_album.update(g("album"));
                        lookup(as.slice(1));
                    }
                });
            }

            return false;
        };

        var control_pause = function () {
            if (current_song == null) return;
            var audio_object = document.getElementById('audio_block');
            var pause_object = document.getElementById('pause');
            var resume_object = document.createElement('a');
            resume_object.setAttribute('id',"resume");
            resume_object.setAttribute('onclick',"control_resume();");
            resume_object.innerHTML = '<img src="<c:url value="/images/play.png "/>" width="100" height="100" class="center"/>';

            pause_object.parentNode.replaceChild(resume_object,pause_object);
            audio_object.pause();
        };

        var control_resume = function () {
            if (current_song == null) return;
            var audio_object = document.getElementById('audio_block');
            var resume_object = document.getElementById('resume');
            var pause_object = document.createElement('a');
            pause_object.setAttribute('id',"pause");
            pause_object.setAttribute('onclick',"control_pause();");
            pause_object.innerHTML = '<img src="<c:url value="/images/pause.png"/>" width="100" height="100" class="center"/>';

            if (resume_object != null) resume_object.parentNode.replaceChild(pause_object,resume_object);
            audio_object.play();
        };

        var control_next = function () {
            if (current_song == null || current_song + 1 >= SONGS.length) return;
            play_song(current_song + 1);
        };

        var control_prev = function () {
            if (current_song == null || current_song == 0) return;
            play_song(current_song - 1);
        };

        var play_song = function (index) {
            var new_audio = document.createElement("audio");
            new_audio.setAttribute("id", "audio_block");
            new_audio.setAttribute("onended", "play_song(" + (index + 1) + ")");
            new_audio.setAttribute("autoplay", "");
            new_audio.innerHTML = '<source src="' + SONGS[index].href + '" />';

            var replaced_div = $("control_swap_holder");
            if (replaced_div != null) {
                replaced_div.parentNode.replaceChild(new_audio,replaced_div);
            } else {
                var replaced_audio = $("audio_block");
                replaced_audio.parentNode.replaceChild(new_audio,replaced_audio);
            }

            if (current_song != null) {
                SONGS[current_song].tr.removeClassName("playing");
            }

            current_song = index;
            SONGS[current_song].tr.addClassName("playing");

            control_resume();
        };
    </script>
</head>
<body>
<div id="mainContainer">
    <div id="player_right">
        Ad Bar
    </div>

    <div id="application">
        <div id="main">
            <div id="player_topbar">
                <center><FONT COLOR="FFFFFF"><big>S O N G C L O U D</big></font></center>
            </div>
            <div id="player_left">
                <img id="songcloud_top_logo" src="<c:url value="/images/songcloud256player.png" />" height="170" class="center"/>
                <div id="directory_structure">
                    <div id="dir1" class="directory">
                        Directory
                    </div>
                    <div id="dir2" class="directory">
                        Directory
                    </div>
                    <div id="dir3" class="directory">
                        Directory
                    </div>
                    <div id="dir4" class="directory">
                        Directory
                    </div>
                    <div id="dir5" class="directory">
                        Directory
                    </div>
                    <div id="dir6" class="directory">
                        Directory
                    </div>
                    <div id="dir7" class="directory">
                        Directory
                    </div>
                    <div id="dir8" class="directory">
                        Directory
                    </div>
                </div>
            </div>
            <div id="player_center">
                <div id="player_center_main">
                <div id="player_center_path">
                    Music > Artist > Album
                </div>
                <div id="player_center_view">
                    <table id="box-table-a">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Artist</th>
                            <th>Album</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="song" items="${songs}">
                            <tr>
                            <td><a href="<c:out value="${song}"/>"><c:out value="${song}"/></a></td>
                            <td><!-- will be filled in by id3 lookup --></td>
                            <td><!-- will be filled in by id3 lookup --></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div id="control_float">
                    <div id="control_box">
                        <!--This is where the controls will go-->
                        <div id="control_swap_holder"></div>
                        <a id="pause" onclick="control_pause();"> <img src="<c:url value="/images/pause.png"/>" width="100" height="100" class="center"/> </a>
                        <a id="prev" onclick="control_prev();"> <img src="<c:url value="/images/skip-back.png"/>" width="80" height="80"/> </a>
                        <a id="next" onclick="control_next();"> <img src="<c:url value="/images/skip-forward.png"/>" width="80" height="80" /> </a>
                    </div>
                </div>
                    </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
