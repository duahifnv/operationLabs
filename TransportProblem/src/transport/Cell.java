package transport;

class Cell {
    public final int cost;
    private int traffic;
    private boolean hasTraffic;
    public Cell(int cost) {
        this.cost = cost;
    }
    public void setTraffic(int traffic) {
        this.traffic = traffic;
        this.hasTraffic = true;
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
    public boolean isHasTraffic() {
        return hasTraffic;
    }
}
