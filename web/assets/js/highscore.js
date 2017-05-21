/**
 * Created by The Black Sheep
 */

var highscoresArray;


var highscoresArray;

var difficultyModifier = {
    EASY: 0.5,
    NORMAL: 1,
    HARD: 2
};

var gameTypeModifier = {
    FOUR_BY_FOUR: 0.00009,
    SIX_BY_SIX: 0.005,
    NINE_BY_NINE: 0.1,
    TWELVE_BY_TWELVE: 0.1350
};

var scoreMultiplier = 1000000000;

var calculateScore = function (difficulty, sudokuType, timeInSeconds) {
    var currentScore;
    switch (sudokuType) {
        case "4X4":
            currentScore = gameTypeModifier.FOUR_BY_FOUR;
            break;
        case "6X6":
            currentScore = gameTypeModifier.SIX_BY_SIX;
            break;
        case "9X9":
            currentScore = gameTypeModifier.NINE_BY_NINE;
            break;
        case "12X12":
            currentScore = gameTypeModifier.TWELVE_BY_TWELVE;
            break;
    }

    switch (difficulty) {
        case "Easy":
            currentScore *= difficultyModifier.EASY;
            break;
        case "Normal":
            currentScore *= difficultyModifier.NORMAL;
            break;
        case "Hard":
            currentScore *= difficultyModifier.HARD;
            break;
    }

    return Math.floor(currentScore / timeInSeconds * scoreMultiplier);
};

var backHighscoreButtonHandler = function () {
    window.location.href = 'index.html';
};

var loadHighscoresFromStorage = function () {
    if ((typeof Storage) !== void(0)
            && localStorage.getItem("highscoresArray") !== null) {
        highscoresArray = JSON.parse(localStorage.getItem("highscoresArray"));
    } else {
        highscoresArray = new Array();
    }
};

var timeDescendingComparator = function (a, b) {
    if (a.timeScore > b.timeScore) {
        return -1;
    } else if (a.timeScore < b.timeScore)
        return 1;
    return 0;
};

var storeHighscoresInStorage = function () {
    if ((typeof Storage) !== void(0)) {
        var highscoreArrayToString = JSON.stringify(highscoresArray);
        localStorage.setItem("highscoresArray", highscoreArrayToString);
    }
};

var createNewHighScoreEntry = function (firstnameString, lastnameString, difficultyString, timeString, sudokuTypeString) {
    var timeRegularFormat = timeString.split(':');
    var timeInSeconds = (+timeRegularFormat[0]) * 60 * 60 + (+timeRegularFormat[1]) * 60 + (+timeRegularFormat[2]);
    var highscoreEntry = {
        firstname: firstnameString,
        lastname: lastnameString,
        difficulty: difficultyString,
        time: timeString,
        timeScore: calculateScore(difficultyString, sudokuTypeString, timeInSeconds),
        grid: sudokuTypeString
    };

    highscoresArray.push(highscoreEntry);
    storeHighscoresInStorage();
};

var displayHighScoreTable = function () {
    highscoresArray.sort(timeDescendingComparator);
    var tableHTML = generateNAVTableHTML();
    $("#highscoreTable").html(tableHTML);
};

/*old code
 var generateTableHTML = function () {
 var HTMLString = "";
 var amountOfEntries = highscoresArray.length;
 
 if (amountOfEntries > 0) {
 HTMLString = '<div class="table-responsive">\
 <table class="table table-striped">\
 <thead>\
 <tr>\
 <th>#</th>\
 <th>First name</th>\
 <th>Last name</th>\
 <th>Sudoku</th>\
 <th>Time</th></tr>\
 </thead>\
 <tbody>';
 for (var i = 0; i < amountOfEntries; i++) {
 HTMLString += "<tr>";
 HTMLString += "<td>" + (i + 1) + "</td>";
 HTMLString += "<td>" + highscoresArray[i].firstname + "</td>";
 HTMLString += "<td>" + highscoresArray[i].lastname + "</td>";
 HTMLString += "<td>" + highscoresArray[i].difficulty + "</td>";
 HTMLString += "<td>" + highscoresArray[i].time + "</td>";
 HTMLString += "</tr>";
 }
 HTMLString += "</tbody></table></div>";
 } else {
 HTMLString = "<p>Er werden nog geen spellen gespeeld...</p>";
 }
 
 return HTMLString;
 };*/

var generateTable = function (difficultyType, active) {
    var amountOfEntries = highscoresArray.length;

    var HTMLString = '<div id="' + difficultyType + '"';
    var HTMLActive = active ? 'class="tab-pane fade in active">' : 'class="tab-pane fade">';
    HTMLString += HTMLActive;
    HTMLString += '<div class="container">\
                    <table class="table table-striped">\
                        <thead>\
                        <tr>\
                            <th>#</th>\
                            <th>First name</th>\
                            <th>Last name</th>\
                            <th>Grid</th>\
                            <th>Time</th>\
                            <th>Score</th>\
                            </tr>\
                        </thead>\
                        <tbody>';
    for (var i = 0, j = 0; i < amountOfEntries; i++) {
        if (highscoresArray[i].difficulty === difficultyType) {
            HTMLString += "<tr>";
            HTMLString += "<td>" + (j + 1) + "</td>";
            HTMLString += "<td>" + highscoresArray[i].firstname + "</td>";
            HTMLString += "<td>" + highscoresArray[i].lastname + "</td>";
            HTMLString += "<td>" + highscoresArray[i].grid + "</td>";
            HTMLString += "<td>" + highscoresArray[i].time + "</td>";
            HTMLString += "<td>" + highscoresArray[i].timeScore + "</td>";
            HTMLString += "</tr>";
            j++;
        }
    }
    HTMLString += "</tbody></table></div></div>";
    return HTMLString;
};

var generateNAVTableHTML = function () {
    var amountOfEntries = highscoresArray.length;

    if (amountOfEntries > 0) {
        var tableRowString = '<ul class="nav nav-tabs">\
                <li><a data-toggle="tab" href="#Easy">Easy</a></li>\
                <li class="active"><a data-toggle="tab" href="#Normal">Normal</a></li>\
                <li><a data-toggle="tab" href="#Hard">Hard</a></li>\
            </ul>\
        <div class="row">\
        <div class="tab-content">';
        tableRowString += generateTable("Easy", false);
        tableRowString += generateTable("Normal", true);
        tableRowString += generateTable("Hard", false);
        tableRowString += "</div></div>";
    } else {
        tableRowString = "<p>No games have been played yet...</p>";
    }

    return tableRowString;
};

$(document).ready(function () {
    loadHighscoresFromStorage();
    displayHighScoreTable();
    $(".back-Highscore").on('click', backHighscoreButtonHandler);
});