var wordsSincePeriod, setnencesSinceParagraph, wordsSinceChapter;

var wordsUntilPeriod, sentencesUntilParagraph, wordsUntilChapter;

wordsUntilPeriod = 8;

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
  for(i = words.length - 1; i > 0; i -= 1) {
    var str = words[i].toString();
    if(searchFor(str, wat) != -1) {
      return (words.length - i - 1);
      break;
    }
    else {
    }
  }
}

var sentencesSince = function(words, wat) {
  var sentences = 0;
  for(i = words.length - 1; i > 0; i -= 1) {
    var str = words[i];
    if(searchFor(str, '.') != -1 && (searchFor(str, wat) === -1)) {
      sentences += 1;
    }
    if(searchFor(str, wat) != -1) {
      break;
    }
  }
  return sentences;
}

var capitalize = function(word) {
  var firstCap = word[0].toUpperCase();
  var rest = word.substring(1, word.length);
  return firstCap+rest;
}

var addPeriod = function(word)  {
  return (word + '.');
}

var addParagraph = function(word) {
  return (word + '<br><br>&#09;')
}

var newSentence = function() {
  setnencesSincePeriod = 0;
  words[words.length - 1] = addPeriod(words[words.length - 1]);
  wordsUntilPeriod = Math.floor(Math.random()*17+8);
}

var newParagraph = function() {
  sentencesSinceParagraph = 0;
  words[words.length - 1] = addParagraph(words[words.length - 1]);
  sentencesUntilParagraph = Math.floor(Math.random()*1+2);
}

var updateGrammar = function() {
  len = words.length - 1;
  words[len] = words[len].toLowerCase();
  wordsSincePeriod = wordsSince(words, '.');
  if(wordsSincePeriod === 1) {
    words[len] = capitalize(words[len]);
  }
  if(wordsSincePeriod === wordsUntilPeriod) {
    newSentence();
  }
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
  if((wordsUntilPeriod - wordsSincePeriod) <= 0) {
    $('#sentences-counter').html("&#x25C6; words until next sentence - 0");
  }
}

updateCounters();
