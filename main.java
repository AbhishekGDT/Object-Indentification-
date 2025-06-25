package in.greendzine.www.gdt_picker;







import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.Locale;
import android.content.Context;

import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;
import org.vosk.android.RecognitionListener;
import org.vosk.android.SpeechService;
import org.vosk.android.StorageService;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



//=======================================================================



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.annotation.TargetApi;
import android.os.Build;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.provider.MediaStore;
import android.widget.ToggleButton;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.Manifest.permission.CAMERA;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


import static in.greendzine.www.gdt_picker.GDT_GlobalData.API_KEY;
import static in.greendzine.www.gdt_picker.GDT_GlobalData.API_KEY_NAME;
import static in.greendzine.www.gdt_picker.GDT_GlobalData.DIAG_PHP;
import static in.greendzine.www.gdt_picker.GDT_GlobalData.HB_PHP;
//import static GDT_GlobalData.DIAG_PHP;
//import static GDT_GlobalData.HB_PHP;
import static in.greendzine.www.gdt_picker.GDT_GlobalData.OLDVINNUMBER;
import static in.greendzine.www.gdt_picker.GDT_GlobalData.PICKER_USER;
import static in.greendzine.www.gdt_picker.GDT_GlobalData.TESTVINNUMBER;
import static in.greendzine.www.gdt_picker.GDT_GlobalData.URL_REALTIME_MOPTRO;
import static in.greendzine.www.gdt_picker.GDT_GlobalData.monthList;
import static in.greendzine.www.gdt_picker.GDT_GlobalData.runningNumber;
//import static GDT_GlobalData.URL_REALTIME_MOPTRO;




public class GDT_Main extends Activity implements View.OnClickListener{
    /* Debugging */
    private static final String TAG = "GDT_Main";
    public static final String TOAST = "toast";

    public static Values values = new Values();
    public static String JSONRequest = "";
    public static String JSONResponse = "";
    public static String g_strHbJsonFromMCB = "";
    public static String g_strHbJsonFromServer = "";
    public static String g_strDiagJsonFromMCB = "";
    public static String g_strDiagJsonFromServer = "";
    public static String g_strAddVinToServer = "";
    public static String g_strDummyBcbdata = ",\"f\":\"BATLIO-2021070002\",\"g\":\"0028000100020003000405.0106.0207.0308.04\",\"h\":\"00100009000A000B\"}";

    //EARLIER IT WAS 451 FOR HEART BEAT AND DIAGNOSTICS IT WAS 317
    private static final long HB_JSON_LEN = 546;
    private static final long HB_JSON_LEN1 = 545;
    //new heartbeat length Len2 is added for PI_2023_09
    private static final long HB_JSON_LEN2 = 615;
    private static final long HB_JSON_LEN3 = 451;
    private static final long DIAG_JSON_LEN = 396;
    private static final long DIAG_JSON_LEN1 = 395;
    private static final long DIAG_JSON_LEN2 = 402;
    private static final long DIAG_JSON_LEN3 = 317;
    // ADDING THE DATA CHECK FOR DIAGNOSTICS DUE TO CHANGE IN THE PROTOCOL LENGTH IN MOPTRO LITE
    // THIS CHECK HAS TO BE REMOVED ONCE THE BCB DATA IS IMPLEMENTED IN MOPTRO LITE FIRMWARE
    private static final long DIAG_JSON_LEN4 = 308;

    //new diag length LEN3 is added for PI_2-23_09
    private static final long DIAG_JSON_LEN5 = 502;

    //new diag length LEN3 is added for PI_2024_02
    private static final long DIAG_JSON_LEN6 = 518;
    private static final long SERVER_HB_RES_JSON_LEN = 255;
    //BELOW HAS TO BE CHANGED BASED ON THE PROCTOCAL CHANGES IN FUTURE FOR BARCODE SCANNING
    private static final long BARCODE_JSON_LEN = 65543;

    public boolean wifiConnected = false;

    /* Application Configurations */
    private static final long STS_SCREEN_UPDATE_TICK_TIME = 2000;
    private static final long HB_DIAG_MISSING_UPDATE_TICK_TIME = (25 * 1000);
    private static final long BT_RECONNECT_TICK_TIME = 5000;
    private static final long BT_TX_TICK_TIME = 3000;
    private static final long BT_RECONNECT_TOUT = 15000;
    private static final long CRATE_MISSING_CHECK_TICK_TIME = 1300;
    private static final long SERVER_POST_ON_ORDER_LIST_TICK_TIME = 3000;
    private static final long SERVER_POST_CHECK_TICK_TIME = 1000;
    private static final long CRATE_MISSING_ALLOWANCE_TIME_SEC_CHANGE_NEW_CRATE = 40;
    private static final long CRATE_MISSING_ALLOWANCE_TIME_SEC = 10;

    private static final long ADD_HB_TO_SERVER_TICK_TIME = 30000;
    private static final long ADD_DIAG_TO_SERVER_TICK_TIME = 1000;

    private static final boolean TEST_BT_RESUME_STOP = false;

    /* Flash data configuration */
    public static final String GDT_SETTINGS = "settings";
    public static final String GDT_ON_GOING_ORDER_JSON_STRING = "ongoingOrderJsonString";
    //Service Settings
    public static final String MOPTRO_NAME = "moptroName";
    public static final String MOPTRO_NUMBER = "moptroNumber";
    public static final String EFP_DO1_TIME = "do1Time";
    public static final String EFP_DO2_TIME = "do2Time";
    public static final String EFP_DC1_TIME = "dc1Time";
    public static final String EFP_TEST_CYCLES = "efpTestCycles";
    public static final String ONGOING_ORDER_JSON = "ongoingOrderJson";
    //Odo Values to be stored
    public static final String ODO_KM = "odoKilometer";
    public static final String ODO_METER = "odoMeter";
    // Login Details
    public static final String PICKER_CREDENTIALS = "pickerCredentials";

    public static final String MOPTRO_NAME_DEFAULT = "MOPNEW-YYYYMM0001";
    public static final String MOPTRO_NUMBER_RESET = "000000000";
    public static final String MOPTRO_NUMBER_DEFAULT = "201600000";
    public static final String DEMO_ADD_SKU_DETAILS = "demoaddedskudetails";

    public static final String UPDATED_COMMANDS = "commands";
    public static final byte SETTINGS_MOPTRO_NUMBER_LENGTH = 17;
    public static final byte SETTINGS_MOPTRO_NAME_MIN_LENGTH = 3;
    public static final int SETTINGS_EFP_MIN_DOOR_TIME = 800;
    public static final int SETTINGS_EFP_MIN_DO1_TIME = 1000;
    public static final int SETTINGS_EFP_MIN_DO2_TIME = 2100;
    public static final int SETTINGS_EFP_MIN_DC1_TIME = 1000;
    public static final int SETTINGS_EFP_MIN_AUTO_TEST_CYCLES = 200;
    public static final int SETTINGS_MAX_ITEMS_PER_CRATE = 17;
    public static final int MINIMUM_BATTERY_PERCENT = 20;
    public static final int MAXIMUM_BATTERY_PERCENT = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1; // Define REQUEST_IMAGE_CAPTURE here

    private static final int REQUEST_CAMERA_PERMISSION = 100; // Define REQUEST_CAMERA_PERMISSION
    //Admin Settings
    public static final String ALGO_SELECTED = "algoSelected";
    public static final String SERVER_SELECTED = "serverSelected";
    public static final String SERVER_TYPED = "serverTyped";
    public static final String LOCAL_USER = "localUser";
    public static final String LOCAL_PASSWORD = "localPassword";
    public static final String MAX_ITEMS_IN_CRATE = "maxItemsPerCrate";
    public static final String IMAGE_ENABLED = "imageEnable";
    public static final String ENABLE_FAKE_SCAN = "fakeScan";
    public static final String ENABLE_BATTERY_DOOR_ALERT = "showbattrydooralert";
    public static final String ENABLE_EFP = "efpEnable";
    public static final String ENABLE_DOOR_SENSOR = "enableDoorSensor";
    public static final String ENABLE_CRATE_SENSOR = "enableCrateSensor";
    public static final String LOGIN_REQUIRED = "loginrequired";
    // Demo Admin Settings
    public static final String ENABLE_STEP_LOGIN = "steplogin";
    public static final String ENABLE_STEP_ORDER_PICK = "steporderpick";
    public static final String ENABLE_STEP_SCAN_CRATE = "stepscancrate";
    public static final String ENABLE_STEP_SCAN_ORDER_PICK = "stepscanorderpick";
    public static final String ENABLE_STEP_PICK_SKU = "steppicksku";
    public static final String ENABLE_STEP_SKU_LOCATION = "stepskulocation";
    //Application Strings for Display
    public static final String SCAN_EMPTY_CRATE = "SCAN AN EMPTY CRATE";
    public static final String SCAN_ORDER_APPEND = "SCAN ORDER# ";
    public static final String SCAN_SKU = "SCAN BELOW SKU";
    public static final String SCAN_SKU_LOACTION = "SCAN SKU LOCATION";
    public static final String NO_CRATE_SCANNED = "-CRATE RFID BARCODE-";
    public static final String NO_ORDER_SCANNED = "-ORDER LABEL BARCODE-";
    public static final String SCAN_USED_CRATE_APPEND = "SCAN CRATE. ODR#";
    public  AlertDialog batteryDoorDialog, wrongScanDialog, vinUpdateDialog;

    /* Server Selection Constants */
    static final int SERVER_GD_TEST = 0;
    static final int SERVER_GD_LIVE = 1;
    static final int SERVER_WH_DC_TEST = 2;
    static final int SERVER_WH_DC_LIVE = 3;
    static final int SERVER_TYPED_IP = 4;

    /* Algorithm Selection Constants */
    static final int ALGO_PAKA_BASIC = 0;
    static final int ALGO_PAKA_ADVANCED = 1;
    static final int ALGO_SEQUENTIAL = 2;

    /* Login Status */
    static final byte LOGIN_NONE = 0;
    static final byte LOGIN_USER = 1;
    static final byte LOGIN_ADMIN = 2;
    static final byte LOGIN_SERVICE = 3;
    static final byte LOGIN_MOPTRO = 4;
    static final byte LOGIN_MAX = 5;
    // NINTH BIT OF STS0
    static final int DOOR_OPEN = 0X0400;

    /* Constant defines for Application control */
    /* App Status */
    public enum enumAppStatus {
        APP_STS_MIN,
        APP_STS_LOGIN,
        APP_STS_ADMIN_SCREEN,
        APP_STS_SERVICE_SCREEN,
        APP_STS_MOPTRO_SCREEN,
        APP_STS_SERVICE_SETTINGS_SCREEN,
        APP_STS_SERVICE_SETTINGS_SCREEN_UPDATE,
        APP_STS_CALIBRATE_EPF,
        APP_STS_EFP_CYCLE_TEST,
        APP_STS_SERVER_LOGIN_CHECK,
        APP_STS_SERVER_ORDER_DOWNLOAD,
        APP_STS_SERVER_HOLD_ORDER_LIST_DOWNLOAD,
        APP_STS_SERVER_HOLD_ORDER_DETAILS_DOWNLOAD,
        APP_STS_SERVER_ORDER_DOWNLOAD_FAILED,
        APP_STS_ORDER_LIST,
        APP_STS_HOLD_ORDER_DETAILS,
        APP_STS_SCAN_CRATE,
        APP_STS_SERVER_CRATE_CHECK,
        APP_STS_SCAN_CRATE_WAITING_SYS_READY,
        APP_STS_SCAN_CRATE_WAITING_SCANNER_ACTIVE,
        APP_STS_SCAN_ORDER,
        APP_STS_SCAN_ORDER_WAITING_SYS_READY,
        APP_STS_SCAN_ORDER_WAITING_SCANNER_ACTIVE,
        APP_STS_SCAN_SKU,
        APP_STS_SCAN_SKU_WAITING_SYS_READY,
        APP_STS_SCAN_SKU_WAITING_SCANNER_ACTIVE,
        APP_STS_RESET_PCB_REQUEST,
        APP_STS_DEMO_ADMIN,
        APP_STS_ADD_SKU_DEMO,
        APP_STS_ADD_ORDER,
        APP_STS_ADD_SCAN_SKU_LOCATION,
        APP_STS_STATUS,
        APP_STS_VOICE_HELLO,
    }

    /* Order Status Constants */
    static final int ORDER_STS_NONE = 0;
    static final int ORDER_STS_NOT_STARTED = 1;
    static final int ORDER_STS_RESUMED_NEW_CRATE = 2;
    static final int ORDER_STS_RESUMED_USED_CRATE = 3;
    static final int ORDER_STS_IN_PROGRESS = 4;
    static final int ORDER_STS_HOLD_DOWNLOAD_PENDING = 5;
    static final int ORDER_STS_HOLD_DOWNLOADING = 6;
    static final int ORDER_STS_HOLD_ERR_ALREADY_POSTED = 7;
    static final int ORDER_STS_HOLD_POST_PENDING = 8;
    static final int ORDER_STS_HOLD_POSTING = 9;
    static final int ORDER_STS_HOLD = 10;
    static final int ORDER_STS_CLOSED_POST_PENDING = 11;
    static final int ORDER_STS_CLOSED_POSTING = 12;
    static final int ORDER_STS_CLOSED = 13;
    static final int ORDER_STS_ERROR = 14;

    /* Admin Settings Variables */
    public static Integer g_iUserOption = -1;
    public static String g_strMoptroName = "";
    public static String g_strMoptroNumber = "";
    public static String g_strDO1Time = "";
    public static String g_strDO2Time = "";
    public static String g_strDC1Time = "";
    public static String g_strEfpTestCycles = "";
    public static String g_strOnGoingOrderJson = "";
    public static String g_strAddSkudetails = "";
    public static String g_strUserDetails = "";
    public static long g_iOdoKm = 0;
    public static long g_iOdoMeter = 0;
    public static int g_iMaxItemsPerCrate = 0;
    public static int g_iServerSelected = 0;
    public static int g_iAlgoSelected = 0;
    public static int g_iMobilepercent = 0;
    public static String g_strTypedIP = "";
    public static String g_strLocalUser = "";
    public static String g_strLocalPassword = "";
    public static boolean g_bEnableSkuPic = false;
    public static boolean g_bEnableEfp = false;
    public static boolean g_bEnableDoorSensor = false;
    public static boolean g_bEnableCrateSensor = false;
    public static boolean g_bEnableFakeScan = false;
    public static boolean g_bEnableBatteryAlert = false;
    public static boolean g_bEnableDirGuide = false;
    public static boolean g_bRefreshOrder = false;
    public static boolean g_bRefreshSku = false;
    public static boolean g_bSendDataToCloud = false;
    public static boolean g_bStepLogin = false;
    public static boolean g_bStepOrderOrPick = false;
    public static boolean g_bStepSelect = false;
    public static boolean g_bStepScanCrate = false;
    public static boolean g_bStepScanOrderOrPick = false;
    public static boolean g_bStepPickSKU = false;
    public static boolean g_bStepSKULOcation = false;
    public static boolean g_bShowBatteryDoorOpenStatus = false;
    public static boolean isDemoUser = false;
    public static boolean isOrderCompleted = false;
    public static boolean showSKULocation = false;
    public static boolean scanSKULocation = false;
    public static boolean lastSkuLocation = false;
    public static boolean g_bIsPickerLoginRequired = true;
    // For the testing purpose please make to false in production release
    public static boolean g_bInPreProductionTesting = false;
    public static int g_iPickerLoggedIn = 0;
    public static int g_iOverrideLoggedIn = 0;
    public static int g_iChargeMobile = 0;
    public static int g_bSendDataToCloudHbMissing = 1;
    public static int g_bSendDataToCloudDiagMissing = 1;
    static enumAppStatus g_eAppSts = enumAppStatus.APP_STS_LOGIN;
    static enumAppStatus appStatusBeforeCalibrate = enumAppStatus.APP_STS_MIN;
    public static boolean g_bLoggedIn = false;
    //Below boolean function is trigger Alert box
    public static boolean g_isU32ErrorCodeAlert = false;

    public static boolean g_isReverseEnabled = false;

    public static int g_u16AppStatus = 0;

    /* WIFI related variables and Constants */
    BroadcastReceiver myBroadcastReceiver = null;
    private static boolean myBroadcastReceiverRegistered = false;

    /* Bluetooth Specific Variable and Constants */
    public static final String DEVICE_NAME = "device_name";

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    static GDT_BluetoothProtocol g_BluetoothProtocol = new GDT_BluetoothProtocol();
    private static BluetoothAdapter myBtAdapter = null;
    static String myBtConnectedDeviceName = null;
    static BluetoothDevice myBtDevice = null;
    private static BluetoothSerialService myBtScannerService = null;

    /* Variables for Server connectivity */
    static final int SERVER_CONNECT_TOUT = 5000;
    static final int SERVER_READ_TOUT= 7000;
    static final int STS_OK = 0;
    static final int STS_PARSE_ERROR = 201;
    static final int STS_CONNECTION_ERROR = 202;
    static final int STS_UNKNOWN_ERROR = 301;

    static final int STS_POST_ADD_VIN_ALREADY_EXISTING = 101;
    static final int STS_POST_ADD_VIN_DB_ERROR = 102;

    static final int STS_POST_ADD_DIAG_NOT_POST_METHOD = 101;
    static final int STS_POST_ADD_DIAG_CONTENT_TYPE_ERR = 102;
    static final int STS_POST_ADD_DIAG_INVALID_JSON = 103;

    static final int STS_GET_CHECK_USER_INVALID_USERID = 101;

    static final int STS_GET_ORDERS_INVALID_USERID = 101;
    static final int STS_GET_ORDERS_NO_ORDERS_ASSIGNED = 102;
    static final int STS_GET_HOLD_LIST_EMPTY = 102;

    static final int STS_GET_QOH_INVALID_SKUCODE = 101;

    static final int STS_POST_ORDERS_INVALID_SKUCODE = 101;
    static final int STS_POST_ORDERS_INVALID_JSON_FORMAT = 102;
    static final int STS_POST_ORDERS_EMPTY_ORDERS = 103;
    static final int STS_POST_ORDERS_INVALID_FORMAT = 104;
    static final int STS_POST_ORDERS_INVALID_ORDER_ID = 105;

    static final int STS_GET_RFID_STATUS_INVALID_RFID = 101;
    static final int STS_GET_RFID_STATUS_ALREADY_ASSIGNED_CRATE = 102;
    static final int STS_GET_RFID_STATUS_NO_CRATE_FOUND = 103;

    static final int STS_SET_HOLD_INVALID_JSON_FORMAT = 101;
    static final int STS_SET_HOLD_ERR_IN_API = 102;
    static final int STS_SET_HOLD_ALREADY_ON_HOLD = 103;
    static final int STS_SET_HOLD_ALREADY_POSTED = 104;

    static final int STS_HOLD_DETAILS_INVALID_USERID = 101;
    static final int STS_HOLD_DETAILS_INVALID_PICKID = 102;
    static final int STS_HOLD_DETAILS_ERROR_IN_API = 103;
    static final int STS_HOLD_DETAILS_ALREADY_POSTED = 104;

    static final int STS_HOLD_ACK_INVALID_JSON_FORMAT = 101;
    static final int STS_HOLD_ACK_INVALID_USER_PICKID = 102;
    static final int STS_HOLD_ACK_API_ERR = 103;
    static final int STS_HOLD_ACK_INDEX_ERROR = 104;

    static final int SERVER_REQ_STS_NO_ERR = 0;
    static final int SERVER_REQ_STS_CONNECTION_ERR = 1;
    static final int SERVER_REQ_STS_INDEX_ERR = 2;

    static final int ADD_VIN_STS_NOT_ADDED = 0;
    static final int ADD_VIN_STS_IN_PROCESS = 1;
    static final int ADD_VIN_STS_ADDED = 2;

    static final int STS_NOT_CONNECTED = 0;
    static final int STS_CONNECTING = 1;
    static final int STS_CONNECTED = 2;
    static final int STS_ACTIVE = 3;

    //Below variable for u32ErrorCodes Mask
    static final byte MASK_VEHICLE_OVERLOAD = 0x01;
    static final byte MASK_CONTROLLER_TEMP_EXCEEDED = 0x02;
    static final byte MASK_MOTOR_TEMP_EXCEEDED = 0x04;
    static final byte MASK_BATTERY_TEMP_EXCEEDED = 0x08;
    static final byte MASK_CONTROLLER_TEMP_CUTOFF = 0x16;
    static final byte MASK_MOTOR_TEMP_CUTOFF = 0x32;

    //System variables for u32ErrorCodes Alert box to be functional
    private Dialog dialog;
    private boolean isPlayingSound = false;
    private MediaPlayer mediaPlayer;

    // Timer duration in milliseconds (e.g., 10 seconds) and handler for u32ErrorCode  to be functional
    private static final long AUDIO_TIMER_DURATION = 10000;
    private Handler audioTimerHandler = new Handler(Looper.getMainLooper());
    //Below variables are related to u32ErrorCodes alert box for Overload of Vehicle
    static String u32ErrorCodesAlertBoxTitle = "";
    static String u32ErrorCodesAlertBoxMessage = "";
    static String u32ErrorCodesAlertBoxDisclaimer = "**The error will go away once the errors are reset";


    private static int g_serverReqErr = SERVER_REQ_STS_NO_ERR;

    /* Resources in App */
    static RelativeLayout frameHeader, frameSystemStatus,frameActive, frameOrderScan,frameSkuDetails,
            frameLogin,frameOrderList,frameService,frameServiceSetting,frameAdmin,frameMOPTro,frameHoldOrder,
            frameDemoAdmin, frameAddSku, frameAddOrder, frameStatus, frameUser, frameVoiceHello;

    static TextView txtMoptroErrCode,txtBtStatus, txtCrateRfid, txtSkuDescription,
            txtSkuLocation, txtMoptroRange, txtMoptroBatPercent, txtOrderClosed, txtOrderOnhold,
            txtOrderPending, txtPassword, txtPickerId,
            txtQtyOnHand, txtQtyPicked, txtCalibrateEfp,txtSelectedOrder,
            txtSystemStatus, txtUserName, txtWifiStatus,txtOrderPostPin,
            txtBatVolt,txtBatVoltADC,txtBatVolt0ADC,txtBldcCurrent,txtBldcCurrentADC,
            txtBldcCurrent0ADC,
            txtOppositeBldcCurrent,txtOppositeBldcCurrentADC, txtOppositeBldcCurrent0ADC,
            txtChargerCurrent,txtChargerCurrentADC,txtChargerCurrent0ADC,
            txtOppositeChargerCurrent,txtOppositeChargerCurrentADC,txtOppositeChargerCurrent0ADC,
            txtV12Current,
            txtV12CurrentADC,txtV12Current0ADC,txtV05Current,txtV05CurrentADC,txtV05Current0ADC,txtOdometer,
            txtSocState,txtSoc,txtAwakeSts,txtObjectSts,txtDoorSts,txtCrateSts,txtEfpCyclesMeter,txtEfpTestCyclesMeter,
            txtEfpDoorErrCount, txtLoginRequiredStatus, txtAppVersion,


    /***************************************HeartBeat TextViews*********************************************/
            txtu32OdoMeter, txtu32UsageInTotalMin, txtu32UsageInMaxWattsMin, txtu32UsageInPeakWattsMin,
            txtu32UsageInPeakAmpsMin, txtu32ChargeCycles,txtiMaxWattsTimer, txtiPeakWattsTimer,txtiPeakAmpsTimer,
            txtiMaxWatts, txtiPeakWatts, txtiPeakAmps, txtiOLresetTimer, txtiSpeedLimitKmph,txtiTripMeter,
            txtiBrakeConfig, txtiBrakeDelayMsec,txtiAdcBatVolt, txtiAdc00ChargeAmps, txtiAdcChargeAmps,
            txtiAdc00LoadAmps1, txtiAdcLoadAmps1, txtiAdc00LoadAmps2, txtiAdcLoadAmps2, txtiAdc00McbAmps, txtiAdcMcbAmps,
            txtiSts0, txtiSts1, txtiSts2, txtiSts3, txtiSocPercent, txtiOutputPower, txtiTrips,
            txtu32OdoMeter_O, txtu32UsageInTotalMin_O,txtu32UsageInMaxWattsMin_O, txtu32UsageInPeakWattsMin_O,
            txtu32UsageInPeakAmpsMin_O, txtu32ChargeCycles_O,txtiAdcBattery_O, txtiAdcBatVolt_O,txtiAdc00ChargeAmps_O,
            txtiAdcChargeAmps_O, txtiAdc00LoadAmps1_O, txtiAdcLoadAmps1_O, txtiAdc00LoadAmps2_O, txtiAdcLoadAmps2_O,
            txtiAdc00McbAmps_O, txtiAdcMcbAmps_O,txtiSts0_O, txtiSts1_O, txtiSts2_O, txtiSts3_O, txtiSocPercent_O,
            txtiOutputPower_O, txtiTrips_O,txtfMaxVolt, txtfLowVoltCutoff, txtfDeadVoltCutoff,txtfBatVoltage,
            txtfLoadAmps, txtfChargeAmps, txtfMcbAmps, txtfSpeedKmph,txtfBatVoltage_O, txtfLoadAmps_O, txtfChargeAmps_O,
            txtfMcbAmps_O, txtfSpeedKmph_O,txtstrRfid1, txtstrRfid2,

    /***************************************Diag TextViews*********************************************/

            txtstrFirmwareRev,txtiIgnCycles, txtiChargeCycles, txtiDistanceTravelled, txtiStartStopCyclesByPulse,
            txtiStartStopCyclesByAmps,txtiOLcountMaxWatts, txtiOLcountPeakWatts, txtiOLcountPeakAmps, txtiBrakeOnMovingCount,
            txtiBrakeOnStandingCount,txtiMotorPowerAvg, txtiMotorPowerAvgAbs, txtiMotorPowerPeak,txtiErrCode0, txtiErrCode1,
            txtiIgnCycles_O, txtiChargeCycles_O, txtiDistanceTravelled_O, txtiStartStopCyclesByPulse_O, txtiStartStopCyclesByAmps_O,
            txtiOLcountMaxWatts_O, txtiOLcountPeakWatts_O, txtiOLcountPeakAmps_O, txtiBrakeOnMovingCount_O, txtiBrakeOnStandingCount_O,
            txtiMotorPowerAvg_O, txtiMotorPowerAvgAbs_O, txtiMotorPowerPeak_O,txtfLoadAmpsAvg, txtfLoadAmpsAvgAbs, txtfLoadAmpsPeak,
            txtfChargeAmpsAvg, txtfChargeAmpsAvgAbs, txtfChargeAmpsPeak, txtfSpeedKmphAvg, txtfSpeedKmphAvgAbs, txtfSpeedKmphPeak,
            txtfLoadAmpsAvg_O, txtfLoadAmpsAvgAbs_O, txtfLoadAmpsPeak_O, txtfChargeAmpsAvg_O, txtfChargeAmpsAvgAbs_O, txtfChargeAmpsPeak_O,
            txtfSpeedKmphAvg_O, txtfSpeedKmphAvgAbs_O, txtfSpeedKmphPeak_O,
            txtHeaderMoptroName,
            txtGdtSettingMoptroNumber,txtGdtSettingMoptroNumberNew,
            txtGdtSettingEfpChainOpen1Time,txtGdtSettingEfpChainOpen1TimeNew,
            txtGdtSettingEfpChainOpen2Time,txtGdtSettingEfpChainOpen2TimeNew,
            txtGdtSettingEfpChainClose1Time,txtGdtSettingEfpChainClose1TimeNew,
            txtGdtSettingEfpChainTestCycles,txtGdtSettingEfpChainTestCyclesNew,
            txtTypedServerIp,txtLocalUser,txtLocalPassword,txtItemsPerCrate,txtStsScreenVoltage,
            txtStsScreenCurrent, txtStsScreenOdometer, txtEvChargingCurrent,txtOrderScanTitle,
            txtHoldSkuDesc1,txtHoldSkuDesc2,txtHoldSkuDesc3,txtHoldSkuDesc4,txtHoldSkuDesc5,txtHoldSkuDesc6,txtHoldSkuDesc7,txtHoldSkuDesc8,txtHoldSkuDesc9,txtHoldSkuDesc10,
            txtHoldSkuQty1,txtHoldSkuQty2,txtHoldSkuQty3,txtHoldSkuQty4,txtHoldSkuQty5,txtHoldSkuQty6,txtHoldSkuQty7,txtHoldSkuQty8,txtHoldSkuQty9,txtHoldSkuQty10,
            txtHoldOrderNumber, txtDemoAdmin, txtSkuCountValue, txtBatteryDoorLogo,
                    txtEmergencyButton, txtBrake, txtPushButton,txtNativeIgnition, txtNativeMotor,
                    txtVehicleDirection,
                    txtVehicleStatus, txtVoltageStatus, txtCurrentStatus, txtThrottleStatus,
                    txtOppositeEmergencyButton, txtOppositeBrake,txtOppositePushButton, txtOppositeMotor, txtOppositeThrottleStatus,
            txtSysNativeIgnition, txtSysVehicleLogin, txtSysVehicleEmergency,txtSysBrakeStatus, txtSysReverseStatus,
            txtSysReverseStatusLabel;

            static EditText txtAddSkuDesc,txtAddSkuLocation,txtAddSkuQuantity,txtAddSkuBarcode, txtAddOrderNumber;

    static Button btnLogin, btnLogout, btnSkipSku, btnCloseCrate,
            btnFakeScan,btnNextOrder,btnResetDoorJamErr,
            btnServiceExit, btnServiceSetting, btnServiceSettingClose, btnServiceSettingUpdate,
            btnAdminSettingsClose,btnAdminSettingsUpdate,btnEfpCycleTest,btnResetErrors,btnResetPcb,
            btnPickHoldOrder,btnPostHoldOrder,btnHoldOrderReturn,btnReturnToOrderList,
            btnDemoAdminSettingsClose, btnDemoAdminSettingsUpdate,btnAddSkuCancel,btnAddSkuNext, btnAddSku,
            btnDemoAdminSettingsAddSku, btnSubmitSKU, btnUpdateVin, btnStatus, btnStatusLogout;
           // faceLogin;

    static ImageView imgBtIcon, imgMoptroIcon, imgIotIcon, imgDirection,imgSettingsIcon,
            imgRefreshOrder,imgRefreshSku, imgBatterDoorLogo;

    static ListView listOrder;

    static Spinner spnAlgoSelection,spnServerSelection;

    static CheckBox chkEnableFakeScan, chkStepLogin, chkStepOrderOrPick,
            chkStepScanCrate, chkStepScanOrderOrPick, chkStepPickSKU, chkStepSKULocation,
            chkEnableBatteryAlert;

    static TextView txtCell_1_2,txtCell_1_3,txtCell_2_2,txtCell_2_3,txtCell_3_2,txtCell_3_3,txtCell_4_2,txtCell_4_3;


    /* Text view and image view for u32ErroCodes Alert box*/
//    ABM - Alert Box error Message
    static RelativeLayout ABMLayoutVehicleOverload,ABMLayoutControllerTempWarn,ABMLayoutMotorTempWarn,ABMLayoutBatteryTempWarn,ABMLayoutControllerTempErr,ABMLayoutMotorTempErr;
    static TextView titleTextView, messageVehicleOverloadTextView, disclaimerTextView, controllerErrMessageTextView, motorErrMessageTextView, batteryWarnMessageTextView, controllerWarnMessageTextView,
    motorWarnMessageTextView;
    static ImageView vehicleOverloadImageView, controllerTemperatureAlertImageview, motorTemperatureAlertImageView, batteryTemperatureAlertImageView, controllerTemperatureWarningImageView, motorTemperatureWarningImageView;

        /* Unsorted System Variables - Sort it when hectic :) */
    static int g_iVinAdded = ADD_VIN_STS_NOT_ADDED;  /* Do this on every APP Launch */
    static int g_iWifiStatus = ADD_VIN_STS_NOT_ADDED;  /* Do this on every APP Launch */
    static int g_iBtStatus = ADD_VIN_STS_NOT_ADDED;  /* Do this on every APP Launch */
    static String g_sWifiAddress = "";
    static String g_strCrateMissingStrtTime = "";
    static CustomAdapter orderListAdapter = null;
    static OrderClass g_OrdersClass_fromServer;
    static String g_strJsonAddHbToServer = "";
    static String g_strJsonAddVIN = "";
    static String g_strJsonAddDiag = "";
    static String g_strJsonCheckuser = "";
    static String g_strJsonGetorders = "";
    static String g_strJsonRefreshSku = "";
    static String g_strJsonPostorders = "";
    static String g_strJsonQohStatus = "";
    static String g_strJsonRfidstatus = "";
    static String g_strJsonSetHoldOrderRes = "";
    static String g_strJsonGetHoldList = "";
    static String g_strJsonGetHoldOrder = "";
    static String g_strJsonAckHoldOrderRes = "";
    static boolean g_validSkuScanned = false;
    static boolean g_validCrateRfidScanned = false;
    static boolean g_validOrderLabelScanned = false;
    static boolean g_usedCrateScanned = false;
    static ProgressDialog g_progressDialog;
    static ProgressDialog g_progressDialogCrateError;
    static GDT_playMedia g_MediaPlayClass = new GDT_playMedia();
    static String g_strUserName = null;
    static String g_strUserNameUcase = null;
    static String g_strCrateLabel = null;
    static String g_strOrderLabel = null;
    static String g_strSkuEanLabel = null;
    static String g_strSkuCode = null;

    static String g_strCommand = null;

    static int g_iQohForSku = 0;
    static boolean g_bEfpTestOngoing = false;
    private Handler crateMissingHandler;
    private Handler btReconnectHandler;
    private Handler btTxHandler;
    private Handler stsScreenUpdateHandler;
    private Handler btReconnectToutHandler;
    private Handler addDiagToServerHandler;
    private Handler addHbToServerHandler;
    private Handler serverOrderPostHandler;
    SharedPreferences g_SettingsSharedPref;
    SharedPreferences g_OnGoingOrderSharedPref;
    Runtime runtime = Runtime.getRuntime();
    static String g_strPickedSkuArray[][] = new String[4][4];
    static long g_u16SampleCountC = 0;
    static long g_u16SampleCountW = 0;
    static long g_u16BtReconnectCount = 0;

    static Map<String, String> g_bItemSelected = new HashMap<>();
    static List<JSONObject> g_bitemsSelected = new ArrayList<JSONObject>();

    //For Algorithm
    //////////////////////////////////////////////////////////
    //GDT_Algorithm last position variables stored in an array PickerConfig
    //only for 1st order these initializing should be done
    Integer current_aisle = new Integer(0);
    Integer x_flag = new Integer(1);
    Integer y_flag = new Integer(-1);
    Integer block_C = new Integer(0);
    static List PickerConfig = new ArrayList();
    private final static int AISLE_INDEX = 0;
    private final static int X_DIR_INDEX = 1;
    private final static int Y_DIR_INDEX = 2;
    private final static int BLOCK_INDEX = 3;
    //////////////////////////////////////////////////////////
    //constructing the GDT_Algorithm PakaConfig array
    static List<Integer> PakaConfig = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_gdt_diag);
        }catch (Exception e){
            e.printStackTrace();
        }

        // BELOW CODE IS FOR THE ENABLING THE SCREEN ONN WHEN THE SCREEN IS OFF
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //setContentView(R.layout.activity_gdt_connect);

        Log.e(TAG, "+ ON CREATE +");

        // Create a new custom dialog
        dialog = new Dialog(GDT_Main.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_dialog);
        //UI variables for Alert Box Text Views
        titleTextView = dialog.findViewById(R.id.u32errorCodesAlertBoxTitle);
        messageVehicleOverloadTextView = dialog.findViewById(R.id.u32errorCodesAlertBoxMessage);
        disclaimerTextView = dialog.findViewById(R.id.u32errorCodesAlertBoxDisclaimer);

        //Below text view of Controller Temperature error message
        controllerErrMessageTextView = dialog.findViewById(R.id.controllerTemperatureAlertBoxMessage);
        //Below text view of Motor Temperature error message
        motorErrMessageTextView = dialog.findViewById(R.id.motorTemperatureAlertBoxMessage);
        //Below text view of Battery Temperature error message
        batteryWarnMessageTextView = dialog.findViewById(R.id.batteryTemperatureWarningAlertBoxMessage);
        //Below text view of Controller Temperature warning message
        controllerWarnMessageTextView = dialog.findViewById(R.id.controllerTemperatureWarningMessage);
        //Below text view of Motor Temperature warning message
        motorWarnMessageTextView = dialog.findViewById(R.id.motorTemperatureWarningMessage);

        //UI variables for Alert Box Image View
        vehicleOverloadImageView = dialog.findViewById(R.id.vehicleOverloadAlertImage);
        controllerTemperatureAlertImageview = dialog.findViewById(R.id.controllerTemperatureAlertImage);
        motorTemperatureAlertImageView = dialog.findViewById(R.id.motorTemperatureAlertImage);
        batteryTemperatureAlertImageView = dialog.findViewById(R.id.batteryTemperatureAlertImage);
        controllerTemperatureWarningImageView = dialog.findViewById(R.id.controllerTemperatureWarningImage);
        motorTemperatureWarningImageView = dialog.findViewById(R.id.motorTemperatureWarningImage);

        //UI variable for Alert Box Relative layout
        ABMLayoutVehicleOverload = dialog.findViewById(R.id.ABMLayoutVehicleOverload);
        ABMLayoutControllerTempWarn = dialog.findViewById(R.id.ABMLayoutControllerTempWarn);
        ABMLayoutMotorTempWarn = dialog.findViewById(R.id.ABMLayoutMotorTempWarn);
        ABMLayoutBatteryTempWarn = dialog.findViewById(R.id.ABMLayoutBatteryTempWarn);
        ABMLayoutControllerTempErr = dialog.findViewById(R.id.ABMLayoutControllerTempErr);
        ABMLayoutMotorTempErr = dialog.findViewById(R.id.ABMLayoutMotorTempErr);

        //Set by default all visibility of ABM layout gone.
        ABMLayoutVehicleOverload.setVisibility(View.GONE);
        ABMLayoutControllerTempWarn.setVisibility(View.GONE);
        ABMLayoutMotorTempWarn.setVisibility(View.GONE);
        ABMLayoutBatteryTempWarn.setVisibility(View.GONE);
        ABMLayoutControllerTempErr.setVisibility(View.GONE);
        ABMLayoutMotorTempErr.setVisibility(View.GONE);

//        //Set by default all visibility of text view gone for u32ErrorCodes alert box
//        messageVehicleOverloadTextView.setVisibility(View.GONE);
//        controllerErrMessageTextView.setVisibility(View.GONE);
//        motorErrMessageTextView.setVisibility(View.GONE);
//        batteryWarnMessageTextView.setVisibility(View.GONE);
//        controllerWarnMessageTextView.setVisibility(View.GONE);
//        motorWarnMessageTextView.setVisibility(View.GONE);
//
//        //Set by default all visibility of image view gone for u32ErrorCodes alert box
//        vehicleOverloadImageView.setVisibility(View.GONE);
//        controllerTemperatureAlertImageview.setVisibility(View.GONE);
//        motorTemperatureAlertImageView.setVisibility(View.GONE);
//        batteryTemperatureAlertImageView.setVisibility(View.GONE);
//        controllerTemperatureWarningImageView.setVisibility(View.GONE);
//        motorTemperatureWarningImageView.setVisibility(View.GONE);

        //Map Layouts
        frameHeader = (RelativeLayout) findViewById(R.id.GdtLayoutHeader);
        frameSystemStatus = (RelativeLayout) findViewById(R.id.GdtLayoutSysSts);
        frameLogin = (RelativeLayout) findViewById(R.id.GdtLayoutLogin);
        frameOrderList = (RelativeLayout) findViewById(R.id.GdtLayoutOrderList);
        frameOrderScan = (RelativeLayout) findViewById(R.id.GdtLayoutOrderScan);
        frameSkuDetails = (RelativeLayout)findViewById(R.id.GdtLayoutSkuDetails);
        frameService = (RelativeLayout) findViewById(R.id.GdtLayoutService);
        frameServiceSetting = (RelativeLayout) findViewById(R.id.GdtLayoutServiceSetting);
        frameAdmin = (RelativeLayout) findViewById(R.id.GdtLayoutAdmin);
        frameMOPTro = (RelativeLayout) findViewById(R.id.GdtLayoutMoptro);
        frameHoldOrder = (RelativeLayout) findViewById(R.id.GdtLayoutHoldOrder);
        frameDemoAdmin = (RelativeLayout) findViewById(R.id.GdtLayoutDemoAdmin);
        frameAddSku = (RelativeLayout) findViewById(R.id.GdtAddSkuForDemo);
        frameAddOrder = (RelativeLayout) findViewById(R.id.GdtAddOrderForDemo);
        frameStatus = (RelativeLayout) findViewById(R.id.GdtLayoutStatus);

        frameSystemStatus.setVisibility(View.INVISIBLE);
        frameOrderList.setVisibility(View.INVISIBLE);
        frameOrderScan.setVisibility(View.INVISIBLE);
        frameService.setVisibility(View.INVISIBLE);
        frameServiceSetting.setVisibility(View.INVISIBLE);
        frameAdmin.setVisibility(View.INVISIBLE);
        frameActive = frameLogin;
        frameActive.setVisibility(View.VISIBLE);
        frameStatus.setVisibility(View.INVISIBLE);

        //Map ImageView
        //imgPhoneBatteryIcon = (ImageView) findViewById(R.id.ImgPhoneBatteryLogo);
        imgBtIcon= (ImageView) findViewById(R.id.ImgBtLogo);
        imgMoptroIcon = (ImageView) findViewById(R.id.ImgMoptroLogo);
        imgIotIcon = (ImageView) findViewById(R.id.ImgIotLogo);
        //imgDirection = (ImageView) findViewById(R.id.ImgDirection);
        imgSettingsIcon = (ImageView) findViewById(R.id.ImgSettingsLogo);
        imgRefreshOrder = (ImageView) findViewById(R.id.ImgRefreshOrder);
        imgRefreshSku = (ImageView) findViewById(R.id.ImgRefreshSku);
        imgBatterDoorLogo = (ImageView) findViewById(R.id.ImgBatterDoorLogo);

        //Map TextView
        txtMoptroErrCode =  (TextView) findViewById(R.id.TxtMoptroErrCode);
        txtMoptroRange = (TextView) findViewById(R.id.TxtMoptroRange);
        txtMoptroBatPercent = (TextView) findViewById(R.id.TxtMoptroBatPercent);
        txtPickerId = (TextView) findViewById(R.id.TxtPickerId);
        txtBtStatus = (TextView) findViewById(R.id.TxtBTStatus);
        txtWifiStatus = (TextView) findViewById(R.id.TxtWifiStatus);
        txtCalibrateEfp = (TextView) findViewById(R.id.TxtCalibrateEfp);
        txtUserName = (EditText) findViewById(R.id.TxtUserName);
        txtPassword = (EditText) findViewById(R.id.TxtPassword);
        txtOrderClosed = (TextView) findViewById(R.id.TxtOrderClosedCount);
        txtOrderPending = (TextView) findViewById(R.id.TxtOrderPendingCount);
        txtOrderOnhold = (TextView) findViewById(R.id.TxtOrderOnholdCount);
        txtSelectedOrder = (TextView) findViewById(R.id.TxtSelectedOrder);
        txtCrateRfid = (TextView) findViewById(R.id.TxtCrateRfid);
        txtSkuLocation = (TextView)findViewById(R.id.TxtSkuLocation);
        txtQtyOnHand = (TextView)findViewById(R.id.TxtQtyOnHand);
        txtQtyPicked = (TextView)findViewById(R.id.TxtQtyPicked);
        txtSkuDescription = (TextView)findViewById(R.id.TxtSkuDescription);
        txtOrderPostPin  = (TextView)findViewById(R.id.TxtOrderPostPin);
        txtBatVolt  = (TextView)findViewById(R.id.TxtBatVolt);
        txtBatVoltADC  = (TextView)findViewById(R.id.TxtBatVoltADC);
        txtBatVolt0ADC  = (TextView)findViewById(R.id.TxtBatVolt0ADC);

        txtBldcCurrent  = (TextView)findViewById(R.id.TxtBldcCurrent);
        txtBldcCurrentADC  = (TextView)findViewById(R.id.TxtBldcCurrentADC);
        txtBldcCurrent0ADC  = (TextView)findViewById(R.id.TxtBldcCurrent0ADC);

        /**************************New Row Added*****************************/
        txtOppositeBldcCurrent  = (TextView)findViewById(R.id.TxtOppositeBldcCurrent);
        txtOppositeBldcCurrentADC  = (TextView)findViewById(R.id.TxtOppositeBldcCurrentADC);
        txtOppositeBldcCurrent0ADC  = (TextView)findViewById(R.id.TxtOppositeBldcCurrent0ADC);

        txtChargerCurrent  = (TextView)findViewById(R.id.TxtChargerCurrent);
        txtChargerCurrentADC  = (TextView)findViewById(R.id.TxtChargerCurrentADC);
        txtChargerCurrent0ADC  = (TextView)findViewById(R.id.TxtChargerCurrent0ADC);

        txtOppositeChargerCurrent  = (TextView)findViewById(R.id.TxtOppositeChargerCurrent);
        txtOppositeChargerCurrentADC  = (TextView)findViewById(R.id.TxtOppositeChargerCurrentADC);
        txtOppositeChargerCurrent0ADC  = (TextView)findViewById(R.id.TxtOppositeChargerCurrent0ADC);


        txtV12Current  = (TextView)findViewById(R.id.TxtV12Current);
        txtV12CurrentADC  = (TextView)findViewById(R.id.TxtV12CurrentADC);
        txtV12Current0ADC  = (TextView)findViewById(R.id.TxtV12Current0ADC);
        txtV05Current  = (TextView)findViewById(R.id.TxtV05Current);
        txtV05CurrentADC  = (TextView)findViewById(R.id.TxtV05CurrentADC);
        txtV05Current0ADC  = (TextView)findViewById(R.id.TxtV05Current0ADC);
        //txtOdometer  = (TextView)findViewById(R.id.TxtOdometer);
        txtSocState  = (TextView)findViewById(R.id.TxtSocState);
        txtSoc  = (TextView)findViewById(R.id.TxtSoc);
        txtAwakeSts  = (TextView)findViewById(R.id.TxtAwakeSts);
        txtDoorSts  = (TextView)findViewById(R.id.TxtDoorSts);
        txtCrateSts  = (TextView)findViewById(R.id.TxtCrateSts);
        txtObjectSts = (TextView)findViewById(R.id.TxtObjectSts);
        txtEfpCyclesMeter  = (TextView)findViewById(R.id.TxtEfpCyclesMeter);
        txtEfpTestCyclesMeter  = (TextView)findViewById(R.id.TxtEfpTestCyclesMeter);
        txtEfpDoorErrCount  = (TextView)findViewById(R.id.TxtEfpDoorErrCount);
        txtLoginRequiredStatus = (TextView) findViewById(R.id.TxtLoginRequiredStatus);
        txtHeaderMoptroName  = (TextView)findViewById(R.id.TxtHeadingMoptroName);
        txtGdtSettingMoptroNumber  = (TextView)findViewById(R.id.TxtGdtSettingMoptroNumber);
        txtGdtSettingEfpChainTestCycles  = (TextView)findViewById(R.id.TxtGdtSettingEfpChainTestCycles);
        txtGdtSettingMoptroNumberNew  = (TextView)findViewById(R.id.TxtGdtSettingMoptroNumberNew);
        txtGdtSettingEfpChainOpen1Time  = (TextView)findViewById(R.id.TxtGdtSettingEfpChainOpen1Time);
        txtGdtSettingEfpChainOpen1TimeNew  = (TextView)findViewById(R.id.TxtGdtSettingEfpChainOpen1TimeNew);
        txtGdtSettingEfpChainOpen2Time  = (TextView)findViewById(R.id.TxtGdtSettingEfpChainOpen2Time);
        txtGdtSettingEfpChainOpen2TimeNew  = (TextView)findViewById(R.id.TxtGdtSettingEfpChainOpen2TimeNew);
        txtGdtSettingEfpChainClose1Time  = (TextView)findViewById(R.id.TxtGdtSettingEfpChainClose1Time);
        txtGdtSettingEfpChainClose1TimeNew  = (TextView)findViewById(R.id.TxtGdtSettingEfpChainClose1TimeNew);
        txtGdtSettingEfpChainTestCycles  = (TextView)findViewById(R.id.TxtGdtSettingEfpChainTestCycles);
        txtGdtSettingEfpChainTestCyclesNew  = (TextView)findViewById(R.id.TxtGdtSettingEfpChainTestCyclesNew);
        txtTypedServerIp =  (TextView) findViewById(R.id.TxtTypedServerIp);
        txtLocalUser =  (TextView) findViewById(R.id.TxtLocalUser);
        txtLocalPassword =  (TextView) findViewById(R.id.TxtLocalPassword);
        txtItemsPerCrate =  (TextView) findViewById(R.id.TxtItemsPerCrate);
        txtStsScreenVoltage =  (TextView) findViewById(R.id.TxtStsScreenVoltage);
        txtStsScreenCurrent =  (TextView) findViewById(R.id.TxtStsScreenCurrent);
        txtStsScreenOdometer =  (TextView) findViewById(R.id.TxtStsScreenOdometer);
        txtEvChargingCurrent =  (TextView) findViewById(R.id.TxtEvChargingCurrent);
        txtOrderScanTitle =  (TextView) findViewById(R.id.TxtOrderScanTitle);
        txtHoldSkuDesc1 =  (TextView) findViewById(R.id.TxtHoldSkuDesc1);
        txtHoldSkuDesc2 =  (TextView) findViewById(R.id.TxtHoldSkuDesc2);
        txtHoldSkuDesc3 =  (TextView) findViewById(R.id.TxtHoldSkuDesc3);
        txtHoldSkuDesc4 =  (TextView) findViewById(R.id.TxtHoldSkuDesc4);
        txtHoldSkuDesc5 =  (TextView) findViewById(R.id.TxtHoldSkuDesc5);
        txtHoldSkuDesc6 =  (TextView) findViewById(R.id.TxtHoldSkuDesc6);
        txtHoldSkuDesc7 =  (TextView) findViewById(R.id.TxtHoldSkuDesc7);
        txtHoldSkuDesc8 =  (TextView) findViewById(R.id.TxtHoldSkuDesc8);
        txtHoldSkuDesc9 =  (TextView) findViewById(R.id.TxtHoldSkuDesc9);
        txtHoldSkuDesc10 =  (TextView) findViewById(R.id.TxtHoldSkuDesc10);
        txtHoldSkuQty1 =  (TextView) findViewById(R.id.TxtHoldSkuQty1);
        txtHoldSkuQty2 =  (TextView) findViewById(R.id.TxtHoldSkuQty2);
        txtHoldSkuQty3 =  (TextView) findViewById(R.id.TxtHoldSkuQty3);
        txtHoldSkuQty4 =  (TextView) findViewById(R.id.TxtHoldSkuQty4);
        txtHoldSkuQty5 =  (TextView) findViewById(R.id.TxtHoldSkuQty5);
        txtHoldSkuQty6 =  (TextView) findViewById(R.id.TxtHoldSkuQty6);
        txtHoldSkuQty7 =  (TextView) findViewById(R.id.TxtHoldSkuQty7);
        txtHoldSkuQty8 =  (TextView) findViewById(R.id.TxtHoldSkuQty8);
        txtHoldSkuQty9 =  (TextView) findViewById(R.id.TxtHoldSkuQty9);
        txtHoldSkuQty10 =  (TextView) findViewById(R.id.TxtHoldSkuQty10);
        txtHoldOrderNumber = (TextView) findViewById(R.id.TxtHoldOrderNumber);
        txtDemoAdmin = (TextView) findViewById(R.id.TxtDemoAdmin);
        txtAddSkuDesc = (EditText) findViewById(R.id.AddSkuDesc);
        txtAddSkuLocation = (EditText) findViewById(R.id.AddSkuLocation);
        txtAddSkuQuantity = (EditText) findViewById(R.id.AddSkuQuantity);
        txtAddSkuBarcode = (EditText) findViewById(R.id.AddSkuBarcode);
        txtAddOrderNumber = (EditText) findViewById(R.id.AddOrderNumber);
        txtSkuCountValue = (TextView) findViewById(R.id.TxtSkuCountValue);

        txtCell_1_2 = (TextView) findViewById(R.id.cell_1_2);
        txtCell_1_3 = (TextView) findViewById(R.id.cell_1_3);
        txtCell_2_2 = (TextView) findViewById(R.id.cell_2_2);
        txtCell_2_3 = (TextView) findViewById(R.id.cell_2_3);
        txtCell_3_2 = (TextView) findViewById(R.id.cell_3_2);
        txtCell_3_3 = (TextView) findViewById(R.id.cell_3_3);
        txtCell_4_2 = (TextView) findViewById(R.id.cell_4_2);
        txtCell_4_3 = (TextView) findViewById(R.id.cell_4_3);

        txtAppVersion = (TextView) findViewById(R.id.TxtAppVersion);

        /*****************************HeartBeat Textview*****************************************/

        txtu32OdoMeter = (TextView) findViewById(R.id.Txtu32OdoMeter);
        txtu32UsageInTotalMin = (TextView) findViewById(R.id.Txtu32UsageInTotalMin);
        txtu32UsageInMaxWattsMin = (TextView) findViewById(R.id.Txtu32UsageInMaxWattsMin);
        txtu32UsageInPeakWattsMin = (TextView) findViewById(R.id.Txtu32UsageInPeakWattsMin);
        txtu32UsageInPeakAmpsMin = (TextView) findViewById(R.id.Txtu32UsageInPeakAmpsMin);
        txtu32ChargeCycles = (TextView) findViewById(R.id.Txtu32ChargeCycles);
        txtiMaxWattsTimer = (TextView) findViewById(R.id.TxtiMaxWattsTimer);
        txtiPeakWattsTimer = (TextView) findViewById(R.id.TxtiPeakWattsTimer);
        txtiPeakAmpsTimer = (TextView) findViewById(R.id.TxtiPeakAmpsTimer);
        txtiMaxWatts = (TextView) findViewById(R.id.TxtiMaxWatts);
        txtiPeakWatts = (TextView) findViewById(R.id.TxtiPeakWatts);
        txtiPeakAmps = (TextView) findViewById(R.id.TxtiPeakAmps);
        txtiOLresetTimer = (TextView) findViewById(R.id.TxtiOLresetTimer);
        txtiSpeedLimitKmph = (TextView) findViewById(R.id.TxtiSpeedLimitKmph);

        txtiTripMeter = (TextView) findViewById(R.id.TxtiTripMeter);
        txtiBrakeConfig = (TextView) findViewById(R.id.TxtiBrakeConfig);
        txtiBrakeDelayMsec = (TextView) findViewById(R.id.TxtiBrakeDelayMsec);


        txtiAdcBatVolt = (TextView) findViewById(R.id.TxtiAdcBatVolt);
        txtiAdc00ChargeAmps = (TextView) findViewById(R.id.TxtiAdc00ChargeAmps);
        txtiAdcChargeAmps = (TextView) findViewById(R.id.TxtiAdcChargeAmps);

        txtiAdc00LoadAmps1 = (TextView) findViewById(R.id.TxtiAdc00LoadAmps1);
        txtiAdcLoadAmps1 = (TextView) findViewById(R.id.TxtiAdcLoadAmps1);
        txtiAdc00LoadAmps2 = (TextView) findViewById(R.id.TxtiAdc00LoadAmps2);
        txtiAdcLoadAmps2 = (TextView) findViewById(R.id.TxtiAdcLoadAmps2);
        txtiAdc00McbAmps = (TextView) findViewById(R.id.TxtiAdc00McbAmps);
        txtiAdcMcbAmps = (TextView) findViewById(R.id.TxtiAdcMcbAmps);



        txtiSts0 = (TextView) findViewById(R.id.TxtiSts0);
        txtiSts1 = (TextView) findViewById(R.id.TxtiSts1);
        txtiSts2 = (TextView) findViewById(R.id.TxtiSts2);
        txtiSts3 = (TextView) findViewById(R.id.TxtiSts3);
        txtiSocPercent = (TextView) findViewById(R.id.TxtiSocPercent);
        txtiOutputPower = (TextView) findViewById(R.id.TxtiOutputPower);
        txtiTrips = (TextView) findViewById(R.id.TxtiTrips);


        txtu32OdoMeter_O = (TextView) findViewById(R.id.Txtu32OdoMeter_O);
        txtu32UsageInTotalMin_O = (TextView) findViewById(R.id.Txtu32UsageInTotalMin_O);

        txtu32UsageInMaxWattsMin_O = (TextView) findViewById(R.id.Txtu32UsageInMaxWattsMin_O);
        txtu32UsageInPeakWattsMin_O = (TextView) findViewById(R.id.Txtu32UsageInPeakWattsMin_O);
        txtu32UsageInPeakAmpsMin_O = (TextView) findViewById(R.id.Txtu32UsageInPeakAmpsMin_O);
        txtu32ChargeCycles_O = (TextView) findViewById(R.id.Txtu32ChargeCycles_O);


        txtiAdcBattery_O = (TextView) findViewById(R.id.TxtiAdcBattery_O);
        txtiAdcBatVolt_O = (TextView) findViewById(R.id.TxtiAdcBatVolt_O);

        txtiAdc00ChargeAmps_O = (TextView) findViewById(R.id.TxtiAdc00ChargeAmps_O);
        txtiAdcChargeAmps_O = (TextView) findViewById(R.id.TxtiAdcChargeAmps_O);
        txtiAdc00LoadAmps1_O = (TextView) findViewById(R.id.TxtiAdc00LoadAmps1_O);
        txtiAdcLoadAmps1_O = (TextView) findViewById(R.id.TxtiAdcLoadAmps1_O);
        txtiAdc00LoadAmps2_O = (TextView) findViewById(R.id.TxtiAdc00LoadAmps2_O);
        txtiAdcLoadAmps2_O = (TextView) findViewById(R.id.TxtiAdcLoadAmps2_O);

        txtiAdc00McbAmps_O = (TextView) findViewById(R.id.TxtiAdc00McbAmps_O);
        txtiAdcMcbAmps_O = (TextView) findViewById(R.id.TxtiAdcMcbAmps_O);

        txtiSts0_O = (TextView) findViewById(R.id.TxtiSts0_O);
        txtiSts1_O = (TextView) findViewById(R.id.TxtiSts1_O);
        txtiSts2_O = (TextView) findViewById(R.id.TxtiSts2_O);
        txtiSts3_O = (TextView) findViewById(R.id.TxtiSts3_O);
        txtiSocPercent_O = (TextView) findViewById(R.id.TxtiSocPercent_O);
        txtiOutputPower_O = (TextView) findViewById(R.id.TxtiOutputPower_O);
        txtiTrips_O = (TextView) findViewById(R.id.TxtiTrips_O);
        txtfMaxVolt = (TextView) findViewById(R.id.TxtfMaxVolt);
        txtfLowVoltCutoff = (TextView) findViewById(R.id.TxtfLowVoltCutoff);
        txtfDeadVoltCutoff = (TextView) findViewById(R.id.TxtfDeadVoltCutoff);


        txtfBatVoltage = (TextView) findViewById(R.id.TxtfBatVoltage);
        txtfLoadAmps = (TextView) findViewById(R.id.TxtfLoadAmps);
        txtfChargeAmps = (TextView) findViewById(R.id.TxtfChargeAmps);
        txtfMcbAmps = (TextView) findViewById(R.id.TxtfMcbAmps);
        txtfSpeedKmph = (TextView) findViewById(R.id.TxtfSpeedKmph);

        txtfBatVoltage_O = (TextView) findViewById(R.id.TxtfBatVoltage_O);
        txtfLoadAmps_O = (TextView) findViewById(R.id.TxtfLoadAmps_O);
        txtfChargeAmps_O = (TextView) findViewById(R.id.TxtfChargeAmps_O);
        txtfMcbAmps_O = (TextView) findViewById(R.id.TxtfMcbAmps_O);
        txtfSpeedKmph_O = (TextView) findViewById(R.id.TxtfSpeedKmph_O);

        txtstrRfid1 = (TextView) findViewById(R.id.TxtstrRfid1);
        txtstrRfid2 = (TextView) findViewById(R.id.TxtstrRfid2);




        /***********************Diag TextView*******************************************/

        txtstrFirmwareRev = (TextView) findViewById(R.id.TxtstrFirmwareRev);
        txtiIgnCycles = (TextView) findViewById(R.id.TxtiIgnCycles);
        txtiChargeCycles = (TextView) findViewById(R.id.TxtiChargeCycles);
        txtiDistanceTravelled = (TextView) findViewById(R.id.TxtiDistanceTravelled);
        txtiStartStopCyclesByPulse = (TextView) findViewById(R.id.TxtiStartStopCyclesByPulse);
        txtiStartStopCyclesByAmps = (TextView) findViewById(R.id.TxtiStartStopCyclesByAmps);
        txtiOLcountMaxWatts = (TextView) findViewById(R.id.TxtiOLcountMaxWatts);
        txtiOLcountPeakWatts = (TextView) findViewById(R.id.TxtiOLcountPeakWatts);
        txtiOLcountPeakAmps = (TextView) findViewById(R.id.TxtiOLcountPeakAmps);
        txtiBrakeOnMovingCount = (TextView) findViewById(R.id.TxtiBrakeOnMovingCount);
        txtiBrakeOnStandingCount = (TextView) findViewById(R.id.TxtiBrakeOnStandingCount);
        txtiMotorPowerAvg = (TextView) findViewById(R.id.TxtiMotorPowerAvg);
        txtiMotorPowerAvgAbs = (TextView) findViewById(R.id.TxtiMotorPowerAvgAbs);
        txtiMotorPowerPeak = (TextView) findViewById(R.id.TxtiMotorPowerPeak);
        txtiErrCode0 = (TextView) findViewById(R.id.TxtiErrCode0);
        txtiErrCode1 = (TextView) findViewById(R.id.TxtiErrCode1);
        txtiIgnCycles_O = (TextView) findViewById(R.id.TxtiIgnCycles_O);
        txtiChargeCycles_O = (TextView) findViewById(R.id.TxtiChargeCycles_O);
        txtiDistanceTravelled_O = (TextView) findViewById(R.id.TxtiDistanceTravelled_O);
        txtiStartStopCyclesByPulse_O = (TextView) findViewById(R.id.TxtiStartStopCyclesByPulse_O);
        txtiStartStopCyclesByAmps_O = (TextView) findViewById(R.id.TxtiStartStopCyclesByAmps_O);
        txtiOLcountMaxWatts_O = (TextView) findViewById(R.id.TxtiOLcountMaxWatts_O);
        txtiOLcountPeakWatts_O = (TextView) findViewById(R.id.TxtiOLcountPeakWatts_O);
        txtiOLcountPeakAmps_O = (TextView) findViewById(R.id.TxtiOLcountPeakAmps_O);
        txtiBrakeOnMovingCount_O = (TextView) findViewById(R.id.TxtiBrakeOnMovingCount_O);
        txtiBrakeOnStandingCount_O = (TextView) findViewById(R.id.TxtiBrakeOnStandingCount_O);
        txtiMotorPowerAvg_O = (TextView) findViewById(R.id.TxtiMotorPowerAvg_O);
        txtiMotorPowerAvgAbs_O = (TextView) findViewById(R.id.TxtiMotorPowerAvgAbs_O);
        txtiMotorPowerPeak_O = (TextView) findViewById(R.id.TxtiMotorPowerPeak_O);
        txtfLoadAmpsAvg = (TextView) findViewById(R.id.TxtfLoadAmpsAvg);
        txtfLoadAmpsAvgAbs = (TextView) findViewById(R.id.TxtfLoadAmpsAvgAbs);
        txtfLoadAmpsPeak = (TextView) findViewById(R.id.TxtfLoadAmpsPeak);
        txtfChargeAmpsAvg = (TextView) findViewById(R.id.TxtfChargeAmpsAvg);
        txtfChargeAmpsAvgAbs = (TextView) findViewById(R.id.TxtfChargeAmpsAvgAbs);
        txtfChargeAmpsPeak = (TextView) findViewById(R.id.TxtfChargeAmpsPeak);
        txtfSpeedKmphAvg = (TextView) findViewById(R.id.TxtfSpeedKmphAvg);
        txtfSpeedKmphAvgAbs = (TextView) findViewById(R.id.TxtfSpeedKmphAvgAbs);
        txtfSpeedKmphPeak = (TextView) findViewById(R.id.TxtfSpeedKmphPeak);
        txtfLoadAmpsAvg_O = (TextView) findViewById(R.id.TxtfLoadAmpsAvg_O);
        txtfLoadAmpsAvgAbs_O = (TextView) findViewById(R.id.TxtfLoadAmpsAvgAbs_O);
        txtfLoadAmpsPeak_O = (TextView) findViewById(R.id.TxtfLoadAmpsPeak_O);
        txtfChargeAmpsAvg_O = (TextView) findViewById(R.id.TxtfChargeAmpsAvg_O);
        txtfChargeAmpsAvgAbs_O = (TextView) findViewById(R.id.TxtfChargeAmpsAvgAbs_O);
        txtfChargeAmpsPeak_O = (TextView) findViewById(R.id.TxtfChargeAmpsPeak_O);
        txtfSpeedKmphAvg_O = (TextView) findViewById(R.id.TxtfSpeedKmphAvg_O);
        txtfSpeedKmphAvgAbs_O = (TextView) findViewById(R.id.TxtfSpeedKmphAvgAbs_O);
        txtfSpeedKmphPeak_O = (TextView) findViewById(R.id.TxtfSpeedKmphPeak_O);
        txtBatteryDoorLogo = (TextView) findViewById(R.id.TxtBatteryDoorLogo);
        txtEmergencyButton = (TextView) findViewById(R.id.TxtEmergencyButton);
        txtBrake  = (TextView) findViewById(R.id.TxtBrake);
        txtPushButton = (TextView) findViewById(R.id.TxtNativePushButton);
        txtNativeIgnition = (TextView) findViewById(R.id.TxtNativeIgnition);
        txtNativeMotor = (TextView) findViewById(R.id.TxtMotorStatus);
        txtVehicleStatus = (TextView) findViewById(R.id.VehicleStatus);
        txtVoltageStatus = (TextView) findViewById(R.id.TxtVoltageStatus);
        txtCurrentStatus = (TextView) findViewById(R.id.TxtCurrentStatus);
        txtOppositeEmergencyButton = (TextView) findViewById(R.id.TxtOppositeEmergencyButton);
        txtOppositeBrake = (TextView) findViewById(R.id.TxtOppositeBrake);
        txtOppositePushButton = (TextView) findViewById(R.id.TxtOppositePushButton);
        txtOppositeMotor = (TextView) findViewById(R.id.TxtOppositeMotorStatus);
        txtThrottleStatus = (TextView) findViewById(R.id.TxtThrotleStatus);
        txtOppositeThrottleStatus = (TextView) findViewById(R.id.TxtOppositeThrotleStatus);
        txtVehicleDirection = (TextView) findViewById(R.id.TxtVehicleDirection);
        txtSysNativeIgnition = (TextView) findViewById(R.id.TxtSysNativeIgnition);
        txtSysVehicleLogin = (TextView) findViewById(R.id.TxtSysVehicleLogin);
        txtSysVehicleEmergency = (TextView) findViewById(R.id.TxtSysVehicleEmergency);
        txtSysBrakeStatus = (TextView) findViewById(R.id.TxtSysBrakeStatus);
        txtSysReverseStatus = (TextView) findViewById(R.id.TxtSysReverseStatus);
        txtSysReverseStatusLabel = (TextView) findViewById(R.id.TxtSysReverseStatusLabel);

        //Map ListView
        listOrder = (ListView) findViewById(R.id.ListOrder);

        //Map Buttons
        btnLogin = (Button) findViewById(R.id.BtnLogin);
        //faceLogin = (Button) findViewById(R.id.FaceLogin);
        btnResetDoorJamErr = (Button) findViewById(R.id.BtnResetDoorJamErr);
        btnLogout = (Button) findViewById(R.id.BtnLogout);
        btnSkipSku = (Button)findViewById(R.id.BtnSkipSku);
        btnCloseCrate = (Button)findViewById(R.id.BtnCloseCrate);
        btnFakeScan = (Button)findViewById(R.id.BtnFakeScan);
        btnNextOrder = (Button)findViewById(R.id.BtnNextOrder);
        btnServiceSetting = (Button)findViewById(R.id.BtnServiceSetting);
        btnServiceExit = (Button)findViewById(R.id.BtnServiceExit);
        btnServiceSettingClose = (Button)findViewById(R.id.BtnServiceSettingClose);
        btnServiceSettingUpdate = (Button)findViewById(R.id.BtnServieSettingUpdate);
        btnAdminSettingsClose = (Button)findViewById(R.id.BtnAdminSettingsClose);
        btnAdminSettingsUpdate = (Button)findViewById(R.id.BtnAdminSettingsUpdate);
        btnEfpCycleTest = (Button) findViewById(R.id.BtnEfpCycleTest);
        btnResetErrors = (Button) findViewById(R.id.BtnResetErrors);
        btnResetPcb = (Button) findViewById(R.id.BtnResetPcb);
        btnPickHoldOrder =  (Button) findViewById(R.id.BtnPickHoldOrder);
        btnPostHoldOrder =  (Button) findViewById(R.id.BtnPostHoldOrder);
        btnHoldOrderReturn =  (Button) findViewById(R.id.BtnHoldOrderReturn);
        btnReturnToOrderList = (Button) findViewById(R.id.BtnReturnToOrderList);
        btnDemoAdminSettingsClose = (Button)findViewById(R.id.BtnDemoAdminSettingsClose);
        btnDemoAdminSettingsUpdate = (Button)findViewById(R.id.BtnDemoAdminSettingsUpdate);
        btnAddSkuCancel = (Button)findViewById(R.id.BtnAddSkuCancel);
        btnAddSkuNext = (Button)findViewById(R.id.BtnAddSkuNext);
        btnAddSku = (Button)findViewById(R.id.BtnAddSku);
        btnDemoAdminSettingsAddSku = (Button)findViewById(R.id.BtnDemoAdminSettingsAddSku);
        btnSubmitSKU = (Button) findViewById(R.id.BtnSubmitSKU);
        btnUpdateVin = (Button) findViewById(R.id.BtnUpdateVin);
        btnStatus = (Button) findViewById(R.id.BtnStatus);
        btnStatusLogout = (Button) findViewById(R.id.BtnStatusLogout);

        //Map Check box
        chkEnableFakeScan = (CheckBox) findViewById(R.id.ChkEnableFakeScan);
        chkStepLogin = (CheckBox) findViewById(R.id.ChkStepLogin);
        chkStepOrderOrPick = (CheckBox) findViewById(R.id.ChkStepOrderOrPick);;
        chkStepScanCrate = (CheckBox) findViewById(R.id.ChkStepScanCrate);
        chkStepScanOrderOrPick = (CheckBox) findViewById(R.id.ChkStepScanOrderOrPick);
        chkStepPickSKU = (CheckBox) findViewById(R.id.ChkStepPickSKU);
        chkStepSKULocation = (CheckBox) findViewById(R.id.ChkStepSKULocation);
        chkEnableBatteryAlert = (CheckBox) findViewById(R.id.ChkEnableBatteryAlert);

        //Spinners - Dropdown list
        spnAlgoSelection = (Spinner)findViewById(R.id.SpnAlgoSelection);
        spnServerSelection = (Spinner)findViewById(R.id.SpnServerSelection);

        //MAP THE NUMBER CONTROL USED IN ADDING SKU


        // spnAlgoSelection Drop down elements
        List<String> algoCategories = new ArrayList<String>();
        algoCategories.add("PAKA Basic");
        algoCategories.add("PAKA Advanced");
        algoCategories.add("Sequential");
        // Creating adapter for spinner
        ArrayAdapter<String> algoListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, algoCategories);
        // Drop down layout style - list view with radio button
        algoListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spnAlgoSelection.setAdapter(algoListAdapter);
        // Algo Spinner Drop down elements
        List<String> serverCategories = new ArrayList<String>();
        serverCategories.add("GD_TEST");
        serverCategories.add("GD_LIVE");
        serverCategories.add("WH_DC_TEST");
        serverCategories.add("WH_DC_LIVE");
        serverCategories.add("TYPED_IP");
        // Creating adapter for spinner
        ArrayAdapter<String> serverListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, serverCategories);
        // Drop down layout style - list view with radio button
        serverListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spnServerSelection.setAdapter(serverListAdapter);

        /* Set on click listener for Objects in screen */
        btnLogin.setOnClickListener(this);
        //faceLogin.setOnClickListener(this);
        btnSkipSku.setOnClickListener(this);
        btnFakeScan.setOnClickListener(this);
        btnCloseCrate.setOnClickListener(this);
        imgSettingsIcon.setOnClickListener(this);
        txtBtStatus.setOnClickListener(this);
        txtCalibrateEfp.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnNextOrder.setOnClickListener(this);
        imgRefreshOrder.setOnClickListener(this);
        imgRefreshSku.setOnClickListener(this);
        btnServiceSetting.setOnClickListener(this);
        btnServiceExit.setOnClickListener(this);
        btnServiceSettingClose.setOnClickListener(this);
        btnServiceSettingUpdate.setOnClickListener(this);
        btnAdminSettingsClose.setOnClickListener(this);
        btnAdminSettingsUpdate.setOnClickListener(this);
        btnEfpCycleTest.setOnClickListener(this);
        btnResetErrors.setOnClickListener(this);
        btnResetPcb.setOnClickListener(this);
        btnResetDoorJamErr.setOnClickListener(this);
        btnPickHoldOrder.setOnClickListener(this);
        btnPostHoldOrder.setOnClickListener(this);
        btnHoldOrderReturn.setOnClickListener(this);
        btnReturnToOrderList.setOnClickListener(this);
        btnDemoAdminSettingsClose.setOnClickListener(this);
        btnDemoAdminSettingsUpdate.setOnClickListener(this);
        btnAddSkuNext.setOnClickListener(this);
        btnAddSkuCancel.setOnClickListener(this);
        btnAddSku.setOnClickListener(this);
        btnDemoAdminSettingsAddSku.setOnClickListener(this);
        btnSubmitSKU.setOnClickListener(this);
        btnUpdateVin.setOnClickListener(this);
        btnStatus.setOnClickListener(this);
        btnStatusLogout.setOnClickListener(this);

        //Create Progress dialog
        g_progressDialog = new ProgressDialog(this);
        g_progressDialog.setIndeterminate(true);
        g_progressDialog.setCancelable(false);
        g_progressDialogCrateError = new ProgressDialog(this);
        g_progressDialogCrateError.setIndeterminate(true);
        g_progressDialogCrateError.setCancelable(false);

        /* Set Default Text's */
        txtPickerId.setText("-NO LOGIN-");
        txtMoptroErrCode.setText("No Error");

        /* Reset picker SKU array */
        resetPickedSkuArray();

        /* Instantiate Classes and Inner Classes */
        g_BluetoothProtocol.app_to_pcb_data = new GDT_BluetoothProtocol().new app_to_pcb_data_class();
        g_BluetoothProtocol.pcb_to_app_data = new GDT_BluetoothProtocol().new pcb_to_app_data_class();

        /* Read Ongoing order JSON */
        g_OnGoingOrderSharedPref = getSharedPreferences(GDT_ON_GOING_ORDER_JSON_STRING, 0);
        g_strOnGoingOrderJson = g_OnGoingOrderSharedPref.getString(ONGOING_ORDER_JSON, "");

        /* Read and settings from flash */
        g_SettingsSharedPref = getSharedPreferences(GDT_SETTINGS, 0);

        //SERVICE Settings
        g_strMoptroName = g_SettingsSharedPref.getString(MOPTRO_NAME, "");
        g_strMoptroNumber = g_SettingsSharedPref.getString(MOPTRO_NUMBER, "");
        g_strDO1Time = g_SettingsSharedPref.getString(EFP_DO1_TIME, "");
        g_strDO2Time = g_SettingsSharedPref.getString(EFP_DO2_TIME, "");
        g_strDC1Time = g_SettingsSharedPref.getString(EFP_DC1_TIME, "");
        g_strEfpTestCycles = g_SettingsSharedPref.getString(EFP_TEST_CYCLES, "");
        g_strAddSkudetails = g_SettingsSharedPref.getString(DEMO_ADD_SKU_DETAILS, "");
        //Odometer values
        g_iOdoKm = Integer.valueOf(g_SettingsSharedPref.getString(ODO_KM, "1")).intValue();
        g_iOdoMeter = Integer.valueOf(g_SettingsSharedPref.getString(ODO_METER, "1")).intValue();

        //Commands

        g_strCommand = g_SettingsSharedPref.getString(UPDATED_COMMANDS, "000c00000000");

        //Validate Service Settings
        if ((g_strMoptroName.length() <= 2) || (g_strMoptroNumber.equals(MOPTRO_NUMBER_RESET))){
            g_strMoptroName = MOPTRO_NAME_DEFAULT;
        }
        if (g_strMoptroNumber.length() <= 2){
            g_strMoptroNumber = MOPTRO_NAME_DEFAULT;
        }
        if (g_strDO1Time.length() <= 1){
            g_strDO1Time = ""+SETTINGS_EFP_MIN_DO1_TIME;
        }
        if (g_strDO2Time.length() <= 1){
            g_strDO2Time = ""+SETTINGS_EFP_MIN_DO2_TIME;
        }
        if (g_strDC1Time.length() <= 1){
            g_strDC1Time = ""+SETTINGS_EFP_MIN_DC1_TIME;
        }
        if (g_strEfpTestCycles.length() <= 1){
            g_strEfpTestCycles = ""+SETTINGS_EFP_MIN_AUTO_TEST_CYCLES;
        }
        txtGdtSettingEfpChainOpen1Time.setText(g_strDO1Time);
        txtGdtSettingEfpChainOpen2Time.setText(g_strDO2Time);
        txtGdtSettingEfpChainClose1Time.setText(g_strDC1Time);
        txtGdtSettingEfpChainTestCycles.setText(g_strEfpTestCycles);
        if (g_SettingsSharedPref.getBoolean(IMAGE_ENABLED, false)) {
            g_bEnableSkuPic = true;
        }
        if (g_SettingsSharedPref.getBoolean(ENABLE_FAKE_SCAN,false)) {
            g_bEnableFakeScan = true;
        }
        if (g_SettingsSharedPref.getBoolean(ENABLE_BATTERY_DOOR_ALERT, false)){
            g_bEnableBatteryAlert = true;
        }
        if (g_SettingsSharedPref.getBoolean(ENABLE_EFP,false)) {
            g_bEnableEfp = true;
            g_BluetoothProtocol.setAppToPcb_CMD0_EnableEFP();
        } else {
            g_bEnableEfp = false;
            g_BluetoothProtocol.setAppToPcb_CMD0_DisableEFP();
        }

        if (g_SettingsSharedPref.getBoolean(ENABLE_DOOR_SENSOR,false)) {
            g_bEnableDoorSensor = true;
            g_BluetoothProtocol.setAppToPcb_CMD0_EnableDoorSensor();
        } else {
            g_bEnableDoorSensor = false;
            g_BluetoothProtocol.setAppToPcb_CMD0_DisableDoorSensor();
        }

        if (g_SettingsSharedPref.getBoolean(ENABLE_CRATE_SENSOR,false)) {
            g_bEnableCrateSensor = true;
            g_BluetoothProtocol.setAppToPcb_CMD0_EnableCrateSensor();
        } else {
            g_bEnableCrateSensor = false;
            g_BluetoothProtocol.setAppToPcb_CMD0_DisableCrateSensor();
        }

        // taking the data from the flash memory(demo admin settings)
        if (g_SettingsSharedPref.getBoolean(ENABLE_STEP_LOGIN, false))
        {
            g_bStepLogin = true;
            chkStepLogin.setChecked(true);
        }
        if (g_SettingsSharedPref.contains(ENABLE_STEP_ORDER_PICK))
        {
            g_bStepOrderOrPick = g_SettingsSharedPref.getBoolean(ENABLE_STEP_ORDER_PICK, false);
            chkStepOrderOrPick.setChecked(g_bStepOrderOrPick);
        }
        else
        {
            g_bStepOrderOrPick = true;
            chkStepOrderOrPick.setChecked(g_bStepOrderOrPick);
        }
        if (g_SettingsSharedPref.contains(ENABLE_STEP_SCAN_CRATE))
        {
            g_bStepScanCrate = g_SettingsSharedPref.getBoolean(ENABLE_STEP_SCAN_CRATE, false);
            chkStepScanCrate.setChecked(g_bStepScanCrate);
        }
        else
        {
            g_bStepScanCrate = true;
            chkStepScanCrate.setChecked(g_bStepScanCrate);
        }
        if (g_SettingsSharedPref.contains(ENABLE_STEP_SCAN_ORDER_PICK))
        {
            g_bStepScanOrderOrPick = g_SettingsSharedPref.getBoolean(ENABLE_STEP_SCAN_CRATE, false);
            chkStepScanOrderOrPick.setChecked(g_bStepScanOrderOrPick);
        }
        else
        {
            g_bStepScanOrderOrPick = true;
            chkStepScanOrderOrPick.setChecked(g_bStepScanOrderOrPick);
        }
        if (g_SettingsSharedPref.getBoolean(ENABLE_STEP_PICK_SKU, false))
        {
            g_bStepPickSKU = true;
            chkStepPickSKU.setChecked(true);
        }
        else
        {
            g_bStepPickSKU = true;
            chkStepPickSKU.setChecked(g_bStepPickSKU);
        }
        if (g_SettingsSharedPref.contains(ENABLE_STEP_SKU_LOCATION))
        {
            g_bStepSKULOcation = g_SettingsSharedPref.getBoolean(ENABLE_STEP_SKU_LOCATION,false);
            chkStepSKULocation.setChecked(g_bStepSKULOcation);
        }
        else
        {
            g_bStepSKULOcation = true;
            chkStepSKULocation.setChecked(g_bStepSKULOcation);
        }

        txtHeaderMoptroName.setText(g_strMoptroName);
        //txtGdtSettingMoptroNumber.setText(g_strMoptroNumber);
        txtGdtSettingMoptroNumber.setText(g_strMoptroName);
        //ADMIN Settings
        g_iAlgoSelected= Integer.valueOf(g_SettingsSharedPref.getString(ALGO_SELECTED, "0")).intValue();
        g_iServerSelected= Integer.valueOf(g_SettingsSharedPref.getString(SERVER_SELECTED, "0")).intValue();
        g_strTypedIP = g_SettingsSharedPref.getString(SERVER_TYPED, "");
        Log.e(TAG,"g_strTypedIP: " + g_strTypedIP + ", Length: " + g_strTypedIP.length());

        if (g_strTypedIP.length() < 20){
            g_strTypedIP = URL_REALTIME_MOPTRO;
        }else {
            //Use the typed IP
        }
        g_strLocalUser = g_SettingsSharedPref.getString(LOCAL_USER, "");
        g_strLocalPassword = g_SettingsSharedPref.getString(LOCAL_PASSWORD, "");
        Log.e(TAG,"Final g_strTypedIP: " + g_strTypedIP + ", Length: " + g_strTypedIP.length());
        g_iMaxItemsPerCrate = Integer.valueOf(g_SettingsSharedPref.getString(MAX_ITEMS_IN_CRATE, "17")).intValue();
        if (g_iMaxItemsPerCrate <= 0){
            g_iMaxItemsPerCrate = SETTINGS_MAX_ITEMS_PER_CRATE;
        }
        //Write back to Admin screen
        spnAlgoSelection.setSelection(g_iAlgoSelected);
        spnServerSelection.setSelection(g_iServerSelected);
        txtTypedServerIp.setText(g_strTypedIP);
        txtLocalUser.setText(g_strLocalUser);
        txtLocalPassword.setText(g_strLocalPassword);
        txtItemsPerCrate.setText("" + g_iMaxItemsPerCrate);
        chkEnableFakeScan.setChecked(g_bEnableFakeScan);
        chkEnableBatteryAlert.setChecked(g_bEnableBatteryAlert);
        if (g_bEnableBatteryAlert) {

            imgBatterDoorLogo.setVisibility(View.VISIBLE);
            txtBatteryDoorLogo.setVisibility(View.VISIBLE);
        }
        else
        {
            imgBatterDoorLogo.setVisibility(View.INVISIBLE);
            txtBatteryDoorLogo.setVisibility(View.INVISIBLE);
        }


        if(g_strMoptroNumber.startsWith("MOPSNL"))
        {
            txtSysReverseStatus.setVisibility(View.INVISIBLE);
            txtSysReverseStatusLabel.setVisibility(View.INVISIBLE);
        }
        
        /* Initialize PakaConfig data for Algorithm */
        if((ALGO_PAKA_BASIC == g_iAlgoSelected) || (ALGO_PAKA_ADVANCED == g_iAlgoSelected)){
            PakaConfig.add(current_aisle);
            PakaConfig.add(x_flag);
            PakaConfig.add(y_flag);
            PakaConfig.add(block_C);
        }
        /* Enable or disable screen views based on settings */
        chkEnableFakeScan.setChecked(g_bEnableFakeScan);
        if (g_bEnableFakeScan) {
            btnFakeScan.setVisibility(View.VISIBLE);
        }else{
            btnFakeScan.setVisibility(View.INVISIBLE);
        }
        if (ALGO_PAKA_ADVANCED == g_iAlgoSelected) {
            btnNextOrder.setVisibility(View.VISIBLE);
        }else{
            btnNextOrder.setVisibility(View.INVISIBLE);
        }
        //imgDirection.setVisibility(View.INVISIBLE);

        /*  Get local Bluetooth adapter */
        if (myBtAdapter == null) myBtAdapter = BluetoothAdapter.getDefaultAdapter();

        //Update for Battery Range
        updatePhoneBatteryRange();

        //Start a Handler's for periodic operations
        crateMissingHandler = new Handler();
        stsScreenUpdateHandler = new Handler();
        btReconnectHandler = new Handler();
        btTxHandler = new Handler();
        btReconnectToutHandler = new Handler();
        serverOrderPostHandler = new Handler();
        addDiagToServerHandler = new Handler();
        addHbToServerHandler = new Handler();

        stsScreenUpdateHandler.postDelayed(stsScreenUpdateRunnable, STS_SCREEN_UPDATE_TICK_TIME);
        btReconnectHandler.postDelayed(btReconnectRunnable, BT_RECONNECT_TICK_TIME);
        //btTxHandler.postDelayed(btTxRunnable, BT_TX_TICK_TIME);
        //crateMissingHandler.postDelayed(crateMissingRunnable, CRATE_MISSING_CHECK_TICK_TIME);
        //serverOrderPostHandler.postDelayed(serverPostOrderRunnable, SERVER_POST_CHECK_TICK_TIME);
        //addDiagToServerHandler.postDelayed(addDiagToServerRunnable,HB_DIAG_MISSING_UPDATE_TICK_TIME);
        addHbToServerHandler.postDelayed(addHbToServerRunnable,ADD_HB_TO_SERVER_TICK_TIME);
        btTxHandler.postDelayed(btTxRunnable, BT_TX_TICK_TIME);
        crateMissingHandler.postDelayed(crateMissingRunnable, CRATE_MISSING_CHECK_TICK_TIME);
        serverOrderPostHandler.postDelayed(serverPostOrderRunnable, SERVER_POST_CHECK_TICK_TIME);
        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        String url = URL_REALTIME_MOPTRO + PICKER_USER + g_strMoptroName;
        mRequestQueue = Volley.newRequestQueue(this);

        // String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject loginResponseObject = new JSONObject(response);
                    g_strUserDetails = loginResponseObject.getString("data");
                    // UPADTING THE VALUES IN THE SHARED PREFRENCES
                    commit_LoginDetails(g_strUserDetails);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                g_strUserDetails = g_SettingsSharedPref.getString(PICKER_CREDENTIALS, "");
            }
        }){
            @Override
            public Map<String,String> getHeaders() {
                Map<String,String> params= new HashMap<>();
                params.put(API_KEY_NAME, API_KEY);
                return params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "+ ON START +");
        /* Turn ON bluetooth if not Enabled */
        if (!myBtAdapter.isEnabled()) {
            txtBtStatus.setText("Turned OFF");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            /* Bluetooth Already ON. Check for paired Device if Connection status is NONE */
            createBluetoothScannerService();
            if (myBtScannerService.getState() == BluetoothSerialService.STATE_NONE) {
                getBluetoothDevice();
            }
        }
        /* Start Wifi Connection */
        startWifiConnectionManager(this);
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        Log.e(TAG, "+ ON RESUME +");

        /* Exit if BT adapter is NOT ON */
        if(!myBtAdapter.isEnabled()) {
            Log.e(TAG, "BT not ON");

        }else {
            // Performing this check in onResume() covers the case in which BT was
            // not enabled during onStart(), so we were paused to enable it...
            // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
            if ((myBtScannerService != null) && (myBtDevice != null) &&
                    (myBtScannerService.getState() == BluetoothSerialService.STATE_NONE)) {
                /* Connect to the BT Device */
                Log.e(TAG, "Connect to BT device: " + myBtDevice.getName().toString());
                myBtScannerService.connect(myBtDevice);
            }else{
                Log.e(TAG, "BT Connect skipped with below status:" + '\n' +
                        "myBtScannerService: " + myBtScannerService + '\n' +
                        "myBtDevice: " + myBtDevice + '\n' +
                        "Bt state: " + myBtScannerService.getState());
            }
        }
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        Log.e(TAG, "- ON PAUSE -");
        //unregisterReceiver(myBroadcastReceiver);

        //Below is feature of u32ErrorCode Alert box On pause of activity the alert sound will stop
        super.onPause();
        stopAudioTimer();
        if (isPlayingSound) {
            isPlayingSound = false;
            stopAlertSound();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //super.onSaveInstanceState(savedInstanceState);
        Log.e(TAG, "-- ON STOP --");
        myBroadcastReceiverRegistered = false;
        getBaseContext().unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "--- ON DESTROY ---");
    }

    @Override
    public void onBackPressed() {
        /* Just Do notning */
        Log.e(TAG, "Ignore the Back Button");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        /* Hold Order Screen Processing */
        if (v.getId() == R.id.BtnPickHoldOrder) {
            new ataskSetHoldOrderAck().execute();
        }

//        if (v.getId() == R.id.FaceLogin) {
//            if (ContextCompat.checkSelfPermission(this, CAMERA) == PERMISSION_GRANTED) {
//                openCamera();
//            } else {
//                // Request camera permission
//                ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA_PERMISSION);
//            }
//        }




        if (v.getId() == R.id.BtnPostHoldOrder) {
            //Log.e(TAG, "Selected Order is Paused.");
            String post_order_pin = txtOrderPostPin.getText().toString().replace(" ", "");
            txtOrderPostPin.setText("");
            post_order_pin = post_order_pin.trim();
            if (post_order_pin.equals(GDT_GlobalData.ADMIN_ORDER_FORCE_CLOSE_PIN)) {
                String[] optionArray = new String[1];
                optionArray[0] = "Post Order";
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Order# " + g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].orderid);
                builder.setSingleChoiceItems(optionArray, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int index) {
                        g_iUserOption = index;
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (0 == g_iUserOption) {
                            /* Post Order */
                            g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].order_status
                                    = ORDER_STS_CLOSED_POST_PENDING;
                            update_PauseTime();
                            g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].stop_time_stamp =
                                    getCurrentTimeStamp();
                            g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                            updateScreenViewOnAppStatusChange();
                        } else {
                            /* No option selected */
                        }
                        g_iUserOption = -1;
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        g_iUserOption = -1;
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }else{
                Toast.makeText(this, "Error: Please Enter a Valid POST PIN.", Toast.LENGTH_SHORT).show();
            }
        }

        if (v.getId() == R.id.BtnHoldOrderReturn) {
            clear_HoldSkuDetail();
            g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
            updateScreenViewOnAppStatusChange();
        }

        if (v.getId() == R.id.BtnReturnToOrderList) {
            if (update_orderSts()) {
                g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                updateScreenViewOnAppStatusChange();
            }
        }

        /* Refresh Order list */
        if (v.getId() == R.id.ImgRefreshOrder) {
            /* Refresh is allowed only in Order list screen */
            if (g_eAppSts == enumAppStatus.APP_STS_ORDER_LIST)
            {
                if (isDemoUser)
                {
                    if (isOrderCompleted)
                    {
                        {
                            AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                            dialog.setMessage("No other orders ");
                            dialog.setTitle("Dialog Box");
                            dialog.setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                    }
                                });
                            AlertDialog alertDialog=dialog.create();
                            alertDialog.show();
                        }
                    }
                    else
                    {
                        if (g_strAddSkudetails.isEmpty())
                        {
                             AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                            dialog.setMessage("No other orders ");
                            dialog.setTitle("Dialog Box");
                            dialog.setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                    }
                                });
                            AlertDialog alertDialog=dialog.create();
                            alertDialog.show();
                        }
                        else
                        {
                            g_progressDialog.setTitle("**** ORDER DOWNLOAD ****");
                            g_progressDialog.setMessage("Downloading Assigned Order List. Please wait...");
                            g_progressDialog.show();
                            applyCustomAdapter();
                        }
                    }
                }
                else
                {
                    /* Download new orders */
                    g_eAppSts = enumAppStatus.APP_STS_SERVER_ORDER_DOWNLOAD;
                    /* Reset Order list and Custom Adapter */
                    g_OrdersClass_fromServer = null;
                    g_OrdersClass_fromServer = new OrderClass();
                    applyCustomAdapter();
                    new ataskDownloadOrderAndHoldList().execute(g_strUserName);
                }
            }


            else {
                /* Skip refresh */
                Log.e(TAG, "ERR: Refresh skipped in AppSts: " + g_eAppSts);
            }

        }

        /* Refresh SKU */
        if (v.getId() == R.id.ImgRefreshSku) {
            if (isDemoUser)
            {
                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setMessage("This functionality will work after full integration with WMS");
                dialog.setTitle("");
                dialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=dialog.create();
                alertDialog.show();
            }
            /* Refresh is allowed only in Order list screen */
            else if (g_eAppSts == enumAppStatus.APP_STS_SCAN_SKU) {
                g_progressDialog.setTitle("***** REFRESH SKU *****");
                g_progressDialog.setMessage(getString(R.string.loading_dialog_message));
                g_progressDialog.show();
                /* Download new orders */
                new ataskRefreshSkuCode().execute(g_strUserName);
            } else {
                /* Skip refresh */
                Log.e(TAG, "ERR: SKU Refresh skipped in AppSts: " + g_eAppSts);
            }
        }

        if (v.getId() == R.id.TxtCalibrateEfp) {
            if (myBtScannerService.getState() != BluetoothSerialService.STATE_CONNECTED) {
                Toast.makeText(this, "Error: Bluetooth is NOT connected.", Toast.LENGTH_SHORT).show();
                return;
            }
            switch (g_eAppSts) {
                // All these states use the same calibration logic
                case APP_STS_LOGIN:
                case APP_STS_MOPTRO_SCREEN:
                case APP_STS_ADMIN_SCREEN:
                case APP_STS_SERVICE_SCREEN:
                case APP_STS_ORDER_LIST:
                case APP_STS_SCAN_CRATE:
                case APP_STS_SCAN_ORDER:
                case APP_STS_SCAN_SKU: {
                    appStatusBeforeCalibrate = g_eAppSts;
                    g_eAppSts = enumAppStatus.APP_STS_CALIBRATE_EPF;
                    Log.e(TAG, "Calibrate EPF");
                    txtCalibrateEfp.setText("Initializing EFP. Please Wait....");
                    txtCalibrateEfp.setBackgroundResource(R.color.bbRed);
                    g_progressDialog.setTitle("**** EFP CALIBRATION ****");
                    g_progressDialog.setMessage("Place Hand to cut Curtain Sensor when Door waits in Open state.");
                    g_progressDialog.show();
                    g_BluetoothProtocol.setAppToPcb_CMD1_CalibrateEfp();
                    send_AppToPcbFrame();
                    g_BluetoothProtocol.setAppToPcb_CMD1_Clear();
                    break; // MAKE SURE TO BREAK HERE
                }
                case APP_STS_VOICE_HELLO: {
                    // Hide current active screen
                    frameActive.setVisibility(View.INVISIBLE);
                    // Inflate the new layout
                    setContentView(R.layout.activity_voice_hello);
                    break;
                }
                default:
                    Log.e(TAG, "Ignore Calibrate in State: " + g_eAppSts);
                    break;
            }
        }

        if (v.getId() == R.id.BtnEfpCycleTest) {
            if (myBtScannerService.getState() != BluetoothSerialService.STATE_CONNECTED) {
                Toast.makeText(this, "Error: Bluetooth is NOT connected.", Toast.LENGTH_SHORT).show();
                return;
            }
            switch (g_eAppSts) {
                case APP_STS_SERVICE_SCREEN: {
                    if (g_bEfpTestOngoing) {
                        g_bEfpTestOngoing = false;
                        /* Test Ongoing. Stop it */
                        Log.e(TAG, "Stop EPF Cycle Test");
                        btnEfpCycleTest.setText("START: EFP Test");
                        btnEfpCycleTest.setBackgroundResource(R.color.gray_dark);
                        g_BluetoothProtocol.setAppToPcb_CMD1_EfpTestCycleStop();
                        send_AppToPcbFrame();
                        g_BluetoothProtocol.setAppToPcb_CMD1_Clear();
                    } else {
                        g_bEfpTestOngoing = true;
                        Log.e(TAG, "EPF Cycle Test");
                        btnEfpCycleTest.setText("STOP: EFP Test");
                        btnEfpCycleTest.setBackgroundResource(R.color.bbRed);
                        g_BluetoothProtocol.setAppToPcb_CMD1_EfpTestCycleStart();
                        send_AppToPcbFrame();
                        g_BluetoothProtocol.setAppToPcb_CMD1_Clear();
                    }
                }
                break;

                default:
                    //Log.e(TAG,"Ignore EFP Test cycle in State: "+g_eAppSts);
                    Toast.makeText(this, "ERR: Only SERVICE Login can initiate this.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        /* Reset PCB Errors. For now it is only DOOR Err count */
        if (v.getId() == R.id.BtnResetDoorJamErr) {
//            if (myBtScannerService.getState() != BluetoothSerialService.STATE_CONNECTED) {
//                Toast.makeText(this, "Error: Bluetooth is NOT connected.", Toast.LENGTH_SHORT).show();
//            }else if (g_BluetoothProtocol.getIsDoorJammedError()){
//                g_BluetoothProtocol.setAppToPcb_CMD1_ResetErrors();
//                //set request for reset Errors
//                send_AppToPcbFrame();
//                g_BluetoothProtocol.setAppToPcb_CMD1_Clear();
//                Toast.makeText(this, "INFO: RESET Errors Request sent", Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(this, "INFO: Door Not Jammed", Toast.LENGTH_SHORT).show();
//            }
            //new ataskAddDiag().execute();
        }

        /* Reset PCB Errors. For now it is only DOOR Err count */
        if (v.getId() == R.id.BtnResetErrors) {
            if (myBtScannerService.getState() != BluetoothSerialService.STATE_CONNECTED) {
                Toast.makeText(this, "Error: Bluetooth is NOT connected.", Toast.LENGTH_SHORT).show();
                return;
            }
            switch (g_eAppSts) {
                case APP_STS_SERVICE_SCREEN:
                    Log.e(TAG, "EPF Cycle Test");
                    g_BluetoothProtocol.setAppToPcb_CMD1_ResetErrors();
                    //set request for reset Errors
                    send_AppToPcbFrame();
                    g_BluetoothProtocol.setAppToPcb_CMD1_Clear();
                    Toast.makeText(this, "INFO: RESET Errors Request sent", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    //Log.e(TAG,"Ignore EFP Test cycle in State: "+g_eAppSts);
                    Toast.makeText(this, "ERR: Only SERVICE Login can initiate this.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
//        /* Reset Scanner. For now it is only DOOR Err count */
//        if (v.getId() == R.id.BtnResetScanner) {
//            if (myBtScannerService.getState() != BluetoothSerialService.STATE_CONNECTED) {
//                Toast.makeText(this, "Error: Bluetooth is NOT connected.", Toast.LENGTH_SHORT).show();
//                return;
//            } else {
//                g_BluetoothProtocol.setAppToPcb_CMD1_ResetScanner();
//                send_AppToPcbFrame();
//                g_BluetoothProtocol.setAppToPcb_CMD1_Clear();
//                Toast.makeText(this, "INFO: RESET Scanner Request sent", Toast.LENGTH_SHORT).show();
//            }
//        }
        /* Reset PCB */
        if (v.getId() == R.id.BtnResetPcb) {
            if (myBtScannerService.getState() != BluetoothSerialService.STATE_CONNECTED) {
                Toast.makeText(this, "Error: Bluetooth is NOT connected.", Toast.LENGTH_SHORT).show();
                return;
            }
            switch (g_eAppSts) {
                case APP_STS_SERVICE_SCREEN:
                    g_BluetoothProtocol.setAppToPcb_CMD1_ResetPCB();
                    //set request for reset pcb
                    send_AppToPcbFrame();
                    g_BluetoothProtocol.setAppToPcb_CMD1_Clear();
                    g_progressDialog.setTitle("***** RESET PCB *****");
                    g_progressDialog.setMessage("Resetting. Please wait....");
                    g_progressDialog.show();
                    g_eAppSts = enumAppStatus.APP_STS_RESET_PCB_REQUEST;
                    //Toast.makeText(this, "INFO: RESET PCB Request sent", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    //Log.e(TAG,"Ignore EFP Test cycle in State: "+g_eAppSts);
                    Toast.makeText(this, "ERR: Only SERVICE Login can initiate this.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        if ((v.getId() == R.id.BtnLogout)) {
            //Check for Login Status
            if (LOGIN_USER == g_BluetoothProtocol.getAppToPcb_LoginStatus()){
                if (enumAppStatus.APP_STS_ORDER_LIST == g_eAppSts) {
                    g_eAppSts = enumAppStatus.APP_STS_LOGIN;
                /* Send Scanner Inactivate */
                    btSend_BarcodeNoSts_ScannerInActivate();
                    updateScreenViewOnAppStatusChange();
                }
            }else{
                g_iPickerLoggedIn = 0;
                generateHBForMCB();
                g_eAppSts = enumAppStatus.APP_STS_LOGIN;
                /* Send Scanner Inactivate */
                btSend_BarcodeNoSts_ScannerInActivate();
                updateScreenViewOnAppStatusChange();
                isOrderCompleted = false;
            }
            g_bLoggedIn = false;
        }

        if(v.getId() == R.id.BtnStatusLogout)
        {
            g_eAppSts = enumAppStatus.APP_STS_LOGIN;
            /* Send Scanner Inactivate */
            btSend_BarcodeNoSts_ScannerInActivate();
            updateScreenViewOnAppStatusChange();
            isOrderCompleted = false;
            g_bLoggedIn = false;
        }


        if (v.getId() == R.id.ImgSettingsLogo) {

            if (View.INVISIBLE == frameSystemStatus.getVisibility()) {
                if (frameActive != null) frameActive.setVisibility(View.INVISIBLE);
                frameSystemStatus.setVisibility(View.VISIBLE);
            } else {
                if (frameActive != null) frameSystemStatus.setVisibility(View.INVISIBLE);
                frameActive.setVisibility(View.VISIBLE);
            }

        }

        if (v.getId() == R.id.BtnLogin) {
            /* Reset Order list and Custom Adapter */
            g_OrdersClass_fromServer = null;
            g_OrdersClass_fromServer = new OrderClass();
            applyCustomAdapter();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		    /* Fetch User name and Password */
            g_strUserName = txtUserName.getText().toString().replace(" ", "");
            g_strUserName = g_strUserName.trim();
            String strPassword = txtPassword.getText().toString().replace(" ", "");
            strPassword = strPassword.trim();

            g_bLoggedIn = false;
            if (g_strUserName.length() == 0) {
                Toast.makeText(this, "Error: Please type User name.", Toast.LENGTH_LONG).show();
            } else if (strPassword.length() == 0) {
                Toast.makeText(this, "Error: Please type password.", Toast.LENGTH_LONG).show();
            } else {
			    /* Clear off username and password for next login */
                txtPassword.setText("");
                g_strUserNameUcase = g_strUserName.toUpperCase();

                //Temporary Change
			    /* Check for Service login verification */
                if (g_strUserName.equals(GDT_GlobalData.SERVICE_USERNAME)){
                    if (strPassword.equals(GDT_GlobalData.SERVICE_PASSWORD)) {
                        txtUserName.setText("");
                        txtPickerId.setText("" + g_strUserNameUcase);
                        g_eAppSts = enumAppStatus.APP_STS_SERVICE_SCREEN;
                        g_bLoggedIn = true;
                        updateScreenViewOnAppStatusChange();
                        g_iPickerLoggedIn = 2;
                        generateHBForMCB();
                    }else{
                        Toast.makeText(this, "Error: Incorrect password.", Toast.LENGTH_LONG).show();
                    }
                }
			    /* Check for admin login verification */
                else if (g_strUserName.equals(GDT_GlobalData.ADMIN_USERNAME)){
                    if (strPassword.toString().equals(GDT_GlobalData.ADMIN_PASSWORD)) {
                        txtUserName.setText("");
                        txtPickerId.setText("" + g_strUserNameUcase);
                        g_eAppSts = enumAppStatus.APP_STS_ADMIN_SCREEN;
                        updateScreenViewOnAppStatusChange();
                        g_bLoggedIn = true;
                    }else{
                        Toast.makeText(this, "Error: Incorrect password.", Toast.LENGTH_LONG).show();
                    }
                }
			    /* Check for MOPTro Login verification */
                else if (g_strUserName.equals(GDT_GlobalData.MOPTRO_USERNAME)){
                    if (strPassword.toString().equals(GDT_GlobalData.MOPTRO_PASSWORD)) {
                        txtPickerId.setText("" + g_strUserNameUcase);
                        g_iPickerLoggedIn = 2;
                        generateHBForMCB();
                        frameActive.setVisibility(View.INVISIBLE);
                        frameActive = frameSystemStatus;
                        frameActive.setVisibility(View.VISIBLE);
                        g_bLoggedIn = true;
                    }else{
                        Toast.makeText(this, "Error: Incorrect password.", Toast.LENGTH_LONG).show();
                    }
                }
                /* Check for Local user verification */
                else if (g_strUserName.equals(g_strLocalUser)){
                    if (strPassword.toString().equals(g_strLocalPassword)) {
                        txtUserName.setText("");
                        txtPickerId.setText("" + g_strUserNameUcase);
                        g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                        updateScreenViewOnAppStatusChange();
                        g_bLoggedIn = true;
                    }else{
                        Toast.makeText(this, "Error: Incorrect password.", Toast.LENGTH_LONG).show();
                    }
                }
				/* Check for Demo Admin login */
				else if (g_strUserName.equals(GDT_GlobalData.DEMO_ADMIN)){
                    if (strPassword.toString().equals(GDT_GlobalData.DEMO_ADMIN_PASSWORD)) {
                        txtUserName.setText("");
                        txtPickerId.setText("" + g_strUserNameUcase);
                        g_eAppSts = enumAppStatus.APP_STS_DEMO_ADMIN;
                        updateScreenViewOnAppStatusChange();
						g_bLoggedIn = true;
                    }else{
                        Toast.makeText(this, "Error: Incorrect password.", Toast.LENGTH_LONG).show();
                    }
                }
				/* Check for Demo User login */
				else if (g_strUserName.equals(GDT_GlobalData.DEMO_USER)){
                    if (strPassword.toString().equals(GDT_GlobalData.DEMO_USER_PASSWORD)) {
                        isDemoUser = true;
                        txtUserName.setText("");
                        txtPickerId.setText("" + g_strUserNameUcase);
                        UpdateDemoUserScreen();
                        isOrderCompleted = false;
                        showSKULocation = false;
                        scanSKULocation = false;
                        lastSkuLocation = false;
			 			g_bLoggedIn = true;
                    }else{
                        Toast.makeText(this, "Error: Incorrect password.", Toast.LENGTH_LONG).show();
                    }
                }

                else if (g_strUserName.equals(GDT_GlobalData.VOICE_USERNAME)) {
                    if (strPassword.equals(GDT_GlobalData.VOICE_PASSWORD)) {
                        setContentView(R.layout.activity_voice_hello);
                        txtUserName.setText("");
                        txtPickerId.setText("" + g_strUserNameUcase);
                        g_eAppSts = enumAppStatus.APP_STS_VOICE_HELLO;
                        updateScreenViewOnAppStatusChange();
                        g_bLoggedIn = true;
                    } else {
                        Toast.makeText(this, "Error: Incorrect password.", Toast.LENGTH_LONG).show();
                    }
                }

			    /* Go to User Login */
                else {
                    if(g_strUserDetails.isEmpty())
                    {
                        Toast.makeText(this, "Error: Connect to internet and restart the app", Toast.LENGTH_LONG).show();
                    }
                    else if(g_strHbJsonFromMCB.isEmpty())
                    {
                        Toast.makeText(this, "Error: Wait for vehicle BT connectivity", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        validateLoginCredentials(g_strUserName, strPassword.toString());
                    }
                    txtUserName.setText("");

                    //new ataskCheckUser().execute(g_strUserName); //goes in background to Login an user
                }
            }
        }
        if (v.getId() == R.id.BtnServiceExit) {
            g_eAppSts = enumAppStatus.APP_STS_LOGIN;
            g_bLoggedIn = false;
            g_iPickerLoggedIn = 0;
            generateHBForMCB();
            updateScreenViewOnAppStatusChange();
        }
        if (v.getId() == R.id.BtnServiceSetting) {
            g_eAppSts = enumAppStatus.APP_STS_SERVICE_SETTINGS_SCREEN;
            updateScreenViewOnAppStatusChange();
        }
        if (v.getId() == R.id.BtnServiceSettingClose) {
            g_eAppSts = enumAppStatus.APP_STS_SERVICE_SCREEN;
            updateScreenViewOnAppStatusChange();
        }
        if (v.getId() == R.id.BtnDemoAdminSettingsClose)
        {
            g_eAppSts = enumAppStatus.APP_STS_LOGIN;
            updateScreenViewOnAppStatusChange();
        }
        if (v.getId() == R.id.BtnServieSettingUpdate)
        {
            if (v.getId() == R.id.BtnServieSettingUpdate) {
                g_eAppSts = enumAppStatus.APP_STS_SERVICE_SETTINGS_SCREEN_UPDATE;
                commit_ServiceSettings();
                /* Start progress dialog and wait for System ready from PCB */
                g_progressDialog.setTitle("***** SERVICE SETTINGS *****");
                g_progressDialog.setMessage("Update sent to PCB. Please wait for few seconds....");
                g_progressDialog.show();
            }
        }
        if (v.getId() == R.id.TxtBTStatus) {
		/* Resume BT connection if needed */
            resumeBtConnection();
        }

        if (v.getId() == R.id.BtnAdminSettingsUpdate) {
            //Update Admin settings to to flash memory and update the Variables
            commit_AdminSettings();
            //g_eAppSts = enumAppStatus.APP_STS_LOGIN;
            //updateScreenViewOnAppStatusChange();
        }

        if (v.getId() == R.id.BtnDemoAdminSettingsUpdate)
        {
            //update the Demo Admin setting to the flash memory
            Commit_DemoAdminSettings();

            //THIS CODE IS USED FOR THE ALERT DIALOG BOX

            AlertDialog.Builder dialog=new AlertDialog.Builder(this);
            dialog.setMessage("Would you like go to login page");
            dialog.setTitle("Updated the picking sequence");
            dialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            g_eAppSts = enumAppStatus.APP_STS_LOGIN;
                            updateScreenViewOnAppStatusChange();
                        }
                    });
            dialog.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog=dialog.create();
            alertDialog.show();

        }
        if (v.getId() == R.id.BtnAdminSettingsClose) {
            g_eAppSts = enumAppStatus.APP_STS_LOGIN;
            updateScreenViewOnAppStatusChange();
        }
        // Button name yet to be changed to cancel
        if (v.getId() == R.id.BtnAddSkuCancel)
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(GDT_Main.this);
            dialog.setMessage("Do You want to erase the details");

            dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    g_bitemsSelected.clear();
                    txtAddOrderNumber.setText("");
                    txtAddSkuQuantity.setText("");
                    txtAddSkuDesc.setText("");
                    txtAddSkuLocation.setText("");
                    txtAddSkuBarcode.setText("");
                    g_eAppSts = enumAppStatus.APP_STS_LOGIN;
                    updateScreenViewOnAppStatusChange();
                }
            });
            //Toast.makeText(this, "Updated SkuDetails", Toast.LENGTH_LONG).show();
            // updating the value in flash memory for the demo purpose
            AlertDialog alertDialog=dialog.create();
            alertDialog.show();
        }

        if (v.getId() == R.id.BtnDemoAdminSettingsAddSku)
        {
            isDemoUser = true;
            txtUserName.setText("");
            txtPickerId.setText("" + g_strUserNameUcase);
            g_eAppSts = enumAppStatus.APP_STS_ADD_ORDER;
            updateScreenViewOnAppStatusChange();
            g_bLoggedIn = true;
            txtAddOrderNumber.setText("");
            txtAddSkuQuantity.setText("");
            txtAddSkuDesc.setText("");
            txtAddSkuLocation.setText("");
            txtAddSkuBarcode.setText("");
        }

        if (v.getId() == R.id.BtnSubmitSKU)
        {
            if (txtAddSkuQuantity.getText().toString().isEmpty() ||
                    txtAddSkuDesc.getText().toString().isEmpty() ||
                    txtAddSkuLocation.getText().toString().isEmpty() ||
                    txtAddSkuBarcode.getText().toString().isEmpty()||
                    txtAddSkuQuantity.getText().toString().equals("0"))
            {
                SubmitSkuDetails();
                g_eAppSts = enumAppStatus.APP_STS_LOGIN;
                updateScreenViewOnAppStatusChange();

            }
            else
            {
                try
                {
                    AddSkuDetails();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to add the SKU", Toast.LENGTH_SHORT).show();
                    return;
                }
                SubmitSkuDetails();
                g_eAppSts = enumAppStatus.APP_STS_LOGIN;
                updateScreenViewOnAppStatusChange();
            }
        }

//        if (v.getId() == R.id.BtnUpdateVin)
//        {
//            Button sample = null;
//            final String[] vinNumber = new String[3];
//            vinNumber[0] = "MOPSNL-2021";
//            // BUILD THE ALERT BUILDER
//            AlertDialog.Builder updateVinBuilder = new AlertDialog.Builder(this);
//            View updateVinView = getLayoutInflater().inflate(R.layout.vinupdate, null);
//            updateVinBuilder.setTitle("NEW VIN");
//            Spinner monthsList = (Spinner) updateVinView.findViewById(R.id.SpnMonth);
//            Spinner runningNumberList = (Spinner) updateVinView.findViewById(R.id.SpnRunningNumber);
//            TextView tvInvisibleError = (TextView)updateVinView.findViewById(R.id.tvInvisibleError);
//
//            runningNumberList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//                {
//                    if (position == 0 || monthsList.getSelectedItem().toString().equals("select number"))
//                    {
//                        vinUpdateDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//                    }
//                    else
//                    {
//                        vinUpdateDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
//                    }
//                    vinNumber[2] = runningNumber.get(position);
//                }
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
//
//            monthsList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    if (position == 0)
//                    {
//                        vinUpdateDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//                    }
//                    else
//                    {
//                        vinUpdateDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
//                    }
//                    vinNumber[1] = monthList.get(position);
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
//
//            updateVinBuilder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog,
//                                    int which) {
//
//                    g_eAppSts = enumAppStatus.APP_STS_SERVICE_SETTINGS_SCREEN_UPDATE;
//                    g_BluetoothProtocol.setAppToPcb_LoginStatus(LOGIN_SERVICE);
//                    send_AppToPcbFrame();
//                    updateService_Screen();
//                    commit_ServiceSettings2(vinNumber[0] + vinNumber[1] + vinNumber[2]);
////                    SubmitSkuDetails();
////                    g_eAppSts = enumAppStatus.APP_STS_LOGIN;
////                    updateScreenViewOnAppStatusChange();
//                }
//            });
//            updateVinBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                }
//            });
//
//            // Create the instance of ArrayAdapter
//            // having the list of courses
//            ArrayAdapter monthAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, monthList);
//            ArrayAdapter runningNumberAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, runningNumber);
//            monthAdapter.setDropDownViewResource(
//                    android.R.layout
//                            .simple_spinner_dropdown_item);
//            runningNumberAdapter.setDropDownViewResource(
//                    android.R.layout
//                            .simple_spinner_dropdown_item);
//            monthsList.setAdapter(monthAdapter);
//            runningNumberList.setAdapter(runningNumberAdapter);
//            updateVinBuilder.setView(updateVinView);
//            vinUpdateDialog = updateVinBuilder.create();
//            vinUpdateDialog.show();
////
////            /* Start progress dialog and wait for System ready from PCB */
////            g_progressDialog.setTitle("***** SERVICE SETTINGS *****");
////            g_progressDialog.setMessage("Update sent to PCB. Please wait for few seconds....");
////            g_progressDialog.show();
//        }

        // THIS BUTTON IS FOR THE STATUS SCREEN
        if (v.getId() == R.id.BtnStatus)
        {
            g_eAppSts = enumAppStatus.APP_STS_STATUS;
            updateScreenViewOnAppStatusChange();
        }

        if (v.getId() == R.id.BtnAddSku)
        {
            g_eAppSts = enumAppStatus.APP_STS_ADD_SKU_DEMO;
            txtSkuCountValue.setText("1");
            // THIS IS REQUIRED IF THE USER AFTER ADDING THE SKU AND DEMOSTRATION IS DONE AND HE IS NOT CLOSED THE APP
            // AND THEY ARE USING THE APP AGAIN THEN THERE WILL JUNK VALUE SO WE ARE CLEARING IT.
            if (g_bitemsSelected.size() > 0);
            {
                g_bitemsSelected.clear();
            }
            updateScreenViewOnAppStatusChange();
        }

        if (v.getId() == R.id.BtnAddSkuNext)
        {
            try
            {
                if (signUp())
                {
                    AddSkuDetails();
                    AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                    dialog.setTitle("Sku Added to order");
                    dialog.setMessage("Select Next Action");
                    dialog.setPositiveButton("Submit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    SubmitSkuDetails();
                                    g_eAppSts = enumAppStatus.APP_STS_LOGIN;
                                    updateScreenViewOnAppStatusChange();
                                }
                            });
                    dialog.setNegativeButton("Add Next SKU",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    txtAddSkuDesc.requestFocus();
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog=dialog.create();
                    alertDialog.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to add the SKU", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (v.getId() == R.id.BtnSkipSku) {
            if (g_eAppSts == enumAppStatus.APP_STS_SCAN_SKU) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                int order_index = g_OrdersClass_fromServer.order_index;
                int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;
                builder.setTitle("" + g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc);
                builder.setMessage("Are you sure to skip this item??");

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
					/* Do nothing */
                    }
                });

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sku_skip();
                        updateScreenViewOnAppStatusChange();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                Toast.makeText(this, "Can't Skip now. Try later", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Close crate Request in g_eAppSts: " + g_eAppSts);
            }
        }

        if (v.getId() == R.id.BtnCloseCrate) {
            if (g_eAppSts == enumAppStatus.APP_STS_SCAN_SKU) {
                int order_index = g_OrdersClass_fromServer.order_index;
                int scanned_label_index = g_OrdersClass_fromServer.order_list[order_index].scanned_label_index;
                int crate_item_qty = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).crate_item_qty;
                if (crate_item_qty > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                    int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;
                    builder.setTitle("Sku qty in Crate: " + crate_item_qty);
                    builder.setMessage("Are you sure to close this crate??");

                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
					    /* Do nothing */
                        }
                    });

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            /* Manual close crate command */
                            if (enumAppStatus.APP_STS_SCAN_SKU == g_eAppSts) {
                                txtCrateRfid.setText(NO_CRATE_SCANNED);
                                txtOrderScanTitle.setText(SCAN_EMPTY_CRATE);
                                g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE;
                                updateScreenViewOnAppStatusChange();
                            }else{

                            }
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(this, "No item in crate. Can't close.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Can't close crate now. Try later", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Close crate Request in g_eAppSts: "+g_eAppSts);
            }
        }

        if (v.getId() == R.id.BtnFakeScan) {
            if (g_bEnableFakeScan) {
                int order_index = g_OrdersClass_fromServer.order_index;
                int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;
                if (g_eAppSts == enumAppStatus.APP_STS_SCAN_SKU) {
                    g_strSkuEanLabel = g_OrdersClass_fromServer.order_list[order_index].items[item_index].ean[0];
                    char[] dummyarray = new char[10];
                    if (showSKULocation && isDemoUser && g_bStepSKULOcation)
                    {
                        UpdateScreenForSKULocation();
                    }
                    else
                    {
                        UpdateBackGroundColorForSkuScan();
                        txtOrderScanTitle.setText(SCAN_SKU);
                        validateSkuBarcode("00000000");
                    }
                    //  this method has to be changed for the multiple quantities if required
                } else if (g_eAppSts == enumAppStatus.APP_STS_SCAN_CRATE) {
                    char[] dummybytes = new char[10];
                    if (g_OrdersClass_fromServer.order_list[order_index].order_status == ORDER_STS_RESUMED_USED_CRATE) {
                        if (g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size() > 0){
                            g_OrdersClass_fromServer.order_list[order_index].scanned_label_index = 0;
                            g_strCrateLabel = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(0).crate_label;
                        }else{
                            g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_RESUMED_NEW_CRATE;
                            g_strCrateLabel = "CB-Crate# " + (g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].post_label_list_Array.size() + 1);
                        }
                    }else {
                        g_strCrateLabel = "CB-Crate# " + (g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].post_label_list_Array.size() + 1);
                    }
                    validateCrate("00000000");
                } else if (g_eAppSts == enumAppStatus.APP_STS_SCAN_ORDER) {
				/* Frame Order Label */
                    String order_label = "";
                    int scanned_label_index = g_OrdersClass_fromServer.order_list[order_index].scanned_label_index;
                    int label_list_qty = g_OrdersClass_fromServer.order_list[order_index].label_list_qty;
                    if (label_list_qty > 0) {
                        order_label = g_OrdersClass_fromServer.order_list[order_index].label_list[0];
                        order_label = order_label.substring(0, ((order_label.length()) - 1));
                        order_label = order_label + "" + (scanned_label_index + 1);
                    } else {
                        order_label = "" + g_OrdersClass_fromServer.order_list[order_index].orderid + " label " + (scanned_label_index + 1);
                    }
                    g_strOrderLabel = order_label;
                    char[] dummyarray = new char[10];
                    validateOrderBarcode("00000000");
                }
            }
        }

        if (v.getId() == R.id.BtnNextOrder) {
            if (ALGO_PAKA_ADVANCED == g_iAlgoSelected) {
                Toast.makeText(this, "Please change to BASIC ALGORITHM in ADMIN settings",
                        Toast.LENGTH_SHORT).show();
                //TODO: Check this out. Functionality not implemented
                //Call_PAKAAdvanced();
            } else {
                Toast.makeText(this, "ERR: Next button should have been disabled in BASIC ALGORITHM. " +
                        "Please Contact GREENDZINE Team.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private  Runnable stsScreenUpdateRunnable = new Runnable() {
        @Override
        public void run() {
        /* Do Cyclic checks here */
        /* Update phone battery status */
        updatePhoneBatteryRange();
        /* Update Moptro Range */
        //updateMoptroRange();
        updateService_Screen();

            //TODO: Update all of the below
//        txtStsScreenVoltage.setText(g_BluetoothProtocol.pcb_to_app_data.mop_bat_v + " Volts");
//        txtStsScreenCurrent.setText(g_BluetoothProtocol.pcb_to_app_data.bldc_amps + " Amps");
//        txtEvChargingCurrent.setText(g_BluetoothProtocol.pcb_to_app_data.charger_amps + " Amps");
//        txtStsScreenOdometer.setText(g_BluetoothProtocol.pcb_to_app_data.u16odoKmeter+"."+g_BluetoothProtocol.pcb_to_app_data.u16odometer+ " KMs");

           // BluetoothSerialService obj = new BluetoothSerialService(getApplicationContext(), myBluetoothHandler);
            //txtStsScreenVoltage.setText(""+ values.fBatVoltage+" Volts");
            txtStsScreenVoltage.setText(""+ values.fBatVoltage_O+" Volts"); //SOC voltage

            txtStsScreenCurrent.setText(""+ values.fLoadAmps+" / "+
                    values.fLoadAmps_O+" Amps");

            txtEvChargingCurrent.setText(""+ values.fChargeAmps+" Amps");
            double odoKm = values.u32OdoMeter / 1000.0;
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(1);
            System.out.println(df.format(values.u32OdoMeter/1000));

            txtStsScreenOdometer.setText(""+ df.format(odoKm) +" Km");


        /* Check for Door Jammed Error and update Settings Screen */
        if (g_BluetoothProtocol.getIsDoorJammedError()){
            txtMoptroErrCode.setText("ERR: EFP Door Jammed.");
            txtMoptroErrCode.setBackgroundResource(R.color.bbRedDark);
            imgMoptroIcon.setImageResource(R.drawable.cross_logo_red);
        }else{
            txtMoptroErrCode.setText("No Error");
            txtMoptroErrCode.setBackgroundResource(R.color.transparent);
            imgMoptroIcon.setImageDrawable(null);
        }

        if (g_bEnableBatteryAlert)
        {
            if ((txtBtStatus.getText().equals("Connected")) && (txtWifiStatus.getText().equals("IOT Connected"))
                && (g_bEnableBatteryAlert == true) &&
                (g_bShowBatteryDoorOpenStatus == false)) {
                imgSettingsIcon.setImageDrawable(null);
            }
            else
            {
            imgSettingsIcon.setImageResource(R.drawable.cross_logo_red);
            }
        }
        else
        {
            if ((txtBtStatus.getText().equals("Connected")) && (txtWifiStatus.getText().equals("IOT Connected")))
            {
                imgSettingsIcon.setImageDrawable(null);
            }
            else
            {
            imgSettingsIcon.setImageResource(R.drawable.cross_logo_red);
            }
        }



        /* Add VIN number if it's not done earlier */
//        if((ADD_VIN_STS_NOT_ADDED == g_iVinAdded) && (STS_CONNECTED == g_iWifiStatus) && (STS_ACTIVE == g_iBtStatus)){
//            /* Send Add VIN */
//            new ataskAddVin().execute();
//        }

        /* Send Diag data to server */
        if (true == g_bSendDataToCloud){
            g_bSendDataToCloud = false;
            //Log.e(TAG,"g_bSendDataToCloud: " + g_bSendDataToCloud);
            //new ataskAddDiag().execute();
        }

        /* Restart the Handler again */
        stsScreenUpdateHandler.postDelayed(this, STS_SCREEN_UPDATE_TICK_TIME);
        }
    };

    private Runnable addDiagToServerRunnable = new Runnable() {
        @Override
        public void run() {
            //Log.e(TAG,"addDiagToServerRunnable Executing :)");
            //DIAG will be updated when the data is received from MCB

            //Check for WIFI connectivity and start the BG task
            if(!(myBtScannerService.getState() == BluetoothSerialService.STATE_CONNECTED)){
                Log.e(TAG, "BT not connected");
            }
            else if(!isJSONValid(g_strDiagJsonFromMCB)) {
                Log.e(TAG, "DIAG JSON is invalid");
            }
            else{
                new ataskAddDiag().execute();
            }
            //Do not restart
        }
    };

    private Runnable addHbToServerRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG,"addHbToServerRunnable");

            //KARTHIK: Check for WIFI connectivity and start below BG task
            //Check 1) BT connected 2) HB string is valid 3) WIFI connectivity / Internet connectivity
            if(myBtScannerService != null) {
                if ((myBtScannerService.getState() != BluetoothSerialService.STATE_CONNECTED)) {
                    Log.e(TAG, "BT not connected");
                } else if (!isJSONValid(g_strHbJsonFromMCB)) {
                    Log.e(TAG, "Data is invalid JSON");
                }
//            else if(){
//                Log.e(TAG, "WIFI connection error");
//            }
                else {
                    new ataskAddHbToServer().execute();
                }
                /* Restart the Handler again */
                addHbToServerHandler.postDelayed(this, ADD_HB_TO_SERVER_TICK_TIME);

                /*****************************************************************************/
                //This is test code check API calls. Please comment below code after testing
//                g_strHbJsonFromMCB = HB_JSON_SAMPLE;
//                new ataskAddHbToServer().execute();
//                addHbToServerHandler.postDelayed(this, ADD_HB_TO_SERVER_TICK_TIME);
                /*****************************************************************************/
            }
        }
    };

    private boolean isJSONValid(String jsonRequest) {
        try {
            new JSONObject(jsonRequest);
        } catch (JSONException ex) {
            Log.e(TAG, "isJSONVALID Error: " + ex.toString());
            return false;
        }
        return true;
    }

    private Runnable btReconnectRunnable = new Runnable() {
        @Override
        public void run() {
            /* Reconnect BT if disconnected */
            if(myBtScannerService!=null) {
                if (myBtScannerService.getState() == BluetoothSerialService.STATE_NONE) {
                    if (!TEST_BT_RESUME_STOP)
                        resumeBtConnection();
                }
            }
            /* Restart the Handler again */
            btReconnectHandler.postDelayed(this, BT_RECONNECT_TICK_TIME);
        }
    };

    private Runnable btTxRunnable = new Runnable() {
        @Override
        public void run() {
            //Store Odometer values before sending to PCB
            //commit_OdoValues();
            /* Send to PCB if BT is connected */
            if(myBtScannerService != null){
                if (myBtScannerService.getState() == BluetoothSerialService.STATE_CONNECTED) {
                    sendAppStatusToMcb();
                }
            }else {
                //Do nothing
            }

            /////////////////////////////////////////////////////////////////////////
            //Just print the status for debugging. Delete after testing
            //Log.e(TAG, "btTxRunnable: Printing values..");
//            String sURL = "";
//            sURL = g_strTypedIP + DIAG_PHP;
//            Log.e(TAG, "DIAG URL: " + sURL);
//            sURL = g_strTypedIP + HB_PHP;
//            Log.e(TAG, "HB URL: " + sURL);
//            //For printing App status
//            sendAppStatusToMcb();
            /////////////////////////////////////////////////////////////////////////

            /* Restart the Handler again */
            btTxHandler.postDelayed(this, BT_TX_TICK_TIME);
        }
    };

    private Runnable btReconnectToutRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG,"btReconnectToutRunnable in g_eAppSts: "+ g_eAppSts);
            /* Send Periodic data to BT */
            if (myBtScannerService.getState() != BluetoothSerialService.STATE_CONNECTED) {
                handleBtReconnectTout();
                if (g_progressDialog != null) g_progressDialog.dismiss();
            }else{
                /* Bluetooth Protocol should have synced already */
            }
        }
    };

    private Runnable crateMissingRunnable = new Runnable() {
        @Override
        public void run() {
            //Log.e(TAG,"crateMissingRunnable");
            boolean showDialog = false;
            if (g_eAppSts == enumAppStatus.APP_STS_SCAN_SKU){
                /* Check if crate sensor enabled */
                if (g_bEnableCrateSensor){
                    if (0 == (g_BluetoothProtocol.pcb_to_app_data.STS4 & 0b00001000)){
                        /* Crate not present */
                        showDialog = true;
                    }else{
                        /* Don't show Progress Dialog */
                    }
                }else{
                    /* Crate Sensor Disabled. Don't Show progress Dialog */
                }
            }else{
                /* App is not in SKU Scanning state. Stop Progress Dialog */
            }

            /* Check and show progress dialog */
            if (showDialog){
                g_progressDialogCrateError.setTitle("******* CRATE MISSING *******");
                g_progressDialogCrateError.setMessage("Please push crate inside MOPTro EFP Box.");
                g_progressDialogCrateError.show();
                /* Check if Order status is In-progress and Crate item qty is > 0 */
                int order_index = g_OrdersClass_fromServer.order_index;
                int scanned_label_index = g_OrdersClass_fromServer.order_list[order_index].scanned_label_index;
                if (ORDER_STS_IN_PROGRESS == g_OrdersClass_fromServer.order_list[order_index].order_status){
                    if ((g_strCrateMissingStrtTime.length() <= 3) || (g_strCrateMissingStrtTime.equals(""))){
                        g_strCrateMissingStrtTime = getCurrentTimeStamp();
                    }

                    if (g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).crate_item_qty > 0) {
                        /* Check if Crate missing Exceeds max seconds */
                        if (get_TimeDiffSec(g_strCrateMissingStrtTime) >= CRATE_MISSING_ALLOWANCE_TIME_SEC) {
                            /* Stop Progress Bar */
                            if (g_progressDialogCrateError != null) g_progressDialogCrateError.dismiss();
                            /* Go back to order list */
                            cleanup_crate_list(order_index);
                            if (update_orderSts()) {
                                g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                                updateScreenViewOnAppStatusChange();
                            }
                        }
                    }else{
                        /* Crate Item Qty is == 0*/
                        /* Check if Crate missing Exceeds max seconds for New crate change */
                        if (get_TimeDiffSec(g_strCrateMissingStrtTime) >= CRATE_MISSING_ALLOWANCE_TIME_SEC_CHANGE_NEW_CRATE) {
                            /* Stop Progress Bar */
                            if (g_progressDialogCrateError != null) g_progressDialogCrateError.dismiss();
                            /* Go back to order list */
                            cleanup_crate_list(order_index);
                            if (update_orderSts()) {
                                g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                                updateScreenViewOnAppStatusChange();
                            }
                        }
                    }
                }
                else{
                    /* Reset g_strCrateMissingStrtTime stamp  */
                    g_strCrateMissingStrtTime = "";
                }
            }else {
                if (g_progressDialogCrateError != null) g_progressDialogCrateError.dismiss();
                g_strCrateMissingStrtTime = "";
            }
            /* Restart Timer */
            crateMissingHandler.postDelayed(crateMissingRunnable, CRATE_MISSING_CHECK_TICK_TIME);
        }
    };

    private Runnable serverPostOrderRunnable = new Runnable() {
        @Override
        public void run() {
            /* Check for App status "APP_STS_ORDER_LIST" */
            if ((enumAppStatus.APP_STS_ORDER_LIST == g_eAppSts)&&(g_OrdersClass_fromServer.order_list_count > 0)){
                int order_index = 0;
                /* Check if waiting for response */
                boolean awaitingServerRes = false;
                for (order_index = 0;order_index < g_OrdersClass_fromServer.order_list_count;order_index++){
                    if((ORDER_STS_HOLD_DOWNLOADING == g_OrdersClass_fromServer.order_list[order_index].order_status) ||
                            (ORDER_STS_CLOSED_POSTING == g_OrdersClass_fromServer.order_list[order_index].order_status) ||
                            (ORDER_STS_HOLD_POSTING == g_OrdersClass_fromServer.order_list[order_index].order_status)){
                        awaitingServerRes = true;
                        break;
                    }
                }


                if (awaitingServerRes){
                    /* Don't Stop Progress Bar. Wait for Response */
                }else {
                    /* Check if any order is in progress and update the same to save hold orders */
                    for (order_index = 0; order_index < g_OrdersClass_fromServer.order_list_count; order_index++) {
                        if (ORDER_STS_IN_PROGRESS == g_OrdersClass_fromServer.order_list[order_index].order_status) {
                            g_OrdersClass_fromServer.order_index = order_index;
                            update_orderSts();
                            orderListAdapter.notifyDataSetChanged();
                            updateOrderCountText();
                            break;
                        }
                    }

                    /* Check for Server requests to be sent */
                    for (order_index = 0; order_index < g_OrdersClass_fromServer.order_list_count; order_index++) {
                        if ((ORDER_STS_HOLD_DOWNLOAD_PENDING == g_OrdersClass_fromServer.order_list[order_index].order_status) ||
                                (ORDER_STS_CLOSED_POST_PENDING == g_OrdersClass_fromServer.order_list[order_index].order_status) ||
                                (ORDER_STS_HOLD_POST_PENDING == g_OrdersClass_fromServer.order_list[order_index].order_status)) {
                            break;
                        }
                    }
                    /* Call Async Task for Server Communication */
                    if (order_index < g_OrdersClass_fromServer.order_list_count) {
                        cleanup_crate_list(order_index);
                        if (g_OrdersClass_fromServer.order_list[order_index].order_status == ORDER_STS_HOLD_DOWNLOAD_PENDING) {
                            g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD_DOWNLOADING;
                            g_OrdersClass_fromServer.order_index = order_index;
                            new ataskDownloadHoldDetails().execute();
                        } else if (g_OrdersClass_fromServer.order_list[order_index].order_status == ORDER_STS_HOLD_POST_PENDING) {
                            g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD_POSTING;
                            g_OrdersClass_fromServer.order_index = order_index;
                            new ataskSetHoldOrder().execute();
                        } else if (g_OrdersClass_fromServer.order_list[order_index].order_status == ORDER_STS_CLOSED_POST_PENDING) {
                            g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_CLOSED_POSTING;
                            g_OrdersClass_fromServer.order_index = order_index;
                            new ataskPostOrders().execute();
                        } else {
                            /* No action as of now */
                        }
                    }
                    else{
                        /* Nothing Pending */
                        /* Stop Progress Bar */
                        if (g_progressDialog != null) g_progressDialog.dismiss();

                        /* Update Order List Screen */
                        if (orderListAdapter != null) orderListAdapter.notifyDataSetChanged();
                        updateOrderCountText();
                    }
                }

                /* Restart the Handler with "SERVER_POST_ON_ORDER_LIST_TICK_TIME" */
                //serverOrderPostHandler.postDelayed(this, SERVER_POST_ON_ORDER_LIST_TICK_TIME);
            }else {
                /* Restart the Handler with "SERVER_POST_CHECK_TICK_TIME" */
                //serverOrderPostHandler.postDelayed(this, SERVER_POST_CHECK_TICK_TIME);
            }
            serverOrderPostHandler.postDelayed(this, SERVER_POST_CHECK_TICK_TIME);
        }
    };

    synchronized private void updatePhoneBatteryRange() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int batteryPct = (level) * (scale / 100);
        //Write percentage to Bluetooth protocol
        g_BluetoothProtocol.setAppToPcb_MobileBattery((byte) batteryPct);
    }

    synchronized private void updateMoptroRange() {
        //txtMoptroRange.setText("RANGE: " + g_BluetoothProtocol.pcb_to_app_data.moptro_range + " Km");
        if (g_BluetoothProtocol.pcb_to_app_data.moptro_range > 1) {
            imgMoptroIcon.setImageDrawable(null);
        } else {
            imgMoptroIcon.setImageResource(R.drawable.cross_logo_red);
        }
    }

    static void updateService_Screen() {
        if (null == values.vin){
            txtHeaderMoptroName.setText(""+g_strMoptroName);
            //txtHeaderMoptroName.setText(""+values.vin);
        }else {
            txtHeaderMoptroName.setText(""+values.vin);
        }


        //After the first restart, MCB not
        if ((0 == values.iSocPercent) && (values.fBatVoltage_O > values.fDeadVoltCutoff)){
            float voltage = values.fBatVoltage;
            voltage = (float)(Math.floor(voltage*10))/10;

            float min =  values.fDeadVoltCutoff;
            float max =  values.fMaxVolt;

            int battery = (int) (((voltage - min) / (max - min)) * 100);

            if (battery < 0)
                battery = 0;
            else if (battery > 100)
                battery = 100;
            txtMoptroBatPercent.setText("Battery: " + battery + "%");
        }else {
            txtMoptroBatPercent.setText("Battery: " + values.iSocPercent + "%");
        }

        txtBatVolt.setText(""+values.fBatVoltage+"/"+values.fBatVoltage_O);
        txtBatVoltADC.setText(""+values.iAdcBatVolt+"/"+values.iAdcBattery_O);
        txtBatVolt0ADC.setText("0000");

        /*************NATIVE************************/
        txtBldcCurrent.setText(""+values.fLoadAmps);
        txtBldcCurrentADC.setText(""+values.iAdcLoadAmps1+"/"+values.iAdcLoadAmps2);
        txtBldcCurrent0ADC.setText(""+values.iAdc00LoadAmps1+"/"+values.iAdc00LoadAmps2);

        /*************OPPOSITE************************/
        txtOppositeBldcCurrent.setText(""+values.fLoadAmps_O);
        txtOppositeBldcCurrentADC.setText(""+values.iAdcLoadAmps1_O+"/"+values.iAdcLoadAmps2_O);
        txtOppositeBldcCurrent0ADC.setText(""+values.iAdc00LoadAmps1_O+"/"+values.iAdc00LoadAmps2_O);

        txtChargerCurrent.setText(""+values.fChargeAmps);
        txtChargerCurrentADC.setText(""+values.iAdcChargeAmps);
        txtChargerCurrent0ADC.setText(""+values.iAdc00ChargeAmps);

        txtOppositeChargerCurrent.setText(""+values.fChargeAmps_O);
        txtOppositeChargerCurrentADC.setText(""+values.iAdcChargeAmps_O);
        txtOppositeChargerCurrent0ADC.setText(""+values.iAdc00ChargeAmps_O);

        /**************************************HeartBeat DATA*********************************************/

        txtu32OdoMeter.setText(""+values.u32OdoMeter);
        txtu32UsageInTotalMin.setText(""+values.u32UsageInTotalMin);
        txtu32UsageInMaxWattsMin.setText(""+values.u32UsageInMaxWattsMin);
        txtu32UsageInPeakWattsMin.setText(""+values.u32UsageInPeakWattsMin);
        txtu32UsageInPeakAmpsMin.setText(""+values.u32UsageInPeakAmpsMin);
        txtu32ChargeCycles.setText(""+values.u32ChargeCycles);
        txtiMaxWattsTimer.setText(""+values.iMaxWattsTimer);
        txtiPeakWattsTimer.setText(""+values.iPeakWattsTimer);

        txtiPeakAmpsTimer.setText(""+values.iPeakAmpsTimer);
        txtiMaxWatts.setText(""+values.iMaxWatts);
        txtiPeakWatts.setText(""+values.iPeakWatts);
        txtiPeakAmps.setText(""+values.iPeakAmps);
        txtiOLresetTimer.setText(""+values.iOLresetTimer);
        txtiSpeedLimitKmph.setText(""+values.iSpeedLimitKmph);
        txtiTripMeter.setText(""+values.iTripMeter);
        txtiBrakeConfig.setText(""+values.iBrakeConfig);
        txtiBrakeDelayMsec.setText(""+values.iBrakeDelayMsec);


        txtiAdcBatVolt.setText(""+values.iAdcBatVolt);
        txtiAdc00ChargeAmps.setText(""+values.iAdc00ChargeAmps);
        txtiAdcChargeAmps.setText(""+values.iAdcChargeAmps);

        txtiAdc00LoadAmps1.setText(""+values.iAdc00LoadAmps1);
        txtiAdcLoadAmps1.setText(""+values.iAdcLoadAmps1);
        txtiAdc00LoadAmps2.setText(""+values.iAdc00LoadAmps2);
        txtiAdcLoadAmps2.setText(""+values.iAdcLoadAmps2);
        txtiAdc00McbAmps.setText(""+values.iAdc00McbAmps);
        txtiAdcMcbAmps.setText(""+values.iAdcMcbAmps);



        txtiSts0.setText(""+values.iSts0);
        txtiSts1.setText(""+values.iSts1);
        txtiSts2.setText(""+values.iSts2);
        txtiSts3.setText(""+values.iSts3);
        txtiSocPercent.setText(""+values.iSocPercent);
        txtiOutputPower.setText(""+values.iOutputPower);
        txtiTrips.setText(""+values.iTrips);


        txtu32OdoMeter_O.setText(""+values.u32OdoMeter_O);
        txtu32UsageInTotalMin_O.setText(""+txtu32UsageInTotalMin_O);

        txtu32UsageInMaxWattsMin_O.setText(""+values.u32UsageInMaxWattsMin_O);
        txtu32UsageInPeakWattsMin_O.setText(""+values.u32UsageInPeakWattsMin_O);
        txtu32UsageInPeakAmpsMin_O.setText(""+values.u32UsageInPeakAmpsMin_O);
        txtu32ChargeCycles_O.setText(""+values.u32ChargeCycles_O);


        txtiAdcBattery_O.setText(""+values.iAdcBattery_O);
        txtiAdcBatVolt_O.setText(""+values.iAdcBatVolt_O);

        txtiAdc00ChargeAmps_O.setText(""+values.iAdc00ChargeAmps_O);
        txtiAdcChargeAmps_O.setText(""+values.iAdcChargeAmps_O);
        txtiAdc00LoadAmps1_O.setText(""+values.iAdc00LoadAmps1_O);
        txtiAdcLoadAmps1_O.setText(""+values.iAdcLoadAmps1_O);
        txtiAdc00LoadAmps2_O.setText(""+values.iAdc00LoadAmps2_O);
        txtiAdcLoadAmps2_O.setText(""+values.iAdcLoadAmps2_O);

        txtiAdc00McbAmps_O.setText(""+values.iAdc00McbAmps_O);
        txtiAdcMcbAmps_O.setText(""+values.iAdcMcbAmps_O);


        txtiSts0_O.setText(""+values.iSts0_O);
        txtiSts1_O.setText(""+values.iSts1_O);
        txtiSts2_O.setText(""+values.iSts2_O);
        txtiSts3_O.setText(""+values.iSts3_O);
        txtiSocPercent_O.setText(""+values.iSocPercent_O);
        txtiOutputPower_O.setText(""+values.iOutputPower_O);
        txtiTrips_O.setText(""+values.iTrips_O);


        txtfMaxVolt.setText(""+values.fMaxVolt);
        txtfLowVoltCutoff.setText(""+values.fLowVoltCutoff);
        txtfDeadVoltCutoff.setText(""+values.fDeadVoltCutoff);


        txtfBatVoltage.setText(""+values.fBatVoltage);
        txtfLoadAmps.setText(""+values.fLoadAmps);
        txtfChargeAmps.setText(""+values.fChargeAmps);
        txtfMcbAmps.setText(""+values.fMcbAmps);
        txtfSpeedKmph.setText(""+values.fSpeedKmph);

        txtfBatVoltage_O.setText(""+values.fBatVoltage_O);
        txtfLoadAmps_O.setText(""+values.fLoadAmps_O);
        txtfChargeAmps_O.setText(""+values.fChargeAmps_O);
        txtfMcbAmps_O.setText(""+values.fMcbAmps_O);
        txtfSpeedKmph_O.setText(""+values.fSpeedKmph_O);


        txtstrRfid1.setText(""+values.strRfid1);
        txtstrRfid2.setText(""+values.strRfid2);




        /**************************************DIAG DATA*********************************************/


        txtstrFirmwareRev.setText(""+values.strFirmwareRev);
        txtiIgnCycles.setText(""+values.iIgnCycles);
        txtiChargeCycles.setText(""+values.iChargeCycles);
        txtiDistanceTravelled.setText(""+values.iDistanceTravelled);
        txtiStartStopCyclesByPulse.setText(""+values.iStartStopCyclesByPulse);
        txtiStartStopCyclesByAmps.setText(""+values.iStartStopCyclesByAmps);
        txtiOLcountMaxWatts.setText(""+values.iOLcountMaxWatts);
        txtiOLcountPeakWatts.setText(""+values.iOLcountPeakWatts);
        txtiOLcountPeakAmps.setText(""+values.iOLcountPeakAmps);
        txtiBrakeOnMovingCount.setText(""+values.iBrakeOnMovingCount);
        txtiBrakeOnStandingCount.setText(""+values.iBrakeOnStandingCount);
        txtiMotorPowerAvg.setText(""+values.iMotorPowerAvg);
        txtiMotorPowerAvgAbs.setText(""+values.iMotorPowerAvgAbs);
        txtiMotorPowerPeak.setText(""+values.iMotorPowerPeak);
        txtiErrCode0.setText(""+values.iErrCode0);
        txtiErrCode1.setText(""+values.iErrCode1);
        txtiIgnCycles_O.setText(""+values.iIgnCycles_O);
        txtiChargeCycles_O.setText(""+values.iChargeCycles_O);
        txtiDistanceTravelled_O.setText(""+values.iDistanceTravelled_O);
        txtiStartStopCyclesByPulse_O.setText(""+values.iStartStopCyclesByPulse_O);
        txtiStartStopCyclesByAmps_O.setText(""+values.iStartStopCyclesByAmps_O);
        txtiOLcountMaxWatts_O.setText(""+values.iOLcountMaxWatts_O);
        txtiOLcountPeakWatts_O.setText(""+values.iOLcountPeakWatts_O);
        txtiOLcountPeakAmps_O.setText(""+values.iOLcountPeakAmps_O);
        txtiBrakeOnMovingCount_O.setText(""+values.iBrakeOnMovingCount_O);
        txtiBrakeOnStandingCount_O.setText(""+values.iBrakeOnStandingCount_O);
        txtiMotorPowerAvg_O.setText(""+values.iMotorPowerAvg_O);
        txtiMotorPowerAvgAbs_O.setText(""+values.iMotorPowerAvgAbs_O);
        txtiMotorPowerPeak_O.setText(""+values.iMotorPowerPeak_O);
        txtfLoadAmpsAvg.setText(""+values.fLoadAmpsAvg);
        txtfLoadAmpsAvgAbs.setText(""+values.fLoadAmpsAvgAbs);
        txtfLoadAmpsPeak.setText(""+values.fLoadAmpsPeak);
        txtfChargeAmpsAvg.setText(""+values.fChargeAmpsAvg);
        txtfChargeAmpsAvgAbs.setText(""+values.fChargeAmpsAvgAbs);
        txtfChargeAmpsPeak.setText(""+values.fChargeAmpsPeak);
        txtfSpeedKmphAvg.setText(""+values.fSpeedKmphAvg);
        txtfSpeedKmphAvgAbs.setText(""+values.fSpeedKmphAvgAbs);
        txtfSpeedKmphPeak.setText(""+values.fSpeedKmphPeak);
        txtfLoadAmpsAvg_O.setText(""+values.fLoadAmpsAvg_O);
        txtfLoadAmpsAvgAbs_O.setText(""+values.fLoadAmpsAvgAbs_O);
        txtfLoadAmpsPeak_O.setText(""+values.fLoadAmpsPeak_O);
        txtfChargeAmpsAvg_O.setText(""+values.fChargeAmpsAvg_O);
        txtfChargeAmpsAvgAbs_O.setText(""+values.fChargeAmpsAvgAbs_O);
        txtfChargeAmpsPeak_O.setText(""+values.fChargeAmpsPeak_O);
        txtfSpeedKmphAvg_O.setText(""+values.fSpeedKmphAvg_O);
        txtfSpeedKmphAvgAbs_O.setText(""+values.fSpeedKmphAvgAbs_O);
        txtfSpeedKmphPeak_O.setText(""+values.fSpeedKmphPeak_O);







        txtV12Current.setText(g_BluetoothProtocol.pcb_to_app_data.v12_amps);
        txtV12CurrentADC.setText(g_BluetoothProtocol.pcb_to_app_data.v12_amps_adc);
        txtV12Current0ADC.setText(g_BluetoothProtocol.pcb_to_app_data.v12_amps_00adc);
        txtV05Current.setText(g_BluetoothProtocol.pcb_to_app_data.v05_amps);
        txtV05CurrentADC.setText(g_BluetoothProtocol.pcb_to_app_data.v05_amps_adc);
        txtV05Current0ADC.setText(g_BluetoothProtocol.pcb_to_app_data.v05_amps_00adc);
        //txtOdometer.setText(g_BluetoothProtocol.pcb_to_app_data.odometer);
        txtEfpCyclesMeter.setText(g_BluetoothProtocol.pcb_to_app_data.efp_cycles_meter);
        txtEfpTestCyclesMeter.setText(g_BluetoothProtocol.pcb_to_app_data.efp_test_cycles_meter);
        txtEfpDoorErrCount.setText(""+g_BluetoothProtocol.pcb_to_app_data.STS8);

        if (0 == g_BluetoothProtocol.pcb_to_app_data.STS6){
            txtSocState.setText("EQUILIBRIUM");
        }else if (1 == g_BluetoothProtocol.pcb_to_app_data.STS6){
            txtSocState.setText("WAIT_FOR_EQUI");
        }else if (2 == g_BluetoothProtocol.pcb_to_app_data.STS6){
            txtSocState.setText("DISCHARGING");
        }else if (3 == g_BluetoothProtocol.pcb_to_app_data.STS6){
            txtSocState.setText("CHARGING");
        }else{
            txtSocState.setText("MAX");
        }
        txtSoc.setText(""+values.iSocPercent+" %");
        if (0 == (g_BluetoothProtocol.pcb_to_app_data.STS4 & 0b00000010)){
            txtDoorSts.setText("0");
        }else{
            txtDoorSts.setText("1");
        }
        if (0 == (g_BluetoothProtocol.pcb_to_app_data.STS4 & 0b00000100)){
            txtAwakeSts.setText("0");
        }else{
            txtAwakeSts.setText("1");
        }
        if (0 == (g_BluetoothProtocol.pcb_to_app_data.STS4 & 0b00001000)){
            txtCrateSts.setText("0");
        }else{
            txtCrateSts.setText("1");
        }
        if (0 == (g_BluetoothProtocol.pcb_to_app_data.STS4 & 0b00010000)){
            txtObjectSts.setText("0");
        }else{
            txtObjectSts.setText("1");
        }
    }

    synchronized private void getBluetoothDevice() {
        /* Reset the Globals */
        myBtDevice = null;
        myBtConnectedDeviceName = null;

        //g_strMoptroName = "HC-05";  //For testing purpose only

        /* Get list of paired devices */
        Set<BluetoothDevice> BtPairedDevices = myBtAdapter.getBondedDevices();
        /* Run Loop to find out Device is Paired ot not */
        if (BtPairedDevices.size() > 0) {
        /* There are Paired Devices. Run the loop */
            for (BluetoothDevice device : BtPairedDevices) {
                if (g_strMoptroName.equals(device.getName())) {
                /* Device Found to be Paired !!! Store the Device */
                    myBtDevice = device;
                    txtBtStatus.setText("Click to connect");
                    txtBtStatus.setBackgroundResource(R.color.maroon);
                    break;
                /* Terminate Loop */
                } else {
                /* Device Not found so far */
                }
            }
        } else {
        /* No Devices Paired */
        }

    /* Check if Device Found */
        if (myBtDevice == null) {
            txtBtStatus.setText("Not Paired");
            //txtBtStatus.setText("Connected");
            txtBtStatus.setBackgroundResource(R.color.transparent);
            imgBatterDoorLogo.setImageDrawable(null);
            txtBatteryDoorLogo.setText("Battery Door Closed");
        }
    }

    /* The Handler that gets information back from the BluetoothSerialService */
    private final Handler myBluetoothHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if (msg.arg1 == BluetoothSerialService.STATE_CONNECTED) {
                        imgBtIcon.setImageDrawable(null);
                        txtBtStatus.setText("Connected");
                        txtBtStatus.setBackgroundResource(R.color.transparent);
                        imgSettingsIcon.setImageResource(0);
                        if (g_eAppSts == enumAppStatus.APP_STS_SCAN_ORDER ||
                                g_eAppSts == enumAppStatus.APP_STS_SCAN_CRATE ||
                                g_eAppSts == enumAppStatus.APP_STS_SCAN_SKU) {
                            btSend_BarcodeInvalid_ScannerInactivate();
                        }
                    } else {
                        txtBtStatus.setText("Click to Connect");
                        txtBtStatus.setBackgroundResource(R.color.maroon);
                        imgBtIcon.setImageResource(R.drawable.cross_logo_red);
                        myBtConnectedDeviceName = null;
                        g_BluetoothProtocol.resetPcb_to_app_data();
                    }
                    switch (msg.arg1) {
                        case BluetoothSerialService.STATE_CONNECTED:
                            //Log.e(TAG, "Bluetooth STATE_CONNECTED");
                            imgBtIcon.setImageDrawable(null);
                            /* Send PCB to App Frame with Current Status */
                            send_AppToPcbFrame();
                            // Phone BT is getiing disconnected when screen is turned off so generating the Heart Beat for MCB
                            // when the BT is connected
                            generateHBForMCB();
                            Log.e(TAG, "Bluetooth connected");
                            if (g_progressDialog != null) g_progressDialog.dismiss();
                            break;
                        case BluetoothSerialService.STATE_CONNECTING:
                            txtBtStatus.setText("Connecting...");
                            txtBtStatus.setBackgroundResource(R.color.transparent);
                            Log.e(TAG, "Bluetooth STATE_CONNECTING");
                            break;
                        case BluetoothSerialService.STATE_LISTEN:
                            Log.e(TAG, "Bluetooth STATE_LISTEN");
                            break;
                        case BluetoothSerialService.STATE_NONE:
                            Log.e(TAG, "Bluetooth STATE_NONE");
                            /* Display Bluetooth Disconnected and wait for BT_RECONNECT_TOUT Seconds */
                            switch (g_eAppSts) {
                                case APP_STS_EFP_CYCLE_TEST:
                                case APP_STS_SERVER_CRATE_CHECK:
                                case APP_STS_SCAN_CRATE_WAITING_SYS_READY:
                                case APP_STS_SCAN_CRATE_WAITING_SCANNER_ACTIVE:
                                case APP_STS_SCAN_ORDER_WAITING_SYS_READY:
                                case APP_STS_SCAN_ORDER_WAITING_SCANNER_ACTIVE:
                                case APP_STS_SCAN_SKU_WAITING_SYS_READY:
                                case APP_STS_SCAN_SKU_WAITING_SCANNER_ACTIVE:
                                    if (g_progressDialog != null) {
                                        g_progressDialog.setTitle("**** BLUETOOTH STATUS ****");
                                        g_progressDialog.setMessage("ERR: Bluetooth Disconnected. Please wait for few seconds. Reconnecting....");
                                        btReconnectToutHandler.postDelayed(btReconnectToutRunnable, BT_RECONNECT_TOUT);
                                    }
                                    break;
                                default:
                                    Log.e(TAG, "BT Disconnected in g_eAppSts: "+g_eAppSts);
                                    imgBatterDoorLogo.setImageDrawable(null);
                                    txtBatteryDoorLogo.setText("Battery Door Closed");
                                    break;
                            }
                            break;
                    }
                    break;
                case MESSAGE_WRITE:

                    break;

                case MESSAGE_READ:
                    /* Heart Beat is implemented in Service Layer */
                    //Log.e(TAG, "Inside MESSAGE_READ"+g_eAppSts);
                    g_bSendDataToCloudHbMissing = 0;
                    char[] readBuf = (char[]) msg.obj;
                    //Log.e(TAG, "readBuf: "+readBuf);
                    //Log.e(TAG,"Stream with Start ID: " + readBuf[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_START_STREAM]);
                    if (readBuf[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_START_STREAM] == g_BluetoothProtocol.BT_PCB_TO_APP_DIAG_START_STREAM){
                        //Log.e(TAG, "handleBtStreamDiagnostics");
                        g_bSendDataToCloudDiagMissing = 0;
                        handleBtStreamDiagnostics(readBuf);
                    } else if (readBuf[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_START_STREAM] == g_BluetoothProtocol.BT_PCB_TO_APP_START_STREAM){
                        if (readBuf[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_BARCODE_CNT] == 0) {
                            //Log.e(TAG, "handleBtStreamWithoutBarcode");
                            handleBtStreamWithoutBarcode(readBuf);
                        } else {
                            //Log.e(TAG, "handleBtStreamWithBarcode Count: " + readBuf[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_BARCODE_CNT]);
                            //handleBtStreamWithBarcode(readBuf);
                        }
                    }else{
                        StringBuilder receivedStr = new StringBuilder();
                        int index = 0;
                        do{
                            receivedStr.append(readBuf[index]);
                        }
                        while ((readBuf[index++] != '}'));

                        try
                        {
                            JSONRequest = receivedStr.toString();
                            int jsonRequestLenth = JSONRequest.length();
                            // THIS CHECK HAS TO BE REMOVED AFTER MOPTRO LITE FIRMWARE IS UPDATED TO HAVE BCB DATA
                            if (jsonRequestLenth == DIAG_JSON_LEN4)
                            {
                                JSONRequest = JSONRequest.replace("}", g_strDummyBcbdata);
                                jsonRequestLenth = JSONRequest.length();
                            }
                            Log.e(TAG,"Received JSON: " + receivedStr.toString());
                            Log.e(TAG,"Received JSON length: " + JSONRequest.length());
                            if (jsonRequestLenth == DIAG_JSON_LEN || jsonRequestLenth == DIAG_JSON_LEN1 || jsonRequestLenth == DIAG_JSON_LEN2 || jsonRequestLenth == DIAG_JSON_LEN3 || jsonRequestLenth == DIAG_JSON_LEN5 || jsonRequestLenth == DIAG_JSON_LEN6){
                                g_strDiagJsonFromMCB = JSONRequest;
                                storeValues(JSONRequest);
                                // Added in PI_2023_09
                                parseAndStoreJson(JSONRequest);
                                //Start runnable for async task
                                Log.e(TAG,"DIAG received");
                                addDiagToServerHandler.postDelayed(addDiagToServerRunnable,ADD_DIAG_TO_SERVER_TICK_TIME);
                            }else if(jsonRequestLenth == HB_JSON_LEN || jsonRequestLenth == HB_JSON_LEN1 || jsonRequestLenth == HB_JSON_LEN3 || jsonRequestLenth == HB_JSON_LEN2){
                                g_strHbJsonFromMCB = JSONRequest;
                                storeValues(JSONRequest);
                                //Added in Pi_2023_09
                                parseAndStoreJson(JSONRequest);
                                // CHECK FOR THE BATTERY DOOR LOCK STATUS
                                if(g_bShowBatteryDoorOpenStatus) {
                                    if (!(batteryDoorDialog != null && batteryDoorDialog.isShowing()))
                                    {
//
                                        LayoutInflater inflater = LayoutInflater.from(GDT_Main.this);
                                        View view = inflater.inflate(R.layout.error, null);

// Reference ImageView and TextView
                                        ImageView gifImageView = view.findViewById(R.id.gifImageView);

// Load GIF from assets using Glide

// Correct and safe way to load GIF from assets using Glide

                                        int nativeDoorLock = values.iSts1 & 2048;
                                        int oppositeDoorLock = values.iSts1_O & 2048;
                                        String fileName = "";
                                    if ((nativeDoorLock == 2048) && (oppositeDoorLock == 2048))
                                    {
                                        fileName = "both.gif";
                                    }
                                    else if (nativeDoorLock == 2048)
                                    {
                                        fileName = "native.gif";
                                    }
                                    else if (oppositeDoorLock == 2048)
                                    {
                                        fileName = "opposite.gif";
                                    }

                                    try{
                                        InputStream inputStream = GDT_Main.this.getAssets().open(fileName);
                                        byte[] bytes = new byte[inputStream.available()];
                                        inputStream.read(bytes);
                                        inputStream.close();
                                        Glide.with(GDT_Main.this)
                                                .asGif()
                                                .load(bytes)
                                                .into(gifImageView);
                                    }
                                    catch (Exception E)
                                    {

                                    }

                                    AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this, R.style.MyDialogTheme);
                                    builder.setView(view)
                                            .setTitle("Attention")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });

                                    batteryDoorDialog = builder.create();
                                    batteryDoorDialog.show();

// Optional: Customize OK button
                                    Button button = batteryDoorDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                    button.setBackgroundColor(Color.BLUE);
                                    button.setTextColor(Color.WHITE);

                                    }
                                    g_MediaPlayClass.playWrongScan(getApplicationContext());
                                }
                                else{
                                    if (batteryDoorDialog.isShowing())
                                    {
                                        ///dialog.dismiss();
                                        batteryDoorDialog.dismiss();
                                    }
                                }
// Build and show the dialog



//                                if (g_bShowBatteryDoorOpenStatus && myBtConnectedDeviceName != null && g_bEnableBatteryAlert)
//                                {
//                                    imgBatterDoorLogo.setImageResource(R.drawable.cross_logo_red);
//                                    txtBatteryDoorLogo.setText("Close The Battery door");
//                                    if (!( batteryDoorDialog != null && batteryDoorDialog.isShowing() ))
//                                    {
//                                        AlertDialog.Builder dialog = new AlertDialog.Builder(GDT_Main.this, R.style.MyDialogTheme);
//                                        dialog.setTitle("ERROR CODE: " + DOOR_OPEN);
//                                        dialog.setMessage("Please Close the Battery door");
//                                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                dialog.cancel();
//                                            }
//                                        });
//                                        batteryDoorDialog = dialog.create();
//                                        batteryDoorDialog.show();
//                                        Button buttonbackground1 = batteryDoorDialog.getButton(DialogInterface.BUTTON_POSITIVE);
//                                        buttonbackground1.setBackgroundColor(Color.BLUE);
//                                    }
//                                    g_MediaPlayClass.playWrongScan(getApplicationContext());
//                                }
//                                else
//                                {
//                                    imgBatterDoorLogo.setImageDrawable(null);
//                                    txtBatteryDoorLogo.setText("Battery Door Closed");
//                                }


                                Log.e(TAG,"HB received");
                            }
                            /*else if (JSONRequest.length() <= BARCODE_JSON_LEN)
                            {

                            }*/
                            else{
                                if (!( wrongScanDialog != null && wrongScanDialog.isShowing() ))
                                {
                                    handleBtStreamWithBarcode(JSONRequest);
                                }
                                Log.e(TAG,"Ignore received JSON due to string len mismatch");
                            }

                            //Below code is to update alert box contents based on u32errocodes
                            u32ErrorCodesAlertBoxTitle = "Err: Vehicle Alert!! \nDo not Operate Vehicle";
                            u32ErrorCodesAlertBoxDisclaimer = "**The error will go away automatically";
                            titleTextView.setText(u32ErrorCodesAlertBoxTitle);
                            disclaimerTextView.setText(u32ErrorCodesAlertBoxDisclaimer);
                            //SETTING BY DEFAULT FALSE EVERYTIME HEART BEAT IS INVOKED
                            g_isU32ErrorCodeAlert = false;
                            if ((values.u32ErrorCodes & MASK_VEHICLE_OVERLOAD) == MASK_VEHICLE_OVERLOAD || (values.u32ErrorCodes_O & MASK_VEHICLE_OVERLOAD) == MASK_VEHICLE_OVERLOAD) {
                                u32ErrorCodesAlertBoxMessage = "Err: The vehicle is overloaded..";
                                messageVehicleOverloadTextView.setText(u32ErrorCodesAlertBoxMessage);
                                ABMLayoutVehicleOverload.setVisibility(View.VISIBLE);
                                g_isU32ErrorCodeAlert = true;
                            }
                            if ((values.u32ErrorCodes & MASK_CONTROLLER_TEMP_EXCEEDED) == MASK_CONTROLLER_TEMP_EXCEEDED || (values.u32ErrorCodes_O & MASK_CONTROLLER_TEMP_EXCEEDED) == MASK_CONTROLLER_TEMP_EXCEEDED) {
                                u32ErrorCodesAlertBoxMessage = "Warning!! the vehicle Controller Temperature Exceeded above the operating temperature..";
                                controllerWarnMessageTextView.setText(u32ErrorCodesAlertBoxMessage);
                                ABMLayoutControllerTempWarn.setVisibility(View.VISIBLE);
                                g_isU32ErrorCodeAlert = true;
                            }
                            if ((values.u32ErrorCodes & MASK_MOTOR_TEMP_EXCEEDED) == MASK_MOTOR_TEMP_EXCEEDED || (values.u32ErrorCodes_O & MASK_MOTOR_TEMP_EXCEEDED) == MASK_MOTOR_TEMP_EXCEEDED) {
                                u32ErrorCodesAlertBoxMessage = "Warning!! the vehicle Motor Temperature Exceeded above the operating temperature..";
                                motorWarnMessageTextView.setText(u32ErrorCodesAlertBoxMessage);
                                ABMLayoutMotorTempWarn.setVisibility(View.VISIBLE);
                                g_isU32ErrorCodeAlert = true;
                            }
                            if ((values.u32ErrorCodes & MASK_BATTERY_TEMP_EXCEEDED) == MASK_BATTERY_TEMP_EXCEEDED || (values.u32ErrorCodes_O & MASK_BATTERY_TEMP_EXCEEDED) == MASK_BATTERY_TEMP_EXCEEDED) {
                                u32ErrorCodesAlertBoxMessage = "Warning!! The vehicle Battery Temperature Exceeded above the operating temperature.";
                                batteryWarnMessageTextView.setText(u32ErrorCodesAlertBoxMessage);
                                ABMLayoutBatteryTempWarn.setVisibility(View.VISIBLE);
                                g_isU32ErrorCodeAlert = true;
                            }
                            if((values.u32ErrorCodes & MASK_CONTROLLER_TEMP_CUTOFF) == MASK_CONTROLLER_TEMP_CUTOFF || (values.u32ErrorCodes_O & MASK_CONTROLLER_TEMP_CUTOFF) == MASK_CONTROLLER_TEMP_CUTOFF)
                            {
                                u32ErrorCodesAlertBoxMessage = "Err: The vehicle Controller temperature is too high.";
                                controllerErrMessageTextView.setText(u32ErrorCodesAlertBoxMessage);
                                ABMLayoutControllerTempErr.setVisibility(View.VISIBLE);
                                g_isU32ErrorCodeAlert = true;
                            }
                            if ((values.u32ErrorCodes & MASK_MOTOR_TEMP_CUTOFF) == MASK_MOTOR_TEMP_CUTOFF || (values.u32ErrorCodes_O & MASK_MOTOR_TEMP_CUTOFF) == MASK_MOTOR_TEMP_CUTOFF) {
                                u32ErrorCodesAlertBoxMessage = "Err: The vehicle Motor temperature is too high.";
                                motorErrMessageTextView.setText(u32ErrorCodesAlertBoxMessage);
                                ABMLayoutMotorTempErr.setVisibility(View.VISIBLE);
                                g_isU32ErrorCodeAlert = true;
                            }
                            
                            if(g_isU32ErrorCodeAlert && (values.u32ErrorCodes !=0))
                            {
                                updateOrShowCustomAlertBox();
                            }
                            else if(dialog.isShowing() && g_isU32ErrorCodeAlert == false)
                            {
                                dialog.dismiss();
                            }

                        }
                        catch (Exception e){
                            Log.e(TAG,"MESSAGE_READ ERROR: " + e.toString());
                        }
                    }
                    break;

                case MESSAGE_DEVICE_NAME:
                    /* Save the connected device's name. Not used anywhere else */
                    myBtConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    break;

                case MESSAGE_TOAST:
                    Log.e(TAG, "MESSAGE_TOAST" + msg.arg1);
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void createBluetoothScannerService() {
        Log.e(TAG, "createBluetoothScannerService()");
        // Initialize the BluetoothSerialService to perform bluetooth connections
        if (myBtScannerService == null) {
            Log.e(TAG,"Starting myBtScannerService: ");
            myBtScannerService = new BluetoothSerialService(this, myBluetoothHandler);
        } else {
            Log.e(TAG, "myBtScannerService: " + myBtScannerService);
        }
    }

    /* Resume BT Connection */
    private void resumeBtConnection() {
        Log.e(TAG, "resumeBtConnection");

        /* Exit if BT adapter is NOT ON */
        if (false == myBtAdapter.isEnabled()) {
            Log.e(TAG, "BT not ON");
        }
        else
        {
            // Preconditions: Scanner Service Created + BT device Available + BT state NONE
            if ((myBtScannerService != null) && (myBtDevice != null) &&
                    (myBtScannerService.getState() == BluetoothSerialService.STATE_NONE))
            {
            /* Connect to the BT Device */
                Log.e(TAG, "Connect to BT device: " + myBtDevice.getName().toString());
                myBtScannerService.connect(myBtDevice);
                g_u16BtReconnectCount++;
            }
            else
            {
                if(myBtScannerService != null)
                    Log.e(TAG, "BT Connect Request skipped with below status:" + '\n' +
                        "myBtScannerService: " + myBtScannerService + '\n' +
                        "myBtDevice: " + myBtDevice + '\n' +
                        "Bt state: " + myBtScannerService.getState());

            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK)
                {
                    /* No functionality yet */
                }
                break;

            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Log.e(TAG, "BT Turned ON :)");
                    // Bluetooth is now enabled, so set up a Bluetooth session
                    createBluetoothScannerService();
                    getBluetoothDevice();

                } else {
                    Log.e(TAG, "BT is not Turned ON :(");
                }
        }
    }

    synchronized private void startWifiConnectionManager(Context context) {
        /* Blow 3 values never changes Even when called Multiple times. So Keep it local */
        ConnectivityManager MyConnectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo myNetworkInfo =
                MyConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetworkInfo =
                MyConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        WifiManager myWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = myWifiManager.getConnectionInfo();
        g_sWifiAddress = info.getMacAddress();

        Log.e(TAG,"WIFI Address: " + g_sWifiAddress);


        /* Enable Wifi if currently OFF */
        if (!myWifiManager.isWifiEnabled()) {
            myWifiManager.setWifiEnabled(true);
        }
        else {
                /* Wifi is ON. Check if connected to Network */
            if (myNetworkInfo.isConnected()) {
                txtWifiStatus.setText("IOT Connected");
                imgIotIcon.setImageDrawable(null);
            }else if (mobileNetworkInfo.isConnected()) {
                txtWifiStatus.setText("IOT Connected");
                imgIotIcon.setImageDrawable(null);
            } else {
                txtWifiStatus.setText("Disconnected");
                imgIotIcon.setImageResource(R.drawable.cross_logo_red);
                imgSettingsIcon.setImageResource(R.drawable.cross_logo_red);
            }
        }

        /* Below Code will be called on changes in Wifi Connection status */
        if (myBroadcastReceiver == null)
            myBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent)
                {
                    Log.e(TAG, "Inside myBroadcastReceiver");
                /* Get Action For Wifi */
                    final String action = intent.getAction();
                    ConnectivityManager MyConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo MyWifi = MyConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    NetworkInfo MyMobile = MyConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


                    if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                        if (MyWifi.isConnected()) {
                            txtWifiStatus.setText("IOT Connected");
                            imgIotIcon.setImageDrawable(null);
                            wifiConnected = true;
                        }else if (MyMobile.isConnected()) {
                            txtWifiStatus.setText("IOT Connected");
                            imgIotIcon.setImageDrawable(null);
                            wifiConnected = true;
                        } else {
                            txtWifiStatus.setText("Disconnected");

                            imgIotIcon.setImageResource(R.drawable.cross_logo_red);
                            wifiConnected = false;
                        }
                    }
                }
            };

        if (!myBroadcastReceiverRegistered) {
            Log.e("startWifi", "Starting myBroadcastReceiver: " + myBroadcastReceiver);
            /* Create a Intent Filter */
            IntentFilter iFilter = new IntentFilter();
            iFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            iFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            //iFilter.addAction("android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED");
            //iFilter.addAction("android.bluetooth.BluetoothDevice.ACTION_ACL_DISCONNECTED");
            //iFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);

            /* Register Receiver */
            context.registerReceiver(myBroadcastReceiver, iFilter);
            myBroadcastReceiverRegistered = true;

            Log.e("startWifi", "Started myBroadcastReceiver: " + myBroadcastReceiver);
        }
        else {
            Log.e("startWifi","myBroadcastReceiver: " + myBroadcastReceiver);
        }
    }

    static void btSend_ScannerActivate_OnSystemReady() {
        //Log.e(TAG, "btSend_ScannerActivate_OnSystemReady in appSts: " + g_eAppSts);
        if (g_BluetoothProtocol.getIsSystemReady()) {
            g_BluetoothProtocol.setAppToPcb_CMD0_ActivateScanner();
            send_AppToPcbFrame();
        } else {
            Log.e(TAG, "setScannerActivate requested on When System is Not Ready");
        }
    }

    static void btSend_BarcodeInvalid_ScannerInactivate() {
        //Log.e(TAG, "btSend_BarcodeInvalid_ScannerInactivate in appSts: " + g_eAppSts);
        g_BluetoothProtocol.setAppToPcb_CMD0_InActivateScanner();
        g_BluetoothProtocol.setAppToPcb_BarcodeInvalid();
        send_AppToPcbFrame();
        g_BluetoothProtocol.setAppToPcb_BarcodeNoSts();
    }

    static void btSend_BarcodeInvalid_ScannerActivate() {
        //Log.e(TAG, "btSend_BarcodeInvalid_ScannerActivate in appSts: " + g_eAppSts);
        g_BluetoothProtocol.setAppToPcb_CMD0_ActivateScanner();
        g_BluetoothProtocol.setAppToPcb_BarcodeInvalid();
        send_AppToPcbFrame();
        g_BluetoothProtocol.setAppToPcb_BarcodeNoSts();
    }

    static void btSend_BarcodeNoSts_ScannerActivate() {
        //Log.e(TAG, "btSend_BarcodeNoSts_ScannerActivate in appSts: " + g_eAppSts);
        g_BluetoothProtocol.setAppToPcb_CMD0_ActivateScanner();
        g_BluetoothProtocol.setAppToPcb_BarcodeNoSts();
        send_AppToPcbFrame();
    }

    static void btSend_BarcodeNoSts_ScannerInActivate() {
        //Log.e(TAG, "btSend_BarcodeNoSts_ScannerInActivate in appSts: " + g_eAppSts);
        g_BluetoothProtocol.setAppToPcb_CMD0_InActivateScanner();
        g_BluetoothProtocol.setAppToPcb_BarcodeNoSts();
        send_AppToPcbFrame();
    }

    static void btSend_BarcodeValid_ScannerInactivate() {
        //Log.e(TAG, "btSend_BarcodeValid_ScannerInactivate in appSts: " + g_eAppSts);
        g_BluetoothProtocol.setAppToPcb_CMD0_InActivateScanner();
        g_BluetoothProtocol.setAppToPcb_BarcodeValid();
        send_AppToPcbFrame();
        g_BluetoothProtocol.setAppToPcb_BarcodeNoSts();
    }

    static void btSend_BarcodeValid_ScannerActivate() {
        //Log.e(TAG, "btSend_BarcodeValid_ScannerInactivate in appSts: " + g_eAppSts);
        g_BluetoothProtocol.setAppToPcb_CMD0_ActivateScanner();
        g_BluetoothProtocol.setAppToPcb_BarcodeValid();
        send_AppToPcbFrame();
        g_BluetoothProtocol.setAppToPcb_BarcodeNoSts();
    }

    // THIS FUNCTION IS USED FOR TO GET THE DATA FROM THE BARCODE
    // NOTE : CHANGE IN THE FUNCTION IS REQUIRED IF THE PROTOCOL STRUCTURE IS CHANGED
    public static String getBarcodeStrFromBtStream(String rxBuffer)
    {
        try
        {
            Log.e(TAG, "Received JSON: " + rxBuffer);
            Log.e(TAG, "Received JSON length: " + rxBuffer.length());
            JSONObject jsonDataOfBarcode = new JSONObject(rxBuffer);
            // CONDITION IS FOR CHECKING THE DATA FROM BLUETOOTH THAT IT IS RELATED TO BARCODE JSON DATA OR OTHER JSON DATA
            if (jsonDataOfBarcode.has("a") && jsonDataOfBarcode.has("b") && !jsonDataOfBarcode.has("c"))
            {
                return jsonDataOfBarcode.get("b").toString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }

        return "";
    }

    static String getCurrentTimeStamp() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()).toString();
    }

    private void handleBtStreamWithBarcode(String rxBuffer) {
            switch (g_eAppSts) {
                case APP_STS_SCAN_CRATE:
                    g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE_WAITING_SYS_READY;
                    g_validCrateRfidScanned = validateCrate(rxBuffer);
                    if (g_validCrateRfidScanned) {
                        if (g_usedCrateScanned) {
                            /* Send Barcode Invalid and wait for System Ready */
                            btSend_BarcodeInvalid_ScannerInactivate();
                        } else {
                            /* Server response will trigger next actions */
                        }
                    } else {
                        g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE;
                        /* Send Barcode Invalid and wait for System Ready */
                        btSend_BarcodeInvalid_ScannerInactivate();
                    }
                    break;

                case APP_STS_SCAN_ORDER:
                    if (!isDemoUser) {
                        g_eAppSts = enumAppStatus.APP_STS_SCAN_ORDER_WAITING_SYS_READY;
                    }
                    g_validOrderLabelScanned = validateOrderBarcode(rxBuffer);
                    /* Send Barcode Invalid and wait for System Ready */
                    btSend_BarcodeInvalid_ScannerInactivate();
                    break;

                case APP_STS_SCAN_SKU:
                    g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU_WAITING_SYS_READY;
                    if (isDemoUser) {
                        g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                    }
                    g_validSkuScanned = validateSkuBarcode(rxBuffer);
                    if (g_validSkuScanned) {
                        if (g_bStepSKULOcation) {
                            sku_next1();
                        } else {
                            g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                            sku_next();
                            if (isOrderCompleted) {
                                g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                                updateScreenViewOnAppStatusChange();
                            }
                        }

                    }
                /*if (g_validSkuScanned)
                {
                    if (g_bStepSKULOcation)
                    {
                        g_eAppSts = enumAppStatus.APP_STS_ADD_SCAN_SKU_LOCATION;
                        UpdateScreenForSKULocation();
                    }
                    else
                    {
                        g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                        sku_next();
                        if (isOrderCompleted)
                        {
                            g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                            updateScreenViewOnAppStatusChange();
                        }
                    }
                }*/

                /*if (g_validSkuScanned)
                {
                    if (g_bStepSKULOcation && showSKULocation)
                    {
                        g_eAppSts = enumAppStatus.APP_STS_ADD_SCAN_SKU_LOCATION;
                        UpdateScreenForSKULocation();
                    }
                    else
                    {
                        g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                        sku_next();
                        if (isOrderCompleted)
                        {
                            g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                            updateScreenViewOnAppStatusChange();
                        }
                    }
                }*/

                /*if (g_bStepSKULOcation && isDemoUser)
                {
                    if (showSKULocation)
                    {
                        UpdateScreenForSKULocation();
                        g_eAppSts = enumAppStatus.APP_STS_ADD_SCAN_SKU_LOCATION;
                    }
                    else if (scanSKULocation)
                    {
                        g_validSkuScanned = validateSkuLocationBarcode(rxBuffer);
                        if (g_validSkuScanned)
                        {
                            scanSKULocation = false;
                        }
                        else
                        {
                            scanSKULocation = true;
                        }
                    }
                    else
                    {
                        g_validSkuScanned = validateSkuBarcode(rxBuffer);
                    }
                }
                else
                {
                    g_validSkuScanned = validateSkuBarcode(rxBuffer);
                }*/


                    break;

                case APP_STS_ADD_SCAN_SKU_LOCATION:
                    g_validSkuScanned = validateSkuLocationBarcode(rxBuffer);
                    if (g_validSkuScanned) {
                        int order_index = g_OrdersClass_fromServer.order_index;
                        int item_list_qty = g_OrdersClass_fromServer.order_list[order_index].item_list_qty;
                        g_OrdersClass_fromServer.order_list[order_index].item_index++;
                        int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;

                        /* Check for item list completed */
                        if (item_index >= item_list_qty) {
                            /*  Order is either picked / paused. Update status as ORDER_STS_CLOSED_POST_PENDING */
                            g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_CLOSED_POST_PENDING;
                            g_OrdersClass_fromServer.order_list[order_index].item_index = 0;
                            /* Check for paused order */
                            for (int index = 0; index < item_list_qty; index++) {
                                if (g_OrdersClass_fromServer.order_list[order_index].items[index].qty_skipped != 0) {
                                    g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD_POST_PENDING;
                                    break;
                                }
                            }
                            /* Capture the timestamp */
                            if (ORDER_STS_HOLD_POST_PENDING == g_OrdersClass_fromServer.order_list[order_index].order_status) {
                                g_OrdersClass_fromServer.order_list[order_index].stop_time_stamp = "";
                                g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp = getCurrentTimeStamp();
                                /* Record Time stamp for Paused order */
                            } else if (ORDER_STS_CLOSED_POST_PENDING == g_OrdersClass_fromServer.order_list[order_index].order_status) {
                                g_OrdersClass_fromServer.order_list[order_index].stop_time_stamp = getCurrentTimeStamp();
                                g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp = "";
                            } else {
                                /* Can't be in this check */
                            }
                            updateOrderCountText();
                            g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                            isOrderCompleted = true;
                            showSKULocation = true;
                            g_OrdersClass_fromServer.order_index = 0;
                            /* Send Scanner Inactivate */
                            btSend_BarcodeNoSts_ScannerInActivate();
                            /* Break loop */
                            updateScreenViewOnAppStatusChange();
                            break;
                        } else {
                            txtOrderScanTitle.setText(SCAN_SKU);
                            UpdateBackGroundColorForSkuScan();
                            showSKULocation = false;
                            g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                            UpdateNextSkutobepicked(order_index, item_index);
                        }
                    }


                    break;

                default:
                    if (frameActive == frameAddSku) {
                        if (txtAddSkuBarcode.hasFocus()) {
                            txtAddSkuBarcode.setText(getBarcodeStrFromBtStream(rxBuffer));
                        }
                        if (txtAddSkuLocation.hasFocus()) {
                            txtAddSkuLocation.setText(getBarcodeStrFromBtStream(rxBuffer));
                        }
                    } else if (frameActive == frameAddOrder) {
                        if (txtAddOrderNumber.hasFocus()) {
                            txtAddOrderNumber.setText(getBarcodeStrFromBtStream(rxBuffer));
                        }
                    }
                    Log.e(TAG, "Unexpected Barcode Received in g_eAppSts: " + g_eAppSts);
                    break;
            }
    }

    private void handleBtStreamWithoutBarcode(char[] rxBuffer) {
        //Log.e(TAG,"handleBtStreamWithoutBarcode");
        if (parsePcbToAppData(rxBuffer)) {
            boolean isSystemReady = g_BluetoothProtocol.getIsSystemReady();
            boolean isScannerActive = g_BluetoothProtocol.getIsScannerActive();
            switch (g_eAppSts) {
                case APP_STS_RESET_PCB_REQUEST:
                    if (isSystemReady) {
                        if (g_progressDialog != null) g_progressDialog.dismiss();
                        //go to Login state
                        g_eAppSts = enumAppStatus.APP_STS_SERVICE_SCREEN;
                        updateScreenViewOnAppStatusChange();
                    } else {
                        /* Wait for BT connection and System Ready */
                    }
                    break;

                case APP_STS_SERVICE_SETTINGS_SCREEN_UPDATE:
                    if (isSystemReady) {
                        if (g_progressDialog != null) g_progressDialog.dismiss();
                        //go to Login state
                        g_eAppSts = enumAppStatus.APP_STS_SERVICE_SETTINGS_SCREEN;
                        updateScreenViewOnAppStatusChange();
                    } else {
                        /* Wait for BT connection and System Ready */
                    }
                    break;

                case APP_STS_CALIBRATE_EPF:
                    if (isSystemReady) {
                        g_eAppSts = appStatusBeforeCalibrate;
                        appStatusBeforeCalibrate = enumAppStatus.APP_STS_MIN;
                        txtCalibrateEfp.setText("START: EFP Calibrate");
                        txtCalibrateEfp.setBackgroundResource(R.color.transparent);
                        g_progressDialog.dismiss();
						/* Check and Activate Scanner */
                        switch (g_eAppSts) {
                            case APP_STS_SCAN_ORDER:
                            case APP_STS_SCAN_CRATE:
                            case APP_STS_SCAN_SKU:
                                if (!isScannerActive) {
									/* Activate Scanner */
                                    btSend_ScannerActivate_OnSystemReady();
                                }
                                break;

                            default:
                                break;
                        }
                    } else {
						/* Wait for System Ready */
                    }
                    break;

                case APP_STS_SCAN_CRATE:
                case APP_STS_SCAN_ORDER:
                case APP_STS_SCAN_SKU:
                    if ((!isSystemReady) || (!isScannerActive)) {
                        /* Some Sync issue. Activate Both */
                        btSend_BarcodeInvalid_ScannerActivate();
                    }else{
                        //Below code written to avoid Hanging
//                        if(g_progressDialog != null){
//                            g_progressDialog.dismiss();
//                        }
                    }
                    break;

                case APP_STS_SCAN_CRATE_WAITING_SYS_READY:
                    if (isSystemReady) {
                        if (isScannerActive) {
                            if (g_validCrateRfidScanned) {
                                g_validCrateRfidScanned = false;
                                if (g_usedCrateScanned){
                                    g_usedCrateScanned= false;
                                    /* Start SKU Scan */
                                    g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                                    sku_next();
                                    update_PauseTime();
                                }else {
                                    /* Start Order Scan */
                                    txtOrderScanTitle.setText(SCAN_ORDER_APPEND + g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].orderid);
                                    txtSelectedOrder.setText(NO_ORDER_SCANNED);
                                    g_eAppSts = enumAppStatus.APP_STS_SCAN_ORDER;
                                    add_new_crate();
                                }
                            }else{
                                /* Stay in Crate Scan */
                                g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE;
                            }
                        } else {
                            /* Scanner Not active yet. Send Scanner Activate */
                            g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE_WAITING_SCANNER_ACTIVE;
                            btSend_ScannerActivate_OnSystemReady();
                        }
                        updateScreenViewOnAppStatusChange();
                    } else {
                        //Log.e(TAG, "Waiting for System ready in g_eAppSts: " + g_eAppSts);
                    }
                    break;

                case APP_STS_SCAN_CRATE_WAITING_SCANNER_ACTIVE:
                    if (isSystemReady) {
                        if (isScannerActive) {
                            if (g_validCrateRfidScanned) {
                                g_validCrateRfidScanned = false;
                                if (g_usedCrateScanned){
                                    g_usedCrateScanned= false;
                                    /* Start SKU Scan */
                                    g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                                    sku_next();
                                    update_PauseTime();
                                }else {
                                    /* Start Order Scan */
                                    txtOrderScanTitle.setText(SCAN_ORDER_APPEND + g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].orderid);
                                    txtSelectedOrder.setText(NO_ORDER_SCANNED);
                                    g_eAppSts = enumAppStatus.APP_STS_SCAN_ORDER;
                                    add_new_crate();
                                }
                            }else{
                                /* Stay in Crate Scan */
                                g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE;
                            }
                        } else {
                            /* Scanner Not active yet. Send Scanner Activate */
                            btSend_ScannerActivate_OnSystemReady();
                        }
                        updateScreenViewOnAppStatusChange();
                    } else {
                        //Log.e(TAG, "Waiting for System ready in g_eAppSts: " + g_eAppSts);
                    }
                    break;

                case APP_STS_SCAN_ORDER_WAITING_SYS_READY:
                    if (isSystemReady) {
                        if (isScannerActive) {
                            if (g_validOrderLabelScanned) {
                                g_validOrderLabelScanned = false;
                                g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                                sku_next();
                            }else{
                                /* Stay in Order label Scan */
                                g_eAppSts = enumAppStatus.APP_STS_SCAN_ORDER;
                            }
                        } else {
                            g_eAppSts = enumAppStatus.APP_STS_SCAN_ORDER_WAITING_SCANNER_ACTIVE;
                            btSend_ScannerActivate_OnSystemReady();
                        }
                        updateScreenViewOnAppStatusChange();
                    } else {
                        //Log.e(TAG, "Waiting for System ready in g_eAppSts: " + g_eAppSts);
                    }
                    break;

                case APP_STS_SCAN_ORDER_WAITING_SCANNER_ACTIVE:
                    if (isSystemReady) {
                        if (isScannerActive) {
                            if (g_validOrderLabelScanned) {
                                g_validOrderLabelScanned = false;
                                g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                                sku_next();
                            }else{
                                /* Stay in Order label Scan */
                                g_eAppSts = enumAppStatus.APP_STS_SCAN_ORDER;
                            }
                        } else {
                            /* Scanner Not active yet. Send Scanner Activate again */
                            btSend_ScannerActivate_OnSystemReady();
                        }
                        updateScreenViewOnAppStatusChange();
                    } else {
                        //Log.e(TAG, "Waiting for System ready in g_eAppSts: " + g_eAppSts);
                    }
                    break;

                case APP_STS_SCAN_SKU_WAITING_SYS_READY:
                    if (isSystemReady) {
                        if (isScannerActive) {
                            g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                            sku_next();
                        } else {
                            g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU_WAITING_SCANNER_ACTIVE;
                            btSend_ScannerActivate_OnSystemReady();
                        }
                        updateScreenViewOnAppStatusChange();
                    } else {
                        //Log.e(TAG, "Waiting for System ready in g_eAppSts: " + g_eAppSts);
                    }
                    break;

                case APP_STS_SCAN_SKU_WAITING_SCANNER_ACTIVE:
                    if (isScannerActive && isSystemReady) {
                        g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                        sku_next();
                        updateScreenViewOnAppStatusChange();
                    } else {
                        //Log.e(TAG, "Waiting for (isScannerActive && isSystemReady) in g_eAppSts: " + g_eAppSts);
                    }
                    break;

                default:
                    //Log.e(TAG, "Received PCB Data in state: "+g_eAppSts);
                    break;
            }
        }else {
            Log.e(TAG,"Err: Parsing Error due to length");
        }
    }

    private void handleBtStreamDiagnostics(char[] rxBuffer) {
        //Log.e(TAG, "parsePcbToAppData with length: " + rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_TOTAL_BYTES]);
        try{
            if (rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_TOTAL_BYTES] >= g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_MIN_BYTES){
                //Parse Diagnostics data
                int index = 2;

                /*******************************Commented on 21st NOV 2018 *******************************************/

//                g_BluetoothProtocol.pcb_to_app_data.bIgnition = rxBuffer[index++];
//                ((TextView)findViewById(R.id.TxtIgnition)).setText(g_BluetoothProtocol.pcb_to_app_data.bIgnition + "");
//                g_BluetoothProtocol.pcb_to_app_data.bMotorON = rxBuffer[index++];
//                ((TextView)findViewById(R.id.TxtMotorON)).setText(g_BluetoothProtocol.pcb_to_app_data.bMotorON + "");
//                g_BluetoothProtocol.pcb_to_app_data.bNativeDirection = rxBuffer[index++];
//                ((TextView)findViewById(R.id.TxtNativeDirection)).setText(g_BluetoothProtocol.pcb_to_app_data.bNativeDirection + "");
//                g_BluetoothProtocol.pcb_to_app_data.bReverseDirection = rxBuffer[index++];
//                ((TextView)findViewById(R.id.TxtReverseDirection)).setText(g_BluetoothProtocol.pcb_to_app_data.bReverseDirection + "");
//                g_BluetoothProtocol.pcb_to_app_data.u8SocPercent = rxBuffer[index++];
//                ((TextView)findViewById(R.id.TxtDiagSoc)).setText(g_BluetoothProtocol.pcb_to_app_data.u8SocPercent + "");
//                g_BluetoothProtocol.pcb_to_app_data.eSocState = rxBuffer[index++];
//                ((TextView)findViewById(R.id.TxtDiagSocState)).setText(g_BluetoothProtocol.pcb_to_app_data.eSocState + "");
//                g_BluetoothProtocol.pcb_to_app_data.u8MopRangeKm = rxBuffer[index++];
//                ((TextView)findViewById(R.id.TxtDiagMoptroRange)).setText(g_BluetoothProtocol.pcb_to_app_data.u8MopRangeKm + "");
//                g_BluetoothProtocol.pcb_to_app_data.u8MobBatPer = rxBuffer[index++];
//                ((TextView)findViewById(R.id.TxtDiagMobBatPer)).setText(g_BluetoothProtocol.pcb_to_app_data.u8MobBatPer + "");
//                g_BluetoothProtocol.pcb_to_app_data.bMoptroMovingPulse = rxBuffer[index++];
//                ((TextView)findViewById(R.id.TxtMoptroMovingPulse)).setText(g_BluetoothProtocol.pcb_to_app_data.bMoptroMovingPulse + "");
//                g_BluetoothProtocol.pcb_to_app_data.bMoptroMovingAmps = rxBuffer[index++];
//                ((TextView)findViewById(R.id.TxtMoptroMovingAmps)).setText(g_BluetoothProtocol.pcb_to_app_data.bMoptroMovingAmps + "");
//                g_BluetoothProtocol.pcb_to_app_data.bCharging = rxBuffer[index++];
//                ((TextView)findViewById(R.id.TxtCharging)).setText(g_BluetoothProtocol.pcb_to_app_data.bCharging + "");
//                g_BluetoothProtocol.pcb_to_app_data.bFloatReady = rxBuffer[index++];
//                ((TextView)findViewById(R.id.TxtFloatReady)).setText(g_BluetoothProtocol.pcb_to_app_data.bFloatReady + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16IgnCycles = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16IgnCycles |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtIgnCycles)).setText(g_BluetoothProtocol.pcb_to_app_data.u16IgnCycles + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16ChargeCycles = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16ChargeCycles |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtChargeCycles)).setText(g_BluetoothProtocol.pcb_to_app_data.u16ChargeCycles + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16OverLoadCount = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16OverLoadCount |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtOverLoadCount)).setText(g_BluetoothProtocol.pcb_to_app_data.u16OverLoadCount + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16ForwardDirCount = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16ForwardDirCount |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtForwardDirCount)).setText(g_BluetoothProtocol.pcb_to_app_data.u16ForwardDirCount + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16StartStopCyclesPulse = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16StartStopCyclesPulse |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtStartStopCyclesPulse)).setText(g_BluetoothProtocol.pcb_to_app_data.u16StartStopCyclesPulse + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16StartStopCyclesLoadAmps = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16StartStopCyclesLoadAmps |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtStartStopCyclesAmps)).setText(g_BluetoothProtocol.pcb_to_app_data.u16StartStopCyclesLoadAmps + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16RevDirCount = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16RevDirCount |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtRevDirCount)).setText(g_BluetoothProtocol.pcb_to_app_data.u16RevDirCount + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16BrakeOnMovingCount = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16BrakeOnMovingCount |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtBrakeOnMovingCount)).setText(g_BluetoothProtocol.pcb_to_app_data.u16BrakeOnMovingCount + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16BrakeOnStandingCount = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16BrakeOnStandingCount |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtBrakeOnStandingCount)).setText(g_BluetoothProtocol.pcb_to_app_data.u16BrakeOnStandingCount + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16TraDistTotal = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16TraDistTotal |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtTraDistTotal)).setText(g_BluetoothProtocol.pcb_to_app_data.u16TraDistTotal + " meters");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16TraDistForward = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16TraDistForward |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtTraDistForward)).setText(g_BluetoothProtocol.pcb_to_app_data.u16TraDistForward + " meters");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16TraDistReverse = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16TraDistReverse |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtTraDistReverse)).setText(g_BluetoothProtocol.pcb_to_app_data.u16TraDistReverse + " meters");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16EmergencyCount = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16EmergencyCount |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtEmergencyCount)).setText(g_BluetoothProtocol.pcb_to_app_data.u16EmergencyCount + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16FootSwitchPressedCount = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16FootSwitchPressedCount |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtFootSwitchPressedCount)).setText(g_BluetoothProtocol.pcb_to_app_data.u16FootSwitchPressedCount + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16SampleCountForAvg = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16SampleCountForAvg |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtSampleCountForAvg)).setText(g_BluetoothProtocol.pcb_to_app_data.u16SampleCountForAvg + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16SampleCountAvgAmpsAbs = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16SampleCountAvgAmpsAbs |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtSampleCountForAvgAmpsAbs)).setText(g_BluetoothProtocol.pcb_to_app_data.u16SampleCountAvgAmpsAbs + "");
//
//                g_BluetoothProtocol.pcb_to_app_data.u16SampleCountAvgSpeedAbs = rxBuffer[index++];
//                g_BluetoothProtocol.pcb_to_app_data.u16SampleCountAvgSpeedAbs |= rxBuffer[index++] << 8;
//                ((TextView)findViewById(R.id.TxtSampleCountForAvgSpeedAbs)).setText(g_BluetoothProtocol.pcb_to_app_data.u16SampleCountAvgSpeedAbs + "");

                g_BluetoothProtocol.pcb_to_app_data.u16AdcBatteryVoltage = rxBuffer[index++];
                g_BluetoothProtocol.pcb_to_app_data.u16AdcBatteryVoltage |= rxBuffer[index++] << 8;

                g_BluetoothProtocol.pcb_to_app_data.u16Adc00AmpsBldcLoad = rxBuffer[index++];
                g_BluetoothProtocol.pcb_to_app_data.u16Adc00AmpsBldcLoad |= rxBuffer[index++] << 8;

                g_BluetoothProtocol.pcb_to_app_data.u16AdcAmpsBldcLoad = rxBuffer[index++];
                g_BluetoothProtocol.pcb_to_app_data.u16AdcAmpsBldcLoad |= rxBuffer[index++] << 8;

                g_BluetoothProtocol.pcb_to_app_data.u16Adc00AmpsCharger= rxBuffer[index++];
                g_BluetoothProtocol.pcb_to_app_data.u16Adc00AmpsCharger |= rxBuffer[index++] << 8;

                g_BluetoothProtocol.pcb_to_app_data.u16AdcAmpsCharger= rxBuffer[index++];
                g_BluetoothProtocol.pcb_to_app_data.u16AdcAmpsCharger |= rxBuffer[index++] << 8;

                byte[] arrFloatBytes = new byte[g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL];

                //fBatVoltage
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++) {
                    arrFloatBytes[i] = (byte)rxBuffer[index++];
                }
                g_BluetoothProtocol.pcb_to_app_data.fBatVoltage = bytesArrToString(arrFloatBytes);

                //fLoadAmps
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++) {
                    arrFloatBytes[i] = (byte)rxBuffer[index++];
                }
                g_BluetoothProtocol.pcb_to_app_data.fLoadAmps = bytesArrToString(arrFloatBytes);

                //fCharAmps
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++) {
                    arrFloatBytes[i] = (byte)rxBuffer[index++];
                }
                g_BluetoothProtocol.pcb_to_app_data.fCharAmps = bytesArrToString(arrFloatBytes);

                //fAvgLoadAmps
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++) {
                    arrFloatBytes[i] = (byte)rxBuffer[index++];
                }

                /*****************************Commented on 21st NOV 2018****************************************************/
//                g_BluetoothProtocol.pcb_to_app_data.fAvgLoadAmps = bytesArrToString(arrFloatBytes);
//                ((TextView)findViewById(R.id.TxtAvgLoadAmps)).setText(g_BluetoothProtocol.pcb_to_app_data.fAvgLoadAmps + " Amps");
//
//                //fAvgLoadAmpsAbs
//                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++) {
//                    arrFloatBytes[i] = (byte)rxBuffer[index++];
//                }
//                g_BluetoothProtocol.pcb_to_app_data.fAvgLoadAmpsAbs = bytesArrToString(arrFloatBytes);
//                ((TextView)findViewById(R.id.TxtAvgLoadAmpsAbs)).setText(g_BluetoothProtocol.pcb_to_app_data.fAvgLoadAmpsAbs + " Amps");
//
//                //fPeakLoadAmps
//                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++) {
//                    arrFloatBytes[i] = (byte)rxBuffer[index++];
//                }
//                g_BluetoothProtocol.pcb_to_app_data.fPeakLoadAmps = bytesArrToString(arrFloatBytes);
//                ((TextView)findViewById(R.id.TxtPeakLoadAmps)).setText(g_BluetoothProtocol.pcb_to_app_data.fPeakLoadAmps + " Amps");
//
//                //fAvgCharAmps
//                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++) {
//                    arrFloatBytes[i] = (byte)rxBuffer[index++];
//                }
//                g_BluetoothProtocol.pcb_to_app_data.fAvgCharAmps = bytesArrToString(arrFloatBytes);
//                ((TextView)findViewById(R.id.TxtAvgCharAmps)).setText(g_BluetoothProtocol.pcb_to_app_data.fAvgCharAmps + " Amps");
//
//                //fPeakCharAmps
//                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++) {
//                    arrFloatBytes[i] = (byte)rxBuffer[index++];
//                }
//                g_BluetoothProtocol.pcb_to_app_data.fPeakCharAmps = bytesArrToString(arrFloatBytes);
//                ((TextView)findViewById(R.id.TxtPeakCharAmps)).setText(g_BluetoothProtocol.pcb_to_app_data.fPeakCharAmps + " Amps");
//
//                //fAvgSpeedKmph
//                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++) {
//                    arrFloatBytes[i] = (byte)rxBuffer[index++];
//                }
//                g_BluetoothProtocol.pcb_to_app_data.fAvgSpeedKmph = bytesArrToString(arrFloatBytes);
//                ((TextView)findViewById(R.id.TxtAvgSpeed)).setText(g_BluetoothProtocol.pcb_to_app_data.fAvgSpeedKmph + " KMPH");
//
//                //fAvgSpeedKmphAbs
//                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++) {
//                    arrFloatBytes[i] = (byte)rxBuffer[index++];
//                }
//                g_BluetoothProtocol.pcb_to_app_data.fAvgSpeedKmphAbs = bytesArrToString(arrFloatBytes);
//                ((TextView)findViewById(R.id.TxtAvgSpeedAbs)).setText(g_BluetoothProtocol.pcb_to_app_data.fAvgSpeedKmphAbs + " KMPH");
//
//                //fPeakSpeedKmph
//                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++) {
//                    arrFloatBytes[i] = (byte)rxBuffer[index++];
//                }
//                g_BluetoothProtocol.pcb_to_app_data.fPeakSpeedKmph = bytesArrToString(arrFloatBytes);
//                ((TextView)findViewById(R.id.TxtPeakSpeed)).setText(g_BluetoothProtocol.pcb_to_app_data.fPeakSpeedKmph + " KMPH");

                //Frame and send data to server
                g_bSendDataToCloud = true;
            }else {
                Log.e(TAG, "Err: Received PCB to APP data with length: " + rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_TOTAL_BYTES]);
            }
        }catch (Exception e){
            Log.e(TAG, "Exception in parsing");
        }
    }

    private void handleBtReconnectTout() {
        Log.e(TAG,"handleBtReconnectTout in State: " + g_eAppSts);
        g_BluetoothProtocol.setPcbToAppSystemReady();
        g_BluetoothProtocol.setPcbToAppScannerActive();
        boolean isSystemReady = g_BluetoothProtocol.getIsSystemReady();
        boolean isScannerActive = g_BluetoothProtocol.getIsScannerActive();
        Log.e(TAG, "isSystemReady: " + isSystemReady);
        Log.e(TAG, "isScannerActive: " + isScannerActive);
        switch (g_eAppSts) {
            case APP_STS_RESET_PCB_REQUEST:
                g_eAppSts = enumAppStatus.APP_STS_SERVICE_SCREEN;
                break;

            case APP_STS_SERVICE_SETTINGS_SCREEN_UPDATE:
                g_eAppSts = enumAppStatus.APP_STS_SERVICE_SETTINGS_SCREEN;
                break;

            case APP_STS_CALIBRATE_EPF:
                g_eAppSts = appStatusBeforeCalibrate;
                appStatusBeforeCalibrate = enumAppStatus.APP_STS_MIN;
                txtCalibrateEfp.setText("START: EFP Calibrate");
                txtCalibrateEfp.setBackgroundResource(R.color.transparent);
                break;

            case APP_STS_SCAN_CRATE_WAITING_SYS_READY:
            case APP_STS_SCAN_CRATE_WAITING_SCANNER_ACTIVE:
                txtOrderScanTitle.setText(SCAN_ORDER_APPEND + g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].orderid);
                txtSelectedOrder.setText(NO_ORDER_SCANNED);
                g_eAppSts = enumAppStatus.APP_STS_SCAN_ORDER;
                break;

            case APP_STS_SCAN_ORDER_WAITING_SYS_READY:
            case APP_STS_SCAN_ORDER_WAITING_SCANNER_ACTIVE:
            case APP_STS_SCAN_SKU_WAITING_SYS_READY:
            case APP_STS_SCAN_SKU_WAITING_SCANNER_ACTIVE:
                g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                sku_next();
                break;

            default:
                break;
        }
        updateScreenViewOnAppStatusChange();
    }

    private boolean parsePcbToAppData (char[] rxBuffer){
        //Log.e(TAG, "parsePcbToAppData with length: " + rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_TOTAL_BYTES]);
        if (rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_TOTAL_BYTES] >= g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FIXED) {
            g_BluetoothProtocol.pcb_to_app_data.login_sts = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_LOGIN_STS];
            g_BluetoothProtocol.pcb_to_app_data.moptro_range = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_RANGE];
            g_BluetoothProtocol.pcb_to_app_data.object_count = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_OBJ_CNT];
            g_BluetoothProtocol.pcb_to_app_data.STS0 = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_STS0];
            g_BluetoothProtocol.pcb_to_app_data.STS1 = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_STS1];
            g_BluetoothProtocol.pcb_to_app_data.STS2 = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_STS2];
            g_BluetoothProtocol.pcb_to_app_data.STS3 = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_STS3];
            g_BluetoothProtocol.pcb_to_app_data.STS4 = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_STS4];
            g_BluetoothProtocol.pcb_to_app_data.STS5 = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_STS5];
            g_BluetoothProtocol.pcb_to_app_data.STS6 = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_STS6];
            g_BluetoothProtocol.pcb_to_app_data.STS7 = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_STS7];
            g_BluetoothProtocol.pcb_to_app_data.STS8 = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_STS8];
            g_BluetoothProtocol.pcb_to_app_data.STS9 = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_STS9];
            g_BluetoothProtocol.pcb_to_app_data.STS10 = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_STS10];
            byte[] arrFloatBytes = new byte[g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL];
            byte[] arrAdcBytes = new byte[g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_ADC_VAL];
            byte[] arrEfpCyclesBytes = new byte[g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_EFP_CYCLES];
            byte[] arrEfpTestCyclesBytes = new byte[g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_EFP_TEST_CYCLES];
            for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++){
                arrFloatBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_BAT_V + i];
            }
            g_BluetoothProtocol.pcb_to_app_data.mop_bat_v = bytesArrToString(arrFloatBytes);
            for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++){
                arrFloatBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_BLDC_AMPS + i];
            }
            g_BluetoothProtocol.pcb_to_app_data.bldc_amps = bytesArrToString(arrFloatBytes);
            for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++){
                arrFloatBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_CHAR_AMPS + i];
            }
            g_BluetoothProtocol.pcb_to_app_data.charger_amps = bytesArrToString(arrFloatBytes);

//            for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_ODOME_VAL; i++){
//                arrOdoBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_ODOKMETER_START + i];
//            }
//            g_BluetoothProtocol.pcb_to_app_data.odoKmeter = bytesArrToString(arrOdoBytes);
//            Log.e(TAG,"odoKmeter: "+g_BluetoothProtocol.pcb_to_app_data.odoKmeter);
//
//            for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_ODOME_VAL; i++){
//                arrOdoBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_ODOMETER_START + i];
//            }
//            g_BluetoothProtocol.pcb_to_app_data.odometer = bytesArrToString(arrOdoBytes);
//            Log.e(TAG,"odometer: "+g_BluetoothProtocol.pcb_to_app_data.odometer);


            g_BluetoothProtocol.pcb_to_app_data.u16odoKmeter = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_ODOKMETER_START];
            g_BluetoothProtocol.pcb_to_app_data.u16odoKmeter |= rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_ODOKMETER_START + 1] << 8;

            g_BluetoothProtocol.pcb_to_app_data.u16odometer = rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_ODOMETER_START];
            g_BluetoothProtocol.pcb_to_app_data.u16odometer |= rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_ODOMETER_START + 1] << 8;

            /* Check and Store Odometer Value */
            if (g_BluetoothProtocol.pcb_to_app_data.u16odoKmeter < 1200){
                if (g_iOdoKm < 1200) {
                    /* First time. Write back as 1500 + Random number */
                    Random random = new Random();
                    int RandomNumber = random.nextInt(1000);
                    g_iOdoKm = 1300 + RandomNumber;
                    g_iOdoMeter = random.nextInt(999);
                    commit_OdoValues();
                }else{
                    /* Random number already generated. To be sent from APP to PCB */
                }
            }else{
                if (g_BluetoothProtocol.pcb_to_app_data.u16odoKmeter > g_iOdoKm){
                    g_iOdoKm = g_BluetoothProtocol.pcb_to_app_data.u16odoKmeter;
                    g_iOdoMeter = g_BluetoothProtocol.pcb_to_app_data.u16odometer;
                    commit_OdoValues();
                }else{
                    /* g_iOdoKm to be sent from APP to PCB */
                }
            }

            /************************************************************************************/
            //Check for Service Bytes data
            if (0 == rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_SERVICE_CNT]){
                /* Service data not available. */
                //Log.e(TAG,"Service Data count = 0");
            }else if (g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_SERVICE == rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_SERVICE_CNT]){
                /* Service data Available */
                //Log.e(TAG,"Service Data received.");
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_ADC_VAL; i++){
                    arrAdcBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_BAT_ADC + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.mop_bat_adc = bytesArrToString(arrAdcBytes);
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_ADC_VAL; i++){
                    arrAdcBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_BLDC_AMPS_ADC + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.bldc_amps_adc = bytesArrToString(arrAdcBytes);
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_ADC_VAL; i++){
                    arrAdcBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_BLDC_AMPS_00ADC + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.bldc_amps_00adc = bytesArrToString(arrAdcBytes);
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_ADC_VAL; i++){
                    arrAdcBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_CHAR_AMPS_ADC + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.charger_amps_adc = bytesArrToString(arrAdcBytes);
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_ADC_VAL; i++){
                    arrAdcBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_CHAR_AMPS_00ADC + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.charger_amps_00adc = bytesArrToString(arrAdcBytes);


                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++){
                    arrFloatBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_V12_AMPS + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.v12_amps = bytesArrToString(arrFloatBytes);

                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_ADC_VAL; i++){
                    arrAdcBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_V12_AMPS_ADC + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.v12_amps_adc = bytesArrToString(arrAdcBytes);
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_ADC_VAL; i++){
                    arrAdcBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_V12_AMPS_00ADC + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.v12_amps_00adc = bytesArrToString(arrAdcBytes);

                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_FLOAT_VAL; i++){
                    arrFloatBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_V05_AMPS + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.v05_amps = bytesArrToString(arrFloatBytes);

                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_ADC_VAL; i++){
                    arrAdcBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_V05_AMPS_ADC + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.v05_amps_adc = bytesArrToString(arrAdcBytes);
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_ADC_VAL; i++){
                    arrAdcBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_MOP_V05_AMPS_00ADC + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.v05_amps_00adc = bytesArrToString(arrAdcBytes);
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_EFP_CYCLES; i++){
                    arrEfpCyclesBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_EFP_CYCLES + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.efp_cycles_meter = bytesArrToString(arrEfpCyclesBytes);
                for (int i = 0; i < g_BluetoothProtocol.BT_PCB_TO_APP_DATA_CNT_EFP_TEST_CYCLES; i++){
                    arrEfpTestCyclesBytes[i] = (byte)rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_EFP_TEST_CYCLES + i];
                }
                g_BluetoothProtocol.pcb_to_app_data.efp_test_cycles_meter = bytesArrToString(arrEfpTestCyclesBytes);
                updateService_Screen();
            }else{
                /* Wrong count received in Service data */
                Log.e(TAG,"Err: Wrong Service Data count: "+rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_SERVICE_CNT]);
                return false;
            }
            return true;
        } else {
            Log.e(TAG, "Err: Received PCB to APP data with length: " + rxBuffer[g_BluetoothProtocol.BT_PCB_TO_APP_INDEX_TOTAL_BYTES]);
            return false;
        }
    }

    static boolean send_AppToPcbFrame() {
        if (myBtScannerService.getState() != BluetoothSerialService.STATE_CONNECTED) {
            Log.e(TAG, "ERR: send_AppToPcbFrame requested when BT is not Connected");
            return false;
        }
        //Log.e(TAG, "send_AppToPcbFrame");
        byte totalBytes = (byte)g_BluetoothProtocol.BT_APP_TO_PCB_DATA_CNT_FIXED;
        byte[] txBuffer = new byte[totalBytes];
        //Log.e(TAG,"send_AppToPcbFrame. Count: " + totalBytes);
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_START_STREAM] = (byte)g_BluetoothProtocol.BT_APP_TO_PCB_START_STREAM;
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_TOTAL_BYTES] = totalBytes;
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_LOGIN_STS] = g_BluetoothProtocol.getAppToPcb_LoginStatus();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_BAT_PER] = g_BluetoothProtocol.getAppToPcb_MobileBattery();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_ODO_KM_LO] = (byte)g_iOdoKm;
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_ODO_KM_HI] = (byte)(g_iOdoKm >> 8);
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_ODO_ME_LO] = (byte)g_iOdoMeter;
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_ODO_ME_HI] = (byte)(g_iOdoMeter >> 8);
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_BARCODE_STS] = g_BluetoothProtocol.getAppToPcb_BarcodeSts();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_STS0] = g_BluetoothProtocol.getAppToPcb_STS0();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_STS1] = g_BluetoothProtocol.getAppToPcb_STS1();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_CMD0] = g_BluetoothProtocol.getAppToPcb_CMD0();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_CMD1] = g_BluetoothProtocol.getAppToPcb_CMD1();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_CMD2] = g_BluetoothProtocol.getAppToPcb_CMD2();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_SER_DATA_CNT] = 0;
        txBuffer[totalBytes-2] = 0x0D;
        txBuffer[totalBytes-1] = 0x0A;
        myBtScannerService.write(txBuffer);
        return true;
    }

    static boolean send_AppToPcbFrame_ServiceSettings() {
        if (myBtScannerService.getState() != BluetoothSerialService.STATE_CONNECTED) {
            return false;
        }
        byte totalBytes = (byte)(g_BluetoothProtocol.BT_APP_TO_PCB_DATA_CNT_FIXED + g_BluetoothProtocol.BT_APP_TO_PCB_DATA_CNT_SERVICE);
        byte[] txBuffer = new byte[totalBytes];

        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_START_STREAM] = (byte)g_BluetoothProtocol.BT_APP_TO_PCB_START_STREAM;
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_TOTAL_BYTES] = totalBytes;
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_LOGIN_STS] = g_BluetoothProtocol.getAppToPcb_LoginStatus();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_BAT_PER] = g_BluetoothProtocol.getAppToPcb_MobileBattery();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_ODO_KM_LO] = (byte)g_iOdoKm;
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_ODO_KM_HI] = (byte)(g_iOdoKm >> 8);
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_ODO_ME_LO] = (byte)g_iOdoMeter;
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_ODO_ME_HI] = (byte)(g_iOdoMeter >> 8);
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_BARCODE_STS] = g_BluetoothProtocol.getAppToPcb_BarcodeSts();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_STS0] = g_BluetoothProtocol.getAppToPcb_STS0();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_STS1] = g_BluetoothProtocol.getAppToPcb_STS1();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_CMD0] = g_BluetoothProtocol.getAppToPcb_CMD0();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_CMD1] = g_BluetoothProtocol.getAppToPcb_CMD1();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_CMD2] = g_BluetoothProtocol.getAppToPcb_CMD2();
        txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_SER_DATA_CNT] = (byte)g_BluetoothProtocol.BT_APP_TO_PCB_DATA_CNT_SERVICE;
        txBuffer[totalBytes-2] = 0x0D;
        txBuffer[totalBytes-1] = 0x0A;

        /* Frame Service Data */
        byte[] bytes = g_BluetoothProtocol.getAppToPcb_MoptroName();
        for (int i = 0; i < g_BluetoothProtocol.BT_APP_TO_PCB_DATA_CNT_MOP_NAME;i++){
            txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_MOP_NAME+i]=bytes[i];
        }
        byte[] bytes1 = g_BluetoothProtocol.getAppToPcb_EfpDoorOpen1Time();
        for (int i = 0; i < g_BluetoothProtocol.BT_APP_TO_PCB_DATA_CNT_EFP_DO1;i++){
            txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_EFP_DO1+i]=bytes1[i];
        }
        byte[] bytes2 = g_BluetoothProtocol.getAppToPcb_EfpDoorOpen2Time();
        for (int i = 0; i < g_BluetoothProtocol.BT_APP_TO_PCB_DATA_CNT_EFP_DO2;i++){
            txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_EFP_DO2+i]=bytes2[i];
        }
        byte[] bytes3 = g_BluetoothProtocol.getAppToPcb_EfpDoorClose1Time();
        for (int i = 0; i < g_BluetoothProtocol.BT_APP_TO_PCB_DATA_CNT_EFP_DC1;i++){
            txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_EFP_DC1+i]=bytes3[i];
        }
        byte[] bytes4 = g_BluetoothProtocol.getAppToPcb_EfpAutoTestCycles();
        for (int i = 0; i < g_BluetoothProtocol.BT_APP_TO_PCB_DATA_CNT_EFP_TEST;i++) {
            txBuffer[g_BluetoothProtocol.BT_APP_TO_PCB_INDEX_EFP_TEST_CYC + i] = bytes4[i];
        }
        //myBtScannerService.write(txBuffer);
        return true;
    }

    static int updateOrderCountText() {
        int not_started = 0;
        int closed = 0;
        int on_hold = 0;
        int post_pending = 0;
        int order_index = 0;
        int order_list_count = g_OrdersClass_fromServer.order_list_count;
        while (order_index < order_list_count)
        {
            if (ORDER_STS_CLOSED == g_OrdersClass_fromServer.order_list[order_index].order_status) {
                closed++;
            } else if (ORDER_STS_HOLD == g_OrdersClass_fromServer.order_list[order_index].order_status) {
                on_hold++;
            } else if (ORDER_STS_NOT_STARTED == g_OrdersClass_fromServer.order_list[order_index].order_status) {
                not_started++;
            } else if(ORDER_STS_CLOSED_POST_PENDING == g_OrdersClass_fromServer.order_list[order_index].order_status){
                post_pending++;
            } else {
                //Log.e(TAG, "ERR: updateOrderCountText() for order index: " +
                        //order_index + ", with status: " + g_OrdersClass_fromServer.order_list[order_index].order_status);
            }
            order_index++;
        }
        txtOrderClosed.setText("" + closed);
        txtOrderPending.setText(""+not_started);
        txtOrderOnhold.setText("" + on_hold);

        int order_completed = closed + on_hold + post_pending;
        if (order_list_count != (closed + not_started + on_hold)) {
            /* */
            //Log.e(TAG, "ERR: updateOrderCountText(): Mismatch in count");
        }
        return order_completed;
    }

    private void sku_next() {
        //Get Order and Item index from class
        int order_index = g_OrdersClass_fromServer.order_index;
        int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;
        int scanned_label_index = g_OrdersClass_fromServer.order_list[order_index].scanned_label_index;

        /* Process only on an valid SKU scan or an item skip */
        if (g_validSkuScanned) {
            g_validSkuScanned = false;
            /* If crate item qty is "0", then add crate and order label */
            if(0 == g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).crate_item_qty){
                addLabel_crate(g_strCrateLabel);
                addLabel_order(g_strOrderLabel);
            }else{
                /* Not a new crate */
            }

            //Increment item qty
            g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).crate_item_qty++;
            g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked++;

            /* Add sku to crate item list */
            addSku_Labelitems(g_OrdersClass_fromServer.order_list[order_index].items[item_index].sku);

            int crate_item_qty = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).crate_item_qty;
            int qty_picked = g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked;
            int qty = g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty;
            int item_list_qty = g_OrdersClass_fromServer.order_list[order_index].item_list_qty;

            /* Change the order status to In-progress if in Used crate state */
            if (ORDER_STS_RESUMED_USED_CRATE == g_OrdersClass_fromServer.order_list[order_index].order_status){
                g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_IN_PROGRESS;
            }

            /* Loop till Item list is complete */
            while (true)
            {
				/* Check for this item qty reached */
                if (qty_picked  >= qty)
                {
					/* Item qty reached. Increment item index */

                    g_OrdersClass_fromServer.order_list[order_index].item_index++;
                    item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;

					/* Check for item list completed */
                    if (item_index >= item_list_qty) {
                        /*  Order is either picked / paused. Update status as ORDER_STS_CLOSED_POST_PENDING */
                        g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_CLOSED_POST_PENDING;
                        g_OrdersClass_fromServer.order_list[order_index].item_index = 0;
						/* Check for paused order */
                        for (int index = 0; index < item_list_qty; index++) {
                            if (g_OrdersClass_fromServer.order_list[order_index].items[index].qty_skipped != 0) {
                                g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD_POST_PENDING;
                                break;
                            }
                        }
						/* Capture the timestamp */
                        if (ORDER_STS_HOLD_POST_PENDING == g_OrdersClass_fromServer.order_list[order_index].order_status) {
                            g_OrdersClass_fromServer.order_list[order_index].stop_time_stamp = "";
                            g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp = getCurrentTimeStamp();
                            /* Record Time stamp for Paused order */
                        } else if (ORDER_STS_CLOSED_POST_PENDING == g_OrdersClass_fromServer.order_list[order_index].order_status) {
                            g_OrdersClass_fromServer.order_list[order_index].stop_time_stamp = getCurrentTimeStamp();
                            g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp = "";
                        } else {
							/* Can't be in this check */
                        }
                        updateOrderCountText();
                        g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                        isOrderCompleted = true;
                        showSKULocation = true;
                        g_OrdersClass_fromServer.order_index = 0;
                        /* Send Scanner Inactivate */
                        btSend_BarcodeNoSts_ScannerInActivate();
						/* Break loop */
                        break;
                    } else {
                        /* Next item to be picked. Check if already picked */
                        qty_picked = g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked;
                        qty = g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty;

                        /* Reset Skipped Quantity */
                        g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_skipped = 0;
                        if (qty_picked >= qty) {
							/* Already this item is picked. Allow the loop to check for next item */
                            Log.e(TAG, "SKU Pick complete: "+g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc);
                        } else {
							/* Stop loop for next item pick */
                            Log.e(TAG, "SKU Pick next: "+g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc);
                            g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                            showSKULocation = true;
                            /* Check if crate has to be changed */
                            if (crate_item_qty >= g_iMaxItemsPerCrate){
                                g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE;
                                txtCrateRfid.setText(NO_CRATE_SCANNED);
                                txtOrderScanTitle.setText(SCAN_EMPTY_CRATE);
                                /* Auto crate close due to next SKU pick */
                                updateScreenViewOnAppStatusChange();
                            } else {
                                /* Start background process to get QOH for new item */
                                g_strSkuCode = g_OrdersClass_fromServer.order_list[order_index].items[item_index].sku;
                                new ataskGetQoh().execute(g_strSkuCode); //goes in background to get Qoh
                            }
                            break;
                        }
                    }
                } else {
					/* Same item to be picked again */
                    //Log.e(TAG, "Same Item to be picked again");
                    g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;

					/* Check if crate has to be changed */
                    if (crate_item_qty >= g_iMaxItemsPerCrate){
                        g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE;
                        txtCrateRfid.setText(NO_CRATE_SCANNED);
                        txtOrderScanTitle.setText(SCAN_EMPTY_CRATE);
                        /* Auto crate close due to same SKU pick (Multi qty) */
                        updateScreenViewOnAppStatusChange();
                    }
                    break;
                }
            }
        }

            if (g_eAppSts == enumAppStatus.APP_STS_SCAN_SKU) {
                txtSkuLocation.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location);
                txtSkuDescription.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc);
                txtQtyPicked.setText("" + g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked);
                /* Set Aisle number for next PAKA sorting */
                int aisleNum = GDT_Algorithm.getAislenum(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location.substring(0, 1));
                PakaConfig.set(AISLE_INDEX, aisleNum);
                //Log.e(TAG, "Current Aisle: " + aisleNum);

                //Fill-up picked list table array
                for (int i = 3; i > 0; i--) {
                    g_strPickedSkuArray[i - 1][1] = g_strPickedSkuArray[i][1];
                    g_strPickedSkuArray[i - 1][2] = g_strPickedSkuArray[i][2];
                }
                int index = 0;
                Log.e("", g_strPickedSkuArray[0][1]);
                for (int i = 0; i < 4; i++) {
                    //Find empty index in array
                    if ((g_strPickedSkuArray[i][1].equals("")) || (g_strPickedSkuArray[i][1].length() <= 1)) {
                        index = i;
                        break;
                    }
                }
                g_strPickedSkuArray[index][1] = g_OrdersClass_fromServer.order_list[order_index].items[item_index].location;
                if (g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc.length() > 18) {
                    g_strPickedSkuArray[index][2] = g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc.substring(0, 18);
                } else {
                    g_strPickedSkuArray[index][2] = g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc;
                }
                //Fill table with array
                for (int i = 0; i < 4; i++) {
                    switch (i) {
                        case 0:
                            txtCell_1_2.setText(g_strPickedSkuArray[i][1]);
                            txtCell_1_3.setText(g_strPickedSkuArray[i][2]);
                            break;
                        case 1:
                            txtCell_2_2.setText(g_strPickedSkuArray[i][1]);
                            txtCell_2_3.setText(g_strPickedSkuArray[i][2]);
                            break;
                        case 2:
                            txtCell_3_2.setText(g_strPickedSkuArray[i][1]);
                            txtCell_3_3.setText(g_strPickedSkuArray[i][2]);
                            break;
                        case 3:
                            txtCell_4_2.setText(g_strPickedSkuArray[i][1]);
                            txtCell_4_3.setText(g_strPickedSkuArray[i][2]);
                            break;
                        default:
                            break;
                    }
                }
            }
    }

    // THE NEXT VERSION OF THE SKU NEXT METHOD IF THEY WANT SCAN SKU LOCATION FEATURE IN THE DEMO
    private void sku_next1() {
        //Get Order and Item index from class
        int order_index = g_OrdersClass_fromServer.order_index;
        int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;
        int scanned_label_index = g_OrdersClass_fromServer.order_list[order_index].scanned_label_index;

        /* Process only on an valid SKU scan or an item skip */
        if (g_validSkuScanned) {
            g_validSkuScanned = false;
            /* If crate item qty is "0", then add crate and order label */
            if (0 == g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).crate_item_qty) {
                addLabel_crate(g_strCrateLabel);
                addLabel_order(g_strOrderLabel);
            } else {
                /* Not a new crate */
            }

            //Increment item qty
            g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).crate_item_qty++;
            g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked++;

            /* Add sku to crate item list */
            addSku_Labelitems(g_OrdersClass_fromServer.order_list[order_index].items[item_index].sku);

            int crate_item_qty = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).crate_item_qty;
            int qty_picked = g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked;
            int qty = g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty;
            int item_list_qty = g_OrdersClass_fromServer.order_list[order_index].item_list_qty;

            /* Change the order status to In-progress if in Used crate state */
            if (ORDER_STS_RESUMED_USED_CRATE == g_OrdersClass_fromServer.order_list[order_index].order_status) {
                g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_IN_PROGRESS;
            }


            /* Check for this item qty reached */
            if (qty_picked >= qty) {
                g_eAppSts = enumAppStatus.APP_STS_ADD_SCAN_SKU_LOCATION;
                UpdateScreenForSKULocation();
                txtQtyPicked.setText("" + g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked);
            } else {
                /* Same item to be picked again */
                //Log.e(TAG, "Same Item to be picked again");
                g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;

                /* Check if crate has to be changed */
                if (crate_item_qty >= g_iMaxItemsPerCrate) {
                    g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE;
                    txtCrateRfid.setText(NO_CRATE_SCANNED);
                    txtOrderScanTitle.setText(SCAN_EMPTY_CRATE);
                    /* Auto crate close due to same SKU pick (Multi qty) */
                    updateScreenViewOnAppStatusChange();
                }

            }
        }

        if (g_eAppSts == enumAppStatus.APP_STS_SCAN_SKU) {
            txtSkuLocation.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location);
            txtSkuDescription.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc);
            txtQtyPicked.setText("" + g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked);
            /* Set Aisle number for next PAKA sorting */
            int aisleNum = GDT_Algorithm.getAislenum(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location.substring(0, 1));
            PakaConfig.set(AISLE_INDEX, aisleNum);
            //Log.e(TAG, "Current Aisle: " + aisleNum);

            //Fill-up picked list table array
            for (int i = 3; i > 0; i--) {
                g_strPickedSkuArray[i - 1][1] = g_strPickedSkuArray[i][1];
                g_strPickedSkuArray[i - 1][2] = g_strPickedSkuArray[i][2];
            }
            int index = 0;
            Log.e("", g_strPickedSkuArray[0][1]);
            for (int i = 0; i < 4; i++) {
                //Find empty index in array
                if ((g_strPickedSkuArray[i][1].equals("")) || (g_strPickedSkuArray[i][1].length() <= 1)) {
                    index = i;
                    break;
                }
            }
            g_strPickedSkuArray[index][1] = g_OrdersClass_fromServer.order_list[order_index].items[item_index].location;
            if (g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc.length() > 18) {
                g_strPickedSkuArray[index][2] = g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc.substring(0, 18);
            } else {
                g_strPickedSkuArray[index][2] = g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc;
            }
            //Fill table with array
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0:
                        txtCell_1_2.setText(g_strPickedSkuArray[i][1]);
                        txtCell_1_3.setText(g_strPickedSkuArray[i][2]);
                        break;
                    case 1:
                        txtCell_2_2.setText(g_strPickedSkuArray[i][1]);
                        txtCell_2_3.setText(g_strPickedSkuArray[i][2]);
                        break;
                    case 2:
                        txtCell_3_2.setText(g_strPickedSkuArray[i][1]);
                        txtCell_3_3.setText(g_strPickedSkuArray[i][2]);
                        break;
                    case 3:
                        txtCell_4_2.setText(g_strPickedSkuArray[i][1]);
                        txtCell_4_3.setText(g_strPickedSkuArray[i][2]);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void UpdateNextSkutobepicked(int order_index, int item_index)
    {
        txtSkuLocation.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location);
        txtSkuDescription.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc);
        txtQtyPicked.setText("" + g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked);
        /* Set Aisle number for next PAKA sorting */
        int aisleNum = GDT_Algorithm.getAislenum(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location.substring(0, 1));
        PakaConfig.set(AISLE_INDEX, aisleNum);
        //Log.e(TAG, "Current Aisle: " + aisleNum);

        //Fill-up picked list table array
        for (int i = 3; i > 0; i--) {
            g_strPickedSkuArray[i - 1][1] = g_strPickedSkuArray[i][1];
            g_strPickedSkuArray[i - 1][2] = g_strPickedSkuArray[i][2];
        }
        int index = 0;
        Log.e("", g_strPickedSkuArray[0][1]);
        for (int i = 0; i < 4; i++) {
            //Find empty index in array
            if ((g_strPickedSkuArray[i][1].equals("")) || (g_strPickedSkuArray[i][1].length() <= 1)) {
                index = i;
                break;
            }
        }
        g_strPickedSkuArray[index][1] = g_OrdersClass_fromServer.order_list[order_index].items[item_index].location;
        if (g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc.length() > 18) {
            g_strPickedSkuArray[index][2] = g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc.substring(0, 18);
        } else {
            g_strPickedSkuArray[index][2] = g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc;
        }
        //Fill table with array
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    txtCell_1_2.setText(g_strPickedSkuArray[i][1]);
                    txtCell_1_3.setText(g_strPickedSkuArray[i][2]);
                    break;
                case 1:
                    txtCell_2_2.setText(g_strPickedSkuArray[i][1]);
                    txtCell_2_3.setText(g_strPickedSkuArray[i][2]);
                    break;
                case 2:
                    txtCell_3_2.setText(g_strPickedSkuArray[i][1]);
                    txtCell_3_3.setText(g_strPickedSkuArray[i][2]);
                    break;
                case 3:
                    txtCell_4_2.setText(g_strPickedSkuArray[i][1]);
                    txtCell_4_3.setText(g_strPickedSkuArray[i][2]);
                    break;
                default:
                    break;
            }
        }
    }

    private void sku_skip() {
        //Get Order and Item index from g_OrdersClass_fromServer
        int order_index = g_OrdersClass_fromServer.order_index;
        int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;
        int item_list_qty = g_OrdersClass_fromServer.order_list[order_index].item_list_qty;

        //Update skipped qty
        g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_skipped =
                g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty -
                        g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked;

		/* Move to next item */
        g_OrdersClass_fromServer.order_list[order_index].item_index++;
        item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;

		/* Check for item list completed */
        if (item_index >= item_list_qty) {
            /* Display order list screen for next order selection */
            g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
        } else {
            /* Keep App status as order list */
            g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;

            /* Check for next sku to display by running a loop till picked qty is less than to be picked qty */
            int qty_picked, qty;
            for (int i = item_index; i < item_list_qty; i++) {
                /* Fetch i-th item picked details */
                qty_picked = g_OrdersClass_fromServer.order_list[order_index].items[i].qty_picked;
                //qty_skipped = g_OrdersClass_fromServer.order_list[order_index].items[i].qty_skipped;
                qty = g_OrdersClass_fromServer.order_list[order_index].items[i].qty;

                if (qty_picked  >= qty) {
				    /* Already this item is picked. Allow the loop to check next item */
                } else {
                    Log.e(TAG, "Pick next item: "+g_OrdersClass_fromServer.order_list[order_index].items[i].desc);

                    /* update item index variable */
                    item_index = i;
                    g_OrdersClass_fromServer.order_list[order_index].item_index = i;

                    /* Set app status as Scan Item */
                    g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                    break;
                }
            }
        }

        if (g_eAppSts == enumAppStatus.APP_STS_SCAN_SKU) {
            txtSkuLocation.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location);
            txtSkuDescription.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc);
            txtQtyPicked.setText("" + g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked);
            txtCell_1_2.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location);
            txtCell_1_3.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc);

            /* Start background process to get QOH for new item */
            g_strSkuCode = g_OrdersClass_fromServer.order_list[order_index].items[item_index].sku;
            new ataskGetQoh().execute(g_strSkuCode); //goes in background to get Qoh
        } else if (g_eAppSts == enumAppStatus.APP_STS_ORDER_LIST) {
            if (update_orderSts()) {
                updateScreenViewOnAppStatusChange();
            }
        } else {
            Log.e(TAG,"Err: Unexpected APP status on Skip SKU function: "+ g_eAppSts);
        }
    }

    // THIS METHOD IS USED FOR THE SUBMITTING THE SKU THAT ARE MANUALLY ADDED BY THE USER FOR THE DEMOSTRATION PURPOSE
    void SubmitSkuDetails()
    {
        JSONObject items = new JSONObject();
        JSONObject assignedlist = new JSONObject();
        JSONArray skuDetails = new JSONArray();
        JSONArray labelList = new JSONArray();
        JSONArray assingedListArray = new JSONArray();
        String itemsString = "";

        try {
            skuDetails.put(g_bitemsSelected);
            if (g_bitemsSelected.size() == 0)
            {
                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setTitle("No Sku are Added");
                dialog.setMessage("Please Add the SKU to submit");
                dialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=dialog.create();
                alertDialog.show();
                return;
            }
            //FINAL JSON DATA REQUIRED FOR THE PARSING THE ASSIGNED LIST
            //JSON DATA IS CONSTRUCTED USING TEMPLATE GIVEN IN GDT_test_json_strings.java VARIABLE(STR_JSON_RES_GET_ORDERS)
            items.put("items", skuDetails);
            if (txtAddOrderNumber.getText().toString() == "")
            {
                items.put("orderid","9876543210-");
            }
            else
            {
                items.put("orderid",txtAddOrderNumber.getText().toString());
            }
            items.put("subtype", "FMCG-NH");
            items.put("pickid", "10704326");
            if (txtAddOrderNumber.getText().toString() == "")
            {
                labelList.put("9876543210-");
            }
            else
            {
                labelList.put(txtAddOrderNumber.getText().toString());
            }

            items.put("label_list", labelList);
            assingedListArray.put(items);
            assignedlist.put("assign_list", assingedListArray);
            assignedlist.put("status", "0");
            assignedlist.put("message", "OK");
            g_strAddSkudetails = assignedlist.toString();
            android.content.SharedPreferences.Editor editor = g_SettingsSharedPref.edit();
            editor.putString(DEMO_ADD_SKU_DETAILS,g_strAddSkudetails);
            editor.commit();
        }

        catch(Exception ex)
        {
            Toast.makeText(this, "UNABLE TO ADD SkuDetails", Toast.LENGTH_LONG).show();
        }
    }

    private boolean signUp() {
        boolean isValid = true;
        if (txtAddSkuQuantity.getText().toString().isEmpty()) {
            txtAddSkuQuantity.setError("Quantity is mandatory");
            isValid = false;
        }
        else if (txtAddSkuQuantity.getText().toString().equals("0")){
            txtAddSkuQuantity.setError("Quantity Cannot be Zero");
            isValid = false;
        }
        else {
            txtAddSkuQuantity.setError(null);
        }

        if (txtAddSkuDesc.getText().toString().isEmpty()) {
            txtAddSkuDesc.setError("Description is mandatory");
            isValid = false;
        } else {
            txtAddSkuDesc.setError(null);
        }

        if (txtAddSkuLocation.getText().toString().isEmpty()) {
            txtAddSkuLocation.setError("Location is mandatory");
            isValid = false;
        } else {
            txtAddSkuLocation.setError(null);
        }

        if (txtAddSkuBarcode.getText().toString().isEmpty() ) {
            txtAddSkuBarcode.setError("Barcode is Manadatory");
            isValid = false;
        } else {
            txtAddSkuBarcode.setError(null);
        }

        return isValid;
    }

    // THIS METHOD IS USED FOR THE ADDING THE SKU FOR THE DEMOSTRATION PURPOSE
    void AddSkuDetails() throws JSONException
    {
        JSONObject currentSkuDetails = new JSONObject();
        JSONArray eanList = new JSONArray();
        int itemsAdded = Integer.parseInt(txtSkuCountValue.getText().toString());
        //INDIVIDUAL ELEMENTS THAT ARE ADDED TO JSON DATA (ITEMS ARRAY)
        currentSkuDetails.put("sku", "sa");
        currentSkuDetails.put("qty", txtAddSkuQuantity.getText().toString());
        currentSkuDetails.put("desc", txtAddSkuDesc.getText().toString());
        currentSkuDetails.put("location", txtAddSkuLocation.getText().toString());
        eanList.put(txtAddSkuBarcode.getText().toString());
        currentSkuDetails.put("ean", eanList);
        g_bitemsSelected.add(currentSkuDetails);
        txtAddSkuQuantity.setText("");
        txtAddSkuDesc.setText("");
        txtAddSkuLocation.setText("");
        txtAddSkuBarcode.setText("");
        itemsAdded++;
        txtSkuCountValue.setText("" + itemsAdded);
    }

    static void UpdateDemoUserScreen()
    {
        g_OrdersClass_fromServer = null;
        g_OrdersClass_fromServer = new OrderClass();
        // this code is used for the when the new SKUS are getting added.
        g_strJsonGetorders = g_strAddSkudetails;
        //g_strJsonGetorders = GDT_test_Json_strings.STR_JSON_RES_GET_ORDERS;
        g_strJsonGetHoldList = GDT_test_Json_strings.STR_JSON_RES_GET_HOLD_LIST;
        txtOrderScanTitle.setBackgroundColor(Color.parseColor("#231F20"));
        parseGetOrdersHoldListJson();
        if (g_bStepOrderOrPick)
        {
            g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
            updateScreenViewOnAppStatusChange();
        }
        else if (g_bStepScanCrate)
        {
            txtCrateRfid.setText(NO_CRATE_SCANNED);
            txtOrderScanTitle.setText(SCAN_EMPTY_CRATE);
            g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE;
            updateScreenViewOnAppStatusChange();
        }
        else if (g_bStepScanOrderOrPick)
        {
            BeforeScanOrder();
            g_eAppSts = enumAppStatus.APP_STS_SCAN_ORDER;
            updateScreenViewOnAppStatusChange();
        }
        else if (g_bStepPickSKU)
        {
            BeforeScanOrder();
            UpdateBeforeScanlabel();
            UpdateBackGroundColorForSkuScan();
            g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
            updateScreenViewOnAppStatusChange();
        }
    }

    static void UpdateCommands()
    {


    }

    static void BeforeScanOrder()
    {
        g_strCrateLabel = "CB-Crate# 1";
        g_strJsonRfidstatus = GDT_test_Json_strings.STR_JSON_RES_GET_RFID_STATUS;
        g_serverReqErr = SERVER_REQ_STS_NO_ERR;
        txtCrateRfid.setText("" + g_strCrateLabel);
        txtOrderScanTitle.setText(SCAN_ORDER_APPEND + g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].orderid);
        txtSelectedOrder.setText(NO_ORDER_SCANNED);
        int order_index = g_OrdersClass_fromServer.order_index;
        //OrderClass.scanned_label_class new_class = new OrderClass().new scanned_label_class();
        //g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.add(new_class);
        g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.add(new OrderClass().new scanned_label_class());
        g_OrdersClass_fromServer.order_list[order_index].scanned_label_index =
                (g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size() - 1);
    }

    static void UpdateBackGroundColorForSkuScan()
    {
        txtSkuDescription.setBackgroundColor(Color.parseColor("#F6B603"));
        txtSkuLocation.setBackgroundColor(Color.parseColor("#555555"));
        txtOrderScanTitle.setBackgroundColor(Color.parseColor("#0000FF"));
        btnSkipSku.setEnabled(true);
    }

    static void UpdateBackGroundColorForSkuLocation()
    {
        txtOrderScanTitle.setText(SCAN_SKU);
        txtSkuLocation.setBackgroundColor(Color.parseColor("#F6B603"));
        txtSkuDescription.setBackgroundColor(Color.parseColor("#555555"));
    }

    static void UpdateBeforeScanlabel()
    {
        String str_barcode = "";
        String str_label_list = "";
        int order_index = g_OrdersClass_fromServer.order_index;
        int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;
        int label_list_qty = g_OrdersClass_fromServer.order_list[order_index].label_list_qty;
        //Record current time stamp as start time
        if (g_OrdersClass_fromServer.order_list[order_index].start_time_stamp.length() <= 2) {
            String strTimeStamp = getCurrentTimeStamp();
            g_OrdersClass_fromServer.order_list[order_index].start_time_stamp = strTimeStamp;
            Log.e(TAG, "Order Start time: " + strTimeStamp);
        }
        update_PauseTime();

        str_barcode  = g_OrdersClass_fromServer.order_list[order_index].label_list[0];
        str_barcode = str_barcode.substring(0, ((str_barcode.length()) - 1));
        g_progressDialog.setMessage("Order Scan: Success !!!");
        g_strOrderLabel = str_barcode;

        /* Update Order barcode in Txt field */
        txtSelectedOrder.setText(str_barcode);
        txtOrderScanTitle.setText(SCAN_SKU);
        UpdateBackGroundColorForSkuScan();

        /* Update Screen for SKU scan */
        txtSkuLocation.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location);
        txtSkuDescription.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc);
        txtQtyPicked.setText("" + g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked);
        txtCell_1_2.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location);
        txtCell_1_3.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc);
        showSKULocation = false;
    }

    static void UpdateScreenForSKULocation()
    {
        int order_index = g_OrdersClass_fromServer.order_index;
        int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;
        int label_list_qty = g_OrdersClass_fromServer.order_list[order_index].label_list_qty;

        // THIS IS USED FOR THE TO SET THE BACKGROUND COLOR BASED ON THE SCAN SKU/SKULOCATION
        UpdateBackGroundColorForSkuLocation();
        txtOrderScanTitle.setText(SCAN_SKU_LOACTION);
        showSKULocation = false;
        scanSKULocation = true;
        btnSkipSku.setEnabled(false);
        if (isOrderCompleted)
        {
            lastSkuLocation = true;
        }
    }

    static void updateScreenViewOnAppStatusChange() {
        //Log.e(TAG,"updateScreenViewOnAppStatusChange in appStatus: " + g_eAppSts);
        switch (g_eAppSts) {
            case APP_STS_HOLD_ORDER_DETAILS:
                frameActive.setVisibility(View.INVISIBLE);
                txtHoldOrderNumber.setText("" + g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].orderid);
                frameActive = frameHoldOrder;
                frameActive.setVisibility(View.VISIBLE);
                break;

            case APP_STS_LOGIN:
                if (frameActive == frameLogin){
                    break;
                }
                txtPickerId.setText("-NO LOGIN-");
                g_strUserName = "";
                g_strUserNameUcase = "";
                g_BluetoothProtocol.setAppToPcb_LoginStatus(LOGIN_NONE);
                //send_AppToPcbFrame();
                //Log.e(TAG, "enumAppStatus.APP_STS_SCAN_SKU @ updateScreenViewOnAppStatusChange");
                frameActive.setVisibility(View.INVISIBLE);
                if (g_progressDialog != null) {
                    g_progressDialog.dismiss();
                }
                //Log.e(TAG, "Service Screen");
                frameActive = frameLogin;
                frameLogin.setVisibility(View.VISIBLE);
                break;

            case APP_STS_SERVICE_SCREEN:
                if (frameActive == frameService){
                    break;
                }
                g_BluetoothProtocol.setAppToPcb_LoginStatus(LOGIN_SERVICE);
                send_AppToPcbFrame();
                //Log.e(TAG, "enumAppStatus.APP_STS_SCAN_SKU @ updateScreenViewOnAppStatusChange");
                frameActive.setVisibility(View.INVISIBLE);
                if (g_progressDialog != null) {
                    g_progressDialog.dismiss();
                }
                Log.e(TAG, "Service Screen");
                frameActive = frameService;
                frameActive.setVisibility(View.VISIBLE);
                updateService_Screen();
                break;

            case APP_STS_SERVICE_SETTINGS_SCREEN:
                if (frameActive == frameServiceSetting){
                    break;
                }
                //Log.e(TAG, "enumAppStatus.APP_STS_SCAN_SKU @ updateScreenViewOnAppStatusChange");
                frameActive.setVisibility(View.INVISIBLE);
                if (g_progressDialog != null) {
                    g_progressDialog.dismiss();
                }
                Log.e(TAG, "Service Screen");
                frameActive = frameServiceSetting;
                frameActive.setVisibility(View.VISIBLE);
                updateService_Screen();
                break;

            case APP_STS_ADMIN_SCREEN:
                if (frameActive == frameAdmin){
                    break;
                }
                g_BluetoothProtocol.setAppToPcb_LoginStatus(LOGIN_ADMIN);
                send_AppToPcbFrame();
                //Log.e(TAG, "enumAppStatus.APP_STS_SCAN_SKU @ updateScreenViewOnAppStatusChange");
                frameActive.setVisibility(View.INVISIBLE);
                if (g_progressDialog != null) {
                    g_progressDialog.dismiss();
                }
                Log.e(TAG, "Service Screen");
                frameActive = frameAdmin;
                frameActive.setVisibility(View.VISIBLE);
                break;

            case APP_STS_MOPTRO_SCREEN:
                if (frameActive == frameMOPTro){
                    break;
                }
                g_BluetoothProtocol.setAppToPcb_LoginStatus(LOGIN_MOPTRO);
                send_AppToPcbFrame();
                //Log.e(TAG, "enumAppStatus.APP_STS_SCAN_SKU @ updateScreenViewOnAppStatusChange");
                frameActive.setVisibility(View.INVISIBLE);
                if (g_progressDialog != null) {
                    g_progressDialog.dismiss();
                }
                frameActive = frameMOPTro;
                frameActive.setVisibility(View.VISIBLE);
                break;


            case APP_STS_ORDER_LIST:
                if (frameActive != frameOrderList) {
                    frameActive.setVisibility(View.INVISIBLE);
                    frameActive = frameOrderList;
                    frameActive.setVisibility(View.VISIBLE);
                    frameSystemStatus.setVisibility(View.INVISIBLE);
                    if (isDemoUser && isOrderCompleted )
                    {
                        break;
                    }
                    if (isDemoUser && !g_bStepOrderOrPick)
                    {
                        if (g_bStepScanCrate){
                            int order_index = g_OrdersClass_fromServer.order_index;
                            if ((g_OrdersClass_fromServer.order_list[order_index].start_time_stamp.length() > 2) &&
                                    (g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp == "")){
                                /* Crate scan due to crate item qty reach. Record it as Pause time */
                                //Log.e(TAG,"Pause time stamp: " + getCurrentTimeStamp());
                                /* Record paused time stamp */
                                g_OrdersClass_fromServer.order_list[order_index].stop_time_stamp = "";
                                g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp = getCurrentTimeStamp();
                            }
                            g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE;
                            updateScreenViewOnAppStatusChange();
                            break;
                        }
                        else if (g_bStepScanOrderOrPick )
                        {
                            BeforeScanOrder();
                            g_eAppSts = enumAppStatus.APP_STS_SCAN_ORDER;
                            updateScreenViewOnAppStatusChange();
                            break;
                        }
                        else if (g_bStepPickSKU)
                        {
                            BeforeScanOrder();
                            UpdateBeforeScanlabel();
                            g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                            updateScreenViewOnAppStatusChange();
                            break;
                        }
                    }
                }
                break;

            case APP_STS_SCAN_CRATE:
                frameActive.setVisibility(View.INVISIBLE);
                if (g_progressDialog != null) {
                    g_progressDialog.dismiss();
                }
                if (isDemoUser && !g_bStepScanCrate) {
                    if (g_bStepScanOrderOrPick) {
                        BeforeScanOrder();
                        g_eAppSts = enumAppStatus.APP_STS_SCAN_ORDER;
                        updateScreenViewOnAppStatusChange();
                        break;
                    } else if (g_bStepPickSKU) {
                        BeforeScanOrder();
                        UpdateBeforeScanlabel();
                        g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                        updateScreenViewOnAppStatusChange();
                        break;
                    }
                    else
                    {
                        frameActive.setVisibility(View.INVISIBLE);
                        frameActive = frameLogin;
                        frameActive.setVisibility(View.VISIBLE);
                        break;
                    }
                }
                frameActive = frameOrderScan;
                txtCrateRfid.setVisibility(View.VISIBLE);
                txtSelectedOrder.setVisibility(View.INVISIBLE);
                frameSkuDetails.setVisibility(View.INVISIBLE);
                frameActive.setVisibility(View.VISIBLE);
                btnReturnToOrderList.setVisibility(View.VISIBLE);
                frameSystemStatus.setVisibility(View.INVISIBLE);

                /* Record pause timestamp if Start time is already recorded and Pause time is Unrecorded */
                int order_index = g_OrdersClass_fromServer.order_index;
                if ((g_OrdersClass_fromServer.order_list[order_index].start_time_stamp.length() > 2) &&
                        (g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp == "")){
                    /* Crate scan due to crate item qty reach. Record it as Pause time */
                    //Log.e(TAG,"Pause time stamp: " + getCurrentTimeStamp());
                    /* Record paused time stamp */
                    g_OrdersClass_fromServer.order_list[order_index].stop_time_stamp = "";
                    g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp = getCurrentTimeStamp();
                    Log.e(TAG, "2. pause_time_stamp: " + g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp);
                }
                break;

            case APP_STS_SERVER_CRATE_CHECK:
            case APP_STS_SCAN_ORDER:
                frameActive.setVisibility(View.INVISIBLE);
                if (g_progressDialog != null) {
                    g_progressDialog.dismiss();
                }
                if (isDemoUser && !g_bStepScanOrderOrPick)
                {
                     if (g_bStepPickSKU)
                     {
                         BeforeScanOrder();
                         UpdateBeforeScanlabel();
                        g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                        updateScreenViewOnAppStatusChange();
                        break;
                     }
                     else
                     {
                         frameActive.setVisibility(View.INVISIBLE);
                         frameActive = frameLogin;
                         frameActive.setVisibility(View.VISIBLE);
                         break;
                     }
                }
                frameActive = frameOrderScan;
                txtSelectedOrder.setVisibility(View.VISIBLE);
                frameSkuDetails.setVisibility(View.INVISIBLE);
                frameActive.setVisibility(View.VISIBLE);
                frameSystemStatus.setVisibility(View.INVISIBLE);
                break;

            case APP_STS_SCAN_SKU:
                if (isDemoUser && !g_bStepPickSKU)
                {
                    frameActive.setVisibility(View.INVISIBLE);
                    frameActive = frameLogin;
                    frameActive.setVisibility(View.VISIBLE);
                    break;
                }

                if (isDemoUser && isOrderCompleted && lastSkuLocation)
                {
                    g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                    updateScreenViewOnAppStatusChange();
                    break;
                }
                frameActive.setVisibility(View.INVISIBLE);
                if (g_progressDialog != null) {
                    g_progressDialog.dismiss();
                }
                frameActive = frameOrderScan;
                frameSkuDetails.setVisibility(View.VISIBLE);
                txtSelectedOrder.setVisibility(View.VISIBLE);
                frameActive.setVisibility(View.VISIBLE);
                frameSystemStatus.setVisibility(View.INVISIBLE);

                //Call Direction Indication if Enabled
                if (g_bEnableDirGuide) {
                    GDT_Algorithm.DirectionIndication();
                }
                break;

            case APP_STS_DEMO_ADMIN:
                frameActive.setVisibility(View.INVISIBLE);
                if (g_progressDialog != null) {
                    g_progressDialog.dismiss();
                }
                frameActive = frameDemoAdmin;
                frameActive.setVisibility(View.VISIBLE);
                chkStepLogin.setVisibility(View.VISIBLE);
                chkStepOrderOrPick.setVisibility(View.VISIBLE);
                chkStepScanCrate.setVisibility(View.VISIBLE);
                chkStepScanOrderOrPick.setVisibility(View.VISIBLE);
                chkStepPickSKU.setVisibility(View.VISIBLE);
                txtDemoAdmin.setVisibility(View.VISIBLE);
                txtDemoAdmin.setText("Picking Sequence");
                break;

            case APP_STS_ADD_SKU_DEMO:
                frameActive.setVisibility(View.INVISIBLE);
                if (g_progressDialog != null) {
                    g_progressDialog.dismiss();
                }
                frameActive = frameAddSku;
                frameActive.setVisibility(View.VISIBLE);
                break;


            case APP_STS_ADD_ORDER:
                frameActive.setVisibility(View.INVISIBLE);
                if (g_progressDialog != null) {
                    g_progressDialog.dismiss();
                }
                frameActive = frameAddOrder;
                frameActive.setVisibility(View.VISIBLE);
                break;

            case APP_STS_STATUS:
                frameActive.setVisibility(View.INVISIBLE);
                if (g_progressDialog != null) {
                    g_progressDialog.dismiss();
                }
                frameActive = frameStatus;
                frameActive.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }

    private boolean validateCrate(String rxBuffer) {
        /*
            Do below listed validations in sequence:
            1) Length of Barcode String
            2) Not used in this Order (Crate Label list)
            3) Crate free to use (Server Check)
        */
        //Log.e(TAG, "Crate Scan: Verifying...");
        g_progressDialog.setTitle("**** CRATE SCAN ****");
        g_progressDialog.setMessage("Verifying... Please Wait.");
        g_progressDialog.show();
        int order_index = g_OrdersClass_fromServer.order_index;
        int scanned_label_qty = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size();

        boolean barcodeOk = false;
        int list_index = 0;
        String str_barcode = "";
        String Err = "UNKNOWN";
        if (g_bEnableFakeScan) {
            str_barcode = g_strCrateLabel;
        }else {
            str_barcode = getBarcodeStrFromBtStream(rxBuffer);
        }
        Log.e(TAG, "str_barcode: " + str_barcode);
        /* Check if this is a ORDER_STS_RESUMED_USED_CRATE */
        if (g_OrdersClass_fromServer.order_list[order_index].order_status == ORDER_STS_RESUMED_USED_CRATE) {
            /* Check for crate label present in the scanned list */
            for (list_index=0; list_index < g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size(); list_index++){
                if (str_barcode.equals(g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(list_index).crate_label)) {
                    /* This Barcode is already present in the scanned list */
                    barcodeOk = true;
                    Err = "UNKNOWN";
                    break;
                }else {
                    Err = "Not a used crate";
                }
            }

            /* Barcode present. Check for Crate full */
            if (barcodeOk) {
                if (g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(list_index).crate_item_qty >= g_iMaxItemsPerCrate) {
                    Err = "Crate is Full. Scan another used crate";
                    barcodeOk = false;
                }
            }

            /* Barcode is present in list */
            if (barcodeOk) {
                g_usedCrateScanned = true;
                /* Store the Index in scanned_label_index */
                g_OrdersClass_fromServer.order_list[order_index].scanned_label_index = list_index;
                /* Set order status IN_PROGRESS. Moved to After single SKU Scan success */
                //g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_IN_PROGRESS;
                /* Update UI display */
                txtCrateRfid.setText("" + str_barcode);
                String order_label = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(list_index).order_label;
                txtSelectedOrder.setText("" + order_label);
                txtSelectedOrder.setVisibility(View.VISIBLE);
                txtOrderScanTitle.setText(SCAN_SKU);

                /* Update Progress Dialog */
                g_progressDialog.setMessage("Crate Scan: Success !!!");
                g_MediaPlayClass.playRightScan(getApplicationContext());

                /* Move to SKU scan if in testing */
                if (g_bEnableFakeScan) {

                    if (g_progressDialog != null) g_progressDialog.dismiss();
                    /* Start SKU Scan */
                    g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                    sku_next();
                    update_PauseTime();
                    updateScreenViewOnAppStatusChange();
                }
            } else {
                g_progressDialog.setMessage("Error: " + Err);
                g_MediaPlayClass.playWrongScan(this);
                if ((g_bEnableFakeScan)&&(g_progressDialog != null)) g_progressDialog.dismiss();
                showInvalidBarcodeScanned("PLEASE SCAN RIGHT CRATE");
            }
        } else {
            barcodeOk = true;
            /* Validate Length */
            if (str_barcode.length() < 3) {
                /* Wrong data available in RX stream */
                barcodeOk = false;
            }

            /* Check for "CB-" as first three chars */
            if (barcodeOk) {
                if (str_barcode.substring(0, 3).equals("CB-")){
                    barcodeOk = true;
                }else {
                    barcodeOk = false;
                    Err = "Barcode not starting with CB-";
                    Log.e(TAG,"Err: Barcode not starting with CB-");
                }
            }

            /* Check if already used in this order */
            if ((barcodeOk) && (scanned_label_qty > 0)) {
                for (int scanned_label_index = 0; scanned_label_index < scanned_label_qty; scanned_label_index++) {
                    if (str_barcode.equals(g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).crate_label)) {
                        /* This Barcode is already present in the scanned list */
                        barcodeOk = false;
                        Log.e(TAG,"Err: Crate already used in this order");
                        Err = "Crate already used in this order";
                        break;
                    }
                }
            }

            /* If it is a test, make it pass */
            if(g_iServerSelected == SERVER_GD_TEST){
                barcodeOk = true;
            }

            if (isDemoUser)
            {
                if (str_barcode.length() < 3)
                {
                    /* Wrong data available in RX stream */
                    barcodeOk = false;
                }
            }

            if (barcodeOk) {
                /* Check for Crate free to use (Server Check) */
                g_strCrateLabel = str_barcode;
                g_eAppSts = enumAppStatus.APP_STS_SERVER_CRATE_CHECK;
                new ataskCheckRfid().execute(g_strCrateLabel); //goes in background to check the crate status
            } else {
                g_progressDialog.setMessage("Error: " + Err + ". Scan another");
                g_MediaPlayClass.playWrongScan(this);
                showInvalidBarcodeScanned("PLEASE SCAN RIGHT CRATE");
                if ((g_bEnableFakeScan)&&(g_progressDialog != null)) g_progressDialog.dismiss();
                if ((isDemoUser)&&(g_progressDialog != null)) g_progressDialog.dismiss();
            }
        }
        return  barcodeOk;
    }

    private boolean validateOrderBarcode(String rxBuffer) {
        /*
            Do below listed validations in sequence:
            1) Length of Barcode String
            2) String Contains Order ID in-between "-"
            3) Fetched Order ID equals to Current Order Id
            4) Label not used in this Order (Order Label list)
        **/
        g_progressDialog.setTitle("**** ORDER LABEL SCAN ****");
        g_progressDialog.setMessage("Verifying... Please Wait.");
        g_progressDialog.show();

        String str_barcode = "";
        String str_label_list = "";
        int order_index = g_OrdersClass_fromServer.order_index;
        int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;
        int label_list_qty = g_OrdersClass_fromServer.order_list[order_index].label_list_qty;
        boolean barcodeOk = true;

        if (label_list_qty <= 0){
            /* Order download issue. Say wrong scan  */
            barcodeOk = false;
        }else {
            /* Get Label list string */
            str_label_list = g_OrdersClass_fromServer.order_list[order_index].label_list[0];
            str_label_list = str_label_list.substring(0, (str_label_list.length() - 1));
            /* Get scanned string */
            if (g_bEnableFakeScan) {
                str_barcode = g_strOrderLabel;
            }else {
                str_barcode = getBarcodeStrFromBtStream(rxBuffer);
            }
        }

        Log.e(TAG, "Label String: " + str_label_list);
        Log.e(TAG, "Barcode String: " + str_barcode);

        /* Compare scanned string and label list string */
        if (barcodeOk) {
            barcodeOk = str_barcode.startsWith(str_label_list);
        }

        /* Barcode length is OK. Start validation */
        if (barcodeOk) {
            /* Check in Order label list */
            int scanned_label_qty = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size();

            /* Check from list of barcode */
            for (int i = 0; i< scanned_label_qty; i++) {
                if (str_barcode.equals(g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(i).order_label)) {
                    barcodeOk = false;
                    Log.e(TAG,"Err: Label already in list");
                    break;
                }
            }
            /* Bypass existing label check if in Test mode */
            if (g_iServerSelected == SERVER_GD_TEST){
                barcodeOk = true;
            }
        }else{
            Log.e(TAG,"Err: Barcode not matching with order id");
        }

        if(g_bEnableFakeScan){
            barcodeOk = true;
        }

        if (barcodeOk) {
            //Record current time stamp as start time
            if (g_OrdersClass_fromServer.order_list[order_index].start_time_stamp.length() <= 2) {
                String strTimeStamp = getCurrentTimeStamp();
                g_OrdersClass_fromServer.order_list[order_index].start_time_stamp = strTimeStamp;
                Log.e(TAG, "Order Start time: " + strTimeStamp);
            }
            update_PauseTime();

            g_progressDialog.setMessage("Order Scan: Success !!!");
            g_MediaPlayClass.playRightScan(this);
            g_strOrderLabel = str_barcode;

            /* Update Order barcode in Txt field */
            txtSelectedOrder.setText("" + g_strOrderLabel);
            txtOrderScanTitle.setText(SCAN_SKU);

            /* Update Screen for SKU scan */
            txtSkuLocation.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location);
            txtSkuDescription.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc);
            txtQtyPicked.setText("" + g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked);
            UpdateBackGroundColorForSkuScan();
            txtCell_1_2.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location);
            txtCell_1_3.setText(g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc);
            /* Start background process to get QOH for new item */
            g_strSkuCode = g_OrdersClass_fromServer.order_list[order_index].items[item_index].sku;
            new ataskGetQoh().execute(g_strSkuCode); //goes in background to get Qoh

            if (g_bEnableFakeScan || isDemoUser){
                g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                updateScreenViewOnAppStatusChange();
                //showSKULocation = true;
            }
        } else {
            g_progressDialog.setMessage("Error: Wrong label. Scan again :(");
            g_MediaPlayClass.playWrongScan(this);
            if (isDemoUser && g_progressDialog != null)
            {
                g_progressDialog.dismiss();
            }
            showInvalidBarcodeScanned("PLEASE SCAN RIGHT ORDER ID");
        }
        return barcodeOk;
    }

    // THIS METHOD IS USED FOR SHOWING DIALOG BOX WHEN THE USER IS SCANNING THE WRONG ITEMS/ORDER ID/ LOCATION OF SKU
    private void showInvalidBarcodeScanned(String message)
    {
        final Timer timer2 = new Timer();
        AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this, R.style.MyDialogTheme);
        builder.setTitle("WRONG BARCODE SCANNED");
        builder.setMessage(message);
        //builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                timer2.cancel();
            }
        });

        wrongScanDialog = builder.create();
        wrongScanDialog.show();
        Button buttonbackground = wrongScanDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground.setBackgroundColor(Color.BLUE);


        timer2.schedule(new TimerTask() {
            public void run() {
                wrongScanDialog.cancel();
                timer2.cancel(); //this will cancel the timer of the system
            }
        }, 5000); // the timer will count 5 seconds...
    }

    // THE FUNCTION IS USED TO VALIDATE THE SKU Location Barcode for demostration purpose only
    private boolean validateSkuLocationBarcode(String rxBuffer)
    {
        int order_index = g_OrdersClass_fromServer.order_index;
        int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;
        //for testingin
        boolean barcodeOk = false;
        String str_barcode = "";
        g_progressDialog.setTitle("**** SKU SCAN LOCATION ****");
        g_progressDialog.setMessage("Verifying... Please Wait.");
        g_progressDialog.show();

        str_barcode = getBarcodeStrFromBtStream(rxBuffer);
        barcodeOk = str_barcode.equals(g_OrdersClass_fromServer.order_list[order_index].items[item_index].location);

        if (barcodeOk) {
            g_progressDialog.setMessage("SKU Scan Location: Success !!!");
            g_MediaPlayClass.playRightScan(this);
            if (g_progressDialog != null)
            {
                g_progressDialog.dismiss();
            }

            if (g_bEnableFakeScan){
                // this method has to be changed for the multiple quantities if required
                g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                g_validSkuScanned = true;
                sku_next();
                updateScreenViewOnAppStatusChange();
            }
            //Increment Correct pick count
            g_u16SampleCountC++;
        } else {
            //Increment Wrong pick count
            g_u16SampleCountW++;
            g_progressDialog.setMessage("SKU Scan Location: Wrong Scan :(");
            g_MediaPlayClass.playWrongScan(this);
            if (isDemoUser)
            {
                g_progressDialog.dismiss();
            }
            showInvalidBarcodeScanned("PLEASE SCAN RIGHT SKU LOCATION");
        }
        return barcodeOk;
    }

    private boolean validateSkuBarcode(String rxBuffer) {
        /*
            Do below listed validations in sequence:
            1) Length of Barcode String
            2) Barcode Matches with ean list
        * */
        int order_index = g_OrdersClass_fromServer.order_index;
        int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;
        boolean barcodeOk = true;
        String str_barcode = "";
        g_progressDialog.setTitle("**** SKU SCAN ****");
        g_progressDialog.setMessage("Verifying... Please Wait.");
        g_progressDialog.show();

        if (g_bEnableFakeScan) {
            str_barcode = g_strSkuEanLabel;
        }else {
            str_barcode = getBarcodeStrFromBtStream(rxBuffer);
        }

        /* Validate Length */
        if (str_barcode.length() <= 0) {
            barcodeOk = false;
        }

        /* Match Ean list */
        if (barcodeOk) {
            barcodeOk = false;
            int ean_qty = g_OrdersClass_fromServer.order_list[order_index].items[item_index].ean_qty;
            for (int i = 0; i < ean_qty; i++) {
                if (str_barcode.equals(g_OrdersClass_fromServer.order_list[order_index].items[item_index].ean[i])) {
                    barcodeOk = true;
                    break;
                }
            }
        }

        if (barcodeOk) {
            g_progressDialog.setMessage("SKU Scan: Success !!!");
            g_progressDialog.setMessage("Place \""+
                    g_OrdersClass_fromServer.order_list[order_index].items[item_index].desc +
                    "\" to Crate, when crate Door is open.");
            g_MediaPlayClass.playRightScan(this);
            if (isDemoUser)
            {
                g_progressDialog.dismiss();
            }

            if (g_bEnableFakeScan){
                // this method has to be changed for the multiple quantities if required
                g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                g_validSkuScanned = true;
                sku_next();
                updateScreenViewOnAppStatusChange();
            }
            //Increment Correct pick count
            g_u16SampleCountC++;
        } else {
            //Increment Wrong pick count
            g_u16SampleCountW++;
            g_progressDialog.setMessage("SKU Scan: Wrong Scan :(");
            g_MediaPlayClass.playWrongScan(this);
            if (isDemoUser)
            {
                g_progressDialog.dismiss();
            }
            showInvalidBarcodeScanned("PLEASE SCAN RIGHT SKU");
        }

        return barcodeOk;
    }

    public void applyCustomAdapter() {
        ArrayList<String> list = new ArrayList<String>();
        if (g_OrdersClass_fromServer.order_list_count <= 0){
            //TODO: No orders assigned. Clear Order list and custom adapter
            list.clear();
        }else {
            for (int i = 0; i < g_OrdersClass_fromServer.order_list_count; i++) {
                list.add("");
            }
        }
        orderListAdapter = null;
        orderListAdapter = new CustomAdapter(list, this);
        listOrder.setAdapter(orderListAdapter);
        orderListAdapter.notifyDataSetChanged();
    }

    static void Call_PAKAAdvanced(){
        Log.e(TAG, "PAKA Advanced second Resorting");
        int order_comp = updateOrderCountText();
        Log.e(TAG, "orders completed : " + order_comp);
        Log.e(TAG, "orders list count : " + g_OrdersClass_fromServer.order_list_count);

        if(order_comp == (g_OrdersClass_fromServer.order_list_count)){
            frameActive.setVisibility(View.INVISIBLE);
            if (g_progressDialog != null) {
                g_progressDialog.dismiss();
            }
            frameActive = frameOrderList;
            frameActive.setVisibility(View.VISIBLE);
            frameSystemStatus.setVisibility(View.INVISIBLE);

            if (orderListAdapter != null) {
                orderListAdapter.notifyDataSetChanged();
            }
        }else{
            int position = GDT_Algorithm.pakaAdvanced(g_OrdersClass_fromServer.order_list);
            Log.e(TAG, "next order index is" + position);
            g_OrdersClass_fromServer.order_index = position;

            Log.e(TAG, "PAKA Basic Resorting");
            GDT_Algorithm.setPAKA_Basic(g_OrdersClass_fromServer.order_list[position].items, position);

            Log.e(TAG, "Sort Again");
            Log.e(TAG, "item_index " + g_OrdersClass_fromServer.order_list[position].item_index);

            txtCrateRfid.setText(NO_CRATE_SCANNED);
            txtOrderScanTitle.setText(SCAN_EMPTY_CRATE);
            g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE;
            btSend_BarcodeInvalid_ScannerActivate();
            updateScreenViewOnAppStatusChange();
        }
    }

    static String get_SetAddVinURL(String vin, String mac) {
        return GDT_GlobalData.GDT_URL_ADD_VIN + "vin=" + vin + "&mac=" + mac;
    }

    static String get_PostAddDiagURL() {
        return GDT_GlobalData.GDT_URL_ADD_DIAG;
    }

    static String get_CheckUserURL(String userName) {
        String finalURL = "";
        switch (g_iServerSelected) {
            case SERVER_GD_LIVE:
                finalURL = GDT_GlobalData.GDT_URL_CHECK_USER + userName;
                break;

            case SERVER_WH_DC_TEST:
                finalURL = GDT_GlobalData.BB_WH_DC_TEST_URL_CHECK_USER + userName;
                break;

            case SERVER_WH_DC_LIVE:
                finalURL = GDT_GlobalData.BB_WH_DC_LIVE_URL_CHECK_USER + userName;
                break;

            case SERVER_TYPED_IP:
                finalURL = GDT_GlobalData.TYPED_URL_CHECK_USER + userName;
                finalURL = g_strTypedIP + finalURL;
                break;

            default:
                finalURL = GDT_GlobalData.GDT_TEST_URL_CHECK_USER + userName;
                break;
        }
        return finalURL;
    }

    static String get_GetOrdersURL(String userName) {
        String finalURL = "";
        switch (g_iServerSelected) {
            case SERVER_GD_LIVE:
                finalURL = GDT_GlobalData.GDT_URL_GET_ORDERS + userName;
                break;

            case SERVER_WH_DC_TEST:
                finalURL = GDT_GlobalData.BB_WH_DC_TEST_URL_GET_ORDERS + userName;
                break;

            case SERVER_WH_DC_LIVE:
                finalURL = GDT_GlobalData.BB_WH_DC_LIVE_URL_GET_ORDERS + userName;
                break;

            case SERVER_TYPED_IP:
                finalURL = GDT_GlobalData.TYPED_URL_GET_ORDERS + userName;
                finalURL = g_strTypedIP + finalURL;
                break;

            default:
                finalURL = GDT_GlobalData.GDT_TEST_URL_GET_ORDERS + userName;
                break;
        }
        return finalURL;
    }

    static String get_RefreshSkuURL(String userName) {
        String finalURL = "";
        switch (g_iServerSelected) {
            case SERVER_GD_LIVE:
                finalURL = GDT_GlobalData.GDT_URL_GET_ORDERS + userName;
                break;

            case SERVER_WH_DC_TEST:
                finalURL = GDT_GlobalData.BB_WH_DC_TEST_URL_GET_ORDERS + userName;
                break;

            case SERVER_WH_DC_LIVE:
                finalURL = GDT_GlobalData.BB_WH_DC_LIVE_URL_GET_ORDERS + userName;
                break;

            case SERVER_TYPED_IP:
                finalURL = GDT_GlobalData.TYPED_URL_GET_ORDERS + userName;
                finalURL = g_strTypedIP + finalURL;
                break;

            default:
                finalURL = GDT_GlobalData.GDT_TEST_URL_GET_ORDERS + userName;
                break;
        }
        return finalURL;
    }

    static String get_QohURL(String skucode) {
        String finalURL = "";
        switch (g_iServerSelected) {
            case SERVER_GD_LIVE:
                finalURL = GDT_GlobalData.GDT_URL_QOH + skucode;
                break;

            case SERVER_WH_DC_TEST:
                finalURL = GDT_GlobalData.BB_WH_DC_TEST_URL_QOH + skucode;
                break;

            case SERVER_WH_DC_LIVE:
                finalURL = GDT_GlobalData.BB_WH_DC_LIVE_URL_QOH + skucode;
                break;

            case SERVER_TYPED_IP:
                finalURL = GDT_GlobalData.TYPED_URL_QOH + skucode;
                finalURL = g_strTypedIP + finalURL;
                break;

            default:
                finalURL = GDT_GlobalData.GDT_TEST_URL_QOH + skucode;
                break;
        }
        return finalURL;
    }

    static String get_PostOrdersURL() {
        String finalURL = "";
        switch (g_iServerSelected) {
            case SERVER_GD_LIVE:
                finalURL = GDT_GlobalData.GDT_URL_POST_ORDERS;
                break;

            case SERVER_WH_DC_TEST:
                finalURL = GDT_GlobalData.BB_WH_DC_TEST_URL_POST_ORDERS;
                break;

            case SERVER_WH_DC_LIVE:
                finalURL = GDT_GlobalData.BB_WH_DC_LIVE_URL_POST_ORDERS;
                break;

            case SERVER_TYPED_IP:
                finalURL = GDT_GlobalData.TYPED_URL_POST_ORDERS;
                finalURL = g_strTypedIP + finalURL;
                break;

            default:
                finalURL = GDT_GlobalData.GDT_TEST_URL_POST_ORDERS;
                break;
        }
        return finalURL;
    }

    static String get_SetHoldOrderURL() {
        String finalURL = "";
        switch (g_iServerSelected) {
            case SERVER_GD_LIVE:
                finalURL = GDT_GlobalData.GDT_URL_SET_HOLD_ORDER;
                break;

            case SERVER_WH_DC_TEST:
                finalURL = GDT_GlobalData.BB_WH_DC_TEST_URL_SET_HOLD_ORDER;
                break;

            case SERVER_WH_DC_LIVE:
                finalURL = GDT_GlobalData.BB_WH_DC_LIVE_URL_SET_HOLD_ORDER;
                break;

            case SERVER_TYPED_IP:
                finalURL = GDT_GlobalData.TYPED_URL_SET_HOLD_ORDER;
                finalURL = g_strTypedIP + finalURL;
                break;

            default:
                finalURL = GDT_GlobalData.GDT_TEST_URL_SET_HOLD_ORDER;
                break;
        }
        return finalURL;
    }

    static String get_GetHoldListURL(String userName) {
        String finalURL = "";
        switch (g_iServerSelected) {
            case SERVER_GD_LIVE:
                finalURL = GDT_GlobalData.GDT_URL_GET_HOLD_LIST + userName;
                break;

            case SERVER_WH_DC_TEST:
                finalURL = GDT_GlobalData.BB_WH_DC_TEST_URL_GET_HOLD_LIST + userName;
                break;

            case SERVER_WH_DC_LIVE:
                finalURL = GDT_GlobalData.BB_WH_DC_LIVE_URL_GET_HOLD_LIST + userName;
                break;

            case SERVER_TYPED_IP:
                finalURL = GDT_GlobalData.TYPED_URL_GET_HOLD_LIST + userName;
                finalURL = g_strTypedIP + finalURL;
                break;

            default:
                finalURL = GDT_GlobalData.GDT_TEST_URL_GET_HOLD_LIST;
                break;
        }
        return finalURL;
    }

    static String get_GetHoldDetailsURL(String userName, long pickid ) {
        String finalURL = "";
        switch (g_iServerSelected) {
            case SERVER_GD_LIVE:
                finalURL = GDT_GlobalData.GDT_URL_GET_HOLD_DETAILS + userName + "&PickId=" + pickid;
                break;

            case SERVER_WH_DC_TEST:
                finalURL = GDT_GlobalData.BB_WH_DC_TEST_URL_GET_HOLD_DETAILS + userName + "&PickId=" + pickid;
                break;

            case SERVER_WH_DC_LIVE:
                finalURL = GDT_GlobalData.BB_WH_DC_LIVE_URL_GET_HOLD_DETAILS + userName + "&PickId=" + pickid;
                break;

            case SERVER_TYPED_IP:
                finalURL = GDT_GlobalData.TYPED_URL_GET_HOLD_DETAILS + userName + "&PickId=" + pickid;
                finalURL = g_strTypedIP + finalURL;
                break;

            default:
                finalURL = GDT_GlobalData.GDT_TEST_URL_GET_HOLD_DETAILS + userName + "&PickId=" + pickid;
                break;
        }
        return finalURL;
    }

    static String get_SetHoldOrderAckURL() {
        String finalURL = "";
        switch (g_iServerSelected) {
            case SERVER_GD_LIVE:
                finalURL = GDT_GlobalData.GDT_URL_SET_ACK_HOLD_DETAILS;
                break;

            case SERVER_WH_DC_TEST:
                finalURL = GDT_GlobalData.BB_WH_DC_TEST_URL_SET_ACK_HOLD_DETAILS;
                break;

            case SERVER_WH_DC_LIVE:
                finalURL = GDT_GlobalData.BB_WH_DC_LIVE_URL_SET_ACK_HOLD_DETAILS;
                break;

            case SERVER_TYPED_IP:
                finalURL = GDT_GlobalData.TYPED_URL_SET_ACK_HOLD_DETAILS;
                finalURL = g_strTypedIP + finalURL;
                break;

            default:
                finalURL = GDT_GlobalData.GDT_TEST_URL_SET_ACK_HOLD_DETAILS;
                break;
        }
        return finalURL;
    }

    static String get_RfidStatusURL(String rfid) {
        String finalURL = "";
        switch (g_iServerSelected) {
            case SERVER_GD_LIVE:
                finalURL = GDT_GlobalData.GDT_URL_RFID_STATUS + rfid;
                break;

            case SERVER_WH_DC_TEST:
                finalURL = GDT_GlobalData.BB_WH_DC_TEST_URL_RFID_STATUS + rfid;
                break;

            case SERVER_WH_DC_LIVE:
                finalURL = GDT_GlobalData.BB_WH_DC_LIVE_URL_RFID_STATUS + rfid;
                break;

            case SERVER_TYPED_IP:
                finalURL = GDT_GlobalData.TYPED_URL_RFID_STATUS + rfid;

                break;

            default:
                finalURL = GDT_GlobalData.GDT_TEST_URL_RFID_STATUS + rfid;
                break;
        }
        return finalURL;
    }

    static String get_RequestMethod() {
        String method = "";
        switch (g_iServerSelected) {
            case SERVER_WH_DC_TEST:
            case SERVER_WH_DC_LIVE:
            case SERVER_TYPED_IP:
                method = "GET";
                break;

            case SERVER_GD_LIVE:
            default:
                method = "POST";
                break;
        }
        return method;
    }

    static void addLabel_crate(String crate_label) {
        /* Add crate in to label list */
        int order_index = g_OrdersClass_fromServer.order_index;
        int label_index = g_OrdersClass_fromServer.order_list[order_index].scanned_label_index;
        g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(label_index).crate_label = crate_label;
    }

    static void addLabel_order(String order_label) {
        /* Add order label in to list */
        int order_index = g_OrdersClass_fromServer.order_index;
        int label_index = g_OrdersClass_fromServer.order_list[order_index].scanned_label_index;
        g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(label_index).order_label = order_label;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    static void addSku_Labelitems(String sku) {
        //Log.e(TAG,"addSku_Labelitems. Received SKU: "+sku);
        /* Get Order id and Label Index */
        int order_index = g_OrdersClass_fromServer.order_index;
        int label_qty = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size();
        int scanned_label_index = g_OrdersClass_fromServer.order_list[order_index].scanned_label_index;

        /* Copy sku */
        if ((scanned_label_index < label_qty)  && (scanned_label_index >= 0)) {
            /* Check for the SKU availability */
            int sku_len = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).Labelitems_lists_jarray.length();
            //Log.e(TAG,"addSku_Labelitems. Labelitems_lists_jarray.length: "+sku_len);
            if (sku_len <= 0) {
                /* Just add the SKU */
                try {
                    JSONObject sku_object = new JSONObject();
                    sku_object.put("sku", sku);
                    sku_object.put("pickqty", 1);
                    sku_object.put("pickwt", 0);
                    g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).Labelitems_lists_jarray.put(sku_object);
                }catch (Exception e) {
                    /* Error */
                }
            } else {
                //Log.e(TAG,"addSku_Labelitems. sku_len > 0");
                boolean sku_found = false;
                for (int i =0; i<sku_len; i++) {
                    //Log.e(TAG,"addSku_Labelitems. sku_index: " + i);
                    JSONObject sku_object = new JSONObject();
                    try {
                        sku_object = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).Labelitems_lists_jarray.getJSONObject(i);
                        //Log.e(TAG,"addSku_Labelitems. sku_object: " + sku_object.toString());
                        if (sku_object.getString("sku").equals(sku)) {
                            /* sku found */
                            sku_found = true;
                            //Log.e(TAG,"Sku found in Index: "+i);
                            int pickqty = sku_object.getInt("pickqty");
                            pickqty++;
                            sku_object.put("pickqty", pickqty);
                            g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).Labelitems_lists_jarray.remove(i);
                            g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).Labelitems_lists_jarray.put(sku_object);
                            break;
                        }
                    }catch (Exception e) {
                        break;
                    }
                }
                /* check not found. Add it */
                if (!sku_found) {
                    try {
                        //Log.e(TAG,"Sku not found");
                        JSONObject sku_object = new JSONObject();
                        sku_object.put("sku", sku);
                        sku_object.put("pickqty", 1);
                        sku_object.put("pickwt", 0);
                        g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).Labelitems_lists_jarray.put(sku_object);
                    }catch (Exception e) {
                    /* Error */
                    }
                }
            }

            /* Frame ongoing order string */
            //TODO: commit ongoing order JSON
        } else{
            Log.e(TAG,"Err: (label_index < 0) or (label_index > label_qty)");
        }
    }

    static void  update_PauseTime() {
        int order_index = g_OrdersClass_fromServer.order_index;
        /* Calculate pause time */
        Log.e(TAG,"Pause Time Stamp: "+g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp);
        Log.e(TAG,"Pause Time sec(Start): "+g_OrdersClass_fromServer.order_list[order_index].pause_time_sec);
        if (g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp != "") {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strNow = getCurrentTimeStamp();
                Date d1 = null;
                Date d2 = null;
                d2 = format.parse(strNow);
                d1 = format.parse(g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp);
                long time_diff = d2.getTime() - d1.getTime();
                long diffSeconds = time_diff / 1000 % 60;
                long diffMinutes = time_diff / (60 * 1000) % 60;
                long diffHours = time_diff / (60 * 60 * 1000) % 24;

                /* Calculate time diff in seconds */
                long total_sec= diffSeconds + (diffMinutes * 60) + (diffHours * 60 * 60);
                //Log.e(TAG,"calculated_pause_sec: "+total_sec);

                /* Reset pause time stamp and calculate Pause time */
                g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp = "";
                g_OrdersClass_fromServer.order_list[order_index].pause_time_sec += total_sec;

                Log.e(TAG,"Pause Time sec(End): "+g_OrdersClass_fromServer.order_list[order_index].pause_time_sec);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //Log.e(TAG,"Pause time stamp is Empty. Current value of pause time in Sec: "
            //        +g_OrdersClass_fromServer.order_list[order_index].pause_time_sec);
        }
    }

    static long  get_TimeDiffSec(String strTimeStamp) {
        long total_sec = 0;
        if (strTimeStamp != "") {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strNow = getCurrentTimeStamp();
                Date d1 = null;
                Date d2 = null;
                d2 = format.parse(strNow);
                d1 = format.parse(strTimeStamp);
                long time_diff = d2.getTime() - d1.getTime();
                long diffSeconds = time_diff / 1000 % 60;
                long diffMinutes = time_diff / (60 * 1000) % 60;
                long diffHours = time_diff / (60 * 60 * 1000) % 24;

                /* Calculate time diff in seconds */
                total_sec= diffSeconds + (diffMinutes * 60) + (diffHours * 60 * 60);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {

        }
        return total_sec;
    }

    void update_Commands(String command)
    {
        if(!(command.equals(g_strCommand))) {
            android.content.SharedPreferences.Editor editor = g_SettingsSharedPref.edit();
            editor.putString(UPDATED_COMMANDS, command);
            editor.commit();
            g_strCommand = command;
        }
    }

    void  commit_ServiceSettings() {
        /* Read and convert entered data */
        String moptroNumber;
        int do1Time,do2Time,dc1Time,efpCycles;

        /* Validate for non zero data length. If zero, read from Existing value */
        if (SETTINGS_MOPTRO_NUMBER_LENGTH != txtGdtSettingMoptroNumberNew.getText().length()){
            moptroNumber = txtGdtSettingMoptroNumber.getText().toString();
        }else{
            moptroNumber = txtGdtSettingMoptroNumberNew.getText().toString();
        }
        if (txtGdtSettingEfpChainOpen1TimeNew.getText().length() > 0){
            do1Time = Integer.valueOf(txtGdtSettingEfpChainOpen1TimeNew.getText().toString()).intValue();
        }else {
            do1Time = Integer.valueOf(txtGdtSettingEfpChainOpen1Time.getText().toString()).intValue();
        }
        if (txtGdtSettingEfpChainOpen2TimeNew.getText().length() > 0){
            do2Time = Integer.valueOf(txtGdtSettingEfpChainOpen2TimeNew.getText().toString()).intValue();
        }else {
            do2Time = Integer.valueOf(txtGdtSettingEfpChainOpen2Time.getText().toString()).intValue();
        }
        if (txtGdtSettingEfpChainClose1TimeNew.getText().length() > 0){
            dc1Time = Integer.valueOf(txtGdtSettingEfpChainClose1TimeNew.getText().toString()).intValue();
        }else {
            dc1Time = Integer.valueOf(txtGdtSettingEfpChainClose1Time.getText().toString()).intValue();
        }
        if (txtGdtSettingEfpChainTestCyclesNew.getText().length() > 0){
            efpCycles = Integer.valueOf(txtGdtSettingEfpChainTestCyclesNew.getText().toString()).intValue();

        }else {
            efpCycles = Integer.valueOf(txtGdtSettingEfpChainTestCycles.getText().toString()).intValue();
        }

        /* Clear entered values in new text box */
        txtGdtSettingMoptroNumberNew.setText("");
        txtGdtSettingEfpChainOpen1TimeNew.setText("");
        txtGdtSettingEfpChainOpen2TimeNew.setText("");
        txtGdtSettingEfpChainClose1TimeNew.setText("");
        txtGdtSettingEfpChainTestCyclesNew.setText("");

        /* Validate Integer value based on MIN value setting */
        if (do1Time < SETTINGS_EFP_MIN_DOOR_TIME){
            do1Time = Integer.valueOf(txtGdtSettingEfpChainOpen1Time.getText().toString()).intValue();
        }
        if (do2Time < SETTINGS_EFP_MIN_DOOR_TIME){
            do2Time = Integer.valueOf(txtGdtSettingEfpChainOpen2Time.getText().toString()).intValue();
        }
        if (dc1Time < SETTINGS_EFP_MIN_DOOR_TIME){
            dc1Time = Integer.valueOf(txtGdtSettingEfpChainClose1Time.getText().toString()).intValue();
        }
        if (efpCycles < SETTINGS_EFP_MIN_AUTO_TEST_CYCLES){
            efpCycles = Integer.valueOf(txtGdtSettingEfpChainTestCycles.getText().toString()).intValue();
        }

        /* Update Global Variables */
        //g_strMoptroName = GDT_GlobalData.MOPTRO_NAME_APPEND + "" + moptroNumber;
        g_strMoptroName = "" + moptroNumber;
        g_strMoptroNumber = "" + moptroNumber;
        g_strDO1Time = "" + do1Time;
        g_strDO2Time = "" + do2Time;
        g_strDC1Time = "" + dc1Time;
        g_strEfpTestCycles = "" + efpCycles;

        /* Update Service Screen textbox with new value */
        txtGdtSettingMoptroNumber.setText(g_strMoptroNumber);
        txtGdtSettingEfpChainOpen1Time.setText(g_strDO1Time);
        txtGdtSettingEfpChainOpen2Time.setText(g_strDO2Time);
        txtGdtSettingEfpChainClose1Time.setText(g_strDC1Time);
        txtGdtSettingEfpChainTestCycles.setText(g_strEfpTestCycles);

        /* Commit the values in to FLASH Memory */
        android.content.SharedPreferences.Editor editor = g_SettingsSharedPref.edit();
        editor.putString(MOPTRO_NAME,g_strMoptroName);
        editor.putString(MOPTRO_NUMBER, g_strMoptroNumber);
        editor.putString(EFP_DO1_TIME, (g_strDO1Time));
        editor.putString(EFP_DO2_TIME, (g_strDO2Time));
        editor.putString(EFP_DC1_TIME, (g_strDC1Time));
        editor.putString(EFP_TEST_CYCLES, (g_strEfpTestCycles));
        editor.commit();

        /* Send New values to PCB through BT */
        g_BluetoothProtocol.setAppToPcb_MoptroName(g_strMoptroName);
        g_BluetoothProtocol.setAppToPcb_EfpDoorOpen1Time(g_strDO1Time);
        g_BluetoothProtocol.setAppToPcb_EfpDoorOpen2Time(g_strDO2Time);
        g_BluetoothProtocol.setAppToPcb_EfpDoorClose1Time(g_strDC1Time);
        g_BluetoothProtocol.setAppToPcb_EfpAutoTestCycles(g_strEfpTestCycles);
        send_AppToPcbFrame_ServiceSettings();
    }

    void commit_LoginDetails(String userDetails)
    {
        android.content.SharedPreferences.Editor editor = g_SettingsSharedPref.edit();
        editor.putString(PICKER_CREDENTIALS, userDetails);
        editor.commit();
    }

    void  commit_ServiceSettings2(String moptroNumber) {
        /* Read and convert entered data */
        int do1Time,do2Time,dc1Time,efpCycles;

        /* Validate for non zero data length. If zero, read from Existing value */

        if (txtGdtSettingEfpChainOpen1TimeNew.getText().length() > 0){
            do1Time = Integer.valueOf(txtGdtSettingEfpChainOpen1TimeNew.getText().toString()).intValue();
        }else {
            do1Time = Integer.valueOf(txtGdtSettingEfpChainOpen1Time.getText().toString()).intValue();
        }
        if (txtGdtSettingEfpChainOpen2TimeNew.getText().length() > 0){
            do2Time = Integer.valueOf(txtGdtSettingEfpChainOpen2TimeNew.getText().toString()).intValue();
        }else {
            do2Time = Integer.valueOf(txtGdtSettingEfpChainOpen2Time.getText().toString()).intValue();
        }
        if (txtGdtSettingEfpChainClose1TimeNew.getText().length() > 0){
            dc1Time = Integer.valueOf(txtGdtSettingEfpChainClose1TimeNew.getText().toString()).intValue();
        }else {
            dc1Time = Integer.valueOf(txtGdtSettingEfpChainClose1Time.getText().toString()).intValue();
        }
        if (txtGdtSettingEfpChainTestCyclesNew.getText().length() > 0){
            efpCycles = Integer.valueOf(txtGdtSettingEfpChainTestCyclesNew.getText().toString()).intValue();

        }else {
            efpCycles = Integer.valueOf(txtGdtSettingEfpChainTestCycles.getText().toString()).intValue();
        }

        /* Clear entered values in new text box */
        txtGdtSettingMoptroNumberNew.setText("");
        txtGdtSettingEfpChainOpen1TimeNew.setText("");
        txtGdtSettingEfpChainOpen2TimeNew.setText("");
        txtGdtSettingEfpChainClose1TimeNew.setText("");
        txtGdtSettingEfpChainTestCyclesNew.setText("");

        /* Validate Integer value based on MIN value setting */
        if (do1Time < SETTINGS_EFP_MIN_DOOR_TIME){
            do1Time = Integer.valueOf(txtGdtSettingEfpChainOpen1Time.getText().toString()).intValue();
        }
        if (do2Time < SETTINGS_EFP_MIN_DOOR_TIME){
            do2Time = Integer.valueOf(txtGdtSettingEfpChainOpen2Time.getText().toString()).intValue();
        }
        if (dc1Time < SETTINGS_EFP_MIN_DOOR_TIME){
            dc1Time = Integer.valueOf(txtGdtSettingEfpChainClose1Time.getText().toString()).intValue();
        }
        if (efpCycles < SETTINGS_EFP_MIN_AUTO_TEST_CYCLES){
            efpCycles = Integer.valueOf(txtGdtSettingEfpChainTestCycles.getText().toString()).intValue();
        }

        /* Update Global Variables */
        //g_strMoptroName = GDT_GlobalData.MOPTRO_NAME_APPEND + "" + moptroNumber;
        g_strMoptroName = "" + moptroNumber;
        g_strMoptroNumber = "" + moptroNumber;
        g_strDO1Time = "" + do1Time;
        g_strDO2Time = "" + do2Time;
        g_strDC1Time = "" + dc1Time;
        g_strEfpTestCycles = "" + efpCycles;

        /* Update Service Screen textbox with new value */
        txtGdtSettingMoptroNumber.setText(g_strMoptroNumber);
        txtGdtSettingEfpChainOpen1Time.setText(g_strDO1Time);
        txtGdtSettingEfpChainOpen2Time.setText(g_strDO2Time);
        txtGdtSettingEfpChainClose1Time.setText(g_strDC1Time);
        txtGdtSettingEfpChainTestCycles.setText(g_strEfpTestCycles);

        /* Commit the values in to FLASH Memory */
        android.content.SharedPreferences.Editor editor = g_SettingsSharedPref.edit();
        editor.putString(MOPTRO_NAME,g_strMoptroName);
        editor.putString(MOPTRO_NUMBER, g_strMoptroNumber);
        editor.putString(EFP_DO1_TIME, (g_strDO1Time));
        editor.putString(EFP_DO2_TIME, (g_strDO2Time));
        editor.putString(EFP_DC1_TIME, (g_strDC1Time));
        editor.putString(EFP_TEST_CYCLES, (g_strEfpTestCycles));
        editor.commit();

        /* Send New values to PCB through BT */
        g_BluetoothProtocol.setAppToPcb_MoptroName(g_strMoptroName);
        g_BluetoothProtocol.setAppToPcb_EfpDoorOpen1Time(g_strDO1Time);
        g_BluetoothProtocol.setAppToPcb_EfpDoorOpen2Time(g_strDO2Time);
        g_BluetoothProtocol.setAppToPcb_EfpDoorClose1Time(g_strDC1Time);
        g_BluetoothProtocol.setAppToPcb_EfpAutoTestCycles(g_strEfpTestCycles);
        send_AppToPcbFrame_ServiceSettings();
    }

    void  commit_AdminSettings() {
        Log.e(TAG,"commit_AdminSettings");
        String strAlgoSelected,strServerSelected,strTypedIP,strItemsPerCrate,strLocalUser,strLocalPassword;
        boolean bFakeScan,bEnableDoorSensor,bEnableCrateSensor = false;
        android.content.SharedPreferences.Editor editor = g_SettingsSharedPref.edit();

        /* Read Strings from fields */
        strAlgoSelected = String.valueOf(""+spnAlgoSelection.getSelectedItemPosition());
        strServerSelected = String.valueOf(""+spnServerSelection.getSelectedItemPosition());
        strTypedIP = String.valueOf(txtTypedServerIp.getText());
        strTypedIP = strTypedIP.trim();
        strLocalUser = String.valueOf(txtLocalUser.getText());
        strLocalUser = strLocalUser.trim();
        strLocalPassword = String.valueOf(txtLocalPassword.getText());
        strLocalPassword = strLocalPassword.trim();
        strItemsPerCrate = String.valueOf(txtItemsPerCrate.getText());
        strItemsPerCrate = strItemsPerCrate.trim();
        bFakeScan = chkEnableFakeScan.isChecked();

        /* Validate */
        if (strTypedIP.equals("") || strTypedIP.equals("0")){
            strTypedIP = g_strTypedIP;
        }
        if (Integer.valueOf(strItemsPerCrate) <= 0){
            strItemsPerCrate = ""+g_iMaxItemsPerCrate;
        }
        Log.e(TAG,"strAlgoSelected: " + strAlgoSelected);
        Log.e(TAG,"strServerSelected: " + strServerSelected);
        Log.e(TAG,"strTypedIP: " + strTypedIP);
        Log.e(TAG,"strItemsPerCrate: " + strItemsPerCrate);
        Log.e(TAG,"bFakeScan: " + bFakeScan);

        editor.putString(ALGO_SELECTED, strAlgoSelected);
        editor.putString(SERVER_SELECTED, strServerSelected);
        editor.putString(SERVER_TYPED, strTypedIP);
        editor.putString(LOCAL_USER, strLocalUser);
        editor.putString(LOCAL_PASSWORD, strLocalPassword);
        editor.putString(MAX_ITEMS_IN_CRATE, strItemsPerCrate);
        editor.putBoolean(ENABLE_FAKE_SCAN, bFakeScan);
        editor.putBoolean(ENABLE_BATTERY_DOOR_ALERT, chkEnableBatteryAlert.isChecked());
        editor.commit();

        /* Read Back and update in fields */
        //ADMIN Settings
        g_iAlgoSelected= Integer.valueOf(g_SettingsSharedPref.getString(ALGO_SELECTED, "1")).intValue();
        g_iServerSelected= Integer.valueOf(g_SettingsSharedPref.getString(SERVER_SELECTED, "1")).intValue();
        g_strTypedIP = g_SettingsSharedPref.getString(SERVER_TYPED, "");
        g_strLocalUser = g_SettingsSharedPref.getString(LOCAL_USER, "");
        g_strLocalPassword = g_SettingsSharedPref.getString(LOCAL_PASSWORD, "");
        g_iMaxItemsPerCrate = Integer.valueOf(g_SettingsSharedPref.getString(MAX_ITEMS_IN_CRATE, "17")).intValue();
        g_bEnableFakeScan = g_SettingsSharedPref.getBoolean(ENABLE_FAKE_SCAN, false);
        g_bEnableBatteryAlert = g_SettingsSharedPref.getBoolean(ENABLE_BATTERY_DOOR_ALERT, false);

        //Write back to Admin screen
        spnAlgoSelection.setSelection(g_iAlgoSelected);
        spnServerSelection.setSelection(g_iServerSelected);
        txtTypedServerIp.setText(g_strTypedIP);
        txtLocalUser.setText(g_strLocalUser);
        txtLocalPassword.setText(g_strLocalPassword);
        txtItemsPerCrate.setText("" + g_iMaxItemsPerCrate);
        chkEnableFakeScan.setChecked(g_bEnableFakeScan);
        chkEnableBatteryAlert.setChecked(g_bEnableBatteryAlert);
        if (g_bEnableBatteryAlert) {

            imgBatterDoorLogo.setVisibility(View.VISIBLE);
            txtBatteryDoorLogo.setVisibility(View.VISIBLE);
        }
        else
        {
            imgBatterDoorLogo.setVisibility(View.INVISIBLE);
            txtBatteryDoorLogo.setVisibility(View.INVISIBLE);
        }
        /* Enable or disable screen views based on settings */
        if (g_bEnableFakeScan) {
            btnFakeScan.setVisibility(View.VISIBLE);
        }else{
            btnFakeScan.setVisibility(View.INVISIBLE);
        }
        if (ALGO_PAKA_ADVANCED == g_iAlgoSelected) {
            btnNextOrder.setVisibility(View.VISIBLE);
        }else{
            btnNextOrder.setVisibility(View.INVISIBLE);
        }
        //Update App to PCB frame and Trigger TX
        if (g_bEnableEfp) {
            g_BluetoothProtocol.setAppToPcb_CMD0_EnableEFP();
        } else {
            g_BluetoothProtocol.setAppToPcb_CMD0_DisableEFP();
        }
        if (g_bEnableDoorSensor) {
            g_BluetoothProtocol.setAppToPcb_CMD0_EnableDoorSensor();
        } else {
            g_BluetoothProtocol.setAppToPcb_CMD0_DisableDoorSensor();
        }
        if (g_bEnableCrateSensor) {
            g_BluetoothProtocol.setAppToPcb_CMD0_EnableCrateSensor();
        } else {
            g_BluetoothProtocol.setAppToPcb_CMD0_DisableCrateSensor();
        }
        //send_AppToPcbFrame();
        Toast.makeText(this, "Updated. Please look at the updated values in screen.", Toast.LENGTH_SHORT).show();
    }

    void validateLoginCredentials(String userName, String password)
    {
        boolean isLoginSuccesfull = false;
        try
        {
            JSONArray userList = new JSONArray(g_strUserDetails);
            for(int i = 0; i < userList.length(); i++)
            {
                JSONObject individualUser = new JSONObject();
                individualUser = userList.getJSONObject(i);
                if(individualUser.get("picker_id").equals(userName) && individualUser.get("password").equals(password))
                {
                    isLoginSuccesfull = true;
                    txtPickerId.setText(individualUser.getString("picker_name"));
                    g_iPickerLoggedIn = 2;
                    generateHBForMCB();
                    frameActive.setVisibility(View.INVISIBLE);
                    frameActive = frameSystemStatus;
                    frameActive.setVisibility(View.VISIBLE);
                    break;
                }
            }

            if(isLoginSuccesfull == false)
            {
                Toast.makeText(this, "Error: Incorrect Username/Password.", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){
            Toast.makeText(this, "Error in login details from server.", Toast.LENGTH_LONG).show();
        }
    }

    void Commit_DemoAdminSettings()
    {
        android.content.SharedPreferences.Editor editor = g_SettingsSharedPref.edit();
        boolean bStepLogin, bStepOrderOrPick, bStepScanCrate, bStepScanOrderOrPick, bStepPickSKU, bStepSKULocation = false;

        // Picking Sequence boolean variables
        bStepLogin = chkStepLogin.isChecked();
        bStepOrderOrPick = chkStepOrderOrPick.isChecked();
        bStepScanCrate = chkStepScanCrate.isChecked();
        bStepScanOrderOrPick = chkStepScanOrderOrPick.isChecked();
        bStepPickSKU = chkStepPickSKU.isChecked();
        bStepSKULocation = chkStepSKULocation.isChecked();

        Log.e(TAG,"bStepLogin: " + bStepLogin);
        Log.e(TAG,"bStepOrderOrPick: " + bStepOrderOrPick);
        Log.e(TAG,"bStepScanCrate: " + bStepScanCrate);
        Log.e(TAG,"bStepScanOrderOrPick: " + bStepScanOrderOrPick);
        Log.e(TAG,"bStepPickSKU: " + bStepPickSKU);
        Log.e(TAG,"bStepSKULocation: " + bStepSKULocation);

        // Storing the Picking Sequence boolean Variables
        editor.putBoolean(ENABLE_STEP_LOGIN, bStepLogin);
        editor.putBoolean(ENABLE_STEP_ORDER_PICK, bStepOrderOrPick);
        editor.putBoolean(ENABLE_STEP_SCAN_CRATE, bStepScanCrate);
        editor.putBoolean(ENABLE_STEP_SCAN_ORDER_PICK, bStepScanOrderOrPick);
        editor.putBoolean(ENABLE_STEP_PICK_SKU, bStepPickSKU);
        editor.putBoolean(ENABLE_STEP_SKU_LOCATION, bStepSKULocation);
        editor.commit();

        g_bStepLogin = g_SettingsSharedPref.getBoolean(ENABLE_STEP_LOGIN, false);
        g_bStepOrderOrPick = g_SettingsSharedPref.getBoolean(ENABLE_STEP_ORDER_PICK, false);
        g_bStepScanCrate = g_SettingsSharedPref.getBoolean(ENABLE_STEP_SCAN_CRATE, false);
        g_bStepScanOrderOrPick = g_SettingsSharedPref.getBoolean(ENABLE_STEP_SCAN_ORDER_PICK, false);
        g_bStepPickSKU = g_SettingsSharedPref.getBoolean(ENABLE_STEP_PICK_SKU, false);
        g_bStepSKULOcation = g_SettingsSharedPref.getBoolean(ENABLE_STEP_SKU_LOCATION, false);

        Toast.makeText(this, "Updated the Values", Toast.LENGTH_SHORT).show();
    }

    void  commit_OngoingJsonString() {
        /* Commit g_strOnGoingOrderJson in to FLASH Memory */
        android.content.SharedPreferences.Editor editor = g_OnGoingOrderSharedPref.edit();
        editor.putString(ONGOING_ORDER_JSON,g_strOnGoingOrderJson);
        editor.commit();
    }

    void  commit_OdoValues() {
        Log.e(TAG,"Commit new Odo Values: "+g_iOdoKm+"."+g_iOdoMeter);

        android.content.SharedPreferences.Editor editor = g_SettingsSharedPref.edit();
        editor.putString(ODO_KM, (""+g_iOdoKm));
        editor.putString(ODO_METER, (""+g_iOdoMeter));
        editor.commit();
    }

    static String bytesArrToString (byte[] bytesArr){
        String s1 = new String(bytesArr);
        return s1;
    }

    public void  restart_Order(int position, boolean newCrate) {
        /* Restart scanning */
        int item_list_qty = g_OrdersClass_fromServer.order_list[position].item_list_qty;
        boolean new_item_index_found = false;

        for (int index = 0; index < item_list_qty; index++) {
            if (g_OrdersClass_fromServer.order_list[position].items[index].qty_skipped != 0) {
                g_OrdersClass_fromServer.order_list[position].items[index].qty_skipped = 0;
            }

            /* Get the new Index for the Item to be scanned */
            if (!new_item_index_found) {
                if (g_OrdersClass_fromServer.order_list[position].items[index].qty >
                        g_OrdersClass_fromServer.order_list[position].items[index].qty_picked) {
                    new_item_index_found = true;
                    g_OrdersClass_fromServer.order_list[position].item_index = index;
                }
            }
        }

        g_OrdersClass_fromServer.order_index = position;
        if (newCrate){
            txtCrateRfid.setText(NO_CRATE_SCANNED);
            txtOrderScanTitle.setText(SCAN_EMPTY_CRATE);
        }else {
            txtCrateRfid.setText(NO_CRATE_SCANNED);
            txtOrderScanTitle.setText(SCAN_USED_CRATE_APPEND
                    + g_OrdersClass_fromServer.order_list[position].orderid);
        }
        g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE;
        btSend_BarcodeNoSts_ScannerActivate();
        updateScreenViewOnAppStatusChange();
    }

    public void  clear_HoldSkuDetail() {
        ArrayList<String> hold_sku_desc = new ArrayList<>();
        ArrayList<String> hold_sku_qty = new ArrayList<>();
        /* Frame default string for in Empty slots */
        for (int fill_index = 0; fill_index < 10; fill_index++){
            hold_sku_desc.add(fill_index ,"-");
            hold_sku_qty.add(fill_index ,"-");
        }

        /* Add Data in to Screen */
        try {
            txtHoldSkuDesc1.setText(hold_sku_desc.get(0));
            txtHoldSkuQty1.setText(hold_sku_qty.get(0));
            txtHoldSkuDesc2.setText(hold_sku_desc.get(1));
            txtHoldSkuQty2.setText(hold_sku_qty.get(1));
            txtHoldSkuDesc3.setText(hold_sku_desc.get(2));
            txtHoldSkuQty3.setText(hold_sku_qty.get(2));
            txtHoldSkuDesc4.setText(hold_sku_desc.get(3));
            txtHoldSkuQty4.setText(hold_sku_qty.get(3));
            txtHoldSkuDesc5.setText(hold_sku_desc.get(4));
            txtHoldSkuQty5.setText(hold_sku_qty.get(4));
            txtHoldSkuDesc6.setText(hold_sku_desc.get(5));
            txtHoldSkuQty6.setText(hold_sku_qty.get(5));
            txtHoldSkuDesc7.setText(hold_sku_desc.get(6));
            txtHoldSkuQty7.setText(hold_sku_qty.get(6));
            txtHoldSkuDesc8.setText(hold_sku_desc.get(7));
            txtHoldSkuQty8.setText(hold_sku_qty.get(7));
            txtHoldSkuDesc9.setText(hold_sku_desc.get(8));
            txtHoldSkuQty9.setText(hold_sku_qty.get(8));
            txtHoldSkuDesc10.setText(hold_sku_desc.get(9));
            txtHoldSkuQty10.setText(hold_sku_qty.get(9));
        }catch(Exception e){
            /* Error in length of Array */
        }
    }

    private boolean update_orderSts() {
        //Get Order and Item index from class
        int order_status_new = ORDER_STS_NONE;
        int order_index = g_OrdersClass_fromServer.order_index;
        boolean return_value = true;
        if ((ORDER_STS_IN_PROGRESS == g_OrdersClass_fromServer.order_list[order_index].order_status) ||
                (ORDER_STS_RESUMED_USED_CRATE == g_OrdersClass_fromServer.order_list[order_index].order_status)){
            Toast.makeText(this, "Returning to LIST. Current OrderSts: " +
                    g_OrdersClass_fromServer.order_list[order_index].order_status, Toast.LENGTH_SHORT).show();

            int item_list_qty = g_OrdersClass_fromServer.order_list[order_index].item_list_qty;
            /* Update All SKU as Skipped */
            for (int item_index = 0; item_index < item_list_qty; item_index++) {
                g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_skipped =
                        g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty -
                                g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked;
            }

            /* Check if at least one item picked */
            for (int item_index = 0; item_index < item_list_qty; item_index++) {
                if (g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked != 0) {
                    cleanup_crate_list(order_index);
                    order_status_new = ORDER_STS_HOLD_POST_PENDING;
                    /* Record paused time stamp */
                    g_OrdersClass_fromServer.order_list[order_index].stop_time_stamp = "";
                    g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp = getCurrentTimeStamp();
                    break;
                }
            }

            /* Check if no item is picked */
            if (ORDER_STS_NONE == order_status_new) {
                cleanup_crate_list(order_index);
                order_status_new = ORDER_STS_NOT_STARTED;
                /* Clear off skipped qty */
                for (int item_index = 0; item_index < item_list_qty; item_index++) {
                    g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_skipped = 0;
                }
            }
        }else{
            /* Order Index is */
            return_value = false;
            Toast.makeText(this, "Error: Can't Return as Order status is: " +
                    g_OrdersClass_fromServer.order_list[order_index].order_status, Toast.LENGTH_SHORT).show();
        }
        g_OrdersClass_fromServer.order_list[order_index].order_status = order_status_new;
        /* Reset item index  and order Index for next round of order scan */
        g_OrdersClass_fromServer.order_list[order_index].item_index = 0;
        g_OrdersClass_fromServer.order_index = 0;

        /* Inactivate Scanner */
        btSend_BarcodeNoSts_ScannerInActivate();

        return return_value;
    }

    private void add_new_crate () {
        /* Add a new member in Array list */
        int order_index = g_OrdersClass_fromServer.order_index;
        //OrderClass.scanned_label_class new_class = new OrderClass().new scanned_label_class();
        //g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.add(new_class);
        g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.add(new OrderClass().new scanned_label_class());
        g_OrdersClass_fromServer.order_list[order_index].scanned_label_index =
                (g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size() - 1);
    }

    static void cleanup_crate_list (int order_index) {
        if (!(isDemoUser && isOrderCompleted))
        {
            if (g_OrdersClass_fromServer.order_list_count > order_index) {
                int array_size = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size();
                /* Remove empty crates fom list */
                for (int array_index = 0; array_index < array_size; array_index++) {
                    if (0 == g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(array_index).crate_item_qty) {
                        g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.remove(array_index);
                    }
                }
            }else{

            }
            /* Orders not downloaded yet */
        }
    }

    static void cleanup_crate_list_all_order () {
        int order_list_count = g_OrdersClass_fromServer.order_list_count;
        for (int order_index = 0; order_index < order_list_count; order_index++) {
            int array_size = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size();
            /* Add a new member in Array list */
            for (int array_index = 0; array_index < array_size; array_index++) {
                if (0 == g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(array_index).crate_item_qty) {
                    g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.remove(array_index);
                }
            }
        }
    }

    static boolean freeCrateAvailable() {
        /* Add crate in to label list */
        int order_index = g_OrdersClass_fromServer.order_index;
        int crate_list_size = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size();
        boolean free_crate_available = false;
        for (int i = 0; i < crate_list_size;i++){
            if (g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(i).crate_item_qty < g_iMaxItemsPerCrate){
                free_crate_available = true;
                break;
            }
        }
        return free_crate_available;
    }
    /**************************************************************/
    /* Asynchronous Tasks for Server Communications */
    /**************************************************************/

    /************************ Add Vin Number *************************/
    private class ataskAddHbToServer extends AsyncTask<String, Integer, Double> {
        HeartBeatFileOperations heartBeatFileOperations = new HeartBeatFileOperations();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            g_serverReqErr = SERVER_REQ_STS_NO_ERR;
        }

        @Override
        protected Double doInBackground(String... params) {
            InputStream inputStream = null;
            JSONObject postJson = null;
            try {
                String sURL = g_strTypedIP + HB_PHP;
//                if (g_iServerSelected == SERVER_TYPED_IP){
//                    sURL = g_strTypedIP + HB_PHP;
//                }else {
//                    sURL = URL_SNAIL_REALTIME + HB_PHP;
//                }
                URL url = new URL(sURL);
                Log.e(TAG, "" + url);

                //Create JSONObject here
                postJson = new JSONObject(g_strHbJsonFromMCB);
                // for testing purpose we are replacing the vin number
                if (g_bInPreProductionTesting)
                {
                    postJson.put("a", TESTVINNUMBER);
                }

                //Add Mobile charge percentage
                //Get mobile battery status
                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = registerReceiver(null, ifilter);
                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int batteryPct = (level) * (scale / 100);
                g_iMobilepercent = batteryPct;
                if (batteryPct > 100)
                {
                    batteryPct=100;
                }
                if(batteryPct <= 15)
                {
                    String hexstring = Integer.toHexString(batteryPct);
                    hexstring = "0008000" + hexstring;
                    postJson.put("z", hexstring);
                }
                else
                {
                    String hexstring = Integer.toHexString(batteryPct);
                    hexstring = "000800" + hexstring;
                    postJson.put("z", hexstring);
                }
                Log.e(TAG, "POSTING HB JSON");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                HttpsTrustManager.allowAllSSL();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty(API_KEY_NAME, API_KEY);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                urlConnection.setReadTimeout(SERVER_READ_TOUT);

                // Send POST output.
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(postJson.toString());
                out.close();

                //Log.e(TAG, "Response Code: " + urlConnection.getResponseCode());

                /* Send data to URL */
                inputStream = new BufferedInputStream(urlConnection.getInputStream());

                // Read the response and append as string
                //Log.e(TAG,"Response Code: " + urlConnection.getResponseCode());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                inputStream.close();
                g_strHbJsonFromServer = sb.toString();
                sb.delete(0, sb.length());
                //addStatusToHb();  /* Moved this functionality to continuous ping of 3 sec*/
            } catch (IOException e) {
                Log.e(TAG, "ataskAddHbToServer:"+"IOException");
                e.printStackTrace();
                heartBeatFileOperations.Save(getApplicationContext(), postJson, txtGdtSettingMoptroNumber.getText().toString());
                if (g_iServerSelected != SERVER_GD_LIVE) {
                    g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            if (g_serverReqErr != SERVER_REQ_STS_NO_ERR) {
                Log.e(TAG,"Error in HB response from server: "+g_serverReqErr);
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
            }else {
                Log.e(TAG,"g_strHbJsonFromServer: "+g_strHbJsonFromServer);
                Log.e(TAG,"g_strHbJsonFromServer length: "+g_strHbJsonFromServer.length());
                //check for received JSON length
                if (((SERVER_HB_RES_JSON_LEN - 1) == g_strHbJsonFromServer.length()) || (SERVER_HB_RES_JSON_LEN == g_strHbJsonFromServer.length())){
                    //Clean up the received string before sending to MCB

                    // UPDATE THE COMMANDS VALUE FOR OFFLINE STORAGE AS IT IS NOT PART OF PROTOCOL

                    g_strHbJsonFromMCB = "";
                    //Write received JSON g_strJsonAddHbToServer to MCB
                    if(InternetConnection.checkConnection(getApplicationContext()))
                    {
                        new AtaskAddLocalHeartBeatData().execute(getApplicationContext());
                    }
                }else {
                    // CONFIGURE THE CLOUD DATA
                    Log.e(TAG,"Error in JSON length");
                }
            }

            generateHBForMCB();
        }
    }

    private class ataskAddVinToServer extends AsyncTask<String, Integer, Double> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            g_strAddVinToServer = "";
//            g_progressDialog.setTitle("**** DOWNLOADING ****");
//            g_progressDialog.setMessage("Please Wait...");
//            g_progressDialog.show();
        }

        @Override
        protected Double doInBackground(String... params) {
            /* Download from Server */
            InputStream inputStream = null;
            try {
                URL url = new URL("https://greendzine.co.in/APIs/CONNECT/UserAppl/V1.0/Testing/VinGeneration.php?vin=" + txtGdtSettingMoptroNumber.getText().toString().trim().toUpperCase(Locale.ROOT));
                Log.e(TAG, "" + url);
                HttpsTrustManager.allowAllSSL();
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                urlConnection.setReadTimeout(SERVER_READ_TOUT);
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                Log.e(TAG, "Connection Error in CheckUser: " + e.toString());
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                inputStream.close();
                g_strAddVinToServer = sb.toString();
                sb.delete(0, sb.length());
            } catch (Exception e) {
                Log.e(TAG, "Error downloading / converting result: " + e.toString());
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }
            return null;
        }


        protected void onPostExecute(Double result)
        {
            g_progressDialog.dismiss();
            JSONObject jsonObject ;
            try
            {
                jsonObject = new JSONObject(g_strAddVinToServer);
                AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                builder.setTitle("");
                builder.setMessage(jsonObject.getString("message"));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            catch (JSONException ex)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                builder.setTitle("");
                builder.setMessage("Not able to add Vin Number");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        }
    }

    private int parseAddHbToServer(){
        //No functionality yet
        return 0;
    }

    /************************ Add Vin Number *************************/
    private class ataskAddVin extends AsyncTask<String, Integer, Double> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            g_iVinAdded = ADD_VIN_STS_IN_PROCESS;
            g_serverReqErr = SERVER_REQ_STS_NO_ERR;
            g_strJsonAddVIN = "";
            g_progressDialog.setTitle("**** Adding VIN ****");
            g_progressDialog.setMessage("Sending MAC to GDT Server. Please Wait...");
            g_progressDialog.show();
        }

        @Override
        protected Double doInBackground(String... params) {
            /* Send to Greendzine Server */
            InputStream inputStream = null;
            try {
                URL url = new URL(get_SetAddVinURL(g_strMoptroName,g_sWifiAddress));
                Log.e(TAG,""+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                HttpsTrustManager.allowAllSSL();
                urlConnection.setRequestMethod("POST");
                urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                urlConnection.setReadTimeout(SERVER_READ_TOUT);
                inputStream = new BufferedInputStream(urlConnection.getInputStream());

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    inputStream.close();
                    g_strJsonAddVIN = sb.toString();
                    sb.delete(0, sb.length());
                } catch (Exception e) {
                    Log.e(TAG, "Error downloading / converting result: " + e.toString());
                    g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
                }

            } catch (IOException e) {
                Log.e(TAG, "Connection Error in CheckUser: " + e.toString());
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            Log.e(TAG,"g_strJsonAddVIN: "+g_strJsonAddVIN);
            if (g_serverReqErr != SERVER_REQ_STS_NO_ERR) {
                if (g_progressDialog != null) g_progressDialog.dismiss();
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                builder.setTitle("ERROR CODE: "+STS_CONNECTION_ERROR);
                builder.setMessage("Connection Failure. Will Try again");
                builder.setPositiveButton(getString(R.string.ok_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                g_iVinAdded = ADD_VIN_STS_NOT_ADDED;
            } else {
                /* Parse JSON String */
                int status = parseAddVinResponse();
                if ((status == STS_OK) || (status == STS_POST_ADD_VIN_ALREADY_EXISTING)) {
                    if (g_progressDialog != null) g_progressDialog.dismiss();
                    /* Vin added */
                    g_iVinAdded = ADD_VIN_STS_ADDED;
                } else {
                    if (g_progressDialog != null) g_progressDialog.dismiss();
                    String errorMsg = "";
                    if (status == STS_POST_ADD_VIN_DB_ERROR) {
                        errorMsg = "Database Error";
                    } else {
                        errorMsg = "Unknown error. Please try again.";
                    }
                    /* Show alert builder */
                    AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                    builder.setTitle("ERROR CODE: " + status);
                    builder.setMessage(errorMsg);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        }
    }

    private int parseAddVinResponse() {
        int status = STS_OK;
        /* Decode JSON for get Order list */
        try {
            JSONObject obj = new JSONObject(g_strJsonAddVIN);
            status = obj.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
            status = STS_PARSE_ERROR;
        }
        return status;
    }

    /************************ Add Vin Number *************************/
    private class ataskAddDiag extends AsyncTask<String, Integer, Double> {
        DiagFileOperations diagFileOperations = new DiagFileOperations();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            g_serverReqErr = SERVER_REQ_STS_NO_ERR;
            g_strDiagJsonFromServer  = "";
        }

        @Override
        protected Double doInBackground(String... params) {
            InputStream inputStream = null;
            JSONObject postJson =  null;
            try {
                /* Process the Download for identified order_index */
                String sURL = g_strTypedIP + DIAG_PHP;
//                /* Process the Download for identified order_index */
//                if (g_iServerSelected == SERVER_TYPED_IP){
//                    sURL = g_strTypedIP + PHP_SNAIL_DIAG;
//                }else {
//                    sURL = URL_SNAIL_REALTIME + PHP_SNAIL_DIAG;
//                }
                URL url = new URL(sURL);
                Log.e(TAG, "" + url);

                // for testing purpose we are replacing the vin number
                if (g_bInPreProductionTesting)
                {
                    g_strHbJsonFromMCB = g_strHbJsonFromMCB.replace(OLDVINNUMBER, TESTVINNUMBER);
                }
                //Create JSONObject here
                postJson = new JSONObject(g_strDiagJsonFromMCB);
                if (g_bInPreProductionTesting)
                {
                    postJson.put("a", TESTVINNUMBER);
                }

                //Add Mobile charge percentage
                //Get mobile battery status
                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = registerReceiver(null, ifilter);
                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int batteryPct = (level) * (scale / 100);
                if (batteryPct > 100){batteryPct=100;}
                if(batteryPct <= 15)
                {
                    String hexstring = Integer.toHexString(batteryPct);
                    hexstring = "001f000" + hexstring + GDT_GlobalData.APP_VERSION;
                    postJson.put("z", hexstring);
                }
                else
                {
                    String hexstring = Integer.toHexString(batteryPct);
                    hexstring = "001f00" + hexstring + GDT_GlobalData.APP_VERSION;
                    postJson.put("z", hexstring);
                }

                Log.e(TAG, "POSTING DIAG JSON");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                HttpsTrustManager.allowAllSSL();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty(API_KEY_NAME,API_KEY);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                urlConnection.setReadTimeout(SERVER_READ_TOUT);

                // Send POST output.
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(postJson.toString());
                out.close();

                Log.e(TAG, "Response Code: " + urlConnection.getResponseCode());

                /* Send data to URL */
                inputStream = new BufferedInputStream(urlConnection.getInputStream());

                // Read the response and append as string
                //Log.e(TAG,"Response Code: " + urlConnection.getResponseCode());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                inputStream.close();
                g_strDiagJsonFromServer = sb.toString();
                sb.delete(0, sb.length());
            } catch (IOException e) {
                // NOT INTERNET SO STORE THE DIAGNOSTIC DATA

                diagFileOperations.Save(getApplicationContext(), postJson.toString(), txtGdtSettingMoptroNumber.getText().toString());
                e.printStackTrace();
                if (g_iServerSelected != SERVER_GD_LIVE) {
                    g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            if (g_serverReqErr != SERVER_REQ_STS_NO_ERR) {
                Log.e(TAG,"Error in DIAG response from server: "+g_serverReqErr);
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
            }else {
                Log.e(TAG,"g_strDiagJsonFromServer: "+g_strDiagJsonFromServer);
                //Clean up the received string before sending to MCB
                g_strDiagJsonFromMCB = "";
                if (InternetConnection.checkConnection(getApplicationContext()))
                {
                    new AtaskAddLocalDiagData().execute(getApplicationContext());
                }
                //No action decided yet
            }
        }
    }

    private int parseAddDiagResponse() {
        //No functionality yet
        return 0;
    }

    /************************ Check User *************************/
    private class ataskCheckUser extends AsyncTask<String, Integer, Double> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            g_strJsonCheckuser = "";
            g_progressDialog.setTitle("**** CHECKING USER ****");
            g_progressDialog.setMessage("Logging-in. Please Wait...");
            g_progressDialog.show();
        }

        @Override
        protected Double doInBackground(String... params) {
            /* Download from Server */
            InputStream inputStream = null;
            try {
                URL url = new URL(get_CheckUserURL(g_strUserName));
                Log.e(TAG,""+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(get_RequestMethod());
                urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                urlConnection.setReadTimeout(SERVER_READ_TOUT);
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                Log.e(TAG, "Connection Error in CheckUser: " + e.toString());
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                inputStream.close();
                g_strJsonCheckuser = sb.toString();
                sb.delete(0, sb.length());
            } catch (Exception e) {
                Log.e(TAG, "Error downloading / converting result: " + e.toString());
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            if (g_iServerSelected == SERVER_GD_TEST) {
                /* Copy JSON stings */
                g_strJsonCheckuser = GDT_test_Json_strings.STR_JSON_RES_GET_CHECK_USER;
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
            }
            Log.e(TAG,"g_strJsonCheckuser: "+g_strJsonCheckuser);
            if (g_serverReqErr != SERVER_REQ_STS_NO_ERR) {
                if (g_progressDialog != null) g_progressDialog.dismiss();
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                builder.setTitle("ERROR CODE: "+STS_CONNECTION_ERROR);
                builder.setMessage("Connection Failure. Try again");
                builder.setPositiveButton(getString(R.string.ok_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                /* Parse JSON String */
                int status = parseCheckUserResponse();
                if (status == STS_OK) {
                    g_bLoggedIn = true;
                    if (g_progressDialog != null) g_progressDialog.dismiss();
                    txtPickerId.setText("" + g_strUserNameUcase);
                    /* Login Successful. Go to Order list screen and show dummy */
                    g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                    updateOrderCountText();
                    updateScreenViewOnAppStatusChange();
                    /* Update MCB on login status */
                    g_BluetoothProtocol.setAppToPcb_LoginStatus(LOGIN_USER);
                    send_AppToPcbFrame();
                } else {
                    g_bLoggedIn = false;
                    if (g_progressDialog != null) g_progressDialog.dismiss();
                    String errorMsg = "";
                    if (status == STS_GET_ORDERS_INVALID_USERID) {
                        errorMsg = "Invalid User Id.";
                    } else if (status == STS_PARSE_ERROR) {
                        errorMsg = "Parse Error. Report to Technical Team.";
                    } else {
                        errorMsg = "Unknown error. Please try again.";
                    }
                    /* Show alert builder */
                    AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                    builder.setTitle("ERROR CODE: " + status);
                    builder.setMessage(errorMsg);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        }
    }

    private int parseCheckUserResponse() {
        int status = STS_OK;
        /* Decode JSON for get Order list */
        try {
            JSONObject obj = new JSONObject(g_strJsonCheckuser);
            status = obj.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
            status = STS_PARSE_ERROR;
        }
        return status;
    }



    /********************* Download Assigned Order List and Hold List ***************/
    private class ataskDownloadOrderAndHoldList extends AsyncTask<String, Integer, Double> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            g_strJsonGetorders = "";
            g_strJsonGetHoldList = "";
            g_progressDialog.setTitle("**** ORDER DOWNLOAD ****");
            g_progressDialog.setMessage("Downloading Assigned Order List. Please wait...");
            g_progressDialog.show();
        }

        @Override
        protected Double doInBackground(String... params) {
            /* Download from Server */
            InputStream inputStream = null;
            try {
                /* Download Order list JSON */
                URL url = new URL(get_GetOrdersURL(g_strUserName));
                Log.e(TAG,""+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(get_RequestMethod());
                urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                urlConnection.setReadTimeout(SERVER_READ_TOUT);
                inputStream = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                inputStream.close();
                g_strJsonGetorders = sb.toString();
                sb.delete(0, sb.length());


                //Download Hold Order list JSON
                //g_progressDialog.setMessage("Downloading Hold Order List. Please wait...");
                try{
                    InputStream inputStream1 = null;
                    URL url1 = new URL(get_GetHoldListURL(g_strUserName));
                    Log.e(TAG,""+url1);
                    HttpURLConnection urlConnection1 = (HttpURLConnection) url1.openConnection();
                    urlConnection1.setRequestMethod(get_RequestMethod());
                    urlConnection1.setConnectTimeout(SERVER_CONNECT_TOUT);
                    urlConnection1.setReadTimeout(SERVER_READ_TOUT);
                    inputStream1 = new BufferedInputStream(urlConnection1.getInputStream());

                    BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream1, "iso-8859-1"), 8);
                    StringBuilder sb1 = new StringBuilder();
                    String line1 = null;
                    while ((line1 = reader1.readLine()) != null) {
                        sb1.append(line1 + "\n");
                    }
                    inputStream1.close();
                    g_strJsonGetHoldList = sb1.toString();
                    sb1.delete(0, sb1.length());

                } catch (IOException e) {
                    g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
                }
            } catch (IOException e) {
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            updateOrderCountText();
            if (g_iServerSelected == SERVER_GD_TEST) {
                /* Copy JSON stings */
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                g_strJsonGetorders = GDT_test_Json_strings.STR_JSON_RES_GET_ORDERS;
                g_strJsonGetHoldList = GDT_test_Json_strings.STR_JSON_RES_GET_HOLD_LIST;
            }

            Log.e(TAG,"g_strJsonGetorders: "+g_strJsonGetorders);
            Log.e(TAG,"g_strJsonGetHoldList: "+g_strJsonGetHoldList);
            if (g_serverReqErr != SERVER_REQ_STS_NO_ERR) {
                if (g_progressDialog != null) g_progressDialog.dismiss();
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                builder.setTitle("ERROR CODE: "+STS_CONNECTION_ERROR);
                builder.setMessage("Connection Failure. Try again");
                builder.setPositiveButton(getString(R.string.ok_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                /* Parse JSON String */
                int status = parseGetOrdersHoldListJson();
                if (status == STS_OK) {
                    /* Download Successful */
                    if (g_progressDialog != null) g_progressDialog.dismiss();
                    applyCustomAdapter();
                    updateScreenViewOnAppStatusChange();
                } else {
                    int errorCode = status;
                    String errorMsg = "";
                    if (status == STS_GET_ORDERS_INVALID_USERID) {
                        errorMsg = "Invalid User Id.";
                    } else if (status == STS_GET_ORDERS_NO_ORDERS_ASSIGNED) {
                        errorMsg = "No Orders assigned";
                    } else if (status == STS_PARSE_ERROR) {
                        errorMsg = "Parse Error. Report to Technical Team.";
                    } else {
                        errorCode = STS_UNKNOWN_ERROR;
                        errorMsg = "Unknown error. Please try again.";
                    }
                    if (g_progressDialog != null) g_progressDialog.dismiss();
                    /* Show alert builder */
                    AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                    builder.setTitle("Error code: " + errorCode);
                    builder.setMessage(errorMsg);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
            /* Move back to Order list */
            g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
        }
    }

    private static int parseGetOrdersHoldListJson() {
        int status = STS_OK;
        int status1 = STS_OK;
        int final_status = STS_OK;
        try {
            JSONObject obj = new JSONObject(g_strJsonGetorders);
            JSONObject obj1 = new JSONObject(g_strJsonGetHoldList);
            g_strJsonGetorders = "";
            g_strJsonGetHoldList = "";
            status = obj.getInt("status");
            status1 = obj1.getInt("Status");
            int new_order_count = 0;
            int hold_order_count = 0;

            /* Get New Order Count */
            if (STS_OK == status) {
                JSONArray assign_list = new JSONArray();
                assign_list = obj.getJSONArray("assign_list");
                new_order_count = assign_list.length();
                if (0 == new_order_count){
                    status = STS_GET_ORDERS_NO_ORDERS_ASSIGNED;
                }
            }else{
                final_status = status;
            }

            /* Get Hold Order Count */
            if (STS_OK == status1){
                JSONArray HoldList_array = new JSONArray();
                HoldList_array = obj1.getJSONArray("holdList");
                hold_order_count = HoldList_array.length();
                if (0 == hold_order_count){
                    status1 = STS_GET_HOLD_LIST_EMPTY;
                }
            }

            g_OrdersClass_fromServer.order_list_count = new_order_count;

            if (g_OrdersClass_fromServer.order_list_count > 0){
                g_OrdersClass_fromServer.order_list = new OrderClass.assign_list_class[g_OrdersClass_fromServer.order_list_count];
            }

            /**************** Parse New Order List *****************/
            if (STS_OK == status) {
                g_OrdersClass_fromServer.status = status;
                g_OrdersClass_fromServer.order_index = 0;
                g_OrdersClass_fromServer.message = obj.getString("message");
                JSONArray assign_list = new JSONArray();
                assign_list = obj.getJSONArray("assign_list");
                JSONObject order = new JSONObject();
                for (int i = 0; i < new_order_count; i++) {
                    /* Instantiate each Assign List class */
                    g_OrdersClass_fromServer.order_list[i] = new OrderClass().new assign_list_class();
                    /* Get Order details */
                    order = assign_list.getJSONObject(i);
                    g_OrdersClass_fromServer.order_list[i].order_status = ORDER_STS_NOT_STARTED;
                    g_OrdersClass_fromServer.order_list[i].orderid = order.getString("orderid");
                    g_OrdersClass_fromServer.order_list[i].subtype = order.getString("subtype");
                    g_OrdersClass_fromServer.order_list[i].pickid = order.getLong("pickid");

                    JSONArray label_list = new JSONArray();
                    label_list = order.getJSONArray("label_list");
                    g_OrdersClass_fromServer.order_list[i].label_list_qty = label_list.length();
                    g_OrdersClass_fromServer.order_list[i].label_list = new String[g_OrdersClass_fromServer.order_list[i].label_list_qty];
                    for (int j = 0; j < g_OrdersClass_fromServer.order_list[i].label_list_qty; j++) {
                        g_OrdersClass_fromServer.order_list[i].label_list[j] = new String();
                        g_OrdersClass_fromServer.order_list[i].label_list[j] = label_list.getString(j);
                    }
                    /* Get Items */
                    JSONArray items_list = new JSONArray();
                    items_list = order.getJSONArray("items");
                    if (isDemoUser)
                    {
                        String items = items_list.get(0).toString();
                        items_list = new JSONArray(items);
                    }
                    g_OrdersClass_fromServer.order_list[i].item_list_qty = items_list.length();
                    g_OrdersClass_fromServer.order_list[i].items = new OrderClass.items_class[g_OrdersClass_fromServer.order_list[i].item_list_qty];
                    JSONObject item = new JSONObject();
                    for (int k = 0; k < g_OrdersClass_fromServer.order_list[i].item_list_qty; k++) {
                        item = items_list.getJSONObject(k);
                        g_OrdersClass_fromServer.order_list[i].items[k] = new OrderClass().new items_class();
                        g_OrdersClass_fromServer.order_list[i].items[k].qty_picked = 0;
                        g_OrdersClass_fromServer.order_list[i].items[k].qty_skipped = 0;
                        g_OrdersClass_fromServer.order_list[i].items[k].sku = item.getString("sku");

                        JSONArray ean_list = new JSONArray();
                        ean_list = item.getJSONArray("ean");
                        g_OrdersClass_fromServer.order_list[i].items[k].ean_qty = ean_list.length();
                        if (g_OrdersClass_fromServer.order_list[i].items[k].ean_qty <= 0){
                            g_OrdersClass_fromServer.order_list[i].items[k].ean_qty = 1;
                            g_OrdersClass_fromServer.order_list[i].items[k].ean = new String[g_OrdersClass_fromServer.order_list[i].items[k].ean_qty];
                            g_OrdersClass_fromServer.order_list[i].items[k].ean[0] = new String();
                            g_OrdersClass_fromServer.order_list[i].items[k].ean[0] = "emptyEan";
                        }else{
                            g_OrdersClass_fromServer.order_list[i].items[k].ean = new String[g_OrdersClass_fromServer.order_list[i].items[k].ean_qty];
                            for (int l = 0; l < g_OrdersClass_fromServer.order_list[i].items[k].ean_qty; l++) {
                                g_OrdersClass_fromServer.order_list[i].items[k].ean[l] = new String();
                                g_OrdersClass_fromServer.order_list[i].items[k].ean[l] = ean_list.getString(l);
                            }
                        }

                        g_OrdersClass_fromServer.order_list[i].items[k].qty = item.getInt("qty");
                        g_OrdersClass_fromServer.order_list[i].items[k].desc = item.getString("desc");
                        /* Don't fetch location if GDT server */
                        if (g_iServerSelected != SERVER_GD_LIVE)
                            g_OrdersClass_fromServer.order_list[i].items[k].location = item.getString("location");
                        g_OrdersClass_fromServer.order_list[i].items[k].location =
                                g_OrdersClass_fromServer.order_list[i].items[k].location.toUpperCase();
                        if (g_OrdersClass_fromServer.order_list[i].items[k].location.length() < 4) {
                            g_OrdersClass_fromServer.order_list[i].items[k].location = "Z-01-E-M-T";
                        }
                    }
                    g_OrdersClass_fromServer.order_list[i].item_index = 0;
                }
            }

            /**************** Parse Hold Order List *****************/
            if (STS_OK == status1){
                JSONArray HoldList_array = new JSONArray();
                HoldList_array = obj1.getJSONArray("holdList");
                for (int j = 0; j < hold_order_count; j++) {
                    JSONObject hold_list_obj = new JSONObject();
                    hold_list_obj = HoldList_array.getJSONObject(j);
                    long PickId = hold_list_obj.getLong("PickId");
                    String Orderid = hold_list_obj.getString("Orderid");
                    String SubType = hold_list_obj.getString("SubType");
                    for (int i = 0; i < g_OrdersClass_fromServer.order_list_count; i++){
                        if ((Orderid == g_OrdersClass_fromServer.order_list[i].orderid) &&
                                (PickId == g_OrdersClass_fromServer.order_list[i].pickid) &&
                                (SubType.equals(g_OrdersClass_fromServer.order_list[i].subtype))){
                            /* Match Found. Mark the order as Hold Download pending */
                            g_OrdersClass_fromServer.order_list[i].order_status = ORDER_STS_HOLD_DOWNLOAD_PENDING;
                            break;
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            final_status = STS_PARSE_ERROR;
        }
        return final_status;
    }

    /************************ Download Hold Details ****************************/
    static class ataskDownloadHoldDetails extends AsyncTask<String, Integer, Double> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            g_strJsonGetHoldOrder = "";
            int order_index = g_OrdersClass_fromServer.order_index;
            g_progressDialog.setTitle("**** HOLD DOWNLOAD ****");
            g_progressDialog.setMessage("Order ID: " +g_OrdersClass_fromServer.order_list[order_index].orderid+"\n\nPlease Wait...");
            g_progressDialog.show();
        }

        @Override
        protected Double doInBackground(String... params) {
            /* Download from Server */
            InputStream inputStream = null;
            int order_index = g_OrdersClass_fromServer.order_index;
            try {
                /* Process the Download for identified order_index */
                if (ORDER_STS_HOLD_DOWNLOADING == g_OrdersClass_fromServer.order_list[order_index].order_status){
                    URL url = new URL(get_GetHoldDetailsURL
                            (g_strUserName, g_OrdersClass_fromServer.order_list[order_index].pickid));
                    Log.e(TAG,""+url);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod(get_RequestMethod());
                    urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                    urlConnection.setReadTimeout(SERVER_READ_TOUT);
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    /* Read Inputstream from Server */
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    inputStream.close();
                    g_strJsonGetHoldOrder = sb.toString();
                    sb.delete(0, sb.length());
                }else{
                    /* Index Error.  Request for Order Index in Global */
                    g_serverReqErr = SERVER_REQ_STS_INDEX_ERR;
                }
            } catch (IOException e) {
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            if ((g_iServerSelected == SERVER_GD_TEST) || (g_iServerSelected == SERVER_GD_LIVE)) {
                g_strJsonGetHoldOrder = GDT_test_Json_strings.STR_JSON_RES_GET_HOLD_ORDER;
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
            }

            Log.e(TAG,"g_strJsonGetHoldOrder: "+g_strJsonGetHoldOrder);

            if (g_serverReqErr == SERVER_REQ_STS_CONNECTION_ERR){
                /* Connection Failure. Will be Retried in Runnable */
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                g_progressDialog.setMessage("WIFI Connection Failure.\n Retrying....");
                for (int order_index = 0; order_index<g_OrdersClass_fromServer.order_list_count;order_index++){
                    if (ORDER_STS_HOLD_DOWNLOADING == g_OrdersClass_fromServer.order_list[order_index].order_status){
                        g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD_DOWNLOAD_PENDING;
                    }
                }
            }else if (g_serverReqErr == SERVER_REQ_STS_INDEX_ERR) {
                /* Connection Failure. Will be Retried in Runnable */
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                g_progressDialog.setMessage("Order Index Mismatch.\n Retrying....");
                for (int order_index = 0; order_index<g_OrdersClass_fromServer.order_list_count;order_index++){
                    if (ORDER_STS_HOLD_DOWNLOADING == g_OrdersClass_fromServer.order_list[order_index].order_status){
                        g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD_DOWNLOAD_PENDING;
                    }
                }
            } else {
                /* Parse JSON String */
                int status = parseGetHoldDetailsJsonResponse();
                int order_index = g_OrdersClass_fromServer.order_index;
                if (status == STS_OK) {
                    g_progressDialog.setMessage("Order ID: " +g_OrdersClass_fromServer.order_list[order_index].orderid +"\n\nDownload Success !!!");
                    g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD;
                }else if (status == STS_HOLD_DETAILS_ALREADY_POSTED) {
                    g_progressDialog.setMessage("Order ID: " +g_OrdersClass_fromServer.order_list[order_index].orderid +"\n\nAlready Posted !!!");
                    g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD_ERR_ALREADY_POSTED;
                }else {
                    String errorMsg = "";
                    if (status == STS_HOLD_DETAILS_INVALID_USERID) {
                        errorMsg = "Invalid User ID";
                    } else if (status == STS_HOLD_DETAILS_INVALID_PICKID) {
                        errorMsg = "Invalid Pick ID";
                    } else if (status == STS_HOLD_DETAILS_ERROR_IN_API) {
                        errorMsg = "Error in API";
                    } else if (status == STS_PARSE_ERROR) {
                        errorMsg = "Parse Error. Report to Technical Team.";
                    } else {
                        status = STS_UNKNOWN_ERROR;
                        errorMsg = "Unknown error. Retrying.";
                    }
                    g_progressDialog.setMessage("ERROR CODE: " + status + "\n\nMSG: " + errorMsg);
                    g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_ERROR;
                }
            }
        }
    }

    static int parseGetHoldDetailsJsonResponse() {
        int status = STS_OK;
        int order_index = g_OrdersClass_fromServer.order_index;
        try {
            JSONObject HoldDetailsobj = new JSONObject(g_strJsonGetHoldOrder);
            g_strJsonGetHoldOrder = "";
            status = HoldDetailsobj.getInt("Status");
            if (STS_OK == status) {
                /* Create Arrays for Hold Details */
                JSONArray HoldMast_Array = new JSONArray();
                JSONArray HoldLable_Array = new JSONArray();
                JSONArray HoldTran_Array = new JSONArray();
                JSONArray TranValidate_Array = new JSONArray();

                HoldMast_Array = HoldDetailsobj.getJSONArray("HoldMast");
                HoldLable_Array = HoldDetailsobj.getJSONArray("HoldLable");
                HoldTran_Array = HoldDetailsobj.getJSONArray("HoldTran");
                TranValidate_Array = HoldDetailsobj.getJSONArray("TranValidate");
                Long PickId = HoldDetailsobj.getLong("PickId");
                Long PauseTime = HoldDetailsobj.getLong("PauseTime");


                /* Find-out the Pick Id from order list before updating */
                if ((g_OrdersClass_fromServer.order_list[order_index].pickid == PickId) &&
                        (ORDER_STS_HOLD_DOWNLOADING == g_OrdersClass_fromServer.order_list[order_index].order_status)){
                    /* PickId matching. Update pick data in to "g_OrdersClass_fromServer" */
                    JSONObject HoldMast_obj = new JSONObject();
                    if (HoldMast_Array.length() > 0){
                        HoldMast_obj = HoldMast_Array.getJSONObject(0);
                    }else{
                        g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_NOT_STARTED;
                        return STS_OK;
                    }
                    g_OrdersClass_fromServer.order_list[order_index].start_time_stamp = HoldDetailsobj.getString("PickStartDate");
                    g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp = HoldMast_obj.getString("CrateEndDate");
                    g_OrdersClass_fromServer.order_list[order_index].pause_time_sec = PauseTime;

                    /* Copy picked qty details */
                    for (int hold_item_index = 0; hold_item_index < TranValidate_Array.length(); hold_item_index++) {
                        JSONObject TranValidate_obj = new JSONObject();
                        TranValidate_obj = TranValidate_Array.getJSONObject(hold_item_index);
                        String sku = TranValidate_obj.getString("SkuCode");
                        for (int item_index = 0; item_index < g_OrdersClass_fromServer.order_list[order_index].item_list_qty; item_index++) {
                            if (sku.equals(g_OrdersClass_fromServer.order_list[order_index].items[item_index].sku)) {
                                g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked = (int)TranValidate_obj.getDouble("PickQty");
                                g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_skipped =
                                        g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty -
                                                g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked;
                                break;
                            }
                        }
                    }

                    /* Frame post_label_list_Array */
                    for (int hold_lable_index = 0; hold_lable_index < HoldLable_Array.length(); hold_lable_index++) {
                        JSONObject HoldLable_obj = new JSONObject();
                        HoldLable_obj = HoldLable_Array.getJSONObject(hold_lable_index);

                        String FixedCrate = HoldLable_obj.getString("FixedCrate");
                        String CrateId = HoldLable_obj.getString("CrateId");

                        if (FixedCrate.equals("") || (FixedCrate.length() <= 0)) {
                            /* Label not locked with Crate */
                        } else {
                            /* Add a Post Label Array */
                            g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.add(new OrderClass().new scanned_label_class());
                            int label_list_index = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size() - 1;

                            /* Copy Crate and Label details */
                            g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(label_list_index).crate_label = FixedCrate;
                            g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(label_list_index).order_label = CrateId;

                            /* Find Crate Index for SKU from HoldTran_Array */
                            for (int HoldTran_index = 0; HoldTran_index < HoldTran_Array.length(); HoldTran_index++) {
                                JSONObject HoldTran_obj = new JSONObject();
                                HoldTran_obj = HoldTran_Array.getJSONObject(HoldTran_index);
                                if (CrateId.equals(HoldTran_obj.getString("CrateId"))) {
                                    String SkuCode = HoldTran_obj.getString("SkuCode");
                                    int PickQty = (int) HoldTran_obj.getDouble("PickQty");
                                    JSONObject Labelitems_lists_obj = new JSONObject();
                                    Labelitems_lists_obj.put("sku", SkuCode);
                                    Labelitems_lists_obj.put("pickqty", (int) PickQty);
                                    Labelitems_lists_obj.put("pickwt", 0);
                                    g_OrdersClass_fromServer.order_list[order_index].
                                            post_label_list_Array.get(label_list_index).crate_item_qty += PickQty;
                                    int Labelitems_lists_jarray_index = g_OrdersClass_fromServer.order_list[order_index].
                                            post_label_list_Array.get(label_list_index).Labelitems_lists_jarray.length();
                                    g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.
                                            get(label_list_index).Labelitems_lists_jarray.put(Labelitems_lists_jarray_index, Labelitems_lists_obj);
                                }
                            }
                        }
                    }
                }else{
                    status = STS_HOLD_DETAILS_INVALID_PICKID;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            status = STS_PARSE_ERROR;
        }
        return status;
    }

    /************************ Refresh SKU code ****************************/
    private class ataskRefreshSkuCode extends AsyncTask<String, Integer, Double> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            g_strJsonRefreshSku = "";
            g_progressDialog.setTitle("**** REFRESH SKU CODE ****");
            g_progressDialog.setMessage("Downloading. Please Wait...");
            g_progressDialog.show();
        }

        @Override
        protected Double doInBackground(String... params) {
            /* Download from Server */
            InputStream inputStream = null;
            try {
                URL url = new URL(get_RefreshSkuURL(g_strUserName));
                Log.e(TAG,""+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(get_RequestMethod());
                urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                urlConnection.setReadTimeout(SERVER_READ_TOUT);

                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                inputStream.close();
                g_strJsonRefreshSku = sb.toString();
                sb.delete(0, sb.length());
            } catch (Exception e) {
                Log.e(TAG, "Error downloading / converting result: " + e.toString());
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            if (g_iServerSelected == SERVER_GD_TEST) {
                /* Copy JSON stings */
                g_strJsonRefreshSku = GDT_test_Json_strings.STR_JSON_RES_GET_ORDERS_REFRESH_SKU;
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
            }

            if (g_serverReqErr != SERVER_REQ_STS_NO_ERR) {
                if (g_progressDialog != null) g_progressDialog.dismiss();
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                builder.setTitle("ERROR CODE: "+STS_CONNECTION_ERROR);
                builder.setMessage("Connection Failure. Try again");
                builder.setPositiveButton(getString(R.string.ok_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                /* Parse JSON String */
                int status = parseRefreshSkuJsonResponse();
                if (status == STS_OK) {
                    g_eAppSts = enumAppStatus.APP_STS_SCAN_SKU;
                    updateScreenViewOnAppStatusChange();
                } else {
                    if (g_progressDialog != null) g_progressDialog.dismiss();
                    int errorCode = status;
                    String errorMsg = "";
                    if (status == STS_GET_ORDERS_INVALID_USERID) {
                        errorMsg = "Invalid User Id.";
                    } else if (status == STS_GET_ORDERS_NO_ORDERS_ASSIGNED) {
                        errorMsg = "No Orders assigned";
                    } else if (status == STS_PARSE_ERROR) {
                        errorMsg = "Parse Error. Report to Technical Team.";
                    } else {
                        errorCode = STS_UNKNOWN_ERROR;
                        errorMsg = "Unknown error. Please try again.";
                    }
                    /* Show alert builder */
                    AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                    builder.setTitle("Error code: " + errorCode);
                    builder.setMessage(errorMsg);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        }
    }

    private int parseRefreshSkuJsonResponse() {
        int status = STS_OK;
        /* Decode JSON for get Order list */
        try {
            JSONObject obj = new JSONObject(g_strJsonRefreshSku);
            status = obj.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
            status = STS_PARSE_ERROR;
        }

        if (status == STS_OK) {
        /* Fetch Current OrderID, OrderIndex & Item Index */
            int order_index_current = g_OrdersClass_fromServer.order_index;
            String order_id_current = g_OrdersClass_fromServer.order_list[order_index_current].orderid;
            int item_index_current = g_OrdersClass_fromServer.order_list[order_index_current].item_index;
        /* Retrieve currently downloaded JSON */
            try {
                JSONObject obj = new JSONObject(g_strJsonRefreshSku);
                JSONArray assign_list = new JSONArray();
                assign_list = obj.getJSONArray("assign_list");
                JSONObject order = new JSONObject();
                int orderlength = assign_list.length();
                //Log.e(TAG, "New orderlength: "+orderlength);
                for (int i = 0; i < orderlength; i++) {
                    order = assign_list.getJSONObject(i);
                    String orderid = order.getString("orderid");
                    //Log.e(TAG, "New Order ID["+i+"]: " + orderid);
                    //check for the particular orderid and sku and then update the ean values for that order
                    if (orderid == order_id_current) {
                        //Log.e(TAG, "Found Order ID in Index: " + i);
                        JSONArray items_list = new JSONArray();
                        items_list = order.getJSONArray("items");
                        JSONObject item = new JSONObject();
                        int itemlength = items_list.length();
                        //Log.e(TAG, "New itemlength: " + itemlength);
                        for (int k = 0; k < itemlength; k++) {
                            item = items_list.getJSONObject(k);
                            String sku = new String();
                            sku = item.getString("sku");
                            //Log.e(TAG, "New sku["+k+"]: " + sku);
                            //ckeck for the sku id
                            if (sku.equals(g_OrdersClass_fromServer.order_list[order_index_current].items[item_index_current].sku)) {
                                //Log.e(TAG, "Found Sku in Index: " + k);
                                JSONArray ean_list = new JSONArray();
                                ean_list = item.getJSONArray("ean");
                                int ean_qty = ean_list.length();
                                g_OrdersClass_fromServer.order_list[order_index_current].items[item_index_current].ean_qty = ean_qty;
                                //Log.e(TAG, "New ean_qty: " + ean_qty);
                                g_OrdersClass_fromServer.order_list[order_index_current].items[item_index_current].ean = new String[ean_qty];
                                for (int l = 0; l < ean_qty; l++) {
                                    g_OrdersClass_fromServer.order_list[order_index_current].items[item_index_current].ean[l] = new String();
                                    g_OrdersClass_fromServer.order_list[order_index_current].items[item_index_current].ean[l] = ean_list.getString(l);
                                }
                                break; /* Break SKU Search */
                            } else {
                                //Log.e(TAG, "Skipped sku in Index: " + k);
                            }
                        }
                        break; /* Break Order Search loop */
                    } else {
                        //Log.e(TAG, "Skipped Order ID in Index: " + i);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                status = STS_PARSE_ERROR;
            }
        }else{
            //Status Error
        }
        return status;
    }

    /************************ Check RFID ****************************/
    private class ataskCheckRfid extends AsyncTask<String, Integer, Double> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Double doInBackground(String... params) {
            if (g_iServerSelected == SERVER_GD_TEST) {
                g_strJsonRfidstatus = GDT_test_Json_strings.STR_JSON_RES_GET_RFID_STATUS;
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                return null;
            }

            /* Download from Server */
            InputStream inputStream = null;
            try {
                URL url = new URL(get_RfidStatusURL(g_strCrateLabel));
                Log.e(TAG,""+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(get_RequestMethod());
                urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                urlConnection.setReadTimeout(SERVER_READ_TOUT);

                // read the response
                //Log.e(TAG,"Response Code: " + urlConnection.getResponseCode());
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                inputStream.close();
                g_strJsonRfidstatus = sb.toString();
                sb.delete(0, sb.length());
            } catch (Exception e) {
                Log.e(TAG, "Error downloading / converting result: " + e.toString());
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            //Toast.makeText(getApplicationContext(), "" + noOfOrders, Toast.LENGTH_SHORT).show();
            Log.e(TAG,"g_strJsonRfidstatus: "+g_strJsonRfidstatus);
            if (g_serverReqErr != SERVER_REQ_STS_NO_ERR) {
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                /* Reset g_validCrateRfidScanned flag. This will be checked for State change in BT stream Handling */
                g_validCrateRfidScanned = false;
                /* Set Error progress bar */
                g_progressDialog.setMessage("Error: Wifi problem. Scan crate again :(");

                g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE_WAITING_SYS_READY;
                /* Send NOK to scanner and wait for system ready */
                g_MediaPlayClass.playWrongScan(getApplicationContext());
                /* Crate Scan failed due to other errors */
                btSend_BarcodeInvalid_ScannerInactivate();

                if ((g_bEnableFakeScan)&&(g_progressDialog != null)) g_progressDialog.dismiss();
            } else {
                int status = parseRfidStatusJson();
                if (g_bEnableFakeScan || isDemoUser) {
                    g_progressDialog.setMessage("Crate Scan: Success !!!");
                    g_MediaPlayClass.playRightScan(getApplicationContext());
                    txtCrateRfid.setText("" + g_strCrateLabel);
                    txtOrderScanTitle.setText(SCAN_ORDER_APPEND + g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].orderid);
                    txtSelectedOrder.setText(NO_ORDER_SCANNED);
                    add_new_crate();
                    g_eAppSts = enumAppStatus.APP_STS_SCAN_ORDER;
                    updateScreenViewOnAppStatusChange();
                } else if (status == STS_OK) {
                    txtCrateRfid.setText("" + g_strCrateLabel);
                    g_progressDialog.setMessage("Crate Scan: Success !!!");
                    g_MediaPlayClass.playRightScan(getApplicationContext());

                    g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE_WAITING_SYS_READY;
                    /* Send NOK to scanner and wait for system ready */
                    btSend_BarcodeInvalid_ScannerInactivate();
                } else {
                    /* Reset g_validCrateRfidScanned flag. This will be checked for State change in BT stream Handling */
                    g_validCrateRfidScanned = false;
                    if (status == STS_GET_RFID_STATUS_INVALID_RFID) {
                        g_progressDialog.setMessage("Error: Invalid RFID");
                    } else if (status == STS_GET_RFID_STATUS_ALREADY_ASSIGNED_CRATE) {
                        g_progressDialog.setMessage("Error: Already Assigned Crate");
                    } else if (status == STS_GET_RFID_STATUS_NO_CRATE_FOUND) {
                        g_progressDialog.setMessage("Error: No Crate Found");
                    } else if (status == STS_PARSE_ERROR) {
                        g_progressDialog.setMessage("Error: Parse Error. Scan again");
                    } else {
                        g_progressDialog.setMessage("Error: Please Scan again");
                    }
                    g_eAppSts = enumAppStatus.APP_STS_SCAN_CRATE_WAITING_SYS_READY;
                    /* Send NOK to scanner and wait for system ready */
                    g_MediaPlayClass.playWrongScan(getApplicationContext());
                    /* Crate Scan failed due to other errors */
                    btSend_BarcodeInvalid_ScannerInactivate();
                }
            }
        }
    }

    private int parseRfidStatusJson() {
        int status;
        status = STS_OK;

        /* Decode JSON for get Order list */
        try {
            JSONObject obj = new JSONObject(g_strJsonRfidstatus);
            status = obj.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
            status = STS_PARSE_ERROR;
        }
        g_strJsonRfidstatus = "";
        return status;
    }

    /************************ Get QOH ****************************/
    /* Class which will in the background get the QOH for an item */
    private class ataskGetQoh extends AsyncTask<String, Integer, Double> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtQtyOnHand.setText("-Q-");
        }

        @Override
        protected Double doInBackground(String... params) {
            if (g_iServerSelected == SERVER_GD_TEST) {
                g_strJsonQohStatus = GDT_test_Json_strings.STR_JSON_RES_GET_QTR_ON_HAND;
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                return null;
            }

            /* Download from Server */
            InputStream inputStream = null;
            try {
                URL url = new URL(get_QohURL(g_strSkuCode));
                Log.e(TAG,""+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(get_RequestMethod());
                urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                urlConnection.setReadTimeout(SERVER_READ_TOUT);

                // read the response
                //Log.e(TAG, "Response Code: " + urlConnection.getResponseCode());
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            }
            catch (IOException e) {
                e.printStackTrace();
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                inputStream.close();
                g_strJsonQohStatus = sb.toString();
                sb.delete(0, sb.length());
            }
            catch (Exception e)
            {
                Log.e(TAG, "Error downloading / converting result: " + e.toString());
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            if (g_serverReqErr != SERVER_REQ_STS_NO_ERR) {
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                txtQtyOnHand.setText("-CE-");
            } else {
                Log.e(TAG,"g_strJsonQohStatus: "+g_strJsonQohStatus);
                int status = parseQohStatusJson();
                int order_index = g_OrdersClass_fromServer.order_index;
                int item_index = g_OrdersClass_fromServer.order_list[order_index].item_index;
                String sku = g_OrdersClass_fromServer.order_list[order_index].items[item_index].sku;
               /* Update QOH value */
                if (g_strSkuCode == sku) {
                    g_OrdersClass_fromServer.order_list[order_index].items[item_index].qoh = g_iQohForSku;
                    txtQtyOnHand.setText("" + g_iQohForSku);
                }else {
                    txtQtyOnHand.setText("-PE-");
                }

                /* Status doesn't have any functionality now */
                if (status == STS_OK) {
                }
                else {
                    /* Error in QOH retrieving */
                }
            }
        }
    }

    private int parseQohStatusJson() {
        int status;
        status = STS_OK;

        /* Decode JSON for get Order list */
        try {
            JSONObject obj = new JSONObject(g_strJsonQohStatus);
            status = obj.getInt("status");
            if (status == STS_OK) {
                g_iQohForSku = obj.getInt("qoh");
            }else {
                g_iQohForSku = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            status = STS_PARSE_ERROR;
        }
        g_strJsonQohStatus = "";
        return status;
    }

    /************************ Post Closed Orders ****************************/
    static class ataskPostOrders extends AsyncTask<String, Integer, Double> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            int order_index = g_OrdersClass_fromServer.order_index;
            g_progressDialog.setTitle("**** ORDER POSTING ****");
            if (!(isDemoUser && !g_bStepOrderOrPick))
            {
                g_progressDialog.setMessage("Order ID: " + g_OrdersClass_fromServer.order_list[order_index].orderid + "\n\nPlease Wait...");
            }
            g_progressDialog.show();
        }

        @Override
        protected Double doInBackground(String... params) {
            /* Download from Server */
            int order_index = g_OrdersClass_fromServer.order_index;
            InputStream inputStream = null;
            try {
                /* Process the Download for identified order_index */
                if (g_OrdersClass_fromServer.order_list[order_index].order_status == ORDER_STS_CLOSED_POSTING) {
                    URL url = new URL(get_PostOrdersURL());
                    Log.e(TAG, "" + url);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setUseCaches(false);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Host", "android.schoolportal.gr");
                    urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                    urlConnection.setReadTimeout(SERVER_READ_TOUT);

                    //Create JSONObject here
                    int item_list_qty = g_OrdersClass_fromServer.order_list[order_index].item_list_qty;
                    int scanned_label_qty = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size();
                    JSONObject postJson = new JSONObject();
                    JSONArray orders_array = new JSONArray();
                    JSONObject order_obj = new JSONObject();
                    JSONArray items_array = new JSONArray();
                    JSONArray label_list_array = new JSONArray();
                    try {
                        if (g_OrdersClass_fromServer.order_list[order_index].stop_time_stamp.length() < 3) {
                            g_OrdersClass_fromServer.order_list[order_index].stop_time_stamp = getCurrentTimeStamp();
                        }
                        postJson.put("status", 0);
                        postJson.put("message", "OK");
                        /* Fill items */
                        for (int item_index = 0; item_index < item_list_qty; item_index++) {
                            JSONObject items_obj = new JSONObject();
                            items_obj.put("sku", g_OrdersClass_fromServer.order_list[order_index].items[item_index].sku);
                            items_obj.put("original_qty", g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty);
                            items_obj.put("picked_qty", g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked);
                            items_obj.put("pickwt", 0);
                            items_array.put(items_obj);
                        }
                        order_obj.put("items_list", items_array);
                        for (int scanned_label_index = 0; scanned_label_index < scanned_label_qty; scanned_label_index++) {
                            if (g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).Labelitems_lists_jarray.length() > 0) {
                                //Log.e(TAG,"label_index: "+label_index);
                                JSONObject label_list_obj = new JSONObject();
                                JSONArray Labelitems_lists_array = new JSONArray();

                                if (g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).order_label.length() > 0) {
                                    label_list_obj.put("label", g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).order_label);
                                } else {
                                    /* Frame the label */
                                    String label = g_OrdersClass_fromServer.order_list[order_index].label_list[0];
                                    label = label.substring(0, (label.length() - 1));
                                    label = label + "-Err-" + scanned_label_index;
                                    label_list_obj.put("label", label);
                                }
                                label_list_obj.put("rfid", g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).crate_label);
                                //Karthik: Below start and end time should be for each crate
                                label_list_obj.put("pick_start_time", g_OrdersClass_fromServer.order_list[order_index].start_time_stamp);
                                label_list_obj.put("pick_end_time", g_OrdersClass_fromServer.order_list[order_index].stop_time_stamp);

                                /* Add crate items list */
                                Labelitems_lists_array = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).Labelitems_lists_jarray;
                                label_list_obj.put("Labelitems_lists", Labelitems_lists_array);

                                label_list_array.put(label_list_obj);
                            } else {
                                Log.e(TAG, "No items. Skipped label: " + g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).order_label);
                            }
                        }

                        /* Add label list Array in to Order Object */
                        order_obj.put("label_list", label_list_array);
                        order_obj.put("pick_start_time", g_OrdersClass_fromServer.order_list[order_index].start_time_stamp);
                        order_obj.put("pick_end_time", g_OrdersClass_fromServer.order_list[order_index].stop_time_stamp);
                        order_obj.put("orderid", g_OrdersClass_fromServer.order_list[order_index].orderid);
                        order_obj.put("subtype", g_OrdersClass_fromServer.order_list[order_index].subtype);
                        order_obj.put("pickid", g_OrdersClass_fromServer.order_list[order_index].pickid);
                        order_obj.put("pause_time", g_OrdersClass_fromServer.order_list[order_index].pause_time_sec);
                        order_obj.put("pickedby", g_strUserName);

                        orders_array.put(order_obj);

                        postJson.putOpt("post_orders", orders_array);

                        /* TODO: First store the Data in Local memory. Implement it later */

                        Log.e(TAG, "postJson.toString(): " + postJson.toString());
                        // Send POST output.
                        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                        out.write(postJson.toString());
                        out.close();
                    } catch (Exception e) {
                        Log.e(TAG, "Post order Exception code: " + e);
                    }

                    /* Send data to URL */
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    // Read the response and append as string
                    //Log.e(TAG,"Response Code: " + urlConnection.getResponseCode());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    inputStream.close();
                    g_strJsonPostorders = sb.toString();
                    sb.delete(0, sb.length());

                }else{
                    /* Indexing Error */
                    g_serverReqErr = SERVER_REQ_STS_INDEX_ERR;
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (g_iServerSelected != SERVER_GD_LIVE) {
                    g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
                }
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            //Toast.makeText(getApplicationContext(), "" + noOfOrders, Toast.LENGTH_SHORT).show();
            if (g_iServerSelected == SERVER_GD_TEST) {
                g_strJsonPostorders = GDT_test_Json_strings.STR_JSON_RES_POST_ORDERS;
                //Format: {"response":[{"status":0,"message":"OK","orderid":628357,"subtype":"CF-FV","pickid":3456}]}
                try {
                    String order_id = g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].orderid;
                    JSONObject obj = new JSONObject(g_strJsonPostorders);
                    obj.getJSONArray("response").getJSONObject(0).remove("orderid");
                    obj.getJSONArray("response").getJSONObject(0).put("orderid",order_id);
                    g_strJsonPostorders = obj.toString();
                }catch(JSONException e){
                    /* Do nothing */
                }
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
            }

            Log.e(TAG,"g_strJsonPostorders: " + g_strJsonPostorders);

            /* Check for server req errors */
            if (g_serverReqErr == SERVER_REQ_STS_CONNECTION_ERR){
                /* Connection Failure. Will be Retried in Runnable */
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                g_progressDialog.setMessage("WIFI Connection Failure.\n Retrying....");
                for (int order_index = 0; order_index<g_OrdersClass_fromServer.order_list_count;order_index++){
                    if (ORDER_STS_CLOSED_POSTING == g_OrdersClass_fromServer.order_list[order_index].order_status){
                        g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_CLOSED_POST_PENDING;
                    }
                }
            }else if (g_serverReqErr == SERVER_REQ_STS_INDEX_ERR) {
                /* Connection Failure. Will be Retried in Runnable */
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                g_progressDialog.setMessage("Order Index Mismatch.\n Retrying....");
                for (int order_index = 0; order_index<g_OrdersClass_fromServer.order_list_count;order_index++){
                    if (ORDER_STS_CLOSED_POSTING == g_OrdersClass_fromServer.order_list[order_index].order_status){
                        g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_CLOSED_POST_PENDING;
                    }
                }
            }else {
                int order_index = g_OrdersClass_fromServer.order_index;
                int status = parsePostOrdersJson();
                if (status == STS_OK) {
                    g_progressDialog.setMessage("Order ID: " + g_OrdersClass_fromServer.order_list[order_index].orderid + "\n\nSuccessfully Closed !!!");
                }else {
                    String errorMsg = "";
                    g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_CLOSED_POST_PENDING;
                    if (status == STS_POST_ORDERS_INVALID_SKUCODE) {
                        errorMsg = "Invalid SKU Code";
                    } else if (status == STS_POST_ORDERS_INVALID_JSON_FORMAT) {
                        errorMsg = "Invalid JSON Format";
                    } else if (status == STS_POST_ORDERS_EMPTY_ORDERS) {
                        errorMsg = "Empty Orders";
                    } else if (status == STS_POST_ORDERS_INVALID_FORMAT) {
                        errorMsg = "Invalid Format";
                    } else if (status == STS_PARSE_ERROR) {
                        errorMsg = "Parse Error. Report to Technical Team.";
                    } else {
                        status = STS_UNKNOWN_ERROR;
                        errorMsg = "Unknown error. Please try again.";
                    }
                    //Log.e(TAG,"Order posting failed: "+g_OrdersClass_fromServer.order_index);
                    g_progressDialog.setMessage("ERROR CODE: " + status + "\nMSG: " + errorMsg);
                }
            }
        }
    }

    static int parsePostOrdersJson() {
        int status;
        status = STS_OK;

        /* Decode JSON for get Order list */
        try {
            //Format: {"response":[{"status":0,"message":"OK","orderid":628357,"subtype":"CF-FV","pickid":3456}]}
            //If unsuccessful, Format:
            JSONObject obj = new JSONObject(g_strJsonPostorders);
            JSONArray status_array = new JSONArray();
            status_array = obj.getJSONArray("response");
            JSONObject response_obj = new JSONObject();
            response_obj = status_array.getJSONObject(0);
            status = response_obj.getInt("status");
            if (status == STS_OK){
                /* Fetch Order ID */
                String order_id = response_obj.getString("orderid");
                /* Update Order status as Closed for Received Order ID */
                for (int order_index = 0; order_index < g_OrdersClass_fromServer.order_list_count; order_index++) {
                    if (( order_id.equals(g_OrdersClass_fromServer.order_list[order_index].orderid)) &&
                            (ORDER_STS_CLOSED_POSTING == g_OrdersClass_fromServer.order_list[order_index].order_status)) {
                        /* Order ID matching and Status Matching. Update as Closed */
                        g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_CLOSED;
                        break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            status = STS_PARSE_ERROR;
        }
        g_strJsonPostorders = "";
        return status;
    }

    /************************ Set Hold Order ****************************/
    /* Class which will in the background download the order list */
    static class ataskSetHoldOrder extends AsyncTask<String, Integer, Double> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            g_strJsonSetHoldOrderRes="";
            int order_index = g_OrdersClass_fromServer.order_index;
            g_progressDialog.setTitle("**** SAVING HOLD ORDER ****");
            g_progressDialog.setMessage("Order ID: " + g_OrdersClass_fromServer.order_list[order_index].orderid + "\n\nPlease Wait...");
            g_progressDialog.show();
        }

        @Override
        protected Double doInBackground(String... params) {
            /* Download from Server */
            InputStream inputStream = null;
            int order_index = g_OrdersClass_fromServer.order_index;
            try {
                if (ORDER_STS_HOLD_POSTING == g_OrdersClass_fromServer.order_list[order_index].order_status){
                    //Frame JSONObject here
                    int item_list_qty = g_OrdersClass_fromServer.order_list[order_index].item_list_qty;
                    int scanned_label_qty = g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.size();
                    DecimalFormat df = new DecimalFormat("#.0");
                    DecimalFormat df_zero = new DecimalFormat("0.0");

                    JSONObject holdJson_obj = new JSONObject();
                    JSONArray HoldMast_array = new JSONArray();
                    JSONArray HoldLable_array = new JSONArray();
                    JSONArray HoldTran_array = new JSONArray();
                    JSONArray TranValidate_array = new JSONArray();
                    try {
                        /* Fill stop time if not filled yet */
                        if (g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp.length() < 3) {
                            g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp = getCurrentTimeStamp();
                        }
                        holdJson_obj.put("PickId", g_OrdersClass_fromServer.order_list[order_index].pickid);
                        holdJson_obj.put("UserId", g_strUserName);
                        holdJson_obj.put("PickStartDate", g_OrdersClass_fromServer.order_list[order_index].start_time_stamp);
                        holdJson_obj.put("PickEndDate", g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp);
                        holdJson_obj.put("PauseTime", g_OrdersClass_fromServer.order_list[order_index].pause_time_sec);
                        holdJson_obj.put("Message", "OK");
                        holdJson_obj.put("Status", 0);

                        /* Frame HoldMast, HoldLable and HoldTran Array using scanned_label_qty */
                        for (int scanned_label_index = 0; scanned_label_index < scanned_label_qty; scanned_label_index++) {
                            /* Fetch Labelitems_lists_qty */
                            int Labelitems_lists_qty =
                                    g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).Labelitems_lists_jarray.length();

                            if (Labelitems_lists_qty > 0) {
                                JSONObject HoldMast_obj = new JSONObject();
                                JSONObject HoldLable_obj = new JSONObject();

                                /* Frame HoldMast_obj */
                                HoldMast_obj.put("PickId", g_OrdersClass_fromServer.order_list[order_index].pickid);
                                HoldMast_obj.put("CrateId", g_OrdersClass_fromServer.order_list[order_index]
                                        .post_label_list_Array.get(scanned_label_index).order_label);
                                HoldMast_obj.put("CrateStartDate", g_OrdersClass_fromServer.order_list[order_index].start_time_stamp);
                                HoldMast_obj.put("CrateEndDate", g_OrdersClass_fromServer.order_list[order_index].pause_time_stamp);
                                HoldMast_obj.put("CrateStatus", "N");
                                HoldMast_obj.put("UserId", g_strUserName);

                                /* Frame HoldLable_obj */
                                HoldLable_obj.put("PickId", g_OrdersClass_fromServer.order_list[order_index].pickid);
                                HoldLable_obj.put("CrateId", g_OrdersClass_fromServer.order_list[order_index]
                                        .post_label_list_Array.get(scanned_label_index).order_label);
                                HoldLable_obj.put("FixedCrate", g_OrdersClass_fromServer.order_list[order_index]
                                        .post_label_list_Array.get(scanned_label_index).crate_label);
                                HoldLable_obj.put("UserId", g_strUserName);

                                /****** Add "HoldMast_obj" and "HoldLable_obj" in to JSON Array ******/
                                int HoldMast_array_index = HoldMast_array.length();
                                int HoldLable_array_index = HoldLable_array.length();
                                HoldMast_array.put(HoldMast_array_index, HoldMast_obj);
                                HoldLable_array.put(HoldLable_array_index, HoldLable_obj);

                                /* Frame HoldTran_obj */
                                for (int Labelitems_lists_index = 0; Labelitems_lists_index < Labelitems_lists_qty; Labelitems_lists_index++) {
                                    JSONObject HoldTran_obj = new JSONObject();
                                    JSONObject Labelitems_lists_obj = new JSONObject();
                                    Labelitems_lists_obj =
                                            g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array
                                                    .get(scanned_label_index).Labelitems_lists_jarray.getJSONObject(Labelitems_lists_index);
                                    /* Frame HoldTran_obj */
                                    HoldTran_obj.put("PickId", g_OrdersClass_fromServer.order_list[order_index].pickid);
                                    HoldTran_obj.put("CrateId", g_OrdersClass_fromServer.order_list[order_index]
                                            .post_label_list_Array.get(scanned_label_index).order_label);
                                    HoldTran_obj.put("SkuCode", Labelitems_lists_obj.getString("sku"));
                                    /* Fetch OrderQty from Order data */
                                    for (int item_index = 0; item_index < item_list_qty; item_index++) {
                                        if (Labelitems_lists_obj.getString("sku").equals(g_OrdersClass_fromServer.order_list[order_index].items[item_index].sku)) {
                                            HoldTran_obj.put("OrderQty", g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty);
                                            break;
                                        }
                                    }
                                    HoldTran_obj.put("PickQty", (double) (Labelitems_lists_obj.getInt("pickqty")));
                                    HoldTran_obj.put("ActPickQty", (double)(Labelitems_lists_obj.getInt("pickqty")));
                                    HoldTran_obj.put("UtilQty", 0);
                                    HoldTran_obj.put("PickItemStatus", "N");
                                    HoldTran_obj.put("UserId", g_strUserName);

                                    /***** Add "HoldTran_obj" in to "HoldTran_array" *****/
                                    int HoldTran_array_index = HoldTran_array.length();
                                    HoldTran_array.put(HoldTran_array_index, HoldTran_obj);
                                }

                            } else {
                                Log.e(TAG, "No items. Skipped label: " +
                                        g_OrdersClass_fromServer.order_list[order_index].post_label_list_Array.get(scanned_label_index).order_label);
                            }
                        }

                        /* Add HoldMast_array and HoldLable_array in to holdJson_obj */
                        holdJson_obj.put("HoldMast", HoldMast_array);
                        holdJson_obj.put("HoldLable", HoldLable_array);
                        holdJson_obj.put("HoldTran", HoldTran_array);

                        /* Frame "TranValidate" */
                        for (int item_index = 0; item_index < item_list_qty; item_index++) {
                            JSONObject TranValidate_obj = new JSONObject();
                            TranValidate_obj.put("PickId", g_OrdersClass_fromServer.order_list[order_index].pickid);
                            TranValidate_obj.put("SkuCode", g_OrdersClass_fromServer.order_list[order_index].items[item_index].sku);
                            TranValidate_obj.put("OrderQty", g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty);
                            if (0 == g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked) {
                                TranValidate_obj.put("PickQty", (double)(g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked));
                            } else {
                                TranValidate_obj.put("PickQty", (double)(g_OrdersClass_fromServer.order_list[order_index].items[item_index].qty_picked));
                            }
                            TranValidate_obj.put("UserId", g_strUserName);
                            TranValidate_array.put(TranValidate_obj);
                        }
                        /* Add TranValidate_array in to holdJson_obj */
                        holdJson_obj.put("TranValidate", TranValidate_array);
                        Log.e(TAG, "holdJson_obj.toString(): " + holdJson_obj.toString());
                    } catch (Exception e) {
                        Log.e(TAG, "Set Hold Order JSON framing Error code: " + e);
                    }

                    URL url = new URL(get_SetHoldOrderURL());
                    Log.e(TAG, "" + url);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setUseCaches(false);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Host", "android.schoolportal.gr");
                    urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                    urlConnection.setReadTimeout(SERVER_READ_TOUT);

                    // Send POST output.
                    OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                    out.write(holdJson_obj.toString());
                    out.close();

                    Log.e(TAG, "Response Code: " + urlConnection.getResponseCode());
                    // Read the response
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    inputStream.close();
                    g_strJsonSetHoldOrderRes = sb.toString();
                    sb.delete(0, sb.length());
                }else {
                    g_serverReqErr = SERVER_REQ_STS_INDEX_ERR;
                }
            } catch (IOException e) {
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            if ((g_iServerSelected == SERVER_GD_TEST)||(g_iServerSelected == SERVER_GD_LIVE)) {
                g_strJsonSetHoldOrderRes = GDT_test_Json_strings.STR_JSON_RES_SET_HOLD_ORDER;
                //Format: {"status":0,"message":"OK","PickId":47}
                try {
                    JSONObject obj = new JSONObject(g_strJsonSetHoldOrderRes);
                    long pickid = g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].pickid;
                    obj.remove("PickId");
                    obj.put("PickId", pickid);
                    g_strJsonSetHoldOrderRes = obj.toString();
                }catch(JSONException e){
                    /* Do nothing */
                }
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
            }

            Log.e(TAG,"g_strJsonSetHoldOrderRes: " + g_strJsonSetHoldOrderRes);

            /* Check for server req errors */
            if (g_serverReqErr == SERVER_REQ_STS_CONNECTION_ERR){
                /* Connection Failure. Will be Retried in Runnable */
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                g_progressDialog.setMessage("WIFI Connection Failure.\n Retrying....");
                for (int order_index = 0; order_index<g_OrdersClass_fromServer.order_list_count;order_index++){
                    if (ORDER_STS_HOLD_POSTING == g_OrdersClass_fromServer.order_list[order_index].order_status){
                        g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD_POST_PENDING;
                    }
                }
            }else if (g_serverReqErr == SERVER_REQ_STS_INDEX_ERR) {
                /* Connection Failure. Will be Retried in Runnable */
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                g_progressDialog.setMessage("Order Index Mismatch.\n Retrying....");
                for (int order_index = 0; order_index < g_OrdersClass_fromServer.order_list_count; order_index++) {
                    if (ORDER_STS_HOLD_POSTING == g_OrdersClass_fromServer.order_list[order_index].order_status) {
                        g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD_POST_PENDING;
                    }
                }
            }else {
                int order_index = g_OrdersClass_fromServer.order_index;
                int status = parseSetHoldOrderJson();
                if (status == STS_OK) {
                    g_progressDialog.setMessage("Order ID: " + g_OrdersClass_fromServer.order_list[order_index].orderid + "\n\nSuccessfully Saved !!!");
                }else {
                    String errorMsg = "";
                    g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD_POST_PENDING;
                    if (status == STS_SET_HOLD_INVALID_JSON_FORMAT) {
                        errorMsg = "Invalid JSON Format";
                    } else if (status == STS_SET_HOLD_ERR_IN_API) {
                        errorMsg = "Error in API";
                    } else if (status == STS_SET_HOLD_ALREADY_ON_HOLD) {
                        errorMsg = "Already On Hold";
                        g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD;
                    } else if (status == STS_SET_HOLD_ALREADY_POSTED) {
                        errorMsg = "Order Closed by Another";
                        g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD_ERR_ALREADY_POSTED;
                    } else {
                        status = STS_UNKNOWN_ERROR;
                        errorMsg = "Unknown error. Please try again.";
                    }
                    //Log.e(TAG,"Order posting failed: "+g_OrdersClass_fromServer.order_index);
                    g_progressDialog.setMessage("ERROR CODE: " + status + "\nMSG: " + errorMsg);
                }
            }
        }
    }

    static int parseSetHoldOrderJson() {
        int status;
        status = STS_OK;
        /* Decode JSON for get Order list */
        try {
            //Format: {"status":0,"message":"OK","PickId":47}
            JSONObject obj = new JSONObject(g_strJsonSetHoldOrderRes);
            status = obj.getInt("Status");
            if (status == STS_OK){
                /* Fetch Order ID */
                long pick_id = obj.getLong("PickId");
                /* Update Order status as Closed for Received Order ID */
                for (int order_index = 0; order_index < g_OrdersClass_fromServer.order_list_count; order_index++) {
                    if ((pick_id == g_OrdersClass_fromServer.order_list[order_index].pickid) &&
                    (ORDER_STS_HOLD_POSTING == g_OrdersClass_fromServer.order_list[order_index].order_status)){
                        /* Pick ID matching. Update as Closed */
                        g_OrdersClass_fromServer.order_list[order_index].order_status = ORDER_STS_HOLD;
                        break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            status = STS_PARSE_ERROR;
        }
        g_strJsonSetHoldOrderRes = "";
        return status;
    }

    /************************ Set Hold Order ACK ****************************/
    /* Class which will in the background download the order list */
    private class ataskSetHoldOrderAck extends AsyncTask<String, Integer, Double> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            g_strJsonSetHoldOrderRes="";
            int order_index = g_OrdersClass_fromServer.order_index;
            g_progressDialog.setTitle("**** START HOLD ORDER ****");
            g_progressDialog.setMessage("Order ID: " + g_OrdersClass_fromServer.order_list[order_index].orderid + "\n\nPlease Wait...");
            g_progressDialog.show();
        }

        @Override
        protected Double doInBackground(String... params) {
            /* Download from Server */
            InputStream inputStream = null;
            int order_index = g_OrdersClass_fromServer.order_index;
            try {
                if (ORDER_STS_HOLD == g_OrdersClass_fromServer.order_list[order_index].order_status){
                    //Frame JSONObject here
                    JSONObject holdAckJson_obj = new JSONObject();
                    try {
                        holdAckJson_obj.put("PickId", g_OrdersClass_fromServer.order_list[order_index].pickid);
                        holdAckJson_obj.put("UserId", g_strUserName);
                    }catch(Exception e) {
                        Log.e(TAG, "Set Hold Order JSON framing Error code: " + e);
                    }

                    URL url = new URL(get_SetHoldOrderAckURL());
                    Log.e(TAG, "" + url);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setUseCaches(false);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Host", "android.schoolportal.gr");
                    urlConnection.setConnectTimeout(SERVER_CONNECT_TOUT);
                    urlConnection.setReadTimeout(SERVER_READ_TOUT);

                    // Send POST output.
                    OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                    out.write(holdAckJson_obj.toString());
                    out.close();

                    // Read the response
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    inputStream.close();
                    g_strJsonAckHoldOrderRes = sb.toString();
                    sb.delete(0, sb.length());
                }else {
                    g_serverReqErr = SERVER_REQ_STS_INDEX_ERR;
                }
            } catch (IOException e) {
                g_serverReqErr = SERVER_REQ_STS_CONNECTION_ERR;
            }
            return null;
        }

        protected void onPostExecute(Double result) {
            if ((g_iServerSelected == SERVER_GD_TEST)||(g_iServerSelected == SERVER_GD_LIVE)) {
                g_strJsonAckHoldOrderRes = GDT_test_Json_strings.STR_JSON_RES_SET_ACK_HOLD_ORDER;
                //Format: {"status":0,"message":"OK","PickId":47}
                try {
                    JSONObject obj = new JSONObject(g_strJsonAckHoldOrderRes);
                    long pickid = g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].pickid;
                    obj.remove("PickId");
                    obj.put("PickId", pickid);
                    g_strJsonAckHoldOrderRes = obj.toString();
                }catch(JSONException e){
                    /* Do nothing */
                }
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
            }

            Log.e(TAG,"g_strJsonAckHoldOrderRes: " + g_strJsonAckHoldOrderRes);

            /* Check for server req errors */
            if (g_serverReqErr == SERVER_REQ_STS_CONNECTION_ERR){
                if (g_progressDialog != null) g_progressDialog.dismiss();
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                builder.setTitle("ERROR CODE: "+SERVER_REQ_STS_CONNECTION_ERR);
                builder.setMessage("Connection Failure. Try again");
                builder.setPositiveButton(getString(R.string.ok_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }else if (g_serverReqErr == SERVER_REQ_STS_INDEX_ERR) {
                if (g_progressDialog != null) g_progressDialog.dismiss();
                g_serverReqErr = SERVER_REQ_STS_NO_ERR;
                AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                builder.setTitle("ERROR CODE: "+SERVER_REQ_STS_INDEX_ERR);
                builder.setMessage("Index Error. Try again");
                builder.setPositiveButton(getString(R.string.ok_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }else {
                int order_index = g_OrdersClass_fromServer.order_index;
                int status = parseSetHoldOrderAckJson();
                if (status == STS_OK) {
                    if (g_progressDialog != null) g_progressDialog.dismiss();
                    /* Update order status as In-progress to make sure the Hold details are written Back to Server */
                    g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].order_status = ORDER_STS_IN_PROGRESS;
                    String[] optionArray;
                    if (freeCrateAvailable()) {
                        optionArray = new String[2];
                        optionArray[0] = "NEW crate";
                        optionArray[1] = "USED crate";
                    }else{
                        optionArray = new String[1];
                        optionArray[0] = "NEW crate";
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                    builder.setTitle("Pick Order# " + g_OrdersClass_fromServer.order_list[order_index].orderid);
                    builder.setSingleChoiceItems(optionArray, -1, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int index) {
                            //Log.e(TAG, "Position: " + index);
                            g_iUserOption = index;
                        }
                    });
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        if (0 == g_iUserOption) {
                            restart_Order(g_OrdersClass_fromServer.order_index, true);
                            g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].order_status
                                    = ORDER_STS_IN_PROGRESS;
                        } else if (1 == g_iUserOption) {
                            restart_Order(g_OrdersClass_fromServer.order_index, false);
                            g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].order_status
                                    = ORDER_STS_RESUMED_USED_CRATE;
                        } else {
                        /* No option selected */
                            Log.e(TAG,"No option");
                        }
                        g_iUserOption = -1;
                        }
                    });

                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Log.e(TAG, "CANCEL");
                            g_iUserOption = -1;
                            if (update_orderSts()) {
                                g_eAppSts = enumAppStatus.APP_STS_ORDER_LIST;
                                updateScreenViewOnAppStatusChange();
                            }
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else {
                    String errorMsg = "";
                    if (status == STS_HOLD_ACK_INVALID_JSON_FORMAT) {
                        errorMsg = "Invalid JSON Format";
                    } else if (status == STS_HOLD_ACK_INVALID_USER_PICKID) {
                        errorMsg = "Invalid User ID (or) Pick ID";
                    } else if (status == STS_HOLD_ACK_API_ERR) {
                        errorMsg = "Error in API";
                    } else if (status == STS_HOLD_ACK_INDEX_ERROR) {
                        errorMsg = "Order Index Mismatch";
                    } else {
                        status = STS_UNKNOWN_ERROR;
                        errorMsg = "Unknown error. Please try again.";
                    }
                    if (g_progressDialog != null) g_progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(GDT_Main.this);
                    builder.setTitle("ERROR CODE: "+status);
                    builder.setMessage("MSG: "+errorMsg);
                    builder.setPositiveButton(getString(R.string.ok_positive_button), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        }
    }

    static int parseSetHoldOrderAckJson() {
        int status;
        status = STS_OK;
        /* Decode JSON for get Order list */
        try {
            //Format: {"PickId":"47","UserId":"RAV","Message":"OK","Status":0}
            JSONObject obj = new JSONObject(g_strJsonAckHoldOrderRes);
            status = obj.getInt("Status");
            if (status == STS_OK){
                /* Fetch Order ID */
                long pick_id = obj.getLong("PickId");
                if (pick_id != g_OrdersClass_fromServer.order_list[g_OrdersClass_fromServer.order_index].pickid){
                    status = STS_HOLD_ACK_INDEX_ERROR;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            status = STS_PARSE_ERROR;
        }
        g_strJsonAckHoldOrderRes = "";
        return status;
    }
    //Below code has to be removed once all the vehicles have been updated with new protocol
    public static void storeValues(String jsonRequest) throws JSONException, IOException {
        JSONObject jsonData = new JSONObject(jsonRequest);
        int jsonRequestLength = JSONRequest.length();

        if(jsonRequestLength == DIAG_JSON_LEN || jsonRequestLength == DIAG_JSON_LEN1 || jsonRequestLength == DIAG_JSON_LEN2 || jsonRequestLength == DIAG_JSON_LEN3){ // Incoming data is Diag
            //Log.e("DIAGNOSTICS PINGED", "TRUE");

            String a = jsonData.getString("a");
            String b = jsonData.getString("b");
            String c = jsonData.getString("c");
            String d = jsonData.getString("d");
            String e = jsonData.getString("e");


            try{
                /******************DIAG DATA**************************************************/
                /*************  "a"  ****************/
                values.vin = a;

                /*************  "b"  ****************/
                values.strFirmwareRev = b;

                /*************  "c"  ****************/
                values.iIgnCycles = Integer.parseInt(c.substring(4, 8), 16);
                values.iChargeCycles = Integer.parseInt(c.substring(8, 12), 16);
                values.iDistanceTravelled = Integer.parseInt(c.substring(12, 16), 16);
                values.iStartStopCyclesByPulse = Integer.parseInt(c.substring(16, 20), 16);
                values.iStartStopCyclesByAmps = Integer.parseInt(c.substring(20, 24), 16);
                values.iOLcountMaxWatts = Integer.parseInt(c.substring(24, 28), 16);
                values.iOLcountPeakWatts = Integer.parseInt(c.substring(28, 32), 16);
                values.iOLcountPeakAmps = Integer.parseInt(c.substring(32, 36), 16);
                values.iBrakeOnMovingCount = Integer.parseInt(c.substring(36, 40), 16);
                values.iBrakeOnStandingCount = Integer.parseInt(c.substring(40, 44), 16);

                values.fLoadAmpsAvg = Float.valueOf(c.substring(44, 49));
                values.fLoadAmpsAvgAbs = Float.valueOf(c.substring(49, 54));
                values.fLoadAmpsPeak = Float.valueOf(c.substring(54, 59));
                values.fChargeAmpsAvg = Float.valueOf(c.substring(59, 64));
                values.fChargeAmpsAvgAbs = Float.valueOf(c.substring(64, 69));
                values.fChargeAmpsPeak = Float.valueOf(c.substring(69, 74));
                values.fSpeedKmphAvg = Float.valueOf(c.substring(74, 79));
                values.fSpeedKmphAvgAbs = Float.valueOf(c.substring(79, 84));
                values.fSpeedKmphPeak = Float.valueOf(c.substring(84, 89));
                values.fBatVoltage = Float.valueOf(c.substring(89, 93));

                values.iMotorPowerAvg = Integer.parseInt(c.substring(93, 97), 16);
                values.iMotorPowerAvgAbs = Integer.parseInt(c.substring(97, 101), 16);
                values.iMotorPowerPeak = Integer.parseInt(c.substring(101, 106), 16);

                /****************************d*********************************************/
                values.iErrCode0 = Integer.parseInt(d.substring(4, 8), 16);
                values.iErrCode1     = Integer.parseInt(d.substring(8, 12), 16);

                /*************************  "e"  **************************************/
                values.iIgnCycles_O = Integer.parseInt(e.substring(4, 8), 16);
                values.iChargeCycles_O = Integer.parseInt(e.substring(8, 12), 16);
                values.iDistanceTravelled_O = Integer.parseInt(e.substring(12, 16), 16);
                values.iStartStopCyclesByPulse_O = Integer.parseInt(e.substring(16, 20), 16);
                values.iStartStopCyclesByAmps_O = Integer.parseInt(e.substring(20, 24), 16);
                values.iOLcountMaxWatts_O = Integer.parseInt(e.substring(24, 28), 16);
                values.iOLcountPeakWatts_O = Integer.parseInt(e.substring(28, 32), 16);
                values.iOLcountPeakAmps_O = Integer.parseInt(e.substring(32, 36), 16);
                values.iBrakeOnMovingCount_O = Integer.parseInt(e.substring(36, 40), 16);
                values.iBrakeOnStandingCount_O = Integer.parseInt(e.substring(40, 44), 16);

                values.fLoadAmpsAvg_O = Float.valueOf(e.substring(44, 49));
                values.fLoadAmpsAvgAbs_O = Float.valueOf(e.substring(49, 54));
                values.fLoadAmpsPeak_O = Float.valueOf(e.substring(54, 59));
                values.fChargeAmpsAvg_O = Float.valueOf(e.substring(59, 64));
                values.fChargeAmpsAvgAbs_O = Float.valueOf(e.substring(64, 69));
                values.fChargeAmpsPeak_O = Float.valueOf(e.substring(69, 74));

                values.fSpeedKmphAvg_O = Float.valueOf(e.substring(74, 79));
                values.fSpeedKmphAvgAbs_O = Float.valueOf(e.substring(79, 84));
                values.fSpeedKmphPeak_O = Float.valueOf(e.substring(84, 89));
                values.fBatVoltage_O = Float.valueOf(e.substring(89, 93));

                values.iMotorPowerAvg_O = Integer.parseInt(e.substring(93, 97), 16);
                values.iMotorPowerAvgAbs_O = Integer.parseInt(e.substring(97, 101), 16);
                values.iMotorPowerPeak_O = Integer.parseInt(e.substring(101, 106), 16);
            }
            catch (Exception ex){
                Log.e("JSON Fetch Error", ex.toString());
            }

        }
        else if(jsonRequestLength == HB_JSON_LEN || jsonRequestLength == HB_JSON_LEN1 || jsonRequestLength == HB_JSON_LEN3 || jsonRequestLength == HB_JSON_LEN2){ // Incoming data is Heartbea

            String a = jsonData.getString("a");
            String b = jsonData.getString("b");
            String c = jsonData.getString("c");
            String d = jsonData.getString("d");
            String e = jsonData.getString("e");
            String f = jsonData.getString("f");
            String g = jsonData.getString("g");
            String h = jsonData.getString("h");

            Log.e("HEARTBEAT PINGED", "TRUE");
            Log.e("a: ", a);
            Log.e("b: ", b);
            Log.e("c: ", c);
            Log.e("d: ", d);
            Log.e("e: ", e);
            Log.e("f: ", f);
            Log.e("g: ", g);
            Log.e("h: ", h);
            //Log.e("i: ", i);

            /******************HEARTBEAT DATA**************************************************/
            /*************  "a"  ****************/
            values.vin = a;

            /*************  "b"  ****************/
            values.u32OdoMeter = Integer.parseUnsignedInt(b.substring(4, 12), 16);
            values.u32UsageInTotalMin = Integer.parseUnsignedInt(b.substring(12, 20), 16);
            values.u32UsageInMaxWattsMin = Integer.parseUnsignedInt(b.substring(20, 28), 16);
            values.u32UsageInPeakWattsMin = Integer.parseUnsignedInt(b.substring(28, 36), 16);
            values.u32UsageInPeakAmpsMin = Integer.parseUnsignedInt(b.substring(36, 44), 16);
            values.u32ChargeCycles = Integer.parseUnsignedInt(b.substring(44, 52), 16);

//            Log.e("u32OdoMeter", String.valueOf(values.u32OdoMeter));
//            Log.e("u32UsageInTotalMin", String.valueOf(values.u32UsageInTotalMin));
//            Log.e("u32UsageInMaxWattsMin", String.valueOf(values.u32UsageInMaxWattsMin));
//            Log.e("u32UsageInPeakWattsMin", String.valueOf(values.u32UsageInPeakWattsMin));
//            Log.e("u32UsageInPeakAmpsMin", String.valueOf(values.u32UsageInPeakAmpsMin));
//            Log.e("u32ChargeCycles", String.valueOf(values.u32ChargeCycles));

            /*************  "c"  ****************/
            values.iMaxWattsTimer = Integer.parseInt(c.substring(4, 8), 16);
            values.iPeakWattsTimer = Integer.parseInt(c.substring(8, 12), 16);
            values.iPeakAmpsTimer = Integer.parseInt(c.substring(12, 16), 16);
            values.iMaxWatts = Integer.parseInt(c.substring(16, 20), 16);
            values.iPeakWatts = Integer.parseInt(c.substring(20, 24), 16);
            values.iPeakAmps = Integer.parseInt(c.substring(24, 28), 16);
            values.iOLresetTimer = Integer.parseInt(c.substring(28, 32), 16);
            values.iSpeedLimitKmph = Integer.parseInt(c.substring(32, 36), 16);
            values.iTripMeter = Integer.parseInt(c.substring(36, 40), 16);
            values.iBrakeConfig = Integer.parseInt(c.substring(40, 44), 16);
            values.iBrakeDelayMsec = Integer.parseInt(c.substring(44, 48), 16);

            values.fMaxVolt = Float.valueOf(c.substring(48, 53));
            values.fLowVoltCutoff = Float.valueOf(c.substring(53, 58));
            values.fDeadVoltCutoff = Float.valueOf(c.substring(58, 63));

            values.strRfid1 = c.substring(63, 71);
            values.strRfid2 = c.substring(71, 79);

//            Log.e("iMaxWattsTimer", String.valueOf(values.iMaxWattsTimer));
//            Log.e("iPeakWattsTimer", String.valueOf(values.iPeakWattsTimer));
//            Log.e("iPeakAmpsTimer", String.valueOf(values.iPeakAmpsTimer));
//            Log.e("iMaxWatts", String.valueOf(values.iMaxWatts));
//            Log.e("iPeakWatts", String.valueOf(values.iPeakWatts));
//            Log.e("iPeakAmps", String.valueOf(values.iPeakAmps));
//            Log.e("iOLresetTimer", String.valueOf(values.iOLresetTimer));
//            Log.e("iSpeedLimitKmph", String.valueOf(values.iSpeedLimitKmph));
//            Log.e("iTripMeter", String.valueOf(values.iTripMeter));
//            Log.e("iBrakeConfig", String.valueOf(values.iBrakeConfig));
//            Log.e("iBrakeDelayMsec", String.valueOf(values.iBrakeDelayMsec));
//
//            Log.e("fMaxVolt", String.valueOf(values.fMaxVolt));
//            Log.e("fLowVoltCutoff", String.valueOf(values.fLowVoltCutoff));
//            Log.e("fDeadVoltCutoff", String.valueOf(values.fDeadVoltCutoff));
//
//            Log.e("strRfid1", String.valueOf(values.strRfid1));
//            Log.e("strRfid2", String.valueOf(values.strRfid2));


            /*************  "d"  ****************/
            values.iAdcBatVolt = Integer.parseInt(d.substring(4, 8), 16);
            values.iAdc00ChargeAmps = Integer.parseInt(d.substring(8, 12), 16);
            values.iAdcChargeAmps = Integer.parseInt(d.substring(12, 16), 16);
            values.iAdc00LoadAmps1 = Integer.parseInt(d.substring(16, 20), 16);
            values.iAdcLoadAmps1 = Integer.parseInt(d.substring(20, 24), 16);
            values.iAdc00LoadAmps2 = Integer.parseInt(d.substring(24, 28), 16);
            values.iAdcLoadAmps2 = Integer.parseInt(d.substring(28, 32), 16);
            values.iAdc00McbAmps = Integer.parseInt(d.substring(32, 36), 16);
            values.iAdcMcbAmps = Integer.parseInt(d.substring(36, 40), 16);

//            Log.e("iAdcBatVolt", String.valueOf(values.iAdcBatVolt));
//            Log.e("iAdc00ChargeAmps", String.valueOf(values.iAdc00ChargeAmps));
//            Log.e("iAdcChargeAmps", String.valueOf(values.iAdcChargeAmps));
//            Log.e("iAdc00LoadAmps1", String.valueOf(values.iAdc00LoadAmps1));
//            Log.e("iAdcLoadAmps1", String.valueOf(values.iAdcLoadAmps1));
//            Log.e("iAdc00LoadAmps2", String.valueOf(values.iAdc00LoadAmps2));
//            Log.e("iAdcLoadAmps2", String.valueOf(values.iAdcLoadAmps2));
//            Log.e("iAdc00McbAmps", String.valueOf(values.iAdc00McbAmps));
//            Log.e("iAdcMcbAmps", String.valueOf(values.iAdcMcbAmps));


            /*************  "e"  ****************/
            values.iSts0 = Integer.parseInt(e.substring(4, 8), 16);
            // THIS STATEMENT IS USED FOR THE TO CHECK FOR THE
            int value = values.iSts0 & DOOR_OPEN;
            if (value == 1024)
            {
                g_bShowBatteryDoorOpenStatus = true;
            }
            else
            {
                g_bShowBatteryDoorOpenStatus = false;
            }
            values.iSts1 = Integer.parseInt(e.substring(8, 12), 16);
            values.iSts2 = Integer.parseInt(e.substring(12, 16), 16);
            values.iSts3 = Integer.parseInt(e.substring(16, 20), 16);
            values.iSocPercent = Integer.parseInt(e.substring(20, 24), 16);
            values.iOutputPower = Integer.parseInt(e.substring(24, 28), 16);
            values.iTrips = Integer.parseInt(e.substring(28, 32), 16);

//            Log.e("iSts0", String.valueOf(values.iSts0));

            values.fBatVoltage = Float.valueOf(e.substring(32, 37));
            values.fLoadAmps = Float.valueOf(e.substring(37, 42));
            values.fChargeAmps = Float.valueOf(e.substring(42, 47));
            values.fMcbAmps = Float.valueOf(e.substring(47, 52));
            values.fSpeedKmph = Float.valueOf(e.substring(52, 57));


            /*************  "f"  ****************/
            values.u32OdoMeter_O = Integer.parseUnsignedInt(f.substring(4, 12), 16);
            values.u32UsageInTotalMin_O = Integer.parseUnsignedInt(f.substring(12, 20), 16);
            values.u32UsageInMaxWattsMin_O = Integer.parseUnsignedInt(f.substring(20, 28), 16);
            values.u32UsageInPeakWattsMin_O = Integer.parseUnsignedInt(f.substring(28, 36), 16);
            values.u32UsageInPeakAmpsMin_O = Integer.parseUnsignedInt(f.substring(36, 44), 16);
            values.u32ChargeCycles_O = Integer.parseUnsignedInt(f.substring(44, 52), 16);

            /*************  "g"  ****************/
            values.iAdcBatVolt_O = Integer.parseInt(g.substring(4, 8), 16);
            values.iAdc00ChargeAmps_O = Integer.parseInt(g.substring(8, 12), 16);
            values.iAdcChargeAmps_O = Integer.parseInt(g.substring(12, 16), 16);
            values.iAdc00LoadAmps1_O = Integer.parseInt(g.substring(16, 20), 16);
            values.iAdcLoadAmps1_O = Integer.parseInt(g.substring(20, 24), 16);
            values.iAdc00LoadAmps2_O = Integer.parseInt(g.substring(24, 28), 16);
            values.iAdcLoadAmps2_O = Integer.parseInt(g.substring(28, 32), 16);
            values.iAdc00McbAmps_O = Integer.parseInt(g.substring(32, 36), 16);
            values.iAdcMcbAmps_O = Integer.parseInt(g.substring(36, 40), 16);

            /*************  "h"  ****************/
            values.iSts0_O = Integer.parseInt(h.substring(4, 8), 16);
            values.iSts1_O = Integer.parseInt(h.substring(8, 12), 16);
            values.iSts2_O = Integer.parseInt(h.substring(12, 16), 16);
            values.iSts3_O = Integer.parseInt(h.substring(16, 20), 16);
            values.iSocPercent_O = Integer.parseInt(h.substring(20, 24), 16);
            values.iOutputPower_O = Integer.parseInt(h.substring(24, 28), 16);
            values.iTrips_O = Integer.parseInt(h.substring(28, 32), 16);

            values.fBatVoltage_O = Float.valueOf(h.substring(32, 37));
            values.fLoadAmps_O = Float.valueOf(h.substring(37, 42));
            values.fChargeAmps_O = Float.valueOf(h.substring(42, 47));
            values.fMcbAmps_O = Float.valueOf(h.substring(47, 52));
            values.fSpeedKmph_O = Float.valueOf(h.substring(52, 57));

            updateStatus();

        }
        else{
            Log.e(TAG, "Data NOT stored in global variables");
        }
    }


    //Below code is to store values with latest protocol updated with PI_2023_09
    public static void parseAndStoreJson(String jsonRequest) throws JSONException, IOException {
        JSONObject jsonData = new JSONObject(jsonRequest);
        int jsonRequestLength = JSONRequest.length();

        if(jsonRequestLength == DIAG_JSON_LEN5 || jsonRequestLength == DIAG_JSON_LEN6){ // Incoming data is Diag
            //Log.e("DIAGNOSTICS PINGED", "TRUE");

            String a = jsonData.getString("a");
            String b = jsonData.getString("b");
            String c = jsonData.getString("c");
            String d = jsonData.getString("d");
            String e = jsonData.getString("e");


            try{
                /******************DIAG DATA**************************************************/
                /*************  "a"  ****************/
                values.vin = a;

                /*************  "b"  ****************/
                values.strFirmwareRev = b;

                /*************  "c"  ****************/
                values.iIgnCycles = Integer.parseInt(c.substring(4, 8), 16);
                values.iChargeCycles = Integer.parseInt(c.substring(8, 12), 16);
                values.iDistanceTravelled = Integer.parseInt(c.substring(12, 16), 16);
                values.iStartStopCyclesByPulse = Integer.parseInt(c.substring(16, 20), 16);
                values.iStartStopCyclesByAmps = Integer.parseInt(c.substring(20, 24), 16);
                values.iOLcountMaxWatts = Integer.parseInt(c.substring(24, 28), 16);
                values.iOLcountPeakWatts = Integer.parseInt(c.substring(28, 32), 16);
                values.iOLcountPeakAmps = Integer.parseInt(c.substring(32, 36), 16);
                //Added in PI_2023_09
                values.iOLcountControlTemp = Integer.parseInt(c.substring(36, 40), 16);
                values.iOLcountMotorTemp = Integer.parseInt(c.substring(40, 44), 16);
                values.iOLcountBatteryTemp = Integer.parseInt(c.substring(44, 48), 16);

                values.iBrakeOnMovingCount = Integer.parseInt(c.substring(48, 52), 16);
                values.iBrakeOnStandingCount = Integer.parseInt(c.substring(52, 56), 16);

                //Added in PI_2023_09
                values.iPushButtonCount = Integer.parseInt(c.substring(56, 60), 16);
                values.iEmergencyAppliedCount = Integer.parseInt(c.substring(60, 64), 16);

                values.fLoadAmpsAvg = Float.valueOf(c.substring(64, 69));
                values.fLoadAmpsAvgAbs = Float.valueOf(c.substring(69, 74));
                values.fLoadAmpsPeak = Float.valueOf(c.substring(74, 79));
                values.fChargeAmpsAvg = Float.valueOf(c.substring(79, 84));
                values.fChargeAmpsAvgAbs = Float.valueOf(c.substring(84, 89));
                values.fChargeAmpsPeak = Float.valueOf(c.substring(89, 94));
                values.fSpeedKmphAvg = Float.valueOf(c.substring(94, 99));
                values.fSpeedKmphAvgAbs = Float.valueOf(c.substring(99, 104));
                values.fSpeedKmphPeak = Float.valueOf(c.substring(104, 109));
                values.fBatVoltage = Float.valueOf(c.substring(109, 113));
                //Added in PI_2023_09
                values.fMotorTemp = Float.valueOf(c.substring(114, 119));
                values.fMotorTempPeak = Float.valueOf(c.substring(119, 124));
                values.fControllerTemp = Float.valueOf(c.substring(124, 129));
                values.fControllerTempPeak = Float.valueOf(c.substring(129, 134));
                values.fBatteryTemp = Float.valueOf(c.substring(134, 139));
                values.fBatteryTempPeak = Float.valueOf(c.substring(139, 144));

                values.iMotorPowerAvg = Integer.parseInt(c.substring(144, 148), 16);
                values.iMotorPowerAvgAbs = Integer.parseInt(c.substring(148, 152), 16);
                values.iMotorPowerPeak = Integer.parseInt(c.substring(152, 156), 16);

                /****************************d*********************************************/
                values.iErrCode0 = Integer.parseInt(d.substring(4, 8), 16);
                values.iErrCode1     = Integer.parseInt(d.substring(8, 12), 16);

                /*************************  "e"  **************************************/
                values.iIgnCycles_O = Integer.parseInt(e.substring(4, 8), 16);
                values.iChargeCycles_O = Integer.parseInt(e.substring(8, 12), 16);
                values.iDistanceTravelled_O = Integer.parseInt(e.substring(12, 16), 16);
                values.iStartStopCyclesByPulse_O = Integer.parseInt(e.substring(16, 20), 16);
                values.iStartStopCyclesByAmps_O = Integer.parseInt(e.substring(20, 24), 16);
                values.iOLcountMaxWatts_O = Integer.parseInt(e.substring(24, 28), 16);
                values.iOLcountPeakWatts_O = Integer.parseInt(e.substring(28, 32), 16);
                values.iOLcountPeakAmps_O = Integer.parseInt(e.substring(32, 36), 16);

                //Added in PI_2023_09
                values.iOLcountControlTemp_O = Integer.parseInt(e.substring(36, 40), 16);
                values.iOLcountMotorTemp_O = Integer.parseInt(e.substring(40, 44), 16);
                values.iOLcountBatteryTemp_O = Integer.parseInt(e.substring(44, 48), 16);

                values.iBrakeOnMovingCount_O = Integer.parseInt(e.substring(48, 52), 16);
                values.iBrakeOnStandingCount_O = Integer.parseInt(e.substring(52, 56), 16);

                //Added in PI_2023_09
                values.iPushButtonCount_O = Integer.parseInt(e.substring(56, 60), 16);
                values.iEmergencyAppliedCount_O = Integer.parseInt(e.substring(60, 64), 16);

                values.fLoadAmpsAvg_O = Float.valueOf(e.substring(64, 69));
                values.fLoadAmpsAvgAbs_O = Float.valueOf(e.substring(69, 74));
                values.fLoadAmpsPeak_O = Float.valueOf(e.substring(74, 79));
                values.fChargeAmpsAvg_O = Float.valueOf(e.substring(79, 84));
                values.fChargeAmpsAvgAbs_O = Float.valueOf(e.substring(84, 89));
                values.fChargeAmpsPeak_O = Float.valueOf(e.substring(89, 94));

                values.fSpeedKmphAvg_O = Float.valueOf(e.substring(94, 99));
                values.fSpeedKmphAvgAbs_O = Float.valueOf(e.substring(99, 104));
                values.fSpeedKmphPeak_O = Float.valueOf(e.substring(104, 109));
                values.fBatVoltage_O = Float.valueOf(e.substring(109, 113));

                //Added in PI_2023_09
                values.fMotorTemp_O = Float.valueOf(e.substring(114, 119));
                values.fMotorTempPeak_O = Float.valueOf(e.substring(119, 124));
                values.fControllerTemp_O = Float.valueOf(e.substring(124, 129));
                values.fControllerTempPeak_O = Float.valueOf(e.substring(129, 134));
                values.fBatteryTemp_O = Float.valueOf(e.substring(134, 139));
                values.fBatteryTempPeak_O = Float.valueOf(e.substring(139, 144));

                values.iMotorPowerAvg_O = Integer.parseInt(e.substring(144, 148), 16);
                values.iMotorPowerAvgAbs_O = Integer.parseInt(e.substring(148, 152), 16);
                values.iMotorPowerPeak_O = Integer.parseInt(e.substring(152, 156), 16);
            }
            catch (Exception ex){
                Log.e("JSON Fetch Error", ex.toString());
            }

        }
        else if(jsonRequestLength == HB_JSON_LEN2){ // Incoming data is Heartbeat

            String a = jsonData.getString("a");
            String b = jsonData.getString("b");
            String c = jsonData.getString("c");
            String d = jsonData.getString("d");
            String e = jsonData.getString("e");
            String f = jsonData.getString("f");
            String g = jsonData.getString("g");
            String h = jsonData.getString("h");

            Log.e("HEARTBEAT PINGED", "TRUE");
            Log.e("a: ", a);
            Log.e("b: ", b);
            Log.e("c: ", c);
            Log.e("d: ", d);
            Log.e("e: ", e);
            Log.e("f: ", f);
            Log.e("g: ", g);
            Log.e("h: ", h);
            //Log.e("i: ", i);

            /******************HEARTBEAT DATA**************************************************/
            /*************  "a"  ****************/
            values.vin = a;

            /*************  "b"  ****************/
            values.u32OdoMeter = Integer.parseInt(b.substring(4, 12), 16);
            values.u32UsageInTotalMin = Integer.parseInt(b.substring(12, 20), 16);
            values.u32UsageInMaxWattsMin = Integer.parseInt(b.substring(20, 28), 16);
            values.u32UsageInPeakWattsMin = Integer.parseInt(b.substring(28, 36), 16);
            values.u32UsageInPeakAmpsMin = Integer.parseInt(b.substring(36, 44), 16);
            values.u32ChargeCycles = Integer.parseInt(b.substring(44, 52), 16);
            values.u32ErrorCodes = Integer.parseInt(b.substring(52, 60), 16);

//            Log.e("u32OdoMeter", String.valueOf(values.u32OdoMeter));
//            Log.e("u32UsageInTotalMin", String.valueOf(values.u32UsageInTotalMin));
//            Log.e("u32UsageInMaxWattsMin", String.valueOf(values.u32UsageInMaxWattsMin));
//            Log.e("u32UsageInPeakWattsMin", String.valueOf(values.u32UsageInPeakWattsMin));
//            Log.e("u32UsageInPeakAmpsMin", String.valueOf(values.u32UsageInPeakAmpsMin));
//            Log.e("u32ChargeCycles", String.valueOf(values.u32ChargeCycles));

            /*************  "c"  ****************/
            values.iMaxWattsTimer = Integer.parseInt(c.substring(4, 8), 16);
            values.iPeakWattsTimer = Integer.parseInt(c.substring(8, 12), 16);
            values.iPeakAmpsTimer = Integer.parseInt(c.substring(12, 16), 16);
            values.iMaxWatts = Integer.parseInt(c.substring(16, 20), 16);
            values.iPeakWatts = Integer.parseInt(c.substring(20, 24), 16);
            values.iPeakAmps = Integer.parseInt(c.substring(24, 28), 16);
            values.iOLresetTimer = Integer.parseInt(c.substring(28, 32), 16);
            values.iSpeedLimitKmph = Integer.parseInt(c.substring(32, 36), 16);
            values.iTripMeter = Integer.parseInt(c.substring(36, 40), 16);
            values.iBrakeConfig = Integer.parseInt(c.substring(40, 44), 16);
            values.iBrakeDelayMsec = Integer.parseInt(c.substring(44, 48), 16);

            values.fMaxVolt = Float.valueOf(c.substring(48, 53));
            values.fLowVoltCutoff = Float.valueOf(c.substring(53, 58));
            values.fDeadVoltCutoff = Float.valueOf(c.substring(58, 63));

            values.strRfid1 = c.substring(63, 71);
            values.strRfid2 = c.substring(71, 79);

//            Log.e("iMaxWattsTimer", String.valueOf(values.iMaxWattsTimer));
//            Log.e("iPeakWattsTimer", String.valueOf(values.iPeakWattsTimer));
//            Log.e("iPeakAmpsTimer", String.valueOf(values.iPeakAmpsTimer));
//            Log.e("iMaxWatts", String.valueOf(values.iMaxWatts));
//            Log.e("iPeakWatts", String.valueOf(values.iPeakWatts));
//            Log.e("iPeakAmps", String.valueOf(values.iPeakAmps));
//            Log.e("iOLresetTimer", String.valueOf(values.iOLresetTimer));
//            Log.e("iSpeedLimitKmph", String.valueOf(values.iSpeedLimitKmph));
//            Log.e("iTripMeter", String.valueOf(values.iTripMeter));
//            Log.e("iBrakeConfig", String.valueOf(values.iBrakeConfig));
//            Log.e("iBrakeDelayMsec", String.valueOf(values.iBrakeDelayMsec));
//
//            Log.e("fMaxVolt", String.valueOf(values.fMaxVolt));
//            Log.e("fLowVoltCutoff", String.valueOf(values.fLowVoltCutoff));
//            Log.e("fDeadVoltCutoff", String.valueOf(values.fDeadVoltCutoff));
//
//            Log.e("strRfid1", String.valueOf(values.strRfid1));
//            Log.e("strRfid2", String.valueOf(values.strRfid2));


            /*************  "d"  ****************/
            values.iAdcBatVolt = Integer.parseInt(d.substring(4, 8), 16);
            values.iAdc00ChargeAmps = Integer.parseInt(d.substring(8, 12), 16);
            values.iAdcChargeAmps = Integer.parseInt(d.substring(12, 16), 16);
            values.iAdc00LoadAmps1 = Integer.parseInt(d.substring(16, 20), 16);
            values.iAdcLoadAmps1 = Integer.parseInt(d.substring(20, 24), 16);
            values.iAdc00LoadAmps2 = Integer.parseInt(d.substring(24, 28), 16);
            values.iAdcLoadAmps2 = Integer.parseInt(d.substring(28, 32), 16);
            values.iAdc00McbAmps = Integer.parseInt(d.substring(32, 36), 16);
            values.iAdcMcbAmps = Integer.parseInt(d.substring(36, 40), 16);

            //Added in PI_2023_09
            values.iAdcMotorTemp = Integer.parseInt(d.substring(40, 44), 16);
            values.iAdcControllerTemp = Integer.parseInt(d.substring(44, 48), 16);
            values.iAdcBatteryTemp = Integer.parseInt(d.substring(48, 52), 16);

//            Log.e("iAdcBatVolt", String.valueOf(values.iAdcBatVolt));
//            Log.e("iAdc00ChargeAmps", String.valueOf(values.iAdc00ChargeAmps));
//            Log.e("iAdcChargeAmps", String.valueOf(values.iAdcChargeAmps));
//            Log.e("iAdc00LoadAmps1", String.valueOf(values.iAdc00LoadAmps1));
//            Log.e("iAdcLoadAmps1", String.valueOf(values.iAdcLoadAmps1));
//            Log.e("iAdc00LoadAmps2", String.valueOf(values.iAdc00LoadAmps2));
//            Log.e("iAdcLoadAmps2", String.valueOf(values.iAdcLoadAmps2));
//            Log.e("iAdc00McbAmps", String.valueOf(values.iAdc00McbAmps));
//            Log.e("iAdcMcbAmps", String.valueOf(values.iAdcMcbAmps));


            /*************  "e"  ****************/
            values.iSts0 = Integer.parseInt(e.substring(4, 8), 16);
            // THIS STATEMENT IS USED FOR THE TO CHECK FOR THE
            int value = values.iSts0 & DOOR_OPEN;
            if (value == 1024)
            {
                g_bShowBatteryDoorOpenStatus = true;
            }
            else
            {
                g_bShowBatteryDoorOpenStatus = false;
            }
            values.iSts1 = Integer.parseInt(e.substring(8, 12), 16);
            values.iSts2 = Integer.parseInt(e.substring(12, 16), 16);
            values.iSts3 = Integer.parseInt(e.substring(16, 20), 16);
            values.iSocPercent = Integer.parseInt(e.substring(20, 24), 16);
            values.iOutputPower = Integer.parseInt(e.substring(24, 28), 16);
            values.iTrips = Integer.parseInt(e.substring(28, 32), 16);

//            Log.e("iSts0", String.valueOf(values.iSts0));

            values.fBatVoltage = Float.valueOf(e.substring(32, 37));
            values.fLoadAmps = Float.valueOf(e.substring(37, 42));
            values.fChargeAmps = Float.valueOf(e.substring(42, 47));
            values.fMcbAmps = Float.valueOf(e.substring(47, 52));
            values.fSpeedKmph = Float.valueOf(e.substring(52, 57));


            /*************  "f"  ****************/
            values.u32OdoMeter_O = Integer.parseInt(f.substring(4, 12), 16);
            values.u32UsageInTotalMin_O = Integer.parseInt(f.substring(12, 20), 16);
            values.u32UsageInMaxWattsMin_O = Integer.parseInt(f.substring(20, 28), 16);
            values.u32UsageInPeakWattsMin_O = Integer.parseInt(f.substring(28, 36), 16);
            values.u32UsageInPeakAmpsMin_O = Integer.parseInt(f.substring(36, 44), 16);
            values.u32ChargeCycles_O = Integer.parseInt(f.substring(44, 52), 16);
            //Added in PI_2023_09
            values.u32ErrorCodes_O = Integer.parseInt(f.substring(52, 60), 16);

            /*************  "g"  ****************/
            values.iAdcBatVolt_O = Integer.parseInt(g.substring(4, 8), 16);
            values.iAdc00ChargeAmps_O = Integer.parseInt(g.substring(8, 12), 16);
            values.iAdcChargeAmps_O = Integer.parseInt(g.substring(12, 16), 16);
            values.iAdc00LoadAmps1_O = Integer.parseInt(g.substring(16, 20), 16);
            values.iAdcLoadAmps1_O = Integer.parseInt(g.substring(20, 24), 16);
            values.iAdc00LoadAmps2_O = Integer.parseInt(g.substring(24, 28), 16);
            values.iAdcLoadAmps2_O = Integer.parseInt(g.substring(28, 32), 16);
            values.iAdc00McbAmps_O = Integer.parseInt(g.substring(32, 36), 16);
            values.iAdcMcbAmps_O = Integer.parseInt(g.substring(36, 40), 16);
            //Added in PI_2023_09
            values.iAdcMotorTemp_O = Integer.parseInt(g.substring(40, 44), 16);
            values.iAdcControllerTemp_O = Integer.parseInt(g.substring(44, 48), 16);
            values.iAdcBatteryTemp_O = Integer.parseInt(g.substring(48, 52), 16);

            /*************  "h"  ****************/
            values.iSts0_O = Integer.parseInt(h.substring(4, 8), 16);
            values.iSts1_O = Integer.parseInt(h.substring(8, 12), 16);
            values.iSts2_O = Integer.parseInt(h.substring(12, 16), 16);
            values.iSts3_O = Integer.parseInt(h.substring(16, 20), 16);
            values.iSocPercent_O = Integer.parseInt(h.substring(20, 24), 16);
            values.iOutputPower_O = Integer.parseInt(h.substring(24, 28), 16);
            values.iTrips_O = Integer.parseInt(h.substring(28, 32), 16);

            values.fBatVoltage_O = Float.valueOf(h.substring(32, 37));
            values.fLoadAmps_O = Float.valueOf(h.substring(37, 42));
            values.fChargeAmps_O = Float.valueOf(h.substring(42, 47));
            values.fMcbAmps_O = Float.valueOf(h.substring(47, 52));
            values.fSpeedKmph_O = Float.valueOf(h.substring(52, 57));

            updateStatus();

        }
        else{
            Log.e(TAG, "Data NOT stored in global variables");
        }
    }
    public static void updateStatus()
    {

        String vehicleStatus = ";";
        String vehicleDirection = "";

        // THESE PARAMETERS ARE CHOSEN ACCORDING TO THE PROTOCOL OF THE MOPTRO SNAIL.
        if((values.iSts0 & (1 << 5)) >= 1)
        {
            vehicleStatus = "MOVING";
            txtVehicleStatus.setText(vehicleStatus);
            txtVehicleStatus.setTextColor(Color.GREEN);
        }
        else
        {
            vehicleStatus = "IDLE";
            txtVehicleStatus.setText(vehicleStatus);
            txtVehicleStatus.setTextColor(Color.RED);
        }

        txtVoltageStatus.setText(""+ values.fBatVoltage_O+" Volts"); //SOC voltage
        if((values.iSts1 & (1 << 6)) == 64)
        {
            vehicleDirection = "NATIVE";
            txtVehicleStatus.setText(vehicleDirection);
            txtVehicleStatus.setTextColor(Color.GREEN);
        }
        else
        {
            vehicleDirection = "OPPOSITE";
            txtVehicleStatus.setText(vehicleDirection);
            txtVehicleStatus.setTextColor(Color.RED);
        }

        txtCurrentStatus.setText(""+ values.fLoadAmps+" / "+
                values.fLoadAmps_O+" Amps");

        updateStatusValue(((values.iSts0 & (1 << 0)) == 1), txtNativeIgnition);
        updateStatusValue(((values.iSts1 & (1 << 1)) == 2), txtPushButton);
        updateStatusValue(((values.iSts1 & (1 << 2)) == 4), txtBrake);
        updateStatusValue(((values.iSts1 & (1 << 4)) == 16), txtEmergencyButton);
        updateStatusValue(((values.iSts1 & (1<<5)) == 32), txtNativeMotor);
        updateStatusValue(((values.iSts2 & (1<<3)) == 8), txtThrottleStatus);
        updateStatusValue(((values.iSts0 & (1 << 0)) == 1), txtSysNativeIgnition);
        updateStatusValue((g_iPickerLoggedIn == 2), txtSysVehicleLogin);
        updateStatusValue((((values.iSts1 & (1 << 2)) == 4) || ((values.iSts1_O & 1 << 2) == 4)) , txtSysBrakeStatus);
        if((((values.iSts1 & (1 << 4)) == 16) || ((values.iSts1_O & (1 << 4)) == 16)))
        {
            txtSysVehicleEmergency.setText("ON");
            txtSysVehicleEmergency.setTextColor(Color.RED);
        }
        else
        {
            txtSysVehicleEmergency.setText("OFF");
            txtSysVehicleEmergency.setTextColor(Color.GREEN);
        }

        if ((((values.iSts1 & (1 << 2)) == 4) || ((values.iSts1_O & 1 << 2) == 4) || ((values.iSts1 & (1 << 3)) == 8)))
        {
            txtSysBrakeStatus.setText("ON");
            txtSysBrakeStatus.setTextColor(Color.RED);
        }
        else
        {
            txtSysBrakeStatus.setText("OFF");
            txtSysBrakeStatus.setTextColor(Color.GREEN);
        }

//        txtNativeIgnition.setText((values.iSts0 & (1 << 0)) == 1 ? "ON" : "OFF");
//        txtPushButton.setText((values.iSts1 & (1 << 1)) == 2 ? "ON" : "OFF");
//        txtBrake.setText(((values.iSts1 & (1 << 2)) == 4 ? "ON" : "OFF"));
//        txtEmergencyButton.setText((values.iSts1 & (1 << 4)) == 16 ? "ON" : "OFF");
//        txtNativeMotor.setText((values.iSts1 & (1<<5)) == 32 ? "ON" : "OFF");
//        txtThrottleStatus.setText((values.iSts2 & (1<<3)) == 8 ? "ON" : "OFF");

        updateStatusValue(((values.iSts1_O & (1 << 4)) == 16), txtOppositeEmergencyButton);
        updateStatusValue((((values.iSts1_O & 1 << 2) == 4) || ((values.iSts1 & (1 << 3)) == 8)), txtOppositeBrake);
        updateStatusValue(((values.iSts1_O & (1 << 1)) == 2), txtOppositePushButton);
        updateStatusValue(((values.iSts1_O & (1<<5)) == 32), txtOppositeMotor);
        updateStatusValue(((values.iSts2_O & (1<<3)) == 8), txtOppositeThrottleStatus);
        if(g_bIsPickerLoginRequired == false)
        {
            txtLoginRequiredStatus.setText("");
            txtSysVehicleLogin.setText("DISABLED");
            txtSysVehicleLogin.setTextColor(Color.WHITE);
        }
        else
        {
            txtLoginRequiredStatus.setText("Login to Run Vehicle");
        }

        if(g_isReverseEnabled == false)
        {
            txtSysReverseStatus.setText("DISABLED");
            txtSysReverseStatus.setTextColor(Color.WHITE);
        }
        else
        {
            txtSysReverseStatus.setText("ENABLED");
            txtSysReverseStatus.setTextColor(Color.WHITE);
        }

//        txtOppositeEmergencyButton.setText((values.iSts1_O & (1 << 4)) == 16  ? "ON" : "OFF");
//        txtOppositeBrake.setText((values.iSts1_O & 1 << 2) == 4 ? "ON" : "OFF");
//        txtOppositePushButton.setText((values.iSts1_O & (1 << 1)) == 2 ? "ON" : "OFF");
//        txtOppositeMotor.setText((values.iSts1_O & (1<<5)) == 32 ? "ON" : "OFF");
//        txtOppositeThrottleStatus.setText((values.iSts2_O & (1<<3)) == 8 ? "ON" : "OFF");
    }

    public void generateHBForMCB()
    {
        JSONObject hbResponseToMCB = null;
        try
        {
            if (((SERVER_HB_RES_JSON_LEN - 1) == g_strHbJsonFromServer.length()) || (SERVER_HB_RES_JSON_LEN == g_strHbJsonFromServer.length()))
            {
                hbResponseToMCB = new JSONObject(g_strHbJsonFromServer);
                update_Commands(hbResponseToMCB.getString("d"));
            }
            else
            {
                // CONFIGURE THE CLOUD DATA
                JSONObject hbData = new JSONObject(g_strHbJsonFromMCB);
                hbResponseToMCB = new JSONObject();
                hbResponseToMCB.put("a", 0);
                hbResponseToMCB.put("b", hbData.getString("b"));
                hbResponseToMCB.put("c", hbData.getString("c"));
                hbResponseToMCB.put("d", g_strCommand);
                hbResponseToMCB.put("e", hbData.getString("a"));
                hbResponseToMCB.put("f", hbData.getString("f"));
            }

            if(g_iMobilepercent <= MINIMUM_BATTERY_PERCENT)
            {
                g_iChargeMobile = 1;
            }
            else if(g_iMobilepercent <= MAXIMUM_BATTERY_PERCENT && g_iChargeMobile == 1)
            {
                g_iChargeMobile = 1;
            }
            else
            {
                g_iChargeMobile = 0;
            }

            StringBuilder d = new StringBuilder(hbResponseToMCB.getString("d"));
            int icmd0 = Integer.parseInt(d.substring(4, 8), 16);

            // CHECK FOR THE LOGIN REQUIRED OR NOT
            int icmd1 = Integer.parseInt(d.substring(8, 12), 16);
            g_isReverseEnabled = (icmd1 & 8) == 8 ? true : false;
            updateLoginStatus(icmd1);
            icmd0 = icmd0 | g_iChargeMobile | g_iPickerLoggedIn;
            String result = String.format("%" + 4 + "s", Integer.toHexString(icmd0)).replace(' ', '0');
            d.replace(4, 8, result);
            hbResponseToMCB.put("d", d.toString());
            myBtScannerService.write(hbResponseToMCB.toString().getBytes());
        }
        catch(Exception ex)
        {
            Log.e(TAG, "Error while generating the the clouddata");
        }
    }

    public void updateLoginStatus(int icmd1)
    {
        if((icmd1 & 4) == 4)
        {
            g_bIsPickerLoginRequired = true;
        }
        else
        {
            g_iPickerLoggedIn = 2;
            g_bIsPickerLoginRequired = false;
        }
    }
    //
    public static void updateStatusValue(boolean value, TextView textView)
    {
        if(value)
        {
            textView.setText("ON");
            textView.setTextColor(Color.GREEN);
        }
        else
        {
            textView.setText("OFF");
            textView.setTextColor(Color.RED);
        }
    }

    public void addStatusToHb() throws JSONException, IOException {
        Log.d("addStatusToHb:", "Entering");
        JSONObject jsonData = new JSONObject(g_strHbJsonFromServer);
        //int u16AppStatus = 0;
        Log.d("addStatusToHb:", "After object conversion");
        try{
            //Add App login status
            g_u16AppStatus |= 0x0000;

            //Get mobile battery status
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = registerReceiver(null, ifilter);
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryPct = (level) * (scale / 100);
//            Log.e(TAG, "level:" + level);
//            Log.e(TAG, "scale:" + scale);
//            Log.e(TAG, "batteryPct:" + batteryPct);

           //Ask for mobile charging if less than 30%
            if (batteryPct <= 30){
                g_u16AppStatus |= 0x0000;
            }else {
                g_u16AppStatus |= 0x0100;
            }

            //Ask for mobile charging if less than 30%
            if (txtPassword.length() == 0){
                g_u16AppStatus |= 0x0000;
            }else {
                g_u16AppStatus |= 0x0003;
            }
            String hexstring = Integer.toHexString(g_u16AppStatus);
//            Log.d("hexstring: ", hexstring);
            //Add to JSON
            jsonData.put("g",hexstring);

            g_strHbJsonFromServer = jsonData.toString();

            Log.d("g_strHbJsonFromServer:", g_strHbJsonFromServer);
        }
        catch (Exception ex) {
            Log.e("JSON Fetch Error", ex.toString());
        }
    }

    public void sendAppStatusToMcb() {
//        Log.d("sendAppStatusToMcb:", "Entering");
        JSONObject jsonData = new JSONObject();
        try{
            //Get mobile battery status
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = registerReceiver(null, ifilter);
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryPct = (level) * (scale / 100);
//            Log.e(TAG,"level: " + level);
//            Log.e(TAG,"scale: " + scale);
//            Log.e(TAG,"batteryPct: " + batteryPct);

//            Log.e(TAG,"Before charging check. g_u16AppStatus: "+g_u16AppStatus);
            //Ask for mobile charging if less than 30%
            if (batteryPct <= 30){
                g_u16AppStatus |= 0x0100;
                //Log.e(TAG,"Setting mobile to charge");
            }else {
                //check for requested for charging
                if (0 != (g_u16AppStatus & 0x0100)){
                    //Already requested. Wait until 95%
                    if (batteryPct > 95){
                        //Stop charging
                        g_u16AppStatus &= ~0x0100;
                    }else{
                        //Wait until 95% charging
                    }
                }else{
                    //Wait until mobile charging reduces to 30%
                    g_u16AppStatus &= ~0x0100;
                }
                //Log.e(TAG,"Stop mobile charging. g_u16AppStatus: "+g_u16AppStatus);
            }

            //Add App login status
            if (g_bLoggedIn) {
                g_u16AppStatus |= 0x0001;
            }else {
                g_u16AppStatus &= ~0x0001;
            }
            //Convert status to Hex string
            String hexstring = Integer.toHexString(g_u16AppStatus);
            //Log.d("hexstring: ", hexstring);
            //Add to JSON
            jsonData.put("sts",500);
            jsonData.put("z",hexstring);
            //Convert to string
            String strSendAppSts = jsonData.toString();
            //Log.e(TAG,"g_u16AppStatus: " + g_u16AppStatus);
            //Log.e(TAG,"App sts JSON: " + strSendAppSts);
            //Writing to BT
            myBtScannerService.write(strSendAppSts.getBytes());
        }
        catch (Exception ex) {
            Log.e("Err in APPSts framing", ex.toString());
        }
    }

	private int resetPickedSkuArray(){
        for (int i=0;i<4;i++){
            g_strPickedSkuArray[i][1] = "";
            g_strPickedSkuArray[i][2] = "";
        }
        return 0;
    }

    /*********************Custom Alert box for u32ErrorCodes related functions*******************************/
    private void playAlertSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.beepwarning);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        // Start the audio timer to stop the audio after a specified duration
        startAudioTimer();
    }

    private void startAudioTimer() {
        audioTimerHandler.postDelayed(() -> {
            stopAlertSound();
            // If the dialog is open, dismiss it
            if (dialog != null && dialog.isShowing()) {
                isPlayingSound = false;
//                dialog.dismiss();
//                g_isU32ErrorCodeAlert = false;
            }
        }, AUDIO_TIMER_DURATION);
    }

    private void stopAudioTimer() {
        audioTimerHandler.removeCallbacksAndMessages(null);
    }

    private void stopAlertSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //show alert box
    private void updateOrShowCustomAlertBox() {
        // Check if the dialog is not showing
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
            // Play the alert sound in a loop
            isPlayingSound = true;
            playAlertSound();


            // Set an OnDismissListener to stop the sound when the dialog is dismissed
            dialog.setOnDismissListener(dialog -> {
                // Stop the alert sound and the loop
                isPlayingSound = false;
                stopAlertSound();
                g_isU32ErrorCodeAlert = false;
            });

        }

    }


    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


//===========================================================================================================
//================================== VOICE BASE PICKING =====================================================
//===========================================================================================================

    static private final int STATE_START = 0;
    static private final int STATE_READY = 1;
    static private final int STATE_DONE = 2;
    static private final int STATE_MIC = 3;
    // Picking workflow states
    static private final int PICKING_STATE_IDLE = 0;
    static private final int PICKING_STATE_WAITING_READY = 1;
    static private final int PICKING_STATE_WAITING_LOCATION_CONFIRM = 2;
    static private final int PICKING_STATE_WAITING_PICK_CONFIRM = 3;
    static private final int PICKING_STATE_COMPLETED = 4;
    /* Used to handle permission request */
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private Model model;
    private SpeechService speechService;
    private TextView resultView;
    private TextToSpeech textToSpeech;
    private boolean ttsInitialized = false;
    // Add these fields to track listening state
    private boolean isListening = false;
    private String currentPartialResult = "";
    // Picking workflow variables
    private int pickingState = PICKING_STATE_IDLE;
    private String currentLocation = "ABC"; // Default location
    private int currentQuantity = 2; // Default quantity
    private String currentItem = "salt"; // Default item
    private Map<String, String> responseMap;
    private List<PickingTask> taskList;
    private int currentTaskIndex = 0;
    private Handler mainHandler;
    private String lastAgentMessage = "";
    private String lastUserMessage = "";
    private boolean isSpeaking = false;
    private Runnable resumeListeningRunnable = null;
    private static final int SPEECH_DELAY_MS = 500; // 0.5 second delay after TTS
    private static final String TAG = "VoskActivit";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_voice_hello);

        // Initialize main handler for UI updates
        mainHandler = new Handler(Looper.getMainLooper());

        // Setup layout
        resultView = findViewById(R.id.result_text);
        setUiState(STATE_START);

        // Initialize Text-to-Speech
        textToSpeech = new TextToSpeech(this, this);

        findViewById(R.id.recognize_mic).setOnClickListener(view -> recognizeMicrophone());
        ((ToggleButton) findViewById(R.id.pause)).setOnCheckedChangeListener((view, isChecked) -> pause(isChecked));

        LibVosk.setLogLevel(LogLevel.INFO);

        // Initialize response mapping
        initializeResponseMap();

        // Load tasks from CSV
        taskList = loadCsvTasks(this, "picking_data.csv");
        if (taskList != null && !taskList.isEmpty()) {
            currentLocation = taskList.get(currentTaskIndex).location;
            currentQuantity = taskList.get(currentTaskIndex).quantity;
            currentItem = taskList.get(currentTaskIndex).item;
        }

        // Check if user has given permission to record audio, init the model after permission is granted
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
        } else {
            initModel();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                appendToResultView("TTS Language not supported\n");
                ttsInitialized = false;
            } else {
                ttsInitialized = true;
                textToSpeech.setSpeechRate(0.9f); // Slightly slower for clarity
                appendToResultView("Voice assistant ready\n");

                textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {
                        isSpeaking = true;
                    }

                    @Override
                    public void onDone(String utteranceId) {
                        mainHandler.postDelayed(() -> resumeListeningAfterSpeech(), SPEECH_DELAY_MS);
                    }

                    @Override
                    public void onError(String utteranceId) {
                        mainHandler.postDelayed(() -> resumeListeningAfterSpeech(), SPEECH_DELAY_MS);
                    }
                });
            }
        } else {
            appendToResultView("TTS initialization failed\n");
            ttsInitialized = false;
        }
    }


    private void speakText(String text) {
        if (ttsInitialized && textToSpeech != null && !text.trim().isEmpty()) {
            pauseListeningForSpeech();

            Bundle params = new Bundle();
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params, "utterance_id");
        }
    }


    private void pauseListeningForSpeech() {
        isSpeaking = true;
        if (speechService != null) {
            speechService.setPause(true);
        }

        // Cancel any pending resume operations
        if (resumeListeningRunnable != null) {
            mainHandler.removeCallbacks(resumeListeningRunnable);
        }
    }


    // Method to schedule resuming listening
    private void scheduleResumeListening(int delayMs) {
        resumeListeningRunnable = new Runnable() {
            @Override
            public void run() {
                resumeListeningAfterSpeech();
            }
        };

        mainHandler.postDelayed(resumeListeningRunnable, delayMs);
    }


    private void resumeListeningAfterSpeech() {
        if (isFinishing() || isDestroyed()) {
            return;
        }

        if (speechService == null || resultView == null || mainHandler == null) {
            Log.w(TAG, "Resume skipped: Critical component is null.");
            return;
        }

        isSpeaking = false;

        if (pickingState != PICKING_STATE_IDLE && pickingState != PICKING_STATE_COMPLETED) {
            try {
                speechService.setPause(false);
                isListening = true;
                updateListeningDisplay();
            } catch (Exception e) {
                Log.e(TAG, "resumeListeningAfterSpeech failed: " + e.getMessage());
            }
        }
    }




    private void stopSpeechAndResume() {
        if (textToSpeech != null && isSpeaking) {
            textToSpeech.stop();
        }

        // Cancel scheduled resume
        if (resumeListeningRunnable != null) {
            mainHandler.removeCallbacks(resumeListeningRunnable);
        }

        // Resume immediately
        resumeListeningAfterSpeech();
    }

    private void initializeResponseMap() {
        responseMap = new HashMap<>();
        // Yes responses
        responseMap.put("yes", "yes");
        responseMap.put("yeah", "yes");
        responseMap.put("yep", "yes");
        responseMap.put("okay", "yes");
        responseMap.put("ok", "yes");
        responseMap.put("sure", "yes");
        responseMap.put("ready", "yes");
        responseMap.put("go", "yes");
        responseMap.put("proceed", "yes");
        responseMap.put("continue", "yes");
        responseMap.put("affirmative", "yes");
        responseMap.put("certainly", "yes");
        responseMap.put("definitely", "yes");
        responseMap.put("absolutely", "yes");
        responseMap.put("of course", "yes");
        responseMap.put("please do", "yes");

        // No responses
        responseMap.put("no", "no");
        responseMap.put("nope", "no");
        responseMap.put("nah", "no");
        responseMap.put("not now", "no");
        responseMap.put("never", "no");
        responseMap.put("negative", "no");
        responseMap.put("stop", "no");
        responseMap.put("cancel", "no");
        responseMap.put("don't", "no");
        responseMap.put("do not", "no");
        responseMap.put("decline", "no");
        responseMap.put("refuse", "no");
        responseMap.put("reject", "no");
    }

    private void initModel() {
        StorageService.unpack(this, "model-en-us", "model",
                (model) -> {
                    this.model = model;
                    setUiState(STATE_READY);
                },
                (exception) -> setErrorState("Failed to unpack the model: " + exception.getMessage()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initModel();
            } else {
                finish();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (resumeListeningRunnable != null && mainHandler != null) {
            mainHandler.removeCallbacks(resumeListeningRunnable);
            resumeListeningRunnable = null;
        }

        if (speechService != null) {
            try {
                speechService.stop();
                speechService.shutdown();
            } catch (Exception e) {
                Log.w(TAG, "Error shutting down speech service: " + e.getMessage());
            }
            speechService = null;
        }

        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }

        resultView = null;
        mainHandler = null;
    }


    @Override
    public void onResult(String hypothesis) {
        String spokenText = extractTextFromHypothesis(hypothesis);
        if (spokenText != null && !spokenText.trim().isEmpty()) {
            processPickingWorkflow(spokenText);
        }
        currentPartialResult = "";
        updateListeningDisplay();
    }

    @Override
    public void onFinalResult(String hypothesis) {
        String spokenText = extractTextFromHypothesis(hypothesis);
        if (spokenText != null && !spokenText.trim().isEmpty()) {
            processPickingWorkflow(spokenText);
        }

        // Only stop if workflow is completed
        if (pickingState == PICKING_STATE_COMPLETED) {
            setUiState(STATE_DONE);
            isListening = false;
            currentPartialResult = "";
            if (speechService != null) {
                speechService.stop();
                speechService = null;
            }
        }
    }

    @Override
    public void onPartialResult(String hypothesis) {
        // Filter out empty or whitespace-only partial results
        if (hypothesis != null && !hypothesis.trim().isEmpty()) {
            String partialText = extractTextFromHypothesis(hypothesis);
            if (partialText != null && !partialText.trim().isEmpty()) {
                currentPartialResult = partialText;
                updateListeningDisplay();
            }
        } else if (isListening && currentPartialResult.isEmpty()) {
            updateListeningDisplay();
        }
    }

    @Override
    public void onError(Exception e) {
        setErrorState(e.getMessage());
        isListening = false;
        pickingState = PICKING_STATE_IDLE;
    }

    @Override
    public void onTimeout() {
        // Don't stop on timeout during picking workflow
        if (pickingState != PICKING_STATE_IDLE) {
            String message = "I didn't hear you. Please respond.";
            appendToResultView("\n[Timeout] Agent: " + message + "\n");
            speakText(message);
            updateListeningDisplay();
        } else {
            setUiState(STATE_DONE);
            isListening = false;
        }
    }

    private void startPickingWorkflow() {
        pickingState = PICKING_STATE_WAITING_READY;
        String message = "Are you ready for the picking?";
        lastAgentMessage = "🤖 Agent: " + message;
        updateResultView();
        speakText(message);
        updateListeningDisplay();
    }

    private void handleReadyResponse(String response) {
        String normalizedResponse = normalizeResponse(response);

        if ("yes".equals(normalizedResponse)) {
            pickingState = PICKING_STATE_WAITING_LOCATION_CONFIRM;
            String message = "Go to Location " + currentLocation;
            appendToResultView("Agent: " + message + "\n");
            speakText(message);
            updateListeningDisplay();
        } else if ("no".equals(normalizedResponse)) {
            String message = "Waiting for you to be ready. Let me know when you're ready.";
            appendToResultView("Agent: " + message + "\n");
            speakText(message);
            // Stay in WAITING_READY state
            updateListeningDisplay();
        } else {
            String message = "Please respond with yes or no. Are you ready for the picking?";
            appendToResultView("Agent: " + message + "\n");
            speakText(message);
            updateListeningDisplay();
        }
    }

    private void handleLocationConfirmation(String response) {
        String normalizedResponse = normalizeResponse(response);

        if ("yes".equals(normalizedResponse)) {
            String pickMessage = "Pick " + currentQuantity + " " + currentItem;
            appendToResultView("Agent: " + pickMessage + "\n");
            speakText(pickMessage);

            // Wait for pick confirmation
            pickingState = PICKING_STATE_WAITING_PICK_CONFIRM;
            updateListeningDisplay();

        } else if ("no".equals(normalizedResponse)) {
            String message = "Please go to Location " + currentLocation + " first.";
            appendToResultView("Agent: " + message + "\n");
            speakText(message);
            updateListeningDisplay();
        } else {
            String message = "Please confirm with yes when you reach Location " + currentLocation;
            appendToResultView("Agent: " + message + "\n");
            speakText(message);
            updateListeningDisplay();
        }
    }

    private void handlePickConfirmation(String response) {
        String normalizedResponse = normalizeResponse(response);

        if ("yes".equals(normalizedResponse)) {
            String completeMessage = "Task completed successfully!";
            appendToResultView("Agent: " + completeMessage + "\n\n");
            speakText(completeMessage);

            moveToNextTask();

            mainHandler.postDelayed(() -> {
                appendToResultView("--- Ready for next picking task ---\n");
                resetPickingWorkflow();
            }, 2000);

            pickingState = PICKING_STATE_COMPLETED;

        } else if ("no".equals(normalizedResponse)) {
            String message = "Please confirm once you have picked the items.";
            appendToResultView("Agent: " + message + "\n");
            speakText(message);
            updateListeningDisplay();
        } else {
            String message = "Say yes when you've picked the items.";
            appendToResultView("Agent: " + message + "\n");
            speakText(message);
            updateListeningDisplay();
        }
    }

    private void processPickingWorkflow(String hypothesis) {
        if (hypothesis == null || hypothesis.trim().isEmpty()) {
            return;
        }

        String cleanHypothesis = hypothesis.toLowerCase().trim();
        String spokenText = extractTextFromHypothesis(hypothesis);
        lastUserMessage = "👤 User: " + spokenText;
        updateResultView();


        switch (pickingState) {
            case PICKING_STATE_IDLE:
                // Start the picking workflow
                startPickingWorkflow();
                break;

            case PICKING_STATE_WAITING_READY:
                handleReadyResponse(cleanHypothesis);
                break;

            case PICKING_STATE_WAITING_LOCATION_CONFIRM:
                handleLocationConfirmation(cleanHypothesis);
                break;

            case PICKING_STATE_WAITING_PICK_CONFIRM:
                handlePickConfirmation(cleanHypothesis);
                break;

            case PICKING_STATE_COMPLETED:
                // Workflow completed, reset for next cycle
                resetPickingWorkflow();
                break;
        }
    }

    private void moveToNextTask() {
        if (taskList != null && !taskList.isEmpty()) {
            currentTaskIndex = (currentTaskIndex + 1) % taskList.size();
            currentLocation = taskList.get(currentTaskIndex).location;
            currentQuantity = taskList.get(currentTaskIndex).quantity;
            currentItem = taskList.get(currentTaskIndex).item;
        }
    }

    private String normalizeResponse(String response) {
        if (response == null) return "unknown";

        response = response.toLowerCase().trim();

        // Check for exact matches first
        if (responseMap.containsKey(response)) {
            return responseMap.get(response);
        }

        // Check for partial matches
        for (Map.Entry<String, String> entry : responseMap.entrySet()) {
            if (response.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        return "unknown";
    }

    private void resetPickingWorkflow() {
        pickingState = PICKING_STATE_IDLE;
        // Automatically start next cycle
        startPickingWorkflow();
    }

    private void updateListeningDisplay() {
        mainHandler.post(() -> {
            // Don't show listening status while agent is speaking
            if (isSpeaking) {
                return;
            }

            if (isListening) {
                if (currentPartialResult.isEmpty()) {
                    String listeningMessage = getListeningMessage();
                    String currentText = resultView.getText().toString();
                    if (!currentText.endsWith(listeningMessage)) {
                        resultView.append(listeningMessage);
                    }
                } else {
                    String currentText = resultView.getText().toString();
                    String listeningMessage = getListeningMessage();

                    if (currentText.endsWith(listeningMessage)) {
                        String newText = currentText.substring(0, currentText.length() - listeningMessage.length()) + currentPartialResult;
                        resultView.setText(newText);
                    } else {
                        String[] lines = currentText.split("\n");
                        if (lines.length > 0) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < lines.length - 1; i++) {
                                sb.append(lines[i]).append("\n");
                            }
                            sb.append(currentPartialResult);
                            resultView.setText(sb.toString());
                        }
                    }
                }
            }
        });
    }

    private void appendToResultView(String text) {
        mainHandler.post(() -> resultView.append(text));
    }

    public static class PickingTask {
        public String location;
        public int quantity;
        public String item;

        public PickingTask(String location, int quantity, String item) {
            this.location = location;
            this.quantity = quantity;
            this.item = item;
        }
    }

    public static List<PickingTask> loadCsvTasks(Context context, String filename) {
        List<PickingTask> tasks = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip header
                    isFirstLine = false;
                    continue;
                }
                String[] tokens = line.split(",");
                if (tokens.length >= 2) {
                    String location = tokens[0].trim();
                    String quantityAndItem = tokens[1].trim();

                    // Parse quantity and item from the second column
                    // Format: "2 salt" or "3 sugar"
                    String[] parts = quantityAndItem.split("\\s+", 2); // Split on whitespace, max 2 parts

                    if (parts.length >= 2) {
                        try {
                            int quantity = Integer.parseInt(parts[0]);
                            String item = parts[1];
                            tasks.add(new PickingTask(location, quantity, item));
                        } catch (NumberFormatException e) {
                            // Skip invalid quantity entries
                            continue;
                        }
                    } else if (parts.length == 1) {
                        // Handle case where only quantity is provided (backward compatibility)
                        try {
                            int quantity = Integer.parseInt(parts[0]);
                            String item = "item"; // Default item name
                            tasks.add(new PickingTask(location, quantity, item));
                        } catch (NumberFormatException e) {
                            // Skip invalid entries
                            continue;
                        }
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }

    private String extractTextFromHypothesis(String hypothesis) {
        if (hypothesis == null) return "";

        try {
            JSONObject jsonObject = new JSONObject(hypothesis);
            if (jsonObject.has("text")) {
                return jsonObject.getString("text");
            } else if (jsonObject.has("partial")) {
                return jsonObject.getString("partial");
            }
        } catch (JSONException e) {
            // Fallback in case hypothesis is not valid JSON
        }
        return hypothesis;
    }

    private String getListeningMessage() {
        switch (pickingState) {
            case PICKING_STATE_WAITING_READY:
                return "[Waiting for ready confirmation...]";
            case PICKING_STATE_WAITING_LOCATION_CONFIRM:
                return "[Waiting for location confirmation...]";
            case PICKING_STATE_WAITING_PICK_CONFIRM:
                return "[Waiting for pick confirmation...]";
            default:
                return "[Listening...]";
        }
    }


    private void updateResultView() {
        final String displayText = lastUserMessage + "\n" + lastAgentMessage;
        mainHandler.post(() -> resultView.setText(displayText));
    }

    private void setUiState(int state) {
        mainHandler.post(() -> {
            switch (state) {
                case STATE_START:
                    resultView.setText("Preparing Picking Assistant...");
                    resultView.setMovementMethod(new ScrollingMovementMethod());
                    findViewById(R.id.recognize_mic).setEnabled(false);
                    findViewById(R.id.pause).setEnabled(false);
                    isListening = false;
                    pickingState = PICKING_STATE_IDLE;
                    break;
                case STATE_READY:
                    resultView.setText("Picking Assistant Ready\nPress 'Start Picking' to begin picking workflow\nVoice assistant initialized: " + (ttsInitialized ? "Yes" : "No"));
                    ((Button) findViewById(R.id.recognize_mic)).setText("Start Picking");
                    findViewById(R.id.recognize_mic).setEnabled(true);
                    findViewById(R.id.pause).setEnabled(false);
                    isListening = false;
                    pickingState = PICKING_STATE_IDLE;
                    break;
                case STATE_DONE:
                    ((Button) findViewById(R.id.recognize_mic)).setText("Start Picking");
                    findViewById(R.id.recognize_mic).setEnabled(true);
                    findViewById(R.id.pause).setEnabled(false);
                    ((ToggleButton) findViewById(R.id.pause)).setChecked(false);
                    isListening = false;
                    currentPartialResult = "";
                    pickingState = PICKING_STATE_IDLE;
                    break;
                case STATE_MIC:
                    ((Button) findViewById(R.id.recognize_mic)).setText("Stop Picking");
                    resultView.setText("=== PICKING WORKFLOW STARTED ===\n");
                    findViewById(R.id.recognize_mic).setEnabled(true);
                    findViewById(R.id.pause).setEnabled(true);
                    isListening = true;
                    currentPartialResult = "";
                    // Start the picking workflow
                    startPickingWorkflow();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + state);
            }
        });
    }

    private void setErrorState(String message) {
        mainHandler.post(() -> {
            resultView.setText("Error: " + message);
            ((Button) findViewById(R.id.recognize_mic)).setText("Start Picking");
            findViewById(R.id.recognize_mic).setEnabled(false);
            isListening = false;
            pickingState = PICKING_STATE_IDLE;
        });
    }

    private void recognizeMicrophone() {
        if (speechService != null) {
            setUiState(STATE_DONE);
            speechService.stop();
            speechService = null;
        } else {
            setUiState(STATE_MIC);
            try {
                // Configure recognizer with picking-specific vocabulary
                Recognizer rec = new Recognizer(model, 16000.0f);
                speechService = new SpeechService(rec, 16000.0f);
                speechService.startListening(this);
            } catch (IOException e) {
                setErrorState(e.getMessage());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mainHandler != null && resumeListeningRunnable != null) {
            mainHandler.removeCallbacks(resumeListeningRunnable);
        }
    }


    private void pause(boolean checked) {
        if (speechService != null) {
            if (checked) {
                // Pausing - stop any ongoing speech first
                if (isSpeaking) {
                    stopSpeechAndResume();
                }

                speechService.setPause(true);
                isListening = false;
                appendToResultView("\n[PAUSED - Press RESUME button to resume]\n");

            } else {
                // Resuming - only if not currently speaking
                if (!isSpeaking) {
                    speechService.setPause(false);
                    isListening = true;
                    appendToResultView("\n[RESUMED]\n");
                    updateListeningDisplay();
                }
            }
        }
    }




}
