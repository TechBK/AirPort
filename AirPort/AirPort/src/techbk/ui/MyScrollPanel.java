package techbk.ui;

import techbk.game.Assets;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MyScrollPanel extends ScrollPane {

	private static Table mTableContent = new Table();

	public MyScrollPanel() {
		super(mTableContent.padBottom(40), Assets.instance.getSkin());
		this.setScrollingDisabled(false, false);
	}

	public void addLabel(MyLabel pLabel) {
		mTableContent.add(pLabel).left();
		mTableContent.row();
		this.scrollTo(0, 0, 0, 0);
	}

}
