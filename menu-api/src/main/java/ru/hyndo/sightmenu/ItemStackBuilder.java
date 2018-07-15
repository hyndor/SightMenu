package ru.hyndo.sightmenu;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import ru.hyndo.sightmenu.util.ColorUtil;

import java.util.*;
import java.util.function.Function;

import static ru.hyndo.sightmenu.util.ColorUtil.color;

public class ItemStackBuilder {

    private Material material;
    private int amount = 1;
    private short data;
    private MaterialData materialData;
    private ItemStackItemMetaBuilder metaBuilder;
    private Function<ItemStack, ItemMeta> itemMetaCreator = is -> {
        Preconditions.checkNotNull(is, "Null itemStack");
        if(metaBuilder != null) {
            return metaBuilder.build(is);
        }
        return is.getItemMeta();
    };
    private Map<Enchantment, Integer> enchantments = new HashMap<>();

    private ItemStack userItem;

    private ItemStackBuilder() {

    }

    private ItemStackBuilder(ItemStack itemStack) {
        Preconditions.checkNotNull(itemStack, "Null ItemStack");
        this.material = itemStack.getType();
        this.amount = itemStack.getAmount();
        this.data = itemStack.getDurability();
        if(itemStack.getData() != null) {
            this.materialData = itemStack.getData();
        }
        if(itemStack.getEnchantments() != null) {
            this.enchantments = itemStack.getEnchantments();
        }
        this.userItem = itemStack;
    }

    public static ItemStackBuilder create() {
        return new ItemStackBuilder();
    }

    public static ItemStackBuilder create(ItemStack stack) {
        return new ItemStackBuilder(stack);
    }

    public ItemStackBuilder setMaterial(Material material) {
        Preconditions.checkNotNull(material, "Null material");
        this.material = material;
        return this;
    }

    public ItemStackBuilder changeAmount(int change) {
        this.amount = change;
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStackBuilder setData(short data) {
        this.data = data;
        return this;
    }

    public ItemStackBuilder setData(MaterialData data) {
        Preconditions.checkNotNull(data, "Null Material Data");
        this.materialData = data;
        return this;
    }

    public ItemStackBuilder setEnchantments(Map<Enchantment, Integer> enchantments) {
        Preconditions.checkNotNull(enchantments, "Null enchantments");
        enchantments.forEach((ench, l) -> Preconditions.checkNotNull(ench, "Null enchantment in enchantment's collection"));
        this.enchantments = new HashMap<>(enchantments);
        return this;
    }

    public ItemStackBuilder addEnchantment(Enchantment enchantment, int level) {
        Preconditions.checkNotNull(enchantment, "Null enchantment");
        enchantments.put(enchantment, level);
        return this;
    }

    public ItemStackItemMetaBuilder withItemMeta() {
        ItemStackItemMetaBuilder metaBuilder = new ItemStackItemMetaBuilder(this);
        this.metaBuilder = metaBuilder;
        if (userItem != null) {
            metaBuilder.setItemMeta(userItem.getItemMeta());
        }
        return metaBuilder;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount, data);
        if(materialData != null) {
            itemStack.setData(materialData);
        }
        itemStack.addUnsafeEnchantments(enchantments);
        ItemMeta createdMeta = itemMetaCreator.apply(itemStack);
        boolean b = itemStack.setItemMeta(createdMeta);
        return itemStack;
    }

    public static class ItemStackItemMetaBuilder {

        private ItemStackBuilder innerBuilder;
        private String name;
        private ItemMeta userMeta;
        private List<String> lore = new ArrayList<>();
        private List<ItemFlag> itemFlags = new ArrayList<>();
        private boolean unbreakable = false;

        private ItemStackItemMetaBuilder(ItemStackBuilder innerBuilder) {
            this.innerBuilder = innerBuilder;
        }

        public ItemStackItemMetaBuilder setItemMeta(ItemMeta meta) {
            this.userMeta = meta;
            return this;
        }

        public ItemStackItemMetaBuilder setName(String name) {
            Preconditions.checkNotNull(name, "Null name");
            this.name = color(name);
            return this;
        }

        public ItemStackItemMetaBuilder addBlankLore() {
            addLore(" ");
            return this;
        }

        public ItemStackItemMetaBuilder addLore(String... lore) {
            Preconditions.checkNotNull(lore, "Null lore");
            addLore(Arrays.asList(lore));
            return this;
        }

        public ItemStackItemMetaBuilder addLore(List<String> lore) {
            Preconditions.checkNotNull(lore, "Null lore");
            this.lore.addAll(ColorUtil.format(lore));
            return this;
        }

        public ItemStackItemMetaBuilder setLore(String... lore) {
            Preconditions.checkNotNull(lore, "Null lore");
            this.lore = Lists.newArrayList(ColorUtil.format(lore));
            return this;
        }

        public ItemStackItemMetaBuilder setLore(List<String> lore) {
            Preconditions.checkNotNull(lore, "Null lore");
            this.lore = Lists.newArrayList(ColorUtil.format(lore));
            return this;
        }

        public ItemStackItemMetaBuilder setItemFlags(List<ItemFlag> itemFlags) {
            Preconditions.checkNotNull(itemFlags, "itemFlags is null");
            this.itemFlags = itemFlags;
            return this;
        }

        public ItemStackBuilder and() {
            return innerBuilder;
        }

        public ItemStackItemMetaBuilder setUnbreakable(boolean flag) {
            this.unbreakable = flag;
            return this;
        }

        //Use deprecated method to support 1.8 versions
        @SuppressWarnings("deprecation")
        private ItemMeta build(ItemStack itemStack) {
            ItemMeta metaToUse = userMeta != null ? userMeta : itemStack.getItemMeta();
            metaToUse.setLore(lore);
            if(name != null) {
                metaToUse.setDisplayName(name);
            }
            if(unbreakable) {
                metaToUse.spigot().setUnbreakable(unbreakable);
            }
            if(itemFlags.size() > 0) {
                metaToUse.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
            }
            return metaToUse;
        }
    }




}
