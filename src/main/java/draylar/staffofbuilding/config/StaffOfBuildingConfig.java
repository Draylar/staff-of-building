package draylar.staffofbuilding.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "staff-of-building")
public class StaffOfBuildingConfig implements ConfigData {
    public int woodenSize = 3 * 3;
    public int stoneSize = 5 * 5;
    public int ironSize = 7 * 7;
    public int goldenSize = 9 * 9;
    public int diamondSize = 11 * 11;
    public int netheriteSize = 13 * 13;
    public int infiniteSize = 25 * 25;
}
