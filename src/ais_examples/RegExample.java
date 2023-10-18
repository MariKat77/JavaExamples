package ais_examples;

import org.jacop.constraints.*;
import org.jacop.constraints.regular.Regular;
import org.jacop.core.*;
import org.jacop.search.*;
import org.jacop.util.fsm.*;


public class RegExample {
    
    public void model() {
        Store store = new Store();
        
        IntVar[] var = new IntVar[3];
        var[0] = new IntVar(store, "v"+0, 0, 2);
        var[1] = new IntVar(store, "v"+1, 0, 2);
        var[2] = new IntVar(store, "v"+2, 0, 2);
        FSM g = new FSM();
        

        FSMState[] s = new FSMState[8];
        for (int i=0; i<s.length; i++) {
            s[i] = new FSMState();
            g.allStates.add(s[i]);
        }
        g.initState = s[0];
        g.finalStates.add(s[7]);
        
        s[0].transitions.add(new FSMTransition(new IntervalDomain(0, 0), s[1]));
        s[0].transitions.add(new FSMTransition(new IntervalDomain(1, 1), s[2]));
        s[0].transitions.add(new FSMTransition(new IntervalDomain(2, 2), s[3]));
        
        s[1].transitions.add(new FSMTransition(new IntervalDomain(1, 1), s[4]));
        s[1].transitions.add(new FSMTransition(new IntervalDomain(2, 2), s[5]));
        
        s[2].transitions.add(new FSMTransition(new IntervalDomain(0, 0), s[4]));
        s[2].transitions.add(new FSMTransition(new IntervalDomain(2, 2), s[6]));
        
        s[3].transitions.add(new FSMTransition(new IntervalDomain(0, 0), s[5]));
        s[3].transitions.add(new FSMTransition(new IntervalDomain(1, 1), s[6]));
        
        s[4].transitions.add(new FSMTransition(new IntervalDomain(2, 2), s[7]));
        
        s[5].transitions.add(new FSMTransition(new IntervalDomain(1, 1), s[7]));
        
        s[6].transitions.add(new FSMTransition(new IntervalDomain(0, 0), s[7]));
        
        store.impose(new Regular(g, var));
        
        IntVar cost = new IntVar(store, "cost", 0, 10);
        store.impose(new XeqY(cost, var[2]));
        
        
       store.impose(new XeqC(var[2], 2));
        
        
        SelectChoicePoint select = new SimpleSelect(var, new SmallestDomain(), new IndomainMax());
        Search search = new DepthFirstSearch();
        
        search.getSolutionListener().searchAll(true);
        search.getSolutionListener().recordSolutions(true);
        
        search.labeling(store, select, cost);
        
        search.getSolutionListener().printAllSolutions();
        
    }
    
    
    public static void main(String args[]) {

            new RegExample().model();
	}
}
