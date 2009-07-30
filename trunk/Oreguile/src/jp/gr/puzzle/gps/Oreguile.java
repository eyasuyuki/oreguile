package jp.gr.puzzle.gps;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class Oreguile extends MapActivity {
	private static final String TAG = "Oregaulie";
	private static final int UNIT = 10;
	private static final double UNIT2 = 10.0;
	private boolean isObserved = true;

	private float dist = 0.0f;
	private Location loc = null;
	private List<Location> points = new ArrayList<Location>();
	private FrameLayout frame = null;
	private MapView map = null;
	private MapController controller = null;
	private LocationManager manager = null;
	private LocationListener listener = null;
	private MyLocationOverlay overlay = null;
	
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
        initLocationListener();
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
    	overlay = new MyLocationOverlay(this, map);
    	overlay.enableMyLocation();
    	overlay.enableCompass();
    	overlay.runOnFirstFix(new Runnable() {
    		public void run() {
    			controller.setZoom(64);
    			GeoPoint point = overlay.getMyLocation();
    			updateMyLocation(point);
    		}
    	});
    	map.getOverlays().add(overlay);
    }
    
    private void updateMyLocation(GeoPoint point) {
    	if (overlay != null) {
			controller.animateTo(point);
    	}
    }
    
    private boolean isMoved(Location loc, Location prev) {
    	return	loc.getLatitude() != prev.getLatitude() &&
    			loc.getLongitude() != prev.getLongitude();
    }
    
    private void initLocationListener() {
    	listener = new LocationListener() {
			public void onLocationChanged(Location location) {
				Location prev = loc;
				if (prev != null && isMoved(location, prev)) {
					dist += location.distanceTo(prev);
					Toast.makeText(Oreguile.this, "dist=" + dist, Toast.LENGTH_LONG).show();
					if (dist >= UNIT2) {
						dist = 0.0f;
						Toast.makeText(Oreguile.this, location.toString(), Toast.LENGTH_LONG).show();
						int late6 = (int)(location.getLatitude() * 1E6);
						int lone6 = (int)(location.getLongitude() * 1E6);
						updateMyLocation(new GeoPoint(late6, lone6));
						points.add(location);
					}
				}
				loc = location;
			}
			public void onProviderDisabled(String provider) {}
			public void onProviderEnabled(String provider) {}
			public void onStatusChanged(String provider, int status, Bundle extras) {}
    	};
		manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, UNIT, listener);
    }
}