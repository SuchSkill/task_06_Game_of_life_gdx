package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// http://www.conwaylife.com/wiki/Rulestring#Rules
public class GameOfLife extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;
	Board board;
	int boardSize = 25;//rectangular
	final int CELL_SIZE = 10;//rectangular
	final int CELL_SCALE = CELL_SIZE;
	List<Integer> bornRule = new ArrayList<Integer>();
	List<Integer> survivalRule = new ArrayList<Integer>();
	OrthographicCamera camera;
	String preSet[] = {"Glider", "R-pentomino", "Diehard"};
	String rule = "3/12345";
	private int delay = 100;

	public GameOfLife() throws URISyntaxException {
	}


	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(true);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);


		parseRules();


		try {
			board = new Board(boardSize, bornRule, survivalRule, Paths.get(ClassLoader.getSystemResource(preSet[2]).toURI()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseRules() {
		String[] split = rule.split("/");
		String[] born = split[0].split("");
		String[] survive = split[1].split("");
		initList(born, bornRule);
		initList(survive, survivalRule);
	}

	private void initList(String[] splitedString, List<Integer> list) {
		for (String s : splitedString) {
			list.add(Integer.parseInt(s));
		}
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (Coordinate coordinate : board.getCells().keySet()) {
			if (board.getCell(coordinate).getState() == State.ALIVE){
				shapeRenderer.setColor(Color.BLACK);
			}
			shapeRenderer.rect(
					10+(board.getCell(coordinate).getCoordinate().getX()*CELL_SCALE)+board.getCell(coordinate).getCoordinate().getX(),
					10+(board.getCell(coordinate).getCoordinate().getY()*CELL_SCALE)+board.getCell(coordinate).getCoordinate().getY(),
					CELL_SIZE, CELL_SIZE);

			shapeRenderer.setColor(Color.WHITE);
		}

		shapeRenderer.end();
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		board.updateState();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
