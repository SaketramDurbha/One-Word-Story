var wordsSincePeriod, sentencesSinceParagraph, paragraphsSinceChapter;

var wordsUntilPeriod, sentencesUntilParagraph, paragraphsUntilChapter;

wordsUntilPeriod = 8;

sentencesUntilParagraph = 3;

paragraphsUntilChapter = 3;

var amtOfChapters = 24;

var sentenceCreated = false;
var paragraphCreated = false;
var chapterCreated = false;

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

function romanize (num) { // PRAISE THE HOLY STACKOVERFLOW
    if (!+num)
        return false;
    var digits = String(+num).split(""),
        key = ["","C","CC","CCC","CD","D","DC","DCC","DCCC","CM",
               "","X","XX","XXX","XL","L","LX","LXX","LXXX","XC",
               "","I","II","III","IV","V","VI","VII","VIII","IX"],
        roman = "",
        i = 3;
    while (i--)
        roman = (key[+digits.pop() + (i * 10)] || "") + roman;
    return Array(+digits.join("") + 1).join("M") + roman;
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

var paragraphsSince = function(words, wat) {
  if(chapterCreated) {
    return 0;
  }
  else {
    var sentences = 0;
    for(i = words.length - 1; i > 0; i -= 1) {
      var str = words[i];
      if(str.search('<br><br>&nbsp;&nbsp;&nbsp;&nbsp;') != -1 && (searchFor(str, wat) === -1)) {
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

var addChapter = function(num) {
  var randAdj = Math.floor(Math.random()*adjs.length);
  var randNoun = Math.floor(Math.random()*nouns.length);
  var randTheme = Math.floor(Math.random()*themes.length);
  setTimeout(function() {
    $("#chapter").html("chapter "+num+": The "+adjs[randAdj]+" "+nouns[randNoun]);
    $('#theme').html("current mood: "+themes[randTheme].toLowerCase());
  }, 500);
  return ("<span class='chapter' id='chapter"+num+"'>CHAPTER&nbsp; "+romanize(num)+": The "+adjs[randAdj]+" "+nouns[randNoun]+"<br><span class='themes'>mood: "+themes[randTheme].toLowerCase()+"</span><br><br></span>");
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

var newChapter = function() {
  chapterCreated = true;
  words[words.length - 1] = addChapter(amtOfChapters);
  paragraphsUntilChapter = 2;
  disableInput();
  setTimeout(function() {
    $('#chapter'+amtOfChapters).addClass("hidden");
  }, 255);
  setTimeout(function() {
    $('#chapter'+amtOfChapters).removeClass("hidden");
    $('#chapter'+amtOfChapters).css("transition", "opacity 2s");
    $('#chapter'+amtOfChapters).css("transition", "opacity 2s");
  }, 500);
  setTimeout(function() {enableInput()}, 2000);
}

var updateGrammar = function() {
  $('.story').removeClass('new-p');
  $('.story').removeClass('new-s');
  $('.story').removeClass('new-c');
  len = words.length - 1;
  words[len] = words[len].toLowerCase();
  sentenceCreated = false;
  paragraphCreated = false;
  chapterCreated = false;
  if(((paragraphsUntilChapter - paragraphsSinceChapter) === 1) && (sentencesUntilParagraph - sentencesSinceParagraph) === 0) {
    newChapter();
  }
  if(wordsSincePeriod === 0 || (words[len - 1].search("CHAPTER")) != -1) {
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
  paragraphsSinceChapter = paragraphsSince(words, "CHAPTER");
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
  $('#chapter-counter').html("&#x25C6; paragraphs until next chapter - "+(paragraphsUntilChapter - paragraphsSinceChapter))
  if(((paragraphsUntilChapter - paragraphsSinceChapter) === 1) && (sentencesUntilParagraph - sentencesSinceParagraph) === 0) {
    setTimeout(function() {$('.story').removeClass('new-p');$('.story').addClass('new-c');amtOfChapters += 1;});
  }
}

updateCounters();
