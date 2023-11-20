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

    private MessageStorage(){
        tweetHistory = new LinkedList<>();
        messageListeners = new HashSet<>();
    }

    public static MessageStorage getInstance(){
        return _instance;
    }

    public void postTweet(User user, String tweetMsg) {
        Tweet newTweet = new Tweet(user.getId(), tweetMsg);
        synchronized(this){
            tweetHistory.addFirst(newTweet);
        }
        for(MessageListener listener: messageListeners) {
            listener.onChange();
        }
    }

    public Collection<Tweet> getLatest(User user) {
        return getLatest(user, NUM_TWEETS);
    }

    public Collection<Tweet> getLatest(User user, int numTweets) { //based on who one is following
        List<Tweet> results = new ArrayList<>(numTweets);

        for(Tweet t: tweetHistory){
            if(user.isFollowing(t)) {
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
}
