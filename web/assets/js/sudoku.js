/**
 * Created by Brent Gregoir on 28/03/17.
 */

/**
 * BACK HOME
 */
var backToHomeButton = function () {
    window.location.href = 'index.html';

};

/**
 * REFRESH PAGE
 */


/**
 * GLOBAL VARIABLES
 */

var currentGrid;
var solution;
var gridSize;
var boxRows;
var boxCols;
var isValid = true;
var isSolved = false;
var isAutoSolved = false;
var currentSelectedCell;
var isNotesEnabled = false;

/**
 * SETTINGS PAGE
 */

var sudokuGridType = "9X9";
var selectedDifficulty = "Normal";


var generateSelectOptionHTML = function () {
    var difficultySelectElement = $("#difficulty");
    var defaultHTMLString = '<option value="Easy">Easy</option>\
                      <option value="Normal" selected="selected">Normal</option>\
                      <option value="Hard">Hard</option>';
    var normalOnlyHTMLString = '<option value="Normal" selected="selected">Normal</option>';

    switch (sudokuGridType) {
        case "4X4":
        case "6X6":
            difficultySelectElement.html(normalOnlyHTMLString);
            break;
        default:
            difficultySelectElement.html(defaultHTMLString);
            break;
    }
};

var sudokuTypeHandler = function () {
    sudokuGridType = $(this).attr("id");
    generateSelectOptionHTML();

    $("input[type=button].active").each(function () {
        ($(this)).removeClass("active");
    });
    $(this).addClass("active");
};

var sudokuDifficultyHandler = function () {
    selectedDifficulty = $("select option:selected").attr('value');
};

var resetColorSelection = function () {
    $("input[type='text']").each(function () {
        $(this).css("background-color", "white");
    });
};

var generateCSSHoverClass = function () {
    var styleObject = $("style#hoverCSS");
    if (styleObject) {
        styleObject.remove();
    }
    $("<style id='hoverCSS'>")
            .prop("type", "text/css")
            .html("\
    table input.cell:hover {\
        background-color:" + selectionColor + " !important;\
        cursor:pointer;\
    }").appendTo("head");
};

var loadCurrentColor = function () {
    loadColorSetupFromStorage();
    generateCSSHoverClass();
};

var colorSelection = function (e) {
    resetColorSelection();

    var selectedRow = $(this).data("row");
    var selectedColumn = $(this).data("column");

    $("input[type='text']").each(function () {
        var currentRow = $(this).data("row");
        var currentColumn = $(this).data("column");

        // Check if the current row/column equals the grid coordinates
        var boxCordRow = parseInt((selectedRow / boxRows)) * boxRows;
        var boxCordCol = parseInt((selectedColumn / boxCols)) * boxCols;
        if (boxCordRow % boxRows || boxCordCol % boxCols === 0) {
            for (var row = boxCordRow; row < (boxCordRow + boxRows); row++) {
                for (var column = boxCordCol; column < (boxCordCol + boxCols); column++) {
                    if (currentRow === row && currentColumn === column) {
                        // Highlight small square in grid
                        $(this).css("background-color", softSelectionColor);
                    }
                }
            }
        }

        // Highlights selected cell
        if (currentRow === selectedRow && currentColumn === selectedColumn) {
            $(this).css("background-color", selectionColor);
            // Highlights all cells in row and column
        } else if (currentRow === selectedRow || currentColumn === selectedColumn) {
            $(this).css("background-color", softSelectionColor);
        }

    });
};


/**
 * GAME PAGE
 */

var generateSudoku = function (size, bRows, bCols) {
    $.ajax({
        type: 'POST',
        url: 'sudoku-api/generate',
        data: {
            gridSize: size,
            boxRows: bRows,
            boxCols: bCols,
            difficulty: selectedDifficulty
        },
        dataType: 'json'
    }).done(function (sudokuData, textStatus, jqXHR) {
        console.log(sudokuData);
        saveSudokuData(sudokuData);
        generateSudokuGridHTML(currentGrid, gridSize, boxRows, boxCols);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log("Er liep iets fout...");
    });
};

var autoSolveSudoku = function () {
    if (!isAutoSolved) {
        sendSolveRequest();
    } else {
        alert("You have already solved this Sudoku!");
    }
};

