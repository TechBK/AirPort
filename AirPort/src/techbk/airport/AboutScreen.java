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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class AboutScreen implements Screen {
	private final AirPort game;
	private Sprite background;
	private SpriteBatch batch;
	private Stage stage;
	private ButtonControl button1;

	protected AboutScreen(final AirPort game) {
		this.game = game;
		batch = new SpriteBatch();
		background = Assets.instance.getBackGround(3);
		background.setPosition(0, 0);
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true);
		Gdx.input.setInputProcessor(stage);
		createControlButton();

	}

	private void createControlButton() {
		addEventIntoControlButton();
		button1.setBounds(Gdx.graphics.getWidth() / 2 - 100,
				Gdx.graphics.getHeight() / 2 - 300, 200, 35);
		stage.addActor(button1);
	}

	private void addEventIntoControlButton() {

		button1 = new ButtonControl(ButtonId.Id_Back);
		button1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				game.setScreen(new MainMenuScreen(game));
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
