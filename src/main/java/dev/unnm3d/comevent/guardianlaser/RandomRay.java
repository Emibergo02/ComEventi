package dev.unnm3d.comevent.guardianlaser;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

import dev.unnm3d.comevent.Main;

public class RandomRay {

	private ArrayList<Location> targets;
	private Location center;
	private int nRays;
	private int delay;
	private ArrayList<Laser> lasers;
	private BukkitTask task;
	private static Random srand=new Random();

	/**
	 * Random ray (targets not set)
	 * @param center origin of the rays
	 * @param n number of rays active per run
	 * @param delay execution delay
	 */
	public RandomRay(Location center,int n,int delay) {
		this.center = center;
		this.targets = new ArrayList<Location>();
		this.nRays=n;
		this.delay=delay;
		this.lasers=new ArrayList<Laser>();
	}



	/**
	 * Start this lightshow
	 */
	public void start() {
		if(targets.size()<nRays) {
			Bukkit.getConsoleSender().sendMessage("RandomRay creation: you inserted too few targets nRay>targets");
			return;
		}
			for(int i=0;i<nRays;i++) {
			try {
				Laser temp=new Laser(center,targets.get(i),Integer.MAX_VALUE,64);
				temp.start(Main.getInstance());
				lasers.add(temp);
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}catch(ArrayIndexOutOfBoundsException ex) {
				
				ex.printStackTrace();
			}
		}
		task=Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new Runnable() {

			public void run() {
				for(Laser l:lasers) {
					try {
						l.moveEnd(targets.get(srand.nextInt(targets.size())));
					} catch (ReflectiveOperationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		}, 0, delay);
	}
	
	/**
	 * Stops the task
	 */
	public void stop() {
		for(Laser l:lasers) {
			l.stop();
		}
		if(task!=null) {
			task.cancel();
		}
	}
	
	
	public Location getCenter() {
		return center;
	}

	/**
	 * Add laser target
	 * @param add target location to add
	 */
	public void addTarget(Location add) {
		this.targets.add(add);
	}

	/**
	 * Clear laser targets
	 */
	public void wipeTargets() {
		this.targets = new ArrayList<Location>();
	}
	
	public void setTargets(ArrayList<Location> targets) {
		this.targets = targets;
	}
}
