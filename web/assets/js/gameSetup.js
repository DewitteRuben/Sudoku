var selectionColor;
var softSelectionColor;
var defaultColor = "rgba(73, 255, 179, 1)";

String.prototype.replaceAt = function (index, replacement) {
    return this.substr(0, index) + replacement + this.substr(index + replacement.length);
};

var setTransparantColor = function () {
    softSelectionColor = selectionColor.replaceAt(selectionColor.length - 2, "0.1)");
};


var resetColor = function () {
    selectionColor = defaultColor;
    storeColorValueInLocalStorage(selectionColor);
};

var loadColorSetupFromStorage = function () {
    if ((typeof Storage) !== void(0)
            && localStorage.getItem("selectionColor") !== null) {
        selectionColor = localStorage.getItem("selectionColor");
    } else {
        resetColor();
    }
    setTransparantColor();
};
var storeColorValueInLocalStorage = function (rgbaString) {
    if ((typeof Storage) !== void(0)) {
        localStorage.setItem("selectionColor", rgbaString);
    }
};

var resetSelectionColorHandler = function () {
    if (confirm('Are you sure you want to reset the selection color?')) {
        resetColor();
        loadCurrentColor();
    }  
};

var handleColorPicker = function (e) {
    storeColorValueInLocalStorage(e.color.toString("rgba"));
    loadCurrentColor();
};

var handleSudokuReset = function () {
    if (confirm('Are you sure you to reset the current Sudoku? (Clears all cells)')) {
        clearSudoku();
        $("#gameSetup").modal("hide");
    }
};

var handleSudokuValidation = function() {
    findNextErrorRequest();
};

$(document).ready(function () {
    $("button#changeSelectionColor").colorpicker().on("changeColor", handleColorPicker);
    $("button#resetSudoku").on("click", handleSudokuReset);
    $("button#validateSudoku").on("click", handleSudokuValidation);
    $("button#resetSelectionColor").on("click", resetSelectionColorHandler);
});