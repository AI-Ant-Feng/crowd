import java.util.concurrent.TimeUnit;

class MyData{

    public volatile int number = 0;

    public void addTo60(){
        this.number = 60;
    }
}

public class VolatileTest {

    public static void main(String[] args) {
        versiableVolatile();
    }

    private static void versiableVolatile() {
        MyData myData = new MyData();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try{
                TimeUnit.SECONDS.sleep(3);
                myData.addTo60();
                System.out.println(Thread.currentThread().getName()+"\t update number value: " + myData.number);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        },"AAA").start();

        while(myData.number == 0){}
        System.out.println(Thread.currentThread().getName()+"\t update number value: " + myData.number);
    }
}
