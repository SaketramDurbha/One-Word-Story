var blurring;
var lowerShown = false;
var bookShown = false;
var errorShown = false;
var toggleLower = function() {
  if(lowerShown) {
    $('#header-top-lower').removeClass("shown");
    $('.story').removeClass("lowered");
    $('#more').html("more");
    $('#more-arrow').removeClass("up");
    bookShown = true;
    toggleBook();
    lowerShown = false;
  }
  else {
    $('#header-top-lower').addClass("shown");
    $('.story').addClass("lowered");
    $('#more').html("less");
    $('#more-arrow').addClass("up");
    bookShown = true;
    toggleBook();
    lowerShown = true;
  }
}

var toggleBook = function() {
  if(bookShown) {
    $("#header-top-book").removeClass("shown");
    $('#evn-more-arrow').removeClass("up");
    $("#evn-more").html("show book");
    bookShown = false;
  }
  else {
    $("#header-top-book").addClass("shown");
    $('#evn-more-arrow').addClass("up");
    $("#evn-more").html("hide book");
    bookShown = true;
  }
}

var toggleError = function() {
  if(errorShown) {
    $('#error-message-outer').addClass("hidden");
    $("#error-message").addClass("hidden");
    setTimeout(function() {errorShown = false;}, 500);
  }
  else {
    $('#error-message-outer').removeClass("hidden");
    $("#error-message").removeClass("hidden");
    errorShown = true;
  }
}

var createError = function(text) {
  errorShown = false;
  toggleError();
  $('#error-message-text').html("&#x25C6; "+text);
}

$(document).ready(function() {
  generateNewBook(); //TEMPORARY
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
  $("#evn-more-outer").click(function() {
    toggleBook();
  })
  $('#gotit-button').click(function() {
    toggleError();
  })
})
