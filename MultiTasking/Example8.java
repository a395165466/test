/**
 * Created by zhangguoqing.zgq on 2017/6/5.
 *
 模拟3个人排除买票，张某、李某和赵某买电影票，售票员只有3张五元的钱，电影票5元一张。

 张某拿20元一张的RMB，李某拿一张10元的RMB买票，赵某拿一张5元的RMB。

 张某、李某和赵某买电影票。

 售票员只有3张五元的钱，电影票5元一张。

 如果售票员找不开钱，就只能等待。请打印出各种买票的情况组合。
 */
public class Example8 {

    private static int restMoney; //剩余钱
    private static int price = 5;  //票单价
    private static int restTickets = 3; //剩余票数
    private Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Example8 example8 = new Example8();
        BuyTicketThread threadA = example8.new BuyTicketThread(10);
        BuyTicketThread threadB = example8.new BuyTicketThread(15);
        BuyTicketThread threadC = example8.new BuyTicketThread(5);

        threadA.start();
        Thread.sleep(500);

        threadB.start();
        Thread.sleep(500);

        threadC.start();
    }

    class BuyTicketThread extends Thread {
        private int money;
        private boolean success = false;

        public BuyTicketThread(int money) {
            this.money = money;
        }

        public void run() {
            while(!success) {
                synchronized (lock) {
                    if (restTickets > 0 && restMoney + price >= money) {
                        restTickets--;
                        restMoney += price;
                        System.out.println("RMB " + money + ", success!");
                        success = true;
                        lock.notifyAll();
                    } else {
                        System.out.println("RMB " + money + ", wait!");
                        lock.notifyAll();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
