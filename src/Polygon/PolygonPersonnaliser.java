package Polygon;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import sun.awt.geom.Crossings;

import java.util.Arrays;


public class PolygonPersonnaliser extends Polygon implements Shape, java.io.Serializable {

   
	private int npoints;

   
	private double xpoints[];

	private double[] ypoints;

   
    protected Rectangle bounds;

   
    private static final long serialVersionUID = -6460061437900069969L;

    private static final int MIN_LENGTH = 4;

  
    public PolygonPersonnaliser() {
    	super();
        xpoints = new double[MIN_LENGTH];
        ypoints = new double[MIN_LENGTH];
    }

    
    public PolygonPersonnaliser(double xpoints[], double ypoints[], int npoints) {
        super();
        if (npoints > xpoints.length || npoints > ypoints.length) {
            throw new IndexOutOfBoundsException("npoints > xpoints.length || "+
                                                "npoints > ypoints.length");
        }
  
        if (npoints < 0) {
            throw new NegativeArraySizeException("npoints < 0");
        }
      
        this.npoints = npoints;
        super.npoints = npoints ; 
        
        this.xpoints = Arrays.copyOf(xpoints, npoints);
        this.ypoints = Arrays.copyOf(ypoints, npoints);
        
        int[] xtmp = new int [npoints]; 
        int[] ytmp = new int[npoints]; 
        
        for(int i=0; i<this.npoints; i++){
        	   xtmp[i]=(int) xpoints[i];
        	   ytmp[i]=(int) ypoints[i]; 
        	}
        super.xpoints = xtmp ; 
        super.ypoints = ytmp ; 
    }

    
    public void reset() {
        npoints = 0;
        bounds = null;
    }

    public void invalidate() {
        bounds = null;
    }

   
    public void translate(double deltaX, double deltaY) {
        for (int i = 0; i < npoints; i++) {
            xpoints[i] += deltaX;
            ypoints[i] += deltaY;
        }
        if (bounds != null) {
            bounds.translate((int) deltaX,(int )  deltaY);
        }
    }

  
    void calculateBounds(double xpoints[],double ypoints[], double npoints) {
        int boundsMinX = Integer.MAX_VALUE;
        int boundsMinY = Integer.MAX_VALUE;
        int boundsMaxX = Integer.MIN_VALUE;
        int boundsMaxY = Integer.MIN_VALUE;

        for (int i = 0; i < npoints; i++) {
            double x = xpoints[i];
            boundsMinX = (int) Math.min(boundsMinX, x);
            boundsMaxX = (int) Math.max(boundsMaxX, x);
            double y = ypoints[i];
            boundsMinY = (int) Math.min(boundsMinY, y);
            boundsMaxY = (int) Math.max(boundsMaxY, y);
        }
        bounds = new Rectangle(boundsMinX, boundsMinY,
                               boundsMaxX - boundsMinX,
                               boundsMaxY - boundsMinY);
    }

   
    void updateBounds(double x, double y) {
        if (x < bounds.x) {
            bounds.width = (int) (bounds.width + (int)(bounds.x - x));
            bounds.x = (int) x;
        }
        else {
            bounds.width = (int) Math.max(bounds.width, x - bounds.x);
            // bounds.x = bounds.x;
        }

        if (y < bounds.y) {
            bounds.height = (int) (bounds.height + (int)(bounds.y - y));
            bounds.y = (int) y;
        }
        else {
            bounds.height = (int) Math.max(bounds.height, y - bounds.y);
            // bounds.y = bounds.y;
        }
    }

   
    public void addPoint(double x,double y) {
    	super.addPoint((int) x , (int)y );
        if (npoints >= xpoints.length || npoints >= ypoints.length) {
            int newLength = npoints * 2;
            // Make sure that newLength will be greater than MIN_LENGTH and
            // aligned to the power of 2
            if (newLength < MIN_LENGTH) {
                newLength = MIN_LENGTH;
            } else if ((newLength & (newLength - 1)) != 0) {
                newLength = Integer.highestOneBit(newLength);
            }

            xpoints = Arrays.copyOf(xpoints, newLength);
            ypoints = Arrays.copyOf(ypoints, newLength);
        }
        xpoints[npoints] = x;
        ypoints[npoints] = y;
        npoints++;
        if (bounds != null) {
            updateBounds((int) x, (int) y);
        }
    }

    public Rectangle getBounds() {
        return getBoundingBox();
    }
	 public Rectangle getBoundingBox() {
	        if (npoints == 0) {
	            return new Rectangle();
	        }
	        if (bounds == null) {
	            calculateBounds(xpoints, ypoints, npoints);
	        }
	        return bounds.getBounds();
	    }
	public boolean contains(Point p) {
	        return contains(p.x, p.y);
	    }
	  public boolean contains(int x, int y) {
	        return contains((double) x, (double) y);
	    }

   
  
