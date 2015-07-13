var blurring;
var lowerShown = false;
var errorShown = false;
var toggleLower = function() {
  if(lowerShown) {
    $('#header-top-lower').removeClass("shown");
    $('.story').removeClass("lowered");
    $('#more').html("more");
    $('#more-arrow').removeClass("up");
    lowerShown = false;
  }
  else {
    $('#header-top-lower').addClass("shown");
    $('.story').addClass("lowered");
    $('#more').html("less");
    $('#more-arrow').addClass("up");
    lowerShown = true;
  }
}

var toggleError = function() {
  if(errorShown) {
    $('#error-message-outer').addClass("hidden");
    setTimeout(function() {errorShown = false;}, 500);
  }
  else {
    $('#error-message-outer').removeClass("hidden");
    errorShown = true;
  }
}

var createError = function(text) {
  errorShown = false;
  toggleError();
  $('#error-message-text').html("&#x25C6; "+text);
}

$(document).ready(function() {
  $("#fixed-footer").hover(function() {
    clearTimeout(blurring);
    $('#word-input').focus();
    $('#fixed-footer').addClass("hovered");
  }, function() {
    blurring = setTimeout(function() {/*$('#word-input').blur();*/$('#fixed-footer').removeClass("hovered")}, 2000);
  })
  $('#header-links-center').click(function() {
    toggleLower();
  })
  $('#gotit-button').click(function() {
    toggleError();
  })
})
