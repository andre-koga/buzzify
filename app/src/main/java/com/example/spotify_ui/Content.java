

package com.example.spotify_ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.firebase.auth.FirebaseAuth;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import okhttp3.MediaType;

public class Content extends AppCompatActivity {


    // spotify api stuff
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String CLIENT_ID = "c5d5db9b10f6403090a273b1e24bee8a";
    public static final String REDIRECT_URI = "spotifyacc://auth";
    public static final String[] SCOPE = { "user-read-private user-read-email user-top-read" };
    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;


    public static String mAccessToken, mAccessCode;
    //
    private AppBarConfiguration appBarConfiguration;

    private static Button btn;


    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getToken();

        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.title_main);
        View v = actionBar.getCustomView();
        firebaseAuth = FirebaseAuth.getInstance();
        btn = v.findViewById(R.id.user_button);
        btn.setText(firebaseAuth.getCurrentUser().getEmail());
        setContentView(R.layout.content);




//
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(
//                navController.getGraph())
//                .build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }
    // more spotify api code here
    /**
     * Get token from Spotify
     * This method will open the Spotify login activity and get the token
     * What is token?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getToken () {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        AuthorizationClient.openLoginActivity(Content.this, AUTH_TOKEN_REQUEST_CODE, request);


    }

    /**
     * When the app leaves this activity to momentarily get a token/code, this
     * function
     * fetches the result of that external activity to get the response from Spotify
     */
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){



        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);


        // Check which request code is present (if any)
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {



            mAccessToken = response.getAccessToken();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    Toast.makeText(Content.this, mAccessToken, Toast.LENGTH_SHORT).show();
                }
            });
        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();


            Toast.makeText(Content.this, mAccessCode,
                    Toast.LENGTH_SHORT).show();
            // onGetTokenWithCode();
        }
    }

    /**
     * Get authentication request
     *
     * @param type the type of the request
     * @return the authentication request
     */
    private AuthorizationRequest getAuthenticationRequest (AuthorizationResponse.Type type){
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(SCOPE) // <--- Change the scope of your requested token here
                .setCampaign("your-campaign-token")
                .build();
    }

    /**
     * Gets the redirect Uri for Spotify
     *
     * @return redirect Uri object
     */
    private Uri getRedirectUri () {
        return Uri.parse(REDIRECT_URI);
    }

    public static Button getButton() {
        return btn;
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
    }

//        public void clearUserInformation() {
//            AuthorizationClient.clearCookies(this);
//        }
    //


}
