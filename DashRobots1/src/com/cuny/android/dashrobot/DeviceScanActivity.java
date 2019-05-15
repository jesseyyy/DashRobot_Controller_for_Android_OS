package com.cuny.android.dashrobot;
/**
 * Sept.30 add scene configure: sunlightMode(outdoor), shadowMode(outdoor), CloudyMode(outdoor) and  indoorLightMode
 * Sept.25 expand the ranges of color values
 * Sept 24: new steps: 
 * 			check buttom
 * 			if no error , go to operationlist buttom and then solve button
 * 			if has error, update with (ULDFBR), and then click correct button, then go back operationlist button and go to solve button. it is different with **EasyMode2!! 
 * Sept. 15.copy the color value and whatColorIsIt of SolvingRubikCube3x3x3ThistlethwaitePlasteFaster.
		 No Zoom mode is much harder to recognize color than Zoom mode. so go back the Zoom mode.
 * Sept. 14. Debugging: kill activity will help to calculate the :the real reason is recognizing color wrong!!!!
 * Spet. 12. there are new issues on bluetooth. BT connections will stop when go back cross different activities. I thought three ways to try:
 * 			a) connect when I need to use. so I read three remote control projects, they have disconnect and connect buttons, so get the issue solved : NXTTalker.jave is changed
 * 			b) android have camera app mode. I can not take pictures continuously.
 * 			c) use intent of camera, but not intent of ReadSurfaceColor.java. Because a) is good enough to solve, so I do not put more effort in c)
 * Aug. 29. First change the sleep time from 1500ms to 1000ms. this cut 8-9 minutes 5-6 minutes. 
 * 			And then add functions to save movement:  UnFn()[12/8],	UnBn()[12/8],	UnRn()[10/6], FnUn()[12/8], FnBn()[14/8], FnRn()[12/8], 
 * 			BnUn()[12/8], LnUn[10/6], LnFn[12/8], LnBn[12/8], LnRn[10/6]. for example, UnFn()[12/8] replace Un() and Fn(), saving 4 movements from 12.
 * 			the new running time is 5-6 minutes.
 * 
 *  
 * Aug 25. If the color is recognized correctly, this program will solve the Rubik Cube. 
 * 		   may add some error check to recognize color.
 * 
 * on Aug. 3, pulling  and pushing  degree is 0x60(96 degree), may add 2-4 degrees. now choose 0x63 degree
 * 			motor B is OK , but power is 0x32 (50%), degree 0x147 (327degree), adjust degree is 0x38(56degree)
 * 			almost solve the cube. 5 times error in pulling and pushing.
 * 
 * on Aug. 3, before step 3, can click display to check all six sides are right.
 * on July 29, plugging Lejos into Eclipse
 * On July 28, 2014
 * now this is copy can recognize the color in 10am-5pm fine day. and return, and start solve the cube.
 * 
 * step 1: click ToStart button, and take 6 pictures of cube, following the order : top , right , bottom , left , back and front. 
 * step 2: just before save the picture of front, turn on NXT and put the arm of NXT in right position
 * step 3: click solveRubik button, the NXT will start to solve the cube by itself.
 * 
 *  Issue A: it will take more than 30 minutes to solve. 
 *  Issue B: not sure the recognizance will all correct.
 *  Issue C: the accurateness of motor will stop the solve. the motor will not roll the accurate degrees.
 *  Issue D : need  to take pictures manually. 
 * 
 * this is a copy of "copy of solvingRbuikCUbe3x3x3"
 * July 23. can see a red cross in the top, sounds like it works, but can not solve all because of accurateness and algorithms(waste lots move) 
 * The accurateness is not good. Here leave the accurateness issue unsolved.
 * 
 * there are too many steps to finish, (average number is 1600 steps. so we decide to change the method
 * this can start an intent(ReadSurfaceColor.java) to take pictures and return the result of color back to
 *  MainActivity
 *  
 *  next is making accurate color and making NXT move
 *  the last is read information of nxt motor
 */
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Set;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Application;
import android.app.Activity;
import android.app.ListActivity;




import android.view.LayoutInflater;
import android.widget.ListView;


public class DeviceScanActivity extends ListActivity implements OnClickListener, OnSharedPreferenceChangeListener{
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;
 
    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
	
