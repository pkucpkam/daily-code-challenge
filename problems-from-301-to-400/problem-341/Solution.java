import java.util.*;

class Solution {
    public int minMutation(String startGene, String endGene, String[] bank) {
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        
        if (!bankSet.contains(endGene)) {
            return -1;
        }
        
        char[] choices = {'A', 'C', 'G', 'T'};
        
        Queue<String> queue = new LinkedList<>();
        queue.offer(startGene);
        
        int mutations = 0; 
        
        while (!queue.isEmpty()) {
            int size = queue.size(); 
            
            for (int i = 0; i < size; i++) {
                String currentGene = queue.poll();
                
                if (currentGene.equals(endGene)) {
                    return mutations;
                }
                
                char[] currArray = currentGene.toCharArray();
                for (int j = 0; j < currArray.length; j++) {
                    char originalChar = currArray[j];
                    
                    for (char c : choices) {
                        if (c == originalChar) continue; 
                        
                        currArray[j] = c;
                        String nextGene = new String(currArray);
                        
                        if (bankSet.contains(nextGene)) {
                            bankSet.remove(nextGene);
                            queue.offer(nextGene);
                        }
                    }
                    currArray[j] = originalChar;
                }
            }
            mutations++;
        }
        
        return -1;
    }
}