package pl.design.mrn.matned.dogmanagementapp.dataBase;

import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;
import java.util.List;

import static pl.design.mrn.matned.dogmanagementapp.Statics.*;


public class DataBaseCreation {



    public static void create(SQLiteDatabase db) {
        List<String> tables = Arrays.asList(
                CREATE_DOGS_TABLE,
                CREATE_CONFIGURATION_TABLE,
                CREATE_CONFIGURATION_MESSAGE_TABLE,
                CREATE_MESSAGE_TABLE,
                CREATE_BREEDING_TABLE,
                CREATE_CHIP_TABLE,
                CREATE_NOTES_TABLE,
                CREATE_OWNERS_TABLE,
                CREATE_SIGNS_TABLE,
                CREATE_TATTOO_TABLE,
                CREATE_ALLERGIES_TABLE,
                CREATE_TEETH_CONTROL_TABLE,
                CREATE_RABIES_VACCINE_TABLE,
                CREATE_BIRTH_CONTROL_TABLE,
                CREATE_OTHER_VACCINE_TABLE,
                CREATE_DEWORMING_TABLE,
                CREATE_TREATMENT_TABLE
        );
        for (String el : tables) {
            db.execSQL(el);
        }
    }



    private static final String CREATE_DOGS_TABLE = "CREATE TABLE " + DOGS_TABLE + "(" +
            DOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DOG_NAME + " TEXT, " +
            DOG_RACE + " TEXT, " +
            DOG_BIRTH_DATE + " TEXT, " +
            DOG_COLOR + " TEXT, " +
            DOG_PHOTO + " TEXT, " +
            DOG_SEX + " TEXT );";

    private static final String CREATE_CONFIGURATION_TABLE = "CREATE TABLE " + CONFIGURATION_TABLE + "(" +
            CONFIGURATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CONFIGURATION_ALERT_VACCINE + " INTEGER, " +
            CONFIGURATION_ALERT_BIRTHDAY + " INTEGER, " +
            CONFIGURATION_ALERT_DEWORMING + " INTEGER, " +
            CONFIGURATION_ALERT_OTHER + " INTEGER, " +
            CONFIGURATION_IS_WELCOME_SENT + " INTEGER );";

    private static final String CREATE_CONFIGURATION_MESSAGE_TABLE = "CREATE TABLE " + CONFIGURATION_MESSAGE_TABLE + "(" +
            CONFIGURATION_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CONFIGURATION_MESSAGE_SUBJECT + " TEXT, " +
            CONFIGURATION_MESSAGE_DATE + " TEXT, " +
            CONFIGURATION_MASTER_ID + " INTEGER );";

    private static final String CREATE_MESSAGE_TABLE = "CREATE TABLE " + MESSAGE_TABLE + "(" +
            MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MESSAGE_SUBJECT + " TEXT, " +
            MESSAGE + " TEXT, " +
            MESSAGE_DATE_TIME + " TEXT, " +
            MESSAGE_STATUS + " TEXT );";

    private static final String CREATE_BREEDING_TABLE = "CREATE TABLE " + BREEDING_TABLE + "(" +
            BREEDING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BREEDING_NAME + " TEXT, " +
            BREEDING_ADDRESS + " TEXT, " +
            BREEDING_PHONE + " TEXT, " +
            BREEDING_EMAIL + " TEXT, " +
            BREEDING_DESCRIPTION + " TEXT, " +
            DOG_ID + " INTEGER );";
    private static final String CREATE_CHIP_TABLE = "CREATE TABLE " + CHIP_TABLE + "(" +
            CHIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CHIP_NUMBER + " TEXT, " +
            CHIP_PUT_DATE + " TEXT, " +
            CHIP_EXP_DATE + " TEXT, " +
            CHIP_DESCRIPTION + " TEXT, " +
            DOG_ID + " INTEGER );";
    private static final String CREATE_NOTES_TABLE = "CREATE TABLE " + NOTES_TABLE + "(" +
            NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTE + " TEXT, " +
            NOTE_DATE + " TEXT, " +
            DOG_ID + " INTEGER );";

    private static final String CREATE_OWNERS_TABLE = "CREATE TABLE " + DOGS_OWNER_TABLE + "(" +
            OWNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            OWNER_NAME + " TEXT, " +
            OWNER_SURNAME + " TEXT, " +
            OWNER_ADDRESS + " TEXT, " +
            OWNER_PHONE + " TEXT, " +
            OWNER_EMAIL + " TEXT, " +
            OWNER_DATE_FROM + " TEXT, " +
            OWNER_DATE_TO + " TEXT, " +
            OWNER_DESCRIPTION + " TEXT, " +
            DOG_ID + " INTEGER );";

