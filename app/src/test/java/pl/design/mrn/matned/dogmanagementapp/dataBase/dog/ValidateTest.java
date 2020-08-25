package pl.design.mrn.matned.dogmanagementapp.dataBase.dog;

import android.widget.Spinner;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ValidateTest {


    @Test
    void notEmpty() {
        String t1 = null;
        String t2 = "";
        String t3 = "1";
        String t4 = "Mongo   ";
        assertFalse(Validate.notEmpty(t1), "NULL STRING");
        assertFalse(Validate.notEmpty(t2), "EMPTY STRING");
        assertTrue(Validate.notEmpty(t3), "NUMERIC STRING");
        assertTrue(Validate.notEmpty(t4), "LITERAL STRING TRIMMED");
    }

    @Test
    void selectedSexIn() {
        Spinner sp = Mockito.mock(Spinner.class);
        when(sp.getSelectedItem()).thenReturn("MALE");
        assertTrue(Validate.selectedSexIn(sp), "passed");
        when(sp.getSelectedItem()).thenReturn("");
        assertFalse(Validate.selectedSexIn(sp), "NOT passed");

    }

    @Test
    void isNumeric() {
        String t1 = "1";
        String t2 = "234   ";
        String t22 = " 2 34   ";
        String t3 = "Mongo";
        String t4 = "";
        String t5 = null;
        assertTrue(Validate.isNumeric(t1), "Numeric");
        assertTrue(Validate.isNumeric(t2), "Numeric trimmed");
        assertTrue(Validate.isNumeric(t22), "Numeric trimmed and whites replaced");
        assertFalse(Validate.isNumeric(t3), "Not numeric");
        assertFalse(Validate.isNumeric(t4), "empty");
        assertFalse(Validate.isNumeric(t5), "Null");
    }

    @Test
    void dateFormat() {
        String wrong = "2012-05-12";
        String good = "25 lip 2012";
        String good2 = "25 Lip 2012";
        assertTrue(Validate.dateFormat(good));
        assertTrue(Validate.dateFormat(good2));
        assertFalse(Validate.dateFormat(wrong));
    }

    @Test
    void noSigns() {
        String wrong = "2012-05-12";
        String good = "25 lip 2012";
        String good2 = "Mongo DB ";
        String good3 = "MongoDB ";
        String wrong2 = "Mongo+DB ";

        assertFalse(Validate.noSignsInText(wrong),"1");
        assertTrue(Validate.noSignsInText(good),"2");
        assertTrue(Validate.noSignsInText(good2), "3");
        assertTrue(Validate.noSignsInText(good3), "4");
        assertFalse(Validate.noSignsInText(wrong2), "5");

    }


}