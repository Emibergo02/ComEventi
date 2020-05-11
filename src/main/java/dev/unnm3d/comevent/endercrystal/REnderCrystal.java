package dev.unnm3d.comevent.endercrystal;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.scheduler.BukkitTask;

import dev.unnm3d.comevent.Main;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.EntityEnderCrystal;

public class REnderCrystal implements Runnable{

	private Location loc;
	private double yhigh;
	private double radius;
	private int nposition;
	private BukkitTask task;
	private ArrayList<Location> puntiCirconferenza;
	private EntityArmorStand entita;
	private EntityEnderCrystal cristallo;
	private Location beamTarget;
	private boolean verso=true;
	private int i;

	/**
	 * Creo EnerCrystal rotante
	 * 
	 * @param locplayer Location del centro della circonferenza di rotazione a terra
	 * @param yhigh     altezza della circonferenza
	 * @param radius    raggio circonferenza
	 * @param nposition	numero posizioni per disegnare la circonferenza
	 */
	public REnderCrystal(Location locplayer, double yhigh, double radius, int nposition,Location beamTarget) {

		this.loc = locplayer;
		this.yhigh = yhigh;
		this.radius = radius;
		this.nposition = nposition;
		this.beamTarget=beamTarget;
		this.puntiCirconferenza=getCircle(locplayer.clone().add(0, yhigh, 0), radius, nposition);
		this.entita=createEnderCrystal(locplayer);
		this.i=0;
		this.task=Bukkit.getScheduler().runTaskTimer(Main.getInstance(), this, 0, 1);
	}
	
	//Crea l'endercrystal su un armorstand data la posizione centrale
	private EntityArmorStand createEnderCrystal(Location loc) {
		//Spawno endercrystal
		EntityEnderCrystal ec = new EntityEnderCrystal(((CraftWorld) loc.getWorld()).getHandle());
		ec.setShowingBottom(false);
		ec.setBeamTarget(new BlockPosition(beamTarget.getX(), beamTarget.getY(), beamTarget.getZ()));
		ec.setPositionRotation(loc.getX(), loc.getY()+yhigh, loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld)loc.getWorld()).getHandle().addEntity(ec, SpawnReason.CUSTOM);
		this.cristallo=ec;
		//ArmorStand su cui sta l'endercrystal
		EntityArmorStand as=new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle());
		as.setArms(false);
		as.setNoGravity(true);
		as.setInvisible(true);
		as.setSmall(true);
		as.setPositionRotation(loc.getX(), loc.getY()+yhigh, loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld)loc.getWorld()).getHandle().addEntity(as, SpawnReason.CUSTOM);
		as.passengers.add(ec);
		return as;
	}
	/**
	 * Restituisce la lista di punti della circonferenza
	 * @param center Location centro circonferenza
	 * @param radius Raggio circonferenza
	 * @param amount Punti della circonferenza da generare
	 * @return	La lista di Locations
	 */
	public ArrayList<Location> getCircle(Location center, double radius, int amount) {
		org.bukkit.World world = center.getWorld();
		double increment = (2 * Math.PI) / amount;
		ArrayList<Location> locations = new ArrayList<Location>();
		for (int i = 0; i < amount; i++) {
			double angle = i * increment;
			double x = center.getX() + (radius * Math.cos(angle));
			double z = center.getZ() + (radius * Math.sin(angle));
			locations.add(new Location(world, x, center.getY(), z));
		}
		return locations;
	}
	/**
	 * Restituisce la task in esecuzione
	 * @return la task
	 */
	public BukkitTask getTask() {
		return task;
	}
	/**
	 * Restituisce l'entità dove poggia l'EnderCrystal
	 * @return l'entità armorstand
	 */
	public EntityArmorStand getEntita() {
		return entita;
	}
	/**
	 * Nuova posizione del target dell'endercrystal
	 * @param beamTarget Location del target
	 */
	public void setBeamTarget(Location beamTarget) {
		this.beamTarget = beamTarget;
	}
	
	/**
	 * Nuovo verso per il giro
	 * @param verso true se orario e false se antiorario
	 */
	public void setVerso(boolean verso) {
		this.verso=verso;
	}
	/**
	 * modifica high
	 * @param y modifica y(+ per alzare e -per diminuire)
	 */
	public void modHigh(double y) {
		for(Location c:puntiCirconferenza) {
			c.setY(c.getY()+y);
		}
	}

	public void run() {
		entita.setPosition(puntiCirconferenza.get(i).getX(), puntiCirconferenza.get(i).getY(), puntiCirconferenza.get(i).getZ());
		cristallo.setBeamTarget(new BlockPosition(beamTarget.getX(), beamTarget.getY(), beamTarget.getZ()));
		if(verso) {
		i++;
		if(i>=nposition-1) {
			i=0;
		}
		}else {
			i--;
			if(i<0) {
				i=nposition-1;
			}
		}
		
	}
	
	
}
