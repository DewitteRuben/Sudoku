/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.ti.sudokuapplication.bridge;

import be.howest.ti.sudokuapplication.game.Hint;
import be.howest.ti.sudokuapplication.game.Sudoku;
import be.howest.ti.sudokuapplication.game.SudokuGenerator;
import be.howest.ti.sudokuapplication.game.SudokuSolver;
import be.howest.ti.sudokuapplication.game.SudokuTools;
import be.howest.ti.sudokuapplication.game.SudokuValidator;
import java.io.IOException;
import java.io.PrintWriter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author wolke
 */
@WebServlet(name = "Bridge", urlPatterns = {"/sudoku-api/*"})
public class Bridge extends HttpServlet {

    private static final String PREFIX = "/2017_S2_Group_40/sudoku-api/";

    private JsonObject generate(String gridSize, String boxRows, String boxCols, String difficulty) {
        if (gridSize == null || boxRows == null || boxCols == null || difficulty == null) {
            return Json.createObjectBuilder()
                    .add("error", "missing data to start generation")
                    .add("gridSize", gridSize)
                    .add("boxRows", boxRows)
                    .add("boxCols", boxCols)
                    .add("difficulty", difficulty)
                    .build();
        } else {

            int gridSizeInt;
            int boxRowsInt;
            int boxColsInt;

            if (gridSize.matches("\\d+") && boxRows.matches("\\d+") && boxCols.matches("\\d+")) {
                gridSizeInt = Integer.parseInt(gridSize);
                boxRowsInt = Integer.parseInt(boxRows);
                boxColsInt = Integer.parseInt(boxCols);
            } else {
                return Json.createObjectBuilder()
                        .add("error", "gridSize, boxRows or boxCols is not of type integer")
                        .add("gridSize", gridSize)
                        .add("boxRows", boxRows)
                        .add("boxCols", boxCols)
                        .add("difficulty", difficulty)
                        .build();
            }

            return generate(gridSizeInt, boxRowsInt, boxColsInt, difficulty);
        }
    }

    Sudoku sudoku;

    private JsonObject generate(int gridSize, int boxRows, int boxCols, String difficulty) {
        SudokuGenerator nsg = new SudokuGenerator(gridSize, gridSize, boxRows, boxCols, difficulty);
        sudoku = nsg.generate();

        return Json.createObjectBuilder()
                .add("msg", "success")
                .add("grid", sudoku.toString())
                .add("solution", sudoku.solutionToString())
                .add("gridSize", gridSize)
                .add("boxRows", boxRows)
                .add("boxCols", boxCols)
                .add("difficulty", difficulty)
                .build();
    }

    private JsonObject clear() {
        sudoku.clear();

        return Json.createObjectBuilder()
                .add("msg", "success")
                .add("currentGrid", sudoku.toString())
                .build();
    }

    private JsonObject solve() {

        sudoku.clear();
        SudokuSolver SudokuSolver = new SudokuSolver(sudoku);
        if (SudokuSolver.fillSolve()) {
            return Json.createObjectBuilder()
                    .add("msg", "success")
                    .add("currentGrid", sudoku.toString())
                    .build();
        }

        return Json.createObjectBuilder()
                .add("error", "failed to solve puzzle")
                .add("currentGrid", sudoku.toString())
                .build();
    }

    private JsonObject makeMove(String cellRow, String cellColumn, String value) {
        if (cellRow == null || cellColumn == null || value == null) {
            return Json.createObjectBuilder()
                    .add("error", "missing data to start making a move")
                    .add("cellRow", cellRow)
                    .add("cellColumn", cellColumn)
                    .add("value", value)
                    .build();
        } else {

            int cellRowInt;
            int cellColumnInt;
            int valueInt;

            if (cellRow.matches("\\d+") && cellColumn.matches("\\d+") && value.matches("\\d+")) {
                valueInt = Integer.parseInt(value);
                cellRowInt = Integer.parseInt(cellRow);
                cellColumnInt = Integer.parseInt(cellColumn);
            } else {
                return Json.createObjectBuilder()
                        .add("error", "value row or column is not of type integer")
                        .add("cellRow", cellRow)
                        .add("cellColumn", cellColumn)
                        .add("value", value)
                        .build();
            }

            return makeMove(cellRowInt, cellColumnInt, valueInt);
        }
    }

    private JsonObject makeMove(int cellRowInt, int cellColumnInt, int valueInt) {
        boolean action = sudoku.makeNewMove(cellRowInt, cellColumnInt, valueInt);
        SudokuTools ST = new SudokuTools(sudoku);

        if (action) {
            return Json.createObjectBuilder()
                    .add("msg", "success")
                    .add("row", cellRowInt)
                    .add("column", cellColumnInt)
                    .add("value", valueInt)
                    .add("currentGrid", sudoku.toString())
                    .add("emptyCells", ST.giveEmptyCells())
                    .build();
        }

        return Json.createObjectBuilder()
                .add("error", "failed to make move")
                .add("row", cellRowInt)
                .add("column", cellColumnInt)
                .add("value", valueInt)
                .add("currentGrid", sudoku.toString())
                .build();
    }

