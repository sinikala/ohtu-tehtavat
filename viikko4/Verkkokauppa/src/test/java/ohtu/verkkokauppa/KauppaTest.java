package ohtu.verkkokauppa;


import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class KauppaTest {
    private Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;
    Kauppa k;

    @Before
    public void setUp() {
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);
        k = new Kauppa(varasto, pankki, viite);
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {

        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));


        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), anyInt());
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaTiedoilla() {

        when(viite.uusi()).thenReturn(42);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");


        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);

    }

    @Test
    public void ostettaessaKahtaVarastossaOlevaaTuotettaPankinMetodiaTilisiirtoKutsutaanOikeillaTiedoilla() {
        when(viite.uusi()).thenReturn(30);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piimä", 3));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);


        k.tilimaksu("maija", "123456");
        verify(pankki).tilisiirto("maija", 30, "123456", "33333-44455", 8);

    }

    @Test
    public void ostettaessaKaksiSamaaTuotettaPankinMetodiaTilisiirtoKutsutaanOikeillaTiedoilla(){
        when(viite.uusi()).thenReturn(30);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);

        k.tilimaksu("maija", "123456");
        verify(pankki).tilisiirto("maija", 30, "123456", "33333-44455", 10);

    }

    @Test
    public void ostettaessaKahtaEriTuotettaJoistaToinenLoppuPankinMetodiaTilisiirtoKutsutaanOikeillaTiedoilla() {
        when(viite.uusi()).thenReturn(30);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piimä", 2));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);


        k.tilimaksu("pekka", "123456");
        verify(pankki).tilisiirto("pekka", 30, "123456", "33333-44455", 5);

    }

    @Test
    public void aloitaAsiointiNollaaEdellisenOstoksenTiedot() {

        when(viite.uusi()).thenReturn(42);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);

    }

    @Test
    public void kauppaPyytaaUudenViitenumeronJokaiselleOstokselle() {


        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        verify(viite, times(1)).uusi();

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("maija", "1234566");
        verify(viite, times(2)).uusi();

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("kalle", "1010101");
        verify(viite, times(3)).uusi();


    }

    @Test
    public void poistaKoristaVahentaaTuotteensaldosta(){
        when(viite.uusi()).thenReturn(30);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.poistaKorista(1);

        k.tilimaksu("maija", "123456");
        verify(pankki).tilisiirto("maija", 30, "123456", "33333-44455", 5);

    }
}