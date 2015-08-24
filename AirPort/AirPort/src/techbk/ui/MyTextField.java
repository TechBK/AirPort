package techbk.ui;

import techbk.game.Assets;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class MyTextField extends TextField {

	public MyTextField() {
		super("", Assets.instance.getSkin());
	}

}
