var words = ["<br><br>&nbsp;&nbsp;&nbsp;&nbsp;Swinging", "my", "hammock", "gracefully", "back", "and", "forth,", "the", "night", "sky", "stayed", "above", "me", "for", "the", "long", "length", "as", "I", "looked", "at", "it", "in", "silence.", "But", "for", "the", "universe,", "two", "hours", "is", "absolutely", "nothing.", "I", "was", "thinking", "purely", "about"];
var blurring;
var updateStory = function() {
	var tempStory = "";
	for(i = 0; i < words.length; i++) {
		tempStory = (tempStory + " " + words[i]);
	}
	$('.story').html(tempStory);
	var scrollGoal = $('#everything').height();
	$('#everything').animate({scrollTop: scrollGoal}, 250);
}

var disableInput = function() {
  $('#word-input').blur();
}

var addText = function() {
  var word = $('input[name=word-input]').val().toLowerCase();
  if((/^[a-z]+$/i.test(word) === true) && (errorShown === false)) { // Checks if word only has letters. Thanks, stackoverflow!
    disableInput();
    $('.story').addClass('new');
    $('input[name=word-input]').val("");
    updateGrammar();
    setTimeout(function() {
      $('#word-input').focus();
      setTimeout(function() {
        $('.story').removeClass('new');
      }, 250);
    }, 250);
    sendWord(word);
  }
  else {
    createError("your word has invalid characters or doesn't exist");
  }
  return false;
}

var invalidChars = false;

var makeTextValid = function() {
	var word = $('input[name=word-input]').val().toLowerCase();
	invalidChars = false;
	if(/^[a-z]+$/i.test(word) === false) { // Checks if word only has letters. Thanks, stackoverflow!
		invalidChars = true;
		$('#word-input').addClass("invalid");
		$('#invalid').removeClass('hidden');
		if(word === "") {
			$('#word-input').removeClass("invalid");
			$('#invalid').addClass('hidden');
		}

	}
	else {
		$('#word-input').removeClass("invalid");
		$('#invalid').addClass('hidden');
	}
	$('input[name=word-input]').val(word);
}
function sendWord(word)
{
	var xmlhttp;
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState===4 )
		{
			if (xmlhttp.responseText === "0")
			{

			}
		}
	}
	xmlhttp.open("POST","/ws/words/add",true);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.send("word="+word);
}
var webSocket;
$(document).ready(function() {
  updateStory();
  $(document).keyup(function() {
    makeTextValid();
  })
  $('#word-input').keypress(function(event){
    clearTimeout(blurring);
    $('#fixed-footer').addClass("hovered");
    blurring = setTimeout(function() {/*$('#word-input').blur();*/$('#fixed-footer').removeClass("hovered")}, 2000);
  	var keycode = (event.keyCode ? event.keyCode : event.which);
  	if(keycode == '13') {
      event.preventDefault();
      if(errorShown === false) {
        addText();
      }
      else {
        toggleError();
      }
  	}
  });

  var messages = document.getElementById("messages");



  if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
    return;
  }
  // Create a new instance of the websocket
  webSocket = new WebSocket("ws://76.25.41.48/websocket/generic");

  webSocket.onopen = function(event){
    if(event.data === undefined)
      return;

    writeResponse(event.data);
  };

  webSocket.onmessage = function(event){
    var json = JSON.parse(event.data);
    words.push(json.message);
    $('.story').addClass('new');
    updateGrammar();
    setTimeout(function() {
      updateStory();
      $('input[name=word-input]').val("");
      $('#word-input').focus();
      setTimeout(function() {
        $('.story').removeClass('new');
      }, 250);
    }, 250);
  };

  webSocket.onclose = function(event){
  };

});
window.onbeforeunload = function() {
	closeSocket();
}
function closeSocket(){
	webSocket.close();
}
