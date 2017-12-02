import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Created by zhangguoqing.zgq on 2017/2/27.
 */
public class SocketProcessor extends Thread {

    private Queue<SocketChannel>        clientQueue;//和接收线程共享的socket队列
    private Map<Long, MyClient>         clientMap;
    private Map<Long, MyClient>         notEmptyClientMap;

    private Selector                    selector;

    private long                        socketId;

    public SocketProcessor(Queue<SocketChannel> clientQueue) {
        this.clientQueue        = clientQueue;
        this.clientMap          = new HashMap<Long, MyClient>();
        this.notEmptyClientMap  = new HashMap<Long, MyClient>();

        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置未捕获异常的处理方法
        setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("xxxxx");
            };
        });
    }

    public void run() {
        while(true) {
            takeNewSockets();
            try {
                readFromSockets();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                prepareWriteToSockets();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void takeNewSockets() {
        SocketChannel socket = this.clientQueue.poll();
        while (socket != null) {
            try {
                SelectionKey key = socket.register(this.selector, SelectionKey.OP_READ);//注册读事件

                long newSokectId = inc();
                MyClient client = new MyClient(newSokectId, socket);//添加socket对应的MyClient作为attachment

                key.attach(client);

                clientMap.put(newSokectId, client);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }

            socket = this.clientQueue.poll();
        }
    }

    //从socket读取完数据之后执行操作（比如数据处理、响应request等操作）
    public void readFromSockets() throws IOException {
        int count = this.selector.selectNow();
        if (count == 0) {
            return;
        }

        Set<SelectionKey> keys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();

            MyClient client = (MyClient) key.attachment();
            if (key.isReadable()) {
                client.read();//从socket读取数据
                System.out.println("read data!");
                client.process();//处理数据，可能需要把数据写道socket

                iterator.remove();
            }
        }
    }

    public void prepareWriteToSockets() throws IOException {
        //获取所有有数据的client
        getNotEmptySocket();

        //对有数据的client注册写事件
        registerWriteEvent();

        //写数据
        writeToSockets();

        //清空非空client
        clearNotEmptySockets();
    }

    //获取有数据待写入的socket，默认写完之后就关闭socket（后续可改）并从clientMap中移除，防止重复register
    public void getNotEmptySocket() {
        Iterator<Map.Entry<Long, MyClient>> iterator = this.clientMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, MyClient> item = iterator.next();
            Long socketId = item.getKey();
            MyClient client = item.getValue();

            if (client != null && client.getWriteBuffer().hasRemaining()) {
                this.notEmptyClientMap.put(socketId, client);
                iterator.remove();
            }
        }
    }

    //将有数据待写入的socket注册write事件
    public void registerWriteEvent() throws ClosedChannelException {
        for (Map.Entry<Long, MyClient> item : this.notEmptyClientMap.entrySet()) {
            MyClient client = item.getValue();

            SelectionKey key = client.getSocket().register(this.selector, SelectionKey.OP_WRITE);
            key.attach(client);
        }
    }

    //将数据写入socket，写完之后关闭
    public void writeToSockets() throws IOException {
        int count = this.selector.selectNow();
        if (count <= 0) {
            return;
        }

        Set<SelectionKey> keys = this.selector.selectedKeys();
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();

            MyClient client = (MyClient) key.attachment();

            if (key.isWritable()) {
                client.write();

                iterator.remove();

                //关闭socket
                key.cancel();
                key.channel().close();
            }
        }
    }

    //清空notEmptyClientMap
    public void clearNotEmptySockets() {
        Iterator<Map.Entry<Long, MyClient>> iterator = this.notEmptyClientMap.entrySet().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    public long inc() {
        return this.socketId++;
    }
}