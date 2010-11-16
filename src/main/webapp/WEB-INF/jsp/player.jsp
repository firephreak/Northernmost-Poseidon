<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<head>
    <script type="text/javascript">
        /*
        var replace_audio = function() {
            var audio_object = document.getElementById('audio_block');

            var new_audio = document.createElement('audio');
            new_audio.setAttribute('id',"audio_block");
            new_audio.setAttribute('onended',"replace_audio()");
            new_audio.setAttribute('controls','');
            new_audio.setAttribute('autoplay','');
            new_audio.innerHTML = '<source src="http://dl.dropbox.com/u/13617849/01_seven_faces_of_him.ogg" />';
            audio_object.parentNode.insertBefore(new_audio,audio_object);

            audio_object.parentNode.removeChild(audio_object);
        };   */

        var load_array = function() {
            song_array = new Array();        //Global song URL array
            filename_array = new Array();    //Global file name array

            song_array[0] = "http://dl.dropbox.com/u/13617849/01_seven_faces_of_him.ogg"; //These will be dynamically
            song_array[1] = "http://dl.dropbox.com/u/13617849/calexico1998-06-14t02.ogg"; // generated eventually
            song_array[2] = "http://dl.dropbox.com/u/13617849/John_Holowach_-_03_-_Harlem_Byzantine_Part_1.ogg";
            song_array[3] = "http://dl.dropbox.com/u/13617849/llab007a_b-complex-amazon_rain.ogg";

            filename_array[0] = "Seven Faces of Him";
            filename_array[1] = "Calexico";
            filename_array[2] = "Harlem Byzantine (Part 1)";
            filename_array[3] = "Amazon Rain";
        };

        var play_song = function(song_number) {
            var new_audio = document.createElement('audio');
            new_audio.setAttribute('id',"audio_block");
            new_audio.setAttribute('onended',"self_delete_replace("+song_number+")");
            new_audio.setAttribute('controls','');
            new_audio.setAttribute('autoplay','');
            new_audio.innerHTML = '<source src="'+song_array[song_number]+'" />';

            var replaced_div = document.getElementById('song'+song_number);

            replaced_div.parentNode.replaceChild(new_audio,replaced_div);
        };

        var self_delete_replace = function(div_number) {
            var audio_object = document.getElementById('audio_block');

            var replacement_div = document.createElement('div');
            replacement_div.setAttribute('id',"song"+div_number);
            replacement_div.innerHTML = filename_array[div_number];

            audio_object.parentNode.replaceChild(replacement_div,audio_object);

            play_song(div_number + 1);
        };

        var generate_songlist = function() {
            var i = 0;
            while(song_array.length != i && filename_array.length != i){
                var newdivname = document.createElement('div');
                var refdiv = document.getElementById('invisible_reference');
                newdivname.setAttribute('id',"song"+i);
                newdivname.innerHTML = filename_array[i];
                refdiv.parentNode.insertBefore(newdivname,refdiv);
                i = i + 1;
            }
        }

    </script>


</head>

<layout:default>
    <jsp:attribute name="content">
        <div id="player_top_bar" class="textframe_nofloat">
            <big>Top Bar</big>
        </div>
        <div id="player_left_bar" class="textframe_left">
            <big>Here will be the file hierarchy.</big>
            <big>As well as playlists, etc.</big> <br/> <br/> <br/> <br/> <br/> <br/> <br/> <br/> <br/> <br/> <br/>
        </div>

        <!--<audio id="audio_block" controls autoplay onended="replace_audio()">
            <source src="http://dl.dropbox.com/u/13617849/calexico1998-06-14t02.ogg" />
        </audio>-->



        <!--<div id="song0">
            Seven Faces of Him
        </div>

        <div id="song1">
            Calexico
        </div>

        <div id="song2">
            Filename of song 2.
        </div>

        <div id="song3">
            Filename of song 3.
        </div>

        <div id="song4">
            Filename of song 4.
        </div>

        <div id="song5">
            Filename of song 5.
        </div>

        <div id="song6">
            Filename of song 6.
        </div>-->

        <div id="invisible_reference"></div>

        <script type="text/javascript">load_array(); generate_songlist();</script>

        <div id="player_right_bar" class="textframe_right_norightmargin">
            <big>Here will be the ad bar, etc.</big> <br/> <br/> <br/> <br/> <br/> <br/> <br/> <br/> <br/> <br/> <br/>
        </div>

        <script type="text/javascript">play_song(0);</script>


    </jsp:attribute>
</layout:default>