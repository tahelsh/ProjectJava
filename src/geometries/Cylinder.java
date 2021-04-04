package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
/**
 * class of the geometry of Cylinder
 * @author Tahel Sharon & Ayala Israeli
 *
 */
public class Cylinder extends Tube {
	/**
	 * height value
	 */
	private double height;

	/**
	 * constructor of cylinder, gets ray, radius and heighth
	 * @param axisRay
	 * @param radius
	 * @param height
	 */
	public Cylinder(Ray axisRay, double radius, double height) {
		super(axisRay, radius);
		this.height = height;
	}

	/**
	 * 
	 * @return height
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "Cylinder [height=" + height + "]";
	}
	
	@Override
	public Vector getNormal(Point3D point) {
		if(point.equals(axisRay.getP0()))//������ ����� ���� �� ������� ������ ��
			return axisRay.getDir().scale(-1).normalize();
		//Point3D centerSecondBase=axisRay.getP0().add(axisRay.getDir().scale(height));
		Point3D centerSecondBase=axisRay.getPoint(height);
		if(point.equals(centerSecondBase))//������ ����� ���� �� ������� ������ ��
			return axisRay.getDir().normalize();
		if(point.subtract(axisRay.getP0()).length()<radius && isZero(point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir())))//���� �����
			return axisRay.getDir().scale(-1).normalize();
		Vector v=point.subtract(centerSecondBase);//������ ����� ���� ���� ������� ������ ��� ������
		if(v.length()<radius && isZero(v.dotProduct(axisRay.getDir())))//���� �����
			return axisRay.getDir().normalize();
		return super.getNormal(point);
		
	}

}
