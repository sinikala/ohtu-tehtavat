
package ohtu.intjoukkosovellus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla.

    public IntJoukko() {
        alusta(KAPASITEETTI, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            kapasiteetti= KAPASITEETTI;
        }
        alusta(kapasiteetti, OLETUSKASVATUS);
    }
    
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            kapasiteetti=KAPASITEETTI;
        }
        if (kasvatuskoko < 0) {
            kasvatuskoko = OLETUSKASVATUS;
        }
        alusta(kapasiteetti, kasvatuskoko);

    }

    public void alusta(int kapasiteetti, int kasvatuskoko){
        ljono = new int[kapasiteetti];
        this.kasvatuskoko = kasvatuskoko;
        alkioidenLkm = 0;
    }

    public boolean lisaa(int luku) {
        if (!onkoJoJoukossa(luku)) {
            if(alkioidenLkm +1 > ljono.length){
                kasvataTaulukkoa();
            }
            ljono[alkioidenLkm++]=luku;
            return true;
        }
        return false;
    }

    private void kasvataTaulukkoa() {
        int [] apu = ljono;
        ljono = new int[alkioidenLkm+kasvatuskoko];
        for (int i = 0; i < apu.length; i++) {
            ljono[i] = apu[i];
        }
    }

    public boolean onkoJoJoukossa(int luku) {
        return IntStream.of(ljono).anyMatch(x -> x == luku );

    }

    public boolean poista(int luku) {
        int [] poistettu = new int[alkioidenLkm];
        int indeksi = 0;
        for (int i = 0; i < alkioidenLkm; i++) {
            if(ljono[i]==luku){
                continue;
            }
            poistettu[indeksi]= ljono[i];
            indeksi++;
        }
        ljono=poistettu;
        alkioidenLkm--;

        return true;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        if (alkioidenLkm==0){return "{}";}
        String tuloste = "{";
        for (int i = 0; i < alkioidenLkm-1; i++) {
            tuloste += ljono[i] + ", ";
        }
        tuloste += ljono[alkioidenLkm-1] + "}";
        return tuloste;

    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko(a.mahtavuus()+b.mahtavuus());
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            x.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            x.lisaa(bTaulu[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();

        for (int i = 0; i <aTaulu.length ; i++) {
            if (b.onkoJoJoukossa(aTaulu[i])){
                y.lisaa(aTaulu[i]);
            }
        }
        return y;

    }
    
    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko erotus = new IntJoukko(a.mahtavuus());
        int[] aTaulu = a.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            if(!(b.onkoJoJoukossa(aTaulu[i]))){
                erotus.lisaa(aTaulu[i]);
            }
        }
        return erotus;
    }
        
}
