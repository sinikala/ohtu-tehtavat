package ohtu;

import java.util.Comparator;

class Sorter implements Comparator<Player> {
    public int compare (Player a, Player b){
        return b.getTotalPoints()-a.getTotalPoints();
    }
}