	static boolean redColor  	= false;
	static boolean yellowColor 	= false;
	static boolean blueColor  	= false;
	static boolean greenColor 	= false;
	static boolean whiteColor 	= false;
	private static final int REQUEST_SETTINGS = 3;
    
	Button bttnRedEye;
	Button bttnYellowEye;		
	Button bttnBlueEye;	
	Button bttnGreenEye;
	Button bttnWhiteEye;
	
	ImageButton bttnUpArrow;
	ImageButton bttnDownArrow;
	ImageButton bttnRightArrow;
	ImageButton bttnLeftArrow;
	ImageButton bttnTurnOffEye;	
	ImageButton bttnEyeBig;	
	ImageButton bttnAutoDance;
	ImageButton bttnAutoFigure8;
	ImageButton bttnAutoCircle;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_ble);

        bttnUpArrow		=(ImageButton) findViewById(R.id.imageButtonUpArrow);
        bttnDownArrow	=(ImageButton) findViewById(R.id.imageButtonDownArrow);
        bttnRightArrow	=(ImageButton) findViewById(R.id.imageButtonRightArrow);
        bttnLeftArrow 	=(ImageButton) findViewById(R.id.imageButtonLeftArrow);
        bttnAutoCircle	=(ImageButton) findViewById(R.id.imageButtonAutocircle);
        bttnAutoFigure8 =(ImageButton) findViewById(R.id.imageButtonAutofigure8);
        bttnAutoDance 	=(ImageButton) findViewById(R.id.imageButtonAutodance);
        bttnTurnOffEye	=(ImageButton) findViewById(R.id.imageButtonEyeBigoff);
        bttnEyeBig		=(ImageButton) findViewById(R.id.imageButtonEyeBig);

    	bttnRedEye		=(Button) findViewById(R.id.buttonred);
    	bttnYellowEye	=(Button) findViewById(R.id.buttonyellow);
    	bttnBlueEye		=(Button) findViewById(R.id.buttonblue);
    	bttnGreenEye  	=(Button) findViewById(R.id.buttongreen);    
    	bttnWhiteEye	=(Button) findViewById(R.id.buttonwhite); 
		
    	bttnUpArrow.setOnClickListener(this);
    	bttnDownArrow.setOnClickListener(this);
    	bttnRightArrow.setOnClickListener(this);
    	bttnLeftArrow.setOnClickListener(this);
    	bttnAutoCircle.setOnClickListener(this);
    	bttnAutoFigure8.setOnClickListener(this);
    	bttnAutoDance.setOnClickListener(this);
    	bttnTurnOffEye.setOnClickListener(this);
    	bttnEyeBig.setOnClickListener(this);
    	bttnRedEye.setOnClickListener(this);
    	bttnYellowEye.setOnClickListener(this);
    	bttnBlueEye.setOnClickListener(this);
    	bttnGreenEye.setOnClickListener(this);
    	bttnWhiteEye.setOnClickListener(this);
		
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        readPreferences(prefs, null);
        prefs.registerOnSharedPreferenceChangeListener(this);
        
        getActionBar().setTitle(R.string.title_devices);//Confuse about "above api ver 18"
        mHandler = new Handler();
 
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
 
        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter(); //Confuse about "above api ver 18"
 
        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }
 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_main, menu);
       /* if (!mScanning) {
            menu.findItem(R.id.imageButtonEyeBig).setVisible(false);
            menu.findItem(R.id.imageButtonEyeBigoff).setVisible(true);
            menu.findItem(R.id.imageButtonUpArrow).setActionView(null);
        } else {
            menu.findItem(R.id.imageButtonRightArrow).setVisible(true);
            menu.findItem(R.id.imageButtonLeftArrow).setVisible(false);
            menu.findItem(R.id.imageButtonEyeBigoff).setActionView( null);
        }*/
		return super.onCreateOptionsMenu(menu);
	}

    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

        switch (item.getItemId()) {
        case R.id.menu_settings:
            Intent i = new Intent(this, SettingsActivity.class);
            startActivityForResult(i, REQUEST_SETTINGS);
            break;
/*        case R.id.imageButtonAutocircle:
            mLeDeviceListAdapter.clear();
            scanLeDevice(true);
            break;
        case R.id.imageButtonAutofigure8:
            scanLeDevice(false);
            break;*/
        default:
            return false;    
        }
