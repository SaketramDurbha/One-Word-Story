var wordsSincePeriod, sentencesSinceParagraph, wordsSinceChapter;

var wordsUntilPeriod, sentencesUntilParagraph, wordsUntilChapter;

wordsUntilPeriod = 8;

sentencesUntilParagraph = 3;

var sentenceCreated = false;
var paragraphCreated = false;

//get these variables from the server first

//BECAUSE SEARCH DOESN'T SUPPORT SEARCHING FOR .!!!!!!!!!

//spell check attempt at http://www.javascriptspellcheck.com/Compatibility

var searchFor = function(string, substring) {
  for(j = 0; j < string.length; j++) {
    if(string[j] === substring) {
      return j;
      break;
    }
  }
  return -1;
}

var wordsSince = function(words, wat) {
  if(sentenceCreated) {
    return 0;
  }
  else {
    for(i = words.length - 1; i > 0; i -= 1) {
      var str = words[i].toString();
      if(searchFor(str, wat) != -1) {
        return (words.length - i - 1);
        break;
      }
    }
  }
}

var sentencesSince = function(words, wat) {
  if(paragraphCreated) {
    return 0;
  }
  else {
    var sentences = 0;
    for(i = words.length - 1; i > 0; i -= 1) {
      var str = words[i];
      if(searchFor(str, '.') != -1 && (searchFor(str, wat) === -1)) {
        sentences += 1;
      }
      if(str.search(wat) != -1) {
        break;
      }
    }
    return sentences;
  }
}

var capitalize = function(word) {
  var firstCap = word[0].toUpperCase();
  var rest = word.substring(1, word.length);
  return firstCap+rest;
}

var addPeriod = function(word) {
  return (word + '.');
}

var addParagraph = function(word) {
  return ('<br><br>&nbsp;&nbsp;&nbsp;&nbsp;' + word)
}

var newSentence = function() {
  sentenceCreated = true;
  words[words.length - 1] = addPeriod(words[words.length - 1]);
  wordsUntilPeriod = Math.floor(Math.random()*17+8);
}

var newParagraph = function() {
  paragraphCreated = true;
  words[words.length - 1] = addParagraph(words[words.length - 1]);
  sentencesUntilParagraph = Math.floor(Math.random()*1+2);
}

var updateGrammar = function() {
  $('.story').removeClass('new-p');
  $('.story').removeClass('new-s');
  len = words.length - 1;
  words[len] = words[len].toLowerCase();
  sentenceCreated = false;
  paragraphCreated = false;
  if(wordsSincePeriod === 0) {
    words[len] = capitalize(words[len]);
  }
  if(wordsSincePeriod === wordsUntilPeriod) {
    newSentence();
  }
  if(sentencesSinceParagraph === sentencesUntilParagraph) {
    newParagraph();
  }
  wordsSincePeriod = wordsSince(words, '.');
  sentencesSinceParagraph = sentencesSince(words, '<br><br>&nbsp;&nbsp;&nbsp;&nbsp;');
  switch(words[len]) {
    case "i":
      words[len] = "I";
      break;
    case "im":
      words[len] = "I'm";
      break;
    case "ive":
      words[len] = "I've";
      break;
  }
  updateCounters();
}

var updateCounters = function() {
  $('#sentences-counter').html("&#x25C6; words until next sentence - "+(wordsUntilPeriod - wordsSincePeriod))
  if((wordsUntilPeriod - wordsSincePeriod) === 0) {
    // $('#sentences-counter').html("&#x25C6; words until next sentence - 0");
    setTimeout(function() {$('.story').addClass('new-s');}, 250);
  }
  $('#paragraph-counter').html("&#x25C6; sentences until next paragraph - "+(sentencesUntilParagraph - sentencesSinceParagraph))
  if((sentencesUntilParagraph - sentencesSinceParagraph) === 0) {
    setTimeout(function() {$('.story').addClass('new-p');});
  }
}

updateCounters();
