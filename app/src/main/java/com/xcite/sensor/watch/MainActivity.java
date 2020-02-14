package com.xcite.sensor.watch;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
/*
import com.google.gson.JsonElement;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.PNTimeResult;
import com.pubnub.api.models.consumer.message_actions.PNMessageAction;
import com.pubnub.api.models.consumer.objects_api.space.PNSpace;
import com.pubnub.api.models.consumer.objects_api.user.PNUser;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.api.models.consumer.pubsub.PNSignalResult;
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult;
import com.pubnub.api.models.consumer.pubsub.objects.PNMembershipResult;
import com.pubnub.api.models.consumer.pubsub.objects.PNSpaceResult;
import com.pubnub.api.models.consumer.pubsub.objects.PNUserResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 */

public class MainActivity extends WearableActivity implements SensorEventListener {

    public Vibrator vibrator;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mVibrateThreshold = 0;
    private float mDeltaXMax = 0;
    private float mDeltaYMax = 0;
    private float mDeltaZMax = 0;

    private float mDeltaX = 0;
    private float mDeltaY = 0;
    private float mDeltaZ = 0;

    private float mLastX, mLastY, mLastZ;

    private double decimalPlace = 100d;

    //  private String mPubId = "pub-c-48e20c2a-37af-4870-b3ce-f8990a5c2cbc";
    // private String mSubId = "sub-c-4d82185a-960d-11e6-b36e-0619f8945a4f";
    // private String subChannel = "subscriber_sensor_watch_channel";
    private String uuid = "b6e37960-4eab-11ea-b77f-2e728ce88125";

    // PubNub pubnub;

    //TcpClient mTcpClient;

    private TextView mCurrentX, mCurrentY, mCurrentZ,
            mGravityXTextView, mGravityYTextView, mGravityZTextView,
            mGyroXTextView, mGyroYTextView, mGyroZTextView,
            mLinearAccelXTextView, mLinearAccelYTextView, mLinearAccelZTextView,
            mRotVectorXTextView, mRotVectorYTextView, mRotVectorZTextView,
            mGameRotVecXTextView, mGameRotVecYTextView, mGameRotVecZTextView,
            mGeoMagRotVecXTextView, mGeoMagRotVecYTextView, mGeoMagRotVecZTextView,
            mMagFieldXTextView, mMagFieldYTextView, mMagFieldZTextView,
            mLightTextView,
            mPressureTextView;

