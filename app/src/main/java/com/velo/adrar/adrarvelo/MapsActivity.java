package com.velo.adrar.adrarvelo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //Constantes
    static final int ON_FOOT_MODE = 1;
    static final int ON_BIKE_MODE = 2;

    //composant graphique
    private GoogleMap mMap;
    private ImageButton bt_go;
    private ImageButton bt_switch_mode;
    private ImageButton bt_choose_address;
    private NumberPicker numberPicker;

    //data
    private ArrayList<VeloStation> veloStations;
    private ArrayList<VeloStation> veloStationsToDisplay;
    private Options options;
    private Location location;
    private Criteria criteria;
    private List<LatLng> listeWaypoints;

    private GetVeloStationsAT getVeloStationsAT = null;
    private GetRoutePolylineAT getRoutePolylineAT = null;
    private LocationManager locationMgr;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        bt_go = findViewById(R.id.bt_go);
        bt_go.setOnClickListener(this);
        bt_switch_mode = findViewById(R.id.bt_switch_mode);
        bt_switch_mode.setOnClickListener(this);
        bt_choose_address = findViewById(R.id.bt_choose_address);
        bt_choose_address.setOnClickListener(this);

        // NumberPicker du nombre de stations à afficher.
        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(50);
        numberPicker.setWrapSelectorWheel(false);

        options = new Options(4, ON_FOOT_MODE);
        veloStations = new ArrayList<>();
        veloStationsToDisplay = new ArrayList<>();
        criteria = new Criteria();

        numberPicker.setValue(options.getNearbyNumber());
        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Si on n'a pas la permission, on la demande.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 25);
        }

        //Initializing googleApiClient
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Listener pour le NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                options.setNearbyNumber(newVal);
                filterStationsToDisplay();
            }
        });
    }

    // Lors de la réponse à la demande d'autorisation, on lance un refresh de la map.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] gr) {
        super.onRequestPermissionsResult(requestCode, permissions, gr);
        if (requestCode == 25) {
            filterStationsToDisplay();
        }
    }
//
//    // Inscription / désinscription au Bus.
//    @Override
//    protected void onStart() {
//        super.onStart();
//        BusApplication.getBus().register(this);
//    }

