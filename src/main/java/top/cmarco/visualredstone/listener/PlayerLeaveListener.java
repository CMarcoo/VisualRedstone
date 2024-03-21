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

package top.cmarco.visualredstone.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import top.cmarco.visualredstone.task.RedstoneRenderingService;

import java.util.Objects;

public final class PlayerLeaveListener implements Listener {

    private final RedstoneRenderingService redstoneRenderingService;

    public PlayerLeaveListener(@NotNull final RedstoneRenderingService redstoneRenderingService) {
        this.redstoneRenderingService = Objects.requireNonNull(redstoneRenderingService);
    }

    @EventHandler
    public void onQuit(@NotNull final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        redstoneRenderingService.stopRendering(player);
    }
}