    //private EditText ipAddressEditText, portEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        //displayCleanValues();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("******** TYPE_ACCELEROMETER", "SUCCESS");
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
            Log.d("******** TYPE_GRAVITY", "SUCCESS");
            Sensor mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
            mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            Log.d("******** TYPE_GYROSCOPE", "SUCCESS");
            Sensor mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            mSensorManager.registerListener(this, mGyro, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            Log.d("******** TYPE_LINEAR_ACCELERATION", "SUCCESS");
            Sensor mLinearAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            mSensorManager.registerListener(this, mLinearAccel, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
            Log.d("******** TYPE_ROTATION_VECTOR", "SUCCESS");
            Sensor mRotVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            mSensorManager.registerListener(this, mRotVector, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION) != null) {
            Log.d("******** TYPE_SIGNIFICANT_MOTION", "SUCCESS");
            Sensor mSigMotion = mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
            mSensorManager.registerListener(this, mSigMotion, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR) != null) {
            Log.d("******** TYPE_GAME_ROTATION_VECTOR", "SUCCESS");
            Sensor mGameRotVec = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
            mSensorManager.registerListener(this, mGameRotVec, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null) {
            Log.d("******** TYPE_GEOMAGNETIC_ROTATION_VECTOR", "SUCCESS");
            Sensor mGeoMagRotVec = mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
            mSensorManager.registerListener(this, mGeoMagRotVec, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            Log.d("******** TYPE_MAGNETIC_FIELD", "SUCCESS");
            Sensor mMagField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorManager.registerListener(this, mMagField, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            Log.d("******** TYPE_LIGHT", "SUCCESS");
            Sensor mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            Log.d("******** TYPE_PRESSURE", "SUCCESS");
            Sensor mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
        }

        //initialize vibration
        //vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        // Enables Always-on
        setAmbientEnabled();

        //initializePubNub();
    }

    public void StartServer(View view) {
        //if(ipAddressEditText.getText().toString().isEmpty()) return;
        //if(portEditText.getText().toString().isEmpty()) return;

        // TcpClient.SERVER_IP = ipAddressEditText.getText().toString();
        // TcpClient.SERVER_PORT = Integer.parseInt(portEditText.getText().toString());

        //new ConnectTask().execute("");
        Log.d("StartServer", "00000000");
    }

    public void StopServer(View view) {
        //if (mTcpClient != null) {
           // Log.d("StopServer", "---------");
           // mTcpClient.stopClient();
        //}
    }

    public void SendMessage(View view) {
        //if (mTcpClient != null) {
           // Log.d("SendMessage", "Rastafari");
            //mTcpClient.sendMessage("Rastafari");
        //}
    }

    /*
        private void initializePubNub() {

            Log.d("    initializePubNub", "START");
            PNConfiguration pnConfiguration = new PNConfiguration();
            pnConfiguration.setSubscribeKey(mSubId);
            pnConfiguration.setPublishKey(mPubId);
            pnConfiguration.setSecure(false);
            pnConfiguration.setUuid(uuid);
            pubnub = new PubNub(pnConfiguration);

           pubnub.time().async(new PNCallback<PNTimeResult>() {
                @Override
                public void onResponse(PNTimeResult result, PNStatus status) {
                    // handle time result.
                    Log.d("PNTimeResult", status.toString());
                }
            });

            pubnub.addListener(new SubscribeCallback() {
                @Override
                public void status(PubNub pubnub, PNStatus status) {
                    switch (status.getOperation()) {
                        // let's combine unsubscribe and subscribe handling for ease of use
                        case PNSubscribeOperation:
                            Log.d("SubscribeOperation", status.toString());
                        case PNUnsubscribeOperation:
                            // note: subscribe statuses never have traditional
                            // errors, they just have categories to represent the
                            // different issues or successes that occur as part of subscribe
                            Log.d("- PNUnsubscribeOperation", status.toString());
                            switch (status.getCategory()) {
                                case PNConnectedCategory:
                                    // this is expected for a subscribe, this means there is no error or issue whatsoever
                                    Log.d("- PNConnectedCategory", String.valueOf(status.getStatusCode()));
                                case PNReconnectedCategory:
                                    // this usually occurs if subscribe temporarily fails but reconnects. This means
                                    Log.d("- PNReconnectedCategory", String.valueOf(status.getStatusCode()));
                                    // there was an error but there is no longer any issue
                                case PNDisconnectedCategory:
                                    // this is the expected category for an unsubscribe. This means there
                                    // was no error in unsubscribing from everything
                                    Log.d("- PNDisconnectedCategory",String.valueOf(status.getStatusCode()));
                                case PNUnexpectedDisconnectCategory:
                                    // this is usually an issue with the internet connection, this is an error, handle appropriately
                                    Log.d("- PNUnexpectedDisconnectCategory", String.valueOf(status.getStatusCode()));
                                case PNAccessDeniedCategory:
                                    // this means that PAM does allow this client to subscribe to this
                                    // channel and channel group configuration. This is another explicit error
                                    Log.d("- PNAccessDeniedCategory", String.valueOf(status.getStatusCode()));
                                default:
                                    // More errors can be directly specified by creating explicit cases for other
                                    // error categories of `PNStatusCategory` such as `PNTimeoutCategory` or `PNMalformedFilterExpressionCategory` or `PNDecryptionErrorCategory`
                                    Log.d("- default", String.valueOf(status.getStatusCode()));
                            }

                        case PNHeartbeatOperation:
                            // heartbeat operations can in fact have errors, so it is important to check first for an error.
                            // For more information on how to configure heartbeat notifications through the status
                            // PNObjectEventListener callback, consult <link to the PNCONFIGURATION heartbeart config>

                            Log.d("- default", String.valueOf(status.getStatusCode()));
                            if (status.isError()) {
                                // There was an error with the heartbeat operation, handle here
                            } else {
                                // heartbeat operation was successful
                            }
                        default: {
                            // Encountered unknown status type
                        }
                    }
                }

                @Override
                public void message(PubNub pubnub, PNMessageResult message) {
                    String messagePublisher = message.getPublisher();
                    System.out.println("Message publisher: " + messagePublisher);
                    System.out.println("Message Payload: " + message.getMessage());
                    System.out.println("Message Subscription: " + message.getSubscription());
                    System.out.println("Message Channel: " + message.getChannel());
                    System.out.println("Message timetoken: " + message.getTimetoken());
                }

                @Override
                public void presence(PubNub pubnub, PNPresenceEventResult presence) {

                }

                @Override
                public void signal(PubNub pubnub, PNSignalResult pnSignalResult) {
                    System.out.println("Signal publisher: " + pnSignalResult.getPublisher());
                    System.out.println("Signal payload: " + pnSignalResult.getMessage());
                    System.out.println("Signal subscription: " + pnSignalResult.getSubscription());
                    System.out.println("Signal channel: " + pnSignalResult.getChannel());
                    System.out.println("Signal timetoken: " + pnSignalResult.getTimetoken());
                }

                @Override
                public void user(PubNub pubnub, PNUserResult pnUserResult) {
                    // for Objects, this will trigger when:
                    // . user updated
                    // . user deleted
                    PNUser pnUser = pnUserResult.getUser(); // the user for which the event applies to
                    pnUserResult.getEvent(); // the event name
                }

                @Override
                public void space(PubNub pubnub, PNSpaceResult pnSpaceResult) {
                    // for Objects, this will trigger when:
                    // . space updated
                    // . space deleted
                    PNSpace pnSpace = pnSpaceResult.getSpace(); // the space for which the event applies to
                    pnSpaceResult.getEvent(); // the event name
                }

                @Override
                public void membership(PubNub pubnub, PNMembershipResult pnMembershipResult) {
                    // for Objects, this will trigger when:
                    // . user added to a space
                    // . user removed from a space
                    // . membership updated on a space
                    JsonElement data = pnMembershipResult.getData(); // membership data for which the event applies to
                    pnMembershipResult.getEvent(); // the event name
                }

                @Override
                public void messageAction(PubNub pubnub, PNMessageActionResult pnActionResult) {
                    PNMessageAction pnMessageAction = pnActionResult.getMessageAction();
                    System.out.println("Message action type: " + pnMessageAction.getType());
                    System.out.println("Message action value: " + pnMessageAction.getValue());
                    System.out.println("Message action uuid: " + pnMessageAction.getUuid());
                    System.out.println("Message action actionTimetoken: " + pnMessageAction.getActionTimetoken());
                    System.out.println("Message action messageTimetoken: " + pnMessageAction.getMessageTimetoken());

                    System.out.println("Message action subscription: " + pnActionResult.getSubscription());
                    System.out.println("Message action channel: " + pnActionResult.getChannel());
                    System.out.println("Message action timetoken: " + pnActionResult.getTimetoken());
                }
            });

            List<String> channels = new ArrayList<>();
            channels.add("test_channel");
            pubnub.subscribe().channels(channels).execute();
        }
    */
    public void initializeViews() {
        mCurrentX = findViewById(R.id.currentX);
        mCurrentY = findViewById(R.id.currentY);
        mCurrentZ = findViewById(R.id.currentZ);

        mGravityXTextView = findViewById(R.id.gravityX);
        mGravityYTextView = findViewById(R.id.gravityY);
        mGravityZTextView = findViewById(R.id.gravityZ);

        mGyroXTextView = findViewById(R.id.gyroX);
        mGyroYTextView = findViewById(R.id.gyroY);
        mGyroZTextView = findViewById(R.id.gyroZ);

        mLinearAccelXTextView = findViewById(R.id.linearAccelX);
        mLinearAccelYTextView = findViewById(R.id.linearAccelY);
        mLinearAccelZTextView = findViewById(R.id.linearAccelZ);

        mRotVectorXTextView = findViewById(R.id.rotVectorX);
        mRotVectorYTextView = findViewById(R.id.rotVectorY);
        mRotVectorZTextView = findViewById(R.id.rotVectorZ);

        mGameRotVecXTextView = findViewById(R.id.gameRotVecX);
        mGameRotVecYTextView = findViewById(R.id.gameRotVecY);
        mGameRotVecZTextView = findViewById(R.id.gameRotVecZ);

        mGeoMagRotVecXTextView = findViewById(R.id.geoMagRotVecX);
        mGeoMagRotVecYTextView = findViewById(R.id.geoMagRotVecY);
        mGeoMagRotVecZTextView = findViewById(R.id.geoMagRotVecZ);

        mMagFieldXTextView = findViewById(R.id.magFieldX);
        mMagFieldYTextView = findViewById(R.id.magFieldY);
        mMagFieldZTextView = findViewById(R.id.magFieldZ);

        mLightTextView = findViewById(R.id.lightText);
        mPressureTextView = findViewById(R.id.pressure);

        //ipAddressEditText = findViewById(R.id.ipAddressEditText);
        //portEditText = findViewById(R.id.portEditText);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mDeltaX = (float) ((double) Math.round(sensorEvent.values[0] * decimalPlace) / decimalPlace);
            mDeltaY = (float) ((double) Math.round(sensorEvent.values[1] * decimalPlace) / decimalPlace);
            mDeltaZ = (float) ((double) Math.round(sensorEvent.values[2] * decimalPlace) / decimalPlace);

            mCurrentX.setText(Float.toString(mDeltaX));
            mCurrentY.setText(Float.toString(mDeltaY));
            mCurrentZ.setText(Float.toString(mDeltaZ));
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_GRAVITY) {
            mGravityXTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[0] * decimalPlace) / decimalPlace));
            mGravityYTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[1] * decimalPlace) / decimalPlace));
            mGravityZTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[2] * decimalPlace) / decimalPlace));
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            mGyroXTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[0] * decimalPlace) / decimalPlace));
            mGyroYTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[1] * decimalPlace) / decimalPlace));
            mGyroZTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[2] * decimalPlace) / decimalPlace));
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            mLinearAccelXTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[0] * decimalPlace) / decimalPlace));
            mLinearAccelYTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[1] * decimalPlace) / decimalPlace));
            mLinearAccelZTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[2] * decimalPlace) / decimalPlace));
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            mRotVectorXTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[0] * decimalPlace) / decimalPlace));
            mRotVectorYTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[1] * decimalPlace) / decimalPlace));
            mRotVectorZTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[2] * decimalPlace) / decimalPlace));
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
            mGameRotVecXTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[0] * decimalPlace) / decimalPlace));
            mGameRotVecYTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[1] * decimalPlace) / decimalPlace));
            mGameRotVecZTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[2] * decimalPlace) / decimalPlace));
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {
            mGeoMagRotVecXTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[0] * decimalPlace) / decimalPlace));
            mGeoMagRotVecYTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[1] * decimalPlace) / decimalPlace));
            mGeoMagRotVecZTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[2] * decimalPlace) / decimalPlace));
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mMagFieldXTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[0] * decimalPlace) / decimalPlace));
            mMagFieldYTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[1] * decimalPlace) / decimalPlace));
            mMagFieldZTextView.setText(String.valueOf((double) Math.round(sensorEvent.values[2] * decimalPlace) / decimalPlace));
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            mLightTextView.setText(String.valueOf(sensorEvent.values[0]));

        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
            mPressureTextView.setText(String.valueOf(sensorEvent.values[0]));

        }

        //if (mDeltaX >  mVibrateThreshold || mDeltaY > mVibrateThreshold || mDeltaZ > mVibrateThreshold) {
        //vibrator.vibrate(VibrationEffect(50));
        //}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
/*
    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {

            //we create a TCPClient object
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mTcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test", "response " + values[0]);
            //process server response here....

        }

    }
 */
}
