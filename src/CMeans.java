
public class CMeans {
    private double[][] data;
    private int jmlData, jmlVariabel;
    private int jmlCluster;
    private double pembobot;
    private double[][] keanggotaan;
    private double[][] pusatCluster;
    private double PCI;
    private double MPCI;
    private double PEI;
    private double fungsiObyektif;

    public CMeans(double[][] data, int jmlCluster, int maksIterasi, double pembobot, double epsilon) {
        if (jmlCluster < 2) {
            throw new IllegalArgumentException("Invalid parameter jmlCluster = " + jmlCluster);
        }
        if (pembobot < 1) {
            throw new IllegalArgumentException("Invalid parameter pembobot = " + pembobot);
        }
        data = transposeMatrix(data);
        this.data = data;
        this.jmlCluster = jmlCluster;
        this.jmlVariabel = data.length;
        this.jmlData = data[0].length;
        this.pembobot = pembobot;

        pusatCluster = new double[getJmlVariabel()][getJmlCluster()];
        keanggotaan = new double[getJmlData()][getJmlCluster()];

        //Generate Matrix
        matriksRandom();

        //Proses Clustering
        fungsiObyektif = hitungFungsiObyektif();
        double j;
        for (int iterasi = 0; iterasi < maksIterasi; iterasi++) {
            hitungPusatCluster();
            hitungPerubahanMatriks();
            j = hitungFungsiObyektif();
            hitungValidasi();

            if (Math.abs(fungsiObyektif - j) < epsilon) {
                break;
            }
            //update fungsi obyektif
            fungsiObyektif = j;
        }
    }

    private void matriksRandom() {
        for (int i = 0; i < this.jmlData; i++) {
            double jml = 0;
            for (int c = 0; c < this.jmlCluster; c++) {
                keanggotaan[i][c] = (double) (Math.random());
                jml += keanggotaan[i][c];
            }
            for (int c = 0; c < this.jmlCluster; c++) {
                keanggotaan[i][c] /= jml;
            }
        }
    }

    private void hitungPusatCluster() {
        double atas, bawah;
        for (int j = 0; j < this.jmlVariabel; j++) {
            for (int k = 0; k < this.jmlCluster; k++) {
                atas = bawah = 0;
                for (int i = 0; i < this.jmlData; i++) {
                    atas += Math.pow(keanggotaan[i][k], pembobot) * data[j][i];
                    bawah += Math.pow(keanggotaan[i][k], pembobot);
                }
                pusatCluster[j][k] = atas / bawah;
            }
        }
    }

    private double hitungFungsiObyektif() {
        double j = 0;
        double[][] aData = new double[jmlData][jmlVariabel];
        for (int i = 0; i < jmlData; i++) {
            for (int k = 0; k < jmlVariabel; k++) {
                aData[i][k] = data[k][i];
            }
            for (int c = 0; c < jmlCluster; c++) {
                double a = 0;
                for (int k = 0; k < jmlVariabel; k++) {
                    a += Math.sqrt(Math.pow((aData[i][k] - pusatCluster[k][c]), pembobot));
                }
                j += a * Math.pow(keanggotaan[i][c], pembobot);
            }
        }
        return j;
    }

    private void hitungPerubahanMatriks() {
        double[][] aData = new double[jmlData][jmlVariabel];
        for (int i = 0; i < jmlData; i++) {
            double j = 0;
            for (int k = 0; k < jmlVariabel; k++) {
                aData[i][k] = data[k][i];
            }
            //bawah
            for (int c = 0; c < jmlCluster; c++) {
                double a = 0;
                for (int k = 0; k < jmlVariabel; k++) {
                    a += Math.pow((aData[i][k] - pusatCluster[k][c]), pembobot);
                }
                j += Math.pow(a, -1 / (pembobot - 1));
            }
            //atas
            for (int l = 0; l < jmlCluster; l++) {
                double a = 0;
                for (int k = 0; k < jmlVariabel; k++) {
                    a += Math.pow((aData[i][k] - pusatCluster[k][l]), pembobot);
                }
                keanggotaan[i][l] = Math.pow(a, -1 / (pembobot - 1)) / j;
            }
        }
    }

    private void hitungValidasi() {
        double pciTemp = 0;
        double peiTemp = 0;
        for (int i = 0; i < jmlData; i++) {
            for (int j = 0; j < jmlCluster; j++) {
                pciTemp += Math.pow(keanggotaan[i][j], pembobot);
                peiTemp += keanggotaan[i][j] * Math.log(keanggotaan[i][j]) / Math.log(2.0);
            }
        }
        //semakin mendekati 1 maka semakin baik
        PCI = pciTemp / jmlData;

        //semakin mendekati 0 maka semakin baik
        PEI = -1 * (peiTemp / jmlData);

        //semakin mendekati satu maka semakin baik
        MPCI = 1 - ((double) jmlCluster / ((double) jmlCluster - 1)) * (1 - PCI);
    }

    public double[][] getPusatCluster() {
        return transposeMatrix(pusatCluster);
    }

    public double getPCI() {
        return PCI;
    }

    public double getMPCI() {
        return MPCI;
    }

    public double getPEI() {
        return PEI;
    }

    public double[][] getKeanggotaan() {
        return keanggotaan;
    }

    public int getJmlData() {
        return jmlData;
    }

    public int getJmlVariabel() {
        return jmlVariabel;
    }

    public double getFungsiObyektif() {
        return fungsiObyektif;
    }

    public int getJmlCluster() {
        return jmlCluster;
    }

    public double[][] getData() {
        return transposeMatrix(data);
    }

    public static double[][] transposeMatrix(double[][] m) {
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                temp[j][i] = m[i][j];
            }
        }
        return temp;
    }
}
