
package ohtu;
import java.util.*;

public class Player {
    private String name;
    private String nationality;
    private int assists;
    private int goals;



    public void setName(String name, String nationality, int assists, int goals) {
        this.name = name;
        this.nationality = nationality;
        this.assists = assists;
        this.goals = goals;

    }

    public String getName() {
        return name;
    }



    public String getNationality() {
        return nationality;
    }

    public int getTotalPoints(){
        return goals + assists;
    }

    @Override
    public String toString() {

        return name;
    }

    public void print() {
        System.out.print(String.format("%-25s %-4s %-4s %-3s\n", name, goals+ " +", assists,"= "+ getTotalPoints()));

    }
}
