package techbk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
	private Texture texture1;
	private Texture texture2;
	private Texture texture3;
	private Texture texture4;
	private Texture texture5;
	private Texture texture6;
	private AssetManager assetManager;
	public static final Assets instance = new Assets();
	private BitmapFont whiteFont;
	private BitmapFont redFont;
	private BitmapFont greenFont;
	private BitmapFont yellowFont;
	private Skin skin;

	private Assets() {
	}

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		skin = new Skin(Gdx.files.internal("data/uiskin.json"),
				new TextureAtlas(Gdx.files.internal("data/uiskin.atlas")));
		whiteFont = new BitmapFont();
		redFont = new BitmapFont();
		redFont.setColor(Color.RED);
		greenFont = new BitmapFont();
		greenFont.setColor(Color.GREEN);
		yellowFont = new BitmapFont();
		yellowFont.setColor(Color.YELLOW);
		
		assetManager.load("images/green767.png", Texture.class);
		assetManager.load("images/yellow767.png", Texture.class);
		assetManager.load("images/red767.png", Texture.class);
		assetManager.load("images/airport.png", Texture.class);
		assetManager.load("images/mainmenu.png", Texture.class);
		assetManager.load("images/about.png", Texture.class);

		assetManager.finishLoading();

		texture1 = assetManager.get("images/green767.png");
		texture2 = assetManager.get("images/yellow767.png");
		texture3 = assetManager.get("images/red767.png");
		texture4 = assetManager.get("images/airport.png");
		texture5 = assetManager.get("images/mainmenu.png");
		texture6 = assetManager.get("images/about.png");

		texture1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		texture2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		texture3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		texture4.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		texture5.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		texture6.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}

	public Sprite getBackGround(int screen) {
		Sprite sprite = null;
		switch (screen) {
		case 1:
			sprite = new Sprite(texture4);
			break;
		case 2:
			sprite = new Sprite(texture5);
			break;
		case 3:
			sprite = new Sprite(texture6);
			break;
		}
		return sprite;
	}

	public Sprite getSpritePlane(int level) {
		Sprite sprite = null;
		switch (level) {
		case 1:
			sprite = new Sprite(texture1);
			sprite.setSize(Constants.planesize, Constants.planesize);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			break;
		case 2:
			sprite = new Sprite(texture2);
			sprite.setSize(Constants.planesize, Constants.planesize);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			break;
		case 3:
			sprite = new Sprite(texture3);
			sprite.setSize(Constants.planesize, Constants.planesize);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			break;
		}
		return sprite;
	}

	public BitmapFont getFont(int font) {
		switch(font){
		case 0:
			return whiteFont;
		case 1:
			return greenFont;
		case 2:
			return yellowFont;
		case 3:
			return redFont;
		}
		return null;
	}

	public Skin getSkin() {
		return skin;
	}

}
