package com.mygdx.game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private Map<Coordinate, Cell> cells;
    private List<Integer> bornRule;
    private List<Integer> surviveRule;
    private List<Coordinate> field = new ArrayList<>();
    private int boardSize;


    public Board(int bs, List<Integer> br, List<Integer> sr, Path p) throws IOException {
        readFile(p);
        boardSize = bs;
        bornRule = br;
        surviveRule = sr;
        cells = new HashMap<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Coordinate coordinate = new Coordinate(i, j);
                State state = State.DEAD;
                if (field.contains(coordinate)){
                    state = State.ALIVE;
                }
                Cell cell = new Cell(coordinate, state);
                cells.put(coordinate, cell);
            }
        }
    }
    private  void readFile(Path filePath) throws IOException {
        List<String> allDocLines = new ArrayList<>();
        System.err.println(filePath);
        try {
            allDocLines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int j = 0;
        for (String line : allDocLines) {
            if (line.isEmpty()) {
                continue;
            }
                String[] split = line.split(",");
                int v = Integer.parseInt(split[0]);
                int k = Integer.parseInt(split[1]);
                Coordinate c = new Coordinate(v, k);

                field.add(c);
        }
    }

    public Map<Coordinate, Cell> getCells() {
        return cells;
    }

    public Cell getCell(Coordinate c) {
        return cells.get(c);
    }

    public void setCells(Map<Coordinate, Cell> cells) {
        this.cells = cells;
    }

    public Board() {
    }

    public void updateState() {
        for (Coordinate coordinate : cells.keySet()) {
            Cell cell = cells.get(coordinate);
            int neighborsCount = countNeighbors(cell);
            boolean shouldBeChanged = checkRules(cell.getState(), neighborsCount);
            cell.setChangeState(shouldBeChanged);
        }
        for (Coordinate coordinate : cells.keySet()) {
            Cell cell = cells.get(coordinate);
            if(cell.isChangeState())
                cell.changeState();
            cell.setChangeState(false);

        }
    }

    private boolean checkRules(State s, int neighborsCount) {
        if(s == State.ALIVE){
            if (surviveRule.contains(neighborsCount))
                return false;
            else
                return true;
        }else{
            if (bornRule.contains(neighborsCount))
                return true;
            else
                return false;
        }
    }

    public int countNeighbors(Cell c){
        int x = c.getCoordinate().getX();
        int y = c.getCoordinate().getY();
        int count = 0;
        for (Direction direction : Direction.values()) {

//            if (c.getState(x + direction.dx, y + direction.dy)==State.ALIVE) {
//                ++count;
//            }
            int newX = Math.floorMod(x + direction.dx, boardSize);
            int newY = Math.floorMod(y + direction.dy, boardSize);
            Coordinate xy = new Coordinate(newX, newY);
            if (cells.get(xy).getState()==State.ALIVE) {
                ++count;
            }
        }
        return count;
    }
}
