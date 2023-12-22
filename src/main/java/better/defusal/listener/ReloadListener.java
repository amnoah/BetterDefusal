package better.defusal.listener;

import better.defusal.BetterDefusal;
import better.reload.api.ReloadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReloadListener implements Listener {

    @EventHandler
    public void onReload(ReloadEvent event) {
        BetterDefusal.getInstance().reload();
    }
}
