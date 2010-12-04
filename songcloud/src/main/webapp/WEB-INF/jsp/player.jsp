<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<html>
<head>
	<title><c:out value="${title != null ? title : 'SongCloud Player'}"/></title>
	<link rel="stylesheet" href="<c:url value="/css/songcloud.css"/>"/>

	<script type="text/javascript">
	
	var load_array = function() {
		song_array = new Array();        //Global song URL array
		filename_array = new Array();    //Global file name array
		current_song = null;                //Global song counter

		song_array[0] = "http://dl.dropbox.com/u/13617849/01_seven_faces_of_him.ogg"; //These will be dynamically
		song_array[1] = "http://dl.dropbox.com/u/13617849/calexico1998-06-14t02.ogg"; // generated eventually
		song_array[2] = "http://dl.dropbox.com/u/13617849/John_Holowach_-_03_-_Harlem_Byzantine_Part_1.ogg";
		song_array[3] = "http://dl.dropbox.com/u/13617849/llab007a_b-complex-amazon_rain.ogg";

		filename_array[0] = "Seven Faces of Him";
		filename_array[1] = "Calexico";
		filename_array[2] = "Harlem Byzantine (Part 1)";
		filename_array[3] = "Amazon Rain";
	};

	var control_pause = function() {
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

	var control_resume = function() {
		if (current_song == null) return;
		var audio_object = document.getElementById('audio_block');
		var resume_object = document.getElementById('resume');
		var pause_object = document.createElement('a');
		pause_object.setAttribute('id',"pause");
		pause_object.setAttribute('onclick',"control_pause();");
		pause_object.innerHTML = "<img src='/images/pause.png' width='100' height='100' class='center'/>";

		if (resume_object != null) resume_object.parentNode.replaceChild(pause_object,resume_object); //If called
		audio_object.play();                                                                          // by play()
	};

	var control_next = function() {
		if (current_song == null) return;
		if (song_array[current_song + 1] == null) return;
		play_song(current_song + 1);
	};

	var control_prev = function() {
		if (current_song == null) return;
		if (current_song == 0) return;
		play_song(current_song - 1);
	};

	var play_song = function(song_number) {
		var new_audio = document.createElement('audio');
		var new_song_div = document.getElementById('song' + song_number);
		var old_song_div = document.getElementById('song' + current_song);
		new_audio.setAttribute('id',"audio_block");
		new_audio.setAttribute('onended',"play_song("+(song_number+1)+")");
		//new_audio.setAttribute('controls','');
		new_audio.setAttribute('autoplay','');
		new_audio.innerHTML = '<source src="'+song_array[song_number]+'" />';

		//var replaced_div = document.getElementById('song'+song_number);    (old version)
		var replaced_div = document.getElementById('control_swap_holder');
		if (replaced_div != null) replaced_div.parentNode.replaceChild(new_audio,replaced_div);
		else {
			var replaced_audio = document.getElementById('audio_block');
			replaced_audio.parentNode.replaceChild(new_audio,replaced_audio);
		}

		new_song_div.innerHTML += "-----Currently Playing-----";
		if (old_song_div != null) old_song_div.innerHTML = "<a onClick=play_song("+current_song+")>"+
		filename_array[current_song]+"</a>";
		// 'if statement in case it's the first song

		current_song = song_number;
		control_resume();           // So that the resume button gets replaced if need be
	};


	var generate_songlist = function() {
		var i = 0;
		while(song_array.length != i && filename_array.length != i){
			var newdivname = document.createElement('div');
			var refdiv = document.getElementById('invisible_reference');
			newdivname.setAttribute('id',"song"+i);
			newdivname.setAttribute('class',"song");
			newdivname.innerHTML = "<a onClick=play_song("+i+")>"+filename_array[i]+"</a>";
			refdiv.parentNode.insertBefore(newdivname,refdiv);
			i = i + 1;
		}
	}

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
			<div id="invisible_reference"></div>
			<script type="text/javascript">load_array(); generate_songlist();</script>
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
