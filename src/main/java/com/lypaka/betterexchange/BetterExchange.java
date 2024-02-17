package com.lypaka.betterexchange;

import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import net.minecraftforge.fml.common.Mod;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("betterexchange")
public class BetterExchange {

    public static final String MOD_ID = "betterexchange";
    public static final String MOD_NAME = "BetterExchange";
    public static Logger logger = LogManager.getLogger("BetterExchange");
    public static BasicConfigManager configManager;

    public BetterExchange() throws ObjectMappingException, IOException {

        Path dir = ConfigUtils.checkDir(Paths.get("./config/betterexchange"));
        String[] files = new String[]{"betterexchange.conf", "points.conf", "blacklist.conf", "mainGUI.conf", "partyGUI.conf"};
        configManager = new BasicConfigManager(files, dir, BetterExchange.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        ConfigGetters.load(false);

    }

}
