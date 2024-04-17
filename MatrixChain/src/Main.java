import matrixChain.Matrix;
import matrixChain.MatrixChain;
import utils.json.JSONmanager;
import java.util.ArrayList;

/**
 * <h2>ЛАБОРАТОРНАЯ РАБОТА №2</h2>
 * <h3>Динамическое умножение матриц</h3>
 * <h3>Реализовать задачу о порядке умножения матриц методом динамического программирования.</h3>
 */
public class Main {
    public static void main(String[] args){
        String jsonPath = "D:\\DSTU\\operationLabs\\MatrixChain\\json\\me.json";
        ArrayList<Matrix> matrices = JSONmanager.parse(jsonPath, Matrix.class);
        MatrixChain matrixChain = new MatrixChain(matrices);
        System.out.println(matrixChain);
    }
}