package ohtu.verkkokauppa;

import java.util.ArrayList;

public interface KirjanpitoIF {
    void lisaaTapahtuma(String tapahtuma);

    ArrayList<String> getTapahtumat();
}
