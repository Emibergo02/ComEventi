package dev.unnm3d.comevent.guardianlaser;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import dev.unnm3d.comevent.Main;



public class Discoraggi {

	private static Location rightlaser;
	private static Location leftlaser;
	private static ArrayList<Location> raydxend=new ArrayList<Location>();
	private static ArrayList<Location> raysxend=new ArrayList<Location>();
	private static ArrayList<Laser> lazdx=new ArrayList<Laser>();
	private static ArrayList<Laser> lazsx=new ArrayList<Laser>();
	private static Random r;
	
	// inizializzazione dei raggi e caricamento Location dalle config
	public static void registerxray() {
		for (String s : Main.getInstance().getConfig().getKeys(false)) {
			if (s.equals("raydx0")) {
				rightlaser=(Location) Main.getInstance().getConfig().get(s);
			}else if (s.contains("raysx0")) {
				leftlaser=(Location) Main.getInstance().getConfig().get(s);
			}else if (s.contains("dxend")) {
				raydxend.add((Location) Main.getInstance().getConfig().get(s));
			}else if (s.contains("sxend")) {
				raysxend.add((Location) Main.getInstance().getConfig().get(s));
			}
		}

	}
	// metodo per far partire i laser callato da Comman()
	public static void testlaser() throws ReflectiveOperationException {
		rightlaser();
		leftlaser();
	}
	//raggi di destra
	private static void rightlaser() throws ReflectiveOperationException {
		lazdx=new ArrayList<Laser>();
		for(int i=0;i<5;i++) {
			Laser laser1=new Laser(rightlaser, raydxend.get(new Random().nextInt(raydxend.size())), -1, 120);
			laser1.start(Main.getInstance());
			lazdx.add(laser1);
		}
		new BukkitRunnable(){

			public void run() {
				// TODO Auto-generated method stub
				for(Laser l:lazdx)
					try {
						l.moveEnd(raydxend.get(new Random().nextInt(raydxend.size())))/*raydxend.get(r.nextInt(raydxend.size()))*/;
					} catch (ReflectiveOperationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		
		}.runTaskTimer(Main.getInstance(), 5, 10);
		
	}
	//raggi di sinistra
	private static void leftlaser() throws ReflectiveOperationException {
		lazsx=new ArrayList<Laser>();
		for(int i=0;i<5;i++) {
			Laser laser1=new Laser(leftlaser, raysxend.get(new Random().nextInt(raysxend.size())), -1, 120);
			laser1.start(Main.getInstance());
			lazsx.add(laser1);
		}
		new BukkitRunnable(){

			public void run() {
				// TODO Auto-generated method stub
				for(Laser l:lazsx)
					try {
						l.moveEnd(raysxend.get(new Random().nextInt(raysxend.size())))/*raydxend.get(r.nextInt(raydxend.size()))*/;
					} catch (ReflectiveOperationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		
		}.runTaskTimer(Main.getInstance(), 5, 10);
	}
	public static void stoplasers() {
		for(Laser l:lazdx) {
			l.stop();
		}
		for(Laser l:lazsx) {
			l.stop();
		}
	}
}
