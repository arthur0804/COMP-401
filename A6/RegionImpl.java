package a6;

public class RegionImpl implements Region {
	private int left;
	private int right;
	private int top;
	private int bottom;
	
	
	// constructor
	public RegionImpl(int left, int top, int right, int bottom) {
		if(left > right || top > bottom) {
			throw new IllegalArgumentException();
		}
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	
	@Override
	public int getTop() {
		// TODO Auto-generated method stub
		return this.top;
	}

	@Override
	public int getBottom() {
		// TODO Auto-generated method stub
		return this.bottom;
	}

	@Override
	public int getLeft() {
		// TODO Auto-generated method stub
		return this.left;
	}

	@Override
	public int getRight() {
		// TODO Auto-generated method stub
		return this.right;
	}

	@Override
	public Region intersect(Region other) throws NoIntersectionException {
		// TODO Auto-generated method stub
		if(other == null) {
			throw new NoIntersectionException();
		}else if	
				// wrong boundaries parameters relations
				(Math.min(this.getBottom(), other.getBottom()) < Math.max(this.getTop(), other.getTop()) ||
	            Math.min(this.getRight(), other.getRight()) < Math.max(this.getLeft(), other.getLeft())){
			throw new NoIntersectionException();
		}else {
			return new RegionImpl(
					Math.max(this.getLeft(), other.getLeft()), 
					Math.max(this.getTop(), other.getTop()), 
					Math.min(this.getRight(), other.getRight()), 
					Math.min(this.getBottom(), other.getBottom()));
		}
	}

	@Override
	public Region union(Region other) {
		// TODO Auto-generated method stub
		if(other == null) {
			return this;
		}else {
			return new RegionImpl(
					Math.min(this.getLeft(), other.getLeft()), 
					Math.min(this.getTop(), other.getTop()), 
					Math.max(this.getRight(), other.getRight()), 
					Math.max(this.getBottom(), other.getBottom()));
		}
	}

}
