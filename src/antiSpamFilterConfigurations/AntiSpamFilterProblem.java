/*
 * 
 */
package antiSpamFilterConfigurations;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import antiSpamFilter.Controller;

// TODO: Auto-generated Javadoc
/**
 * The Class AntiSpamFilterProblem.
 */
public class AntiSpamFilterProblem extends AbstractDoubleProblem {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The controller. */
	private Controller controller;
	  
  	/**
  	 * Instantiates a new anti spam filter problem.
  	 */

	  /**
  	 * Instantiates a new anti spam filter problem.
  	 *
  	 * @param numberOfVariables the number of variables
  	 */
  	public AntiSpamFilterProblem(Integer numberOfVariables) {
		  controller = Controller.getInstance();
	    setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(2);
	    setName("AntiSpamFilterProblem");

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(-5.0);
	      upperLimit.add(5.0);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	  }

	  /* (non-Javadoc)
  	 * @see org.uma.jmetal.problem.Problem#evaluate(java.lang.Object)
  	 */
  	public void evaluate(DoubleSolution solution){
		//Conta 1 avaliação do algoritmo
		  controller.count();
		    
	    double[] fx = new double[getNumberOfObjectives()];
	    double[] x = new double[getNumberOfVariables()];
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      x[i] = solution.getVariableValue(i) ;
	    }
	    //Define os pesos das regras para os dados pelo algoritmo
	    controller.pesosAlgoritmo(x);
	   
	    fx[0] = controller.calcularFP();

	    fx[1] = controller.calcularFN();
	    
	    solution.setObjective(0, fx[0]);
	    solution.setObjective(1, fx[1]);
	  }
	}
