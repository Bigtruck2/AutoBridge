package ml.bigtruck2.AutoBridge;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class NmsArmorStand {
    private final Player player;
    private EntityArmorStand entity;
    private Location entLoc;
    private int entId;
    public NmsArmorStand(Player player){
        this.player = player;
    }
    public void spawn(Location loc,Vector vector,Player player){
        WorldServer worldServer = ((CraftWorld) this.player.getLocation().getWorld()).getHandle();
        entity = new EntityArmorStand(worldServer);
        entity.setLocation(loc.getX()+.5, loc.getY()-1.35, loc.getZ()+.5, player.getLocation().getYaw(), 0);
        entity.setInvisible(true);
        entLoc = new Location(entity.getWorld().getWorld(), entity.locX,entity.locY+1.35,entity.locZ, entity.yaw,0);
        entId = entity.getId();
    }
    public void show(Player player, short dur){
        org.bukkit.inventory.ItemStack itemStack = new org.bukkit.inventory.ItemStack(Material.SKULL_ITEM);
        itemStack.setDurability(dur);
        ItemStack itemStack1 = CraftItemStack.asNMSCopy(itemStack);
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(entity));

        playerConnection.sendPacket(new PacketPlayOutEntityEquipment(entity.getId(),4,itemStack1));
    }
    public void remove(Player player, int entId){
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        playerConnection.sendPacket(new PacketPlayOutEntityDestroy(entId));
    }
    public void move(Player player, Vector vector){
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        playerConnection.sendPacket(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(entity.getId(), (byte) ((int) (vector.getX() * 32.0D)), (byte)(0), (byte)((int) (vector.getZ() * 32.0D)),false));
        entLoc.add(vector.getX(), 0, vector.getZ());
    }

    public Location getEntLoc() {
        return entLoc.clone();
    }
    public int getEntId() {
        return entId;
    }
}
