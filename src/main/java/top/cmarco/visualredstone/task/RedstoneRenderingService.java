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
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class RedstoneRenderingService {

    public RedstoneRenderingService(@NotNull final Plugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "Tried to instantiate " + getClass().getName() + " with a null plugin instance!");
    }

    // Instance fields:

    private final Map<UUID, BukkitTask> activeRenderTasks = new HashMap<>();
    private final Map<UUID, RedstoneRenderingTask> runnableTasks = new HashMap<>();

    private final Plugin plugin;

    // Methods:

    /**
     * Starts the rendering mode for this specific player.
     *
     * @param player The player target.
     */
    public void startRendering(@NotNull final Player player) {
        final UUID uuid = Objects.requireNonNull(player, "A null players has been passed to the start of the rendering!").getUniqueId();

        if (activeRenderTasks.containsKey(uuid)) {
            stopRendering(player);
        }

        final RedstoneRenderingTask task = new RedstoneRenderingTask(plugin, player);
        final BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, task, 1L, 10L);

        activeRenderTasks.put(uuid, Objects.requireNonNull(bukkitTask, "A null bukkit task generated from player " + player.getName()));
        runnableTasks.put(uuid, task);
    }

    /**
     * Disabled the rendering mode for this specific player.
     *
     * @param player The player target.
     */
    public void stopRendering(@NotNull final Player player) {
        final UUID uuid = Objects.requireNonNull(player, "A null players has been passed to the stop of the rendering!").getUniqueId();

        final BukkitTask task = activeRenderTasks.get(uuid);
        final RedstoneRenderingTask runnableTask = runnableTasks.get(uuid);

        if (task == null) {
            return;
        }

        task.cancel();

        if (runnableTask == null) {
            return;
        }

        runnableTask.clearData();
        runnableTasks.clear();
    }

    /**
     * Clears all the data associated to an instance of this class.
     */
    public void clearData() {
        runnableTasks.values().forEach(RedstoneRenderingTask::clearData);
        runnableTasks.clear();

        activeRenderTasks.values().forEach(BukkitTask::cancel);
        activeRenderTasks.clear();
    }

}
