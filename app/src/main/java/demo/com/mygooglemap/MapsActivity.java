package demo.com.mygooglemap;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMap(0,0,"幾內亞灣");
        // Add a marker in Sydney and move the camera
    }

    void setMap(double lat, double lng, String title) {

        LatLng latLng = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_spinner_menu, menu);
        int[] ids = {R.id.pointOfViewSpinner, R.id.spotSpinner, R.id.mapTypeSpinner};
        int[] arrays = {R.array.point_of_view, R.array.spot, R.array.map_type};

        for (int i = 0; i < ids.length; i++) {
            MenuItem item = menu.findItem(ids[i]);
            Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this, arrays[i], R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);
            spinner.setGravity(Gravity.RIGHT);
            spinner.setOnItemSelectedListener(itemSelectedListener);
        }
        return true;
    }

    AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(i==0) {
                return;
            }
            String itemName =((TextView)view).getText().toString();

            Toast.makeText(MapsActivity.this, ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
            switch (adapterView.getId()) {
                case R.id.pointOfViewSpinner:
                    switch (i) {
                        case 1: setPointOfView(0, 16.9f); break;
                        case 2: setPointOfView(60, 17); break;
                    } break;
                case R.id.spotSpinner:
                    switch (i) {
                        case 1: setMap(40.689217, -74.044436, itemName); break;
                        case 2: setMap(-45.071792, 169.903311, itemName); break;
                        case 3: setMap(48.858522, 2.295039, itemName); break;
                        case 4: setMap(31.619986, 74.876531, itemName); break;
                    } break;
                case R.id.mapTypeSpinner:
                    switch (i) {
                        case 1: mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); break;
                        case 2: mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE); break;
                        case 3: mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID); break;
                        case 4: mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN); break;
                        case 5: mMap.setMapType(GoogleMap.MAP_TYPE_NONE); break;
                    } break;

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    void setPointOfView(float angle, float level) {
        LatLng latlng = mMap.getCameraPosition().target;
        CameraUpdate camUpdate = CameraUpdateFactory.
                newCameraPosition(new CameraPosition.Builder()
                        .target(latlng)
                        .tilt(angle)
                        .zoom(level)
                        .build());
        mMap.animateCamera(camUpdate);
    }
}
