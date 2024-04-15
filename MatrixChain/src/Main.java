import matrixChain.Matrix;
import matrixChain.MatrixChain;
import utils.JSONparser;

import java.io.File;
import java.io.IOException;

/**
 * <h2>ЛАБОРАТОРНАЯ РАБОТА №2</h2>
 * <h3>Динамическое умножение матриц</h3>
 * <h3>Реализовать задачу о порядке умножения матриц методом динамического программирования.</h3>
 */
public class Main {
    public static void main(String[] args) {
        File file = new File("D:\\DSTU\\operationLabs\\MatrixChain\\json\\matrices.json");
        JSONparser<Matrix> jsonParser = new JSONparser<>();
        Matrix[] matrices = new Matrix[2];
        try {
            matrices = jsonParser.parseJsonArray(file, Matrix.class);
        }
        catch (IOException e) {
            System.out.println("Не удалось обработать JSON");
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        MatrixChain matrixChain = new MatrixChain(matrices);
        System.out.println(matrixChain);
    }
}