    public boolean inside(int x, int y) {
        return contains((double) x, (double) y);
    }

    public Rectangle2D getBounds2D() {
        return getBounds();
    }

    public boolean contains(double x, double y) {
        if (npoints <= 2 || !getBoundingBox().contains(x, y)) {
            return false;
        }
        int hits = 0;

        double lastx = xpoints[npoints - 1];
        double lasty = ypoints[npoints - 1];
        double curx, cury;

        for (int i = 0; i < npoints; lastx = curx, lasty = cury, i++) {
            curx = xpoints[i];
            cury = ypoints[i];

            if (cury == lasty) {
                continue;
            }

            double leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }

        return ((hits & 1) != 0);
    }

    private Crossings getCrossings(double xlo, double ylo,
                                   double xhi, double yhi)
    {
        Crossings cross = new Crossings.EvenOdd(xlo, ylo, xhi, yhi);
        double lastx = xpoints[npoints - 1];
        double lasty = ypoints[npoints - 1];
        double curx, cury;

        // Walk the edges of the polygon
        for (int i = 0; i < npoints; i++) {
            curx = xpoints[i];
            cury = ypoints[i];
            if (cross.accumulateLine(lastx, lasty, curx, cury)) {
                return null;
            }
            lastx = curx;
            lasty = cury;
        }

        return cross;
    }


    public boolean contains(Point2D p) {
        return contains(p.getX(), p.getY());
    }

   
    public boolean intersects(double x, double y, double w, double h) {
        if (npoints <= 0 || !getBoundingBox().intersects(x, y, w, h)) {
            return false;
        }

        Crossings cross = getCrossings(x, y, x+w, y+h);
        return (cross == null || !cross.isEmpty());
    }

    public boolean intersects(Rectangle2D r) {
        return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
    public boolean contains(double x, double y, double w, double h) {
        if (npoints <= 0 || !getBoundingBox().intersects(x, y, w, h)) {
            return false;
        }

        Crossings cross = getCrossings(x, y, x+w, y+h);
        return (cross != null && cross.covers(y, y+h));
    }

   
    public boolean contains(Rectangle2D r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    public PathIterator getPathIterator(AffineTransform at) {
        return new PolygonPathIterator((Polygon) this, at);
    }

    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return getPathIterator(at);
    }

    class PolygonPathIterator implements PathIterator {
        Polygon poly;
        AffineTransform transform;
        int index;

        public PolygonPathIterator(Polygon pg, AffineTransform at) {
            poly = pg;
            transform = at;
            if (pg.npoints == 0) {
                // Prevent a spurious SEG_CLOSE segment
                index = 1;
            }
        }

      
        public int getWindingRule() {
            return WIND_EVEN_ODD;
        }

        
        public boolean isDone() {
            return index > poly.npoints;
        }

       
        public void next() {
            index++;
        }

       
        public int currentSegment(float[] coords) {
            if (index >= poly.npoints) {
                return SEG_CLOSE;
            }
            coords[0] = poly.xpoints[index];
            coords[1] = poly.ypoints[index];
            if (transform != null) {
                transform.transform(coords, 0, coords, 0, 1);
            }
            return (index == 0 ? SEG_MOVETO : SEG_LINETO);
        }

        
        public int currentSegment(double[] coords) {
            if (index >= poly.npoints) {
                return SEG_CLOSE;
            }
            coords[0] = poly.xpoints[index];
            coords[1] = poly.ypoints[index];
            if (transform != null) {
                transform.transform(coords, 0, coords, 0, 1);
            }
            return (index == 0 ? SEG_MOVETO : SEG_LINETO);
        }
    }

	public void setX(int i, double d) {
		// TODO Auto-generated method stub
		xpoints[i] = d ; 
		super.xpoints[i]=(int) d ;
	}
	public void setY(int i, double d) {
		// TODO Auto-generated method stub
		ypoints[i] = d ; 
		super.ypoints[i]=(int) d ;
	}


	public double getX(int i) {
		return xpoints[i];
	}

	public double getY(int i) {
		return ypoints[i];
	}


	public int getNpoint() {
		// TODO Auto-generated method stub
		return npoints;
	}


	public double[] getXPoint() {
		// TODO Auto-generated method stub
		return xpoints;
	}
	public double[] getYPoint() {
		// TODO Auto-generated method stub
		return ypoints;
	}
}
