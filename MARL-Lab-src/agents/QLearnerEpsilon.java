package agents;

/**
 *  EPSILON-GREEDY Q-LEARNING ALGORITHM
 *
 *  @author chiarapaglioni
 */
public class QLearnerEpsilon implements Agent {

    private double Q[], alpha, alphadecay, epsilon_decay, exploration_rate;

    public QLearnerEpsilon(int numberOfActions) {
        Q = new double[numberOfActions];
        for (int i=0; i<numberOfActions; i++)
            Q[i] = -0.1+Math.random()*0.2;
        epsilon_decay = 0.1;
        alpha = 0.01;
        alphadecay = 1.0;
        exploration_rate = 0.4;
    }

    // TODO: fix probabilities for visualization
//    public double actionProb(int index) {
//        // double temp = (1/Q.length)*exploration_rate;
//        double temp = exploration_rate/Q.length;
//        System.out.println(temp);
//        double prob_max = (1 * (1-exploration_rate)) + temp;
//        double prob_min = (0 * (1-exploration_rate)) + temp;
//        if(index == getInd_max()){
//            System.out.println("Max Index: " + getInd_max());
//            System.out.println("Max Prob: " + prob_max);
//            return prob_max;
//        } else {
//            System.out.println("Min Prob: " + prob_min);
//            return prob_min;
//        }
//    }

    public double actionProb(int index) {
        double sum = 0.0;
        double action_prob;
        for (double a : Q)
            sum += Math.exp(a/epsilon_decay);
        action_prob = Math.exp(Q[index]/epsilon_decay)/sum;
        System.out.println("Action " + index + " Probability: " + action_prob);
        return action_prob;
    }

    public int selectAction() {
        exploration_rate *= epsilon_decay;
        int index;
        // Random continuous variable between 0 and 1
        double temp = Math.random();
        System.out.println("Random Value: " + temp);

        // If ABOVE exploration rate --> return random index
        if(temp > exploration_rate){
            index = (int) (Math.random() * Q.length);

        // If BELOW exploration rate --> return index with highest Q-Value
        } else {
            index = getInd_max();
        }
        System.out.println("Index Selected Action: " + index);
        System.out.println();
        return index;
    }

    public int getInd_max() {
        int ind = 0;
        for(int i = 0; i < Q.length; i++){
            if(Q[i] > Q[ind]){
                ind = i;
            }
        }
        return ind;
    }

    public void update(int own, int other, double reward) {
        update(own,reward);
    }

    private void update(int index, double reward) {
        Q[index] = Q[index] + alpha * (reward-Q[index]);
        alpha*=alphadecay;
    }

    @Override
    public double getQ(int i) { return Q[i]; }
}