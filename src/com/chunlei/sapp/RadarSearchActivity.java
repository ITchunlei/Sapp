package com.chunlei.sapp;

import java.util.ArrayList;
import java.util.List;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class RadarSearchActivity extends BaseActivity {
	private Button mExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_radar_search);
		initUI();
		initData();
		super.onCreate(savedInstanceState);
	}

	
	@Override
	protected void initUI() {
		mExit = (Button) findViewById(R.id.btn_exit);
		mExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
	}

	
	private static final int[] GSM_TYPES = {TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE,
		TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_UMTS};
	
	private static final int[] CDMA_TYPES = {TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT,
		TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A};
	
	
	private List<CellIDInfo> getCellCDMAList(TelephonyManager manager) {
		List<CellIDInfo> list = new ArrayList<CellIDInfo>();
		CellIDInfo cellIDInfo = new CellIDInfo();
		
		CdmaCellLocation cdma = (CdmaCellLocation) manager.getCellLocation();
		if (cdma == null) {
			return null;
		}
		
		int lac = cdma.getNetworkId();
		int mcc = Integer.parseInt(manager.getNetworkOperator().substring(0, 3));
		int mnc = Integer.parseInt(String.valueOf(cdma.getSystemId()));
		int cid = cdma.getBaseStationId();
		
		cellIDInfo.CID = cid;
		cellIDInfo.MCC = mcc;
		cellIDInfo.MNC = mnc;
		cellIDInfo.LAC = lac;
		cellIDInfo.TYPE = "cdma";
		
		list.add(cellIDInfo);
		
		List<NeighboringCellInfo> nList = manager.getNeighboringCellInfo();
		int size = nList.size();
		for (int i = 0; i < size; i ++) {
			CellIDInfo info = new CellIDInfo();
			info.CID = nList.get(i).getCid();
			info.MCC = mcc;
			info.MNC = mnc;
			info.LAC = lac;
			list.add(info);
		}
		
		return list;
		
	}
	
	
	
	private List<CellIDInfo> getCellGsmList(TelephonyManager manager) {
		List<CellIDInfo> list = new ArrayList<CellIDInfo>();
		CellIDInfo cellIDInfo = new CellIDInfo();
		GsmCellLocation gsm = (GsmCellLocation) manager.getCellLocation();
		
		if (gsm == null) {
			return null;
		}
		
		int lac = gsm.getLac();
		int mcc = Integer.parseInt(manager.getNetworkOperator().substring(0, 3));
		int mnc = Integer.parseInt(manager.getNetworkOperator().substring(3, 5));
		int cid = gsm.getCid();
		
		cellIDInfo.CID = cid;
		cellIDInfo.MCC = mcc;
		cellIDInfo.MNC = mnc;
		cellIDInfo.LAC = lac;
		cellIDInfo.TYPE = "gsm";
		list.add(cellIDInfo);
		
		List<NeighboringCellInfo> nList = manager.getNeighboringCellInfo();
		int size = list.size();
		for (int i = 0; i < size; i ++) {
			CellIDInfo info = new CellIDInfo();
			info.CID = nList.get(i).getCid();
			info.MCC = mcc;
			info.MNC = mnc;
			info.LAC = lac;
			list.add(info);
		}
		
		return null;
	}
	
	
	
	@Override
	protected void initData() {
		TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		List<CellIDInfo> cellIDInfoList = null;
		
		int type = manager.getNetworkType();
		
	//	int phoneType = manager.getPhoneType();
		
		
		boolean flag = false;
		
		for (int t : GSM_TYPES) {
			if (type == t) {
				cellIDInfoList = getCellGsmList(manager);
				flag = true;
				break;
			}
		}
		
		if (!flag) {
			for (int t : CDMA_TYPES) {
				if (type == t) {
					cellIDInfoList = getCellCDMAList(manager);
					flag = true;
					break;
				}
			}
		}
		
		if (!flag) {
			cellIDInfoList = getCellGsmList(manager);
			if (cellIDInfoList == null || cellIDInfoList.isEmpty()) {
				cellIDInfoList = getCellCDMAList(manager);
			}
		}
		
		
		if (cellIDInfoList == null || cellIDInfoList.size() == 0) {
			//TODO:
		}
		
//		DefaultHttpClient client = new DefaultHttpClient();
//		HttpPost post = new Htt
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//	
//		
//		
//		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//			Toast.makeText(this, "enabled!!!", Toast.LENGTH_LONG).show();
//		} else {
//			Toast.makeText(this, "not enabled!!!", Toast.LENGTH_LONG).show();
//		}
//		
		
//		Criteria criteria = new Criteria();
//		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//		criteria.setPowerRequirement(Criteria.POWER_LOW);
//		criteria.setAltitudeRequired(false);
//		criteria.setBearingRequired(false);
//		criteria.setSpeedRequired(false);
//		criteria.setCostAllowed(true);
		
//		
//		String provider = LocationManager.NETWORK_PROVIDER;
//
//		
//		
//		
//		locationManager.requestLocationUpdates(provider, 1000, 0, new LocationListener() {
//
//			@Override
//			public void onLocationChanged(Location location) {
//				System.out.println("location: " + location);
//			}
//
//			@Override
//			public void onStatusChanged(String provider, int status,
//					Bundle extras) {
//				
//			}
//
//			@Override
//			public void onProviderEnabled(String provider) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onProviderDisabled(String provider) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
//		
//		Location location = locationManager.getLastKnownLocation(provider);
//		
//		if (location != null) {
//			Toast.makeText(this, "location-x:" + location.getLatitude() + "location-y:" + location.getLongitude(), Toast.LENGTH_LONG).show();
//		} else {
//			Toast.makeText(this, "null..", Toast.LENGTH_LONG).show();
//		}
		
		
	}

}


class CellIDInfo {
	public int MCC;
	public int MNC;
	public int LAC;
	public int CID;
	public String TYPE;
}
