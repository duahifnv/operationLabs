package transport;

class Cell {
    private final int cost;
    private final int x;
    private final int y;
    private int traffic;
    private boolean hasTraffic;
    private int delta;
    public Cell(int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }
    public void setTraffic(int traffic) {
        this.traffic = traffic;
        this.hasTraffic = true;
    }
    public void addLambda(int lambda) {
        this.traffic += lambda;
        this.hasTraffic = true;
    }
    public boolean isHasTraffic() {
        return hasTraffic;
    }
    public int getCost() {
        return cost;
    }
    public int getTraffic() {
        return traffic;
    }
    public void removeTraffic() {
        this.traffic = 0;
        this.hasTraffic = false;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setDelta(int delta) {
        this.delta = delta;
    }
    public int getDelta() {
        return delta;
    }
}
