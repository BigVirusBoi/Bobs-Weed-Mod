package me.bigvirusboi.weedmod.item;

import me.bigvirusboi.weedmod.init.SoundInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Random;

public class JointItem extends Item {
    private final Random rand = new Random();
    private final boolean isFrank;

    public JointItem(boolean isFrank, Item.Properties properties) {
        super(properties);
        this.isFrank = isFrank;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;

            int i = this.getUseDuration(stack) - timeLeft;
            if (i < 0 || !(stack.getItem() instanceof JointItem)) return;

            if (!stack.isEmpty()) {
                double x = player.getPosX();
                double y = player.getPosY();
                double z = player.getPosZ();

                worldIn.playSound(null, x, y, z, SoundInit.EXHALE.get(), SoundCategory.PLAYERS, 0.5F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) * 0.5F);
                particle(player, worldIn);
                if (!player.abilities.isCreativeMode) {
                    if (isFrank) {
                        player.addPotionEffect(new EffectInstance(Effects.WITHER, 1000, 100, false, true));
                    }
                    stack.shrink(1);
                    if (stack.isEmpty()) {
                        player.inventory.deleteStack(stack);
                    }
                }

                player.addStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
    }

    public void particle(PlayerEntity player, World worldIn) {
        float f = -MathHelper.sin(player.rotationYaw * ((float) Math.PI / 180F)) * MathHelper.cos(player.rotationPitch * ((float) Math.PI / 180F));
        float f1 = -MathHelper.sin(player.rotationPitch * ((float) Math.PI / 180F));
        float f2 = MathHelper.cos(player.rotationYaw * ((float) Math.PI / 180F)) * MathHelper.cos(player.rotationPitch * ((float) Math.PI / 180F));

        Vector3d vector3d = (new Vector3d(f, f1, f2)).normalize().add(this.rand.nextGaussian() * (double) 0.0075F * (double) 1.0F, this.rand.nextGaussian() * (double) 0.0075F * (double) 1.0F, this.rand.nextGaussian() * (double) 0.0075F * (double) 1.0F).scale(0.2F);

        worldIn.addParticle(ParticleTypes.CLOUD, player.getPosX(), player.getPosY() + 1, player.getPosZ(), f, f1, f2);
    }
}
