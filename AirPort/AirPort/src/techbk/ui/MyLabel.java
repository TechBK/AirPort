package techbk.ui;

import techbk.game.Assets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MyLabel extends Label {
	public MyLabel(String text, float time) {
		super("*" + timeToString(time) + "* " + text, Assets.instance.getSkin(),
				"default");
	}

	private static String timeToString(float time) {
		return (int) (time / 60) + ":" + (int) (time % 60);
	}
}
