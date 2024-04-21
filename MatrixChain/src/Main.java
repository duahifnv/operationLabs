import matrixChain.Matrix;
import matrixChain.MatrixMultiply;
import matrixChain.MatrixSize;
import matrixChain.MatrixChain;
import utils.json.JSONmanager;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * <h2>ЛАБОРАТОРНАЯ РАБОТА №2</h2>
 * <h3>Динамическое умножение матриц</h3>
 * <h3>Реализовать задачу о порядке умножения матриц методом динамического программирования.</h3>
 */
public class Main {
    public static void main(String[] args) {
        String jsonPath = "D:\\DSTU\\operationLabs\\MatrixChain\\json\\random.json";
        ArrayList<MatrixSize> matricesSizes = JSONmanager.parse(jsonPath, MatrixSize.class);
        MatrixChain matrixChain = new MatrixChain(matricesSizes);
        ArrayList<Matrix> matrices = new ArrayList<>();
        for (MatrixSize matrixSize : matricesSizes) {
            matrices.add(new Matrix(matrixSize.rows(), matrixSize.cols(), -10, 10));
        }
        System.out.println(matrixChain);
        Matrix finalMatrix = MatrixMultiply.multiplyChain(matrixChain, matrices);
        System.out.println(finalMatrix);
    }
}