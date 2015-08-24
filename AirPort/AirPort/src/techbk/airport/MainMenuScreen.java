package techbk.airport;

import techbk.game.Assets;
import techbk.ui.ButtonControl;
import techbk.ui.ButtonControl.ButtonId;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen implements Screen {
	private final AirPort game;
	private Sprite background;
	private SpriteBatch batch;
	private Stage stage;
	private Table mTableControl;
	private ButtonControl button1;
	private ButtonControl button2;
	private ButtonControl button3;

	public MainMenuScreen(final AirPort game) {
		this.game = game;
		batch = new SpriteBatch();
		background = Assets.instance.getBackGround(2);
		background.setPosition(0, 0);
		mTableControl = new Table();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true);
		Gdx.input.setInputProcessor(stage);
		createControlButton();
	}

	private void createControlButton() {
		mTableControl.setBounds(Gdx.graphics.getWidth() / 2 - 100,
				Gdx.graphics.getHeight() / 2 - 50, 200, 100);
		addEventIntoControlButton();

		mTableControl.add(button1).center().width(200).padBottom(20);
		mTableControl.row();
		mTableControl.add(button2).center().width(200).padBottom(20);
		mTableControl.row();
		mTableControl.add(button3).center().width(200);
		stage.addActor(mTableControl);

	}

	private void addEventIntoControlButton() {

		button1 = new ButtonControl(ButtonId.Id_Start1);
		button1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new AirPortScreen(game, 1));
				dispose();
			}
		});

		button2 = new ButtonControl(ButtonId.Id_Start2);
		button2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new AirPortScreen(game, 2));
				dispose();
			}
		});

		button3 = new ButtonControl(ButtonId.Id_About);
		button3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new AboutScreen(game));
				dispose();
			}
		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		background.draw(batch);
		batch.end();
		stage.act();
		stage.draw();
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
	}
}