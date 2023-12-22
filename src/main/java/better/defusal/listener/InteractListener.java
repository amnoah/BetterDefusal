package better.defusal.listener;

import better.defusal.BetterDefusal;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteractEntityRightClick(PlayerInteractEntityEvent event) {
        if (event.isCancelled()) return;
        if (!event.getPlayer().hasPermission("better.defusal.use")) return;

        Material defusalItem = BetterDefusal.getInstance().getDefusalItem();
        ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
        ItemStack offHand = event.getPlayer().getInventory().getItemInOffHand();
        Damageable damageable = null;

        // Verify the right item is used.
        if (defusalItem != null) {
            if (mainHand.getType() == defusalItem) {
                if (mainHand.getItemMeta() instanceof Damageable && BetterDefusal.getInstance().isDurabilityUsed())
                    damageable = (Damageable) mainHand.getItemMeta();
            } else if (offHand.getType() == defusalItem) {
                if (offHand.getItemMeta() instanceof Damageable && BetterDefusal.getInstance().isDurabilityUsed())
                    damageable = (Damageable) offHand.getItemMeta();
            } else return;
        }

        Location location = event.getRightClicked().getLocation();
        Material material;

        // Determine the material to be dropped.
        if (event.getRightClicked() instanceof TNTPrimed) {
            material = Material.TNT;
        } else if(event.getRightClicked() instanceof ExplosiveMinecart) {
            material = Material.TNT_MINECART;
        } else if (event.getRightClicked() instanceof FallingBlock && BetterDefusal.getInstance().isAllBlocks()) {
            material = ((FallingBlock) event.getRightClicked()).getBlockData().getMaterial();
        } else return;

        // Drop the material and get rid of the entity.
        event.getRightClicked().getWorld().dropItem(location, new ItemStack(material));
        event.getRightClicked().getWorld().playSound(location, Sound.ENTITY_PLAYER_HURT, 1, 2);
        event.getRightClicked().remove();

        // Apply damage.
        if (damageable == null) return;
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        damageable.setDamage(damageable.getDamage() + 1);
        if (mainHand.getType() == defusalItem) mainHand.setItemMeta(damageable);
        else offHand.setItemMeta(damageable);
    }
}
