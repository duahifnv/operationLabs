package transport;

public class Equation {
    public Integer u = null;
    public Integer v = null;
    public final int d;
    public final int uIdx;
    public final int vIdx;
    public Equation(int uIdx, int vIdx, int d) {
        this.uIdx = uIdx;
        this.vIdx = vIdx;
        this.d = d;
    }
}