var sendSolveRequest = function () {
    $.ajax({
        type: 'POST',
        url: 'sudoku-api/solve',
        dataType: 'json'
    }).done(function (data, textStatus, jqXHR) {
        console.log(data);
        currentGrid = data.currentGrid;
        generateSudokuGridHTML(currentGrid, gridSize, boxRows, boxCols);
        isAutoSolved = true;
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log("Er liep iets fout...");
    });
};


var makeMoveSudoku = function (row, column, value) {
    $.ajax({
        type: 'POST',
        url: 'sudoku-api/move',
        data: {
            cellRow: row,
            cellColumn: column,
            value: value
        },
        dataType: 'json'
    }).done(function (data, textStatus, jqXHR) {
        console.log(data);
        if (data.emptyCells === 0) {
            sendValidationRequest();
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log("Er liep iets fout...");
    });
};

var clearSudoku = function () {
    $.ajax({
        type: 'POST',
        url: 'sudoku-api/clear',
        dataType: 'json'
    }).done(function (data, textStatus, jqXHR) {
        console.log(data);
        currentGrid = data.currentGrid;
        generateSudokuGridHTML(currentGrid, gridSize, boxRows, boxCols);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log("Er liep iets fout...");
    });
};

var saveSudokuData = function (data) {
    currentGrid = data.grid;
    gridSize = data.gridSize;
    boxRows = data.boxRows;
    boxCols = data.boxCols;
    solution = data.solution;
};

// source: mobiledevicedetection.com
var isMobileDevice = function () {
    var check = false;
    (function (a) {
        if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk/i.test(a) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0, 4)))
            check = true;
    })(navigator.userAgent || navigator.vendor || window.opera);
    return check;
};

var generateSudokuGridHTML = function (grid, gridSize, boxRows, boxCols) {
    var htmlSTRING = "";
    var gridArray = grid.split("|");
    var maxLength = gridSize > 9 ? 2 : 1;

    htmlSTRING += '<table><tbody>';

    // TODO refactor into multiple functions and or rewrite
    for (var row = 1; row <= gridSize; row++) {
        if (row % boxRows === 0) {
            htmlSTRING += "<tr class='borderBottom'>";
        } else {
            htmlSTRING += "<tr>";
        }

        for (var column = 1; column <= gridSize; column++) {
            var position = ((row * gridSize) - (gridSize - column)) - 1;

            // generate 2px border at the correct cell
            if (column % boxCols === 0) {
                htmlSTRING += "<td class='borderRight position'>";
            } else {
                htmlSTRING += "<td class='position'>";
            }

            if (gridArray[position] !== ".") {
                htmlSTRING += '<input id="' + position + '" value="'
                        + gridArray[position] + '" data-row="' + (row - 1) + '" data-column="'
                        + (column - 1) + '" class="disabledCell" type="text" disabled>';
            } else {

                htmlSTRING += generateNoteContainerHTML();

                if (isMobileDevice()) {
                    htmlSTRING += '<input id="' + position + '" class="cell" type="text" readonly="readonly" data-row="' + (row - 1) + '" data-column="'
                            + (column - 1) + '" maxlength="' + maxLength + '">';
                } else {
                    htmlSTRING += '<input id="' + position + '" class="cell" type="text" data-row="' + (row - 1) + '" data-column="'
                            + (column - 1) + '" maxlength="' + maxLength + '">';
                }

            }

            htmlSTRING += "</td>";

        }
        htmlSTRING += "</tr>";
    }

    htmlSTRING += "</tbody></table>";

    $("#gameGrid").html(htmlSTRING);
    assureTwelveByTwelveLayout();
    handleSideBarButtonDisplay();

};


var generateNoteContainerHTML = function () {
    // If cell is empty, generate hint box
    var HTMLString = "";
    HTMLString += "<div class='noteContainer'>";
    for (var hint = 1; hint <= gridSize; hint++) {
        switch (hint) {
            case 10:
                HTMLString += "<div class='notes note-10 hidden'>A</div>";
                break;
            case 11:
                HTMLString += "<div class='notes note-11 hidden'>B</div>";
                break;
            case 12:
                HTMLString += "<div class='notes note-12 hidden'>C</div>";
                break;
            default:
                HTMLString += "<div class='notes note-" + hint + " hidden'>" + hint + "</div>";
        }
    }

    HTMLString += "</div>";
    return HTMLString;
};

