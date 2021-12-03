import OSPRNG.*;

import static java.lang.Math.ceil;
import static java.lang.Math.random;

public class main {
    public static void main(String[] args) {
        //uloha1();
        //uloha2();
        //uloha3();
        //uloha4(1);
        //uloha4n(4);


    }


    public static double zisk(double poplatokTlaciarne, int pocetPredanychKusov, int pocetKupenychBalikov, double cenaPredaja) {
        double nakup = poplatokTlaciarne * pocetKupenychBalikov * 10;
        double predaj =Math.min(pocetKupenychBalikov*10,pocetPredanychKusov) * cenaPredaja;
        double vrateneZisk = 0.65 * poplatokTlaciarne * (pocetKupenychBalikov * 10 - pocetPredanychKusov);
        double zisk = predaj - nakup + vrateneZisk;
        return zisk;
    }

    public static void uloha1() {
        UniformContinuousRNG GeneratorUni = new UniformContinuousRNG(250.0, 420.0);
        TriangularRNG GeneratorTri = new TriangularRNG(0.25, 0.6, 0.95);
        double priemerPocetPredanych = 0;
        int pocetReplikacii = 1000000;
        double poplatokTlaciarne = 0.15;


        double zisk = 0;
        for (int i = 0; i < pocetReplikacii; i++) {
            double dlzkaPredajaDen = GeneratorUni.sample();
            double canePredajaDen = GeneratorTri.sample();

            int pocetPredanychDen = (int) ceil(dlzkaPredajaDen / 2.7);


            zisk += zisk(poplatokTlaciarne, pocetPredanychDen, 15, canePredajaDen);
            priemerPocetPredanych += pocetPredanychDen;
        }


        zisk /= pocetReplikacii;
        System.out.println(zisk);
        priemerPocetPredanych /= pocetReplikacii;
        //System.out.println(priemerPocetPredanych);
    }

    public static void uloha2(){
        TriangularRNG typA = new TriangularRNG(1.0, 1.75, 2.5);
        TriangularRNG typB = new TriangularRNG(0.7, 1.2, 1.7);

        UniformDiscreteRNG dopytArozdel = new UniformDiscreteRNG(40, 79);
        UniformDiscreteRNG dopytBrozdel = new UniformDiscreteRNG(66,154);
        int pocetReplikacii=1000000;
        double ziskA=0;
        double ziskB=0;
        for(int i=0;i<pocetReplikacii;i++){
            double nakladyA=typA.sample();
            double nakladyB=typB.sample();

            int dopytA=dopytArozdel.sample();
            int dopytB=dopytBrozdel.sample();

            ziskA+=3*Math.min(dopytA,70)-nakladyA*70;
            ziskB+=2*Math.min(dopytB,90)-nakladyB*90;
        }
        System.out.println(ziskA/pocetReplikacii);
        System.out.println(ziskB/pocetReplikacii);


    }
    public static void uloha3(){

        UniformContinuousRNG surX=new UniformContinuousRNG(0.0,1.0);
        UniformContinuousRNG surY=new UniformContinuousRNG(0.0,1.0);

        double x0=0.5;
        double y0=0.5;
        double r=0.5;
        double padolDoKruhu=0;
        double pi=3.141592;
        double myPi=0;
        int i=1;
        while(Math.abs(myPi-pi)>0.000001){

            double randomX=surX.sample();
            double randomY=surY.sample();

            if(Math.pow((randomX-x0),2)+Math.pow((randomY-y0),2) <= Math.pow((r),2)){
                padolDoKruhu++;

            }
            myPi=padolDoKruhu/i/Math.pow((r),2);

            i++;
        }
        System.out.println("Pocet replikacii: "+i);
        System.out.println(myPi);



    }

    public static void uloha4(int pocetRozmerov){
        int pocetReplikacii=100000;
        UniformDiscreteRNG generator=new UniformDiscreteRNG(0,1);
        int pocetKrokov=1000;
        double konecnaVzdialenost=0;
        for (int y=0;y<pocetReplikacii;y++) {
            double vzdialenost = 0;
            for (int i = 0; i < pocetKrokov; i++) {
                if (generator.sample() == 1) {
                    vzdialenost++;
                } else {
                    vzdialenost--;
                }
            }
            konecnaVzdialenost+=Math.abs(vzdialenost);


        }
        System.out.println(konecnaVzdialenost/pocetReplikacii);
        System.out.println(Math.sqrt(2*pocetKrokov/Math.PI));

    }
    public static void uloha4n(int pocetRozmerov){
        int pocetReplikacii=100000;
        UniformDiscreteRNG generator=new UniformDiscreteRNG(0,pocetRozmerov*2-1);
        int pocetKrokov=1000;
        double konecnaVzdialenost=0;


        for (int y=0;y<pocetReplikacii;y++) {
            double vzdialenost = 0;
            double[]rozmer= new double[pocetRozmerov];
            for (int i = 0; i < pocetKrokov; i++) {

                int sample=generator.sample();
                int index=(int)Math.floor(sample/2);
                rozmer[index]+= (sample%2==0) ? 1: (-1);
            }
            for (int x=0;x<pocetRozmerov;x++) {
                konecnaVzdialenost += Math.abs(rozmer[x]);
            }
        }
        System.out.println(konecnaVzdialenost/pocetReplikacii);
        System.out.println(Math.sqrt(2*pocetRozmerov*pocetKrokov/Math.PI));

    }
}