var themeURL;
var setTheme = function () {
    switch (themeURL) {
        case "images/normal.jpg":
            $('body').css('background-image', 'none');
            break;
        case "images/8bit.jpg":
            $('body').css('background-image', 'url("images/8bit.png")');
            break;
        default:
            $('body').css('background-image', 'url("' + themeURL + '")');
            $('.logo8bit').hide();
    }
};

;

var swapCSSFile = function () {
    if (location.pathname === "/2017_S2_Group_40/sudoku.html") {
        $("theme_css").attr("href", "assets/css/sudoku.css");

    } else {
        $("theme_css").attr("href", "assets/css/screen.css");
    }
    
};
var loadThemeFromLocalStorage = function () {
    if ((typeof Storage) !== void(0)
            && localStorage.getItem("themeURL") !== null) {
        var localStorageThemeURL = localStorage.getItem("themeURL");
        themeURL = localStorageThemeURL;
        setTheme();
    }
};

var storeThemeInLocalStorage = function (URL) {
    if ((typeof Storage) !== void(0)) {
        localStorage.setItem("themeURL", URL);
    }
};

var themeSet = function () {
    var selectedTheme = $(this).data("theme");
    var clickedThemeURL = 'images/' + selectedTheme + '.jpg';
    console.log('selected theme is: ' + selectedTheme);
    console.log('selected theme url is: ' + clickedThemeURL);
    storeThemeInLocalStorage(clickedThemeURL);
    ThemeURL = clickedThemeURL;
    loadThemeFromLocalStorage();
};

$(document).ready(function () {
    loadThemeFromLocalStorage();
    swapCSSFile();
    console.log(location.pathname);
    $(".button-themes").on('click', themeSet);
});