package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussa() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoa() {
        kortti.lataaRahaa(2500);
        assertEquals(3500, kortti.saldo());
    }
    
    @Test
    public void saldoVahenee() {
        assertTrue(kortti.otaRahaa(1000));
        assertEquals(0, kortti.saldo());
    }
    
    @Test
    public void saldoEiVaheneJosKortillaOnLiianVahanRahaa() {
        assertFalse(kortti.otaRahaa(1000000000));
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void maksukortinToStringToimii() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
}
