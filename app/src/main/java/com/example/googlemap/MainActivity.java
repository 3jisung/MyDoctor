package com.example.googlemap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private ConnectivityManager connMgr;
    private NetworkInfo networkInfo;
    private Double myLatitude = 0.0;
    private Double myLongitude = 0.0;
    private List<Address> list = null;
    private Intent intent;
    TextView txt_distance;
    TextView txt_result;
    TextView txt_myaddress;

    Context context;

    int distanceOption = 5;     //거리 초기값
    int resultCount = 0;
    Boolean[] type = new Boolean[17];
    String[] typeString = new String[17];

    private MainActivity parent = this;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        geocoder = new Geocoder(this, Locale.KOREAN);

        txt_distance = findViewById(R.id.txt_distance);
        txt_result = findViewById((R.id.txt_result));
        txt_myaddress = findViewById(R.id.txt_myaddress);
        txt_distance.setText("검색 기준 거리 : " + distanceOption + "km" );

        for(int i = 0; i < type.length; i++)
            type[i] = true;

        typeString[0] = "A";
        typeString[1] = "B";
        typeString[2] = "C";
        typeString[3] = "D";
        typeString[4] = "E";
        typeString[5] = "G";
        typeString[6] = "H";
        typeString[7] = "I";
        typeString[8] = "M";
        typeString[9] = "N";
        typeString[10] = "R";
        typeString[11] = "T";
        typeString[12] = "U";
        typeString[13] = "V";
        typeString[14] = "W";
        typeString[15] = "Y";
        typeString[16] = "Z";

        connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); // 인터넷 연결 매니저
        networkInfo = null;

        if(connMgr != null)
            networkInfo = connMgr.getActiveNetworkInfo();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    myLatitude = location.getLatitude();
                    myLongitude = location.getLongitude();
                    updateMap();

