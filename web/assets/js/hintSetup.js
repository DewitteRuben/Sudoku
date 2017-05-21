var hintSettingsObject;

var changeButtonLayout = function () {
    if (hintSettingsObject.showValidValues) {
        $("button#toggleValidValues").addClass("highlight");
    }
    if (hintSettingsObject.autoFillNotes) {
        $("button#toggleAutoFill").addClass("highlight");
    }
};

var loadSetupFromLocalStorage = function () {
    if ((typeof Storage) !== void(0)
            && localStorage.getItem("hintSettings") !== null) {
        var settingsObjectAsString = localStorage.getItem("hintSettings");
        hintSettingsObject = JSON.parse(settingsObjectAsString);
        changeButtonLayout();
    } else {
        hintSettingsObject = {"showValidValues": false, "autoFillNotes": false};
        storeObjectInLocalStorage(hintSettingsObject, "hintSettings");
    }
};

var storeObjectInLocalStorage = function (object, storeName) {
    if ((typeof Storage) !== void(0)) {
        var settingsObjectAsString = JSON.stringify(object);
        localStorage.setItem(storeName, settingsObjectAsString);
    }
};

var toggleValidValuesHint = function () {
    if (hintSettingsObject.showValidValues) {
        hintSettingsObject.showValidValues = false;
    } else {
        hintSettingsObject.showValidValues = true;
    }

    $(this).toggleClass("highlight");
    storeObjectInLocalStorage(hintSettingsObject, "hintSettings");
};

// Possible replacement?
/*var toggleHint = function (objectHintBoolean) {
 if (objectHintBoolean) {
 objectHintBoolean = false;
 } else {
 objectHintBoolean = true;
 }
 
 $(this).toggleClass("highlight");
 storeObjectInLocalStorage(hintSettingsObject, "hintSettings");
 };*/

var toggleAutoFillHint = function (e) {
    if (hintSettingsObject.autoFillNotes) {
        hintSettingsObject.autoFillNotes = false;
    } else {
        hintSettingsObject.autoFillNotes = true;
    }

    $(this).toggleClass("highlight");
    storeObjectInLocalStorage(hintSettingsObject, "hintSettings");
};

var solveSudokuPuzzle = function (e) {
    if (confirm('Do you really want us to solve the Sudoku for you? (No points will be earned)')) {
        autoSolveSudoku();
        $("#scoreTimer").stopwatch().stopwatch("stop");
        $("#hintsSetup").modal("hide");
    }
};

var solveNextCell = function (e) {
    getNextCellSolution();
};

var getNextCellSolution = function () {
    $.ajax({
        type: 'POST',
        url: 'sudoku-api/getNextCellSolution',
        dataType: 'json'
    }).done(function (data, textStatus, jqXHR) {
        console.log(data);
        continueNextCellSolutionRequest(data);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log("Er liep iets fout...");
    });
};

var handleHintData = function (data) {
    var rowCord = data.row;
    var colCord = data.column;
    var solutionForCellCoordinates = data.solutionForCellCoordinates;
        $("input[type='text']").each(function (index, obj) {
            var currentRow = $(this).data("row");
            var currentColumn = $(this).data("column");
            if (rowCord === currentRow && colCord === currentColumn) {
                $(this).val(solutionForCellCoordinates).css("background-color","yellow");
                makeMoveSudoku(rowCord, colCord, solutionForCellCoordinates);        
            }
        });
};

var continueNextCellSolutionRequest = function (data) {
    if (data.error === undefined) {
        $("#hintsSetup").modal("hide");
        handleHintData(data);
    } else {
        alert("The Sudoku has already been completed!");
    }

};

$(document).ready(function () {
    loadSetupFromLocalStorage();
    $("button#toggleValidValues").on("click", toggleValidValuesHint);
    $("button#solve").on("click", solveSudokuPuzzle);
    $("button#toggleAutoFill").on("click", toggleAutoFillHint);
    $("button#solveNext").on("click", solveNextCell);
});

