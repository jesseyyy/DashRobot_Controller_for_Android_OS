package com.cuny.android.dashrobot;

import java.util.HashMap;

public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();


    public static String HEART_RATE_MEASUREMENT = "713D0003-503E-4C75-BA94-3148F18D941E";//"00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    
    public static String CLIENT_CHARCONFIG = "00002902-0000-1000-8000-00805f9b34fb";
	public static String HEART_MEASUREMENT = "713D0003-503E-4C75-BA94-3148F18D941E";//"00002a37-0000-1000-8000-00805f9b34fb";
    
    public static String ServiceUUIDString = "713D0000-503E-4C75-BA94-3148F18D941E";// Service UUID
    public static String CharactUUIDString = "713D0001-503E-4C75-BA94-3148F18D941E";// First read characteristic
    public static String NotifyCUUIDString = "713D0002-503E-4C75-BA94-3148F18D941E";// Notify characteristic
    public static String WriteWiUUIDString = "713D0003-503E-4C75-BA94-3148F18D941E"; //// Write w/o Response Characteristic


	public static String uuidStringTest1 = "00001800-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest2 = "00002a00-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest3 = "00002a01-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest4 = "00002a02-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest5 = "00002a03-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest6 = "00002a04-0000-1000-8000-00805f9b34fb";


	public static String uuidStringTest7 = "00001801-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest8 = "00002a05-0000-1000-8000-00805f9b34fb";

	public static String uuidStringTest9 = "0000180a-0000-1000-8000-00805f9b34fb";

	public static String uuidStringTest10 = "00002a23-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest11 = "00002a24-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest12 = "00002a25-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest13 = "00002a26-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest14 = "00002a27-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest15 = "00002a28-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest16 = "00002a29-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest17 = "00002a2a-0000-1000-8000-00805f9b34fb";
	public static String uuidStringTest18 = "00002a50-0000-1000-8000-00805f9b34fb";


	public static String uuidStringTest19 = "713d0000-503e-4c75-ba94-3148f18d941e";
	public static String uuidStringTest20 = "713d0002-503e-4c75-ba94-3148f18d941e";
	public static String uuidStringTest21 = "713d0003-503e-4c75-ba94-3148f18d941e";
	public static String uuidStringTest22 = "713d0004-503e-4c75-ba94-3148f18d941e";
	public static String uuidStringTest23 = "713d0005-503e-4c75-ba94-3148f18d941e";
	
	public static String[] uuidStringTest = {
			"00001800-0000-1000-8000-00805f9b34fb",
			"00002a00-0000-1000-8000-00805f9b34fb",
			"00002a01-0000-1000-8000-00805f9b34fb",
			"00002a02-0000-1000-8000-00805f9b34fb",
			"00002a03-0000-1000-8000-00805f9b34fb",
			"00002a04-0000-1000-8000-00805f9b34fb",
			"00001801-0000-1000-8000-00805f9b34fb",
			"00002a05-0000-1000-8000-00805f9b34fb",
			"0000180a-0000-1000-8000-00805f9b34fb",
			"00002a23-0000-1000-8000-00805f9b34fb",
			"00002a24-0000-1000-8000-00805f9b34fb",
			"00002a25-0000-1000-8000-00805f9b34fb",
			"00002a26-0000-1000-8000-00805f9b34fb",
			"00002a27-0000-1000-8000-00805f9b34fb",
			"00002a28-0000-1000-8000-00805f9b34fb",
			"00002a29-0000-1000-8000-00805f9b34fb",
			"00002a2a-0000-1000-8000-00805f9b34fb",
			"00002a50-0000-1000-8000-00805f9b34fb",
			"713d0000-503e-4c75-ba94-3148f18d941e",
			"713d0002-503e-4c75-ba94-3148f18d941e",
			"713d0003-503e-4c75-ba94-3148f18d941e",
			"713d0004-503e-4c75-ba94-3148f18d941e",
			"713d0005-503e-4c75-ba94-3148f18d941e" };

    static {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        // Sample Characteristics.
        attributes.put(HEART_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
