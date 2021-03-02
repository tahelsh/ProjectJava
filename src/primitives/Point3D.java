package primitives;
/**
 *Point3D is class for point in 3D 
 * @author Tahel and Ayala
 *
 */
public class Point3D {
	
	Coordinate x;//x
	Coordinate y;//y
	Coordinate z;//z
	public static Point3D ZERO=new Point3D(0,0,0);//zero static

/**
 * Point3D constructor receiving a 3 coordinate value of x,y,z values
 * @param x x value
 * @param y y value
 * @param z z value
 */
	public Point3D(Coordinate x, Coordinate y, Coordinate z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
/**
 * Point3D constructor receiving a 3 double value of x,y,z values
 * @param x x value
 * @param y y value
 * @param z z value
 */
	public Point3D(double x, double y, double z) {
		super();
		this.x = new Coordinate(x);
		this.y = new Coordinate(y);
		this.z = new Coordinate(z);
	}
/**
 * subtract between two points and return vector
 * @param otherPoint the second point
 * @return new vector after subtract
 * @throws IllegalArgumentException
 */
public Vector subtract(Point3D otherPoint) throws IllegalArgumentException
{
	double newX=otherPoint.x.coord -x.coord;
	double newY=otherPoint.x.coord -y.coord;
	double newZ=otherPoint.x.coord -z.coord;
	return new Vector(newX, newY, newZ);
}

/**
 * add between vector and point and return point
 * @param otherPoint the second point
 * @return
 */
public Point3D add(Vector otherVector)
{
	double newX=otherVector.getHead().x.coord+x.coord;
	double newY=otherVector.getHead().y.coord+y.coord;
	double newZ=otherVector.getHead().z.coord+z.coord;
	return new Point3D(newX, newY, newZ);
}

/**
 * distance squared
 * @param otherPoint other point
 * @return the distance squared
 */
public double distanceSquared(Point3D otherPoint)
{
	double subX=otherPoint.x.coord-x.coord;//x1-x2
	double subY=otherPoint.y.coord-y.coord;//y1-y2
	double subZ=otherPoint.z.coord-z.coord;//z1-z2
	return subX*subX+subY*subY+subZ*subZ;	
}

/**
 * distance between two points
 * @param otherPoint other point
 * @return the distance between them
 */
public double distance(Point3D otherPoint)
{
	return Math.sqrt(distanceSquared(otherPoint));	
}

@Override
public boolean equals(Object obj)
{
	 if (this == obj) return true;
     if (obj == null) return false;
     if (!(obj instanceof Point3D)) return false;
     Point3D other=(Point3D)obj;
     return x.equals(other.x)&&y.equals(other.y)&&z.equals(other.z);     
}

@Override
public String toString() {
	return "("+x+","+y+","+z+")";
}


}
