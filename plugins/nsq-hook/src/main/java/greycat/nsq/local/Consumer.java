package greycat.nsq.local;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

import java.io.UnsupportedEncodingException;

public class Consumer
{
    public static void main( String[] args )
    {
        NSQLookup lookup = new DefaultNSQLookup();
        lookup.addLookupAddress("localhost", 4161);
        NSQConsumer consumer = new NSQConsumer(lookup, "Greycat", "MyChannel", (message) -> {

            try {
                System.out.println("Received msg: " +  new String(message.getMessage(), "ASCII"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //now mark the message as finished.
            message.finished();

            //or you could requeue it, which indicates a failure and puts it back on the queue.
            //message.requeue();
        });

        consumer.start();
    }
}