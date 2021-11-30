import java.text.DecimalFormat;

public class GA {
    public static final DecimalFormat decimal = new DecimalFormat("0.00");
    public static double bestFit = -1;
    public static int myriadPassed = 0; //Stores the amount of 10K generations we have completed.
    public static long[] times = new long[10]; //Array to store times in
    public static BlackBoxFunction blackBoxFunction = new BlackBoxFunction();

    public static void main(String[] args) {
        Population generation = new Population(blackBoxFunction); //Generate the current population

        //Print info about the first generation
        System.out.println("Gen: 0" +
                "\tAvg Fit: " + decimal.format(generation.getAverageFitness()) +
                "\t\tBest Fit: " + decimal.format(generation.maxFit));


        //Get the start time for the 10k iterations.
        long startTime = System.currentTimeMillis();

        //Loop though 100k generations
        for(int i = 1; i <= 100000; i++){
            generation = new Population(generation.generation()); //Mutate and replace the old population with a new one

            //Check to see if the fitness is the best so far.
            if(generation.maxFit > bestFit) bestFit = generation.maxFit;

            //Print generation info
            System.out.println("Gen: " + i +
                            "\tAvg Fit: " + decimal.format(generation.getAverageFitness()) +
                            "\t\tBest Fit: " + decimal.format(generation.maxFit) +
                            "\tMax Fit: " + decimal.format(bestFit));



            //Check if we reached 10k iterations, print if we did.
            if(i % 10000 == 0){
                long finishTime = System.currentTimeMillis(); //Get the finishing time of the 10k iterations.
                times[myriadPassed] = finishTime - startTime; //Store the time taken to complete 10k iterations
                startTime = finishTime; //Update the start time for the next 10k iterations.
                myriadPassed++; //Update the amount of 10k iterations we have completed.
            }

        }

        //Prints the time to complete each 10k iterations
        for(int i = 0; i < times.length; i++){
            System.out.println("Iteration: " + i + "\t[" + times[i] + "]");
        }
    }
}