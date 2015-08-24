package techbk.airport;

import techbk.game.Assets;
import techbk.game.Constants;
import techbk.game.GameRandom;
import techbk.plane.MyPlane;
import techbk.runway.RunWays;
import techbk.terminal.Terminals;
import techbk.ui.ButtonControl;
import techbk.ui.ButtonControl.ButtonId;
import techbk.ui.MyLabel;
import techbk.ui.MyScrollPanel;
import techbk.ui.MyTextField;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class AirPortScreen implements Screen {
	private final AirPort game;
	private SpriteBatch gameBatch;
	private Table mTableMain;
	private Stage stage;
	private BitmapFont whiteFont;
	private BitmapFont redFont;
	private BitmapFont greenFont;
	private BitmapFont yellowFont;
	private Sprite background;
	//
	private AirPortAI ai;
	private Terminals terminal;
	//
	private RunWays runways;
	protected int sizeRunway;
	//
	protected Array<MyPlane> mPlane;
	private long lastDropTime = 0;
	private int id = 0;
	//
	protected float time = 0;
	//
	private ButtonControl button1;
	private ButtonControl button2;
	private ButtonControl button3;
	private ButtonControl button4;
	protected MyScrollPanel mScrollPanel;
	private MyTextField textField;
	private int number;

	protected AirPortScreen(final AirPort game, int sizeRunway) {
		this.game = game;
		this.sizeRunway = sizeRunway;
		whiteFont = Assets.instance.getFont(0);
		redFont = Assets.instance.getFont(3);
		greenFont = Assets.instance.getFont(1);
		yellowFont = Assets.instance.getFont(2);
		//
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true);
		Gdx.input.setInputProcessor(stage);
		textField = new MyTextField();
		mTableMain = new Table();
		mTableMain.setFillParent(true);
		createScrollPanel();
		createControlButton();
		stage.addActor(mTableMain);
		//
		ai = new AirPortAI(this);
		//
		setTerminal(new Terminals());
		setRunways(new RunWays(sizeRunway));
		//
		background = Assets.instance.getBackGround(1);
		background.setPosition(0, 0);
		//
		gameBatch = new SpriteBatch();
		//
		mPlane = new Array<MyPlane>();
		//
		spawnPlane(id++);
	}

	private void spawnPlane(int id) {
		Vector2 position = GameRandom.getInstance().getRandomOut();
		Vector2 facing = new Vector2(1, 0);
		int level = GameRandom.getInstance().getRandomLevel();
		MyPlane plane = new MyPlane(id, level, position, facing);
		mPlane.add(plane);
		mScrollPanel.addLabel(new MyLabel("Plane ID " + id
				+ " just have arrived.", time));
		lastDropTime = TimeUtils.millis();
	}

	private void createControlButton() {
		Table mTableControl = new Table();
		mTableControl.setSize(300, 200);
		addEventIntoControlButton();

		mTableControl.add(textField).pad(10).width(100);
		mTableControl.row();
		//
		Table mSubTableControl = new Table();
		mSubTableControl.add(button1).pad(5).width(50);
		mSubTableControl.add(button2).pad(5).width(50);

		mTableControl.add(mSubTableControl).pad(5).center();
		mTableControl.row();
		//
		mTableControl.add(button3).expand().padTop(10).width(100).center();
		mTableControl.row();
		mTableControl.add(button4).expand().padTop(10).width(100).center();
		//
		mTableMain.row();
		mTableMain.add(mTableControl).padRight(50).padTop(100).padBottom(200)
				.right();
	}

	private void addEventIntoControlButton() {
		button1 = new ButtonControl(techbk.ui.ButtonControl.ButtonId.Id_Land);
		button1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					number = Integer.parseInt(textField.getText());
					ai.land(number);
					textField.setText("");
				} catch (NumberFormatException e) {
					mScrollPanel.addLabel(new MyLabel("Plzz put a number!!!",
							time));
					textField.setText("");
				}

			}
		});

		button2 = new ButtonControl(ButtonId.Id_Lift);
		button2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				try {
					number = Integer.parseInt(textField.getText());
					ai.lift(number);
					textField.setText("");
				} catch (NumberFormatException e) {
					mScrollPanel.addLabel(new MyLabel("Plzz put a number!!!",
							time));
					textField.setText("");
				}
			}
		});

		button3 = new ButtonControl(ButtonId.Id_Auto);
		button3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (ai.isAuto()) {
					ai.setAuto(!ai.isAuto());
					mScrollPanel
							.addLabel(new MyLabel(
									"You just have changed AirPort to Controller !",
									time));
					button3.setText("Auto");
				} else {
					ai.setAuto(!ai.isAuto());
					mScrollPanel.addLabel(new MyLabel(
							"You just have changed AirPort to Auto !", time));
					button3.setText("Controller");
				}

			}
		});

		button4 = new ButtonControl(ButtonId.Id_Back);
		button4.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				game.setScreen(new MainMenuScreen(game));
			}
		});

	}

	private void createScrollPanel() {
		mScrollPanel = new MyScrollPanel();
		// mScrollPanel.clear();
		mTableMain.add(mScrollPanel).expand().pad(10).width(380).height(200)
				.top().right();
		mScrollPanel.addLabel(new MyLabel("Airport have been started with "
				+ sizeRunway + " Runways.", time));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//
		time += Gdx.graphics.getDeltaTime();
		//
		ai.update();
		//
		gameBatch.begin();
		background.draw(gameBatch);

		for (MyPlane plane : mPlane) {
			plane.draw(gameBatch);
		}

		gameBatch.end();

		stage.act();
		stage.draw();

		gameBatch.begin();
		drawInfo();
		gameBatch.end();
		//
		if ((TimeUtils.millis() - lastDropTime > Constants.timeSpawn)
				&& (mPlane.size < (8 + (sizeRunway - 1) * 2))) {
			spawnPlane(id++);
		}
	}

	private void drawInfo() {
		whiteFont.draw(gameBatch, (int) (time / 60) + ":" + (int) (time % 60),
				20, Gdx.graphics.getHeight() - 20);
		if(ai.isAuto()) whiteFont.draw(gameBatch, "Status: Auto", 80, Gdx.graphics.getHeight() - 20);
		else whiteFont.draw(gameBatch, "Status: Controller", 80, Gdx.graphics.getHeight() - 20);
		whiteFont.draw(gameBatch, "Run Way: " + sizeRunway, 210,
				Gdx.graphics.getHeight() - 20);
		whiteFont.draw(gameBatch, "Sum Plane: " + mPlane.size, 350,
				Gdx.graphics.getHeight() - 20);
		
		whiteFont.draw(gameBatch, "Red Plane: ", 80,
				Gdx.graphics.getHeight() - 50);
		redFont.draw(gameBatch, "" + ai.redPlane, 160,
				Gdx.graphics.getHeight() - 50);
		whiteFont.draw(gameBatch, "Yellow Plane: ", 210,
				Gdx.graphics.getHeight() - 50);
		yellowFont.draw(gameBatch, "" + ai.yellowPlane, 300,
				Gdx.graphics.getHeight() - 50);

		whiteFont.draw(gameBatch, "Green Plane: ", 350,
				Gdx.graphics.getHeight() - 50);
		greenFont.draw(gameBatch, "" + ai.greenPlane, 440,
				Gdx.graphics.getHeight() - 50);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.clear();
		stage.dispose();
		// TODO Auto-generated method stub
		gameBatch.dispose();
	}

	public Terminals getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminals terminal) {
		this.terminal = terminal;
	}

	public RunWays getRunways() {
		return runways;
	}

	public void setRunways(RunWays runways) {
		this.runways = runways;
	}

}
