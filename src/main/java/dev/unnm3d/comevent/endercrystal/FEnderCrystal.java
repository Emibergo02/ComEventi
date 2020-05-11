package dev.unnm3d.comevent.endercrystal;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.EntityEnderCrystal;

public class FEnderCrystal {

	private Location loc;
	private EntityArmorStand entita;
	private EntityEnderCrystal cristallo;
	private Location beamTarget;


	/**
	 * Creo EnerCrystal rotante
	 * 
	 * @param locplayer Location dell'endercrystal(armorstand)
	 * @param beamTarget location del target dell'endercrystal
	 */
	public FEnderCrystal(Location locplayer,Location beamTarget) {

		this.loc = locplayer;
		this.beamTarget=beamTarget;
		this.entita=createEnderCrystal(locplayer);
	}
	
	//Crea l'endercrystal su un armorstand data la posizione centrale
	private EntityArmorStand createEnderCrystal(Location loc) {
		//Spawno endercrystal
		EntityEnderCrystal ec = new EntityEnderCrystal(((CraftWorld) loc.getWorld()).getHandle());
		ec.setShowingBottom(false);
		ec.setBeamTarget(new BlockPosition(beamTarget.getX(), beamTarget.getY(), beamTarget.getZ()));
		ec.setPositionRotation(loc.clone().getX(), loc.clone().getY(), loc.clone().getZ(), loc.clone().getYaw(), loc.clone().getPitch());
		((CraftWorld)loc.getWorld()).getHandle().addEntity(ec, SpawnReason.CUSTOM);
		this.cristallo=ec;
		//ArmorStand su cui sta l'endercrystal
		EntityArmorStand as=new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle());
		as.setArms(false);
		as.setNoGravity(true);
		as.setInvisible(true);
		as.setSmall(true);
		as.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld)loc.getWorld()).getHandle().addEntity(as, SpawnReason.CUSTOM);
		as.passengers.add(ec);
		return as;
	}

	/**
	 * Nuova posizione del target dell'endercrystal
	 * @param beamTarget Location del target
	 */
	public void setBeamTarget(Location beamTarget) {
		this.cristallo.setBeamTarget(new BlockPosition(beamTarget.getX(), beamTarget.getY(), beamTarget.getZ()));
		this.beamTarget = beamTarget;
	}
	
	/**
	 * Sposta tutto il malloppo su quella location
	 * @param l location dove si deve spostare
	 */
	public void sposta(Location l) {
		this.loc=l;
		this.entita.setPosition(l.getX(), l.getY(), l.getZ());
	}
	/**
	 * Restituisce la location dell'armorstand e quindi dell'EnderCrystal
	 * @return Location dell'armorstand
	 */
	public Location getLoc() {
		return loc;
	}

	public Location getBeamTarget() {
		return beamTarget;
	}
	
	
}
