package pl.design.mrn.matned.dogmanagementapp;

public class Statics {

    public static final String DATE_FORMAT = "dd MMM yyyy";
    public static final String DATE_FORMAT_MESSAGE = "E, dd MMM yyyy HH:mm:ss z";

    public static final String USAGE = "USAGE";

    public static final String USAGE_EDIT = "EDIT";
    public static final String USAGE_INFO = "INFO";
    public static final String USAGE_ADD = "ADD";

    public static final String APP_NAME = "Dogzapp";

    public static final String LIST_ELEMENT_ACTIVITY = "LIST_ELEMENT_ACTIVITY";


    public static final String DATA_TYPE = "DATA_TYPE";

    public static final String START = "START_ACTIVITY";
    public static final String CHIP = "CHIP_ACTIVITY";
    public static final String TATTOO = "TATTOO_ACTIVITY";
    public static final String SIGN = "SIGN_ACTIVITY";
    public static final String NOTE_ACTIVITY = "NOTE_ACTIVITY";
    public static final String BREEDING = "BREEDING_ACTIVITY";
    public static final String OWNER = "OWNER_ACTIVITY";


    public static final String ALLERGIES = "ALLERGIES";
    public static final String TREATMENT = "TREATMENT";
    public static final String TEETH = "TEETH";
    public static final String BIRTH = "BIRTH";
    public static final String DEWORMING = "DEWORMING";
    public static final String INJECTION = "INJECTION";

    public static final String DATA_SPLITMENT_REGEX = ">>";
    public static final String R_INJECTION = "R_INJECTION";
    public static final String O_INJECTION = "O_INJECTION";

//    DATABASE STATIC STRINGS:

    public static final String DATABASE_NAME = "dogs_db";

    public static final String DOG_ID = "DOG_ID";

    public static final String DOGS_TABLE = "DOGS";
    public static final String DOG_NAME = "DOG_NAME";
    public static final String DOG_RACE = "DOG_RACE";
    public static final String DOG_BIRTH_DATE = "DOG_BIRTH_DATE";
    public static final String DOG_COLOR = "DOG_COLOR";
    public static final String DOG_SEX = "DOG_SEX";
    public static final String DOG_PHOTO = "DOG_PHOTO";

    public static final String DOGS_TATTOO_TABLE = "DOGS_TATTOO";
    public static final String TATTOO_ID = "TATTOO_ID";
    public static final String TATTOO_DESCRIPTION = "TATTOO_DESCRIPTION";
    public static final String TATTOO_DATE = "TATTOO_DATE";

    public static final String CHIP_TABLE = "CHIP_TABLE";
    public static final String CHIP_ID = "CHIP_ID";
    public static final String CHIP_NUMBER = "CHIP_NUMBER";
    public static final String CHIP_PUT_DATE = "CHIP_PUT_DATE";
    public static final String CHIP_EXP_DATE = "CHIP_EXP_DATE";
    public static final String CHIP_DESCRIPTION = "CHIP_DESCRIPTION";

    public static final String NOTES_TABLE = "NOTES_TABLE" ;
    public static final String NOTE_ID = "NOTE_ID" ;
    public static final String NOTE_DATE = "NOTE_DATE" ;
    public static final String NOTE = "NOTE" ;

    public static final String DOGS_OWNER_TABLE = "DOGS_OWNER_TABLE";
    public static final String OWNER_ID = "OWNER_ID";
    public static final String OWNER_NAME = "OWNER_NAME";
    public static final String OWNER_SURNAME = "OWNER_SURNAME";
    public static final String OWNER_ADDRESS = "OWNER_ADDRESS";
    public static final String OWNER_PHONE = "OWNER_PHONE";
    public static final String OWNER_EMAIL = "OWNER_EMAIL";
    public static final String OWNER_DATE_FROM = "OWNER_DATE_FROM";
    public static final String OWNER_DATE_TO = "OWNER_DATE_TO";
    public static final String OWNER_DESCRIPTION = "OWNER_DESCRIPTION";

    public static final String SIGNS_TABLE = "SIGNS_TABLE";
    public static final String SIGN_ID = "SIGN_ID";
    public static final String SIGN_DESCRIPTION = "SIGN_DESCRIPTION";
    public static final String SIGN_DATE = "SIGN_DATE";

    public static final String BREEDING_TABLE = "BREEDING_TABLE";
    public static final String BREEDING_ID = "BREEDING_ID";
    public static final String BREEDING_NAME = "BREEDING_NAME";
    public static final String BREEDING_ADDRESS = "BREEDING_ADDRESS";
    public static final String BREEDING_PHONE = "BREEDING_PHONE";
    public static final String BREEDING_EMAIL = "BREEDING_EMAIL";
    public static final String BREEDING_DESCRIPTION = "BREEDING_DESCRIPTION";

    public static final String MESSAGE_TABLE = "MESSAGE_TABLE";
    public static final String MESSAGE_ID = "MESSAGE_ID";
    public static final String MESSAGE_SUBJECT = "MESSAGE_SUBJECT";
    public static final String MESSAGE = "MESSAGE";
    public static final String MESSAGE_DATE_TIME = "MESSAGE_DATE_TIME";
    public static final String MESSAGE_STATUS = "MESSAGE_STATUS";





//    Health tables

