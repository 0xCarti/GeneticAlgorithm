import java.util.Random;

public class Population {
    public final int POP_SIZE = 100;
    public final Random rnd = new Random();
    public Chromosome[] chromosomes = new Chromosome[POP_SIZE];
    public double fitSum = 0;
    public double maxFit = -1;

    public Population(BlackBoxFunction blackBoxFunction){
        double fitness; //the current fitness of the chromosome

        for(int i = 0; i < chromosomes.length; i++){
            chromosomes[i] = new Chromosome(blackBoxFunction);  //Generate a new Chromosome, passing the blackBoxFunction
            fitness = chromosomes[i].fitness(); //Get the fitness of the new chromosome
            //Checks the fitness
            if(fitness > maxFit){
                maxFit = fitness;
            }
            fitSum += fitness; //Add the fitness to the sum of the populations fitness
        }
    }

    public Chromosome[] generation(){
        Chromosome[] newGeneration = new Chromosome[POP_SIZE];
        Chromosome[] childGeneration;
        Chromosome father, mother;

        //Cross over a random mother and father, generating 2 children.
        for(int i = 0; i < (newGeneration.length/2); i++){
            //Get random mother and father
            father = rndSelect();
            mother = rndSelect();

            childGeneration = father.crossover(mother);

            //childGeneration[0] = first child, childGeneration[1] = second child.
            newGeneration[i] = childGeneration[0];
            newGeneration[i + (newGeneration.length/2)] = childGeneration[1]; //Add half the generation size because the loop needs to be filled in half the loops.
        }

        return newGeneration;
    }

    public double getAverageFitness(){
        return fitSum/POP_SIZE;
    }

    //Roulette style selection using algorithm from class notes.
    private Chromosome rndSelect(){
        double probability = rnd.nextDouble() * fitSum; //Generate the probability to match
        int index = 0; //The index of the chromosome to be returned

        //This loops while buffer < probability, and adds the fitness of the chromosome to the buffer.
        for(double buffer = chromosomes[index].fitness;
            buffer < probability;
            buffer += chromosomes[++index].fitness){}

        return chromosomes[index];
    }

    public Population(Chromosome[] gen){
        chromosomes = gen;
        double fitness;

        for(int i = 0; i < chromosomes.length; i++){
            fitness = chromosomes[i].fitness(); //Get the fitness of the new Chromosome
            if(fitness > maxFit){ //Checks the fitness
                maxFit = fitness;
            }
            fitSum += fitness; //Add fitness to the sum.
        }
    }
}