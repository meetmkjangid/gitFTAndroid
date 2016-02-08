package in.futuretrucks.Constant;

/**
 * Created by win8 on 9/10/2015.
 */
public class Constant {

    /*Key Constants*/
    //MAHADEV SYSTEM
    public static final String GOOGLE_PLACE_API_KEY="AIzaSyCD4vTSXqiY0XWnMTnRLgw5HyEX2OVMG1I";
    public static final String GCM_REGISTER_KEY="gcm_reg_key";


    //PRIYO SYSTEM
    //public static final String GOOGLE_PLACE_API_KEY="AIzaSyDYnG93I3yDgkGUmF8PHEA43smGXjhK2Gs";
    public static final String Call_No="09891705019 ";
    public static final String ANDROID="android ";

    /*Server URL*/
    public static final String BASE_URL = "http://tracklabs.in:3000/";
    public static final String BASE_URL_IMAGE = "http://tracklabs.in:3000/api/file/";
    public static final String REGISTRATION = "api/users/";
    public static final String DRIVER_REGISTRATION = "api/drivers/";
    public static final String FORGOT_PASSWORD = "auth/forgotPass";
    public static final String LOGIN = "auth/login";
    public static final String LOGOUT = "auth/logout";
    public static final String VERIFY = "auth/verify_otp";
    public static final String VERIFY_OTP_FP = "auth/verify_otp_fp";
    public static final String RESEND_OTP = "auth/resend_otp";
    public static final String GET_CITY = "api/cities//F10U08T2015U/?sCity=";
    public static final String GET_VEHICLE_CATEGORY = "api/truckTypes/data/F10U08T2015U";
    public static final String GET_MATERIAL_TYPES = "api/materialTypes/F10U08T2015U";



    //Methos For Call

    //Auto SMS Detection
    public static final String SMS_ORIGIN = "MM-FTruck";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";



    public static String EMAIL_FORMATE="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    //Application Constants
    public static String CURRENT_LATITUDE="current_lat";
    public static String CURRENT_LONGITUDE="current_lng";
    public static String isLogin="is_login";


    /*User Constant*/
    public static String USER_TOKEN="user_token";
    public static String USER_ID="user_id";
    public static String NEW_DRIVER_REG_ID="new_driver_reg_id";
    public static String NEW_TRUCK_REG_ID="new_truck_reg_id";
    public static String OWNER_NAME="owner_name";
    public static String USER_EMAIl="user_email";
    public static String USER_MOBILE="user_mobile";
    public static String USER_TYPE="user_type";
    public static String USER_PASSWORD="user_password";
    public static String USER_COMPANY_NAME="user_company_name";
    public static String USER_CITY="city";
    public static String USER_NON_CUST_VALUES="noncust_details";

    //User Documents
    public static String USER_PROFILE_PIC="user_profile_pic";
    public static String USER_PAN_CARD_PIC="user_pan_card_pic";
    public static String USER_PHOTO_ID_PROOF_PIC="user_photo_id_proof_pic";
    public static String USER_TIN_NUM_PROOF_PIC="user_tin_number_proof_pic";
    public static String USER_SERVICE_TAX_NUM_PIC="user_service_tax_num_pic";
    public static String USER_COMAPNY_LOGO_PIC="user_comapny_logo_pic";
    public static String USER_ADDRESS_PROOF_PIC="user_address_proof_pic";




    //User Type String Define
    public static String truck_owner="truck_owner";
    public static String cha="cha";
    public static String customer="customer";
    public static String sl="sl";
    public static String transporter="transporter";

        //Request for city list
        public static String CITY_LIST_REQUEST = "city_list_request";
        public static int DAILY_SERVICE_IN_CITY = 101;
        public static int INTERESTED_CITY = 102;
        public static int BRANCH_IN_CITY = 103;

    //Driver Constant
    public static String DRIVER_ID ="_id";
    public static String DRIVER_FIRST_NAME ="first_name";
    public static String DRIVER_LAST_NAME="last_name";
    public static String LICENSE_NO="license_no";
    public static String DRIVER_MOBILE="mobile";
    public static String ADDRESS_1="address_line_1";
    public static String ADDRESS_2="address_line_2";
    public static String AREA="area";
    public static String CITY = "city";
    public static String DISTRICT = "district";
    public static String PINCODE = "pincode";
  //  public static String transporter = "transporter";


    //Pick Image Constant
    public static int PROFILE_PIC_DOC = 1001;
    public static int PHOTO_ID_DOC = 1002;
    public static int PAN_CARD_DOC = 1003;
    public static int TIN_NO_DOC = 1004;
    public static int SERVICE_PROOF_DOC = 1005;
    public static int COMPANY_LOGO_DOC = 1006;
    public static int ADDRESS_PROOF_DOC = 1007;

    public static int DRIVER_PROFILE_PHOTO = 1008;
    public static int DRIVER_ADDRESS_PROOF = 1009;
    public static int DRIVER_POLICE_VERIFICATION = 1010;
    public static int DRIVER_LICENSE = 1011;

    public static int VEHICLE_PHOTO = 1012;
    public static int VEHICLE_PERMIT = 1013;
    public static int VEHICLE_FITNESS_CERT = 1014;
    public static int VEHICLE_CHASSIS_TRACE = 1015;
    public static int VEHICLE_INSURANCE_COPY = 1016;
    public static int VEHICLE_ROAD_TAX= 1017;
    public static int VEHICLE_RC_BOOK = 1018;

    public static String Transporter="Transporter";
    public static String Truck_Owner="Truck Owner";
    public static String Customer="Customer";
    public static String CHA="CHA";
    public static String Shipping_line="Shipping line";
}
