package techbk.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameRandom {
	private static GameRandom instance = new GameRandom();

	private GameRandom() {
	}

	public static GameRandom getInstance() {
		return instance;
	}

	public boolean onScreen(Vector2 position) {
		return (position.x >= Constants.screenLeft)
				&& (position.x <= Constants.screenRight)
				&& (position.y >= Constants.screenBottom)
				&& (position.y <= Constants.screenTop);
	}

	/**
	 * chon target Random tren Screen
	 * 
	 * @return
	 */
	public Vector2 getRandomTarget() {
		Vector2 target = new Vector2();
		target.x = MathUtils.random(Constants.screenLeft + 10f,
				Constants.screenRight - 10f);
		target.y = MathUtils.random(Constants.screenBottom + 10f,
				Constants.screenTop - 10f);
		return target;
	}

	public Vector2 getRandomOut() {
		Vector2 target = new Vector2();
		int percent = MathUtils.random(100);
		if (percent > 75) {
			target.x = MathUtils.random(Constants.screenLeft,
					Constants.screenRight);
			target.y = Constants.screenTop + 100f;
		} else if (percent > 50) {
			target.x = Constants.screenRight + 100f;
			target.y = MathUtils.random(Constants.screenBottom,
					Constants.screenTop);
		} else if (percent > 25) {
			target.x = MathUtils.random(Constants.screenLeft,
					Constants.screenRight);
			target.y = Constants.screenBottom - 100f;
		} else {
			target.x = Constants.screenLeft - 100f;
			target.y = MathUtils.random(Constants.screenBottom,
					Constants.screenTop);
		}
		return target;
	}

	public int getRandomLevel() {
		int level = 1;
		int percent = MathUtils.random(100);
		if (percent > 70) {
			level = 3;
		} else if (percent > 40) {
			level = 2;
		} else {
			level = 1;
		}
		return level;
	}
}
