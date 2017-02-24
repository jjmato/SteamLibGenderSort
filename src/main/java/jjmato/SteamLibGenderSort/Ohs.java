package jjmato.SteamLibGenderSort;

import java.util.Scanner;
/**
 *
 * @author Solaire
 */
public class Ohs {

    /**
     * @param args the command line arguments
     */
    @Deprecated
    public void main( Scanner s) {
        boolean exit=false;
        String op;
        int m = 0, f = 0, c = 0, t = 0;
        while(!exit){
            op=s.nextLine();
            switch(op){
                case "m":
                    m++;
                    break;
                case "f":
                    f++;
                    break;
                case "c":
                    c++;
                    break;
                case "e":
                    exit=true;
                    break;
                default:
                    break;
            }
        }
        System.out.println("Total: "+t);
        String str=Integer.toString(m);
        Double flt=Double.valueOf(str)*100;
        flt=flt/t;
        System.out.println("Masculinos: "+m + " "+flt+"%");
        str=Integer.toString(f);
        flt=Double.valueOf(str)*100;
        flt=flt/t;
        System.out.println("Femeninos: "+f+" "+flt+"%");
        str=Integer.toString(c);
        flt=Double.valueOf(str)*100;
        flt=flt/t;
        System.out.println("Seleccionables"+c+" "+flt+"%");
    }

    public void doSomething(int t, int m, int f, int c, int o) {

        System.out.println("Total: "+t);
        String str=Integer.toString(m);
        Double flt=Double.valueOf(str)*100;
        flt=flt/t;
        System.out.println("Masculinos: "+m + " "+flt+"%");
        str=Integer.toString(f);
        flt=Double.valueOf(str)*100;
        flt=flt/t;
        System.out.println("Femeninos: "+f+" "+flt+"%");
        str=Integer.toString(c);
        flt=Double.valueOf(str)*100;
        System.out.println("Seleccionables"+c+" "+flt+"%");
        str=Integer.toString(o);
        flt=Double.valueOf(str)*100;
        flt=flt/t;
        System.out.println("Otros: "+o+" "+flt+"%");
    }
}
