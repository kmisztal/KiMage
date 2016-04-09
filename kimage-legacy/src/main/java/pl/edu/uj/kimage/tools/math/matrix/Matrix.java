package pl.edu.uj.kimage.tools.math.matrix;

import static java.lang.Math.sqrt;

/**
 * @author Krzysztof
 */

/**
 * ***********************************************************************
 * Compilation: javac Matrix.java Execution: java Matrix
 *
 * A bare-bones immutable data type for M-by-N matrices.
 *
 ************************************************************************
 */
final public class Matrix {

    private final int M;             // number of rows
    private final int N;             // number of columns
    private final double[][] data;   // M-by-N array

    // create M-by-N matrix of 0's
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }

    // create matrix based on 2d array
    public Matrix(double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, N);
        }
    }

    // copy constructor
    private Matrix(Matrix A) {
        this(A.data);
    }

    // create and return a random M-by-N matrix with values between 0 and 1
    public static Matrix random(int M, int N) {
        Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j) {
                A.data[i][j] = Math.random();
            }
        }
        return A;
    }

    // create and return the N-by-N identity matrix
    public static Matrix identity(int N) {
        Matrix I = new Matrix(N, N);
        for (int i = 0; i < N; ++i) {
            I.data[i][i] = 1;
        }
        return I;
    }

    private static Matrix createSubMatrix(Matrix matrix, int excluding_row, int excluding_col) {
        Matrix mat = new Matrix(matrix.getRowsNumber() - 1, matrix.getColumnsNumber() - 1);
        int r = -1;
        for (int i = 0; i < matrix.getRowsNumber(); ++i) {
            if (i == excluding_row) {
                continue;
            }
            r++;
            int c = -1;
            for (int j = 0; j < matrix.getColumnsNumber(); ++j) {
                if (j == excluding_col) {
                    continue;
                }
                mat.set(r, ++c, matrix.get(i, j));
            }
        }
        return mat;
    }

    //returns 1 if i is even and -1 otherwise
    private static double changeSign(int i) {
        return (i % 2 == 0) ? 1 : -1;
    }

    // test client
    public static void main(String[] args) throws NoSquareException {
        double[][] d = {{1, 2, 3}, {0, 5, 6}, {0, 0, 3}};
        Matrix D = new Matrix(d);
        System.out.println(D);
        System.out.println();
        System.out.println(D.determinant());
//
//        Matrix A = Matrix.random(5, 5);
//        A.show();
//        System.out.println();
//
//        A.swap(1, 2);
//        A.show();
//        System.out.println();
//
//        Matrix B = A.transpose();
//        B.show();
//        System.out.println();
//
//        Matrix C = Matrix.identity(5);
//        C.show();
//        System.out.println();
//
//        A.plus(B).show();
//        System.out.println();
//
//        B.times(A).show();
//        System.out.println();
//
//        // shouldn't be equal since AB != BA in general
//        System.out.println(A.times(B).eq(B.times(A)));
//        System.out.println();
//
//        Matrix b = Matrix.random(5, 1);
//        b.show();
//        System.out.println();
//
//        Matrix x = A.solve(b);
//        x.show();
//        System.out.println();
//
//        A.times(x).show();

    }

    // swap rows i and j
    private void swap(int i, int j) {
        double[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    // create and return the transpose of the invoking matrix
    public Matrix transpose() {
        Matrix A = new Matrix(N, M);
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j) {
                A.data[j][i] = this.data[i][j];
            }
        }
        return A;
    }

    private Matrix transpose(Matrix matrix) {
        Matrix A = new Matrix(matrix.getRowsNumber(), matrix.getColumnsNumber());
        for (int i = 0; i < matrix.getRowsNumber(); ++i) {
            for (int j = 0; j < matrix.getColumnsNumber(); ++j) {
                A.data[j][i] = matrix.data[i][j];
            }
        }
        return A;
    }

    // return C = A + B
    public Matrix plus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j) {
                C.data[i][j] = A.data[i][j] + B.data[i][j];
            }
        }
        return C;
    }

    // return C = A - B
    public Matrix minus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j) {
                C.data[i][j] = A.data[i][j] - B.data[i][j];
            }
        }
        return C;
    }

    // does A = B exactly?
    public boolean eq(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j) {
                if (A.data[i][j] != B.data[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public double determinant() throws NoSquareException {
        return determinant(this);
    }

    private double determinant(Matrix matrix) throws NoSquareException {
        if (!matrix.isSquare()) {
            throw new NoSquareException("matrix need to be square.");
        }
        if (matrix.getRowsNumber() == 1 && matrix.getColumnsNumber() == 1) {
            return matrix.get(0, 0);
        }
        if (matrix.getRowsNumber() == 2 && matrix.getColumnsNumber() == 2) {
            return (matrix.get(0, 0) * matrix.get(1, 1)) - (matrix.get(0, 1) * matrix.get(1, 0));
        }
        double sum = 0.0;
        for (int i = 0; i < matrix.getColumnsNumber(); ++i) {
            sum += changeSign(i) * matrix.get(0, i) * determinant(createSubMatrix(matrix, 0, i));
        }
        return sum;
    }

    private Matrix cofactor(Matrix matrix) throws NoSquareException {
        Matrix mat = new Matrix(matrix.getRowsNumber(), matrix.getColumnsNumber());
        for (int i = 0; i < matrix.getRowsNumber(); ++i) {
            for (int j = 0; j < matrix.getColumnsNumber(); ++j) {
                mat.set(i, j, changeSign(i) * changeSign(j) * determinant(createSubMatrix(matrix, i, j)));
            }
        }

        return mat;
    }

    public Matrix inverse(Matrix matrix) throws NoSquareException {
        return (transpose(cofactor(matrix)).multiplyByConstant(1.0 / determinant(matrix)));
    }

    // return C = A * B
    public Matrix times(Matrix B) {
        Matrix A = this;
        if (A.N != B.M) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        Matrix C = new Matrix(A.M, B.N);
        for (int i = 0; i < C.M; i++) {
            for (int j = 0; j < C.N; j++) {
                for (int k = 0; k < A.N; k++) {
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
                }
            }
        }
        return C;
    }

    // return x = A^-1 b, assuming A is square and has full rank
    public Matrix solve(Matrix rhs) {
        if (M != N || rhs.M != N || rhs.N != 1) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        // create copies of the data
        Matrix A = new Matrix(this);
        Matrix b = new Matrix(rhs);

        // Gaussian elimination with partial pivoting
        for (int i = 0; i < N; i++) {

            // find pivot row and swap
            int max = i;
            for (int j = i + 1; j < N; j++) {
                if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i])) {
                    max = j;
                }
            }
            A.swap(i, max);
            b.swap(i, max);

            // singular
            if (A.data[i][i] == 0.0) {
                throw new RuntimeException("Matrix is singular.");
            }

            // pivot within b
            for (int j = i + 1; j < N; j++) {
                b.data[j][0] -= b.data[i][0] * A.data[j][i] / A.data[i][i];
            }

            // pivot within A
            for (int j = i + 1; j < N; j++) {
                double m = A.data[j][i] / A.data[i][i];
                for (int k = i + 1; k < N; k++) {
                    A.data[j][k] -= A.data[i][k] * m;
                }
                A.data[j][i] = 0.0;
            }
        }

        // back substitution
        Matrix x = new Matrix(N, 1);
        for (int j = N - 1; j >= 0; j--) {
            double t = 0.0;
            for (int k = j + 1; k < N; k++) {
                t += A.data[j][k] * x.data[k][0];
            }
            x.data[j][0] = (b.data[j][0] - t) / A.data[j][j];
        }
        return x;

    }

    // print matrix to standard output
    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                ret += String.format("%15.4f ", data[i][j]);
            }
            System.out.println();
            ret += '\n';
        }
        return ret;
    }

    private boolean isSquare() {
        return M == N;
    }

    public int getColumnsNumber() {
        return N;
    }

    public int getRowsNumber() {
        return M;
    }

    public double get(int i, int j) {
        return data[i][j];
    }

    public void set(int i, int j, double val) {
        data[i][j] = val;
    }

    public Matrix multiplyByConstant(double d) {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                this.data[i][j] *= d;
            }
        }
        return this;
    }

    public Matrix scale(double val) {
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                C.data[i][j] = this.data[i][j] * val;
            }
        }
        return C;
    }

    public Matrix plus(double val, Matrix m) {
        return this.plus(m.scale(val));
    }

    public double trace() {
        if (M != N) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }
        double ret = 0;
        for (int i = 0; i < M; i++) {
            ret += data[i][i];
        }
        return ret;
    }

    public double[][] eig(boolean eigenvalues) {
        if (M == N && N == 2) {
            final double a = data[0][0],
                    b = data[0][1],
                    c = data[1][0],
                    d = data[1][1];
            if (eigenvalues) {
                return new double[][]{
                        {(a + d - sqrt(a * a + 4 * b * c - 2 * a * d + d * d)) / 2.},
                        {(a + d + sqrt(a * a + 4 * b * c - 2 * a * d + d * d)) / 2.}
                };
            } else {
                return new double[][]{
                        {-(-a + d + sqrt(a * a + 4 * b * c - 2 * a * d + d * d)) / (2. * c), 1},
                        {-(-a + d - sqrt(a * a + 4 * b * c - 2 * a * d + d * d)) / (2. * c), 1}
                };
            }
        } else {
            throw new RuntimeException("Not supported yet: matrix should be 2x2");
        }
    }

    public static class NoSquareException extends Exception {

        private NoSquareException(String mes) {
            super(mes);
        }
    }
}
