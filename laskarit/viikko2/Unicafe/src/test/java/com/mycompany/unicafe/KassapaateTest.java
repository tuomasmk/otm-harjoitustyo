package com.mycompany.unicafe;

import junit.framework.AssertionFailedError;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    
    Kassapaate paate;

    @Before
    public void setUp() {
        paate = new Kassapaate();
    }
    
    @Test
    public void kassapaateOnLuotuOikein() {
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisenLounaanOstaminenKasvattaaMyytyjenLounaidenMaaraa() {
        paate.syoEdullisesti(240);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanLounaanOstaminenKasvattaaMyytyjenLounaidenMaaraa() {
        paate.syoMaukkaasti(400);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisestaLounaastaAnnetaanRahaaTakaisin() {
        assertEquals(260, paate.syoEdullisesti(500));
    }
    
    @Test
    public void maukkaastaLounaastaAnnetaanRahaaTakaisin() {
        assertEquals(600, paate.syoMaukkaasti(1000));
    }
    
    @Test
    public void myytyLounasKasvattaaKassanRahamaaraa() {
        paate.syoEdullisesti(500);
        assertEquals(100240, paate.kassassaRahaa());
        paate.syoMaukkaasti(500);
        assertEquals(100640, paate.kassassaRahaa());
    }
    
    @Test
    public void riittamatonMaksuEiKasvataMyytyjenLounaidenMaaraa() {
        paate.syoEdullisesti(100);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        paate.syoMaukkaasti(100);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void riittamatonMaksuEiKasvataKassanRahamaaraa() {
        paate.syoEdullisesti(100);
        assertEquals(100000, paate.kassassaRahaa());
        paate.syoMaukkaasti(100);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void riittamatonMaksuPalautetaan() {
        assertEquals(100, paate.syoEdullisesti(100));
        assertEquals(100, paate.syoMaukkaasti(100));
    }
    
    @Test
    public void edullisenLounaanOstaminenKortillaKasvattaaMyytyjenLounaidenMaaraa() {
        Maksukortti kortti = new Maksukortti(1000);
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanLounaanOstaminenKortillaKasvattaaMyytyjenLounaidenMaaraa() {
        Maksukortti kortti = new Maksukortti(1000);
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kortillaMyytyLounasEiKasvattaKassanRahamaaraa() {
        Maksukortti kortti = new Maksukortti(1000);
        paate.syoEdullisesti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void riittamatonKorttimaksuEiKasvataMyytyjenLounaidenMaaraa() {
        Maksukortti kortti = new Maksukortti(100);
        paate.syoEdullisesti(kortti);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        paate.syoMaukkaasti(kortti);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kortiltaEiVahennetaMitaanJosSaldoEiRiita() {
        Maksukortti kortti = new Maksukortti(100);
        paate.syoEdullisesti(kortti);
        assertEquals(100, kortti.saldo());
        paate.syoMaukkaasti(kortti);
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void riittamatonKorttimaksuPalauttaaFalse() {
        Maksukortti kortti = new Maksukortti(100);
        assertFalse(paate.syoEdullisesti(kortti));
        assertFalse(paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void rahanLataaminenKortilleKasvattaaSaldoa() {
        Maksukortti kortti = new Maksukortti(100);
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(1100, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKortilleKasvattaaKassaa() {
        Maksukortti kortti = new Maksukortti(100);
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000, paate.kassassaRahaa());
    }
    
    @Test
    public void negatiivisenSummanLataaminenEiTeeMitaan() {
        Maksukortti kortti = new Maksukortti(1000);
        paate.lataaRahaaKortille(kortti, -100000);
        assertEquals(1000, kortti.saldo());
        assertEquals(100000, paate.kassassaRahaa());
    }
}