    private JsonObject getNextCellSolution() {
        Hint Hint = new Hint(sudoku);
        int[] cellData = Hint.giveNextCellSolution();
        int row = cellData[0];
        int column = cellData[1];
        int solution = cellData[2];
        if (row == -1 || column == -1 || solution == -1) {
            return Json.createObjectBuilder()
                    .add("error", "unable to find a solution for a the next cell")
                    .add("row", row)
                    .add("column", column)
                    .add("solutionForCellCoordinates", solution)
                    .build();
        }

        return Json.createObjectBuilder()
                .add("msg", "success")
                .add("row", row)
                .add("column", column)
                .add("solutionForCellCoordinates", solution)
                .build();
    }

    private JsonObject getNextErrorCoordinates() {
        Hint Hint = new Hint(sudoku);
        int[] cellData = Hint.findNextError();
        int row = cellData[0];
        int column = cellData[1];
        if (row == -1 || column == -1) {
            return Json.createObjectBuilder()
                    .add("error", "unable to find the next error")
                    .add("row", row)
                    .add("column", column)
                    .build();
        }

        return Json.createObjectBuilder()
                .add("msg", "success")
                .add("row", row)
                .add("column", column)
                .build();
    }

    private JsonObject validValuesHint(String cellNumber) {
        if (cellNumber == null) {
            return Json.createObjectBuilder()
                    .add("error", "missing data to start making a move")
                    .add("cellNumber", cellNumber)
                    .build();
        } else {

            int cellNumberInt;
            if (cellNumber.matches("\\d+")) {
                cellNumberInt = Integer.parseInt(cellNumber);
            } else {
                return Json.createObjectBuilder()
                        .add("error", "value is not of type integer")
                        .add("cellNumber", cellNumber)
                        .build();
            }
            return validValuesHint(cellNumberInt);
        }
    }

    private JsonObject validValuesHint(int cellNumber) {
        Hint Hint = new Hint(sudoku);
        int[] possibleValuesArray = Hint.getValidValuesOfCell(cellNumber);

        return Json.createObjectBuilder()
                .add("msg", "success")
                .add("cellNumber", cellNumber)
                .add("validValues", Arrays.toString(possibleValuesArray))
                .build();
    }

    private JsonObject check() {

        SudokuValidator SudokuValidator = new SudokuValidator(sudoku);

        return Json.createObjectBuilder()
                .add("msg", "success")
                .add("valid", SudokuValidator.isValid())
                .add("solved", SudokuValidator.isSolved())
                .build();
    }

    private JsonArray allValidValuesHint() {
        Hint Hint = new Hint(sudoku);
        int[][] possibleValuesGrid = Hint.getValidValuesOfGrid();
        int possibleValuesGridLength = possibleValuesGrid.length;

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < possibleValuesGridLength; i++) {
            arrayBuilder.add(Arrays.toString(possibleValuesGrid[i]));
        }

        JsonArray jsa = arrayBuilder.build();
        return jsa;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String command = request.getRequestURI().substring(PREFIX.length());

            switch (command) {
                case "generate":
                    String gridSize = request.getParameter("gridSize");
                    String boxRows = request.getParameter("boxRows");
                    String boxCols = request.getParameter("boxCols");
                    String difficulty = request.getParameter("difficulty");
                    JsonObject generateObject = generate(gridSize, boxRows, boxCols, difficulty);
                    out.println(generateObject);
                    break;
                case "move":
                    String cellRow = request.getParameter("cellRow");
                    String cellColumn = request.getParameter("cellColumn");
                    String value = request.getParameter("value");
                    JsonObject makeMoveObject = makeMove(cellRow, cellColumn, value);
                    out.println(makeMoveObject);
                    break;
                case "solve":
                    JsonObject solveObject = solve();
                    out.println(solveObject);
                    break;
                case "check":
                    JsonObject checkObject = check();
                    out.println(checkObject);
                    break;
                case "clear":
                    JsonObject clearObject = clear();
                    out.println(clearObject);
                    break;
                case "getValidValuesByCell":
                    String cellNumber = request.getParameter("cellNumber");
                    JsonObject validValuesObject = validValuesHint(cellNumber);
                    out.println(validValuesObject);
                    break;
                case "getAllValidValues":
                    JsonArray allValidValuesObject = allValidValuesHint();
                    out.println(allValidValuesObject);
                    break;
                case "getNextCellSolution":
                    JsonObject getNextCellSolutionObject = getNextCellSolution();
                    out.println(getNextCellSolutionObject);
                    break;
                case "getNextError":
                    JsonObject getNextErrorCoordinatesObject = getNextErrorCoordinates();
                    out.println(getNextErrorCoordinatesObject);
                    break;

            }
        }

    }// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
