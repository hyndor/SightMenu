package ru.hyndo.sightmenu.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class SimpleItemMeta implements ItemMeta {

    private String name;
    private List<String> lore = new ArrayList<>();
    private List<ItemFlag> itemFlags = new ArrayList<>();
    private boolean unbreakable = false;

    private static int countInstance = 0;

    public SimpleItemMeta() {
    }

    @Override
    public boolean hasDisplayName() {
        return false;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public void setDisplayName(String name) {
        System.out.println("Setting display name " + name);
        this.name = name;
    }

    @Override
    public boolean hasLocalizedName() {
        return false;
    }

    @Override
    public String getLocalizedName() {
        return null;
    }

    @Override
    public void setLocalizedName(String name) {

    }

    @Override
    public boolean hasLore() {
        return false;
    }

    @Override
    public List<String> getLore() {
        return lore;
    }

    @Override
    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    @Override
    public boolean hasEnchants() {
        return false;
    }

    @Override
    public boolean hasEnchant(Enchantment ench) {
        return false;
    }

    @Override
    public int getEnchantLevel(Enchantment ench) {
        return 0;
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        return null;
    }

    @Override
    public boolean addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        return false;
    }

    @Override
    public boolean removeEnchant(Enchantment ench) {
        return false;
    }

    @Override
    public boolean hasConflictingEnchant(Enchantment ench) {
        return false;
    }

    @Override
    public void addItemFlags(ItemFlag... itemFlags) {
        this.itemFlags.addAll(Arrays.asList(itemFlags));
    }

    @Override
    public void removeItemFlags(ItemFlag... itemFlags) {

    }

    @Override
    public Set<ItemFlag> getItemFlags() {
        return new HashSet<>(itemFlags);
    }

    @Override
    public boolean hasItemFlag(ItemFlag flag) {
        return false;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @Override
    public void setUnbreakable(boolean unbreakable) {

    }

    @Override
    public ItemMeta clone() {
        return null;
    }

    @Override
    public Spigot spigot() {
        return new Spigot() {
            @Override
            public void setUnbreakable(boolean unbreakable) {
                SimpleItemMeta.this.unbreakable = unbreakable;
            }

            @Override
            public boolean isUnbreakable() {
                return SimpleItemMeta.this.unbreakable;
            }
        };
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }
}
