package techbk.runway;

import com.badlogic.gdx.math.Vector2;

public class RunWays {
	private boolean direct = true;
	private RunWay[] runway;

	public RunWays(int size) {
		runway = new RunWay[size];
		for (int i = 0; i < runway.length; i++) {
			runway[i] = new RunWay(new Vector2(300, 185 + i * 166),
					new Vector2(835, 185 + i * 166));
		}
	}

	public boolean isFull() {
		for (int i = 0; i < runway.length; i++) {
			if (runway[i].used == false)
				return false;
		}
		return true;
	}

	public int getRunWay() {
		if(direct){
			for (int i = 0; i < runway.length; i++) {
				if (runway[i].used == false) {
					direct=!direct;
					setUsed(i,true);
					return i;
				}
			}
		}else{
			for (int i = runway.length-1; i>=0 ; i--) {
				if (runway[i].used == false) {
					direct=!direct;
					setUsed(i,true);
					return i;
				}
			}
		}
		
		return -1;
	}

	public Vector2 getFirst(int i) {
		return runway[i].first;
	}

	public Vector2 getLast(int i) {
		return runway[i].last;
	}

	public void setUsed(int i, boolean bool) {
		runway[i].used = bool;
	}

	private class RunWay {
		private Vector2 first;
		private Vector2 last;
		private boolean used;

		public RunWay(Vector2 v1, Vector2 v2) {
			used = false;
			first = new Vector2(v1);
			last = new Vector2(v2);
		}
	}
}
