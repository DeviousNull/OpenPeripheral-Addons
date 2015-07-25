package openperipheral.addons.glasses.drawable;

import openmods.structured.StructureField;
import openperipheral.addons.glasses.utils.IPointListBuilder;
import openperipheral.addons.glasses.utils.RenderState;
import openperipheral.api.adapter.Property;

import org.lwjgl.opengl.GL11;

public abstract class Triangle<P> extends BoundedShape<P> {

	@Property
	@StructureField
	public P p1;

	@Property
	@StructureField
	public P p2;

	@Property
	@StructureField
	public P p3;

	public Triangle(P p1, P p2, P p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;

		updateBoundingBox();
	}

	@Override
	protected void drawContents(RenderState renderState, float partialTicks) {
		if (pointList != null) {
			super.drawContents(renderState, partialTicks);

			GL11.glBegin(GL11.GL_TRIANGLES);
			pointList.drawPoint(renderState, 0);
			pointList.drawPoint(renderState, 1);
			pointList.drawPoint(renderState, 2);
			GL11.glEnd();
		}
	}

	@Override
	protected boolean isVisible() {
		return true;
	}

	@Override
	protected void addPoints(IPointListBuilder<P> points) {
		points.add(p1);
		points.add(p2);
		points.add(p3);
	}

}
