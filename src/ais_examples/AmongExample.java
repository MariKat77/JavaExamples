package ais_examples;

import org.jacop.constraints.*;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSelect;
import org.jacop.search.SmallestDomain;

public class AmongExample {
    public void model() {
        Store store = new Store();
        IntVar numbers[] = new IntVar[5];
        for (int i = 0; i < numbers.length; i++)
                numbers[i] = new IntVar(store, "n"+i, 0,5);

        IntVar count = new IntVar(store, "count", 2,2);
        count.addDom(4,4); //comment this line to change the solution

        IntVar[] values = new IntVar[2];
        values[0] = new IntVar(store, "val_0", 1,1);
        values[1] = new IntVar(store, "val_1", 3,3);
        
        store.impose(new AmongVar(numbers, values, count));
        
        SelectChoicePoint select = new SimpleSelect(numbers, new SmallestDomain(), new IndomainMin());
        Search search = new DepthFirstSearch();
        
        search.getSolutionListener().searchAll(true);
        search.getSolutionListener().recordSolutions(true);
        
        search.labeling(store, select);
        
        search.getSolutionListener().printAllSolutions();
    }
    
    public static void main(String args[]) {

        new AmongExample().model();
    }
}
