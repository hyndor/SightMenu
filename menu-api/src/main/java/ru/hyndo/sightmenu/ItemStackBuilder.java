package ru.hyndo.sightmenu;

import com.google.common.collect.Lists;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import ru.hyndo.sightmenu.util.ColorUtil;

import java.util.*;
import java.util.stream.Collectors;

import static ru.hyndo.sightmenu.util.ColorUtil.color;

public class ItemStackBuilder {

    private final ItemStack itemStack;

    private ItemStackBuilder() {
        itemStack = new ItemStack(Material.STONE);
        setName("");
        setLore(new ArrayList<>());
    }

    private ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static ItemStackBuilder create() {
        return new ItemStackBuilder();
    }

    public static ItemStackBuilder create(ItemStack stack) {
        return new ItemStackBuilder(stack);
    }

    public ItemStackBuilder setItemMeta(ItemMeta meta) {
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setMaterial(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemStackBuilder changeAmount(int change) {
        itemStack.setAmount(itemStack.getAmount() + change);
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder setData(short data) {
        itemStack.setDurability(data);
        return this;
    }

    public ItemStackBuilder setData(MaterialData data) {
        itemStack.setData(data);
        return this;
    }

    public ItemStackBuilder setEnchantments(Map<Enchantment, Integer> enchantments) {
        for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
            itemStack.removeEnchantment(enchantment);
        }
        itemStack.addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemStackBuilder addEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder setName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name.equals("") ? " " : color(name));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder addBlankLore() {
        addLore(" ");
        return this;
    }

    public ItemStackBuilder addLore(String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> original = itemMeta.getLore();
        if (original == null) {
            original = new ArrayList<>();
        }
        Collections.addAll(original, ColorUtil.format(lore));
        itemMeta.setLore(original);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder addLore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> original = itemMeta.getLore();
        if (original == null) {
            original = new ArrayList<>();
        }
        original.addAll(ColorUtil.format(lore));
        itemMeta.setLore(original);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder setLore(String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(ColorUtil.format(Lists.newArrayList(lore)));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(ColorUtil.format(lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }



}