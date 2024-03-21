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

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ChatUtils {

    private static Map<Integer, String> redstonePowerHexColourMap = new HashMap<>(0x10);
    private static Map<Integer, Component> cachedRedstoneTextComponents = new HashMap<>(0x10);

    static {
        redstonePowerHexColourMap.put(0x0, "#FF0000");
        redstonePowerHexColourMap.put(0x1, "#FF1A00");
        redstonePowerHexColourMap.put(0x2, "#FF3400");
        redstonePowerHexColourMap.put(0x3, "#FF4E00");
        redstonePowerHexColourMap.put(0x4, "#FF6800");
        redstonePowerHexColourMap.put(0x5, "#FF8100");
        redstonePowerHexColourMap.put(0x6, "#FF9B00");
        redstonePowerHexColourMap.put(0x7, "#FFB500");
        redstonePowerHexColourMap.put(0x8, "#FFCF00");
        redstonePowerHexColourMap.put(0x9, "#FFE900");
        redstonePowerHexColourMap.put(0xA, "#FFFF00");
        redstonePowerHexColourMap.put(0xB, "#E9FF00");
        redstonePowerHexColourMap.put(0xC, "#CFFF00");
        redstonePowerHexColourMap.put(0xD, "#B5FF00");
        redstonePowerHexColourMap.put(0xE, "#9BFF00");
        redstonePowerHexColourMap.put(0xF, "#81FF00");

        redstonePowerHexColourMap.forEach((k,v) -> cachedRedstoneTextComponents.put(k, PlainTextComponentSerializer
                .builder()
                .build()
                .deserialize(Integer.toString(k))
                .color(TextColor.fromHexString(redstonePowerHexColourMap.get(k)))));
    }

    /**
     * Get the coloured component for a redstone power level.
     *
     * @param redstonePower The power level of a redstone, from 0 to 15.
     * @return The coloured component.
     */
    @NotNull
    public static Component getRedstoneHoverComponent(@Range(from=0, to=15) final int redstonePower) {
        Preconditions.checkArgument(0 <= redstonePower && redstonePower <= 15, "Illegal Redstone Power!");
        return cachedRedstoneTextComponents.get(redstonePower);
    }

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
