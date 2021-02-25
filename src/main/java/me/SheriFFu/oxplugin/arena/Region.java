package me.SheriFFu.oxplugin.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class Region {
    public Location corner1;
    public Location corner2;

    public Region() {

    }

    public Region(String fileID, FileConfiguration configuration) {
        World world = Bukkit.getWorld((String) configuration.get("arena.world"));
        double x = configuration.getDouble("arena." + fileID + ".corner1.x");
        double y = configuration.getDouble("arena." + fileID + ".corner1.y");
        double z = configuration.getDouble("arena." + fileID + ".corner1.z");

        corner1 = new Location(world, x, y, z);

        x = configuration.getDouble("arena." + fileID + ".corner2.x");
        y = configuration.getDouble("arena." + fileID + ".corner2.y");
        z = configuration.getDouble("arena." + fileID + ".corner2.z");

        corner2 = new Location(world, x, y, z);
    }

    public boolean isLocationInRegion(Location location) {
        boolean x = corner1.getX() < corner2.getX() ? location.getX() >= corner1.getX() && location.getX() <= corner2.getX():
                location.getX() >= corner2.getX() && location.getX() <= corner1.getX();
        boolean y = corner1.getY() < corner2.getY() ? location.getY() >= corner1.getY() && location.getY() <= corner2.getY():
                location.getY() >= corner2.getY() && location.getY() <= corner1.getY();
        boolean z = corner1.getZ() < corner2.getZ() ? location.getZ() >= corner1.getZ() && location.getZ() <= corner2.getZ():
                location.getZ() >= corner2.getZ() && location.getZ() <= corner1.getZ();

        return x && y && z;
    }

    public Location getRegionCenter() {
        double x = (corner1.getX() + corner2.getX()) / 2;
        double y = (corner1.getY() + corner2.getY()) / 2;
        double z = corner1.getZ() + 1;

        return new Location(corner1.getWorld(), x, y, z);
    }

    public void setCorner1(Location location) {
        corner1 = location;
    }

    public void setCorner2(Location location) {
        corner2 = location;
    }

}
