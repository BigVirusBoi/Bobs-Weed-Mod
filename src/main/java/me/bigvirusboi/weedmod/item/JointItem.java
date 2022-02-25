package me.bigvirusboi.weedmod.item;

import me.bigvirusboi.weedmod.init.SoundInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.Random;

public class JointItem extends Item {
    private final Random rand = new Random();
    private final boolean isFrank;

    public JointItem(boolean isFrank, Item.Properties properties) {
        super(properties);
        this.isFrank = isFrank;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            int i = this.getUseDuration(stack) - timeLeft;
            if (i < 0 || !(stack.getItem() instanceof JointItem)) return;

            if (!stack.isEmpty()) {
                double x = player.getX();
                double y = player.getY();
                double z = player.getZ();

                level.playSound(null, x, y, z, SoundInit.EXHALE.get(), SoundSource.PLAYERS, 0.5F, 1.0F / (rand.nextFloat() * 0.4F + 1.2F) * 0.5F);
                particle(player, level);
                if (!player.isCreative()) {
                    if (isFrank) {
                        player.addEffect(new MobEffectInstance(MobEffects.WITHER, 1000, 100, false, true));
                    }
                    stack.shrink(1);
                    if (stack.isEmpty()) {
                        player.getInventory().removeItem(stack);
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    public void particle(Player player, Level level) {
        float f = -Mth.sin(player.getYRot() * ((float) Math.PI / 180F)) * Mth.cos(player.getXRot() * ((float) Math.PI / 180F));
        float f1 = -Mth.sin(player.getXRot() * ((float) Math.PI / 180F));
        float f2 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F)) * Mth.cos(player.getXRot() * ((float) Math.PI / 180F));

        //Vector3d vec = (new Vector3d(f, f1, f2)).normalized().add(this.rand.nextGaussian() * (double) 0.0075F * (double) 1.0F, this.rand.nextGaussian() * (double) 0.0075F * (double) 1.0F, this.rand.nextGaussian() * (double) 0.0075F * (double) 1.0F).scale(0.2F);

        level.addParticle(ParticleTypes.CLOUD, player.getX(), player.getY() + 1, player.getZ(), f, f1, f2);
    }
}
