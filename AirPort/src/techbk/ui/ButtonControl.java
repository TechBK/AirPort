package techbk.ui;

import techbk.game.Assets;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ButtonControl extends TextButton {
	public ButtonId buttonId;

	public ButtonControl(ButtonId buttonId) {
		super(buttonId.toString(), Assets.instance.getSkin(), "default");
		this.buttonId = buttonId;
	}
	
	public enum ButtonId {
		Id_Start1 {
			public String toString() {
				return "Start with \"1\" Runway";
			}
		},
		Id_Start2 {
			public String toString() {
				return "Start with \"2\" Runway";
			}
		},
		Id_About {
			public String toString() {
				return "About";
			}
		},
		Id_Auto {
			public String toString() {
				return "Controller";
			}
		},
		Id_Land {
			public String toString() {
				return "Land";
			}
		},
		Id_Lift {
			public String toString() {
				return "Lift";
			}
		},
		Id_Back {
			public String toString() {
				return "Back";
			}
		}
	}
}
