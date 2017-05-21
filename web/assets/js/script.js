/**
 * Created by Thomas De Rycke
 */

/**
 * MAIN PAGE
 */
var mainPlayButtonHandler = function () {
    window.location.href = 'sudoku.html'
};

var mainHighscoreButtonHandler = function () {
    window.location.href='highscore.html';
};
var mainAboutButtonHandler = function () {
    $(".main").addClass("hidden");
    $(".about").removeClass("hidden");
};

var mainHelpButtonHandler = function () {
    $(".main").addClass("hidden");
    $(".help").removeClass("hidden");
};


/**
 * ABOUT PAGE
 */

var backAboutButtonHandler = function () {
    $(".about").addClass("hidden");
    $(".main").removeClass("hidden");

};

/**
 * HELP PAGE
 */
var backHelpButtonHandler = function () {
    console.log("test");
    $(".help").addClass("hidden");
    $(".main").removeClass("hidden");

};

/**
 * THEME PAGE
 */
var mainthemeButtonHandler = function () {
    $(".main").addClass("hidden");
    $(".theme").removeClass("hidden");
};

var backthemeButtonHandler = function () {
    $(".theme").addClass("hidden");
    $(".main").removeClass("hidden");
};

$(document).ready(function () {
    $(".button-play").on('click', mainPlayButtonHandler);
    $(".button-highscore").on('click', mainHighscoreButtonHandler);
    $(".button-about").on('click', mainAboutButtonHandler);
    $(".button-help").on('click', mainHelpButtonHandler);
    $(".button-theme").on('click', mainthemeButtonHandler);

    $(".back-About").on('click', backAboutButtonHandler);
    $(".back-help").on('click', backHelpButtonHandler);
    $(".back-theme").on('click', backthemeButtonHandler);
});