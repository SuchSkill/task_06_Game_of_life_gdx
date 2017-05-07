package com.mygdx.game;

public class Cell {
    private Coordinate coordinate;

    private State state;
    private boolean changeState;

    public boolean isChangeState() {
        return changeState;
    }

    public void setChangeState(boolean changeState) {
        this.changeState = changeState;
    }

    public Cell(Coordinate coordinate, State state) {

        this.coordinate = coordinate;
        this.state = state;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    public void changeState() {
        if(state==State.ALIVE){
            state = State.DEAD;
        }else{
            state = State.ALIVE;
        }
    }
}
