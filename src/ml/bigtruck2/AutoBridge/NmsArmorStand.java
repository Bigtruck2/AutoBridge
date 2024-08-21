package ml.bigtruck2.AutoBridge;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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
    private AutoBridge autoBridge;
    public NmsArmorStand(Player player, AutoBridge autoBridge){
        this.player = player;
        this.autoBridge = autoBridge;
    }
    public void spawn(Location loc,Vector vector,Player player){
        WorldServer worldServer = ((CraftWorld) this.player.getLocation().getWorld()).getHandle();
        entity = new EntityArmorStand(worldServer);
        double doublePi = 2 * Math.PI;
        double yawAtan = Math.atan2(-vector.getX(), vector.getZ());
        double yaw = Math.toDegrees((yawAtan + doublePi) % doublePi);
        entity.setLocation(loc.getX()+.5, loc.getY()-1.35, loc.getZ()+.5, (float) yaw, 0);

        entity.setInvisible(true);
        entLoc = new Location(entity.getWorld().getWorld(), entity.locX,entity.locY+1.35,entity.locZ, entity.yaw,0);
        entId = entity.getId();
    }
    public void name(Player player, int blocks){
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        DataWatcher dataWatcher = entity.getDataWatcher();
        dataWatcher.watch(2, autoBridge.getConfig().getString("Hologram-text.Blocks").replace("<blocks>",String.valueOf(blocks)));
        playerConnection.sendPacket(new PacketPlayOutEntityMetadata(entId, dataWatcher, true));
    }
    public void show(Player player, int blocks, int tier){
        org.bukkit.inventory.ItemStack itemStack = new org.bukkit.inventory.ItemStack(Material.SKULL_ITEM);
        itemStack.setDurability((short) 3);
        NBTUtil.setHeadSkin(itemStack,player, tier,autoBridge);
        ItemStack itemStack1 = CraftItemStack.asNMSCopy(itemStack);
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(entity));
        playerConnection.sendPacket(new PacketPlayOutEntityEquipment(entity.getId(),4,itemStack1));

        DataWatcher dataWatcher = entity.getDataWatcher();
        dataWatcher.watch(2, autoBridge.getConfig().getString("Hologram-text.Blocks").replace("<blocks>",String.valueOf(blocks)));
        dataWatcher.watch(3, (byte) autoBridge.getConfig().getInt("Hologram"));
        playerConnection.sendPacket(new PacketPlayOutEntityMetadata(entId, dataWatcher, true));
    }
    public void remove(Player player, int entId){
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        playerConnection.sendPacket(new PacketPlayOutEntityDestroy(entId));
    }
    public void move(Player player, Vector vector){
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        playerConnection.sendPacket(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(entity.getId(), (byte) (vector.getX() * 32.0D), (byte)(0), (byte) (vector.getZ() * 32.0D),false));

    }
    public void serverMove(Vector vector){
        int x = (int) (vector.getX()*32.0D);
        int z = (int) (vector.getZ()*32.0D);
        entLoc.add(x / 32.0D, 0, z / 32.0D);
    }

    public Location getEntLoc() {
        return entLoc.clone();
    }
    public int getEntId() {
        return entId;
    }
}
