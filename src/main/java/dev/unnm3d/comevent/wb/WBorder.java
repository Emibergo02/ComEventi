package dev.unnm3d.comevent.wb;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_12_R1.WorldBorder;

public class WBorder {
	/**World of the border*/
	private World world;
	/**Center of the wb*/
	private Location center;
	/**Radius of the wb*/
	private int size;
	/**Warning block from the border*/
	private int warning;

	/**
	 * WorldBorder default
	 * Center in 0,0
	 * Size almost 30000000
	 * Warning blocks 4
	 * @param w world of the border
	 */
	public WBorder(World w) {
		this.world=w;
		this.center=new Location(w,0,0,0);
		this.size=29999984;
		this.warning=4;
	}
	/**
	 * WorldBorder center
	 * Size almost 30000000
	 * Warning blocks 4
	 * @param center centro del wb
	 */
	public WBorder(Location center) {
		this.world=center.getWorld();
		this.center=center;
		this.size=29999984;
		this.warning=4;
	}
	/**
	 * WorldBorder size
	 * Warning blocks 4
	 * @param center center of the wb
	 * @param size radius of the wb
	 */
	public WBorder(Location center,int size) {
		this.world=center.getWorld();
		this.center=center;
		this.size=size;
		this.warning=4;
	}
	/**
	 * WorldBorder warning
	 * @param center center of the wb
	 * @param size radius of the wb
	 * @param warning warning blocks from border
	 */
	public WBorder(Location center,int size,int warning) {
		this.world=center.getWorld();
		this.center=center;
		this.size=size;
		this.warning=warning;
	}
	
	/**
	 * Send worldborder packet to defined player
	 * @param p player to send packet
	 */
	public void sendToPlayer(Player p) {
        WorldBorder wb = new WorldBorder();
        wb.setSize(size);
        wb.world=((CraftWorld)world).getHandle();
        wb.setCenter(center.getX(), center.getZ());
        wb.setWarningDistance(warning);
        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(wb, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
	}
	
	
	
	public Location getCenter() {
		return center;
	}
	public void setCenter(Location center) {
		this.center = center;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getWarning() {
		return warning;
	}
	public void setWarning(int warning) {
		this.warning = warning;
	}
	public World getWorld() {
		return world;
	}
}