var assureTwelveByTwelveLayout = function () {
    if (sudokuGridType === "12X12") {
        $("table input").addClass("twelveByTwelveInput");
        $(".notes").addClass("twelveByTwelveNotes");
    }
};

var setSelectedCell = function (e) {
    currentSelectedCell = $(this).attr("id");
    console.log(currentSelectedCell);
};

var handleHighscoreSubmit = function (difficulty, currentTime) {
    var firstnameObject = $("#firstname");
    var firstName = firstnameObject.val();
    var lastname = firstnameObject.next().next().next().val();
    createNewHighScoreEntry(firstName, lastname, difficulty, currentTime, sudokuGridType);
};

var inputNewValueInCell

var inputNewValueInCell = function (selectedCellObject, value) {
    if ((currentSelectedCell !== undefined)) {
        selectedCellObject.focus();
        selectedCellObject.val(value).trigger("input");
    } else {
        alert("Please select a cell first!");
    }
};

var onInputButtonClick = function () {

    var selectedCellObject = $("input#" + currentSelectedCell);
    var btnText = $(this).text();
    var action = $(this).data("action");

    if (action === undefined) {
        if (isNotesEnabled) {
            inputNewNote(selectedCellObject, btnText);
        } else {
            inputNewValueInCell(selectedCellObject, btnText);
        }
    } else {
        selectedCellObject.focus();
        switch (action) {
            case "erase":
                clearSelectedCell();
                break;
            case "note":
                toggleNoteInterface();
                break;
        }
    }
};

var toggleNoteInterface = function () {
    if (!isNotesEnabled) {
        isNotesEnabled = true;
    } else {
        isNotesEnabled = false;
    }

    $(".noteTrigger").toggleClass("highlight").prev().toggleClass("highlight");
};

var inputNewNote = function (selectedCellObject, value) {
    var hintBoxObject = selectedCellObject.prev();

    if ((currentSelectedCell !== undefined) && !selectedCellObject.val()) {
        selectedCellObject.focus();
        hintBoxObject.children(".note-" + value).toggleClass("hidden");
    } else if (selectedCellObject.val()) {
        selectedCellObject.focus();
        alert("You've already entered a number in this cell!");
    } else {
        alert("Please select a cell first!");
    }
};

var clearSelectedCell = function () {
    var selectedCellObject = $("input#" + currentSelectedCell);

    var hintBoxObject = selectedCellObject.prev();

    if (isNotesEnabled) {
        hintBoxObject.children().removeClass("hidden").addClass("hidden");
    } else {
        selectedCellObject.val("").trigger("input");
    }
};

var handleSideBarButtonDisplay = function () {
    $("td.btnNumpad").each(function () {
        if (parseInt($(this).text()) > gridSize) {
            $(this).css("background", "rgba(0, 0, 0, 0.2)").css("pointer-events", "none");
        }
    });
};

var generateCorrespondingSudoku = function () {
    switch (sudokuGridType) {
        case "4X4":
            generateSudoku(4, 2, 2);
            break;
        case "6X6":
            if (Math.random() > 0.5) {
                generateSudoku(6, 3, 2);
            } else {
                generateSudoku(6, 2, 3);
            }
            break;
        case "9X9":
            generateSudoku(9, 3, 3);
            break;
        case "12X12":
            if (Math.random() > 0.5) {
                generateSudoku(12, 3, 4);
            } else {
                generateSudoku(12, 4, 3);
            }
            break;
    }
};

var showGamePage = function (e) {
    $(".settingsPage").addClass("hidden").next().removeClass("hidden");
    generateCorrespondingSudoku();
    $("#scoreTimer").stopwatch().stopwatch("start");
    $(".difficultytext").html(selectedDifficulty);
};

/**
 * CONGRATS PAGE
 */

var congratsHomeButtonHandler = function () {
    window.location.href = 'index.html';
};