    public static final String ALLERGIES_TABLE = "ALLERGIES_TABLE";
    public static final String ALLERGY_ID = "ALLERGY_ID";
    public static final String ALLERGEN = "ALLERGEN";
    public static final String ALLERGY_DESCRIPTION = "ALLERGY_DESCRIPTION";
    public static final String ALLERGY_DETECTION_DATE = "ALLERGY_DETECTION_DATE";
    public static final String WAS_ALLERGY_TREATED = "WAS_ALLERGY_TREATED";
    public static final String ALLERGY_DATE_OF_TREATMENT = "ALLERGY_DATE_OF_TREATMENT";
    public static final String ALLERGY_DATE_OF_NEXT_TREATMENT = "ALLERGY_DATE_OF_NEXT_TREATMENT";
    public static final String ALLERGY_NOTE = "ALLERGY_NOTE";
    public static final String ALLERGY_STAMP_PHOTO = "ALLERGY_STAMP_PHOTO";

    public static final String TEETH_CONTROL_TABLE = "TEETH_CONTROL_TABLE";
    public static final String TEETH_C_ID = "TEETH_C_ID";
    public static final String TEETH_C_DESCRIPTION = "TEETH_C_DESCRIPTION";
    public static final String TEETH_C_DATE = "TEETH_C_DATE";
    public static final String TEETH_C_NEXT_DATE = "TEETH_C_NEXT_DATE";
    public static final String TEETH_C_STAMP_PHOTO = "TEETH_C_STAMP_PHOTO";
    public static final String TEETH_C_NOTE = "TEETH_C_NOTE";

    public static final String RABIES_VACCINE_TABLE = "RABIES_VACCINE_TABLE";
    public static final String RABIES_VACCINE_ID = "RABIES_VACCINE_ID";
    public static final String RABIES_VACCINE_MEDICINE = "RABIES_VACCINE_MEDICINE";
    public static final String RABIES_VACCINE_DESCRIPTION = "RABIES_VACCINE_DESCRIPTION";
    public static final String RABIES_VACCINE_DATE = "RABIES_VACCINE_DATE";
    public static final String RABIES_VACCINE_NEXT_DATE = "RABIES_VACCINE_NEXT_DATE";
    public static final String RABIES_VACCINE_NOTE = "RABIES_VACCINE_NOTE";
    public static final String RABIES_VACCINE_STAMP_PHOTO = "RABIES_VACCINE_STAMP_PHOTO";

    public static final String BIRTH_CONTROL_TABLE = "BIRTH_CONTROL_TABLE";
    public static final String BIRTH_CONTROL_ID = "BIRTH_CONTROL_ID";
    public static final String BIRTH_CONTROL_NUMBER_OF_CHILDREN = "BIRTH_CONTROL_NUMBER_OF_CHILDREN";
    public static final String BIRTH_CONTROL_DESCRIPTION = "BIRTH_CONTROL_DESCRIPTION";
    public static final String BIRTH_CONTROL_DATE = "BIRTH_CONTROL_DATE";
    public static final String BIRTH_CONTROL_NOTE = "BIRTH_CONTROL_NOTE";
    public static final String BIRTH_CONTROL_STAMP_PHOTO = "BIRTH_CONTROL_STAMP_PHOTO";

    public static final String OTHER_VACCINE_TABLE = "OTHER_VACCINE_TABLE";
    public static final String OTHER_VACCINE_ID = "OTHER_VACCINE_ID";
    public static final String OTHER_VACCINE_MEDICINE = "OTHER_VACCINE_MEDICINE";
    public static final String OTHER_VACCINE_DESCRIPTION = "OTHER_VACCINE_DESCRIPTION";
    public static final String OTHER_VACCINE_DATE = "OTHER_VACCINE_DATE";
    public static final String OTHER_VACCINE_NEXT_DATE = "OTHER_VACCINE_NEXT_DATE";
    public static final String OTHER_VACCINE_NOTE = "OTHER_VACCINE_NOTE";
    public static final String OTHER_VACCINE_STAMP_PHOTO = "OTHER_VACCINE_STAMP_PHOTO";

    public static final String DEWORMING_TABLE = "DEWORMING_TABLE";
    public static final String DEWORMING_ID = "DEWORMING_ID";
    public static final String DEWORMING_MEDICINE = "DEWORMING_MEDICINE";
    public static final String DEWORMING_DESCRIPTION = "DEWORMING_DESCRIPTION";
    public static final String DEWORMING_DATE = "DEWORMING_DATE";
    public static final String DEWORMING_NEXT_DATE = "DEWORMING_NEXT_DATE";
    public static final String DEWORMING_NOTE = "DEWORMING_NOTE";
    public static final String DEWORMING_STAMP_PHOTO = "DEWORMING_STAMP_PHOTO";

    public static final String TREATMENT_TABLE = "TREATMENT_TABLE";
    public static final String TREATMENT_ID = "TREATMENT_ID";
    public static final String ILLNESS = "ILLNESS";
    public static final String TREATMENT_DESCRIPTION = "TREATMENT_DESCRIPTION";
    public static final String TREATMENT_DATE = "TREATMENT_DATE";
    public static final String DATE_OF_NEXT_TREATMENT = "DATE_OF_NEXT_TREATMENT";
    public static final String TREATMENT_NOTE = "TREATMENT_NOTE";
    public static final String TREATMENT_STAMP_PHOTO = "TREATMENT_STAMP_PHOTO";
}
