import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MessageStorage {
    public static final int NUM_TWEETS = 20;

    public interface MessageListener {
        void onChange();
    }

    
    
    private static final MessageStorage _instance = new MessageStorage();
    private final LinkedList<Tweet> tweetHistory;
    private final Set<MessageListener> messageListeners;
    public int count2 = 0;
    
    private MessageStorage(){
        tweetHistory = new LinkedList<>();
        messageListeners = new HashSet<>();
    }

    public static MessageStorage getInstance(){
        return _instance;
    }

    
     public int getNumMessages() {
        int num = 0;
        if(tweetHistory == null){
            num = 0;
        }else{
        num = tweetHistory.size();
        }
        return num; //returns number of messages
    }

    public int getPos(){
        int countPos = 0;
        if(tweetHistory != null){
            String [] positiveWords = {"Thank you", "Good", "Happy"};
            
            for(int i = 0; i < positiveWords.length; i++) {
                for(int k = 0; k < tweetHistory.size(); k++){
                    if (tweetHistory.get(k).toString() == positiveWords[i]){
                        countPos += 1;
                    }
                }
            }
            return countPos;
        }else{
            System.out.println("Error");
            return 0;
        }
    }

    public Collection<Tweet> getLatest(User user) {
        return getLatest(user, NUM_TWEETS);
    }

    public Collection<Tweet> getLatest(User user, int numTweets) { //based on who one is following
        List<Tweet> results = new ArrayList<>(numTweets);

        for(Tweet t: tweetHistory){
            this.count2 += 1;
            if(user.isFollowing(t) || t.getId().equals(user.getId())) {
                results.add(t);
                if(results.size() >= numTweets) {
                    break;
                }
            }
        }

        return results;
    }


    public synchronized void addChangeListener(UserView userView) {
        this.messageListeners.add(userView);
    }

    public synchronized void removeChangeListener(UserView userView) {
        this.messageListeners.remove(userView);
    }

    public void postTweet(User user, String tweetMsg, String time) {

        Tweet newTweet = new Tweet(user.getId(), tweetMsg, time);
        synchronized(this){
            tweetHistory.addFirst(newTweet);
        }
        for(MessageListener listener: messageListeners) {
            listener.onChange();
        }

    }
}
