package net.tejty.gamediscs.util.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;


public class ItemTagModifier extends LootModifier {
    public static final MapCodec<ItemTagModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            codecStart(inst).and(TagKey.codec(Registries.ITEM)
                    .fieldOf("tag").forGetter(m -> m.tag)).apply(inst, ItemTagModifier::new));

    private final TagKey<Item> tag;

    public ItemTagModifier(LootItemCondition[] conditionsIn, TagKey<Item> itemTag) {
        super(conditionsIn);
        this.tag = itemTag;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (LootItemCondition condition : this.conditions) {
            if (!condition.test(context)) {
                return generatedLoot;
            }
        }
        LootTable lootTable = LootTable.lootTable().withPool(LootPool.lootPool().add(TagEntry.expandTag(tag))).build();
        lootTable.getRandomItems(context, generatedLoot::add);
        return generatedLoot;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
