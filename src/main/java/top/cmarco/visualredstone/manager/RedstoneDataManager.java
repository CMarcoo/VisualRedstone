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

package top.cmarco.visualredstone.manager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

/**
 * A simple data holder for players who are using the Visual Redstone mode feature.
 */
public final class RedstoneDataManager {

    // Instance fields:
    private final Collection<UUID> playerToggledRedstone = new HashSet<>();

    // Methods:

    /**
     * Toggles the visual redstone mode of a player.
     * It will enable if he has not enabled it,
     * and will disable it otherwise.
     *
     * @param player The player whose mode shall be toggled.
     * @return true if his mode was enabled, false when it was disabled.
     */
    public boolean togglePlayer(@NotNull final Player player) {
        final UUID uuid = player.getUniqueId();

        if (playerToggledRedstone.contains(uuid)) {
            return !removePlayer(player);
        } else {
            return addPlayer(player);
        }
    }

    /**
     * Clears all the data associated to an instance of this class.
     */
    public void clearData() {
        playerToggledRedstone.clear();
    }

    /**
     * Adds the player to the collection of players
     * who have enabled the visual redstone mode.
     *
     * @param player The player to add.
     * @return true if added, false if no changes occurred.
     */
    private boolean addPlayer(@NotNull final Player player) {
        return playerToggledRedstone.add(Objects.requireNonNull(player, "The player passed unto RedstoneDataManager#addPlayer was null.").getUniqueId());
    }

    /**
     * Removes the player from the collection of players
     * who have enabled the visual redstone mode.
     *
     * @param player The player to remove.
     * @return true if removed, false if no changes occurred.
     */
    private boolean removePlayer(@NotNull final Player player) {
        return playerToggledRedstone.remove(Objects.requireNonNull(player, "The player passed unto RedstoneDataManager#removePlayer was null.").getUniqueId());
    }

}
