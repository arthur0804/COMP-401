package a6;

import java.util.ArrayList;
import java.util.List;

public class ObservablePictureImpl implements ObservablePicture {
    private Picture pic;
    private List<ROIObserver> observers;
    private List<Region> regions;
    private Region changed_region;
    private Region region;
    private boolean suspend;

    public ObservablePictureImpl(Picture p) {
        if (p == null) {
            throw new IllegalArgumentException("new picture");
        }
        this.observers = new ArrayList<ROIObserver>();
        this.regions = new ArrayList<Region>();
        this.pic = p;
        this.suspend = false;
        this.region = null;
    }

    @Override
    public int getWidth() {
        return this.pic.getWidth();
    }

    @Override
    public int getHeight() {
        return this.pic.getHeight();
    }

    @Override
    public Pixel getPixel(int x, int y) {
        return this.pic.getPixel(x, y);
    }

    @Override
    public Picture paint(int x, int y, Pixel p) {
        return paint(x, y, p, 1.0);
    }

    @Override
    public Picture paint(int x, int y, Pixel p, double factor) {
        this.pic = pic.paint(x, y, p, factor);
        // Save the value for later notify pass in 
        region = new RegionImpl(x, y, x, y);
        // Update changed_union
        changed_region = region.union(changed_region);
        notify_observer(region);
        return this;
    }

    @Override
    public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
        return paint(ax, ay, bx, by, p, 1.0);
    }

    @Override
    public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
        this.pic = pic.paint(ax, ay, bx, by, p, factor);
        
        // Restrict boundary
        int left = (ax < bx) ? ax : bx;
        int right = (ax > bx) ? ax : bx;
        int top = (ay < by) ? ay : by;
        int bottom = (ay > by) ? ay : by;
        
        region = new RegionImpl(left, top, right, bottom);
        changed_region = region.union(changed_region);
        notify_observer(region);
        
        return this;
    }

    @Override
    public Picture paint(int cx, int cy, double radius, Pixel p) {
        return paint(cx, cy, radius, p, 1.0);
    }

    @Override
    public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
        this.pic = pic.paint(cx, cy, radius, p, factor);
        region = new RegionImpl(cx - (int) radius, cy - (int) radius, cx + (int) radius, cy + (int) radius);
        
        changed_region = region.union(changed_region);
        notify_observer(region);
        return this;
    }

    @Override
    public Picture paint(int x, int y, Picture p) {
        return paint(x, y, p, 1.0);
    }

    @Override
    public Picture paint(int x, int y, Picture p, double factor) {
        this.pic = pic.paint(x, y, p, factor);
        region = new RegionImpl(x, y, x+p.getWidth()-1, y+p.getHeight()-1);
        
        changed_region = region.union(changed_region);
        notify_observer(region);
        return this;
    }
    
    
    // Getter function
    @Override
    public String getCaption() {
        return this.pic.getCaption();
    }

    
    // Setter function
    @Override
    public void setCaption(String caption) {
        this.pic.setCaption(caption);
    }

    @Override
    public SubPicture extract(int x, int y, int width, int height) {
        return this.pic.extract(x, y, width, height);
    }

    @Override
    public void registerROIObserver(ROIObserver observer, Region r) {
    	// Check validity 
        if (observer == null || r == null) {
            throw new IllegalArgumentException("missing observer or r");
        }
        observers.add(observer);
        regions.add(r);
    }

    @Override
    public void unregisterROIObservers(Region r) {
        if (r == null) {
            throw new IllegalArgumentException("missing r");
        }
        // Add corresponding regions and observers
        List<Region> newregions = new ArrayList<Region>();
        List<ROIObserver> newobservers = new ArrayList<ROIObserver>();
        for (int i = 0; i < regions.size(); i++) {
            try {
                regions.get(i).intersect(r);
            } catch (NoIntersectionException e) { 
            	// If no intersection, add to list
                newregions.add(regions.get(i));
                newobservers.add(observers.get(i));
            }
        }
        // Update
        regions = newregions;
        observers = newobservers;
    }

    @Override
    public void unregisterROIObserver(ROIObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("missing observer");
        }
        List<ROIObserver> newobservers = new ArrayList<ROIObserver>();
        List<Region> newregions = new ArrayList<Region>();
        for (int i = 0; i < observers.size(); i++) {
            if (observers.get(i) != observer) {
                newobservers.add(observers.get(i));
                newregions.add(regions.get(i));
            }
        }
        observers = newobservers;
        regions = newregions;
    }

    @Override
    public ROIObserver[] findROIObservers(Region r) {
        if (r == null) {
            throw new IllegalArgumentException("missing r");
        }
        List<ROIObserver> found_o = new ArrayList<ROIObserver>();
        List<Region> found_r = new ArrayList<Region>();
        for (int i = 0; i < regions.size(); i++) {
            try {
                regions.get(i).intersect(r);
                found_o.add(observers.get(i));
                found_r.add(regions.get(i));
            } catch (NoIntersectionException e) {
            }
        }
        return found_o.toArray(new ROIObserver[0]);
    }

    @Override
    public void suspendObservable() {
        this.suspend = true;
    }

    @Override
    public void resumeObservable() {
        this.suspend = false;
        notify_observer(changed_region);
        changed_region = null;
    }

    
    // Helper function
    private void notify_observer(Region thisregion) {
        if (!suspend) {
            if (thisregion != null) {
                for (Region _region : regions) {
                    try {
                        observers.get(regions.indexOf(_region)).notify(this, thisregion.intersect(_region));
                    } catch (NoIntersectionException e) {
                    }

                }
            }
        }
    }
}
