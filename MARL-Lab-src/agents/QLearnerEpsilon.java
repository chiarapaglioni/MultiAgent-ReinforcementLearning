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
    public double actionProb(int index) {
        double prob_max = (1*(1-exploration_rate))+((1/Q.length)*exploration_rate);
        double prob_min = (0*(1-exploration_rate))+((1/Q.length)*exploration_rate);
        if(index == getInd_max()){
            System.out.println("Max Index: " + getInd_max());
            System.out.println("Max Prob: " + prob_max);
            return prob_max;
        } else {
            System.out.println("Min Prob: " + prob_min);
            return prob_min;
        }
    }

    public int selectAction() {
        exploration_rate *= epsilon_decay;
        int index = 0;
        // Random continuous variable between 0 and 1
        double temp = Math.random();
        System.out.println("Temp: " + temp);

        // If ABOVE exploration rate --> return random index
        if(temp > exploration_rate){
            index = (int) (Math.random() * Q.length);

        // If BELOW exploration rate --> return index with highest Q-Value
        } else {
            index = getInd_max();
        }
        System.out.println("Index: " + index);
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