var congratsHighscoreButtonHandler = function () {
    window.location.href = 'highscore.html';
};

var sendNewInput = function () {
    var row = $(this).data("row");
    var column = $(this).data("column");
    var value = $(this).val();

    if (value === "") {
        makeMoveSudoku(row, column, 0);
    } else {
        makeMoveSudoku(row, column, value);
    }
};

var restrictInput = function (event) {
    if (event.which === 8 || event.which === 46)
        return;
    if ((event.which < 49 || event.which > 57)) {
        event.preventDefault();
    }
};

var colorCellWithCoordinates = function (row, column, color) {
    $("input[type='text']").each(function (index, obj) {
        var currentRow = $(this).data("row");
        var currentColumn = $(this).data("column");
        if (row === currentRow && column === currentColumn) {
            $(this).css("background-color", color);
        }
    });
};

var handleNextErrorData = function (data) {
    var row = data.row;
    var column = data.column;

    if (data.error === undefined) {
        if (confirm("U have made a mistake, would you like to see where?")) {
            $("#gameSetup").modal("hide");
            colorCellWithCoordinates(row, column, "red");
        }
    } else {
        sendValidationRequest();
    }
};

var findNextErrorRequest = function () {
    $.ajax({
        type: 'POST',
        url: 'sudoku-api/getNextError',
        dataType: 'json'
    }).done(function (data, textStatus, jqXHR) {
        console.log(data);
        handleNextErrorData(data);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log("Er liep iets fout...");
    });
};

var sendValidationRequest = function () {
    $.ajax({
        type: 'POST',
        url: 'sudoku-api/check',
        dataType: 'json'
    }).done(function (data, textStatus, jqXHR) {
        handleValidationResponse(data);
        console.log(data);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log("Er liep iets fout...");
    });
};

var handleValidationResponse = function (data) {
    isValid = data.valid;
    isSolved = data.solved;

    if (!isAutoSolved && isSolved && isValid) {
        handleVictory();
    } else if (isValid && !isSolved) {
        alert("You're on track...");
    } else if (!isValid && !isSolved) {
        alert("You have messed up somewhere...");
    }
};

var handleVictory = function () {
    var delayMillis = 1250;
    var currentTime = $("#scoreTimer").stopwatch().stopwatch("toggle");
    var congratsPopup = $("#congratsPopUp");

    currentTime = currentTime.text();
    $("#score").text(currentTime);

    setTimeout(function () {
        congratsPopup.modal({backdrop: 'static', keyboard: false});

        $("#highscoreSubmit").on("click", function () {
            handleHighscoreSubmit(selectedDifficulty, currentTime);
            congratsPopup.modal("hide");
            window.location.replace("highscore.html");
        });
    }, delayMillis);
};

var getAllValidValues = function () {
    $.ajax({
        type: 'POST',
        url: 'sudoku-api/getAllValidValues',
        dataType: 'json'
    }).done(function (data, textStatus, jqXHR) {
        console.log(data);
        fillInNotes(data);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log(errorThrown);
    });
};

var fillInNotes = function (valuesArray) {
    var validValuesGridLength = valuesArray.length;
    var validValuesArrayLength = $.parseJSON(valuesArray[0]).length;
    for (var i = 0; i < validValuesGridLength; i++) {
        for (var j = 1; j < validValuesArrayLength; j++) {
            var noteObject = $("input#" + i).prev().children(".note-" + j);
            noteObject.removeClass("hidden").addClass("hidden");
            var currentArray = $.parseJSON(valuesArray[i]);
            if (currentArray[j] !== 0) {
                noteObject.toggleClass("hidden");
            }
        }

    }
};

var showValidValuesForCell = function (data) {
    var validValuesArray = $.parseJSON(data.validValues);
    var validValuesLength = validValuesArray.length;
    var HTMLString = "<p>Possible values in current cell: ";
    var validValuesSum = 0;
    for (var i = 1; i < validValuesLength; i++) {
        validValuesSum += validValuesArray[i];
        if (validValuesArray[i] !== 0) {
            HTMLString += validValuesArray[i] + ",";
        }
    }
    if (validValuesSum === 0) {
        HTMLString += "None";
    }
    HTMLString += "</p>";
    console.log(HTMLString);
    $("#valueHints").html(HTMLString);
};

