package agents;

/**
 *  EPSILON-GREEDY Q-LEARNING ALGORITHM
 *
 *  @author chiarapaglioni
 */
public class QLearnerEpsilon implements Agent {

    private double Q[], alpha, alphadecay, epsilon_decay, exploration_rate;
    private int ind_max = 0;

    public QLearnerEpsilon(int numberOfActions) {
        Q = new double[numberOfActions];
        for (int i=0; i<numberOfActions; i++)
            Q[i] = -0.1+Math.random()*0.2;
        epsilon_decay = 0.1;
        alpha = 0.01;
        alphadecay = 1.0;
        exploration_rate = 0.4;
    }

    public double actionProb(int index) {
        double prob_max = 1*(1-exploration_rate)+(1/Q.length)*exploration_rate;
        double prob_min = 0*(1-exploration_rate)+(1/Q.length)*exploration_rate;
        if(index == ind_max){
            return prob_max;
        } else {
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
            // index = (int) Math.random();

        // If BELOW exploration rate --> return index with highest Q-Value
        } else {
            for(int i = 0; i < Q.length; i++){
                if(Q[i] > Q[ind_max]){
                    ind_max = i;
                }
            }
            index = ind_max;
        }
        System.out.println(index);
        return index;
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