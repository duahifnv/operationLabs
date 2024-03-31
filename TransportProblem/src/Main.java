import transport.*;

 class Main {
    public static void main(String[] args) {
        int[] srcWeights = {200, 120, 190};
        int[] dstWeights = {100, 90, 200, 30, 80};
        int[][] costs = {{1, 2, 4, 1, 5},
                         {1, 2, 1, 3, 1},
                         {2, 1, 3, 3, 1}};
        Transport transport = new Transport(srcWeights, dstWeights, costs);
    }
}