//		return super.onOptionsItemSelected(item);
        return true;
	}
    @Override
    protected void onResume() {
        super.onResume();
 
        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
 
        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter();
//        setListAdapter(mLeDeviceListAdapter);
        scanLeDevice(true);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
 //       scanLeDevice(false);
        mLeDeviceListAdapter.clear();
    }
/*	private void testImageButton() {
		// TODO Auto-generated method stub
		ImageButton btn = (ImageButton) findViewById(R.id.imageButtonEyeBigoff);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "click btn", Toast.LENGTH_SHORT).show();
				ImageButton btn = (ImageButton) findViewById(R.id.imageButtonEyeBigoff);
				btn.setImageResource(R.drawable.eyebig);
			}
		});
	}*/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageButtonAutocircle:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			
			////////////copied from 
			int position = 4;
	        final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
	        if (device == null) return;
	        final Intent intent = new Intent(this, DeviceControlActivity.class);
	        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
	        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
	        if (mScanning) {
	            mBluetoothAdapter.stopLeScan(mLeScanCallback);
	            mScanning = false;
	        }
	        startActivity(intent);
			
			////////////////
			break;
		case R.id.imageButtonAutodance:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			break;
		case R.id.imageButtonAutofigure8:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			break;
		case R.id.imageButtonEyeBig:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			ImageButton btnCurrent1 = (ImageButton) findViewById(R.id.imageButtonEyeBig);
			btnCurrent1.setImageResource(R.drawable.eyebigoff);
  			break;
		case R.id.imageButtonEyeBigoff:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			ImageButton btnCurrent2 = (ImageButton) findViewById(R.id.imageButtonEyeBigoff);
			btnCurrent2.setImageResource(R.drawable.eyebig);
   			break;
		case R.id.imageButtonUpArrow:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
//			R.id.imageButtonUpArrow.setVisible(true);
//            menu.findItem(R.id.imageButtonLeftArrow).setVisible(false);
			break;
		case R.id.imageButtonDownArrow:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			break;
		case R.id.imageButtonLeftArrow:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			break;
		case R.id.imageButtonRightArrow:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			break;
		case R.id.buttonred:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			break;
		case R.id.buttongreen:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			break;
		case R.id.buttonblue:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			break;
		case R.id.buttonyellow:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			break;
		case R.id.buttonwhite:
			Toast.makeText(DeviceScanActivity.this, "click btn", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	 
/*    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
        if (device == null) return;
        final Intent intent = new Intent(this, DeviceControlActivity.class);
        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
        if (mScanning) {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mScanning = false;
        }
        startActivity(intent);
    }
 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    





	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		readPreferences(sharedPreferences, key);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	///////////
    private void readPreferences(SharedPreferences prefs, String key) {
		if (key == null) {
			redColor	= prefs.getBoolean("redColor", false);
			yellowColor 		= prefs.getBoolean("yellowColor", false);
			blueColor 	= prefs.getBoolean("blueColor", false);
			greenColor 	= prefs.getBoolean("greenColor", false);
			whiteColor 	= prefs.getBoolean("whiteColor", false);
        } else if (key.equals("sunlightMode")) {
        	redColor = prefs.getBoolean("redColor", false);
        } else if (key.equals("shadowMode")) {
        	yellowColor 	= prefs.getBoolean("yellowColor", false);
        } else if (key.equals("indoorLightMode")) {
        	blueColor 	= prefs.getBoolean("blueColor", false);
        } else if (key.equals("cloudyMode")) {
        	greenColor 	= prefs.getBoolean("greenColor", false);
        } else if (key.equals("cloudyMode")) {
        	whiteColor 	= prefs.getBoolean("whiteColor", false);
        }
    }
	///////////////
    
    // Adapter for holding devices found through scanning.
    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;
 
        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = DeviceScanActivity.this.getLayoutInflater();
        }
 
        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }
 
        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }
 
        public void clear() {
            mLeDevices.clear();
        }
 
        @Override
        public int getCount() {
            return mLeDevices.size();
        }
 
        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }
 
        @Override
        public long getItemId(int i) {
            return i;
        }
 
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.device_list, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
 
            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.deviceName.setText(deviceName);
            else
                viewHolder.deviceName.setText(R.string.unknown_device);
            viewHolder.deviceAddress.setText(device.getAddress());
 
            return view;
        }
    }
    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }
    
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);
 
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }
    
    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
 
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLeDeviceListAdapter.addDevice(device);
                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }
    };
}
