/*
 *     VisualRedstone - PaperMC Plugin to Visualize your Redstone Power.
 *     Copyright © 2024  CMarco
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.cmarco.visualredstone.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import top.cmarco.visualredstone.chat.ChatUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public final class RedstoneRenderingTask implements Runnable {

    private final Collection<UUID> textDisplayEntities = new HashSet<>();
    private final Player player;
    private final Plugin plugin;

    public RedstoneRenderingTask(@NotNull final Plugin plugin, @NotNull final Player player) {
        this.plugin = Objects.requireNonNull(plugin, "Cannot start a " + getClass().getName() + " using a null plugin!");
        this.player = Objects.requireNonNull(player, "Cannot start a " + getClass().getName() + " using a null player!");
    }

    public void removeAllPrevious() {
        for (final UUID uuid : textDisplayEntities) {
            final Entity entity = Bukkit.getEntity(uuid);

            if (entity == null) {
                continue;
            }

            entity.remove();

        }

        textDisplayEntities.clear();
    }

    @Override
    public void run() {
        if (player == null || !player.isOnline()) {
            return;
        }

        removeAllPrevious();

        final World world = Objects.requireNonNull(player.getWorld(), "The player cannot be in a null world!");

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 6; y++) {

                    final Location location = player.getLocation().add(x - 8f, y - 3f, z - 8f);
                    final Block block = world.getBlockAt(location);
                    final BlockData blockData = block.getBlockData();

                    if (!(blockData instanceof final AnaloguePowerable powerable)) {
                        continue;
                    }

                    final int power = powerable.getPower();

                    world.spawn(block.getLocation().add(0.5,0.125,0.5), TextDisplay.class, CreatureSpawnEvent.SpawnReason.CUSTOM,
                            textDisplay -> {
                                textDisplay.setVisibleByDefault(false);
                                textDisplay.setBillboard(Display.Billboard.CENTER);
                                textDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);
                                textDisplay.setSeeThrough(false);
                                textDisplay.text(ChatUtils.getRedstoneHoverComponent(power));
                                player.showEntity(plugin, textDisplay);
                                textDisplayEntities.add(textDisplay.getUniqueId());
                            });

                }
            }
        }
    }

    /**
     * Clears all the data associated to an instance of this class.
     */
    public void clearData() {
        textDisplayEntities.forEach(uuid -> {
            final Entity entity = Bukkit.getEntity(uuid);
            if (entity != null) {
                entity.remove();
            }
        });
        textDisplayEntities.clear();
    }
}
