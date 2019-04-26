import javax.xml.soap.SOAPPart;
import java.util.*;

class Item{
    String name;
    float price;
    int count;
    int id;

    public Item(int id, String name, int count, float price) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public  String getName(){
        return name;
    }

    public void deCount(){
        count--;
        System.out.println("count of "+name+" = "+count);
    }
}

class Currency{
    float value;
    String type;

    public Currency(float value, String type) {
        this.value = value;
        this.type = type;
    }
}


class SVM {
    ArrayList<Item> Items;
    ArrayList<Currency> currencies;
    float totalRevenue;

    public SVM() {
        this.Items = new ArrayList<Item>();
        this.currencies = new ArrayList<Currency>();

        this.currencies.add(new Currency((float) 0.1,"10C"));
        this.currencies.add(new Currency((float)0.2,"20C"));
        this.currencies.add(new Currency((float)0.5,"50C"));
        this.currencies.add(new Currency((float)1.0,"1$"));
        this.currencies.add(new Currency((float)20.0,"20$"));
        this.currencies.add(new Currency((float)50.0,"50$"));

        this.totalRevenue = 0;
    }

    private void sellItem(int id){
        if(this.getItemCount(id)>0) {
            totalRevenue += this.getItemPrice(id);
            Items.get(getIdIndex(id)).deCount();
        }
    }

    private int getIdIndex(int id) {
        for(int i=0; i<this.Items.size(); i++) {
            if (this.Items.get(i).id == id) {
                return i;
            }
        }
        return -1;
    }


    // return  0 => successfully added
    // return -1 => rowsOverLoadException
    // return -2 => columnsOverLoadException
    // return -3 => idIsAlreadyExistException
    public int addItem(int id, String name, int count, float price){
        if(Items.size() >5){
            return -1;
        }else if(count > 5){
            return -2;
        }else if(this.getItemPrice(id)!= -1){
            return -3;
        }
        else{
            Items.add(new Item(id, name, count,price));
            return 0;
        }
    }

    // return -1 => ItemNotFoundException
    // return n ; n>0 successfully returned item count
    public int getItemCount(int id) {
        for(int i=0; i<this.Items.size(); i++){
            if(this.Items.get(i).id == id){
                return this.Items.get(i).count;
            }
        }
        return -1;
    }

    // return -1 => ItemNotFoundException
    // return n ; n>0 successfully returned item count
    public float getItemPrice(int id) {
        for(int i=0; i<this.Items.size(); i++){
            if(this.Items.get(i).id == id){
                return this.Items.get(i).price;
            }
        }
        return -1;
    }
    // return "-1" => ItemNotFoundException
    // return string ; successfully returned item count
    public String getItemName(int id) {
        for(int i=0; i<this.Items.size(); i++){
            if(this.Items.get(i).id == id){
                return this.Items.get(i).name;
            }
        }
        return "-1";
    }

    public ArrayList<String> getNames() {

        ArrayList<String> names = new ArrayList<String>();
         for (int i=0; i<this.Items.size();i++){
             names.add(Items.get(i).name);
         }

        return names;
    }

    private ArrayList<String> getChange(float v)
    {
        ArrayList<String> change = new ArrayList<String>();
        ArrayList<Double> coins = new ArrayList<Double>();

        for (int i =0; i<currencies.size(); i++){
            coins.add(Math.round( currencies.get(i).value* 100.0) / 100.0);
        }
        Collections.sort(coins,Collections.reverseOrder());


        int i=0;
        while(v>0 && i< coins.size()){
            if(v>=coins.get(i)){
                change.add(coins.get(i)+"");
                v-=coins.get(i);
            }else{
                i++;
            }
        }
        return change;
    }

    public void SelectItem(int id){
        System.out.println("count ="+this.getItemCount(id));
        if(this.getItemCount(id)<=0){
            System.out.println("sorry this Item Sold out");
            return;
        }
        Scanner in = new Scanner(System.in);
        float price = getItemPrice(id);
        float balance = 0;

        int state=-1;
        while (state!=0 ) {
            System.out.println("Please select payment method:");
            System.out.println("0. cancel order");
            System.out.println("1. credit card");
            System.out.println("2. coins and notes");


            state = in.nextInt();
            switch (state) {
                case 1:
                    System.out.println("please insert your Credit Card: :hint press \'y\' ");
                    if(this.handleCreditCard(in.next())){
                        this.sellItem(id);
                        System.out.println("thanks for order from my SVM");
                        state = 0;
                    }else {
                        System.out.println("Sorry, invalid credit card. please try again!");
                        continue;
                    }


                    break;
                case 2:
                    System.out.println("please insert the money: ");
                    //set timer to break the code after 30 sec
                   try {
                       while (balance < price) {
                           float value = in.nextFloat();
                           if(this.checkMoneyType(value)){
                               balance+=value;
                               System.out.println("current balance = "+balance);
                           }else{
                               System.out.println("Sorry Invalid currency!, insert another type \n or press \'x\' to cancel order");
                               System.out.println("current balance = "+balance);
                           }
                       }
                   }catch (InputMismatchException e){
                       System.out.println("total change :" + balance);
                       System.out.println("please take the change"+getChange(balance));
                       System.out.println("your order has canceled!");
                       System.out.println("thanks for order from my SVM");
                       state = 0;
                       break;
                   }

                    float change = (float) (Math.round((balance-price) * 100.0) / 100.0);
                    System.out.println("total change :" + change);
                    System.out.println("please take the change"+getChange(change));
                    System.out.println("thanks for order from my SVM");
                    this.sellItem(id);
                    state = 0;

                    break;
                default:
                    System.out.println("sorry invalid input, pleas try again");
            }

        }


    }

    private boolean checkMoneyType(float v) {
        for(int i=0; i< this.currencies.size(); i++){
            if(this.currencies.get(i).value == v)
                return true;
        }

        return false;
    }

    private boolean handleCreditCard(String s) {
        if(s.equals("y")){
            return true;
        }
        else return false;
    }
}
