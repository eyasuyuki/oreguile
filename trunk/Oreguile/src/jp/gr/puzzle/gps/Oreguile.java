package jp.gr.puzzle.gps;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class Oreguile extends MapActivity {
	private List<Location> points = new ArrayList<Location>();
	private FrameLayout frame = null;
	private MapView map = null;
	private MapController controller = null;
	
    @Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViews();
        initZoomControll();
        initMyLocation();
    }
    
    private void initViews() {
    	frame = (FrameLayout)findViewById(R.id.frame);
        map = (MapView)findViewById(R.id.map);
        controller = map.getController();
    }
    
    private void initZoomControll() {
    	View zoomControl = map.getZoomControls();
    	FrameLayout.LayoutParams p =
    		new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
    									LayoutParams.WRAP_CONTENT,
    									Gravity.BOTTOM + Gravity.CENTER_HORIZONTAL);
    	frame.addView(zoomControl, p);
    }
    
    private void initMyLocation() {
    	final MyLocationOverlay overlay = new MyLocationOverlay(this, map);
    	overlay.enableMyLocation();
    	overlay.enableCompass();
    	overlay.runOnFirstFix(new Runnable() {
    		public void run() {
    			controller.setZoom(64);
    			controller.animateTo(overlay.getMyLocation());
    		}
    	});
    	map.getOverlays().add(overlay);
    }
}