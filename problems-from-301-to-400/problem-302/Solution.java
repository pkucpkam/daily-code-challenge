import java.util.*;

class Twitter {
    private static int timeStamp = 0;
    private Map<Integer, List<Tweet>> tweets; // userId -> list of tweets
    private Map<Integer, Set<Integer>> followees; // userId -> set of followees

    private class Tweet {
        int tweetId;
        int time;

        Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }

    public Twitter() {
        tweets = new HashMap<>();
        followees = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        tweets.putIfAbsent(userId, new ArrayList<>());
        tweets.get(userId).add(new Tweet(tweetId, timeStamp++));
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> b.time - a.time); // Max heap by time

        // Add user's own tweets
        if (tweets.containsKey(userId)) {
            for (Tweet tweet : tweets.get(userId)) {
                pq.offer(tweet);
            }
        }

        // Add followees' tweets
        if (followees.containsKey(userId)) {
            for (int followeeId : followees.get(userId)) {
                if (tweets.containsKey(followeeId)) {
                    for (Tweet tweet : tweets.get(followeeId)) {
                        pq.offer(tweet);
                    }
                }
            }
        }

        // Get top 10 most recent tweets
        List<Integer> result = new ArrayList<>();
        int count = 0;
        while (!pq.isEmpty() && count < 10) {
            result.add(pq.poll().tweetId);
            count++;
        }

        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId)
            return; // A user cannot follow themselves
        followees.putIfAbsent(followerId, new HashSet<>());
        followees.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followerId == followeeId)
            return;
        if (followees.containsKey(followerId)) {
            followees.get(followerId).remove(followeeId);
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */