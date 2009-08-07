package jp.gr.puzzle.gps;

import jp.gr.puzzle.gps.data.Route;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class RouteOverlay extends Overlay {
	private static final String TAG = "RouteOverlay";

	private Route route = null;

	@Override
	public void draw(Canvas canvas, MapView view, boolean shadow) {
		if (shadow) {
			// TODO
		} else {
			Log.d(TAG, "route=" + route);
			if (route != null && route.getPoints().size() > 0) {
				Log.d(TAG, "size=" + route.getPoints().size());
				Path path = new Path();
				GeoPoint top = route.getPoints().get(0);
				Point p = view.getProjection().toPixels(top, null);
				path.moveTo(p.x, p.y);
				for (GeoPoint g: route.getPoints()) {
					p = view.getProjection().toPixels(g, null);
					path.lineTo(p.x, p.y);
				}
				Paint paint = new Paint();
				paint.setStyle(Paint.Style.STROKE);
				paint.setStrokeWidth(10.0f);
				paint.setColor(0xffff0000);
				canvas.drawPath(path, paint);
			}
		}
		super.draw(canvas, view, shadow);
	}
	
	public void setRoute(Route route) {
		this.route = route;
	}

}
