package primitives;

public class Vector {
	
	private Point3D head;
	
/**
 * c-tor from Point3D
 * @param head Point3D
 */
	public Vector(Point3D head) {
		super();
		this.head = head;
	}
/**
 * c-tor from 3 coordinate	
 * @param x coord x
 * @param y coord y
 * @param z coord z
 * @throws IllegalArgumentException
 */
	public Vector(Coordinate x, Coordinate y, Coordinate z) throws IllegalArgumentException {
		super();
		Point3D point=new Point3D(x,y,z);
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Error, cannot create vector zero");
		this.head = point;
	}
/**
 * c-tor that gets 3 double value
 * @param x x value
 * @param y y value
 * @param z z value
 * @throws IllegalArgumentException
 */
	public Vector(double x, double y, double z) throws IllegalArgumentException {
		super();
		Point3D point=new Point3D(new Coordinate(x),new Coordinate(y),new Coordinate(z));
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Error, cannot create vector zero");
		this.head = point;
	}
	
	/**
	 * get
	 * @return the head point
	 */
	public Point3D getHead() {
		return head;
	}
/**
 * adding two vectors
 * @param otherVector other vector
 * @return new vector after adding
 * @throws IllegalArgumentException
 */
	public Vector add(Vector otherVector)throws IllegalArgumentException
	{
		return new Vector(head.add(otherVector));
	}
/**
 * subtract between two vectors	
 * @param otherVector other vector
 * @return new vector after subtract
 * @throws IllegalArgumentException
 */
	public Vector subtract(Vector otherVector) throws IllegalArgumentException
	{
		return head.subtract(otherVector.getHead());
	}
/**
 *  scale product
 * @param scaleNum a scale number
 * @return new vector after scale product
 * @throws IllegalArgumentException
 */
	public Vector scale(double scaleNum) throws IllegalArgumentException
    {
    	return new Vector(scaleNum*head.x.coord, scaleNum*head.y.coord, scaleNum*head.z.coord);
    }
/**
 * dot product  
 * @param otherVec other vector
 * @return new vector after dot product
 */
	public double dotProduct(Vector otherVec)
    {
    	return head.x.coord*otherVec.getHead().x.coord+head.y.coord*otherVec.getHead().y.coord+ head.z.coord*otherVec.getHead().z.coord;   
    }
/**
 * cross product	
 * @param otherVec other vector
 * @return new vector after cross product
 * @throws IllegalArgumentException
 */
	public Vector crossProduct(Vector otherVec) throws IllegalArgumentException
	{
		return new Vector(head.y.coord*otherVec.head.z.coord - head.z.coord*otherVec.head.y.coord,
				otherVec.head.x.coord*head.z.coord - head.x.coord*otherVec.head.z.coord,
				head.x.coord*otherVec.head.y.coord - head.y.coord*otherVec.head.x.coord);
	}
	public double lengthSquared()
	{
		return this.dotProduct(this);
	}
	public double length()
	{
		return Math.sqrt(this.lengthSquared());
	}
	public Vector normalize()
	{
		double length=this.length();
		head=new Point3D(head.x.coord/length, head.y.coord/length, head.z.coord/length);
		return this;
	}
	public Vector normalized() throws IllegalArgumentException
	{
		double length=this.length();
		return new Vector(head.x.coord/length, head.y.coord/length, head.z.coord/length);
	}
	@Override
	public String toString() {
		return head.toString();
	}

	

}
