package techbk.terminal;

import com.badlogic.gdx.math.Vector2;

public class Terminals {
	private SlotTerminal[] slots = new SlotTerminal[9];
	private boolean used;

	public Terminals() {
		used = false;
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new SlotTerminal(new Vector2(912, 143 + (i * 40)));
		}
	}

	public boolean isFull() {
		for (int i = 0; i < slots.length; i++) {
			if ((slots[i].used) == false)
				return false;
		}
		return true;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean bool) {
		used = bool;
	}

	public Vector2 getSlotPos(int i) {
		setSlot(i, true);
		return slots[i].position;

	}

	public int getSlotIndex(int runwayId) {
		if(runwayId==0){
			for (int i = 0; i < slots.length; i++) {
				if (slots[i].used == false) {
					setSlot(i, true);
					return i;
				}
			}
		}
		else{
			for (int i = slots.length-1; i >=0; i--) {
				if (slots[i].used == false) {
					setSlot(i, true);
					return i;
				}
			}
		}
		return -1;
	}

	public void setSlot(int i, boolean bool) {
		slots[i].used = bool;
	}

	public int slotNotUsed() {
		int counter = 0;
		for (int i = 0; i < slots.length; i++) {
			if (slots[i].used == false)
				counter++;
		}
		return counter;
	}

	private class SlotTerminal {
		private boolean used;
		private Vector2 position = new Vector2();

		public SlotTerminal(Vector2 pos) {
			used = false;
			position.set(pos);
		}
	}

}
