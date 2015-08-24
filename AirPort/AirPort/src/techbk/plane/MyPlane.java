package techbk.plane;

import techbk.game.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MyPlane extends Sprite {
	private int id;
	private int level;

	private int status = 0;

	// khi ha cat canh
	protected float turnSpeed = 200f;
	// khi bay
	protected float turnSpeed1 = 100f;
	protected float accel = 2f;

	private float delta = 0.0f;

	protected float aliveTime = 0.0f;
	protected float parkTime;
	
	private float scale=1;
	private float scaleSpeed=0.02f;
	
	protected Vector2 position = new Vector2();
	protected Vector2 velocity = new Vector2();

	protected Vector2 facing = new Vector2();

	private Vector2 collisionCenter = new Vector2();
	private Array<Vector2> collisionPoints = new Array<Vector2>();

	private BitmapFont font;

	private MyPlaneAI ai = new MyPlaneAI(this);

	/**
	 * khoi tao plane
	 * 
	 * @param id
	 * @param level
	 *            cap cua may bay
	 * @param position
	 *            vi tri khoi tao
	 * @param facing
	 *            mat tiên cua may bay
	 */
	public MyPlane(int id, int level, Vector2 position, Vector2 facing) {
		font = Assets.instance.getFont(level);
		this.id =id;
		this.level=level;
		this.position.set(position);
		this.facing.set(facing);
		this.setOrigin(this.getWidth() / 2.f, this.getHeight() / 2.f);
		collisionPoints.clear();
		collisionPoints.add(new Vector2());
		collisionPoints.add(new Vector2());
		collisionPoints.add(new Vector2());
		collisionPoints.add(new Vector2());
		getSprite();
	}

	/**
	 * dua anh vao
	 */
	private void getSprite() {
		this.set(Assets.instance.getSpritePlane(getLevel()));
	}

	@Override
	public void draw(SpriteBatch batch) {
		delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());

		getAi().update();

		aliveTime = aliveTime + delta;

		// xac dinh diem trung tam cua may bay
		collisionPoints.get(0)
				.set(this.getVertices()[0], this.getVertices()[1]);
		collisionPoints.get(1)
				.set(this.getVertices()[5], this.getVertices()[6]);
		collisionPoints.get(2).set(this.getVertices()[10],
				this.getVertices()[11]);
		collisionPoints.get(3).set(this.getVertices()[15],
				this.getVertices()[16]);

		getCollisionCenter().set(collisionPoints.get(0)).add(collisionPoints.get(2))
				.mul(0.5f);

		velocity.mul((float) Math.pow(0.97f, delta * 60.f));
		position.add(velocity.x * delta, velocity.y * delta);
		this.setScale(scale);
		this.setRotation(facing.angle());
		this.setPosition(position.x, position.y);

		super.draw(batch);
		font.draw(batch, "" + getId(), position.x, position.y);
	}

	protected void mScale(boolean bool){
		delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
		if(bool){
			scale += scaleSpeed* delta;
		}else{
			scale -= scaleSpeed* delta;
		}
		
	}
	
	private void turn(float direction) {
		delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
		facing.rotate(direction * turnSpeed * delta).nor();
	}

	private void turn1(float direction) {
		delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
		facing.rotate(direction * turnSpeed1 * delta).nor();
	}

	private void thrust() {
		velocity.add(facing.x * accel, facing.y * accel);
	}
	
	private static final Vector2 target_direction = new Vector2();

	/**
	 * chuyen dong toi diem dich
	 * 
	 * @param targetPos
	 *            dich
	 * @param forceThrust
	 */
	protected void goTowards(Vector2 targetPos, boolean forceThrust) {
		target_direction.set(targetPos).sub(getCollisionCenter());

		if (facing.crs(target_direction) > 0) {
			turn(1);
		} else {
			turn(-1);
		}

		if (forceThrust || facing.dot(target_direction) > 0) {
			thrust();
		}
	}

	protected void goTowards1(Vector2 targetPos, boolean forceThrust) {
		target_direction.set(targetPos).sub(getCollisionCenter());

		if (facing.crs(target_direction) > 0) {
			turn1(1);
		} else {
			turn1(-1);
		}

		if (forceThrust || facing.dot(target_direction) > 0.8) {//
			thrust();
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public MyPlaneAI getAi() {
		return ai;
	}

	public int getId() {
		return id;
	}


	public float getAliveTime() {
		return aliveTime;
	}

	public int getLevel() {
		return level;
	}

	public float getParkTime() {
		return parkTime;
	}

	public Vector2 getCollisionCenter() {
		return collisionCenter;
	}

	public void setParkTime(float parkTime) {
		this.parkTime = parkTime;
	}

}
