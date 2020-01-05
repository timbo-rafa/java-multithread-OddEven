
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74
75
class PrintEvenTask implements Runnable{
    Printer printer;
    int max;
    PrintEvenTask(Printer printer, int max){
        this.printer = printer;
        this.max = max;
    }
    @Override
    public void run() {
        for(int i = 2; i <= max; i+=2){        
            printer.printEven(i);
        }   
    }
}
 
class PrintOddTask implements Runnable{
    Printer printer;
    int max;
    PrintOddTask(Printer printer, int max){
        this.printer = printer;
        this.max = max;
    }
    @Override
    public void run() {
        for(int i = 1; i <= max; i+=2){
            printer.printOdd(i);
        }   
    }
}
 
public class Printer {
    boolean evenFlag = false;
    //Prints even numbers 
    public void printEven(int num){
        synchronized (this) {
            while(!evenFlag){
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Thread Interrupted" + e.getMessage());
                }
            }
            System.out.println(Thread.currentThread().getName() + " - " + num);
            evenFlag = false;
            // notify thread waiting for this object's lock
            notify();
        }
    }
    
    //Prints odd numbers
    public void printOdd(int num){
        synchronized (this) {
            while(evenFlag){
                try {
                    //make thread to wait
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Thread Interrupted" + e.getMessage());
                }
            }
            System.out.println(Thread.currentThread().getName() + " - " + num);
            evenFlag = true;
            // notify thread waiting for this object's lock
            notify();
        }
    }
    public static void main(String[] args) {
        Printer printer = new Printer();
        // creating two threads
        Thread t1 = new Thread(new PrintOddTask(printer, 10), "Odd");
        Thread t2 = new Thread(new PrintEvenTask(printer, 10), "Even");
        t1.start();
        t2.start();
    }
}
