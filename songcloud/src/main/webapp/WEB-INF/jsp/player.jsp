<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<html>
<head>
    <title><c:out value="${title != null ? title : 'SongCloud Player'}"/></title>
    <link rel="stylesheet" href="<c:url value="/css/songcloud.css"/>"/>
    
    <style type="text/css">
        table { width: 100%; }
        table a { color: black; }
        .playing { font-style: italic; font-weight: bold; }
    </style>
    
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/prototype/1.6.1.0/prototype.js"></script>
    <script type="text/javascript">
        SONGS = [];
        current_song = null;
     
        window.onload = function () {
            var as = $$("#player_center_view a");
            for (var i = 0; i < as.length; i++) {
                var href = as[i].getAttribute("href");
                SONGS.push({ href: href, a: as[i], tr: as[i].parentNode.parentNode });
                as[i].update(href.substring(href.lastIndexOf("/") + 1));
                as[i].onclick = mk_onclick(i);
            }
            lookup(SONGS);
        };
        
        function mk_onclick(i) { return function () { play_song(i); return false; }; };
        
        function lookup(as) {
            if (as.length > 0) {
                var url = encodeURI(as[0].href);
                var tds = as[0].tr.getElementsByTagName("td");
                var td_name = tds[0], td_artist = tds[1], td_album = tds[2];
                new Ajax.Request("id3", {
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
            resume_object.innerHTML = "<img src='/images/play.png' width='100' height='100' class='center'/>";

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
            pause_object.innerHTML = "<img src='/images/pause.png' width='100' height='100' class='center'/>";

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
    <div id="player_top_bar" class="player_topbar">
        <center><FONT COLOR="FFFFFF"><big>S O N G C L O U D</big></font></center>
    </div>
    <div id="player_left_bar" class="player_left">
        <img id="songcloud_top_logo" src="<c:url value="/images/songcloud256player.png" />" height="170" class="center"/>
        <div id="directory" class="directory_structure">
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
            <div id="dir9" class="directory">
                Directory
            </div>
            <div id="dir10" class="directory">
                Directory
            </div>
            <div id="dir11" class="directory">
                Directory
            </div>
            <div id="dir12" class="directory">
                Directory
            </div>
            <div id="dir13" class="directory">
                Directory
            </div>
            <div id="dir14" class="directory">
                Directory
            </div>
            <div id="dir15" class="directory">
                Directory
            </div>
            <div id="dir16" class="directory">
                Directory
            </div>
            <div id="dir17" class="directory">
                Directory
            </div>
            <div id="dir18" class="directory">
                Directory
            </div>
            <div id="dir19" class="directory">
                Directory
            </div>
            <div id="dir20" class="directory">
                Directory
            </div>
            <div id="dir21" class="directory">
                Directory
            </div>
            <div id="dir22" class="directory">
                Directory
            </div>
            <div id="dir23" class="directory">
                Directory
            </div>
            <div id="dir24" class="directory">
                Directory
            </div>
            <div id="dir25" class="directory">
                Directory
            </div>
            <div id="dir26" class="directory">
                Directory
            </div>
            <div id="dir27" class="directory">
                Directory
            </div>
            <div id="dir28" class="directory">
                Directory
            </div>
        </div>
    </div>
    <div id="player_center" class="player_center">
        <div id="center_path" class="player_center_path">
            Public > Artist > Album
        </div>
        <div id="player_center_view" class="player_center_songs">
            <table>
              <c:forEach var="song" items="${songs}">
                <tr>
                    <td><a href="<c:out value="${song}"/>"><c:out value="${song}"/></a></td>
                    <td><!-- will be filled in by id3 lookup --></td>
                    <td><!-- will be filled in by id3 lookup --></td>
                </tr>
              </c:forEach>
            </table>
        </div>
        <div id="control_float">
            <div id="control_box">
                <!--This is where the controls will go-->
                <div id="control_swap_holder"></div>
                <a id="pause" onclick="control_pause();"> <img src="/images/pause.png" width="100" height="100" class="center"/> </a>
                <a id="prev" onclick="control_prev();"> <img src="/images/skip-back.png" width="80" height="80"/> </a>
                <a id="next" onclick="control_next();"> <img src="/images/skip-forward.png" width="80" height="80" /> </a>
            </div>
        </div>
    </div>
    <div id="player_right_bar" class="player_right">
        Ad Bar
    </div>
</body>
</html>