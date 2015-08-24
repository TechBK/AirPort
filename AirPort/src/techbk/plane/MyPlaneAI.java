package techbk.plane;

import techbk.game.GameRandom;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MyPlaneAI {

	private Vector2 target;
	private Vector2 to_target = new Vector2();

	public Vector2 firstPoint = new Vector2();
	public Vector2 lastPoint = new Vector2();
	private Vector2 outPoint = new Vector2();
	private int runwayIndex;

	private Vector2 slotPos = new Vector2();
	private int slotIndex;

	private MyPlane plane;

	protected MyPlaneAI(MyPlane plane) {
		this.plane = plane;
		retarget();
	}

	private void retarget() {
		target = GameRandom.getInstance().getRandomTarget();
	}

	protected void update() {
		switch (plane.getStatus()) {
		case 0:// trang thai dang bay
			fly();
			break;

		case 1:// trang thai may bay dang chuyen tu auto fly sang chuan bi Land
			plane.goTowards(firstPoint, false);
			if (plane.getCollisionCenter().epsilonEquals(firstPoint, 30))
				plane.setStatus(2);
			break;

		case 2:// trang thai may bay dang ha canh
			plane.goTowards(lastPoint, false);
			plane.mScale(false);
			if (plane.getCollisionCenter().epsilonEquals(lastPoint, 30)) {
				plane.setStatus(3);
				plane.parkTime = plane.aliveTime;
			}

		case 3:// trang thai may bay sau khi ha canh, may bay dung im chuan bi
				// di chuyen ve terminal
			// doi lenh cua airportAi
			break;

		case 4:// trang thai may bay dang chuyen dong ve phia terminal
			plane.goTowards(slotPos, false);
			break;

		case 5:// trang thai may bay dung im trong terminal, goi la park

			break;
		case 6:// trang thai may bay tu terminal di chuyen toi san bay
			plane.goTowards(lastPoint, false);
			if (plane.getCollisionCenter().epsilonEquals(lastPoint, 30)) {
				plane.setStatus(7);
				plane.parkTime = plane.aliveTime;
			}
			break;
		case 7:// trang thai may bay dung im chuan bi cat canh
				// doi lenh cua airportAi
			break;
		case 8:// trang thai may bay cat canh trn runway
			plane.goTowards(firstPoint, false);
			plane.mScale(true);
			break;
		case 9:
			plane.goTowards1(getOutPoint(), true);
			break;
		default:// set loi may bay

		}
	}

	private void fly() {
		to_target.set(target.x - plane.getCollisionCenter().x,
				target.y - plane.getCollisionCenter().y);
		float dist_squared = to_target.dot(to_target);
		boolean too_close = dist_squared < Math.pow(50, 2);

		boolean on_screen = GameRandom.getInstance().onScreen(
				plane.getCollisionCenter());

		if (target == null || too_close || !on_screen
				|| (MathUtils.random() < 0.005f)) {
			retarget();
		}

		if (target != null) {
			// go towards the target and attack!
			plane.goTowards1(target, true);
		}
	}

	public int getRunwayIndex() {
		return runwayIndex;
	}

	public void setRunwayIndex(int runwayIndex) {
		this.runwayIndex = runwayIndex;
	}

	public Vector2 getOutPoint() {
		return outPoint;
	}

	public void setOutPoint(Vector2 outPoint) {
		this.outPoint = outPoint;
	}

	public int getSlotIndex() {
		return slotIndex;
	}

	public void setSlotIndex(int slotIndex) {
		this.slotIndex = slotIndex;
	}

	public void setSlotPos(Vector2 slotPos) {
		this.slotPos = slotPos;
	}

	public Vector2 getSlotPos() {
		return slotPos;
	}
}
