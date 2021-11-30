import java.util.Random;

public class Chromosome {
    public final boolean[] DNA;
    public final double Pm = 0.001; //Probability of mutation, as given by the pdf
    public final Random rnd = new Random();
    public BlackBoxFunction blackBoxFunction;
    public double fitness;

    public Chromosome(BlackBoxFunction bbf){
        blackBoxFunction = bbf;
        DNA = new boolean[blackBoxFunction.getLength()]; //Create the DNA based on the BBF

        //Fill the array.
        for(int i = 0; i < DNA.length; i++){
            DNA[i] = rnd.nextBoolean();
        }
    }

    public Chromosome[] crossover(Chromosome mother){
        boolean crossover = rnd.nextBoolean(); //Since the probability of crossover given is .5 we can use a boolean to determine if we should cross over or not.
        boolean[] daughter = mother.DNA; //assign the daughter array to the mothers array
        boolean[] son = this.DNA; //assign the son array to the fathers array (this.DNA)
        int index = rnd.nextInt();

        Chromosome[] children = new Chromosome[2];
        for(int i = 0; i < DNA.length; i++){
            //Check if we are crossing over.
            if(crossover && i >= index){
                //Swap the two children.
                boolean temp = son[i];
                son[i] = daughter[i];
                daughter[i] = temp;
            }
        }

        //Mutate the children.
        daughter = mutate(daughter);
        son = mutate(son);

        //Create two new chromosomes with the mutated children.
        children[0] = new Chromosome(blackBoxFunction, son);
        children[1] = new Chromosome(blackBoxFunction, daughter);

        return children;
    }

    public boolean[] mutate(boolean[] children){
        for(int i = 0; i < children.length; i++){
            //Check for mutation
            if(rnd.nextDouble() <= Pm){
                //Flip the bit at children[1]
                children[i] = !children[i];
            }
        }
        return children;
    }

    public double fitness(){
        fitness = blackBoxFunction.function(DNA); //Assign the fitness since the function takes some time.
        return fitness;
    }

    public Chromosome(BlackBoxFunction bbf, boolean[] children){
        blackBoxFunction = bbf; //Assign the BBF
        DNA = new boolean[blackBoxFunction.getLength()]; //Create the DNA based on the BBF

        //Fill the array with children.
        System.arraycopy(children, 0, DNA, 0, DNA.length);
    }
}