//    @Subscribe
//    public void triggersWhenLocationIsPosted(Location location) {
//        myPosition = new LatLng(location.getLatitude(), location.getLongitude());
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        BusApplication.getBus().unregister(this);
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(this);

        // On lance la requête pour la liste de stations.
        getVeloStationsAT = new GetVeloStationsAT();
        getVeloStationsAT.execute();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    // Click sur un marqueur pour faire apparaitre une fenêtre d'info et la polyline de la route.
    @Override
    public View getInfoContents(Marker marker) {

        VeloStation veloStation = (VeloStation) marker.getTag();

        //Layout de la fenêtre. Créer un fichier XML représentant le popup
        View view = LayoutInflater.from(this).inflate(R.layout.marker_cellule, null);
        TextView tvAddress = view.findViewById(R.id.textAddress);
        TextView tvStands = view.findViewById(R.id.textStands);
        TextView tvBikes = view.findViewById(R.id.textBikes);
        ImageView iv = view.findViewById(R.id.imageView);

        tvAddress.setText(veloStation.getAddress());
        tvStands.setText(String.format(Locale.FRANCE, "Places : %d", veloStation.getAvailable_bike_stands()));
        tvBikes.setText(String.format(Locale.FRANCE, "Vélos : %d", veloStation.getAvailable_bikes()));
        iv.setImageResource(veloStation.getLogo());

        return view;
    }

    // Click sur une fenêtre d'info.
    @Override
    public void onInfoWindowClick(Marker marker) {

        VeloStation veloStation = (VeloStation) marker.getTag();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = locationMgr.getLastKnownLocation(locationMgr.getBestProvider(criteria, false));

            getRoutePolylineAT = new GetRoutePolylineAT(veloStation);
            getRoutePolylineAT.execute();
        }

        marker.hideInfoWindow();
    }

    @Override
    public void onClick(View v) {
        if (v == bt_go) {
            getVeloStationsAT = new GetVeloStationsAT();
            getVeloStationsAT.execute();
        } else if (v == bt_switch_mode) {
            if (options.getMode() == ON_FOOT_MODE) {
                options.setMode(ON_BIKE_MODE);
                bt_switch_mode.setImageResource(R.mipmap.ic_icon_bike);
                Toast.makeText(this, "Mode Vélo : les stations sans places libres sont affichées en rouge", Toast.LENGTH_SHORT).show();
            } else if (options.getMode() == ON_BIKE_MODE) {
                options.setMode(ON_FOOT_MODE);
                bt_switch_mode.setImageResource(R.mipmap.ic_icon_walking);
                Toast.makeText(this, "Mode Piéton : les stations sans vélos sont affichées en rouge", Toast.LENGTH_SHORT).show();
            }
            refreshMap();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // ASYNC TASK pour la polyline.
    //
    private class GetRoutePolylineAT extends AsyncTask {

        private VeloStation veloStation;

        public GetRoutePolylineAT(VeloStation veloStation) {

            this.veloStation = veloStation;
        }

        protected Object doInBackground(Object[] objects) {
            try {
                return WebServiceUtils.getRoutePolyline(location, veloStation, options);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Object resultat) {
            if (resultat != null) {
                listeWaypoints = PolyUtil.decode((String) resultat);
                refreshMap();
            }
        }
    }

    // ASYNC TASK pour la liste des stations.
    //
    private class GetVeloStationsAT extends AsyncTask {

        protected Object doInBackground(Object[] objects) {
            try {
                return WebServiceUtils.getVeloStations();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Object resultat) {
            if (resultat != null) {
                veloStations.clear();
                veloStations.addAll((Collection<? extends VeloStation>) resultat);
            }
            filterStationsToDisplay();
        }
    }

    public void filterStationsToDisplay() {

        veloStationsToDisplay.clear();

        // Si la géolocalisation est autorisée, on active le marqueur personnel.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            // On récupère les dernières coordonnées connues.
            location = locationMgr.getLastKnownLocation(locationMgr.getBestProvider(criteria, false));

            if (location != null) {

                // Pour chaque station de la liste complète :
                for (VeloStation station : veloStations) {

                    // On calcule sa distance par rapport à location.
                    station.setDistance(Math.sqrt(
                            Math.pow(location.getLatitude() - station.getPosition().latitude, 2) + Math.pow(location.getLongitude() - station.getPosition().longitude, 2)
                    ));
                }

                // On trie la liste complète.
                Collections.sort(veloStations, new Comparator<VeloStation>() {
                    @Override
                    // 'compare' renvoie un int, or on travaille ici sur des doubles, donc on gère la comparaison nous-mêmes.
                    public int compare(VeloStation o1, VeloStation o2) {
                        double res = o1.getDistance() - o2.getDistance();
                        if (res > 0) {
                            return 1;
                        } else if (res == 0) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                });

                // On ajoute dans la liste à afficher, les n premiers éléments de la liste complète.
                veloStationsToDisplay.addAll(veloStations.subList(0, options.getNearbyNumber()));
            }

            // Si on n'a pas la permission, on remplit la liste de stations à afficher avec la liste de toutes les stations.
            else {
                veloStationsToDisplay.addAll(veloStations);
            }
        }

        refreshMap();
    }

    public void refreshMap() {
        // Si la map n'est pas là, on ne fait rien.
        if (mMap == null) {
            return;
        }

        mMap.clear();

        // Dessin de la polyline, le cas échéant.
        if (listeWaypoints != null) {
            mMap.addPolyline(new PolylineOptions()
                    .addAll(listeWaypoints)
                    .width(5)
                    .color(Color.RED));
        }

        if (veloStationsToDisplay != null) {

            // Construction des marqueurs et préparation du zoom général.
            LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
            for (VeloStation stationVelo : veloStationsToDisplay) {
                MarkerOptions markerVelo = new MarkerOptions();
                markerVelo.position(stationVelo.getPosition());
                markerVelo.title(stationVelo.getName());

                // Décision de la couleur des icônes en fonction du mode (à pied ou en vélo).
                if ((options.getMode() == ON_FOOT_MODE && stationVelo.getAvailable_bikes() == 0)
                        || (options.getMode() == ON_BIKE_MODE && stationVelo.getAvailable_bike_stands() == 0)) {
                    markerVelo.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_circle_holo_red));
                } else {
                    markerVelo.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_circle_holo_green));
                }

                mMap.addMarker(markerVelo).setTag(stationVelo);
                // On rajoute ces coordonnées à la liste pour le zoom général.
                latLngBounds.include(stationVelo.getPosition());
            }
            Criteria criteria = new Criteria();
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = locationMgr.getLastKnownLocation(locationMgr.getBestProvider(criteria, false));
                if (location != null) {
                    latLngBounds.include(new LatLng(location.getLatitude(), location.getLongitude()));
                }
            }

            // On effectue le zoom/recentrage de la vue.
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 150));
        }
    }
}

