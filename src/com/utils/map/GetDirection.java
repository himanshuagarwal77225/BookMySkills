package com.utils.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public abstract class GetDirection extends AsyncTask<String, String, String> {

	private Context mContext;
	private LatLng p2;
	private LatLng p1;

	boolean isProgressShown = true;

	public GetDirection(Context mContext) {
		this.mContext = mContext;
		isProgressShown = true;
	}

	public GetDirection(Context mContext, boolean isShow) {
		this.mContext = mContext;
		isProgressShown = false;
	}

	public void setPoints(LatLng toLaLng, LatLng fromLatLng) {
		this.p1 = toLaLng;
		this.p2 = fromLatLng;
	}

	private ProgressDialog pDialog;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		pDialog = new ProgressDialog(mContext);
		pDialog.setMessage("Loading route. Please wait...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		if (isProgressShown)
			pDialog.show();
	}

	protected String doInBackground(String... args) {

		try {
			String lat1 = "" + p1.latitude;
			if (lat1.length() > 8)
				lat1 = lat1.substring(0, 9);
			String lng1 = "" + p1.longitude;
			if (lng1.length() > 8)
				lng1 = lng1.substring(0, 9);

			String lat2 = "" + p2.latitude;
			if (lat2.length() > 8)
				lat2 = lat2.substring(0, 9);
			String lng2 = "" + p2.longitude;
			if (lng2.length() > 8)
				lng2 = lng2.substring(0, 9);

			String stringUrl = "http://maps.google.com/maps/api/directions/json?origin="
					+ lat1
					+ ","
					+ lng1
					+ "&destination="
					+ lat2
					+ ","
					+ lng2
					+ "&sensor=false";

			StringBuilder response = new StringBuilder();

			URL url = new URL(stringUrl);
			HttpURLConnection httpconn = (HttpURLConnection) url
					.openConnection();
			if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader input = new BufferedReader(
						new InputStreamReader(httpconn.getInputStream()), 8192);
				String strLine = null;

				while ((strLine = input.readLine()) != null) {
					response.append(strLine);
				}
				input.close();
			}
			String jsonOutput = response.toString();
			JSONObject jsonObject = new JSONObject(jsonOutput);
			JSONArray routesArray = jsonObject.getJSONArray("routes");
			JSONObject route = routesArray.getJSONObject(0);
			JSONObject poly = route.getJSONObject("overview_polyline");
			String polyline = poly.getString("points");
			polyz = decodePoly(polyline);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	List<LatLng> polyz;

	private PolylineOptions line;

	public abstract void onPolyInit(PolylineOptions mPolyline);

	protected void onPostExecute(String file_url) {
		if (polyz != null)
			for (int i = 0; i < polyz.size() - 1; i++) {
				LatLng src = polyz.get(i);
				LatLng dest = polyz.get(i + 1);

				line = new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(8).color(Color.BLUE).geodesic(true);

				onPolyInit(line);

			}

		pDialog.dismiss();
	}

	/* Method to decode polyline points */
	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

	public void clearDirections() {
		polyz.clear();
	}

}