    private static final String CREATE_SIGNS_TABLE = "CREATE TABLE " + SIGNS_TABLE + "(" +
            SIGN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SIGN_DESCRIPTION + " TEXT, " +
            SIGN_DATE + " TEXT, " +
            DOG_ID + " INTEGER );";

    private static final String CREATE_TATTOO_TABLE = "CREATE TABLE " + DOGS_TATTOO_TABLE + "(" +
            TATTOO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TATTOO_DATE + " TEXT, " +
            TATTOO_DESCRIPTION + " TEXT, " +
            DOG_ID + " INTEGER );";


//    HEALTH

    private static final String CREATE_ALLERGIES_TABLE = "CREATE TABLE " + ALLERGIES_TABLE + "(" +
            ALLERGY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ALLERGEN + " TEXT, " +
            ALLERGY_DESCRIPTION + " TEXT, " +
            ALLERGY_DETECTION_DATE + " TEXT, " +
            WAS_ALLERGY_TREATED + " INTEGER DEFAULT 0, " +
            ALLERGY_DATE_OF_TREATMENT + " TEXT, " +
            ALLERGY_DATE_OF_NEXT_TREATMENT + " TEXT, " +
            ALLERGY_NOTE + " TEXT, " +
            ALLERGY_STAMP_PHOTO + " TEXT, " +
            DOG_ID + " INTEGER );";

    private static final String CREATE_TEETH_CONTROL_TABLE = "CREATE TABLE " + TEETH_CONTROL_TABLE + "(" +
            TEETH_C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TEETH_C_DESCRIPTION + " TEXT, " +
            TEETH_C_DATE + " TEXT, " +
            TEETH_C_NEXT_DATE + " TEXT, " +
            TEETH_C_NOTE + " TEXT, " +
            TEETH_C_STAMP_PHOTO + " TEXT, " +
            DOG_ID + " INTEGER );";

    private static final String CREATE_RABIES_VACCINE_TABLE = "CREATE TABLE " + RABIES_VACCINE_TABLE + "(" +
            RABIES_VACCINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            RABIES_VACCINE_MEDICINE + " TEXT, " +
            RABIES_VACCINE_DESCRIPTION + " TEXT, " +
            RABIES_VACCINE_DATE + " TEXT, " +
            RABIES_VACCINE_NEXT_DATE + " TEXT, " +
            RABIES_VACCINE_NOTE + " TEXT, " +
            RABIES_VACCINE_STAMP_PHOTO + " TEXT, " +
            DOG_ID + " INTEGER );";

    private static final String CREATE_BIRTH_CONTROL_TABLE = "CREATE TABLE " + BIRTH_CONTROL_TABLE + "(" +
            BIRTH_CONTROL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BIRTH_CONTROL_NUMBER_OF_CHILDREN + " INTEGER, " +
            BIRTH_CONTROL_DESCRIPTION + " TEXT, " +
            BIRTH_CONTROL_DATE + " TEXT, " +
            BIRTH_CONTROL_NOTE + " TEXT, " +
            BIRTH_CONTROL_STAMP_PHOTO + " TEXT, " +
            DOG_ID + " INTEGER );";

    private static final String CREATE_OTHER_VACCINE_TABLE = "CREATE TABLE " + OTHER_VACCINE_TABLE + "(" +
            OTHER_VACCINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            OTHER_VACCINE_MEDICINE + " TEXT, " +
            OTHER_VACCINE_DESCRIPTION + " TEXT, " +
            OTHER_VACCINE_DATE + " TEXT, " +
            OTHER_VACCINE_NEXT_DATE + " TEXT, " +
            OTHER_VACCINE_NOTE + " TEXT, " +
            OTHER_VACCINE_STAMP_PHOTO + " TEXT, " +
            DOG_ID + " INTEGER );";

    private static final String CREATE_DEWORMING_TABLE = "CREATE TABLE " + DEWORMING_TABLE + "(" +
            DEWORMING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DEWORMING_MEDICINE + " TEXT, " +
            DEWORMING_DESCRIPTION + " TEXT, " +
            DEWORMING_DATE + " TEXT, " +
            DEWORMING_NEXT_DATE + " TEXT, " +
            DEWORMING_NOTE + " TEXT, " +
            DEWORMING_STAMP_PHOTO + " TEXT, " +
            DOG_ID + " INTEGER );";

    private static final String CREATE_TREATMENT_TABLE = "CREATE TABLE " + TREATMENT_TABLE + "(" +
            TREATMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ILLNESS + " TEXT, " +
            TREATMENT_DESCRIPTION + " TEXT, " +
            TREATMENT_DATE + " TEXT, " +
            DATE_OF_NEXT_TREATMENT + " TEXT, " +
            TREATMENT_NOTE + " TEXT, " +
            TREATMENT_STAMP_PHOTO + " TEXT, " +
            DOG_ID + " INTEGER );";

}
