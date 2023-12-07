package com.lypaka.betterexchange.PointHandlers;

import com.lypaka.betterexchange.ConfigGetters;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;

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

        } else {

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
