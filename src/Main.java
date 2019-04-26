import javax.xml.soap.SOAPPart;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        //machine initialization
        Scanner in=new Scanner(System.in);
        SVM svm = new SVM();

        //machine setup
        svm.addItem(1,"Cola",5,(float) 0.2);
        svm.addItem(2,"Pringles",5,(float) 0.5);
        svm.addItem(3,"Snickers",5,(float) 1);
        svm.addItem(4,"Kinder ",5,(float) .8);
        svm.addItem(5,"Kit Kat",5,(float) 1.2);
        svm.addItem(2,"Raffaello",5,(float) 1.5);

        int state=-1;
        while(state != 0){

            System.out.println("0. Exit");
            ArrayList<String> itemsNames = svm.getNames();
            for(int i=0; i<itemsNames.size() ; i++){
                System.out.println((i+1)+". "+itemsNames.get(i));
            }
            System.out.println("Enter your choice:");
            state = in.nextInt();
            if(state ==0)
                continue;

            System.out.println("you selected : "+ state +" "+ svm.getItemName(state)+ " \nand it cost: "+svm.getItemPrice(state));
            svm.SelectItem(state);
        }

    }

}