var getValidValues = function (cellNumber) {
    $.ajax({
        type: 'POST',
        url: 'sudoku-api/getValidValuesByCell',
        data: {
            cellNumber: cellNumber
        },
        dataType: 'json'
    }).done(function (data, textStatus, jqXHR) {
        showValidValuesForCell(data);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log(jqXHR);
    });
};

var showValidChoices = function (e) {
    if (hintSettingsObject.showValidValues) {
        getValidValues(currentSelectedCell);
    }
};

var clearAllNotes = function () {
    var numberOfCells = gridSize * gridSize;
    for (var i = 0; i < numberOfCells; i++) {
        var noteBoxObject = $("input#" + i).prev();
        for (var j = 1; j <= gridSize; j++) {
            noteBoxObject.children(".note-" + j).removeClass("hidden").addClass("hidden");
        }
    }
};

var autoCompleteAllNotes = function () {
    if (hintSettingsObject.autoFillNotes) {
        getAllValidValues();
    }
};

var handleNoteBoxDisplay = function (e) {
    if (!(currentSelectedCell === undefined)) {
        var selectedCellObject = $("input#" + currentSelectedCell);
        if (selectedCellObject.prev().is(":visible") && selectedCellObject.val().length !== 0) {
            selectedCellObject.prev().hide();
        } else if (selectedCellObject.val().length === 0) {
            selectedCellObject.prev().show();
        }
    }
};

var onLeavingGameGrid = function (e) {
    var container = $("#gameGrid");
    if (!container.is(e.target) && container.has(e.target).length === 0) {
        resetColorSelection();
        resetValidChoices();
    }
};

var resetValidChoices = function (e) {
    var valueObject = $("#valueHints");
    var isValidChoiceHintEnabled = hintSettingsObject.showValidValues;
    if (isValidChoiceHintEnabled) {
        valueObject.html("<p>No cell selected<p>");
    } else {
        valueObject.html("");
    }
};

var handleHintsMenuClose = function (e) {
    if (hintSettingsObject.autoFillNotes) {
        autoCompleteAllNotes();
    } else {
        clearAllNotes();
    }
};

var refreshSudoku = function (e) {
    var timer = $("#scoreTimer").stopwatch();
    timer.stopwatch("stop");
    if (confirm("Do you wish to start a new Sudoku? (Timer will be reset)")) {
        generateCorrespondingSudoku();
        timer.stopwatch("reset");
    }
    timer.stopwatch("start");
};

var handlePauseMenu = function () {
    $("#pauseScreen").modal({backdrop: 'static', keyboard: false});
    $("#scoreTimer").stopwatch().stopwatch("stop");
    $("#gameGrid").addClass("blackOverlay");
};

var handlePauseMenuClose = function () {
    $("#gameGrid").removeClass("blackOverlay");
    $("#scoreTimer").stopwatch().stopwatch("start");
};

$(document).ready(function () {
    loadCurrentColor();

    $(".backHome").on('click', backToHomeButton);

    $(".btnNumpad").on("click", onInputButtonClick);
    $("#gameGrid")
            .on("keypress", "input", restrictInput)
            .on("focusin", "input", setSelectedCell)
            .on("focusin", "input", colorSelection)
            .on("input", "input", sendNewInput)
            .on("input", "input", autoCompleteAllNotes)
            .on("input focusin", "input", showValidChoices)
            .on("input", "input", handleNoteBoxDisplay);

    $(".refresh").on("click", refreshSudoku);

    $(".type").on('click', sudokuTypeHandler);
    $("#difficulty").on("mouseup", sudokuDifficultyHandler);
    $(".play-settings").on('click', showGamePage);
    $(".pauseButton").on("click", handlePauseMenu);
    $(".home-button").on('click', congratsHomeButtonHandler);
    $(".highscore-button").on('click', congratsHighscoreButtonHandler);

    $("#hintsSetup").on("hidden.bs.modal", handleHintsMenuClose);
    $("#pauseScreen").on("hidden.bs.modal", handlePauseMenuClose);

}).on("mouseup touch", onLeavingGameGrid);



