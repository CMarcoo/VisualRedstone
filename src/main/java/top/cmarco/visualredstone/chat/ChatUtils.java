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

package top.cmarco.visualredstone.chat;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class ChatUtils {

    private ChatUtils() {
        throw new RuntimeException(getClass().getName() + " constructor cannot be used!");
    }

    @NotNull
    public static TextComponent colour(@NotNull final String text) {
        return LegacyComponentSerializer
                .legacyAmpersand()
                .deserialize(Objects.requireNonNull(text, "Provided null string for ChatUtils#colour."));
    }
}
