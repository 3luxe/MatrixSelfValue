package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Math.abs;

public class MatrixSelfValue {
    private int dim;
    private double [][] a, l, u;
    private double [] L;
    private double e;


    public MatrixSelfValue() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Для обчислення власних значень матриці, вкажіть розмір матриці:");
        dim = Integer.parseInt(reader.readLine());

        a = new double[dim][dim];
        l = new double[dim][dim];
        u = new double[dim][dim];
        L = new double[dim];

        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++) {
                System.out.println("Введіть елемент [" + (i+1) + "] рядка, " +
                        "[" + (j+1) + "] стовпчика, A[" + (i+1) + "][" + (j+1) + "]:");
                a[i][j] = Double.valueOf(reader.readLine());
            }
        System.out.println("Уведіть точність е:");
        e = Double.valueOf(reader.readLine());
        System.out.print("Для обчислень значень маємо таку матрицю:");
        for(int i = 0; i < dim; i++) {
            System.out.println();
            for(int j = 0; j < dim; j++) System.out.print(" " + a[i][j]);
        }
    }

    public static void main(String[] args) throws IOException {
        MatrixSelfValue c = new MatrixSelfValue();
        c.L = c.mainWorkflow();
        System.out.println("\nВласні значення матриці:");
        for (int i = 0; i < c.dim; i++) {
            System.out.print(" " + c.L[i]);
        }
    }

    private double[] mainWorkflow() {
        int r = processing();
        while (r == 0) {
            r = processing();
        }
        for (int i = 0; i < this.dim; i++)
            L[i] = a[i][i];
        return L;
    }

    private int processing() {
        int cc = this.dim;
        //1.L
        for (int i = 0; i < this.dim; i++)
            for (int j = 0; j < this.dim; j++)
                if (i >= j)
                    l[i][j] = a[i][j];
                else
                    l[i][j] = 0;
        //2.U
        for (int i = 0; i < this.dim; i++)
            for (int j = 0; j < this.dim; j++)
                if (i < j)
                    u[i][j] = a[i][j];
                else if (i == j)
                    u[i][j] = 1;
                else
                    u[i][j] = 0;
        //3.S
        for (int i = 0; i < this.dim; i++)
            for (int j = 0; j < this.dim; j++) {
                double s = 0;
                if (i >= j) {
                    for (int k = 0; k < (j - 1); k++)
                        s = s + l[i][k] * u[k][j];
                    l[i][j] = a[i][j] - s;
                } else {
                    for (int k = 0; k < (i - 1); k++)
                        s = s + l[i][k] * u[k][j];
                    u[i][j] = (a[i][j] - s) / l[i][i];
                }
            }
        //4.Al
        double al[][] = new double[this.dim][this.dim];
        for (int i = 0; i < this.dim; i++)
            for (int j = 0; j < this.dim; j++) {
                double s = 0;
                for (int k = 0; k < this.dim; k++)
                    s = s + u[i][k] * l[k][j];
                al[i][j] = s;
            }
        //5.abs
        for (int i = 0; i < this.dim; i++)
            if (abs(al[i][i] - a[i][i]) <= e)
                cc = cc - 1;
        a = al;
        return cc;
    }
}