package techbk.airport;

import techbk.game.Constants;
import techbk.game.GameRandom;
import techbk.plane.MyPlane;
import techbk.ui.MyLabel;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class AirPortAI {

	private MyPlane tmpPlane = new MyPlane(-1, 1, new Vector2(0, 0),
			new Vector2(0, 1));
	private AirPortScreen airPortScreen;
	private boolean auto;
	private Array<MyPlane> queuesLand;
	private Array<MyPlane> queuesLift;
	private MyPlane flyPlane;
	private MyPlane parkPlane;
	private float time1;
	private float time2;
	protected int sumFly;
	protected int sumPark;
	protected int redPlane;
	protected int greenPlane;
	protected int yellowPlane;
	private long lastDropInfo;

	protected AirPortAI(AirPortScreen gameScreen) {
		this.airPortScreen = gameScreen;
		setAuto(true);
		queuesLand = new Array<MyPlane>();
		queuesLift = new Array<MyPlane>();
	}

	public void update() {
		if (isAuto()) {
			autoRun();
		} else {
			browseAllPlane();
		}
	}

	private void autoRun() {
		browseAllPlane();
		if (airPortScreen.sizeRunway == 1) {
			if (queuesLift.first() != null) {
				if (!airPortScreen.getTerminal().isUsed()
						&& !airPortScreen.getRunways().isFull()) {
					lift(queuesLift.first());
				}
			} else {
				if (queuesLand.first() != null) {

					if (!airPortScreen.getTerminal().isFull()
							&& !airPortScreen.getRunways().isFull()) {
						land(queuesLand.first());
					}
				}
			}
		} else {
			if (queuesLift.first() != null) {
				if (!airPortScreen.getTerminal().isUsed()
						&& !airPortScreen.getRunways().isFull()) {
					lift(queuesLift.first());
				}
			}

			if (queuesLand.first() != null) {
				if (!airPortScreen.getTerminal().isFull()
						&& !airPortScreen.getRunways().isFull()) {
					land(queuesLand.first());
				}
			}
		}
	}

	public void land(int id) {
		MyPlane plane;
		airPortScreen.mScrollPanel.addLabel(new MyLabel(
				"You just have told Plane ID " + id + " to land.",
				airPortScreen.time));

		if (isAuto() == true) {
			airPortScreen.mScrollPanel.addLabel(new MyLabel(
					"AirPort's status is Auto!!!", airPortScreen.time));
			return;
		}

		plane = searchPlane(id);
		if (plane == null) {
			airPortScreen.mScrollPanel.addLabel(new MyLabel("Plane ID " + id
					+ " not exist!!!", airPortScreen.time));
			return;
		}

		if (plane.getStatus() != 0) {
			airPortScreen.mScrollPanel.addLabel(new MyLabel("Plane ID " + id
					+ " is having not status that it can land!",
					airPortScreen.time));
			return;
		}

		if (airPortScreen.getTerminal().isFull()) {
			airPortScreen.mScrollPanel.addLabel(new MyLabel(
					"The terminal is FULL, \nso Plane ID " + id
							+ " can't land Now!", airPortScreen.time));
			return;
		}

		if (airPortScreen.getRunways().isFull()) {
			airPortScreen.mScrollPanel.addLabel(new MyLabel(
					"All runways are being used, \nso Plane ID " + id
							+ " cant land Now!", airPortScreen.time));
			return;
		}
		land(plane);
	}

	private void land(MyPlane plane) {

		queuesLand.removeValue(plane, false);

		// set runway
		plane.getAi().setRunwayIndex(airPortScreen.getRunways().getRunWay());
		plane.getAi().firstPoint.set(airPortScreen.getRunways().getFirst(
				plane.getAi().getRunwayIndex()));
		plane.getAi().lastPoint.set(airPortScreen.getRunways().getLast(
				plane.getAi().getRunwayIndex()));

		// set terminal
		plane.getAi().setSlotIndex(
				airPortScreen.getTerminal().getSlotIndex(
						plane.getAi().getRunwayIndex()));
		plane.getAi()
				.getSlotPos()
				.set(airPortScreen.getTerminal().getSlotPos(
						plane.getAi().getSlotIndex()));

		// set trang thai ha canh
		plane.setStatus(1);
		airPortScreen.mScrollPanel.addLabel(new MyLabel("Plane ID "
				+ plane.getId() + " is landing at Runway ID "
				+ plane.getAi().getRunwayIndex()
				+ "\nthen park at Terminal Slot ID "
				+ plane.getAi().getSlotIndex() + ".", airPortScreen.time));

	}

	public void lift(int id) {
		MyPlane plane;
		airPortScreen.mScrollPanel.addLabel(new MyLabel(
				"You just have told Plane ID " + id + " to lift.",
				airPortScreen.time));

		if (isAuto() == true) {
			airPortScreen.mScrollPanel.addLabel(new MyLabel(
					"AirPort's status is Auto!!!", airPortScreen.time));
			return;
		}

		plane = searchPlane(id);

		if (plane == null) {
			airPortScreen.mScrollPanel.addLabel(new MyLabel("Plane ID " + id
					+ " not exist!!!", airPortScreen.time));
			return;
		}

		if (plane.getStatus() != 5) {
			airPortScreen.mScrollPanel.addLabel(new MyLabel("Plane ID " + id
					+ " is having not status that it can lift!",
					airPortScreen.time));
			return;
		}

		if (airPortScreen.getTerminal().isUsed()) {
			airPortScreen.mScrollPanel.addLabel(new MyLabel(
					"The terminal is being used, so Plane ID " + id
							+ " \ncan't lift Now!", airPortScreen.time));
			return;
		}

		if (airPortScreen.getRunways().isFull()) {
			airPortScreen.mScrollPanel.addLabel(new MyLabel(
					"All runways are being used, so Plane ID " + id
							+ " \ncan't lift Now!", airPortScreen.time));
			return;
		}
		lift(plane);
	}

	private void lift(MyPlane plane) {
		if ((!airPortScreen.getRunways().isFull())
				&& (!airPortScreen.getTerminal().isUsed())) {
			queuesLift.removeValue(plane, false);
			// set target
			plane.getAi().getOutPoint()
					.set(GameRandom.getInstance().getRandomOut());

			// set terminal
			airPortScreen.getTerminal().setUsed(true);
			airPortScreen.getTerminal().setSlot(plane.getAi().getSlotIndex(),
					false);

			// set runway
			plane.getAi()
					.setRunwayIndex(airPortScreen.getRunways().getRunWay());
			airPortScreen.getRunways().setUsed(plane.getAi().getRunwayIndex(),
					true);
			plane.getAi().firstPoint.set(airPortScreen.getRunways().getFirst(
					plane.getAi().getRunwayIndex()));
			plane.getAi().lastPoint.set(airPortScreen.getRunways().getLast(
					plane.getAi().getRunwayIndex()));

			// set trang thai chuan cat canh
			plane.setStatus(6);
			airPortScreen.mScrollPanel.addLabel(new MyLabel("Plane ID "
					+ plane.getId() + " is lifting at Runway ID "
					+ plane.getAi().getRunwayIndex()
					+ "\nfrom Terminal Slot ID " + plane.getAi().getSlotIndex()
					+ ".", airPortScreen.time));
		} else {
			// thong bao may bay chua the cat canh
		}

	}

	private void browseAllPlane() {
		flyPlane = tmpPlane;
		parkPlane = tmpPlane;
		sumFly = 0;
		sumPark = 0;
		redPlane = 0;
		yellowPlane = 0;
		greenPlane = 0;
		if (airPortScreen.mPlane.first() != null) {
			for (MyPlane plane : airPortScreen.mPlane) {
				switch (plane.getLevel()) {
				case 1:
					greenPlane++;
					break;
				case 2:
					yellowPlane++;
					break;
				case 3:
					redPlane++;
					break;
				}
				switch (plane.getStatus()) {
				case 0:// trang thai dang bay Flying
					if (!isAuto()) {
						sumFly++;
						continue;
					}
					sumFly++;
					if (!queuesLand.contains(plane, false)
							&& plane.getAliveTime() > Constants.timeFly) {
						if (flyPlane.equals(tmpPlane)) {
							flyPlane = plane;
						} else {
							time1 = (3 - flyPlane.getLevel())
									* Constants.timeLimit
									- flyPlane.getAliveTime();
							if (plane.getLevel() == 3) {
								queuesLand.insert(0, plane);
								continue;
							}
							time2 = (3 - plane.getLevel())
									* Constants.timeLimit
									- plane.getAliveTime();
							if (time2 < time1)
								flyPlane = plane;
						}
					}

					break;

				case 1:// trang thai may bay dang chuyen tu auto fly sang chuan
						// bi Land
					sumFly++;
					break;

				case 2:// trang thai may bay dang ha canh Landing
					break;

				case 3:// trang thai may bay sau khi ha canh, may bay dung im
						// chuan bi di chuyen ve terminal
					if ((!airPortScreen.getTerminal().isUsed())
							&& (plane.getAliveTime() - plane.getParkTime()) > 2) {
						plane.setStatus(4);
						airPortScreen.getTerminal().setUsed(true);
						// giai phong runways
						airPortScreen.getRunways().setUsed(
								plane.getAi().getRunwayIndex(), false);
					}
					break;

				case 4:// trang thai may bay dang chuyen dong ve phia slot
						// giai phong terminal
					if (plane.getCollisionCenter().epsilonEquals(
							plane.getAi().getSlotPos(), 20)) {
						plane.setStatus(5);
						airPortScreen.getTerminal().setUsed(false);
						plane.setParkTime(plane.getAliveTime());
					}
					break;

				case 5:// trang thai may bay dung im trong terminal, goi la pass
					if (!isAuto()) {
						sumPark++;
						continue;
					}
					sumPark++;
					if (!queuesLift.contains(parkPlane, false)
							&& (plane.getAliveTime() - plane.getParkTime()) > Constants.timePark) {
						if (parkPlane.equals(tmpPlane)) {
							parkPlane = plane;
						} else {
							if ((plane.getAliveTime() - plane.getParkTime()) > (parkPlane
									.getAliveTime() - plane.getParkTime()))
								parkPlane = plane;
						}
					}
					break;

				case 6:// trang thai may bay tu terminal di chuyen toi san bay

					break;

				case 7:// trang thai may bay dung im chuan bi cat canh
					if ((plane.getAliveTime() - plane.getParkTime()) > 2) {
						airPortScreen.getTerminal().setUsed(false);
						plane.setStatus(8);
					}
					break;

				case 8:// trang thai may bay cat canh toi first point
					if (plane.getCollisionCenter().epsilonEquals(
							plane.getAi().firstPoint, 10)) {
						airPortScreen.getRunways().setUsed(
								plane.getAi().getRunwayIndex(), false);
						plane.setStatus(9);
					}
					break;
				case 9:
					if (!GameRandom.getInstance().onScreen(
							plane.getCollisionCenter())) {
						airPortScreen.mPlane.removeValue(plane, false);
					}
					break;
				default:
				}

			}

			if ((TimeUtils.millis() - lastDropInfo > 45000)) {
				spawnInfo();
			}

			if (!flyPlane.equals(tmpPlane)) {

				airPortScreen.mScrollPanel.addLabel(new MyLabel("Plane ID "
						+ flyPlane.getId() + " request to land.",
						airPortScreen.time));
				queuesLand.add(flyPlane);

			}

			if (!(parkPlane.equals(tmpPlane))
					&& (!queuesLift.contains(parkPlane, false))) {
				airPortScreen.mScrollPanel.addLabel(new MyLabel("Plane ID "
						+ parkPlane.getId() + " request to lift.",
						airPortScreen.time));
				queuesLift.add(parkPlane);
			}
		}
	}

	private void spawnInfo() {
		if (queuesLand.first() != null) {
			for (MyPlane plane : queuesLand) {
				airPortScreen.mScrollPanel.addLabel(new MyLabel("Plane ID "
						+ plane.getId() + " request to land.",
						airPortScreen.time));
			}
		}

		if (queuesLift.first() != null) {
			for (MyPlane plane : queuesLift) {
				airPortScreen.mScrollPanel.addLabel(new MyLabel("Plane ID "
						+ plane.getId() + " request to lift.",
						airPortScreen.time));
			}
		}

		lastDropInfo = TimeUtils.millis();
	}

	public MyPlane searchPlane(int id) {
		for (MyPlane plane : airPortScreen.mPlane) {
			if (plane.getId() == id) {
				return plane;
			}
		}
		return null;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

}
