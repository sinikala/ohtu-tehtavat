package statistics.matcher;

import java.util.ArrayList;

public class QueryBuilder {
    ArrayList<Matcher> matchers;
    Matcher matcher;

    public QueryBuilder(){
        matchers = new ArrayList<>();
        matcher = new All();
    }

    public Matcher build(){
        Matcher [] args = new Matcher[matchers.size()];
        args = matchers.toArray(args);
        return new And(args);

    }

    public QueryBuilder playsIn(String team){
        matchers.add(new PlaysIn(team));
        return this;
    }

    public QueryBuilder hasAtLeast(int value, String category){
        matchers.add(new HasAtLeast(value, category));
        return this;
    }

    public QueryBuilder hasFewerThan(int value, String category){
        matchers.add(new HasFewerThan(value, category));
        return this;
    }

}
