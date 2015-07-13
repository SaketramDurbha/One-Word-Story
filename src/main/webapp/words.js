var words = ["<br><br>&#09;Swinging", "my", "hammock", "gracefully", "back", "and", "forth,", "the", "night", "sky", "stayed", "above", "me", "for", "the", "long", "length", "as", "I", "looked", "at", "it", "in", "silence.", "But", "for", "the", "universe,", "two", "hours", "is", "absolutely", "nothing.", "I", "was", "thinking", "purely", "about"];
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

var addText = function() {
  var word = $('input[name=word-input]').val().toLowerCase();
  if((/^[a-z]+$/i.test(word) === true) && (errorShown === false)) { // Checks if word only has letters. Thanks, stackoverflow!
    words.push(word);
    $('.story').addClass('new');
    $('input[name=word-input]').val("");
    updateGrammar();
    setTimeout(function() {
      updateStory();
      $('#word-input').focus();
      setTimeout(function() {
        $('.story').removeClass('new');
      }, 250);
    }, 250);
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
      if(errorShown === false) {
        addText();
      }
      else {
        toggleError();
      }
      event.preventDefault()
  	}
  });
});
