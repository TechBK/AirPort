package techbk.airport;

import techbk.game.Assets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class AirPort extends Game {	
	@Override
	public void create(){
		Assets.instance.init(new AssetManager());
        this.setScreen(new MainMenuScreen(this));
	}
	
	/**
	 * quan trong, ke thua lop cha
	 */
	public void render(){
        super.render(); //important!
    }
	
	
    public void dispose(){
    	Assets.instance.dispose();
    }

}
