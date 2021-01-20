import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.ArrayList;

public class tabularmethod {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter number of variables");
        int num = in.nextInt();
        while (num <=0 || num>26) {
            System.out.println("error");
            System.out.println("enter the number again ");
            num = in.nextInt();
        }
        System.out.println("enter minterms , if you finished enter -1");
        int min[] = new int[(int) Math.pow(2, num)];
        int dont[] = new int[(int) Math.pow(2, num)];
        int NOm = 0;
        int NOd = 0;
        NOm = fellArray(min);
        System.out.println("enter don't care , if you finished enter -1");
        NOd = fellArray(dont);
        int array[] = new int[NOm + NOd];
        for (int i = 0; i < NOm; i++) {
            array[i] = min[i];
        }
        for (int i = 0; i < NOd; i++) {
            array[i + NOm] = dont[i];
        }
        int length = NOm + NOd;
        if(length == Math.pow(2,num))
        {
            System.out.println(" the final answer is : 1");
            return;
        }
        boolean test[] = new boolean[length];
        ArrayList<ArrayList<Integer>> firstList =
                new ArrayList<ArrayList<Integer>>(num + 1);

        for (int i = 0; i <= num; i++) {
            ArrayList<Integer> a = new ArrayList<Integer>();
            for (int j = 0; j < length; j++) {
                if (numberOfOnes(array[j]) == i) {

                    a.add(array[j]);
                }
            }
            firstList.add(i, a);
        }
        ArrayList<mylist> ListOfObjects = new ArrayList<mylist>();
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < firstList.get(i).size(); j++) {

                for (int k = 0; k < firstList.get(i + 1).size(); k++) {

                    if (firstList.get(i + 1).get(k) > firstList.get(i).get(j) && numberOfOnes(firstList.get(i + 1).get(k) - firstList.get(i).get(j)) == 1)
                    {
                        mylist first = new mylist(num);
                        first.num = firstList.get(i).get(j);
                        first.arr.add(firstList.get(i + 1).get(k) - firstList.get(i).get(j));
                        first.ones=i;
                        ListOfObjects.add(first);
                        for (int y = 0; y < length; y++) {
                            if (array[y] == firstList.get(i + 1).get(k))
                                test[y] = true;
                            if (array[y] == firstList.get(i).get(j))
                                test[y] = true;
                        }
                    }
                }

            }
        }
        ArrayList<mylist> helplist = new ArrayList<mylist>();

        for(int i=0;i<num;i++)
        {
            while(ListOfObjects.size() != 0 )
            {

                Collections.sort(ListOfObjects.get(0).arr);
                for (int j = 1; j < ListOfObjects.size(); j++)
                {
                    Collections.sort(ListOfObjects.get(j).arr);
                    if (ListOfObjects.get(0).arr.equals(ListOfObjects.get(j).arr) )
                    {
                        if (ListOfObjects.get(0).num < ListOfObjects.get(j).num && numberOfOnes(ListOfObjects.get(j).num - ListOfObjects.get(0).num) == 1  && ListOfObjects.get(j).ones-ListOfObjects.get(0).ones==1) {
                            mylist help = new mylist(num);
                            ListOfObjects.get(0).test = true;
                            ListOfObjects.get(j).test = true;
                            help.num =  ListOfObjects.get(0).num;
                            help.ones = ListOfObjects.get(0).ones;
                            for(int z=0;z<ListOfObjects.get(0).arr.size();z++){
                                help.arr.add(ListOfObjects.get(0).arr.get(z)) ;
                            }
                            help.arr.add(ListOfObjects.get(j).num - ListOfObjects.get(0).num);
                            helplist.add(help);
                        }else if(ListOfObjects.get(j).num == ListOfObjects.get(0).num){
                            ListOfObjects.remove(j); j--;
                        }
                    }
                }
                if(ListOfObjects.get(0).test == true)
                    ListOfObjects.remove(0);
                else{
                    helplist.add(ListOfObjects.get(0));
                    ListOfObjects.remove(0);
                }}
            while(helplist.size()!=0)
            {
                ListOfObjects.add(helplist.get(0));
                helplist.remove(0);
            }
        }
        for(int i=0;i<length;i++)
        {
            if(test[i] == false)
            {
                mylist help = new mylist(num);
                help.num = array[i];
                ListOfObjects.add(help);
            }
        }
        for(int i =0; i<ListOfObjects.size();i++)
            preper(ListOfObjects.get(i));
        ArrayList <Integer> minterms = new ArrayList<Integer>();
        for(int i=0; i<NOm;i++)
            minterms.add(min[i]);

        ArrayList<mylist> essential = new ArrayList<mylist>();
        boolean Essential=false,rowdomenance = false,coldomenance = false;
        int counter =0;
        do {
            Essential = false;    rowdomenance = false; coldomenance = false;
            boolean Towdimintional1[][] = new boolean[ListOfObjects.size()][minterms.size()];
            for(int i=0;i<ListOfObjects.size();i++)
            {
                for(int j=0;j<minterms.size();j++)
                {
                    for(int k=0;k<ListOfObjects.get(i).min.size();k++)
                    {
                        if(ListOfObjects.get(i).min.get(k)+ListOfObjects.get(i).num == minterms.get(j)) {
                            Towdimintional1[i][j] = true;
                            break;
                        }
                    }
                }
            }

            for (int j = 0; j < minterms.size(); j++) {
                int temp = -1;
                boolean found = false;
                for (int i = 0; i < ListOfObjects.size(); i++) {
                    if (Towdimintional1[i][j] == true && !found) {
                        temp = i;
                        found = true;
                        continue;
                    }
                    if (Towdimintional1[i][j] == true) {
                        temp = -1;
                        break;
                    }
                }
                if (temp != -1) {
                    ListOfObjects.get(temp).test = true;
                    Essential = true;
                }
            }
            if(Essential)
                for (int i = 0; i < ListOfObjects.size(); i++) {
                    if (ListOfObjects.get(i).test == true) {
                        essential.add(ListOfObjects.get(i));
                        ListOfObjects.remove(i);
                        i--;
                    }
                }
            if(Essential)
                for (int i = 0; i < essential.size(); i++) {
                    for (int j = 0; j < minterms.size(); j++) {
                        for (int k = 0; k < essential.get(i).min.size(); k++) {
                            if (essential.get(i).min.get(k) + essential.get(i).num == minterms.get(j)) {
                                minterms.remove(j);
                                j--;
                                break;
                            }
                        }
                    }
                }
            boolean Towdimintional2[][] = new boolean[ListOfObjects.size()][minterms.size()];
            if(!Essential)
                Towdimintional2 = Towdimintional1;
            else
            {
                for(int i=0;i<ListOfObjects.size();i++)
                {
                    for(int j=0;j<minterms.size();j++)
                    {
                        for(int k=0;k<ListOfObjects.get(i).min.size();k++)
                        {
                            if(ListOfObjects.get(i).min.get(k)+ListOfObjects.get(i).num == minterms.get(j)) {
                                Towdimintional2[i][j] = true;
                                break;
                            }
                        }
                    }
                }
            }
            if((Essential || counter == 0 )&& minterms.size()!=0)
            {
                for(int i=0;i<ListOfObjects.size();i++)
                {

                    for(int j=0;j<ListOfObjects.size();j++)
                    {
                        boolean found=false;
                        if(ListOfObjects.get(i).cast<ListOfObjects.get(j).cast && i!=j)
                        {
                            for(int k=0;k<minterms.size();k++)
                            {
                                if(Towdimintional2[i][k] == false && Towdimintional2[j][k] == true)
                                {
                                    found = true; break;
                                }
                            }
                            if(found == false) {
                                ListOfObjects.get(j).test = true;
                                rowdomenance = true;
                            }
                        }
                    }
                }
            }
            if(rowdomenance)
                for (int i = 0; i < ListOfObjects.size(); i++) {
                    if (ListOfObjects.get(i).test == true) {
                        ListOfObjects.remove(i);
                        i--;
                    }
                }

            boolean Towdimintional3[][] = new boolean[ListOfObjects.size()][minterms.size()];
            if(!rowdomenance)
                Towdimintional3 = Towdimintional2;
            else
            {
                for(int i=0;i<ListOfObjects.size();i++)
                {
                    for(int j=0;j<minterms.size();j++)
                    {
                        for(int k=0;k<ListOfObjects.get(i).min.size();k++)
                        {
                            if(ListOfObjects.get(i).min.get(k)+ListOfObjects.get(i).num == minterms.get(j)) {
                                Towdimintional3[i][j] = true;
                                break;
                            }
                        }
                    }
                }
            }
            if((Essential || counter==0) && minterms.size()!=0)
            {
                ArrayList<Integer> index = new ArrayList<Integer>();
                for(int j=0;j<minterms.size();j++)
                {
                    boolean found=false;
                    for(int g=0;g<index.size();g++)
                        if(index.get(g) == j)
                        {
                            found = true;
                            break;
                        }
                    if(found)
                        continue;
                    for(int i=0;i<minterms.size();i++)
                    {
                        found=false;

                        if (i != j && !found) {
                            for(int k=0;k<ListOfObjects.size();k++) {

                                if (Towdimintional3[k][j] == false && Towdimintional3[k][i] == true) {
                                    found = true;
                                    break;
                                }
                            }

                            if (found == false) {
                                index.add(j);
                                coldomenance = true;
                            }
                        }
                    }
                }
                for(int i=0;i<index.size();i++)
                {
                    minterms.remove(index.get(i));
                }
            }

            counter++;
        }while ((coldomenance || rowdomenance) && ListOfObjects.size()!=0 && minterms.size()!=0);


        if(ListOfObjects.size() == 1 && minterms.size()!=0)
            essential.add(ListOfObjects.get(0));

        if(essential.size() !=0)
        {
            System.out.print("essential :   ");
            for(int i=0;i<essential.size();i++)
            {
                for(int j=0;j<essential.get(i).name.size();j++)
                {
                    System.out.print(essential.get(i).name.get(j));
                }
                System.out.print("        ");
            }
        }

        ArrayList<Integer> minimumCost= new ArrayList<>();
        ArrayList<ArrayList<Integer>> forhelp1 =
                new ArrayList<ArrayList<Integer>>();
        if(ListOfObjects.size() > 1 && minterms.size()!=0 )
        {
            ArrayList<ArrayList<Integer>> patric =
                    new ArrayList<ArrayList<Integer>>();
            for(int j=0;j<minterms.size();j++)
            {
                ArrayList<Integer> h = new ArrayList<>();
                for(int i=0;i<ListOfObjects.size();i++)
                {
                    for(int k=0;k<ListOfObjects.get(i).min.size();k++)
                    {
                        if(ListOfObjects.get(i).min.get(k)+ListOfObjects.get(i).num == minterms.get(j)) {
                            h.add(i);
                            break;
                        }
                    }
                }
                patric.add(h);
            }

            for(int i=0;i<patric.get(0).size();i++)
            {
                ArrayList<Integer> h = new ArrayList<>();
                h.add(patric.get(0).get(i));
                forhelp1.add(h);
            }
            for(int i=1;i<patric.size();i++)
            {
                int temp=forhelp1.size();
                for(int j=1;j<patric.get(i).size();j++)
                {
                    for(int k =0;k<temp;k++)
                    {
                        ArrayList<Integer> h = new ArrayList<>();
                        for(int g=0;g<forhelp1.get(k).size();g++)
                        {
                            h.add(forhelp1.get(k).get(g));
                        }

                        forhelp1.add(h);
                    }
                }
                for(int j=0;j<patric.get(i).size();j++)
                {
                    counter =0;
                    for(int k=temp*j;k<forhelp1.size() && counter<temp;k++) {
                        forhelp1.get(k).add(patric.get(i).get(j));
                        counter++;
                    }
                }
            }
            for(int i=0;i<forhelp1.size();i++)
                for(int j=0;j<forhelp1.get(i).size();j++)
                    for(int k=j+1;k<forhelp1.get(i).size();k++)
                    {
                        if(forhelp1.get(i).get(j) == forhelp1.get(i).get(k))
                        {
                            forhelp1.get(i).remove(k);
                            k--;
                        }
                    }
            int arrCost[] = new int[forhelp1.size()];
            boolean checkEssential[] = new boolean[num];
            for(int i=0;i<essential.size();i++)
            {
                for(int j=0;j<num;j++)
                {
                    if(essential.get(i).inverter[j] == true)
                        checkEssential[j]= true;
                }
            }
            for(int i=0;i<forhelp1.size();i++)
            {
                counter=0;
                boolean helpCost[] = new boolean[num];
                for(int j=0;j<forhelp1.get(i).size();j++)
                {
                    for(int k=0;k<num;k++)
                    {
                        int number =forhelp1.get(i).get(j);
                        if(ListOfObjects.get(number).inverter[k] == true && checkEssential[k] == false)
                            helpCost[k] = true;
                    }
                    counter += ListOfObjects.get(forhelp1.get(i).get(j)).countAlphapit;
                }
                for(int j=0;j<num;j++)
                {
                    if(helpCost[j] == true)
                        counter++;
                }
                counter +=forhelp1.get(i).size();
                arrCost[i] = counter;
            }
            int minimum = arrCost[0];
            int index=0;
            for(int i=0;i<arrCost.length;i++)
            {
                if(minimum>arrCost[i]) {
                    minimum = arrCost[i];
                    index = i;
                }
            }

            for(int i=0;i<arrCost.length;i++)
                if(arrCost[i] == minimum)
                    minimumCost.add(i);

            System.out.println();
            System.out.print("after using patrick method:");
        }
        System.out.println();
        System.out.print("final answer is :");
        int temp=minimumCost.size();
        do{
            System.out.println();
            for(int i=0;i<essential.size();i++)
            {
                for(int j=0;j<essential.get(i).name.size();j++)
                {
                    System.out.print(essential.get(i).name.get(j));
                }
                System.out.print("        ");

            }
            if(temp >0)
                for(int k=0;k<forhelp1.get(minimumCost.get(temp-1)).size();k++) {
                    for(int g=0;g<ListOfObjects.get(forhelp1.get(minimumCost.get(temp-1)).get(k)).name.size();g++)
                    {
                        System.out.print(ListOfObjects.get(forhelp1.get(minimumCost.get(temp-1)).get(k)).name.get(g));
                    }
                    System.out.print("          ");
                }
            temp--;
            if(temp > 0)
                System.out.println("(((((OR)))))");
        }while (temp>0);
        System.out.println();
        System.out.println("if you want to continue enter 0");
        int x = in.nextInt();
    }






    public static int fellArray(int arr[]) {
        Scanner in = new Scanner(System.in);
        int num=0,i=0;
        while((num = in.nextInt())!=-1) {
            while (num>arr.length || num<0){
                System.out.println("please enter the number again");
                num = in.nextInt();
            }
            if(num == -1)
                break;
            boolean found = false;
            for(int j=0;j<i;j++)
                if(arr[j] == num){
                    found = true;
                    break;
                }
            if(found == true)
                continue;
            arr[i] = num;
            i++;
        }
        return i;
    }

    public static int numberOfOnes(int n)
    {
        int counter=0;
        while(n>0) {
            if(n%2 == 1)
                counter++;
            n /=2;
        }
        return counter;
    }

    public static void preper(mylist x)
    {
        boolean inver[] = new boolean[x.size];
        int arr[] = new int[x.size]; int counter =x.size-1;
        int n = x.num;
        while(counter >=0)
        {
            arr[counter--] = n%2;
            n /=2;
        }
        boolean found = false;
        counter =0;
        for(int i=0;i<x.size;i++)
        {
            for(int j=0;j<x.arr.size();j++)
            {
                if(Math.pow(2,x.size-1-i) == x.arr.get(j)) {
                    found = true;
                    break;
                }
            }
            if(found == false)
            {
                if(arr[i] == 1)
                {
                    x.name.add((char)(65+i));
                    x.countAlphapit++;
                }else
                {
                    inver[i] = true;
                    x.name.add((char)(65+i));
                    x.name.add('\'');
                    x.countAlphapit++;
                }
            }
            found = false;
        }
        x.cast = 1+ x.name.size();
        x.inverter = inver;
        for(int i=0;i<x.arr.size();i++)
        {

            int g = x.min.size();
            for(int j=0;j<g;j++)
            {
                x.min.add(x.arr.get(i)+x.min.get(j));

            }
            x.min.add(x.arr.get(i));
        }
        x.min.add(0);
    }


}