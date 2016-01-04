package openperipheral.addons.sensors;

import javax.vecmath.Matrix4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import openperipheral.addons.Config;
import openperipheral.addons.OpenPeripheralAddons.Blocks;
import openperipheral.addons.utils.CCUtils;
import openperipheral.api.ApiAccess;
import openperipheral.api.architecture.cc.IComputerCraftObjectsFactory;

import org.apache.commons.lang3.tuple.Pair;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;

public class TurtleUpgradeSensor implements ITurtleUpgrade {

	private static class TurtleSensorEnvironment implements ISensorEnvironment {

		private ITurtleAccess turtle;

		public TurtleSensorEnvironment(ITurtleAccess turtle) {
			this.turtle = turtle;
		}

		@Override
		public boolean isTurtle() {
			return true;
		}

		@Override
		public Vec3 getLocation() {
			return turtle.getVisualPosition(0);
		}

		@Override
		public World getWorld() {
			return turtle.getWorld();
		}

		@Override
		public int getSensorRange() {
			final World world = getWorld();
			return (world.isRaining() && world.isThundering())? Config.sensorRangeInStorm : Config.sensorRange;
		}

		@Override
		public boolean isValid() {
			return CCUtils.isTurtleValid(turtle);
		}

	}

	@Override
	public int getLegacyUpgradeID() {
		return 180;
	}

	@Override
	public ResourceLocation getUpgradeID() {
		return new ResourceLocation("openperipheral", "sensor");
	}

	@Override
	public String getUnlocalisedAdjective() {
		return "openperipheral.turtle.sensor.adjective";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(Blocks.sensor);
	}

	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return ApiAccess.getApi(IComputerCraftObjectsFactory.class).createPeripheral(new TurtleSensorEnvironment(turtle));
	}

	@Override
	public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, EnumFacing direction) {
		return null;
	}

	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {}

	@Override
	@SideOnly(Side.CLIENT)
	public Pair<IBakedModel, Matrix4f> getModel(ITurtleAccess turtle, TurtleSide side) {
		final Minecraft mc = Minecraft.getMinecraft();
		final ModelManager modelManager = mc.getRenderItem().getItemModelMesher().getModelManager();

		// TODO: actual models
		ModelResourceLocation location = new ModelResourceLocation(side == TurtleSide.Left? "computercraft:turtle_crafting_table_left" : "computercraft:turtle_crafting_table_right", "inventory");
		return Pair.of(modelManager.getModel(location), null);
	}

}
