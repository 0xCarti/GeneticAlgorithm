//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.File;
import java.util.Scanner;

public class BlackBoxFunction implements BlackBoxFunctionInterface {
    private static final String DataFileName = "rndw.txt";
    private int numVar;
    private int numClause;
    private int[] weight;
    private int[][] clause;

    public BlackBoxFunction(String var1) {
        this.readDataSet(var1);
    }

    public BlackBoxFunction() {
        this("src/rndw.txt");
    }

    public int getLength() {
        return this.numVar;
    }

    private void readDataSet(String var1) {
        File var2 = new File(var1);
        Scanner var3 = null;

        try {
            var3 = new Scanner(var2);
        } catch (Exception var7) {
            System.out.println(var7.getMessage());
            System.exit(1);
        }

        this.numVar = var3.nextInt();
        this.numClause = var3.nextInt();
        this.weight = new int[this.numClause];
        this.clause = new int[this.numClause][];

        for(int var4 = 0; var4 < this.numClause; ++var4) {
            int var5 = var3.nextInt();
            this.clause[var4] = new int[var5];
            this.weight[var4] = var3.nextInt();

            for(int var6 = 0; var6 < var5; ++var6) {
                this.clause[var4][var6] = var3.nextInt();
            }
        }

        var3.close();
        System.out.println("Successfully read " + var1 + "!");
    }

    public double function(boolean[] var1) {
        int var2 = 1;
        int var3 = 0;

        for(int var4 = 0; var4 < this.clause.length; ++var4) {
            boolean var5 = false;

            for(int var6 = 0; var6 < this.clause[var4].length && !var5; ++var6) {
                int var7 = this.clause[var4][var6];
                if (var7 > 0 && var1[var7 - 1]) {
                    var5 = true;
                }

                if (this.clause[var4][var6] < 0 && !var1[-var7 - 1]) {
                    var5 = true;
                }
            }

            if (!var5) {
                var2 += this.weight[var4];
                ++var3;
            }
        }

        return 1.0E7D / (double)(var2 + 1);
    }
}
