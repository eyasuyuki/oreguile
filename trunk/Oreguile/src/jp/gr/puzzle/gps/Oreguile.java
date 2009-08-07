package jp.gr.puzzle.gps;

import java.util.List;

import jp.gr.puzzle.gps.data.Helper;
import jp.gr.puzzle.gps.data.LocationDao;
import jp.gr.puzzle.gps.data.LocationData;
import jp.gr.puzzle.gps.data.Route;
import jp.gr.puzzle.gps.data.RouteDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class Oreguile extends MapActivity {
	private static final String TAG = "Oregaulie";
	private static final int UNIT = 5;
	private static final double UNIT2 = 5.0;
	
	private boolean isObserved;

	private float dist = 0.0f;
	private Location loc = null;
	private FrameLayout frame = null;
	private MapView map = null;
	private MapController controller = null;
	private LocationManager manager = null;
	private LocationListener listener = null;
	private MyLocationOverlay overlay = null;
	private MenuItem startStopItem = null;
	private Route currentRoute = null;
	private SQLiteDatabase db = null;
	private RouteDao routeDao = null;
	private LocationDao locationDao = null;
	private RouteOverlay routeOverlay = null;
	
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
        
        currentRoute = (Route)this.getLastNonConfigurationInstance();
        isObserved = currentRoute != null;

        initDatabase();
        initViews();
        initZoomControll();
        initMyLocation();
        initLocationListener();
    }
    
    private void initDatabase() {
    	Helper helper = new Helper(this);
    	db = helper.getWritableDatabase();
    	routeDao = new RouteDao(db);
    	locationDao = new LocationDao(db);
    }
    
    private void initViews() {
    	frame = (FrameLayout)findViewById(R.id.frame);
        map = (MapView)findViewById(R.id.map);
        controller = map.getController();
            }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		startStopItem = menu.findItem(R.id.start_stop);
		setStartStopLabel();
		return true;
	}
    
    private void setStartStopLabel() {
		CharSequence startLabel = getText(R.string.start_label);
		CharSequence stopLabel = getText(R.string.stop_label);
		startStopItem.setTitle(isObserved ? stopLabel : startLabel);
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
        List<Overlay> overlays = map.getOverlays();
        if (!overlays.contains(overlay)) {
        	overlays.add(overlay);
        }
        // route overlay
        routeOverlay = new RouteOverlay();
        if (!overlays.contains(routeOverlay)) {
        	overlays.add(routeOverlay);
        }
        map.invalidate();
    }
    
    private void updateMyLocation(Location location) {
		int late6 = (int)(location.getLatitude() * 1E6);
		int lone6 = (int)(location.getLongitude() * 1E6);
		GeoPoint g = new GeoPoint(late6, lone6);
		updateMyLocation(g);
		if (isObserved && currentRoute != null) {
			LocationData data = LocationData.create(location);
			data.setRouteId(currentRoute.getRowid());
			locationDao.insert(data);
			Toast.makeText(Oreguile.this, "geoPoint=" + g, Toast.LENGTH_LONG).show();
			currentRoute.getPoints().add(g);
		}
    }

    private void updateMyLocation(GeoPoint point) {
    	if (overlay != null) {
			controller.animateTo(point);
    	}
    }
    
    private boolean isMoved(Location loc, Location prev) {
    	return loc.distanceTo(prev) > 0.1;
    }
    
    private void initLocationListener() {
    	listener = new LocationListener() {
			public void onLocationChanged(Location location) {
				Location prev = loc;
				if (prev != null && isMoved(location, prev)) {
					dist += location.distanceTo(prev);
					if (dist >= UNIT2) {
						dist = 0.0f;
						Log.d(TAG, "location=" + location);
						updateMyLocation(location);
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
    
    private void doStartStop() {
    	if (isObserved) {
    		// end time
    		currentRoute.setEnd(System.currentTimeMillis());
    		// update route
    		routeDao.update(currentRoute);
    		// reset
    		//currentRoute = null; // TEST
    		isObserved = false;
    	} else {
    		// create route
    		currentRoute = new Route();
    		currentRoute.setStart(System.currentTimeMillis());
    		// insert route
    		long rowid = routeDao.insert(currentRoute);
    		// get stored data
    		currentRoute.setRowid(rowid);
    		// route overlay
    		routeOverlay.setRoute(currentRoute);
    		// init
    		isObserved = true;
    	}
		setStartStopLabel();
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.start_stop:
			doStartStop();
			return true;
		}
		return false;
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return currentRoute;
	}

}