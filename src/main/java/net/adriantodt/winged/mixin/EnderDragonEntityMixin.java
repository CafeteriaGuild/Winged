package net.adriantodt.winged.mixin;

import net.adriantodt.winged.Winged;
import net.adriantodt.winged.WingedUtilityItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(EnderDragonEntity.class)
public abstract class EnderDragonEntityMixin extends MobEntity {

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow public int ticksSinceDeath;

    private UUID taggedPlayer = null;

    protected EnderDragonEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damagePart", at = @At("HEAD"))
    private void winged_tagPlayer(EnderDragonPart part, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Entity attacker = source.getAttacker();
        if (attacker instanceof PlayerEntity && ((PlayerEntity) attacker).getMainHandStack().getItem() == WingedUtilityItems.INSTANCE.getDippedCeremonialKnife()) {
            taggedPlayer = attacker.getUuid();
        }
    }

    @Inject(method = "updatePostDeath", at = @At("TAIL"))
    private void winged_dropHeartOfTheSky(CallbackInfo ci) {
        if (!world.isClient && this.ticksSinceDeath == 200 && Winged.INSTANCE.getConfigHolder().getConfig().getLootTables().getEnderdragonDropsHeartOfTheSky() && taggedPlayer != null) {
            ServerPlayerEntity player = getServer().getPlayerManager().getPlayer(taggedPlayer);
            if (player != null) {
                taggedPlayer = null;
                ItemScatterer.spawn(world, getBlockPos(), DefaultedList.ofSize(1, new ItemStack(WingedUtilityItems.INSTANCE.getHeartOfTheSky75())));
                player.sendMessage(Text.translatable("misc.winged.dragonKill")
                        .formatted(Formatting.DARK_PURPLE, Formatting.ITALIC), false);
            }
        }
    }
}
