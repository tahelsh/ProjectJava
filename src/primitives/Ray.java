package primitives;
import java.util.Objects;

public class Ray {

	Point3D p0;
	Vector dir;
	
	public Ray(Point3D p0, Vector dir) throws IllegalArgumentException {
		super();
		this.p0 = p0;
		this.dir = dir.normalize();
	}

	@Override
	public int hashCode() {
		return Objects.hash(p0, dir);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
	    if (obj == null) return false;
		if (!(obj instanceof Ray)) {
			return false;
		}
		Ray other = (Ray) obj;
		return p0.equals(other.p0) && dir.equals(other.dir);	
	}
	
	@Override
	public String toString() {
		return "Ray [" + (p0 != null ? "p0=" + p0 + ", " : "")
				+ (dir != null ? "dir=" + dir : "") + "]";
	}
/**
 * 
 * @return p0
 */
	public Point3D getP0() {
		return p0;
	}

/**
 * 
 * @return dir
 */
	public Vector getDir() {
		return dir;
	}



}
