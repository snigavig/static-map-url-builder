package dmax.staticmap.builder;

import android.content.Context;

import dmax.staticmap.Config;
import dmax.staticmap.Marker;

import static dmax.staticmap.R.string.markerColor;
import static dmax.staticmap.R.string.markerDef;
import static dmax.staticmap.R.string.markerLabel;
import static dmax.staticmap.R.string.markerSize;

/**
 * Class for append maps markers url segments.
 *
 * @user: Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * @date: 7/31/14
 * @time: 4:13 PM
 */
public class MarkerSegment extends Segment {

    @Override
    public void append(Config config, StringBuilder urlBuilder, Context context) {
        Marker[] markers = config.getMarkers();

        if (markers.length == 0) return;

        for (Marker marker : markers) {
            appendWithSeparator(urlBuilder, context.getString(markerDef));
            appendMarker(marker, urlBuilder, context);
        }
    }

    private void appendMarker(Marker marker, StringBuilder urlBuilder, Context context) {
        String targetAddress = marker.getAddress();

        if (marker.getColor() != null) appendMarkerPath(urlBuilder, context, markerColor, marker.getColor());
        if (marker.getLabel() != null) appendMarkerPath(urlBuilder, context, markerLabel, marker.getLabel());
        if (marker.getSize() != null) appendMarkerPath(urlBuilder, context, markerSize, marker.getSize().name());

        urlBuilder.append(targetAddress != null
                ? targetAddress
                : createLocation(marker));
    }

    private String createLocation(Marker marker) {
        return "" + marker.getLatitude() + SEPARATOR_LOCATION + marker.getLongitude();
    }

    private <T> void appendMarkerPath(StringBuilder urlBuilder, Context context, int resId, T data) {
        urlBuilder.append(context.getString(resId, data)).append(SEPARATOR_MARKER);
    }
}