//                    Toast.makeText(getApplicationContext(), Double.toString(myLatitude) + " / " + Double.toString(myLongitude),
//                            Toast.LENGTH_SHORT).show();

                    try {
                        list = geocoder.getFromLocation(
                                myLatitude, // 위도
                                myLongitude, // 경도
                                10); // 얻어올 값의 개수

                        if(list.get(0).getLocality() != null && list.get(0).getSubLocality() != null)
                            txt_myaddress.setText("현재 주소 : " + list.get(0).getAdminArea() + " " + list.get(0).getLocality() + " "
                                    + list.get(0).getSubLocality() + " " + list.get(0).getThoroughfare() + " " + list.get(0).getFeatureName());
                        else if(list.get(0).getLocality() == null && list.get(0).getSubLocality() != null)
                            txt_myaddress.setText("현재 주소 : " + list.get(0).getAdminArea() + " " + list.get(0).getSubLocality() + " "
                                    + list.get(0).getThoroughfare() + " " + list.get(0).getFeatureName());
                        else if(list.get(0).getLocality() != null && list.get(0).getSubLocality() == null)
                            txt_myaddress.setText("현재 주소 : " + list.get(0).getAdminArea() + " " + list.get(0).getLocality() + " "
                                    + list.get(0).getThoroughfare() + " " + list.get(0).getFeatureName());
                        else
                            txt_myaddress.setText("현재 주소 : " + list.get(0).getAdminArea() + " " + list.get(0).getThoroughfare() + " "
                                    + list.get(0).getFeatureName());

                        searchHospital();
//                        Toast.makeText(getApplicationContext(), list.get(0).getAdminArea() + list.get(0).getLocality() + list.get(0).getSubLocality(),
//                            Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);   //거리단위 : M
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
    }

    public class ProcessData extends AsyncTask<Integer, Void, Hospital[]> { //doInBackground, onProgressUpdate, onPostExecute

        @Override
        protected Hospital[] doInBackground(Integer... index) { // void의 배열형태
            Log.e("******", Integer.toString(index[0]));
            Log.e("******", typeString[index[0]]);
            if(list.get(0).getLocality() != null)
               return NetworkUtils.getXmlData(list.get(0).getAdminArea(), list.get(0).getLocality(), typeString[index[0]]);
            else
               return NetworkUtils.getXmlData(list.get(0).getAdminArea(), list.get(0).getSubLocality(), typeString[index[0]]);
        }

        @Override
        protected void onPostExecute(Hospital[] hospitals)  // doInBackground의 결과값을 받아온다
        {
            Log.e("******", Integer.toString(hospitals.length));
            super.onPostExecute(hospitals);
//            txtResult.setText(s);
//            System.out.println(s);
//            Log.e("*******", s); // 화면에 빨갛게 출력 / 스트링패턴, 출력시키고 싶은 객체

            try{
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//                Document doc = dBuilder.parse(s);

                for(int i=0; i<hospitals.length; i++)
                {
                    Double dis = distance(hospitals[i].getLatitude(), hospitals[i].getLongitude(), myLatitude, myLongitude);
                    if(dis.compareTo((double)distanceOption) != 1)
                        createMarker(hospitals[i]);
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
//        Toast.makeText(getApplicationContext(), Double.toString(lat1) + "/" + Double.toString(lon1) + "/" + Double.toString(lat2) + "/" + Double.toString(lon2), Toast.LENGTH_SHORT).show();
        Location startPos = new Location("PointA");
        Location endPos = new Location("PointB");

        startPos.setLatitude(lat1);
        startPos.setLongitude(lon1);
        endPos.setLatitude(lat2);
        endPos.setLongitude(lon2);

        double distance = startPos.distanceTo(endPos);
        distance /= 1000.0;
        return distance;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        updateMap();
    }

    public void searchHospital()
    {
        for(int i = 0; i < type.length; i++)
        {
            if(type[i] == true)
            {
                if(networkInfo != null && networkInfo.isConnected())
                    new MainActivity.ProcessData().execute(i); //AsyncTask 객체 생성 (인터넷 연결 후 작업처리 할 놈)
                else
                    Toast.makeText(getApplicationContext(), "인터넷 연결상태가 비정상적입니다.",
                            Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updateMap()
    {
        mMap.clear();
        resultCount = 0;
        txt_result.setText("검색 결과 : " + resultCount + "개");
        LatLng myPosition = new LatLng(myLatitude, myLongitude);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(myPosition);
//        markerOptions.title("서울");
//        markerOptions.snippet("한국의 수도");
//        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12 - (distanceOption/20)));
    }

    public void updateDistance(int distance)
    {
        distanceOption = distance;
        txt_distance.setText("검색 기준 거리 : " + distanceOption + "km");

        updateMap();
        searchHospital();
    }

    public void updateFilter(int index, Boolean b)
    {
        type[index] = b;
        updateMap();
        searchHospital();
    }

    public void createMarker(Hospital hospital)
    {
        resultCount += 1;
        txt_result.setText("검색 결과 : " + resultCount + "개");
        LatLng myPosition = new LatLng(hospital.getLatitude(), hospital.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(myPosition);
        markerOptions.title(hospital.getName());
        markerOptions.snippet(hospital.getAddress());
        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag(hospital);
        Hospital hos = (Hospital)marker.getTag();

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Hospital hos = (Hospital)marker.getTag();
                intent = new Intent(context, SubActivity.class);
                intent.putExtra("address", hos.getAddress());
                intent.putExtra("type", hos.getType());
                intent.putExtra("simpleMap", hos.getSimpleMap());
                intent.putExtra("name", hos.getName());
                intent.putExtra("mainNumber", hos.getMainNumber());
                intent.putExtra("emergencyNumber", hos.getEmergencyNumber());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.filter:
                CustomDialogFilter customDialogFilter = new CustomDialogFilter(MainActivity.this);
                customDialogFilter.callFunction(this);
                break;
            case R.id.distance:
                CustomDialogDistance customDialogDistance = new CustomDialogDistance(MainActivity.this);
                customDialogDistance.callFunction(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}