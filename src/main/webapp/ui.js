var blurring;
var lowerShown = false;
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
$(document).ready(function() {
  $("#fixed-footer").hover(function() {
    clearTimeout(blurring);
    $('#word-input').focus();
  }, function() {
    blurring = setTimeout(function() {$('#word-input').blur();}, 2000);
  })
  $('#header-links-center').click(function() {
    toggleLower();
  });
});
