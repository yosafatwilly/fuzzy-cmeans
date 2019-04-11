
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        double[][] data = {
            {15000000, 25000000, 42, 5000000},
            {20000000, 26420000, 72, 5230000},
            {17820000, 22052000, 35, 5200000},
            {16205000, 18500000, 12, 4250000},
            {8000000, 15200000, 5, 3500000},
            {14260000, 19640000, 15, 4023000},
            {7025000, 15230000, 19, 5000000},
            {25032000, 34000000, 28, 8000000},
            {24320100, 35100000, 39, 12500000},
            {25602100, 38200000, 43, 13250000},
            {19872000, 28000000, 27, 10500000},
            {19000000, 25000200, 41, 6350000},
            {16540200, 30000200, 29, 7525000},
            {28920000, 41000000, 58, 15620000},
            {15870200, 26750000, 19, 4025000},
            {26840320, 39000200, 47, 13025000},
            {24601200, 38450000, 64, 11000250},
            {21650000, 37525000, 60, 9850000},
            {18602000, 30500000, 74, 11230000},
            {35024000, 52000000, 73, 18230000},
            {39024300, 52050000, 26, 15725000},
            {27500000, 36500000, 6, 10560000},
            {32500500, 45600000, 10, 16583000},
            {27963000, 40250000, 38, 13670000},
            {37250020, 51000000, 68, 18530000},
            {16523000, 26750000, 9, 8500000},
            {25690000, 39565000, 48, 15250000},
            {34500000, 51065000, 37, 21500000},
            {9850000, 1350000, 13, 2000000},
            {16950000, 24580000, 18, 4500000}
        };

        CMeans f = new CMeans(data, 5, 100, 2, 0.00001);

        System.out.println("Jumlah Variable     : " + f.getJmlVariabel() + "\n");
        System.out.println("Jumlah Data         : " + f.getJmlData() + "\n");
        System.out.println("MPCI                : " + f.getMPCI() + "\n");
        System.out.println("PCI                 : " + f.getPCI() + "\n");
        System.out.println("PEI                 : " + f.getPEI() + "\n");
        System.out.println("Fungsi Obyektif     : " + f.getFungsiObyektif() + "\n");

        //Keanggotaan
        for (int i = 0; i < f.getKeanggotaan().length; i++) {
            System.out.println("Keanggotaan data ke " + (i + 1) + " : " + Arrays.toString(f.getKeanggotaan()[i]));
        }
        System.out.println();
        //Pusat Cluster
        for (int i = 0; i < f.getPusatCluster().length; i++) {
            System.out.print("Pusat Cluster ke " + (i + 1) + " : ");
            for (int j = 0; j < f.getJmlVariabel(); j++) {
                System.out.print(String.format("%.40f", f.getPusatCluster()[i][j]));
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
        
        for (int i = 0; i < f.getKeanggotaan().length; i++) {
            double max = 0, tmp = 0;
            int h = 0;
            for (int k = 0; k < f.getJmlCluster(); k++) {
                max = Math.max(f.getKeanggotaan()[i][k], max);
                if (tmp != max)
                    h = k;
                tmp = max;
            }
            System.out.println("Data ke "+(i+1)+" berada di cluster " + h);            
        }
    }
}
