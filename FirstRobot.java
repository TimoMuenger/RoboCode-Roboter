package firstRobot;
import robocode.*;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * FirstRobot - a robot by (your name here)
 */
public class FirstRobot extends Robot
{
	int moveDirection = 1;
	double enemyEnergy = 100;

	/**
	 * run: FirstRobot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here
		setAdjustGunForRobotTurn(true);

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			turnGunRight(30);
			ahead(25 * moveDirection);
			turnGunRight(30);
			turnRight(15 * moveDirection);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		double gunTurn = getHeading() + e.getBearing() - getGunHeading();
		gunTurn = normalizeBearing(gunTurn);
		turnGunRight(gunTurn);

		double power;
		if (e.getDistance() < 150) {
			power = 3;
		} else if (e.getDistance() < 400) {
			power = 2;
		} else {
			power = 1;
		}
		if (getEnergy() > power + 0.5) {
			fire(power);
		}

		double energyDrop = enemyEnergy - e.getEnergy();
		enemyEnergy = e.getEnergy();
		if (energyDrop > 0 && energyDrop <= 3) {
			moveDirection = -moveDirection;
		}

		double moveTurn = e.getBearing() + 90 - (20 * moveDirection);
		moveTurn = normalizeBearing(moveTurn);
		turnRight(moveTurn);

		if (e.getDistance() > 400) {
			ahead(80 * moveDirection);
		} else if (e.getDistance() < 120) {
			back(40 * moveDirection);
		} else {
			ahead(50 * moveDirection);
		}
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		moveDirection = -moveDirection;
		double escape = 90 - e.getBearing();
		escape = normalizeBearing(escape);
		turnRight(escape);
		ahead(70 * moveDirection);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		moveDirection = -moveDirection;
		back(40);
		turnRight(60);
		ahead(40 * moveDirection);
	}	

	public void onHitRobot(HitRobotEvent e) {
		double gunTurn = getHeading() + e.getBearing() - getGunHeading();
		gunTurn = normalizeBearing(gunTurn);
		turnGunRight(gunTurn);
		if (getEnergy() > 3) {
			fire(3);
		}
		moveDirection = -moveDirection;
		back(40);
	}

	public double normalizeBearing(double angle) {
		while (angle > 180) {
			angle -= 360;
		}
		while (angle < -180) {
			angle += 360;
		}
		return angle;
	}
}
