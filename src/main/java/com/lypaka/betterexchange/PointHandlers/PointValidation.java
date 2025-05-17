package com.lypaka.betterexchange.PointHandlers;

import com.lypaka.betterexchange.ConfigGetters;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;

import java.util.Map;

public class PointValidation {

    public static int getPointWorth (Pokemon pokemon) {

        int points = 0;
        if (ConfigGetters.exchangeSellMap.containsKey(pokemon.getSpecies().getName())) {

            Map<String, String> priceMap = ConfigGetters.exchangeSellMap.get(pokemon.getSpecies().getName());
            for (Map.Entry<String, String> entry : priceMap.entrySet()) {

                String key = entry.getKey();
                String value = entry.getValue();

                if (key.equalsIgnoreCase("default")) {

                    if (!value.equalsIgnoreCase("ivs")) {

                        points = Integer.parseInt(value);

                    } else {

                        points = getPointsByIVs(pokemon);

                    }
                    break;

                }

                if (key.contains("form-")) {

                    String form = key.replace("form-", "");
                    if (pokemon.getForm().getLocalizedName().equalsIgnoreCase(form)) {

                        if (!value.equalsIgnoreCase("ivs")) {

                            points = Integer.parseInt(value);

                        } else {

                            points = getPointsByIVs(pokemon);

                        }
                        break;

                    }

                } else if (key.equalsIgnoreCase("shiny")) {

                    if (pokemon.isShiny()) {

                        if (!value.equalsIgnoreCase("ivs")) {

                            points = Integer.parseInt(value);

                        } else {

                            points = getPointsByIVs(pokemon);

                        }
                        break;

                    }


                } else if (key.contains("specialTexture:") || key.contains("palette:")) {

                    if (pokemon.getPalette().getName().equalsIgnoreCase(key.replace("specialTexture:", "").replace("palette:", ""))) {

                        if (!value.equalsIgnoreCase("ivs")) {

                            points = Integer.parseInt(value);

                        } else {

                            points = getPointsByIVs(pokemon);

                        }
                        break;

                    }

                }

            }

        } else if (ConfigGetters.exchangeSellMap.containsKey("Legendary") && PixelmonSpecies.isLegendary(pokemon.getSpecies())) {

            points = Integer.parseInt(ConfigGetters.exchangeSellMap.get("Legendary").get("default"));
            if (pokemon.isShiny()) {

                if (ConfigGetters.exchangeSellMap.containsKey("ShinyLegendary")) {

                    points = Integer.parseInt(ConfigGetters.exchangeSellMap.get("ShinyLegendary").get("default"));

                }

            }

        } else if (ConfigGetters.exchangeSellMap.containsKey("Mythical") && PixelmonSpecies.isMythical(pokemon.getSpecies())) {

            points = Integer.parseInt(ConfigGetters.exchangeSellMap.get("Mythical").get("default"));
            if (pokemon.isShiny()) {

                if (ConfigGetters.exchangeSellMap.containsKey("ShinyMythical")) {

                    points = Integer.parseInt(ConfigGetters.exchangeSellMap.get("ShinyMythical").get("default"));

                }

            }

        } else if (ConfigGetters.exchangeSellMap.containsKey("UltraBeast") && PixelmonSpecies.isUltraBeast(pokemon.getSpecies())) {

            points = Integer.parseInt(ConfigGetters.exchangeSellMap.get("UltraBeast").get("default"));
            if (pokemon.isShiny()) {

                if (ConfigGetters.exchangeSellMap.containsKey("ShinyUltraBeast")) {

                    points = Integer.parseInt(ConfigGetters.exchangeSellMap.get("ShinyUltraBeast").get("default"));

                }

            }

        }  else {

            points = getPointsByIVs(pokemon);

        }

        return points;

    }

    private static int getPointsByIVs (Pokemon pokemon) {

        int hp = pokemon.getIVs().getStat(BattleStatsType.HP);
        int atk = pokemon.getIVs().getStat(BattleStatsType.ATTACK);
        int def = pokemon.getIVs().getStat(BattleStatsType.DEFENSE);
        int sAtk = pokemon.getIVs().getStat(BattleStatsType.SPECIAL_ATTACK);
        int sDef = pokemon.getIVs().getStat(BattleStatsType.SPECIAL_DEFENSE);
        int spd = pokemon.getIVs().getStat(BattleStatsType.SPEED);

        int total = hp + atk + def + sAtk + sDef + spd;

        double percent = ((float) total / 186) * 100;
        return (int) percent;

    }

}
